<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.GregorianCalendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Registrazionimercato"%>
<%@ page import="java.util.List"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Giorno"%>
<%@ page import="it.gruppoinit.pal.gp.core.domain.Mercati"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.dao.helper.PeriodicitaHelper"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="form.registrazionimercato.title.view" />
	</title>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="form.registrazionimercato.title.view" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
        <jsp:include page="../includes/history.jsp">
		   <jsp:param name="path" value="../registrazionimercato/create" />
	    </jsp:include>
<div id="subcontent">

	<span class="parametri"><fmt:message key="form.registrazionimercato.mercato" />: <label>${mercati.descrizione}</label></span>
	<span class="parametri"><fmt:message key="form.registrazionimercato.mercatiUso" />: <label>${registrazionimercato.mercatiUso.descrizione}</label></span>	
	<span class="parametri"><fmt:message key="form.registrazionimercato.anno" />: <label>${registrazionimercato.anno}</label></span>
	
	<spring-form:form commandName="registrazionimercato" name="inviodati">
	<div class="titoloSezione">   
        <fmt:message key="form.registrazionimercato.title.sezione" />
    </div>
    <jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="registrazionimercato" />
    </jsp:include>
    <table>
		<spring-form:hidden path="anno"/>
		<spring-form:hidden path="mercatiUso.id.codice"/> 
		<tr>
			<td><fmt:message key="form.registrazionimercato.dataRegistrazione" /></td>
			<td>
			    <spring-form:input id="dataRegistrazione_id" path="dataRegistrazione" size="10" maxlength="10"  onblur="isValidDate(this,true);"/> 
			  	<init:calendar imagePath="/images/cal.gif" idImage="calDataRegistrazione" idInput="dataRegistrazione_id" textKey="label.calendar"/>
			    <spring-form:errors	path="dataRegistrazione" cssClass="error" />
			</td>
		</tr>
        <tr>
			<td><fmt:message key="form.registrazionimercato.registrazioniCausali" /></td>
			<td><spring-form:select path="registrazioniCausali.id.codice" >
				<spring-form:option value=" "><fmt:message key="label.select.default" /></spring-form:option>
				<spring-form:options items="${registrazionicausaliList}" itemLabel="descrizione" itemValue="id.codice" />	
				</spring-form:select>
				<init:help idHelp="help1" textKey="form.registrazionimercato.registrazioniCausali.help"/>
				<spring-form:errors path="registrazioniCausali" cssClass="error"/>
			</td>
		</tr>
		<c:if test="${mercati.flagConsorzio eq true }">
		<tr>
			<td><fmt:message key="form.registrazionimercato.imputaConsorzio" /></td>
			<td><spring-form:checkbox path="imputaConsorzio" id="imputaConsorzio_id"/>
				<label for="imputaConsorzio_id"><fmt:message key="form.registrazionimercato.imputaConsorzio.help" /></label>
				<%--
				<init:help idHelp="help1" textKey="form.registrazionimercato.registrazioniCausali.help"/>
				<spring-form:errors path="registrazioniCausali" cssClass="error"/>
				 --%>
			</td>
		</tr>        
		</c:if>
		</table>
		<br/>
	
	<br/>
	
    <fieldset>
    <legend><fmt:message key="label.registrazionimercato_scelta_tipo_algoritmo_creazione_registrazioni" /></legend>
    <table style="border: 1px dotted;">
    <tr>
    	<td id="td_tipo_algoritmo_annuale_id" style="border-bottom: 1px dotted">
    	   	<spring-form:radiobutton id="tipo_algoritmo_annuale_id" path="tipoCalcolo" title="calcolo annuale" value="1"  onclick="cambiaSfondoTipoCalcolo();"/>
    	   	<label for="tipo_algoritmo_annuale_id">
	    		<fmt:message key="label.registrazionimercato_scelta_tipo_algoritmo_creazione_registrazioni.annuale" />
	    	</label>
	    	<br />
	    	<br />
	    	<fmt:message key="form.registrazionimercato.tiporateizzazione.scadenzarate" />: &nbsp;
	    	<spring-form:select path="tipoCalcoloAnnualeScadenzaRate">
	    		<spring-form:option value="7">Inizio mese</spring-form:option>
	    		<spring-form:option value="0">Fine mese</spring-form:option>
	    		<spring-form:option value="1">15 del mese</spring-form:option>
	    	</spring-form:select>
    	</td>
    </tr>    
    <tr>
    <td  id="td_tipo_algoritmo_calcolo_id">
    	<spring-form:radiobutton id="tipo_algoritmo_calcolo_id" path="tipoCalcolo" title="calcolo_rate" value="0" onclick="cambiaSfondoTipoCalcolo();"/><label for="tipo_algoritmo_calcolo_id">
    		<fmt:message key="label.registrazionimercato_scelta_tipo_algoritmo_creazione_registrazioni.calcolo_rateale" />
    	</label>
    	<br />
    	<br />
    	<div id="alg_container">
		    <span class="titoloTabella"><fmt:message key="form.registrazionimercato.rangeRateizzazioni.list" /></span>
			<init:help idHelp="help2" textKey="form.registrazionimercato.rangeRateizzazioni.help"/>
			<c:if test="${empty rangeRateizzazioniList}">
			<span class="error_header"> 
		    	<span class="error"><fmt:message key="form.registrazionimercato.rangeRateizzazioni.error" /></span>
		    </span>
		    </c:if>
		    <div class="jmesa" >
				<table border="0" width="70%" cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td><fmt:message key="form.registrazionimercato.rangeRateizzazioni.rangeBasso" /></td>
							<td><fmt:message key="form.registrazionimercato.rangeRateizzazioni.rangeAlto" /></td>
							<td><fmt:message key="form.registrazionimercato.tiporateizzazione.descrizione" /></td>
							<td><fmt:message key="form.registrazionimercato.tiporateizzazione.nrorate" /></td>
							<td>
								<fmt:message key="form.registrazionimercato.tiporateizzazione.ripartizionerate" />
								<init:help idHelp="help3" textKey="form.registrazionimercato.rangeRateizzazioni.ripartizionerate.help"/>
							</td>
							<td>
							<fmt:message key="form.registrazionimercato.tiporateizzazione.frequenzarate" />
							<init:help idHelp="help4" textKey="form.registrazionimercato.rangeRateizzazioni.frequenzarate.help"/>
							</td>
							<td><fmt:message key="form.registrazionimercato.tiporateizzazione.scadenzarate" /></td>
							<td><fmt:message key="form.registrazionimercato.tiporateizzazione.interessirate" /></td>
						</tr>
					</thead>
					<tbody class="tbody" >
					<%int i=1;%>
					<c:forEach var="rangeRate_var" items="${rangeRateizzazioniList}" varStatus="rangeRateStatus">
					<tr class="<%=(i%2)==0?"odd":"even"%>"  >
						<td>${rangeRate_var.rangeBasso}</td>
						<td>${rangeRate_var.rangeAlto}</td>
						<td >${rangeRate_var.tiporateizzazione.descrizione}</td>
						<td>${rangeRate_var.tiporateizzazione.nrorate}</td>
						<td>${rangeRate_var.tiporateizzazione.ripartizionerate}</td>
						<td>${rangeRate_var.tiporateizzazione.frequenzarate}</td>
						<td>${rangeRate_var.tiporateizzazione.scadenzarate.descrizione}</td>
						<td>${rangeRate_var.tiporateizzazione.interessirate}</td>
					</tr>
					<%i++;%>
					</c:forEach>
					</tbody>
				</table>
				
			</div>
		
		</div>
		</td>
	</tr>
	</table>
	</fieldset>
	</spring-form:form>
