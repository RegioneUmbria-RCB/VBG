<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tipimovStcAlberoproc.id.codice==null}">
			<fmt:message key="form.tipimovstcalberoproc.title.create" />
		</c:if> 
		<c:if test="${tipimovStcModelli.id.codice!=null}">
			<fmt:message key="form.tipimovstcalberoproc.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
<c:if test="${tipimovStcAlberoproc.id.codice==null}">
	<fmt:message key="form.tipimovstcalberoproc.title.create" />
</c:if> 
<c:if test="${tipimovStcAlberoproc.id.codice!=null}">
	<fmt:message key="form.tipimovstcalberoproc.title.view" />
</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="tipimovStcAlberoproc" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="tipimovStcAlberoproc" />
    </jsp:include>
    <span class="parametri">
        <fmt:message key="form.tipimovstcmapping.tipimovimento" /> : <label><c:out value="${tipimovStcAlberoproc.tipimovimento.movimento }"/></label>
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
			<td><fmt:message key="form.tipimovstcalberoproc.fkScid" /></td>
			<td><spring-form:input id="fkScid_id" path="fkScid" size="10" maxlength="10" cssStyle="text-align: right" />
				<init:help idHelp="help1" textKey="tipimovstcalberoproc.help.fkScid"/>
				<spring-form:errors path="fkScid" cssClass="error"/>
    		</td>
    	</tr>
		
	</table>
</spring-form:form>
</div>
<div id="functions">
<ul>
	<c:if test="${tipimovStcAlberoproc.id.codice==null}">
		<li><a href="javascript:doSubmit('insertAlberoproc.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>
	<c:if test="${tipimovStcAlberoproc.id.codice!=null}">
		<li><a href="javascript:doSubmit('updateAlberoproc.htm?tipimovimento.idtipomovimento=${idtipomovimento}','',document.inviodati)"><fmt:message key="button.update" /></a></li>
		<li><a href="javascript:doSubmit('deleteAlberoproc.htm?tipimovimento.idtipomovimento=${idtipomovimento}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
	</c:if>
	<li><a href="javascript:void 0;" onclick="historyBack('')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>
