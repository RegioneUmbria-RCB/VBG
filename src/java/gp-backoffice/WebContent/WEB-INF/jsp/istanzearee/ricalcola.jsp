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
		<fmt:message key="label.ricalcola_aree" />
	</title>
	<style>
       .ielimnaarea{
       display: none;
       cursor: pointer;
      }
      
      .areeSelezionateLi:hover .ielimnaarea{
       display: initial;
      }
    </style>
</head>
<body>
<span class="titoloPagina">
	<fmt:message key="label.ricalcola_aree" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
    		<jsp:param name="path" value="../istanzearee/createRicalcola" />
</jsp:include>
<div id="subcontent">
	<spring-form:form commandName="istanzearee" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="istanzearee" />
    </jsp:include>
	<div class="vbg-form">
	   <fieldset>
	   <legend id="legend_ricostruzione_aree" ><fmt:message key="label.ricostruzione_delle_aree"/></legend>
	   
	   <div>
	       <span><fmt:message key="label.ricostruzione_delle_aree_spiegazione" /></span>
	       <br></br>
	   </div>
	   
	   <div class="form-group">
	   <label><fmt:message key="label.data_presentazione" /></label>
	   
	   <span><fmt:message key="label.data.inizio" /></span>
	   <input type="text" id="dataInizio_id" name="dallaData" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
	   <init:calendar imagePath="/images/cal.gif" idImage="caldatainizio" idInput="dataInizio_id" textKey="label.calendar"/>
	   
	   <span><fmt:message key="label.data.fine" /></span>
	   <input type="text" id="dataFine_id" name="allaData" size="10" maxlength="10" onblur="isValidDate(this,true);"/>
	   <init:calendar imagePath="/images/cal.gif" idImage="caldatafine" idInput="dataFine_id" textKey="label.calendar"/>
	   </div>
	   
	   <div class="form-group">
	   <label><fmt:message key="label.aree.ricalcolo" /></label>
	   <jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
							<jsp:param name="idElemento" value="area_id" />				
							<jsp:param name="autocompleterAjax" value="findAree.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_aree" />
	   </jsp:include>
	   <ul id="areeselezionate" style="list-style-type: none;padding-left: 0;">
	   </ul>
	   <input id="areeric_in_id" type="hidden" name="areeric" value="">
	   </div>
	</div>
</spring-form:form>
</div>
<div id="functions">
	<ul>
		<li id="ricalcolabtn" class="${disabledriclink}"><a href="javascript:doSubmit('ricalcola.htm','',document.inviodati)" ><fmt:message key="input.button.ricalcolo" /></a></li>
		<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
	</ul>
</div>
<br></br>
<br></br>
<br></br>
<div id="ricalcolaAreeDivId">

<table>
		<tr class="titoloSezione">
			<td colspan="4"><fmt:message key="label.elenco.elaborazioni" /></td>
		</tr>
</table>


<table class="vbg-table">
									 	<thead>
											<tr>
												<th>STATO</th>
												<th>RIMANENTI</th>
												<th>CALCOLATI</th>
												<th>TOTALI</th>
												<th>AGGIORNAMENTO</th>
												<th>DETTAGLIO</th>
											</tr>	 	
									 	</thead>
					<tbody>
					<c:forEach items="${ricalcoloAreeList}" var="ricalcoloaree_var" varStatus="i">
					 <tr>
					  <td style="${ricalcoloaree_var.boldStyle}">${ricalcoloaree_var.stato}</td>
					  <td>${ricalcoloaree_var.dafare}</td>
					  <td>${ricalcoloaree_var.fatti}</td>
					  <td>${ricalcoloaree_var.totali}</td>
					  <td><div id="${ricalcoloaree_var.id}" style="float:left;" class="${ricalcoloaree_var.classDisabledButton}" onclick="getDataFromRicalcoloAreeId('${ricalcoloaree_var.id}')"><i style="font-size: 20px;" class="${ricalcoloaree_var.fontAwesomeButton}"></i></div></td>
					  <td><a class="dettaglioColumn" href="javascript:historySet('${_urlback}','../istanzearee/goToDettaglioElaborazione.htm?idRicalcoloAree=${ricalcoloaree_var.id}','')" title="Dettagli elaborazione">
								<label><fmt:message key="label.edit.record.image" /></label>
								</a></td>
					 </tr>
					</c:forEach>
					
					
					<c:if test="${empty ricalcoloAreeList}">
					 <tr>
					  <td colspan=6>Nessun record trovato</td>
					 </tr>
					</c:if>
					</tbody>
</table>
</div>
<script type="text/javascript">


