<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.mercatiformulecalcolo.title" />
	</title>
</head>
<body>



	<span class="titoloPagina">
			<fmt:message key="label.mercatiformulecalcolo.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatiformulecalcolo/view" />
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="mercatiformulecalcolo" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatiformulecalcolo" />
		    </jsp:include>
		    <br/>
		    <fmt:message key="label.mercatiformulecalcolo.descrizione_funzionalita" />
		    <br/><br/>
		    <table border="1" width="100%">
		    <tr>
		    <td width="50%">
		    <%-- COLONNA UNO --%>
		    <%-- TABELLA INSERT DATI --%>
			<table border="0" width="100%">
	            <tr>
	                <td width="40%">
						<fmt:message key="label.giorno" />
					</td>
					<td width="85%" >
						<spring-form:input id="giorno_id" path="entity.mercatiUso.descrizione" size="70" readonly="true" />
					</td>
	            </tr>
	            <tr>
					<td>
						<fmt:message key="label.contesto_formule" />
					</td>
					<td >
	    				<spring-form:select id="contesto_id" path="entity.contesto">
							<spring-form:option value=""><fmt:message key="label.select.default"/></spring-form:option>
							<spring-form:options items="${contestiCalcolo}" itemValue="id" itemLabel="descrizione"></spring-form:options>
						</spring-form:select>						
					</td>
				</tr>
	            
	            
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td >
						<spring-form:input id="descrizione_id" path="entity.descrizione" size="70" />
						<spring-form:errors path="entity.descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.formula" />
					</td>
					<td >
						<spring-form:textarea id="formula_id" path="entity.formula" rows="5" cols="70"/>
						<spring-form:errors path="entity.formula" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.note" />
					</td>
					<td >
						<spring-form:textarea id="note_id" path="entity.note" rows="5" cols="70"/>
						<spring-form:errors path="entity.note" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.data_inizio_validita" />
					</td>
					<td>
						<spring-form:input id="data_id" path="entity.dataInizioValidita" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldata" idInput="data_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.dataInizioValidita" cssClass="error"/>
					
						<fmt:message key="label.data_fine_validita" />
						<spring-form:input id="datafine_id" path="entity.dataFineValidita" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="datafine_id" textKey="label.calendar"/>
						<spring-form:errors path="entity.dataFineValidita" cssClass="error"/>
					</td>
				</tr>
				
				<c:if test="${mercatiformulecalcolo.displayMode == mercatiformulecalcolo.displayConstants.NEW}">
				<tr>
					<td colspan="2"  class="titoloSezione">
						<fmt:message key="label.conti_accertamenti" />
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.conto_accertamento" />
					</td>
					<td>
					<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="mercatiContabilitaTributi" />
							<jsp:param name="propertyPath" value="mercatiContabilitaTributi" />
							<jsp:param name="pathPropertyDescription" value="mercatiContabilitaTributi.conti.descrizione" />
							<jsp:param name="pathPropertyCode" value="mercatiContabilitaTributi.conti.id.codice" />
							<jsp:param name="autocompleterAjax" value="findConti.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_conto" />
						</jsp:include>
						<spring-form:errors path="mercatiContabilitaTributi.conti.descrizione" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.descrizione" />
					</td>
					<td >
						<spring-form:input id="descrizione_mercatiContabilitaTributi_id" path="mercatiContabilitaTributi.descrizione" size="70" />
						<spring-form:errors path="mercatiContabilitaTributi.descrizione" cssClass="error"/>
						<fmt:message key="help.label.copia_dscrizione_conto" />
					</td>
				</tr>
				</c:if>
			</table>
			<%-- END TABELLA INSERT DATI --%>
			</td>
			<td valign="top">
			<%-- COLONNA DUE --%>
			<%-- START TABELLA  LIVELLO SERVIZI --%>
			
			
				<div class="jmesa" style="padding:0;">
					<table  border="0"  cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="15%" ><fmt:message key="label.segnaposto_formula" /></td>
								<td width="40%"><fmt:message key="label.descrizione" /> </td>
								<td width="5%"><fmt:message key="label.aggiungi" /> </td>
				            </tr>
						</thead>
						<tbody class="tbody">
						<%int l=1;%>					
						
			            <c:if test="${not empty livelloServizios}">
			            <c:forEach items="${livelloServizios}" var="liv_servizio_var"> 
						<tr class="<%=(l%2)==0?"odd":"even"%>">
						      <td class="segnaposto" data-valore="${liv_servizio_var.segnaposto}"><b>
						      <c:if test="${not empty liv_servizio_var.codice}">
						      	<a  href="javascript:dettagliotariffe('${liv_servizio_var.codice}')"  />
						      </c:if>
						      ${liv_servizio_var.segnaposto}
						      <c:if test="${not empty liv_servizio_var.codice}">
						      	</a>
						      </c:if></b></td>
						      <td >${liv_servizio_var.descrizione}</td>	
						      <td>
						      	<a class="addSegnaposto addColumn" href="javascript:aggiungiSegnaposto('[${liv_servizio_var.segnaposto}]')" title="<fmt:message key="label.nuova" />&nbsp;<fmt:message key="label.aggiungi" />">
									<label><fmt:message key="label.add.record.image" /></label>
								</a>
						      </td>		      
			             </tr>
						<%l++; %>
						</c:forEach>
						</c:if>
						<c:if test="${ empty livelloServizios}">
							<tr class="even">
								<td colspan="6"><fmt:message key="html.statusbar.noResultsFound" /></td>
							</tr>
						</c:if>
						</tbody>
				</table>
			</div>
			
			<div id="functions">
				<ul>
					<c:if test="${mercatiformulecalcolo.displayMode == mercatiformulecalcolo.displayConstants.VIEW}">
						
						<li><a
							href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatilivelloservizio/create.htm?codiceuso=${mercatiformulecalcolo.entity.mercatiUso.id.codice}&codicemercato=${mercati.id.codice}','')"><fmt:message
							key="button.nuovo_livelli_servizio" /></a></li>
						<li><a
							href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatilivelloservizio/list.htm?codicemercato=${mercati.id.codice}&codiceuso=${mercatiformulecalcolo.entity.mercatiUso.id.codice}&codicemercato=${mercati.id.codice}','')"><fmt:message
							key="button.aggiorna_livelli_servizio" /></a></li>
					</c:if>
				</ul>
			</div>
			
			<%-- END TABELLA INSERT DATI --%>
			</td>
			</tr>
			</table>
			
			<div id="dettaglioTariffeServizio"></div>
			
		</spring-form:form>
	</div>
	
	
	<div id="functions">
		<ul>
			<c:if test="${mercatiformulecalcolo.displayMode == mercatiformulecalcolo.displayConstants.NEW}">
				<li><a href="javascript:inserisci()"><fmt:message key="button.insert" /></a></li>
			</c:if>
			<c:if test="${mercatiformulecalcolo.displayMode == mercatiformulecalcolo.displayConstants.VIEW}">
				
				<li><a href="javascript:aggiorna()"><fmt:message key="button.update" /></a></li>

				<li><a href="javascript:doSubmit('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercatid/listconfigurazione.htm?codicemercato=${mercati.id.codice}','',document.inviodati)"><fmt:message
					key="button.posteggi" /></a></li>
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<br />
	<c:if test="${mercatiformulecalcolo.displayMode == mercatiformulecalcolo.displayConstants.VIEW}">
	        <div class="titoloSezione"><fmt:message key="label.conti_accertamenti" /></div>
			<div class="jmesa">
					<table border="0"  cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="40%"><fmt:message key="label.descrizione" /> </td>
								<td width="10%"><fmt:message key="label.conto" /> </td>
				                <td width="7%" ><fmt:message key="label.data_inizio_validita" /></td>
				                <td width="7%" ><fmt:message key="label.data_fine_validita" /></td>
				                <td width="6%" ><fmt:message key="label.azioni" /></td>
				            </tr>
						</thead>
						<tbody class="tbody">
						<%int l=1;%>
						<c:if test="${not empty mercatiformulecalcolo.entity.mercatiContabilitaTributis}">
						<c:forEach items="${mercatiformulecalcolo.entity.mercatiContabilitaTributis}" var="conto_var">
						<tr class="<%=(l%2)==0?"odd":"even"%>">
						      <td>${conto_var.descrizione}</td>
						      <td>${conto_var.conti.descrizione}</td>
    		                  <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${conto_var.dataInizioValidita}"/></td>
    		                  <td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${conto_var.dataFineValidita}"/></td>
    		                  <td>
    		                  	<a class="dettaglioColumn" href="javascript:javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercaticontabilitatributi/view.htm?codice=${conto_var.id.codice}','');" title="<fmt:message key="label.edit.record" />${conto_var.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
								<%--  DANGERUS ZONE, CAPIRE COME GESTIRE
								<a class="eliminaRiga" href="javascript:javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercaticontabilitatributi/deleteSingoloconto.htm?codice=${conto_var.id.codice}','<fmt:message key="javascript.confirm.delete" />');" title="<fmt:message key="label.elimina" />  " >
										<label><fmt:message key="label.elimina" /></label>
								--%>
    		                  </td>
			             </tr>
						<%l++; %>
						</c:forEach>
						</c:if>
						<c:if test="${empty mercatiformulecalcolo.entity.mercatiContabilitaTributis}">
							<tr class="even">
								<td colspan="8"><fmt:message key="html.statusbar.noResultsFound" /></td>
							</tr>
						</c:if>
						<tr>
							<td>
							<a class="addColumn" href="javascript:javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fmercaticontabilitatributi/create.htm?codiceFormula=${mercatiformulecalcolo.entity.id.codice}','');" title="<fmt:message key="label.nuova" />&nbsp;<fmt:message key="label.formula" />">
									<label><fmt:message key="label.add.record.image" /></label>
								</a>
							</td>
						</tr>
						</tbody>
					</table>
				</div>
		</c:if>
		<script type="text/javascript">
		
		var inserisci = function(){
			
			if(validaInformazioni()){
				doSubmit('insert.htm','',document.inviodati);
			}
		}
		var aggiorna = function(){
			
			if(validaInformazioni()){
				doSubmit('update.htm','',document.inviodati);
			}
		}
		
		function validaInformazioni(){
			var ok = true;			
			if(document.getElementById('descrizione_id').value === '' ){
				
	         	alert("Descrizione obbligatorio");
				ok = false;
			}
			if(document.getElementById('contesto_id').value === '' ){
				
	         	alert("Contesto obbligatorio");
				ok = false;
			}
			return ok;
		}

