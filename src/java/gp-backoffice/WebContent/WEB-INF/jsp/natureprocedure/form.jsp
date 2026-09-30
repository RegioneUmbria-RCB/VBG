<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.service.NatureProcedureService"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		Procedure – Configurazione
	</title>
</head>
<body>
	<span class="titoloPagina">
		Procedure – Configurazione
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="natureProcedure" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="natureProcedure" />
		    </jsp:include>
		    
		    <div>
		    
La seguente configurazione viene utilizzata nel caso di acquisizione di una nuova pratica presentata da On Line.<br />
Se la nuova pratica ha una NATURA (Comunicazione, SCIA, Ordinario) diversa da quella associata all’intervento (caso ad esempio della SCIA condizionata) la procedura associata alla pratica non sarà quella configurata nell’intervento ma sarà quella configurata in questa tabella.
		    
		    </div>
		    
			<table width="100%" >
				<c:if test="${natureProcedure.id.codice!=null}">
				<tr>
					<td>
						<fmt:message key="label.codice" />
					</td>
					<td>
						<spring-form:input id="codice_id" path="id.codice" size="6" readonly="true"/>
					</td>
				</tr>
				</c:if>
				<tr>
					<td>
						Procedura
					</td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="tipiprocedure_id" />				
							<jsp:param name="propertyPath" value="tipiprocedure" />			
							<jsp:param name="pathPropertyDescription" value="tipiprocedure.procedura" />
							<jsp:param name="pathPropertyCode" value="tipiprocedure.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiprocedure.htm?soloConMovimentoAvvio=true" />
							<jsp:param name="titleKey" value="label.ricerca_tipiprocedure" />
						</jsp:include>
					</td>
				</tr>
				<tr>
					<td>
						Codice natura base
					</td>
					<td>
						<spring-form:select  path="codicenaturabase">
						 	<spring-form:option value=""><fmt:message key="label.seleziona" /></spring-form:option>
							<spring-form:option value="<%=NatureProcedureService.NATURA_BASE_ENUM.comunicazione%>"><%=NatureProcedureService.NATURA_BASE_ENUM.comunicazione%></spring-form:option>
							<spring-form:option value="<%=NatureProcedureService.NATURA_BASE_ENUM.scia%>"><%=NatureProcedureService.NATURA_BASE_ENUM.scia%></spring-form:option>
							<spring-form:option value="<%=NatureProcedureService.NATURA_BASE_ENUM.ordinario%>"><%=NatureProcedureService.NATURA_BASE_ENUM.ordinario%></spring-form:option>		                   
						</spring-form:select>
						<spring-form:errors path="codicenaturabase" cssClass="error"/>
					</td>
				</tr>

			</table>
			
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${natureProcedure.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${natureProcedure.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>