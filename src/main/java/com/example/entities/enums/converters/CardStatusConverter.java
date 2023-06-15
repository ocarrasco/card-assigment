package com.example.entities.enums.converters;

import com.example.entities.enums.CardStatus;

import javax.persistence.AttributeConverter;

public class CardStatusConverter implements AttributeConverter<CardStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CardStatus attribute) {
        return attribute.getIndex();
    }

    @Override
    public CardStatus convertToEntityAttribute(Integer dbData) {
        return CardStatus.fromIndex(dbData);
    }

}
