<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.domain.web.ProtocollazioneCommand"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html
	xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>
	<c:if test="${protocolloCommand.displayMode == protocolloCommand.displayConstants.NEW}">
		<fmt:message key="label.crea_fascicolazione" />
	</c:if>	
	<c:if test="${protocolloCommand.displayMode == protocolloCommand.displayConstants.EDIT}">
		<fmt:message key="label.modifica_fascicolazione" />
	</c:if>
</title>
</head>
<body>
<span class="titoloPagina"> 
	<c:if test="${protocolloCommand.displayMode == protocolloCommand.displayConstants.NEW}">
		<fmt:message key="label.crea_fascicolazione" />
	</c:if>	
	<c:if test="${protocolloCommand.displayMode == protocolloCommand.displayConstants.EDIT}">
		<fmt:message key="label.modifica_fascicolazione" />
	</c:if>
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
	<jsp:include page="../includes/history.jsp">
	   	<jsp:param name="path" value="../protocollazione/creaFascicoloView" />
	</jsp:include>
	<c:import url="/ajax/dettaglioIstanza.htm">
		<c:param name="codIstanza">${protocolloCommand.entity.id.codice}</c:param>
	</c:import>
	<br class="clear" />
	<div id="subcontent">
		<spring-form:form commandName="protocolloCommand" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp">
				<jsp:param name="commandName" value="protocolloCommand" />
			</jsp:include>						
			
			
			<c:set var="readOnlyFascicolo" value="true"></c:set>
			<c:set var="isMovimento" value="true"></c:set>
			<c:if test="${empty protocolloCommand.movimento.id.codice}">
				<c:set var="readOnlyFascicolo" value=""></c:set>
				<c:set var="isMovimento" value="false"></c:set>
			</c:if>
				<table width="100%">
					<c:if test="${isDocEr eq false}">
					<tr>
						<td><fmt:message key="label.data_fascicolo" />
						</td>
						<td>						
						<c:if test="${isMovimento eq 'true' }">
							<spring-form:input id="dataFascicolo_id" path="dataFascicolo" size="10" maxlength="10" readonly="${readOnlyFascicolo}"  />
						</c:if> 
						<c:if test="${isMovimento eq 'false' }">
							<c:if test="${not empty listaFascicoli}">
								<spring-form:select id="dataFascicolo_id" path="dataFascicolo" onchange="changeDataFascicolo();">
									<spring-form:options items="${listaFascicoli}" itemLabel="codice" itemValue="codice"/>
								</spring-form:select>
							</c:if>
							<c:if test="${empty listaFascicoli}">
								<spring-form:input id="dataFascicolo_id" path="dataFascicolo" size="10" maxlength="10" onblur="isValidDate(this,true);" />
								<init:calendar imagePath="/images/cal.gif" idImage="calDataFascicolo" idInput="dataFascicolo_id" textKey="label.calendar"/>
								<c:if test="${isCrea eq false}">
									<init:help idHelp="help_datafascicolo" textKey="help.cambia_fascicolazione_messaggio"/>
								</c:if>
							</c:if>
						</c:if>	
							<spring-form:errors path="dataFascicolo" cssClass="error" />
						</td>
					</tr>
					
					</c:if>
					
					<tr>
						<td><fmt:message key="label.numero_fascicolo" /></td>
						<td>
							<c:if test="${not empty listaFascicoli}">
							<spring-form:select id="numeroFascicolo_id" path="numeroFascicolo" onchange="changeNumeroFascicolo();">
								<spring-form:options items="${listaFascicoli}" itemLabel="descrizione" itemValue="descrizione"/>
							</spring-form:select>
							</c:if>
							<c:if test="${empty listaFascicoli}">
								<spring-form:input id="numeroFascicolo_id" path="numeroFascicolo" size="100" readonly="${readOnlyFascicolo}"  />
								<c:if test="${isCrea eq false}">
									<init:help idHelp="help_numerofascicolo" textKey="help.cambia_fascicolazione_messaggio"/>
								</c:if>
							</c:if>
							<spring-form:errors path="numeroFascicolo" cssClass="error" />
						</td>
					</tr>
					<c:if test="${isCrea eq true}">
						<tr id="classificaOggetto">
									<td><fmt:message key="label.classifica" /></td>
									<c:if test="${isModificaClassificaParametriProt eq false}">
										<td>
											<spring-form:input path="classificaFascicolo" id="classificaFascicolo_id" disabled="true"  size="70" readonly="${readOnlyFascicolo}" />
											<spring-form:errors path="classificaFascicolo" cssClass="error"/>
										</td>
									</c:if>
									<c:if test="${isModificaClassificaParametriProt eq true}">
										<td>
											<c:choose>	
												<c:when test="${not empty listaClassificheFascicolis}">
													<spring-form:select path="classificaFascicolo" id="classificaFascicolo_id" >
														<spring-form:options items="${listaClassificheFascicolis}" itemLabel="descrizione" itemValue="codice" />
													</spring-form:select>
												</c:when>
												<c:otherwise>
													<spring-form:input path="classificaFascicolo" id="classificaFascicolo_id"  size="70"/>
												</c:otherwise>					
											</c:choose>				
											<spring-form:errors path="classificaFascicolo" cssClass="error"/>
										</td>
									</c:if>
						</tr>			
						<tr id="classificaOggetto">
							<td><fmt:message key="label.oggetto" /></td>
							<td>
								<spring-form:textarea id="oggettoFascicolo_id" path="oggettoFascicolo" cols="100" rows="5"  readonly="${readOnlyFascicolo}"  />
								<spring-form:errors path="oggettoFascicolo" cssClass="error" />
							</td>
						</tr>			
					</c:if>
				<c:if test="${isCrea eq false}">
					<c:if test="${isDocEr eq true}">
							<tr id="classificaOggetto">
								<td><fmt:message key="label.classifica" /></td>
					 			<c:if test="${isModificaClassificaParametriProt eq false}">
									<td>
										<spring-form:input path="classificaFascicolo" id="classificaFascicolo_id" disabled="true"  size="70" readonly="${readOnlyFascicolo}" />
										<spring-form:errors path="classificaFascicolo" cssClass="error"/>
									</td>
								</c:if>
								<c:if test="${isModificaClassificaParametriProt eq true}">
									<td>
										<c:choose>	
											<c:when test="${not empty listaClassificheFascicolis}">
												<spring-form:select path="classificaFascicolo" id="classificaFascicolo_id" >
													<spring-form:options items="${listaClassificheFascicolis}" itemLabel="descrizione" itemValue="codice" />
												</spring-form:select>
											</c:when>
											<c:otherwise>
												<spring-form:input path="classificaFascicolo" id="classificaFascicolo_id"  size="70"/>
											</c:otherwise>					
										</c:choose>				
										<spring-form:errors path="classificaFascicolo" cssClass="error"/>
									</td>
								</c:if>
						<%--
								
								<td>
									<spring-form:input id="classificaFascicolo_id" path="classificaFascicolo" size="100" readonly="true" />
									<spring-form:errors path="classificaFascicolo" cssClass="error" />
								</td>
							
							--%>
						</tr>
					</c:if>	
				</c:if>
					<tr id="classificaOggetto">
						<td><fmt:message key="label.anno" />
						</td>
						<td>
							<spring-form:input id="annoFascicolo_id" path="annoFascicolo" size="6" readonly="${readOnlyFascicolo}" />
							<spring-form:errors path="annoFascicolo" cssClass="error"/>
						</td>
					</tr>				
				</table>			
		</spring-form:form>
		
		
		
		<c:if test="${isPanelRicercaFascicoli eq true}">
		<c:if test="${isMovimento eq 'false' }">
		
			<div style="padding: 10px;">
				<fmt:message key="label.ricerca_fascicoli" />
				<a href="javascript:void(0);" onclick="dijit.byId('pannello_ricerca_fascicoli_id').show();"><img src="${pageContext.request.contextPath}/images/find.gif" border="0" /></a>
			</div>
		</c:if>
		
		
			<div dojoType="dijit.Dialog" id="pannello_ricerca_fascicoli_id" 
				 title="<fmt:message key="label.ricerca_fascicoli" />" style="display: none;">
				 <span style="border: thin;">
				 	<fmt:message key="label.ricerca_fascicoli.docer_help" />
				 </span>
				<table>
				<tr>
					<td><fmt:message key="label.anno" /></td>
					<td><input type="text" id="s_anno_id" size="10"/></td>
				</tr>
				<tr>	
					<td><fmt:message key="label.numero" /></td>
					<td><input type="text" id="s_numero_id" value="${protocolloCommand.numeroFascicoloAlberoIntervento}" size="10"/></td>
				</tr>
				
				<c:choose>
					<c:when test="${isPanelPrecompilaClassifica}">
						<tr>	
							<td><fmt:message key="label.classifica" /></td>
							<td><input value="${protocolloCommand.classificaFascicoloAlberoIntervento}" type="text" id="s_classifica_id" size="10" readonly="readonly"/></td>
						</tr>					
					</c:when>
					<c:otherwise>
							<tr>	
								<td><fmt:message key="label.classifica" /></td>
								<td><input value="${protocolloCommand.classificaFascicoloAlberoIntervento}" type="text" id="s_classifica_id" size="10"/></td>
							</tr>
					</c:otherwise>
				</c:choose>
				

				
				
				
				<tr>	
					<td><fmt:message key="label.descrizione" /></td>
					<td><input type="text" id="s_descrizione_id" size="20"/></td>
				</tr>
				</table>	
				<div id="functions">
					<ul>
						<li><a href="javascript:cercaFascicoli();"><fmt:message key="button.search" /></a></li>
					</ul>
				</div>
				<br class="clear" />
				<div id="cercaFascicoliRisposta_id" style="overflow-y: scroll; width: 600px; height: 250px;" ></div>
				<br class="clear" />
			</div>
			<script type="text/javascript">
			
				function scegliFascicolo(idx){
					
					jQuery('#annoFascicolo_id').val(jQuery('#anno_fasc_'+idx).html());
					jQuery('#numeroFascicolo_id').val(jQuery('#numero_fasc_'+idx).html());
					jQuery('#oggettoFascicolo_id').val(jQuery('#oggetto_fasc_'+idx).html());
					jQuery('#classificaFascicolo_id').val(jQuery('#classifica_fasc_'+idx).html());					
					// jQuery('#dataFascicolo_id').val(jQuery('#data_fasc_'+idx).html());
					dijit.byId('pannello_ricerca_fascicoli_id').hide();
					
					
				}
				function cercaFascicoli(){
					
					var anno = jQuery('#s_anno_id').val();
					var nf = jQuery('#s_numero_id').val();
					var oggetto = jQuery('#s_descrizione_id').val();
					var classifica = jQuery('#s_classifica_id').val();
					var htmlInCorso = "ricerca in corso <img src=\"${pageContext.request.contextPath}/images/spinner.gif\" />";
					jQuery('#cercaFascicoliRisposta_id').html(htmlInCorso);
					var jhqrPr = jQuery.ajax({
						  url: '../protocollazione/ajaxCercaFascicoli.htm',
						  context: document.body,
						  cache: false,
						  data: "anno="+anno+"&numeroFascicolo="+nf+"&oggetto="+oggetto+"&classifica="+classifica,
						  dataType: "html",
						  success: function(data) {
								jQuery('#cercaFascicoliRisposta_id').html(data);
						  },
						  error: function(jqXHR, textStatus, errorThrown){
							  jQuery('#cercaFascicoliRisposta_id').html(jqXHR.responseText);
						  }
					});
					
				}
			</script>
			
			
			
		</c:if>			
		
		
	</div>
	<script type="text/javascript">
	
	
		function creaFascicolo(){
			if(controlla()){
				doSubmit('creaFascicolo.htm','<fmt:message key="javascript.confirm.crea_fascicolo" />',document.inviodati);
			}
		}
		function cambiaFascicolo(){
			doSubmit('cambiaFascicolo.htm','<fmt:message key="javascript.confirm.cambia_fascicolo" />',document.inviodati);
		}		
		function changeDataFascicolo(){
			var pos = $('dataFascicolo_id').selectedIndex;
			$('numeroFascicolo_id').selectedIndex=pos;			
			gestClassificaOggetto();
		}
		function changeNumeroFascicolo(){
			var pos = $('numeroFascicolo_id').selectedIndex;
			$('dataFascicolo_id').selectedIndex=pos;			
			gestClassificaOggetto();
		}
		
		
		function controlla(){
			<c:if test="${isCrea eq false}">
				<c:if test="${empty listaFascicoli}">
					if(!checkAnnoODataObbligatorio()){
						return false;
					}
					if($('numeroFascicolo_id').value==''){
						alert('<fmt:message key="javascript.alert.numero_fascicolo_obbligatorio" />');
						$('numeroFascicolo_id').focus();
						return false;
					}
				</c:if>
			</c:if>
			<c:if test="${isMovimento eq 'true' }">
			<c:if test="${isDocEr eq false}">
				if(!checkAnnoODataObbligatorio()){
					return false;
				}
			</c:if>	
				if($('numeroFascicolo_id').value==''){
					alert('<fmt:message key="label.numero_fascicolo" /> <fmt:message key="alert.required" />');
					$('numeroFascicolo_id').focus();
					return false;
				}
			</c:if>
			return true;
		}
		
		function checkAnnoODataObbligatorio(){
			
			let dataFascicolo = document.getElementById('dataFascicolo_id');
			let anno = document.getElementById('annoFascicolo_id');
			if(anno && dataFascicolo){
				if(dataFascicolo.value=='' && anno.value==''){
					alert('Attenzione! E\' necessario specificare la data o l\'anno del fascicolo.');
					return false;
				}	
			}			
			return true;
		}
		
		function gestClassificaOggetto(){
			
			var numeroFascicolo = getSelectTextAndValue($('numeroFascicolo_id'));
			
			if(numeroFascicolo[0]==''){
				mostraNascondiElems(false);					
			}else{
				mostraNascondiElems(true);
			}
			
		}
		function mostraNascondiElems(mostra){
			var nodeList = document.getElementsByTagName('tr');
			for(var i=0;i<nodeList.length;i++){
				var currEl=nodeList[i];
				if (currEl.id == 'classificaOggetto'){
					if(mostra){
						currEl.style.display='';
					}else{
						currEl.style.display='none';
					}
				}
			}
		}
	</script>
	<div id="functions">
		<ul>
		<c:if test="${protocolloCommand.displayMode == protocolloCommand.displayConstants.NEW}">
			<li><a href="javascript:creaFascicolo();"><fmt:message key="button.crea_fascicolo" /></a></li>
		</c:if>
		<c:if test="${protocolloCommand.displayMode == protocolloCommand.displayConstants.EDIT}">
			<li><a href="javascript:cambiaFascicolo();"><fmt:message key="button.cambia_fascicolo" /></a></li>
		</c:if>
			<li><a href="javascript:historyBack('');"><fmt:message	key="button.back" /></a></li>
		</ul>
	</div>
</body>
</html>