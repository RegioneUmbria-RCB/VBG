<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>PDF UTILS</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
<style>
body {
	font-family: Arial;
	font-size: small;
}

.listaSchede {
	border-bottom: 1px solid #ccc;
	margin: 0;
	padding-bottom: 19px;
	padding-left: 10px;
}

.listaSchede ul,.listaSchede li {
	display: inline;
	list-style-type: none;
	margin: 0;
	padding: 0;
}

.listaSchede a:link,.listaSchede a:visited {
	background: #E8EBF0;
	border: 1px solid #ccc;
	color: #999;
	float: left;
	font-size: small;
	font-weight: normal;
	line-height: 14px;
	margin-right: 8px;
	padding: 2px 10px 2px 10px;
	text-decoration: none;
}

.listaSchede a:hover {
	background: #d8dBe0;
	color: #696969;
}

.listaSchede a.SchedaAttiva {
	background: #ccc;
	border-bottom: 1px solid #fff;
	color: #888;
	font-weight: bold;
}
table{
	background-color: #f0f0f0;
	padding: 10px;
	width: 100%;
	
}
legend{
	font-size: medium;
	font-weight: bolder;
	background-color: #e0e0e0;
	padding-top: 5px;
	padding-bottom: 5px;
	padding-left: 5em;
	padding-right: 5em;
}
</style>
<script type="text/javascript">

	var show = function(divToShow){		
		$.each($('.tab'), function() { 			
			if($(this).attr('id') == divToShow){
				$(this).show();
				$('#'+$(this).attr('id')+"_command").removeClass("Scheda");
				$('#'+$(this).attr('id')+"_command").addClass("SchedaAttiva");
			}else{
				$(this).hide();
				$('#'+$(this).attr('id')+"_command").removeClass("SchedaAttiva");
				$('#'+$(this).attr('id')+"_command").addClass("Scheda");
			}
		});
		
	}
</script>
</head>
<body>
	<h1>
		PDF UTILS - <a href="${pageContext.request.contextPath}/services/">WSDL</a>
	</h1>
	
	<c:if test="${CONFIGURAZIONE_UPDATED eq true}">
		<h3>La configurazione è stata ricaricata</h3>
	</c:if>

	<ul class="listaSchede">
		<li class="Scheda" id="compilazione_scheda_command"><a href="javascript:show('compilazione_scheda')">Compilazione pdf</a></li>
		<li class="Scheda" id="decompilazione_scheda_command"><a href="javascript:show('decompilazione_scheda')">Recupera dati da pdf</a></li>
		<li class="Scheda" id="configurazione_scheda_command"><a href="javascript:show('configurazione_scheda')">Configura i Mapping</a></li>
		<li class="Scheda" id="samples_scheda_command"><a href="javascript:show('samples_scheda')">File esempio</a></li>
	</ul>
	<br />
	<fieldset id="compilazione_scheda" class="tab">
		<legend>Compilazione pdf</legend>
		<form action="compilaPDF.htm" name="invio"
			enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<td>Alias</td>
					<td><input type="text" id="alias" name="alias"
						value="${sessionScope.alias}" /></td>
				</tr>
				<tr>
					<td>File xml</td>
					<td><input type="file" name="xmlFile" id="xmlFile" /></td>
				</tr>
				<tr>
					<td>File pdf</td>
					<td><input type="file" name="pdfFile" id="pdfFile" /></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" onclick="invia()"
						value="precompila" /></td>
				</tr>
			</table>
			<script type="text/javascript">
				function invia() {
					if (document.getElementById("alias").value == '') {
						alert('E\' obbligatorio specificare un alias');
						return;
					}
					if (document.getElementById("xmlFile").value == '') {
						alert('E\' obbligatorio specificare un xmlFile');
						return;
					}
					if (document.getElementById("pdfFile").value == '') {
						alert('E\' obbligatorio specificare un pdfFile');
						return;
					}
					document.invio.submit();
				}
			</script>
		</form>
	</fieldset>
	
	<fieldset id="decompilazione_scheda" style="display: none;" class="tab">
		<legend>Recupera dati da pdf</legend>
		<form action="reversePDF.htm" name="reverse"
			enctype="multipart/form-data" method="post">
			<table >
				<tr>
					<td>Alias</td>
					<td><input type="text" id="alias_1" name="alias"
						value="${sessionScope.alias}" /></td>
				</tr>
				<tr>
					<td>File pdf</td>
					<td><input type="file" name="pdfFile" id="pdfFile_1" /></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button" onclick="reversePDF()"
						value="scarica dati da pdf" /></td>
				</tr>
			</table>
			<script type="text/javascript">
				function reversePDF() {
					if (document.getElementById("alias_1").value == '') {
						alert('E\' obbligatorio specificare un alias');
						return;
					}
					if (document.getElementById("pdfFile_1").value == '') {
						alert('E\' obbligatorio specificare un pdfFile');
						return;
					}
					document.reverse.submit();
				}
			</script>
		</form>
	</fieldset>
	<fieldset id="configurazione_scheda" style="display: none;" class="tab">
		<legend>Configura i Mapping</legend>
		<form action="${pageContext.request.contextPath}/pdfmapping/list.htm"
			name="configura" enctype="multipart/form-data" method="post">
			<table>
				<tr>
					<td>Alias</td>
					<td><input type="text" id="alias_2" name="alias"
						value="${sessionScope.alias}" /></td>
				</tr>
				<tr>
					<td colspan="2"><input type="button"
						onclick="configuraMapping()" value="configura le mappature" /></td>
				</tr>
			</table>
			<script type="text/javascript">
				function configuraMapping() {
					if (document.getElementById("alias_2").value == '') {
						alert('E\' obbligatorio specificare un alias');
						return;
					}
					document.configura.submit();
				}
			</script>
		</form>
	</fieldset>
	
	<fieldset id="samples_scheda" style="display: none;"  class="tab">
		<legend>File esempio</legend>
		<table>
		<tr>
			<td>
			STC:
				<ul>
					<li><a target="_new"
						href="${pageContext.request.contextPath}/samples/domandaSTC.xml">Domanda
							STC</a></li>
					<li><a target="_new"
						href="${pageContext.request.contextPath}/samples/EsempioPDFSTC.pdf">File
							PDF</a></li>
				</ul>
			</td>
		</tr>
		<tr>	
			<td>
			CART:
				<ul>
					<li><a target="_new"
						href="${pageContext.request.contextPath}/samples/MDA.CART.xml">Domanda
							CART (MDA)</a></li>
					<li><a target="_new"
						href="${pageContext.request.contextPath}/samples/EsempioPDFCART.pdf">File
							PDF</a></li>
				</ul>
			</td>
		</tr>	
		</table>
	</fieldset>

</body>
</html>