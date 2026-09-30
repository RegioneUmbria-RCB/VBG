<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
		<c:choose>
			<c:when test="${ endoprocedimentoTipo2TTR eq true}">
				<fmt:message key="label.inventarioprocedimenti.amministrativi" />						
			</c:when>
			<c:otherwise>
				<fmt:message key="label.inventarioprocedimenti" />
			</c:otherwise>
		</c:choose>
	</title>
</head>
<body>
	<span class="titoloPagina">
		<c:choose>
			<c:when test="${ endoprocedimentoTipo2TTR eq true}">
				<fmt:message key="label.inventarioprocedimenti.amministrativi" />						
			</c:when>
			<c:otherwise>
				<fmt:message key="label.inventarioprocedimenti" />
			</c:otherwise>
		</c:choose>
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
	<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../inventarioprocedimenti/view" />
		<jsp:param name="qs" value="codice%3D${inventarioprocedimenti.entity.id.codice}%26codicecomune=${inventarioprocedimenti.entity.id.idcomune}"/>	
	</jsp:include>
	<%
     String  swSettato="display:none;";
     String  swTT="display:inline;";
	%>
	<div id="subcontent">
		<spring-form:form commandName="inventarioprocedimenti" name="inviodati">
			<jsp:include page="../includes/displayGlobalMessages.jsp" >
		        <jsp:param name="commandName" value="inventarioprocedimenti" />
		    </jsp:include>
			
			<table  width="100%">
				<%--
			    <tr>
					<td width="25%">
						<fmt:message key="label.codice_ancitel" />
					</td>
					<td>
						<spring-form:input id="codiceancitel_id" path="entity.codiceancitel" size="12" />
						<spring-form:errors path="entity.codiceancitel" cssClass="error"/>
					</td>
				</tr>
				--%>
				 <%if(ORMHelper.isConsoleLocale()) {	%>
				 <%--		 	
				 	<c:if test="${empty inventarioprocedimenti.entity.stpEndoTipo1s and empty inventarioprocedimenti.entity.stpEndoTipo2s }">
				 	 --%>
					 	<c:set var="isReadOnly" scope="page" value="false"></c:set>
						<c:if test="${ _VIEW_  eq true }">
							<c:set var="isReadOnly" scope="page" value="true"></c:set>
						</c:if>
						<c:choose>
							<c:when test="${_COMUNIASSOCIATI_ eq true }">
								<tr id="elementIdBeforeCombo">
									<td width="15%"></td>
									<td></td>
								</tr>						
								<jsp:include page="../includes/comboComuni.jsp">
									<jsp:param name="mostraTutti" value="true" />
									<jsp:param name="readOnly" value="${ isReadOnly }" />
									<jsp:param name="commandPropertyPath" value="entity.comune" />
									<jsp:param name="colspan" value="2" />
									<jsp:param name="comune" value="${inventarioprocedimenti.entity.comune.codicecomune}" />
									<jsp:param name="elementBeforeCombo" value="elementIdBeforeCombo" />
								</jsp:include>
							</c:when>
							<c:otherwise>
								<spring-form:hidden path="entity.comune.codicecomune" />
							</c:otherwise>	
						</c:choose>
					<%--	
				</c:if>	
				 --%>
				<%} %>
				<%--
				<c:if test="${not empty inventarioprocedimenti.stpEndoTipo1 }">
				<tr class="titoloSezione">
					<td colspan="2">
							<fmt:message key="label.parametri_regionali" />
					</td>
				</tr>			
				
				
				<tr>
					<td width="25%">
						<fmt:message key="label.codice_stp" />
					</td>
					<td>
						<spring-form:input id="stpEndoTipo1_codiceStp_id" path="stpEndoTipo1.codiceStp" size="10" />
						<spring-form:errors path="stpEndoTipo1.codiceStp" cssClass="error"/>
					</td>
				</tr>
					
				<tr>
					<td width="25%">
						<fmt:message key="label.codice_endo_regionale" />
					</td>
					<td>
						<spring-form:input id="stpEndoTipo1_codiceEndoRegionale_id" path="stpEndoTipo1.codiceEndoRegionale" size="10" />
						<spring-form:errors path="stpEndoTipo1.codiceEndoRegionale" cssClass="error"/>
					</td>
				</tr>
				</c:if>				
 --%>							
			   
			   <tr class="titoloSezione">
					<td colspan="2">
							<fmt:message key="inventarioprocedimenti.label.caratteristiche_endoprocedimento" />
					</td>
				</tr>		
				<tr>
					<td width="25%">
						<fmt:message key="inventarioprocedimenti.label.endo_procedimento" />
					</td>
					<td>
						<spring-form:input id="procedimento_id" path="entity.procedimento" size="70" />
						<spring-form:errors path="entity.procedimento" cssClass="error"/>
					</td>
				</tr>
				<tr>
					<td><fmt:message key="inventarioprocedimenti.label.data_aggiornamento" /></td>
					<td>
						<spring-form:input id="dataAggiornamento_id" path="entity.dataaggiornamento" size="10" maxlength="10" onblur="isValidDate(this,true);" readonly="true"/>
						
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.tipi_endo" />
					</td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tipoendo_id" />		
							<jsp:param name="propertyPath" value="entity.tipoendo" />				
							<jsp:param name="pathPropertyDescription" value="entity.tipoendo.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="entity.tipoendo.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipiendoSWeTT.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tipi_endo" />
						</jsp:include>
						
					</td>
				</tr>
				 
