package com.ssafy.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserMyInfoDTO extends userSimpleInfoDTO{
    LocalDateTime regdate;
    List<ProjectSimpleInfoDTO> activateProjects = new ArrayList<ProjectSimpleInfoDTO>();
}
