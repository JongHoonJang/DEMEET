package com.ssafy.DTO;

import lombok.Data;

import java.util.List;

@Data
public class ProjectSimpleInfoDTO {
    Long pid;
    Long projectOwner;
    List<UserSimpleInfoWithPrifileDTO> member;
    String pjtName;
    String pjtDesc;
    boolean activation;
}
