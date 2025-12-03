package com.app.appUser.profile;

import com.app.appUser.UserService;
import com.app.enums.AgeCategory;
import com.app.enums.Gender;
import com.app.system.exception.BadRequestException;
import com.app.util.Global;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository repository;
    private final UserService userService;

    public Profile find() {
        return userService.getCurrentUser().getProfile();
    }

    public Profile update(Profile update, String gender, String ageCategory) {
        Profile old = find();

        old.update(update);

        try {
            old.setGender(Gender.valueOf(gender));
            old.setAgeCategory(AgeCategory.valueOf(ageCategory));
        } catch (Exception e) {
            throw new BadRequestException("Некорректные данные");
        }

        return repository.save(old);
    }

    public Profile updateImg(MultipartFile img) {
        Profile profile = find();

        try {
            profile.setImg(Global.saveFile(img, "profile"));
        } catch (IOException e) {
            throw new BadRequestException("Некорректное изображение");
        }

        return repository.save(profile);
    }

}
