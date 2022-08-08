package com.ssafy.api.controller;


/**
 * Demeet에 사용되는 회원관리 컨트롤러
 * Users테이블을 사용한다.
 */

import com.ssafy.DTO.ProjectSimpleInfoDTO;
import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.ImageUploadReq;
import com.ssafy.api.request.UserPwChangePostReq;
import com.ssafy.api.request.UsersLoginPostReq;
import com.ssafy.api.request.UsersRegisterPostReq;
import com.ssafy.api.response.UserListRes;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UsersMyInfoRes;
import com.ssafy.api.response.UsersRes;
import com.ssafy.api.service.AwsS3Service;
import com.ssafy.api.service.ProjectsService;
import com.ssafy.api.service.UserProjectService;
import com.ssafy.api.service.UsersService;
import com.ssafy.common.auth.SsafyUsersDetails;
import com.ssafy.common.customException.NotImageException;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.common.util.JwtTokenUtil;
import com.ssafy.db.entity.Users;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

@Api(value = "유저 API", tags = {"Users"})
@RestController
@Validated
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    ProjectsService projectsService;
    @Autowired
    UserProjectService userProjectService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AwsS3Service awsS3Service;

    @PostMapping()
    @ApiOperation(value = "회원가입", notes = "<Strong>이메일과 패스워드</Strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "sign-in success")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value = "회원가입 정보", required = true) @Validated UsersRegisterPostReq registerInfo) {
        Field[] fields = UsersRegisterPostReq.class.getFields();
        System.out.println("registerInfo = " + registerInfo);
        for (Field field : fields) {
            System.out.println(Modifier.isPublic(field.getModifiers()));
        }
        Users newUser = usersService.createUsers(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "sign-in success"));
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 404, message = "사용자 없음", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<UserLoginPostRes> login(@RequestBody @ApiParam(value = "로그인 정보", required = true) @Validated UsersLoginPostReq loginInfo) {
        String userEmail = loginInfo.getEmail();
        String userPassword = loginInfo.getPassword();
        Users newUser = usersService.getUsersByUserEmail(userEmail);

//        // 로그인 요청한 유저로부터 입력된 패스워드 와 디비에 저장된 유저의 암호화된 패스워드가 같은지 확인.(유효한 패스워드인지 여부 확인)
        if (passwordEncoder.matches(userPassword, newUser.getPassword())) {
            // 유효한 패스워드가 맞는 경우, 로그인 성공으로 응답.(액세스 토큰을 포함하여 응답값 전달)
            return ResponseEntity.ok(UserLoginPostRes.of(200, "Success", JwtTokenUtil.getToken(userEmail)));
        }
//        // 유효하지 않는 패스워드인 경우, 로그인 실패로 응답.
        return ResponseEntity.status(401).body(UserLoginPostRes.of(401, "Invalid Password", null));
    }

    @GetMapping("/me")
    public ResponseEntity<BaseResponseBody> getMyInfo(Authentication authentication) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        // 내가 속한 프로젝트들중에서 끝난 프로젝트들을 가지고온다.
        try {
            String email = ssafyUsersDetails.getUsername();
            Users newUser = usersService.getUsersByUserEmail(email);
            List<ProjectSimpleInfoDTO> deActivateProjects = projectsService.getDeActivateProjectsByUid(newUser.getUid());
            for (int i = 0; i < deActivateProjects.size(); i++) {
                List<userSimpleInfoDTO> userList = userProjectService.getUserListByPid(deActivateProjects.get(i).getPid());
                deActivateProjects.get(i).setMember(userList);
            }
            return ResponseEntity.status(200).body(UsersMyInfoRes.of(200, "Success", newUser, deActivateProjects));
        } catch (ProjectNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, "not found"));
        } catch (UidNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, "fail to find user"));
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<BaseResponseBody> checkEmailDuplication(@PathVariable("email") @Email @NotBlank String email) {
        boolean check = usersService.checkEmailDuplicate(email);
        // check가 false일경우는 이메일 중복이 없음
        if (!check) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "email checked, no duplicated email"));
        }
        // check가 true일경우는 이메일 중복이 있음
        else {
            return ResponseEntity.status(401).body(BaseResponseBody.of(401, "email checked, duplicated email, please try another email"));
        }
    }

    @GetMapping()
    public ResponseEntity<UserListRes> getUserList(@ApiIgnore Authentication authentication) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        String email = ssafyUsersDetails.getUsername();
        List<userSimpleInfoDTO> userList = usersService.getUserList();
        return ResponseEntity.status(200).body(UserListRes.of(200, "user list lookup success", userList));
    }

    @PatchMapping("/password")
    public ResponseEntity<BaseResponseBody> changeUserPassword(@ApiIgnore Authentication authentication, @RequestBody @Validated UserPwChangePostReq userPwChangePostReq) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        // 토큰을 통해 email을 포함하고 있는 User를 받아온다.
        Users user = usersService.getUsersByUserEmail(ssafyUsersDetails.getUsername());
        // 그 후 passwordEncoder.match를 통해 user의 비밀번호와 비교하여 맞는지 틀리는지 출력해준다
        boolean check = passwordEncoder.matches(userPwChangePostReq.getCurrPassword(), user.getPassword());
        System.out.println(check);
        // 만약 틀리다면 406 에러를 띄운다.
        if (!check) {
            return ResponseEntity.status(406).body(BaseResponseBody.of(406, "invalid currPassword"));
        }
        // 같다면 새로운 비밀번호를 새로이 갱신시켜준다.
        Boolean changeCheck = usersService.changeUserPassword(user.getUid(), userPwChangePostReq.getNewPassword());
        if (changeCheck) {
            // 비밀번호 변경 성공
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success to change user password"));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(500, "server error"));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<BaseResponseBody> changeUserNickname(@ApiIgnore Authentication authentication, @RequestBody Map<String, String> nicknameMap) {
        String newNickname = nicknameMap.get("nickname");
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        Users user = usersService.getUsersByUserEmail(ssafyUsersDetails.getUsername());
        // 이제 현재 nickname과 newNickname이 같은지 비교한 후 같다면 바꿀필요가 없다고 데이터 전송
        if (user.getNickname().equals(newNickname)) {
            return ResponseEntity.status(426).body(BaseResponseBody.of(426, "new nickname matches current nickname. Please change your new nickname."));
        }
        // 닉네임이 다르기 때문에 변경 작업 시작
        Boolean changeCheck = usersService.changeUserNickname(user.getUid(), newNickname);
        if (changeCheck) {
            // 닉네임 변경 성공
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success to change user's nickname"));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(500, "server error"));
    }

    @DeleteMapping()
    public ResponseEntity<BaseResponseBody> deleteUser(Authentication authentication) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        System.out.println(ssafyUsersDetails.getUsername());
        boolean deleteCheck = usersService.deleteUser(ssafyUsersDetails.getUsername());
        if (deleteCheck) {
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success to delete user"));
        } else {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, "fail to find user"));

        }

    }

    @PostMapping("/profile")
    public ResponseEntity<BaseResponseBody> uploadProfileImage(@ApiIgnore Authentication authentication, @ModelAttribute ImageUploadReq imageUploadReq){
        SsafyUsersDetails usersDetails = (SsafyUsersDetails) authentication.getDetails();
//        long uid = usersService.getUsersByUserEmail(usersDetails.getUsername()).getUid();
        long uid = usersDetails.getUserUid();
        String path = null;
        // 이미지 업로드
        try {
            // 파일, id, 프로필/드로잉 구분
            path = awsS3Service.putImage(imageUploadReq.getMultipartFile(), uid, "profile");
            // 성공시 db에 정보 넣기
            System.out.println(path);
            Users users = awsS3Service.saveImagePath(path, uid, "profile");
            users.toString();
        }catch (IOException e){
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        }catch (NotImageException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BaseResponseBody.of(HttpStatus.NOT_ACCEPTABLE.value(), "the File is NOT image"));
        };
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, path));
    }

    @DeleteMapping("/profile")
    public ResponseEntity<BaseResponseBody> deleteProfileImage(@ApiIgnore Authentication authentication) {
        SsafyUsersDetails usersDetails = (SsafyUsersDetails) authentication.getDetails();
        long uid = usersDetails.getUserUid();
        try{
            // 이미지 삭제
            awsS3Service.DeleteImage(uid, "profile");
            // DB에서 삭제
            // uid를 기반으로 pipid를 찾는다 -> 1:1 대응이라 가능
            awsS3Service.saveImagePath(null, uid, "profile");
        }catch (NotImageException e1){
            e1.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BaseResponseBody.of(HttpStatus.NOT_ACCEPTABLE.value(), "the File is NOT image"));
        }


        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Profile image Delete Success"));
    }
}