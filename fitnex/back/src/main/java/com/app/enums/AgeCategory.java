package com.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AgeCategory {
    AC18_20("18-20"),
    AC21_25("21-25"),
    AC26_30("26-30"),
    AC31_40("31-40"),
    AC41("41+"),
    ;

    private final String name;
}

