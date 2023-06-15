package com.example.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum CardStatus {

    TO_DO(1), IN_PROGRESS(2), DONE(3);

    private static final Map<Integer, CardStatus> intToTypeMap = new HashMap<>();
    private final int index;

    static {
        for (var item : CardStatus.values()) {
            intToTypeMap.put(item.index, item);
        }
    }

    public static CardStatus fromIndex(Integer index) {
        return intToTypeMap.getOrDefault(index, null);
    }

}
