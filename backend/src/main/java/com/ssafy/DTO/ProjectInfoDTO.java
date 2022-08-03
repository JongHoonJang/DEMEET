package com.ssafy.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProjectInfoDTO {
    Long pid;
    Long projectOwner;
    List<userSimpleInfoDTO> member;
    LocalDateTime pjtStartDate;
    LocalDateTime pjtEndDate;
    String pjtName;
    String pjtDesc;
    LocalDateTime totalMeetTime;
    boolean activation;
}
