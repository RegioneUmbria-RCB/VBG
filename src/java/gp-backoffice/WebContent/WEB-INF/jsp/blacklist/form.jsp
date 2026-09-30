<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.RicalcoloAreeBean"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.blacklistpd" />
	</title>
</head>
<body>
<c:set var="CSI_BLACKLIST_NUMPAGE" value="0" />
<c:set var="CSI_BLACKLIST_MAXRES" value="5000" />
<c:set var="CSI_BLACKLIST_ESCLUDI_BY_TOT" value="30" />
<c:set var="TOT_RECORD_ESTRATTI_OR_DEF" value="${not empty totrecordestratti ? totrecordestratti : 0 }" />
<span class="titoloPagina">
	<fmt:message key="label.blacklistpd" />
</span>
<br>
<div id="subcontent">    
    <ul class="listaSchede">
       <li><a id="raggruppatoScheda" class="${contesto == 'presenze' ? 'SchedaAttiva' : '' }" href="view.htm?contesto=presenze"><fmt:message key="label.contesto.schedapresenze" /></a></li>
       <li><a id="raggruppatoScheda" class="${contesto == 'bollettazione' ? 'SchedaAttiva' : '' }" href="view.htm?contesto=bollettazione"><fmt:message key="label.contesto.schedaboll" /></a></li>
    </ul>
	<spring-form:form commandName="blacklistpd" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="blacklistpd" />
    </jsp:include>	
	<div class="vbg-form">
	
	   <div class="btn btn-primary" onclick="esportaBlChiuse()"><fmt:message key="label.accertamenti_conclusi_export" /><a id="esporta_bl_chiuse_id" href="generateReportBlackListChiuse.htm?contesto=${contesto}"></a></div>
	   <br>
	   <br>
	
	   <fieldset id="filtri_fieldset" class="collassabile">
	   <legend id="legend_filtri_ricerca" ><fmt:message key="label.filtri"/></legend>
	   
	   <div class="form-group">
	   <label><fmt:message key="label.date_accertamento" /></label>
	   <span style="visibility:hidden;"><fmt:message key="form.istanze.data.inizio" /></span>
	   <select class="input_select_class" name="dataaccertamentoselect"  ${ dropdownDateList.size() > 10 ? 'onfocus="this.size=3;" onblur="this.size=1;" onchange="this.size=1; this.blur();"' : '' }>	     
	     <c:forEach items="${dropdownDateList}" var="dropdowndate_var" varStatus="i">
	          <option value="${dropdowndate_var}"   ${dropdowndate_var == dataaccertamentoselect ? 'selected' : '' }   >${dropdowndate_var}</option>
	     </c:forEach>	     
	   </select>	   
	   
	   </div>	   
	   	   	   
	   <div class="form-group">
	   <label><fmt:message key="label.blacklist" /></label>
	   
	   <span><fmt:message key="form.istanze.data.inizio" /></span>
	   <input type="text" id="dataInizio_id" class="input_text_class" name="dallaData" size="10" maxlength="10" onblur="isValidDate(this,true);" value="${dallaData }"/>
	   <init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataInizio_id" textKey="label.calendar"/>
	   
	   <span><fmt:message key="form.istanze.data.fine" /></span>
	   <input type="text" id="dataFine_id" class="input_text_class" name="allaData" size="10" maxlength="10" onblur="isValidDate(this,true);" value="${allaData }"/>
	   <init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" textKey="label.calendar"/>
	   </div>
	   
	   <div class="form-group">
	   <label><fmt:message key="label.iuv" /></label>
	   
	   <span><fmt:message key="form.istanze.data.inizio" /></span>
	   <input type="text" id="dataInizio_iuv_id" class="input_text_class" name="dallaDataIuv" size="10" maxlength="10" onblur="isValidDate(this,true);" value="${dallaDataIuv }"/>
	   <init:calendar imagePath="/images/cal.gif" idImage="caldatainizioIuv" idInput="dataInizio_iuv_id" textKey="label.calendar"/>
	   
	   <span><fmt:message key="form.istanze.data.fine" /></span>
	   <input type="text" id="dataFine_iuv_id" class="input_text_class" name="allaDataIuv" size="10" maxlength="10" onblur="isValidDate(this,true);" value="${allaDataIuv }"/>
	   <init:calendar imagePath="/images/cal.gif" idImage="caldatafineIuv" idInput="dataFine_iuv_id" textKey="label.calendar"/>
	   </div>
	   
	   <div class="form-group">
	   <label><fmt:message key="label.iuvnumero" /></label>
	   <span style="visibility:hidden;"><fmt:message key="form.istanze.data.inizio" /></span>	   
	   <input type="text" class="input_text_class" name="iuvnumeroname" value="${iuvnumeroname }"/>
	   </div>
	   
	   <div class="form-group">
	   <label><fmt:message key="form.anagrafe.codicefiscale" /></label>
	   <span style="visibility:hidden;"><fmt:message key="form.istanze.data.inizio" /></span>	   
	   <input type="text" class="input_text_class" name="codicefiscalename" value="${codicefiscalename }"/>
	   </div>		
	   
	   <div class="form-group">
	   <label><fmt:message key="label.concessione_titolare" /></label>
	   <span style="visibility:hidden;"><fmt:message key="form.istanze.data.inizio" /></span>	   
	   <input type="text" class="input_text_class" name="titolarename" value="${titolarename }"/>
	   </div>	   	   				   
	   
	</div>
	<input type="hidden" name="contestohidden" value="${contesto }"></input>
	<input type="hidden" name="firstresult_name" value="${CSI_BLACKLIST_NUMPAGE }"></input>
	<input type="hidden" name="maxresult_name" value="${CSI_BLACKLIST_MAXRES }"></input>
	<input id="chkbox_escludi_id" type="hidden" name="nascondi_importi_name" value="${nascondiImportiName }"></input>
