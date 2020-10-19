package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_USER_ID = START_SEQ + 2;
    public static final int MEAL_ADMIN_ID = START_SEQ + 9;
    public static final int NOT_FOUND_MEAL = 11;

    public static final Meal userMeal1 = new Meal(MEAL_USER_ID, LocalDateTime.of(2020, 1, 30, 10, 0, 0), "Завтрак", 500);
    public static final Meal userMeal2 = new Meal(MEAL_USER_ID + 1, LocalDateTime.of(2020, 1, 30, 13, 0, 0), "Обед", 1000);
    public static final Meal userMeal3 = new Meal(MEAL_USER_ID + 2, LocalDateTime.of(2020, 1, 30, 20, 0, 0), "Ужин", 500);
    public static final Meal userMeal4 = new Meal(MEAL_USER_ID + 3, LocalDateTime.of(2020, 1, 31, 0, 0, 0), "Еда на граничное значение", 100);
    public static final Meal userMeal5 = new Meal(MEAL_USER_ID + 4, LocalDateTime.of(2020, 1, 31, 10, 0, 0), "Завтрак", 1000);
    public static final Meal userMeal6 = new Meal(MEAL_USER_ID + 5, LocalDateTime.of(2020, 1, 31, 13, 0, 0), "Обед", 500);
    public static final Meal userMeal7 = new Meal(MEAL_USER_ID + 6, LocalDateTime.of(2020, 1, 31, 20, 0, 0), "Ужин", 410);
    public static final Meal adminMeal1 = new Meal(MEAL_ADMIN_ID, LocalDateTime.of(2020, 1, 30, 9, 0, 0), "Завтрак админ", 500);
    public static final Meal adminMeal2 = new Meal(MEAL_ADMIN_ID + 1, LocalDateTime.of(2020, 1, 30, 14, 0, 0), "Обед админ", 1000);
    public static final Meal adminMeal3 = new Meal(MEAL_ADMIN_ID + 2, LocalDateTime.of(2020, 1, 30, 19, 0, 0), "Ужин админ", 500);
    public static final Meal adminMeal4 = new Meal(MEAL_ADMIN_ID + 3, LocalDateTime.of(2020, 1, 31, 9, 30, 0), "Завтрак админ", 800);
    public static final Meal adminMeal5 = new Meal(MEAL_ADMIN_ID + 4, LocalDateTime.of(2020, 1, 31, 14, 10, 0), "Обед админ", 700);
    public static final Meal adminMeal6 = new Meal(MEAL_ADMIN_ID + 5, LocalDateTime.of(2020, 1, 31, 20, 0, 0), "Ужин админ", 600);

    public static List<Meal> userMeals = Arrays.asList(userMeal7, userMeal6, userMeal5, userMeal4, userMeal3, userMeal2, userMeal1);
    public static List<Meal> adminMealsFrom30th = Arrays.asList(adminMeal3, adminMeal2, adminMeal1);
    public static List<Meal> adminMeals = Arrays.asList(adminMeal6, adminMeal5, adminMeal4, adminMeal3, adminMeal2, adminMeal1);

    public static final Meal meal = new Meal(LocalDateTime.of(2020, 9, 1, 10, 0, 0), "Завтрак create", 430);

    public static Meal getUpdated(int mealId) {
        Meal updated = new Meal(meal);
        updated.setId(mealId);
        updated.setDescription("Обновлённая еда");
        return updated;
    }

    public static Meal getNew() {
        Meal newMeal = new Meal(meal);
        meal.setDescription("Новая еда");
        return newMeal;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(List<Meal> actual, List<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
