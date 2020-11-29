package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/meals")
public class MealUiController extends AbstractMealController {

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                       @RequestParam String description,
                       @RequestParam int calories) {
        super.create(new Meal(dateTime, description, calories));
    }


}
