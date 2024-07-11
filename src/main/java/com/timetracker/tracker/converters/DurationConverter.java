package com.timetracker.tracker.converters;

import jakarta.persistence.AttributeConverter;
import java.time.Duration;
import java.util.Objects;

public class DurationConverter implements AttributeConverter<Duration, Long> {
    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        return Objects.requireNonNullElse(attribute.toMinutes(), null);
    }

    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return Objects.requireNonNullElse(Duration.ofMinutes(dbData), null);
    }

}
