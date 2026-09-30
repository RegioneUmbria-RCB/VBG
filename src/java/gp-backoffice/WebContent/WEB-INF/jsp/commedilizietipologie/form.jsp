<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="it">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizietipologie.id.codice==null}">
			<fmt:message key="commedilizietipologie.label.nuovo_commedilizietipologie.title" />
		</c:if> 
		<c:if test="${commedilizietipologie.id.codice!=null}">
			<fmt:message key="commedilizietipologie.label.dettaglio_commedilizietipologie.title" />
		</c:if>
	</title>
	<style>
		.btn-foot {
			text-align: right !important;
		}
	</style>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizietipologie.id.codice==null}">
			<fmt:message key="commedilizietipologie.label.nuovo_commedilizietipologie.title" />
		</c:if> 
		<c:if test="${commedilizietipologie.id.codice!=null}">
			<fmt:message key="commedilizietipologie.label.dettaglio_commedilizietipologie.title" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="commedilizietipologie" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizietipologie" />
		    </jsp:include>
		    
		    <div class="vbg-form">
		    <fieldset>
		    <legend><fmt:message key="label.commissioni_edilize_tipologia_commissione" /></legend>
			    	<input id="commedilizietipologie_id" type="hidden" data-comedilizie-codice="${commedilizietipologie.id.codice}" value="${commedilizietipologie.id.codice}"/>
			    	<div class="form-group">
			    		<label><fmt:message key="label.descrizione" /></label>
			    		<spring-form:input id="descrizione_id" path="descrizione" size="70" cssClass="required" />
						<%-- <spring-form:errors path="descrizione" cssClass="error"/> --%>
						<div id="errore_descrizione_id" class="error validation-feedback"><fmt:message key="commedilizietipologie.label.descrizione.obbligatorio"/></div>
			    	</div>
			    	
			    	<div class="form-group">
			    		<label><fmt:message key="commedilizietipologie.label.flagdisabilita" /></label>
			    		<spring-form:checkbox id="flagDisabilita_id" path="flagDisabilita" value="1" />
						<fmt:message key="commedilizietipologie.label.flagdisabilita.help" />
						<spring-form:errors path="flagDisabilita" cssClass="error"/>
			    	</div>
			    	
			    	<div class="form-group">
			    		<label for="amministrazioni_id"><fmt:message key="label.amministrazione" /></label> 		    		
			    		<spring-form:select path="amministrazione.id.codice" id="amministrazioni_id">
							<spring-form:options items="${amministrazioniList}" itemLabel="descrizioneEstesa" itemValue="id.codice" />
						</spring-form:select>
										
			    	</div>
		    	</fieldset>
		    	<fieldset>
		    		<legend><fmt:message key="label.areariservata" /></legend>
			    	<div class="form-group">
			    		<label title="<fmt:message key="commedilizietipologie.label.flagUploaddoc" />" ><fmt:message key="commedilizietipologie.label.flagUploaddoc" /></label>
			    		<spring-form:checkbox id="flagUploaddoc_id" path="flagUploadDocParere" value="1" />					
						<spring-form:errors path="flagDisabilita" cssClass="error"/>					
						<div class="input-help">
							<fmt:message key="commedilizietipologie.label.flagUploaddoc.help"/>
						</div>			    		
			    	</div>
		    	</fieldset>
		    </div> 
		    
			
		</spring-form:form>
	</div>
	<div>		
		<c:if test="${commedilizietipologie.id.codice==null}">
			<a id="btn_inserisci" class="btn btn-primary" ><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${commedilizietipologie.id.codice!=null}">
			<a id="btn_aggiorna" class="btn btn-primary"><fmt:message key="button.update" /></a>
			<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
		</c:if>
		<a class="btn btn-secondary" href="javascript:historyBack('');"><fmt:message key="button.back" /></a>		
	</div>
	
	<c:if test="${(commedilizietipologie.id.codice != null)}">
	</br>
	</br>
	<div class="vbg-form">
		<fieldset>
			<legend><fmt:message key="commedilizietipologie.label.tipologiedett" /></legend>
				<table class="vbg-table">
					<thead>
						<th><fmt:message key="label.tipimovimento"/></th>
						<th><fmt:message key="label.elimina"/></th>
					</thead>
					<tbody>
						<c:forEach items="${commedilizietipologie.commedilizieTipologiedetts}" var="tipologiedett_var" >
							<tr>
								<td> ${tipologiedett_var.tipimovimento.movimento} (${tipologiedett_var.tipimovimento.id.tipomovimento})</td>
								<td data-id-tipomov="${tipologiedett_var.tipimovimento.id.tipomovimento}">									
									<i class="fa fa-times vbg-link fa-lg togli-pratica azione"></i>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<td></td>
							<td class="btn-foot">
								<a href="javascript:doSubmit('createDettaglio.htm?codice=${commedilizietipologie.id.codice}','',document.inviodati)">
									<i class="fa fa-plus-circle"></i>&nbsp;<fmt:message key="button.new" />
								</a>
							</td>
						</tr>
					</tfoot>
				</table>
					
		</fieldset>
	</div>
	</c:if>
	
	
	<c:if test="${(commedilizietipologie.id.codice != null)}">
	</br>
	</br>
	<div class="vbg-form">
		<fieldset>
			<legend><fmt:message key="label.ruoli" /></legend>
			
				<table width="100%">       
					<tr>
						<td>		     
							<input id="ruolo_id" name="ruolo" class="searchbox" size="40" onkeydown="return searchAll(this,event)" />
							<init:autocompleter methodAjax="findRuoli.htm" afterUpdateElement="setHiddenFieldRuolo"  idHidden="ruolo_id_hidden" idInput="ruolo_id" inputTitleKey="" minChars="1" />
							<input type="hidden" id="ruolo_id_hidden" name="ruolo_id_hidden" />    	
					 	</td>
					</tr>
					<tr>
						<td><div id="messaggioErrore" class="error_header" style="display:none;"></div></td>
					</tr>
				</table>
				<table class="vbg-table">
					<thead>
						<th><fmt:message key="label.ruoli"/></th>
						<th><fmt:message key="label.elimina"/></th>
					</thead>
					<tbody>
						<c:forEach items="${commedilizietipologie.ruolis}" var="ruolis_var" >
							<tr>
								<td> ${ruolis_var.ruolo.ruolo}</td>
								<td data-id-ruolo="${ruolis_var.ruolo.id.codice}">							
									<i class="fa fa-times vbg-link fa-lg togli-ruolo azione"></i>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					
				</table>
					
		</fieldset>
	</div>
	</c:if>
	
	<script type='text/javascript'>
	

	
	
			vbg.ready(() => {
				
				document.querySelector('#errore_descrizione_id').style.display = 'none';
				
				document.querySelectorAll('.togli-pratica').forEach(
						x => {
							
							x.title='<fmt:message key="label.elimina" />';
							
							x.addEventListener('click', (e) => {
								e.preventDefault();
								
								let codice = document.getElementById('commedilizietipologie_id').dataset.comedilizieCodice;
								let tipomovimento = x.parentElement.dataset.idTipomov;								
								
								doHref('deleteDettaglio.htm?codice='+ codice+'&tipomovimento='+tipomovimento,'<fmt:message key="javascript.confirm.delete" />');
							});
						}
					);
				
				let btnInserisci = document.querySelector('#btn_inserisci');
				let btnAggiorna = document.querySelector('#btn_aggiorna');
				
				if(btnInserisci){
					btnInserisci.addEventListener('click', function() {
						
						if(campiObbligatoriSpecificati() ){
							
							doSubmit('insert.htm','',document.inviodati);
						}
					});
				}
				
				if(btnAggiorna){
					btnAggiorna.addEventListener('click', function() {
						
						if(campiObbligatoriSpecificati() ){
							
							doSubmit('update.htm','',document.inviodati);
						}
					});
				}
								
				document.querySelectorAll('.required').forEach(
						x => { 
							
							x.required = true;
							
							let targetErrore = document.getElementById('errore_' + x.id);
							
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
				
				function campiObbligatoriSpecificati(){
					
					let campiOk = true;
					
					document.querySelectorAll('.required').forEach(
						x => {
							if (x.value === ''){
								x.dispatchEvent(new Event('focusout'));								
								campiOk = false;
								return;
							}
						}				
					);
					
					return campiOk;
				}
				
				
				document.querySelectorAll('.togli-ruolo').forEach(
						x => {
							
							x.title='<fmt:message key="label.elimina" />';
							
							x.addEventListener('click', (e) => {
								e.preventDefault();
								eliminaRuoli(x.parentElement.dataset.idRuolo);
													
								});
						}
					);

				
				
				
			});
			
				
			
			
			<%-- ruoli begin --%>
			
			
			function nuovoRuolo(codiceRuolo) {
				  // elimino l'eventuale messaggio di errore se presente
				 eliminaMessaggioErrore();

				  if (codiceRuolo != "") {
					vbg.mostraModalCaricamento();
				    var jhqrPr = jQuery.ajax({
				      url:
				        "${pageContext.request.contextPath}/commedilizietipologie/ajaxAssegnaRuoli.htm?idtipologia=${commedilizietipologie.id.codice}&codiceRuolo=" +
				        codiceRuolo,
				      context: document.body,
				      cache: false,
				      dataType: "html",
				      success: function (data, textStatus, jqXHR) {
				        verificaErroreoSuccesso(data, codiceRuolo, "ruolo");
				      },
				      error: gestisciErrore,
				    });
				  }
				}

				function eliminaRuoli(codiceRuolo) {
					
				  if (confirm('<fmt:message key="javascript.confirm.delete" />')) {
				    // elimino l'eventuale messaggio di errore se presente
				    eliminaMessaggioErrore();
				    vbg.mostraModalCaricamento();
				    
				    var jhqrPr = jQuery.ajax({
				      url:
				        "${pageContext.request.contextPath}/commedilizietipologie/ajaxEliminaRuoli.htm?idtipologia=${commedilizietipologie.id.codice}&codiceRuolo=" +
				        codiceRuolo,
				      context: document.body,
				      cache: false,
				      dataType: "html",
				      success: function (data, textStatus, jqXHR) {
				        verificaErroreoSuccesso(data, "", "ruolo");
				      },
				      error: gestisciErrore,
				    });
				  }
				}
				
				
				function verificaErroreoSuccesso(data, codice, identificativo) {
				    // verifica errori o altro e aggiorna
				    
				    
				    if (data.toLowerCase() == "ok") {
				        document.location.reload();
				        return;
				    } 
				    
				    vbg.nascondiModalCaricamento();
				}
				
				
				function gestisciErrore(jqXHR, textStatus, errorThrown) {
					  console.error([jqXHR, textStatus, errorThrown]);

					  let divErrore = document.querySelector("#messaggioErrore");
				      let testo = document.createTextNode(data);
				      divErrore.appendChild(testo);
				      divErrore.show();
				      document.querySelector("#messaggioErrore").show();						  
				      vbg.nascondiModalCaricamento();
					}
				
				
				
				function setHiddenFieldRuolo(inputField, listItem) {
					  var a = listItem.id;
					  nuovoRuolo(a);
				}
				
				
				
				function eliminaMessaggioErrore(){
				    
				    if(document.querySelector("#messaggioErrore") != null){
					 document.querySelector("#messaggioErrore").innerText = '';
					 document.querySelector("#messaggioErrore").hide();
				    }
				    
				   
				}
				<%-- ruoli end --%>
			
			</script>	
</body>
</html>