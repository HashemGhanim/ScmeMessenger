package com.scme.messenger.model.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigInteger;

@Converter(autoApply = true)
public class BigIntegerStringConverter implements AttributeConverter<BigInteger , String> {

    @Override
    public String convertToDatabaseColumn(BigInteger attribute) {
        if (attribute != null) {
            return attribute.toString();
        } else {
            return null;
        }
    }

    @Override
    public BigInteger convertToEntityAttribute(String dbData) {
        if (dbData != null && !dbData.isEmpty()) {
            return new BigInteger(dbData);
        } else {
            return null;
        }
    }
}
