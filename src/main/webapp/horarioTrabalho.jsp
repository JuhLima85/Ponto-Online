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

	<div class="container">

		<h1>Registro de Ponto</h1>

		<form method="POST" action="HoraDeTrabalhoServlet">
			<input type="hidden" name="action" value="add"> <input
				type="hidden" name="delete_all" value="true">
			<div>
			<h2>Cadastro de Funcionário:</h2><br>
				<label> CPF: <input type="text" name="cpf"
					value="${not empty param.cpf ? param.cpf : ''}" required></label>
			</div>			
			Entrada: <input type="text" name="entrada"
				pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM"
				maxlength="5" required> Saída: <input type="text" name="saida"
				pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM"
				maxlength="5" required>
			<div>
				<br> <input type="submit" value="Cadastrar"> <input
					type="button" value="Excluir todos"
					onclick="if(confirm('Tem certeza que deseja excluir todos?')) { document.forms[0].action='HoraDeTrabalhoServlet?action=delete_all'; document.forms[0].submit(); }">
			</div>
		</form>
<br>
		<div class="clear"></div>

		<!-- Lista os horários de trabalho cadastrados -->
		
		<table class="horarios">
			<thead>
				<tr>
					<th>Entrada</th>
					<th>Saída</th>
					<th>Opções</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="horario" items="${horarios}">
					<tr>
						<td>${horario.entrada}</td>
						<td>${horario.saida}</td>
						<td>
							<button class="editar" onclick="editarHorario(${horario.id})">Editar</button>
							<button class="excluir" onclick="excluirHorario(${horario.id})">Excluir</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
<div class="clear"></div>
	 <div class="voltar-container">
    <a href="index.jsp" class="btn-voltar">Voltar</a>
  </div>


	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>

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
</body>
</html>