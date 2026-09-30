<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.lista_verticalizzazioni_base" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message
	key="label.lista_verticalizzazioni_base_parametri" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<div id="subcontent">

	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="verticalizzazionibase" />
	</jsp:include>
	<div class="parametriDiv">
		<div class="etichetta">
		<div><c:out value="${verticalizzazionibase.entity.modulo}" />:</div>
		</div>
		<div class="parametro">
			<div><c:out value="${verticalizzazionibase.entity.descrizione}" /></div>
		</div>
	</div>
	<br class="clear" />
	<fieldset>
		<legend><fmt:message key="label.filtri" /></legend>		
		<div style="padding: 5px;">
		<label for="filroparametro_id"><fmt:message key="label.parametro" />:</label>
		<input type="text" name="filroparametro" id="filroparametro_id"/> <input type="button" name="filtra" onclick="filtra()" value="cerca" />
		</div>
		<div style="padding: 5px;">
		<label for="filtrosoftware_id"><fmt:message key="label.modulo" />:&nbsp;&nbsp;&nbsp;&nbsp;</label>
		<select name="filtrosoftware" id="filtrosoftware_id" onchange="filtrasoftware()">
			<option value=""></option>
			<c:forEach items="${softwares}" var="s">
				<option value="${s.software.codice}">${s.software.descrizione}</option>
			</c:forEach>		
		</select>
		</div>
	</fieldset>
	<br class="clear"/>
	<spring-form:form commandName="verticalizzazionibase" name="inviodati">
		<input type="hidden" name="codiceComune" id="codiceComuneId" />
		<input type="hidden" name="codice" id="codiceVerticalizzazione" value="${verticalizzazionibase.entity.modulo}" />
		
		
		<div id="configurazioniId" style="width: 80%;">
			
		</div>	
	</spring-form:form>
</div>


<script type="text/javascript">

function filtrasoftware(){
	var valore = jQuery('#filtrosoftware_id').val();
	
	if(valore==''){
		jQuery('.softwareCss').each(function(i) {
			var $p = $(this);
			$p.show();
			});
	}else{

		jQuery('.softwareCss').each(function(i) {
			var $p = $(this);
			$p.hide();
			});
		jQuery('.'+valore).each(function(i) {
			var $p = $(this);
					$p.show();
			});
	}
	
}
	


	function filtra(){
		var valore = jQuery('#filroparametro_id').val();
		if(valore==''){
			jQuery('.odd, .even').each(function(i) {
				var $p = $(this);
				$p.show();
				});
		}else{

			jQuery('.odd, .even').each(function(i) {
				var $p = $(this);
				$p.hide();
				});
			valore = valore.toUpperCase();
			jQuery('.odd, .even').each(function(i) {
				var $p = $(this);
				var text = $p.attributes['nome'].value;
					if(text.contains(valore)){
						$p.show();
					}
				});
		}
		
	}
	
	
	if (typeof String.prototype.startsWith != 'function') {
		  // see below for better implementation!
		  String.prototype.startsWith = function (str){
			return this.toLowerCase().indexOf(str.toLowerCase()) == 0;
		  };
		}

		if (typeof String.prototype.contains != 'function') {
		  // see below for better implementation!
		  String.prototype.contains = function (str){		
			return this.toLowerCase().indexOf(str.toLowerCase()) >= 0;
		  };
		}
		
	
	jQuery(document).ready(function(){
		loadConfigurazioni('');
	});

	function loadConfigurazioni(codComune){
		disableFunctions();
		jQuery.ajax({
    		url: '${pageContext.request.contextPath}/verticalizzazionibase/ajaxCaricaConfigurazioni.htm', 
    		dataType: 'html',
    		type: 'POST',
    		data: 'codice=${verticalizzazionibase.entity.modulo}&codiceComune='+codComune,
    		cache: false,	
    		success: elaborazioneCallback,
    		error: elaborazioneError
    	});
	}
	
	function elaborazioneCallback(data, textStatus, jqXHR) {
		jQuery('#configurazioniId').html(data);
		enableFunctions();
	}	
	
	function elaborazioneError(jqXHR, textStatus, errorThrown) {
		jQuery('#configurazioniId').html(errorThrown);
		enableFunctions();
	}
	
</script>
<div id="functions">
<ul>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>