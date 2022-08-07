package com.ssafy.api.request.openvidu;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Data
public class SessionCreatedPostReq {

    // 미완성
    Map<String, ArrayList<Map<String, String>>> sessionCreated = new HashMap<String, ArrayList<Map<String, String>>>();
}
