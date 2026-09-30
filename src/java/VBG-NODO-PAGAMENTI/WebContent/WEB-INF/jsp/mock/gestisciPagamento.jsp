<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pagina di test del connettore Mock Test di attivazione della sessione di pagamento</title>
<style>

.negativo{
	color: red;
	font-weight: bold;
}

.positivo{
	font-weight: bold;
	color: green;
}
</style>

</head>
<body>
	<h2>Test di attivazione della sessione di pagamento</h2>
	<form action="${pageContext.request.contextPath}/esitoSessionePagamento/${profilo}" method="POST">
		<input type="hidden" name="idSessione" value="${idSessione}">
		<table>
			<tr>
				<td><label for="esito_negativo_id" class="negativo">PAGAMENTO NON EFFETTUATO</label></td>
				<td><input id="esito_negativo_id" type="radio" name="esito" value="false"></td>
			</tr>
			<tr>
				<td><label for="esito_positivo_id" class="positivo">PAGAMENTO EFFETTUATO</label></td>
				<td><input id="esito_positivo_id" type="radio" name="esito" value="true"></td>
			</tr>			
		</table>	
		<div>
			<button type="submit">INVIA</button>
		</div>
	</form>
	<script type="text/javascript">
		function pagamentiGet() {

			document.location = "${urlget}";
		}
	</script>
</body>
</html>
