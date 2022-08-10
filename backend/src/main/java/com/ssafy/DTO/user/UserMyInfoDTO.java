package com.ssafy.DTO.user;

import com.ssafy.DTO.project.ProjectDeactivateSimpleInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserMyInfoDTO extends UserSimpleInfoDTO{
    LocalDateTime regdate;
    List<ProjectDeactivateSimpleInfoDTO> deActivateProjects = new ArrayList<>();
}