</div>
<br />

<div id="functions">
<ul>
	<c:if test="${not empty rangeRateizzazioniList}">
		<li><a href="javascript:doSubmit('insertRegistrazioni.htm?mercati.id.codice=${mercati.id.codice}','<fmt:message key="button.insert.registrazionimercato.alert" />',document.inviodati)"><fmt:message key="button.insert" /></a></li>
	</c:if>	
	<li><a href="javascript:verifica();"><fmt:message key="button.costoposteggi" /></a></li>
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
 </ul> 
</div>
<script type="text/javascript">
	function verifica(){
		 historySet('${_urlback}', '../mercaticonti/costoposteggi.htm?mercati.id.codice=${mercati.id.codice}&anno=${registrazionimercato.anno}', '');
	}
	jQuery(document).ready(function(){
		cambiaSfondoTipoCalcolo();
	});
	
	function cambiaSfondoTipoCalcolo(){
		
		if(jQuery("#tipo_algoritmo_calcolo_id").attr('checked')==true){
			jQuery("#td_tipo_algoritmo_calcolo_id").css('background-color','#B7FC72');
			jQuery("#td_tipo_algoritmo_annuale_id").css('background-color','#C0C0C0');
		}else{
			jQuery("#td_tipo_algoritmo_calcolo_id").css('background-color','#C0C0C0');
			jQuery("#td_tipo_algoritmo_annuale_id").css('background-color','#B7FC72');
		}		
		
	}
	
</script>
</body>
</html>
