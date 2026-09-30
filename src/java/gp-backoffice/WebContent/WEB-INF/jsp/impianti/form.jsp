<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${impianti.id.codice==null}">
			<fmt:message key="impianti.label.nuovo_impianti.title" />
		</c:if> 
		<c:if test="${impianti.id.codice!=null}">
			<fmt:message key="impianti.label.dettaglio_impianti.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${impianti.id.codice==null}">
			<fmt:message key="impianti.label.nuovo_impianti.title" />
		</c:if> 
		<c:if test="${impianti.id.codice!=null}">
			<fmt:message key="impianti.label.dettaglio_impianti.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="impianti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="impianti" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="impianto_id" path="impianto" size="90" />
						<spring-form:errors path="impianto" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td>
						<spring-form:textarea id="note_id" path="note" cols="70" rows="4" />
						<spring-form:errors path="note" cssClass="error"/>
					</td>
				</tr>				
			</table>
			<script type='text/javascript'>
				$('impianto_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${impianti.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${impianti.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
	<c:if test="${(impianti.id.codice != null)}">
	<br/>
	<fieldset><legend><fmt:message key="label.tipiprocedure" /></legend>
	
			<form name="dettaglioForm" action="view.htm">
					<jmesa:springTableFacade
						id="impiantiprocedure_id" 
						items="${impianti.impiantiprocedures}" 
						var="impiantiprocedure_var"
						maxRows="10" 
						exportTypes="" 
						stateAttr="restore" >
						<jmesa:htmlTable>
							<jmesa:htmlRow>												
								<jmesa:htmlColumn property="tipiprocedure.procedura" titleKey="label.tipiprocedure.procedura" width="80%" filterable="false" sortable="false" />
								<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
									<a class="eliminaRiga" href="javascript:doSubmit('deleteDettaglio.htm?codice=${impianti.id.codice}&tipoprocedura=${impiantiprocedure_var.tipiprocedure.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" 
										title="<fmt:message key="label.elimina" /> ${impiantiprocedure_var.tipiprocedure.procedura}">
									<label><fmt:message key="label.elimina.image" /></label></a>
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
					</jmesa:springTableFacade>
					<input type="hidden" value="${impianti.id.codice}" name="codice"/>
				</form>
		<div id="functions">
		<ul>			
			<li><a href="javascript:doSubmit('createDettaglio.htm?codice=${impianti.id.codice}','',document.inviodati)">
					<fmt:message key="button.new" /></a>
			</li>		
		</ul>
		</div>
		<br /> 
	</fieldset>
	</c:if>
	
</body>
</html>