package com.ssafy.api.request;


import lombok.Data;

import java.util.Optional;

/**
 * 프로젝트 정보를 업데이트할때 필요한 리퀘스트 바디 정의
 */
@Data
public class ProjectPatchPostReq {
    Long pid;

    String name;

    String desc;

    Optional<Boolean> deactivate;

}
