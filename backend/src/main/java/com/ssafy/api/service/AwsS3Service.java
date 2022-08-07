package com.ssafy.api.service;

import com.ssafy.common.customException.NotImageException;
import com.ssafy.db.entity.ProfileImagePath;
import com.ssafy.db.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AwsS3Service {
    // 파일을 업로드 한다.
    String putImage(MultipartFile multipartFile, Long id, String flag) throws IOException, NotImageException;

    // dipid를 받으면 이미지 주소를 리턴한다.
    String getImage(Long dipid);

    // dipid를 받으면 이미지를 삭제한다.
    void DeleteImage(Long dipid, String flag) throws NotImageException;

    String getFolderName(Long imgid, String flag);

    // pid를 기반으로 파일 이름을 생성한다.
    String getFileName(String originalFileName, Long id, String flag) throws NotImageException;

    // 파일의 형식을 판단한다.
    String getFileExtension(String fileName) throws NotImageException;

    // 파일 정보를 DB에 저장한다.
    Users saveImagePath(String path, long uid, String flag) throws NotImageException;
}
