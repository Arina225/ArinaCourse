package com.app.training.ordering;

import com.app.training.TrainingDto;

public record TrainingOrderingDto(
        Long id,

        float price,

        String status,
        String statusName,

        String trainerFio,

        String clientFio,

        TrainingDto training
) {
}
