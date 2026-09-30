<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<fmt:message key="label.commissioni_conferenze" />
	</title>
	<script type="module" src="${pageContext.request.contextPath}/scripts/custom-components/vbg-modal.js?<%=vJS %>" defer></script>
	<style>
		.btn-foot {
			text-align: right !important;
		}
		
		#form {
			display: flex;
			flex-wrap: wrap;
		}
		
		#form > fieldset {
			flex-grow: 1;
			min-width: 400px
		
		}
	</style>
</head>
<body>
    <c:set scope="page" value="${sizeConvocazionis}" var="sizeList"></c:set>
	<span class="titoloPagina">
		<fmt:message key="label.commissioni_conferenze" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<div id="subcontent">
		<spring-form:form commandName="commissione" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="commissione" />
		    </jsp:include>
		    <jsp:include page="../includes/history.jsp">
	            <jsp:param name="path" value="../commissioniediliziet/view" />
	        </jsp:include>
			 <div id="form" class="vbg-form">
			 <fieldset>
			 	<legend><fmt:message key="label.commissioni_conferenze.dati_generali" /></legend>
			 
			 	<div class="form-group">
			 		<label><fmt:message key="label.numero_commissione" /></label>
			 		<spring-form:input id="numprotocollo_id" path="numeroProtocollo" size="30" maxlength="60" cssClass="required" />
			 		<div id="errore_numprotocollo_id" class="error validation-feedback" ><fmt:message key="commissioniediliziet.label.errore.numeroprotocollo.required" /></div>
			 	</div>
			 	<div class="form-group">
			 		<label><fmt:message key="label.descrizione" /></label>
			 		<spring-form:input id="descrizione_id" path="descrizione" size="70" cssClass="required" />
			 		<div id="errore_descrizione_id" class="error validation-feedback" ><fmt:message key="commissioniediliziet.label.errore.descrizione.required" /></div>
			 	</div>
			 	<c:if test="${commissione.showODG eq true }">
					<div class="form-group">
				 		<label><fmt:message key="label.oggetto" /></label>
				 		<spring-form:textarea id="odg_id" path="odg" cols="70" />
				 	</div>			 	
			 	</c:if>
			 	<div class="form-group">
			 		<label><fmt:message key="label.note" /></label>
			 		<spring-form:textarea id="note_id" path="note" cols="70" />
			 	</div>
			 	<div class="form-group">
			 		<label><fmt:message key="label.stato" /></label>
			 		<spring-form:select id="flagaperta_id" path="aperta">
					 	<c:if test="${commissione.id eq null }">
					 		<option value="1" selected><fmt:message key="label.aperta" /></option>
					 	</c:if>
					 	<c:if test="${commissione.id ne null }">
						 	<c:if test="${commissione.aperta eq null }">
								<option value="1" ><fmt:message key="label.aperta" /></option>
								<option value="0" ><fmt:message key="label.chiusa" /></option>
							</c:if>
						    <c:if test="${commissione.aperta eq true }">
								<option value="1" selected="selected"><fmt:message key="label.aperta" /></option>
								<option value="0" ><fmt:message key="label.chiusa" /></option>
							</c:if>
							<c:if test="${commissione.aperta eq false }">
								<option value="1" ><fmt:message key="label.aperta" /></option>
								<option value="0" selected="selected"><fmt:message key="label.chiusa" /></option>
							</c:if>
						</c:if>
					</spring-form:select>
					<c:if test="${commissione.aperta eq false}">
						<a class="btn btn-primary" href="javascript:doSubmit('riapri.htm','<fmt:message key="javascript.confirm.commissioni-edilizie.riapri" />',document.inviodati)"><fmt:message key="button.commissioni-edilizie.riapri" /></a>
					</c:if>
			 	</div>
			 	<div id="trDataFine" class="form-group">
			 		<label><fmt:message key="label.data_fine" /></label>
					<spring-form:input id="data_fine_id" path="dataFine" size="10" onblur="isValidDate(this,true);" />
					<init:calendar imagePath="/images/cal.gif" idImage="calDataFine" idInput="data_fine_id" textKey="label.calendar" />
			 	</div>
			 	<div class="form-group">
			 		<label><fmt:message key="label.tipologia" /></label> 
					<jsp:include page="../includes/autocompletergenerico.jsp" >
						<jsp:param name="idElemento" value="tipologia" />
						<jsp:param name="propertyPath" value="tipologia" />										
						<jsp:param name="pathPropertyDescription" value="tipologia" />
						<jsp:param name="pathPropertyCode" value="codiceTipologia" />
						<jsp:param name="autocompleterAjax" value="findCommedilizieTipologia.htm" />							
						<jsp:param name="titleKey" value="label.ricerca_tipologia_commissione" />
						<jsp:param name="autocompleterInputSize" value="68" />
						<jsp:param name="cssClass" value="required" />
					</jsp:include>
					<div id="errore_tipologia_id" class="error validation-feedback" ><fmt:message key="commissioniediliziet.label.errore.tipologia.required" /></div>
			 	</div>
			 	<div class="form-group">
			 		<label><fmt:message key="commissioniediliziet.label.flag_sincrona" /></label>
			 		<spring-form:checkbox id="flagSincrona_id" path="sincrona"/>
					<label style="width:100%" for="flagSincrona_id"><span style="line-height: 2em; vertical-align: top;"><fmt:message key="commissioniediliziet.label.descrizione_flag_sincrona" /></span></label>
			 	</div>
		 	</fieldset>
			 
			 <c:if test="${commissione.id != null}">
			 	<fieldset>
			 		<legend><fmt:message key="label.commissioni_conferenze.convocazioni"/></legend>
			 	
				<div class="form-group">
					<table class="vbg-table">
	 					<thead>
							<tr class="header">
								<th><fmt:message key="label.convocazione"/></th>
							</tr>
						</thead>
			                <%
						    int i=0;
						    %>
						<tbody>
							<c:if test="${not empty commissione.convocazioni}">
								<c:forEach items="${commissione.convocazioni}" var="convocazione" varStatus="indice">
									<tr class="<%=(i%2)==0?"odd":"even"%>">
										<td>
										    <c:if test="${commissione.idConvocazione !=null && commissione.idConvocazione == convocazione.id}">
												<input id="id_checkbox${indice.index}" type="checkbox" checked="checked" onclick="javascript:updateIdConvocazione(${commissione.id},${convocazione.id},${commissione.numeroConvocazioni},'id_checkbox${indice.index}')"/>
											</c:if>
											<c:if test="${(commissione.idConvocazione !=null && commissione.idConvocazione != convocazione.id) || commissione.idConvocazione == null}">
												<input id="id_checkbox${indice.index}" type="checkbox" onclick="javascript:updateIdConvocazione(${commissione.id},${convocazione.id},${commissione.numeroConvocazioni},'id_checkbox${indice.index}')"/>
											</c:if>									
											<a style="vertical-align: super;" href="javascript:doHref('../commedilizieconvocazioni/view.htm?codice=${convocazione.id}','')" title="<fmt:message key="label.azioni" /> ${convocazione.id}">
												${indice.index + 1}<fmt:message key="label.a"/> Convocazione ( ${convocazione.descrizione} )
											</a>
											<div id="aggiornato${convocazioni.id.codice}" style="display: none;"></div>
										</td>
									</tr>
									<%i++;%>
						 		</c:forEach>
						 	</c:if>
						 	<c:if test="${empty commissione.convocazioni}">
						 		<tr class="even">
									<td align="center"><fmt:message key="label.convocazioni_non_presenti"/></td>
								</tr>
							</c:if>	
						</tbody>
						<tfoot>
							<tr>
								<td class="btn-foot">
									<c:if test="${commissione.aperta eq true}"><%-- ${commissione.id != null} --%>
										<a href="javascript:doHref('../commedilizieconvocazioni/create.htm?codiceCommissione=${commissione.id}','');">
											<i class="fa fa-plus-circle"></i>&nbsp;<fmt:message key="button.nuova_convocazione" />
										</a>
									</c:if>
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
				</fieldset>
			</c:if>
		 	
		 	</div class="vbg-form">
		 	<div >
		 	<div class="form-button">
				<c:if test="${commissione.id==null}">
					<a id="btnInsert" class="btn btn-primary"><fmt:message key="button.insert" /></a>
				</c:if>
				<c:if test="${commissione.id != null}">
				
					
					<c:if test="${commissione.aperta eq true}">
						<a id="btnUpdate" class="btn btn-primary" ><fmt:message key="button.update" /></a>
						<a class="btn btn-primary" href="javascript:doSubmit('delete.htm?codice=${commissione.id}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a>
					</c:if>
					<a class="btn btn-primary" href="javascript:doHref('listCommissioniedilizieR.htm?codiceCommissione=${commissione.id}','');"><fmt:message key="button.dettaglio" /></a>
					<a class="btn btn-primary" href="javascript:doHref('../commedilizieappello/list.htm?codiceCommissione=${commissione.id}','');"><fmt:message key="button.appello_iniziale" /></a>
					<a class="btn btn-primary" href="javascript:historySet('${_urlback}','../comunicazionicommissioni/list.htm?idCommissione=${commissione.id}','');"><fmt:message key="button.comunicazioni.massive" /></a>
					<a class="btn btn-primary" id="btnStampa" href="#"><fmt:message key="button.stampa" /></a>
					<a class="btn btn-primary" href="javascript:doHref('../commedilizieallegati/list.htm?codiceCommissione=${commissione.id}','');"><fmt:message key="button.allegati" /></a>
					<a class="btn btn-primary" id="btnAuditing" href="javascript:doHref('../commissioniauditing/list.htm?codice=${commissione.id}','');"><fmt:message key="button.auditing" /></a>
				</c:if>
				<a class="btn btn-secondary" href="javascript:historyBack()"><fmt:message key="button.back" /></a>
			</div>
	
		 	
 			</div>
			<script type='text/javascript'>
				$('numprotocollo_id').focus();
				
				function updateIdConvocazione(idcommissione,idconvocazione,size,isCheck){
					// va a mettere i check a false tranne quello che abbiamo cliccato
					if(size!=1 &&  document.getElementById(isCheck).checked==true)
					{
					
					for(i=0;i<size;i++)
					{	
						if(document.getElementById('id_checkbox'+i) != document.getElementById(isCheck))
							{
								document.getElementById('id_checkbox'+i).checked=false; 
							}
					}
					
					doHref('../commissioniediliziet/updateIdConvocazione.htm?codiceCommissione='+idcommissione+'&codiceConvocazione='+idconvocazione,'');
					}
					else
					{
						alert('<fmt:message key="alert.convocazioni_in_commissione_obbligatoria"/>')
						document.getElementById(isCheck).checked=true;
					}
			}
			</script>	
		</spring-form:form>
	</div>

