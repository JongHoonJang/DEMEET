package com.ssafy.api.request;

import lombok.Data;

@Data
public class AddDelUserInProjectPostReq {
    Long pid;
    Long uid;
}
