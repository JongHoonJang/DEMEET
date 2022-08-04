package com.ssafy.api.request;

import lombok.Data;

@Data
public class AddUserInProjectPostReq {
    Long pid;
    Long uid;
}
