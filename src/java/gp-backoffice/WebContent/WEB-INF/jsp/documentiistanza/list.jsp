<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.security.LoggedUser"%>
<%@ page import="org.springframework.security.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.context.SecurityContext"%>
<%@ page import="org.springframework.security.userdetails.UserDetails"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="documentiistanza.label.lista_documentiistanza.title" /></title>	
	<%
		SecurityContext sc = SecurityContextHolder.getContext();
		UserDetails ud = (UserDetails)sc.getAuthentication().getPrincipal();
		LoggedUser user = (LoggedUser)ud;
		Boolean isAmministratore = (Boolean)user.isAmministratore();
		pageContext.setAttribute("isAmministratore",isAmministratore);
	%>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	
</head>
<body>
	<jsp:include page="../includes/history.jsp">
    	<jsp:param name="path" value="../documentiistanza/list" />
	</jsp:include>	
	<span class="titoloPagina"><fmt:message key="documentiistanza.label.lista_documentiistanza.title" /></span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="list"/>
    </jsp:include>
    <jsp:include page="../includes/displayGlobalMessages.jsp" >
		<jsp:param name="commandName" value="documentiistanza" />
	</jsp:include>    
    <c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${documentiistanza.istanza.id.codice}</c:param>
	</c:import>
	<c:if test="${not empty documentiistanza.istanza.lavori}">
		<div class="header_dati">
				<div class="header_dato">
					<span class="header_dato_etichetta"><fmt:message key="label.lavori" />:</span>
					<span class="header_dato_valore">${documentiistanza.istanza.lavori}</span>
				</div>		
		</div>
	</c:if>
	<style>
		.applica_layer {
		  padding-block: inherit;
		}	
	</style>
	<script type="text/javascript">
		function visualizzaDocDaVerificare(){
			var scelta = jQuery('#visualizzaValidi').val();
			nascondiTutti();	
			jQuery( ".elementoValidabile" ).each(function() {
			  	var valore = jQuery( this ).val();
			  	if(scelta === 'tutti'){
			  		visualizzaDocumento(jQuery( this ), true)
			  	}else if(scelta === 'DaVerificareEValido'){
			  		
				  		if(valore === "1" || valore === "null"){
				  			visualizzaDocumento(jQuery( this ), true);
				  		}else{ 
				  			visualizzaDocumento(jQuery( this ), false) ;
				  		}
				  		
			  	}else{
			  		valore === scelta ? visualizzaDocumento(jQuery( this ), true):visualizzaDocumento(jQuery( this ), false) ;
			  	}
			});
		}

		function nascondiTutti(){
			
			jQuery( ".elementoValidabile" ).each(function() {
				jQuery( this ).parent().parent().hide();
			});			
		}	
		
		function visualizzaDocumento(obj, mostra){
			
			console.log(obj +"-->"+mostra);
			if(mostra){
				jQuery( obj ).parent().parent().show();
			}else{
				jQuery( obj).parent().parent().hide();
			}
			
		}
	
		function hideXMLEst() {
			var checked = jQuery("#CONF_UTENTE_VISUALIZZA_FILE_XML").prop('checked');
			console.log("(hideXMLEst) senza parametro  -->"+checked);
			jQuery(".nomeFile_cls").each(function () {
				var filename = jQuery(this).text(), labelTitle = "Nascondi file xml";
				if (filename.toLowerCase().endsWith(".xml") && checked) {
					labelTitle = "File xml nascosti";
					visualizzaDocumento(jQuery(this), false);
				} else {
					visualizzaDocumento(jQuery(this), true);
				}
				jQuery("#nascondiXMLFileTitleLabel").text(labelTitle);
			});
		}
		
		function nascondiXMLFile(obj, nomeParametro){
			var valore = obj.checked==true?'1':'0';
			hideXMLEst();
			saveUserPreference(nomeParametro, valore)
		}

		var asc = true; 
		
		function sortTable(tableId, tdIndex) {
			  if(asc){
				  asc = false;
			  }else{
				  asc = true;
			  }
			  var table, rows, switching, i, x, y, shouldSwitch;
			  table = document.getElementById(tableId);
			  switching = true;
			  /* Make a loop that will continue until
			  no switching has been done: */
			  while (switching) {
			    // Start by saying: no switching is done:
			    switching = false;
			    rows = table.rows;
			    /* Loop through all table rows (except the
			    first, which contains table headers): */
			    for (i = 1; i < (rows.length - 1); i++) {
			      // Start by saying there should be no switching:
			      shouldSwitch = false;
			      /* Get the two elements you want to compare,
			      one from current row and one from the next: */
			      x = rows[i].getElementsByTagName("td")[tdIndex];
			      y = rows[i + 1].getElementsByTagName("td")[tdIndex];
			      // Check if the two rows should switch place:
			      let xValOrDefault = x.innerText;
			      let yValOrDefault = y.innerText;
			      if(x.getAttribute('data-valore-ordinabile')){
			    	  xValOrDefault = x.getAttribute('data-valore-ordinabile');
			      }
			      if(y.getAttribute('data-valore-ordinabile')){
			    	  yValOrDefault = y.getAttribute('data-valore-ordinabile');
			      }
			      xValOrDefault==''? '0000000000000000000000000':xValOrDefault.toLowerCase()
			      yValOrDefault==''? '0000000000000000000000000':yValOrDefault.toLowerCase()
			      if(asc){
				      if (xValOrDefault > yValOrDefault) {
				        // If so, mark as a switch and break the loop:
				        shouldSwitch = true;
				        break;
				      }
			     }else{
				      if (xValOrDefault < yValOrDefault) {
					        // If so, mark as a switch and break the loop:
					        shouldSwitch = true;
					        break;
					      }			    	 
			     }
			    }
			    if (shouldSwitch) {
			      /* If a switch has been marked, make the switch
			      and mark that a switch has been done: */
			      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			      switching = true;
			    }
			  }
			}
		
	</script>
	
	
<div id="subcontent">	
	
	 <div id="form" class="vbg-form">
	 	<div class="form-group" style="display: inline;float: right; border: 1px dotted black; padding: 4px;">
            <label for="CONF_UTENTE_VISUALIZZA_FILE_XML" title="${titleNascondiFileXML}" id="nascondiXMLFileTitleLabel">Nascondi file xml</label>
      		 <%
						String showXMLFileChecked = "";
						// gestisce la visualizzazione dei file xml nei documenti dell'istanza e degli allegati dei movimenti
						if(((String)request.getAttribute(WebConstants.CONF_UTENTE_VISUALIZZA_FILE_XML)).equals("1")) {
						    showXMLFileChecked = "checked='checked'";
						}
						pageContext.setAttribute("showXMLFileChecked", showXMLFileChecked);
					%>
					<c:set var="titleNascondiFileXML"><fmt:message key="label.documentiistanza_visualizza_file_xml_help"/></c:set>
					<c:set var="_CONF_UTENTE_VISUALIZZA_FILE_XML"><%=WebConstants.CONF_UTENTE_VISUALIZZA_FILE_XML%></c:set>
					<input title="${titleNascondiFileXML}" ${showXMLFileChecked} type="checkbox" id="CONF_UTENTE_VISUALIZZA_FILE_XML" onclick="nascondiXMLFile(this, '${_CONF_UTENTE_VISUALIZZA_FILE_XML}');"/>
				
		 </div>
		<div class="form-group"  style="display: inline;float: right; border: 1px dotted black; padding: 4px;">
            <label><fmt:message key="label.visualizza" /></label>
            <select name="visualizzaValidi" id="visualizzaValidi" onchange="visualizzaDocDaVerificare()">
				<option  value="tutti">Tutti</option>
				<option  value="null">Da verificare</option>								
				<option  value="1">Valido</option>
				<option  value="0">Non valido</option>
				<option  value="DaVerificareEValido">Da verificare e Valido</option>
			</select>
      	</div>		 
	 
			
		
			
