<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ponto Online</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/editarMarcacao.css">
</head>
<body>
	<jsp:include page="cabecalho.jsp" />

	<div class="container">
		<h1 class="titulos">Registro de Ponto - Recursos Humanos</h1>
		<form action="MarcacoesFeitasServlet" method="post">
			<input type="hidden" name="action" value="edit"> <input
				type="hidden" name="id" value="${marcacao.id}">
			<div>
				<h2 class="titulos">Editar Marcações</h2>
				<br> <strong>CPF:</strong> <span class="cpf">${marcacao.getCpf()}</span>
			</div>
			<br> 1º Período: <input type="text" id="entrada" name="entrada"
				pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM"
				maxlength="5" value="${marcacao.entrada}" size="10"> <input
				type="text" id="intervaloInicio" name="intervaloInicio"
				pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM"
				maxlength="5" value="${marcacao.intervaloInicio}" size="10"
				class="ajuste-media"><br>
			<br> 2º Período: <input type="text" id="intervaloFim"
				name="intervaloFim" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$"
				placeholder="HH:MM" maxlength="5" value="${marcacao.intervaloFim}"
				size="10"> <input type="text" id="saida" name="saida"
				pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM"
				maxlength="5" value="${marcacao.saida}" size="10"
				class="ajuste-media">

			<div>
				<br> <input type="submit" value="Salvar">
			</div>
		</form>
	</div>
	<br>
	<p class="atencao">${mensagem}</p>
	<div class="clear"></div>
	<div class="voltar-container">
		<a href="index.jsp" class="btn-voltar">Voltar</a>
	</div>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
	<script>
		$(document).ready(function() {
			$('input[name="entrada"]').mask('00:00');
			$('input[name="intervaloInicio"]').mask('00:00');
			$('input[name="intervaloFim"]').mask('00:00');
			$('input[name="saida"]').mask('00:00');
		});
	</script>
	<jsp:include page="rodape.jsp" />
</body>
</html>