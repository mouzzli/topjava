package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(MEAL_USER_ID, USER_ID);
        assertMatch(meal, USER_MEAL_1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void getNotBelong() {
       assertThrows(NotFoundException.class, () -> service.get(MEAL_USER_ID + 3, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ADMIN_ID, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ADMIN_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND_MEAL, USER_ID));
    }

    @Test
    public void deleteNotBelong() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ADMIN_ID + 2, USER_ID));
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, user_meals);
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> filteredMeal = service.getBetweenInclusive(LocalDate.of(2020, 1, 30),
                LocalDate.of(2020, 1, 30), ADMIN_ID);
        assertMatch(filteredMeal, admin_meals_from_30th);
    }

    @Test
    public void update() {
        Meal updated = MealTestData.getUpdated(MEAL_ADMIN_ID + 1);
        service.update(updated, ADMIN_ID);
        assertMatch(service.get(MEAL_ADMIN_ID + 1, ADMIN_ID), updated);
    }

    @Test
    public void updateNotBelong() {
        Meal updated = MealTestData.getUpdated(MEAL_ADMIN_ID + 1);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void updateNotFound() {
        Meal updated = MealTestData.getUpdated(NOT_FOUND_MEAL);
        assertThrows(NotFoundException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertMatch(created, newMeal);
    }

    @Test
    public void duplicateDateCreate() {
        Meal duplicateMeal = new Meal(ADMIN_MEAL_3);
        duplicateMeal.setId(null);
        assertThrows(DataAccessException.class, () -> service.create(duplicateMeal, ADMIN_ID));
    }
}