package com.app.training.converter;

import com.app.training.Training;
import com.app.training.TrainingDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TrainingDtoToTrainingConverter implements Converter<TrainingDto, Training> {
    @Override
    public Training convert(TrainingDto source) {
        return new Training(
                source.name(),
                source.date(),
                source.duration(),
                source.places(),
                source.price()
        );
    }
}