</spring-form:form>
</div>
<div id="functions">
	<ul>
	    <div class="btn btn-primary" onclick="doSubmit('cerca.htm','',document.inviodati);" ><fmt:message key="button.search" /></div>
	    <div class="btn btn-secondary" onclick="pulisciFiltri()" ><fmt:message key="button.pulisci" /></div>
		<div class="btn btn-secondary" onclick="doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')" ><fmt:message key="button.back" /></div>
	</ul>
</div>
<br>
<br>
<div class="vbg-form">
<div class="form-group">	   
	   <input type="checkbox" class="input_checkbox_class" value="true" onclick="escludiMinimoTotaleByCodiceFiscaleClick(this)" ${nascondiImportiName ? 'checked' : '' }></input>
	   <label style="width:auto;">
	   <fmt:message key="label.escludi_importi_sotto_tot">
        <fmt:param value="${CSI_BLACKLIST_ESCLUDI_BY_TOT}" />
       </fmt:message>
	   </label>
</div>
</div>
<br>
<br>
<span style="color:red;font-weight:bold;">
<fmt:message key="label.limite.ricerca.blaclist.csi">
    <fmt:param value="${CSI_BLACKLIST_MAXRES}" />
</fmt:message>
</span>
<span id="totalerecordgreen_id" style="color:green;font-weight:bold;display:${isRicerca and (TOT_RECORD_ESTRATTI_OR_DEF < CSI_BLACKLIST_MAXRES) ? 'block' : 'none'};margin-top:4px;">Totale record estratti <span class="totrecordestrattic">${TOT_RECORD_ESTRATTI_OR_DEF }</span></span>
<span id="totalerecordred_id" style="color:red;font-weight:bold;display:${isRicerca and (TOT_RECORD_ESTRATTI_OR_DEF >= CSI_BLACKLIST_MAXRES) ? 'block' : 'none'};margin-top:4px;">Totale record estratti <span class="totrecordestrattic">${TOT_RECORD_ESTRATTI_OR_DEF }</span> applicare ulteriori filtri</span>
<div id="blackListDivId">

