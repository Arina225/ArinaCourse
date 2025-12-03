package com.app.appUser.profile;

import com.app.appUser.profile.converter.ProfileDtoToProfileConverter;
import com.app.appUser.profile.converter.ProfileToProfileDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.app.util.Global.TRAINER;
import static com.app.util.Global.CLIENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/profiles")
public class ProfileController {

    private final ProfileService service;
    private final ProfileToProfileDtoConverter toDtoConverter;
    private final ProfileDtoToProfileConverter toConverter;

    @Secured({TRAINER, CLIENT})
    @PutMapping
    public Result update(@Valid @RequestBody ProfileDto updateDto, @RequestParam String gender, @RequestParam String ageCategory) {
        Profile update = toConverter.convert(updateDto);
        Profile updated = service.update(update, gender, ageCategory);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update",
                toDtoConverter.convert(updated)
        );
    }

    @Secured({TRAINER})
    @PatchMapping("/img")
    public Result updateImg(@RequestParam MultipartFile files) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Success Update Img",
                toDtoConverter.convert(service.updateImg(files))
        );
    }

}
