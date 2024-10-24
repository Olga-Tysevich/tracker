package com.timetracker.tracker.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.timetracker.tracker.utils.Constants;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;


/**
 * Custom serializer for {@link Date} objects to JSON format.
 * This serializer formats date objects into a string representation.
 * using the pattern "yyyy-MM-dd".
 */
public class DateSerializer extends JsonSerializer<Date> {
    private final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_PATTERN);

    /**
     * Serializes a {@link Date} object to a JSON string.
     *
     * @param date        the date object to serialize.
     * @param gen         the JSON generator used to write the JSON data.
     * @param serializers a provider that can be used to get serializers for other types.
     * @throws IOException if there is a problem writing to the JSON generator.
     */
    @Override
    public void serialize(Date date, JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {
        gen.writeString(sdf.format(date));
    }
}