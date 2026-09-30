<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.gestione_subentri.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.gestione_subentri.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="search" />
	</jsp:include>
	<div id="subcontent">
	 <c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice}</c:param>
	 </c:import>
	 <br class="clear"/>
	<jsp:include page="../includes/displayGlobalMessages.jsp">
	        <jsp:param name="commandName" value="autorizzazioniSubentriCommand" />
	</jsp:include>
	<div id="form" class="vbg-form">		
	 	<fieldset>
                    <legend>
					 <c:if test="${param.return_to==null}">
					 	<fmt:message key="label.cerca_autorizzazioni_concessioni" />
					 </c:if>
					 <c:if test="${param.return_to!=null}">
					 	<fmt:message key="label.ricerca_aut_conc_da_aggiungere" />
					 </c:if>
		 </legend>
	 <spring-form:form commandName="autorizzazioniSubentriCommand" name="inviodati">
	 
	 
	 	<div class="form-group">
                  <label>
                      <fmt:message key="label.numero" />
                  </label>
                   <spring-form:input id="autoriznumero_id" path="filter.autoriznumero" size="10" />
				<spring-form:errors path="filter.autoriznumero" cssClass="error" delimiter="," />
         </div>
	 	
	 	<div class="form-group">
                  <label>
                      <fmt:message key="label.periodo" />
					<fmt:message key="label.da" />
                  </label>
        			<spring-form:input id="dallaData_id" path="filter.dallaData" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataInizio" idInput="dallaData_id" textKey="label.calendar" /> 			  	
					<spring-form:errors path="filter.dallaData" cssClass="error" delimiter="," />
					&nbsp;  
				<label><fmt:message key="label.a" /></label> 
			    <spring-form:input id="allaData_id" path="filter.allaData" size="10" onblur="isValidDate(this,true);" />
				<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="allaData_id" textKey="label.calendar" /> 
				<spring-form:errors path="filter.allaData" cssClass="error" delimiter="," />
				
		</div>

	 	<div class="form-group">
                  <label>
                      <fmt:message key="label.comune" />
                  </label>
                   <spring-form:input path="filter.autorizcomune.descrizioneEstesa" id="autorizcomune_id" cssClass="searchbox"  onchange="checkValue(this,'autorizcomune_hidden')" onkeydown="javascript:return searchAll(this,event,3)" size="67"/>
			    	<init:autocompleter methodAjax="findEnteautorizzazione.htm" idHidden="autorizcomune_hidden" idInput="autorizcomune_id" inputTitleKey="label.ricerca_comune" minChars="3"/>
					<spring-form:hidden id="autorizcomune_hidden" path="filter.autorizcomune.codicecomune" />
					<spring-form:errors path="filter.autorizcomune" cssClass="error" />
		</div>
		
		<div class="form-group">
                  <label>
                      <fmt:message key="label.registro" />
                  </label>
                  <spring-form:input path="filter.tipologiaregistro.trDescrizione" id="autorizregistro_id" cssClass="searchbox"  onchange="checkValue(this,'autorizregistro_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
		    		<init:autocompleter methodAjax="findTipologiaRegistri.htm" idHidden="autorizregistro_hidden" idInput="autorizregistro_id" inputTitleKey="label.ricerca_tipo_registro" />
					<spring-form:hidden id="autorizregistro_hidden" path="filter.tipologiaregistro.id.codice" />
					<spring-form:errors path="filter.tipologiaregistro" cssClass="error" />
				
        </div>
       <div class="form-group">
                  <label>
                      <fmt:message key="label.anagrafe" />
                  </label>
                  <spring-form:input id="anagrafe_id" path="filter.anagrafe.descrizioneRichiedente" cssClass="searchbox" onchange="checkValue(this,'anagrafe_hidden');" onkeydown="javascript:return searchAll(this,event,3)" size="67"/>
				<init:autocompleter methodAjax="findAnagrafe.htm" idHidden="anagrafe_hidden" idInput="anagrafe_id" minChars="3" inputTitleKey="label.ricerca_richiedente"/>
				<spring-form:errors path="filter.anagrafe" cssClass="error" /> 
				<spring-form:hidden id="anagrafe_hidden" path="filter.anagrafe.id.codice"  />
				
        </div>			
		
        <div class="form-group">
                  <label>
                      <fmt:message key="label.includi_cessate" />
                  </label>
                  <spring-form:checkbox path="filter.includiCessate"/>
        </div>	
        <c:if test="${autorizzazioniSubentriCommand.filter.codiceIstanzaDaEscludere != null }">
        <div class="form-group">
                  <label>
                      <fmt:message key="label.escludi_aut_istanza" />	
                  </label>
                  <script type="text/javascript">
					var codiciIstanzaDaEscludere = '';
						function abilitaDisabilita(){
							if(jQuery('#escludi_istanze_id').is(":checked")){
								jQuery('#codiceIstanzaDaEscludere_id').val(codiciIstanzaDaEscludere);								
							}else{
								codiciIstanzaDaEscludere = jQuery('#codiceIstanzaDaEscludere_id').val();
								jQuery('#codiceIstanzaDaEscludere_id').val('');								
							}
						}
					</script>
					<spring-form:hidden path="filter.codiceIstanzaDaEscludere" id="codiceIstanzaDaEscludere_id" />
					<input type="checkbox" name="escludi_istanze_chk" id="escludi_istanze_id" checked="checked" onclick="abilitaDisabilita()"/>
				
        </div>	
        <div class="form-group">
                  <label>
                      <fmt:message key="label.maxRows" />		
                  </label>
                  <select name="filter.maxRows">
						<option label="10" value="10" />
						<option label="50" value="50" />						
					</select>
        </div>	
        </c:if>
        <c:if test="${isGestioneMercatiAttiva eq true}">
        <fieldset>
                <legend><fmt:message key="label.dati_manifestazione" />		</legend>
		        <div class="form-group">
		                  <label>
		                      <fmt:message key="label.manifestazione" />
		                  </label>
		                  <script type="text/javascript">
					function setHiddenFieldmercati(inputField,listItem){
						var a = listItem.id;
						document.getElementById('mercato_id').value = inputField.value;
						document.getElementById('mercato_hidden').value = a;
						
						$('mercato_uso_id').value = '';
						$('mercato_uso_hidden').value = '';
						$('mercatiD_id').value = '';
						$('mercatiD_hidden').value = '';
						
						
					}
				    </script>	
					<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="mercato" />		
						<jsp:param name="propertyPath" value="filter.mercati" />				
						<jsp:param name="pathPropertyDescription" value="filter.mercati.descrizione" />
						<jsp:param name="pathPropertyCode" value="filter.mercati.id.codice" />
						<jsp:param name="autocompleterAjax" value="findMercati.htm" />
						<jsp:param name="afterUpdateElement" value="setHiddenFieldmercati" />		
						<jsp:param name="titleKey" value="label.ricerca_manifestazione" />
					</jsp:include>
		        </div>
		        <div class="form-group">
		                  <label>
		                      <fmt:message key="label.giorno" />
		                  </label>
		                  <script type="text/javascript">

							function filtermercato(element, entry) {
								return entry + "&codiceMercato=" + document.getElementById("mercato_hidden").value;								
							}
						</script>							
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="mercato_uso" />		
								<jsp:param name="propertyPath" value="filter.mercatiUso" />				
								<jsp:param name="pathPropertyDescription" value="filter.mercatiUso.descrizione" />
								<jsp:param name="pathPropertyCode" value="filter.mercatiUso.id.codice" />
								<jsp:param name="autocompleterAjax" value="findMercatiUsoAndMercato.htm" />
								<jsp:param name="ajaxCallBack" value="filtermercato" />							
								<jsp:param name="titleKey" value="label.ricerca_giorno" />
							</jsp:include>
		        </div>
		        <div class="form-group">
		                  <label>
		                      <fmt:message key="label.numero_posteggio" />
		                  </label>
		                  <script type="text/javascript">

							function filtermercato(element, entry) {
								return entry + "&codiceMercato=" + document.getElementById("mercato_hidden").value;								
							}
						</script>							
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="mercatiD" />		
							<jsp:param name="propertyPath" value="filter.mercatiD" />				
							<jsp:param name="pathPropertyDescription" value="filter.mercatiD.codiceposteggio" />
							<jsp:param name="pathPropertyCode" value="filter.mercatiD.id.codice" />
							<jsp:param name="autocompleterAjax" value="findPosteggioAndMercato.htm" />
							<jsp:param name="ajaxCallBack" value="filtermercato" />							
							<jsp:param name="titleKey" value="label.ricerca_posteggio" />
						</jsp:include>
		        </div>
        </fieldset>
        </c:if>   
        </spring-form:form>
		 </fieldset>
		</div>
	</div>
	<div id="functions">
		<ul>			
			<c:if test="${param.return_to!=null}">
			<li><a href="javascript:doSubmit('list.htm?return_to=${param.return_to}','',document.inviodati)"><fmt:message key="button.search" /></a></li>
			<li><a href="javascript:doHref('addToList.htm','')"><fmt:message key="button.back" /></a></li>
			</c:if>
			<c:if test="${param.return_to==null}">
			<li><a href="javascript:doSubmit('list.htm','',document.inviodati)"><fmt:message key="button.search" /></a></li>
			<li><a href="javascript:doHref('../autorizzazioni/create.htm?codiceIstanza=${autorizzazioniSubentriCommand.istanzaDiSubentro.id.codice }','')"><fmt:message key="button.back" /></a></li>
			</c:if>
		</ul>
	</div>
</body>
</html>