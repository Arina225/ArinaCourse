package com.app.training;

import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.training.converter.TrainingDtoToTrainingConverter;
import com.app.training.converter.TrainingToTrainingDtoConverter;
import com.app.util.Global;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.TRAINER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainings")
public class TrainingController {

    private final TrainingService service;
    private final TrainingToTrainingDtoConverter toDtoConverter;
    private final TrainingDtoToTrainingConverter toConverter;

    @Secured({TRAINER})
    @GetMapping("/my")
    public Result findAllMy() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All My",
                service.findAllMy().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/trainer/{id}")
    public Result findAllByTrainerId(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All By Trainer Id",
                service.findAllByTrainerId(id).stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({TRAINER})
    @GetMapping("/{id}")
    public Result find(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find",
                toDtoConverter.convert(service.find(id))
        );
    }

    @Secured({TRAINER})
    @PostMapping
    public Result save(@Valid @RequestBody TrainingDto saveDto) {
        Training save = toConverter.convert(saveDto);
        Training saved = service.save(save);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(saved)
        );
    }

    @Secured({TRAINER})
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @Valid @RequestBody TrainingDto updateDto) {
        Training update = toConverter.convert(updateDto);
        Training updated = service.update(id, update);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update",
                toDtoConverter.convert(updated)
        );
    }

    @Secured({TRAINER})
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        service.delete(id);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Delete"
        );
    }

}
