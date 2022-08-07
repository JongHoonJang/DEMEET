package com.ssafy.api.service;

import com.ssafy.DTO.ProjectSimpleInfoDTO;
import com.ssafy.api.request.ProjectPatchPostReq;
import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.common.customException.NoAuthorizedException;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service("projectsService")
public class ProjectsServiceImpl implements ProjectsService {
    @Autowired
    ProjectsRepository projectRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    UserProjectRepositorySupport userProjectRepositorySupport;
    @Autowired
    UsersRepositorySupport usersRepositorySupport;

    @Autowired
    ProjectsRepositorySupport projectRepositorySupport;


    public ProjectSimpleInfoDTO makeProjectSimpleInfoDTO(Projects project) {
        ProjectSimpleInfoDTO simpleInfoDTO = new ProjectSimpleInfoDTO();
        simpleInfoDTO.setPid(project.getPid());
        simpleInfoDTO.setProjectOwner(project.getOwnerId());
        simpleInfoDTO.setPjtName(project.getPjtName());
        simpleInfoDTO.setPjtDesc(project.getPjtDesc());
        simpleInfoDTO.setActivation(project.isActivation());
        return simpleInfoDTO;
    }


    @Override
    @Transactional
    public Long createProject(ProjectsCreatePostReq projectsCreatePostReq) throws UidNullException {
//        1. 프로젝트를 먼저 생성해준다.
        Projects project = new Projects();
//        (컨트롤러에서 ownerId확인과정을 거치므로 다시 확인해줄 필요는 없음.
//        2. 회원들의 명단을 넣기전에 다 있는지 확인함.
        List<Long> memberList = new ArrayList<Long>();
        memberList.add(projectsCreatePostReq.getOwner_id());
        memberList.addAll(projectsCreatePostReq.getMemberList());
        List<Users> userList = new ArrayList<Users>();
        for (Long member : memberList) {
            /*
            Oprional.empty로 반환된 타입의 경우 isPresent로 null인지 아닌지 확인가능함.
            !isPresent를 해줌으로써 null인지 체크 후 Exception반환해줌.
            하지만 우리는 아래와같이 try catch를 사용하도록함
            optional로 던져주는값의 형태가 너무상이해 이렇게 처리함.
             */
            try {
                Users user = usersRepositorySupport.findUserById(member).get();
                userList.add(user);
            } catch (NoSuchElementException e) {
                throw new UidNullException("can not find user that uid is " + member);
            }
        }
        // 3. 유저가 다 있는걸 확인했으니 이제 프로젝트를 생성해준다.
        LocalDateTime localDateTime;
        // 프로젝트 이름지정
        project.setPjtName(projectsCreatePostReq.getPjt_name());
        // 프로젝트 설명 지정
        if (projectsCreatePostReq.getPjt_desc() != null) {
            project.setPjtDesc(projectsCreatePostReq.getPjt_desc());
        }
        // 프로젝트 오너 지정
        project.setOwnerId(projectsCreatePostReq.getOwner_id());
        // 프로젝트 활성화
        project.setActivation(true);
        // 프로젝트 시작 날짜 설정
        localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        project.setPjtStartDate(localDateTime);
        // 프로젝트 토탈시간 초기화
        localDateTime = LocalDateTime.of(0, 1, 1, 0, 0, 0, 0);
        System.out.println("ProjectsServiceImpl.createProject");
        System.out.println("localdateTime = " + localDateTime);
        project.setTotalMeetTime(localDateTime);
        // 프로젝트의 세션ID 지정
        String baseString = project.getPjtName() + project.getPjtStartDate().toString();
        String sessionId = Base64.getEncoder().encodeToString(baseString.getBytes());
        project.setSessionId(sessionId);
        // 프로젝트 저장
        Projects savedProject = projectRepository.save(project);
        System.out.println("프로젝트 저장 완료");
        System.out.println(savedProject.toString());
//        4. userProject에 프로젝트들 저장해줌.
        int userProjectCnt = 0;
        for (Users user : userList) {
            UserProject userProject = new UserProject();
            userProject.setUsers(user);
            userProject.setProjects(savedProject);
            //위처럼 저장 다 완료했으면 이제 얘도 저장해준다.
            userProjectRepository.save(userProject);
            userProjectCnt++;
        }
        return savedProject.getPid();
    }

    @Override
    public Projects getProject(Long pid) throws ProjectNullException {
        Optional<Projects> optProject = projectRepositorySupport.getProject(pid);
        // 위 결과가 null이면 오류발생, null이 아니면 정상값 반환
        Projects project = optProject.orElseThrow(() -> new ProjectNullException("Project " + pid + " does not exist"));
        return project;
    }

