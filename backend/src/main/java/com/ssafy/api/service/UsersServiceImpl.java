package com.ssafy.api.service;

/**
 * Users 관련 비즈니스 로직 처리를 위한 서비스 구현 정의
 */

import com.ssafy.DTO.user.UserSimpleInfoDTO;
import com.ssafy.api.request.UsersRegisterPostReq;
import com.ssafy.common.customException.UserNullException;
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
import java.util.ArrayList;
import java.util.List;

import static org.kurento.jsonrpc.client.JsonRpcClient.log;


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

    /**
     * User객체를 userSimpleInfoDTO로 만들어준다.
     *
     * @param newUser
     * @return UsersSimpleInfoDTO
     */
    public UserSimpleInfoDTO makeUserSimpleInfoDTO(Users newUser) {
        UserSimpleInfoDTO dto = new UserSimpleInfoDTO();
        dto.setUid(newUser.getUid());
        dto.setEmail(newUser.getEmail());
        dto.setNickname(newUser.getNickname());
        return dto;
    }

    @Override
    public Users createUser(UsersRegisterPostReq usersRegisterInfo) {
        log.info("회원가입 시작");
        Users newUser = new Users();
        LocalDateTime localDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        newUser.setEmail(usersRegisterInfo.getEmail());
        newUser.setNickname(usersRegisterInfo.getNickname());
        newUser.setRegDate(localDateTime);
        // jwt를 이용해 패스워드를 암호화하여 디비에 저장
        newUser.setPassword(passwordEncoder.encode(usersRegisterInfo.getPassword()));
        log.debug("회원가입 유저 = {}", newUser.toString());
        Users user = usersRepository.save(newUser);
        log.info("회원가입 성공");
        return user;
    }

    @Override
    public Users getUsersByUid(Long uid) throws UserNullException {
        log.info("Uid로 유저 조회");
        Users user = usersRepository.findByUid(uid).orElseThrow(() -> new UserNullException("could not find user by uid " + uid));
        log.info("유저 조회 성공");
        log.debug(user.toString());
        return user;
    }

    @Override
    public Users getUsersByUserEmail(String userEmail) throws UserNullException {
        log.info("이메일로 유저 조회");
        Users user = usersRepository.findUsersByEmail(userEmail).orElseThrow(() -> new UserNullException("Could not find user by email " + userEmail));
        log.info("유저 조회 완료");
        log.debug(user.toString());
        return user;
    }

    @Override
    public Boolean checkEmailDuplicate(String email) {
        boolean check = usersRepositorySupport.checkEmailDuplicate(email);
        return check;
    }

    @Override
    public List<UserSimpleInfoDTO> getUserList() {
        log.info("유저의 전체 목록 조회");
        List<Users> userList = usersRepository.findAll();
        log.debug("유저의 명수 = {}", userList.size());
        log.info("userDTOList 생성");
        List<UserSimpleInfoDTO> userDTOList = new ArrayList<UserSimpleInfoDTO>();
        for (Users user : userList) {
            UserSimpleInfoDTO usersSimpleInfoDTO = new UserSimpleInfoDTO();
            usersSimpleInfoDTO.setUid(user.getUid());
            usersSimpleInfoDTO.setEmail(user.getEmail());
            usersSimpleInfoDTO.setNickname(user.getNickname());
            usersSimpleInfoDTO.setProfileImagePath(user.getProfileImagePath().getPath() + "");
            userDTOList.add(usersSimpleInfoDTO);
        }
        return userDTOList;
    }

    @Override
    @Transactional
    public Boolean changeUserPassword(Long uid, String newPassword) {
        log.info("유저 비밀번호 변경");
        Boolean changeCheck = usersRepositorySupport.changeUserPassword(uid, passwordEncoder.encode(newPassword));
        log.debug("성공여부 = {}", changeCheck);
        return changeCheck;
    }

    @Override
    @Transactional
    public Boolean changeUserNickname(Long uid, String newNickname) {
        log.info("유저 닉네임 변경");
        Boolean changeCheck = usersRepositorySupport.changeUserNickname(uid, newNickname);
        log.debug("성공여부 = {}", changeCheck);
        return changeCheck;
    }


    @Override
    public boolean deleteUser(String username) {
        log.info("유저 삭제");
        // 호스트가 자기가만든 진행중인 프로젝트가 있을경우 그 프로젝트들 전부 비활성화시킨다.
        // 오너아이디는 그 다음사람에게 넘긴다.
        // 만약에 그 다음사람이 없으면, 그냥 프로젝트 삭제

        try {
            Long uid = usersRepositorySupport.findUserByEmail(username).get().getUid();
            // 여기서 사용자가 만든 프로젝트가 있는지 조회한다.
            List<Projects> projectsList = projectsRepository.findProjectsByOwnerId(uid);
            System.out.println(projectsList.size());
            if (projectsList.size() != 0) {

            }
            usersRepository.deleteById(uid);
            log.debug("유저 삭제 성공여부 = {}", true);
            return true;
        } catch (IllegalArgumentException e) {
            log.error("유저 삭제 실패");
            return false;
        }
    }
}
