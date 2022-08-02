package com.ssafy.api.service;

import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.repository.UserProjectRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("UserProjectService")
public class UserProjectServiceImpl implements UserProjectService{

    @Autowired
    UserProjectRepositorySupport userProjectRepositorySupport;
    @Override
    public List<userSimpleInfoDTO> getUserListByPid(Long pid) throws UidNullException {
        Optional<List<userSimpleInfoDTO>> optUserList = userProjectRepositorySupport.getUserListByPid(pid);
        List<userSimpleInfoDTO> userList = optUserList.orElseThrow(() -> new UidNullException("cannot get user list by pid " + pid));
        for(userSimpleInfoDTO user : userList) {
            System.out.println(user.toString());
        }
        return userList;
    }
}
