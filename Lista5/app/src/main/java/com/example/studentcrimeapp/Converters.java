package com.example.studentcrimeapp;

import androidx.room.TypeConverter;

import java.util.Date;
import java.util.UUID;

public class Converters {
    @TypeConverter
    public static String fromUUID(UUID uuid) {
        return uuid.toString();
    }

    @TypeConverter
    public static UUID uuidFromString(String string) {
        return UUID.fromString(string);
    }

    @TypeConverter
    public static long timestampFromDate(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public static Date dateFromTimestamp(long timestamp) {
        return new Date(timestamp);
    }
}
