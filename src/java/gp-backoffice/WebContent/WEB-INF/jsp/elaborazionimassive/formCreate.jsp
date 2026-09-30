<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message
		key="label.elaborazioni_massive_schede_istanza" /></title>
</head>
<body>
	<span class="titoloPagina"> <fmt:message
			key="label.elaborazioni_massive_schede_istanza" />

	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../bollgestione/view" />
	</jsp:include>
	<div id="subcontent">

	<spring-form:form commandName="elaborazioniMassiveCreateCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="bollGeelaborazioniMassiveCreateCommandstTestata" />
		    </jsp:include>
		    
		    
		<div class="vbg-form">

		<fieldset>
				<legend>
					<fmt:message key="label.dettaglio" />
				</legend>
				<div class="form-group">
					<label><fmt:message key="label.descrizione" /></label>
					<spring-form:input id="descrizione_id" path="descrizione"
						size="100" maxlength="100" />
					<spring-form:errors path="descrizione" cssClass="error" />
				</div>
		</fieldset>
		<fieldset>
			
			<legend>
				<fmt:message key="label.filtri" />
			</legend>	    	
			    	
			    <fieldset>
			
						<legend>
							<fmt:message key="label.registri_selezionati" />
						</legend>
			    		<div class="form-group">
				    		
							<div class="nascondi_per_schede_selezionate">
								<jsp:include page="../includes/autocompletergenerico.jsp" >
									<jsp:param name="idElemento" value="tipologiaregistro" />		
									<jsp:param name="propertyPath" value="tipologiaregistro" />				
									<jsp:param name="pathPropertyDescription" value="tipologiaregistro.descrizione" />
									<jsp:param name="pathPropertyCode" value="tipologiaregistro.id" />
									<jsp:param name="autocompleterAjax" value="findTipologiaRegistri.htm" />	
									<jsp:param name="titleKey" value="label.ricerca_tipo_registro" />
									<jsp:param name="afterUpdateElement" value="popolaRegistri" />
								</jsp:include>
							</div>			
				    	</div>			    	
			    	
			    		
			    		
			    		<table class="vbg-table" id="registri_table">
							<thead>
								<th style="width: 80%"><fmt:message key="tipologiaregistri.label.tipologiaregistro" /></th>
								<th><fmt:message key="label.azioni" /></th>
							</thead>						
							<tbody id="registri_table_body">
				    		</tbody>
			    		</table>
			    	
			    	
			    	
			    	
			    </fieldset>				    	  
		    		
			    	
			    <fieldset>
			
						<legend>
							<fmt:message key="label.stato_istanza" />
						</legend>
			    		<div class="form-group">
				    		<label><fmt:message key="label.stato_istanza" /></label>
								
								<div class="nascondi_per_schede_selezionate">
									<spring-form:select id="statoistanza_id" path="statoistanza.codice">
									    <spring-form:option value="stato_tutte"><fmt:message key="label.tutte"/></spring-form:option>
									    <spring-form:option value="stato_aperte"><fmt:message key="label.tutte_le_istanze_non_chiuse"/></spring-form:option>
									    <spring-form:option value="stato_chiuse"><fmt:message key="label.tutte_le_istanze_chiuse"/></spring-form:option>
										<spring-form:options items="${elaborazioniMassiveCreateCommand.statiIstanzas}" itemValue="codice" itemLabel="descrizione"/>
									</spring-form:select>		
								</div>
				    	</div>		
    	
			    		
			    	
			    </fieldset>	
			    	
			   			
			    
			    
			<fieldset>
					
						<legend>
							<fmt:message key="label.bollettazione.seleziona_interventi" />
						</legend>		    	
					    	<fieldset>
					    		<div id="lista_interventi"></div>	
							</fieldset>
<div class="nascondi_per_schede_selezionate">							
							
							<div dojoType="dojo.data.ItemFileReadStore" 
							jsId="alberoprocStore" 
							url="${pageContext.request.contextPath}/json/getAlberoprocPerRuoli.htm?time=<%=System.currentTimeMillis() %>"></div>
							
							<div dojoType="dijit.tree.ForestStoreModel" jsId="alberoprocModel" 
							store="alberoprocStore"	query="{root:'1'}" rootId="<%= WebConstants.ATECO_CODICE_ROOT %>"
							rootLabel="<fmt:message key="label.albero_dei_procedimenti" />" childrenAttrs="children"></div>
							
							<div dojoType="dijit.Tree" id="tree2" model="alberoprocModel" />
							