<form name="inviodati" action="list.htm" id="myForm" method="post">			
			
			<table class="vbg-table" id="docIstanzaTable">
				<thead>
					<% String indexTd ="1"; %>
					<tr>
						<th width="15%"><fmt:message key="label.nome" /><a onclick="sortTable('docIstanzaTable',0)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
						 <c:if test="${true eq categoria_presente }">
						 	<% indexTd ="2"; %>
							<th width="15%"><fmt:message key="label.categoria" /></th>
						</c:if>
						<th width="30%"><fmt:message key="label.documento" /><a onclick="sortTable('docIstanzaTable',<%= indexTd%>)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
						<th width="12%"><fmt:message key="label.note" /></th>
						<th width="10%"><fmt:message key="label.necessario" /></th>
						<th width="10%"><fmt:message key="label.presente" /></th>
						<th width="5%"><fmt:message key="label.valido" /></th>
						
						<th width="10%"><fmt:message key="label.data" /></th>						
						<th width="8%"><fmt:message key="documentiistanza.label.oggetto" /></th>
						<c:if test="${ isDocErAttivo eq true}">
							<th width="5%"><fmt:message key="label.archiviato_docer.list" /></th>	
						</c:if>		
						<th width="5%"><fmt:message key="label.edit.record" /></th>
						<th width="5%"><fmt:message key="label.seleziona" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${documentiistanza.documentiistanzaListDTO}" var="documentiistanza_var">
						<tr>
							<td><p class="nomeFile_cls">${documentiistanza_var.nomeFile}</p></td>
							<c:if test="${true eq categoria_presente }">
								<td>${documentiistanza_var.alberoprocDocumentiCat}</td>
							</c:if>
							<td class="sortable_doc">${documentiistanza_var.documento}</td>
							<td>${documentiistanza_var.note}</td>
							<td>
								<c:choose>
									<c:when test="${documentiistanza_var.isAttivoAllaChiusura}">
										<input id="chk_necessario${documentiistanza_var.id.codice}" type="checkbox" value="${documentiistanza_var.id.codice}" name="chk_necessario" ${documentiistanza_var.necessario?'checked':''} onclick="abilitaNecessario(this, 'chk_necessario${documentiistanza_var.id.codice}')"></input>
										<span id="result_necessario_${documentiistanza_var.id.codice}" style="display: none"></span>
									</c:when>
									<c:otherwise>
										<c:if test="${documentiistanza_var.necessario}"> 
											<input id="chk_necessario${documentiistanza_var.id.codice}" type="checkbox" value="${documentiistanza_var.id.codice}" name="chk_necessario" ${documentiistanza_var.necessario?'checked':''} onclick="abilitaNecessario(this, 'chk_necessario${documentiistanza_var.id.codice}')" disabled></input>
											<span id="result_necessario_${documentiistanza_var.id.codice}" style="display: none"></span>
										</c:if>
									</c:otherwise>
								</c:choose>						
							</td>
							<td>
								<input id="chk_presente${documentiistanza_var.id.codice}" ${documentiistanza_var.codiceOggetto!=null?'disabled checked':''} type="checkbox" value="${documentiistanza_var.presente}" name="chk_presente" ${documentiistanza_var.presente?'checked':''} onclick="regolaAggiornaPresenteDocIsta('chk_presente${documentiistanza_var.id.codice}', 'select_valido_doc_ist${documentiistanza_var.id.codice}',${documentiistanza_var.id.codice})"></input>
								<span id="result_presente_${documentiistanza_var.id.codice}" style="display: none"></span>					
							</td>
							<td>
								<c:choose>
									<c:when test="${documentiistanza_var.isAttivoAllaChiusura}">
										<select id="select_valido_doc_ist${documentiistanza_var.id.codice}" class="elementoValidabile" name="controllook" onchange="regolaAggiornaValidoDocIstanza('select_valido_doc_ist${documentiistanza_var.id.codice}','chk_presente${documentiistanza_var.id.codice}','${documentiistanza_var.id.codice}');">
											<option  value="null" ${documentiistanza_var.controllook==null?'selected':''}>Da verificare</option>								
											<option  value="1" ${documentiistanza_var.controllook==1?'selected':''}>Valido</option>
											<option  value="0" ${documentiistanza_var.controllook==0?'selected':''}>Non valido</option>
										</select>
									</c:when>
									<c:otherwise>
										<c:if test="${documentiistanza_var.controllook==null}">Da verificare</c:if>
										<c:if test="${documentiistanza_var.controllook==1}">Valido</c:if>
										<c:if test="${documentiistanza_var.controllook==0}">Non valido</c:if>
									</c:otherwise>
								</c:choose>						
							</td>
							<td>
								<c:choose>
									<c:when test="${documentiistanza_var.isAttivoAllaChiusura}">
										<input type="text" id="data_id${documentiistanza_var.id.codice}"
											value="<fmt:formatDate value="${documentiistanza_var.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />"
											name="documentiistanza.data" size="8" onblur="isValidDate('data_id${documentiistanza_var.id.codice}',true);" onchange="changeData('data_id${documentiistanza_var.id.codice}','${documentiistanza_var.id.codice}');"
										/>
										<init:calendar imagePath="/images/cal.gif"
											idImage="caldata${documentiistanza_var.id.codice}" idInput="data_id${documentiistanza_var.id.codice}"
											textKey="label.calendar" javascriptAction="changeData('data_id${documentiistanza_var.id.codice}','${documentiistanza_var.id.codice}');" />			
										<span id="result_${documentiistanza_var.id.codice}"	style="display: none"></span>
									</c:when>
									<c:otherwise>
										<fmt:formatDate value="${documentiistanza_var.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>
									</c:otherwise>
								</c:choose>								
							</td>						
							<td>
								<c:if test="${documentiistanza_var.codiceOggetto!=null}">
									<c:set var="readonlyOk" value="${!documentiistanza_var.isAttivoAllaChiusura}"/>
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="docIstanza${documentiistanza_var.id.codice}" />
			       						<jsp:param name="fileId" value="${documentiistanza_var.codiceOggetto}" />
			       						<jsp:param name="readonly" value="${readonlyOk}" />
			   						</jsp:include>
		   						
		   						</c:if>	
		   						
								<c:if test="${documentiistanza_var.codiceOggetto==null && documentiistanza_var.stcIdallegato!=null && documentiistanza_var.stcIddocumento!=null}">
									<jsp:include page="../includes/visualizzaOggettoSTC.jsp" >
			   							<jsp:param name="codiceistanza" value="${documentiistanza_var.codiceIstanza}" />
			   							<jsp:param name="stcIddocumento" value="${documentiistanza_var.stcIddocumento}" />
			   							<jsp:param name="stcIdallegato" value="${documentiistanza_var.stcIdallegato}" />
			   							<jsp:param name="codiceRiferimento" value="${documentiistanza_var.id.codice}" />
										<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_DOCUMENTI%>" />
										<jsp:param name="indice" value="documento_ist${documentiistanza_var.id.codice}"/>			   							
			   						</jsp:include>
								</c:if>	
								<!-- Applica stampigliatura -->	
								<div class="applica_layer">							
									<c:if test="${inite:endsWith(documentiistanza_var.nomeFile, '.pdf') && isAttivoLayerPDF}"> 
										 <a href="javascript:void(0);" class="applica_layer_id vbg-btn btn-applicalayer" data-codice-istanza="${documentiistanza_var.codiceIstanza}"
										 data-codice-oggetto="${documentiistanza_var.codiceOggetto}" title="Applica informazioni protocollo come layer"></a>
									 </c:if>
									 <c:if test="${inite:endsWith(documentiistanza_var.nomeFile, '.pdf') && isAttivoQr}">
										 <a href="javascript:void(0);" class="applica_qr_id vbg-btn btn-applicaqr" data-codice-istanza="${documentiistanza_var.codiceIstanza}"
										 data-codice-oggetto="${documentiistanza_var.codiceOggetto}" title="Applica informazioni protocollo come layer">
										 <i class="fa fa-qrcode"></i></a>
									</c:if>									 
								 </div>									
							</td>
							<c:if test="${ isDocErAttivo eq true}">
								<td>
									<jsp:include page="../documentiistanza/oggettodocer.jsp" >
										<jsp:param name="view" value="list" />	
										<jsp:param name="docnum" value="${documentiistanza_var.idDocer}" />
										<jsp:param name="identificativo" value="${documentiistanza_var.id.codice}_${documentiistanza_var.idDocer}" />
										<jsp:param name="codiceComune" value="${documentiistanza_var.codicecomune}" />
									</jsp:include>
								</td>	
							</c:if>		
							<td>
								<c:if test="${documentiistanza_var.isAttivoAllaChiusura}">
									<a class="dettaglioColumn" href="view.htm?codice=${documentiistanza_var.id.codice}" title="<fmt:message key="label.edit.record" />${documentiistanza_var.id.codice}">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</c:if>		
							</td>
							<td>
								<c:if test="${documentiistanza_var.isAttivoAllaChiusura}">
									<input id="chk_seleziona${documentiistanza_var.id.codice}" 
										type="checkbox" value="${documentiistanza_var.id.codice}" 
										data-id="${documentiistanza_var.id.codice}" 
										name="chk_seleziona" class="seleziona_doc_istanza" />
								</c:if>
							</td>									
						</tr>			
					</c:forEach>
				</tbody>		
			</table>
			
			<vbg-modal id="dialog-7">
				<div slot='body' id='popup_layer'>
				<h1>Applica layer protocollo</h1>
		         	<fmt:message key="label.messaggio_applica_layer_protocollo_documento">
						<fmt:param>${documentiistanza.istanza.numeroprotocollo}</fmt:param>
						<fmt:param><fmt:formatDate pattern= "<%=WebConstants.DATE_FORMAT_PATTERN %>" value ="${documentiistanza.istanza.dataprotocollo}" /></p>  </fmt:param>
					</fmt:message>
		         <div>
		            <label for="terms"><fmt:message key="label.accettazione"/></label>
		            <input type="checkbox" id="terms">
		         </div>
	         </div>
	        <div slot='footer'>
				<div class="btn btn-primary" id="saveButtonmodalLayer"><fmt:message key="button.applica_layer"/></div>
				<div class="btn btn-secondary" id="closeButtonmodalLayer"><fmt:message key="button.close"/></div>            
			</div>	
	      </vbg-modal>      
	      <!-- QR CODE -->
	      <vbg-modal id="dialogQr">
			<div slot='body' id='popup_layer'>
				<h2>Applica QR code</h2>
				<p>Applicare il QR code al documento?</p>       	
		        <div>
		            <label for="accept"><fmt:message key="label.accettazione"/></label>
		            <input type="checkbox" id="accept">
		        </div>
	        </div>
	        <div slot='footer'>
				<div class="btn btn-primary" id="saveButtonmodalQr"><fmt:message key="button.applica"/></div>
				<div class="btn btn-secondary" id="closeButtonmodalQr"><fmt:message key="button.close"/></div>            
			</div>	
	      </vbg-modal>
	      
	      
	      <vbg-modal id="dialogSpostaDoc" >
			<div slot='body' id='dialogSpostaDoc_layer'>
				<h2>Sposta/Copia i documenti selezionati</h2>
       			<ul>
       				<li><b>SPOSTA</b>: Muove i file da questa istanza a quella indicata. I file non saranno più presenti in questa pratica</li>
       				<li><b>COPIA</b>: Copia i file da questa istanza a quella indicata. I file rimarranno disponibili in questa pratica</li>
       			</ul>
       			<p>L'operatore DEVE avere i permessi sull'istanza di destinazione altrimenti verrà rilanciato un errore applicativo</p>
       			<p>Le operazioni saranno riportate nei logs di sistema</p>
		        <div>
        			<jsp:include page="../includes/autocompletergenerico-no-bind.jsp" >
						<jsp:param name="idElemento" value="istanza_dest_id" />		
						<jsp:param name="pathPropertyDescription" value="istanza_dest_descrizione" />
						<jsp:param name="pathPropertyCode" value="istanza_dest_codice" />
						<jsp:param name="autocompleterAjax" value="findIstanze.htm" />
						<jsp:param name="titleKey" value="documentmerge.label.istanza.alt" />
						<jsp:param name="autocompleterInputSize" value="90" />
						<jsp:param name="autocompleterMinChars" value="1" />
					</jsp:include>
		            
		        </div>
	        </div>
	        <div slot='footer'>
				<div class="btn btn-primary" id="spostaButtonmodal"><fmt:message key="button.sposta"/></div>
				<div class="btn btn-primary" id="copiaButtonmodal"><fmt:message key="button.copia"/></div>
				<div class="btn btn-secondary" id="closeButtonmodalSposta"><fmt:message key="button.close"/></div>            
			</div>	
	      </vbg-modal>	      
	      
</form>			


		
      
		<!-- Apre una dialog per visualizzare la nota -->
			
		<script type="text/javascript">
		
		function afterUpdateIstanza(inputField,listItem){
			var spanToShow = jQuery(listItem).find('#show_' + listItem.id);
			if(spanToShow.length > 0){
				inputField.value = spanToShow.text();
				jQuery("[name = 'movimento.istanza.id.codice']").val(listItem.id);
			}
		}
		
		function searchIstanzeParams(element, entry){
			var searchProtChk = jQuery("#searchProtocolloMovimentiChk");
			if(searchProtChk && searchProtChk.length){
				if(searchProtChk.prop('checked')){
					return entry + "&protInMovimenti=true";
				}
			}
			return entry + "&protInMovimenti=false";
		}
		
		

		jQuery(document).ready(function() {

				document.querySelectorAll('.seleziona_doc_istanza').forEach((button) => { 	
					button.addEventListener('click', async function(e){
						
						mostraNascondiFunzioniDocumenti();					
					});
				});
				
				document.getElementById('spostaDocumentiBtn').addEventListener('click', (e)=>{
					spostaDocumentiConConferma();
				});	
				
				mostraNascondiFunzioniDocumenti();
			});
		
			
			
			function mostraNascondiFunzioniDocumenti(){
				
				let visualizza = 'none';		
				document.querySelectorAll('.seleziona_doc_istanza').forEach((el) => { 
					
					if(el.checked){
						visualizza = '';
					}
					
				});
				
				document.querySelectorAll('.funzioni-documenti').forEach((el)=>{
					console.log('el==>'+el);
					el.style.display=visualizza;
					}
				);
				

			}
				
			const modalSpostaDoc = document.getElementById('dialogSpostaDoc');
			
			document.getElementById('spostaButtonmodal').addEventListener('click', (e)=>{
				console.log('sposta');
				spostaCopiaDocumenti(false);
			});	
			
			document.getElementById('copiaButtonmodal').addEventListener('click', (e)=>{
				console.log('copia');
				spostaCopiaDocumenti(true);
			});				
			
			
			async function spostaCopiaDocumenti(isCopia){
				
				
				
				let codiceIstanzaDest = document.getElementById('istanza_dest_id_hidden').value;
				if(	codiceIstanzaDest == ''){
					alert('E\' necessario specificare l\'istanza sulla quale spostare/copiare i documenti');
					return ;
				}
				if(confirm("Attenzione! Si vuole procedere con l\'operazione? L\'attività sarà riportata nei logs di sistema")){

					vbg.mostraModalCaricamento();
					try
					{
						const formData = new FormData();
				        formData.append('codiceIstanza', ${documentiistanza.istanza.id.codice});			        
				        formData.append('codiceIstanzaDest', codiceIstanzaDest);
				        formData.append('isCopia', isCopia);
				        document.querySelectorAll('.seleziona_doc_istanza').forEach((el) => { 
							
							if(el.checked){
						        formData.append('docIstanzaId', el.dataset.id);
							}
							
						});
						let response = await fetch("../documentiistanza/ajaxSpostaCopia.htm" , {
				            method: "POST",
				            cache: "no-cache",
				            body: formData
				        });
						
						let jsonResponse = await response.json();
						vbg.nascondiModalCaricamento();
						if(jsonResponse.esito != 'SUCCESS'){
							let err = 'Si sono verificati i seguenti problemi:';
							for (var i = 0; i < jsonResponse.errori.length; i++){
								  err += "\n"+jsonResponse.errori[i]+";";
							}
							alert(err);						
						}else{
							alert("Documenti spostati correttamente");
							document.location.reload();
						}
						
					} 
					catch(error) {                                    
	                    alert(error);
	                }
					finally{                                  
						vbg.nascondiModalCaricamento();
	                }
				
				}
			}
			
			
			function spostaDocumentiConConferma(){
			
				modalSpostaDoc.open();
			
			
			}
		
		
			document.getElementById('closeButtonmodalSposta').addEventListener('click', (e)=>{
				modalSpostaDoc.close();		
			});
			
			
			
			
			
		
			// GESTISCE LA FUNZIONE PER SELEZIONARE TUTTI I CHECKBOX PRESENTI NEI DOC DELL'ISTANZA 
			// A LANCIA LA FUNZIONE CHE AGGIORNA SUL DB IL VALORE
			function selezionaDeselezionaTuttiPresentiDocIstanza(opzione)
			{ 
				javascript:doHref('${pageContext.request.contextPath}/documentiistanza/impostaTuttiFlagPresenti.htm?codice='+${documentiistanza.istanza.id.codice}+'&presente='+opzione,'');
				
			}
			// GESTISCE LA FUNZIONE PER SELEZIONARE TUTTI I CHECKBOX NECESSARIO NEI DOC DELL'ISTANZA, 
			// LANCIA LA FUNZIONE CHE AGGIORNA SUL DB IL VALORE
			function selezionaDeselezionaTuttiNecessarioDocIstanza(opzione)
			{
				
				if(opzione == true){
					jQuery("input[id^='chk_necessario']").attr('checked', true);
					var opzione=true;
				}else
				{
					jQuery("input[id^='chk_necessario']").attr('checked', false);
					var opzione=false;
				}
			    
				new Ajax.Request('${pageContext.request.contextPath}/documentiistanza/ajaxImpostaAllFlagNecessario.htm?codice='+${documentiistanza.istanza.id.codice}+'&necessario='+opzione, {
					method: 'post',	
					onSuccess: function(transport){
					  dijit.showTooltip(transport.responseText, dojo.byId('id_check_necessario_tot'));
					  setTimeout(function(){dijit.hideTooltip(dojo.byId('id_check_necessario_tot'))},1000);	
					},
					onFailure: function(transport){ 
					  $(id).innerHTML= transport.responseText;
					  $(id).className='error_checkbox'
					  $(id).style.display='';
					  applyStyle();
					  $(id).pulsate();
					  $(id).fade();
					 }						    		 
			});
			}

			
			// GESTISCE IL CHECKBOX RICHIESTO (NECESSARIO) PER I DOCUMENTI ISTANZA
			function abilitaNecessario(obj, id){			
				new Ajax.Request('${pageContext.request.contextPath}/documentiistanza/ajaxAbilitaDisabilitaNecessario.htm?codice='+escape(obj.value)+'&necessario='+obj.checked, {
						method: 'post',	
						onSuccess: function(transport){
						  dijit.showTooltip(transport.responseText, dojo.byId(id));
						  setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
						},
						onFailure: function(transport){ 
						  $(id).innerHTML= transport.responseText;
						  $(id).className='error_checkbox'
						  $(id).style.display='';
						  applyStyle();
						  $(id).pulsate();
						  $(id).fade();
						 }						    		 
				});
			}
			
			<%--
			   1. Controlla se si può aggiornare il flag presente (Se è verificato o valido deve essere per forza presente)
	 		   2. Se passa il primo controllo aggiorna il flag su db
	 		--%>
	 		function regolaAggiornaPresenteDocIsta(presente,valido,codice)
	 		{
	 			if(document.getElementById(presente).checked==false && document.getElementById(valido).value!='null')
	 			{
	 				alert('<fmt:message key="label.se_verificato_allora_presente"/>');
	 				document.getElementById(presente).checked=true;
	 				return false;
	 			}
	 			
	 			 abilitaPresente(codice, presente);
	 		}
	 		
	 		
	 		// GESTISCE IL CHECHBOX PRESENTE IN DOCUMENTI ISTANZA
			function abilitaPresente(obj, id){			
				var opzione=document.getElementById(id).checked
				new Ajax.Request('${pageContext.request.contextPath}/documentiistanza/ajaxAbilitaDisabilitaPresente.htm?codice='+obj+'&presente='+opzione, {
						method: 'post',	
						onSuccess: function(transport){						
							dijit.showTooltip(transport.responseText, dojo.byId(id));
							 setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);
						},
						onFailure: function(transport){ 
						  $(id).innerHTML= transport.responseText;
						  $(id).className='error_checkbox';
						  applyStyle();
						  $(id).style.display='';
						  $(id).pulsate();
						  $(id).fade();
						}						    		 
				});
			}
	 		
			
			<%--
			    1. Controlla se il valore può essere cambiato (un documento può essere valido o verificato solo se presente)
		    	2. Se passa la validazione invoca il metodo che aggiorna il valore sul DB
			--%>
			function regolaAggiornaValidoDocIstanza(valido,presente,codice)
			{
			if(document.getElementById(valido).value!='null' && document.getElementById(presente).checked==false)
				{
				alert('<fmt:message key="label.non_verificato_allora_non_valido"/>');
				document.getElementById(valido).value=null;
				return false;
				}
			  changeValueValidoDocIstanza(codice, valido);
			}
			

			// GESTISCE LA SELECT BOX VALIDO IN DOCUMENTI ISTANZA
			function changeValueValidoDocIstanza(obj, id){			
				var opzione="";
			    if(document.getElementById(id).value!='null')
				{
					opzione=document.getElementById(id).value;
				}
				
				new Ajax.Request('${pageContext.request.contextPath}/documentiistanza/ajaxChangeValueFieldValido.htm?codice='+obj+'&valido='+opzione, {
							method: 'post',	
							onSuccess: function(transport){
							  dijit.showTooltip(transport.responseText, dojo.byId(id));
							  setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
							},
							onFailure: function(transport){ 
							  $(id).innerHTML= transport.responseText;
							  $(id).className='error_checkbox';
							  applyStyle();
							  $(id).style.display='';
							  $(id).pulsate();
							  $(id).fade();
							 }						    		 
					});
				}
			
			// GESTISCE IL CAMBIO DEL CAMPO DATA DEI DOCUMENTI DELL'ISTANZA
			function changeData(id, codice) {
				var data = document.getElementById(id);
				new Ajax.Request(
						'${pageContext.request.contextPath}/documentiistanza/ajaxChangeData.htm?codice='+ codice + '&data=' + escape(data.value), {
							method : 'post',
							onSuccess : function(transport) {
								dijit.showTooltip(transport.responseText, dojo.byId(id));
								setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
							},
							onFailure : function(transport) {
								$('result_'+codice).innerHTML = transport.responseText;
								$('result_'+codice).className = 'error_header';
								applyStyle();
								$('result_'+codice).style.display = '';
								$('result_'+codice).pulsate({
									pulses : 2,
									duration : 1.0
								});
							}
						});
			}
			
			function confermaOperazioneBackupStc(divId){
				dijit.byId(divId).show();
			}		
			
		</script>
		
	
	
	<div class="form-button" style="margin-top: 15px;">
		<a class="btn btn-primary" href="javascript:doHref('create.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','');"><fmt:message key="button.new" /></a>
		<a class="btn btn-secondary" href="javascript:doHref('createAllineaDocumenti.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','');"><fmt:message key="button.allinea_documenti" /></a>
		
		<a class="btn btn-primary funzioni-documenti" href="javascript:doSubmit('eliminaDocumenti.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.cancella_documenti" /></a>
		<a class="btn btn-primary funzioni-documenti" id="spostaDocumentiBtn"><fmt:message key="button.sposta_documenti" /></a>
		
		<a class="btn btn-secondary" href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fistanzeallegati%2Flist.htm%3FcodiceIstanza%3D${documentiistanza.istanza.id.codice}','');" title="<fmt:message key="button.allegati_endo" />" ><fmt:message key="button.allegati_endo" /></a>
		<c:if test="${isAllegatiStcPresenti eq true }">
			<a class="btn btn-secondary" href="javascript:confermaOperazioneBackupStc('message_dialog_confirm');" title="<fmt:message key="label.salva_tutti_allegati_stc" />"><fmt:message key="button.salva_allegati_stc" /></a>
		</c:if>
		<c:if test="${isAmministratore eq true && isAllegatiStcPresenti eq true}">
			<a class="btn btn-secondary" href="javascript:historySet('${_urlback }','../stc/resetBackupSTC.htm?codiceIstanza=${documentiistanza.istanza.id.codice}');"  ><fmt:message key="button.reset_backup_stc" /></a>	
		</c:if>
		<c:if test="${ isDocErAttivo eq true}">
			<a class="btn btn-secondary" href="javascript:historySet('${_urlback}','../documentiistanza/pannelloRicercaDocer.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','');"><fmt:message key="label.ricerca_documenti_docer" /></a>
		</c:if>
		<a class="btn btn-secondary" href="javascript:historySet('${_urlback}','../documentiistanza/preDownloadDocumentiZip.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','')"><fmt:message key="button.download_documenti" /></a>			
		<a class="btn btn-secondary" href="javascript:historySet('${_urlback}','../movimenti/listElaborazione.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','')"><fmt:message key="button.elaborazione" /></a>
		<a class="btn btn-secondary" href="../documentiistanza/ajaxExportDocumentazioneCsv.htm?codiceIstanza=${documentiistanza.istanza.id.codice}" title="<fmt:message key="label.help_export_csv" />"><fmt:message key="button.export_csv" /></a>																						
		<a class="btn btn-secondary" href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a>
	</div>
		

	<!-- GESTIONE DEI DOCUMENTI DEGLI ENDO PROCEDIMENTI DELL'ISTANZA --> 
	
	
	
	<c:if test="${not empty documentiistanza.istanzeallegatiDTOs}">
		<%
			String styleEndo = "";
			String displayEndoCollassato="";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCENDO_DIV)).equals("1")) {
			    displayEndoCollassato=" data-collassato='false' ";
			} else {
			    styleEndo="collassato";
			}
		%>
		
		<fieldset class="collassabile <%=styleEndo%>" <%= displayEndoCollassato %> id="documenti_endo_id">
		<legend onclick="salvaVisualizzazionePannello('<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCENDO_DIV %>','documenti_endo_id')"><fmt:message key="documentiistanza.label.documenti_su_endoprocedimenti.title" /></legend>	

		<div class="form-group">
		
			<table class="vbg-table" id="docEndoTable" >
				<thead>
					<tr>
						<th width="15%"><fmt:message key="label.nome" /><a onclick="sortTable('docEndoTable',0)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
						<th width="30%"><fmt:message key="label.allegato" /><a onclick="sortTable('docEndoTable',1)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
						<th width="15%"><fmt:message key="label.note" /></th>
						<th width="10%"><fmt:message key="label.endoprocedimento" /><a onclick="sortTable('docEndoTable',3)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
						<th width="10%"><fmt:message key="label.richiesto" /></th>
						<th width="10%"><fmt:message key="label.presente" /></th>
						<th width="5%"><fmt:message key="label.valido" /></th>			
						<th width="5%"><fmt:message key="documentiistanza.label.oggetto" /></th>
						<th width="5%"><fmt:message key="label.edit.record" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${documentiistanza.istanzeallegatiDTOs}" var="istanzeallegati_var">
						<tr>
								<td><p class="nomeFile_cls">${istanzeallegati_var.nomeFile}</p></td>
								<td>${istanzeallegati_var.allegatoextra}</td>
								<td>${istanzeallegati_var.note}</td>
								<td>
									${istanzeallegati_var.procedimento}
								</td>
								<td>
									<input id="chk_ist_all_necessario_${istanzeallegati_var.id.codice}" type="checkbox" value="${istanzeallegati_var.id.codice}" name="chk_necessario"  ${istanzeallegati_var.necessario?'checked':''} onclick="abilitaNecessarioIstanAllegati(this, 'chk_ist_all_necessario_${istanzeallegati_var.id.codice}')"></input>
									<span id="result_necessario_${istanzeallegati_var.id.codice}" style="display: none"></span>
								</td>
								<td>
									<input
										id="checkbox_presente_id${istanzeallegati_var.id.codice}" ${istanzeallegati_var.codiceOggetto!=null?'disabled checked':''} onclick="regolaAggiornaPresenteAllIsta('checkbox_presente_id${istanzeallegati_var.id.codice}','select_valido${istanzeallegati_var.id.codice}',${istanzeallegati_var.id.codice})"
										type="checkbox"
										value="${istanzeallegati_var.presente}"
										name="presente"
										${istanzeallegati_var.presente?'checked':''} /> 		
										<span id="result_presente${istanzeallegati_var.id.codice}"	style="display: none"></span>
								</td>
								<td>
									<select id="select_valido${istanzeallegati_var.id.codice}" class="elementoValidabile"  name="controllook" onchange="regolaAggiornaValidoIstanAll('select_valido${istanzeallegati_var.id.codice}','checkbox_presente_id${istanzeallegati_var.id.codice}','${istanzeallegati_var.id.codice}');">
											<option  value="null" ${istanzeallegati_var.controllook==null?'selected':''}>Da verificare</option>								
											<option  value="1" ${istanzeallegati_var.controllook==1?'selected':''}>Valido</option>
											<option  value="0" ${istanzeallegati_var.controllook==0?'selected':''}>Non valido</option>
										</select>
									<span id="result_look${istanzeallegati_var.id.codice}" style="display: none"></span>								
								</td>			
								<td>
									<c:if test="${istanzeallegati_var.codiceOggetto!=null}">
									<jsp:include page="../includes/visualizzaOggetto.jsp" >
			       						<jsp:param name="idElemento" value="iAllegati${istanzeallegati_var.id.codice}" />
			       						<jsp:param name="fileId" value="${istanzeallegati_var.codiceOggetto}" />
			   						</jsp:include>
			   						</c:if>	
			   						<c:if test="${istanzeallegati_var.codiceOggetto==null && istanzeallegati_var.stcIdallegato!=null && istanzeallegati_var.stcIddocumento!=null}">
										<jsp:include page="../includes/visualizzaOggettoSTC.jsp" >
				       						<jsp:param name="codiceistanza" value="${istanzeallegati_var.codiceIstanza}" />
				   							<jsp:param name="stcIddocumento" value="${istanzeallegati_var.stcIddocumento}" />
				   							<jsp:param name="stcIdallegato" value="${istanzeallegati_var.stcIdallegato}" />
				   							<jsp:param name="codiceRiferimento" value="${istanzeallegati_var.id.codice}" />
											<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_ENDO%>" />
											<jsp:param name="indice" value="documento_ist${istanzeallegati_var.id.codice}"/>			   							
				   						</jsp:include>							
									</c:if>
									<div class="applica_layer">							
										<c:if test="${inite:endsWith(istanzeallegati_var.nomeFile, '.pdf') && isAttivoLayerPDF}"> 
											 <a href="javascript:void(0);" class="applica_layer_id vbg-btn btn-applicalayer" data-codice-istanza="${istanzeallegati_var.codiceIstanza}"
											 data-codice-oggetto="${istanzeallegati_var.codiceOggetto}" title="Applica informazioni protocollo come layer"></a>
										 </c:if>
										 <c:if test="${inite:endsWith(istanzeallegati_var.nomeFile, '.pdf') && isAttivoQr}">
											 <a href="javascript:void(0);" class="applica_qr_id vbg-btn btn-applicaqr" data-codice-istanza="${istanzeallegati_var.codiceIstanza}"
											 data-codice-oggetto="${istanzeallegati_var.codiceOggetto}" title="Applica informazioni protocollo come layer">
											 <i class="fa fa-qrcode"></i></a>
										</c:if>									 
									 </div>						
								</td>
								<td>
									<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../istanzeallegati/viewIstanzaAllegato.htm?codice=${istanzeallegati_var.id.codice}')" title="<fmt:message key="label.edit.record" />">
										<label><fmt:message key="label.edit.record.image" /></label>
									</a>
								</td>						
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>	
		</fieldset>
		
		<script type="text/javascript">
        
			// GESTISCE IL CHECKBOX RICHIESTO
			function abilitaNecessarioIstanAllegati(obj, id){			
					
				new Ajax.Request('${pageContext.request.contextPath}/istanzeallegati/ajaxAbilitaDisabilitaNecessario.htm?codice='+escape(obj.value)+'&necessario='+obj.checked, {
							method: 'post',	
							onSuccess: function(transport){
							  dijit.showTooltip(transport.responseText, dojo.byId(id));
							  setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
							},
							onFailure: function(transport){ 
							  $(id).innerHTML= transport.responseText;
							  $(id).className='error_checkbox';
							  $(id).style.display='';
							  applyStyle();
							  $(id).pulsate();
							  $(id).fade();
							 }						    		 
					});
				}
			
				<%--
				 	1. Controlla se il valore può essere modificato (può essere messo a verifica o valido solo se il glag presnete è true)
		 		    2. In caso di validazione ottenuta, aggiorna il valore su DB
		 		--%>
		 		function regolaAggiornaValidoIstanAll(valido,presente,codice)
		 		{
		
		 			if(document.getElementById(valido).value!='null' && document.getElementById(presente).checked==false)
		 				{
		 				alert('<fmt:message key="label.non_verificato_allora_non_valido"/>');
		 				document.getElementById(valido).value=null;
		 				return false;
		 				}
		 			changeValueValido(codice, valido);
		 		}
				// GESTISCE LA SELECT BOX VALIDO
				function changeValueValido(obj, id){			
						var opzione="";
					    if(document.getElementById(id).value!='null')
						{
							opzione=document.getElementById(id).value;
						}
						
						new Ajax.Request('${pageContext.request.contextPath}/istanzeallegati/ajaxChangeValueFieldValido.htm?codice='+obj+'&valido='+opzione, {
									method: 'post',	
									onSuccess: function(transport){
									  dijit.showTooltip(transport.responseText, dojo.byId(id));
									  setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
									},
									onFailure: function(transport){ 
									  $(id).innerHTML= transport.responseText;
									  $(id).className='error_checkbox';
									  $(id).style.display='';
									  applyStyle();
									  $(id).pulsate();
									  $(id).fade();
									 }						    		 
							});
				}
		
		
		
			<%--
			 	1. Controlla se il flag presente può essere aggiornato. (Se il documento è verificato o valido non può essere settato come non presente)
			 	2. Se passa il controllo aggiorna il valore del flag su DB
			--%>
			function regolaAggiornaPresenteAllIsta(presente,valido,codice)
			{
				if(document.getElementById(presente).checked==false && document.getElementById(valido).value!='null')
				{
					alert('<fmt:message key="label.se_verificato_allora_presente"/>');
					document.getElementById(presente).checked=true;
					return false;
				}
				abilitaDisabilitaCheckbox(codice,presente,'','');
			}
		    //GESTICE IL CHECKBOX PRESENTE
	 		function abilitaDisabilitaCheckbox(codice,presente,valido) {

	  	       
	 			var isPresentato ='';
	 			
	 			if(document.getElementById(presente)!=null)
	 			{
	 				var isPresentato = document.getElementById(presente).value;
	 			}
	 			new Ajax.Request(
 						'${pageContext.request.contextPath}/istanzeallegati/ajxaChangeCheckboxvalue.htm?codice='+codice+'&presentato='+ isPresentato,
 						{
 							onSuccess : function(transport) {
 								dijit.showTooltip(transport.responseText, dojo.byId(presente));
 								setTimeout(function(){dijit.hideTooltip(dojo.byId(presente))},1000);
 							},
 							onFailure : function(transport) {
 								$(result+codice).innerHTML = transport.responseText;
 								$(result+codice).className = 'error_ajax_call';
 								$(result+codice).style.display = '';
 								applyStyle();
 								$(result+codice).pulsate;
 								({
 									pulses : 2,
 									duration : 1.0
 								});
 							}
 						});
 				}
				
		 		
				
		</script>
		
		
	</c:if>	
	
	
	<!--  DOCUMENTI DELLE PROCURE DELL'ISTANZE -->	
	<fieldset>
	<legend><fmt:message key="label.procure_dell_istanza" /></legend>
	
		<table class="vbg-table">
			<thead>
				<tr>
					<th><fmt:message key="label.procuratore" /> </th>
					<th><fmt:message key="label.anagrafe_rappresentata_da_procuratore" /> </th>
					<th><fmt:message key="label.richiesto" /> </th>
					<th><fmt:message key="label.presente" /> </th>
					<th><fmt:message key="label.valido" /> </th>
					<th><fmt:message key="label.nome" /> </th>
					<th><fmt:message key="label.oggetto" /> </th>
					<th><fmt:message key="label.documento_identita" /> </th>
					<th><fmt:message key="label.edit.record" /></th>
				</tr>
			</thead>
			<tbody>
				
				<c:forEach items="${istanzeprocures}" var="procura">
				<tr>
					<td>
						${procura.anagrafeProcuratore.descrizioneRichiedente}
					</td>
					<td>${procura.anagrafeRappresentato.descrizioneRichiedente} </td>
					<td>
						<input id="chk_doc_procure_necessario_${procura.id.codice}" type="checkbox" value="${procura.id.codice}" name="chk_necessario"  ${procura.necessario?'checked':''} onclick="abilitaNecessarioProcureAllegati(this, 'chk_doc_procure_necessario_${procura.id.codice}')"></input>
						<span id="result_necessario_${procura.id.codice}" style="display: none"></span>
					</td>
					<td>
						<input id="chk_doc_procure_presente_${procura.id.codice}" ${procura.codiceOggetto!=null?'disabled checked':''} onclick="regolaAggiornaPresenteProcureAllegati('chk_doc_procure_presente_${procura.id.codice}','select_valido_doc_procure${procura.id.codice}',${procura.id.codice})"
							   type="checkbox" value="${procura.presente}" name="presente" ${procura.presente?'checked':''} /> 
					</td>
					<td>
						<select id="select_valido_doc_procure${procura.id.codice}" name="controllook" class="elementoValidabile" onchange="changeValueValidoDocProcure('${procura.id.codice}','select_valido_doc_procure${procura.id.codice}');">
							<option  value="null" ${procura.controllook==null?'selected':''}>Da verificare</option>								
							<option  value="1" ${procura.controllook==1?'selected':''}>Valido</option>
							<option  value="0" ${procura.controllook==0?'selected':''}>Non valido</option>
						</select>
					</td> 
					<td>
						<p class="nomeFile_cls">${procura.nomeFile}</p>
					</td>
					<td>
						<c:if test="${procura.codiceOggetto==null && procura.stcIdallegato!=null && procura.stcIddocumento!=null}">
							<jsp:include page="../includes/visualizzaOggettoSTC.jsp">
	       						<jsp:param name="codiceistanza" value="${procura.codiceIstanza}" />
	   							<jsp:param name="stcIddocumento" value="${procura.stcIddocumento}" />
								<jsp:param name="stcIdallegato" value="${procura.stcIdallegato}" />	
								<jsp:param name="indice" value="allegato_proc${procura.codiceIstanza}"/>		 						
							</jsp:include>    				
						</c:if>
						<c:if test="${procura.codiceOggetto != null}">						
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       						<jsp:param name="idElemento" value="docProcure${procura.id.codice}" />
	       						<jsp:param name="fileId" value="${procura.codiceOggetto}" />
	   						</jsp:include>
						</c:if>

					</td>
					
					<td>
						<c:if test="${procura.codiceOggettoDocId==null && procura.stcIdallegatoDocId!=null && procura.stcIddocumentoDocId!=null}">
							<jsp:include page="../includes/visualizzaOggettoSTC.jsp">
	       						<jsp:param name="codiceistanza" value="${procura.codiceIstanza}" />
	   							<jsp:param name="stcIddocumento" value="${procura.stcIddocumentoDocId}" />
								<jsp:param name="stcIdallegato" value="${procura.stcIdallegatoDocId}" />	
								<jsp:param name="indice" value="allegato_proc${procura.codiceIstanza}"/>		 						
							</jsp:include>    				
						</c:if>
						<c:if test="${procura.codiceOggettoDocId != null}">						
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       						<jsp:param name="idElemento" value="docProcureDocId${procura.id.codice}" />
	       						<jsp:param name="fileId" value="${procura.codiceOggettoDocId}" />
	   						</jsp:include>
						</c:if>

					</td>
					<td>
						<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../istanzeprocure/view.htm?codice=${procura.id.codice}');" title="<fmt:message key="label.edit.record" /> ${procura.id.codice}">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>							
					</td>
				</tr>			
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="7">
						<a class="addColumn" href="javascript:historySet('${_urlback }','../istanzeprocure/create.htm?codiceIstanza=${documentiistanza.istanza.id.codice}');" 
								title="<fmt:message key="label.nuovo" />">
						<label><fmt:message key="label.add.record.image" /></label></a>
					</td>					
				</tr>
			</tfoot>				
		</table>		
		
		<script type="text/javascript">

			// GESTISCE LA SELECT BOX VALIDO IN DOCUMENTI ISTANZA
			function changeValueValidoDocProcure(obj, id){			
				
				var opzione="";
				
			    if(document.getElementById(id).value!='null')
				{
					opzione=document.getElementById(id).value;
				}
				
				new Ajax.Request('${pageContext.request.contextPath}/istanzeprocure/ajaxChangeValueFieldValido.htm?codice='+obj+'&valido='+opzione, {
					method: 'post',	
					onSuccess: function(transport){
					  dijit.showTooltip(transport.responseText, dojo.byId(id));
					  setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
					},
					onFailure: function(transport){ 
					  $(id).innerHTML= transport.responseText;
					  $(id).className='error_checkbox';
					  applyStyle();
					  $(id).style.display='';
					  $(id).pulsate();
					  $(id).fade();
					 }						    		 
					});
				}

			
			
			function changeValueValidoDocDyn2(obj, id){
				
				var opzione="";
			    if(document.getElementById(id).value!='null')
				{
					opzione=document.getElementById(id).value;
				}
				
				new Ajax.Request('${pageContext.request.contextPath}/documentiistanza/ajaxChangeValueFieldValido.htm?codice='+obj+'&valido='+opzione, {
							method: 'post',	
							onSuccess: function(transport){
							  dijit.showTooltip(transport.responseText, dojo.byId(id));
							  setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
							},
							onFailure: function(transport){ 
							  $(id).innerHTML= transport.responseText;
							  $(id).className='error_checkbox';
							  applyStyle();
							  $(id).style.display='';
							  $(id).pulsate();
							  $(id).fade();
							 }						    		 
					});
				}
			
			
			function abilitaNecessarioProcureAllegati(obj, id){			
				
				new Ajax.Request('${pageContext.request.contextPath}/istanzeprocure/ajaxAbilitaDisabilitaNecessario.htm?codice='+escape(obj.value)+'&necessario='+obj.checked, {
							method: 'post',	
							onSuccess: function(transport){
							  dijit.showTooltip(transport.responseText, dojo.byId(id));
							  setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);	
							},
							onFailure: function(transport){ 
							  $(id).innerHTML= transport.responseText;
							  $(id).className='error_checkbox';
							  applyStyle();
							  $(id).style.display='';
							  $(id).pulsate();
							  $(id).fade();
							 }						    		 
					});
				}
			
			<%--
		 	1. Controlla se il flag presente può essere aggiornato. (Se il documento è verificato o valido non può essere settato come non presente)
		 	2. Se passa il controllo aggiorna il valore del flag su DB
		--%>
		function regolaAggiornaPresenteProcureAllegati(presente,valido,codice)
		{
			if(document.getElementById(presente).checked==false && document.getElementById(valido).value!='null')
			{
				alert('<fmt:message key="label.se_verificato_allora_presente"/>');
				document.getElementById(presente).checked=true;
				return false;
			}
			abilitaDisabilitaCheckbox(codice,presente,'','');
		}
	    //GESTICE IL CHECKBOX PRESENTE
 		function abilitaDisabilitaCheckbox(codice,presente,valido) {

  	       
 			var isPresentato ='';
 			
 			if(document.getElementById(presente)!=null)
 			{
 				var isPresentato = document.getElementById(presente).value;
 			}
 			new Ajax.Request(
						'${pageContext.request.contextPath}/istanzeprocure/ajxaChangeCheckboxvalue.htm?codice='+codice+'&presentato='+ isPresentato,
						{
							onSuccess : function(transport) {
								dijit.showTooltip(transport.responseText, dojo.byId(presente));
								setTimeout(function(){dijit.hideTooltip(dojo.byId(presente))},1000);
							},
							onFailure : function(transport) {
								$(result+codice).innerHTML = transport.responseText;
								$(result+codice).className = 'error_ajax_call';
								applyStyle();
								$(result+codice).style.display = '';
								$(result+codice).pulsate;
								({
									pulses : 2,
									duration : 1.0
								});
							}
						});
				}
			
			
		</script>		
	</fieldset>

	<!-- Documenti delle anagrafiche appartennti all'istanza (AnagrafeDocumenti) -->
	<br class="break" />
	<c:if test="${not empty documentiistanza.anagrafedocumentiDTOs}">
		<%
			String styleAnagrafe = "";
			String displayAnagrafeCollassato="";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCANAGR_DIV)).equals("1")) {
			    displayAnagrafeCollassato=" data-collassato='false' ";
			} else {
			    styleAnagrafe="collassato";
			}
		%>
		
		<fieldset class="collassabile <%=styleAnagrafe%>" <%= displayAnagrafeCollassato %> id="documenti_anagrafe_id">
		<legend onclick="salvaVisualizzazionePannello('<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCANAGR_DIV %>','documenti_anagrafe_id')"><fmt:message key="documentiistanza.label.documenti_anagrafiche.title" /></legend>	

		<div class="form-group">


			<table class="vbg-table">
				<thead>
					<tr>
						<th><fmt:message key="label.anagrafe" /> </th>
						<th><fmt:message key="label.documento" /> </th>
						<th><fmt:message key="label.allegato" /> </th>
						<th><fmt:message key="label.oggetto" /> </th>
						<th><fmt:message key="label.edit.record" /></th>
					</tr>
				</thead>
				<tbody>
					
					<c:forEach items="${documentiistanza.anagrafedocumentiDTOs}" var="anagrafedocumenti_var">
					<tr>
						<td>${anagrafedocumenti_var.transientTipoSoggettoAndRichiedente}</td>
						<td>${anagrafedocumenti_var.documento}</td>
						<td>${anagrafedocumenti_var.nomeFile}</td>
						<td>
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       						<jsp:param name="idElemento" value="docAnagrafe${anagrafedocumenti_var.id.codice}" />
	       						<jsp:param name="fileId" value="${anagrafedocumenti_var.codiceOggetto}" />
	   						</jsp:include>
	   					</td>
						<td>
							<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../anagrafe/viewDocumenti.htm?codice=${anagrafedocumenti_var.id.codice}')" title="<fmt:message key="label.edit.record" />">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>	
						</td>
					</tr>
					</c:forEach>
				</tbody>
				</table>	
	
			</div>
		</fieldset>
	</c:if>
	<!-- Documenti salvati nei campi dinamici -->	
		<c:if test="${not empty documentiistanza.documentiistanzaDynList}">
		
		<%
			String styleDyn = "";
			String displayDynCollassato="";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCDYN_DIV)).equals("1")) {
			    displayDynCollassato=" data-collassato='false' ";
			} else {
			    styleDyn="collassato";
			}
		%>
		
		<fieldset class="collassabile <%=styleDyn%>" <%= displayDynCollassato %> id="documenti_dyn_id">
		<legend onclick="salvaVisualizzazionePannello('<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCDYN_DIV %>','documenti_dyn_id')">
		<fmt:message key="documentiistanza.label.documenti_su_campi_dyn.title" /></legend>	

		<div class="form-group">
		
			<table class="vbg-table" id="dynDoctable">
				<thead>
					<tr>
					    <th><fmt:message key="label.nome" /><a onclick="sortTable('dynDoctable',0)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
					    <th><fmt:message key="label.documento" /><a onclick="sortTable('dynDoctable',1)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
					    <th><fmt:message key="label.note" /> </th>
					    <th><fmt:message key="label.richiesto" /> </th>
					    <th><fmt:message key="label.presente" /> </th>					
						<th><fmt:message key="label.valido" /> </th>
						<th><fmt:message key="label.data" /> </th>
						<th><fmt:message key="label.oggetto" /> </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${documentiistanza.documentiistanzaDynList}" var="documenti_dyn">
					<tr>
						<td>					
							<p class="nomeFile_cls">${documenti_dyn.oggetto.nomefile}</p>
						</td>		
						<td>
							${documenti_dyn.documento}
						</td>
						<td>
							${documenti_dyn.note}
						</td>					
						<td>
								<input id="chk_necessario${documenti_dyn.id.codice}" type="checkbox" value="${documenti_dyn.id.codice}" name="chk_necessario" ${documenti_dyn.necessario?'checked':''} onclick="abilitaNecessario(this, 'chk_necessario${documenti_dyn.id.codice}')"></input>
								<span id="result_necessario_${documenti_dyn.id.codice}" style="display: none"></span>					
						</td>
						<td>
								<input id="chk_presente${documenti_dyn.id.codice}" ${documenti_dyn.oggetto.id.codice!=null?'disabled checked':''} type="checkbox" value="${documenti_dyn.presente}" name="chk_presente" ${documenti_dyn.presente?'checked':''} onclick="regolaAggiornaPresenteDocIsta('chk_presente${documenti_dyn.id.codice}', 'select_valido_doc_ist${documenti_dyn.id.codice}',${documenti_dyn.id.codice})"></input>
								<span id="result_presente_${documenti_dyn.id.codice}" style="display: none"></span>					
						</td>
						<td>
							<select id="select_valido_doc_dyn2${documenti_dyn.id.codice}" class="elementoValidabile" name="controllook" onchange="changeValueValidoDocDyn2('${documenti_dyn.id.codice}','select_valido_doc_dyn2${documenti_dyn.id.codice}');">
								<option  value="null" ${documenti_dyn.controllook==null?'selected':''}>Da verificare</option>								
								<option  value="1" ${documenti_dyn.controllook==1?'selected':''}>Valido</option>
								<option  value="0" ${documenti_dyn.controllook==0?'selected':''}>Non valido</option>
							</select>
						</td> 
						<td>
								<input type="text" id="data_id${documenti_dyn.id.codice}"
									value="<fmt:formatDate value="${documenti_dyn.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />"
									name="documentiistanza.data" size="8" onblur="isValidDate('data_id${documenti_dyn.id.codice}',true);" onchange="changeData('data_id${documenti_dyn.id.codice}','${documenti_dyn.id.codice}');"
									/>
								<init:calendar imagePath="/images/cal.gif"
									idImage="caldata${documenti_dyn.id.codice}" idInput="data_id${documenti_dyn.id.codice}"
									textKey="label.calendar" javascriptAction="changeData('data_id${documenti_dyn.id.codice}','${documenti_dyn.id.codice}');" />			
								<span id="result_${documenti_dyn.id.codice}"	style="display: none"></span>			
						</td>
						<td>					
						<c:if test="${documenti_dyn.oggetto!=null}">						
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       						<jsp:param name="idElemento" value="docDyn${documenti_dyn.id.codice}" />
	       						<jsp:param name="fileId" value="${documenti_dyn.oggetto.id.codice}" />
	   						</jsp:include>
	   						<div class="applica_layer">							
								<c:if test="${inite:endsWith(documenti_dyn.oggetto.nomefile, '.pdf') && isAttivoLayerPDF}"> 
									 <a href="javascript:void(0);" class="applica_layer_id vbg-btn btn-applicalayer" data-codice-istanza="${documenti_dyn.istanza.id.codice}"
									 data-codice-oggetto="${documenti_dyn.oggetto.id.codice}" title="Applica informazioni protocollo come layer"></a>
								 </c:if>
								 <c:if test="${inite:endsWith(documenti_dyn.oggetto.nomefile, '.pdf') && isAttivoQr}">
									 <a href="javascript:void(0);" class="applica_qr_id vbg-btn btn-applicaqr" data-codice-istanza="${documenti_dyn.istanza.id.codice}"
									 data-codice-oggetto="${documenti_dyn.oggetto.id.codice}" title="Applica informazioni protocollo come layer">
									 <i class="fa fa-qrcode"></i></a>
								</c:if>									 
							 </div>
						</c:if>					 
						</td>
					</tr>	
					</c:forEach>					
				</tbody>
			</table>
		</div>
		</fieldset>
		
	</c:if>		

	<!-- Documenti salvati nei movimenti -->
	<c:if test="${not empty documentiistanza.movimentiallegatiDTOs}">
	<%
			String styleMovimenti = "";
			String displayMovimentiCollassato="";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCMOV_DIV)).equals("1")) {
			    displayMovimentiCollassato=" data-collassato='false' ";
			} else {
			    styleMovimenti="collassato";
			}
		%>
		
		<fieldset class="collassabile <%=styleMovimenti%>" <%= displayMovimentiCollassato %> id="documenti_movimenti_id">
		<legend onclick="salvaVisualizzazionePannello('<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCMOV_DIV %>','documenti_movimenti_id')"><fmt:message key="documentiistanza.label.documenti_su_movimenti.title" /></legend>	

		<div class="form-group">


			<table class="vbg-table" id="movimentiDocTable">
				<thead>
					<tr>
					    <th><fmt:message key="label.nome" /><a onclick="sortTable('movimentiDocTable',0)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
					    <th><fmt:message key="label.documento" /><a onclick="sortTable('movimentiDocTable',1)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
					    <th><fmt:message key="label.note" /> </th>
					    <th><fmt:message key="label.movimento" /><a onclick="sortTable('movimentiDocTable',3)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
					    <th><fmt:message key="label.data" /><a onclick="sortTable('movimentiDocTable',4)"><i class="fa fa-sort" aria-hidden="true"></i></a></th>
					    <th><fmt:message key="label.protocollo" /> </th>
					    <th><fmt:message key="label.amministrazione" /></th>
					    <th><fmt:message key="label.pubblica" /> </th>					
						<th><fmt:message key="label.valido" /> </th>
						<th><fmt:message key="label.oggetto" /> </th>
						<th><fmt:message key="label.edit.record" /> </th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${documentiistanza.movimentiallegatiDTOs}" var="movimentiallegati_var">
					<tr>
					    <td><p class="nomeFile_cls">${movimentiallegati_var.nomeFile}</p></td>
					    <td>${movimentiallegati_var.descrizione}</td>
					    <td>${movimentiallegati_var.note}</td>
					    <td>
					    <c:if test="${not empty movimentiallegati_var.descrizioneMovimento}">
					    	 <span title="[${movimentiallegati_var.tipomovimento}] - ${movimentiallegati_var.descrizioneMovimento}&#13;&#10;${movimentiallegati_var.responsabileMovimento}(<fmt:formatDate value="${movimentiallegati_var.dataMovimento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>)">
						       ${movimentiallegati_var.descrizioneMovimento}
					    </c:if>
					    <c:if test="${empty movimentiallegati_var.descrizioneMovimento}">		
					    <span title="[${movimentiallegati_var.tipomovimento}] - ${movimentiallegati_var.desctipomovimento}&#13;&#10;${movimentiallegati_var.responsabileMovimento}(<fmt:formatDate value="${movimentiallegati_var.dataMovimento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>)">
						       ${movimentiallegati_var.descrizioneEstesa}
					    </c:if>
					    </td>
					    <td data-valore-ordinabile="<fmt:formatDate value="${movimentiallegati_var.dataregistrazione}" pattern="yyyyMMdd"/>"><fmt:formatDate value="${movimentiallegati_var.dataregistrazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
					    <td>${movimentiallegati_var.protocolloAndData}</td>
					    <td>${movimentiallegati_var.amministrazione}</td>
					    <td>
					    	<input id="flagPubblicaId${movimentiallegati_var.id.codice }" type="checkbox"
					    		 onclick="changeCheckboxValue('flagPubblicaId${movimentiallegati_var.id.codice }','${pageContext.request.contextPath}/movimentiallegati/ajaxChangeFlagPubblica.htm?codice=${movimentiallegati_var.id.codice}&checkPermessi=true')" ${movimentiallegati_var.flagPubblica?'checked':''} />
						</td>					
						<td>
							<select id="select_valido_doc_mov${movimentiallegati_var.id.codice}" class="elementoValidabile" name="controllook" onchange="changeValueValidoMovAll('select_valido_doc_mov${movimentiallegati_var.id.codice}','${movimentiallegati_var.id.codice}');">
								<option  value="null" ${movimentiallegati_var.controllook==null?'selected':''}>Da verificare</option>								
								<option  value="1" ${movimentiallegati_var.controllook==1?'selected':''}>Valido</option>
								<option  value="0" ${movimentiallegati_var.controllook==0?'selected':''}>Non valido</option>
							</select>						
						</td>
						<td>
							<c:if test="${movimentiallegati_var.codiceMovimento!=null}">
								<jsp:include page="../includes/visualizzaOggetto.jsp" >
		       						<jsp:param name="idElemento" value="docMovimenti${movimentiallegati_var.id.codice}" />
		       						<jsp:param name="fileId" value="${movimentiallegati_var.codiceOggetto}" />
		   						</jsp:include>	   						
	   						</c:if>	
	   						<c:if test="${movimentiallegati_var.codiceOggetto==null && movimentiallegati_var.stcIdallegato!=null && movimentiallegati_var.stcIddocumento!=null}">
								<jsp:include page="../includes/visualizzaOggettoSTC.jsp" >
		       						<jsp:param name="codiceistanza" value="${movimentiallegati_var.codiceIstanza}" />
		       						<jsp:param name="codicemovimento" value="${movimentiallegati_var.codiceMovimento}" />
		   							<jsp:param name="stcIddocumento" value="${movimentiallegati_var.stcIddocumento}" />
		   							<jsp:param name="stcIdallegato" value="${movimentiallegati_var.stcIdallegato}" />		   							
		   							<jsp:param name="codiceRiferimento" value="${movimentiallegati_var.id.codice}" />
									<jsp:param name="contesto" value="<%=WebConstants.CONTESTO_ALLEGATI_MOVIMENTO%>" />
									<jsp:param name="indice" value="allegato_mov${movimentiallegati_var.id.codice}"/>	   								   										   							
		   						</jsp:include>							
							</c:if>						
						</td>
						<td><a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../movimentiallegati/view.htm?codice=${movimentiallegati_var.id.codice}')" title="<fmt:message key="label.edit.record" />">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
						</td>					
					</tr>
					</c:forEach>
				</tbody>
			</table>
		
		</div>
		
		
		</fieldset>
		
	</c:if>	
	
	   <script type="text/javascript">		

            // GESTISCE LA SELECT BOX VALIDO
			function changeValueValidoMovAll(obj, id){			
				var opzione="";
			    if(document.getElementById(obj).value!='null')
				{
			    	opzione=document.getElementById(obj).value;
				}
				
				new Ajax.Request('${pageContext.request.contextPath}/movimentiallegati/ajaxChangeValueFieldValido.htm?codice='+id+'&valido='+opzione, {
							method: 'post',	
							onSuccess: function(transport){
							  dijit.showTooltip(transport.responseText, dojo.byId(obj));
							  setTimeout(function(){dijit.hideTooltip(dojo.byId(obj))},1000);	
							},
							onFailure: function(transport){ 
							  $(id).innerHTML= transport.responseText;
							  $(id).className='error_checkbox';
							  applyStyle();
							  $(id).style.display='';
							  $(id).pulsate();
							  $(id).fade();
							 }						    		 
					});
				}

		</script>
		
