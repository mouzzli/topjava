package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T lt, T startTime, T endTime) {
        if (lt instanceof LocalTime) {
            startTime = startTime == null ? (T) LocalTime.MIN : startTime;
            endTime = endTime == null ? (T) LocalTime.MAX : endTime;
        }
        if (lt instanceof LocalDate) {
            startTime = startTime == null ? (T) LocalDate.MIN : startTime;
            endTime = endTime == null ? (T) LocalDate.MAX : endTime;
        }

        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

