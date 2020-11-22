package ru.javawebinar.topjava.util.formatter;

import org.springframework.core.convert.converter.Converter;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;

public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String s) {
        return DateTimeUtil.parseLocalDate(s);
    }
}