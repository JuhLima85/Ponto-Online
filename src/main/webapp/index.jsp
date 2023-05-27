<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registro de Ponto</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<jsp:include page="cabecalho.jsp" />
	<div class="container">
		<h1 class="titulos">Registro de Ponto</h1>
		<form method="post" action="login.jsp" class="titulos">
			<input type="submit" name="button" value="Recursos Humanos">
			<input type="submit" name="button" value="Funcionários">
		</form>
		
	</div>
<div class="contagr">
			<a href="index.jsp" class="btn-agradecimentos">Agradecimentos</a>
		</div>
</body>
<jsp:include page="rodape.jsp" />
</body>
</html>