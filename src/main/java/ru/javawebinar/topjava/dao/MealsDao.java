package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDao {
    void create(Meal meal);

    void delete(int id);

    void update(Meal meal);

    List<Meal> getAll();

    Meal getById(int mealId);
}
