<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.verbale_cds" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.verbale_cds" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	     
		<spring-form:form commandName="cdsatti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="cdsatti" />
		    </jsp:include>
		    <c:import url="/ajax/dettaglioIstanza.htm">
				<c:param name="codIstanza">${cds.istanze.id.codice}</c:param>
			</c:import>
			<br class="clear" /><br class="clear" />
			<table>
				<tr>
					<td>
						<fmt:message key="label.data_incontro" />
					</td>
					<td>
						<spring-form:input  id="data_id" path="data" size="10" maxlength="10" onblur="isValidDate(this,true);"/> 
						<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
			   			<spring-form:errors path="data" cssClass="error" />
					</td>
				    <td><fmt:message  key="label.orario_incontro" /></td>
					<td>
						<spring-form:input id="orario_incontro_id" path="ora" size="6" maxlength="5" onblur="isValidOra(this,true);" />
						<spring-form:errors path="ora" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="label.documento_verbale" /></td>
					<td colspan="3">
						<jsp:include page="../includes/oggetti.jsp" >
			       			<jsp:param name="idElemento" value="oggettoIdCodice" />
			   				<jsp:param name="codiceOggetto" value="${cdsatti.oggetti.id.codice}" />
			   				<jsp:param name="codiceOggettoId" value="oggetto_id_codice" />
			   				<jsp:param name="nomefileId" value="oggetto_nomefile" />
		    			</jsp:include>
		    			<spring-form:hidden path="oggetti.id.codice" id="oggetto_id_codice"/>
		    			<spring-form:hidden path="oggetti.nomefile" id="oggetto_nomefile"/>
		    			<spring-form:errors path="oggetti" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message  key="label.note" /></td>
					<td colspan="3">
						<spring-form:textarea id="note_id" path="note" cols="70" rows="5" />
						<spring-form:errors path="note" cssClass="error"/>
					</td>
				</tr>
				
				 
				<tr>
					<td>
						<fmt:message key="label.chiusura_atto" />
					</td>
					<td>
						<spring-form:checkbox path="chiusa" value="<%=WebConstants.S%>" />
						<fmt:message key="label.se_chiusa" />
			   			<spring-form:errors path="chiusa" cssClass="error" />
					</td>
					<%--
				    <td><fmt:message  key="label.orario_seconda_convocazione" /></td>
					<td>
						<spring-form:checkbox path="" />
						<spring-form:errors path="oraconvocazione2" cssClass="error"/>
					</td>
					--%>
				</tr>
				
				
			</table>
			<script type='text/javascript'>
				$('data_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${cdsatti.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert"/></a></li>
			</c:if>
			<c:if test="${cdsatti.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update"/></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm?codiceCds=${cds.id.codice}','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>