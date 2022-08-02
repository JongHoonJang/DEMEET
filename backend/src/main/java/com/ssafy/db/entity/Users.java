package com.ssafy.db.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@ToString(of = {"uid", "email", "nickname", "regDate"})
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    Long uid;
    @Column(length = 30, nullable = false, unique = true)
    String email;
    @Column(length = 100, nullable = false)
    String password; // 토큰값이 생각보다 길어 length값을 늘림
    @Column(length = 15, nullable = false)
    String nickname;
    @Column(nullable = false)
    LocalDateTime regDate;


}