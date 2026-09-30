<?xml version="1.0" encoding="UTF-8" ?>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message	key="label.anagrafe_tributaria_tracciati.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message	key="label.anagrafe_tributaria_tracciati.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="file" />
    </jsp:include>
<div id="subcontent">

<spring-form:form commandName="creazioneTestataTracciatoModel" name="inviodati"
	enctype="multipart/form-data">
	
 <div id="form" class="vbg-form">	
	<fieldset>
			 	<legend><fmt:message key="label.dati_generali" /></legend>

			 	<div class="form-group">
			 		<label><fmt:message key="label.descrizione" /></label>
			 		<spring-form:input id="descrizione_id" path="descrizione" size="100" maxlength="255" cssClass="required" />
			 		<div id="errore_descrizione_id" class="error validation-feedback" ><fmt:message key="field.required" /></div>
			 	</div>	
			 	<div class="form-group">
			 		<label><fmt:message key="label.anagrafe_tributaria_tracciati.file_tracciato" /></label>
			 		<input name="fileTracciato" id="file_tracciato" type="file" size="80" onchange="controlla_estensione(this);" /> 
					<fmt:message key="label.anagrafe_tributaria_tracciati.file_tracciato.help"/>
			 		<div id="errore_file_tracciato_id" class="error validation-feedback" ><fmt:message key="field.required" /></div>
			 	</div>	
			 	<div class="form-group">
			 		<label><fmt:message key="label.anagrafe_tributaria_tracciati.file_esito" /></label>
			 		<input name="fileEsito" id="file_esito" type="file" size="80" onchange="controlla_estensione(this);" /> 
					<fmt:message key="label.anagrafe_tributaria_tracciati.file_esito.help"/>
			 		<div id="errore_file_esito_id" class="error validation-feedback"><fmt:message key="field.required" /></div>
			 	</div>	
	</fieldset>
</div>
				 	
</spring-form:form>
<script type="text/javascript">
	
	
	function get_estensione(obj) {
		
			let path = obj.value;
			if(path){
				
				let posizione_punto = path.lastIndexOf(".");
				let lunghezza_stringa = path.length;
				let estensione = path.substring(posizione_punto + 1, lunghezza_stringa);			
				return estensione.toLowerCase();
			}else{
				return '';
			}
	}
	
	function controlla_estensione(obj) {
		
		if (get_estensione(obj) != "txt") {
			alert("Il file deve avere estensione txt");		
		}
		checkFileCaricati();
	}
	
	
	function checkFileCaricati(){
		
		let esito = document.getElementById("file_esito");
		let tracciato = document.getElementById("file_tracciato");
		if(esito.value && tracciato.value){
			if (!( get_estensione(esito) =='txt' && get_estensione(tracciato) =='txt')){			
				alert('I file devono avere estensione "txt"');		
			}else{
				document.getElementById("uploadButton").style.display='';
			}
		}
	}
</script>

	<div class="form-button">
		<a id="uploadButton" style="display: none;" class="btn btn-primary" href="javascript:doSubmit('caricaTracciati.htm','',document.inviodati)"><fmt:message key="button.ok" /></a>
		<a class="btn btn-secondary" href="javascript:historyBack('')"><fmt:message key="button.back" /></a>
	</div>

</body>
</html>