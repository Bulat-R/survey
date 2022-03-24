package com.example.survey.dto;

import com.example.survey.entity.Answer;
import com.example.survey.entity.SingleUserAnswer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
public class SingleUserAnswerDto {

    @NotNull
    @ApiModelProperty(
            name = "question", notes = "Id вопроса, на который дается ответ",
            required = true
    )
    private Long questionId;

    @ApiModelProperty(
            notes = "Id ответа, который пользователь выбрал при ответе на вопрос типа SINGLE, MULTI",
            position = 1
    )
    private Long answerId;

    @ApiModelProperty(
            notes = "Ответ пользователя текстом на вопрос типа TEXT",
            example = "Не знаю", position = 2
    )
    private String userText;

    public static SingleUserAnswerDto fromEntity(SingleUserAnswer sua) {
        Long answerId = sua.getAnswer() == null ? null : sua.getAnswer().getId();
        return SingleUserAnswerDto.builder()
                .questionId(sua.getQuestion().getId())
                .answerId(answerId)
                .userText(sua.getUserText())
                .build();
    }
}
