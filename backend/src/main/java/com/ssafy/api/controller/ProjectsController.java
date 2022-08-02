package com.ssafy.api.controller;

import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.api.response.ProjectInfoRes;
import com.ssafy.api.service.ProjectsService;
import com.ssafy.api.service.UserProjectService;
import com.ssafy.api.service.UsersService;
import com.ssafy.common.auth.SsafyUsersDetails;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * Demmet에 사용되는 프로젝트 관리 컨트롤러
 * Projects테이블 등을 사용한다.
 */
@RestController
@RequestMapping("/projects")
public class ProjectsController {
    @Autowired
    UsersService usersService;

    @Autowired
    ProjectsService projectsService;

    @Autowired
    UserProjectService usersProjectService;

    @PostMapping()
    public ResponseEntity<BaseResponseBody> createProject(@ApiIgnore Authentication authentication, @RequestBody ProjectsCreatePostReq projectsCreatePostReq) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        //프로젝트 생성에 필요한 정보들은 다음과같다.
        //owner_id(토큰을 통해 얻어낸다.)
        Users user = usersService.getUsersByUserEmail(ssafyUsersDetails.getUsername());
        projectsCreatePostReq.setOwner_id(user.getUid());
        Long pid = null;
        try {
            pid = projectsService.createProject(projectsCreatePostReq);
        } catch (UidNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success to make project"));
    }

    @GetMapping("/{pid}")
    public ResponseEntity<BaseResponseBody> getProject(@PathVariable Long pid) {
//        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        System.out.println("ProjectsController.getProject");
        System.out.println("pid = " + pid);
        try {
            // 우선 프로젝트를 먼저 가지고온다.
            Projects project = projectsService.getProject(pid);
            System.out.println(project.toString());

            // 이후 pid로 userprojects테이블에서 해당 pid를 가진 유저들을 모조리 가지고온다.
            List<userSimpleInfoDTO> userList = usersProjectService.getUserListByPid(pid);

            // owner_id에 넣어줄 userSimpleInfoDTO 또한 만들어준다.
            userSimpleInfoDTO projectOwner = usersService.getUsersByUid(project.getOwnerId());

            // 이제 이 모든걸 하나로 합쳐준다.
            System.out.println("하나로 합쳐준다~");
            return ResponseEntity.status(200).body(ProjectInfoRes.of(200, "success to find project details", project, projectOwner, userList));

        } catch (ProjectNullException e) {
            // pid로 프로젝트를 찾지 못한다면
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));

        } catch (UidNullException e) {
            // db가 꼬일경우만 발생할듯함
            // pid를 기반으로 userProject테이블에서 user를 찾을때 없으면 발생하는 오류
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        }


    }

}
