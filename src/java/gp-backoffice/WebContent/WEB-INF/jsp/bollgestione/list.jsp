<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="bollgestione.label.lista.title" /></title>
	<style media="all">
		.dettaglioExport {
			height: 16px;
			width: 16px;
			position: absolute;
			padding: 4px 0 0 4px;
		}
		.in_elaborazione{
			background-color: yellow
		}
	</style>
</head>
<body>
	<span class="titoloPagina"><fmt:message key="bollgestione.label.lista.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../bollgestione/list" />
	</jsp:include>
	<div id="subcontent">
		<div class="vbg-form">
			<form name="elementoListaBollettazioniForm" action="list.htm">			
			<fieldset>
				<legend>
					<fmt:message key="label.ricerca" />
				</legend>
				<div class="form-group">
					<div class="input-icons">
						<i class="fa fa-search icon"></i> <input id="input_ricerca"
							type="text" placeholder="Cerca" onkeyup="filtraTestoCelle()" />
					</div>
					<div class="input-help">
						<fmt:message key="label.messaggio_ricerca_tabella" />
					</div>
				</div>
			</fieldset>
			<fieldset>
				<legend><fmt:message key="bollgestione.label.lista.title"/></legend>
			
				<table id="elementoListaBollettazioni_id" class="vbg-table">
					<thead>
						<tr>
							<th width="2%"><fmt:message key="label.codice"/></th>
							<th><fmt:message key="bollgestione.label.descrizione.table"/></th>
							<th><fmt:message key="bollgestione.label.tipo.table"/></th>
							<th><fmt:message key="bollgestione.label.periodo.table"/></th>
							<th><fmt:message key="bollgestione.label.dallaData.table"/></th>
							<th><fmt:message key="bollgestione.label.allaData.table"/></th>
							<th><fmt:message key="bollgestione.label.dataCreazione.table"/></th>
							<th><fmt:message key="bollgestione.label.stato.table"/></th>
							<th><fmt:message key="label.azioni"/></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${elementoListaBollettazioniList}" var="elementoListaBollettazioni_var">
							<tr>
								<td>
									<a href="javascript:historySet('${_urlback }','../bollgestione/view.htm?codice=${elementoListaBollettazioni_var.id}','');">${elementoListaBollettazioni_var.id}</a>
								</td>
								<td>${elementoListaBollettazioni_var.descrizione}&nbsp;<a data-id="${elementoListaBollettazioni_var.id}" class="elab_in_corso"></a></td>
								<td>${elementoListaBollettazioni_var.tipo}</td>
								<td>${elementoListaBollettazioni_var.periodo}</td>
								<td><fmt:formatDate value="${elementoListaBollettazioni_var.dallaData}"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /> </td>
								<td><fmt:formatDate value="${elementoListaBollettazioni_var.allaData}"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></td>
								<td><fmt:formatDate value="${elementoListaBollettazioni_var.dataCreazione}"  pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>" /></td>
								<td>${elementoListaBollettazioni_var.stato}</td>
								<td>
									<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../bollgestione/view.htm?codice=${elementoListaBollettazioni_var.id}','');" title="<fmt:message key="label.edit.record" />${elementoListaBollettazioni_var.id}">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
									<c:if test="${mostraFunzioneComunicazioniMassive eq true}">
										<a class= "emailColumn" href="javascript:historySet('${_urlback }','../comunicazionibollettazione/list.htm?idBollettazione=${elementoListaBollettazioni_var.id}','');" title="<fmt:message key="label.comunicazione.bollettazione.title" />"></a>
									</c:if>
									<a class="dettaglioExport" href="javascript:historySet('${_urlback }','../bollgestione/createExportModalitaPentaho.htm?idBollettazione=${elementoListaBollettazioni_var.id}','');" title="<fmt:message key="button.esporta"/>">
										<i class="fa fa-file-export"></i>
									</a>										
								</td>
							</tr>
						</c:forEach>
					</tbody>			
				</table>
			</fieldset>		
			
		</form>
		<script type="text/javascript">
			var _jmesaUrl='list.htm?';
			var _captionTab='<fmt:message key="bollgestione.label.lista.title" />';
			
			function filtraTestoCelle() {
				
				let input, filter, table, tr, td, i, txtValue;
				input = document.getElementById("input_ricerca");
				filter = input.value.toUpperCase();
				table = document.getElementById("elementoListaBollettazioni_id");
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
				  if(trovato){
				  	tr[i].style.display = "";
				  	trovato = false;
				  } else{
				  	tr[i].style.display = "none";				  	
				  }
				}
			}

			async function verificaElaborazioni(){
			
			
			const data = new URLSearchParams();
			let presentiBollettazioni = false;	
			document.querySelectorAll('.elab_in_corso').forEach(async e=>  {
				let codice = e.dataset.id;
			 	data.append('idBollettazioneTestata',codice);
			 	presentiBollettazioni = true;
		    });    
			
			if(!presentiBollettazioni){
				console.log("Non sono presenti bollettazioni");
				return;
			}
			
			let url = "${pageContext.request.contextPath}/ajax/ajaxBollettazioneVerificaElaborazioni.htm";
		 	const response = await fetch(url, {
				method: "POST",
                cache: "no-cache",
                body: data       
			});
		 	
		 	
		 	
		 	if (response.status !== 200) {
			      let errore = await response.text();
			      console.error(errore);
			      throw errore;
			 }
		 	
			 let elaborazioni = await response.json();
			 	elaborazioni.elaborazioni_in_corso.each(e =>{
			 		document.querySelectorAll('.elab_in_corso[data-id="'+e+'"]').forEach(async el=>  {
			 			el.title='È in corso l\'elaborazione dell\'invio al nodo. Click per dettagli';
			 			el.style="cursor:pointer";
						el.innerHTML=`
									<i class="fa fa-gears fa-lg"></i><span></span>
								`;
						el.addEventListener('click', function(evt) {
							statoElaborazione(el);
							evt.preventDefault();
						});
						
						for (const child of el.parentElement.parentElement.children) {
							 child.classList.add("in_elaborazione");
						}

			 		});    

			 		 console.log(e);
			 	});
			}
			async function statoElaborazione(el){
				
				let idBollettazioneTestata = el.dataset.id;
				
				let data = new URLSearchParams();
				 	data.append('idBollettazioneTestata',idBollettazioneTestata);
			       
				 	
				el.children[1].innerHTML=`&nbsp;<i id="spinner" class="fa fa-circle-o-notch fa-spin fa-lg"></i>`;	
				 	
				let url = "${pageContext.request.contextPath}/ajax/ajaxBollettazioneVerificaElaborazioneNodoPagamenti.htm";
			 	const response = await fetch(url, {
					method: "POST",
	                cache: "no-cache",
	                body: data       
				});
				
			 	if (response.status !== 200) {
				      let errore = await response.text();
				      console.error(errore);
				      throw errore;
				 }
			 	
				 let elaborazioni = await response.json();
				 
				 if(elaborazioni.elaborazione_in_corso){
					 el.children[1].innerHTML=`&nbsp;Elaborazione in corso: elaborati \${elaborazioni.elaborati} su \${elaborazioni.totale}`;
				 }else{
					 el.innerHTML = 'Elaborazione completata';
				 }
			 	
			}
			
			vbg.ready(() => {
				
				verificaElaborazioni();
			});
			
		</script>
		</div>
	</div>
	<div class="form-button">		
		<a class="btn btn-primary" href="javascript:doHref('create.htm','');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
		
	</div>
</body>
</html>