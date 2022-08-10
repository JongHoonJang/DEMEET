package com.ssafy.api.response;


import com.ssafy.DTO.project.ProjectInfoDTO;
import com.ssafy.DTO.user.UserSimpleInfoDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Projects;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
public class ProjectInfoRes extends BaseResponseBody {


    ProjectInfoDTO project = new ProjectInfoDTO();

    public ProjectInfoRes(){
        super();
    }
    public static ProjectInfoRes of(Integer statusCode, String message, Projects project, Long projectOwner,  List<UserSimpleInfoDTO> userList){
        ProjectInfoRes res = new ProjectInfoRes();
//         project = new ProjectInfoDTO();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        System.out.println(project.toString());
        res.project.setPid(project.getPid());
        System.out.println("pid완료");
        res.project.setProjectOwner(projectOwner);
        res.project.setMember(userList);
        res.project.setSessionId(project.getSessionId());
        res.project.setPjtStartDate(project.getPjtStartDate());
        res.project.setPjtEndDate(project.getPjtEndDate());
        res.project.setPjtName(project.getPjtName());
        res.project.setPjtDesc(project.getPjtDesc());
        res.project.setTotalMeetTime(project.getTotalMeetTime());
        res.project.setActivation(project.isActivation());
        return res;
    }

}
