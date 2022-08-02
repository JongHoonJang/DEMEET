package com.ssafy.DTO;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class projectInfoDTO {
    int pid;
    userSimpleInfoDTO projectOwner;
    List<userSimpleInfoDTO> member;
    LocalDateTime pjtStartDate;
    LocalDateTime pjtEndDate;
    String pjtName;
    String pjtDesc;
    LocalDateTime totalMeetTime;
    boolean activation;
}
