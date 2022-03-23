package com.example.survey.service;

import com.example.survey.dto.AnswerDto;
import com.example.survey.entity.Answer;
import com.example.survey.entity.Question;
import com.example.survey.entity.QuestionType;
import com.example.survey.exception.BadRequestException;
import com.example.survey.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository repository;
    private final QuestionService questionService;

    @Transactional
    public AnswerDto save(AnswerDto dto) {
        Question q = questionService.getById(dto.getQuestionId());
        if (q.getType() == QuestionType.TEXT) {
            throw new BadRequestException("Неверный тип вопроса");
        }
        Answer a;
        if (dto.getId() == null) {
            a = Answer.builder()
                    .text(dto.getText())
                    .question(q)
                    .build();
        } else {
            a = getById(dto.getId());
            a.setText(dto.getText());
            a.setQuestion(q);
        }
        repository.save(a);
        return AnswerDto.fromEntity(a);
    }

    public Answer getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Неверный id ответа"));
    }

    public List<AnswerDto> get(Long questionId) {
        List<Answer> list;
        if (questionId != null) {
            list = repository.findByQuestionId(questionId);
        } else {
            list = repository.findAll();
        }
        return list.stream()
                .map(AnswerDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
