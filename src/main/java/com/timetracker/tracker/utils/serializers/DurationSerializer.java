package com.timetracker.tracker.utils.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.time.Duration;

import static com.timetracker.tracker.utils.Constants.*;

/**
 * Custom serializer for {@link Duration} objects to JSON format.
 * This serializer formats duration objects into a string representation.
 * using a notation that includes weeks (w), days (d), hours (h), and minutes (m).
 * For example, a duration of 10 days and 5 hours would be represented as "10d 5h".
 */
public class DurationSerializer extends JsonSerializer<Duration> {
    /**
     * Serializes a {@link Duration} object to a JSON string.
     *
     * @param duration    the duration object to serialize.
     * @param gen         the JSON generator used to write the JSON data.
     * @param serializers a provider that can be used to get serializers for other types.
     * @throws IOException if there is a problem writing to the JSON generator.
     */
    @Override
    public void serialize(Duration duration, JsonGenerator gen, com.fasterxml.jackson.databind.SerializerProvider serializers) throws IOException {
        long days = duration.toDays();
        long weeks = duration.toDays() / DAYS_IN_A_WEEK;
        long hours = duration.toHours() % HOURS_IN_A_DAY;
        long minutes = duration.toMinutes() % 60;

        StringBuilder durationString = new StringBuilder();
        if (weeks > 0) {
            durationString.append(days).append(WEEK_TOKEN + StringUtils.SPACE);
        }
        if (days > 0) {
            durationString.append(days).append(DAY_TOKEN + StringUtils.SPACE);
        }
        if (hours > 0) {
            durationString.append(hours).append(HOUR_TOKEN + StringUtils.SPACE);
        }
        if (minutes > 0) {
            durationString.append(minutes).append(MINUTES_TOKEN + StringUtils.SPACE);
        }

        gen.writeString(durationString.toString().trim());
    }
}