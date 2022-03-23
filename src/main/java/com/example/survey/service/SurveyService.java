package com.example.survey.service;

import com.example.survey.dto.SurveyDto;
import com.example.survey.entity.Survey;
import com.example.survey.exception.BadRequestException;
import com.example.survey.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository repository;

    @Transactional
    public SurveyDto save(SurveyDto dto) {
        Survey s;
        if (dto.getId() == null) {
            s = Survey.builder()
                    .title(dto.getTitle())
                    .startTime(dto.getStartTime())
                    .endTime(dto.getEndTime())
                    .build();
        } else {
            s = getById(dto.getId());
            s.setTitle(dto.getTitle());
            s.setEndTime(dto.getEndTime());
        }
        repository.save(s);
        return SurveyDto.fromEntity(s);
    }

    public Survey getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new BadRequestException("Неверный id опроса"));
    }

    public List<SurveyDto> get(boolean active) {
        List<Survey> list;
        if (active) {
            LocalDateTime dateTime = LocalDateTime.now();
            list = repository.findAllByStartTimeBeforeAndEndTimeAfter(dateTime, dateTime);
        } else {
            list = repository.findAll();
        }
        return list.stream()
                .map(SurveyDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
