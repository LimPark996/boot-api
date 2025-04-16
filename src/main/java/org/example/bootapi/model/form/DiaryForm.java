package org.example.bootapi.model.form;

import org.springframework.web.multipart.MultipartFile;

public record DiaryForm(MultipartFile file) {
    public static DiaryForm empty() {
        return new DiaryForm(null);
    }
}
