<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${documenticontabilita.entity.id.codice==null}">
			<fmt:message key="label.nuovo_documento_contabilita" />
		</c:if> 
		<c:if test="${documenticontabilita.entity.id.codice!=null}">
			<fmt:message key="label.modifica_documento_contabilita" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${documenticontabilita.entity.id.codice==null}">
			<fmt:message key="label.nuovo_documento_contabilita"/>
		</c:if> 
		<c:if test="${documenticontabilita.entity.id.codice!=null}">
			<fmt:message key="label.modifica_documento_contabilita"/>
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">

		<spring-form:form commandName="documenticontabilita" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="documenticontabilita" />
		    </jsp:include>
			<table>
				<tr>
					<td><fmt:message key="label.documento" /></td>
					<td>
						<spring-form:input id="nome_doc_id" path="entity.nomedocumento" size="60"/>
						<spring-form:errors path="entity.nomedocumento" cssClass="error"/>  
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.descrizione" /></td>
					<td>
						<spring-form:textarea path="entity.documento" rows="5" cols="62"></spring-form:textarea>
						<spring-form:errors path="entity.documento" cssClass="error"/>  
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.da" /></td>
					<td>	
						<spring-form:input id="data_id" path="entity.data" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.data" cssClass="error"/> 
				    </td>
				 </tr> 
				 <tr>
				 	<td><fmt:message key="label.documento" /></td>
					<td>
						<jsp:include page="../includes/oggetti.jsp">
						<jsp:param name="idElemento" value="oggettoIdCodice" />
						<jsp:param name="codiceOggetto" value="${documenticontabilita.entity.oggetti.id.codice}" />
						<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
						<jsp:param name="nomefileId" value="oggetto_nomefile" />					
						</jsp:include> 
						<spring-form:hidden path="entity.oggetti.id.codice" id="oggetto_id_codice" />
						<spring-form:hidden path="entity.oggetti.nomefile" id="oggetto_nomefile" />
						<spring-form:errors path="entity.oggetti" cssClass="error"/>
					</td>
				 </tr>  
				
			</table>
			<script type='text/javascript'>
				$('nome_doc_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${documenticontabilita.entity.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${documenticontabilita.entity.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<%--<li><a href="javascript:doHref('list.htm?codiceCommissione=${commedilizieallegati.commissioniedilizieT.id.codice }','')"><fmt:message key="button.back" /></a></li> --%>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>