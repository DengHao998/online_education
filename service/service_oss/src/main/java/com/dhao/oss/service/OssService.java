package com.dhao.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: dhao
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file);
}
