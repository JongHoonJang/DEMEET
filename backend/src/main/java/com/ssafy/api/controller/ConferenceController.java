package com.ssafy.api.controller;

import com.ssafy.common.auth.SsafyUsersDetails;
import io.openvidu.java.client.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("api-sessions")
public class ConferenceController {
    // SDK와 연결점이 되는 OpenVidu 객체
    private OpenVidu openVidu;
    // 세션 이름과 OpenVidu 세션 객체
    private Map<String, Session> mapSessions = new ConcurrentHashMap<>();
    // 세션 이름과 토큰 (내부는 토큰과 역할 연결)
    private Map<String, Map<String, OpenViduRole>> mapSessionNamesTokens = new ConcurrentHashMap<>();

    // OpenVidu 서버가 듣는 URL
    private String OPENVIDU_URL;
    // OpenVidu 서버와 통신하는 비밀들
    private String SECRET;

    public ConferenceController(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }


    @RequestMapping(value = "/get-token", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> getToken(@ApiIgnore Authentication authentication, @RequestBody String sessionsNameParam, HttpSession httpSession) throws ParseException {
        // 로그인 유저 검증 -> 우리에 맞게 변경 필요할듯

        System.out.println("getting a token from OpenVidu Server | {sessionName} = " + sessionsNameParam);

        JSONObject sessionJSON = (JSONObject) new JSONParser().parse(sessionsNameParam);

        // 연결할 비디오 콜
        String sessionName = (String) sessionJSON.get("sessionName");

        // 이 유저의 역할
        //OpenViduRole role = LoginController.users.get(httpSession.getAttribute("loggedUser")).role;
        OpenViduRole role = OpenViduRole.PUBLISHER;

        // 유저가 컨퍼런스에 참여할 때, 다른 유저에게 넘겨줄 정보
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        // 여기선 로그인할 때 있는 httpSession에 JSON에 넣는다.
        String serverData = "{\"serverData\": \"" + ssafyUsersDetails.getUsername() + "\"}";

        // 서버 데이터와 역할을 담은 connectionProperties 객체 빌드
        ConnectionProperties connectionProperties = new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(role).build();

        JSONObject responseJson = new JSONObject();


        if (this.mapSessions.get(sessionName) != null) {
            // 세션이 이미 있는 경우
            System.out.println("Existing sessing " + sessionName);
            try {
                // 아까 만든 connectionProperties로 새로운 연결 생성
                String token = this.mapSessions.get(sessionName).createConnection(connectionProperties).getToken();

                // 새 토큰을 가지고 있는 맵으로 업데이트
                this.mapSessionNamesTokens.get(sessionName).put(token, role);

                // 토큰과 함께 response 준비
                responseJson.put(0, token);

                // 클라이언트에게 response 리턴
                return new ResponseEntity<>(responseJson, HttpStatus.OK);
            } catch (OpenViduJavaClientException e1) {
                // 만약 내부 에러 발생 시 에러메시지를 클라이언트에게 전송
                return getErrorResponse(e1);
            } catch (OpenViduHttpException e2) {
                if (404 == e2.getStatus()) {
                    // 유효하지 않은 세션 아이디 (유저가 의도치 않게 떠남)
                    // 세션 객체는 더이상 유효하지 않음.
                    // connection 정리 및 새 세션으로 이어감
                    this.mapSessions.remove(sessionName);
                    this.mapSessionNamesTokens.remove(sessionName);
                }
            }
        }

        // 새 세션
        System.out.println("New session " + sessionName);
        try {
            // 새로운 OpenVidu 세션
            Session session = this.openVidu.createSession();
            // 아까 만든 connectionProperties 기반으로 새 커넥션 생성
            String token = session.createConnection(connectionProperties).getToken();

            // 세션과 토큰을 맵에 저장
            this.mapSessions.put(sessionName, session);
            this.mapSessionNamesTokens.put(sessionName, new ConcurrentHashMap<>());
            this.mapSessionNamesTokens.get(sessionName).put(token, role);

            // 토큰과 함께 response 준비
            responseJson.put(0, token);

            // 클라이언트에게 response 리턴
            return new ResponseEntity<>(responseJson, HttpStatus.OK);

        } catch (Exception e) {
            // 만약 내부 에러 발생 시 에러메시지를 클라이언트에게 전송
            return getErrorResponse(e);
        }
    }

    @RequestMapping(value = "/remove-user", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> removeUser(@ApiIgnore Authentication authentication, @RequestBody String sessionNameToken) throws Exception {
        // 로그인 유저 검증 -> 우리에 맞게 변경 필요할듯
        System.out.println("Removing user | {sessionName, token}= " + sessionNameToken);

        // BODY에서 파라미터 가져옴
        JSONObject sessionNameTokenJSON = (JSONObject) new JSONParser().parse(sessionNameToken);
        String sessionName = (String) sessionNameTokenJSON.get("sessionName");
        String token = (String) sessionNameTokenJSON.get("token");

        // 만약 세션이 이미 있을 경우
        if (this.mapSessions.get(sessionName) != null && this.mapSessionNamesTokens.get(sessionName) != null) {
            // 만약 토큰이 있을 경우
            if (this.mapSessionNamesTokens.get(sessionName).remove(token) != null) {
                // 유저가 세션을 떠남
                if (this.mapSessionNamesTokens.get(sessionName).isEmpty()) {
                    // 마지막 유저가 나갔을 경우 세션 삭제
                    this.mapSessions.remove(sessionName);
                }
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                // 그 토큰이 유효하지 않을 때
                System.out.println("Problem in ths app server: the TOKEN wasn't valid");
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            // 세션이 존재하지 않을 경우
            System.out.println("Problem in the app server: the SESSION does not exist");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<JSONObject> getErrorResponse(Exception e) {
        JSONObject json = new JSONObject();
        json.put("cause", e.getCause());
        json.put("error", e.getMessage());
        json.put("exception", e.getClass());
        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
