package com.timetracker.tracker.converters;

import jakarta.persistence.AttributeConverter;

import java.time.Duration;
import java.util.Objects;

/**
 * Attribute Converter for converting Duration objects to Long values for database storage *
 * and vice versa.
 */
public class DurationConverter implements AttributeConverter<Duration, Long> {
    /**
     * Converts a Duration attribute to a Long value for database storage.
     *
     * @param attribute the entity attribute value to be converted.
     * @return representing the Duration attribute in minutes.
     */
    @Override
    public Long convertToDatabaseColumn(Duration attribute) {
        return Objects.requireNonNullElse(attribute.toMinutes(), null);
    }

    /**
     * Converts a Long value from the database to a Duration entity attribute.
     *
     * @param dbData the data from the database column to be
     *               converted.
     * @return the Duration entity attribute represented by the Long value in minutes.
     */
    @Override
    public Duration convertToEntityAttribute(Long dbData) {
        return Objects.requireNonNullElse(Duration.ofMinutes(dbData), null);
    }

}
