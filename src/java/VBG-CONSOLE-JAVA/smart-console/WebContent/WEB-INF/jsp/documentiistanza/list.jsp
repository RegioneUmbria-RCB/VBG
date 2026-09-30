<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.security.LoggedUser"%>
<%@ page import="org.springframework.security.context.SecurityContextHolder"%>
<%@ page import="org.springframework.security.context.SecurityContext"%>
<%@ page import="org.springframework.security.userdetails.UserDetails"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
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
	<div id="subcontent">
		<form name="inviodati" action="list.htm" id="myForm" method="post">
			
				<!-- GESTIONE DEI DOCUMENTI DELL?ISTANZA -->
			
			 <br class="break" />			 	 
			 <jmesa:springTableFacade
				id="documentiistanza_id" 
				items="${documentiistanza.documentiistanzaListDTO}" 
				var="documentiistanza_var"
				exportTypes="pdfp,excel,csv" 
				maxRows="100"
				stateAttr="restore" filterMatcherMap="org.jmesa.custom.DocumentiistanzaFilterMatcherMap" >				
				<jmesa:htmlTable>
					<jmesa:htmlRow>	
					    <jmesa:htmlColumn property="nomeFile" titleKey="label.nome" width="15%" filterable="false"/>						
						<jmesa:htmlColumn property="documento" titleKey="label.documento" width="30%" filterable="false"/>
						<jmesa:htmlColumn property="note" titleKey="label.note" width="15%" filterable="false"/>							
						<jmesa:htmlColumn property="necessario" titleKey="label.richiesto" width="10%" headerEditor="org.jmesa.custom.DocIstanzaNecessarioHeaderEditor" filterable="false" >
							<input id="chk_necessario${documentiistanza_var.id.codice}" type="checkbox" value="${documentiistanza_var.id.codice}" name="chk_necessario" ${documentiistanza_var.necessario?'checked':''} onclick="abilitaNecessario(this, 'chk_necessario${documentiistanza_var.id.codice}')"></input>
							<span id="result_necessario_${documentiistanza_var.id.codice}" style="display: none"></span>
						</jmesa:htmlColumn>										
						<jmesa:htmlColumn property="presente" titleKey="label.presente" width="10%" headerEditor="org.jmesa.custom.DocIstanzaPresenteHeaderEditor" filterable="false" >
							<input id="chk_presente${documentiistanza_var.id.codice}" ${documentiistanza_var.codiceOggetto!=null?'disabled checked':''} type="checkbox" value="${documentiistanza_var.presente}" name="chk_presente" ${documentiistanza_var.presente?'checked':''} onclick="regolaAggiornaPresenteDocIsta('chk_presente${documentiistanza_var.id.codice}', 'select_valido_doc_ist${documentiistanza_var.id.codice}',${documentiistanza_var.id.codice})"></input>
							<span id="result_presente_${documentiistanza_var.id.codice}" style="display: none"></span>
						</jmesa:htmlColumn>	
						<jmesa:htmlColumn  property="controllook" titleKey="label.valido" filterable="false" sortable="false" width="5%">
							<select id="select_valido_doc_ist${documentiistanza_var.id.codice}" name="controllook" onchange="regolaAggiornaValidoDocIstanza('select_valido_doc_ist${documentiistanza_var.id.codice}','chk_presente${documentiistanza_var.id.codice}','${documentiistanza_var.id.codice}');">
								<option  value="null" ${documentiistanza_var.controllook==null?'selected':''}>Da verificare</option>								
								<option  value="1" ${documentiistanza_var.controllook==1?'selected':''}>Valido</option>
								<option  value="0" ${documentiistanza_var.controllook==0?'selected':''}>Non valido</option>
							</select>
					   </jmesa:htmlColumn>
						<jmesa:htmlColumn property="_" titleKey="label.elimina" width="5%" headerEditor="org.jmesa.custom.DocIstanzaEliminaHeaderEditor" sortable="false" filterable="false" >
							<input id="chk_elimina${documentiistanza_var.id.codice}" type="checkbox" value="${documentiistanza_var.id.codice}" name="chk_elimina" ></input>
						</jmesa:htmlColumn>	
						<jmesa:htmlColumn property="data" titleKey="label.data" filterable="false" width="10%">
							<input type="text" id="data_id${documentiistanza_var.id.codice}"
								value="<fmt:formatDate value="${documentiistanza_var.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />"
								name="documentiistanza.data" size="8" onblur="isValidDate('data_id${documentiistanza_var.id.codice}',true);" onchange="changeData('data_id${documentiistanza_var.id.codice}','${documentiistanza_var.id.codice}');"
								/>
							<init:calendar imagePath="/images/cal.gif"
								idImage="caldata${documentiistanza_var.id.codice}" idInput="data_id${documentiistanza_var.id.codice}"
								textKey="label.calendar" javascriptAction="changeData('data_id${documentiistanza_var.id.codice}','${documentiistanza_var.id.codice}');" />			
							<span id="result_${documentiistanza_var.id.codice}"	style="display: none"></span>				
						</jmesa:htmlColumn>							
						<jmesa:htmlColumn property="oggetto" titleKey="documentiistanza.label.oggetto" sortable="false" filterable="false" width="5%" >
							<c:if test="${documentiistanza_var.codiceOggetto!=null}">
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       					
	       						<jsp:param name="idElemento" value="docIstanza${documentiistanza_var.id.codice}" />
	       						<jsp:param name="fileId" value="${documentiistanza_var.codiceOggetto}" />
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
						</jmesa:htmlColumn>
						<c:if test="${ isDocErAttivo eq true}">
							<jmesa:htmlColumn property="idDocer" titleKey="label.archiviato_docer.list" sortable="false" filterable="false" width="5%">
								<jsp:include page="../documentiistanza/oggettodocer.jsp" >
									<jsp:param name="view" value="list" />	
									<jsp:param name="docnum" value="${documentiistanza_var.idDocer}" />
									<jsp:param name="identificativo" value="${documentiistanza_var.id.codice}_${documentiistanza_var.idDocer}" />
								</jsp:include>
							</jmesa:htmlColumn>
						</c:if>
												
						<jmesa:htmlColumn property="." titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
							<a class="dettaglioColumn" href="view.htm?codice=${documentiistanza_var.id.codice}" title="<fmt:message key="label.edit.record" />${documentiistanza_var.id.codice}">
								<label><fmt:message key="label.edit.record.image" /></label>
							</a>								
						</jmesa:htmlColumn>
					</jmesa:htmlRow>
				</jmesa:htmlTable>					
			 </jmesa:springTableFacade>
			 <input type="hidden" value="${documentiistanza.istanza.id.codice}" name="codiceIstanza" />		
		</form>
		<!-- Apre una dialog per visualizzare la nota -->
			
		<script type="text/javascript">

			var _jmesaUrl='list.htm?codiceIstanza=${documentiistanza.istanza.id.codice}&';
			var _captionTab='<fmt:message key="documentiistanza.label.lista_documentiistanza.title" />';
			
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
					  $(id).pulsate();
					  $(id).fade();
					 }						    		 
			});
			}
			
			// GESTISCE LA FUNZIONE PER SELEZIONARE TUTTI I CHECKBOX ELIMINA DEI DOC DELL'ISTANZA 
			function selezionaTuttiEliminaDocIstanza()
			{
				if($('id_check_elimina_tot').checked==false){
					jQuery("input[id^='chk_elimina']").attr('checked', false);
				}
				if($('id_check_elimina_tot').checked==true){
					jQuery("input[id^='chk_elimina']").attr('checked', true);
				}
			
		
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
						  $(id).pulsate();
						  $(id).fade();
						 }						    		 
				});
			}
			
			<%--
			   1. Controlla se si può aggiornare il flag presnete (Se è verificato o valido deve essere per forza presente)
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
						  $(id).className='error_checkbox'
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
							  $(id).className='error_checkbox'
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
								$('result_'+codice).className = 'error_header'
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
		
	</div>
	<div id="functions">
		<ul>
			<li><a href="javascript:doHref('create.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','');"><fmt:message key="button.new" /></a></li>
			<li><a href="javascript:doHref('createAllineaDocumenti.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','');"><fmt:message key="button.allinea_documenti" /></a></li>
			<li><a href="javascript:doSubmit('eliminaDocumenti.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.cancella_documenti" /></a></li>
			<li><a href="javascript:doHref('../history/set.htm?ReturnTo=${_urlback}&GoTo=..%2Fistanzeallegati%2Flist.htm%3FcodiceIstanza%3D${documentiistanza.istanza.id.codice}','');" title="<fmt:message key="button.allegati_endo" />" ><fmt:message key="button.allegati_endo" /></a></li>			
			
			<c:if test="${isAllegtaiStcPresenti eq true }">
				<li><a
					href="javascript:confermaOperazioneBackupStc('message_dialog_confirm');"
					title="<fmt:message key="label.salva_tutti_allegati_stc" />"><fmt:message key="button.salva_allegati_stc" />
				</a>
				</li>	
			</c:if>
			<%-- 
			<c:if test="${isAllegtaiStcPresenti eq false }">
				<li class="buttondisabled"><a href="javascript:void(0);" title="<fmt:message key="label.non_sono_presenti_allegati_stc" />" ><fmt:message key="button.salva_allegati_stc" /></a></li>	
			</c:if>
			--%>
			<c:if test="${isAmministratore eq true && isAllegtaiStcPresenti eq true}">
				<li><a href="javascript:historySet('${_urlback }','../stc/resetBackupSTC.htm?codiceIstanza=${documentiistanza.istanza.id.codice}');"  ><fmt:message key="button.reset_backup_stc" /></a></li>	
			</c:if>
			<c:if test="${ isDocErAttivo eq true}">
				<li><a href="javascript:historySet('${_urlback}','../documentiistanza/pannelloRicercaDocer.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','');"><fmt:message key="label.ricerca_documenti_docer" /></a></li>
			</c:if>		
			<li><a href="javascript:historySet('${_urlback}','../movimenti/listElaborazione.htm?codiceIstanza=${documentiistanza.istanza.id.codice}','')"><fmt:message key="button.elaborazione" /></a></li>						
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
		
	<br class="break" />
	<br class="break" />		
	
	<!-- GESTIONE DEI DOCUMENTI DEGLI ENDO PROCEDIMENTI DELL'ISTANZA --> 
	
	<c:if test="${not empty documentiistanza.istanzeallegatiDTOs}">
		<%
			String displayEndo = "display:none;";
			String styleEndo = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCENDO_DIV)).equals("1")) {
			    displayEndo = "";
			    styleEndo="sezioneDatiMeno";
			} else {
			    displayEndo = "display:none;";
			    styleEndo="sezioneDatiPiu";
			}
		%>
		<fieldset>
		<legend>	
		<a class="<%= styleEndo %>" 
			id="id_link_endo" 
			href="javascript:showHidePanel('visEndoId', 'id_link_endo', '<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCENDO_DIV %>', '${pageContext.request.contextPath}/images/','div');"	
			title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="documentiistanza.label.documenti_su_endoprocedimenti.title" />">
			<label for="id_link_endo"><fmt:message key="documentiistanza.label.documenti_su_endoprocedimenti.title" /></label>
		</a>
		</legend>	
		<div class="jmesa" id="visEndoId" style="<%= displayEndo%>">
		
		<form name="inviodatiIstanzeallegati" action="list.htm" id="formIstanzeallegati" method="post">
		<jmesa:springTableFacade 
				id="istanzeallegati_id" 
				items="${documentiistanza.istanzeallegatiDTOs}" 
				var="istanzeallegati_var"  
				stateAttr="restore" 
				maxRows="100">				
				<jmesa:htmlTable>
					<jmesa:htmlRow>
					    <jmesa:htmlColumn property="nomeFile" titleKey="label.nome" filterable="false"/>															
						<jmesa:htmlColumn property="allegatoextra" titleKey="label.allegato" filterable="false"/>
						<jmesa:htmlColumn property="note" titleKey="label.note" filterable="false"/>						
						<jmesa:htmlColumn property="procedimento" titleKey="label.endoprocedimento" filterable="false"/>						
                        <jmesa:htmlColumn property="necessario" titleKey="label.richiesto" width="5%" filterable="false" >
							<input id="chk_ist_all_necessario_${istanzeallegati_var.id.codice}" type="checkbox" value="${istanzeallegati_var.id.codice}" name="chk_necessario"  ${istanzeallegati_var.necessario?'checked':''} onclick="abilitaNecessarioIstanAllegati(this, 'chk_ist_all_necessario_${istanzeallegati_var.id.codice}')"></input>
							<span id="result_necessario_${istanzeallegati_var.id.codice}" style="display: none"></span>
						</jmesa:htmlColumn>	
                        <jmesa:htmlColumn property="presente" titleKey="label.presente" filterable="false" sortable="false" width="5%">
                        <input
								id="checkbox_presente_id${istanzeallegati_var.id.codice}" ${istanzeallegati_var.codiceOggetto!=null?'disabled checked':''} onclick="regolaAggiornaPresenteAllIsta('checkbox_presente_id${istanzeallegati_var.id.codice}','select_valido${istanzeallegati_var.id.codice}',${istanzeallegati_var.id.codice})"
								type="checkbox"
								value="${istanzeallegati_var.presente}"
								name="presente"
								${istanzeallegati_var.presente?'checked':''}  
								
						/> 
						<span id="result_presente${istanzeallegati_var.id.codice}"	style="display: none"></span>
						</jmesa:htmlColumn>	
					   <jmesa:htmlColumn  property="controllook" titleKey="label.valido" filterable="false" sortable="false" width="5%">
							<select id="select_valido${istanzeallegati_var.id.codice}" name="controllook" onchange="regolaAggiornaValidoIstanAll('select_valido${istanzeallegati_var.id.codice}','checkbox_presente_id${istanzeallegati_var.id.codice}','${istanzeallegati_var.id.codice}');">
								<option  value="null" ${istanzeallegati_var.controllook==null?'selected':''}>Da verificare</option>								
								<option  value="1" ${istanzeallegati_var.controllook==1?'selected':''}>Valido</option>
								<option  value="0" ${istanzeallegati_var.controllook==0?'selected':''}>Non valido</option>
							</select>
						<span id="result_look${istanzeallegati_var.id.codice}" style="display: none"></span>
					   </jmesa:htmlColumn>	
																																						
						<jmesa:htmlColumn property="oggetto" titleKey="label.oggetto" sortable="false" filterable="false" width="5%" >
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
						</jmesa:htmlColumn>
						<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../istanzeallegati/viewIstanzaAllegato.htm?codice=${istanzeallegati_var.id.codice}')" title="<fmt:message key="label.edit.record" />">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>								
						</jmesa:htmlColumn>																
					</jmesa:htmlRow>
				</jmesa:htmlTable>	
		</jmesa:springTableFacade>
		<input type="hidden" value="${documentiistanza.istanza.id.codice}" name="codiceIstanza" />	
		</form>
		
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
							  $(id).className='error_checkbox'
							  $(id).style.display='';
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
									  $(id).className='error_checkbox'
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
		</div>
	</c:if>	
	<!--  DOCUMENTI DELLE PROCURE DELL'ISTANZE -->	
	<fieldset><legend><fmt:message key="label.procure_dell_istanza" /></legend>
	<div class="jmesa" id="visProcureId">
		<table border="0" cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td><fmt:message key="label.procuratore" /> </td>
					<td><fmt:message key="label.anagrafe_rappresentata_da_procuratore" /> </td>
					<td><fmt:message key="label.oggetto" /> </td>
					<td><fmt:message key="label.edit.record" /></td>
				</tr>
			</thead>
			<tbody class="tbody">
				<%int yy=1;%>
				<c:forEach items="${istanzeprocures}" var="procura">
				<tr class="<%=(yy%2)==0?"odd":"even"%>">
					<td>
						${procura.anagrafeProcuratore.descrizioneRichiedente}
					</td>
					<td>${procura.anagrafeRappresentato.descrizioneRichiedente} </td>
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
						<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../istanzeprocure/view.htm?codice=${procura.id.codice}');" title="<fmt:message key="label.edit.record" /> ${procura.id.codice}">
							<label><fmt:message key="label.edit.record.image" /></label>
						</a>							
					</td>
				</tr>			
				</c:forEach>
			</tbody>
			<tfoot>
				<tr class="odd">
					<td colspan="4">
						<a class="addColumn" href="javascript:historySet('${_urlback }','../istanzeprocure/create.htm?codiceIstanza=${documentiistanza.istanza.id.codice}');" 
								title="<fmt:message key="label.nuovo" />">
						<label><fmt:message key="label.add.record.image" /></label></a>
					</td>					
				</tr>
			</tfoot>				
		</table>		
	</div>			
	</fieldset>
	
	
	<!-- Documenti delle anagrafiche appartennti all'istanza (AnagrafeDocumenti) -->
	<br class="break" />
	<c:if test="${not empty documentiistanza.anagrafedocumentiDTOs}">
		<%
			String displayDocAnagrafe = "display:none;";
			String styleDocAnagrafe = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCANAGR_DIV)).equals("1")) {
			    displayDocAnagrafe = "";
			    styleDocAnagrafe="sezioneDatiMeno";
			} else {
			    displayDocAnagrafe = "display:none;";
			    styleDocAnagrafe ="sezioneDatiPiu";
			}
		%>
		<fieldset>
		<legend>	 
		<a class="<%= styleDocAnagrafe %>" 
			id="id_link_anagrafe_doc" 
			href="javascript:showHidePanel('visAnagDocId', 'id_link_anagrafe_doc', '<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCANAGR_DIV %>', '${pageContext.request.contextPath}/images/','div');"	
			title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="documentiistanza.label.documenti_anagrafiche.title" />">
			<label for="id_link_anagrafe_doc" ><fmt:message key="documentiistanza.label.documenti_anagrafiche.title" /></label>
		</a>
		</legend>	
		<div class="jmesa" id="visAnagDocId" style="<%= displayDocAnagrafe%>">
		<form name="inviodatiDocAnagrafe" action="list.htm" id="formDocAnagrafe" method="post">
		<jmesa:springTableFacade 
				id="anagrafedocumenti_id" 
				items="${documentiistanza.anagrafedocumentiDTOs}" 
				var="anagrafedocumenti_var"  
				stateAttr="restore" 
				maxRows="100">				
				<jmesa:htmlTable>
					<jmesa:htmlRow>													
					    <jmesa:htmlColumn property="transientTipoSoggettoAndRichiedente" titleKey="label.anagrafe" filterable="false"/>		
						<jmesa:htmlColumn property="documento" titleKey="label.documento" filterable="false"/>
						<jmesa:htmlColumn property="nomeFile" titleKey="label.allegato" filterable="false"/>																	
						<jmesa:htmlColumn property="oggetto" titleKey="label.oggetto" sortable="false" filterable="false" width="5%" > 
							<jsp:include page="../includes/visualizzaOggetto.jsp" >
	       						<jsp:param name="idElemento" value="docAnagrafe${anagrafedocumenti_var.id.codice}" />
	       						<jsp:param name="fileId" value="${anagrafedocumenti_var.codiceOggetto}" />
	   						</jsp:include>
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../anagrafe/viewDocumenti.htm?codice=${anagrafedocumenti_var.id.codice}')" title="<fmt:message key="label.edit.record" />">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>								
							</jmesa:htmlColumn>				
						</jmesa:htmlColumn>								
					</jmesa:htmlRow>
				</jmesa:htmlTable>	
		</jmesa:springTableFacade>
		<input type="hidden" value="${documentiistanza.istanza.id.codice}" name="codiceIstanza" />	
		</form>
		</div>
		</fieldset>
		
	</c:if>	
	
	
	
	<!-- Documenti salvati nei campi dinamici -->
	<br class="break" />
		<c:if test="${not empty documentiistanza.documentiistanzaDynList}">
		<%
			String displayDyn = "display:none;";
			String styleDyn = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCDYN_DIV)).equals("1")) {
			    displayDyn = "";
			    styleDyn="sezioneDatiMeno";
			} else {
			    displayDyn = "display:none;";
			    styleDyn ="sezioneDatiPiu";
			}
		%>		 
		<a class="<%= styleDyn %>" 
			id="id_link_dyn" 
			href="javascript:showHidePanel('visDynId', 'id_link_dyn', '<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCDYN_DIV %>', '${pageContext.request.contextPath}/images/','div');"	
			title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="documentiistanza.label.documenti_su_campi_dyn.title" />">
			<label for="id_link_dyn" ><fmt:message key="documentiistanza.label.documenti_su_campi_dyn.title" /></label>
		</a>
		<div class="jmesa" id="visDynId" style="<%= displayDyn%>">
		<fieldset>
		<table border="0" cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
				    <td ><fmt:message key="label.documento" /> </td>
					<td ><fmt:message key="label.data" /> </td>
					<td ><fmt:message key="label.oggetto" /> </td>
				</tr>
			</thead>
			<tbody class="tbody">
				<%int j=1;%>				
				<c:forEach items="${documentiistanza.documentiistanzaDynList}" var="documenti_dyn">
				<tr class="<%=(j%2)==0?"odd":"even"%>">		
					<td>
						${documenti_dyn.documento}
					</td>
					<td>
						<fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${documenti_dyn.data}"/>						
					</td>
					<td>					
					<c:if test="${documenti_dyn.oggetto!=null}">						
						<jsp:include page="../includes/visualizzaOggetto.jsp" >
       						<jsp:param name="idElemento" value="docDyn${documenti_dyn.id.codice}" />
       						<jsp:param name="fileId" value="${documenti_dyn.oggetto.id.codice}" />
   						</jsp:include>
					</c:if>					 
					</td>
				</tr>	
				<%j++; %>	
				</c:forEach>					
			</tbody>
		</table>
		</fieldset>
		</div>
	</c:if>		
	<!-- Documenti salvati nei movimenti -->
	<br class="break" />
	<c:if test="${not empty documentiistanza.movimentiallegatiDTOs}">
		<%
			String displayMovimenti = "display:none;";
			String styleMovimenti = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCMOV_DIV)).equals("1")) {
			    displayMovimenti = "";
			    styleMovimenti="sezioneDatiMeno";
			} else {
			    displayMovimenti = "display:none;";
			    styleMovimenti ="sezioneDatiPiu";
			}
		%>
		<fieldset>
		<legend>	 
		<a class="<%= styleMovimenti %>" 
			id="id_link_movimenti" 
			href="javascript:showHidePanel('visMovId', 'id_link_movimenti', '<%= WebConstants.CONF_UTENTE_DOCUMENTIISTANZA_VISDOCMOV_DIV %>', '${pageContext.request.contextPath}/images/','div');"	
			title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="documentiistanza.label.documenti_su_movimenti.title" />">
			<label for="id_link_movimenti" ><fmt:message key="documentiistanza.label.documenti_su_movimenti.title" /></label>
		</a>
		</legend>	
		<div class="jmesa" id="visMovId" style="<%= displayMovimenti%>">
		
		<form name="inviodatiMovimentiallegati" action="list.htm" id="formMovimentiallegati" method="post">
		<jmesa:springTableFacade 
				id="movimentiallegati_id" 
				items="${documentiistanza.movimentiallegatiDTOs}" 
				var="movimentiallegati_var"  
				stateAttr="restore" 
				maxRows="100">				
				<jmesa:htmlTable>
					<jmesa:htmlRow>													
					    <jmesa:htmlColumn property="nomeFile" titleKey="label.nome" filterable="false"/>		
						<jmesa:htmlColumn property="descrizione" titleKey="label.documento" filterable="false"/>
						<jmesa:htmlColumn property="note" titleKey="label.note" filterable="false"/>
						
						<c:if test="${not empty movimentiallegati_var.descrizioneMovimento}">
							<jmesa:htmlColumn property="descrizioneMovimento" titleKey="label.movimento" filterable="false">						
						       
						        <span title="[${movimentiallegati_var.tipomovimento}] - ${movimentiallegati_var.descrizioneMovimento}&#13;&#10;${movimentiallegati_var.responsabileMovimento}(<fmt:formatDate value="${movimentiallegati_var.dataMovimento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>)">
						       ${movimentiallegati_var.descrizioneMovimento}
						       </span> 
						    </jmesa:htmlColumn>
						</c:if>		
						
						<c:if test="${empty movimentiallegati_var.descrizioneMovimento}">				
							<jmesa:htmlColumn property="movimento.tipomovimento.descrizioneEstesa" titleKey="label.movimento" >
							 
						       <span title="[${movimentiallegati_var.movimento.tipomovimento}] - ${movimentiallegati_var.descrizioneMovimento}&#13;&#10;${movimentiallegati_var.responsabileMovimento}(<fmt:formatDate value="${movimentiallegati_var.dataMovimento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/>)">
						       ${movimentiallegati_var.movimento.tipomovimento.descrizioneEstesa}
						       </span>  
							</jmesa:htmlColumn>				
						</c:if>
						<jmesa:htmlColumn property="dataregistrazione" titleKey="label.data" filterable="false" width="18%" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"  cellEditor="org.jmesa.view.editor.DateCellEditor" />
						
						<jmesa:htmlColumn property="amministrazione" titleKey="label.amministrazione" filterable="false"/>												
						<jmesa:htmlColumn width="5%"  property="flagPubblica" titleKey="label.pubblica" sortable="false" filterable="false">
								<input id="flagPubblicaId${movimentiallegati_var.id.codice }" type="checkbox" onclick="changeCheckboxValue('flagPubblicaId${movimentiallegati_var.id.codice }','${pageContext.request.contextPath}/movimentiallegati/ajaxChangeFlagPubblica.htm?codice=${movimentiallegati_var.id.codice}&checkPermessi=true')" ${movimentiallegati_var.flagPubblica?'checked':''} />
							</jmesa:htmlColumn>
						<jmesa:htmlColumn  property="controllook" titleKey="label.valido" filterable="false" sortable="false" width="5%">
							<select id="select_valido_doc_mov${movimentiallegati_var.id.codice}" name="controllook" onchange="changeValueValidoMovAll('select_valido_doc_mov${movimentiallegati_var.id.codice}','${movimentiallegati_var.id.codice}');">
								<option  value="null" ${movimentiallegati_var.controllook==null?'selected':''}>Da verificare</option>								
								<option  value="1" ${movimentiallegati_var.controllook==1?'selected':''}>Valido</option>
								<option  value="0" ${movimentiallegati_var.controllook==0?'selected':''}>Non valido</option>
							</select>
					   </jmesa:htmlColumn>	
						<jmesa:htmlColumn property="oggetto" titleKey="label.oggetto" sortable="false" filterable="false" width="5%" >
							
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
							
							<jmesa:htmlColumn property="" titleKey="label.edit.record" sortable="false" filterable="false" width="5%">
								<a class="dettaglioColumn" href="javascript:historySet('${_urlback }','../movimentiallegati/view.htm?codice=${movimentiallegati_var.id.codice}')" title="<fmt:message key="label.edit.record" />">
									<label><fmt:message key="label.edit.record.image" /></label>
								</a>								
							</jmesa:htmlColumn>								
						</jmesa:htmlColumn>								
					</jmesa:htmlRow>
				</jmesa:htmlTable>	
		</jmesa:springTableFacade>
		<input type="hidden" value="${documentiistanza.istanza.id.codice}" name="codiceIstanza" />	
		</form>
		</fieldset>
		</div>
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
							  $(id).className='error_checkbox'
							  $(id).style.display='';
							  $(id).pulsate();
							  $(id).fade();
							 }						    		 
					});
				}

		</script>
	
		<!-- Crea la finstra di dialogo che avevrte l'operatore che l'operazione che sta facendo potrebe durare alcuni minuti  
		     In quanto è un operazione di backup	
		-->
		<script type="text/javascript">		

			function confermaOperazioneBackupStc(divId){
				dijit.byId(divId).show();
		}

		</script>
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
	
</body>
</html>