package com.app.training.ordering;

import com.app.system.Result;
import com.app.system.StatusCode;
import com.app.training.ordering.converter.TrainingOrderingToTrainingOrderingDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.CLIENT;
import static com.app.util.Global.TRAINER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainings/orderings")
public class TrainingOrderingController {

    private final TrainingOrderingService service;
    private final TrainingOrderingToTrainingOrderingDtoConverter toDtoConverter;

    @Secured({TRAINER})
    @GetMapping
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({CLIENT})
    @GetMapping("/my")
    public Result findAllMy() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Find All My",
                service.findAllMy().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({CLIENT})
    @PostMapping
    public Result save(@RequestParam String trainingId) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Save",
                toDtoConverter.convert(service.save(trainingId))
        );
    }

    @Secured({TRAINER})
    @GetMapping("/{id}/confirmed")
    public Result confirmed(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Confirmed",
                toDtoConverter.convert(service.confirmed(id))
        );
    }

    @Secured({TRAINER})
    @GetMapping("/{id}/rejected")
    public Result rejected(@PathVariable String id) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Rejected",
                toDtoConverter.convert(service.rejected(id))
        );
    }

}