<table class="vbg-table">
									 	<thead>
									 	
									 	   <c:if test="${contesto == 'presenze'}">									 	    									 	
											<tr>
											    <th><input type="checkbox" id="selectallchck_id" onclick="selectDeselectAllCheckBox()"></input></th>
												<th>IUV</th>
												<th>DESCRIZIONE</th>
												<th>MERCATO</th>
												<th>DATA</th>
												<th>TITOLARE</th>
												<th>IMPORTO</th>
												<th>DATA INIZIO BLACKLIST</th>
												<th>DATA ACCERTAMENTO</th>
												<th>TOTALE PER CODICE FISCALE</th>
											</tr>											
										   </c:if>
									 	
									 	 
									 	   <c:if test="${contesto == 'bollettazione'}">									 	    									 	
											<tr>
											    <th><input type="checkbox" id="selectallchck_id" onclick="selectDeselectAllCheckBox()"></input></th>
												<th>IUV</th>
												<th>DATA</th>
												<th>CAUSALE</th>
												<th>TITOLARE</th>
												<th>IMPORTO</th>
												<th>DATA INIZIO BLACKLIST</th>
												<th>DATA ACCERTAMENTO</th>
												<th>TOTALE PER CODICE FISCALE</th>
											</tr>											
										   </c:if>	 	
									 	</thead>
					<tbody>
					
					<c:if test="${contesto == 'presenze'}">
						<c:forEach items="${blacklistResultList}" var="blacklist_var" varStatus="i">
	                       <tr>
					         <td><input type="checkbox" class="chckbxpd" value="${blacklist_var.iddettposizionedebitoria }" data-bl-id="${blacklist_var.idblacklistmotivi}" onclick="selectDeselectMainCheckBox(this)"></input></td>
					         <td>${blacklist_var.iuv }</td>
					         <td>${blacklist_var.descrizione }</td>
					         <td>${blacklist_var.mercato }</td>
					         <td>${blacklist_var.dataStr }</td>					         
					         <td data-cfpiva="${blacklist_var.codicefiscale }">${blacklist_var.titolare }</td>
					         <td data-importobd="${blacklist_var.importo }">${blacklist_var.importoStr }</td>
					         <td>${blacklist_var.dataInizioBlacklistStr }</td>
					         <td>${blacklist_var.dataAccertamentoStr }</td>
					         <td data-importotottd></td>
					       </tr>
	                    </c:forEach>
	                    
	                    <c:if test="${empty blacklistResultList}">
					      <tr class="csiNorecordsTable">
					       <td colspan=10> ${isRicerca ? 'Nessun record trovato' : 'Effettuare una ricerca' } </td>
					      </tr>
					    </c:if>		                    
					</c:if>
					
					<c:if test="${contesto == 'bollettazione'}">
						<c:forEach items="${blacklistResultList}" var="blacklist_var" varStatus="i">
	                       <tr>
					         <td><input type="checkbox" class="chckbxpd" value="${blacklist_var.iddettposizionedebitoria }" data-bl-id="${blacklist_var.idblacklistmotivi}" onclick="selectDeselectMainCheckBox(this)"></input></td>
					         <td>${blacklist_var.iuv }</td>
					         <td>${blacklist_var.dataStr }</td>
					         <td>${blacklist_var.descrizione }</td>
					         <td data-cfpiva="${blacklist_var.codicefiscale }">${blacklist_var.titolare }</td>
					         <td data-importobd="${blacklist_var.importo }">${blacklist_var.importoStr }</td>
					         <td>${blacklist_var.dataInizioBlacklistStr }</td>
					         <td>${blacklist_var.dataAccertamentoStr }</td>
					         <td data-importotottd></td>
					       </tr>
	                    </c:forEach>
	                    
	                    <c:if test="${empty blacklistResultList}">
					      <tr class="csiNorecordsTable">
					       <td colspan=9> ${isRicerca ? 'Nessun record trovato' : 'Effettuare una ricerca' } </td>
					      </tr>
					    </c:if>		
					</c:if>
					
					
					
															
					</tbody>
</table>
<div id="functions">
	<ul>
		<li><a href="javascript:void(0)" onclick="avviaAccertamentoMultiplo()" ><fmt:message key="input.button.avviaaccertamento" /></a></li>
		<li><a href="javascript:void(0)" onclick="chiudiAccertamentoMultiplo()" ><fmt:message key="input.button.concludiaccertamento" /></a></li>
	</ul>
</div>
</div>
<script type="text/javascript">

const isEscludiChecked = ${nascondiImportiName ? true : false};
const minimoTotale = ${CSI_BLACKLIST_ESCLUDI_BY_TOT};
const massimoResult = ${CSI_BLACKLIST_MAXRES};
const totaleFormatter = new Intl.NumberFormat('it-IT', {
	  minimumFractionDigits: 2,
	  maximumFractionDigits: 2
	});
			
async function avviaAccertamentoSingolo(iddettpd, idbl){
	
	try{
		debugger;
	    const resp = await fetch("../blacklist/ajaxAvviaAccertamentoSingolo.htm?iddettpd="+iddettpd+"&idbl="+idbl, {
	        method: "GET",
	        cache: "no-cache",
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    });

	    if (!resp.ok) {
	        throw new Error("Errore HTTP: " + resp.status);
	        console.log('Ho avuto l errore');
	    }
	    
	    }catch(e){
	    	console.error("Errore nella chiusura della posizione debitoria: " + iddettpd, e);
	    	return 0;
	    }
	    
	    return 1;

}