<script type="dojo/method" event="onClick" args="item">
		if(item.id != '0'){
			var itemId = alberoprocStore.getValue(item, "id");
			var descrizioneEstesa = alberoprocStore.getValue(item, "descrizioneEstesa");
			var padre = alberoprocStore.getValue(item, "padre");
			cercaProcedimentoAjax(itemId, descrizioneEstesa)
		}
</script>

<script type="dojo/method" event="getIconClass" args="item, opened">
	if(item.id != '0'){
		var dis = 'false';
		if(item){
			dis = alberoprocStore.getValue(item, "disabilitato");
		}
		if(dis == 'false'){
			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpened" : "dijitFolderClosed") : "dijitLeaf"
		}else{
			return (!item || this.model.mayHaveChildren(item)) ? (opened ? "dijitFolderOpenedDisabled" : "dijitFolderClosedDisabled") : "dijitLeafDisabled"
		}
	}else{
		return "dijitFolderOpened"
	}
</script>

		
</div>		    	
					    	
		</fieldset>					    
			        	
			    	
		</fieldset>


 <fieldset>
			
		<legend>
			<fmt:message key="label.lista_schede_dinamiche_da_rielaborare" />
			<div id="messaggio_avviso_schede"></div>
		</legend>
   		<div class="form-group">
    		

				<jsp:include page="../includes/autocompletergenerico.jsp" >
					<jsp:param name="idElemento" value="dyn2Modellit_id" />		
					<jsp:param name="propertyPath" value="dyn2Modellit" />				
					<jsp:param name="pathPropertyDescription" value="dyn2Modellit.descrizione" />
					<jsp:param name="pathPropertyCode" value="dyn2Modellit.id" />
					<jsp:param name="autocompleterAjax" value="../elaborazionimassive/ajaxFindSchede.htm" />	
					<jsp:param name="titleKey" value="label.ricerca_modelli" />
					<jsp:param name="ajaxCallBack" value="filterRicercaSchede" />
					<jsp:param name="afterUpdateElement" value="popolaSchede" />
				</jsp:include>			    		

    	</div>		
	
   		<div id="schede_table">
   		
   		<table class="vbg-table" id="schede_table">
			<thead>
				<th style="width: 80%"><fmt:message key="label.modello" /></th>
				<th><fmt:message key="label.azioni" /></th>
			</thead>						
			<tbody id="schede_table_body">
    		</tbody>
   		</table>
   		
   		</div>
   	
   </fieldset>	


			
		</div>
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			
			<li><a
				href="javascript:creaElaborazione()"><fmt:message
						key="button.insert" /></a></li>
						
									
			<li><a href="javascript:doHref('list.htm','')"><fmt:message
						key="button.back" /></a></li>
		</ul>
	</div>
	
	<vbg-modal id="vbgmodal-validazione">
		<div slot='body'>
			<h1>
               <fmt:message key="label.result_validazione"/>
             </h1>
             <div id="vbgmodal-validazione-testo" class="error_header">
             </div>
		</div>
		<div slot='footer'>
			<div class="btn btn-primary" id="closeModalValidazione"><fmt:message key="button.close"/></div>            
		</div>
	</vbg-modal>
	
	
	
<script type="text/javascript">

var glbSchedeAssegnate = 0;

function gestSchedeAssegnate(schedeAssegnateVal){
	
	glbSchedeAssegnate += schedeAssegnateVal;
	
	abilitaDisabilitaFiltri(glbSchedeAssegnate>0);
	
}

function abilitaDisabilitaFiltri(disabilita){
	
	console.log('schedeAssegnate: '+glbSchedeAssegnate+', disabilita: ' + disabilita);
	let display = '';
	let testo = '';
	let classAvvisi = ''
	if(disabilita){
		display = 'none';
		testo = `<b>I filtri sono stati disabilitati in quanto e' stata aggiunta una scheda.<br/>
					Per modificare i filtri e' necessario eliminare le schede`;
					
		document.getElementById('messaggio_avviso_schede').classList.add("warning_header");
		document.getElementById('messaggio_avviso_schede').classList.add("alert");
		document.getElementById('messaggio_avviso_schede').classList.add("alert-warning");
	}else{
		document.getElementById('messaggio_avviso_schede').classList.remove("warning_header");
		document.getElementById('messaggio_avviso_schede').classList.remove("alert");
		document.getElementById('messaggio_avviso_schede').classList.remove("alert-warning");
	}
	document.querySelectorAll(".nascondi_per_schede_selezionate").forEach((child) => { 
		child.style.display = display;
	});
	document.getElementById('messaggio_avviso_schede').innerHTML = testo;
	
	
}


