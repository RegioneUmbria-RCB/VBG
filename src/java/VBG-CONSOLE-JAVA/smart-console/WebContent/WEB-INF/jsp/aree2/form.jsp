<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${aree2.id.codice==null}">
			<fmt:message key="aree2.label.nuovo_aree2.title" />
		</c:if> 
		<c:if test="${aree2.id.codice!=null}">
			<fmt:message key="aree2.label.dettaglio_aree2.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${aree2.id.codice==null}">
			<fmt:message key="aree2.label.nuovo_aree2.title" />
		</c:if> 
		<c:if test="${aree2.id.codice!=null}">
			<fmt:message key="aree2.label.dettaglio_aree2.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="aree2" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="aree2" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="aree2.label.denominazione" />
					</td>
					<td>
						<spring-form:input id="denominazione_id" path="denominazione" size="70" />
						<spring-form:errors path="denominazione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="aree2.label.proprieta" />
					</td>
					<td>
						<spring-form:input id="proprieta_id" path="proprieta" size="70" />
						<spring-form:errors path="proprieta" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="aree2.label.localita" />
					</td>
					<td>
						<spring-form:input id="localita_id" path="localita" size="70" />
						<spring-form:errors path="localita" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:input id="note_id" path="note" size="70" />
						<spring-form:errors path="note" cssClass="error"/>
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('denominazione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${aree2.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${aree2.id.codice!=null}">
				<c:if test="${aree2.id.codice>0}">
					<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				</c:if>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>