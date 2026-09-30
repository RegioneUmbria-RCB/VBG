<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.autorizzazioni.ricerca.list_pagatmenti.title" /></title>
<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-dettaglio-posizione-debitoria/vbg-dettaglio-posizione-debitoria.js?<%=vJS %>" defer></script>
<style type="text/css">
	.badge {
	  display: inline-block;
	  padding: .25em .4em;
	  font-size: 75%;
	  font-weight: 700;
	  line-height: 1;
	  text-align: center;
	  white-space: nowrap;
	  vertical-align: text-top;
	  border-radius: .25rem;
	  transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
	}
	
	.badge-danger {
	  color: #fff;
	  background-color: #dc3545;
	}
	.badge-success {
	  color: #fff;
	  background-color: #28a745;
	}
	.elemento-lista-pagamenti {
	  border:1px solid #a9a9a9;
	  border-left: 4px solid #16c793;
	  padding: var(--default-padding);
	  font-size: var(--font-size-small);
	  grid-template-columns: 1fr 1fr 28px;
	  margin-bottom: var(--default-padding);
	  line-height: 1.4rem;
	  display: grid;
	  
  	}
  	.elemento-lista-pagamenti > div {
	  margin-bottom: var(--default-padding);
	}
  	.elemento-lista-pagamenti > div:first-child {
	  grid-row-start: 1;
	  grid-column-start: 1;
	}
  	.elemento-lista-pagamenti > div:nth-child(2) {
	  grid-column-start: 1;
	  grid-column-end: 3;
	  grid-row-start: 2;
	}
	.elemento-lista-pagamenti > div:nth-child(3) {
	  grid-column-start: 2;
	  grid-column-end: 3;
	  grid-row-start: 2;
	}
	.elemento-lista-pagamenti > div:nth-child(4) {
	  grid-column-start: 1;
	  grid-column-end: 3;
	  grid-row-start: 3;
	}
	.elemento-lista-pagamenti > div:nth-child(5) {
	  grid-column-start: 2;
	  grid-column-end: 3;
	  grid-row-start: 3;
	}
	.elemento-lista-pagamenti > div:nth-child(6) {
	  grid-column-start: 1;
	  grid-column-end: 3;
	  grid-row-start: 4;
	}
	.elemento-lista-pagamenti > div:nth-child(7) {
	  grid-column-start: 2;
	  grid-column-end: 3;
	  grid-row-start: 4;
	}
	.elemento-lista-pagamenti > div:nth-child(8) {
	  grid-column-start: 1;
	  grid-column-end: 3;
	  grid-row-start: 5;
	}
	.elemento-lista-pagamenti > div:nth-child(9) {
	  grid-column-start: 2;
	  grid-column-end: 3;
	  grid-row-start: 5;
	}
  	.elemento-lista-pagamenti .titolo {
	  border-bottom: 1px solid #c0c0c0;
	  padding-bottom: var(--half-padding);
	  grid-column-end: 4 !important;
	}
	.elemento-lista-pagamenti.da-effettuare {
	  border-left-color: #721c24;
	}
	
    .elemento-lista-pagamenti label {
	  display: block;
	  line-height: inherit;
	  text-transform: none;
	  font-weight: 700;
	}
	legend{
		font-weight: 700;
	}
	.elemento-lista-pagamenti.chiuso .nascondi-se-aperto {
	  display: block;
	}
	.elemento-lista-pagamenti.chiuso .nascondi-se-chiuso {
	  display: none;
	}
	
	
	
	.filtri-stato {
	  display: flex;
	  justify-content: space-evenly;
	  margin-bottom: var(--default-padding);
	  margin-top: var(--default-padding);
	  font-weight: 700;
	}
	.filtri-ricerca-avanzata {
	  position: relative;
	}
	.filtri-avanzati-presenze {
	  margin-bottom: var(--default-padding);
	}
	.filtri-ricerca-avanzata .filtri-avanzati-presenze .barra-titolo .azioni {
	  cursor: pointer;
	  white-space: nowrap;
	}
	.filtri-avanzati-presenze.chiuso .barra-titolo {
	  display: flex;
	  justify-content: space-between;
	  align-items: baseline;
	  font-weight: 700;
	}
	.filtri-ricerca-avanzata .filtri-avanzati-presenze .form-filtri {
	  display: flex;
	  justify-content: space-between;
	  align-items: baseline;
	  flex-wrap: wrap;
	  margin-top: 0;
	  top: -6px;
	  right: 0;
	  width: 100%;
	}

	.filtri-ricerca-avanzata .filtri-avanzati-presenze .form-filtri .vbg-form {
	  width: 100%;
	  padding: var(--default-padding);
	}
	.filtri-avanzati-presenze .form-group {
	  margin-bottom: var(--default-padding) !important;
	}
	.row {
	  display: flex;
	  flex-wrap: wrap;
	}
	.col-6 {
	  flex: 0 0 50%;
	  max-width: 50%;
	}
	.input-group-text {
	  font-weight: 700;
	}
	.filtri-impostati {
	  margin-top: var(--half-padding);
	  display: block;
	}
	ul {
	  list-style: none;
	  padding-left: 0px;
	}
	.filtri-impostati li {
	  display: inline-block;
	  background-color: #dff0fd;
	  margin-right: var(--half-padding);
	  margin-bottom: var(--half-padding);
	  padding: var(--half-padding);
	  border-radius: 2px;
	}
	.importo{
		font-weight: 700;
	}

