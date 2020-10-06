package ru.javawebinar.topjava.dao;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDaoMemoryRepo implements MealsDao {
    private static final AtomicInteger id = new AtomicInteger(1);

    private static final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    static {
        meals.put(id.get(), new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.put(id.get(), new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.put(id.get(), new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.put(id.get(), new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.put(id.get(), new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.put(id.get(), new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.put(id.get(), new Meal(id.getAndIncrement(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    @Override
    public Meal create(Meal meal) {
        int idSetter = id.getAndIncrement();
        meal.setId(idSetter);
        meals.put(idSetter, meal);
        return meal;
    }

    @Override
    public void delete(int mealId) {
        meals.remove(mealId);
    }

    @Override
    public Meal update(Meal meal) {
        if (meals.containsKey(meal.getId())) {
            meals.put(meal.getId(), meal);
            return meal;
        } else {
            return null;
        }
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getById(int mealId) {
        return meals.get(mealId);
    }
}
