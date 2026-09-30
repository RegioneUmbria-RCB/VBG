<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>

<!-- Recupero i parametri -->
<c:set var="movimento" value=""/>
<c:if test="${not empty param.movimento }">
	<c:set var="movimento" value="${ param.movimento }"/>
</c:if>
<c:set var="codicemovimento" value=""/>
<c:if test="${not empty param.codicemovimento }">
	<c:set var="codicemovimento" value="${ param.codicemovimento }"/>
</c:if>
<c:set var="isZipLogico" value=""/>
<c:if test="${not empty param.isZipLogico}">
	<c:set var="isZipLogico" value="${param.isZipLogico}"/>
</c:if>
<c:choose>
	<c:when test="${param.displayNone eq true }">
		<% String displayNone = "display:none;"; %>
		<c:set var="displayNoneVar" value="<%=displayNone%>"/>
	</c:when>
	<c:otherwise>
		<c:set var="displayNoneVar" value=""/>
	</c:otherwise>
</c:choose>
<c:set var="labelForFlgZipLogicoChbx" value=""/>
<c:if test="${not empty param.labelForFlgZipLogicoChbx}">
	<c:set var="labelForFlgZipLogicoChbx" value="${param.labelForFlgZipLogicoChbx}"/>
</c:if>

<c:set var="pathProperty" value=""/>
<c:if test="${not empty param.commandPathProperty }">
	<c:set var="pathProperty" value="${param.commandPathProperty}"/>
</c:if>

<c:set var="help" value=""/>
<c:if test="${not empty param.help }">
	<c:set var="help" value="${param.help}"/>
</c:if>

<!-- classi css OBBLIGATORIE -->
<c:set var="hideDocAltrimov" value="${param.hideDocAltrimov}"/>
<c:set var="inputMaChbx" value="${param.inputMaChbx}"/>

<c:set var="hideDocist" value="${param.hideDocist }"/>
<c:set var="inputIstChbx" value="${param.inputIstChbx }"/>

<c:if test="${not empty param.hideDocproc and not empty param.inputProcChbx  }">
	<c:set var="hideDocproc" value="${param.hideDocproc }"/>
	<c:set var="inputProcChbx" value="${param.inputProcChbx }"/>
</c:if>

<c:set var="hideDocendo" value="${param.hideDocendo }"/>
<c:set var="inputEndoChbx" value="${param.inputEndoChbx }"/>

<c:set var="hideDocanag" value="${param.hideDocanag }"/>
<c:set var="inputAnagChbx" value="${param.inputAnagChbx }"/>

<c:if test="${ not empty param.hideDoccds and not empty param.inputCdsChbx }">
	<c:set var="hideDoccds" value="${param.hideDoccds }"/>
	<c:set var="inputCdsChbx" value="${param.inputCdsChbx }"/>
</c:if>
<c:set var="radioBtn" value="" />
<c:if test="${ not empty param.isRadioBtn and param.isRadioBtn eq true }">
 <c:set var="radioBtn" value="${param.isRadioBtn }" />
</c:if>
<c:set var="colspan" value="" />
<c:if test="${ not empty param.colspan }">
 	<c:set var="colspan" value="${param.colspan }" />
</c:if>
<c:set var="gestisciDocPrincipale" value="true" />
<c:if test="${ not empty param.gestisciDocPrincipale }">
 	<c:set var="gestisciDocPrincipale" value="${param.gestisciDocPrincipale }" />
</c:if>

<c:if test="${isZipLogico eq true }">
	<tr id="zip_logico_row" style="${displayNoneVar}">
		<td><label for="flg_zip_logico_id"><fmt:message key="${labelForFlgZipLogicoChbx}"/></label></td>
		<td colspan="${colspan}">
			<div>
				<div>
				<c:choose>
					<c:when test="${isDocumentoZipLogicoCollegato}">
						<spring-form:checkbox id="flg_zip_logico_id" path="${pathProperty}" disabled="true"/>
						<label class="error_header alert alert-danger" for="flg_zip_logico_id"><fmt:message key="label.movimenti_zip_logico.doc_principale_esiste"/></label>					
					</c:when>
					<c:otherwise>
						<spring-form:checkbox id="flg_zip_logico_id" path="${pathProperty}" onclick="hideOtherDocumentSectionsIfFlgZipLogicoChecked(${radioBtn})" />
						<label for="flg_zip_logico_id"><fmt:message key="${help }"/></label>
					</c:otherwise>
				
				</c:choose>
					
					
					<div id="clip-popup-zip-logico" title='<fmt:message key="label.click_per_visualizzare"/>'
						 data-role="open-vbg-modal" 
						 data-target-modal-id="movimenti_zip_logico_id"
						 style="display: inline-block; cursor: pointer;"
						 >
						<i class="fa fa-paperclip fa-2x" aria-hidden="true"></i>
					</div>
				    <div  class='vbg-modal'
				    	 id="movimenti_zip_logico_id" 
						 title="<fmt:message key="label.movimenti_zip_logico.documenti_zip" />: [<c:out value="${movimento}" />]">
						 
							<c:import url="/ajax/dettaglioZipLogico.htm">
								<c:param name="codMovimento">${codicemovimento}</c:param>
							</c:import> 
					
					</div>
					
					
				</div>
			</div>
		</td>
	</tr>
