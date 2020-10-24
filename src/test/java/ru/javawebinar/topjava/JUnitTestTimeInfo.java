package ru.javawebinar.topjava;

import java.util.concurrent.TimeUnit;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JUnitTestTimeInfo extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(JUnitTestTimeInfo.class);

    public final static StringBuilder result;

    static {
        result = new StringBuilder("\n");
        result.append("===============Test Result==============\n");
    }

    @Override
    protected void finished(long nanos, Description description) {
        String testName = description.getMethodName();
        String testInfo = String.format("%-25s  %10d ms\n", testName, TimeUnit.NANOSECONDS.toMillis(nanos));
        log.info(testInfo);
        result.append(testInfo);
    }
}