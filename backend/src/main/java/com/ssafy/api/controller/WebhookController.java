package com.ssafy.api.controller;

import com.ssafy.common.model.response.BaseResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/openvidu_webhook")
public class WebhookController{

    @PostMapping()
    public ResponseEntity<BaseResponseBody> getEvents(@RequestBody Map<String, String> events){
        System.out.println("++++++++++++++++++++웹훅발생+++++++++++++++++");
        System.out.println(events.toString());
        // 어떤경우에도 웹훅은 200을 리턴해줘야한다. 그렇지않으면 오픈비두서버가 다음작업을 완료하지않는다고한다.
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }


}