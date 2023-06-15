package com.example.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum UserRole {

    MEMBER("MEMBER"), ADMIN("ADMIN");

    private static final Map<String, UserRole> stringToTypeMap = new HashMap<>();
    private String text;

    static {
        for (UserRole status : UserRole.values()) {
            stringToTypeMap.put(status.text, status);
        }
    }

    public static UserRole fromText(String text) {
        return stringToTypeMap.getOrDefault(text, null);
    }

}
