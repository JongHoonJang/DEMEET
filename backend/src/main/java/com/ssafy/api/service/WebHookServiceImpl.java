package com.ssafy.api.service;

import com.ssafy.api.request.openvidu.OVSessionCreatedReq;
import com.ssafy.api.request.openvidu.OVSessionDestroyedReq;
import com.ssafy.common.util.TypeConverter;
import com.ssafy.db.entity.Conferences;
import com.ssafy.db.repository.ConferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Service("WebhookService")

public class WebHookServiceImpl implements WebhookService {

    @Autowired
    ConferencesRepository conferencesRepository;

    @Autowired
    TypeConverter typeConverter;

    @Override
    public Conferences makeConferenceWithOvSessionCreatedReq(OVSessionCreatedReq ovSessionCreatedReq) {
        log.info("WebhookService.makeConferenceWithOvSessionCreatedReq 시작");
        // 세션 정보를 받아서 컨퍼런스 테이블에 저장한다.
        log.info("confernece 생성");
        Conferences conference = new Conferences();
        // 세션의 이름
        conference.setSessionName(ovSessionCreatedReq.getSessionId());
        conference.setUniqueSessionName(ovSessionCreatedReq.getUniqueSessionId());
        // 세션 시작시간
        log.debug("timestamp : " + ovSessionCreatedReq.getSessionId());
        LocalDateTime localDateTime = typeConverter.LongToLocalDateTime(ovSessionCreatedReq.getTimestamp());
        log.debug(localDateTime.toString());
        conference.setConfStartTime(localDateTime);
        // 세션 활성화여부 체크
        conference.setActivation(true);
        log.info("conference DB에 저장");
        return conferencesRepository.save(conference);
    }

    @Override
    public Conferences editConferenceWithOvSessionDestroyed(OVSessionDestroyedReq ovSessionDestroyedReq) {
        log.info("WebhookService.editConferenceWithOvSessionDestroyed 시작");
        // 세션 정보를 받아서 컨퍼런스 테이블에 저장한다.
        log.info("confernece 가져오기");
        LocalDateTime startTime = typeConverter.LongToLocalDateTime(ovSessionDestroyedReq.getStartTime());
//        Conferences conference = conferencesRepository.findConferencesBySessionNameAndConfStartTime(ovSessionDestroyedReq.getSessionId(), startTime).orElseThrow(()->new IllegalStateException());
//        Conferences conference = conferencesRepository.findConferencesBySessionName(ovSessionDestroyedReq.getSessionId()).orElseThrow(()->new IllegalArgumentException());
        Conferences conference = conferencesRepository.findConferencesByUniqueSessionName(ovSessionDestroyedReq.getUniqueSessionId()).orElseThrow(() -> new IllegalStateException());
        System.out.println(conference.toString());


        if (conference == null) {
            log.debug("WebhookService.editConferenceWithOvSessionDestroyed: conference를 찾을수가없다.");
        }
        conference.setActivation(false);
        LocalDateTime endTime = typeConverter.LongToLocalDateTime(ovSessionDestroyedReq.getTimestamp());
        conference.setConfEndTime(endTime);
        return conferencesRepository.save(conference);
    }
}
