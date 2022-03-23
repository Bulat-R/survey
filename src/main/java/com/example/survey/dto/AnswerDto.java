package com.example.survey.dto;

import com.example.survey.entity.Answer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class AnswerDto {

    @ApiModelProperty(notes = "Id ответа. При отсутствии id создается новый ответ")
    private Long id;

    @ApiModelProperty(notes = "Текст ответа для вопросов типа SINGLE, MULTI", example = "Да", position = 1)
    private String text;

    @NotNull
    @ApiModelProperty(
            notes = "Id вопроса, для которого данный ответ предназначен",
            example = "1", required = true, position = 2
    )
    private Long questionId;

    public static AnswerDto fromEntity(Answer a) {
        return AnswerDto.builder()
                .id(a.getId())
                .text(a.getText())
                .questionId(a.getQuestion().getId())
                .build();
    }
}
