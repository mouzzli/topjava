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

    public static final Meal USER_MEAL_1 = new Meal(MEAL_USER_ID, LocalDateTime.of(2020, 1, 30, 10, 0, 0), "Завтрак", 500);
    public static final Meal USER_MEAL_2 = new Meal(MEAL_USER_ID + 1, LocalDateTime.of(2020, 1, 30, 13, 0, 0), "Обед", 1000);
    public static final Meal USER_MEAL_3 = new Meal(MEAL_USER_ID + 2, LocalDateTime.of(2020, 1, 30, 20, 0, 0), "Ужин", 500);
    public static final Meal USER_MEAL_4 = new Meal(MEAL_USER_ID + 3, LocalDateTime.of(2020, 1, 31, 0, 0, 0), "Еда на граничное значение", 100);
    public static final Meal USER_MEAL_5 = new Meal(MEAL_USER_ID + 4, LocalDateTime.of(2020, 1, 31, 10, 0, 0), "Завтрак", 1000);
    public static final Meal USER_MEAL_6 = new Meal(MEAL_USER_ID + 5, LocalDateTime.of(2020, 1, 31, 13, 0, 0), "Обед", 500);
    public static final Meal USER_MEAL_7 = new Meal(MEAL_USER_ID + 6, LocalDateTime.of(2020, 1, 31, 20, 0, 0), "Ужин", 410);
    public static final Meal ADMIN_MEAL_1 = new Meal(MEAL_ADMIN_ID, LocalDateTime.of(2020, 1, 30, 9, 0, 0), "Завтрак админ", 500);
    public static final Meal ADMIN_MEAL_2 = new Meal(MEAL_ADMIN_ID + 1, LocalDateTime.of(2020, 1, 30, 14, 0, 0), "Обед админ", 1000);
    public static final Meal ADMIN_MEAL_3 = new Meal(MEAL_ADMIN_ID + 2, LocalDateTime.of(2020, 1, 30, 19, 0, 0), "Ужин админ", 500);

    public static List<Meal> user_meals = Arrays.asList(USER_MEAL_7, USER_MEAL_6, USER_MEAL_5, USER_MEAL_4, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);
    public static List<Meal> admin_meals_from_30th = Arrays.asList(ADMIN_MEAL_3, ADMIN_MEAL_2, ADMIN_MEAL_1);

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
