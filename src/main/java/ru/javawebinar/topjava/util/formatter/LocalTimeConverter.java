package ru.javawebinar.topjava.util.formatter;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalTime;

public class LocalTimeConverter implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(String s) {
        return DateTimeUtil.parseLocalTime(s);
    }
}