<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${tempificazioni.id.codice==null}">
			<fmt:message key="tempificazioni.label.nuovo_tempificazioni.title" />
		</c:if> 
		<c:if test="${tempificazioni.id.codice!=null}">
			<fmt:message key="tempificazioni.label.dettaglio_tempificazioni.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${tempificazioni.id.codice==null}">
			<fmt:message key="tempificazioni.label.nuovo_tempificazioni.title" />
		</c:if> 
		<c:if test="${tempificazioni.id.codice!=null}">
			<fmt:message key="tempificazioni.label.dettaglio_tempificazioni.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="tempificazioni" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="tempificazioni" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="tempificazioni.label.tempificazione" />
					</td>
					<td>
						<spring-form:input id="tempificazione_id" path="tempificazione" size="50" />
						<spring-form:errors path="tempificazione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="tempificazioni.label.ordine" />
					</td>
					<td>
						<spring-form:input id="ordine_id" path="ordine" size="2" maxlength="2"/>
						<spring-form:errors path="ordine" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('tempificazione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${tempificazioni.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${tempificazioni.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>