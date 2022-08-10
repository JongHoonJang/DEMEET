package com.ssafy.api.service;

import com.ssafy.DTO.UserSimpleInfoWithPrifileDTO;
import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.AddDelUserInProjectPostReq;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.UserProjectRepository;
import com.ssafy.db.repository.UserProjectRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("UserProjectService")
public class UserProjectServiceImpl implements UserProjectService{

    @Autowired
    UserProjectRepository userProjectRepository;
    @Autowired
    UserProjectRepositorySupport userProjectRepositorySupport;

    @Override
    public List<userSimpleInfoDTO> getUserSimpleInfoDTOListByPid(Long pid) throws UidNullException {
        Optional<List<userSimpleInfoDTO>> optUserList = userProjectRepositorySupport.getUserSimpleInfoDTOListByPid(pid);
        List<userSimpleInfoDTO> userList = optUserList.orElseThrow(() -> new UidNullException("cannot get user list by pid " + pid));
        for(userSimpleInfoDTO user : userList) {
            System.out.println(user.toString());
        }
        return userList;
    }

    @Override
    public List<UserSimpleInfoWithPrifileDTO> getUserSimpleInfoWithPrifileDTOListByPid(Long pid) throws UidNullException {
        List<Users> optUserList = userProjectRepositorySupport.getUserListByPid(pid).orElseThrow(() -> new UidNullException("cannot get user list by pid"));
        List<UserSimpleInfoWithPrifileDTO> userSimpleInfoWithPrifileDTOList = new ArrayList<UserSimpleInfoWithPrifileDTO>();
        for(Users user : optUserList) {
            UserSimpleInfoWithPrifileDTO userSimpleInfoWithPrifileDTO = new UserSimpleInfoWithPrifileDTO();
            userSimpleInfoWithPrifileDTO.setUid(user.getUid());
            userSimpleInfoWithPrifileDTO.setEmail(user.getEmail());
            userSimpleInfoWithPrifileDTO.setNickname(user.getNickname());
            userSimpleInfoWithPrifileDTOList.add(userSimpleInfoWithPrifileDTO);
        }
        return userSimpleInfoWithPrifileDTOList;
    }

    @Override
    public List<Long> getUserUidListByPid(Long pid) throws UidNullException {
        Optional<List<userSimpleInfoDTO>> optUserList = userProjectRepositorySupport.getUserSimpleInfoDTOListByPid(pid);
        List<userSimpleInfoDTO> userList = optUserList.orElseThrow(() -> new UidNullException("cannot get user list by pid " + pid));
        for(userSimpleInfoDTO user : userList) {
            System.out.println(user.toString());
        }
        List<Long> newUserList = new ArrayList<Long>();
        for(userSimpleInfoDTO user : userList) {
            newUserList.add(user.getUid());
        }

        return newUserList;
    }

    @Override
    public List<Projects> getJoinedProjectList(Long uid) throws ProjectNullException {
        Optional<List<Projects>> joinedProjectList = userProjectRepositorySupport.getJoinedProjectList(uid);
        List<Projects> projectList = joinedProjectList.orElseThrow(() -> new ProjectNullException("cannot get project list by uid " + uid));

        return projectList;
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
