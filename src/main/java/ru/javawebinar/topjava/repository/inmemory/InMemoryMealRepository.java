package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_1;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_2;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, USER_1));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "user2 Завтрак", 500), USER_2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "user2 Обед", 1000), USER_2);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "user2 Ужин", 500), USER_2);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, k -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            log.info("save new Meal {} by userId {}", meal, userId);
            meals.put(meal.getId(), meal);
            return meal;
        }
        log.info("update {} by userId {}", meal, userId);
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> oldMeal = meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete id {}  by userId {}", id, userId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get id {}  by userId {}", id, userId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll by userId {}", userId);
        return getAllSortedAndFilteredByPredicate(userId, meal -> true);
    }

    @Override
    public List<Meal> getAllWithFilter(int userId, LocalDate startDate, LocalDate endDate) {
        log.info("getAllWithFilter by userId {}", userId);
        LocalDate endDateInside = !endDate.equals(LocalDate.MAX) ? endDate.plusDays(1) : endDate;
        return getAllSortedAndFilteredByPredicate(userId, meal -> DateTimeUtil.isBetweenHalfOpen(meal.getDate(), startDate, endDateInside));
    }

    private List<Meal> getAllSortedAndFilteredByPredicate(int userId, Predicate<Meal> filter) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? Collections.emptyList() : meals.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

