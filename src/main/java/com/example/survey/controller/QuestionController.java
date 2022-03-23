package com.example.survey.controller;

import com.example.survey.dto.QuestionDto;
import com.example.survey.service.QuestionService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/admin/question",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Api(tags = "Для администратора")
public class QuestionController {

    private final QuestionService service;

    @PostMapping
    @Operation(
            summary = "Создание или изменение вопроса",
            description = "При отсутствии id - создает новый вопрос; при наличии id - изменяет существующий"
    )
    public QuestionDto save(@RequestBody QuestionDto dto) {
        return service.save(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение вопроса по id")
    public QuestionDto getById(@PathVariable
                               @Parameter(description = "Id вопроса")
                                       Long id) {
        return QuestionDto.fromEntity(service.getById(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех вопросов")
    public List<QuestionDto> get(@RequestParam(required = false)
                                 @Parameter(description = "При наличии параметра - возвращает все вопросы по id опроса")
                                         Long surveyId) {
        return service.get(surveyId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление вопроса по id")
    public void delete(@PathVariable
                       @Parameter(description = "Id вопроса")
                               Long id) {
        service.delete(id);
    }
}
