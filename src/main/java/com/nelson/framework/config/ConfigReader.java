package com.nelson.framework.config;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties PROPS = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) throw new RuntimeException("config.properties not found in src/main/resources");
            PROPS.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        String sys = System.getProperty(key); // allows -Dbrowser=firefox
        return (sys != null && !sys.isBlank()) ? sys : PROPS.getProperty(key);
    }

    public static int getInt(String key, int defaultValue) {
        String v = get(key);
        return (v == null || v.isBlank()) ? defaultValue : Integer.parseInt(v.trim());
    }

    public static boolean getBool(String key, boolean defaultValue) {
        String v = get(key);
        return (v == null || v.isBlank()) ? defaultValue : Boolean.parseBoolean(v.trim());
    }
}

