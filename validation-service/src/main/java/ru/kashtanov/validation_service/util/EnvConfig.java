package ru.kashtanov.validation_service.util;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;


public class EnvConfig {

    private static Dotenv dotenv = Dotenv.load();
    public static String get(String key) {
        return dotenv.get(key);
    }

    public static String get(String key, String defaultValue) {
        return dotenv.get(key, defaultValue);
    }
}
