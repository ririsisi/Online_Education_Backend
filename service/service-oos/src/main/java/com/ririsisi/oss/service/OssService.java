package com.ririsisi.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author ririsisi
 * @Version 1.0
 */
public interface OssService {

    // 上传头像
    String uploadFileAvatar(MultipartFile file);

}
