package com.ssafy.api.service;

import com.ssafy.api.request.openvidu.OVSessionCreatedReq;
import com.ssafy.common.util.TypeConverter;
import com.ssafy.db.entity.Conferences;
import com.ssafy.db.repository.ConferencesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;

@Service("WebhookService")

public class WebHookServiceImpl implements WebhookService{

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
        // 세션 시작시간
        log.debug("timestamp : "+ovSessionCreatedReq.getSessionId());
        LocalDateTime localDateTime = typeConverter.LongToLocalDateTime(ovSessionCreatedReq.getTimestamp());
        log.debug(localDateTime.toString());
        conference.setConfStartTime(localDateTime);
        // 세션 활성화여부 체크
        conference.setActivation(true);
        log.info("conference DB에 저장");
        return conferencesRepository.save(conference);
    }
}
