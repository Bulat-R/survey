package com.example.survey.repository;

import com.example.survey.entity.SingleUserAnswer;
import com.example.survey.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SingleUserAnswerRepository extends JpaRepository<SingleUserAnswer, Long> {

    List<SingleUserAnswer> findAllByUserId(Long userId);

    @Query("select distinct sua.question.survey from SingleUserAnswer sua where sua.userId = ?1")
    List<Survey> getUserSurveys(Long userId);
}
