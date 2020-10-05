package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MealsDaoMemoryRepo;
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
    private MealsDao mealsDao;
    private static final String LIST_MEALS = "meals.jsp";
    private static final String INSERT_OR_EDIT = "mealsEdit.jsp";

    @Override
    public void init() throws ServletException {
        mealsDao = new MealsDaoMemoryRepo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        request.setCharacterEncoding("UTF-8");

        String forward;
        String action = request.getParameter("action");

        action = action == null? "": action;

        switch (action) {
            case "delete":
                log.debug("delete meal");
                int mealId = parseIntFromReqParam(request, "id");
                mealsDao.delete(mealId);
                response.sendRedirect("meals");
                return;
            case "edit":
                log.debug("forward to mealsEdit.jsp for update meal");
                mealId = parseIntFromReqParam(request, "id");
                forward = INSERT_OR_EDIT;
                Meal meal = mealsDao.getById(mealId);
                request.setAttribute("meal", meal);
                break;
            case "newMeal":
                log.debug("forward to mealsEdit.jsp for create meal");
                forward = INSERT_OR_EDIT;
                break;
            default:
                forward = LIST_MEALS;
                request.setAttribute("mealsTo", getMealsTo());
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = parseIntFromReqParam(request, "calories");
        String mealId = request.getParameter("id");

        if (mealId == null || mealId.isEmpty()) {
            log.debug("add meal");
            mealsDao.create(new Meal(dateTime, description, calories));
        } else {
            log.debug("update meal");
            mealsDao.update(new Meal(Integer.parseInt(mealId), dateTime, description, calories));
        }

        response.sendRedirect("meals");
    }

    private List<MealTo> getMealsTo() {
        return MealsUtil.filteredByStreams(mealsDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    private int parseIntFromReqParam(HttpServletRequest request, String param) {
        return Integer.parseInt(request.getParameter(param));
    }
}
