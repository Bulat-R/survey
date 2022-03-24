package com.example.survey.dto;

import com.example.survey.entity.Question;
import com.example.survey.entity.Survey;
import com.example.survey.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class SurveyDto {

    @ApiModelProperty(notes = "Id опроса. При отсутствии id создается новый опрос", example = "1")
    private Long id;

    @NotBlank
    @ApiModelProperty(notes = "Заголовок опроса", example = "Опрос №1", required = true, position = 1)
    private String title;

    @NotNull
    @ApiModelProperty(
            notes = "Дата и время начала опроса. После создания не может изменяться",
            example = "2022-03-23T20:00:00", required = true, position = 2
    )
    private LocalDateTime startTime;

    @NotNull
    @ApiModelProperty(
            notes = "Дата и время завершения опроса",
            example = "2022-03-25T20:00:00", required = true, position = 3
    )
    private LocalDateTime endTime;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "Список вопросов в опросе (только для чтения)", position = 4)
    private List<QuestionDto> questions;

    public void validateDates() {
        if (id == null && startTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Дата начала в прошлом");
        }
        if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
            throw new BadRequestException("Дата окончания должна быть позже даты начала");
        }
    }

    public static SurveyDto fromEntity(Survey s) {
        List<QuestionDto> list;
        List<Question> questions = s.getQuestions();
        if (questions != null) {
            list = questions
                    .stream()
                    .map(QuestionDto::fromEntity)
                    .collect(Collectors.toList());
        } else {
            list = new ArrayList<>();
        }

        return SurveyDto.builder()
                .id(s.getId())
                .title(s.getTitle())
                .startTime(s.getStartTime())
                .endTime(s.getEndTime())
                .questions(list)
                .build();
    }
}
