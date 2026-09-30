<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link type="text/css" href="${pageContext.request.contextPath}/css/cart/smoothness/jquery-ui-1.11.1.custom.css" rel="stylesheet"  ></link>
	<link type="text/css" href="${pageContext.request.contextPath}/css/jquery.tooltip.css" rel="stylesheet"  ></link>
	<link type="text/css" href="${pageContext.request.contextPath}/css/schededinamiche.css" rel="stylesheet" ></link>
	<%-- 
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery-ui-1.11.1.custom.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.ui.datepicker-it.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tooltip.min.js"></script>
	--%>
	<%-- TODO spostare lo script da scripts/cart a scripts dopo aver unificato le versioni di jQUery ed effettuato i test per le regressioni --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/jquery.tmpl.min.js"></script>
	<%-- 
	<script type='text/javascript' src="${pageContext.request.contextPath}/scripts/prototype.js"></script>
	--%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/gruppoinit.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/cart/autoNumeric-1.7.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/init-schededinamiche.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.uploadDatiDinamici.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery.form.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/hoverIntent.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/superfish.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/supersubs.js"></script>
	<title>
	<c:if test="${not empty param.codiceIstanza}">
		<fmt:message key="label.schede_dell_istanza" />	
	</c:if>
	<c:if test="${empty param.codiceIstanza}">
		<fmt:message key="label.anteprima_modello" />
	</c:if>
	</title>
</head>
<body>
	
	<script type="text/javascript">
		jQuery(document).ready(function(){
			//if(initUIControls){
				initUIControls();
			//}
		});
	</script>

	<span class="titoloPagina">
		<c:if test="${not empty param.codiceIstanza}">
			<fmt:message key="label.schede_dell_istanza" />	
		</c:if>
		<c:if test="${empty param.codiceIstanza}">
			<fmt:message key="label.anteprima_modello" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<c:if test="${not empty param.codiceIstanza}">
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${param.codiceIstanza}</c:param>
		</c:import>
	</c:if>
	<br class="clear" />
	<div id="subcontent">
	<spring-form:form commandName="info" name="inviodati">
		
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="info" />
		</jsp:include>
		
		<br class="clear" />
			
		<c:if test="${not empty param.codiceIstanza }">
		<script type="text/javascript">
		
		var dataChanged = false;
		<c:if test="${not empty param.dataChanged}">
			dataChanged = ${param.dataChanged}; 
		</c:if>
			var visualizzaModello= function(codiceModello){
				if(changeData()){
					doHref('viewModelliIstanza.htm?codiceIstanza=${param.codiceIstanza}&codiceModello='+codiceModello,'');
				}
			};
			function changeData(){
				if(dataChanged){
					if( confirm('<fmt:message key="javascript.confirm.dati_non_salvati_procedere" />')){
						dataChanged = false;
						return true;
					}else{
						return false;
					}
				}
				return true;
			}
			
			var scegliNuovaScheda = function(){
				if(changeData()){
					dijit.byId("nuovaScheda_Div").show(); 
				}
	
			};
			
			function salvaScheda(){
				doSubmit('salvaScheda.htm?codiceIstanza=${param.codiceIstanza}&codiceModello=${info.codiceModello}','');
			}
			
			function addModello(){
				var selectObj  = document.getElementById('dyn2Modello_hidden');				
				var codiceModello = selectObj.value;
				if(codiceModello){
					doHref('addModelloIstanza.htm?codiceIstanza=${param.codiceIstanza}&codiceModello='+codiceModello,'');
				}
				else{
					alert('<fmt:message key="javascript.alert.dyn2modellot_obbligatorio" />');
				}
			}
			
			function infoWarningMsg(){
				$('change_data_status_msg').innerHTML='Dati non salvati';
				$('change_data_status_msg').style.display='';
				
			}
		</script>
			<ul class="listaSchede">
				<c:forEach items="${info.listaModelliAttivati}" var="modellot">
					<c:set var="cssClass" value="Scheda"/>
					<c:if test="${modellot.id.fkD2mtId eq codiceModello}">
						<c:set var="cssClass" value="SchedaAttiva"/>
					</c:if>
					<li><a class="${cssClass}" href="javascript:visualizzaModello(${modellot.id.fkD2mtId});">${modellot.dyn2Modellit.descrizione}</a></li>
				</c:forEach>
				<li><a class="Scheda" href="javascript:scegliNuovaScheda();"><fmt:message key="label.nuova_scheda" /></a></li>
			</ul>
		</c:if>
		<div id="change_data_status_msg" class="warning_header" style="display: none;text-transform: uppercase;"></div>
		<div id="nuovaScheda_Div" dojoType="dijit.Dialog" style="display: none; overflow: visible !important;" title="<fmt:message key="label.nuova_scheda" />">
		
			<!-- 
			<select name="modelloDaSCegliere" id="mds_id">
				<c:forEach items="${listaModelliDaAggiungere}" var="modellot">
					<option value="${modellot.id.codice}">${modellot.descrizione}</option>
				</c:forEach>
			</select>
			 -->
				<jsp:include page="../includes/autocompletergenericoTT.jsp" >
					<jsp:param name="idElemento" value="dyn2Modello" />		
					<jsp:param name="propertyPath" value="dyn2Modellit" />				
					<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
					<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
					<jsp:param name="autocompleterAjax" value="findNewDyn2ModelliCurretSoftwareOrTT.htm?idIstanza=${param.codiceIstanza}&codicesoftware=" />
					<jsp:param name="help" value="help.modelli_archivi_base" />
					<jsp:param name="id_help" value="new_modello" /> 
				</jsp:include>
			<div id="functions">
			<ul>
				<li><a href="javascript:addModello();"><fmt:message key="button.ok" /></a></li>
			</ul>
			</div>
		</div>
		
			<br class="clear" />
			
			${modello}	
							
			</spring-form:form>
		</div>
	<div id="functions">
	<ul>
	
		<c:if test="${not empty param.codiceIstanza}">
			<c:if test="${not empty info.codiceModello}">
				<c:if test="${info.readOnlyModello eq false}">
					<li><a href="javascript:salvaScheda();"><fmt:message key="button.update" /></a></li>			
					<li><a href="javascript:doHref('eliminaScheda.htm?codiceIstanza=${param.codiceIstanza}&codiceModello=${info.codiceModello}','<fmt:message key="javascript.confirm.delete_scheda_istanza" />');"><fmt:message key="button.delete" /></a></li>
				</c:if>
			</c:if>
		</c:if>
		
		<li><a href="javascript:historyBack('');"><fmt:message key="button.back" /></a></li>
	</ul>
	</div>
</body>
</html>