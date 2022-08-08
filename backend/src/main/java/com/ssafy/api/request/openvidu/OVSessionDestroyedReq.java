package com.ssafy.api.request.openvidu;

import lombok.Data;

@Data
public class OVSessionDestroyedReq extends OVSessionCreatedReq{
//{sessionId=ses_KEY10EWVwb,
// uniqueSessionId=ses_KEY10EWVwb_1659920022264,
// timestamp=1659920064493,
// startTime=1659920022264,
// duration=42,
// reason=lastParticipantLeft,
// event=sessionDestroyed}
    String uniqueSessionId;
    Long startTime;
    Long duration;
    String reason;
}
