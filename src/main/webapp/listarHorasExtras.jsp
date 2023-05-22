<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Horas Extras</title>
</head>
<body>
    <h1>Horas Extras</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Data</th>
                <th>Horas Extras</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="horaExtra" items="${horasExtras}">
                <tr>
                    <td>${horaExtra.data}</td>
                    <td>${horaExtra.horasExtras}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
