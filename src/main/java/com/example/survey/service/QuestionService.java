package com.example.survey.service;

import com.example.survey.dto.QuestionDto;
import com.example.survey.entity.Question;
import com.example.survey.entity.QuestionType;
import com.example.survey.exception.BadRequestException;
import com.example.survey.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository repository;
    private final SurveyService surveyService;

    @Transactional
    public QuestionDto save(QuestionDto dto) {
        Question q;
        if (dto.getId() == null) {
            q = Question.builder()
                    .text(dto.getText())
                    .type(QuestionType.valueOf(dto.getType()))
                    .survey(surveyService.getById(dto.getSurveyId()))
                    .build();
        } else {
            q = getById(dto.getId());
            q.setText(dto.getText());
            q.setType(QuestionType.valueOf(dto.getType()));
            q.setSurvey(surveyService.getById(dto.getSurveyId()));
        }
        repository.save(q);
        return QuestionDto.fromEntity(q);
    }

    public Question getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Неверный id вопроса"));
    }

    public List<QuestionDto> get(Long surveyId) {
        List<Question> list;
        if (surveyId != null) {
            list = repository.findBySurveyId(surveyId);
        } else {
            list = repository.findAll();
        }
        return list.stream()
                .map(QuestionDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
