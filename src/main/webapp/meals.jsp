<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>
<style type="text/css">
    .tg {
        border-collapse: collapse;
        border-spacing: 0;
        border-color: #ccc;
    }
    .tg td {
        padding: 15px;
        font-family: Arial, sans-serif;
        font-size: 14px;
        border-style: solid;
        border-width: 1px;
        overflow: hidden;
        word-break: normal;
        border-color: #ccc;
        background-color: #fff;
    }
    th {
        font-family: Arial, sans-serif;
        font-size: 14px;
        font-weight: normal;
        padding: 15px 10px;
        border-style: solid;
        border-width: 1px;
        overflow: hidden;
        word-break: normal;
        border-color: #ccc;
        color: #333;
        background-color: #f0f0f0;
    }
</style>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<a href="meals?action=newMeal"> Add Meal</a>
<table class="tg">
    <tbody>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <c:forEach var="mealTo" items="${mealsTo}">
        <tr style="color:${mealTo.excess ? 'Red' : 'Green'}">
            <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="date" />
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" /></td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
            <td><a href="meals?action=edit&id=<c:out value="${mealTo.id}"/>">Edit</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${mealTo.id}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
