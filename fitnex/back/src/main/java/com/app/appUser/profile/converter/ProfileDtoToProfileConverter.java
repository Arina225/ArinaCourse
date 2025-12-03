package com.app.appUser.profile.converter;

import com.app.appUser.profile.Profile;
import com.app.appUser.profile.ProfileDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileDtoToProfileConverter implements Converter<ProfileDto, Profile> {
    @Override
    public Profile convert(ProfileDto source) {
        return new Profile(
                source.fio(),
                source.tel(),
                source.date(),
                source.educate(),
                source.speciality()
        );
    }
}
