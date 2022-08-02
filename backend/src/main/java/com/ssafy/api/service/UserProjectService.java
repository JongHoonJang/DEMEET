package com.ssafy.api.service;

import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.common.customException.UidNullException;

import java.util.List;

/**
 * UsersProjects 관련 빈즈니스 로직 처리를 위한 서비스 인터페이스 정의
 *
 */
public interface UserProjectService {


    List<userSimpleInfoDTO> getUserListByPid(Long pid) throws UidNullException;
}
