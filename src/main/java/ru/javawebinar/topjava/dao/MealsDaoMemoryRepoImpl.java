package ru.javawebinar.topjava.dao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealsMemoryRepository;

import java.util.List;

public class MealsDaoMemoryRepoImpl implements MealsDao {
    private MealsMemoryRepository repository = new MealsMemoryRepository();

    @Override
    public void create(Meal meal) {
       repository.add(meal);
    }

    @Override
    public void delete(int mealId) {
        repository.delete(mealId);
    }

    @Override
    public void update(Meal meal) {
        repository.update(meal);
    }

    @Override
    public List<Meal> getAll() {
        return repository.getMeals();
    }

    @Override
    public Meal getById(int mealId) {
        return repository.getById(mealId);
    }
}
