package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsMemoryRepository {
    private static final AtomicInteger id = new AtomicInteger(1);
    private static final List<Meal> meals = new CopyOnWriteArrayList<>(Arrays.asList(
            new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));

    public List<Meal> getMeals() {
        return meals;
    }

    public void add(Meal meal) {
        meal.setId(id.getAndIncrement());
        meals.add(meal);
    }

    public void delete(int mealId) {
        meals.remove(getById(mealId));
    }

    public Meal getById(int mealId) {
        for (Meal meal : meals) {
            if (meal.getId() == mealId) {
                return meal;
            }
        }
        return null;
    }

    public void update(Meal newMeal) {
        if (meals.remove(getById(newMeal.getId()))) {
            meals.add(newMeal);
        }
    }
}
