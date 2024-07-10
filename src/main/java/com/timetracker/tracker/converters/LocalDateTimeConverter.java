package com.timetracker.tracker.converters;

import jakarta.persistence.AttributeConverter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        return Objects.requireNonNullElse(Timestamp.valueOf(attribute), null);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return Objects.requireNonNullElse(dbData.toLocalDateTime(), null);
    }
}
