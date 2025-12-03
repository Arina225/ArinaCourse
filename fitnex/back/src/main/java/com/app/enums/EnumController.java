package com.app.enums;

import com.app.system.Result;
import com.app.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/enums")
public class EnumController {

    @GetMapping("/roles")
    public Result roles() {
        Map<String, String> res = new HashMap<>();
        for (Role i : Role.values()) res.put(i.name(), i.getName());
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Roles",
                res
        );
    }

    @GetMapping("/genders")
    public Result genders() {
        Map<String, String> res = new HashMap<>();
        for (Gender i : Gender.values()) res.put(i.name(), i.getName());
        return new Result(
                true,
                StatusCode.SUCCESS,
                "Genders",
                res
        );
    }

    @GetMapping("/ageCategories")
    public Result ageCategories() {
        Map<String, String> res = new HashMap<>();
        for (AgeCategory i : AgeCategory.values()) res.put(i.name(), i.getName());
        return new Result(
                true,
                StatusCode.SUCCESS,
                "AgeCategories",
                res
        );
    }

}
