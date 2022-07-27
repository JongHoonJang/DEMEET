package com.ssafy.api.service;

/**
 * Users 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */

import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.UsersRegisterPostReq;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ssafy.db.repository.UsersRepositorySupport;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersRepositorySupport usersRepositorySupport;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Users createUsers(UsersRegisterPostReq usersRegisterInfo) {
        Users newUser = new Users();
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        System.out.println(localDateTime);
        newUser.setEmail(usersRegisterInfo.getEmail());
        newUser.setNickname(usersRegisterInfo.getNickname());
        newUser.setRegDate(localDateTime);
        // 이론상 오류가 난다면 이때 나야한다.
        System.out.println("UsersServiceImpl의 createUsers 30");
        // jwt를 이용해 패스워드를 암호화하여 디비에 저장
        newUser.setPassword(passwordEncoder.encode(usersRegisterInfo.getPassword()));
        return usersRepository.save(newUser);
    }

    @Override
    public Users getUsersByUserEmail(String userEmail) {
        // 디비에 유저 정보 조회
        Users newUser = usersRepositorySupport.findUserByEmail(userEmail).get();
        return newUser;
    }

    @Override
    public Boolean checkEmailDuplicate(String email) {

        boolean check = usersRepositorySupport.checkEmailDuplicate(email);
        return check;
    }

    @Override
    public List<userSimpleInfoDTO> getUserList() {
        List<userSimpleInfoDTO> userList = usersRepositorySupport.getUserList();
        return userList;
    }
}