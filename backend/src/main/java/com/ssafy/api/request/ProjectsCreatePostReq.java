package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 프로젝트 생성 요청에 필요한 리퀘스트 바디 정의
 */
@Getter
@Setter
@ToString
public class ProjectsCreatePostReq {
    /**
     * The Owner id.
     */
    Long owner_id;
    /**
     * The Pjt name.
     */
    String pjt_name;
    /**
     * The Member list.
     */
    List<Long> memberList;
    /**
     * The Pjt desc.
     */
    String pjt_desc;
}