var dialogWorking = null; 
jQuery(document).ready(function(){
	dialogWorking = new dijit.Dialog({
       title: "Operazione in corso..." ,
       style: "overflow:auto; width: 250px;height: 70px;",
       content: "<img src='../images/spinner.gif'/>"
    });				
});

const div = document.getElementById('area_id_id_choices');

div.addEventListener('click', function(event) {
  if (event.target.tagName.toLowerCase() === 'li') {
    console.log("Hai cliccato su:", event.target.textContent);
    
    if(event.target.id){
    	areasMap.set(event.target.id,event.target.textContent);
    	console.log(areasMap);
        refreshAreeSelezionate();
    }
  }
});

let areasMap = new Map();

function getDataFromRicalcoloAreeId(ricalcoloAreeId){
	
	dialogWorking.show();
	
	fetch('getDataProcessFromRicalcoloAreeId.htm?ricalcoloAreeId='+ricalcoloAreeId ,{
        method: "POST",
        cache: "no-cache"
    })
	  .then(data => {
	    return data.json();
	  }).then(res => aggiornaElaborazioneTable(ricalcoloAreeId, res))
	  .catch(error => {
	    console.error('Errore:', error);
	    mostraErrore(ricalcoloAreeId);
	  });
	
}

function aggiornaElaborazioneTable(ricalcoloAreeId,jsonin){
	
	console.log(jsonin);
	
	let el = document.getElementById(ricalcoloAreeId);
	
	let spans = el.parentElement.getElementsByTagName('span');
	if(spans.length > 0){
		el.parentElement.removeChild(spans[0]);
	}
	
	let riga = el.closest('tr');
	let tds = riga.getElementsByTagName('td');
	
	tds[0].textContent = jsonin.status;
	tds[1].textContent = jsonin.daFaree + '';
	tds[2].textContent = jsonin.fatte + '';
	tds[3].textContent = jsonin.totali + '';
	
	console.log(jsonin.messaggio);
	if(jsonin.messaggio.includes('Elaborazione in corso')){
		
		if(jsonin.totali === 0){
			tds[1].textContent = '';
			tds[2].textContent = '';
			tds[3].textContent = '';
		}

	}else if (jsonin.messaggio === 'Elaborazione completata'){
		let ielements = el.getElementsByTagName('i');
		ielements[0].className='fas fa-check-circle';
		el.className='checkatoButton';
		
		document.getElementById('ricalcolabtn').className='';
	}else if (jsonin.messaggio === 'In errore'){
		let ielements = el.getElementsByTagName('i');
		ielements[0].className='fas fa-times-circle';
		el.className='checkatoButton';
		
		document.getElementById('ricalcolabtn').className='';
	}
	
	if(jsonin.messaggio.includes('Elaborazione in corso')){
		tds[0].setAttribute("style", "font-weight: bold;");
	}else{
		tds[0].setAttribute("style", "");
	}
	
	dialogWorking.hide();
}

function mostraErrore(ricalcoloAreeId){
	let el = document.getElementById(ricalcoloAreeId);
	let td = el.parentElement;
	
	let spans = td.getElementsByTagName('span');
	
	if(spans.length < 1){
		let span = document.createElement("span");
		span.textContent = 'Si è verificato un errore, riprovare';
		span.style.color = 'red';
		span.style.marginLeft = '10px';
		span.style.fontSize = '15px';
		td.appendChild(span);
	}
	dialogWorking.hide();
}



  function refreshAreeSelezionate() {
	  
	    const inHidElement = document.getElementById("areeric_in_id");
	    inHidElement.value = "[" + [...areasMap.keys()] + "]";
	    console.log([...areasMap.keys()]);
	  
	    const ulElement = document.getElementById("areeselezionate");
	    ulElement.innerHTML = '';

	    if(areasMap.size === 0){
	    	
	    }else{
	    	
	    	const litit = document.createElement('li');
	    	litit.textContent = 'Aree selezionate:';
	    	litit.style.fontWeight = 'bold';
	    	ulElement.appendChild(litit);
	    	
	    	for (let [key, value] of areasMap.entries()) {
	  	      const li = document.createElement('li');
	  	      li.innerHTML = '- ' + value + ' <i class="ielimnaarea fas fa-times-circle" onclick="eliminaAreaSelezionata(this)"></i>';
	  	      li.id = 'liarea' + key;
	  	      li.className = "areeSelezionateLi";
	  	      ulElement.appendChild(li);
	  	    }
	    }
	    
	    
 }
  
 
 function eliminaAreaSelezionata(el){
	 let id = el.parentElement.id;
	 id = id.replace('liarea','');
	 areasMap.delete(id);
	 refreshAreeSelezionate();
 } 
				
</script>
</body>
</html>