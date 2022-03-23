package com.example.survey.dto;

import com.example.survey.entity.Question;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class QuestionDto {

    @ApiModelProperty(notes = "Id вопроса. При отсутствии id создается новый вопрос")
    private Long id;

    @NotNull
    @ApiModelProperty(notes = "Id опроса, в котором должен быть данный вопрос", required = true, position = 1)
    private Long surveyId;

    @NotBlank
    @ApiModelProperty(notes = "Текст вопроса", example = "Где?", required = true, position = 2)
    private String text;

    @NotNull
    @ApiModelProperty(
            notes = "Тип вопроса (TEXT - ответ текстом, SINGLE - ответ с выбором одного варианта, MULTI - ответ с выбором нескольких вариантов)",
            required = true, position = 3, allowableValues = "TEXT, SINGLE, MULTI"
    )
    private String type;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(notes = "Список ответов к вопросам типа SINGLE, MULTI (только для чтения)", position = 4)
    private List<AnswerDto> answers;

    public static QuestionDto fromEntity(Question q) {
        List<AnswerDto> list = q.getAnswers().stream()
                .map(AnswerDto::fromEntity)
                .collect(Collectors.toList());

        return QuestionDto.builder()
                .id(q.getId())
                .surveyId(q.getSurvey().getId())
                .text(q.getText())
                .type(String.valueOf(q.getType()))
                .answers(list)
                .build();
    }
}
