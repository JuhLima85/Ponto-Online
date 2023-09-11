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
<link rel="stylesheet" href="css/registrarPonto.css">
<link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:include page="cabecalho.jsp" />
	<div class="container">
		<h1 class="titulos">Registro de ponto - Funcionários</h1>
		<div class="retangulo">
			<h2 class="titulos">Marcação de Ponto</h2>
			<form method="POST" action="MarcacoesFeitasServlet">
				<input type="hidden" name="action" value="add">
				<div>
					CPF: <input type="text" name="cpf" maxlength="11" placeholder="00000000000" required>
				</div>
				<div>
					Senha: <input type="text" name="senha" maxlength="6" placeholder="Insira a Senha" required>
				</div>
				<div class="voltar-container">
					<input type="submit" value="Registrar">
				</div>
			</form>
		</div>
		<p class="atencao">${mensagem}</p>
		<div class="retangulo-table">
			<table class="horarios">
				<thead>
					<tr>
						<th>Entrada</th>
						<th>Intervalo</th>
						<th>Retorno</th>
						<th>Saída</th>
						<th>Horas Extras / Atrasos</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="marcacao" items="${mf}">
						<tr>
							<td>${marcacao.entrada}</td>
							<td>${marcacao.intervaloInicio}</td>
							<td>${marcacao.intervaloFim}</td>
							<td>${marcacao.saida}</td>
							<td>${bhs.discrepanciaDiaria}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="clear"></div>
		<div class="voltar-container">
			<a href="index.jsp" class="btn-voltar">Voltar</a>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
	<script>
		$(document).ready(function() {
			var tabelaHorarios = $(".horarios");

			if (tabelaHorarios.length > 0) {
				$("html, body").animate({
					scrollTop : tabelaHorarios.offset().top
				}, 1000);
			}
		});
		
		 $(document).ready(function() {     
		      $('input[name="cpf"]').mask('000.000.000-00');
		  });
	</script>

	<jsp:include page="rodape.jsp" />
</body>
</html>