package com.app.appUser;

import com.app.appUser.profile.ProfileDto;

public record UserDto(
        Long id,

        String username,
        String role,

        ProfileDto profile
) {
}
