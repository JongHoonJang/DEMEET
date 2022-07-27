package com.ssafy.DTO;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class userSimpleInfoDTO {
    int uid;
    String email;
    String nickname;

    public userSimpleInfoDTO() {
    }

    @QueryProjection
    public userSimpleInfoDTO(int uid, String email, String nickname) {
        this.uid = uid;
        this.email = email;
        this.nickname = nickname;
    }
}