<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
		<fmt:message key="label.gruppiistruttoriresp.title" />
</title>
</head>
	<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.gruppiistruttoriresp.title" />
	</span>
	
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<div id="subcontent"><spring-form:form commandName="gruppiistruttoriresp" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="gruppiistruttoriresp" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../gruppiistruttoriresp/view" />
	    	<jsp:param name="qs" value="codice%3D${gruppiistruttoriresp.id.codice}"/>
	    </jsp:include>
		<table>
				<tr>
					    <td>
							<fmt:message key="label.istruttore" />
						</td>
						<td colspan="3">
							<spring-form:input id="responsabileistruttoria_id" path="responsabili.responsabile" cssClass="searchbox" onchange="checkValue(this,'responsabileistruttoria_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
							<init:autocompleter methodAjax="findResponsabiliIstruttoria.htm" idHidden="responsabileistruttoria_hidden" idInput="responsabileistruttoria_id" inputTitleKey="label.ricerca_responsabile"/>
							<spring-form:errors path="responsabili.responsabile" cssClass="error"/> 
							<spring-form:hidden id="responsabileistruttoria_hidden" path="responsabili.id.codice"  />
						</td>
						<%-- <init:help idHelp="help_responsabili" textKey="gruppiistruttoriresp.help.responsabili"/> --%>
				</tr>
		</table>
	<script type='text/javascript'>
	//$('denominazione_id').focus();
</script>
</spring-form:form>
</div>
<div id="functions">
	<ul>
		<c:if test="${gruppiistruttoriresp.id.codice==null}">
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${gruppiistruttoriresp.id.codice!=null}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a title="<fmt:message key="help.resposabiliassenze.descrizione" />" href="javascript:historySet('${_urlback}', '../responsabiliassenze/list.htm?codice=${gruppiistruttoriresp.responsabili.id.codice}', '')"> <fmt:message key="button.assenze"/></a></li>
			<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>