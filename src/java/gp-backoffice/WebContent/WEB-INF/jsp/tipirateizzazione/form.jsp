
<%@ include file="../includes/taglibs.jsp" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>
			<fmt:message key="label.oneritipirateizzazione.title" />
		</title>
		<style>
			.cella-numerica {
				text-align: right; 
				min-width: unset !important; 
				max-width: 60px;
			}

		</style>
	</head>
	<body>
		<span class="titoloPagina">
			<fmt:message key="label.oneritipirateizzazione.title" />
		</span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="form"/>
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../tipirateizzazione/create" />
		</jsp:include>
		<div id="subcontent">
			<spring-form:form commandName="tipirateizzazione" name="inviodati">
				<jsp:include page="../includes/displayGlobalMessages.jsp" >
			        <jsp:param name="commandName" value="tipirateizzazione" />
			    </jsp:include>
				<div id="form" class="vbg-form">
					<fieldset>
						<legend><fmt:message key="label.oneritipirateizzazione.title"/></legend>
						<div class="form-group">
							<label><fmt:message key="label.codice" /></label>
							<labe>${tipirateizzazione.id}</labe>
							<spring-form:hidden path="id" />
						</div>
						<div class="form-group validate-required">
							<label><fmt:message key="label.oneritipirateizzazione.descrizione" /></label>
							<spring-form:input path="descrizione" size="150" cssClass="control-to-validate"/>
							<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						</div>
						<div class="form-group validate-required">
							<label><fmt:message key="label.oneritipirateizzazione.numerorate" /></label>
							<spring-form:input path="numeroRate" size="5" onblur="checkNumberInt(this); isPositiveNumber(this);" cssClass="control-to-validate cella-numerica"/>
							<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						</div>
						<div class="form-group validate-required">
							<label><fmt:message key="label.oneritipirateizzazione.ripartizionerate" /></label>
							<spring-form:input path="ripartizioneRate" size="20" cssClass="control-to-validate"/>
							<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						</div>
						<div class="form-group validate-required">
							<label><fmt:message key="label.oneritipirateizzazione.datainiziorateizzazione" /></label>
							<select id="determDataInizioRate" name="determDataInizioRate" class="control-to-validate">
								<option value=""></option>
								<c:forEach  items="${mappainiziorateizzazione}" var="iniziorateizzazione">
									<option value="${iniziorateizzazione.key}" label="${iniziorateizzazione.value}" <c:if test="${ tipirateizzazione.determDataInizioRate eq iniziorateizzazione.key}"> selected </c:if> >
										${iniziorateizzazione.value}
									</option>
								</c:forEach>
							</select>
							<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						</div>
						<div id="div_movimento" class="form-group">
							<label><fmt:message key="label.movimento" /></label>
							<jsp:include page="../includes/autocompletergenericoTT.jsp" >
								<jsp:param name="idElemento" value="movimento" />		
								<jsp:param name="propertyPath" value="tipoMovimento" />				
								<jsp:param name="pathPropertyDescription" value="movimento" />
								<jsp:param name="pathPropertyCode" value="tipoMovimento" />
								<jsp:param name="autocompleterAjax" value="findTipiMovimentoForSoftware.htm?codice=" />	
								<jsp:param name="titleKey" value="label.ricerca_tipimovimento" />
								<jsp:param name="id_help" value="help_tipomovimento" />
								<jsp:param name="help" value="help.tipimovimenti" />
							</jsp:include>
							<fmt:message key="help.ricerca_per_software_TT" />
						</div>
						<div class="form-group validate-required">
							<label><fmt:message key="label.oneritipirateizzazione.scadenzarate" /></label>
							<select id="idScadenzaRate" name="idScadenzaRate" class="control-to-validate">
								<option value="" /></option>
								<c:forEach  items="${listascadenzarate}" var="scadenzarate">
									<option value="${scadenzarate.id}" label="${scadenzarate.descrizione}" <c:if test="${ tipirateizzazione.idScadenzaRate eq scadenzarate.id}"> selected </c:if>>
										${scadenzarate.descrizione}
									</option>
								</c:forEach>
							</select>
							<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						</div>
						
						<div id="div_scadenze_periodi" class="form-group validate-required">
							<label><fmt:message key="label.oneritipirateizzazione.scadenzeperiodi" /></label>
							<spring-form:input path="scadenzePeriodi" cssClass="control-to-validate"/>
							<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						</div>
						
						
						<div class="form-group">
							<label><fmt:message key="label.oneritipirateizzazione.interessi" /></label>
							<spring-form:input path="interessi" size="5" onblur="checkNumberValue(this); isPositiveNumber(this);" cssClass="cella-numerica"/>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.oneritipirateizzazione.speseRateizzazioni" /></label>
							<spring-form:input path="speseRateizzazione" size="5" onblur="checkNumberValue(this); isPositiveNumber(this);" cssClass="cella-numerica"/>
						</div>
						<div class="form-group">
							<label><fmt:message key="label.oneritipirateizzazione.interessilegali" /></label>
							<spring-form:checkbox path="interessiLegali" />
						</div>
						<div id="divTipoAnatocismo" class="form-group">
							<label><fmt:message key="label.oneritipirateizzazione.tipoanatocismo" /></label>
							<select id="tipoAnatocismo" name="tipoAnatocismo">
								<c:forEach  items="${mappatipoanatocismo}" var="tipoanatocismo">
									<option value="${tipoanatocismo.key}" label="${tipoanatocismo.value}" <c:if test="${ tipirateizzazione.tipoAnatocismo eq tipoanatocismo.key}"> selected </c:if> >
										${tipoanatocismo.value}
									</option>
								</c:forEach>
							</select>
						</div>
						<div id="div_tipo_rateizzazione" class="form-group validate-required">
							<label><fmt:message key="label.oneritipirateizzazione.tiporateizzazione" /></label>
							<select id="tipologiaRateizzazione" name="tipologiaRateizzazione" class="control-to-validate">
								<option value="" /></option>
								<c:forEach  items="${mappaammortamento}" var="ammortamento">
									<option value="${ammortamento.key}" label="${ammortamento.value}" <c:if test="${ tipirateizzazione.tipologiaRateizzazione eq ammortamento.key}"> selected </c:if>>
										${ammortamento.value}
									</option>
								</c:forEach>
							</select>
							<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						</div>
						<div  id="div_frequenza_rate" class="form-group validate-required" >
							<label><fmt:message key="label.oneritipirateizzazione.frequenzarategg" /></label>
							<select id="frequenzaRateFR" name="frequenzaRateFR">
								<option value="" /></option>
								<c:forEach  items="${mappafrequenzarateFR}" var="frequenzaFR">
									<option value="${frequenzaFR.key}" label="${frequenzaFR.value}" <c:if test="${ tipirateizzazione.frequenzaRate eq frequenzaFR.key}"> selected </c:if>>
										${ammortamento.value}
									</option>
								</c:forEach>						
							</select>
							<spring-form:input path="frequenzaRate" cssClass="control-to-validate" />
							<div class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>
						</div>
						<div>
							<c:if test="${tipirateizzazione.id==null}">
								<a class="btn btn-primary bottone-salvataggio" href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a>
							</c:if>
							<c:if test="${tipirateizzazione.id!=null}">
								<a class="btn btn-primary bottone-salvataggio" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a>
								<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
							</c:if>
							<a class="btn btn-secondary" href="javascript:doHref('list.htm','')"><fmt:message key="button.back" /></a>
						</div>					
					</fieldset>
				</div>
				<script type='text/javascript'>
					vbg.ready(() => {
						
						const bottoni = document.querySelectorAll('.bottone-salvataggio');
						var campiDaValidare = Array.from(document.querySelectorAll('.validate-required')).map(
								(campo) =>
								new vbg.validators.RequiredFieldValidator(campo)
						);
						
						let validationGroup = new vbg.validators.ValidationGroup(campiDaValidare, bottoni);
						
						const determdatainiziorate = document.getElementById('determDataInizioRate');
						determdatainiziorate.addEventListener ('change', (e)=>{
							mostraNascondiMovimento(e.target.value);
							e.preventDefault();
						});
						
						const divMovimento = document.getElementById('div_movimento');
						mostraNascondiMovimento(determdatainiziorate.value);
											
						function mostraNascondiMovimento(valoreSelezionato)
						{
							let iniziorateizzazionemovimento = '${iniziorateizzazionemovimento}';
							
							if( valoreSelezionato == iniziorateizzazionemovimento ) {
								divMovimento.style.display = '';
							} else {
								divMovimento.style.display = 'none';
							}							
						}
						
						const tipologiaRateizzazione = document.getElementById('tipologiaRateizzazione');
						tipologiaRateizzazione.addEventListener ('change', (e)=>{
							mostraNascondiFrequenzaRate();
							//mostraNascondiFrequenzaFR(e.target.value);
							frequenzaRate.value = '';
							e.preventDefault();
						});
						
						const frequenzaRate = document.getElementById('frequenzaRate');
						const selectFrequenzaFR = document.getElementById('frequenzaRateFR');
						selectFrequenzaFR.addEventListener ('change', (e)=>{
							frequenzaRate.value = selectFrequenzaFR.value;
							e.preventDefault();
						});
						
						//mostraNascondiFrequenzaFR(tipologiaRateizzazione.value);
						mostraNascondiFrequenzaRate();
												
						function mostraNascondiFrequenzaFR(valoreSelezionato)
						{
							let frequenzaFR = '${frequenzaFR}';
							
							if( valoreSelezionato == frequenzaFR ) {
								
								mostraCampoObbligatorio(selectFrequenzaFR);
								nascondiCampoObbligatorio(frequenzaRate);
								frequenzaRate.value = selectFrequenzaFR.value;
							} else {
								nascondiCampoObbligatorio(selectFrequenzaFR);
								mostraCampoObbligatorio(frequenzaRate);
							}
							
							verificaValidatori();
						}
						
						function verificaValidatori(){
							validationGroup.dispose();
							
							let nuoviCampi = Array.from(document.querySelectorAll('.validate-required')).map(
									(campo) =>
									new vbg.validators.RequiredFieldValidator(campo)
							);
							
							
							validationGroup  = new vbg.validators.ValidationGroup(nuoviCampi, bottoni);
						}	
						
						const interessiLegali = document.getElementsByName('interessiLegali')[0];
						interessiLegali.addEventListener ('click', (e)=>{
							mostraNascondiAnatocismo(e.target);
						});
						
						mostraNascondiAnatocismo(interessiLegali);
						function mostraNascondiAnatocismo(checkInteressiLegali)
						{
							let frequenzaFR = '${frequenzaFR}';
							let divAnatocismo = document.getElementById('divTipoAnatocismo');						
							
							if( checkInteressiLegali.checked ) {
								divAnatocismo.style.display = '';
							} else {
								divAnatocismo.style.display = 'none';
							}
						}
						
						const idScadenzaRate = document.getElementById('idScadenzaRate');
						idScadenzaRate.addEventListener ('change', (e)=>{
							mostraNascondiFrequenzaRate();
						});
						
						function validaCampoRispettoANumeroRate(campoDaValidare){
							let numeroRate = document.getElementById('numeroRate');
							
							if( numeroRate.value == '' || campoDaValidare.value == '' ){
								return;
							}			
							
							if( parseInt(numeroRate.value) != campoDaValidare.value.split(';').length ){
								alert("I valori indicati non coincidono con il numero di rate specificate");
								campoDaValidare.focus();
							}
						}
						
						const scadenzePeriodi = document.getElementById('scadenzePeriodi');
						scadenzePeriodi.addEventListener ('blur', (e)=>{
							//validaCampoRispettoANumeroRate(e.target);
							e.preventDefault();
						});
						
						function mostraNascondiFrequenzaRate(){
							let divFrequenzaRate = document.getElementById('div_frequenza_rate');
							let divTipoRateizzazione = document.getElementById('div_tipo_rateizzazione');
							let divScadenzePeriodi = document.getElementById('div_scadenze_periodi');
							let scadenzaPeriodica = '${scadenzaPeriodica}';
							let idScadenzaSelezionata = document.getElementById('idScadenzaRate').value;
							let tipologiaRateizzazione = document.getElementById('tipologiaRateizzazione');
							let frequenzaRate = document.getElementById('frequenzaRate');
							let frequenzaRateFR = document.getElementById('frequenzaRateFR');
							let frequenzaFR = '${frequenzaFR}';
							let scadenzePeriodi = document.getElementById('scadenzePeriodi');
							if( idScadenzaSelezionata == scadenzaPeriodica ){
								nascondiDivObbligatorio(divFrequenzaRate);
								nascondiDivObbligatorio(divTipoRateizzazione);
								nascondiCampoObbligatorio(tipologiaRateizzazione);
								nascondiCampoObbligatorio(frequenzaRate);
								
								
								mostraDivObbligatorio(divScadenzePeriodi);
								mostraCampoObbligatorio(scadenzePeriodi);
							} else {
								nascondiDivObbligatorio(divScadenzePeriodi);
								nascondiCampoObbligatorio(scadenzePeriodi);
								
								mostraDivObbligatorio(divFrequenzaRate);
								mostraDivObbligatorio(divTipoRateizzazione);
								mostraCampoObbligatorio(tipologiaRateizzazione);
								if( tipologiaRateizzazione.value == frequenzaFR ) {
									mostraCampoObbligatorio(frequenzaRateFR);
									nascondiCampoObbligatorio(frequenzaRate);
									frequenzaRate.value = selectFrequenzaFR.value;
								} else {
									nascondiCampoObbligatorio(frequenzaRateFR);
									mostraCampoObbligatorio(frequenzaRate);
								}
							}
							
							verificaValidatori();
							
						}
						
						function mostraCampoObbligatorio(campo){
							campo.style.display = '';
							campo.classList.add("control-to-validate");
							//campo.classList.add("input-error");
						}
						
						function nascondiCampoObbligatorio(campo){
							campo.value = '';
							campo.style.display = 'none';
							campo.classList.remove("control-to-validate");
							campo.classList.remove("input-error");
						}
						
						function mostraDivObbligatorio(div){
							console.log(div);
							div.style.display = '';
							div.classList.add('validate-required');
						}
						
						function nascondiDivObbligatorio(div){
							div.style.display = 'none';
							div.classList.remove('validate-required');
						}
					});	
				</script>
			</spring-form:form>
		</div>
	</body>
</html>
