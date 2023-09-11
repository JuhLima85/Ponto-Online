<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="br.com.DAO.AcessoContadorDAO" %>
<%@ page import="br.com.Entity.AcessoContador" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ponto Online</title>
<link rel="stylesheet" href="css/index.css">
<link rel="stylesheet" href="css/style.css">
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
	
 <%-- Verificar e incrementar o contador de acesso --%>
    <%
        if (session.getAttribute("visited") == null) {
            AcessoContadorDAO acessoContadorDAO = new AcessoContadorDAO();
            AcessoContador acessoContador = acessoContadorDAO.buscarPorId(1L);
            
            if (acessoContador == null) {               
                acessoContador = new AcessoContador();
                acessoContador.setId(1L);
                acessoContador.setQuantidadeAcessos(1);
                acessoContadorDAO.atualizar(acessoContador);
            } else {                
                acessoContador.setQuantidadeAcessos(acessoContador.getQuantidadeAcessos() + 1);
                acessoContadorDAO.atualizar(acessoContador);
            }
            
            session.setAttribute("visited", true);
        }
    %>
	
</body>
<jsp:include page="rodape.jsp" />
</body>
</html>