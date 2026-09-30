<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.mercatidlivelloservizio.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
			<fmt:message key="label.mercatidlivelloservizio.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.NEW}">
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatidlivelloservizio/create" />
	</jsp:include>
	</c:if>
	<c:if test="${mercatidlivelloservizio.displayMode == mercatidlivelloservizio.displayConstants.VIEW}">
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../mercatidlivelloservizio/view" />
	</jsp:include>
	</c:if>
	<div id="subcontent">
		<spring-form:form commandName="mercatidlivelloservizio" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="mercatidlivelloservizio" />
		    </jsp:include>
		    		    				 
<div id="form" class="vbg-form" >
				
				<c:set var="currentStep" value="${mercatidlivelloservizio.livelloServizioWizard.step}" />
				<c:set var="isStep1" value="${currentStep == 1}" />
				<c:set var="codicemercatoback" value="${mercatidlivelloservizio.entity.mercatiD.mercati.id.codice}" />
				<fieldset id="id_step_1"  style="${isStep1 ? '' : 'opacity:0.7;pointer-events:none;'}">
					<legend>1. Seleziona le giornate da configurare</legend>
					<div class="form-group">
						<label>Giorni</label>
						<c:forEach
							items="${mercatidlivelloservizio.livelloServizioWizard.giornateDaConfigurare}"
							var="giornate" varStatus="idx">
							<!--<input id="idgiornata${giornate.id}" style="margin-left: 20px;"
								name="livelloServizioWizard.giornateDaConfigurare[${idx.index }].checked"
								type="checkbox" value="true"  ${mercatidlivelloservizio.livelloServizioWizard.giornateDaConfigurare[idx.index].checked ? 'checked' : ''}> -->
							<spring-form:checkbox id="idgiornata${giornate.id}" path="livelloServizioWizard.giornateDaConfigurare[${idx.index }].checked" cssStyle="margin-left: 20px;"/>	
							<span style="position: relative; top: -5px;">${giornate.descrizione}</span>
						</c:forEach>
					</div>
					<c:if test="${isStep1}">
					<div class="form-button">
						<a class="btn btn-primary"
							href="javascript:doSubmit('nextStep.htm?currstep=2','',document.inviodati)">Avanti</a><a
							class="btn btn-secondary"
							href="javascript:doHref('../mercatid/list.htm?codicemercato=${codicemercatoback }','')">Chiudi</a>
					</div>
					</c:if>
				</fieldset>

                <c:set var="isStep2" value="${currentStep == 2}" />
                <c:set var="isGEStep2" value="${currentStep >= 2}" />
                <c:set var="radioselectedStep2" value="${mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.tipoLivelloDiServizio}" />
                <c:if test="${isGEStep2}">
				<fieldset id="id_step_2" style="${isStep2 ? '' : 'opacity:0.7;pointer-events:none;'}">
					<legend>2. SCEGLI IL LIVELLO DI SERVIZIO</legend>
					<div class="form-group">
							<span style="font-weight: bold;">Associati al mercato</span>
							<input
							id="r_associatiAlMercato_id"
							name="livelloServizioWizard.scegliLivelloDiServizio.tipoLivelloDiServizio"
							type="radio" value="associatiAlMercato" style="min-width:0px;position: relative;top: 2px;" ${ radioselectedStep2 == 'associatiAlMercato' ? 'checked' : '' }>
							
							<span style="font-weight: bold;margin-left:20px;">Nuovo da associare</span>
							<input
							id="r_nuovoDaAssociare_id"
							name="livelloServizioWizard.scegliLivelloDiServizio.tipoLivelloDiServizio"
							type="radio" value="nuovoDaAssociare" style="min-width:0px;position: relative;top: 2px;" ${ radioselectedStep2 == 'nuovoDaAssociare' ? 'checked' : '' }>
					</div>
					<div class="form-group" id="associatomercato" style="margin-top:30px;${ radioselectedStep2 == 'associatiAlMercato' ? '' : 'display:none;' }">
						<label>Livello di servizio</label> 
						
						        <select name="livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioM"  ${ mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioMDD.size() > 10 ? 'onfocus="this.size=10;" onblur="this.size=1;" onchange="this.size=1; this.blur();"' : '' }  >
								<c:if test="${empty mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioMDD}">
								<option disabled selected>Nessun record disponibile</option>
								</c:if>
								<c:forEach var="entry" items="${mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioMDD}">
								   <option value="${entry.key}" ${ mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioM eq entry.key ? 'selected' : '' } >${entry.value}</option>
								</c:forEach>
								</select>

                    </div>
                    <div id="nuovodaassociare" style="margin-top:30px;${ radioselectedStep2 == 'nuovoDaAssociare' ? '' : 'display:none;' }">
					<div class="form-group">
						<label>Descrizione</label> <input id="n_descrizione_id"
							name="livelloServizioWizard.scegliLivelloDiServizio.descrizione"
							type="text" value="${ mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.descrizione }" size="70" >
					</div>
					<div class="form-group">
						<label>Livello di servizio</label> <input
							id="n_livelloServizio_id"
							name="livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioN"
							class="searchbox " size="67"
							onkeydown="return searchAll(this,event)"
							onchange="checkValue(this,'n_livelloServizio_id_hidden')"
							value="${ mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioN }">
							<init:autocompleter methodAjax="findLivelloServizio.htm"
								idHidden="n_livelloServizio_id_hidden"
								idInput="n_livelloServizio_id" inputTitleKey="" minChars="1" />
							<input type="hidden" id="n_livelloServizio_id_hidden"
							name="livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioNid" 
							value="${ mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.livelloDiServizioNid }"/>
					</div>
					<div class="form-group">
						<label>Attivo</label>
							<spring-form:checkbox id="a_attivo_id" path="livelloServizioWizard.scegliLivelloDiServizio.attivo"/>
							<span
							style="position: relative; top: -5px;">Il flag attiva può
								essere utilizzato quando il sistema non permette di cancellare
								il livello di servizio perchè utilizzato. Disattivandolo non
								sarà più possibile aggangiarlo ad un posteggio e non verrà più
								considerato in fase di calcolo (Es conguagli).</span>
					</div>
					<div class="form-group">
						<label>Tariffa</label> <input id="n_tariffa_id"
							name="livelloServizioWizard.scegliLivelloDiServizio.tariffa"
							onblur="checkNumberValue(this);" type="text" value="${ mercatidlivelloservizio.livelloServizioWizard.scegliLivelloDiServizio.tariffa }" size="6">
					</div>
					<div class="form-group">
						<label>Inizio validità</label>
						<spring-form:input id="n_da_data_id"
							path="livelloServizioWizard.scegliLivelloDiServizio.inizioValidita"
							size="10" maxlength="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="n_da_caldata"
							idInput="n_da_data_id" textKey="label.calendar" />
					</div>
					<div class="form-group">
						<label>Fine validità</label>
						<spring-form:input id="n_a_data_id"
							path="livelloServizioWizard.scegliLivelloDiServizio.fineValidita"
							size="10" maxlength="10" onblur="isValidDate(this,true);" />
						<init:calendar imagePath="/images/cal.gif" idImage="n_a_caldata"
							idInput="n_a_data_id" textKey="label.calendar" />
					</div>
					</div>
					<c:if test="${isStep2}">
					<div class="form-button">
								<a class="btn btn-primary"
									href="javascript:doSubmit('nextStep.htm?currstep=3','',document.inviodati)">Avanti</a> <a class="btn btn-primary"
									href="javascript:doSubmit('nextStep.htm?currstep=1','',document.inviodati)">Indietro</a> <a class="btn btn-primary"
									href="javascript:doSubmit('createM.htm','',document.inviodati)">Annulla</a> <a class="btn btn-secondary"
									href="javascript:doHref('../mercatid/list.htm?codicemercato=${codicemercatoback }','')">Chiudi</a>
							</div>
					</c:if>
				</fieldset>
				</c:if>

				<c:set var="isStep3" value="${currentStep == 3}" />
				<c:set var="isGEStep3" value="${currentStep >= 3}" />
				<c:if test="${isGEStep3}">
					<fieldset id="id_step_3" style="${isStep3 ? '' : 'opacity:0.7;pointer-events:none;'}">
						<legend>3. Configura le impostazioni</legend>
						<div class="form-group">
							<label>Usa mq del posteggio</label> 
								
								<spring-form:checkbox id="usaMqPosteggio_id" path="livelloServizioWizard.impostazioni.mqposteggio"/>
								
								<span
								style="position: relative; top: -5px;">Se selezionato nel
									calcolo del costo del singolo livello di servizio non verrà
									preso in considerazione il fattore moltiplicativo, ma il valore
									in mq della superficie del posteggio.</span>
						</div>
						<div class="form-group">
							<label>Fattore moltiplicativo</label> <input
								id="fattoreMoltiplicativo_id"
								name="livelloServizioWizard.impostazioni.fattoremoltiplicativo"
								onblur="checkNumberValue(this);" type="text" value="${ mercatidlivelloservizio.livelloServizioWizard.impostazioni.fattoremoltiplicativo }" size="6">
						</div>

						<div class="form-group">
							<label>Inizio validità</label>
							<spring-form:input id="i_da_data_id"
								path="livelloServizioWizard.impostazioni.inizioValidita"
								size="10" maxlength="10" onblur="isValidDate(this,true);" />
							<init:calendar imagePath="/images/cal.gif" idImage="i_da_caldata"
								idInput="i_da_data_id" textKey="label.calendar" />
						</div>
						<div class="form-group">
							<label>Fine validità</label>
							<spring-form:input id="i_a_data_id"
								path="livelloServizioWizard.impostazioni.fineValidita" size="10"
								maxlength="10" onblur="isValidDate(this,true);" />
							<init:calendar imagePath="/images/cal.gif" idImage="i_a_caldata"
								idInput="i_a_data_id" textKey="label.calendar" />
						</div>
						<c:if test="${isStep3}">
							<div class="form-button">
								<a class="btn btn-primary"
									href="javascript:doSubmit('nextStep.htm?currstep=4','',document.inviodati)">Avanti</a> <a class="btn btn-primary"
									href="javascript:doSubmit('nextStep.htm?currstep=2','',document.inviodati)">Indietro</a> <a class="btn btn-primary"
									href="javascript:doSubmit('createM.htm','',document.inviodati)">Annulla</a> <a class="btn btn-secondary"
									href="javascript:doHref('../mercatid/list.htm?codicemercato=${codicemercatoback }','')">Chiudi</a>
							</div>
						</c:if>
					</fieldset>
				</c:if>

				<c:set var="isStep4" value="${currentStep == 4}" />
				<c:set var="isGEStep4" value="${currentStep >= 4}" />
				<c:if test="${isGEStep4}">
					<fieldset id="id_step_4">
						<legend>4. Procedi</legend>
						<div class="form-group">
							<label>Mercato</label> <span>${mercatidlivelloservizio.livelloServizioWizard.step4Mercato }</span>
						</div>
						<div class="form-group">
							<label>Giorni</label> <span>${mercatidlivelloservizio.livelloServizioWizard.step4Giorni }</span>
						</div>
						<div class="form-group">
							<label>Livello di servizio</label> <span>${mercatidlivelloservizio.livelloServizioWizard.step4LivelloServizio }</span>
						</div>
						<div class="form-group">
							<label>Posteggi</label> <span>${mercatidlivelloservizio.livelloServizioWizard.step4Posteggi }</span>
						</div>
						<div class="form-group">
							<label>Impostazioni</label> <span>${mercatidlivelloservizio.livelloServizioWizard.step4UsaMqPosteggio }</span>
						</div>
						<div class="form-group">
							<label>Validità</label> <span>${mercatidlivelloservizio.livelloServizioWizard.step4Validita }</span>
						</div>
						<c:if test="${isStep4}">
							<div class="form-button">
								<a class="btn btn-primary"
									href="javascript:doSubmit('insertM.htm','',document.inviodati)">Inserisci</a> <a class="btn btn-primary"
									href="javascript:doSubmit('nextStep.htm?currstep=3','',document.inviodati)">Indietro</a> <a class="btn btn-primary"
									href="javascript:doSubmit('createM.htm','',document.inviodati)">Annulla</a> <a class="btn btn-secondary"
									href="javascript:doHref('../mercatid/list.htm?codicemercato=${codicemercatoback }','')">Chiudi</a>
							</div>
						</c:if>
					</fieldset>
				</c:if>


			</div>
			
			<c:forEach var="codposteggio" items="${mercatidlivelloservizio.listacodici}">
               <input type="hidden" name="codiceposteggi" value="${codposteggio }"></input>
            </c:forEach>
				
		</spring-form:form>
	</div>
	
	<script>
	
	   
	document.querySelectorAll('input[name="livelloServizioWizard.scegliLivelloDiServizio.tipoLivelloDiServizio"]').forEach(radio => {
    	  radio.addEventListener('change', () => {
    	    if (radio.checked) {
    	      if (radio.value === 'associatiAlMercato') {
    	    	  document.querySelector('#associatomercato').style.display = '';
    	    	  document.querySelector('#nuovodaassociare').style.display = 'none';
    	      } else if (radio.value === 'nuovoDaAssociare') {
    	    	  document.querySelector('#associatomercato').style.display = 'none';
    	    	  document.querySelector('#nuovodaassociare').style.display = '';
    	      }
    	    }
    	  });
      });
	
	
	<c:if test="${empty scrollIntoStep}">
	window.addEventListener('load', () => {
		  const el = document.getElementById('id_step_${mercatidlivelloservizio.livelloServizioWizard.step}');
		  if (el) {
		    el.scrollIntoView();
		  }
		});
	</c:if>
	
	</script>
	
</body>
</html>