<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<meta http-equiv="pragma" content="no-cache" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/jmesa.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.3.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jmesa.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
		<title>Mappature PDF</title>
	</head>
	<body>
<!-- §§§BEGIN§§§ -->
		<h1>Mappature PDF</h1>	
		<br style="clear: left;"/>
		<a href="#" onclick="jQuery('#help_decodifica').toggle()">Help</a>
		<div id="help_decodifica" style="display: none;">
		<fieldet>
			<legend>Conversione di valori dall'xml</legend>
		<pre>
In caso che il valore recuperato dall'XML debba essere convertito in un formato diverso è necessario configurare i campi <b>"Tipo decodifica"</b>. 

Il campo tipo decodifica accetta solamente i valori (‘RE’,’JS’, ‘DV’,’PF’) dove:

        <b>RE</b>: Regular expression
        <b>JS</b>: javascript evalutation
        <b>DV</b>: decodifica valori (nel formato codice ##> descrizione)
        <b>PF</b>: Property File (File esterno dove recuperare le informazioni nella forma codice descrizione), serve per le liste di valori molto lunghe.Viene caricato in memoria solamente la prima volta.
<b style="font-size: 2em;">RE</b>
Es: nel caso di date
ESEMPIO VALORE INPUT: 2013-12-02+01:00
TIPO DECODIFICA: RE
FORMATO INPUT (Input): ([0-9]{4})-([0-9]{2})-([0-9]{2})[+-][0-9]{2}:[0-9]{2}
FORMATO OUTPUT (Output): $3/$2/$1
VALORE RESTITUITO: 02/12/2013


<b style="font-size: 2em;">JS</b>
Nel caso di decodifica del tag sesso:
VALORE DI INPUT: F
TIPO DECODIFICA: JS (javascript evaluation)
FORMATO INPUT (Input): if(#input#==’F’) then {#output#=’Femmina’;}else{#output#=’Maschio’;}
FORMATO OUTPUT (Output): NON USATO
VALORE RESTITUITO: Femmina

<b style="font-size: 2em;">DV</b>
VALORE DI INPUT: F
TIPO DECODIFICA: DV (decodifica valori)
FORMATO INPUT (Input):F##>Femmina|M##>Maschio|(NULL)##>Maschio
FORMATO OUTPUT (Output): NON USATO
VALORE RESTITUITO: Femmina

<b style="font-size: 2em;">PF</b>

VALORE DI INPUT: E256
TIPO DECODIFICA: PF (Property file)
FORMATO INPUT (Input): lista comuni.properties (deve essere presente nel classpath)
FORMATO OUTPUT (Output): NON USATO
VALORE RESTITUITO: Gubbio


</pre>
</fieldet>
		</div>
		
		
		<br />
		<form 
			id="formConfigurazione" 
			name="formConfigurazione" 
			action="${pageContext.request.contextPath}/pdfmapping/list.htm" 
			style="text-align: left;">
			${configurazioni}
			
			
			<input type="hidden" name="alias" value="<%=request.getAttribute("alias") %>" />
			
		</form>
		<br />




		<p>${saveResults}</p>
		<script type="text/javascript">
			function onInvokeAction(id) {     
				createHiddenInputFieldsForLimitAndSubmit(id); 
			}
			function logout(){
				document.location.href = "${pageContext.request.contextPath}/j_spring_security_logout";
			}
			function onInvokeExportAction(id) {
			    var parameterString = createParameterStringForLimit(id);
			    location.href = '${pageContext.request.contextPath}/pdfmapping/list.htm?' + parameterString+'&alias=<%=request.getAttribute("alias") %>';
			}
		</script>
		<input type="button" onclick="location.href='${pageContext.request.contextPath}'" value="indietro" />
<!-- §§§END§§§ -->
	</body>
</html>