package com.ssafy.api.service;

/**
 * Users 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */

import com.ssafy.DTO.ProjectSimpleInfoDTO;
import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.api.request.UsersRegisterPostReq;
import com.ssafy.common.customException.UidNullException;
import com.ssafy.db.entity.Projects;
import com.ssafy.db.entity.Users;
import com.ssafy.db.repository.ProjectsRepository;
import com.ssafy.db.repository.UsersRepository;
import com.ssafy.db.repository.UsersRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;


@Service("usersService")
public class UsersServiceImpl implements UsersService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersRepositorySupport usersRepositorySupport;

    @Autowired
    ProjectsRepository projectsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    // User객체를 userSimpleInfoDTO로 만들어주는 함수
    public userSimpleInfoDTO makeUserSimpleInfoDTO(Users newUser){
        userSimpleInfoDTO dto = new userSimpleInfoDTO();
        dto.setUid(newUser.getUid());
        dto.setEmail(newUser.getEmail());
        dto.setNickname(newUser.getNickname());
        return dto;
    }

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

    @Override
    @Transactional
    public Boolean changeUserPassword(Long uid, String newPassword) {
        Boolean changeCheck = usersRepositorySupport.changeUserPassword(uid, passwordEncoder.encode(newPassword));
        return changeCheck;
    }

    @Override
    @Transactional
    public Boolean changeUserNickname(Long uid, String newNickname) {
        Boolean changeCheck = usersRepositorySupport.changeUserNickname(uid, newNickname);
        return changeCheck;
    }

    @Override
    public Users getUsersByUid(Long ownerId) throws UidNullException {
        Optional<Users> optOwner = usersRepositorySupport.findUserById(ownerId);
        Users owner = optOwner.orElseThrow(() -> new UidNullException("User not found: " + ownerId));

        return owner;
    }


    @Override
    public boolean deleteUser(String username) {
        try {
            Long uid = usersRepositorySupport.findUserByEmail(username).get().getUid();
            Long userId = uid;
            System.out.println("userId: " + userId);
            // 여기서 사용자가 만든 프로젝트가 있는지 조회한다.
            List<Projects> projectsList = projectsRepository.findProjectsByOwnerId(uid);
            System.out.println(projectsList.size());
            if (projectsList.size() != 0){

            }
            usersRepository.deleteById(userId);
        }
        catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
