<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message
		key="label.elaborazioni_massive_schede_istanza" /></title>
	<style type="text/css">
		.alert {
		  padding: 15px;
		  margin-bottom: 20px;
		  border: 1px solid transparent;
		  border-radius: 4px;
		  width:20%;
		  font-weight: bold;
		}
		.alert-warning {
		  background-color: #fcf8e3;
		  border-color: #faebcc;
		  color: #8a6d3b;
		}
	
	</style>
</head>
<body>
	<span class="titoloPagina"> <fmt:message
			key="label.elaborazioni_massive_schede_istanza" />

	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form" />
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../elaborazionimassive/view" />
	</jsp:include>
	<div id="subcontent">

		<c:if test="${disabilitaElaborazione eq true}">
			<div class="alert alert-warning" role="alert">
			  Elaborazione in corso
			</div>
		</c:if>
		<div class="vbg-form">
			<fieldset class="collassabile" data-collassato='false'>
				<legend>
					<fmt:message key="label.dettaglio" />
				</legend>
				<div class="form-group">
					<label title="<fmt:message key="label.descrizione" />"><fmt:message
							key="label.descrizione" /></label> <span
						class="readonly-form-control" id="testata.descrizione"></span>
				</div>
				<div class="form-group">
					<label title="<fmt:message key="label.data_inizio" />"><fmt:message
							key="label.data_inizio" /></label> <span
						class="readonly-form-control" id="testata.data_inizio"></span>
				</div>
				<div class="form-group">
					<label title="<fmt:message key="label.data_fine" />"><fmt:message
							key="label.data_fine" /></label> <span
						class="readonly-form-control" id="testata.data_fine"></span>
				</div>
			</fieldset>
			<fieldset class="collassabile" data-collassato='false'>
				<legend>
					<fmt:message key="label.filtri" />
				</legend>
				<div class="form-group">
					<label title="<fmt:message key="label.interventi_selezionati" />"><fmt:message
							key="label.interventi_selezionati" /></label> <span
						class="readonly-form-control" id="filtri.interventi"></span>
				</div>
				<div class="form-group">
					<label title="<fmt:message key="label.registri_selezionati" />"><fmt:message
							key="label.registri_selezionati" /></label> <span
						class="readonly-form-control" id="filtri.registri"></span>
				</div>
				<div class="form-group">
					<label title="<fmt:message key="label.stato_istanza" />"><fmt:message
							key="label.stato_istanza" /></label> <span class="readonly-form-control"
						id="filtri.stato_istanza"></span>
				</div>
			</fieldset>
			<fieldset class="collassabile" data-collassato='false'>
				<legend>
					<fmt:message key="label.lista_schede_dinamiche_da_rielaborare" />
				</legend>
				<div class="form-group">
					<label title="<fmt:message key="label.schede_selezionate" />"><fmt:message
							key="label.schede_selezionate" /></label> <span
						class="readonly-form-control" id="schede.lista"></span>
				</div>
			</fieldset>
			<fieldset class="collassabile" data-collassato='false'>
				<legend>
					<fmt:message key="label.lista_istanze" />
				</legend>
				<div class="form-group">
					<div class="input-icons">
						<i class="fa fa-search icon"></i>
						<input id="input_ricerca" type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()"/>
					</div>
					<div class="input-help"><fmt:message key="label.messaggio_ricerca_tabella" /></div>		
				</div>
				<div class="table-fix-head">
					<table id="istanze" class="vbg-table searchable sortable">
						<thead>
							<tr>							
								<th><fmt:message key="label.istanza"/></th>
								<th><fmt:message key="label.richiedente"/></th>
								<th><fmt:message key="label.intervento"/></th>
								<th><fmt:message key="label.stato_elaborazione"/></th>
								<th><fmt:message key="label.logs"/></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</fieldset>
		</div>
	</div>
	<div>

			<%--
			<li><a
				href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message
						key="button.delete" /></a></li>
						
			--%>						

			<c:if test="${not disabilitaElaborazione }">
				<a href="javascript:void(0)" id="bottone-elabora" class="btn btn-primary"><fmt:message
							key="button.elabora" /></a>
			</c:if>
			<a href="javascript:doHref('eliminaElaborazioneMassiveRiga.htm?idElaborazione='+${param.idElaborazione},'')" class="btn btn-primary"><fmt:message key="label.elimina" /></a>
			<a href="javascript:doHref('list.htm','')" class="btn btn-secondary"><fmt:message
			key="button.back" /></a>
			
		
	</div>
	<script type="text/javascript">
	vbg.ready(function(){

			async function getTestata(idElaborazione) {

				//console.log('ricarica blocco con idElaborazione=', idElaborazione);
				const formData = new FormData();
                   formData.append("idElaborazione", idElaborazione);
              
                   
                   vbg.mostraModalCaricamento();
                   const result = await fetch("ajaxCaricaModel.htm", {
                       method: "POST",
                       body: formData,
                   });
          
                   const testataModel = await result.json();
                   //console.log(testataModel);

                   caricaDatiTestata(testataModel);
                   vbg.nascondiModalCaricamento();
                   
                   
			}
			
			function caricaDatiTestata(testataModel){
				
				generaSezioneDettaglio(testataModel.descrizione, testataModel.data_inizio, testataModel.data_fine);
				
				generaSezioneFiltri(testataModel.filtri);
				
				generaSezioneSchedeDinamiche(testataModel.modelli);
				
				generaSezioneListaIstanze(testataModel.righe);
			}
			
			function generaSezioneDettaglio(descrizione, dataInizio, dataFine){
				document.getElementById('testata.descrizione').innerHTML = descrizione;
				document.getElementById('testata.data_inizio').innerHTML = parseJSONDate(dataInizio);
				document.getElementById('testata.data_fine').innerHTML = parseJSONDate(dataFine);
			}
			
			function generaSezioneFiltri(filtri){
				//filtri
				let filtriInterventi = document.getElementById('filtri.interventi');
				let filtriRegistri = document.getElementById('filtri.registri');
				let filtriStati = document.getElementById('filtri.stato_istanza');
				
				let testoInterventi = document.createElement("ul");
				let testoRegistri = document.createElement("ul");
				let testoStati = document.createElement("ul");
				
				filtri.forEach( (element)=>{
										
					if( element.filtro.startsWith('INTERVENTI') == true ) {
						let li = document.createElement("li");
						li.innerHTML = element.valore.replaceAll(',','<br/>');
						testoInterventi.appendChild(li);
					} 
					
					if( element.filtro.startsWith('REGISTRI') == true ) {
						let li = document.createElement("li");
						li.innerHTML = element.valore.replaceAll(',','<br/>');
						testoRegistri.appendChild(li);
					}
					
					if( element.filtro.startsWith('STATO_ISTANZA') == true ) {
						let li = document.createElement("li");
						li.innerHTML = element.valore;
						testoStati.appendChild(li);
					}
					
				});
				
				filtriInterventi.appendChild(testoInterventi);
				filtriRegistri.appendChild(testoRegistri);
				filtriStati.appendChild(testoStati);
			}
			
			function generaSezioneSchedeDinamiche(modelli){
				//modelli
				let listaSchede = document.getElementById('schede.lista');
				let testoModelli = document.createElement("ul");
				modelli.forEach( (modello)=>{
					let li = document.createElement("li");
					li.innerHTML = modello.descrizione;
					testoModelli.appendChild(li);
				});
				listaSchede.appendChild(testoModelli);
			}
			
			function generaColonnaIstanza(riga){

				let colonnaIstanza = document.createElement('td');
				
				let divComune = document.createElement('div');
				divComune.innerHTML = riga.comune;
				
				//console.log(riga.data_istanza);
				
				let divRifIstanza = document.createElement('div');
				divRifIstanza.style.whiteSpace = 'nowrap';
				divRifIstanza.innerHTML = 'Numero ' + riga.numero_istanza + ' del ' + parseJSONDate(riga.data_istanza);
				
				let linkIstanza = "javascript:historySet('${_urlback }','../istanze/view.htm?codice=" + riga.codice_istanza + "','')";

				let hrefIstanza = document.createElement('a');
				hrefIstanza.appendChild(divComune);
				hrefIstanza.appendChild(divRifIstanza);
				hrefIstanza.setAttribute('href',linkIstanza);
				
				colonnaIstanza.appendChild(hrefIstanza);
				
				return colonnaIstanza;
			}
			
			function generaSezioneListaIstanze(righe){
				//istanze
				let elencoIstanza =  document.getElementById('istanze');
				righe.forEach( (riga)=>{
					
					let tr = document.createElement("tr");
					
					let colonnaIstanza = generaColonnaIstanza(riga);
					let colonnaRichiedente = generaColonnaRichiedente(riga);
					
					let colonnaIntervento = document.createElement('td');
					colonnaIntervento.innerHTML = riga.intervento;
					
					let colonnaStatoElaborazione = document.createElement('td');
					if( riga.stato_elaborazione ){
						colonnaStatoElaborazione.innerHTML = riga.stato_elaborazione;						
					}
	
					let colonnaLog = document.createElement('td');
					if( riga.log ) {
						colonnaLog.innerHTML = riga.log;	
					}
					
					tr.appendChild(colonnaIstanza);
					tr.appendChild(colonnaRichiedente);
					tr.appendChild(colonnaIntervento);
					tr.appendChild(colonnaStatoElaborazione);
					tr.appendChild(colonnaLog);
					
					elencoIstanza.appendChild(tr);
					
				});
			}
			
			function generaColonnaRichiedente(riga){
				let colonnaRichiedente = document.createElement('td');
				
				let divRichiedente = document.createElement('div');
				divRichiedente.style.whiteSpace = 'nowrap';
				divRichiedente.innerHTML = riga.richiedente;
				colonnaRichiedente.appendChild(divRichiedente);
				
				if( riga.in_qualita_di ){
					let divInQualitaDi = document.createElement('div');
					divInQualitaDi.innerHTML = riga.in_qualita_di;
					divInQualitaDi.style.whiteSpace = 'nowrap';
					colonnaRichiedente.appendChild(divInQualitaDi);
				}
				
				if( riga.azienda ){
					let divAzienda = document.createElement('div');
					divAzienda.innerHTML = riga.azienda;
					divAzienda.style.whiteSpace = 'nowrap';
					colonnaRichiedente.appendChild(divAzienda);
				}
				
				return colonnaRichiedente;
			}
			
			function parseJSONDate(strDate){
				if( !strDate ){
					return '';
				}
				
				let date = new Date(strDate);
				
				let dd = String(date.getDate()).padStart(2, '0');
				let mm = String(date.getMonth() + 1).padStart(2, '0');
				let yyyy = date.getFullYear();
				
				return dd + '/' + mm + '/' + yyyy;
			}

			getTestata(${param.idElaborazione});
			document.getElementById("bottone-elabora").addEventListener("click",()=>{
				if(confirm('Verranno mandate in esecuzione le schede dinamiche per le istanze della lista.\nProseguire con l\'operazione?')){				
					vbg.mostraModalCaricamento();
					fetch('elabora.htm?idElaborazione='+${param.idElaborazione}, {
	                    method: "POST"	          
	                });
					setTimeout(()=>{
						window.location.href="../elaborazionimassive/view.htm?idElaborazione="+${param.idElaborazione};
					}, 3000);
				}
			});
	});
	
	function filtraTestoCelle() {
		
		let input, filter, table, tr, td, i, txtValue;
		
		input = document.getElementById("input_ricerca");
		filter = input.value.toUpperCase();
		table = document.getElementById("istanze");
		tr = table.getElementsByTagName("tr");
		
		let trovato = false;
		for (i = 1; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td");
			for (let cell of td) {
				if (cell) {
					txtValue = cell.textContent || cell.innerText;
					if (txtValue.toUpperCase().indexOf(filter) > -1) {
						trovato = true;
					}
				}
			}
			if(trovato) {
				tr[i].style.display = "";
				trovato = false;
			} else {
				tr[i].style.display = "none";
			}
		}
	}
</script>

</body>
</html>