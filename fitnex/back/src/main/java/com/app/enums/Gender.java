package com.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Gender {
    MAN("Мужчина"),
    WOMAN("Женщина"),
    ;

    private final String name;
}

