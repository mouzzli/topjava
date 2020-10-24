package ru.javawebinar.topjava;

import java.util.concurrent.TimeUnit;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

public class JUnitTestTimeInfo extends Stopwatch {

    public final static StringBuilder result = new StringBuilder("\n\n");

    @Override
    protected void finished(long nanos, Description description) {
        String testName = description.getMethodName();
        String testInfo = String.format("Test name: %s - %d ms\n", testName, TimeUnit.NANOSECONDS.toMillis(nanos));
        System.out.println(testInfo);
        result.append(testInfo);
    }
}