async function chiudiAccertamentoSingolo(iddettpd, idbl){
	
	try{
	    const resp = await fetch("../blacklist/ajaxConcludiAccertamentoSingolo.htm?iddettpd="+iddettpd+"&idbl="+idbl, {
	        method: "GET",
	        cache: "no-cache",
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    });

	    if (!resp.ok) {
	        throw new Error("Errore HTTP: " + resp.status);
	        console.log('Ho avuto l errore');
	    }
	    
	    }catch(e){
	    	console.error("Errore nella chiusura della posizione debitoria: " + iddettpd, e);
	    	return 0;
	    }
	    
	    return 1;
}
async function chiudiAccertamentoMultiplo() {
    
    const checkedBoxes = document.querySelectorAll(".chckbxpd:checked");

    if (checkedBoxes.length === 0) {
        return;
    }

    const chckNumber = checkedBoxes.length;
    
    const conferma = confirm(
            'Confermi la chiusura di ' + chckNumber + ' posizioni selezionate?'
    );

    if (!conferma) return;
    
    
    let posizioniannullate = 0;
    window.vbg.mostraModalCaricamento();

    const modal = document.getElementById("modal-attendere-prego");
    const h1 = modal.querySelector("h1");
    h1.textContent = "Operazione CHIUSURA ACCERTAMENTO";

    const p = modal.querySelector("p");

    for (let i = 0; i < chckNumber; i++) {
        p.textContent = "In corso annullamento di " + (i + 1) + " di " + chckNumber + " posizioni...";
        
        if(await chiudiAccertamentoSingolo(checkedBoxes[i].value, checkedBoxes[i].dataset.blId) === 1){
        	posizioniannullate = posizioniannullate + 1;
        }
    }
    
    window.vbg.nascondiModalCaricamento();
    
    if(chckNumber === posizioniannullate ){
    	window.location.replace("view.htm?contesto=${contesto}&status_msg=02");
    }else{    	
    	await eseguiLogAuditChiusure(chckNumber);    	
    	window.location.replace("view.htm?contesto=${contesto}");
    }        
}


async function avviaAccertamentoMultiplo() {	
	
	const checkedBoxes = document.querySelectorAll(".chckbxpd:checked");

    if (checkedBoxes.length === 0) {
        return;
    }

    const chckNumber = checkedBoxes.length;
    
    const conferma = confirm(
            'Confermi l\'avvio accertamento di ' + chckNumber + ' posizioni selezionate?'
    );

    if (!conferma) return;
    
    
    let posizioniannullate = 0;
    window.vbg.mostraModalCaricamento();

    const modal = document.getElementById("modal-attendere-prego");
    const h1 = modal.querySelector("h1");
    h1.textContent = "Operazione AVVIO ACCERTAMENTO";

    const p = modal.querySelector("p");

    for (let i = 0; i < chckNumber; i++) {
        p.textContent = "In corso avvio accertamento di " + (i + 1) + " di " + chckNumber + " posizioni...";
        
        if(await avviaAccertamentoSingolo(checkedBoxes[i].value, checkedBoxes[i].dataset.blId ) === 1){
        	posizioniannullate = posizioniannullate + 1;
        }
    }
    
    window.vbg.nascondiModalCaricamento();
    
    if(chckNumber === posizioniannullate ){
    	window.location.replace("view.htm?contesto=${contesto}&status_msg=02");
    }else{    	
    	await eseguiLogAuditAccertamenti(chckNumber);    	
    	window.location.replace("view.htm?contesto=${contesto}");
    } 
	
}

async function eseguiLogAuditChiusure(totpdeb) {
	
	   try{
	    const resp = await fetch("../blacklist/ajaxLogAuditPosizioniDebitorieChiusure.htm?totpdeb="+totpdeb, {
	        method: "POST",
	        cache: "no-cache",
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    });

	    if (!resp.ok) {
	        throw new Error("Errore HTTP: " + resp.status);
	        console.log('Ho avuto l errore');
	    }
	    
	    }catch(e){
	    	console.error("Errore nella chiusura della posizione debitoria: " + iddettpd, e);
	    	return 0;
	    }
}

async function eseguiLogAuditAccertamenti(totpdeb) {
	
	   try{
	    const resp = await fetch("../blacklist/ajaxLogAuditPosizioniDebitorieAccertamenti.htm?totpdeb="+totpdeb, {
	        method: "POST",
	        cache: "no-cache",
	        headers: {
	            'Content-Type': 'application/json'
	        }
	    });

	    if (!resp.ok) {
	        throw new Error("Errore HTTP: " + resp.status);
	        console.log('Ho avuto l errore');
	    }
	    
	    }catch(e){
	    	console.error("Errore nella chiusura della posizione debitoria: " + iddettpd, e);
	    	return 0;
	    }
}

