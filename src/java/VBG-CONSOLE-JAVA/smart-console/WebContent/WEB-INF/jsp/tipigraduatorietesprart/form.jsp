<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipigraduatorietesprart.entity.id.codice==null}">
			<fmt:message key="label.nuova_configurazione_art_temp.title" />
		</c:if> 
		<c:if test="${tipigraduatorietesprart.entity.id.codice!=null}">
			<fmt:message key="label.modifica_configurazione_art_temp.title" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipigraduatorietesprart.entity.id.codice==null}">
	<fmt:message key="label.nuova_configurazione_art_temp.title" />
</c:if> 
<c:if test="${tipigraduatorietesprart.entity.id.codice!=null}">
	<fmt:message key="label.modifica_configurazione_art_temp.title" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<br />
<div id="subcontent">
    <span class="parametri"><fmt:message key="form.tipigraduatoriet.title.prefix"/><label> ${tipigraduatorietesprart.entity.tipigraduatoriet.descrizione}</label></span>
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipigraduatorietesprart" />
	</jsp:include>
	
	<spring-form:form commandName="tipigraduatorietesprart" name="inviodati">
	<br />
	<table>
			 <tr>
				<td>
					<fmt:message key="label.modello" />
				</td>
				<td>
				<jsp:include page="../includes/autocompletergenericoTT.jsp" >
					<jsp:param name="idElemento" value="dyn2Modellit" />		
					<jsp:param name="propertyPath" value="dyn2Modellit" />				
					<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
					<jsp:param name="pathPropertyCode" value="dyn2Modellit.id.codice" />
					<jsp:param name="autocompleterAjax" value="findDyn2ModelliCurretSoftwareOrTT.htm?codicesoftware=" />
					<jsp:param name="id_help" value="help_modello" />	
					<jsp:param name="help" value="help.modelli_archivi_base" />
				</jsp:include>
				</td>
			</tr>
			
			<tr>
				<td>
					<fmt:message key="label.campo_Da" />
				</td>
				<td>
				<script type="text/javascript">
						function filterDa(element, entry) {
							return entry + "&codiceModello=" + document.getElementById("dyn2Modellit_hidden").value;								
						}
				</script>
				<jsp:include page="../includes/autocompletergenericoTT.jsp" >
					<jsp:param name="idElemento" value="campiDa" />		
					<jsp:param name="propertyPath" value="entity.campiDa" />				
					<jsp:param name="pathPropertyDescription" value="entity.campiDa.nomecampo" />
					<jsp:param name="pathPropertyCode" value="entity.campiDa.id.codice" />
					<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
					<jsp:param name="ajaxCallBack" value="filterDa" />
					<jsp:param name="id_help" value="help_campo__" />	
				</jsp:include>
				</td>
			</tr>
			
			<tr>
				<td>
					<fmt:message key="label.campo_A" />
				</td>
				<td>
				<script type="text/javascript">
						function filterA(element, entry) {
							return entry + "&codiceModello=" + document.getElementById("dyn2Modellit_hidden").value;								
						}
				</script>
				<jsp:include page="../includes/autocompletergenericoTT.jsp" >
					<jsp:param name="idElemento" value="campiA" />		
					<jsp:param name="propertyPath" value="entity.campiA" />				
					<jsp:param name="pathPropertyDescription" value="entity.campiA.nomecampo" />
					<jsp:param name="pathPropertyCode" value="entity.campiA.id.codice" />
					<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
					<jsp:param name="ajaxCallBack" value="filterA" />
					<jsp:param name="id_help" value="help_campo_" />	
				</jsp:include>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.campi_posteggio" />
				</td>
				<td>
				<script type="text/javascript">
						function filterPos(element, entry) {
							return entry + "&codiceModello=" + document.getElementById("dyn2Modellit_hidden").value;								
						}
				</script>
				<jsp:include page="../includes/autocompletergenericoTT.jsp" >
					<jsp:param name="idElemento" value="campiPosteggio" />		
					<jsp:param name="propertyPath" value="entity.campiPosteggio" />				
					<jsp:param name="pathPropertyDescription" value="entity.campiPosteggio.nomecampo" />
					<jsp:param name="pathPropertyCode" value="entity.campiPosteggio.id.codice" />
					<jsp:param name="autocompleterAjax" value="findDyn2CampiByModelloAndCurrentSoftwareOrTT.htm?codiceSoftware=" />
					<jsp:param name="ajaxCallBack" value="filterPos" />
					<jsp:param name="id_help" value="help_campo" />	
				</jsp:include>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="label.multiplo" /></td>
				<td><spring-form:checkbox path="entity.flagMultiplo" value="1" />
				<init:help idHelp="help_flagMultiplo_id" textKey="label.tipigradtcfgrotazione.campo_multiplo.help" />
				</td>
			</tr>
	</table>
</spring-form:form>
</div>

<div id="functions">
<ul>
	<c:if test="${tipigraduatorietesprart.entity.id.codice==null}">
		<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipigraduatorietesprart.entity.id.codice!=null}">
		<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
