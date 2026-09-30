<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.anagrafeimpresa-aree-pubbliche" /></title>
</head>
<body>
	<div class="titolo">
		<fmt:message key="label.anagrafeimpresa-aree-pubbliche" />
	</div>
	<div class="descrizione"></div>
	<c:if test="${param.ret eq 0 }">
		<label style="color: green; font-weight: bold;"><fmt:message key='label.salvataggio-effettuato-con-successo' /></label>			
	</c:if>
	<spring-form:form commandName="anagrafeImpresaCommand" action="salva.htm" method="post" name="viewFormAnagrafe" id="viewFormAnagrafeId">
		<input type="hidden" name="returnto" value="${returnto }" />
	    <table class="sezione_table" border="0" width="100%">
	    	<input type="hidden" name="codice" value="${codice}"></input>	
	    	<tr>
				<td class="sezione_table_label"><fmt:message key='label.nome' />*</td>
				<td>
					<spring-form:input path="entity.nome" cssErrorClass="validation_error_input" maxlength="1000" size="60" />
					<spring-form:errors path="entity.nome" cssClass="validation_error" />
				</td>		
				<td class="sezione_table_label"><fmt:message key='label.cognome' />*</td>
				<td colspan="3">
					<spring-form:input path="entity.cognome" cssErrorClass="validation_error_input" maxlength="1000" size="60" />
					<spring-form:errors path="entity.cognome" cssClass="validation_error" />
				</td>
			</tr>
			<tr>
			    <spring-security:authorize ifAnyGranted="ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE">
				<td class="sezione_table_label"><fmt:message key='label.sesso' /></td>
				<td >
					<spring-form:select path="entity.sesso" cssErrorClass="validation_error_input">	
					    <spring-form:option value=""></spring-form:option>				
						<spring-form:option value="M">Maschio</spring-form:option>
						<spring-form:option value="F">Femmina</spring-form:option>
					</spring-form:select>
					<spring-form:errors path="entity.sesso" cssClass="validation_error" />
				</td>
			    </spring-security:authorize>
				<td class="sezione_table_label"><fmt:message key='label.codicefiscale'/>*</td>
				<td colspan="3">
					<spring-form:input path="entity.codicefiscale" cssErrorClass="validation_error_input" size="20" />
					<spring-form:errors path="entity.codicefiscale" cssClass="validation_error" />
				</td>
			</tr>
			<spring-security:authorize ifAnyGranted="ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE">
			<tr>
				<td class="sezione_table_label"><fmt:message key='label.comune-nascita' /></td>
				<td >
					<spring-form:input path="entity.comuneNascita.comune" size="40" id="ricerca_comune_nascita" />
					<spring-form:hidden path="entity.comuneNascita.codicecomune" id="ricerca_comune_codice" />					
	    		</td>
	    		<td class="sezione_table_label"><fmt:message key='label.provincia' /></td>
				<td colspan="3">
					<spring-form:input path="entity.provinciaNascita" size="3" id="pr_nascita_id" readonly="true"/>			
	    		</td>
	    	</tr>
	    	</spring-security:authorize>
	    	<spring-security:authorize ifAnyGranted="ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE">
	    	<tr>
				<td class="sezione_table_label"><fmt:message key='label.cittadinanza' /></td>
				<td colspan="5">
					<spring-form:input path="entity.cittadinanza.cittadinanza" size="40" id="ricerca_cittadinanza" />
					<spring-form:hidden path="entity.cittadinanza.codice" id="ricerca_cittadinanza_id" />		
				</td>		
			</tr>
			</spring-security:authorize>
			<spring-security:authorize ifAnyGranted="ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE">
	    	<tr>
				<td class="sezione_table_label"><fmt:message key='label.comune-residenza' /></td>
				<td>
					<spring-form:input path="entity.comuneResidenza.comune" size="40" id="ricerca_comune_residenza" />
					<spring-form:hidden path="entity.comuneResidenza.codicecomune" id="ricerca_comune_codice_residenza" />					
	    		</td>
	    		<td class="sezione_table_label"><fmt:message key='label.provincia' /></td>
				<td colspan="3">
					<spring-form:input path="entity.provinciaResidenza" size="3" id="pr_nascita_residenza_id" readonly="true"/>			
	    		</td>
	    	</tr>
	    	<tr>
				<td class="sezione_table_label"><fmt:message key='label.indirizzo-residenza' /></td>
				<td>
					<spring-form:input path="entity.indirizzo" cssErrorClass="validation_error_input"  size="60" />
					<spring-form:errors path="entity.indirizzo" cssClass="validation_error" />
				</td>		
				<td class="sezione_table_label"><fmt:message key='label.civico' /></td>
				<td >
					<spring-form:input path="entity.civico" cssErrorClass="validation_error_input"  size="10" />
					<spring-form:errors path="entity.civico" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.cap' /></td>
				<td >
					<spring-form:input path="entity.cap" cssErrorClass="validation_error_input"  size="5" />
					<spring-form:errors path="entity.cap" cssClass="validation_error" />
				</td>
			</tr>
			</spring-security:authorize>
			<!--  SEZIONE DATI PER EXTRACOMUNITARI -->
			<spring-security:authorize ifAnyGranted="ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE">
			<tr class="sez-extra">
				<b><td colspan="6" class=sezione><fmt:message key='label.sezione-dati-extracomunitari' /></b> (<fmt:message key='label.help.sezione-dati-extracomunitari' />)</td>
			</tr>
			<tr class="sez-extra">
				<td class="sezione_table_label"><fmt:message key='label.permesso-carta-soggiorno' /></td>
				<td>
					<spring-form:input path="entity.permessoCartaSoggiorno" cssErrorClass="validation_error_input"  size="60" />
					<spring-form:errors path="entity.permessoCartaSoggiorno" cssClass="validation_error" />
				</td>		
				<td class="sezione_table_label"><fmt:message key='label.data-rilascio' /></td>
				<td  colspan="3">
					<spring-form:input path="entity.dataRilascioPermessoSogg" cssErrorClass="validation_error_input" maxlength="10" size="8" cssClass="data" />
					<spring-form:errors path="entity.dataRilascioPermessoSogg" cssClass="validation_error" />
				</td>
			</tr>
			<tr class="sez-extra">	
				<td class="sezione_table_label"><fmt:message key='label.rilasciato-da' /></td>
				<td colspan="5" >
					<spring-form:input path="entity.rilasciatoDa" cssErrorClass="validation_error_input"  size="60" />
					<spring-form:errors path="entity.rilasciatoDa" cssClass="validation_error" />
				</td>
			</tr>
			<tr class="sez-extra">	
				<td class="sezione_table_label"><fmt:message key='label.motivo-rilascio' /></td>
				<td colspan="5">
					<spring-form:input path="entity.motivoRilascio" cssErrorClass="validation_error_input" size="120" />
					<spring-form:errors path="entity.motivoRilascio" cssClass="validation_error" />
				</td>
			</tr>
			
			<tr class="sez-extra">
				<td class="sezione_table_label"><fmt:message key='label.data-rinnovo-permesso' /></td>
				<td >
					<spring-form:input path="entity.dataRinnovoPerm" cssErrorClass="validation_error_input" maxlength="10" size="8" cssClass="data" />
					<spring-form:errors path="entity.dataRinnovoPerm" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.data-fine-validita-permesso' /></td>
				<td colspan="3">
					<spring-form:input path="entity.dataFineValiditaPerm" cssErrorClass="validation_error_input" maxlength="10" size="8" cssClass="data" />
					<spring-form:errors path="entity.dataFineValiditaPerm" cssClass="validation_error" />
				</td>
				
			</tr>
			<tr class="sez-extra">
				<td class="sezione_table_label"><fmt:message key='label.estremi-raccomandata-rinnovo' /></td>
				<td colspan="5">
					<spring-form:input path="entity.estremiRaccomandataRinnov" cssErrorClass="validation_error_input"  size="60" />
					<spring-form:errors path="entity.estremiRaccomandataRinnov" cssClass="validation_error" />
				</td>
			</tr>
			</spring-security:authorize>
			<spring-security:authorize ifAnyGranted="ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE">
			<tr class="sez-extra">
				<td class="sezione_table_label"><fmt:message key='label.in-qualita-di' /></td>
				<td colspan="4">
					<spring-form:select onchange="mostraSezioneAzienda()" id="id_in_qualita" path="entity.inQualitaDi" cssErrorClass="validation_error_input">				
						<spring-form:option value=""></spring-form:option>	
						<spring-form:option value="Titolare dell'impresa individuale">Titolare dell'impresa individuale</spring-form:option>
						<spring-form:option value="Legale rappresentante della società">Legale rappresentante della società</spring-form:option>
					</spring-form:select>
					<spring-form:errors path="entity.inQualitaDi" cssClass="validation_error" />
				</td>
			</tr>
			</spring-security:authorize>
			<!-- Sezione azienda -->
			
			<tr class="sez-azienda">
				<b><td colspan="6" class=sezione><fmt:message key='label.sezione-dati-azienda' /></b></td>
			</tr>
			<tr class="sez-azienda-denominazione" >
				<td class="sezione_table_label"><fmt:message key='label.denominazione-azienda' /></td>
				<td colspan="5">
					<spring-form:input id="denominazione-value" cssClass="value-azienda-denominazione"  path="entity.denominazione" cssErrorClass="validation_error_input"  size="60" />
					<spring-form:errors path="entity.denominazione" cssClass="validation_error" />
				</td>
			</tr>
			<tr class="sez-azienda">
				<td class="sezione_table_label"><fmt:message key='label.codice-fiscale-piva' /></td>
				<td  colspan="5">
					<spring-form:input cssClass="value-azienda"  path="entity.cfPi" cssErrorClass="validation_error_input"  size="60" />
					<spring-form:errors path="entity.cfPi" cssClass="validation_error" />
				</td>
			</tr>
			<spring-security:authorize ifAnyGranted="ROLE_GESTIONE_INSERIMENTI_ANAGRAFICHE">
			<tr class="sez-azienda">
				<td class="sezione_table_label"><fmt:message key='label.comune-sede-legale' /></td>
				<td>
					<spring-form:input cssClass="value-azienda"  path="entity.comuneSedeLegale.comune" size="40" id="ricerca_comune_legale" />
					<spring-form:hidden path="entity.comuneSedeLegale.codicecomune" id="ricerca_comune_codice_legale" />					
	    		</td>
	    		<td class="sezione_table_label"><fmt:message key='label.provincia' /></td>
				<td colspan="3">
					<spring-form:input cssClass="value-azienda"  path="entity.provSedeLegale" size="3" id="pr_legale_id" readonly="true"/>			
	    		</td>
	    	</tr>
	    	<tr class="sez-azienda">
	    		<td colspan="6" class="sezione_table_label"><fmt:message key='label.indirizzo-sedelegale' /></td>
	    	</tr>
   			<tr class="sez-azienda">
   			    
   				<td >
					<spring-form:select cssClass="value-azienda"  path="entity.toponimoSedeLegale" cssErrorClass="validation_error_input">
						<spring-form:option value=""></spring-form:option>				
						<spring-form:option value="Via">Via</spring-form:option>
						<spring-form:option value="Viale">Viale</spring-form:option>
						<spring-form:option value="Piazza">Piazza</spring-form:option>
						<spring-form:option value="Piazzale">Piazzale</spring-form:option>
					</spring-form:select>
					<spring-form:errors path="entity.toponimoSedeLegale" cssClass="validation_error" />
				</td>
				<td>
					<spring-form:input cssClass="value-azienda"  path="entity.indirizzoSedeLegale" cssErrorClass="validation_error_input"  size="50" />
					<spring-form:errors path="entity.indirizzoSedeLegale" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.civico-sedelegale' /></td>
				<td >
					<spring-form:input cssClass="value-azienda"  path="entity.civicoSedeLegale" cssErrorClass="validation_error_input"  size="10" />
					<spring-form:errors path="entity.civicoSedeLegale" cssClass="validation_error" />
				</td >	
			</tr>
			<tr class="sez-azienda">
				<td class="sezione_table_label"><fmt:message key='label.cap-sedelegale' /></td>
				<td >
					<spring-form:input cssClass="value-azienda" path="entity.capSedeLegale" cssErrorClass="validation_error_input"  size="5" />
					<spring-form:errors path="entity.capSedeLegale" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.telefono' /></td>
				<td colspan="3">
					<spring-form:input cssClass="value-azienda" path="entity.telefonoSedeLegale" cssErrorClass="validation_error_input"  size="5" />
					<spring-form:errors path="entity.telefonoSedeLegale" cssClass="validation_error" />
				</td>
			</tr>
			<tr class="sez-azienda">
				<td class="sezione_table_label"><fmt:message key='label.numero-reg-imprese' /></td>
				<td >
					<spring-form:input cssClass="value-azienda" path="entity.numeroRegImprese" cssErrorClass="validation_error_input"  size="5" />
					<spring-form:errors path="entity.numeroRegImprese" cssClass="validation_error" />
				</td>
				<td class="sezione_table_label"><fmt:message key='label.data-iscrizione' /></td>
				<td colspan="3">
					<spring-form:input id="data-iscrizione-rea"  path="entity.iscrizioneRegImprese" cssErrorClass="validation_error_input" maxlength="10" size="8" cssClass="data" />
					<spring-form:errors path="entity.iscrizioneRegImprese" cssClass="validation_error" />
				</td>
			</tr>
			<tr class="sez-azienda">
				<td class="sezione_table_label"><fmt:message key='label.camera-comm-iaa' /></td>
				<td colspan="3">
					<spring-form:input cssClass="value-azienda"  path="entity.cameraCommIaa" cssErrorClass="validation_error_input"  size="60" />
					<spring-form:errors path="entity.cameraCommIaa" cssClass="validation_error" />
				</td>
			</tr>
			</spring-security:authorize>
		</table>
	
	<table class="sezione_table">
	<tr>
		<td class="sezione_table_buttons">
			<spring-security:authorize ifNotGranted="ROLE_READONLY">
			<input type="button" value="<fmt:message key='button.salva' />" id="salva" />
			<c:if test="${anagrafeImpresaCommand.entity.id.codice !=null}">
				<input type="button" value="<fmt:message key='button.elimina' />" id="delete" />
			</c:if>
		
			</spring-security:authorize>
			<input type="button" value="<fmt:message key='button.chiudi' />" id="chiudi" />
		</td>
	</tr>
	</table>
	</spring-form:form>
	<script type="text/javascript">

	$( document ).ready(function() {
		mostraSezioneAzienda();
	});
	
	
	
	function mostraSezioneAzienda()
	{
		var iqd =$("#id_in_qualita").val();
		if(iqd=='')
		{
			$(".sez-azienda").hide();
			$(".sez-azienda-denominazione").hide();
			$(".value-azienda").val('');
			$("#data-iscrizione-rea").val('');
			$("#ricerca_comune_codice_legale").val('');
			$("#denominazione-value").val('');
			
		}else
		{
			$(".sez-azienda").show();
			if(iqd == "Titolare dell'impresa individuale")
			{
				$(".sez-azienda-denominazione").hide();
				$("#denominazione-value").val('');
			}else
			{
				$(".sez-azienda-denominazione").show();
			}
		}
	}
	
	$(".data").datepicker();
	
	$("#salva").click(function(){
		document.viewFormAnagrafe.submit();
	});
	
	$("#delete").click(function(){
		if(confirm("<fmt:message key='alert.elimina' />")){
		window.location.replace("${pageContext.request.contextPath}/anagrafeimpresa/delete.htm?codice=${anagrafeImpresaCommand.entity.id.codice}");
		}
	});
	
	$(function () {
		
		function creaAutocomplete(parametri) {
			
			$(parametri.idCampoTesto).autocomplete({
				source: parametri.url, 
				minLength: 2,
				select: function( event, ui ) {
					if(ui.item){
						parametri.successCallback(ui.item);
					}else{
						parametri.noElementFoundCallback();
					};
				},
				change: function(event, ui) {
					if(ui.item){
						parametri.successCallback(ui.item);
					}else{
						parametri.noElementFoundCallback();
					};
				}
			});
		}
		
		creaAutocomplete({
			url: "${pageContext.request.contextPath}/ajax/getComuneItaliani.htm",
			idCampoTesto: '#ricerca_comune_nascita',
			successCallback: function (item) {
				$("#ricerca_comune_codice").val(item.id);
				$("#pr_nascita_id").val(item.pr);
			},
			noElementFoundCallback: function () {
				$("#ricerca_comune_codice").val('');
				$("#ricerca_comune_nascita").val('');
				$("#pr_nascita_id").val('');
			}
		});
		
		
		
		creaAutocomplete({
			url: "${pageContext.request.contextPath}/ajax/getComuneItaliani.htm",
			idCampoTesto: '#ricerca_comune_residenza',
			successCallback: function (item) {
				$("#ricerca_comune_codice_residenza").val(item.id);
				$("#pr_nascita_residenza_id").val(item.pr);
			},
			noElementFoundCallback: function () {
				$("#ricerca_comune_codice_residenza").val('');
				$("#ricerca_comune_residenza").val('');
				$("#pr_nascita_residenza_id").val('');
			}
		});
		
		creaAutocomplete({
			url: "${pageContext.request.contextPath}/ajax/getComuneItaliani.htm",
			idCampoTesto: '#ricerca_comune_legale',
			successCallback: function (item) {
				$("#ricerca_comune_codice_legale").val(item.id);
				$("#pr_legale_id").val(item.pr);
			},
			noElementFoundCallback: function () {
				$("#ricerca_comune_codice_legale").val('');
				$("#ricerca_comune_legale").val('');
				$("#pr_legale_id").val('');
			}
		});
		
		
		
		ricerca_comune_legale
		
		creaAutocomplete({
			url: "${pageContext.request.contextPath}/ajax/getCittadinanza.htm",
			idCampoTesto: '#ricerca_cittadinanza',
			successCallback: function (item) {
				$("#ricerca_cittadinanza_id").val(item.id);
			},
			noElementFoundCallback: function () {
				$("#ricerca_comune_codice_residenza").val('');
				$("#ricerca_cittadinanza_id").val('');
				
			}
		});
		
		
		
	});
	
		/*
		$("#ricerca_comune_nascita").autocomplete({
			source: "${pageContext.request.contextPath}/ajax/getComuneItaliani.htm",
			minLength: 2,
			select: function( event, ui ) {
				if(ui.item){
					$("#ricerca_comune_codice").val(ui.item.id);
					$("#pr_nascita_id").val(ui.item.pr);
				}else{
					$("#ricerca_comune_codice").val('');
					$("#ricerca_comune_nascita").val('');
					$("#pr_nascita_id").val('');
				};
			},
			change: function(event, ui) {
				if(ui.item){
					$("#ricerca_comune_codice").val(ui.item.id);
					$("#pr_nascita_id").val(ui.item.pr);
				}else{
					$("#ricerca_comune_codice").val('');
					$("#ricerca_comune_nascita").val('');
					$("#pr_nascita_id").val('');
				};
			}
		});
		*/
	</script>
	<c:choose>
	<c:when test="${returnto eq 'list'}">
	<script type="text/javascript">
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/anagrafeimpresa/search.htm");
		});
	</script>
	</c:when>
	<c:otherwise>
	<script type="text/javascript">
		$("#chiudi").click(function(){
			window.location.replace("${pageContext.request.contextPath}/home/start.htm");
		});
	</script>
	</c:otherwise>
	</c:choose>
</body>
</html>