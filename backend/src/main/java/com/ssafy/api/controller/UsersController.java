package com.ssafy.api.controller;


/**
 * Demeet에 사용되는 회원관리 컨트롤러
 * Users테이블을 사용한다.
 */

import com.ssafy.api.request.UsersRegisterPostReq;
import com.ssafy.api.service.UsersService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Users;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "유저 API", tags = {"Users"})
@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    UsersService usersService;


    @PostMapping()
    @ApiOperation(value = "회원가입", notes = "<Strong>이메일과 패스워드</Strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "sign-in success")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value = "회원가입 정보", required = true) UsersRegisterPostReq registerInfo){

        Users newUser = usersService.createUsers(registerInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}