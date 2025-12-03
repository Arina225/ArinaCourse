package com.app.appUser.profile;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record ProfileDto(
        Long id,

        @Size(min = 1, max = 255, message = "fio is required length 1-255")
        @NotEmpty(message = "fio is required")
        String fio,
        @Size(min = 1, max = 255, message = "tel is required length 1-255")
        @NotEmpty(message = "tel is required")
        String tel,

        @Size(min = 1, max = 255, message = "date is required length 1-255")
        @NotEmpty(message = "date is required")
        String date,

        @Size(min = 1, max = 255, message = "educate is required length 1-255")
        @NotEmpty(message = "educate is required")
        String educate,
        @Size(min = 1, max = 255, message = "speciality is required length 1-255")
        @NotEmpty(message = "speciality is required")
        String speciality,

        String img,

        String gender,
        String genderName,

        String ageCategory,
        String ageCategoryName
) {
}
