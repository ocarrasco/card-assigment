package com.example.entities.enums.converters;

import com.example.entities.enums.UserRole;

import javax.persistence.AttributeConverter;

public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole userRole) {
        return userRole.name();
    }

    @Override
    public UserRole convertToEntityAttribute(String text) {
        return UserRole.fromText(text);
    }

}
