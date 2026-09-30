<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.pal.gp.core.domain.web.StpCommand"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../includes/taglibs.jsp" %>
<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
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

	<div class="sezione">
		<div>
			<div class="titolo2"  style="text-align: center">
				<b>${stpCommand.inventarioprocedimenti.tipoendo.tipifamiglieendo.tipo }</b>
			</div>
			<div class="titolo"  style="text-align: center">
				<b>${stpCommand.inventarioprocedimenti.procedimento }</b>
			</div>
		</div>		
	</div>
	
	<div class="clear"></div>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>B - Chi è interessato</legend>
	<ol>
		<li>Interessati<br />
		${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.interessato }
		</li>
	</ol>
	</fieldset>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>C - Che caratteristiche o requisiti deve avere il fabbricato o l'impianto</legend>
	<ol>
		<li>Requisiti<br />
		${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.requisito }
		</li>
	</ol>
	</fieldset>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>D - Cosa si deve fare</legend>
		<ol>
		<c:if test="${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.modalitaAperturaStandard != null}">
			<%=((StpCommand)request.getAttribute("stpCommand")).getInvioSchedaEndoTipo1().getParteRegionaleSchedaEndoTipo1().getModalitaAperturaStandard().value() %>
		</c:if>		
		</ol>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>E - Normativa applicabile</legend>
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
		<c:forEach items="${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.elencoNormativeRegionaliEndoTipo1.normativaRegionale}" var="var" varStatus="status" >
			<tr class='<%=(k%2)==0?"odd":"even"%>'>
				<td>
					${adempimentiMap[var.adempimentoNormativa]}
				</td>
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
	<legend>F - Cosa si deve fare per presentare la	documentazione</legend>
	<ul>
		<li>A chi si presenta la documentazione:<b>${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.documentazioneLocale.destinatarioDocumentazione}
		</b></li>
		<li>Quando si può iniziare l'attività : <b>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.documentazioneRegionaleEndoTipo1.quandoIniziare}</b></li>
		<li>Tempi previsti per la conclusione del procedimento: <b>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.documentazioneRegionaleEndoTipo1.tempiPrevistiConclusione}</b></li>
		<c:if test="${not empty stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.documentazioneLocale.documentazioneLocale.noteDocumentazione}">		
			<li>Ulteriori annotazioni sulla documentazione: <b>${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.documentazioneLocale.noteDocumentazione}</b></li>
		</c:if>
	</ul>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;" >
	<legend>G - Quanto e come si deve pagare</legend>
	<ul>
		<li>Marche da bollo: <b>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.pagamentoRegionaleEndoTipo1.marcheDaBollo}</b></li>
		<li>Contributi/Oneri: <b>${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.pagamentoLocale.contributiOneri}
		&nbsp;${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.pagamentoLocale.contributiOneriValore}
		</b></li>
		<li>Diritti di segreteria: <b>${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.pagamentoLocale.dirittiSegreteria}</b></li>
		<li>Diritti di istruttoria SUAP: <b>${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.pagamentoLocale.dirittiIstruttoriaSUAP}
		&nbsp;${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.pagamentoLocale.dirittiIstruttoriaSUAPValore}</b></li>
		<li>Diritti di istruttoria dell'ente terzo: <b>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.pagamentoRegionaleEndoTipo1.dirittiIstruttoriaEnteTerzo}</b></li>
		<li>Indicazioni sui pagamenti: <b>${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.pagamentoLocale.notePagamento}</b>
		</li>
		
	</ul>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>H - Flusso dell’endoprocedimento</legend>
	<div class="jmesa">
	<table border="0"  cellpadding="2" cellspacing="0" class="table">
		<thead>
			<tr class="header">
				<td width="5%" >Descrizione del flusso</td>
				<td width="5%" >Sub procedimento</td>
				<td width="10%">Ente competente</td>
			</tr>
		</thead>
		<tbody class="tbody">
		<%int w=1;%>
		<c:forEach items="${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.flusso.elencoSubprocedimentiPrevisti.subprocedimento}" var="var" varStatus="status" >
			<tr class='<%=(w%2)==0?"odd":"even"%>'>
				<td>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.flusso.descrizioneFlusso}</td>
                <td>${var.nomeSubprocedimento}</td>
                <td>${var.enteCompetenteSubprocedimento}</td>
			</tr>
			<%w++; %>
		</c:forEach>
		</tbody>
	</table>
	</div>
	</fieldset>
	<br />
	<fieldset  style="padding: 10px;line-height: 15pt;">
	<legend>I - Elenco allegati da produrre e relative spiegazioni</legend>
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
		<c:forEach items="${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.elencoAllegatiRichiesti.allegatoRichiesto}" var="var" varStatus="status" >
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
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>L - Adempimenti successivi</legend>
	<ol>
		<li>
			${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.adempimentiSuccessiviRegionali}
		</li>
	</ol>	
	<c:if test="${not empty stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.adempimentiSuccessiviLocali}">
		<ol>
			<li>
			${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.adempimentiSuccessiviLocali}
			</li>
		</ol>	
	</c:if>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend >M - Quando scade il provvedimento conclusivo dell'endoprocedimento</legend>
	<ol>
		<li>Indicare la data di scadenza: <b>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.scadenzaEndoTipo1.dataScadenza}</b></li>
		<li>Come si rinnova (indicare cosa bisogna fare per il rinnovo): <b>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.scadenzaEndoTipo1.modalitaRinnovo}</b></li>
		<li>Dove si rinnova (indicare l'ente competente): <b>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.scadenzaEndoTipo1.enteCompetenteRinnovo}</b></li>
	</ol>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>N - Note</legend>
	<ol>
		<li>Note regionali:<br />
			<b>${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.noteRegionali}</b>
		</li>
	</ol>
		<c:if test="${not empty stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.noteLocali}">
			<ol>
			<li>Annotazioni del comune:<br />
				<b>${stpCommand.invioSchedaEndoTipo1.parteLocaleSchedaEndoTipo1.noteLocali}</b>
			</li>
		</ol>			
		</c:if>
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
	<legend>Quadri aggiuntivi</legend>
		<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
		<legend>Lista di controllo standard 5</legend>
			<div class="jmesa">
			<table border="0"  cellpadding="2" cellspacing="0" class="table">
				<thead>
					<tr class="header">
						<td width="5%" >Quadro</td>
						<td width="5%" >Allegati</td>
					</tr>
				</thead>
				<tbody class="tbody">
				<%int x=1;%>
				<c:forEach items="${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.elencoQuadriStandard5.quadro}" var="var" varStatus="status" >
					<tr class='<%=(x%2)==0?"odd":"even"%>'>
						<td><a href="javascript:doHref('downloadAllegatoQuadro.htm?nomefile=${var.testoQuadro.nomeFile}&codice=${codice}&tipo=<%=WebConstants.STP_QUADRO5%>','')"> ${var.testoQuadro.nomeFile}</a></td>
		                <td>
		                <c:forEach items="${var.elencoAllegatiRichiestiQuadro.allegatoRichiesto}" var="var_allegati" varStatus="status" >
		                	<a href="javascript:doHref('downloadAllegatoQuadro.htm?nomefile=${var.testoQuadro.nomeFile}&codice=${codice}&tipo=<%=WebConstants.STP_QUADRO5_ALLEGATI%>','')" style="text-decoration: underline;">${var_allegati.templateAllegato.nomeFile}</a>
		                </c:forEach>
		                </td>
		            </tr>
					 <%x++; %>
				</c:forEach>
				</tbody>
			</table>
		</div>
		</fieldset>
		<br />
		<fieldset style="padding: 10px;line-height: 15pt;text-align: justify;">
		<legend>Lista di controllo standard 6</legend>
			<div class="jmesa">
			<table border="0"  cellpadding="2" cellspacing="0" class="table">
				<thead>
					<tr class="header">
						<td width="5%" >Quadro</td>
						<td width="5%" >Allegati</td>
					</tr>
				</thead>
				<tbody class="tbody">
				<%int y=1;%>
				<c:forEach items="${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.elencoQuadriStandard6.quadro}" var="var" varStatus="status" >
					<tr class='<%=(y%2)==0?"odd":"even"%>'>
		                <td><a href="javascript:doHref('downloadAllegatoQuadro.htm?nomefile=${var.testoQuadro.nomeFile}&codice=${codice}&tipo=<%=WebConstants.STP_QUADRO6%>','')"> ${var.testoQuadro.nomeFile}</a></td>
		                <td>
		                <c:forEach items="${var.elencoAllegatiRichiestiQuadro.allegatoRichiesto}" var="var_allegati" varStatus="status" >
		                	<a href="javascript:doHref('downloadAllegatoQuadro.htm?nomefile=${var.testoQuadro.nomeFile}&codice=${codice}&tipo=<%=WebConstants.STP_QUADRO6_ALLEGATI%>','')" style="text-decoration: underline;">${var_allegati.templateAllegato.nomeFile}</a>
		                </c:forEach>
		                </td>
		            </tr>
					 <%y++; %>
				</c:forEach>
				</tbody>
			</table>
		</div>
		</fieldset>
		<br />
	</fieldset>
	<br />
	<fieldset style="padding: 10px;line-height: 15pt;">
		<legend>Riepilogo validità scheda</legend>
		<ul>
			<li>Data inizio: <b>${stpCommand.dataInizioValidita}</b></li> 
			<li>Data fine: <b><c:if test="${stpCommand.dataFineValidita == '31/12/9999'}">in corso di validità</c:if><c:if test="${stpCommand.dataFineValidita != '31/12/9999'}">${stpCommand.dataFineValidita}</c:if></b></li>
		</ul>
		</fieldset>
	<br />
</div>
<c:if test="${empty chiamataEsterna}">
<div id="functions">
<ul>

	<li><a href="javascript:doHref('../partelocaleschedaendotipo1/create.htm?codice=${stpCommand.invioSchedaEndoTipo1.parteRegionaleSchedaEndoTipo1.endoprocedimento }','')"><fmt:message key="button.localizza_scheda_endo" /></a></li>
		
	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
</ul>
</div>
</c:if>
</body>
</html>