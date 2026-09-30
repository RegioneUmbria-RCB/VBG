<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
	<fmt:message key="label.dettaglio_stp_tipologie_endo2.title" />
</title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.dettaglio_stp_tipologie_endo2.title" /></span>
<jsp:include page="../includes/history.jsp">
    <jsp:param name="path" value="../stptipologieendo2/view" />
</jsp:include>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent"><spring-form:form commandName="stptipologieendo2" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp">
		<jsp:param name="commandName" value="stptipologieendo2" />
	</jsp:include>
	<table>
		<tr>
			<td><fmt:message key="label.descrizione" /></td>
			<td><spring-form:input id="descrizione_id" path="descrizione" size="50" readonly="true" /> 
			<spring-form:errors path="descrizione" cssClass="error" /></td>
		</tr>
		<tr>
			<td><fmt:message key="label.azione" /></td>
			<td>
				<spring-form:select  id="lista_azioni_id" path="azioni.azId" >
				    <spring-form:options items="${azionis}" itemLabel="azDescrizione" itemValue="azId" />
				</spring-form:select>
			</td>
		</tr>
		
						
		

	</table>
	<script type='text/javascript'>
	$('descrizione_id').focus();
</script>
</spring-form:form></div>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>