(function ($, namespaceContainer){

	var campoFormula = $('#formula_id');
	
	campoFormula.on('blur', function() {
		campoFormula.val(campoFormula.val().toUpperCase());
	});
	

	
	function dettagliotariffe(codice_servizio){
	
		var jhqrPr = $.ajax({
			  url: '${pageContext.request.contextPath}/mercatilivelloservizio/ajaxDettaglio.htm?codiceuso=${mercatiformulecalcolo.entity.mercatiUso.id.codice}&codiceservizio='+codice_servizio,
			  context: document.body,
			  cache: false,					  
			  dataType: "html",
			  success: function(data, textStatus, jqXHR){
				  if(data){
					  $('#dettaglioTariffeServizio').html(data);
					  
					  $("#dettaglioTariffeServizio").dialog({
							 resizable: false,
							 modal: true,
							 width:'90%',
							 title: 'Lista tariffe livello servizio'
							}
						);
				  }					  
			  }
		});

	}
	
	function aggiungiSegnaposto(segnaposto) {
		
		var textAreaFormula = $('#formula_id').val();
		textAreaFormula = textAreaFormula + segnaposto;
		$('#formula_id').val(textAreaFormula);		
	}
	
	namespaceContainer.dettagliotariffe = dettagliotariffe;
	namespaceContainer.aggiungiSegnaposto = aggiungiSegnaposto;

})(jQuery, window);

	</script>
</body>
</html>