<%--
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.tempificazione" />
					</td>
					<td>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="tempificazione_id" />		
							<jsp:param name="propertyPath" value="entity.tempificazione" />				
							<jsp:param name="pathPropertyDescription" value="entity.tempificazione.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="entity.tempificazione.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTempificazioni.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_tempificazione" />
						</jsp:include>
					
					</td>
				</tr>
 --%>				
				

					<tr>
						<td width="25%" style="vertical-align: top;">
							<fmt:message key="label.descrizione" />
						</td>
						<td>
							<spring-form:textarea id="datigenerali_id" path="entity.datigenerali" rows="4" cols="72"></spring-form:textarea>
							<spring-form:errors path="entity.datigenerali" cssClass="error"/>						
						</td>
					</tr>
				
				
				<tr>
					<td style="vertical-align: top;">
						<fmt:message key="label.requisiti" />
					</td>
					<td>
						<spring-form:textarea id="applicazione_id" path="entity.campoapplicazione" rows="4" cols="72"></spring-form:textarea>
						<spring-form:errors path="entity.campoapplicazione" cssClass="error"/>
						
					</td>					
				</tr>
				<tr >
					<td  style="vertical-align: top;">
						<fmt:message key="inventarioprocedimenti.label.adempimenti" />
					</td>
					<td>
						<spring-form:textarea id="id_adempimenti" path="entity.adempimenti" rows="4" cols="72"></spring-form:textarea>
						<spring-form:errors path="entity.adempimenti" cssClass="error"/>
						
					</td>
				</tr>

			
				<%--
				<tr>
					<td>
						<fmt:message key="inventarioprocedimenti.label.collaudo" />
					</td>
					<td>
						<spring-form:checkbox path="entity.collaudo"/>
						<spring-form:errors path="entity.collaudo" cssClass="error"/> 
					</td>
				</tr>
				--%>
				<tr>
					<td>
						<fmt:message key="label.pubblica" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox path="entity.flagPubblica"/>
						<spring-form:errors path="entity.flagPubblica" cssClass="error"/> 
					</td>
				</tr>
				
				
				<tr>
					<td>
						<fmt:message key="label.disabilitato" />
					</td>
					<td>
				        <spring-form:checkbox path="entity.disabilitato"/>	
						<spring-form:errors path="entity.disabilitato" cssClass="error"/> 
					</td>
				</tr>
				<tr>
					<td>
						<fmt:message key="label.amministrazione" />
					</td>
					
					<td>
					    <script type="text/javascript">
					    	
					    	function clearUffici(inputField,listItem){
					    		var a = listItem.id;
								document.getElementById('amministrazioni_hidden').value = a;
								document.getElementById("amministrazionereferente_hidden").value='';
								document.getElementById("amministrazionereferente_id").value='';		
							}							
						</script>	
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="amministrazioni" />		
							<jsp:param name="propertyPath" value="entity.amministrazioni" />				
							<jsp:param name="pathPropertyDescription" value="entity.amministrazioni.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="entity.amministrazioni.id.codice" />
							<jsp:param name="autocompleterAjax" value="findAmministrazioni.htm?tutteLeAmministrazioni=true" />	
							<jsp:param name="afterUpdateElement" value="clearUffici" />					
							<jsp:param name="titleKey" value="label.ricerca_amministrazione" />
						</jsp:include>		
					</td>
										
				</tr>
				
				
				<tr>
					<td>
						<fmt:message key="label.obbligo_possesso_titolo" />
					</td>
					<td class="inline-ui-cell">
						<spring-form:checkbox path="entity.flagtipotitolo"/>
						<spring-form:errors path="entity.flagtipotitolo" cssClass="error"/> 
						<init:help idHelp="help_flagtipotitolo_id" textKey="label.obbligo_possesso_titolo.help" />
					</td>
				</tr>
				

				<%
					String displayAltriDati = "display:none;";
					String styleAltri = "";
					//gestisce la visualizzazione della tabella altri dati
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI)).equals("1")) {
					    displayAltriDati = "";
					    styleAltri="sezioneDatiMeno";
					} else {
					    displayAltriDati = "display:none;";
					    styleAltri="sezioneDatiPiu";
					}
				%>
				<tr class="titoloSezione">
					<td colspan="2">
						<a class="<%=styleAltri %>" id="id_link_altridati" href="javascript:showHidePanel('id_altridati_table', 'id_link_altridati', '<%= WebConstants.CONF_UTENTE_INVENTARIO_PROCED_ALTRIDATI %>', '${pageContext.request.contextPath}/images/');"	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="inventarioprocedimenti.label.altri_dati"/>">
							<label for="id_link_altridati"><fmt:message key="inventarioprocedimenti.label.altri_dati"/></label>
						</a>						
					</td>
				</tr>				
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="label.ordine" />
					</td>
					<td>
						<spring-form:input id="ordine_id" path="entity.ordine" cssStyle="text-align:right;" size="5" />
						<spring-form:errors path="entity.ordine" cssClass="error"/>
					</td>
				</tr>
				<%-- 
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
					    <fmt:message key="inventarioprocedimenti.label.tipimovimento" />
					</td>
					<td>
					
	                	<jsp:include page="../includes/tipimovimentosearch.jsp" >
							<jsp:param name="idElemento" value="tipoMovimentoInputId" />
							<jsp:param name="pathTipomovimento" value="entity.tipomovimento" />							
						</jsp:include>		
	                	
	                	
					</td>					
			    </tr>
			    --%>
				<tr id="id_altridati_table" style="<%=displayAltriDati%>;">
					<td>
						<fmt:message key="inventarioprocedimenti.label.natura_endo" />
					</td>
					<td>
					 	<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="naturaendo_id" />		
							<jsp:param name="propertyPath" value="entity.naturaendo" />				
							<jsp:param name="pathPropertyDescription" value="entity.naturaendo.descrizioneEstesa" />
							<jsp:param name="pathPropertyCode" value="entity.naturaendo.id" />
							<jsp:param name="autocompleterAjax" value="findNaturaEndo.htm" />							
							<jsp:param name="titleKey" value="label.ricerca_natura_endo" />
						</jsp:include>
						
					</td>
				</tr>
				
				
				
				
				<%
					String displayNormative = "display:none;";
					String style = "";
					//gestisce la visualizzazione della tabella normative
					if (((String) request.getAttribute(WebConstants.CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE)).equals("1")) {
					    displayNormative = "";
					    style="sezioneDatiMeno";
					} else {
					    displayNormative = "display:none;";
					    style="sezioneDatiPiu";
					}
				%>
				<tr class="titoloSezione">
					<td colspan="2">

						<a class="<%=style %>" id="id_link_procedure" href="javascript:showHidePanelBase('id_normative_table', 'id_link_procedure', '<%= WebConstants.CONF_UTENTE_INVENTARIO_PROCED_NORMATIVE %>', '${pageContext.request.contextPath}/images/',false);" 	title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="inventarioprocedimenti.label.normative"/>">
							<label for="id_link_procedure"><fmt:message key="inventarioprocedimenti.label.normative"/> (sezione dismessa - utilizzare la funzionalità NORMATIVE) </label>
						</a>
				 	</td>
				</tr>
				<tr id="id_normative_table" style="<%=displayNormative%>;" >
					<td  width="25%"  style="vertical-align: top;">
						<fmt:message key="inventarioprocedimenti.label.normativa_ue" />
					</td>
					<td>
						<spring-form:textarea id="id_normativa_ue" path="entity.normativaue" rows="4" cols="72"></spring-form:textarea>
						<spring-form:errors path="entity.normativaue" cssClass="error"/> 
						 
					</td>
				</tr>
				<tr id="id_normative_table" style="<%=displayNormative%>;" >
					<td  style="vertical-align: top;">
						<fmt:message key="inventarioprocedimenti.label.normativa_nazionale" />
					</td>
					<td>
						<spring-form:textarea id="id_normativa_nazionale" path="entity.normativana" rows="4" cols="72"></spring-form:textarea>
						<spring-form:errors path="entity.normativana" cssClass="error"/> 
						
					</td>
				</tr>
				<tr id="id_normative_table" style="<%=displayNormative%>;" >
					<td  style="vertical-align: top;">
						<fmt:message key="inventarioprocedimenti.label.normativa_regionale" />
					</td>
					<td>
						<spring-form:textarea id="id_normativa_regionale" path="entity.normativare" rows="4" cols="72"></spring-form:textarea>
						<spring-form:errors path="entity.normativare" cssClass="error"/> 
						
					</td>
				</tr>
				<tr id="id_normative_table" style="<%=displayNormative%>;" >
					<td  style="vertical-align: top;">
						<fmt:message key="inventarioprocedimenti.label.regolamenti" />
					</td>
					<td>
						<spring-form:textarea id="id_regolamenti" path="entity.regolamenti" rows="4" cols="72"></spring-form:textarea>
						<spring-form:errors path="entity.regolamenti" cssClass="error"/>
						
					</td>
				</tr>
				
			</table>
				
			<script type='text/javascript'>
				
				$('procedimento_id').focus();
				
				
			</script>
			
			<!-- Setto il codice dell'ogetto endo come variabile di classe, se non c'è lo metto a null,
			mi serve per evitare errori javascript quando ${inventarioprocedimenti.entity.id.codice} è uguale a null -->
			<c:set var="codEndo" value="null" />
				<c:if test="${inventarioprocedimenti.entity.id.codice!=null}">
					<c:set var="codEndo">${inventarioprocedimenti.entity.id.codice}</c:set>
				</c:if>				
			<script type="text/javascript">
		
			// Se il 
			// 1- codEndo == null allora creo la stringa di plugins senza l'opzione salva, non voglio permettere di salvare il singolo campo di testo in inserimento (evita errori di validazione)
			// 2- codEndo != null allora creo la stringa di plugins con l'opzione salva
			if(${codEndo!='null'})
			{
				var plugins="save,safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable";
			}else
			{
				var plugins="safari,layer,table,advhr,advimage,advlink,emotions,inlinepopups,insertdatetime,preview,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable";
			}
			// Chiamo la funzione che inizializza l'editor di testo
			inizializzazioneEditor(plugins);
			/*
			Funzione che crea l'editor di testo
			*/
			function inizializzazioneEditor(plugins)
			{
				initTextEditors();
				/*
				tinyMCE.init({
						//mode: "exact",   			
						//elements: "applicazione_id,datigenerali_id,id_normativa_ue,id_normativa_nazionale,id_normativa_regionale,id_regolamenti,id_adempimenti",
						//elements: "applicazione_id",
						mode : "textareas",
						theme: "advanced",
						theme_advanced_toolbar_location: "top",
						theme_advanced_toolbar_align: "left",
						plugins: plugins,
						theme_advanced_buttons1: "save,newdocument,|,preview,print,|,search,replace,|,undo,redo,|,bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,|,fullscreen,|,code",
					  	theme_advanced_buttons2: "formatselect,fontselect,fontsizeselect,|,bullist,numlist,|,outdent,indent",
					  	save_onsavecallback : "Editor_Save",
					  	forced_root_block : false,
				        force_br_newlines : true,
				        force_p_newlines : false,
				        theme_advanced_resizing : true
					});
				*/
			}
			
			/* Funzione che viene chiamata quando si clicca sull'icona salva di ogni sezione di testo. Fa la'ggiornamento del campo tramite una chiamata ajax*/
			function Editor_Save() {
				
				codiceprocedimento=${codEndo};
				// recupera il valore del campo di testo
				var ed=tinyMCE.activeEditor;
				// recupera l'id del campo textarea, per cui abbiamo inviato il comando di salva
       			var idAttivo=tinyMCE.activeEditor.id
        		var campoDaModificare='';
	        	if(idAttivo == 'applicazione_id')
	        	{
	        		campoDaModificare='campoapplicazione';
	        	}
	        	if(idAttivo == 'datigenerali_id')
	        	{
	        		campoDaModificare='datigenerali';
	        	}
	        	
	        	if(idAttivo == 'id_normativa_ue')
	        	{
	        		campoDaModificare='normativaue';
	        	}
	        	if(idAttivo == 'id_normativa_nazionale')
	        	{
	        		campoDaModificare='normativana';
	        	}
	        	if(idAttivo == 'id_normativa_regionale')
	        	{
	        		campoDaModificare='normativare';
	        	}
	        	if(idAttivo == 'id_regolamenti')
	        	{
	        		campoDaModificare='regolamenti';
	        	}
	        	if(idAttivo == 'id_adempimenti')
	        	{
	        		campoDaModificare='adempimenti';
	        	}
	        	
	       		new Ajax.Request('ajaxUpdateProprieta.htm', {
					  method: 'post',
					  parameters: {codiceEndo: codiceprocedimento, valore: ed.getContent(), campoDaModificare: campoDaModificare},
					  onSuccess: function(transport){
						  var response = transport.responseText;
						  if(response!=''){
						   	alert(response);
						  }else{
						  	alert("operazione avvenuta correttamente");
						  }
					  },
					  onFailure: function(transport){ 
						var response = transport.responseText;
						console.error("Errore nella ricerca salvaNoteIstanza:" + responseTexts);		
					    }						    		 
				} );		
					
	             
					
				}
			</script>	
			
			<spring-form:hidden id="codiceAlberoproc_id" path="codiceAlberoproc" />
			
		
		</spring-form:form>
	</div>
	<div id="functions">
		<ul>
			<c:if test="${inventarioprocedimenti.entity.id.codice==null}">
				<li><a href="javascript:doSubmit('insert.htm','',document.inviodati)"><fmt:message key="button.insert" /></a></li>
				<%--
				<li><a href="javascript:doSubmit('createCopia.htm','',document.inviodati)"><fmt:message key="button.copia" /></a></li>
				 --%>
			</c:if>
			<c:if test="${inventarioprocedimenti.entity.id.codice!=null}">
				<c:if test="${inventarioprocedimenti.modificaPermessa eq true}">
					<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
					<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li>
				</c:if>
				<li><a href="javascript:historySet('${_urlback}','../inventarioprocedimenti/listallegati.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="button.allegati" /></a></li>
			    <!-- Bottone da rifare con la jaspser report -->
			    <%-- 
			    <li><a href="javascript:historySet('${_urlback}','#','')"><fmt:message key="button.stampa" /></a></li>
			    --%>

			    <li><a href="javascript:historySet('${_urlback}','../oneri/list.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}&idcomendo=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="button.istanze_oneri" /></a></li>
		    
		    
			    
				    	<li><a href="javascript:historySet('${_urlback}','../inventarioprocedimenti/listmodelli.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="button.modelli" /></a></li>
				    	<%--
				    	<li><a href="javascript:historySet('${_urlback}','../inventarioprocqrt/list.htm?inventarioproc.id.codice=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="label.inventarioprocqrt" /></a></li>
				    	 --%>
			   
			    <%--
			    <li><a href="javascript:historySet('${_urlback}','../endocausali/list.htm?inventarioprocedimenti.id.codice=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.endo_causali" /></a></li>
			    
			    
			    <c:if test="${inventarioprocedimenti.entity.software.codice=='TT'}">
			         <li><a href="javascript:historySet('${_urlback}','../inventarioprocedimenti/listmodalita.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.modalita_attivate" /></a></li>
			    </c:if>
			     --%>
			     <%if(ORMHelper.isConsoleRegionale()) {%>
			     <c:if test="${inventarioprocedimenti.entity.software.codice=='TT'}">
			         <li><a href="javascript:historySet('${_urlback}','../inventarioprocedimenti/listmodalita.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="button.modalita_attivate" /></a></li>
			    </c:if>
			   
			    
			    <li><a href="javascript:historySet('${_urlback}','../inventarioprocedimenti/listendoincompatibili.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="button.endo_incompatibili" /></a></li>
				 <%}%>
				 <%--
				<li><a href="javascript:historySet('${_urlback}','../inventarioprocedimenti/listMappingSTP.htm?codiceInventario=${inventarioprocedimenti.entity.id.codice}','')"><fmt:message key="button.listMappingSTP" /></a></li>
				 --%>
				 <li><a href="javascript:historySet('${_urlback}','../inventarioprocedimenti/listtipititolo.htm?codiceendo=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="button.tipi_titolo" /></a></li>
				 
				 <%if(ORMHelper.isConsoleRegionale()) {
				 	%>		 	
						<li><a href="javascript:historySet('${_urlback}','../inventarioprocendo/list.htm?codiceT=${inventarioprocedimenti.entity.id.codice}&codicecomune=${inventarioprocedimenti.entity.id.idcomune}','')"><fmt:message key="button.endo_raggruppamento" /></a></li>
				<%
				  } %>
			 	
			</c:if>
			<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
	<%--//fabrizioc: selenium input type="hidden" id="id-codice" value="${inventarioprocedimenti.entity.id.codice}" / --%>
</body>
</html>