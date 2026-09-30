<%@ include file="../includes/taglibs.jsp"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>
		<fmt:message key="label.gruppiistruttori.title" />
</title>
</head>
	<body>
	<span class="titoloPagina"> 
		<fmt:message key="label.gruppiistruttori.title" />
	</span>
	
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../gruppiistruttori/view" />
	 </jsp:include>
	<div id="subcontent"><spring-form:form commandName="gruppiistruttori" name="inviodati">
		<jsp:include page="../includes/displayGlobalMessages.jsp">
			<jsp:param name="commandName" value="gruppiistruttori" />
		</jsp:include>
		<table>
				<tr>			
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="desc_id" path="descrizione" size="70" /> 
						<spring-form:errors path="descrizione" cssClass="error" />
					</td>
				</tr>
		</table>
	<script type='text/javascript'>
	//$('denominazione_id').focus();
</script>
</spring-form:form>
</div>
<div id="functions">
	<ul>
		<c:if test="${gruppiistruttori.id.codice==null}">
			<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
		</c:if>
		<c:if test="${gruppiistruttori.id.codice!=null}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			<li><a href ="javascript:historySet('${_urlback}','../gruppiistruttoriresp/list.htm?codice=${gruppiistruttori.id.codice}');"><fmt:message key="button.istruttori" /></a></li>
			<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
		</c:if>
		<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
</body>
</html>