package com.example.survey.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllUserSurveysDto {

    @ApiModelProperty(notes = "Id пользователя", required = true)
    private Long userId;

    @ApiModelProperty(notes = "Список опросов, в которых участвовал пользователь", required = true, position = 1)
    private List<SurveyDto> surveys;

    @ApiModelProperty(notes = "Список ответов пользователя на вопросы", required = true, position = 2)
    private List<SingleUserAnswerDto> answers;
}