</c:if>
<script type="text/javascript">

	var lclGestisciDocPrincipale = ${gestisciDocPrincipale};
	var iconZipLogicoSettate = false;
	function hideOtherDocumentSectionsIfFlgZipLogicoChecked(ifRadioBtn) {
		
		var codiciOggettoZip = recuperaCodiciOggetto();
		var codiceOggettoDocZipLogico = recuperaCodiciOggettoZipLogico();
		// ricerco tutti i codici oggetto presenti nello zip logico e gli assegno una classe 
		assegnaClasseAOggettiZipLogico(codiciOggettoZip);
		gestisciCheckBoxesAOggettiZipLogico(codiciOggettoZip);
		if(lclGestisciDocPrincipale){
			gestisciDocPrincipale(codiceOggettoDocZipLogico);
		}
	}
	
	function gestisciDocPrincipale(codiceOggettoDocZipLogico, ifRadioBtn){
		
		if(codiceOggettoDocZipLogico){
				let check = document.querySelector('#flg_zip_logico_id').checked;
				
				let elementi = document.querySelectorAll('.colonna-allegati-selezionabili');
				elementi.forEach( td => {
					let codiceOggetto = td.getAttribute('data-codiceoggetto');
					
					if(codiceOggetto===codiceOggettoDocZipLogico){
						
						td.querySelectorAll('input[type=checkbox]').forEach( input => {
							if(check){
								input.checked = true;
							}else{
								input.checked = false;
							}
							
							if(codiceOggettoDocZipLogico && td.getAttribute('data-codiceoggetto')===codiceOggettoDocZipLogico){
								td.querySelectorAll('input[type=checkbox]').forEach( input => {
									input.addEventListener('click', checkZipLogicoSelezionato);
							});
						}
							
						});
					}
				});			
		}
	}
	
	function gestisciCheckBoxesAOggettiZipLogico(codiciOggettoZip){
		
		let check = document.querySelector('#flg_zip_logico_id').checked;
		// class="colonna-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }"
		let elementi = document.querySelectorAll('.colonna-allegati-selezionabili');
		elementi.forEach( td => {
			console.log('gestisciCheckBoxesAOggettiZipLogico: '+ td.getAttribute('data-codiceoggetto'));
			if(codiciOggettoZip.includes(td.getAttribute('data-codiceoggetto'))){		
				console.log('gestisciCheckBoxesAOggettiZipLogico:' + td.getAttribute('data-codiceoggetto')+ ' presente nell\'array ');
				if(check){
					td.querySelectorAll('input').forEach( input => {
						input.setAttribute('disabled', 'disabled');
						input.checked = false;
					});
				}else{
					td.querySelectorAll('input').forEach( input => {
						input.removeAttribute('disabled');
					
					});
				}
			}
			
		});
		
	}
	
	function checkZipLogicoSelezionato(e, ifRadioBtn){
		
		let checkBoxEl = document.querySelector('#flg_zip_logico_id');
		let check = checkBoxEl.checked;
		let checkDoc = e.target.checked;
		var selezionato = check;
		if(checkDoc && !check){
			checkBoxEl.click();
		}
		if(!checkDoc && check){
			checkBoxEl.click();
		}
	}
	function assegnaClasseAOggettiZipLogico(codiciOggettoZip){
		
		let check = document.querySelector('#flg_zip_logico_id').checked;
		
		// class="riga-allegati-selezionabili" data-codiceoggetto="${ var.codiceOggetto }" 
		let elementi = document.querySelectorAll('.riga-allegati-selezionabili');
		elementi.forEach( tr => {
			console.log('assegnaClasseAOggettiZipLogico: '+ tr.getAttribute('data-codiceoggetto'));
			if(codiciOggettoZip.includes(tr.getAttribute('data-codiceoggetto'))){
				console.log('assegnaClasseAOggettiZipLogico:' + tr.getAttribute('data-codiceoggetto')+ ' presente nell\'array ');
				
				if(!iconZipLogicoSettate){
					tr.firstElementChild.innerHTML = '<i  title="Il file appartiene allo zip logico del movimento" class="fa fa-file-archive-o fa-2x" aria-hidden="true" style="margin-right: 6px"></i>' + tr.firstElementChild.innerHTML ;
				}
				if(check){
					tr.classList.add("disabilitata-zip-logico");
					tr.setAttribute('title','Non è possibile utilizzare il documento in quanto registrato nello zip logico')
				}else{
					tr.classList.remove("disabilitata-zip-logico");
					tr.removeAttribute('title');
				}
			}
		});
		
	 	iconZipLogicoSettate = true;
		
	}
	

	
	function recuperaCodiciOggettoZipLogico(){
		// recupero il codice oggetto del doc principale
		//  id="zip-logico-content-table" data-codiceoggetto-doc-allegato="${ codiceoggettoDocAll }"
		let codiceOggetto = document.querySelector('#zip-logico-content-table').getAttribute('data-codiceoggetto-doc-allegato');
		console.log("data-codiceoggetto-doc-allegato: "+codiceOggetto);
		return codiceOggetto;
	}
	
	function recuperaCodiciOggetto(){
		// recupero i codicioggetto dello zip logico
		// class="riferimenti-zip-logico" data-codiceoggetto="${ziplogico_var.codiceOggetto}" data-id="${ziplogico_var.id.codice }"
		
		var codiciOggetto = [];
		 
		let elementi = document.querySelectorAll('.riferimenti-zip-logico');
		elementi.forEach( el => {
			console.log(el.getAttribute('data-codiceoggetto'));
			codiciOggetto.push(el.getAttribute('data-codiceoggetto'));
		});
		console.log('recuperaCodiciOggetto: '+codiciOggetto);
		return codiciOggetto;
		
		
	}
	function showOtherDocumentSections() {
		
		jQuery("${hideDocAltrimov}").show();
		jQuery("${hideDocist}").show();
		
		<c:if test="${ not empty param.hideDocproc }">
			jQuery("${hideDocproc}").show();
		</c:if>
		
		jQuery("${hideDocendo}").show();
		jQuery("${hideDocanag}").show();
		
		<c:if test="${ not empty param.hideDoccds }">
			jQuery("${hideDoccds}").show();
		</c:if>
		
	}
	
	function hideDocumentSectionsAndUncheckCheckboxes(ifIncludeRadioBtn) {
		
		// debugger;
		hideOtherDocumentSections();
		
		uncheckCheckboxes("${inputMaChbx}");
		uncheckCheckboxes("${inputIstChbx}");
		
		<c:if test="${not empty param.inputProcChbx  }">
			uncheckCheckboxes("${inputProcChbx}");
		</c:if>
		
		uncheckCheckboxes("${inputEndoChbx}");
		uncheckCheckboxes("${inputAnagChbx}");
		
		<c:if test="${not empty param.inputCdsChbx }">
			uncheckCheckboxes("${inputCdsChbx}");
		</c:if>
		
		if(ifIncludeRadioBtn != null && ifIncludeRadioBtn) {
			uncheckRadioIfChecked();
		}
		
	}
	
	function hideOtherDocumentSections() {
		
		jQuery("${hideDocAltrimov}").hide();
		jQuery("${hideDocist}").hide();
		
		<c:if test="${ not empty param.hideDocproc }">
			jQuery("${hideDocproc}").hide();
		</c:if>
		
		jQuery("${hideDocendo}").hide();
		jQuery("${hideDocanag}").hide();
		
		<c:if test="${ not empty param.hideDoccds }">
			jQuery("${hideDoccds}").hide();
		</c:if>
		
	}
	
	function uncheckCheckboxes(idChbx) {
		jQuery(idChbx).each(function () {
			if (jQuery(this).prop("checked") == true) {
				jQuery(this).prop("checked", false);
			}
		});
	}
	
	function showHiddenDocumentSections() {
		
		// debugger;
		
		if (jQuery('#flg_zip_logico_id').prop('checked') == true) {
			jQuery('#flg_zip_logico_id').prop('checked', false);
		}
		
		showDocumentSectionsIfAreHidden("${hideDocAltrimov}");
		showDocumentSectionsIfAreHidden("${hideDocist}");
		
		<c:if test="${ not empty param.hideDocproc }">
			showDocumentSectionsIfAreHidden("${hideDocproc}");
		</c:if>
		
		showDocumentSectionsIfAreHidden("${hideDocendo}");
		showDocumentSectionsIfAreHidden("${hideDocanag}");
		
		<c:if test="${ not empty param.hideDoccds }">
			showDocumentSectionsIfAreHidden("${hideDoccds}");
		</c:if>
		
	}
	
	function showDocumentSectionsIfAreHidden(classDocSection) {
		if (jQuery(classDocSection).is(":hidden")) {
			jQuery(classDocSection).show();
		}
	}
	
	function uncheckRadioIfChecked() {
		jQuery("input[id^='radio_button_id']").each(function () {
			if (jQuery(this).prop("checked") == true) {
				jQuery(this).prop("checked", false);
			}
		});
	}
	

	
</script>