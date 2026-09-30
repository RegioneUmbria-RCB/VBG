<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.StpCommand"%>
<%@page import="it.gruppoinit.sigepro.cart.schema.rfcsuap.ModalitaAperturaEndoTipo2"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>
			<fmt:message key="stp.label.titolo_schedaspiegazione.title" />
	</title>
</head>
<body>
<c:if test="${empty chiamataEsterna}">
	<span class="titoloPagina">
		<fmt:message key="stp.label.titolo_schedaspiegazione.title" />
	</span>
	<jsp:include page="../includes/innerNavigation.jsp">
		<jsp:param name="navmode" value="form"/>
	</jsp:include>
</c:if>
<div id="subcontent">
	<div class="parametriDiv">
		<div class="etichetta">
			<div >
				<fmt:message key="stp.label.nome_attivita" />:
			</div>
			<div>
				<fmt:message key="stp.label.tipologia_endoprocedimento" />:
			</div>
		<c:if test="${empty chiamataEsterna}">
			<div>
				<fmt:message key="label.salva_come" />:
			</div>
		</c:if>	
		</div>	
		<div class="parametro">
			<div>
				${stpCommand.tipologiaEndoprocedimento }
			</div>
			<div >
				${stpCommand.nomeAttivita }
			</div>
		<c:if test="${empty chiamataEsterna}">
			<div>
				<a href="javascript:popup('ajaxDownloadPDFSchedaSpiegazioneEndo2.htm?codice=${param.codice}&tipo=PDF','','')"><img alt="<fmt:message key="label.pdf" />" src="${pageContext.request.contextPath}/images/table/pdf.gif" border="0"/></a>
			</div>
		</c:if>
		</div>		
	</div>
	<div class="clear"></div>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>B - Individuazione attività</legend>
	<br />
	${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.descrizioneRegionale }
	<c:if test="${not empty stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.descrizioneLocale}">
		<br />
		Informazioni specifiche del comune: ${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.descrizioneLocale}
	</c:if>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
		<legend>B1 - ALTRI ELEMENTI INFORMATIVI SPECIFICI RELATIVI ALL'ATTIVITA'</legend>		
	${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.altreInfoRegionali }
	<c:if test="${not empty stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.altreInfoLocali}">
		<br />
		${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.altreInfoLocali}
	</c:if>
	</fieldset>
	</fieldset>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>C - Che requisiti deve avere</legend>
	<br />
	<ol>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiOggettivi.testoRequisito != ''}">
		<li><b>Requisiti oggettivi</b><br />
			${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiOggettivi.testoRequisito }
		</li>
		</c:if>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiSoggettiviMorali.testoRequisito != ''}">
		<li><b>Requisiti soggettivi morali</b><br />
			${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiSoggettiviMorali.testoRequisito }
		</li>
		</c:if>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiSoggettiviProfessionali.testoRequisito != ''}">
		<li><b>Requisiti soggettivi professionali</b><br />
			${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiSoggettiviProfessionali.testoRequisito }
		</li>
		</c:if>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiExtracomunitari.testoRequisito != ''}">
		<li><b>Requisiti per i cittadini extracomunitari</b><br />
			${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiExtracomunitari.testoRequisito }
		</li>
		</c:if>
	</ol>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>C1 - Riferimenti Normativi di cui al quadro C</legend>
	<br />
		<ol>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiOggettivi.testoRequisito != ''}">
		<li><b>Requisiti oggettivi</b><br />
			<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiOggettivi.normativaRequisito}" var="var_oggettivi" varStatus="a">
			${a.index+1}) ${var_oggettivi.value} <br />  
			</c:forEach>
		</li>
		</c:if>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiSoggettiviMorali.testoRequisito != ''}">
		<li><b>Requisiti soggettivi morali</b><br />
			<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiSoggettiviMorali.normativaRequisito}" var="var_morali" varStatus="a">
			${a.index+1}) ${var_morali.value} <br />
			</c:forEach>
		</li>
		</c:if>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiSoggettiviProfessionali.testoRequisito != ''}">
		<li><b>Requisiti soggettivi professionali</b><br />
			<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiSoggettiviProfessionali.normativaRequisito}" var="var_professionali" varStatus="a">
			${a.index+1}) ${var_professionali.value} <br />   
			</c:forEach>
		</li>
		</c:if>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiExtracomunitari.testoRequisito != ''}">
		<li><b>Requisiti per i cittadini extracomunitari</b><br />
			<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoRequisiti.requisitiExtracomunitari.normativaRequisito}" var="var_extra" varStatus="a">
			${a.index+1}) ${var_extra.value}   <br />
			</c:forEach>
		</li>
		</c:if>
	</ol>
	</fieldset>
	<br />
	</fieldset>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>D - Endoprocedimenti relativi alla verifica dei requisiti previsti per i fabbricati e gli impianti</legend>
	<br />
	Prima di esaminare il quadro degli adempimenti eventualmente previsti per l'esercizio dell'attività  individuata nel quadro A 
	è necessario verificare che siano stati svolti tutti gli adempimenti previsti dalla normativa per realizzare/modificare i fabbricati 
	e gli impianti che verranno utilizzati per lo svolgimento dell'attività. 
	In via generale per la realizzazione/modifica dei fabbricati e degli impianti relativi all'attività individuata nel quadro A 
	è necessaria l'attivazione dei seguenti endoprocedimenti prima di quelli relativi all'esercizio dell'attività:
	Nota bene: gli endoprocedimenti qui indicati devono essere attivati anche quando non è richiesta nessuna domanda/dichiarazione/notifica per 
	l'esercizio dell'attività.
	<ol style="text-align: left;">
		<li><b>Adempimenti da effettuare prima dell'inizio dell'intervento sul fabbricato e/o sugli impianti</b><br />
		<div class="jmesa">
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td width="5%" >Adempimento</td>
					<td width="5%" >Codice dizionario</td>
					<td width="10%">Nome endoprocedimento (da dizionario)</td>
					<td width="2%">Obbligatorio</td>
				</tr>
			</thead>
			<tbody class="tbody">
			<%int w=1;%>
			<c:set var="rowColor" value='<%=(w%2)==0?"odd":"even"%>'></c:set>
			<c:forEach items="${stpCommand.inventarioprocedimentisPrima}" var="var" varStatus="status" >
				<c:set	value="<%=w-1 %>" var="indice"></c:set>
				<c:if test="${stpCommand.inventarioprocedimentisPrima[indice].chiave.tipoendo.tipifamiglieendo.tipo != stpCommand.inventarioprocedimentisPrima[indice-1].chiave.tipoendo.tipifamiglieendo.tipo}">
				<tr class='<%=((String)pageContext.getAttribute("rowColor")).equals("even")?"odd":"even"%>'>
					<td>${var.chiave.tipoendo.tipifamiglieendo.tipo}</td>
					<td>${var.chiave.codiceancitel}</td>
	                <td>${var.chiave.procedimento}</td>
	                <td>
	                	<c:choose>
	                		<c:when test="${var.valore eq true }">Sì</c:when>
	                		<c:otherwise>No</c:otherwise>
	                	</c:choose>
					</td>
				</tr>
				<c:set var="rowColor" value='<%=((String)pageContext.getAttribute("rowColor")).equals("even")?"odd":"even"%>'></c:set>
				</c:if>
				<c:if test="${stpCommand.inventarioprocedimentisPrima[indice].chiave.tipoendo.tipifamiglieendo.tipo eq stpCommand.inventarioprocedimentisPrima[indice-1].chiave.tipoendo.tipifamiglieendo.tipo}">
				<tr class="${rowColor }">
					<td style="border:0px;"></td>
					<td>${var.chiave.codiceancitel}</td>
	                <td>${var.chiave.procedimento}</td>
	                <td>
	                   	<c:choose>
	                		<c:when test="${var.valore eq true }">Sì</c:when>
	                		<c:otherwise>No</c:otherwise>
	                	</c:choose>
					</td>
				</tr>
				</c:if>
				 <%w++; %>
			</c:forEach>
			</tbody>
		</table>
		
		<ul style="text-align: left; border-top: 1px dotted;">
		<li><b>Adempimenti locali da effettuare prima dell'inizio dell'intervento sul fabbricato e/o sugli impianti</b><br />
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td width="5%">Adempimento</td>
					<td width="5%">Allegati</td>					
				</tr>
			</thead>
			<tbody class="tbody">
			<%w=1;%>
			<c:set var="rowColor" value='<%=(w%2)==0?"odd":"even"%>'></c:set>
			<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.elencoEndoPrevistiPrima.elencoEndoLocali.endoLocale}" var="var" varStatus="status" >
				<c:set	value="<%=w-1 %>" var="indice"></c:set>
				<tr class="${rowColor }">					
					<td style="vertical-align: top">(${var.codice}) - ${var.nome}</td>
					<td style="vertical-align: top">					
					<table>					
					<c:forEach items="${var.elencoQuadriStandard5.quadro}"  var="q">
						<c:forEach items="${q.elencoAllegatiRichiestiQuadro.allegatoRichiesto}"  var="arqs" varStatus="allStat">
						<tr>
							<td style="vertical-align: top">${arqs.spiegazioniAllegato }</td>
			    		</tr>
			    		</c:forEach>											
					</c:forEach>
					</table>
					</td>	                
				</tr>
				<tr>
					<td colspan="2" style="border-bottom: 1px  dotted;">&nbsp;</td>
				</tr>
				 <%w++; %>
			</c:forEach>
			</tbody>
		</table>
		</li>
		</ul>
		
		</div>
		</li>
		<li><b>Adempimenti da effettuare dopo la conclusione dell'intervento sul fabbricato e/o sugli impianti</b><br />
		<div class="jmesa">
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
			<thead>
				<tr class="header">
					<td width="5%" >Adempimento</td>
					<td width="5%" >Codice dizionario</td>
					<td width="10%">Nome endoprocedimento (da dizionario)</td>
					<td width="2%" >Obbligatorio</td>
				</tr>
			</thead>
			<tbody class="tbody">
			<%int z=1;%>
			<c:set var="rowColor" value='<%=(z%2)==0?"odd":"even"%>'></c:set>
			<c:forEach items="${stpCommand.inventarioprocedimentisDopo}" var="var" varStatus="status" >
				<c:set	value="<%=z%>" var="indice"></c:set>
				<c:if test="${stpCommand.inventarioprocedimentisDopo[indice-1].chiave.tipoendo.tipifamiglieendo.tipo != stpCommand.inventarioprocedimentisDopo[indice-2].chiave.tipoendo.tipifamiglieendo.tipo}"> 
				<tr class='<%=((String)pageContext.getAttribute("rowColor")).equals("even")?"odd":"even"%>'>
					<td>${var.chiave.tipoendo.tipifamiglieendo.tipo}</td>
					<td>${var.chiave.codiceancitel}</td>
	                <td>${var.chiave.procedimento}</td>
					<td>
	                   	<c:choose>
	                		<c:when test="${var.valore eq true }">Sì</c:when>
	                		<c:otherwise>No</c:otherwise>
	                	</c:choose>
					</td>	                
				</tr>
				<c:set var="rowColor" value='<%=((String)pageContext.getAttribute("rowColor")).equals("even")?"odd":"even"%>'></c:set>
				</c:if>
				<c:if test="${stpCommand.inventarioprocedimentisDopo[indice-1].chiave.tipoendo.tipifamiglieendo.tipo eq stpCommand.inventarioprocedimentisDopo[indice-2].chiave.tipoendo.tipifamiglieendo.tipo}">
				<tr class="${rowColor }">
					<td style="border:0px;"></td>
					<td>${var.chiave.codiceancitel}</td>
	                <td>${var.chiave.procedimento}</td>
					<td>
	                   	<c:choose>
	                		<c:when test="${var.valore eq true }">Sì</c:when>
	                		<c:otherwise>No</c:otherwise>
	                	</c:choose>
					</td>	                
				</tr>
				</c:if>
				 <%z++; %>
			</c:forEach>
			</tbody>
		</table>
		
		<ul style="text-align: left;">
		<li><b>Adempimenti locali da effettuare dopo la conclusione dell'intervento sul fabbricato e/o sugli impianti</b><br />
		<table border="0"  cellpadding="2" cellspacing="0" class="table">
		
			<thead>
				<tr class="header">
					<td width="5%">Adempimento</td>
					<td width="5%">Allegati</td>					
				</tr>
			</thead>
			<tbody class="tbody">
			<%z=1;%>
			<c:set var="rowColor" value='<%=(z%2)==0?"odd":"even"%>'></c:set>
			<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.elencoEndoPrevistiDopo.elencoEndoLocali.endoLocale}" var="var" varStatus="status" >
				<c:set	value="<%=z-1 %>" var="indice"></c:set>
				<tr class="${rowColor }">					
					<td style="vertical-align: top">(${var.codice}) - ${var.nome}</td>
					<td style="vertical-align: top">					
					<table>					
					<c:forEach items="${var.elencoQuadriStandard5.quadro}"  var="q">
						<c:forEach items="${q.elencoAllegatiRichiestiQuadro.allegatoRichiesto}"  var="arqs" varStatus="allStat">
						<tr>
							<td style="vertical-align: top">${arqs.spiegazioniAllegato }</td>
			    		</tr>
			    		</c:forEach>											
					</c:forEach>
					</table>
					</td>	                
				</tr>
				<tr>
					<td colspan="2" style="border-bottom: 1px  dotted;">&nbsp;</td>
				</tr>
				 <%z++; %>
			</c:forEach>
			</tbody>					
		</table>
		</li>
		</ul>
		
		
		</div>
		</li>
	</ol>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;" >
	<legend>D1 - Normativa applicabile agli endoprocedimenti indicati nei quadri D</legend>
	<br />
	Vengono indicati i riferimenti normativi degli endoprocedimenti specifici relativi all'attività. 
	Per gli altri riferimenti normativi degli endoprocedimenti indicati al quadro D di carattere generale  
	il riferimento è presente nelle schede di spiegazione dei singoli endoprocedimenti apribili nel quadro D.
	<div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header" >
				<td width="5%" >Adempimento</td>
				<td width="5%" >Norme Nazionali</td>
				<td width="10%">Norme Regionali</td>
			</tr>
		</thead>
		<tbody class="tbody">
		<%int i=1;%>
		<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoNormativeRegionaliEndoTipo1.normativaRegionale}" var="var" varStatus="status" >
			<tr class= '<%=(i%2)==0?"odd":"even"%>' style="text-align: left;">
				<td>
				${adempimentiMap[var.adempimentoNormativa]}				
				</td>
                <td>${var.normaNazionale.value}</td>
                <td>${var.normaRegionale.value}</td>
			</tr>
			 <%i++; %>
		</c:forEach>
		</tbody>
	</table>
	</div>
	</fieldset>
	<br />
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;"> 
	<legend >E - Cosa serve per iniziare l'esercizio dell'attività</legend>
	<br />
	<ol>
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.modalitaAperturaStandard != null}">
			<%=((StpCommand)request.getAttribute("stpCommand")).getInvioSchedaEndoTipo2().getParteRegionaleSchedaEndoTipo2().getModalitaAperturaStandard().value() %>
		</c:if>
		<%--
		<c:if test="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.modalitaAperturaNonStandard.descrizioneApertura != null}">
			<li>${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.modalitaAperturaNonStandard.descrizioneApertura}</li>
		</c:if>
		 --%>
	</ol>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;" >
	<legend >E1 Normativa applicabile agli endoprocedimenti indicati nei quadri E</legend>
	<div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="5%" >Adempimento</td>
				<td width="5%" >Norme Nazionali</td>
				<td width="10%">Norme Regionali</td>
			</tr>
		</thead>
		<tbody class="tbody">
		<%int k=1;%>
		<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoNormativeRegionaliEndoTipo2.normativaRegionale}" var="var" varStatus="status" >
			<tr class='<%=(k%2)==0?"odd":"even"%>'>
				<td>${adempimentiMap[var.adempimentoNormativa]}</td>
                <td>${var.normaNazionale.value}</td>
                <td>${var.normaRegionale.value}</td>
			</tr>
			 <%k++; %>
		</c:forEach>
		</tbody>
	</table>
	</div>
	</fieldset>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>E2 Se sono necessari endoprocedimenti per l'esercizio dell'attività  indicare cosa si deve fare per presentare la
	documentazione</legend>
	<br />
	<ul>
		<li>A chi si presenta la documentazione:<b>${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.documentazioneLocale.destinatarioDocumentazione}</b>
		
		</li>
		<li>Quando si può iniziare l'attività : <b>${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.documentazioneRegionaleEndoTipo2.quandoIniziare}</b></li>
		<li>Precisare se sono necessarie altre comunicazioni prima di iniziare: <b>${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.documentazioneRegionaleEndoTipo2.altreComunicazioniNecessarie}</b></li>
		<li>Tempi previsti per la conclusione del procedimento: <b>${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.documentazioneRegionaleEndoTipo2.tempiPrevistiConclusione}</b></li>
		<c:if test="${not empty stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.documentazioneLocale.noteDocumentazione}">		
			<li>Ulteriori annotazioni sulla documentazione: <b>${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.documentazioneLocale.noteDocumentazione}</b></li>
		</c:if>
	</ul>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>E2.1 Dichiarazioni relative alla specifica attività</legend>
	<br />
	${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.tabella.formulazione}
	<c:if test="${empty chiamataEsterna}">
		<c:if test="${fn:length(stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.tabella.dichiarazioni.datiFile)>0}">
			<a href="downloadAllegatoTabella.htm?codice=${param.codice}&nomeFile=${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.tabella.dichiarazioni.nomeFile}" target="_blank"> ${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.tabella.dichiarazioni.nomeFile}</a>
		</c:if>
	</c:if>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;" >
	<legend>E3 Quanto e come si deve pagare per gli endoprocedimenti indicati nei quadri E</legend>
	<br />
	<ul>
		<li>Marche da bollo: <b>
			${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.pagamentoRegionaleEndoTipo2.marcheDaBollo}
			</b></li>
		<li>Contributi/Oneri: <b>${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.pagamentoLocale.contributiOneri}
		&nbsp;${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.pagamentoLocale.contributiOneriValore}</b>
		</li>
		<li>Diritti di segreteria: <b>${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.pagamentoLocale.dirittiSegreteria}</b>			
		</li>
		<li>Diritti di istruttoria SUAP: <b>${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.pagamentoLocale.dirittiIstruttoriaSUAP}
		&nbsp;${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.pagamentoLocale.dirittiIstruttoriaSUAPValore}</b></li>
		<li>Indicazioni sui pagamenti: <b>${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.pagamentoLocale.notePagamento}</b>
		</li>
	</ul>
	</fieldset>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;">
	<legend>E4 Elenco allegati da produrre e relative spiegazioni</legend>
	<br />
	<div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="5%" >Adempimento</td>
				<td width="5%" >Tipologia</td>
				<td width="10%">Codice</td>
				<td width="10%">Spiegazioni</td>
			</tr>
		</thead>
		<tbody class="tbody">
		<%int j=1;%>
		<c:forEach items="${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.elencoAllegatiRichiesti.allegatoRichiesto}" var="var" varStatus="status" >
			<tr class='<%=(j%2)==0?"odd":"even"%>'>
				<td>${var.adempimentoAllegato}</td>
                <td>${var.tipologiaAllegato}</td>
                <td>${var.codiceAllegato}</td>
                <td>${var.spiegazioniAllegato}</td>
			</tr>
			 <%j++; %>
		</c:forEach>
		</tbody>
	</table>
	</div>
	</fieldset>
	<br />
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>F - Adempimenti successivi</legend>
	<br />
		${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.adempimentiSuccessiviRegionali}
		
		<c:if test="${not empty stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.adempimentiSuccessiviLocali}">
			${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.adempimentiSuccessiviLocali}
		</c:if>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend >G - Quando scade l'endoprocedimento relativo all'esercizio di attività</legend>
	<br />
	<ol>
		<li>Indicare la data di scadenza: <b>${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.scadenzaEndoTipo2.dataScadenza}</b></li>
		<li>Come si rinnova (indicare cosa bisogna fare per il rinnovo): <b>${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.scadenzaEndoTipo2.modalitaRinnovo}</b></li>
		<li>Dove si rinnova (indicare l'ente competente): <b>${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.scadenzaEndoTipo2.enteCompetenteRinnovo}</b></li>
	</ol>
	</fieldset>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>H - Note</legend>
	
		${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.noteRegionali}
		<c:if test="${not empty stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.noteLocali}">
			<br />${stpCommand.invioSchedaEndoTipo2.parteLocaleSchedaEndoTipo2.noteLocali}
		</c:if>
		<br />
		<fieldset style="padding: 10px;line-height: 15pt;">
		<legend>Riepilogo validità scheda</legend>
		<ul>
			<li>Data inizio: <b>${stpCommand.dataInizioValidita}</b></li> 
			<li>Data fine: <b><c:if test="${stpCommand.dataFineValidita == '31/12/9999'}">in corso di validità</c:if><c:if test="${stpCommand.dataFineValidita != '31/12/9999'}">${stpCommand.dataFineValidita}</c:if></b></li>
		</ul>
		</fieldset>
		<br />
	</fieldset>
	<br />
</div>
<c:if test="${empty chiamataEsterna}">
	<div id="functions">
		<ul>
		
			<li><a href="javascript:doHref('../partelocaleschedaendotipo2/create.htm?codice=${stpCommand.invioSchedaEndoTipo2.parteRegionaleSchedaEndoTipo2.endoprocedimento }','')"><fmt:message key="button.localizza_scheda_endo" /></a></li>
		
			<li><a href="javascript:doHref('../history/back.htm?<%= WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</c:if>
</body>
</html>
