package com.ssafy.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserSimpleInfoWithPrifileDTO extends userSimpleInfoDTO {
    String profileImagePath;
}
