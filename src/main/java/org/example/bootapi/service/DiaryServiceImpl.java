package org.example.bootapi.service;

import lombok.RequiredArgsConstructor;
import org.example.bootapi.model.entity.Diary;
import org.example.bootapi.model.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    @Override
    public List<Diary> getAllDiaryList() throws Exception {
        return diaryRepository.findAll();
    }

    @Override
    public Diary createDiary(Diary diary) throws Exception {
        return diaryRepository.save(diary);
    }
}
