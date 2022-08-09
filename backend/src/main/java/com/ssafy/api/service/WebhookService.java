package com.ssafy.api.service;

import com.ssafy.api.request.openvidu.OVSessionCreatedReq;
import com.ssafy.api.request.openvidu.OVSessionDestroyedReq;
import com.ssafy.db.entity.Conferences;

public interface WebhookService {


    Conferences makeConferenceWithOvSessionCreatedReq(OVSessionCreatedReq ovSessionCreatedReq);

    Conferences editConferenceWithOvSessionDestroyed(OVSessionDestroyedReq ovSessionDestroyedReq);
}
