package com.example.survey.service;

import com.example.survey.dto.AllUserSurveysDto;
import com.example.survey.dto.SingleUserAnswerDto;
import com.example.survey.dto.SurveyDto;
import com.example.survey.entity.Answer;
import com.example.survey.entity.Question;
import com.example.survey.entity.QuestionType;
import com.example.survey.entity.SingleUserAnswer;
import com.example.survey.exception.BadRequestException;
import com.example.survey.repository.SingleUserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SingleUserAnswerService {

    private final SingleUserAnswerRepository repository;
    private final QuestionService questionService;
    private final AnswerService answerService;

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
                .id(userId)
                .question(question)
                .answer(answer)
                .userText(dto.getUserText())
                .build();
    }
}
