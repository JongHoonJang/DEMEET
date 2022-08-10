package com.ssafy.api.response;

import com.ssafy.DTO.UserSimpleInfoWithPrifileDTO;
import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.common.model.response.BaseResponseBody;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserListRes extends BaseResponseBody {

    List<UserSimpleInfoWithPrifileDTO> userList;

    public static UserListRes of(Integer statusCode, String message, List<UserSimpleInfoWithPrifileDTO> userList) {
        UserListRes res = new UserListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setUserList(userList);
        return res;
    }
}