function selectDeselectAllCheckBox() {
	  const chckbx = document.getElementById("selectallchck_id");
	  const allchckbx = document.querySelectorAll(".chckbxpd");

	  allchckbx.forEach(el => {
	  el.checked = chckbx.checked;
	  
	    if(document.querySelector('.input_checkbox_class').checked && chckbx.checked){
		    const tr = el.closest("tr");
		    const importotottd = tr.querySelector('[data-importotottd]')?.dataset.importotottd;
			  if(!importotottd || Number(importotottd) < minimoTotale){
				  tr.querySelector('.chckbxpd').checked = false;
			  }	
	    }
	 });
}

function selectDeselectMainCheckBox(chckbx) {
	  const mainchckbx = document.getElementById("selectallchck_id");
      if(!chckbx.checked){
    	  mainchckbx.checked = false;
      }
}

function pulisciFiltri(){
	document.querySelectorAll('.input_text_class').forEach(el => {
	    el.value = '';
	  });
	
	document.querySelectorAll('.input_select_class').forEach(el => {
		  for (let option of el.options) {
		    option.selected = false;
		  }
	});
	
	document.querySelectorAll('.input_checkbox_class').forEach(el => {
		  if(el.checked){
			  el.click(); 
		  }		  
	});
}

function esportaBlChiuse(){
	const a = document.getElementById("esporta_bl_chiuse_id");
	a.click();
}

function getTotaliByCodiceFiscale() {
	  return [...document.querySelectorAll('#blackListDivId tbody tr')]
	    .reduce((acc, tr) => {
	      const tdCf = tr.querySelector('[data-cfpiva]');
	      const tdImp = tr.querySelector('[data-importobd]');
	      
	      if (!tdCf) return acc;
	      const cfpiva = tdCf.dataset.cfpiva?.trim();
	      if (!cfpiva) return acc;

	      let importo = 0;
	      if (tdImp?.dataset.importobd) {
	        importo = Number(tdImp.dataset.importobd);
	        if (isNaN(importo)) importo = 0;
	      }

	      acc[cfpiva] ??= 0;
	      acc[cfpiva] += importo;

	      return acc;
	    }, {});
}
	
function valorizzaTotaleByCodiceFiscale(){
	const mapTotali = getTotaliByCodiceFiscale();
	const righe = document.querySelectorAll('#blackListDivId tbody tr:not(.csiNorecordsTable)');
	righe.forEach(tr => {
		  const cfpiva = tr.querySelector('[data-cfpiva]')?.dataset.cfpiva;
		  if(cfpiva && mapTotali[cfpiva]){
			  const importotottd = tr.querySelector('[data-importotottd]');
			  tr.querySelector('[data-importotottd]').innerText = '\u20AC ' + totaleFormatter.format(mapTotali[cfpiva]);
			  importotottd.dataset.importotottd = mapTotali[cfpiva];
		  }
	});
}

function escludiMinimoTotaleByCodiceFiscale(isChecked){
	const righe = document.querySelectorAll('#blackListDivId tbody tr:not(.csiNorecordsTable)');
	let countmostrate = 0;
	righe.forEach(tr => {
		  if(!isChecked){
			  tr.style.display = '';
			  countmostrate = countmostrate + 1;
		  }else{
			  const importotottd = tr.querySelector('[data-importotottd]')?.dataset.importotottd;
			  if(!importotottd || Number(importotottd) < minimoTotale){
				  tr.style.display = 'none';
				  tr.querySelector('.chckbxpd').checked = false;
			  }else{
				  countmostrate = countmostrate + 1;
			  }			  
		  }
	});
	
	document.querySelectorAll('.totrecordestrattic').forEach(sp => {sp.innerText = countmostrate;})
	if(countmostrate < massimoResult){
		document.querySelector('#totalerecordgreen_id').style.display = 'block';
		document.querySelector('#totalerecordred_id').style.display = 'none';
	}else{
		document.querySelector('#totalerecordgreen_id').style.display = 'none';
		document.querySelector('#totalerecordred_id').style.display = 'block';
	}
}

function escludiMinimoTotaleByCodiceFiscaleClick(el){
	document.querySelector('#chkbox_escludi_id').value = el.checked;
	escludiMinimoTotaleByCodiceFiscale(el.checked);
}

window.addEventListener('load', function() {	
	document.querySelector('#filtri_fieldset').classList.remove('collassato');
	valorizzaTotaleByCodiceFiscale();
	if(isEscludiChecked){
		escludiMinimoTotaleByCodiceFiscale(isEscludiChecked);
	}
});
			
</script>
</body>
</html>