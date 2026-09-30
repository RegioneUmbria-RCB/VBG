<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="it.gruppoinit.sde.pratiche.EstremiAttoEstesoType"%>
<%@page import="java.util.Calendar"%>
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="it.gruppoinit.sde.pratiche.DettaglioAttivitaType"%>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="it.gruppoinit.sde.pratiche.DettaglioPraticaVisuraType" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><fmt:message key="label.visura-istanza" /></title>
</head>
<body>
	<div class="titolo"><fmt:message key="label.visura-istanza" /></div>
	<div class="descrizione"></div>
	<fieldset>
	<div id="datigenerali" class="sezione">
	
	<label class="titolo"><fmt:message key="label.dati-istanza" /></label>
	<div>
	<fmt:message key="label.numero-istanza" />: <b>${istanza.dettaglioPratica.numeroPratica } </b>
	<c:if test="${not empty istanza.dettaglioPratica.codicePraticaTelematica}">
	<br />Codice pratica telematica: <b>${istanza.dettaglioPratica.codicePraticaTelematica }</b><br />
	</c:if>
	<%
	Date dprat = ((DettaglioPraticaVisuraType)request.getAttribute("istanza")).getDettaglioPratica().getDataPratica().toGregorianCalendar().getTime(); 
	pageContext.setAttribute("_dataPratica", dprat);
	XMLGregorianCalendar dProt = ((DettaglioPraticaVisuraType)request.getAttribute("istanza")).getDettaglioPratica().getDataProtocolloGenerale();
	if(dProt!=null){
	    Date _dProt = dProt.toGregorianCalendar().getTime();
	    pageContext.setAttribute("_dataProtocollo", _dProt);
	}
	
	%>
	<fmt:formatDate value="${_dataPratica}" var="dataPratica" pattern="dd/MM/yyyy" />
	<fmt:formatDate value="${_dataProtocollo}" var="dataProtocollo" pattern="dd/MM/yyyy" />
	<fmt:message key="label.data-istanza" />: <b><c:out value="${dataPratica}" /></b><br />
	<c:if test="${not empty istanza.dettaglioPratica.numeroProtocolloGenerale}">
		<fmt:message key="label.numero-protocollo" />: <b><c:out value="${istanza.dettaglioPratica.numeroProtocolloGenerale }" default="-" /> </b>
	<fmt:message key="label.data-protocollo" />: <b><c:out value="${dataProtocollo}" default="-" /></b><br />
	</c:if>
	<c:if test="${istanza.responsabileProcedimento}">
		<br />Responsabile del procedimento: <b>${istanza.responsabileProcedimento}</b>
	</c:if>
	
	</div>
	</div>
	<div id="richiedente" class="sezione">
		<label class="titolo">Richiedente</label>
		<div>
				nominativo:<b> ${istanza.dettaglioPratica.richiedente.anagrafica.nome}&nbsp;${istanza.dettaglioPratica.richiedente.anagrafica.cognome}  (${istanza.dettaglioPratica.richiedente.anagrafica.codiceFiscale})</b>
				<br />
				<c:if test="${not empty istanza.dettaglioPratica.richiedente.ruolo.ruolo }">								
					in qualita' di <b>${istanza.dettaglioPratica.richiedente.ruolo.ruolo}</b>
				</c:if>
				<c:if test="${not empty istanza.dettaglioPratica.aziendaRichiedente }">
					della ditta: <b>${istanza.dettaglioPratica.aziendaRichiedente.ragioneSociale} (cf=${istanza.dettaglioPratica.aziendaRichiedente.codiceFiscale}, piva=${istanza.dettaglioPratica.aziendaRichiedente.partitaIva})</b>
				</c:if>
		</div>
	</div>
	<c:if test="${not empty istanza.dettaglioPratica.intermediario }">
		<div id="intermediario" class="sezione">
			<label class="titolo">Intermediario</label>
			<div>
				<c:if test="${not empty istanza.dettaglioPratica.intermediario.personaFisica }">		
					nominativo:<b> ${istanza.dettaglioPratica.intermediario.personaFisica.nome}&nbsp;${istanza.dettaglioPratica.intermediario.personaFisica.cognome}  (${istanza.dettaglioPratica.intermediario.personaFisica.codiceFiscale})</b>
					<br />
				</c:if>
				<c:if test="${not empty istanza.dettaglioPratica.intermediario.personaGiuridica }">
					nominativo:<b> <b>${istanza.dettaglioPratica.intermediario.personaGiuridica.ragioneSociale} (cf=${istanza.dettaglioPratica.intermediario.personaGiuridica.codiceFiscale}, piva=${istanza.dettaglioPratica.intermediario.personaGiuridica.partitaIva})</b>				
				</c:if>
				</div>
					
		</div>		
	</c:if>

	
	
	<div id="oggetto" class="sezione">
		<label class="titolo">Tipologia della pratica</label>
		<div>
				<c:if test="${not empty istanza.dettaglioPratica.oggetto }">								
					oggetto: <b>${istanza.dettaglioPratica.oggetto}</b> <br />
				</c:if>
				<c:if test="${not empty istanza.dettaglioPratica.intervento }">
					intervento: <b>${istanza.dettaglioPratica.intervento.descrizione}</b> <br />
				</c:if>
		</div>
	</div>			
	
	<c:if test="${not empty istanza.dettaglioPratica.documenti}">
		<div id="allegati" class="sezione">
			<label class="titolo">Allegati</label>
			<div>
				<table width="100%">
				<tr>
					<th>Allegato</th>
					<th>File</th>
				</tr>			
				<c:forEach items="${ istanza.dettaglioPratica.documenti }" var="doc">
				<tr>
					<td>${doc.documento}</td>
					<td>
						<c:if test="${not empty doc.allegati}">
							<a href="#" title="scarica file" onclick="scaricaFile(${ doc.allegati.id });"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"></a> ${ doc.allegati.allegato } 														
						</c:if>
					</td>
				</tr>				
				</c:forEach>	
				</table>	
			</div>
	</div>
	</c:if>
	
	</fieldset>						
	<fieldset>
	
	<c:if test="${not empty istanza.listaAttivita}">
		<div id="attivita" class="sezione">
			<label class="titolo"><fmt:message key="label.lista-attivita" /></label>
			<div>
				<table width="100%">
				<tr>
					<th>Descrizione</th>
					<th>Data</th>
					
					<th>Parere</th>
					<th width="20%">Allegati</th>
				</tr>			
				<c:forEach items="${ istanza.listaAttivita }" var="doc">
				<tr>
					<td>${doc.tipoAttivita.descrizione}</td>
					<td>
						<%
						XMLGregorianCalendar cal = (XMLGregorianCalendar)((DettaglioAttivitaType) pageContext.getAttribute("doc")).getDataAttivita();
						if(cal!=null){
						Calendar c = cal.toGregorianCalendar();
						out.print(Utilities.formatDate(c.getTime(), false));
						}
						%>
					</td>
					
					<td>
						<c:out value="${doc.parere}"  escapeXml="true"/> 						
					</td>
					<td>
						<c:if test="${not empty  doc.documenti  }">
						<table width="100%">
						<c:forEach items="${ doc.documenti }" var="doc">
						<tr>
							<td>${doc.documento}</td>
							<td>
								<c:if test="${not empty doc.allegati}">
									<a href="#" title="scarica file" onclick="scaricaFile(${ doc.allegati.id });"><img src="${pageContext.request.contextPath}/images/download16x16.png" border="0"></a> ${ doc.allegati.allegato }  									
								</c:if>
							</td>
						</tr>				
						</c:forEach>	
						</table>	
						</c:if>
					
					</td>
					
				</tr>				
				</c:forEach>	
				</table>	
			</div>
	</div>
	</c:if>		
	</fieldset>
	<c:if test="${not empty istanza.listaAtti}">
		<div id="attivita" class="sezione">
			<label class="titolo">Lista atti</label>
			<div>
				<table width="100%">
				<tr>
					<th>Numero</th>
					<th>Data</th>					
					<th>Registro</th>
				</tr>			
				<c:forEach items="${ istanza.listaAtti }" var="doc">
				<tr>
					<td>${doc.numero}</td>
					<td>
						<%
						XMLGregorianCalendar cal = (XMLGregorianCalendar)((EstremiAttoEstesoType) pageContext.getAttribute("doc")).getData();
						if(cal!=null){
						Calendar c = cal.toGregorianCalendar();
						out.print(Utilities.formatDate(c.getTime(), false));
						}
						%>
					</td>
					
					<td>
						${doc.tipoRegistro}						
					</td>										
				</tr>				
				</c:forEach>	
				</table>	
			</div>
	</div>
	</c:if>	
	
	</fieldset>
	
	
	<input type="button" value="<fmt:message key='button.chiudi' />" onclick="chiudi()"/>
	
	
	<script type="text/javascript">
		function scaricaFile(idAllegato){
			
			location.href = "../istanze/ajaxDownload.htm?idAllegato="+idAllegato+"&idPratica=${param.codiceIstanza}&codiceComune=${param.codiceComune}";			
		}
		function chiudi(){
			location.href = "../istanze/search.htm";			
		}
		
	</script>
	
</body>
</html>