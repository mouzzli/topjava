<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meals Edit Or Add</title>
</head>
<style type="text/css">
    div.field {
        padding-bottom: 5px;
    }
    div.field label {
        display: block;
        float: left;
        width: 100px;
        height: 15px;
    }
    div.field input{
        width: 180px;
    }
    div.button{
        padding: 10px;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals Edit</h2>
<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" id="id" name="id" value="${meal.id}">

    <div class="field">
        <label for="date">Date: </label>
        <input id="date" name="date" type="datetime-local" value="${meal.dateTime}">
    </div>

    <div class="field">
        <label for="description">Description: </label>
        <input type="text" id="description" name="description" value="${meal.description}">
    </div>

    <div class="field">
        <label for="calories">Calories: </label>
        <input type="number" id="calories" name="calories" value="${meal.calories}">
    </div>

    <div class="button">
        <input type="submit" value="Save"/>
    </div>
</form>
</body>
</html>
