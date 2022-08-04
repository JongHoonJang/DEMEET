package com.ssafy.api.service;

import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.AddUserInProjectPostReq;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.UserProject;
import com.ssafy.db.entity.Users;

import java.util.List;

/**
 * UsersProjects 관련 빈즈니스 로직 처리를 위한 서비스 인터페이스 정의
 *
 */
public interface UserProjectService {


    List<userSimpleInfoDTO> getUserListByPid(Long pid) throws UidNullException;

    UserProject addUserInProject(AddUserInProjectPostReq addUserInProjectPostReq, Projects project, Users user);

    boolean userDuplicateCheck(Projects project, Users user);
}
