package com.ssafy.api.request.openvidu;

import lombok.Data;

@Data
public class OVBasePostReq {
    String event;
    String sessionId;
    String uniqueSessionId;
    Long timestamp;

}
