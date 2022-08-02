package com.ssafy.api.service;

import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.UsersRegisterPostReq;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Users;

import java.util.List;

/**
 * Users 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의
 */
public interface UsersService {

    Users createUsers(UsersRegisterPostReq usersRegisterInfo);

    Users getUsersByUserEmail(String userEmail);
    Boolean checkEmailDuplicate(String email);

    List<userSimpleInfoDTO> getUserList();

    Boolean changeUserPassword(int uid, String newPassword);

    Boolean changeUserNickname(int uid, String newNickname);

    userSimpleInfoDTO getUsersByUid(int ownerId) throws UidNullException;
}