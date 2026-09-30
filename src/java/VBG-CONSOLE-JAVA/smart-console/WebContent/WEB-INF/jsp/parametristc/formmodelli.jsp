<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovStcModelli.id.codice==null}">
			<fmt:message key="form.tipimovstcmodelli.title.create" />
		</c:if> 
		<c:if test="${tipimovStcModelli.id.codice!=null}">
			<fmt:message key="form.tipimovstcmodelli.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipimovStcModelli.id.codice==null}">
	<fmt:message key="form.tipimovstcmodelli.title.create" />
</c:if> 
<c:if test="${tipimovStcModelli.id.codice!=null}">
	<fmt:message key="form.tipimovstcmodelli.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="tipimovStcModelli" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipimovStcModelli" />
    </jsp:include>
    <span class="parametri">
        <fmt:message key="form.tipimovstcmapping.tipimovimento" /> : <label><c:out value="${tipimovStcModelli.tipimovimento.movimento }"/></label>
    </span>
	<table>
		<tr>
			<td><fmt:message key="form.tipimovstcmapping.amministrazioni" /></td>
			<td><spring-form:select id="amministrazioni_id" path="amministrazioni.id.codice" >
				<spring-form:options items="${amministrazioniList}" itemLabel="amministrazione" itemValue="id.codice"/>
			</spring-form:select>
			<spring-form:errors path="amministrazioni" cssClass="error"/></td>
		</tr>
		<tr>
			<td><fmt:message key="form.tipimovstcmodelli.modello" /></td>
			<td><spring-form:input id="dyn2Modellit_id" path="dyn2Modellit.descrizione" size="55" cssClass="searchbox" onchange="checkValue(this,'dyn2Modellit_hidden')" onkeydown="javascript:return searchAll(this,event)" />
				<init:autocompleter methodAjax="findAllDyn2Modelli.htm" idHidden="dyn2Modellit_hidden" idInput="dyn2Modellit_id" inputTitleKey="label.ricerca_modello"/>
				<spring-form:hidden id="dyn2Modellit_hidden" path="dyn2Modellit.id.codice"/>
				<spring-form:errors path="dyn2Modellit" cssClass="error"/>
    		</td>
    	</tr>
		
	</table>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipimovStcModelli.id.codice==null}">
		<li><a href="javascript:doSubmit('insertModelli.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipimovStcModelli.id.codice!=null}">
		<li><a href="javascript:doSubmit('updateModelli.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('deleteModelli.htm?tipimovimento.idtipomovimento=${idtipomovimento}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:void 0;" onclick="historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
