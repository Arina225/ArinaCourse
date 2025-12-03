package com.app.appUser.profile.converter;

import com.app.appUser.profile.Profile;
import com.app.appUser.profile.ProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToProfileDtoConverter implements Converter<Profile, ProfileDto> {
    @Override
    public ProfileDto convert(Profile source) {
        return new ProfileDto(
                source.getId(),

                source.getFio(),
                source.getTel(),

                source.getDate(),

                source.getEducate(),
                source.getSpeciality(),

                source.getImg(),

                source.getGender().name(),
                source.getGender().getName(),

                source.getAgeCategory().name(),
                source.getAgeCategory().getName()
        );
    }
}
