package com.ssafy.api.service;

import com.ssafy.api.request.openvidu.OVSessionCreatedReq;
import com.ssafy.db.entity.Conferences;

public interface WebhookService {


    Conferences makeConferenceWithOvSessionCreatedReq(OVSessionCreatedReq ovSessionCreatedReq);
}
