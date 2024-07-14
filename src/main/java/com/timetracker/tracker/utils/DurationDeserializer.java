package com.timetracker.tracker.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Duration;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * The JSON deserializer for converting a string representation of a duration into a Duration object.
 */
public class DurationDeserializer extends JsonDeserializer<Duration> {

    /**
     * Deserializes a JSON string into a Duration object.
     * <p>
     * The string should be in the format of "X weeks Y days Z hours A minutes".
     *
     * @param p    Parsed used for reading JSON content
     * @param ctxt Context that can be used to access information about
     *             this deserialization activity.
     * @return a Duration object representing the parsed duration
     * @throws IOException if an I/O error occurs during deserialization
     */
    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        long minutes = 0;
        String source = p.getText();

        int lastPos = 0;
        for (char token : DURATION_TOKENS) {
            int currentPos = source.indexOf(token);
            int value = Integer.parseInt(source.substring(lastPos, currentPos).trim());

            switch (token) {
                case WEEK_TOKEN -> minutes += value * DAYS_IN_A_WEEK * HOURS_IN_A_DAY * MINUTES_IN_A_HOURS;
                case DAY_TOKEN -> minutes += value * HOURS_IN_A_DAY * MINUTES_IN_A_HOURS;
                case HOUR_TOKEN -> minutes += value * MINUTES_IN_A_HOURS;
                case MINUTES_TOKEN -> minutes += value;
            }
            lastPos = currentPos + 1;
        }

        return Duration.ofMinutes(minutes);
    }
}
