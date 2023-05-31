<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Horário de Trabalho</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="cabecalho.jsp"/>
	<div class="container">
	

		<h1 class="titulos">Registro de Ponto - Recursos Humanos</h1>

		<form method="POST" action="HoraDeTrabalhoServlet">
	<input type="hidden" name="action" value="add">
	<input type="hidden" name="delete_all" value="true">
	<div>
		<h2 class="titulos">Cadastro de Funcionário</h2><br>
		<label> CPF: <input type="text" name="cpf" value="" style="margin-right: 60px;" required></label>		
		<label>Senha: <input type="password" name="senha" size="10" required></label>		
		
	</div>
	<br>
	1º Período: 
	<input type="text" name="entrada" style="margin-right: 10px; text-align: center;" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="Entrada HH:MM" maxlength="5">
	<input type="text" name="intervaloInicio" style="text-align: center;" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="Intervalo HH:MM" maxlength="5"><br><br>
	
	2º Período: 
	<input type="text" name="intervaloFim" style="margin-right: 10px; text-align: center;" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="Retorno HH:MM" maxlength="5">
	<input type="text" name="saida" style="text-align: center;" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="Saída HH:MM" maxlength="5">
	
	<div>
		<br>
		<input type="submit" value="Cadastrar">
		<input type="button" value="Excluir todos" onclick="if(confirm('Tem certeza que deseja excluir todos?')) { document.forms[0].action='HoraDeTrabalhoServlet?action=delete_all'; document.forms[0].submit(); }">
	</div>
</form>
<br>
<p class="atencao">${mensagem}</p>
		<div class="clear"></div>

		<!-- Lista os horários de trabalho cadastrados -->
		
		<table class="horarios">
	<thead>
		<tr>
			<th></th>
			<th colspan="2">1º Período</th>
			<th colspan="2">2º Período</th>
			<th></th>
		</tr>
		<tr>
			<th>CPF</th>
			<th>Entrada</th>
			<th>Intervalo</th>
			<th>Retorno</th>
			<th>Saída</th>
			<th>Opções</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="horario" items="${horarios}">
			<tr>
				<td>${horario.cpf}</td>
				<td>${horario.entrada}</td>
				<td>${horario.intervaloInicio}</td>
				<td>${horario.intervaloFim}</td>
				<td>${horario.saida}</td>
				<td>
					<button class="editar" onclick="editarHorario(${horario.id})">Editar</button>
					<button class="excluir" onclick="excluirHorario(${horario.id})">Excluir</button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<br>
<!-- Lista horario previsto do colaborador -->
<div class="retangulo">
	<h2 class="titulos">Registro de Entrada / Saída</h2>	
	<form method="GET" action="MarcacoesFeitasServlet">
    <input type="hidden" name="action" value="list">
    <label for="cpf">CPF:</label>
    <input type="text" id="cpf" name="cpf" >
    <input type="submit" value="Buscar">
</form>	   

	<!-- Lista das marcações feitas -->
	<table class="horarios">
		<thead>
			<tr>
				<th>Entrada</th>				
				<th>Intervalo</th>
				<th>Retorno</th>
				<th>Saída</th>
				<th>Horas Extras / Atrasos</th>
				<th>Opções</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="marcacao" items="${sessionScope.marcacoes}">
				<tr>
					<td>${marcacao.entrada}</td>
					<td>${marcacao.intervaloInicio}</td>
					<td>${marcacao.intervaloFim}</td>					
					<td>${marcacao.saida}</td>
					<td>${marcacao.qtdHorasNegativa}</td>
					<td>
					<button class="editar" onclick="editarHorario(${marcacao.id})">Editar</button>
					<button class="excluir" onclick="excluirHorario(${marcacao.id})">Excluir</button>
				</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

	</div>
<div class="clear"></div>
	 <div class="voltar-container">
    <a href="index.jsp" class="btn-voltar">Voltar</a>
  </div>


	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
	
	<script>
  	function editarHorario(id) {
        // Redireciona para a página de edição passando o ID como parâmetro na URL
	 window.location.href = "HoraDeTrabalhoServlet?action=edit&id=" + id;
    }
	
	function excluirHorario(id) {
	     if (confirm('Tem certeza que deseja excluir este horário?')) {
	        var form = document.createElement('form');
	        form.method = 'POST';
	        form.action = 'HoraDeTrabalhoServlet';
	        form.innerHTML = '<input type="hidden" name="action" value="delete"><input type="hidden" name="id" value="' + id + '">';
	        document.body.appendChild(form);
	        form.submit();
	        
	        // Exibir mensagem de excluído com sucesso como pop-up
	        alert("Horário excluído com sucesso!");
	    }
	} 


<!-- Botão Cadastrar -->
<input type="submit" value="Cadastrar" onclick="cadastrarClick()">

  //para aplicar automaticamente a máscara ao campo de valor hora
  $(document).ready(function() {	 
  $('input[name="entrada"]').mask('00:00');
  $('input[name="intervaloInicio"]').mask('00:00');
  $('input[name="intervaloFim"]').mask('00:00');
  $('input[name="saida"]').mask('00:00');
});


</script>
<jsp:include page="rodape.jsp"/>
</body>
</html>