<c:if test="${commissione.id != null}">

	<jsp:include page="./stampadoctipo.jsp">
		<jsp:param name="codicecommissione" value="${commissione.id}" />
	</jsp:include>

</c:if>	

	
	<script type="text/javascript">
		vbg.ready(() => {
			
			let btnInsert = document.getElementById('btnInsert');
			if( btnInsert )
			{
				btnInsert.addEventListener('click', function() {
					if( campiObbligatoriSpecificati() ){
						doSubmit('insert.htm','',document.inviodati);
					}
				});
			}
			
			let btnUpdate = document.getElementById('btnUpdate');
			if( btnUpdate )
			{
				btnUpdate.addEventListener('click', function() {
					if( campiObbligatoriSpecificati() ){
						doSubmit('update.htm?codice=${commissione.id}','',document.inviodati);
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
					
			const modal = document.getElementById('vbg-modal-stampe-id');
			
			if( modal ) {
				let btnStampa = document.getElementById('btnStampa');
				
				btnStampa.addEventListener('click', (e) => {
					e.preventDefault();
					modal.open();
				});
			
				modal.beforeClose = function()
				{
					modal.querySelectorAll('input').forEach( x => x.value = '' );
					return true;
				}
			}
			
			var idTestata = '${commissione.id}';

			if( idTestata != '' ){
				
				let checkSincrona = document.getElementById('flagSincrona_id');
				checkSincrona.addEventListener('click', (e) => {
					e.preventDefault();
					return false;
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
			
		 });
	</script>
</body>
</html>