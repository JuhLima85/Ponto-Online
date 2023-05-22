<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listar Horários de Trabalho</title>
</head>
<body>
    <h1>Horários de Trabalho</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Entrada</th>
                <th>Intervalo (Início)</th>
                <th>Intervalo (Fim)</th>
                <th>Saída</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="horario" items="${horarios}">
                <tr>
                    <td>${horario.entrada}</td>
                    <td>${horario.intervaloInicio}</td>
                    <td>${horario.intervaloFim}</td>
                    <td>${horario.saida}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
