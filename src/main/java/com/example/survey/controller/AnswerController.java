package com.example.survey.controller;

import com.example.survey.dto.AnswerDto;
import com.example.survey.service.AnswerService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/api/admin/answer",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Api(tags = "Для администратора")
public class AnswerController {

    private final AnswerService service;

    @PostMapping
    @Operation(
            summary = "Создание или изменение ответа",
            description = "При отсутствии id - создает новый ответ; при наличии id - изменяет существующий"
    )
    public AnswerDto save(@RequestBody AnswerDto dto) {
        return service.save(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение ответа по id")
    public AnswerDto getById(@PathVariable
                             @Parameter(description = "Id ответа")
                                     Long id) {
        return AnswerDto.fromEntity(service.getById(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех ответов")
    public List<AnswerDto> get(@RequestParam(required = false)
                               @Parameter(description = "При наличии параметра - возвращает все ответы по id вопроса")
                                       Long questionId) {
        return service.get(questionId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление ответа по id")
    public void delete(@PathVariable
                       @Parameter(description = "Id ответа")
                               Long id) {
        service.delete(id);
    }
}
