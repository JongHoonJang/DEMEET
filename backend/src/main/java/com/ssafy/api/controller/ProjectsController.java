package com.ssafy.api.controller;

import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.api.service.UsersService;
import com.ssafy.common.auth.SsafyUsersDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Demmet에 사용되는 프로젝트 관리 컨트롤러
 * Projects테이블 등을 사용한다.
 */
@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Autowired
    UsersService usersService;

    @PostMapping()
    public ResponseEntity<BaseResponseBody> createProject(@ApiIgnore Authentication authentication, @RequestBody ProjectsCreatePostReq projectsCreatePostReq){
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        //프로젝트 생성에 필요한 정보들은 다음과같다.
        //owner_id(토큰을 통해 얻어낸다.)
        Users user = usersService.getUsersByUserEmail(ssafyUsersDetails.getUsername());
        projectsCreatePostReq.setOwner_id(user.getUid());
        //pjt_desc(선택)
        //pjt_name(필수)
        //pjt_start_date(직접 생성해준다.)
        //total_meet_time(일단 0로 둔다.)

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, projectsCreatePostReq.toString()));
//        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "email checked, no duplicated email"));

    }
}
