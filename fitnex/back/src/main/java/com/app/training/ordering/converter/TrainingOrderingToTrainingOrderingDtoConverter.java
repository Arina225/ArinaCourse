package com.app.training.ordering.converter;

import com.app.training.converter.TrainingToTrainingDtoConverter;
import com.app.training.ordering.TrainingOrdering;
import com.app.training.ordering.TrainingOrderingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrainingOrderingToTrainingOrderingDtoConverter implements Converter<TrainingOrdering, TrainingOrderingDto> {

    private final TrainingToTrainingDtoConverter trainingToTrainingDtoConverter;

    @Override
    public TrainingOrderingDto convert(TrainingOrdering source) {
        return new TrainingOrderingDto(
                source.getId(),

                source.getPrice(),

                source.getStatus().name(),
                source.getStatus().getName(),

                source.getTraining().getTrainer().getProfile().getFio(),

                source.getClient().getProfile().getFio(),

                trainingToTrainingDtoConverter.convert(source.getTraining())
        );
    }
}
