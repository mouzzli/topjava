package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class ExecuteTimeTestUtil extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger("result");
    public final static StringBuilder results = new StringBuilder();


    @Override
    protected void finished(long nanos, Description description) {
        String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
        results.append(result);
        log.info(result + " ms\n");
    }

    public static void print() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }
}
