package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int uid;
    @Column(length = 30, nullable = false, unique = true)
    String email;
    @Column(length = 100, nullable = false)
    String password; // 토큰값이 생각보다 길어 length값을 늘림
    @Column(length = 15, nullable = false)
    String nickname;
    @Column(nullable = false)
    LocalDateTime regDate;


}