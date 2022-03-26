package com.example.survey.service;

import com.example.survey.dto.AllUserSurveysDto;
import com.example.survey.dto.SingleUserAnswerDto;
import com.example.survey.dto.SurveyDto;
import com.example.survey.entity.*;
import com.example.survey.exception.BadRequestException;
import com.example.survey.repository.SingleUserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SingleUserAnswerService {

    private final SingleUserAnswerRepository repository;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Transactional
    public List<SingleUserAnswerDto> save(List<SingleUserAnswerDto> dtoList, Long userId) {
        List<SingleUserAnswer> list = dtoList.stream()
                .map(dto -> fromDto(dto, userId))
                .collect(Collectors.toList());

        return repository.saveAll(list).stream()
                .map(SingleUserAnswerDto::fromEntity)
                .collect(Collectors.toList());
    }

    public AllUserSurveysDto getAllUserSurveysAndAnswers(Long userId) {
        List<SurveyDto> surveys = repository.getUserSurveys(userId).stream()
                .map(SurveyDto::fromEntity)
                .collect(Collectors.toList());

        List<SingleUserAnswerDto> answers = repository.findAllByUserId(userId)
                .stream()
                .map(SingleUserAnswerDto::fromEntity)
                .collect(Collectors.toList());

        return AllUserSurveysDto.builder()
                .userId(userId)
                .surveys(surveys)
                .answers(answers)
                .build();
    }

    private SingleUserAnswer fromDto(SingleUserAnswerDto dto, Long userId) {
        Question question = questionService.getById(dto.getQuestionId());

        Survey survey = question.getSurvey();
        LocalDateTime now = LocalDateTime.now();
        if (survey.getStartTime().isBefore(now) || survey.getEndTime().isAfter(now)) {
            throw new BadRequestException("Id вопроса не соответствует активным опросам");
        }

        Answer answer = null;
        if (question.getType() == QuestionType.TEXT) {
            dto.setAnswerId(null);
        } else {
            if (dto.getAnswerId() == null) {
                throw new BadRequestException("Id ответа не может быть пустым");
            }
            List<Long> answersIds = question.getAnswers().stream()
                    .map(Answer::getId)
                    .collect(Collectors.toList());
            if (!answersIds.contains(dto.getAnswerId())) {
                throw new BadRequestException("Неверный id ответа");
            }
            dto.setUserText(null);
            answer = answerService.getById(dto.getAnswerId());
        }

        return SingleUserAnswer.builder()
                .userId(userId)
                .question(question)
                .answer(answer)
                .userText(dto.getUserText())
                .build();
    }
}
