<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Atrasos</title>
</head>
<body>
    <h1>Atrasos</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Data</th>
                <th>Horas Atrasadas</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="atraso" items="${atrasos}">
                <tr>
                    <td>${atraso.data}</td>
                    <td>${atraso.horasAtrasadas}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
