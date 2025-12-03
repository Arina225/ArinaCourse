package com.app.appUser.converter;

import com.app.appUser.AppUser;
import com.app.appUser.UserDto;
import com.app.appUser.profile.converter.ProfileToProfileDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToUserDtoConverter implements Converter<AppUser, UserDto> {

    private final ProfileToProfileDtoConverter profileToProfileDtoConverter;

    @Override
    public UserDto convert(AppUser source) {
        return new UserDto(
                source.getId(),

                source.getUsername(),
                source.getRole().name(),

                profileToProfileDtoConverter.convert(source.getProfile())
        );
    }
}
