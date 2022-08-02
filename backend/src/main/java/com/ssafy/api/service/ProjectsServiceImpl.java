package com.ssafy.api.service;

import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("projectsService")
public class ProjectsServiceImpl implements ProjectsService {
    @Autowired
    ProjectsRepository projectRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    UsersRepositorySupport usersRepositorySupport;

    @Autowired
    ProjectsRepositorySupport projectRepositorySupport;

    @Override
    @Transactional
    public Integer createProject(ProjectsCreatePostReq projectsCreatePostReq) throws UidNullException {
//        1. 프로젝트를 먼저 생성해준다.
        Projects project = new Projects();
//        (컨트롤러에서 ownerId확인과정을 거치므로 다시 확인해줄 필요는 없음.
//        2. 회원들의 명단을 넣기전에 다 있는지 확인함.
        List<Integer> memberList = projectsCreatePostReq.getMemberList();
        List<Users> userList = new ArrayList<Users>();
        for (Integer member : memberList) {
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
//        3. 유저가 다 있는걸 확인했으니 이제 프로젝트를 생성해준다.
        LocalDateTime localDateTime;
//        // 프로젝트 이름지정
        project.setPjtName(projectsCreatePostReq.getPjt_name());
//        // 프로젝트 설명 지정
        if (projectsCreatePostReq.getPjt_desc() != null) {
            project.setPjtDesc(projectsCreatePostReq.getPjt_desc());
        }
//        // 프로젝트 오너 지정
        project.setOwnerId(projectsCreatePostReq.getOwner_id());
//        // 프로젝트 활성화
        project.setActivation(true);
//        // 프로젝트 시작 날짜 설정
        localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        project.setPjtStartDate(localDateTime);
//        // 프로젝트 토탈시간 초기화
        localDateTime = LocalDateTime.of(0, 1, 1, 0, 0, 0, 0);
        System.out.println("ProjectsServiceImpl.createProject");
        System.out.println("localdateTime = " + localDateTime);
        project.setTotalMeetTime(localDateTime);
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
    public Projects getProject(int pid) throws ProjectNullException {
        Optional<Projects> optProject = projectRepositorySupport.getProject(pid);
        // 위 결과가 null이면 오류발생, null이 아니면 정상값 반환
        Projects project = optProject.orElseThrow(() -> new ProjectNullException("Project " + pid + " does not exist"));
        return project;
    }
}

