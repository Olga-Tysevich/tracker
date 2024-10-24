package com.timetracker.tracker.utils;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class DotenvLoader {
    public static void load() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JWT_ACCESS_KEY_SECRET", Objects.requireNonNull(dotenv.get("JWT_ACCESS_KEY_SECRET")));
        System.setProperty("JWT_ACCESS_KEY_EXPIRATION_TIME", Objects.requireNonNull(dotenv.get("JWT_ACCESS_KEY_EXPIRATION_TIME")));
        System.setProperty("JWT_REFRESH_KEY_SECRET", Objects.requireNonNull(dotenv.get("JWT_REFRESH_KEY_SECRET")));
        System.setProperty("JWT_REFRESH_KEY_EXPIRATION_TIME", Objects.requireNonNull(dotenv.get("JWT_REFRESH_KEY_EXPIRATION_TIME")));
        System.setProperty("TRACKER_DB_EPORT", Objects.requireNonNull(dotenv.get("TRACKER_DB_EPORT")));
        System.setProperty("TRACKER_DB_NAME", Objects.requireNonNull(dotenv.get("TRACKER_DB_NAME")));
        System.setProperty("TRACKER_DB_USER", Objects.requireNonNull(dotenv.get("TRACKER_DB_USER")));
        System.setProperty("TRACKER_DB_PASSWORD", Objects.requireNonNull(dotenv.get("TRACKER_DB_PASSWORD")));
    }
}
