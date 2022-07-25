package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QUsers;
import com.ssafy.db.entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * users 모델 관련 디비 쿼리 생성을 위한 구현 정의
 */
@Repository
public class UsersRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QUsers qNewUsers = QUsers.users;


    public Optional<Users> findUserByEmail(String email) {
        Users newUser = jpaQueryFactory.select(qNewUsers).from(qNewUsers)
                .where(qNewUsers.email.eq(email)).fetchOne();
        if(newUser == null) return Optional.empty();
        return Optional.ofNullable(newUser);
    }
}
