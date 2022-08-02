package com.ssafy.api.service;

import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.common.customException.UidNullException;

/**
 * Projects 관련 빈즈니스 로직 처리를 위한 서비스 인터페이스 정의
 *
 */
public interface ProjectsService {

    Integer createProject(ProjectsCreatePostReq projectsCreatePostReq) throws UidNullException;
}
