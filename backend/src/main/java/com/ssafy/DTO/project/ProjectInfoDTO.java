package com.ssafy.DTO.project;

import com.ssafy.DTO.user.UserSimpleInfoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectInfoDTO {
    Long pid;
    Long projectOwner;
    List<UserSimpleInfoDTO> member;
    LocalDateTime pjtStartDate;
    LocalDateTime pjtEndDate;
    String pjtName;
    String pjtDesc;
    String sessionId;
    Long totalMeetTime;
    boolean activation;
}
