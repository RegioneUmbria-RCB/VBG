<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizietipologie.id.codice==null}">
			<fmt:message key="commedilizietipologie.label.nuovo_commedilizietipologie.title" />
		</c:if> 
		<c:if test="${commedilizietipologie.id.codice!=null}">
			<fmt:message key="commedilizietipologie.label.dettaglio_commedilizietipologie.title" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizietipologie.id.codice==null}">
			<fmt:message key="commedilizietipologie.label.nuovo_commedilizietipologie.title" />
		</c:if> 
		<c:if test="${commedilizietipologie.id.codice!=null}">
			<fmt:message key="commedilizietipologie.label.dettaglio_commedilizietipologie.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="commedilizietipologie" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizietipologie" />
		    </jsp:include>
			<table>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td>
						<spring-form:input id="descrizione_id" path="descrizione" size="70" />
						<spring-form:errors path="descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="commedilizietipologie.label.flagdisabilita" />
					</td>
					<td>
						<spring-form:checkbox id="flagDisabilita_id" path="flagDisabilita" value="1" />
						<fmt:message key="commedilizietipologie.label.flagdisabilita.help" />
						<spring-form:errors path="flagDisabilita" cssClass="error"/>
					</td>
				</tr>			
				<tr>
					<td>
						<fmt:message key="commedilizietipologie.label.numeroprogressivo" />
					</td>
					<td>
						<spring-form:input id="numeroprogressivo_id" path="numeroprogressivo" size="10" />
						<spring-form:errors path="numeroprogressivo" cssClass="error"/>
					</td>
				</tr>	
				<tr>
					<td>
						<fmt:message key="label.amministrazione" />
					</td>
					<td>
						<spring-form:input id="amministrazione_id" path="amministrazione.amministrazione" size="50" cssClass="searchbox" onchange="checkValue(this,'amministrazione_hidden')" onkeydown="javascript:return searchAll(this,event)"/>
						<init:autocompleter methodAjax="findAmministrazioni.htm?tutteLeAmministrazioni=false" idHidden="amministrazione_hidden" idInput="amministrazione_id" inputTitleKey="label.ricerca_amministrazione"></init:autocompleter>
						<spring-form:errors path="amministrazione" cssClass="error"/> 
						<spring-form:hidden id="amministrazione_hidden" path="amministrazione.id.codice"  />
					</td>
				</tr>
			</table>
			<script type='text/javascript'>
				$('descrizione_id').focus();
			</script>	
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${commedilizietipologie.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${commedilizietipologie.id.codice!=null}">
				<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
			</c:if>
			<li><a href="javascript:historyBack('');"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	
	<c:if test="${(commedilizietipologie.id.codice != null)}">
	<br/>
	<fieldset><legend><fmt:message key="commedilizietipologie.label.tipologiedett" /></legend>
	
			<form name="dettaglioForm" action="view.htm">
					<jmesa:springTableFacade
						id="tipologiedett_id" 
						items="${commedilizietipologie.commedilizieTipologiedetts}" 
						var="tipologiedett_var"
						maxRows="10" 
						exportTypes="" 
						stateAttr="restore" >
						<jmesa:htmlTable>
							<jmesa:htmlRow>												
								<jmesa:htmlColumn property="tipimovimento.movimento" titleKey="label.tipimovimento" width="80%" filterable="false" sortable="false">
									${tipologiedett_var.tipimovimento.movimento} (${tipologiedett_var.tipimovimento.id.tipomovimento})
								</jmesa:htmlColumn>
								<jmesa:htmlColumn property="" titleKey="label.elimina" sortable="false" filterable="false" width="5%">
									<a class="eliminaRiga" href="javascript:doSubmit('deleteDettaglio.htm?codice=${commedilizietipologie.id.codice}&tipomovimento=${tipologiedett_var.tipimovimento.id.tipomovimento}','<fmt:message key="javascript.confirm.delete" />',document.inviodati);" 
										title="<fmt:message key="label.elimina" /> ${tipologiedett_var.tipimovimento.movimento}">
									<label><fmt:message key="label.elimina.image" /></label></a>
								</jmesa:htmlColumn>
							</jmesa:htmlRow>
						</jmesa:htmlTable>
					</jmesa:springTableFacade>
					<input type="hidden" value="${commedilizietipologie.id.codice}" name="codice"/>
				</form>
		<div id="functions">
		<ul>			
			<li><a href="javascript:doSubmit('createDettaglio.htm?codice=${commedilizietipologie.id.codice}','',document.inviodati)">
					<fmt:message key="button.new" /></a>
			</li>		
		</ul>
		</div>
		<br /> 
	</fieldset>
	</c:if>
	
</body>
</html>