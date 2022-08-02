package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.DTO.QuserSimpleInfoDTO;
import com.ssafy.DTO.userSimpleInfoDTO;
import com.ssafy.db.entity.QUserProject;
import com.ssafy.db.entity.QUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserProjectRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUserProject qUserProject = QUserProject.userProject;
    QUsers qUsers = QUsers.users;

    public Optional<List<userSimpleInfoDTO>> getUserListByPid(int pid) {
        List<userSimpleInfoDTO> userList = jpaQueryFactory.select(new QuserSimpleInfoDTO(qUserProject.users.uid, qUserProject.users.email, qUserProject.users.nickname))
                .from(qUserProject)
                .where(qUserProject.projects.pid.eq(pid)).fetch();
        if (userList == null) return Optional.empty();
        return Optional.ofNullable(userList);
    }
}
