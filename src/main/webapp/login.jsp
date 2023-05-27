<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Registro de Ponto</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<jsp:include page="cabecalho.jsp" />
	<div class="container">
		<%
		String button = request.getParameter("button");

		if ("Recursos Humanos".equals(button)) {
			if ("senha123".equals(request.getParameter("password"))) {
				response.sendRedirect("horarioTrabalho.jsp");
			} else {
		%>
		<h1 class="titulos">Registro de Ponto - Recursos Humanos</h1>
		<form method="post" action="login.jsp" class="titulos" onsubmit="return validateForm()">
			<input type="hidden" name="button" value="Recursos Humanos">
			<label>Senha:</label> <input type="password"
				id="password" name="password"> <input type="submit"
				value="Enviar">
			<p id="error-message" style="color: red; display: none;">
				Senha incorreta. Tente novamente.</p>
		</form>
		<%
		}
		} else if ("Funcionários".equals(button)) {
		response.sendRedirect("controleDeHora.jsp");
		}
		%>
	</div>
	<div class="voltar-container">
		<a href="index.jsp" class="btn-voltar">Voltar</a>
	</div>
	<script>
		function validateForm() {
			var password = document.getElementById("password").value;

			if (password !== "senha123") {
				document.getElementById("error-message").style.display = "block";	
				document.getElementById("password").value = "";
				return false; // Impede o envio do formulário
			}

			return true; // Permite o envio do formulário
		}
	</script>
	<jsp:include page="rodape.jsp" />
</body>
</html>