function filterRicercaSchede(element, entry) { 
	
		let registriElements = "";
	
		document.getElementsByName("registri").forEach((child) => { 
			registriElements+="&registri="+child.value;
		});
		let statoIstanza = "&statoistanza=" + document.getElementById('statoistanza_id').value;
		
		let interventiElements = "";
		
		document.getElementsByName("interventi").forEach((child) => { 
			interventiElements+="&interventi="+child.value;
		});
		
	
		return entry + registriElements + statoIstanza + interventiElements ;
	
}
	
// INTERVENTI
async function cercaProcedimentoAjax(codiceAlberoproc, descrizioneAlberoproc){
	
	if(isNaN(codiceAlberoproc)){
		alert("Ricerca per codice. Inserire un valore numerico");
		return;
	}
	
	const formData = new FormData();
    formData.append("id", codiceAlberoproc);
    
    vbg.mostraModalCaricamento();
	const result = await fetch("../elaborazionimassive/ajaxCheckAlberoproc.htm", {
        method: "POST",
        body: formData,
    });
	
	const esito = await result.json();
	vbg.nascondiModalCaricamento();
	if(esito.assegnabile === "false"){
		alert("Procedimento non assegnabile.");
		return;
	}
	assegnaIntervento(codiceAlberoproc, descrizioneAlberoproc);
}

function assegnaIntervento(codiceAlberoproc, descrizioneAlberoproc){
	
	var elementId = 'scCodice_' + codiceAlberoproc +'_'+ new Date().getTime();
	var htmlDaScrivere = '<div id="'+elementId+'_id"><span><a class="eliminaRiga nascondi_per_schede_selezionate" style="float: none;" href="javascript:eliminaIntervento(\''+ elementId +'\')" title="<fmt:message key="label.elimina" />"><label><fmt:message key="label.elimina.image" /></label></a></span>'+
						 '&nbsp;<input type="hidden" name="interventi" id="'+elementId+ '" value="'+codiceAlberoproc+'" />'+						 
						 descrizioneAlberoproc+'&nbsp;('+codiceAlberoproc+')&nbsp;</div>' ;
	if(document.getElementById('lista_interventi')){
		document.getElementById('lista_interventi').innerHTML = document.getElementById('lista_interventi').innerHTML + htmlDaScrivere;
		applyStyle();
	}
}

function eliminaIntervento(elementId){		
		
		document.getElementById(elementId+"_id").remove();		
	
}	

// SCHEDE
function eliminaScheda(codice){
	
	let tableBody = document.getElementById('schede_table_body');
	let righe = tableBody.children;
	let rigaTrovata = null;
	if(righe.length>0){
		for (const riga of righe) {
			console.log(riga);
			if(riga.getAttribute('data-id') === codice){
				riga.remove();				
				gestSchedeAssegnate(-1);
			}
		};
	}
	
}

function popolaSchede(inputField,listItem){
	
	vbg.mostraModalCaricamento();
	let codice = listItem.id;
	let descrizione  = inputField.value;
	
	let tableBody = document.getElementById('schede_table_body');
	let righe = tableBody.children;
	let ordine = 0;
	if(righe.length>0){
		for (const riga of righe) {
			ordine++;
			console.log(riga);
			if(riga.getAttribute('data-id') === codice){
				inputField.value = '';
				vbg.nascondiModalCaricamento();
				return;
			}
		};
	}
	
	let indice = ordine --;
	
	console.log('Aggiungo ');
	
	let tr = document.createElement('tr');
	tr.setAttribute('data-id', codice);
	
	
	let colonnaRegistro = document.createElement('td');
	colonnaRegistro.innerHTML = descrizione;
	
	let colonnaAzioni = document.createElement('td');
	let azioni = '<span><a class="eliminaRiga" ' +
			'style="float: none;" href="javascript:eliminaScheda(\''+ codice +'\')" '+
			'title="<fmt:message key="label.elimina" />"><label><fmt:message key="label.elimina.image" /></label></a></span>';
	
	azioni+='<span class="sposta_elementi_schede">';
	azioni+=' <span><a class="upColumn" href="javascript:spostaSchedaUp(\''+ codice +'\')" title="<fmt:message key="label.down" />"><label><fmt:message key="label.azioni" /></label></a></span>';
	azioni+=' <span><a class="downColumn" href="javascript:spostaSchedaDown(\''+ codice +'\')" title="<fmt:message key="label.down" />"><label><fmt:message key="label.azioni" /></label></a></span>';
	azioni+='</span>';
	azioni+='&nbsp;<input type="hidden" name="scheda_id_scheda" value="'+codice+'" />';
	azioni+='&nbsp;<input type="hidden" name="scheda_ordine" value="'+ordine+'" />';
	
	
	colonnaAzioni.innerHTML = azioni;
	
								
								
								
	tr.appendChild(colonnaRegistro);
	tr.appendChild(colonnaAzioni);
	
	tableBody.appendChild(tr);   
	inputField.value = '';
	
	gestSchedeAssegnate(1);
	
	vbg.nascondiModalCaricamento();
	
}

