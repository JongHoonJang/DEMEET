package com.ssafy.api.request;


import lombok.Getter;
import lombok.Setter;

/**
 * changeUserPassword의 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
public class UserPwChangePostReq {
    String currPassword;
    String newPassword;
}