<!--  Documenti delle autorizzazioni dell'istanza  -->
<%
			String styleAutorizzazioni = "";
			String displayAutorizzazioniCollassato="";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCAUT_DIV)).equals("1")) {
			    displayAutorizzazioniCollassato=" data-collassato='false' ";
			} else {
			    styleAutorizzazioni="collassato";
			}
		%>
		
		<fieldset class="collassabile <%=styleAutorizzazioni%>" <%= displayAutorizzazioniCollassato %> id="documenti_auts_id">
		<legend onclick="salvaVisualizzazionePannello('<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCAUT_DIV %>','documenti_auts_id')"><fmt:message key="documentiistanza.label.documenti_su_autorizzazioni.title" /></legend>	

		<div class="form-group">
				<table class="vbg-table">
					<thead>
						<tr>
							<th><fmt:message key="label.filename" /></th>
							<th width="10%"><fmt:message key="label.documenti_autorizzazioni.istanza" /></th>
							<th width="10%"><fmt:message key="label.documenti_autorizzazioni.numeroAutorizzazioni" /></th>
							<th><fmt:message key="label.movimento" /></th>
							<th width="10%"><fmt:message key="label.documenti_autorizzazioni.endo" /></th>
							<th width="10%"><fmt:message key="label.anagrafe" /></th>
							<th width="10%"><fmt:message key="label.documenti_autorizzazioni.procure" /></th>
							<th width="6%"><fmt:message key="label.azioni"/></th>
							<th><fmt:message key="label.edit.record" /></th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when
								test="${ not empty documentiistanza.autorizzazionedocumentiDTOs}">
								<c:forEach
									items="${documentiistanza.autorizzazionedocumentiDTOs}"
									var="documentoAutorizzazioni_var" varStatus="idx">
									<tr id="doc_autorizzazione_trow_id">
										<td>${ documentoAutorizzazioni_var.nomeFile }</td>
										<td>${ documentoAutorizzazioni_var.numeroIstanza }</td>
										<td><a href="javascript:historySet('${_urlback}','../autorizzazioni/viewAutorizzazione.htm?codiceIstanza=${documentoAutorizzazioni_var.codiceIstanza}&codice=${documentoAutorizzazioni_var.id.idautorizzazione}','')">${documentoAutorizzazioni_var.numeroAutorizzazione}</a></td>
 										<td>${ documentoAutorizzazioni_var.movimento }</td>
										<td>${ documentoAutorizzazioni_var.procedimento }</td>
										<td>${ documentoAutorizzazioni_var.nominativo } ${ documentoAutorizzazioni_var.nome}</td>
										<td>${ documentoAutorizzazioni_var.procure }</td>
										<c:choose>
											<c:when test="${not empty documentoAutorizzazioni_var.codiceDocumentiistanza}">
												<td style="background-color:${color};">
													<jsp:include page="../includes/visualizzaOggetto.jsp" >
							       						<jsp:param name="idElemento" value="docIstanza${documentoAutorizzazioni_var.codiceDocumentiistanza}" />
							       						<jsp:param name="fileId" value="${documentoAutorizzazioni_var.id.codiceoggetto}" />
							       						<jsp:param name="readonly" value="true" />
							   						</jsp:include>
												</td>
											</c:when>
											<c:when test="${not empty documentoAutorizzazioni_var.codiceMovimentiallegati}">
												<td style="background-color:${color};">
													<jsp:include page="../includes/visualizzaOggetto.jsp" >
							       						<jsp:param name="idElemento" value="docIstanza${documentoAutorizzazioni_var.codiceMovimentiallegati}" />
							       						<jsp:param name="fileId" value="${documentoAutorizzazioni_var.id.codiceoggetto}" />
							       						<jsp:param name="readonly" value="true" />
							   						</jsp:include>
												</td>
											</c:when>
											<c:when test="${not empty documentoAutorizzazioni_var.codiceIstanzeallegati}">
												<td style="background-color:${color};">
													<jsp:include page="../includes/visualizzaOggetto.jsp" >
							       						<jsp:param name="idElemento" value="docIstanza${documentoAutorizzazioni_var.codiceIstanzeallegati}" />
							       						<jsp:param name="fileId" value="${documentoAutorizzazioni_var.id.codiceoggetto}" />
							       						<jsp:param name="readonly" value="true" />
							   						</jsp:include>
												</td>
											</c:when>
											<c:when test="${not empty documentoAutorizzazioni_var.codiceAnagrafedocumenti}">
												<td style="background-color:${color};">
													<jsp:include page="../includes/visualizzaOggetto.jsp" >
							       						<jsp:param name="idElemento" value="docIstanza${documentoAutorizzazioni_var.codiceAnagrafedocumenti}" />
							       						<jsp:param name="fileId" value="${documentoAutorizzazioni_var.id.codiceoggetto}" />
							       						<jsp:param name="readonly" value="true" />
							   						</jsp:include>
												</td>
											</c:when>
											<c:when test="${not empty documentoAutorizzazioni_var.codiceIstanzeprocure}">
												<td style="background-color:${color};">
													<jsp:include page="../includes/visualizzaOggetto.jsp" >
							       						<jsp:param name="idElemento" value="docIstanza${documentoAutorizzazioni_var.codiceIstanzeprocure}" />
							       						<jsp:param name="fileId" value="${documentoAutorizzazioni_var.id.codiceoggetto}" />
							       						<jsp:param name="readonly" value="true" />
							   						</jsp:include>
												</td>
											</c:when>
										</c:choose>
										<td>
											<a class="dettaglioColumn"
											   href="javascript:historySet('${_urlback }','../documentiautorizzazione/list.htm?codiceautorizzazione=${ documentoAutorizzazioni_var.id.idautorizzazione }');"
											   title="<fmt:message key="label.edit.record" /> ${documentoAutorizzazioni_var.id.idautorizzazione}">
												<label><fmt:message key="label.edit.record.image" /></label>
											</a>
										</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="9" align="center"><fmt:message key="label.documenti_autorizzazioni.empty_list" /></td>
								</tr>
							</c:otherwise>
						</c:choose>
	
					</tbody>
				</table>
		</div>
	</fieldset>
	<!--  Documenti delle autorizzazioni dell'istanza  -->

	<%
			String styleCds= "";
			String displayCdsCollassato="";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCCDS_DIV)).equals("1")) {
			    displayCdsCollassato=" data-collassato='false' ";
			} else {
			    styleCds="collassato";
			}
		%>
		
		<fieldset class="collassabile <%=styleCds%>" <%= displayCdsCollassato %> id="documenti_cds_id">
		<legend onclick="salvaVisualizzazionePannello('<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCCDS_DIV %>','documenti_cds_id')"><fmt:message key="label.verbale_cds" /></legend>	

		<div class="form-group">
				<table class="vbg-table">
				<thead>
					<tr>
						<th width="80%"><fmt:message key="label.filename" /></th>
						<th width="6%"><fmt:message key="label.azioni"/></th>
						<th><fmt:message key="label.edit.record" /></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach
						items="${documentiistanza.cdsattiDTOs}"
						var="documentoCds_var" >
						<tr id="doc_autorizzazione_trow_id">
							<td>${ documentoCds_var.nomefile }</td>
							<td style="background-color:${color};">
								<jsp:include page="../includes/visualizzaOggetto.jsp" >
									<jsp:param name="idElemento" value="docIstanza${documentoCds_var.id.codice}" />
									<jsp:param name="fileId" value="${documentoCds_var.codiceoggetto}" />
									<jsp:param name="readonly" value="true" />
								</jsp:include>
							</td>
							<td>
								<a class="dettaglioColumn"
								   href="javascript:historySet('${_urlback }','../dcs/view.htm?codiceIstanza=${ documentoCds_var.id.codice }');"
								   title="<fmt:message key="label.edit.record" />${documentiistanza.istanza.id.codice}">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
		</div>
	</fieldset>	
	
	
		<!-- Crea la finstra di dialogo che avverte l'operatore che l'operazione che sta facendo potrebe durare alcuni minuti  
		     In quanto è un operazione di backup	
		-->
		<div style="display: none;" align="center" dojoType="dijit.Dialog" id="message_dialog_confirm" title="<fmt:message key="label.messaggio_conferma"/>">
	 	<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width:400px;height: 150px">
			<div align="center">
				<div align="center"><fmt:message key="javascript.confirm.backup_allegati_stc" /><div>
				<br class="clear" />
				<div style="float: none;" id="functions">
					<ul>
			    	<li><a href="javascript:historySet('${_urlback }','../stc/prepareCopiaAllegatoInLocale.htm?codiceIstanza=${documentiistanza.istanza.id.codice}&contesto=<%=WebConstants.CONTESTO_TUTTI%>','');" ><fmt:message key="button.ok" /></a></li>
					<li><a href="javascript:void(0)" onClick="dijit.byId('message_dialog_confirm').hide()"><fmt:message key="button.annulla" /></a></li>
			   		</ul>
			   	</div>
			</div>		
		</div>
		

		
		<script type="text/javascript">
			function confermaOperazioneBackupStc(divId){
				dijit.byId(divId).show();
			}
			function salvaVisualizzazionePannello(nomePreferenza, idPannello){
				visibile = document.getElementById(idPannello).classList.contains("collassato")? true : false;
				var valore = (visibile == true) ? 1 : 0;
				saveUserPreference(nomePreferenza, valore);
			}
			jQuery(document).ready(function() {
				hideXMLEst();
			});
			
			
			
			document.querySelectorAll('.applica_layer_id').forEach((el) => { 
				
				 el.addEventListener('click', x => {
					 let dialog = document.querySelector('#dialog-7');
					 let istId = el.dataset.codiceIstanza;
		             let oggId = el.dataset.codiceOggetto;		            
		             
		             applicaLayerConConferma(dialog,istId,oggId);
					 
				 });
				
			});
			
				
			const modalLayer = document.getElementById('dialog-7');
			const modalQr = document.getElementById('dialogQr');
			let isCheck = document.querySelector('#terms');
			let isCheckQr = document.querySelector('#accept');
			let bottoneChiudiAdv =document.getElementById('closeButtonmodalLayer');
			
			if(bottoneChiudiAdv){
				document.getElementById('closeButtonmodalLayer').addEventListener('click', (e)=>{
					modalLayer.close();		
				});
			}
			
			if(bottoneChiudiAdv){
				document.getElementById('closeButtonmodalQr').addEventListener('click', (e)=>{
					modalQr.close();		
				});
			}
			
			function applicaLayerConConferma(dialog,istId,oggId) {
			
				modalLayer.open();	
				document.getElementById('saveButtonmodalLayer').addEventListener('click', (e)=>{
					if(isCheck.checked){
						doHref('applicaLayerProtocolloPdf.htm?codiceIstanza='+istId+'&codiceOggetto='+oggId,'');
					}
				});				
			}		
			
			
			document.querySelectorAll('.applica_qr_id').forEach((el) => { 
				
				 el.addEventListener('click', x => {
					
					 let istId = el.dataset.codiceIstanza;
		             let oggId = el.dataset.codiceOggetto;		            
		             
		             applicaQrCodeConConferma(istId,oggId);
					 
				 });				
			});
			
			function applicaQrCodeConConferma(istId,oggId){
				
				modalQr.open();
				document.getElementById('saveButtonmodalQr').addEventListener('click', (e)=>{
					if(isCheckQr.checked){
						doHref('applicaQrcodePdf.htm?codiceIstanza='+istId+'&codiceOggetto='+oggId,'');
					}
				});	
				
			}
			
		</script>
	
</body>
</html>