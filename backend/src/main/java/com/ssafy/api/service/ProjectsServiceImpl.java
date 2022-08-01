package com.ssafy.api.service;

import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.ProjectsRepository;
import com.ssafy.db.repository.ProjectsRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service("projectsService")
public class ProjectsServiceImpl implements ProjectsService {
    @Autowired
    ProjectsRepository projectRepository;

    @Autowired
    ProjectsRepositorySupport projectRepositorySupport;

    @Override
    @Transactional
    public Integer createProject(ProjectsCreatePostReq projectsCreatePostReq) {
          Projects project = new Projects();


//        Projects newProject = new Projects();
//
//        LocalDateTime localDateTime;
//        // 프로젝트 이름지정
//        newProject.setPjtName(projectsCreatePostReq.getPjt_name());
//
//        // 프로젝트 설명 지정
//        if (projectsCreatePostReq.getPjt_desc() != null) {
//            newProject.setPjtDesc(projectsCreatePostReq.getPjt_desc());
//        }
//
//        // 프로젝트 오너 지정
//        newProject.setOwnerId(projectsCreatePostReq.getOwner_id());
//
//        // 프로젝트 활성화
//        newProject.setActivation(true);
//
//        // 프로젝트 시작 날짜 설정
//        localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
//        newProject.setPjtStartDate(localDateTime);
//
//        // 프로젝트 토탈시간 초기화
//        localDateTime = LocalDateTime.of(0, 1, 1, 0, 0, 0, 0);
//        System.out.println("ProjectsServiceImpl.createProject");
//        System.out.println("localdateTime = " + localDateTime);
//        newProject.setTotalMeetTime(localDateTime);
//
//        // projects에 프로젝트 넣어주기
//        Integer newProjectPid = projectRepositorySupport.createProject(newProject).get();
//        System.out.println("ProjectsServiceImpl.createProject.newProjectPid = " + newProjectPid);
//
//        // users_projects 테이블에 이용자들 넣어주기
//        List<Integer> memberList = projectsCreatePostReq.getMemberList();
//        for (Integer member : memberList) {
//            boolean memberinsertCheck = projectRepositorySupport.addMemberInProject(member, newProjectPid);
//            System.out.println("ProjectsServiceImpl.createProject.memberinsertCheck = " + memberinsertCheck);
//        }
//
//        return newProjectPid;
        return null;
    }
}
