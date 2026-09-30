<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="it.gruppoinit.pal.gp.core.domain.web.IstanzeAccessoAttiTCommand" %>
<%@ page import="java.net.URLEncoder" %>
<%@page import="org.springframework.security.userdetails.UserDetails"%>
<%@page import="org.springframework.security.Authentication"%>
<%@page import="org.springframework.security.context.SecurityContextHolder"%>
<%@page import="org.springframework.security.context.SecurityContext"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page import="java.util.Date" %>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.accesso_atti.title" />
	</title>
</head>
<body>
	<span class="titoloPagina">
		<fmt:message key="label.accesso_atti.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzeaccessoattit/view"/>
		<jsp:param name="qs" value="codice%3D${istanzeaccessoattit.entity.id.codice}"/>
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${istanzeaccessoattit.entity.istanze.id.codice }</c:param>
	</c:import>
	<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="istanzeaccessoattit" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="istanzeaccessoattit" />
		    </jsp:include>
		    <spring-form:hidden id="codiceoperatore_hidden" path="entity.responsabili.id.codice" />
		    <spring-form:hidden id="codiceistanza_hidden" path="entity.istanze.id.codice" />
		    
		    <div id="form" class="vbg-form">
				<fieldset>
				<legend><fmt:message key="label.dati_generali"/></legend>	
		    
   				<div class="form-group">
		            <label>* <fmt:message key="label.descrizione_fascicolo" /></label>
		            <spring-form:input id="descrizioneFascicolo_id" path="entity.descrizioneFascicolo" size="50" />
					<spring-form:errors path="entity.descrizioneFascicolo" cssClass="error"/>	            
		        </div>
   				<div class="form-group">
		            <label>* <fmt:message key="label.data_inizio" /></label>		            
					<spring-form:input id="datainizio_id" path="entity.datainizio" size="10" maxlength="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="caldataInizio" idInput="datainizio_id" textKey="label.calendar"/>
					<spring-form:errors path="entity.datainizio" cssClass="error"/>       
		        </div>		        
		
   				<div class="form-group">
		            <label>* <fmt:message key="label.data_fine" /></label>		            
					<spring-form:input id="datafine_id" path="entity.datafine" size="10" maxlength="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="caldataFine" idInput="datafine_id" textKey="label.calendar"/>
					<spring-form:errors path="entity.datafine" cssClass="error"/>       
		        </div>					

   				<div class="form-group">
		            <label><fmt:message key="label.pubblica" /></label>		            
					<spring-form:checkbox id="flgpubblica_id" path="entity.flgPubblica"/>	      
		        </div>		
				<div class="form-button">
					<!-- INSERISCI -->
					<c:if test="${istanzeaccessoattit.displayMode eq istanzeaccessoattit.displayConstants.NEW }">
						<a class="btn btn-primary"  href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert"/></a>
					</c:if>
					
					<c:if test="${istanzeaccessoattit.displayMode eq istanzeaccessoattit.displayConstants.VIEW }">
						<!-- SALVA -->
						<a class="btn btn-primary" href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
						<!-- ELIMINA -->					
						<a class="btn btn-secondary" href="javascript:cancellaIstanzaAccessoAttiTConfirm()"><fmt:message key="button.delete" /></a>
						
						
						<vbg-modal id="vbgPopupCancellazione" data-auto-open='false'>
							<div slot='body'>
								<h1>
					               	<fmt:message key="label.conferma_cancellazione" />
					             </h1>
					             <p>
					             	<input type="checkbox" id="cancellaistanzaaccessoattitchk_id" onClick="showHideDiv('doDeleteId')" />
									<label for="cancellaistanzaaccessoattitchk_id">
										<fmt:message key="label.messaggio_cancellazione_istanza_accesso_atti_per_operatore">
											<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
											<fmt:param>${istanzeaccessoattit.entity.id.codice}</fmt:param>
										</fmt:message>
									</label>
					             </p>                     
							</div>
							<div slot='footer'>		
								<a class="btn btn-primary" style="display:none;" id="doDeleteId" href="javascript:doSubmit('delete.htm', '', document.inviodati)"><fmt:message key="button.delete" /></a>								
								<a class="btn btn-secondary" id="closeModalCancellazione"><fmt:message key="button.close"/></a>            
							</div>
						</vbg-modal>
					</c:if>
					<a class="btn btn-secondary" href="javascript:historyBack('')"><fmt:message key="button.back" /></a>
				</div>
				
				</fieldset>
				
				
				<c:if test="${istanzeaccessoattit.displayMode eq istanzeaccessoattit.displayConstants.VIEW }">
				
				
					<fieldset>
					<legend><fmt:message key="label.lista_istanze_accesso_atti"/></legend>	
					
					<c:choose>
						<c:when test="${not empty istanzeaccessoattids}">					
								<table class="vbg-table">
									<thead>
										<tr>
											<th><fmt:message key="label.numeroistanza"/></th>
											<th><fmt:message key="label.data_presentazione"/></th>
											<th><fmt:message key="label.richiedente"/></th>
											<th><fmt:message key="label.flag_doc_no_validi_accesso_atti"/></th>
											<th><fmt:message key="label.elimina"/></th>
										</tr>
									</thead>
													
									<tbody>
									<c:forEach items="${istanzeaccessoattids}" var="riga">
										<tr>
											<td>${riga.istanze.numeroistanza}</td>
											<td><fmt:formatDate value="${riga.istanze.data}" pattern="<%= WebConstants.DATE_FORMAT_PATTERN%>" /></td>
											<td>${riga.istanze.richiedente.descrizioneRichiedente}</td>
											<td>
													<select id="flgVisualizzaDocId${a.index}" data-id="${riga.id.codice}" onchange="modificaVisibilita(this);">
														<option <c:if test="${ riga.flgVisualizzaDoc eq 0 }"> selected </c:if> value="0"><fmt:message key="label.accesso_atti.solo_i_documenti_validi"/></option>
														<option <c:if test="${ riga.flgVisualizzaDoc eq 2 }"> selected </c:if> value="2"><fmt:message key="label.accesso_atti.tutti_i_documenti_validi_o_da_verificare"/></option>
														<option <c:if test="${ riga.flgVisualizzaDoc eq 1 }"> selected </c:if> value="1"><fmt:message key="label.accesso_atti.tutti_i_documenti"/></option>														
													</select>								
											
											</td>
											<td>
												<a class="eliminaRiga" style="float: none;" href="javascript:doHref('deleteAccessoAttiD.htm?codice=${riga.id.codice}','<fmt:message key="javascript.confirm.delete" />')" title="<fmt:message key="label.elimina"/> ${riga.id.codice}" >
													<label><fmt:message key="label.elimina.image" /></label>
												</a>								
											</td>
										</tr>
									</c:forEach>
								</tbody>
								</table>
						</c:when>		
						<c:otherwise>
								<fmt:message key="label.istanze_accesso_atti_non_presenti"/>
						</c:otherwise>
					</c:choose>
														
					<div class="form-button" style="margin-top: 15px;">
							<a class="btn btn-primary" href="javascript:historySet('${_urlback}','..%2Fistanze/searchIstanze.htm?tipoRicerca=<%=WebConstants.SEARCH_ISTANZE_ACCESSO_ATTI %>&codiceIstanzaAccessoAtti=${istanzeaccessoattit.entity.id.codice}','')"><fmt:message key="button.aggiungi_istanza_accesso_atti"/></a>
							<a class="btn btn-secondary" href="javascript:historySet('${_urlback}','..%2Fistanzeaccessoattit/listLogs.htm?codice=${istanzeaccessoattit.entity.id.codice}','')"><fmt:message key="button.visualizza_accessi_atti"/></a>
					</div>	
				</fieldset>
					
				<fieldset>
					<legend><fmt:message key="label.anagrafiche_accesso_atti"/></legend>		

					<div class="form-group">
			            <label><fmt:message key="label.anagrafe"/></label>
			            <script type='text/javascript'>
								function setHiddenFieldAnagrafe(inputField, listItem) {
									var a = listItem.id;
									nuovoAnagrafe(a);
								}				
							</script>
							<jsp:include page="../includes/autocompletergenerico.jsp" >
								<jsp:param name="idElemento" value="anagrafe"/>
								<jsp:param name="propertyPath" value="anagrafe"/>
								<jsp:param name="pathPropertyDescription" value="anagrafe.descrizioneRichiedente"/>
								<jsp:param name="pathPropertyCode" value="anagrafe.id.codice"/>
								<jsp:param name="autocompleterAjax" value="findAnagrafe.htm"/>
								<jsp:param name="afterUpdateElement" value="setHiddenFieldAnagrafe"/>
								<jsp:param name="titleKey" value="label.anagrafe" />
							</jsp:include>          
			        </div>

					
					<div id="messaggioErrore" class="error_header" style="display: none;"></div>
			
					<div id="dettaglioGruppiAnagrafe">&nbsp;<img src='${pageContext.request.contextPath}/images/spinner.gif'/></div>
						
					</fieldset>
				</c:if>
			</div>
		</spring-form:form>
				
		
	</div>
	
	<script type="text/javascript">

    	const modalCancellazione = document.getElementById('vbgPopupCancellazione');
	
    	
    	
		function cancellaIstanzaAccessoAttiTConfirm(){
			
			modalCancellazione.open();
		}
	
		document.getElementById('closeModalCancellazione').addEventListener('click', (e)=>{
			modalCancellazione.close();
		});
    	
    	
    	
		var modificaVisibilita = async function(obj){
			
			
			let urlmodifica = '${pageContext.request.contextPath}/istanzeaccessoattit/ajaxChangeFlagVisualizzaDoc.htm';
			
			vbg.mostraModalCaricamento();
			let formData = new FormData();
		    formData.append('codice', obj.getAttribute('data-id'));
		   	formData.append('flgVisualizzaDoc', obj.value);
			let response = await fetch(urlmodifica, {
	            method: "POST",
	            cache: "no-cache",
	            body: formData                
			});				
			
			let messaggio = await response.text();
			
			vbg.nascondiModalCaricamento();
			
			if (response.status !== 200) {
	            let errore = await response.text();
	            console.error(errore);
	            throw errore;
	        }
			
		}
		
		var visualizzaDettaglio = function(codiceAnagrafe){
			
			var jhqrtPr = jQuery.ajax({
				url: '${pageContext.request.contextPath}/istanzeaccessoattit/ajaxDettaglioIstanzaAccessoAttiAnagrafe.htm?codiceistanzaaccessoattit=${istanzeaccessoattit.entity.id.codice}&codiceanagrafeInserito='+codiceAnagrafe,
				context: document.body,
				cache: false,
				dataType: "html",
				success: function(data, textStatus, jqXHR){
					if(data){
						document.getElementById('dettaglioGruppiAnagrafe').innerHTML = data;
						document.getElementById('dettaglioGruppiAnagrafe').style.display='block';
					}
				}
			});
		}
		
		
		
		function nuovoAnagrafe(codiceAnagrafe){
			//elimino l'eventuale messaggio di errore se presente
			
			document.getElementById('messaggioErrore').style.display='none';
			if (codiceAnagrafe != ''){
				var jhqrtPr = jQuery.ajax({
					url: "${pageContext.request.contextPath}/istanzeaccessoattit/ajaxAssegnaIstanzaAccessoAttiAnagrafe.htm?codiceIstanzaAccessoAttiT=${istanzeaccessoattit.entity.id.codice}&codiceAnagrafe="+codiceAnagrafe,
					context: document.body,
					cache: false,
					dataType: "html",
					success: function(data, textStatus, jqXHR){
						verificaErroreoSuccesso(data, codiceAnagrafe);
						azzeraAutoCompleter();
					}
				});
			}
		}
		
		function azzeraAutoCompleter(){
			document.getElementById('anagrafe_id').value = '';
			document.getElementById('anagrafe_hidden').value= '';
		}
		
		function eliminaRiga(codiceIstanzaAccessoAttiT, codiceAnagrafe){
			if (confirm('<fmt:message key="javascript.confirm.delete"/>')){
				//elimino l'eventuale messaggio di errore se presente
				document.getElementById('messaggioErrore').style.display='none';
				
				var jhqrtPr = jQuery.ajax({
					url: "${pageContext.request.contextPath}/istanzeaccessoattit/ajaxEliminaIstanzaAccessoAttiAnagrafe.htm?codiceistanzaaccessoattit="+codiceIstanzaAccessoAttiT+"&codiceanagrafe="+codiceAnagrafe,
					context: document.body,
					cache: false,
					dataType: "html",
					success: function(data, textStatus, jqXHR){
						verificaErroreoSuccesso(data, '');
					}
				});
			}
		}
		
		
		
		
		function cancellaErrore(){
			document.getElementById('messaggioErrore').style.display='none';
			document.getElementById('messaggioErrore').innerHTML = '';
		}
		
		function verificaErroreoSuccesso(data, codiceAnagrafe){
			// verifica errori o altro e aggiorna
			if(data == 'OK'){
				visualizzaDettaglio(codiceAnagrafe);
			}else{
				document.getElementById('messaggioErrore').innerHTML = data;
				document.getElementById('messaggioErrore').style.display='block';
			}
		}
		
		vbg.ready(() => {	
			visualizzaDettaglio('');
		});
		
	</script>
	
	
</body>
</html>