</style>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.autorizzazioni.ricerca.list_pagatmenti.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
			<jsp:param name="path" value="../autorizzazioni/ricercaPagamentiAutorizzazioniConcessione.htm" />
		</jsp:include>
	<div id="subcontent">
		<vbg-fetch-ref id='data-by-id' method='get' response-format='json' request-format='form'
           url='../dettposizionedebitoria/ajaxDettaglioPosizione.htm'>
        </vbg-fetch-ref>
        	<c:set var="effettuati" value="0" scope="page"/>
			<c:set var="daEffettuare" value="0" scope="page"/>
			<c:forEach items="${pagamenti}" var="entry1">
					<c:forEach items="${entry1.giorno}" var="entry2">
						<c:forEach items="${entry2.pagamenti}" var="entry3">
							<c:choose>
								<c:when test="${entry3.effettuato }">
									<c:set var="effettuati" value="${effettuati +1 }" scope="page"/>
								
								</c:when>
								<c:otherwise>
									<c:set var="daEffettuare" value="${daEffettuare +1 }" scope="page"/>
								
								</c:otherwise>
							</c:choose>
							
						</c:forEach>
					</c:forEach>
			</c:forEach>
		<div class="filtri-stato" id="stati">
			<div> Visualizza: </div>
			<div class="stato-tutti">
				<input type="radio" name="statoVis" id="statoTutti" class="inverse"> &nbsp; <label for="statoTutti">Tutti</label>
			</div>
			<div class="stato-effettuato">
				<input type="radio" name="statoVis" id="statoEffettuato" class="inverse"> &nbsp; <label for="statoEffettuato">Effettuati</label>
			</div>
			<div class="stato-da-effettuare">
				<input type="radio" name="statoVis" id="statoDaEffettuare" class="inverse"> &nbsp; <label for="statoDaEffettuare">Da effettuare</label>
			</div>
		</div>
		
		<div class="filtri-ricerca-avanzata">
			<div id="filtriAvanzati" class="filtri-avanzati-presenze chiuso">
				<div class="barra-titolo">
					<div style="font-style: italic;" id="titolo-filtri"> Nessun filtro impostato </div>					
					<div class="azioni">
						<a id="link-ricerca-avanzata" href="javascript:void(0);"> Ricerca avanzata <i class="fa fa-bars"></i></a>
					</div>
				</div>
				<div class="filtri-impostati" ><ul id="filtri-impostati"></ul></div>
				<vbg-modal id="modal-ricerca-avanzata">
					<div slot='body'>
						<h1>
			               <div> Specifica almeno un filtro ... </div>
			             </h1>
			             <div class="form-filtri">		
							<div class="vbg-form">
								<form novalidate="" class="ng-untouched ng-pristine ng-invalid">
									<div class="form-group">
										<select name="mercatoDesc" id="mercatoDesc" formcontrolname="mercato" class="form-control ng-untouched ng-pristine ng-invalid">
											<option></option>
											<c:forEach items="${pagamenti}" var="entry1">											
												<option>${entry1.descrizione_mercato }</option>
											</c:forEach>	
										</select>
									</div>
									<div class="row date-range form-group">
										<div class="col-6"><div class="input-group">
											<div class="input-group-prepend"><span id="basic-addon1" class="input-group-text">Dal</span></div>
											<input type="date" id="dataInizio" name="dataInizio" formcontrolname="dataInizio" class="form-control ng-untouched ng-pristine ng-invalid"></div>
										</div>
										<div class="col-6"><div class="input-group"><div class="input-group-prepend"><span id="basic-addon1" class="input-group-text">Al</span></div>
										<input type="date" id="dataFine" name="dataFine" formcontrolname="dataFine" class="form-control ng-untouched ng-pristine ng-invalid"></div>
										</div>
									</div>
									
								</form>
							</div>
						</div>
						 	
			         </div>
					
					<div slot='footer'>
						<button type="submit"  id="btn-ricerca-avanzata" onclick="ricercaAvanzata()" class="btn btn-primary"> <fmt:message key="button.search"></fmt:message> </button>
						<button class="btn btn-primary"  onclick="svuotaFiltri()"> Svuota filtri </button>          
					</div>
					
		</vbg-modal>
		</div>		
	</div>
	</div>
		<div class="vbg-form lista-pagamenti" id="da-eff">
			<fieldset>
				<legend><fmt:message key="label.autorizzazioni.ricerca.list_pagatmenti.da_effetuare"/>&nbsp;<span class="badge badge-danger">${daEffettuare}</span></legend>
				<c:forEach items="${pagamenti}" var="entry1">
					<c:forEach items="${entry1.giorno}" var="entry2">
						<c:forEach items="${entry2.pagamenti}" var="entry3">
							<c:if test="${not entry3.effettuato and not fn:containsIgnoreCase(entry3.stato_pagamento,'annulato')}">
								    <div class="elemento-lista-pagamenti da-effettuare chiuso">								      
								      <div class="titolo">${ entry1.descrizione_mercato}</div>
								      <div>
								        <label for="data-mercato">Data</label>
								        <span name="data-mercato" class="data-mercato"><fmt:formatDate value="${entry3.data_presenza }" pattern="dd/MM/yyyy" /> - ${entry2.descrizione_giorno }</span>
								      </div>
								      <div>
								        <label>Importo da saldare</label>
								        <vbg-dettaglio-posizione-debitoria
                                             id-posizione="${entry3.id_pagamento}"
                                             mostra-testo="true"
                                             fetch-ref='data-by-id' >
                                         </vbg-dettaglio-posizione-debitoria>                      
								        <span class="importo">€ ${entry3.importo }</span>
								      </div>
								      <div>
								        <label>Nr autorizzazione</label><span>${entry3.autorizzazione.autoriznumero }</span>
								      </div>
								       <div>
								        <label>Comune e data rilascio</label><span> ${entry3.autorizzazione.autorizcomune } - <fmt:formatDate value="${entry3.autorizzazione.autorizdata }" pattern="dd/MM/yyyy" /></span>
								      </div>
								       <div>
								        <label>Occupante</label><span>${entry3.autorizzazione.nominativo } & CF: ${entry3.autorizzazione.codicefiscale }</span>
								      </div>
								       <div>
								        <label>Titolare</label><span>${entry3.autorizzazione.titNominativo } & CF: ${entry3.autorizzazione.titCodicefiscale }</span>
								      </div>
								     
								      <div>
								        <label>Nr Posteggio</label>${entry3.posteggio }<span></span>
								      </div>
								      <div>
								        <label>Superficie</label><span>${entry3.superficie } mq</span>
								      </div>
								      
								   </div>
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:forEach>
				
			</fieldset>
		</div>
		<div class="vbg-form lista-pagamenti" id="eff">
			
			<fieldset>
				<legend><fmt:message key="label.autorizzazioni.ricerca.list_pagatmenti.effetuati"/>&nbsp;<span class="badge badge-success">${effettuati}</span></legend>
				<c:forEach items="${pagamenti}" var="entry1">
					<c:forEach items="${entry1.giorno}" var="entry2">
						<c:forEach items="${entry2.pagamenti}" var="entry3">
							<c:if test="${entry3.effettuato and not fn:containsIgnoreCase(entry3.stato_pagamento,'annulato')}">
								    <div class="elemento-lista-pagamenti effettuato chiuso">								      
								      <div class="titolo">${ entry1.descrizione_mercato}</div>
								      <div>
								        <label for="data-mercato">Data</label>
								        <span name="data-mercato" class="data-mercato"><fmt:formatDate value="${entry3.data_presenza }" pattern="dd/MM/yyyy" /> - ${entry2.descrizione_giorno }</span>
								      </div>
								      <div>
								        <label>Importo saldato</label>
								        <vbg-dettaglio-posizione-debitoria
                                             id-posizione="${entry3.id_pagamento}"
                                             mostra-testo="true"
                                             fetch-ref='data-by-id' >
                                         </vbg-dettaglio-posizione-debitoria>                                         
								        <span class="importo">€ ${entry3.importo }</span>
								      </div>
								      <div>
								        <label>Nr autorizzazione</label><span>${entry3.autorizzazione.autoriznumero }</span>
								      </div>
								      <div>
								        <label>Comune e data rilascio</label><span> ${entry3.autorizzazione.autorizcomune } - <fmt:formatDate value="${entry3.autorizzazione.autorizdata }" pattern="dd/MM/yyyy" /></span>
								      </div>
								      <div>
								        <label>Occupante</label><span>${entry3.autorizzazione.nominativo } & cf: ${entry3.autorizzazione.codicefiscale } </span>
								      </div>
								       <div>
								        <label>Titolare</label><span>${entry3.autorizzazione.titNominativo } & CF: ${entry3.autorizzazione.titCodicefiscale }</span>
								      </div>
								      
								    
								      <div>
								        <label>Superficie</label><span>${entry3.superficie } mq</span>
								      </div>
								        <div>
								        <label>Nr Posteggio</label>${entry3.posteggio }<span></span>
								      </div>							      
								    </div>
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:forEach>
			</fieldset>
			<div id="functions">
			<ul>
				<li><a href="javascript:doHref('../autorizzazioni/ricercaPagamentiAutorizzazioniConcessione.htm','');"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
		</div>
		
	</div>
	