    @Override
    public List<ProjectSimpleInfoDTO> getActivateProjectsList(Long uid) throws ProjectNullException {
        List<Projects> activateProjectsList = projectRepository.findProjectsByOwnerIdAndActivation(uid, true).orElseThrow(() -> new ProjectNullException("not found"));
        System.out.println("ProjectsServiceImpl.getActivateProjectsList");
        System.out.println("리스트 사이즈 = " + activateProjectsList.size());
        List<ProjectSimpleInfoDTO> projectSimpleInfoList = new ArrayList<ProjectSimpleInfoDTO>();
        for (Projects project : activateProjectsList) {
            System.out.println(project.toString());
            // 리스트속 객체(Projects)들을들을 우리가 원하는 객체(ProjectSimpleInfoDTO)로 바꿔준다.
            projectSimpleInfoList.add(makeProjectSimpleInfoDTO(project));
        }
        System.out.println("새로운 프로젝트 사이즈 = " + projectSimpleInfoList.size());

        return projectSimpleInfoList;
    }

    @Override
    public List<ProjectSimpleInfoDTO> getJoinedProjectList(Long uid) throws ProjectNullException {
        List<Projects> activateProjectsList = userProjectRepositorySupport.getJoinedProjectList(uid).orElseThrow(() -> new ProjectNullException("Projets not found"));
        List<ProjectSimpleInfoDTO> projectSimpleInfoList = new ArrayList<ProjectSimpleInfoDTO>();
        for (Projects project : activateProjectsList) {
            System.out.println(project.toString());
            // 리스트속 객체(Projects)들을들을 우리가 원하는 객체(ProjectSimpleInfoDTO)로 바꿔준다.
            projectSimpleInfoList.add(makeProjectSimpleInfoDTO(project));
        }
        return projectSimpleInfoList;
    }

    @Override
    public List<ProjectSimpleInfoDTO> getDeActivateProjectsByUid(Long uid) throws ProjectNullException {
        List<Projects> deActivateProjects = userProjectRepositorySupport.getDeactivateProjectsByUid(uid).orElseThrow(() -> new ProjectNullException("Projets not found"));
        List<ProjectSimpleInfoDTO> projectSimpleInfoList = new ArrayList<ProjectSimpleInfoDTO>();
        for (Projects project : deActivateProjects) {
            System.out.println(project.toString());
            // 리스트속 객체(Projects)들을들을 우리가 원하는 객체(ProjectSimpleInfoDTO)로 바꿔준다.
            projectSimpleInfoList.add(makeProjectSimpleInfoDTO(project));
        }
        return projectSimpleInfoList;
    }

    @Override
    public Projects deactivateProject(int pid, Long userUid) throws ProjectNullException, NoAuthorizedException {
        Projects project = projectRepository.findProjectsByPid(Long.valueOf(pid)).orElseThrow(() -> new ProjectNullException("ProjectNullException"));
        if (project.getOwnerId() != userUid) {
            // 본 uid의 사용자가 해당 프로젝트의 오너가 아니기때문에 비활성화 못함
            throw new NoAuthorizedException("this user is not allowed to deactivate " + pid + " project");
        }
        project.setActivation(false);
        projectRepository.save(project);
        return projectRepository.save(project);
    }

    @Override
    public Projects patchProjectInfo(ProjectPatchPostReq projectPatchPostReq, Long uid) throws ProjectNullException, NullPointerException {
//        0. 프로젝트를 가져와 저장한다.
//        조건 : 해당하는 pid의 프로젝트가 있고, 내가 그 프로젝트의 오너이고, 프로젝트는 활성화되어있어야한다.
        Projects currProject = projectRepository.findProjectsByPidAndOwnerIdAndActivationIsTrue(projectPatchPostReq.getPid(), uid).orElseThrow(() -> new ProjectNullException("Your request does not meet the criteria."));
        System.out.println(currProject.toString());
//         변경할 정보(name, desc, decativate)모두 null일경우 똑같이 오류를 띄워준다.
        if (projectPatchPostReq.getName() == null & projectPatchPostReq.getDesc() == null & !projectPatchPostReq.getDeactivate().orElse(false)) {
            System.out.println(projectPatchPostReq.getDeactivate().orElse(true));
            throw new NullPointerException();
        }

        // 위 모든 조건들을 통과하였을경우 이제 변환작업을 시작한다.
        if (projectPatchPostReq.getName() != null) {
            currProject.setPjtName(projectPatchPostReq.getName());
        }
        if (projectPatchPostReq.getDesc() != null) {
            currProject.setPjtDesc(projectPatchPostReq.getDesc());
        }
        currProject.setActivation(!projectPatchPostReq.getDeactivate().orElse(false));
        System.out.println("프로젝트에 회원 추가");
        return projectRepository.save(currProject);
    }


}

