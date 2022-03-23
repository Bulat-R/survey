package com.example.survey.controller;

import com.example.survey.dto.AllUserSurveysDto;
import com.example.survey.dto.SingleUserAnswerDto;
import com.example.survey.dto.SurveyDto;
import com.example.survey.service.SingleUserAnswerService;
import com.example.survey.service.SurveyService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/public",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Api(tags = "Для пользователей")
public class PublicController {

    private final SurveyService surveyService;
    private final SingleUserAnswerService singleUserAnswerService;

    @GetMapping
    @Operation(summary = "Получение активных опросов")
    public List<SurveyDto> getActiveSurveys() {
        return surveyService.get(true);
    }

    @PostMapping("/user/{userId}")
    @Operation(summary = "Сохранение ответов пользователя")
    public List<SingleUserAnswerDto> saveUserAnswers(@RequestBody List<SingleUserAnswerDto> dtoList,
                                                     @PathVariable @Parameter(description = "Id пользователя")
                                                             Long userId) {
        return singleUserAnswerService.save(dtoList, userId);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Получение опросов и результатов по id пользователя")
    public AllUserSurveysDto getAllUserSurveysAndAnswers(@PathVariable @Parameter(description = "Id пользователя") Long userId) {
        return singleUserAnswerService.getAllUserSurveysAndAnswers(userId);
    }

}
