package com.ssafy.api.controller;

import com.ssafy.DTO.ProjectSimpleInfoDTO;
import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.AddDelUserInProjectPostReq;
import com.ssafy.api.request.ProjectPatchPostReq;
import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.api.response.ProjectInfoRes;
import com.ssafy.api.response.ProjectSimpleInfoRes;
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
//        Users user = usersService.getUsersByUserEmail(ssafyUsersDetails.getUsername());
//        projectsCreatePostReq.setOwner_id(user.getUid());
        projectsCreatePostReq.setOwner_id(ssafyUsersDetails.getUserUid());
        Long pid = null;
        try {
            pid = projectsService.createProject(projectsCreatePostReq);
        } catch (UidNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success to make project"));
    }

    @GetMapping("/{pid}")
    public ResponseEntity<BaseResponseBody> getProject(Authentication authentication,@PathVariable Long pid) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        System.out.println("ProjectsController.getProject");
        System.out.println("pid = " + pid);
        try {
            // 우선 프로젝트를 먼저 가지고온다.
            Projects project = projectsService.getProject(pid);
            System.out.println(project.toString());

            // 이후 pid로 userprojects테이블에서 해당 pid를 가진 유저들을 모조리 가지고온다.
            List<userSimpleInfoDTO> userList = usersProjectService.getUserListByPid(pid);

            // 유저 세션Id 또한 추가해준다.

            // 이제 이 모든걸 하나로 합쳐준다.
            System.out.println("하나로 합쳐준다~");
            return ResponseEntity.status(200).body(ProjectInfoRes.of(200, "success to find project details", project, project.getOwnerId(), userList));

        } catch (ProjectNullException e) {
            // pid로 프로젝트를 찾지 못한다면
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));

        } catch (UidNullException e) {
            // db가 꼬일경우만 발생할듯함
            // pid를 기반으로 userProject테이블에서 user를 찾을때 없으면 발생하는 오류
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        }
    }


    @GetMapping("/activate/joind")
    public ResponseEntity<BaseResponseBody> getJoindActivateProjects(Authentication authentication){
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        Long uid = ssafyUsersDetails.getUserUid();
        System.out.println(uid);
        try {
            List<ProjectSimpleInfoDTO> projectList = projectsService.getJoinedProjectList(uid);
            for (int i = 0; i < projectList.size(); i++) {
                List<userSimpleInfoDTO> userList = usersProjectService.getUserListByPid(projectList.get(i).getPid());
                projectList.get(i).setMember(userList);
            }
            return ResponseEntity.status(200).body(ProjectSimpleInfoRes.of(200, "success", projectList));
        } catch (ProjectNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        } catch (UidNullException e) {
            // db가 꼬일경우만 발생할듯함
            // pid를 기반으로 userProject테이블에서 user를 찾을때 없으면 발생하는 오류
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        }
    }

    @GetMapping("/activate")
    public ResponseEntity<BaseResponseBody> getActivateProjects(Authentication authentication) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        Long uid = ssafyUsersDetails.getUserUid();
        System.out.println(uid);
        try {
            List<ProjectSimpleInfoDTO> projectList = projectsService.getActivateProjectsList(uid);
            for (int i = 0; i < projectList.size(); i++) {
                List<userSimpleInfoDTO> userList = usersProjectService.getUserListByPid(projectList.get(i).getPid());
                projectList.get(i).setMember(userList);
            }
            return ResponseEntity.status(200).body(ProjectSimpleInfoRes.of(200, "success", projectList));
        } catch (ProjectNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        } catch (UidNullException e) {
            // db가 꼬일경우만 발생할듯함
            // pid를 기반으로 userProject테이블에서 user를 찾을때 없으면 발생하는 오류
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        }
    }

    @PatchMapping()
    public ResponseEntity<BaseResponseBody> patchProjectInfo(Authentication authentication, @RequestBody ProjectPatchPostReq projectPatchPostReq) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        Long uid = ssafyUsersDetails.getUserUid();
        try {
            Projects changedProject = projectsService.patchProjectInfo(projectPatchPostReq, uid);
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
        } catch (ProjectNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        } catch (NullPointerException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, "Either name or desc or deactivate must not be null."));
        }
    }

    @PostMapping("/user")
    public ResponseEntity<BaseResponseBody> addUserInProject(Authentication authentication, @RequestBody AddDelUserInProjectPostReq addDelUserInProjectPostReq) {
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        Long uid = ssafyUsersDetails.getUserUid();
        try {
            // 내가 해당 pid를 가지는 프로젝트의 오너일 경우에만 유저추가가 가능하기때문에 일단 그 여부부터 확인한다.
            Projects project = projectsService.getProject(addDelUserInProjectPostReq.getPid());
            // 같지않다면
            if (!project.getOwnerId().equals(uid))
                return ResponseEntity.status(422).body(BaseResponseBody.of(422, "You do not have permission."));
            // 해당하는 uid를 가지는 유저가 Users에 있는지 체크
            Users user = usersService.getUsersByUid(addDelUserInProjectPostReq.getUid());
            // 해당하는 uid를 가지는 유저가 프로젝트에 이미 추가되어있는지 확인
            // true면 중복됨, false면 중복 없음
            if (usersProjectService.userDuplicateCheck(project, user)) {
                return ResponseEntity.status(200).body(BaseResponseBody.of(422, "member duplicate"));
            }
            // 두 조건 모두 완료하면 실제 추가직업 진행
            usersProjectService.addUserInProject(addDelUserInProjectPostReq, project, user);
            return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));

        } catch (ProjectNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        } catch (UidNullException e) {
            return ResponseEntity.status(422).body(BaseResponseBody.of(422, e.getMessage()));
        }
    }
    @DeleteMapping("/user")
    public ResponseEntity<BaseResponseBody> deleteUserInProject(Authentication authentication, @RequestBody AddDelUserInProjectPostReq addDelUserInProjectPostReq){
        SsafyUsersDetails ssafyUsersDetails = (SsafyUsersDetails) authentication.getDetails();
        Long uid = ssafyUsersDetails.getUserUid();
        try {
            Projects project = projectsService.getProject(addDelUserInProjectPostReq.getPid());
            Users user = usersService.getUsersByUid(addDelUserInProjectPostReq.getUid());
            usersProjectService.deleteUserInProject(project, user);
        } catch (ProjectNullException e) {
            throw new RuntimeException(e);
        } catch (UidNullException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }
}
