<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title> 
	ELIMINATA<%--fmt:message key="form.mercatiConfigurazione.identaut.parametri" /--%>
	</title>
</head>
<body>
ELIMINATA
<%-- 
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../mercaticonfigurazione/view" />
</jsp:include>
<span class="titoloPagina">
	<fmt:message key="form.mercatiConfigurazione.identaut.parametri" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="mercatiConfigurazione" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="mercatiConfigurazione" />
    </jsp:include>
    <span class="parametri"><fmt:message key="form.mercatiConfigurazione.modello" />: <label><c:out value="${mercatiConfigurazione.dyn2Modellit.descrizione}"/></label></span>
    <div class="titoloSezione"><fmt:message key="form.mercatiConfigurazione.identaut.parametri" /></div>
    <br />
    <fmt:message key="form.mercatiConfigurazione.ordine" />
    <br />
    <c:forEach items="${mercatiConfigurazione.mercatiIdentAuts}" var="ide" varStatus="ide_status">
        <script type='text/javascript'>
		function setHiddenFielddyn2Campi${ide_status.index}(inputField,listItem){
			var a = listItem.id;
			document.getElementById('dyn2Campi_id${ide_status.index}').value = inputField.value;
			document.getElementById('dyn2Campi_hidden${ide_status.index}').value = a;
			$('dyn2Campi_id_choices${ide_status.index}').fade();	 
		}
		</script>
    	<spring-form:input path="mercatiIdentAuts[${ide_status.index}].ordine" maxlength="2" size="2"/>
    	<spring-form:textarea rows="1" cols="62" id="dyn2Campi_id${ide_status.index}" path="mercatiIdentAuts[${ide_status.index}].dyn2Campi.nomecampo" cssClass="searchbox" onchange="checkValue(this,'dyn2Campi_hidden${ide_status.index}')" onkeydown="javascript:return searchAll(this,event)" />
		<init:autocompleter afterUpdateElement="setHiddenFielddyn2Campi${ide_status.index}" methodAjax="findDyn2Campi.htm?idModello=${mercatiConfigurazione.dyn2Modellit.id.codice}" idHidden="dyn2Campi_hidden${ide_status.index}" idInput="dyn2Campi_id${ide_status.index}" inputTitleKey="label.ricerca_campo"/>
		<spring-form:hidden id="dyn2Campi_hidden${ide_status.index}" path="mercatiIdentAuts[${ide_status.index}].dyn2Campi.id.codice" />
    	<br />
    </c:forEach>
    <c:set var="next" value="${fn:length(mercatiConfigurazione.mercatiIdentAuts)}"></c:set>
   	<input type="text" id="mercatiIdentAuts[${next}].ordine" name="mercatiIdentAuts[${next}].ordine" maxlength="2" size="2" value="${next}"/>
   	<spring-form:textarea rows="1" cols="62" id="dyn2Campi_id${next}" path="mercatiIdentAuts[${next}].dyn2Campi.nomecampo" cssClass="searchbox" onchange="checkValue(this,'dyn2Campi_hidden${next}')" onkeydown="javascript:return searchAll(this,event)"/>
	<script type='text/javascript'>
		document.getElementById('dyn2Campi_id${next}').focus();
		function setHiddenFielddyn2Campi${next}(inputField,listItem){
			var a = listItem.id;
			document.getElementById('dyn2Campi_id${next}').value = inputField.value;
			document.getElementById('dyn2Campi_hidden${next}').value = a;
			$('dyn2Campi_id_choices${next}').fade();	 
		}	
	</script>
	<init:autocompleter afterUpdateElement="setHiddenFielddyn2Campi${next}" methodAjax="findDyn2Campi.htm?idModello=${mercatiConfigurazione.dyn2Modellit.id.codice}" idHidden="dyn2Campi_hidden${next}" idInput="dyn2Campi_id${next}" inputTitleKey="label.ricerca_campo"/>
	<spring-form:hidden id="dyn2Campi_hidden${next}" path="mercatiIdentAuts[${next}].dyn2Campi.id.codice" />
   	<br />
    </spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('updateMercatiIdentAut.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<li><a href="javascript:doHref('view.htm','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
--%>
</body>
</html>