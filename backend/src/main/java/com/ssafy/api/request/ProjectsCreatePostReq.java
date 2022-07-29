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
    int owner_id;
    String pjt_name;
    List<Integer> memberList;
    String pjt_desc;
}
