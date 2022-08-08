package com.ssafy.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectDeactivateSimpleInfoDTO {

    Long pid;
    String pjtName;
    List<Long> member = new ArrayList<>();
    LocalDateTime pjtStartDate;
    LocalDateTime pjtEndDate;

}
