package com.ssafy.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class DrawingUploadReq extends ImageUploadReq{
    @NotBlank
    int pid;
    @NotBlank
    int cid;
}
