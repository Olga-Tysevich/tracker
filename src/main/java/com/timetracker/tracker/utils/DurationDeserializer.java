package com.timetracker.tracker.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;

public class DurationDeserializer extends JsonDeserializer<Duration> {

    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        long minutes = 0;
        String source = p.getText();

        char[] tokens = {'w', 'd', 'h', 'm'};
        int lastPos = 0;
        for (char token : tokens) {
            int currentPos = source.indexOf(token);
            int value = Integer.parseInt(source.substring(lastPos, currentPos).trim());

            switch (token) {
                case 'w' -> minutes += value * 7 * 24 * 60L;
                case 'd' -> minutes += value * 24 * 60L;
                case 'h' -> minutes += value * 60L;
                case 'm' -> minutes += value;
            }
            lastPos = currentPos + 1;
        }

        return Duration.ofMinutes(minutes);
    }
}
