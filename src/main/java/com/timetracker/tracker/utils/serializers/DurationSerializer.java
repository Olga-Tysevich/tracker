package com.timetracker.tracker.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;
import java.time.Duration;

public class DurationSerializer extends JsonSerializer<Duration> {
    @Override
    public void serialize(Duration duration, JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {
        long days = duration.toDays();
        long weeks = duration.toDays() / 7;
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        StringBuilder durationString = new StringBuilder();
        if (weeks > 0) {
            durationString.append(days).append("w ");
        }
        if (days > 0) {
            durationString.append(days).append("d ");
        }
        if (hours > 0) {
            durationString.append(hours).append("h ");
        }
        if (minutes > 0) {
            durationString.append(minutes).append("m");
        }

        gen.writeString(durationString.toString().trim());
    }
}