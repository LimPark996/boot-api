package org.example.bootapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    // 2가지
    // 1. 파일명 - 파일명만...
    // 2. URL - 괜히 서버를 거치는 것도...
    String upload(MultipartFile file) throws Exception; // 어떠한 형태가 나올지 모르기 때문에...
    byte[] download(String filename) throws Exception;
}
