<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Events</title>
</head>
<body>
    <h2>${message}</h2>
    <h2>이벤트 목록</h2>
    <table>
        <tr><th>이름</th><th>시작 시간</th></tr>
        <c:forEach items="${events}" var="event">
        <tr>
            <td>${event.name}</td>
            <td>${event.starts}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>