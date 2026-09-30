<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.AlberoprocAteco"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Ateco"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants" %>
<%@page import="java.net.URLEncoder"%><html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>
			<fmt:message key="label.gestione_attivita" />
	</title>
</head>
<body>
<span class="titoloPagina">
		<fmt:message key="label.gestione_attivita" />
</span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form"/>
</jsp:include>
<jsp:include page="../includes/history.jsp">
	<jsp:param name="path" value="../iattivita/view" />
	<jsp:param name="qs" value="codice%3D${iattivitaCommand.entity.id.codice}" />
</jsp:include>

<div id="subcontent">
	<spring-form:form commandName="iattivitaCommand" name="inviodati">
	<jsp:include page="../includes/displayGlobalMessages.jsp" >
        <jsp:param name="commandName" value="iattivitaCommand" />
    </jsp:include>
	<!-- Sezione inizale -->
	<!-- START -->			    
    <div>
    	<div id="functions">
	    	<c:set var="_CONF_UTENTE_VIS_VISSTORICOATTIVITA"><%= WebConstants.CONF_UTENTE_VIS_VISSTORICOATTIVITA%></c:set>
	    	<spring-form:checkbox path="visstorico" id="CONF_UTENTE_VIS_VISSTORICOATTIVITA" onclick="visualizzaStorico(this,'${_CONF_UTENTE_VIS_VISSTORICOATTIVITA}');"/>
			<label for="CONF_UTENTE_VIS_VISSTORICOATTIVITA"><fmt:message key="label.attivita_help_visualizza_storico" /></label></div>
	</div>				    
    <fieldset><legend><fmt:message key="label.dati_dell_attivita" /></legend>
		<table>
			<tr>
				<td>
					<fmt:message key="label.stato" />
				</td>
				<td>
					<b>
						<c:if test="${iattivitaCommand.entity.attiva eq true}">
							<fmt:message key="label.attiva" />
						</c:if>
						<c:if test="${iattivitaCommand.entity.attiva ne true}">
							<fmt:message key="label.non_attiva" />
						</c:if>
					</b>	
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.denominazione_dell_attivita" />
				</td>
				<td>
					<spring-form:input id="denominazione_id" path="entity.denominazione" size="70" />
					<spring-form:errors path="entity.denominazione" cssClass="error"/>
				</td>
			</tr>
			<tr>
					<td>
						<fmt:message key="label.tipologia_attivita" />
					</td>
					<td colspan="3">
					<script type="text/javascript">
						
							function filterTipologieBySoftware(element, entry) {
								
								var software = '<%=ORMHelper.getSoftware()%>';
								return entry + "&paramSoftware=" + software+"&ts_=" + Date();								
							}
					</script>
						<jsp:include page="../includes/autocompletergenerico.jsp">
							<jsp:param name="idElemento" value="foarjstepstestata_id" />				
							<jsp:param name="propertyPath" value="entity.tipologiaAttivita" />			
							<jsp:param name="pathPropertyDescription" value="entity.tipologiaAttivita.descrizione" />
							<jsp:param name="pathPropertyCode" value="entity.tipologiaAttivita.id.codice" />
							<jsp:param name="autocompleterAjax" value="../iattivitatipologie/ajaxFindIAttivitaTipologie.htm" />
							<jsp:param name="titleKey" value="label.ricerca_tipologieattivita" />
							<jsp:param name="ajaxCallBack" value="filterTipologieBySoftware" />
						</jsp:include>		
					</td>
				</tr>
			<tr>
				<td>
					<fmt:message key="label.istanza_rappresentativa" />
				</td>
				<td>
					<b>${iattivitaCommand.entity.istanza.numeroistanza}</b>
				</td>
			</tr>
			<tr>
				<td>
					<fmt:message key="label.richiedente" />
				</td>
				<td>
					<b>${iattivitaCommand.entity.istanza.transientRichiedenteQualitaAziendaStorico}</b>
					<init:help idHelp="helpAnagrafestorico_1" text="${iattivitaCommand.entity.istanza.transientRichiedenteQualitaAzienda}"/>
				</td>
			</tr>																					
		</table>
	</fieldset>
	<!-- Sezione inizale -->
	<!-- END -->			
	<br class="clear"/>
	<!-- --------------------------------------------------------------------------------------------------------  -->
	<!-- --------------------------------------INFORMAZIONI SULLE LOCALIZZAZIONI---------------------------------  -->
	<!-- ----------------------------------------------START-----------------------------------------------------  -->
	<%
		String displayTr = "display:none;";
		String styleTr = "";
		//gestisce la visualizzazione della tabella altri dati
		if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_LOCALIZZAZIONI)).equals("1")) {
		    displayTr = "";
		    styleTr="sezioneDatiMeno";
		} else {
		    displayTr = "display:none;";
		    styleTr="sezioneDatiPiu";
		}
	%>
	<fieldset>
	<!-- Gestione del fieldset pricipale che descrive la sezione -->
	<legend>
			<a class="<%= styleTr%>"
				id="id_link_localizzazioni"
				href="javascript:showHidePanel('localizzazioni_table', 'id_link_localizzazioni','<%= WebConstants.CONF_UTENTE_VIS_LOCALIZZAZIONI %>','${pageContext.request.contextPath}/images/','div');"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.localizzazioni"/>">
				<label for="id_link_localizzazioni"><fmt:message key="label.localizzazioni" /></label> 
			</a> 					
	</legend>
	<div id="localizzazioni_table" style="<%=displayTr%>">
	<!-- Tabella che riporta le localizzazioni -->
		<div class="jmesa">
			<table border="1" width="100%" cellpadding="0" cellspacing="0" class="table" >
				<thead>
					<tr class="header">
					    <td width="10%"><fmt:message key="label.numero_istanza"/></td>
						<td colspan="2" width="20%"><fmt:message key="label.indirizzo"/></td>
						<td><fmt:message key="label.cap"/></td>
						<td><fmt:message key="label.frazione"/></td>
						<td><fmt:message key="label.circoscrizione"/></td>
						<td colspan="2"><fmt:message key="label.dati_catastali"/></td>
					</tr>
				</thead>
				<tbody class="tbody">
				    <c:if test="${iattivitaCommand.visstorico}">
				    <tr>
				    	<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI%></c:set>
	   					<spring-form:checkbox path="flagStoricoLocalizzazioni" id="id_CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI}');" onmouseover="showTooltip('id_CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI')"/>
						<label for="id_CONF_UTENTE_STORICO_ATTIVITA_LOCALIZZAZIONI"></label>
				    </tr>
				    </c:if>
					<!-- Crea la sola prima riga utilizzando l'istanza contenuta nell'oggetto iattivita con il numero istanza e 
					setta un rowspan per quante sono gli stradati dell'istanza -->
				    <c:forEach items="${iattivitaCommand.entity.istanza.istanzestradarios}" var="istStradario" varStatus="istStradStatus">
					<c:set var="trStyle" value="odd"/>							
						<tr class="${trStyle}" title="${primarioTitle}">
						<c:if test="${istStradStatus.index==0}">
								<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzestradarios)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
							</c:if>
							<c:if test="${istStradStatus.index!=0}">
								
							</c:if>
						<td width="2%" >
							<c:if test="${not empty istStradario.primario && istStradario.primario eq true}">
								<fmt:message key="label.p"/>
							</c:if>	
						</td>
						
						<td>${istStradario.descrizioneEstesaTransient}</td>
						<%-- 
						<td>${istStradario.stradario.descrizioneAndCodiceviario}</td>
						--%>
						<td width="5%">${istStradario.cap}</td>
						<td>${istStradario.frazione}</td>
						<td>${istStradario.circoscrizione}</td>
						<td>
							<c:forEach items="${istStradario.istanzemappalis}" var="istMappale" varStatus="istMappStatus">
								${istMappale.descrizioneEstesa}<br />															
							</c:forEach>	
						</td>
						</tr>
						</c:forEach>
						<%--
						<c:if test="${fn:length(iattivitaCommand.entity.istanza.istanzestradarios)!=0}">
						<tr class="header">
							<td colspan="8"></td>
						</tr>
						</c:if>
						 --%>
		                <c:if test="${iattivitaCommand.flagStoricoLocalizzazioni eq true}">
		                <!-- Crea le righe con le istanze che appartengono alla stessa attivita -->
						<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper"  varStatus="indexIst">
						    <!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
						    <c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
							<c:forEach items="${istanzaHelper.istanzestradarios}" var="istStradario" varStatus="istStradStatus">
							
								<c:if test="${istStradStatus.index==0}">
									<tr class="header">
										<td colspan="8"></td>
									</tr>
								</c:if>
							
							
							<c:set var="primarioTitle" value=""/>
								<c:if test="${not empty istStradario.primario && istStradario.primario eq true}">
									<c:set var="primarioTitle"><fmt:message key="label.primario"/></c:set>
								</c:if>				
						 	<c:set var="trStyle" value="odd"/>							
								<tr class="${trStyle}" title="${primarioTitle}">	
									<!-- Crea la sola prima colonna con il numero istanza e setta un rowspan per quante sono le istanze -->
									<c:if test="${istStradStatus.index==0}">
										<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzestradarios)}">${istStradario.istanza.numeroistanza}</td>
									</c:if>
									<c:if test="${istStradStatus.index!=0}">
										
									</c:if>
									<td width="2%" >
										<c:if test="${not empty istStradario.primario && istStradario.primario eq true}">
											<fmt:message key="label.p"/>
										</c:if>	
									</td>
									<td>${istStradario.descrizioneEstesaTransient}</td>
									<td width="5%">${istStradario.cap}</td>
									<td>${istStradario.frazione}</td>
									<td>${istStradario.circoscrizione}</td>
									<td>
										<c:forEach items="${istStradario.istanzemappalis}" var="istMappale" varStatus="istMappStatus">
											${istMappale.descrizioneEstesa}<br />															
										</c:forEach>	
									</td>
								</tr>
								
							 </c:forEach>
							 </c:if>	
						 </c:forEach>
						</c:if>
				 </tbody>
			</table>	
       	</div>
	</div>
	</fieldset>	
	<br class="clear"/>
	<!-- --------------------------------------------------------------------------------------------------------  -->
	<!-- --------------------------------------INFORMAZIONI SULLE LOCALIZZAZIONI---------------------------------  -->
	<!-- ------------------------------------------------END-----------------------------------------------------  -->
	<%
		displayTr = "display:none;";
		styleTr = "";
		//gestisce la visualizzazione della tabella altri dati
		if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_SOGGCOLL)).equals("1")) {
		    displayTr = "";
		    styleTr="sezioneDatiMeno";
		} else {
		    displayTr = "display:none;";
		    styleTr="sezioneDatiPiu";
		}
	%>
	<!-- --------------------------------------------------------------------------------------------------------  -->
	<!-- --------------------------------------INFORMAZIONI SULLE SOGGETTI COLLEGATI-----------------------------  -->
	<!-- ----------------------------------------------START-----------------------------------------------------  -->
	<!-- Gestione del fieldset pricipale che descrive la sezione -->
	<fieldset>
		<legend>
			<a class="<%= styleTr%>"
				id="id_link_sogg_coll"
				href="javascript:showHidePanel('soggcoll_table', 'id_link_sogg_coll','<%= WebConstants.CONF_UTENTE_VIS_SOGGCOLL%>','${pageContext.request.contextPath}/images/','div');"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.soggetti_collegati_all_istanza" />">
				<label for="id_link_sogg_coll"><fmt:message key="label.soggetti_collegati_all_istanza" /></label> 
			</a> 			
		</legend>
        <div id="soggcoll_table" style="<%=displayTr%>">
	    <!-- Tabella che riporta soggetti collegati -->
		<div class="jmesa" >
			<table  border="0" width="100%" cellpadding="0" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td width="10%"><fmt:message key="label.numero_istanza"/></td>
							<td><fmt:message key="label.nominativo"/></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<c:if test="${iattivitaCommand.visstorico}">
				   	 <tr>
				    	<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI%></c:set>
	   					<spring-form:checkbox path="flagStoricoSoggettiCollegati" id="id_CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI}');" onmouseover="showTooltip('id_CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI');"/>
						<label for="id_CONF_UTENTE_STORICO_ATTIVITA_SOGGETTI_COLLEGATI"></label>
				    </tr>
				    </c:if>
					<!-- Crea la sola prima riga utilizzando l'istanza contenuta nell'oggetto iattivita con il numero istanza e setta un rowspan per quante sono i soggetti dell'istanza -->
				    <c:forEach items="${iattivitaCommand.entity.istanza.istanzerichiedentis}" var="istanzerichiedenti" varStatus="statusRic">
					<c:set var="trStyle" value="odd"/>							
						<tr class="${trStyle}" >
							<c:if test="${statusRic.index==0}">
									<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzerichiedentis)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
							</c:if>
							<c:if test="${statusRic.index!=0}">
							
							</c:if>						
							<td>
								${istanzerichiedenti.richiedente.descrizioneRichiedente} - ${istRichiedente.tiposoggetto.tiposoggetto}
									<c:if test="${not empty istRichiedente.descrsoggetto}">
										&nbsp;${istRichiedente.descrsoggetto}
									</c:if>
									&nbsp;${istRichiedente.anagrafeCollegata.descrizioneRichiedente}
							</td>
						</tr>	
						</c:forEach>
					
		                
		                <!-- Mostro tutti i soggetti collegati della altre istanze legate all'attività -->
		                <c:if test="${iattivitaCommand.flagStoricoSoggettiCollegati eq true}">			
						<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper"  varStatus="indexIst">
						     <!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
						    <c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
						    <c:forEach items="${istanzaHelper.istanzerichiedentis}" var="istanzerichiedenti" varStatus="statusRic">
							
								<c:if test="${statusRic.index==0}">
									<tr class="header">
										<td colspan="8"></td>
									</tr>
								</c:if>
							
							<c:set var="trStyle" value="odd"/>
							<tr class="${trStyle}" >
								<c:if test="${statusRic.index==0}">
									<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzerichiedentis)}">${istanzerichiedenti.istanza.numeroistanza}</td>
								</c:if>
								<c:if test="${statusRic.index!=0}">
								
								</c:if>						
								<td>
									${istanzerichiedenti.richiedente.descrizioneRichiedente} - ${istRichiedente.tiposoggetto.tiposoggetto}
										<c:if test="${not empty istRichiedente.descrsoggetto}">
											&nbsp;${istRichiedente.descrsoggetto}
										</c:if>
										&nbsp;${istRichiedente.anagrafeCollegata.descrizioneRichiedente}
								</td>
							</tr>	
						</c:forEach>
						</c:if>	
					</c:forEach>
					</c:if>
					</tbody>								
			</table>
		</div>
	</div>
	</fieldset>
	<br class="clear"/>
	<!-- --------------------------------------------------------------------------------------------------------  -->
	<!-- --------------------------------------INFORMAZIONI SULLE SOGGETTI COLLEGATI-----------------------------  -->
	<!-- ------------------------------------------------END-----------------------------------------------------  -->
	<%
		displayTr = "display:none;";
		styleTr = "";
		//gestisce la visualizzazione della tabella altri dati
		if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_DETTINFO)).equals("1")) {
		    displayTr = "";
		    styleTr="sezioneDatiMeno";
		} else {
		    displayTr = "display:none;";
		    styleTr="sezioneDatiPiu";
		}
	%>
	<!-- --------------------------------------------------------------------------------------------------------  -->
	<!-- --------------------------------------INFORMAZIONI DETTAGLIO INFORMAZIONI-----------------------------  -->
	<!-- ------------------------------------------------START-----------------------------------------------------  -->
	<!-- Gestione del fieldset pricipale che descrive la sezione -->
	<fieldset>
		<legend>
			<a class="<%= styleTr%>"
				id="id_link_det_info"
				href="javascript:showHidePanel('det_info_table', 'id_link_det_info','<%= WebConstants.CONF_UTENTE_VIS_DETTINFO%>','${pageContext.request.contextPath}/images/','div');"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.dettaglio_informazioni" />">
				<label for="id_link_det_info"><fmt:message key="label.dettaglio_informazioni" /></label> 
			</a> 			
		</legend>
		<!-- Inizio ciclo della lista delle istanze   -->
		<!-- Gestione del fieldset secondario associato ad ogni istanza -->
		<div id="det_info_table" style="<%=displayTr%>">
	    <!-- Tabella che riporta le localizzazioni per ogni istanza cliclata -->
		<div class="jmesa" >
			<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">					
					<thead>
						<tr class="header">
							<td width="10%"><fmt:message key="label.numero_istanza"/></td>
							<td><fmt:message key="label.tipo_informazione"/></td>
							<td><fmt:message key="label.dettaglio_informazione"/></td>
							<td><fmt:message key="label.quantita"/></td>
							<td><fmt:message key="label.note"/></td>
						</tr>
					</thead>
					<tbody class="tbody">
					    <c:if test="${iattivitaCommand.visstorico}">
					   	 <tr>
					    	<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO%></c:set>
		   					<spring-form:checkbox path="flagStoricoDettaglioInfo" id="id_CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO}');" onmouseover="showTooltip('id_CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO');"/>
							<label for="id_CONF_UTENTE_STORICO_ATTIVITA_DETTAGLIO_INFO"></label>
					    </tr>
					    </c:if>
					
					    <!-- Crea la sola prima riga utilizzando l'istanza contenuta nell'oggetto iattivita con il numero istanza e setta un rowspan per quante sono i soggetti dell'istanza -->
					    <c:forEach items="${iattivitaCommand.entity.istanza.istanzeattivitas}" var="istanzeattivita"  varStatus="statusDett">
						<c:set var="trStyle" value="odd"/>							
							<tr class="${trStyle}" >
								<c:if test="${statusDett.index==0}">
										<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzeattivitas)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
								</c:if>
								<c:if test="${statusDett.index!=0}">
								
								</c:if>						
								<td>${istanzeattivita.attivita.settori.settore}</td>
								<td>${istanzeattivita.attivita.istat}</td>
								<td>
									${istanzeattivita.attivita.settori.tipiunitamisura.umDescrbreve}&nbsp;
									${istanzeattivita.metriq}
								</td>
								<td>${istanzeattivita.note}</td>
							</tr>
						</c:forEach>
					   
					    <c:if test="${iattivitaCommand.flagStoricoDettaglioInfo}">
						<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="indexIst">
							 <!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
						    <c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
							 <c:forEach items="${istanzaHelper.istanzeattivitas}" var="istanzeattivita" varStatus="statusDett">
								
									<c:if test="${statusDett.index==0}">
										<tr class="header">
											<td colspan="8"></td>
										</tr>
									</c:if>
								
								<c:set var="trStyle" value="odd"/>
								<tr class="${trStyle}">
									<c:if test="${statusDett.index==0}">
										<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzeattivitas)}">${istanzeattivita.istanza.numeroistanza}</td>
									</c:if>
									<c:if test="${statusDett.index!=0}">
										
									</c:if>	
									<td>${istanzeattivita.attivita.settori.settore}</td>
									<td>${istanzeattivita.attivita.istat}</td>
									<td>
										${istanzeattivita.attivita.settori.tipiunitamisura.umDescrbreve}&nbsp;
										${istanzeattivita.metriq}
									</td>
									<td>${istanzeattivita.note}</td>
								</tr>	
							</c:forEach>
							</c:if>	
						</c:forEach>
						</c:if>
					</tbody>									
			</table>
		</div>
		</div>
		
		
	</fieldset>
	<!-- Fine Gestione del fieldset pricipale che descrive la sezione -->
	<br class="clear"/>
	<!-- --------------------------------------------------------------------------------------------------------  -->
	<!-- --------------------------------------INFORMAZIONI DETTAGLIO INFORMAZIONI-----------------------------  -->
	<!-- ------------------------------------------------END-----------------------------------------------------  -->
	<%
		displayTr = "display:none;";
		styleTr = "";
		//gestisce la visualizzazione della tabella altri dati
		if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_ORARI)).equals("1")) {
		    displayTr = "";
		    styleTr="sezioneDatiMeno";
		} else {
		    displayTr = "display:none;";
		    styleTr="sezioneDatiPiu";
		}
	%>
		<!-- --------------------------------------------------------------------------------------------------------  -->
		<!-- --------------------------------------INFORMAZIONI DETTAGLIO ORARI---------------------------------------  -->
		<!-- ------------------------------------------------START-----------------------------------------------------  -->
		<!-- Gestione del fieldset pricipale che descrive la sezione -->
		<fieldset>
			<legend>
				<a class="<%= styleTr%>"
					id="id_link_orari"
					href="javascript:showHidePanel('det_orari_table', 'id_link_orari','<%= WebConstants.CONF_UTENTE_VIS_ORARI%>','${pageContext.request.contextPath}/images/','div');"
					title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.orari" />">
					<label for="id_link_orari"><fmt:message key="label.orari" /></label> 
				</a> 			
			</legend>
				<div id="det_orari_table" style="<%=displayTr%>">								
				<div class="jmesa" >
					<!-- Tabella che gestisce il la testata e dettaglio di un tipo orario  -->
					<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
					<c:if test="${iattivitaCommand.visstorico}">
					   	 <tr>
					    	<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_ORARI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_ORARI%></c:set>
		   					<spring-form:checkbox path="flagStoricoOrari" id="id_CONF_UTENTE_STORICO_ATTIVITA_ORARI" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_ORARI}');" onmouseover="showTooltip('id_CONF_UTENTE_STORICO_ATTIVITA_ORARI');"/>
							<label for="id_CONF_UTENTE_STORICO_ATTIVITA_ORARI"></label>
					    </tr>
				    </c:if>	
					<c:forEach items="${iattivitaCommand.entity.istanza.orariaperturatestatas}" var="orariaperturatestata" varStatus="statusOrari">
							<!-- Riga che visulalizzano il la testata  di un tipo orario  -->
							<tr>
								<td>
									<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
										<tr>
											<td class="header"><fmt:message key="label.descrizione"/>:
												&nbsp;${orariaperturatestata.tipiorario.toDescrizione}
												&nbsp;(<fmt:message key="label.periodo_dal"/>&nbsp;${orariaperturatestata.periododaTransient}
												&nbsp;<fmt:message key="label.al"/>&nbsp;${orariaperturatestata.periodoaTransient})
											</td>
										</tr>
									</table>
								</td>
							</tr>
							<!-- Righe che visulalizzano il la testata  di un tipo orario  -->
							<tr>
							<td>
								<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
								<thead>
									<tr class="header">
										<td width="10%"><fmt:message key="label.numero_istanza"/></td>
										<td><fmt:message key="label.giorno_settimana"/></td>
										<td><fmt:message key="label.inizio_mattina"/></td>
                   						<td><fmt:message key="label.fine_mattina"/></td>
                  							<td><fmt:message key="label.inizio_pomeriggio"/></td>
                  							<td><fmt:message key="label.fine_pomeriggio"/></td>
                   						<td><fmt:message key="label.tipologia_orario"/></td>  
									</tr>
								</thead>
								<tbody class="tbody">
									<c:forEach items="${orariaperturatestata.orariaperturas}" var="orariaperturadettaglio" varStatus="statusDettOrari">
										<c:if test="${statusOrari.index ne 0}">
											<c:if test="${statusDettOrari.index==0}">
												<tr class="header">
												<td colspan="8"></td>
												</tr>
											</c:if>
										</c:if>
										<c:set var="trStyle" value="odd"/>
											<tr class="${trStyle}">
											<c:if test="${statusDettOrari.index==0}">
													<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(orariaperturatestata.orariaperturas)}">${orariaperturatestata.istanze.numeroistanza}</td>
											</c:if>
											<c:if test="${statusDettOrari.index!=0}">
									
								            </c:if>	
											<td>${orariaperturadettaglio.giornisettimana.gsDescrizione}</td>
											<td>${orariaperturadettaglio.oaDalleore}</td>
											<td>${orariaperturadettaglio.oaAlleore}</td>
											<td>${orariaperturadettaglio.oaDalleorepom}</td>
											<td>${orariaperturadettaglio.oaAlleorepom}</td>
											<td>${orariaperturadettaglio.tipiapertura.taDescrizione}</td>
										</tr>	
									</c:forEach>	
								</tbody>
								</table>
							</td>
						</tr>
						<tr class="header">
							<td colspan="8"></td>
						</tr>
					</c:forEach>	
					<c:if test="${iattivitaCommand.flagStoricoOrari}">
					<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="orariIndex">
						<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
					    <c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
						<c:if test="${not empty istanzaHelper.orariaperturatestatas}" >
					    <c:forEach items="${istanzaHelper.orariaperturatestatas}" var="orariaperturatestata" varStatus="statusOrari">
						<!-- Riga che visulalizzano il la testata  di un tipo orario  -->
						<tr>
							<td>
								<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
									<tr>
										<td class="header"><fmt:message key="label.descrizione"/>:
											&nbsp;${orariaperturatestata.tipiorario.toDescrizione}
											&nbsp;(<fmt:message key="label.periodo_dal"/>&nbsp;${orariaperturatestata.periododaTransient}
											&nbsp;<fmt:message key="label.al"/>&nbsp;${orariaperturatestata.periodoaTransient})
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<!-- Righe che visulalizzano il la testata  di un tipo orario  -->
						<tr>
						<td>
							<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
							<thead>
								<tr class="header">
									<td width="10%"><fmt:message key="label.numero_istanza"/></td>
									<td><fmt:message key="label.giorno_settimana"/></td>
									<td><fmt:message key="label.inizio_mattina"/></td>
                  						<td><fmt:message key="label.fine_mattina"/></td>
                 							<td><fmt:message key="label.inizio_pomeriggio"/></td>
                 							<td><fmt:message key="label.fine_pomeriggio"/></td>
                  						<td><fmt:message key="label.tipologia_orario"/></td>  
								</tr>
							</thead>
							<tbody class="tbody">
								<c:forEach items="${orariaperturatestata.orariaperturas}" var="orariaperturadettaglio" varStatus="statusDettOrari">
									<c:if test="${statusOrari.index ne 0}">
										<c:if test="${statusDettOrari.index==0}">
											<tr class="header">
												<td colspan="8"></td>
											</tr>
										</c:if>
									</c:if>
									<c:set var="trStyle" value="odd"/>
										<tr class="${trStyle}">
										<c:if test="${statusDettOrari.index==0}">
												<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(orariaperturatestata.orariaperturas)}">${orariaperturatestata.istanze.numeroistanza}</td>
										</c:if>
										<c:if test="${statusDettOrari.index!=0}">
								
							            </c:if>	
										<td>${orariaperturadettaglio.giornisettimana.gsDescrizione}</td>
										<td>${orariaperturadettaglio.oaDalleore}</td>
										<td>${orariaperturadettaglio.oaAlleore}</td>
										<td>${orariaperturadettaglio.oaDalleorepom}</td>
										<td>${orariaperturadettaglio.oaAlleorepom}</td>
										<td>${orariaperturadettaglio.tipiapertura.taDescrizione}</td>
									</tr>	
								</c:forEach>	
							</tbody>
							</table>
						</td>
					</tr>
					</c:forEach>
					</c:if>
					</c:if>
					</c:forEach>
					</c:if>
					</table>
					<!-- Fine Tabella che gestisce il la testata di un tipo orario  -->
				</div>
				</div>															
		</fieldset>
		<!--Fine Gestione del fieldset pricipale che descrive la sezione -->
		<br class="clear"/>
		<!-- --------------------------------------------------------------------------------------------------------  -->
		<!-- --------------------------------------INFORMAZIONI DETTAGLIO ORARI---------------------------------------  -->
		<!-- ------------------------------------------------END-----------------------------------------------------  -->
		<%
			displayTr = "display:none;";
			styleTr = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_AUTORIZZAZIONI)).equals("1")) {
			    displayTr = "";
			    styleTr="sezioneDatiMeno";
			} else {
			    displayTr = "display:none;";
			    styleTr="sezioneDatiPiu";
			}
		%>
	<!-- --------------------------------------------------------------------------------------------------------  -->
	<!-- --------------------------------------INFORMAZIONI AUTORIZZAZIONI---------------------------------------  -->
	<!-- ------------------------------------------------START-----------------------------------------------------  -->
	<!-- Gestione del fieldset pricipale che descrive la sezione -->
	<fieldset>
		<legend>
			<a class="<%= styleTr%>"
				id="id_link_autoriz"
				href="javascript:showHidePanel('autorizz_table', 'id_link_autoriz','<%= WebConstants.CONF_UTENTE_VIS_AUTORIZZAZIONI%>','${pageContext.request.contextPath}/images/','div');"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.autorizzazioni" />">
				<label for="id_link_autoriz"><fmt:message key="label.autorizzazioni" /></label> 
			</a>						
		</legend>
		<!-- Inizio ciclo della lista delle istanze   -->
		
		<!-- Gestione del fieldset secondario associato ad ogni istanza -->
		<div id="autorizz_table" style="<%=displayTr%>">
		<div class="jmesa" >
			<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
					<thead>
						<tr class="header">
							<td width="10%"><fmt:message key="label.numero_istanza"/></td>
							<td><fmt:message key="label.numero"/></td>
							<td><fmt:message key="label.data"/></td>
							<td><fmt:message key="label.comune"/></td>
							<td><fmt:message key="label.registro"/></td>
							<td ><fmt:message key="label.stato"/></td>
						</tr>
					</thead>
					<tbody class="tbody">
					 <c:if test="${iattivitaCommand.visstorico}">
					 <tr>
				    	<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI%></c:set>
	   					<spring-form:checkbox path="flagStoricoAutorizzazioni" id="id_CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI}');" onmouseover="showTooltip('id_CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI');"/>
						<label for="id_CONF_UTENTE_STORICO_ATTIVITA_AUTORIZZAZIONI"></label>
					 </tr>
					 </c:if>
					   <!-- Crea la sola prima riga con il numero istanza e setta un rowspan per quante le autorizzazioni dell'istanza -->
					   <c:forEach items="${iattivitaCommand.entity.istanza.autorizzazionis}" var="autorizzazioni"  varStatus="statusAut">
					   <c:if test="${empty autorizzazioni.autorizzazioniConcessionisForFkAutconcAutatt}">
						<c:set var="trStyle" value="odd"/>							
							<tr class="${trStyle}" >
								<c:if test="${statusAut.index==0}">
									<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.autorizzazionis)}">${autorizzazioni.istanza.numeroistanza}</td>
								</c:if>
								<c:if test="${statusAut.index!=0}">
									
								</c:if>						
								<td>${autorizzazioni.autoriznumero }</td>
								<td><fmt:formatDate value="${autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
								<td>${autorizzazioni.autorizcomune.comune}</td>
								<td>${autorizzazioni.tipologiaregistro.trDescrizione}</td>
								<td>
									<c:if test="${autorizzazioni.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
									<c:if test="${autorizzazioni.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${autorizzazioni.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
								</td>
							</tr>
						</c:if>	
						</c:forEach>
						
					
					  
					<c:if test="${iattivitaCommand.flagStoricoAutorizzazioni}">
					<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="autIndex">
						 <!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
						 <c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
						<c:forEach items="${istanzaHelper.autorizzazionis}" var="autorizzazioni" varStatus="statusAut">
							<c:if test="${empty autorizzazioni.autorizzazioniConcessionisForFkAutconcAutatt}">
								
									<c:if test="${statusAut.index==0}">
										<tr class="header">
											<td colspan="8"></td>
										</tr>
									</c:if>
							    
								<c:set var="trStyle" value="odd"/>
								<tr class="${trStyle}">
								<c:if test="${statusAut.index==0}">
									<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.autorizzazionis)}">${autorizzazioni.istanza.numeroistanza}</td>
								</c:if>
								<c:if test="${statusAut.index!=0}">
									
								</c:if>	
									<td>${autorizzazioni.autoriznumero }</td>
									<td><fmt:formatDate value="${autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td>${autorizzazioni.autorizcomune.comune}</td>
									<td>${autorizzazioni.tipologiaregistro.trDescrizione}</td>
									<td>
										<c:if test="${autorizzazioni.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
										<c:if test="${autorizzazioni.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${autorizzazioni.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
									</td>
								</tr>
							</c:if>	
						</c:forEach>
						</c:if>							
				       </c:forEach>
				       </c:if>
				</tbody>
			</table>
		</div>
		</div>
		</fieldset>				
		<br class="clear"/>
		<!-- --------------------------------------------------------------------------------------------------------  -->
		<!-- --------------------------------------INFORMAZIONI AUTORIZZAZIONI---------------------------------------  -->
		<!-- ------------------------------------------------END-----------------------------------------------------  -->
		<%
			displayTr = "display:none;";
			styleTr = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_CONCESSIONI)).equals("1")) {
			    displayTr = "";
			    styleTr="sezioneDatiMeno";
			} else {
			    displayTr = "display:none;";
			    styleTr="sezioneDatiPiu";
			}
		%>
		<!-- --------------------------------------------------------------------------------------------------------  -->
		<!-- --------------------------------------INFORMAZIONI CONCESSIONI---------------------------------------  -->
		<!-- ------------------------------------------------START-----------------------------------------------------  -->
		<!-- Gestione del fieldset pricipale che descrive la sezione -->
		<fieldset>
			<legend>
				<a class="<%= styleTr%>"
					id="id_link_conc"
					href="javascript:showHidePanel('conc_table', 'id_link_conc','<%= WebConstants.CONF_UTENTE_VIS_CONCESSIONI%>','${pageContext.request.contextPath}/images/','div');"
					title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.concessioni" />">
					<label for="id_link_conc"><fmt:message key="label.concessioni" /></label> 
				</a>						
			</legend>
			<div id="conc_table" style="<%=displayTr%>">
			<div class="jmesa" >
				<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="10%"><fmt:message key="label.numero_istanza"/></td>
								<td><fmt:message key="label.numero"/></td>
								<td><fmt:message key="label.data"/></td>
								<td><fmt:message key="label.comune"/></td>
								<td><fmt:message key="label.registro"/></td>
								<td><fmt:message key="label.stato"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
						<c:if test="${iattivitaCommand.visstorico}">
							 <tr>
						    	<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI%></c:set>
			   					<spring-form:checkbox path="flagStoricoConcessioni" id="id_CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI}');" onmouseover="showTooltip('id_CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI');"/>
								<label for="id_CONF_UTENTE_STORICO_ATTIVITA_CONCESSIONI"></label>
							 </tr>
						</c:if>
						<c:forEach items="${iattivitaCommand.entity.istanza.autorizzazionis}" var="autorizzazioni"  varStatus="statusConc">
							<c:if test="${not empty autorizzazioni.autorizzazioniConcessionisForFkAutconcAutatt}">
									<c:if test="${concIndex.index ne 0}">
										<c:if test="${statusConc.index==0}">
											<tr class="header">
												<td colspan="8"></td>
											</tr>
										</c:if>
								</c:if>
								<c:set var="trStyle" value="odd"/>
								<tr class="${trStyle}">
								<c:if test="${statusConc.index==0}">
									<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.autorizzazionis)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
								</c:if>
								<c:if test="${statusConc.index!=0}">
									
								</c:if>	
									<td>${autorizzazioni.autoriznumero }</td>
									<td><fmt:formatDate value="${autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td>${autorizzazioni.autorizcomune.comune}</td>
									<td>${autorizzazioni.tipologiaregistro.trDescrizione}</td>
									<td>
										<c:if test="${autorizzazioni.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
										<c:if test="${autorizzazioni.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${autorizzazioni.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
									</td>
								</tr>
							</c:if>		
						</c:forEach>
						
						 
						<c:if test="${iattivitaCommand.flagStoricoConcessioni}">
						<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="concIndex">
							<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
						 	<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
							<c:forEach items="${istanzaHelper.autorizzazionis}" var="autorizzazioni" varStatus="statusConc">
								<c:if test="${not empty autorizzazioni.autorizzazioniConcessionisForFkAutconcAutatt}">
									
										<c:if test="${statusConc.index==0}">
											<tr class="header">
												<td colspan="8"></td>
											</tr>
										</c:if>
								
								<c:set var="trStyle" value="odd"/>
								<tr class="${trStyle}">
								<c:if test="${statusConc.index==0}">
									<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.autorizzazionis)}">${autorizzazioni.istanza.numeroistanza}</td>
								</c:if>
								<c:if test="${statusConc.index!=0}">
									
								</c:if>	
									<td>${autorizzazioni.autoriznumero }</td>
									<td><fmt:formatDate value="${autorizzazioni.autorizdata}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td>${autorizzazioni.autorizcomune.comune}</td>
									<td>${autorizzazioni.tipologiaregistro.trDescrizione}</td>
									<td>
										<c:if test="${autorizzazioni.flagAttiva eq true}"><fmt:message key="label.attiva" /></c:if>
										<c:if test="${autorizzazioni.flagAttiva ne true}"><fmt:message key="label.cessata" /> <fmt:formatDate value="${autorizzazioni.dataCessazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></c:if>
									</td>
								</tr>
							</c:if>	
							</c:forEach>
							</c:if>	
						</c:forEach>
						</c:if>
						</tbody>
				</table>
			</div>
			</div>
		</fieldset>					
		<!-- Fine del fieldset pricipale che descrive la sezione -->							
		<br class="clear"/>
		<!-- --------------------------------------------------------------------------------------------------------  -->
		<!-- --------------------------------------INFORMAZIONI CONCESSIONI---------------------------------------  -->
		<!-- ------------------------------------------------END-----------------------------------------------------  -->
		<%
			displayTr = "display:none;";
			styleTr = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_ENDOPROCEDIMENTI)).equals("1")) {
			    displayTr = "";
			    styleTr="sezioneDatiMeno";
			} else {
			    displayTr = "display:none;";
			    styleTr="sezioneDatiPiu";
			}
		%>
		<!-- --------------------------------------------------------------------------------------------------------  -->
		<!-- --------------------------------------INFORMAZIONI ENDO PROCEDIMENTI------------------------------------  -->
		<!-- ------------------------------------------------START---------------------------------------------------  -->
		<!-- Gestione del fieldset pricipale che descrive la sezione -->
		<fieldset>
			<legend>
				<a class="<%= styleTr%>"
					id="id_link_endo"
					href="javascript:showHidePanel('endo_table', 'id_link_endo','<%= WebConstants.CONF_UTENTE_VIS_ENDOPROCEDIMENTI%>','${pageContext.request.contextPath}/images/','div');"
					title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.endoprocedimenti" />">
					<label for="id_link_endo"><fmt:message key="label.endoprocedimenti" /></label> 
				</a>						
			</legend>
			<!-- Inizio ciclo della lista delle istanze   -->
			
			<!-- Gestione del fieldset secondario associato ad ogni istanza -->
			<div class="jmesa" id="endo_table" style="<%= displayTr%>">
			<div class="jmesa" >
				<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="10%"><fmt:message key="label.numero_istanza"/></td>
								<td><fmt:message key="label.endoprocedimento"/></td>						
								<td><fmt:message key="label.famiglia_endo"/></td>
								<td><fmt:message key="label.categoria_endo"/></td>
								<td><fmt:message key="label.data"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
						 <c:if test="${iattivitaCommand.visstorico}">
							 <tr>
						    	<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI%></c:set>
			   					<spring-form:checkbox path="flagStoricoEndoprocedimenti" id="id_CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI}');" onmouseover="showTooltip('id_CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI');"/>
								<label for="id_CONF_UTENTE_STORICO_ATTIVITA_INVENTARIO_PROCEDIMENTI"></label>
							 </tr>
						 </c:if>
						 
						 <c:forEach items="${iattivitaCommand.entity.istanza.istanzeprocedimentis}" var="endos"  varStatus="statusEndo">
							<c:set var="trStyle" value="odd"/>							
								<c:set var="trStyle" value="odd"/>
									<tr class="${trStyle}">
									<c:if test="${statusEndo.index==0}">
										<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzeprocedimentis)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
									</c:if>
									<c:if test="${statusEndo.index!=0}">
									</c:if>	
										<td>${endos.inventarioprocedimenti.procedimento}</td>
										<td>${endos.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo}</td>
										<td>${endos.inventarioprocedimenti.tipoendo.tipo}</td>
										<td><fmt:formatDate value="${endos.dataattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
									</tr>
						 </c:forEach>
						
						<c:if test="${iattivitaCommand.flagStoricoEndoprocedimenti}">
						<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="endoIndex">
							<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
						 	<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
							<c:forEach items="${istanzaHelper.istanzeprocedimentis}" var="endos" varStatus="statusEndo">
									
										<c:if test="${statusEndo.index==0}">
											<tr class="header">
												<td colspan="8"></td>
											</tr>
										</c:if>
								    
									<c:set var="trStyle" value="odd"/>
									<tr class="${trStyle}">
									<c:if test="${statusEndo.index==0}">
										<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzeprocedimentis)}">${endos.istanza.numeroistanza}</td>
									</c:if>
									<c:if test="${statusEndo.index!=0}">
									</c:if>	
										<td>${endos.inventarioprocedimenti.procedimento}</td>
										<td>${endos.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo}</td>
										<td>${endos.inventarioprocedimenti.tipoendo.tipo}</td>
										<td><fmt:formatDate value="${endos.dataattivazione}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
									</tr>
									
							</c:forEach>
							</c:if>	
					   </c:forEach>
					   </c:if>
					   </tbody>
				</table>
			</div>
			</div>
		</fieldset>														
								
		<br class="clear"/>
		<!-- --------------------------------------------------------------------------------------------------------  -->
		<!-- --------------------------------------INFORMAZIONI ENDO PROCEDIMENTI------------------------------------  -->
		<!-- ------------------------------------------------END-----------------------------------------------------  -->
		<%
			displayTr = "display:none;";
			styleTr = "";
			//gestisce la visualizzazione della tabella altri dati
			if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_ONERI)).equals("1")) {
			    displayTr = "";
			    styleTr="sezioneDatiMeno";
			} else {
			    displayTr = "display:none;";
			    styleTr="sezioneDatiPiu";
			}
		%>
		<!-- --------------------------------------------------------------------------------------------------------  -->
		<!-- --------------------------------------INFORMAZIONI ENDO ONERI------------------------------------  -->
		<!-- ------------------------------------------------START---------------------------------------------------  -->
		<!-- Gestione del fieldset pricipale che descrive la sezione -->
	<fieldset>
		<legend>
			<a class="<%= styleTr%>"
				id="id_link_oneri"
				href="javascript:showHidePanel('oneri_table', 'id_link_oneri','<%= WebConstants.CONF_UTENTE_VIS_ONERI%>','${pageContext.request.contextPath}/images/','div');"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.oneri" />">
				<label for="id_link_oneri"><fmt:message key="label.oneri" /></label> 
			</a>						
		</legend>
			<div  id="oneri_table" style="<%= displayTr%>">
			<div class="jmesa" >
				<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
						<thead>
							<tr class="header">
								<td width="10%"><fmt:message key="label.numero_istanza"/></td>
								<td><fmt:message key="label.causale"/></td>
								<td><fmt:message key="label.importo"/></td>
								<td><fmt:message key="label.data_registrazione"/></td>
								<td><fmt:message key="label.data_scadenza"/></td>
								<td><fmt:message key="label.data_pagamento"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
						<c:if test="${iattivitaCommand.visstorico}">
							 <tr>
						    	<c:set var="_CONF_UTENTE_STORICO_ATTIVITA_ONERI"><%= WebConstants.CONF_UTENTE_STORICO_ATTIVITA_ONERI%></c:set>
			   					<spring-form:checkbox path="flagStoricoOneri" id="id_CONF_UTENTE_STORICO_ATTIVITA_ONERI" onclick="visualizzaStorico(this,'${_CONF_UTENTE_STORICO_ATTIVITA_ONERI}');" onmouseover="showTooltip('id_CONF_UTENTE_STORICO_ATTIVITA_ONERI');"/>
								<label for="id_CONF_UTENTE_STORICO_ATTIVITA_ONERI"></label>
							 </tr>
						</c:if>
						
						 <c:forEach items="${iattivitaCommand.entity.istanza.istanzeoneris}" var="oneri"  varStatus="statusOneri">
						 		<c:set var="trStyle" value="odd"/>
								<tr class="${trStyle}">
								<c:if test="${statusOneri.index==0}">
									<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(iattivitaCommand.entity.istanza.istanzeoneris)}">${iattivitaCommand.entity.istanza.numeroistanza}</td>
								</c:if>
								<c:if test="${statusOneri.index!=0}">
								</c:if>	
									<td>${oneri.tipicausalioneri.coDescrizione}</td>
									<td>${oneri.prezzo}</td>
									<td><fmt:formatDate value="${oneri.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
									<td><fmt:formatDate value="${oneri.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									<td><fmt:formatDate value="${oneri.datapagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
								</tr>
								
						 </c:forEach>
						 
						<c:if test="${iattivitaCommand.flagStoricoOneri}">
						<c:forEach items="${istanzeHelper.istanzes}" var="istanzaHelper" begin="1" varStatus="oneriIndex">
							<!-- Controllo per non far visualizzare l'istanza presente nell'oggetto iattvita -->
						 	<c:if test="${istanzaHelper.id.codice !=iattivitaCommand.entity.istanza.id.codice}">
							<c:forEach items="${istanzaHelper.istanzeoneris}"  var="oneri" varStatus="statusOneri">												
								
									<c:if test="${statusOneri.index==0}">
										<tr class="header">
											<td colspan="8"></td>
										</tr>
									</c:if>
							   
								    <c:set var="trStyle" value="odd"/>
									<tr class="${trStyle}">
									<c:if test="${statusOneri.index==0}">
										<td style="vertical-align: text-top; font-weight: bold;" rowspan="${fn:length(istanzaHelper.istanzeoneris)}">${oneri.istanza.numeroistanza}</td>
									</c:if>
									<c:if test="${statusOneri.index!=0}">
									</c:if>	
										<td>${oneri.tipicausalioneri.coDescrizione}</td>
										<td>${oneri.prezzo}</td>
										<td><fmt:formatDate value="${oneri.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
										<td><fmt:formatDate value="${oneri.datascadenza}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
										<td><fmt:formatDate value="${oneri.datapagamento}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
									</tr>
							</c:forEach>
							</c:if>	
					</c:forEach>
					</c:if>
					</tbody>
			 </table>
		  </div>
		</div>
		</fieldset>				
					
	<br class="clear"/>
	<!-- --------------------------------------------------------------------------------------------------------  -->
	<!-- --------------------------------------INFORMAZIONI ENDO PROCEDIMENTI------------------------------------  -->
	<!-- ------------------------------------------------END---------------------------------------------------  -->
	<%
		displayTr = "display:none;";
		styleTr = "";
		//gestisce la visualizzazione della tabella altri dati
		if (((String) request.getAttribute(WebConstants.CONF_UTENTE_VIS_ISTANZE)).equals("1")) {
		    displayTr = "";
		    styleTr="sezioneDatiMeno";
		} else {
		    displayTr = "display:none;";
		    styleTr="sezioneDatiPiu";
		}
	%>
	<fieldset>
		<legend>
			<a class="<%= styleTr%>"
				id="id_link_istanze"
				href="javascript:showHidePanel('istanze_table', 'id_link_istanze','<%= WebConstants.CONF_UTENTE_VIS_ISTANZE%>','${pageContext.request.contextPath}/images/','div');"
				title="<fmt:message key="label.mostra_nasconde_sezione" /> <fmt:message key="label.istanze" />">
				<label for="id_link_istanze"><fmt:message key="label.istanze" /></label> 
			</a>						
		</legend>
		<!-- La variabile "page" istanzestoricos recupera le istanze che sono legate all'attività sia che vogliamo visualizzare
		lo storico delle istanze che la compongono ,sia che vogliamo visualizzare le informazioni solo dell'ultima istanza legata
		all'attività  
		 -->
		<c:choose>
			<c:when test="${iattivitaCommand.visstorico}">
				<c:set value="${iattivitaCommand.istanzeHelper.istanzes}" var="istanzestoricos" scope="page"></c:set>
			</c:when>
			<c:otherwise>
				<c:set value="${iattivitaCommand.istanzeHelper.storicoistanzes}" var="istanzestoricos" scope="page"></c:set>
			</c:otherwise>
		</c:choose>
		<div class="jmesa" id="istanze_table" style="<%=displayTr%>">
			<table border="0" width="100%" cellpadding="2" cellspacing="0" class="table">
				<thead>
					<tr class="header">
						<td><fmt:message key="label.numero"/></td>
						<td><fmt:message key="label.data"/></td>
						<td><fmt:message key="label.data_validita"/></td>
						<td><fmt:message key="label.richiedente"/></td>
						<td><fmt:message key="label.richiedente_storico"/></td>
						<td><fmt:message key="label.indirizzo"/></td>
						<td><fmt:message key="label.posizione_in_archivio" /></td>
						<td><fmt:message key="label.intervento"/></td>
						<td><fmt:message key="label.procedura"/></td>
						<td><fmt:message key="label.stato"/></td>
						<td><fmt:message key="label.azione"/></td>
						<td><fmt:message key="label.copia"/></td>
						<td width="15%"><fmt:message key="label.ordine"/>
						</td>
					</tr>
				</thead>
				<tbody class="tbody">
				
				
				<c:forEach items="${istanzestoricos}" var="istanzaHelper" varStatus="status">																								
						<c:set var="trStyle" value="odd"/>
						<c:if test="${(status.index mod 2) eq 0}">
							<c:set var="trStyle">even</c:set>
						</c:if>
						<tr class="${trStyle}">
								<td><a href="javascript:historySet('${_urlback}', '../istanze/view.htm?codice=${istanzaHelper.id.codice}&software=${istanzaHelper.software.codice}', '')">${istanzaHelper.numeroistanza}</a>
								</td>													
								<td><fmt:formatDate value="${istanzaHelper.data}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>														
								<td><fmt:formatDate value="${istanzaHelper.datavalidita}" pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>"/></td>
							    <td>${istanzaHelper.transientRichiedenteQualitaAzienda}</td>
							    <td>${istanzaHelper.transientRichiedenteQualitaAziendaStorico}</td>
								<td>${istanzaHelper.transientLocalizzazionePrimario}</td>
								<td>${istanzaHelper.posizionearchivio}</td>
								<td>${istanzaHelper.alberoproc.vwAlberoproc.scDescrizione}</td>
								<td>${istanzaHelper.procedura.procedura}</td>
								<td>${istanzaHelper.chiusura.stato}</td>
								<td>${istanzaHelper.azione}</td>
								<c:if test="${status.index!=fn:length(istanzestoricos)-1}"> 
									<td >
										<a href="../iattivita/copiaTutti.htm?codiceIstanza=${istanzaHelper.id.codice}&codice=${iattivitaCommand.entity.id.codice}" title="<fmt:message key="label.copia_tutto"/>"><fmt:message key="label.T"/></a>
										<a href="javascript:doSubmit('../iattivita/copiaSoggettiCollegati.htm?codiceIstanza=${istanzaHelper.id.codice}&codice=${iattivitaCommand.entity.id.codice}','',document.inviodati)" title="<fmt:message key="label.soggetti_collegati"/>"><fmt:message key="label.S"/></a>
										<a href="../iattivita/copiaMappali.htm?codiceIstanza=${istanzaHelper.id.codice}&codice=${iattivitaCommand.entity.id.codice}" title="<fmt:message key="label.mappali"/>"><fmt:message key="label.M"/></a>
										<a href="../iattivita/copiaDettaglioInformazioni.htm?codiceIstanza=${istanzaHelper.id.codice}&codice=${iattivitaCommand.entity.id.codice}" title="<fmt:message key="label.dettaglio_info"/>"><fmt:message key="label.D"/></a>
										<a href="../iattivita/copiaOrariApertura.htm?codiceIstanza=${istanzaHelper.id.codice}&codice=${iattivitaCommand.entity.id.codice}" title="<fmt:message key="label.orari"/>"><fmt:message key="label.O"/></a>
										<a href="../iattivita/copiaLocalizzazioni.htm?codiceIstanza=${istanzaHelper.id.codice}&codice=${iattivitaCommand.entity.id.codice}" title="<fmt:message key="label.localizzazioni"/>"><fmt:message key="label.L"/></a>
										<a href="../iattivita/copiaSchedeDinamiche.htm?codiceIstanza=${istanzaHelper.id.codice}&codice=${iattivitaCommand.entity.id.codice}" title="<fmt:message key="label.schede_dell_istanza"/>"><fmt:message key="label.S"/></a>
									</td>
								</c:if>
								<c:if test="${status.index==fn:length(istanzestoricos)-1}">
									<td>&nbsp;</td>
								</c:if>
								<td width="5%">
								<c:if test="${fn:length(istanzestoricos)>1}">
								<c:choose>	
									<c:when test="${(istanzestoricos[status.index].datavalidita!=null )&&(istanzestoricos[status.index].datavalidita eq istanzestoricos[status.index+1].datavalidita or istanzestoricos[status.index].datavalidita eq istanzestoricos[status.index-1].datavalidita)}">
										<input type="text"  value="${istanzaHelper.attivitaOrdine}" name="ordineIstanze" size="4"></input>
										<input type="hidden" value="${istanzaHelper.id.codice}" name="codiceIstanze"></input>
									    <c:set scope="page" value="true" var="viewSalvaOrdine"></c:set>
									    <c:if test="${istanzestoricos[status.index-1].datavalidita==istanzestoricos[status.index].datavalidita}">
											<a class="upColumn" href="upColumn.htm?codiceIstanzaSup=${istanzestoricos[status.index-1].id.codice}&codiceIstanza=${istanzestoricos[status.index].id.codice}" title="<fmt:message key="label.up" /> ">
												<label><fmt:message key="label.azioni" /></label>
											</a>
										</c:if>
									    <c:if test="${istanzestoricos[status.index].datavalidita == istanzestoricos[status.index+1].datavalidita }">
										    <a class="downColumn" href="downColumn.htm?codiceIstanza=${istanzestoricos[status.index].id.codice}&codiceIstanzaInf=${istanzestoricos[status.index+1].id.codice}" title="<fmt:message key="label.down" /> ">
												<label><fmt:message key="label.azioni" /></label>
											</a>
										</c:if>
									</c:when>
									<c:when test="${istanzestoricos[status.index].datavalidita==null && istanzaDataValiditaNull>1}">
										<input type="text"  value="${istanzaHelper.attivitaOrdine}" name="ordineIstanze" size="4"></input>
										<input type="hidden" value="${istanzaHelper.id.codice}" name="codiceIstanze"></input>
									    <c:set scope="page" value="true" var="viewSalvaOrdine"></c:set>
									    <c:if test="${istanzestoricos[status.index-1].datavalidita==istanzestoricos[status.index].datavalidita}">
											<a class="upColumn" href="upColumn.htm?codiceIstanzaSup=${istanzestoricos[status.index-1].id.codice}&codiceIstanza=${istanzestoricos[status.index].id.codice}" title="<fmt:message key="label.up" /> ">
												<label><fmt:message key="label.azioni" /></label>
											</a>
										</c:if>
									    <c:if test="${status.index!=fn:length(istanzestoricos)-1}">
									       	<a class="downColumn" href="downColumn.htm?codiceIstanza=${istanzestoricos[status.index].id.codice}&codiceIstanzaInf=${istanzestoricos[status.index+1].id.codice}" title="<fmt:message key="label.down" /> ">
												<label><fmt:message key="label.azioni" /></label>
											</a>
										</c:if>
									</c:when> 
									<c:otherwise>
										<input type="hidden"  value="0" name="ordineIstanze" size="4"></input>
										<input type="hidden" value="${istanzaHelper.id.codice}" name="codiceIstanze"></input>
									</c:otherwise>
							    </c:choose>
							    </c:if>		
								</td>
						</tr>				
						<c:if test="${istanzestoricos[status.index].datavalidita ne istanzestoricos[status.index+1].datavalidita}">
						 <tr>
						  	<td colspan="13" class="rigaSeparazione"></td>
						 </tr>
						</c:if>
				</c:forEach> 
				        <tr>
				        	<td colspan="12">&nbsp;</td>
				            <td valign="top">
					            <c:if test="${viewSalvaOrdine eq 'true'}">
								<%-- <init:help idHelp="helpSalvataggio" textKey="label.aggiorna_ordine_istanze" />--%>
								<a class="aggiornaColumn" o id="mioRefId" href="javascript:doSubmit('updateOrdineIstanza.htm','',document.inviodati)" >
										<label><fmt:message key="label.aggiorna" /></label>
								</a>
								<script type="text/javascript">
									new dijit.Tooltip({
									   connectId: ["mioRefId"],									   
									   label: "<fmt:message key="label.aggiorna_ordine_istanze" />"
									});
								</script>
								</c:if>	
								
				            </td>
				        </tr>
																			
				</tbody>
			</table>
		</div>							
	</fieldset>																	
</spring-form:form>
</div>
<div id="functions">
	<ul>
		<c:if test="${iattivitaCommand.entity.id.codice!=null}">
			<li><a href="javascript:doSubmit('update.htm','',document.inviodati)"><fmt:message key="button.update" /></a></li>
			
			
			<%-- SCHEDE --%>
			<c:set var="_URL_BACK" value="../iattivita/view.htm?codice=${iattivitaCommand.entity.id.codice}" scope="page"/>
			
			<%							
				pageContext.setAttribute("URL_ATTIVITADYN2_MODELLI", BackofficeNETConstants.getURL_ATTIVITA_DYN2_MODELLI());
			%>
			<c:set var="_URL_ATTIVITADYN2_MODELLI" value="${URL_ATTIVITADYN2_MODELLI}?CodiceAttivita=${iattivitaCommand.entity.id.codice}"/>			
			<c:set var="_URL_ATTIVITADYN2_MODELLI" value="${inite:linkschede(pageContext.request, _URL_ATTIVITADYN2_MODELLI,_URL_BACK, null, false, iattivitaCommand.entity.id.codice)}" />
			
			<li><a href="${_URL_ATTIVITADYN2_MODELLI}"><fmt:message key="button.schede" /></a></li>
			<c:if test="${isSchedePresenti eq false}">
				<li><a href="javascript:doSubmit('delete.htm','<fmt:message key="javascript.confirm.delete" />',document.inviodati)"><fmt:message key="button.delete" /></a></li> 
			</c:if>
			<li><a href="javascript:historySet('${_urlback}','../iattivita/viewStoricoSnapshot.htm?codiceattivita=${iattivitaCommand.entity.id.codice}','')"><fmt:message key="button.storico" /></a></li>
			
			<c:if test="${isSchedePresenti eq true}">
				<li><a href="javascript:cancellaIattivitaConfirm();"><fmt:message key="button.delete" /></a></li>
					<%-- DIV che contiene un dialog che viene aperto alla cancellazione di un attivita quando ad
						 essa sono collegate delle schede  --%>
					<div dojoType="dijit.Dialog" id="cancellaIattivitaDialogDiv" title="<fmt:message key="label.conferma_cancellazione" />" style="display: none;">
						<input type="checkbox" id="cancellazionedocistanzachk_id" onclick="showHideDiv('doDeleteId')"/>
						<label for="cancellazioneiattivitachk_id">
							<fmt:message key="label.messaggio_cancellazione_iattivita_con_schede_dinamiche"/><br /><br />
							<fmt:message key="label.messaggio_cancellazione_attivita_per_operatore">
								<fmt:param><spring-security:authentication property="principal.responsabile" /></fmt:param>
							</fmt:message>
						</label>
						<div id="functions">
							<ul>
								<li style="display: none;" id="doDeleteId"><a href="javascript:doSubmit('delete.htm','',document.inviodati)"><fmt:message key="button.delete" /></a></li>
								<li><a href="javascript:void 0" onclick="dijit.byId('cancellaIattivitaDialogDiv').hide();"><fmt:message key="button.annulla" /></a></li>
							</ul>
						</div>
						<br class="clear" />										
					</div>					
					<script type="text/javascript">
					function cancellaIattivitaConfirm(){
						dijit.byId('cancellaIattivitaDialogDiv').show();
					}	
					</script>
			</c:if>
		</c:if>
		 	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			
	</ul>
</div>
	<script type="text/javascript">
	
	
	function value()
	{
		var a=document.getElementById('id_data_validita0').value;
		alert(a);
	}
	
	var clickedOnce = false;
	function visualizzaStorico(chkbox,nomepreferenza){
		if(!clickedOnce){
			clickedOnce = true;
			salvaPreferenza( nomepreferenza , chkbox );
			setTimeout("doSubmit('view.htm?codice=${iattivitaCommand.entity.id.codice}','')", 300);
		}
	}
	
	
	function salvaPreferenza(nomeparametro, objchk){
		var valore = "0";	
		if(objchk.checked == true){
			valore="1";	
		}
		saveUserPreference(nomeparametro, valore);
	}
	/**
	* La funzione javascript è utilizzata per mostrare un tooltip di spiegazione quando si passa sopra checkbox che attivano lo storico 
	* della varie sezioni.
	*/
	function showTooltip(id){
	   dijit.showTooltip('<fmt:message key="label.attivita_help_visualizza_storico_sezione" />', dojo.byId(id));
	   setTimeout('dijit.hideTooltip(dojo.byId('+id+'))', 3000);
	};


	/**
	*
	*
	*
	*/
	</script>
	
	
	
</body>
</html>