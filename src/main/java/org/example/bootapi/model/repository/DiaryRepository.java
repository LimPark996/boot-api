package org.example.bootapi.model.repository;

import org.example.bootapi.model.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, String> {
}
