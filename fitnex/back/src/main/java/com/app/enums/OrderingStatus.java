package com.app.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderingStatus {
    WAITING("Ожидание"),
    CONFIRMED("Подтверждено"),
    REJECTED("Отклонено"),
    ;

    private final String name;
}

