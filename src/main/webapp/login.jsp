<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Registro de Ponto</title>
    <link rel="stylesheet" href="style.css">    
</head>
<body>
    <%
        String button = request.getParameter("button");

        if ("Recursos Humanos".equals(button)) {
            if ("senha123".equals(request.getParameter("password"))) {
                response.sendRedirect("horarioTrabalho.jsp");
            } else {
    %>
                <h1>Registro de Ponto - Recursos Humanos</h1>                
                <form method="post" action="login.jsp">
                    <input type="hidden" name="button" value="Recursos Humanos">
                    <label for="password">Senha:</label>
                    <input type="password" id="password" name="password">
                    <input type="submit" value="Enviar">
                </form>
    <%
            }
        } else if ("Funcionários".equals(button)) {
            response.sendRedirect("controleDeHora.jsp");
        }
    %>
</body>
</html>
