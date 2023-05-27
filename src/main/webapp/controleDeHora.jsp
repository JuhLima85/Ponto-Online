<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>Marcações Feitas</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<jsp:include page="cabecalho.jsp" />
<div class="container">
	<h1 class="titulos">Registro de ponto - Funcionários</h1>
	
<div class="retangulo">
	<h2 class="titulos">Horário de Trabalho</h2>
	
	<form method="POST" action="HoraDeTrabalhoServlet">
    <input type="hidden" name="action" value="list">
    <label for="cpf">CPF:</label>
    <input type="text" id="cpf" name="cpf">
    <input type="submit" value="Buscar">
</form>
	   
  <ul id="horariosList">
    <c:if test="${empty sessionScope.horarios}">
        <c:if test="${empty sessionScope.cpf}">
            <li>Nenhum CPF informado.</li>
        </c:if>
        <c:if test="${not empty sessionScope.cpf}">
            <li>Este CPF ainda não foi cadastrado. Entre em contato com RH.</li>
        </c:if>
    </c:if>
    <c:forEach var="horario" items="${sessionScope.horarios}">
        <li>
    <strong>CPF:</strong> <span class="cpf">${horario.getCpf()}</span><br><br>
    <div class="horario-container">
      <div class="horario-item">
        <strong>Entrada:</strong> ${horario.getEntrada()}<br><br>
        <strong>Intervalo:</strong> ${horario.getIntervaloInicio()}
      </div>
      <div class="horario-item">
        <strong>Retorno:</strong> ${horario.getIntervaloFim()}<br><br>
        <strong>Saída:</strong> ${horario.getSaida()}
      </div>
    </div>
  </li>
</c:forEach>
</ul>
</div>

<div class="retangulo">
	<h2 class="titulos">Registro de Entrada / Saída</h2>
	<form  method="POST" action="MarcacoesFeitasServlet">
		<input type="hidden" name="action" value="add"> <label></label>
		Entrada: <input type="text" name="entrada" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM" maxlength="5"> 				
		Intervalo: <input type="text" name="intervaloInicio" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM" maxlength="5">
		<br>
		Retorno: <input type="text" name="intervaloFim" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM" maxlength="5">
		Saída: <input type="text" name="saida" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM" maxlength="5">
		<div class="voltar-container">
			<br> <input type="submit" value="Cadastrar">
		</div>
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
			</tr>
		</thead>
		<tbody>
			<c:forEach var="marcacao" items="${marcacoes}">
				<tr>
					<td>${marcacao.entrada}</td>
					<td>${marcacao.intervaloInicio}</td>
					<td>${marcacao.intervaloFim}</td>					
					<td>${marcacao.saida}</td>
					<td>${marcacao.qtdHorasNegativa}</td>
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
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>

<script>

</script>
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
	
  //para aplicar automaticamente a máscara ao campo de valor hora
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