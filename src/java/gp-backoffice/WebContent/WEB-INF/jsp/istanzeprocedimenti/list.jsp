<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.elenco_procedimenti" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.elenco_procedimenti" /></span>

<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="list" />
</jsp:include>
<jsp:include page="../includes/history.jsp">
		<jsp:param name="path" value="../istanzeprocedimenti/list" />
	</jsp:include>
<jsp:include page="../includes/displayGlobalMessages.jsp" >
	<jsp:param name="commandName" value="istanzeprocedimentiCommand" />
</jsp:include>
<div id="subcontent">
<c:import url="/ajax/dettaglioIstanza.htm">
	<c:param name="codIstanza">${istanze.id.codice}</c:param>
</c:import>
<br class="clear" />

		<%
		    Integer sizeListNaturaendo=(Integer)request.getAttribute("sizeListNatureendo");
			String displayIstanzeproceidimenti_inseriti= "display:none;";
			String styledisplayIstanzeproceidimenti_inseriti = "";
			displayIstanzeproceidimenti_inseriti = "";
		    styledisplayIstanzeproceidimenti_inseriti="sezioneDatiMeno";
		    
		    /*
		    String dettaglioColumn="display:none;";
		    boolean isVisible=false;
		    if(request.getAttribute("isVisibleColumnDettaglio")!=null)
			{
			    isVisible=(Boolean)request.getAttribute("isVisibleColumnDettaglio");
			}
		    pageContext.setAttribute("isVisible",isVisible);
		    */
			
		%>
		<c:set var="sizeListNaturaendo" value="<%= sizeListNaturaendo %>" scope="page"/>

	<fieldset><legend> <a
		class="<%=styledisplayIstanzeproceidimenti_inseriti%>"
		id="id_link_istanzeprocedimenti_configurati"
		href="javascript:showHidePanelBase('id_istanzeprocedimenti_configurati_table', 'id_link_istanzeprocedimenti_configurati', ' ', '${pageContext.request.contextPath}/images/','tr',false);"
		title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.elenco_endo_procedimenti_attivati"/>">
		<label for="id_link_istanzeprocedimenti_configurati"><fmt:message key="label.elenco_endo_procedimenti_attivati" /></label> </a> </legend>
	<table width="100%">
		<tr id="id_istanzeprocedimenti_configurati_table">
			<td>
			<form name="istanzeprocedimentiForm" action="list.htm">
			<!-- TABELLA PER MOSTRARE ED GESTIRE GLI ENDO PROCEDIMENTI ATTIVATI -->
			<!-- START -->
			<jmesa:springTableFacade
				id="procedimenti_istanze_id" items="${istanzeprocedimentiList}"
				var="istanzeprocedimenti_var" stateAttr="restore">
				<jmesa:htmlTable>
				<jmesa:htmlRow>
					<jmesa:htmlColumn  property="id.codiceinventario" titleKey="label.codice" width="2%" />
					<c:if test="${isFamiglieendoAttive==true}">
						<jmesa:htmlColumn property="inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo" titleKey="label.famiglia_endo" />
					</c:if>
					<jmesa:htmlColumn property="inventarioprocedimenti.tipoendo.tipo" titleKey="label.categoria_endo" />
					
					<c:if test="${empty istanzeprocedimenti_var.descrizioneEndoprocedimento}">
						<jmesa:htmlColumn width="15%" property="inventarioprocedimenti.procedimento" titleKey="label.procedimento" />
					</c:if>
					<c:if test="${not empty istanzeprocedimenti_var.descrizioneEndoprocedimento}">
						<jmesa:htmlColumn width="15%" property="descrizioneEndoprocedimento" titleKey="label.procedimento" />
					</c:if>
					<jmesa:htmlColumn width="8%" property="inventarioprocedimenti.amministrazioni.amministrazione" titleKey="label.amministrazione" />
					<jmesa:htmlColumn property="inventarioprocedimenti.naturaendo.natura"  titleKey="label.natura"/>
					<jmesa:htmlColumn property="data" titleKey="label.data" sortable="false" filterable="false">
						<input type="text" id="data_attivazione_id${istanzeprocedimenti_var.id.codiceinventario}"
							value="<fmt:formatDate value="${istanzeprocedimenti_var.dataattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" />"
							name="inventarioprocedimenti.dataattivazione" size="8" onchange="changeData(this,'inventario${istanzeprocedimenti_var.id.codiceinventario}','istanza${istanzeprocedimenti_var.id.codiceistanza}${istanzeprocedimenti_var.id.codiceinventario}','result_${istanzeprocedimenti_var.id.codiceinventario}');"
							onblur="isValidDate(this,true);"
							/>
						<init:calendar imagePath="/images/cal.gif"
							idImage="caldataattivazione${istanzeprocedimenti_var.id.codiceinventario}" idInput="data_attivazione_id${istanzeprocedimenti_var.id.codiceinventario}"
							textKey="label.calendar" javascriptAction="changeData('data_attivazione_id${istanzeprocedimenti_var.id.codiceinventario}','inventario${istanzeprocedimenti_var.id.codiceinventario}','istanza${istanzeprocedimenti_var.id.codiceistanza}${istanzeprocedimenti_var.id.codiceinventario}','result_${istanzeprocedimenti_var.id.codiceinventario}');" />
						<input
							id="inventario${istanzeprocedimenti_var.id.codiceinventario}"
							type="hidden" name="id.codiceinventario "
							value="${istanzeprocedimenti_var.id.codiceinventario}" />
						<input
							id="istanza${istanzeprocedimenti_var.id.codiceistanza}${istanzeprocedimenti_var.id.codiceinventario}"
							type="hidden" name="id.codiceistanza"
							value="${istanzeprocedimenti_var.id.codiceistanza}" />
					</jmesa:htmlColumn>
					<jmesa:htmlColumn  property="1" titleKey="label.avvia" sortable="false" filterable="false" headerEditor="org.jmesa.custom.AvviaHeaderEditor">
						<input type="hidden" value="${istanzeprocedimenti_var.id.codiceinventario}"  id="aut_id${istanzeprocedimenti_var.id.codiceinventario}" />
						<c:if test="${isModificaIstanza eq true }">	
							<input
								id="checkbox_aut_id${istanzeprocedimenti_var.id.codiceinventario}"
								type="checkbox"
								value="1"
								name="autorizzazione"
								${istanzeprocedimenti_var.perprovvedimento?'checked':''}  onclick="changetipo${istanzeprocedimenti_var.id.codiceinventario}('aut_id${istanzeprocedimenti_var.id.codiceinventario}','istanza${istanzeprocedimenti_var.id.codiceistanza}${istanzeprocedimenti_var.id.codiceinventario}','checkbox_aut_id${istanzeprocedimenti_var.id.codiceinventario}','autorizzazione','result_${istanzeprocedimenti_var.id.codiceinventario}','false');" />
						</c:if>
						<c:if test="${isModificaIstanza eq false }">
							<input
								id="checkbox_aut_id${istanzeprocedimenti_var.id.codiceinventario}"
								type="checkbox"
								value="1"
								name="autorizzazione"
								disabled="disabled"
								${istanzeprocedimenti_var.perprovvedimento?'checked':''}
								/>
						</c:if>	
						<span
							id="result_aut_${istanzeprocedimenti_var.id.codiceinventario}"
							style="display: none"></span>
                    </jmesa:htmlColumn>
					<jmesa:htmlColumn property="2" titleKey="label.acquis" sortable="false" filterable="false" headerEditor="org.jmesa.custom.AcquisitoHeaderEditor">
						<input type="hidden" value="${istanzeprocedimenti_var.id.codiceinventario}"  id="acq_id${istanzeprocedimenti_var.id.codiceinventario}" />
						<c:if test="${isModificaIstanza eq true }">
						<input
							id="checkbox_acq_id${istanzeprocedimenti_var.id.codiceinventario}"
							type="checkbox"
							value="1"
							name="acquisizione"
							${istanzeprocedimenti_var.acquisito?'checked':''} onclick="changetipo${istanzeprocedimenti_var.id.codiceinventario}('acq_id${istanzeprocedimenti_var.id.codiceinventario}','istanza${istanzeprocedimenti_var.id.codiceistanza}${istanzeprocedimenti_var.id.codiceinventario}','checkbox_acq_id${istanzeprocedimenti_var.id.codiceinventario}','acquisizione','result_${istanzeprocedimenti_var.id.codiceinventario}','false');" />
						</c:if>
						<c:if test="${isModificaIstanza eq false }">
						<input
							id="checkbox_acq_id${istanzeprocedimenti_var.id.codiceinventario}"
							type="checkbox"
							value="1"
							name="acquisizione"
							disabled="disabled"
							${istanzeprocedimenti_var.acquisito?'checked':''}
							/>
						</c:if>		
						<span
							id="result_acq_${istanzeprocedimenti_var.id.codiceinventario}"
							style="display: none"></span>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="3" titleKey="label.sigla_cds" sortable="false" filterable="false" headerEditor="org.jmesa.custom.CDSHeaderEditor">
						<input type="hidden" value="${istanzeprocedimenti_var.id.codiceinventario}"  id="conf_id${istanzeprocedimenti_var.id.codiceinventario}" />
						<c:if test="${isModificaIstanza eq true }">
						<input
							id="checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}"
							type="checkbox"
							value="${istanzeprocedimenti_var.id.codiceinventario}"
							name="commissioni"
							${istanzeprocedimenti_var.flagCommissione?'checked':''} onclick="changetipo${istanzeprocedimenti_var.id.codiceinventario}('conf_id${istanzeprocedimenti_var.id.codiceinventario}','istanza${istanzeprocedimenti_var.id.codiceistanza}${istanzeprocedimenti_var.id.codiceinventario}','checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}','commissioni','result_${istanzeprocedimenti_var.id.codiceinventario}','true');" />
						</c:if>
						<c:if test="${isModificaIstanza eq false }">
						<input
							id="checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}"
							type="checkbox"
							value="${istanzeprocedimenti_var.id.codiceinventario}"
							name="commissioni"
							disabled="disabled"
							${istanzeprocedimenti_var.flagCommissione?'checked':''}/>
						</c:if>
						<span
							id="result_commis_${istanzeprocedimenti_var.id.codiceinventario}"
							style="display: none"></span>
							
							<script type="text/javascript">
							
							/* Il javascript fa la chiamata ajax per modificare il checkbox selszionato */			
							function changetipo${istanzeprocedimenti_var.id.codiceinventario}(id, istanza,obj, parametro, result,isFlagCommissioni) {
								if (controlloCheckBox${istanzeprocedimenti_var.id.codiceinventario}(obj) && aggiornaCheckBox${istanzeprocedimenti_var.id.codiceinventario}(isFlagCommissioni)) {
									var inventario = document.getElementById(id);
									var istanza = document.getElementById(istanza);
									if(document.getElementById(obj).checked)
									{
										  var parametrovalue=1;
									}
									else
									{
										  var parametrovalue=0;
									}
									new Ajax.Request(
											'${pageContext.request.contextPath}/istanzeprocedimenti/ajaxChangetipo.htm?codiceinvetario='+ inventario.value+ '&codiceistanza='+ istanza.value + '&' + parametro + '=' + parametrovalue, {
												method : 'post',
												onSuccess : function(transport) {
													
													var risultato= transport.responseText;
													var risultatoSplit=risultato.split("#");			
													dijit.showTooltip(risultatoSplit[0], dojo.byId(obj));
													setTimeout(function(){dijit.hideTooltip(dojo.byId(obj))},1000);
													if(risultatoSplit[1]=='true')
													{
														document.getElementById('dettaglioColumn_id'+risultatoSplit[2]).style.display="";
													}else
													{
														document.getElementById('dettaglioColumn_id'+risultatoSplit[2]).style.display="none";
													}
												},
												onFailure : function(transport) {
													$(result).innerHTML = transport.responseText;
													$(result).className = 'error_ajax_call'
													$(result).style.display = '';
													$(result).pulsate({
														pulses : 2,
														duration : 1.0
													});
												}
											});
								}
							}							
	                        /*Javascript che controlla la logica lato client per l'aggiornamento dei checkbox   */
							function aggiornaCheckBox${istanzeprocedimenti_var.id.codiceinventario}(isFlagCommissioni)
							{
								    /*Se true abbiamo selazionato il flag CDS*/
									if(isFlagCommissioni=='true')
									{
										// Se true significa che è passato dallo stato false a quello true
										if(document.getElementById('checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}').checked==true)
										   	{
												// Notifica che verranno disattivati gli altri check box se viene premuto ok
												if (checkConfirmMessage('<fmt:message key="alert.verrano_disabilitati_avvia_e_acq" />')) {
													document.getElementById('checkbox_aut_id${istanzeprocedimenti_var.id.codiceinventario}').checked=false;
													document.getElementById('checkbox_acq_id${istanzeprocedimenti_var.id.codiceinventario}').checked=false;
													return true;
												}
												else{// si annulla l'operazione e si rimette il flag CDS a false
													document.getElementById('checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}').checked=false;
													return false;
												}
											}
									}else/* se false allora abbiamo aggiornato o il checkbox perprovvedimento o acquisito*/
									{
										/*Se true significa che andremo ad aggiornare a false il flag CDS*/
										if(document.getElementById('checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}').checked==true)
										{
											// Notifica che verranno disattivato il  check box CDS se viene premuto ok
											if (checkConfirmMessage('<fmt:message key="alert.verra_disabilitato_commiss" />')) {
												/* Gestisce la logica a secondo di quale dei due checkbox selezionati */
												if(document.getElementById('checkbox_aut_id${istanzeprocedimenti_var.id.codiceinventario}').checked==true)
							  					{
							  						document.getElementById('checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}').checked=false
							  					}
							  					if(document.getElementById('checkbox_acq_id${istanzeprocedimenti_var.id.codiceinventario}').checked == true)
									  			{
													document.getElementById('checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}').checked=false
												}
							  					return true;
											}else/* Annullo, riporto tutto allo stato iniziale. */
											{
												document.getElementById('checkbox_acq_id${istanzeprocedimenti_var.id.codiceinventario}').checked=false;
												document.getElementById('checkbox_aut_id${istanzeprocedimenti_var.id.codiceinventario}').checked=false;
												return false;
												}
									}else{
										return true;
									}
										
									}
								}
							/*Funzione javascript che controlla che ci sia sempre almeno un checkbox selezionato.*/
							function controlloCheckBox${istanzeprocedimenti_var.id.codiceinventario}(obj) 
							{
							    if (document.getElementById('checkbox_aut_id${istanzeprocedimenti_var.id.codiceinventario}').checked == false
										&& document.getElementById('checkbox_acq_id${istanzeprocedimenti_var.id.codiceinventario}').checked==false
										&& document.getElementById('checkbox_comm_id${istanzeprocedimenti_var.id.codiceinventario}').checked==false) {
									alert('<fmt:message key="alert.necessario_selezionare_un_elemento"/>');
									document.getElementById(obj).checked=true
									return false;
								}
								return true;
							}
						    </script>
					</jmesa:htmlColumn>
					<jmesa:htmlColumn property="5" titleKey="label.edit.record" sortable="false" filterable="false" width="3%">
						<c:set var="visualizzaDett" scope="page" value="display:none;" ></c:set>
						<c:if test="${(not empty istanzeprocedimenti_var.acquisito and istanzeprocedimenti_var.acquisito eq true) or (not empty istanzeprocedimenti_var.protNum or not empty istanzeprocedimenti_var.protDel or not empty istanzeprocedimenti_var.note)}">
							<c:set var="visualizzaDett" scope="page" value="" ></c:set>
						</c:if>
						<div id="dettaglioColumn_id${istanzeprocedimenti_var.id.codiceinventario}" style="${visualizzaDett}">
							<a class="dettaglioColumn" style="cursor: pointer;" onclick="javascript:tabMessaggioEndo('dettaglioDialogDiv${istanzeprocedimenti_var.id.codiceinventario}','inventario${istanzeprocedimenti_var.id.codiceinventario}','istanza${istanzeprocedimenti_var.id.codiceistanza}${istanzeprocedimenti_var.id.codiceinventario}');"> 
								<label><fmt:message key="label.edit.record.image" /></label> 
							</a>
							<div dojoType="dijit.Dialog" id="dettaglioDialogDiv${istanzeprocedimenti_var.id.codiceinventario}" title="<fmt:message key="label.atto" />: ">
								<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 500px">
									<div id="dettaglio${istanzeprocedimenti_var.id.codiceinventario}"></div>
								</div>
								<!-- Permette di sovrascivere l'evento di chiusura del dialog e in più aggiungere altre funzioni 
								     In questo caso alla chiusa del dialog chiude anche il calendare se era aperto-->
								<script type="text/javascript">
									dojo.addOnLoad(function() {
										myDialog = dijit.byId("dettaglioDialogDiv${istanzeprocedimenti_var.id.codiceinventario}");
										dojo.connect(myDialog, "hide", function(){
								    	// alla chiusura del div di dialog devo chiudere anche il calendar se è ancora aperto
									    	if (typeof calendar != "undefined"){
									    		calendar.hide();
											}
								        });
									});
								</script>
							</div>
						    </div>
					</jmesa:htmlColumn>
					<c:if test="${istanzeprocedimenti_var.istanza.creatoDaStc eq true }">
					<jmesa:htmlColumn  property="6" titleKey="label.codici_stc" sortable="false" filterable="false" width="3%">
							<c:if test="${not empty istanzeprocedimenti_var.inventarioprocedimenti.inventarioprocedimentipeoples }">
							<a class="listaRecordColumn"
								style="cursor: pointer;" onclick="javascript:tabListCodiciSTC('listaCodiciSTCDialogDiv${istanzeprocedimenti_var.id.codiceinventario}','${istanzeprocedimenti_var.id.codiceinventario}')"
								title="<fmt:message key="label.lista_codici_stc" />"> <label><fmt:message
								key="label.azioni" /></label> 
							</a>
							<div dojoType="dijit.Dialog" id="listaCodiciSTCDialogDiv${istanzeprocedimenti_var.id.codiceinventario}" title="<fmt:message key="label.lista_codici_stc" />: ">
								<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 500px ;height: 100%" >
									<div id="listaCodiciSTC${istanzeprocedimenti_var.id.codiceinventario}"></div>
								</div>
							</div>
							</c:if>
					</jmesa:htmlColumn>
					</c:if>
					<jmesa:htmlColumn property="4" titleKey="label.azioni" sortable="false" filterable="false" width="3%">
						<c:if test="${isModificaIstanza eq true }">
							<a class="eliminaRiga"
								href="javascript:doHref('deleteIstanzeprocedimenti.htm?codiceinvetario=${istanzeprocedimenti_var.id.codiceinventario}&codiceistanza=${istanzeprocedimenti_var.id.codiceistanza}','<fmt:message key="javascript.confirm.disattivazione_inventario_procedimento" />')"
								title="<fmt:message key="label.elimina" />"> <label><fmt:message
								key="label.azioni" /></label> </a>
							<span id="result_${istanzeprocedimenti_var.id.codiceinventario}"
								style="display: none"></span>
						</c:if>		
					</jmesa:htmlColumn>
			</jmesa:htmlRow>
		</jmesa:htmlTable>
	</jmesa:springTableFacade> 
	<input type="hidden" value="${istanze.id.codice}" name="codiceIstanza" /></form>
	<!-- TABELLA PER MOSTRARE ED GESTIRE GLI ENDO PROCEDIMENTI ATTIVATI -->
	<!-- END -->
	</td>
	</tr>
	</table>
	</fieldset>
	</div>

	<br class="clear"/><br class="clear"/>
	    <!-- GESTIONE DELLA TABELLA PER SCEGLIERE GLI ENDO DA ATTIVARE -->
		<spring-form:form commandName="istanzeprocedimentiCommand" name="inviodati">
	
				<%
				Boolean isAggiornato=(Boolean)request.getAttribute("aggiornamento");
				String displayEndoprocedimenti_attivabili= "display:none;";
				String styledisplayEndoprocedimenti_attivabili = "display:none;";
				String displayRicercatestuale = "display:none;";
				if(isAggiornato==false)
				{
				    displayRicercatestuale = "display:none;";
					displayEndoprocedimenti_attivabili = "display:none;";
					styledisplayEndoprocedimenti_attivabili="sezioneDatiPiu";
				}else
				{
				    displayRicercatestuale = "";
				    displayEndoprocedimenti_attivabili = "";
					styledisplayEndoprocedimenti_attivabili="sezioneDatiMeno";
				}
				String displayTipifamiglieendo= "display:none;";
				String styleTipifamiglieendo = "display:none;";
		
				displayTipifamiglieendo = "display:none;";
				styleTipifamiglieendo="sezioneDatiPiu";
				
				String displayTipicategorieendo= "display:none;";
				String styleTipicategorieendo = "display:none;";
		
				displayTipicategorieendo = "display:none;";
				styleTipicategorieendo="sezioneDatiPiu";
				
	
				
			%>
			<fieldset>
				<legend> 
				<a class="<%=styledisplayEndoprocedimenti_attivabili%>"
					id="id_link_endoprocedimenti_attivabili"
					href="javascript:showHidePanelBase('id_endoprocedimenti_attivabili_table', 'id_link_endoprocedimenti_attivabili','','${pageContext.request.contextPath}/images/','tr',false);"
					title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.elenco_endo_procedimenti_attivabili"/>">
					<label for="id_link_endoprocedimenti_attivabili"><font style="font-weight: bold; text-transform: uppercase;"><fmt:message
						key="label.elenco_endo_procedimenti_attivabili" /></font>
					</label> 
				</a>
				</legend>
				<!-- TABELLA PER MOSTRARE ED GESTIRE GLI ENDO PROCEDIMENTI ATTIVABILI -->
				<!-- END -->

                <!-- Tabella che racchiudte la funzionalità di ricerca testuale -->
				<table>
				   
				</table>
				<table  width="100%">
				
					<tr class="titoloSezione" id="id_endoprocedimenti_attivabili_table" style="<%=displayEndoprocedimenti_attivabili%>;">
					    <%
					    	int i=0;
					    %>
					    <c:forEach items="${listNatureendo}" var="naturaendo" varStatus="indiceNaturaendo" >
					    <c:set scope="page" value="${naturaendo.transietFlagBinariodipendenze}" var="compatibilita">
					      
					    </c:set>
						<td>
						    <c:if test="<%=i==0%>">
						    	<init:help idHelp="help_filtri_natura_endo" textKey="help.configurazione_filtri_nature_default"/>     
							</c:if>
							${naturaendo.natura}
							<input type="checkbox" value="${naturaendo.id.codice}" ${naturaendo.transietFlagBinariodipendenze?'checked':''} id="id_check_natura${indiceNaturaendo.index}" onclick="aggiornaCheckEndo('id_check_natura${indiceNaturaendo.index}',${istanze.id.codice},${sizeListNaturaendo})"/>
			
						</td>
						<%
						   i++;
						%>
						</c:forEach>
					</tr>
					<tr id="id_endoprocedimenti_attivabili_table"  style="<%=displayRicercatestuale%>" >
				   		<td>
				   		 	    <b><fmt:message key="label.ricerca_testuale"/></b>
				   	  			<init:help idHelp="help_ricerca_testuale_endo" textKey="help.ricerca_testuale_endo"/> 
				   		</td>
				    </tr>
					<tr id="id_endoprocedimenti_attivabili_table"  style="<%=displayRicercatestuale%>" >
						<td>
							<fmt:message key="alberoproc.label.alberoprocEndo_famigliendo" />
						</td>
						<td>
						<jsp:include page="../includes/autocompletergenerico.jsp" >
							<jsp:param name="idElemento" value="famigliaendo" />		
							<jsp:param name="propertyPath" value="entity.inventarioprocedimenti.tipoendo.tipifamiglieendo" />				
							<jsp:param name="pathPropertyDescription" value="entity.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo" />
							<jsp:param name="pathPropertyCode" value="entity.inventarioprocedimenti.tipoendo.tipifamiglieendo.id.codice" />
							<jsp:param name="autocompleterAjax" value="findTipifamiglieendoSWeTT.htm" />	
							<jsp:param name="titleKey" value="label.ricerca_tipo_famiglia_endo" />
							<jsp:param name="id_help" value="help_famiglia" />
							<jsp:param name="help" value="help.search_famiglie_e_categorie_endo_archivi_base" />
						</jsp:include>
						</td>
					</tr>
					<tr id="id_endoprocedimenti_attivabili_table"  style="<%=displayRicercatestuale%>">
						<td>
							<fmt:message key="alberoproc.label.alberoprocEndo_tipiendo" />
						</td>
						<td>
							<script type="text/javascript">
								function filtertipiendo(element, entry) { 
									//return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+"&codicesoftware="+document.getElementById("famigliaendo_software_hidden").value;
									return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value;
									}
							</script>
							<input id="tipiendo_id"  class="searchbox" onchange="checkValue(this,'tipiendo_hidden');" onkeydown="javascript:return searchAll(this,event)" size="67"/>
							<init:autocompleter callBack="filtertipiendo" methodAjax="findTipiendoSWeTT.htm" idHidden="tipiendo_hidden" idInput="tipiendo_id" inputTitleKey="label.ricerca_tipiendo"></init:autocompleter>
							<input name="entity.inventarioprocedimenti.tipiendo.id.codice" type="hidden" id="tipiendo_hidden"  />
						</td>
					</tr>
					<tr id="id_endoprocedimenti_attivabili_table"  style="<%=displayRicercatestuale%>">
						<td>
							<fmt:message key="alberoproc.label.alberoprocEndo_inventarioprocedimento" />
						</td>
						
						<style type="text/css">
							.rowTableSelected {	
							    color: red;  
							}
						
						</style>
						
						<td>
							<script type="text/javascript">

								function jumpTo(divId){
									var new_position = jQuery(divId).offset();
									window.scrollTo(new_position.left,new_position.top);
								}
							
								function inventarioCallBack(inputField,listItem){
									var a = listItem.id;
									document.getElementById('inventarioprocedimento_id').value = inputField.value;
									document.getElementById('inventarioprocedimento_hidden').value = a;
									var codice_endo=document.getElementById('inventarioprocedimento_hidden').value;
									document.getElementById('inventarioprocedimento_id_hidden').value = a;
									$('inventarioprocedimento_id_choices').fade();									
									new Ajax.Request('../json/getInventarioprocedimento.htm', {
										  method: 'post',
										  parameters: {id: codice_endo},
										  onSuccess: function(transport){ 
											var response = transport.responseText;
											var json = response.evalJSON();
											var tipo_endo=json.codiceTipoendo;
											var tipofamiglia_endo=json.codiceFamigliaendo;											
											showPanel('id_link_tipi_famiglie_endo_table'+tipofamiglia_endo, 'id_link_tipi_famiglie_endo'+tipofamiglia_endo, '','${pageContext.request.contextPath}/images/','tr',false);
											mostraEndo(tipo_endo,${istanze.id.codice},${sizeListNaturaendo},true);
											setTimeout(function(){jumpTo('#id_endo_'+codice_endo);},2000);																									
										  },
										  onFailure: function(transport){ 
											var response = transport.responseText; 
										    alert("Errore nella ricerca del procedimento!");
										  }						    		 
									} );									
								}
                                /**
                               	 Il codice software verrà passato di default TT.
                                */
								function filterinventario(element, entry) { 
									//Permette di recuperare i codici delle nature endo che sono state selezionate , in modo che la ricerca non presenti
									// tutti gli endo procedimenti, ma solo quelli con la natura edo selezionata. 
									var sizeList=${sizeListNaturaendo};
									var codici="";
									for ( var i = 0; i < sizeList; i++) {
										if(document.getElementById('id_check_natura'+i).checked)
										{
											codici = codici+ document.getElementById('id_check_natura'+i).value+",";
										}
									}
									codici=codici.substring(0, codici.length-1);
									//return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_hidden").value+"&codicesoftware="+document.getElementById("famigliaendo_software_hidden").value+"&codiciNaturaEndo="+codici;
									//return entry + "&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_hidden").value+"&codicesoftware=TT&codiciNaturaEndo="+codici+"&codiciEndoAttivati=${codiciInvetarioprocedimenti}";
									return entry +"&codiceIstanza=${istanze.id.codice}&codiceFamiglia=" + document.getElementById("famigliaendo_hidden").value+ "&codiceTipologia=" + document.getElementById("tipiendo_hidden").value+"&codiciNaturaEndo="+codici;
									}
							</script>
							<spring-form:input id="inventarioprocedimento_id" path="entity.inventarioprocedimenti.procedimento" cssClass="searchbox" onchange="checkValue(this,'inventarioprocedimento_hidden')" onkeydown="javascript:return searchAll(this,event)" size="67"/>
							<init:autocompleter methodAjax="findByTipiendoAndNonAttivatiPerIstanzaAndDescrizione.htm" idHidden="inventarioprocedimento_hidden" idInput="inventarioprocedimento_id" callBack="filterinventario" afterUpdateElement="inventarioCallBack" inputTitleKey="label.ricerca_inventarioprocedimento"/>
							<spring-form:errors path="entity.inventarioprocedimenti" cssClass="error"/> 
							<spring-form:hidden id="inventarioprocedimento_hidden" path="entity.inventarioprocedimenti.id.codice"  />
							<spring-form:hidden id="inventarioprocedimento_id_hidden" path="entity.inventarioprocedimenti.id.codice"  />
						</td>
					</tr>
					<tr id="id_endoprocedimenti_attivabili_table"><td>&nbsp;</td></tr>
					<tr id="id_endoprocedimenti_attivabili_table" style="<%=displayEndoprocedimenti_attivabili%>;">
						<td colspan="<%=i%>">
						  <!-- TABELLA PER MOSTRARE ED GESTIRE LA LISTA DELLE FAMIGLIE ENDO -->
						  <!-- START -->
						  <table width="100%">
						       <c:forEach items="${listTipifamigliendo}" var="tipifamigliendo" varStatus="a">
						       		<tr>
										<td>
											<a class="<%=styleTipifamiglieendo%>" id="id_link_tipi_famiglie_endo${tipifamigliendo.id.codice}" href="javascript:showHidePanelBase('id_link_tipi_famiglie_endo_table${tipifamigliendo.id.codice}', 'id_link_tipi_famiglie_endo${tipifamigliendo.id.codice}', '','${pageContext.request.contextPath}/images/','tr',false);"	title="<fmt:message key="label.mostra_nasconde_sezione" />${tipifamigliendo.tipo}">
												<label for="id_link_tipi_famiglie_endo${tipifamigliendo.id.codice}"><font style="color: #800000; font-weight: bold; text-transform: uppercase;">${tipifamigliendo.tipo}</font></label>
											</a>
										</td>
							   		</tr>					   		
							   		<tr id="id_link_tipi_famiglie_endo_table${tipifamigliendo.id.codice}" style="<%=displayTipifamiglieendo%>;">
							   				<td style="padding-left: 25px;">
							   				   <!-- TABELLA PER MOSTRARE ED GESTIRE LA LISTA DEI TIPI ENDO -->
						  					   <!-- START -->
							   				    <table width="100%">
							   				    	<%i=0; %>
							   				    	<c:forEach items="${tipifamigliendo.tipiendos}" var="tipiendo" varStatus="b">
							   				    	<c:if test="${fn:length(tipiendo.inventarioprocedimentis)>=0}">
							   				    	<tr>
							   				    	  <td style="color: #800000">
							   				    	  	<a class="<%=styleTipicategorieendo%>" id="id_link_tipi_categorie_endo${tipiendo.id.codice}" href="javascript:mostraEndo(${tipiendo.id.codice},${istanze.id.codice},${sizeListNaturaendo},false)" title="<fmt:message key="label.mostra_nasconde_sezione" />${tipiendo.tipo}">
															<label for="id_link_tipi_categorie_endo${tipiendo.id.codice}" class="red"><font style="color: #800000; font-weight: bold; text-transform: uppercase;">${tipiendo.tipo}</font></label>
														</a>
							   		  				  </td>
							   				    	</tr>
							   				    	<tr id="id_link_tipi_categorie_endo_table${tipiendo.id.codice}" style="<%=displayTipicategorieendo%>;">
							   				    		<td style="padding-left: 25px;">
							   				    		<div id="listaInventarioprocedimenti${tipiendo.id.codice}">
							   				    		<!-- TABELLA  PER MOSTRARE ED GESTIRE LA LISTA DELLE ISTANZE PROCEDIMENTI DA ATTIVARE 
							   				    		     CREATA TRAMITE UNA CHIAMATA AJAX-->
						  					  			</div>
			
							   				    		  <!-- FINE -->
							   				    		</td>
							   				    	</tr>
							   				    	</c:if>
							   				    	<%i++; %>
							   				    	</c:forEach>
							   				    	
							   				    </table>
							   				     <!-- TABELLA PER MOSTRARE ED GESTIRE LA LISTA DEI TIPI ENDO -->
						  					   	 <!-- END -->
							   				</td>
							   		</tr>
						       </c:forEach>
						  </table>
						  <!-- TABELLA PER MOSTRARE ED GESTIRE LA LISTA DELLE FAMIGLIE ENDO -->
					      <!-- END -->
						</td>
					</tr>
				<c:if test="${isModificaIstanza eq true }">			
					<tr class="functions"   id="id_endoprocedimenti_attivabili_table" style="<%=displayEndoprocedimenti_attivabili%>;">
						<td colspan="<%=i%>">
						    <div id="functions">
								<ul>
									<li><a href="javascript:doSubmit('insertEndo.htm?codiceIstanza=${istanze.id.codice}','',document.inviodati);"><fmt:message key="button.update" /></a></li>
								</ul>
					        </div>
					     </td>   
					</tr>
				</c:if>
			</table>
			
	</fieldset>
	</spring-form:form>



	<div id="functions">
			<ul>
			<c:if test="${fn:length(istanzeprocedimentiList)>0}">
			    <li><a href="javascript:historySet('${_urlback }','../istanzeallegati/list.htm?codiceIstanza=${istanze.id.codice}','') " title="<fmt:message key="button.gestione_allegati" />"><fmt:message key="button.gestione_allegati" /></a></li>
			    <li><a href="javascript:historySet('${_urlback }','../istanzeprocedimenti/riepilogo.htm?codiceIstanza=${istanze.id.codice}','') " title="<fmt:message key="button.riepilogo_istanzeprocedimenti" />"><fmt:message key="button.riepilogo_istanzeprocedimenti" /></a></li>
			</c:if>
				<c:if test="${not inite:contains(oggettiDisabilitatiSetInRequest, 'btnElaborazione')}">						
					<li><a href="javascript:historySet('${_urlback}','../movimenti/listElaborazione.htm?codiceIstanza=${istanze.id.codice}','')"><fmt:message key="button.elaborazione" /></a></li>						
				</c:if>	
				<li><a href="javascript:historyBack();"><fmt:message key="button.back" /></a></li>
			</ul>
	</div>

	<%-- 
	1- changeData(id, obj, obj1, result) : 		     	    		CAMBIA DINAMICAMNETE TRAMITE UNA CHIAMATA AJAX LA DATA DI UN ENDO PROCEDIMENTO ATTIVATO
							 									    id:     è l'id del campo che contiene il riferimento alla codice inventario 
						                                            obj:    è l'id del campo che contiene il riferimento alla codice istanza
						                                            obj1:   è l'id del campo che contiene il riferimento alla data passata
						                                            result: è l'id del campo in cui viene mostrato il messaggio di aggiornamento avvenuto
				                                            
	
    2- tabMessaggioEndo(divId, id, istanza) : 						APRE UN AFINESTRA DI DIALOG PER SALVARE DEI DATI                                                                        
    3- dettaglioTab(id, istanza) : 									EFFETTUA LA CHIAMATA AJXA PER LA MOFIFICA DEI DATI INSERITI NEI CAMPI DEL 
    																FORM DELLA FINESTRA DIALOG APERTA
    																
 	4- listInventarioprocedimenti(codtipiendo,codistanza,sizeList): CARICA TRAMITE UNA CHIAMATA AJXA GLI ENDO PROCEDIMENTI CHE POSSONO 
 																	ESSERE ATIVATI
 																	
 	5- aggiornaCheckEndo(id,codiceIstanza,sizeList) :				CARICA LE NATURE ENDO PER CUI VERRANNO FILTRATI GLI ENDO 
 																	PROCEDIMENTI ATTIVABILI
 
 	--%> 

	<script type="text/javascript">
		var _jmesaUrl = 'list.htm?codiceIstanza=${istanze.id.codice}&';
	
		
		function changeData(id, obj, obj1, result) {
			var a = document.getElementById(obj);
			var b = document.getElementById(obj1);
			var data = document.getElementById(id);
			new Ajax.Request(
					'${pageContext.request.contextPath}/istanzeprocedimenti/ajaxChangeData.htm?codiceinvetario='+ escape(a.value)+ '&codiceistanza='+ escape(b.value)+ '&data=' + escape(data.value), {
						method : 'post',
						onSuccess : function(transport) {
							dijit.showTooltip(transport.responseText, dojo.byId(id));
							setTimeout(function(){dijit.hideTooltip(dojo.byId(id))},1000);
						},
						onFailure : function(transport) {
							$(result).innerHTML = transport.responseText;
							$(result).className = 'error_ajax_call'
							$(result).style.display = '';
							$(result).pulsate({
								pulses : 2,
								duration : 1.0
							});
						}
					});
		}
		
		
		
			
		function setOneCheckBox(checkbox1, checkbox2) {
	
			document.getElementById(checkbox1).checked = false;
			document.getElementById(checkbox2).checked = false;
		}
	
		
		
		function tabMessaggioEndo(divId, id, istanza){
			
				dijit.byId(divId).show();
			    dettaglioTab(id, istanza);		
		}
		
		
		function dettaglioTab(id, istanza) {
			var inventario = document.getElementById(id);
			var istanza = document.getElementById(istanza);		
				new Ajax.Request(
						'${pageContext.request.contextPath}/ajax/dettaglioIstanzaprocedimento.htm?codiceinventario='+ inventario.value+ '&codiceistanza='+ istanza.value,
						{
							method : 'post',
							onSuccess : function(transport) {							
								var response = transport.responseText;							
								$("dettaglio" + inventario.value).innerHTML = parseScript(response);
								applyStyle();				
							},
							onFailure : function(transport) {
								var response = transport.responseText;
								alert(response);
							}
						});
			}
		
		
		function tabListCodiciSTC(divId, id){
			
			dijit.byId(divId).show();
			listCodiciSTC(id);		
		}
		
		function listCodiciSTC(id) {
			
				new Ajax.Request(
						'${pageContext.request.contextPath}/inventarioprocedimenti/ajaxListaMappingCodiceSTC.htm?codiceinventario='+ id,
						{
							method : 'post',
							onSuccess : function(transport) {							
								var response = transport.responseText;
								$("listaCodiciSTC" + id).innerHTML = parseScript(response);
								applyStyle();				
							},
							onFailure : function(transport) {
								var response = transport.responseText;
								alert(response);
							}
						});
			}
		
		
	
		
		
		function listInventarioprocedimenti(codtipiendo,codistanza,sizeList){
			
			var codici="";
			for ( var i = 0; i < sizeList; i++) {
				if(document.getElementById('id_check_natura'+i).checked)
				{
					codici = codici+ document.getElementById('id_check_natura'+i).value+",";
				}
			}
			codici=codici.substring(0,codici.lastIndexOf(","));	
			new Ajax.Request(
			'${pageContext.request.contextPath}/istanzeprocedimenti/ajaxLoadInventarioprocedimenti.htm?codicetipiedo='+ codtipiendo+'&codiceistanza='+codistanza+'&codiciNature='+codici,
			{
				method : 'post',
				onSuccess : function(transport) {
					var response = transport.responseText;
					
					$("listaInventarioprocedimenti"+codtipiendo).innerHTML = parseScript(response);
					$("listaInventarioprocedimenti"+codtipiendo).appear();
					applyStyle();
				},
				onFailure : function(transport) {
					var response = transport.responseText;
					alert(response);
				}
			});
		}
		
		
		
		function aggiornaCheckEndo(id,codiceIstanza,sizeList)
		{
			
			var codici="";
			if(document.getElementById(id).checked==false)
			{
			for ( var i = 0; i < sizeList; i++) {
				
				if(document.getElementById('id_check_natura'+i).checked && document.getElementById('id_check_natura'+i).value>document.getElementById(id).value)
				{
					codici = codici+ document.getElementById('id_check_natura'+i).value+",";
				}else
				{
					document.getElementById('id_check_natura'+i).checked=false;
				}
				
			}
			codici=codici.substring(0,codici.lastIndexOf(","));	
			codici=codici.substring(codici.lastIndexOf(",")+1,codici.length);
				if(codici=='')
				{
					codici=document.getElementById(id).value;
				}
			}else
			{
				codici=document.getElementById(id).value;
			}
			doHref('${pageContext.request.contextPath}/istanzeprocedimenti/list.htm?codiceIstanza='+codiceIstanza+'&codiciNaturaendoAggiornati='+codici,'<fmt:message key="javascript.confirm.annullamento_riceche_tipi_endo"/>');
		}
		
		// Javascrip per mostrare il calendario (Viene richiamto all'interno della jsp ajax/listInventarioprocedimenti.jsp)
		
		function setupCal(inputId, imageId){
			RANGE_CAL_1 = new Calendar({
					inputField: inputId,
					dateFormat: "%d/%m/%Y",
					trigger: imageId,
					bottomBar: false,
					onSelect: function() {
				var date = Calendar.intToDate(this.selection.get());
				this.hide();
			}
			})
		
		}
		
		function parseScript(_source) {
			var source = _source;
			var scripts = new Array();
			
			// Strip out tags
			while(source.indexOf("<script") > -1 || source.indexOf("</script") > -1) {
				var s = source.indexOf("<script");
				var s_e = source.indexOf(">", s);
				var e = source.indexOf("</script", s);
				var e_e = source.indexOf(">", e);
				
				// Add to scripts array
				scripts.push(source.substring(s_e+1, e));
				// Strip from source
				source = source.substring(0, s) + source.substring(e_e+1);
			}
			
			// Loop through every script collected and eval it
			for(var i=0; i<scripts.length; i++) {
				try {
					eval(scripts[i]);
				}
				catch(ex) {
					// do what you want here when a script fails
				}
			}
			
			// Return the cleaned source
			return source;
		}
	
		// Apre la sezione tipi endo e richiam la funzione Ajax che popola la lista filtrata per tipoendo,famiglia endo 
		//e codici natura
		// Se isOnlyOpen = true : la funzionalità permette solo di aprire e non di chiudere il pannello
		// Se isOnlyOpen = false : la funzionalità permette di aprire e chiudere il pannello
		function mostraEndo(tipiendo_id_codice,istanze_id_codice,sizeListNaturaendo,isOnlyOpen)
		{
            if(isOnlyOpen)
            { 
				showPanel('id_link_tipi_categorie_endo_table'+tipiendo_id_codice, 'id_link_tipi_categorie_endo'+tipiendo_id_codice,'','${pageContext.request.contextPath}/images/','tr',false);
	        }else
            {
	        	showHidePanelBase('id_link_tipi_categorie_endo_table'+tipiendo_id_codice, 'id_link_tipi_categorie_endo'+tipiendo_id_codice,'','${pageContext.request.contextPath}/images/','tr',false);
            }
			if(jQuery('#id_link_tipi_categorie_endo'+tipiendo_id_codice).hasClass('sezioneDatiMeno')){
				listInventarioprocedimenti(tipiendo_id_codice,istanze_id_codice,sizeListNaturaendo);
			}
		}	
</script>


</body>
</html>