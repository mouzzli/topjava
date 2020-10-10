package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get {} by userId {}", id, userId );
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete {} by userId {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} by userId {}", meal, userId);
        service.update(meal, userId);
    }

    public void create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create {} by userId {}", meal, userId);
        service.create(meal, userId);
    }

    public List<Meal> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll  by userId {}", userId);
        return service.getAll(userId);
    }

    public List<Meal> getAllWithFilter(LocalDate startTime, LocalDate endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getAllWithFilter by userId {}", userId);
        return service.getAllWithFilter(userId, startTime, endTime);
    }
}