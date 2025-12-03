package com.app.training;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TrainingDto(
        Long id,

        @Size(min = 1, max = 255, message = "name is required length 1-255")
        @NotEmpty(message = "name is required")
        String name,
        @Size(min = 1, max = 255, message = "date is required length 1-255")
        @NotEmpty(message = "date is required")
        String date,
        @Min(value = 1, message = "duration is required min 1")
        @Max(value = 12, message = "duration is required max 12")
        int duration,
        @Min(value = 1, message = "places is required min 1")
        @Max(value = 10, message = "places is required max 10")
        int places,
        @Min(value = 0, message = "price is required min 0.01")
        @Max(value = 1000000, message = "price is required min 1000000")
        float price,

        int free,

        List<Long> orderingsClientsId
) {
}
