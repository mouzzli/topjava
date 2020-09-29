package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import static ru.javawebinar.topjava.util.TimeUtil.isBetweenHalfOpen;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        System.out.println("//////////Cycles/////////////////");
        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println("\n///////////Streams/////////////////");
        List<UserMealWithExcess> mealsToStream = (filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000));
        mealsToStream.forEach(System.out::println);

        System.out.println("\n///////////Optional cycle/////////////////");
        List<UserMealWithExcess> mealsToOptionsCycles = optionalFilteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
        mealsToOptionsCycles.forEach(System.out::println);

        System.out.println("\n///////////Optional stream/////////////////");
        List<UserMealWithExcess> optionalFilteredByStreams = optionalFilteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 0), 2000);
        optionalFilteredByStreams.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        meals.forEach(userMeal -> dayCalories.merge(getDate(userMeal), userMeal.getCalories(), Integer::sum));

        List<UserMealWithExcess> result = new ArrayList<>();
        meals.forEach(userMeal -> {
            if (isBetweenHalfOpen(getTime(userMeal), startTime, endTime)) {
                result.add(createUserMealWithExcess(userMeal,dayCalories.get(getDate(userMeal)) > caloriesPerDay));
            }
        });
        return result;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = meals.stream()
                .collect(Collectors.toMap(UserMealsUtil::getDate, UserMeal::getCalories, Integer::sum));

        return meals.stream()
                .filter(userMeal -> TimeUtil.isBetweenHalfOpen(getTime(userMeal), startTime, endTime))
                .map(userMeal -> createUserMealWithExcess(userMeal, dayCalories.get(getDate(userMeal)) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExcess> optionalFilteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        Map<LocalDate, AtomicBoolean> dayExcess = new HashMap<>();
        List<UserMealWithExcess> result = new ArrayList<>();

        for (UserMeal userMeal : meals) {
            dayCalories.merge(getDate(userMeal), userMeal.getCalories(), Integer::sum);

            if(dayExcess.putIfAbsent(getDate(userMeal), new AtomicBoolean(dayCalories.get(getDate(userMeal)) > caloriesPerDay)) != null){
                dayExcess.get(getDate(userMeal)).set(dayCalories.get(getDate(userMeal)) > caloriesPerDay);
            }

            if (isBetweenHalfOpen(getTime(userMeal), startTime, endTime)) {
                result.add(createUserMealWithExcess(userMeal, dayExcess.get(getDate(userMeal))));
            }
        }
        return result;
    }

    public static List<UserMealWithExcess> optionalFilteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        class SumExcessCollector implements Collector<UserMeal,List<UserMealWithExcess>, List<UserMealWithExcess>> {
            private final  Map<LocalDate, Integer> dayCalories = new HashMap<>();
            private final  Map<LocalDate, AtomicBoolean> dayExcess = new HashMap<>();

            @Override
            public Supplier<List<UserMealWithExcess>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<UserMealWithExcess>, UserMeal> accumulator() {
                return (result, userMeal) -> {
                    dayCalories.merge(getDate(userMeal), userMeal.getCalories(), Integer::sum);

                    if(dayExcess.putIfAbsent(getDate(userMeal), new AtomicBoolean(dayCalories.get(getDate(userMeal)) > caloriesPerDay)) != null){
                        dayExcess.get(getDate(userMeal)).set(dayCalories.get(getDate(userMeal)) > caloriesPerDay);
                    }

                    if (isBetweenHalfOpen(getTime(userMeal), startTime, endTime)) {
                        result.add(createUserMealWithExcess(userMeal, dayExcess.get(getDate(userMeal))));
                    }
                };
            }

            @Override
            public BinaryOperator<List<UserMealWithExcess>> combiner() {
                return (a, b) -> {
                    a.addAll(b);
                    return a;
                };
            }

            @Override
            public Function<List<UserMealWithExcess>, List<UserMealWithExcess>> finisher() {
                return  a -> a;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return EnumSet.of(Characteristics.UNORDERED);
            }
        }

        return meals.stream()
                .collect(new SumExcessCollector());
    }

    public static LocalDate getDate(UserMeal userDate) {
        return userDate.getDateTime().toLocalDate();
    }

    public static LocalTime getTime(UserMeal userTime) {
        return userTime.getDateTime().toLocalTime();
    }

    public static UserMealWithExcess createUserMealWithExcess(UserMeal userMeal, AtomicBoolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }

    public static UserMealWithExcess createUserMealWithExcess(UserMeal userMeal, boolean excess) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), excess);
    }
}
