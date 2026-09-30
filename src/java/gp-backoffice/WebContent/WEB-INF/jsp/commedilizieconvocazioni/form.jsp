<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:if test="${commedilizieconvocazioni.id.codice==null}">
			<fmt:message key="label.nuova_convocazione" />
		</c:if> 
		<c:if test="${commedilizieconvocazioni.id.codice!=null}">
			<fmt:message key="label.modifica_convocazione" />
		</c:if>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:if test="${commedilizieconvocazioni.id.codice==null}">
			<fmt:message key="label.nuova_convocazione" />
		</c:if> 
		<c:if test="${commedilizieconvocazioni.id.codice!=null}">
			<fmt:message key="label.modifica_convocazione" />
		</c:if>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
	
	<div class="vbg-form">
		<fieldset>
			<legend><fmt:message key="label.dettaglio_commissione_edilizia"/></legend>
				<div class="parametriDiv">
					<div class="etichetta">
						<div><fmt:message key="label.numero_commissione" />:</div>
						<div><fmt:message key="label.descrizione" />:</div>
					</div>		
					<div class="parametro">       		 	
						<div>${commedilizieconvocazioni.commissioniedilizieT.numprotocollo}</div>
						<div>${commedilizieconvocazioni.commissioniedilizieT.descrizione}</div>
					</div>
				</div>
		</fieldset>
	</div>	
	
		<br class="clear" />
		<spring-form:form commandName="commedilizieconvocazioni" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commedilizieconvocazioni" />
		    </jsp:include>
		    
		    <div class="vbg-form">	
		    	<fieldset>
		    		<legend><fmt:message key="label.convocazione"/></legend>		    	   
			    	<div class="form-group">
			    		<label><fmt:message key="label.data_convocazione" /></label>
			    		<spring-form:input id="dataconvocazione_id" path="dataconvocazione" size="8" onblur="isValidDate(this,true);" cssClass="required"/>
						<init:calendar imagePath="/images/cal.gif" idImage="caldataconvocazione" idInput="dataconvocazione_id" textKey="label.calendar"/>					
						<div id="errore_dataconvocazione_id" class="error validation-feedback"><fmt:message key="commedilizieconvocazioni.label.dataconvocazione.required"/> </div>
			    	</div>
			    	<div class="form-group">
			    		<label><fmt:message key="label.ora_convocazione" /></label>
			    		<spring-form:input id="oraconvocazione_id" path="oraconvocazione" size="8" maxlength="5" onblur="isValidOra(this,true);" cssClass="required"/>					
						<div id="errore_oraconvocazione_id" class="error validation-feedback"><fmt:message key="commedilizieconvocazioni.label.oraconvocazione.required"/></div>
			    	</div>
		    	</fieldset>	
		    </div>
		    
			
			<script type='text/javascript'>				
				
				vbg.ready(() => {
					
					const buttonInserisci = document.querySelector('#btn_inserisci');
					const buttonModifica = document.querySelector('#btn_aggiorna');									
					
					if(buttonInserisci){
						buttonInserisci.addEventListener('click', function() {
							if( campiObbligatoriSpecificati() ){
								doSubmit('insert.htm','',document.inviodati);
							}
						});
					}
					
					if(buttonModifica){
						buttonModifica.addEventListener('click', function(){
							if( campiObbligatoriSpecificati() ){
								doSubmit('update.htm','',document.inviodati);
							}
						});
					}
					
					let cal = document.getElementById('caldataconvocazione');
					
					
					
					cal.addEventListener('click',function(){
						console.log(cal);
						
						let targetErrore = document.getElementById('errore_dataconvocazione_id');
						
						targetErrore.hide();
						document.getElementById('dataconvocazione_id').classList.remove('input-error');
					});
					
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
									console.log(x.id + ': ' + x.value)
									campiOk = false;
									return;
								}
							}				
						);
						
						return campiOk;
					}					
					
				});
				
				
			</script>	
		</spring-form:form>
	</div>
	<div class="bottoni">		
		<c:if test="${commedilizieconvocazioni.id.codice==null}">
			<a id="btn_inserisci" class="btn btn-primary"><fmt:message key="button.insert" /></a>
		</c:if>
		<c:if test="${commedilizieconvocazioni.id.codice!=null}">
			<a id="btn_aggiorna" class="btn btn-primary"><fmt:message key="button.update" /></a>
			<a class="btn btn-primary" href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
		</c:if>
		<a class="btn btn-secondary" href="javascript:doHref('../commissioniediliziet/view.htm?codice=${commedilizieconvocazioni.commissioniedilizieT.id.codice}','')"><fmt:message key="button.back" /></a>		
	</div>
</body>
</html>