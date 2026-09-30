<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.esito_commissione" />
	</title>
</head>
<body>
     <style>
     
    .alert-success {
	    color: #155724;
	    background-color: #d4edda;
	    border-color: #c3e6cb;
	}
	
	.alert-danger {
	    color: #721c24;
	    background-color: #f8d7da;
	    border-color: #f5c6cb;
	}

	.alert {
	    position: relative;
	    padding: .75rem 1.25rem;
	    margin-bottom: 1rem;
	    border: 1px solid transparent;
	    border-radius: .25rem;
	}
     </style>

	<span class="titoloPagina">
			<fmt:message key="label.esito_commissione" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	 <%
        int size =(Integer)request.getAttribute("numeroPresenti");
        pageContext.setAttribute("sizePresenti",size);
     %>
	<jsp:include page="../includes/history.jsp">
   		<jsp:param name="path" value="../commissioniediliziet/createEsitoCommissioniedilizieR" />
	</jsp:include>
    <c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${commissioniediliziet.commissioniedilizieR.movimento.istanza.id.codice}</c:param>
	</c:import>
	<br class="clear" />
    
	<div id="subcontent">
		<spring-form:form commandName="commissioniediliziet" name="inviodati" id="inviodati_id">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commissioniediliziet" />
		    </jsp:include>
		    
		    <c:set scope="page" value="${fn:length(listVoti)}" var="sizeList"></c:set> 
			    <c:set scope="page" value="${fn:length(commissioniediliziet.listaAppello)}" var="sizePresenti"></c:set> 
			   

			
			<div id="form" class="vbg-form">
				<fieldset>
				<legend><fmt:message key="label.discussione_commissioni"/></legend>	
			
				<table border="0"  cellpadding="2" cellspacing="0" class="vbg-table">
			    <thead>
					<tr style="vertical-align: text-top">
						<th><fmt:message key="label.discussione_votazione" /></th>
						<th><fmt:message key="label.carica" /></th>
						<th><fmt:message key="label.presente" /></th>
						<th>
							<div class="form-group">
								<select id="lista_id" onchange="cambiaTuttiVotazioni()">
									<option value="" label="<fmt:message key="label.voto" />"><fmt:message key="label.voto" /></option>
								    <c:forEach  items="${listVoti}" var="voti">
								    	<option id="option_id" value="${voti.id}" label="${voti.descrizione}">${voti.descrizione}</option>
								    </c:forEach>
								</select>
								<init:help idHelp="lista_id_help" textKey="help.commissioniediliziet.select_votazione" />
							</div>						
						</th>
						<th><fmt:message key="label.parere" /></th>
						<c:if test="${commissioniediliziet.entity.commedilizieTipologie.flagUploadDocParere eq true 
										or visualizzaAllegatiConTipologiaDocParereFalse eq true}">
							<th><fmt:message key="label.documento" /></th>
						</c:if>
						<th><fmt:message key="label.protocollo"/></th>
				</tr>
			    
			    
			    </thead>
			    <tbody >				
			    <c:forEach items="${commissioniediliziet.listaAppello}" var="appello" varStatus="a">
			    	<tr style="vertical-align: top">
			    		<td>
					    	${appello.commedilizieAppello.componente}
			    		</td>
			    		
				    	<td>
				    	  	${appello.commedilizieAppello.commedilizieCarica.descrizione}							
				    	</td>
				    	
				        <td>
				        	<div class="form-group" >
						        <spring-form:checkbox id="check_presente${a.index}" path="listaAppello[${a.index}].presente"  
						        	onclick="javascript:isAllowedVoto(this,'.voto_id${a.index}')" cssClass="elemento-modificato" />
					        </div>	
						</td>
						<td>
						<c:if test="${appello.commedilizieAppello.commedilizieCarica.dirittovoto == true}">
							<div class="voto_id${a.index} form-group" >
								 <spring-form:select id="select_id${a.index}" path="listaAppello[${a.index}].transientVoto" cssClass="elemento-modificato">
									  <spring-form:option value="">Seleziona</spring-form:option>
									  <spring-form:options id="voto_lista_id${a.index}" items="${listVoti}" itemLabel="descrizione" itemValue="id"/>
								 </spring-form:select>
								 <c:if test="${appello.votoFO == true}">
									 <i class="fas fa-cloud" title="<fmt:message key="label.commissioni_edilizie_votofo" />"></i>
								</c:if>
							 </div>
						</c:if>
						</td>
						<td >
							<div class="voto_id${a.index} form-group" >
									<spring:bind path="listaAppello[${a.index}].parere">
										<textarea name="${status.expression}" rows="2" cols="40"
													class="elemento-modificato controllo-conteggio-caratteri"
													data-id-conteggio-caratteri="areaContaCaratteri_${a.index}"
													style="min-width: 20em; height: 4em" >${status.value}</textarea>

									</spring:bind>
				                    <div id="areaContaCaratteri_${a.index}"></div>
							</div>	
						</td>
						<c:if test="${commissioniediliziet.entity.commedilizieTipologie.flagUploadDocParere eq true 
									or visualizzaAllegatiConTipologiaDocParereFalse eq true}">
							<td>
								<div class="voto_id${a.index}">
									<span class="sezione_allegati_discussione" data-id="${commissioniediliziet.listaAppello[a.index].id.codice}" 
										data-flag-upload-parere="${commissioniediliziet.entity.commedilizieTipologie.flagUploadDocParere}" 
										data-sola-lettura="${visualizzaAllegatiConTipologiaDocParereFalse}"></span>	
									<spring-form:hidden id="oggetto_id_codice_${commissioniediliziet.listaAppello[a.index].id.codice}" path="listaAppello[${a.index}].codiceoggetto"/>
								</div>
							</td>
						</c:if>
						<td>
							<c:if test="${commissioniediliziet.listaAppello[a.index].numeroprotocollo != null}">
								N. ${commissioniediliziet.listaAppello[a.index].numeroprotocollo} del <fmt:formatDate value="${commissioniediliziet.listaAppello[a.index].dataprotocollo}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN%>"/> 								 
							</c:if>
						</td>
			    </tr>
			    </c:forEach>
			    </tbody>
				
				
			</table>
			
			<div style="margin-top: 10px;">
				<div role="alert" id="feedback-btnsalvaVotazioni"></div>
				<a id="btnsalvaVotazioni" class="btn btn-primary"><fmt:message key="button.update" /></a>			
			</div>
			
 			</fieldset>	
			<fieldset>
				<legend><fmt:message key="label.esito"/></legend>	
				 	<div class="form-group">
				 		<label><fmt:message key="label.tipologia_parere" /></label>
				 		<c:choose>
				 		<c:when test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice==null}">
				    	
				    		 <spring-form:select cssClass='required' id="tipologia_parere_id" path="commissioniedilizieR.commedilizieTipopareri.id.codice">
							 <spring-form:options items="${commissioniediliziet.listTipopareri}" itemLabel="descrizione" itemValue="id.codice"/>
							 </spring-form:select>
							<div id="errori_tipologia_parere_id" class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>  
						</c:when>
						<c:otherwise>
							<spring-form:input path="commissioniedilizieR.commedilizieTipopareri.descrizione" readonly="true" size="30"/>
						</c:otherwise>
				 		</c:choose>
				 		
				 	</div>
					<div class="form-group">
				 		<label><fmt:message key="label.template_pareri" /></label>
				 		<select id="mailtipo_id" onchange="recuperaParere(this);">
						<option></option>
						<c:forEach items="${mailtipos}" var="mailtipi_var">
							<option label="${mailtipi_var.descrizione}" value="${mailtipi_var.id.codice}">${mailtipi_var.descrizione}</option>
						</c:forEach>
						</select>
				 	</div>	
					<div class="form-group">	
				 		<label><fmt:message key="label.parere" /></label>
				 		<c:choose>
				 		<c:when test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice==null}">
					 		<spring-form:textarea cssClass='control-to-validate  required'  id="parere_id" path="parere" cols="60" rows="8" />
					    	<spring-form:errors path="commissioniedilizieR.movimento.parere" cssClass="error"/>	
						</c:when>
						<c:otherwise>
							<spring-form:textarea cssClass='required' id="parere_id" path="commissioniedilizieR.movimentoRientro.parere" cols="60" rows="8" />
				    		<spring-form:hidden path="parere"/>
					    	<spring-form:errors path="commissioniedilizieR.movimento.parere" cssClass="error"/>							
						</c:otherwise>
				 		</c:choose>
				 		<div id="errori_parere_id" class="error validation-feedback"><fmt:message key="label.campo_obbligatorio" /></div>  
				 	</div>
				 	
				 	
				 	<div style="margin-top:10px">
		
						<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice==null}">
							<a class="btn btn-primary" id="insert_esito_id" href="javascript:salvaCommissioni()"><fmt:message key="button.insert" /></a>
						</c:if>
						<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice!=null}">
							<a class="btn btn-primary" id="insert_esito_id"  href="javascript:salvaCommissioni()"><fmt:message key="button.update" /></a>
							<a class="btn btn-primary" href="javascript:doSubmit('deleteEsitoCommissioniedilizieR.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
						</c:if>
						<a class="btn btn-secondary" href="javascript:doSubmit('listCommissioniedilizieR.htm?codiceCommissione=${commissioniediliziet.commissioniedilizieR.commissioniedilizieT.id.codice}','',document.inviodati)"><fmt:message key="button.back" /></a>
					
					</div>
				 			
				 </fieldset>			 				 	
				</div>
			
		</spring-form:form>
	</div>
	<script type='text/javascript'>
	
	
	var salvaCommissioni= function(){
		
		if(campiObbligatoriSpecificati()){
			doSubmit('insertEsitoCommissioniedilizieR.htm','Attenzione! proseguire con l\'aggiornamento dei dati?', document.inviodati);
		}
	}
	
	
	var salvaVotazioni = async function (){
		
		let confirmMessage = 'Attenzione! proseguire con l\'aggiornamento dei dati?';
		<c:if test="${commissioniediliziet.commissioniedilizieR.movimentoRientro.id.codice!=null}">
			confirmMessage = 'Attenzione! È già stato espresso il parere per questa pratica.\nProseguire con l\'aggiornamento dei dati della discussione?';
		</c:if>
		
		if(confirm(confirmMessage)){
			vbg.mostraModalCaricamento();
			const data = new URLSearchParams(new FormData(document.getElementById('inviodati_id')));
			const response = await fetch("${pageContext.request.contextPath}/commissioniediliziet/ajaxSalvaDiscussioneEsitoCommissioniedilizieR.htm", {
	            method: "POST",
	            cache: "no-cache",
	            body: data                
			});	
			
			let messaggio = await response.text();
			let feedback = document.querySelector('#feedback-btnsalvaVotazioni')
			vbg.nascondiModalCaricamento();
			let testo = '';
			let className= '';
			if (response.status === 200) {
				testo  = '<i class="fas fa-check"></i>&nbsp;dati salvati correttamente ';
				className = 'alert alert-success';
			}else{					
				testo = '<i class="fas fa-exclamation"></i>&nbsp;si è verificato un errore nel salvataggio dati: ' + messaggio;
				className = 'alert alert-danger';
			}
			feedback.className = className;
			feedback.innerHTML = testo;
			setTimeout(() => {
				feedback.style.display =  'none';
			}, 2500);
			aggiornaAllegati();
		}
	}
	
    function cambiaTuttiVotazioni()
    {  
  	var indexSelect=$('lista_id').selectedIndex;
	    for(var i=0;i<${sizePresenti};i++){
	    	if(document.getElementById('select_id'+i)){
				document.getElementById('select_id'+i).options[indexSelect].selected=true;
				document.getElementById('select_id'+i).dispatchEvent(new Event('change'));
	    	}
	    }  
    }
	var recuperaParere = async function(elem)
	{
		var code=elem[elem.selectedIndex].value;
		if(code!=''){
			vbg.mostraModalCaricamento();
			
			
			const data = new URLSearchParams();
			data.append('codiceistanza','${commissioniediliziet.commissioniedilizieR.movimento.istanza.id.codice}');
			data.append('codicemovimento','${commissioniediliziet.commissioniedilizieR.movimento.id.codice}');
			data.append('chiave_ricerca', code);
			
			const response = await fetch("${pageContext.request.contextPath}/jsonmail/recuperaOggettoCorpo.htm", {
                method: "POST",
                cache: "no-cache",
                body: data                
			});		
			
			let messaggio = await response.text();
			vbg.nascondiModalCaricamento();
			
			if (response.status === 200) {
				let json = messaggio.evalJSON();
				let mailtipo = json.mailtipo;
				let parere = document.querySelector('#parere_id')
				parere.value = mailtipo.corpo;
				parere.dispatchEvent(new Event('focusout'));
			}else{					
				alert(messaggio);
			}
			
		
		}else{
			
			document.querySelector('#parere_id').value='';
		}
	}
	
	
	function isVota(size)
	{
		
		for (i=0;i<size;i++)
		{
			if(document.getElementById('check_presente'+i).checked==false)
			{					
				document.querySelectorAll('.voto_id'+i).forEach((item) => {
	        			item.style.display='none';
	        					
	        	});					
			}
		}
	}
	
	function isAllowedVoto(obj,voto)
	{
		
		document.querySelectorAll(voto).forEach((item) => {
			if(obj.checked){
				item.style.display='';
			}else{
				item.style.display='none';
			}

		});		
	}
	
	function campiObbligatoriSpecificati(){
		
		let campiOk = true;
		
		document.querySelectorAll('.required').forEach(
			x => {
				if (x.value === ''){
					x.dispatchEvent(new Event('focusout'));
					console.log(x.id + ': ' + x.value)
					campiOk = false;
					return;
				}
			}				
		);
		
		return campiOk;
	}					
		
	var eliminaAllegatoVotazione = function (item){
	
		if(confirm('Attenzione! Si intende eliminare l\'allegato caricato?\nÈ necessario salvare i dati')){
			let id = item.getAttribute('data-id');
			document.querySelector('#file-content-'+id).style.display = 'none';
			document.querySelector('#oggetto_id_codice_'+id).value = '';		
			item.style.display='none';
		}		
	}
	
	
	
	var setInnerHTML = function(elm, html) {
		  elm.innerHTML = html;
		  Array.from(elm.querySelectorAll("script")).forEach( oldScript => {
		    const newScript = document.createElement("script");
		    Array.from(oldScript.attributes)
		      .forEach( attr => newScript.setAttribute(attr.name, attr.value) );
		    newScript.appendChild(document.createTextNode(oldScript.innerHTML));
		    oldScript.parentNode.replaceChild(newScript, oldScript);
		  });
		}
	
	var aggiornaAllegati = function (){
		
		document.querySelectorAll('.sezione_allegati_discussione').forEach(async (item) => {
			
			let id 			= item.getAttribute('data-id');
			let solaLettura = item.getAttribute('data-sola-lettura');
			let tipologiaUploadParere = item.getAttribute('data-flag-upload-parere');
			
			const data = new URLSearchParams();
			data.append('id_allegato',id);
			data.append('sola_lettura', solaLettura);
			data.append('tipologiaUploadParere',tipologiaUploadParere);
			
			const response = await fetch("${pageContext.request.contextPath}/commissioniediliziet/ajaxSezioneAllegati.htm", {
                method: "POST",
                cache: "no-cache",
                body: data                
			});		
			
			let messaggio = await response.text();
			
			
			vbg.nascondiModalCaricamento();
			
			if (response.status === 200) {

				setInnerHTML(item, messaggio);
				
			}else{					
				item.innerHTML = messaggio;
			}
			
			
			document.querySelectorAll('.elimina-allegato').forEach((item) => {
					if(!item.getAttribute('click-attached')){ 
						 item.addEventListener('click', (event) => {
							 event.preventDefault();
							 eliminaAllegatoVotazione(item); 
						});
						item.setAttribute('click-attached','true');
					}
			});	
			
		});
	}
	
	vbg.ready(() => {	


		
		document.querySelectorAll('.elemento-modificato').forEach((item) => {
			
			item.addEventListener('change', (event) => {
				  console.log(item.name);
			});
					
		});
		
		document.querySelectorAll('.required').forEach(
				x => { 
					
					x.required = true;
					
					let targetErrore = document.getElementById('errori_' + x.id);
					
					targetErrore.hide();
					
					x.addEventListener('focusout', function () {
						x.classList.remove('input-error');
						targetErrore.hide();
						if (x.value === '') {
							targetErrore.show();
							x.classList.add('input-error');
						}
					});						
					
				}
			);
		
		
		isVota(${sizePresenti});
		
		document.querySelector('#btnsalvaVotazioni').addEventListener('click', (event) => {
			salvaVotazioni();			
		});
					
		aggiornaAllegati();	
		
		
		
	}); 
	</script>
	
	<script type="module">
	import { VbgContaCaratteriRimanenti } from '${pageContext.request.contextPath}/scripts/custom-components/vbg-dettaglio-posizione-debitoria/vbg-conta-caratteri-rimanenti.js?<%=vJS %>'; 

	vbg.ready(() => {	

		document.querySelectorAll('.controllo-conteggio-caratteri').forEach((item) => {
			let maxLength = 4000;
			let idContaCaratteri = item.dataset.idConteggioCaratteri;
			let contenitoreContaCaratteri = document.querySelector('#'+idContaCaratteri);
		    let vgbContaCaratteriRimanenti = new VbgContaCaratteriRimanenti(item ,contenitoreContaCaratteri,maxLength);
	        vgbContaCaratteriRimanenti.verificaLunghezza();
					
		});

	});

	</script>
	
</body>
</html>