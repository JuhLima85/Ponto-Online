<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ponto Online</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/login.css">
</head>
<body>
	<jsp:include page="cabecalho.jsp" />
	<div class="container">
		<%
		String button = request.getParameter("button");

		if ("Recursos Humanos".equals(button)) {
			if ("123".equals(request.getParameter("password"))) {
				response.sendRedirect("horarioTrabalho.jsp");
			} else {
		%>
		<h1 class="titulos">Registro de Ponto - Recursos Humanos</h1>
		<form method="post" action="login.jsp" class="titulos"
			onsubmit="return validateForm()">
			<input type="hidden" name="button" value="Recursos Humanos">
			<label>Senha:</label> <input type="password" id="password"
				name="password"> <input type="submit" value="Login"><br>
			<strong><span class="senha-label">Digite:</span></strong> <span
				class="senha">123</span>
			<p id="error-message" style="color: red; display: none;">Senha
				incorreta. Tente novamente.</p>
		</form>
		<%
		}
		} else if ("Funcionários".equals(button)) {
		response.sendRedirect("registrarPonto.jsp");
		}
		%>
	</div>
	<div class="voltar-container">
		<a href="index.jsp" class="btn-voltar">Voltar</a>
	</div>
	<script>
		function validateForm() {
			var password = document.getElementById("password").value;

			if (password !== "123") {
				document.getElementById("error-message").style.display = "block";
				document.getElementById("password").value = "";
				return false;
			}

			return true;
		}
	</script>
	<jsp:include page="rodape.jsp" />
</body>
</html>