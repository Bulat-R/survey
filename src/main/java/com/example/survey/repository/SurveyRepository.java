package com.example.survey.repository;

import com.example.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

    List<Survey> findAllByStartTimeBeforeAndEndTimeAfter(LocalDateTime startBefore, LocalDateTime endAfter);

    Survey findByTitle(String title);
}
