package com.app.training.converter;

import com.app.training.Training;
import com.app.training.TrainingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TrainingToTrainingDtoConverter implements Converter<Training, TrainingDto> {
    @Override
    public TrainingDto convert(Training source) {
        return new TrainingDto(
                source.getId(),

                source.getName(),
                source.getDate(),
                source.getDuration(),
                source.getPlaces(),
                source.getPrice(),

                source.getFree(),

                source.getOrderingsClientsId()
        );
    }
}
