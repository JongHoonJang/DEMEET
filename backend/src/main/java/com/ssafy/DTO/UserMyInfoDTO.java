package com.ssafy.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserMyInfoDTO extends userSimpleInfoDTO{
    LocalDateTime regdate;
    String profileImagePath;
    List<ProjectDeactivateSimpleInfoDTO> deActivateProjects = new ArrayList<>();
}
