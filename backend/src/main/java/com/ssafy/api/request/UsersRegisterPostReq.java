package com.ssafy.api.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 유저스 회원가입 api ([POST] /users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UsersRegisterPostReq")
public class UsersRegisterPostReq {
    @ApiModelProperty(name="유저 email", example = "ssafy@ssafy.com")
    String email;
    @ApiModelProperty(name="유저 password", example = "your_password")
    String password;
    @ApiModelProperty(name="유저 nickname", example = "ssafy")
    String nickname;
}