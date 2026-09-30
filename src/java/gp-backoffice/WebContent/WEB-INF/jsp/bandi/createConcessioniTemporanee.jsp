<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.title.rilascia_concessioni" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.title.rilascia_concessioni" />
	</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../bandi/viewGraduatoria" />
</jsp:include>
<div id="subcontent">
<spring-form:form commandName="concessioni" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="concessioni" />
	</jsp:include>
	<table>
	    <tr><td colspan="2"><fmt:message key="label.help_tipologiaregistri" /></td></tr>
		<tr>
			<td><fmt:message key="label.registro" /></td>
			<td>  <spring-form:input id="registro_id" path="tipologiaregistri.trDescrizione" cssClass="searchbox" onchange="checkValue(this,'registro_hidden')" onkeydown="javascript:return searchAll(this,event)" size="70"/>
					<init:autocompleter methodAjax="findTipologiaRegistri.htm"   idHidden="registro_hidden"  idInput="registro_id" inputTitleKey="label.ricerca_tipo_registro"/>
					<spring-form:errors	path="tipologiaregistri.trDescrizione" cssClass="error"	/>
					<spring-form:hidden id="registro_hidden" path="tipologiaregistri.id.codice"  />
			</td>				
	    </tr>
	</table>
</spring-form:form>
</div>

<div id="functions">
<ul>			
	 <li><a href="javascript:doSubmit('assegnaConcessioniTemporaneeAlleIstanzeInGratuatoria.htm?codice=${graduatoriet.id.codice}','',document.inviodati)"><fmt:message key="button.rilascia_concessioni" /></a></li>
	 <li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>		
</ul>
</div>
</body>
</html>
