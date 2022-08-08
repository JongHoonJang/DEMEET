package com.ssafy.api.controller;

import com.ssafy.api.request.openvidu.*;
import com.ssafy.api.service.WebhookService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Conferences;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@Slf4j
@RequestMapping("/openvidu_webhook")
public class WebhookController {

    @Autowired
    WebhookService webhookService;

    @PostMapping()
    public ResponseEntity<BaseResponseBody> getEvents(@RequestBody OVAllInOneReq req) {

        log.info("웹훅발생");
        log.info(req.toString());

        String event = req.getEvent();
        Conferences conference = null;
        switch (event) {
            case "sessionCreated":
                OVSessionCreatedReq ovSessionCreatedReq = makeSessionCreatedReq(req);
                conference = webhookService.makeConferenceWithOvSessionCreatedReq(ovSessionCreatedReq);
                break;
            case "sessionDestroyed":
                OVSessionDestroyedReq ovSessionDestroyedReq = makeSessionDestroyReq(req);
                conference = webhookService.editConferenceWithOvSessionDestroyed(ovSessionDestroyedReq);
                break;
            case "participantJoined":
                OVParticipantJoinedReq ovParticipantJoinedReq = makeParticipantJoinedReq(req);
                break;
            case "participantLeft":
                OVParticipantLeftReq ovParticipantLeftReq = makeParticipantLeftReq(req);
                break;
            default:
                return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
        }
        // 어떤경우에도 웹훅은 200을 리턴해줘야한다. 그렇지않으면 오픈비두서버가 다음작업을 완료하지않는다고한다.
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

    public OVSessionCreatedReq makeSessionCreatedReq(OVAllInOneReq ovAllInOneReq) {
        OVSessionCreatedReq ovSessionCreatedReq = new OVSessionCreatedReq();

        ovSessionCreatedReq.setSessionId(ovAllInOneReq.getSessionId());
        ovSessionCreatedReq.setUniqueSessionId((ovAllInOneReq.getUniqueSessionId()));
        ovSessionCreatedReq.setEvent(ovAllInOneReq.getEvent());
        ovSessionCreatedReq.setTimestamp(ovAllInOneReq.getTimestamp());

        return ovSessionCreatedReq;
    }

    public OVSessionDestroyedReq makeSessionDestroyReq(OVAllInOneReq ovAllInOneReq) {
        OVSessionDestroyedReq ovSessionDestroyReq = new OVSessionDestroyedReq();

        ovSessionDestroyReq.setSessionId(ovAllInOneReq.getSessionId());
        ovSessionDestroyReq.setUniqueSessionId(ovAllInOneReq.getUniqueSessionId());
        ovSessionDestroyReq.setEvent(ovAllInOneReq.getEvent());
        ovSessionDestroyReq.setTimestamp(ovAllInOneReq.getTimestamp() + 32400000);
        ovSessionDestroyReq.setStartTime(ovAllInOneReq.getStartTime() + 32400000);
        ovSessionDestroyReq.setDuration(ovAllInOneReq.getDuration());
        ovSessionDestroyReq.setReason(ovAllInOneReq.getReason());

        return ovSessionDestroyReq;
    }

    public OVParticipantJoinedReq makeParticipantJoinedReq(OVAllInOneReq ovAllInOneReq) {
        OVParticipantJoinedReq ovParticipantJoinedReq = new OVParticipantJoinedReq();

        ovParticipantJoinedReq.setSessionId(ovAllInOneReq.getSessionId());
        ovParticipantJoinedReq.setUniqueSessionId(ovAllInOneReq.getUniqueSessionId());
        ovParticipantJoinedReq.setTimestamp(ovAllInOneReq.getTimestamp() + 32400000);
        ovParticipantJoinedReq.setParticipantId(ovAllInOneReq.getParticipantId());
        ovParticipantJoinedReq.setConnectionId(ovAllInOneReq.getConnectionId());
        ovParticipantJoinedReq.setClientData(ovAllInOneReq.getClientData());
        ovParticipantJoinedReq.setServerData(ovAllInOneReq.getServerData());
        ovParticipantJoinedReq.setLocation(ovAllInOneReq.getLocation());
        ovParticipantJoinedReq.setIp(ovAllInOneReq.getIp());
        ovParticipantJoinedReq.setPlatform(ovAllInOneReq.getPlatform());
        ovParticipantJoinedReq.setEvent(ovAllInOneReq.getEvent());

        return ovParticipantJoinedReq;
    }

    public OVParticipantLeftReq makeParticipantLeftReq(OVAllInOneReq ovAllInOneReq) {
        OVParticipantLeftReq ovParticipantLeftReq = new OVParticipantLeftReq();

        ovParticipantLeftReq.setSessionId(ovAllInOneReq.getSessionId());
        ovParticipantLeftReq.setUniqueSessionId(ovAllInOneReq.getUniqueSessionId());
        ovParticipantLeftReq.setTimestamp(ovAllInOneReq.getTimestamp() + 32400000);
        ovParticipantLeftReq.setParticipantId(ovAllInOneReq.getParticipantId());
        ovParticipantLeftReq.setConnectionId(ovAllInOneReq.getConnectionId());
        ovParticipantLeftReq.setClientData(ovAllInOneReq.getClientData());
        ovParticipantLeftReq.setServerData(ovAllInOneReq.getServerData());
        ovParticipantLeftReq.setLocation(ovAllInOneReq.getLocation());
        ovParticipantLeftReq.setIp(ovAllInOneReq.getIp());
        ovParticipantLeftReq.setPlatform(ovAllInOneReq.getPlatform());
        ovParticipantLeftReq.setEvent(ovAllInOneReq.getEvent());
        ovParticipantLeftReq.setReason(ovAllInOneReq.getReason());
        ovParticipantLeftReq.setDuration(ovAllInOneReq.getDuration());

        return ovParticipantLeftReq;
    }


}