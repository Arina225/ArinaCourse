package com.app.appUser;

import com.app.appUser.converter.UserDtoToUserConverter;
import com.app.appUser.converter.UserToUserDtoConverter;
import com.app.system.Result;
import com.app.system.StatusCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.app.util.Global.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserToUserDtoConverter toDtoConverter;
    private final UserDtoToUserConverter toUserConverter;

    @Secured({ADMIN})
    @GetMapping("/all")
    public Result findAll() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find All Users Success",
                service.findAll().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @GetMapping("/trainers")
    public Result findAllTrainers() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find All Trainers Success",
                service.findAllTrainers().stream().map(toDtoConverter::convert).collect(Collectors.toList())
        );
    }

    @Secured({ADMIN, TRAINER, CLIENT})
    @GetMapping
    public Result find() {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find User Success",
                toDtoConverter.convert(service.getCurrentUser())
        );
    }

    @Secured({ADMIN})
    @GetMapping("/{userId}")
    public Result findById(@PathVariable String userId) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Find One User Success",
                toDtoConverter.convert(service.findById(userId))
        );
    }

    @PostMapping
    public Result addUser(@Valid @RequestBody AppUser newUser) {
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Add Success",
                toDtoConverter.convert(service.save(newUser))
        );
    }

    @Secured({ADMIN, TRAINER, CLIENT})
    @PutMapping
    public Result update(@Valid @RequestBody UserDto updateDto) {
        AppUser update = toUserConverter.convert(updateDto);
        AppUser updated = service.update(update);
        UserDto updatedDto = toDtoConverter.convert(updated);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Update Success",
                updatedDto
        );
    }

    @Secured({ADMIN})
    @PatchMapping("/{userId}/role")
    public Result updateRole(@PathVariable String userId, @RequestParam String role) {
        AppUser updated = service.updateRole(userId, role);
        UserDto updatedDto = toDtoConverter.convert(updated);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Update Role Success",
                updatedDto
        );
    }

    @Secured({ADMIN})
    @DeleteMapping("/{userId}")
    public Result delete(@PathVariable String userId) {
        service.deleteById(userId);
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Delete Success"
        );
    }
}
