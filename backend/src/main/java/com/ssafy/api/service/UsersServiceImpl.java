package com.ssafy.api.service;

/**
 * Users 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */

import com.ssafy.api.request.UsersRegisterPostReq;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.ssafy.db.entity.QUser.user;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Users createUsers(UsersRegisterPostReq usersRegisterInfo) {
        Users newUser = new Users();
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        System.out.println(timestamp);
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
}