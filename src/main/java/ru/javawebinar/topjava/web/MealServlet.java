package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MealsDaoMemoryRepoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealsDao mealsDao;
    private static final String LIST_MEALS = "meals.jsp";
    private static final String INSERT_OR_EDIT = "mealsEdit.jsp";

    public MealServlet() {
        super();
        this.mealsDao = new MealsDaoMemoryRepoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        request.setCharacterEncoding("UTF-8");

        String forward;
        String action = request.getParameter("action");

        if (action == null) {
            forward = LIST_MEALS;
            request.setAttribute("mealsTo", getMealsTo());
        } else if (action.equalsIgnoreCase("delete")) {
            log.debug("delete meal");
            int mealId = Integer.parseInt(request.getParameter("id"));
            mealsDao.delete(mealId);
            response.sendRedirect("meals");
            request.setAttribute("mealsTo", getMealsTo());
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("id"));
            forward = INSERT_OR_EDIT;
            Meal meal = mealsDao.getById(mealId);
            request.setAttribute("meal", meal);
        } else {
            forward = INSERT_OR_EDIT;
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        String mealId = request.getParameter("id");

        if (mealId == null || mealId.isEmpty()) {
            log.debug("add meal");
            mealsDao.create(new Meal(dateTime, description, calories));
        } else {
            log.debug("update meal");
            mealsDao.update(new Meal(Integer.parseInt(mealId), dateTime, description, calories));
        }

        request.setAttribute("mealsTo", getMealsTo());
        request.getRequestDispatcher(LIST_MEALS).forward(request, response);
    }

    private List<MealTo> getMealsTo() {
        return MealsUtil.filteredByStreams(mealsDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
