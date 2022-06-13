package com.sdxx.oss.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface OssService {
    //上传头像到oss
//    String uploadFileAvatar(MultipartFile file);
    String uploadFileAvatar(MultipartFile file, HttpServletRequest request);

    String uploadFile(MultipartFile file);

    void downloadFile(String filename);

//    void delFile(String filename);

}
