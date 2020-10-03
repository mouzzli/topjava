<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
</head>

<style>
    table, th, td {
        border: 1px solid #000000;
        border-collapse: collapse;
    }
    th, td {
        padding: 10px;
    }
</style>

<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <tbody>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <jsp:useBean id="mealsTo" scope="request" type="java.util.List"/>
    <c:forEach var="mealTo" items="${mealsTo}">
        <tr style="color:${mealTo.excess ? 'Red' : 'Green'}; margin: 2px">
            <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="date" />
            <td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parsedDateTime}" /></td>
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
