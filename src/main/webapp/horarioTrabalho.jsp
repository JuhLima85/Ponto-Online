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
<link rel="stylesheet" href="css/horarioTrabalho.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
</head>
<body>
	<div class="header">
		<img src="imagens/logo.png" alt="CodeDeving - Do Back ao Front"
			src="Logo CodeDeving" class="logo-cabecalho">
	</div>

	<button class="menu-button" onclick="toggleMenu()">
		<i class="fas fa-bars"></i>
	</button>
	<div class="menu-lateral" id="menu-lateral">
		<ul>
			<li><a href="#cadastro" onclick="hideMenu()">Cadastrar
					Funcionário</a></li>
			<li><a href="#listagem" onclick="hideMenu()">Listar Todos
					Horários</a></li>
			<li><a href="#pesquisa-cpf" onclick="hideMenu()">Pesquisar
					Horário Por CPF</a></li>
			<li><a href="#pesquisa-registros" onclick="hideMenu()">Pesquisar
					Registros de Ponto</a></li>
		</ul>
	</div>

	<div class="container">
		<div id="cadastro" class="section">
			<h1 class="titulos">Registro de Ponto - Recursos Humanos</h1>
			<form method="POST" action="HoraDeTrabalhoServlet">
				<input type="hidden" name="action" value="add">
				<div class="retangulo">
					<div>
						<h2 class="titulos">Cadastro de Funcionário</h2>
						<br> <label> CPF: <input type="text" name="cpf"
							value="" style="margin-right: 15px;" size="14"
							placeholder="00000000000" required></label> <label>Senha:
							<input type="password" name="senha" size="10" maxlength="6"
							placeholder="Max. 6 dígitos" required>
						</label>
					</div>
					<br> 1º Período: <input type="text" name="entrada"
						style="text-align: center;"
						pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$"
						placeholder="Entrada HH:MM" maxlength="5" required> <input
						type="text" name="intervaloInicio" style="text-align: center;"
						pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$"
						placeholder="Intervalo HH:MM" maxlength="5" required><br>
					<br> 2º Período: <input type="text" name="intervaloFim"
						style="text-align: center;"
						pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$"
						placeholder="Retorno HH:MM" maxlength="5"> <input
						type="text" name="saida" style="text-align: center;"
						pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$"
						placeholder="Saída HH:MM" maxlength="5">
					<div class="center">
						<br> <br> <a
							href="https://www.4devs.com.br/gerador_de_cpf" target="_blank"
							style="font-size: 14px;">Simule um CPF válido aqui</a><br> <br>
						<input type="submit" value="Cadastrar">
					</div>
				</div>
			</form>
			<div class="retangulo" id="listagem">
				<form method="GET" action="HoraDeTrabalhoServlet">
					<input type="hidden" name="action" value="listAll">
					<h2 class="titulos">Listar Todos os Horários Cadastrados</h2>
					<br> <input type="submit" value="Listar Todos">
				</form>
			</div>
			<div class="retangulo" id="pesquisa-cpf">
				<form method="GET" action="HoraDeTrabalhoServlet">
					<input type="hidden" name="action" value="listCPF">
					<h2 class="titulos">Pesquisar Horário Cadastrado</h2>
					<br> <label> CPF: <input type="text" name="cpf"
						value="" size="14" placeholder="00000000000" required></label><br>
					<br> <input type="submit" value="Pesquisar">
				</form>
			</div>
		</div>
		<p class="atencao">${mensagem}</p>
		<div class="clear"></div>
		<div id="" class="section">
			<div class="retangulo-table">
				<table class="horarios">
					<thead>
						<tr>
							<th>CPF</th>
							<th>Senha</th>
							<th>Entrada</th>
							<th>Intervalo</th>
							<th>Retorno</th>
							<th>Saída</th>
							<th colspan="2">Opções</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="horario" items="${horarios}">
							<tr>
								<td>${horario.cpf}</td>
								<td>${horario.senha}</td>
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
			</div>
		</div>
		<br>
		<div id="pesquisa-registros" class="section">
			<div class="retangulo">
				<h2 class="titulos">Pesquisar Registros de Ponto</h2>
				<form method="GET" action="MarcacoesFeitasServlet">
					<input type="hidden" name="action" value="list"> <label
						for="cpf">CPF:</label> <input type="text" id="cpf" name="cpf"
						size="15" style="margin-right: 30px" placeholder="00000000000" required> <label
						for="dataInicio">De:</label> <input type="date" id="dataInicio"
						name="dataInicio" style="margin-right: 30px" required> <label
						for="dataFim">Até:</label> <input type="date" id="dataFim"
						name="dataFim" style="margin-right: 30px" required> <br>
					<br> <input type="submit" value="Buscar">
				</form>
			</div>
			<p class="atencao">${mensagem1}</p>
		</div>
		<div id="" class="section">
			<div class="retangulo-table">
				<table class="horarios">
					<thead>
						<tr>
							<th>Data</th>
							<th>Entrada</th>
							<th>Intervalo</th>
							<th>Retorno</th>
							<th>Saída</th>
							<th>Horas Extras / Atrasos</th>
							<th>Opções</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="marcacao" items="${marcacoes}" varStatus="loop">
							<tr>
								<td>${marcacao.data}</td>
								<td>${marcacao.entrada}</td>
								<td>${marcacao.intervaloInicio}</td>
								<td>${marcacao.intervaloFim}</td>
								<td>${marcacao.saida}</td>
								<td>${bhs[loop.index].discrepanciaDiaria}</td>
								<td>
									<button class="editar" onclick="editarMarcacao(${marcacao.id})">Editar</button>
									<button class="excluir"
										onclick="excluirMarcacao(${marcacao.id}, ${bhs[loop.index].id})">Excluir</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<div id="" class="section">
		<div class="voltar-container">
			<a href="index.jsp" class="btn-voltar">Voltar</a>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
	<script>
  	function editarHorario(id) {    
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
	       
	        alert("Horário excluído com sucesso!");
	    }
	}		

	function editarMarcacao(id) { 
	 window.location.href = "MarcacoesFeitasServlet?action=edit&id=" + id;
    }
	
	function excluirMarcacao(idMarcacao, idBancoDeHoras) {
	    if (confirm('Tem certeza que deseja excluir este registro?')) {
	        var form = document.createElement('form');
	        form.method = 'GET';
	        form.action = 'MarcacoesFeitasServlet';
	        form.innerHTML = '<input type="hidden" name="action" value="delete"><input type="hidden" name="idMarcacao" value="' + idMarcacao + '"><input type="hidden" name="idBancoDeHoras" value="' + idBancoDeHoras + '">';
	        document.body.appendChild(form);
	        form.submit();

	        alert("Registro excluído com sucesso!");
	    }
	}

	
	$(document).ready(function() {
		var tabelaHorarios = $(".horarios");

		if (tabelaHorarios.length > 0) {	
			$("html, body").animate({
				scrollTop : tabelaHorarios.offset().top
			}, 1000); 
		}
	});
 
  $(document).ready(function() {	 
  $('input[name="entrada"]').mask('00:00');
  $('input[name="intervaloInicio"]').mask('00:00');
  $('input[name="intervaloFim"]').mask('00:00');
  $('input[name="saida"]').mask('00:00');
});
  
  $(document).ready(function() {     
      $('input[name="cpf"]').mask('000.000.000-00');
  });

  function updateMarginTop() {
      var menuLateral = document.getElementById("menu-lateral");
      var scrollTop = window.scrollY || document.documentElement.scrollTop;     
  }
 
  updateMarginTop();
  window.addEventListener("scroll", updateMarginTop);   

	  function toggleMenu() {
    console.log("toggleMenu() foi chamado");
    var menuLateral = document.getElementById("menu-lateral");
    if (window.innerWidth <= 600) { 
        if (menuLateral.style.display === "block") {
            menuLateral.style.display = "none";
        } else {
            menuLateral.style.display = "block";
        }
    } else {
        if (menuLateral.style.left === "0px") {
            menuLateral.style.left = "-250px";
        } else {
            menuLateral.style.left = "0px";
        }
    }
}	
	  function hideMenu() {
	      if (window.innerWidth <= 600) {
	          var menuLateral = document.getElementById("menu-lateral");
	          menuLateral.style.display = "none";
	      }
	  }

</script>
	<jsp:include page="rodape.jsp" />
</body>
</html>