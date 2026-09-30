<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${livelloservizio.id.codice==null}">
			<fmt:message key="livelloservizio.label.nuovo_livelloservizio.title" />
		</c:if> 
		<c:if test="${livelloservizio.id.codice!=null}">
			<fmt:message key="livelloservizio.label.dettaglio_livelloservizio.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${livelloservizio.id.codice==null}">
			<fmt:message key="livelloservizio.label.nuovo_livelloservizio.title" />
		</c:if> 
		<c:if test="${livelloservizio.id.codice!=null}">
			<fmt:message key="livelloservizio.label.dettaglio_livelloservizio.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="livelloservizio" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="livelloservizio" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="livelloservizio.label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />						
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="livelloservizio.label.segnaposto" />
					</td>
					<td>
						<spring-form:input id="segnaposto_id" path="segnaposto" size="70" />						
						<spring-form:errors path="segnaposto" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				
			(function ($, namespaceContainer){

				var campoSegnaposto = $('#segnaposto_id');
				
				campoSegnaposto.on('blur', function() {
					campoSegnaposto.val(campoSegnaposto.val().toUpperCase());
				});
			})(jQuery, window);
			
				/* $('descrizione_id').focus(); */
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${livelloservizio.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${livelloservizio.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>