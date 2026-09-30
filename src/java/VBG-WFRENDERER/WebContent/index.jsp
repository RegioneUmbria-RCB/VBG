<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<script type="text/javascript">
		function validateForm(){
			if(document.forms[0].idcomunealias.value=="" || document.forms[0].codiceprocedura.value=="" || isNaN(document.forms[0].codiceprocedura.value)){
				alert("Inserire campi obbligatori!");
				return false;
			}else{
				document.forms[0].submit();
			}
		}
		</script>
		<title>Renderer</title>
	</head>
	<body>
		<h2>Renderer</h2>
		<form action="graph" method="post">
			<table>
				<tr><td>Idcomune alias*</td><td><input type="text" name="idcomunealias" size="7" /></td></tr>
			 	<tr><td>Codice procedura*</td><td><input type="text" name="codiceprocedura" size="7" /></td></tr>
			 	<tr><td>Colore blocchi</td><td><input type="text" name="color" size="7" value="113,213,234" /> (R,G,B) [0-255]</td></tr>
			 	<tr><td><input type="button" onclick="validateForm();" value="Genera grafico" /></td></tr>
			</table>
		</form>
	</body>
</html>