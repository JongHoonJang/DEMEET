package com.ssafy.DTO;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class projectInfoDTO {

    int pid;

    int ownerId;

    LocalDateTime pjtStartDate;

    LocalDateTime pjtEndDate;

    String pjtName;

    String pjtDesc;

    LocalDateTime totalMeetTime;

    boolean activation;

    public projectInfoDTO() {
    }

    @QueryProjection
    public projectInfoDTO(int pid, int ownerId, LocalDateTime pjtStartDate, LocalDateTime pjtEndDate, String pjtName, String pjtDesc, LocalDateTime totalMeetTime, boolean activation) {
        this.pid = pid;
        this.ownerId = ownerId;
        this.pjtStartDate = pjtStartDate;
        this.pjtEndDate = pjtEndDate;
        this.pjtName = pjtName;
        this.pjtDesc = pjtDesc;
        this.totalMeetTime = totalMeetTime;
        this.activation = activation;
    }
}