<script type="text/javascript">
	
	
	const stati = document.getElementById('stati');
	stati.querySelectorAll('input').forEach(function(el) {
		el.addEventListener('click',(e)=>{
			const inpName = e.target.getAttribute("id");
			switch (inpName) {
			case "statoTutti":
				document.querySelectorAll('.lista-pagamenti').forEach((l)=>{
					
					l.style.display="block";
				});
				
				break;
			case "statoEffettuato":
				document.querySelectorAll('.lista-pagamenti').forEach((l)=>{
					const id = l.getAttribute('id');
					if (id === "eff") {
						l.style.display="block";
					}else{
						l.style.display="none";
					}
					
				});
			break;
			case "statoDaEffettuare":
				document.querySelectorAll('.lista-pagamenti').forEach((l)=>{
									
					const id = l.getAttribute('id');
					if (id === "da-eff") {
						l.style.display="block";
					}else{
						l.style.display="none";
					}
				});
			
			break;

			default:
				break;
			}
		});
		
	});
	document.getElementById('link-ricerca-avanzata').addEventListener('click',(e)=>{
		document.getElementById('modal-ricerca-avanzata').open();
	});
		
	function ricercaAvanzata(){
		window.vbg.mostraModalCaricamento();
		const modalRicercaAvanzata = document.getElementsByTagName('vbg-modal').item(0);
		let mercatoGiorno = modalRicercaAvanzata.querySelector('#mercatoDesc').value;
		let dataDal = modalRicercaAvanzata.querySelector('#dataInizio').value;
		let dataAl = modalRicercaAvanzata.querySelector('#dataFine').value;
		let d1;
		let d2;
		let cnt1=0;
		let cnt2=0;
		
		if (dataDal !== '' && dataAl === '') {		
			 
			 d1 = new Date(dataDal).getTime();
			 
		}else if (dataDal !== '' &&  dataAl !== '') {
			
			 d1 = new Date(dataDal).getTime();
			 d2 = new Date(dataAl).getTime();
		}
		else if (dataDal === '' &&  dataAl !== '') {
			
			 
			 d2 = new Date(dataAl).getTime();
		}
		
		const filtriImpostati = document.querySelector('#filtri-impostati');
		
		
		
		document.querySelectorAll('.elemento-lista-pagamenti').forEach((el)=>{
			let titolo  = el.querySelector('.titolo').innerHTML;
			if (mercatoGiorno.toUpperCase() !== titolo.toUpperCase() && mercatoGiorno.toUpperCase() !=='' ) {
				el.style.display='none';
			}
			else if (mercatoGiorno.toUpperCase() === titolo.toUpperCase() && dataDal !== '' && dataAl !== '') {
				filtriImpostati.innerHTML ='';
				let html = `<li> \${mercatoGiorno} <i class="fa fa-times" id="filtro-mercato-giorno"></i></li>`;
				html += `<li>Dal \${new Date(dataDal).toLocaleString('it-IT')}  <i class="fa fa-times" id="filtro-data-dal"></i></li>`;
				html += `<li>Al \${new Date(dataAl).toLocaleString('it-IT')}  <i class="fa fa-times" id="filtro-data-al"></i></li>`;
				filtriImpostati.insertAdjacentHTML("beforeend",html);
				 
				
				 let elData = el.getElementsByClassName('data-mercato').item(0).innerHTML.split('-')[0].trim();
				 let d = elData.split('/');
				 let d3 = new Date(d[2]+'-'+d[1]+'-'+d[0]).getTime();
				 if (titolo === mercatoGiorno && d3 >= d1 && d3 <= d2 ) {
					 el.style.display='grid';
					 if (el.classList.contains("da-effettuare")) {
						cnt1++;
					  } else {
						 cnt2++;
					  }
				}else{
					el.style.display='none';
				}
				 document.getElementById('titolo-filtri').innerHTML = 'Filtri impostati';
				
				
			}
			else if (mercatoGiorno.toUpperCase() === '' && dataDal !== '' && dataAl !== '') {
				filtriImpostati.innerHTML ='';
				
				let html = `<li>Dal \${new Date(dataDal).toLocaleString('it-IT')}  <i class="fa fa-times" id="filtro-data-dal"></i></li>`;
				html += `<li>Al \${new Date(dataAl).toLocaleString('it-IT')}  <i class="fa fa-times" id="filtro-data-al"></i></li>`;
				filtriImpostati.insertAdjacentHTML("beforeend",html);
				 
				
				 let elData = el.getElementsByClassName('data-mercato').item(0).innerHTML.split('-')[0].trim();
				 let d = elData.split('/');
				 let d3 = new Date(d[2]+'-'+d[1]+'-'+d[0]).getTime();
				 if ( d3 >= d1 && d3 <= d2 ) {
					 el.style.display='grid';
					 if (el.classList.contains("da-effettuare")) {
						cnt1++;
					  } else {
						 cnt2++;
					  }
				}else{
					el.style.display='none';
				}
				 document.getElementById('titolo-filtri').innerHTML = 'Filtri impostati';
				
			}
			else if (mercatoGiorno.toUpperCase() === titolo.toUpperCase() &&  dataDal !== '' &&  dataAl === '') {
				filtriImpostati.innerHTML ='';
				let html = `<li> \${mercatoGiorno} <i class="fa fa-times"></i></li>`;
				html += `<li>Dal \${new Date(dataDal).toLocaleString('it-IT')}  <i class="fa fa-times" id="filtro-data-dal"></i></li>`;
				filtriImpostati.insertAdjacentHTML("beforeend",html);
				 
				console.log(mercatoGiorno);
					 let elData = el.getElementsByClassName('data-mercato').item(0).innerHTML.split('-')[0].trim();
					 let d = elData.split('/');
					 let d3 = new Date(d[2]+'-'+d[1]+'-'+d[0]).getTime();
					 if ( d3 >= d1 ) {
						 el.style.display='grid';
						 if (el.classList.contains("da-effettuare")) {
							 cnt1++;
							  } else {
								cnt2++;
							  }
					}else{
						el.style.display='none';
					}
					 document.getElementById('titolo-filtri').innerHTML = 'Filtri impostati';			
			}else if (mercatoGiorno.toUpperCase() === '' &&  dataDal !== '' &&  dataAl === '') {
				filtriImpostati.innerHTML ='';
				
				let html = `<li>Dal \${new Date(dataDal).toLocaleString('it-IT')}  <i class="fa fa-times" id="filtro-data-dal"></i></li>`;
				filtriImpostati.insertAdjacentHTML("beforeend",html);
				 
					 let elData = el.getElementsByClassName('data-mercato').item(0).innerHTML.split('-')[0].trim();
					 let d = elData.split('/');
					 let d3 = new Date(d[2]+'-'+d[1]+'-'+d[0]).getTime();
					 if ( d3 >= d1 ) {
						 el.style.display='grid';
						 if (el.classList.contains("da-effettuare")) {
							 cnt1++;
							  } else {
								cnt2++;
							  }
					}else{
						el.style.display='none';
					}
					document.getElementById('titolo-filtri').innerHTML = 'Filtri impostati';

			}
			else if (mercatoGiorno.toUpperCase() === titolo.toUpperCase() &&  dataDal === '' &&  dataAl !== '') {
				filtriImpostati.innerHTML ='';
				let html = `<li> \${mercatoGiorno} <i class="fa fa-times" id="filtro-mercato-giorno"></i></li>`;
				html += `<li>Al \${new Date(dataAl).toLocaleString('it-IT')}  <i class="fa fa-times" id="filtro-data-al"></i></li>`;
				filtriImpostati.insertAdjacentHTML("beforeend",html);
				 
					 let elData = el.getElementsByClassName('data-mercato').item(0).innerHTML.split('-')[0].trim();	
					 let d = elData.split('/');				
					 let d3 = new Date(d[2]+'-'+d[1]+'-'+d[0]).getTime();
					 if ( d3 <= d2 ) {
						 el.style.display='grid';
						 if (el.classList.contains("da-effettuare")) {
							 cnt1++;
							  } else {
								cnt2++;
							  }
					}else{
						el.style.display='none';
					}
					 document.getElementById('titolo-filtri').innerHTML = 'Filtri impostati';
				
				
			}	
			else if (mercatoGiorno.toUpperCase() === '' &&  dataDal === '' &&  dataAl !== '') {
				filtriImpostati.innerHTML ='';
				
				let html = `<li>Al \${new Date(dataAl).toLocaleString('it-IT')}  <i class="fa fa-times" id="filtro-data-al"></i></li>`;
				filtriImpostati.insertAdjacentHTML("beforeend",html);
				 
					 let elData = el.getElementsByClassName('data-mercato').item(0).innerHTML.split('-')[0].trim();
					 let d = elData.split('/');
					 let d3 = new Date(d[2]+'-'+d[1]+'-'+d[0]).getTime();
					 if ( d3 <= d2 ) {
						 el.style.display='grid';
						 if (el.classList.contains("da-effettuare")) {
							 cnt1++;
							  } else {
								cnt2++;
							  }
					}else{
						el.style.display='none';
					}
					 document.getElementById('titolo-filtri').innerHTML = 'Filtri impostati';
				
				
			}
			else if (mercatoGiorno.toUpperCase() === titolo.toUpperCase() &&  dataDal === '' &&  dataAl === '') {
				filtriImpostati.innerHTML ='';
				let html = `<li> \${mercatoGiorno} <i class="fa fa-times" id="filtro-mercato-giorno"></i></li>`;
				filtriImpostati.insertAdjacentHTML("beforeend",html);
	
					 
						 el.style.display='grid';
						 if (el.classList.contains("da-effettuare")) {
							 cnt1++;
						 } else {
								cnt2++;
						}
						document.getElementById('titolo-filtri').innerHTML = 'Filtri impostati';
					
				
			}else if (mercatoGiorno.toUpperCase() === '' &&  dataDal === '' &&  dataAl === '') {
				filtriImpostati.innerHTML ='';
				
						 el.style.display='grid';
						 if (el.classList.contains("da-effettuare")) {
							 cnt1++;
							  } else {
								cnt2++;
							  }
						 document.getElementById('titolo-filtri').innerHTML = 'Nessun filtro impostato';
				
			}
			});
		document.getElementById('modal-ricerca-avanzata').close();
		document.getElementById('da-eff').getElementsByClassName('badge').item(0).innerHTML=cnt1;
		document.getElementById('eff').getElementsByClassName('badge').item(0).innerHTML=cnt2;
		
		document.getElementById('filtri-impostati').querySelectorAll('li').forEach((l)=>{
			
			l.getElementsByTagName('i').item(0).addEventListener('click',(e)=>{
				let idFiltro = e.target.getAttribute('id');
				if (idFiltro === 'filtro-mercato-giorno') {
					document.getElementById('mercatoDesc').value='';
					//document.getElementById('mercatoDesc').getElementsByTagName('option').item(0).selected='selected';
					ricercaAvanzata();
				}else if (idFiltro === 'filtro-data-dal') {
					document.getElementById('dataInizio').value='';
					ricercaAvanzata();
				} 
				else if(idFiltro === 'filtro-data-al'){
					document.getElementById('dataFine').value='';
					ricercaAvanzata();
				}
			});
		});
		window.vbg.nascondiModalCaricamento();
	}
	function svuotaFiltri(){
		let cnt1 = 0;
		let cnt2 = 0;
		document.querySelectorAll('.elemento-lista-pagamenti').forEach((el)=>{
			el.style.display='grid';
			 if (el.classList.contains("da-effettuare")) {
				 cnt1++;
				  } else {
					cnt2++;
				  }
		});
		const modalRicercaAvanzata = document.getElementsByTagName('vbg-modal').item(0);
		let mercatoGiorno = modalRicercaAvanzata.querySelector('#mercatoDesc').value = '';
		let dataDal = modalRicercaAvanzata.querySelector('#dataInizio').value = '';
		let dataAl = modalRicercaAvanzata.querySelector('#dataFine').value = '';
		document.querySelector('#filtri-impostati').innerHTML ='';
		document.getElementById('modal-ricerca-avanzata').close();
		document.getElementById('da-eff').getElementsByClassName('badge').item(0).innerHTML=cnt1;
		document.getElementById('eff').getElementsByClassName('badge').item(0).innerHTML=cnt2;
		document.getElementById('titolo-filtri').innerHTML = 'Nessun filtro';
		
	}

</script>
</body>
</html>