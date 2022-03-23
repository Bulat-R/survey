package com.example.survey.controller;

import com.example.survey.dto.SurveyDto;
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
        value = "/api/admin/survey",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
@Api(tags = "Для администратора")
public class SurveyController {

    private final SurveyService service;

    @PostMapping
    @Operation(
            summary = "Создание или изменение опроса",
            description = "При отсутствии id создается новый опрос"
    )
    public SurveyDto save(@RequestBody SurveyDto dto) {
        dto.validateDates();
        return service.save(dto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение опроса по id")
    public SurveyDto getById(@PathVariable
                             @Parameter(description = "Id опроса")
                                     Long id) {
        return SurveyDto.fromEntity(service.getById(id));
    }

    @GetMapping
    @Operation(summary = "Получение всех опросов")
    public List<SurveyDto> get(@RequestParam(required = false)
                               @Parameter(description = "При active = true - возвращает все активные опросы")
                                       boolean active) {
        return service.get(active);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление опроса по id")
    public void delete(@PathVariable
                       @Parameter(description = "Id опроса")
                               Long id) {
        service.delete(id);
    }

}
