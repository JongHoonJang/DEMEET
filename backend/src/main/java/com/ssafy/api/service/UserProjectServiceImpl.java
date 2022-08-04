package com.ssafy.api.service;

import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.AddDelUserInProjectPostReq;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.UserProjectRepository;
import com.ssafy.db.repository.UserProjectRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("UserProjectService")
public class UserProjectServiceImpl implements UserProjectService{

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    UserProjectRepositorySupport userProjectRepositorySupport;

    @Override
    public List<userSimpleInfoDTO> getUserListByPid(Long pid) throws UidNullException {
        Optional<List<userSimpleInfoDTO>> optUserList = userProjectRepositorySupport.getUserListByPid(pid);
        List<userSimpleInfoDTO> userList = optUserList.orElseThrow(() -> new UidNullException("cannot get user list by pid " + pid));
        for(userSimpleInfoDTO user : userList) {
            System.out.println(user.toString());
        }
        return userList;
    }

    @Override
    public UserProject addUserInProject(AddDelUserInProjectPostReq addDelUserInProjectPostReq, Projects project, Users user) {
        UserProject userProject = new UserProject();
        userProject.setUsers(user);
        userProject.setProjects(project);
        return userProjectRepository.save(userProject);
    }

    @Override
    public boolean userDuplicateCheck(Projects project, Users user) {
        // 프로젝트에 유저가 있는지 확인하는 메소드
//        true면 중복됨, false면 중복 없음
        UserProject userProject =userProjectRepository.getUserProjectByProjectsAndUsers(project, user);
        return userProject != null ? true:false;
    }

    @Override
    public void deleteUserInProject(Projects project, Users user) {
        UserProject userProject = userProjectRepository.getUserProjectByProjectsAndUsers(project, user);
        System.out.println(userProject.toString());
        userProjectRepository.delete(userProject);
    }
}
