<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Editar Horários</title>
<link rel="stylesheet" href="style.css">
</head>
<body>

<form action="HoraDeTrabalhoServlet" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="id" value="${horario.id}">
        
        <label for="entrada">Entrada:</label>
        <input type="text" id="entrada" name="entrada" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM" maxlength="5" value="${horario.entrada}">
        
       <label for="saida">Saída:</label>
        <input type="text" id="saida" name="saida" pattern="^([0-1][0-9]|2[0-3]):[0-5][0-9]$" placeholder="HH:MM" maxlength="5" value="${horario.saida}">   
              
        <input type="submit" value="Salvar">
    </form>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
<script>
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