function spostaSchede(isUp, codice){
	
	let tableBody = document.getElementById('schede_table_body');
	let righe = tableBody.children;
	let rigaTrovata = null;	
	
	
	if(righe.length>0){
		for (const riga of righe) {
			console.log(riga);
			if(riga.getAttribute('data-id') === codice){
				rigaTrovata = riga;				
			}
		};
	}
	
	
	let next = rigaTrovata.nextSibling;
	let prev = rigaTrovata.previousSibling;
	let par = rigaTrovata.parentNode;
	
	if (isUp){
		if (prev) {
		    par.removeChild(rigaTrovata);
		    par.insertBefore(rigaTrovata, prev);
		}
	}else{
		if (next) {			
			par.removeChild(next);
		    par.insertBefore(next, rigaTrovata);
		}
	}
	
}

function spostaSchedaUp(codice){
	spostaSchede(true, codice);	
}

function spostaSchedaDown(codice){
	spostaSchede(false, codice);
}

 // REGISTRI 
function eliminaRegistro(codice){
	let tableBody = document.getElementById('registri_table_body');
	let righe = tableBody.children;
	let rigaTrovata = null;
	if(righe.length>0){
		for (const riga of righe) {
			console.log(riga);
			if(riga.getAttribute('data-id') === codice){
				riga.remove();				
			}
		};
	}
	
}


function popolaRegistri(inputField,listItem){
	
	vbg.mostraModalCaricamento();
	let codice = listItem.id;
	let descrizione  = inputField.value;
	
	let tableBody = document.getElementById('registri_table_body');
	let righe = tableBody.children;
	if(righe.length>0){
		for (const riga of righe) {
			console.log(riga);
			if(riga.getAttribute('data-id') === codice){
				inputField.value = '';
				vbg.nascondiModalCaricamento();
				return;
			}
		};
	}
	console.log('Aggiungo ');
	
	
	let tr = document.createElement('tr');
	tr.setAttribute('data-id', codice);
	
	
	let colonnaRegistro = document.createElement('td');
	colonnaRegistro.innerHTML = descrizione +
								'&nbsp;<input type="hidden" name="registri" value="'+codice+'" />';
	
	
	let colonnaAzioni = document.createElement('td');
	colonnaAzioni.innerHTML = '<span><a class="eliminaRiga nascondi_per_schede_selezionate" ' +
								'style="float: none;" href="javascript:eliminaRegistro(\''+ codice +'\')" '+
								'title="<fmt:message key="label.elimina" />"><label><fmt:message key="label.elimina.image" /></label></a></span>';
	
	tr.appendChild(colonnaRegistro);
	tr.appendChild(colonnaAzioni);
	
	tableBody.appendChild(tr);   
	inputField.value = '';
	vbg.nascondiModalCaricamento();
}





// GENERICI
	async function validaInserimento(){
		
		
		const formData = new FormData( document.inviodati );

	    
		const result = await fetch("ajaxValidaCommand.htm", {
	        method: "POST",
	        body: formData,
	    });
		const esito = await result.text();
		return esito;
	}
	
	async function creaElaborazione(){
		vbg.mostraModalCaricamento();
		
		let validazione = await validaInserimento();
		
		
		if(validazione==''){
			doSubmit('insertElaborazione.htm','',document.inviodati);
			return;
		}
		
		vbg.nascondiModalCaricamento();
		
		let divEsito = document.getElementById('vbgmodal-validazione-testo');
		divEsito.innerHTML =  validazione.replace('\n','<br />') ;
		modalValidazione.open();
	}
	
	const modalValidazione = document.getElementById('vbgmodal-validazione');
	
	document.getElementById('closeModalValidazione').addEventListener('click', (e)=>{
		modalValidazione.close();		
	});
	
</script>

</body>
</html>