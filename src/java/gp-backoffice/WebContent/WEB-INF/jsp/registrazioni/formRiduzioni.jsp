<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${command.regRiduzioneAccertamento.id.codice==null}">
			<fmt:message key="form.registrazioni.riduzione.title.create" />
		</c:if> 
		<c:if test="${command.regRiduzioneAccertamento.id.codice!=null}">
			<fmt:message key="form.registrazioni.riduzione.title.view" />
		</c:if>
	</title>
</head>
<body>
<span class="titoloPagina">
	<c:if test="${command.regRiduzioneAccertamento.id.codice==null}">
		<fmt:message key="form.registrazioni.riduzione.title.create" />
	</c:if> 
	<c:if test="${command.regRiduzioneAccertamento.id.codice!=null}">
		<fmt:message key="form.registrazioni.riduzione.title.view" />
	</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<div id="subcontent">
	<spring-form:form  commandName="command" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="command" />
    </jsp:include>
    
    <fieldset>
    <legend><fmt:message key="form.registrazioni.dettaglioregistrazione"/></legend>
	<table border="0">
		<c:if test="${command.regRiduzioneAccertamento.id.codice!=null}">
		<tr>
			<td><fmt:message key="form.registrazioni.progressivo" /></td>
			<td><spring-form:input id="progressivo_id" path="regRiduzioneAccertamento.progressivo" size="12" maxlength="7" readonly="true" tabindex="1"/>
			<spring-form:errors path="regRiduzioneAccertamento.progressivo" cssClass="error"/></td>
		</tr>
		</c:if>
		<tr>
			<td><fmt:message key="form.registrazioni.dataregistrazione" /></td>
			<td>
				<spring-form:input id="dataregistrazione_id" path="regRiduzioneAccertamento.dataRegistrazione" size="10" maxlength="10" onblur="isValidDate(this,true);" tabindex="2"/> 
				<init:calendar imagePath="/images/cal.gif" idImage="caldataregistrazione" idInput="dataregistrazione_id" textKey="label.calendar"/>
				<spring-form:errors	path="regRiduzioneAccertamento.dataRegistrazione" cssClass="error" />
		   		<spring-form:hidden id="datasistema_id" path="regRiduzioneAccertamento.dataSistema"/> 
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.registrazioniCausali" /></td>
			<td>
				<spring-form:select id="regCausali" path="regRiduzioneAccertamento.registrazioniCausali.id.codice"  onchange="assegna(this);" tabindex="3">
					<spring-form:options items="${registrazioniCausaliList}" itemValue="id.codice"	itemLabel="descrizione" />
				</spring-form:select> 
				<spring-form:errors path="regRiduzioneAccertamento.registrazioniCausali" cssClass="error" />
				
			</td>
		</tr>		
		<tr>
			<td><fmt:message key="form.registrazioni.mercati" /></td>
			<td>
				${command.regRiduzioneAccertamento.mercatiD.mercati.descrizione}
			</td>
		</tr>
		
		<tr>
			<td><fmt:message key="form.registrazioni.mercati.posteggio" /></td>
			<td>
				${command.regRiduzioneAccertamento.mercatiD.codiceposteggio }
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.mercatiUso" /></td>
			<td> 
				${command.regRiduzioneAccertamento.mercatiUso.descrizione }
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.anagrafe" />
			</td>
			<td>
				${command.regRiduzioneAccertamento.anagrafe.descrizioneRichiedente }
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.operatore" />
			</td>
			<td>
				<spring-form:input id="responsabili_id" path="regRiduzioneAccertamento.responsabili.responsabile" cssClass="searchbox" onkeydown="javascript:return searchAll(this,event)"onchange="checkValue(this,'responsabili_hidden')" size="67" tabindex="4"/>
				<init:autocompleter methodAjax="findResponsabili.htm" idHidden="responsabili_hidden" idInput="responsabili_id" inputTitleKey="label.ricerca_responsabile"/>
				<spring-form:errors path="regRiduzioneAccertamento.responsabili.responsabile" cssClass="error"/> 
				<spring-form:hidden id="responsabili_hidden" path="regRiduzioneAccertamento.responsabili.id.codice"  />
			</td>
		</tr>
		<tr>
			<td><fmt:message key="form.registrazioni.descrizione" /></td>
			<td><spring-form:textarea id="descrizione_id" path="regRiduzioneAccertamento.descrizione" cols="70" rows="2" tabindex="5"/>
			<spring-form:errors path="regRiduzioneAccertamento.descrizione" cssClass="error"/></td>
		</tr>
		<tr>
			<td>
				<fmt:message key="form.registrazioni.note" />
			</td>
			<td>
				<spring-form:textarea id="note_id" path="regRiduzioneAccertamento.note" cols="70" rows="4" tabindex="6"/>
				<spring-form:errors path="regRiduzioneAccertamento.note" cssClass="error"/> 
			</td>
		</tr>
	</table>
	</fieldset>
	
	<fieldset>
    <legend><fmt:message key="form.registrazioniimporti.importidaridurre.title" /></legend>
	<div class="jmesa">
	<table class="table" width="100%">
		<thead>
			<tr class="header">
				<td><fmt:message key="form.registrazioniimporti.nrRata" /></td>
				<td><fmt:message key="form.registrazioniimporti.conti" /></td>
				<td style="text-align: right;"><fmt:message key="form.registrazioniFilter.importo" /></td>
				<td style="text-align: right;"><fmt:message key="form.registrazioniFilter.incassato" /></td>
				<td style="text-align: right;"><fmt:message key="form.registrazioniFilter.daincassare" /></td>
				<td><a href="javascript:selezionaDeselezionaTuttiCheckbox(document.inviodati);"><fmt:message key="form.registrazioniimporti.seleziona" /></a></td>
			</tr>
		</thead>
		<tbody class="tbody">
			<%int i=0; %>
			<c:forEach items="${command.entity.registrazioniImportis}" var="currRegImp">
			<c:if test="${currRegImp.nonPrevedeIncassi eq false}">
				<c:if test="${currRegImp.rimanenza gt 0}">
				<tr class="<%=(i%2)==0?"odd":"even"%>">
					<td>${currRegImp.nrRata }</td>
					<td>${currRegImp.conti.descrizione }</td>
					<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${currRegImp.importo }</fmt:formatNumber></td>
					<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${currRegImp.incassato }</fmt:formatNumber></td>
					<td style="text-align: right;"><fmt:formatNumber minFractionDigits="2">${currRegImp.rimanenza }</fmt:formatNumber></td>
					<td><input type="checkbox" name="regImportiSelezionati" value="${currRegImp.id.codice}"/></td>
				</tr>
				<%i++; %>
				</c:if>
			</c:if>
			</c:forEach>		
		</tbody>
	</table>
	</div>
	</fieldset>
	</spring-form:form>
</div>
<div id="functions">
<ul>
	<li><a href="javascript:doSubmit('insertRiduzioni.htm','<fmt:message key="form.registrazioniimporti.riduzioni.alert" />',document.inviodati)" tabindex="7"><fmt:message key="button.insert" /></a></li>
	<li><a href="javascript:doHref('view.htm?codice=${command.entity.id.codice}','')" tabindex="8"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</body>
</html>