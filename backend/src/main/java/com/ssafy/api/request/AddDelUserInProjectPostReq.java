package com.ssafy.api.request;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class AddDelUserInProjectPostReq {
    @Positive
    Long uid;

    @Positive
    Long pid;
}
