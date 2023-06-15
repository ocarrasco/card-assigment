package com.example.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum SortAttribute {

    NAME("name"), COLOR("color"), STATUS("status"), CREATION_DATE("createdAt");

    private static Map<String, SortAttribute> stringToTypeMap = new HashMap<>();
    private final String attribute;

    static {
        for (SortAttribute item : SortAttribute.values()) {
            stringToTypeMap.put(item.getAttribute(), item);
        }
    }

    public static SortAttribute fromAttribute(String key) {
        return stringToTypeMap.getOrDefault(key, null);
    }

}
