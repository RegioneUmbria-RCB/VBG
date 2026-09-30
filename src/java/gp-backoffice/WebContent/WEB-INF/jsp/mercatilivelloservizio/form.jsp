<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.dettaglio_mercatilivelloservizio.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.dettaglio_mercatilivelloservizio.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatilivelloservizio/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="mercatilivelloservizio" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatilivelloservizio" />
		    </jsp:include>
			<c:if test="${mercatilivelloservizio.entity.id.codice!=null}">
			<fieldset>
		        <legend><fmt:message key="label.procedura_aggiornamento.title" /></legend>
		        <br/>
		       	<fmt:message key="label.procedura_aggiornamento_livello" /><br/>
		       	<br/><fmt:message key="help.label.procedura_aggiornamento_livello" /><br/>
				
    		 </fieldset>
    		 </c:if>
		    <br/>
			<c:set value="false" scope="page" var="isReadOnly"></c:set>
			<c:if test="${mercatilivelloservizio.entity.id.codice!=null}">
				<c:set value="true" scope="page" var="isReadOnly"></c:set>
			</c:if>
			<table border="0">
			     
	            <tr>
	                <td>
						<fmt:message key="label.giorno" />
					</td>
	                <c:if test="${codiceuso!=null}">
					<td>
						<spring-form:input id="descrizione_id" path="entity.mercatiUso.descrizione" size="70" readonly="true" />
					</td>
					</c:if>
					
					<c:if test="${codiceuso==null && codicemercato!=null}">
						<td>
							<c:forEach items="${usos}" var="uso">
								<input type="checkbox" value="${uso.id.codice}" name="mercatiusi" />&nbsp;${uso.descrizione}&nbsp;
							</c:forEach>
						</td>
					</c:if>
	            </tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td colspan="4">
						<spring-form:input id="descrizione_id" path="entity.descrizione" size="70" />
						<spring-form:errors path="entity.descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.tipologia_livello_servizio" />
					</td>
					<td>
					<c:if test="${mercatilivelloservizio.entity.id.codice==null}">
					<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="livelloServizio" />
							<jsp:param name="propertyPath" value="entity.livelloServizio" />
							<jsp:param name="pathPropertyDescription" value="entity.livelloServizio.descrizione" />
							<jsp:param name="pathPropertyCode" value="entity.livelloServizio.id.codice" />
							<jsp:param name="autocompleterAjax" value="findLivelloServizio.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_livello_servizio" />
						</jsp:include>
						<spring-form:errors path="entity.livelloServizio.descrizione" cssClass="error"/>
					</c:if>
					<c:if test="${mercatilivelloservizio.entity.id.codice!=null}">
						<spring-form:input id="descrizione_id" path="entity.livelloServizio.descrizione" size="70" readonly="true"/>
					</c:if>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.attivo" />
					</td>
					<td>
						<spring-form:checkbox id="attivo_id" path="entity.attivo" />
						<fmt:message key="hepl.mercatilivelloservizio.attivo" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.tariffa" />
					</td>
					<td>
						<spring-form:input id="tariffa_id" path="entity.tariffa" size="6" onblur="checkNumberValue(this);" readonly="${isReadOnly}"/>
						<spring-form:errors path="entity.tariffa" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.data_inizio_validita" />
					</td>
					<td>
						<spring-form:input id="data_id" path="entity.dataInizioValidita" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="${isReadOnly}"/>
						<c:if test="${!isReadOnly}">
						<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.dataInizioValidita" cssClass="error"/>
						</c:if>
						<c:set value="false" var="bloccato" scope="page"></c:set>
						<c:if test="${mercatilivelloservizio.entity.dataFineValidita!=null}">
							<c:set value="true" var="bloccato" scope="page"></c:set>
						</c:if>
						<fmt:message key="label.data_fine_validita" />
						<spring-form:input id="datafine_id" path="entity.dataFineValidita" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="${bloccato}"/>
						<c:if test="${!bloccato}">
						<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="datafine_id" textKey="label.calendar"/>
						</c:if>
						<spring-form:errors path="entity.dataFineValidita" cssClass="error"/>
					</td>
				</tr>
				<c:if test="${mercatilivelloservizio.entity.id.codice!=null}">
				<tr>
					<td><fmt:message key="label.note" /></td>
					<td>
						<spring-form:textarea id="note_id" path="entity.note" rows="3" cols="60"/>
						<spring-form:errors path="entity.tariffa" cssClass="error"/>
					</td>
				</tr>
				</c:if>
			</table>
		</spring-form:form>
	</div>
	

	<div id="functions">
		<ul>
			<c:if test="${mercatilivelloservizio.entity.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mercatilivelloservizio.entity.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','<fmt:message key="help.mercatilivelloservizio.aggiorna" />',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<%-- ><li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>  --%>
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>