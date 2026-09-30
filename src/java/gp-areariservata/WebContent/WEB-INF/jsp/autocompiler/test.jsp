<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" deferredSyntaxAllowedAsLiteral="true" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link type="text/css" href="<%=request.getContextPath() %>/css/layout.css" rel="stylesheet"></link>
		<link type="text/css" href="<%=request.getContextPath() %>/css/default.css" rel="stylesheet"></link>
		<link type="text/css" href="<%=request.getContextPath() %>/css/pager.css" rel="stylesheet"></link>
		<link type="text/css" href="<%=request.getContextPath() %>/css/smoothness/jquery-ui-1.9.2.custom.css" rel="stylesheet"></link>
		<link type="text/css" href="<%=request.getContextPath() %>/css/autocompiler/init-autocompiler.css" rel="stylesheet"></link>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery-ui-1.9.2.custom.js"></script>
		<script type="text/javascript" src="<%=request.getContextPath() %>/js/autocompiler/init-autocompiler.js"></script>

<title>Test del controllo autocompiler</title>
</head>
<body>
<h5>Prova init-autocompiler: ricerca persone fisiche</h5>
<div>
	<form action="">
		<label for="nome_id">Nome</label>
		<input type="text" name="nome" id="nome_id"/>
		<br/>
		<label for="cognome_id">Cognome</label>
		<input type="text" name="cognome" id="cognome_id"/>
		<br/>
		<label for="data_nascita_id">Data di nascita</label>
		<input type="text" name="data_nascita" id="data_nascita_id"/>
		<br/>
		<label for="comune_nascita_id">Comune di nascita</label>
		<input type="text" name="comune_nascita" id="comune_nascita_id"/>
		<br/>
		<label for="segno">Segno zodiacale</label>
		<input type="text" name="segno" id="segno"/>
		<script>
			$(document).ready(function(){
				var attrMappings = [];
				attrMappings["nome"] = "nome_id";
				attrMappings["nominativo"] = "cognome_id";
				attrMappings["datanascita"] = "data_nascita_id";
				attrMappings["comuneNascita.comune"] = "comune_nascita_id";
				$('#nome_id').cercapersonafisica({
					mappings: attrMappings, 
					url:"http://localhost:8080/AreaRiservata/ajaxdata/findAnagraficaPF.htm"
				});
			});
		</script>
	</form>
</div>
<h5>Prova init-autocompiler: ricerca persone giuridiche</h5>
<div>
	<form action="">
		<label for="ragsoc_id">Ragione Sociale</label>
		<input type="text" name="ragsoc" id="ragsoc_id"/>
		<br/>
		<label for="indirizzo_id">Indirizzo</label>
		<input type="text" name="indirizzo" id="indirizzo_id"/>
		<br/>
		<label for="comune_id">Comune</label>
		<input type="text" name="comune" id="comune_id"/>
		<br/>
		<label for="provincia_id">Provincia</label>
		<input type="text" name="provincia" id="provincia_id"/>
		<br/>
		<label for="formag_id">Forma Giuridica</label>
		<input type="text" name="formag" id="formag_id"/>
		<br/>
		<label for="num_addetti_id">Numero Addetti</label>
		<input type="text" name="num_addetti" id="num_addetti_id"/>
		<script>
			$(document).ready(function(){
				var attrMappings = [];
				attrMappings["nominativo"] = "ragsoc_id";
				attrMappings["indirizzo"] = "indirizzo_id";
				attrMappings["comuneResidenza.comune"] = "comune_id";
				attrMappings["provincia"] = "provincia_id";
				attrMappings["formagiuridica.formagiuridica"] = "formag_id";
				$('#ragsoc_id').cercapersonagiuridica({
					mappings: attrMappings, 
					url:"http://localhost:8080/AreaRiservata/ajaxdata/findAnagraficaPG.htm"
				});
			});
		</script>
	</form>
</div>

<h5>Prova init-autocompiler: ricerca comune</h5>
<div>
	<form action="">
		<label for="comune2_id">Comune</label>
		<input type="text" name="comune" id="comune2_id"/>
		<br/>
		<label for="provincia2_id">Provincia</label>
		<input type="text" name="provincia" id="provincia2_id"/>
		<br/>
		<label for="cap_id">CAP</label>
		<input type="text" name="cap" id="cap_id"/>
		<br/>
		<script>
			$(document).ready(function(){
				var attrMappings = [];
				attrMappings["comune"] = "comune2_id";
				attrMappings["provincia"] = "provincia2_id";
				attrMappings["cap"] = "cap_id";
				$('#comune2_id').comboautocompiler({
					mappings: attrMappings, 
					url:"http://localhost:8080/AreaRiservata/ajaxdata/findComuni.htm"
				});
			});
		</script>
	</form>
</div>

</body>
</html>