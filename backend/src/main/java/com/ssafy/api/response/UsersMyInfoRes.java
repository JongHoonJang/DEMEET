package com.ssafy.api.response;

import com.ssafy.DTO.ProjectSimpleInfoDTO;
import com.ssafy.DTO.UserMyInfoDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Users;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class UsersMyInfoRes extends BaseResponseBody {
    UserMyInfoDTO user = new UserMyInfoDTO();

    public static UsersMyInfoRes of(Integer statusCode, String message, Users user, List<ProjectSimpleInfoDTO> deActivateProjects){
        UsersMyInfoRes res = new UsersMyInfoRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        System.out.println(user.toString());
        res.user.setUid(user.getUid());
        res.user.setEmail(user.getEmail());
        res.user.setNickname(user.getNickname());
        res.user.setRegdate(user.getRegDate());
        res.user.setActivateProjects(deActivateProjects);
        return res;
    }

}
