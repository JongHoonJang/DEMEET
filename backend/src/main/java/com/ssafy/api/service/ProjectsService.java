package com.ssafy.api.service;

import com.ssafy.DTO.project.ProjectDeactivateSimpleInfoDTO;
import com.ssafy.DTO.project.ProjectSimpleInfoDTO;
import com.ssafy.api.request.ProjectPatchPostReq;
import com.ssafy.api.request.ProjectsCreatePostReq;
import com.ssafy.common.customException.NoAuthorizedException;
import com.ssafy.common.customException.ProjectNullException;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Projects;

import java.util.List;

/**
 * Projects 관련 빈즈니스 로직 처리를 위한 서비스 인터페이스 정의
 *
 */
public interface ProjectsService {

    Projects createProject(ProjectsCreatePostReq projectsCreatePostReq) throws UidNullException;

    Projects getProject(Long pid) throws ProjectNullException;

    List<ProjectSimpleInfoDTO> getActivateProjectsList(Long uid) throws ProjectNullException;

    Projects patchProjectInfo(ProjectPatchPostReq projectPatchPostReq, Long uid) throws ProjectNullException, NullPointerException;

    List<ProjectSimpleInfoDTO> getJoinedProjectList(Long uid) throws ProjectNullException;

    List<ProjectDeactivateSimpleInfoDTO> getDeActivateProjectsByUid(Long uid) throws ProjectNullException, UidNullException;

    Projects deactivateProject(long pid, Long userUid) throws ProjectNullException, NoAuthorizedException;

    Projects getProjectBySessionId(String sessionId) throws ProjectNullException;

    List<Projects> getDeactivatedProjectsByUid(Long uid) throws ProjectNullException;


    List<ProjectDeactivateSimpleInfoDTO> changetProjectListToProjectDeactivateSimpleInfoDTOList(List<Projects> deActivateProjectsList) throws UidNullException;
}
