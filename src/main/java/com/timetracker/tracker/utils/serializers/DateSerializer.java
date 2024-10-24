package com.timetracker.tracker.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateSerializer extends JsonSerializer<Date> {
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void serialize(Date date, JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {
        gen.writeString(sdf.format(date));
    }
}