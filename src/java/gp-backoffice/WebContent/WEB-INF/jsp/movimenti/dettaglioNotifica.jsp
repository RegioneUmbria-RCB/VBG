<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="it.gruppoinit.pal.gp.core.utils.Utilities"%>
<%@page import="java.util.Date"%>
<%@page import="it.gruppoinit.pal.gp.core.domain.Movimenti"%>
<%@page import="it.init.sigepro.rte.types.ProcedimentoType"%>
<%@page import="it.init.sigepro.rte.NotificaAttivitaRequest"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.net.URLEncoder"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="label.dettaglio_notifica.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="label.dettaglio_notifica.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
	<br />
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${movimento.istanza.id.codice}</c:param>
		</c:import>
		<div class="parametriDiv">
			<div class="etichetta">
				<div> <fmt:message key="label.data_invio" />:</div>
				<div><fmt:message key="label.movimento" />:</div>
				<div><fmt:message key="label.data" /> <fmt:message key="label.movimento" />:</div>
				<div><fmt:message key="label.data_scadenza" />:</div>
				<div><fmt:message key="label.numero_protocollo" />:</div>
				<div><fmt:message key="label.data_protocollo" />:</div>
				<div><fmt:message key="label.dettaglio_notifica.file_xml_richiesta" />:</div>		
				<c:if test="${not empty movimento.idAttDest }">		
					<div>Riferimento esterno:</div>
				</c:if>
			</div>
			<div class="parametro">
				<div>
				
					<%Movimenti mov = (Movimenti)request.getAttribute("movimento");
					String nomeFile = mov.getOggettoNotifica().getNomefile();
					String dataInvioStr = nomeFile.replaceAll("NotificaStc-", "");
					dataInvioStr = dataInvioStr.replaceAll("\\.xml", "");
					String data  = "";
					try{
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH-mm-ss-SSSZ");
						Date d = sdf.parse(dataInvioStr);					
						data = Utilities.formatDate(d, true);
					}catch(Exception e){
					    data = dataInvioStr;
					}
					out.print(data);
					%>
				</div>       		 					
				<div>					
					${notificarequest.datiAttivita.tipoAttivita.descrizione} - [${notificarequest.datiAttivita.tipoAttivita.codice}]							
				</div>
				<div>
					<%
					NotificaAttivitaRequest dettaglioNotifica = (NotificaAttivitaRequest)request.getAttribute("notificarequest");
					XMLGregorianCalendar xmlCal = dettaglioNotifica.getDatiAttivita().getDataAttivita();
					if(null!=xmlCal){
						Calendar cal = xmlCal.toGregorianCalendar();
						SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);				
						out.print(sdf.format(cal.getTime()));
					}
					%>		&nbsp;			
				</div>
				<div>
					<%
					dettaglioNotifica = (NotificaAttivitaRequest)request.getAttribute("notificarequest");
					xmlCal = dettaglioNotifica.getDatiAttivita().getDataScadenza();
					if(null!=xmlCal){
						Calendar cal = xmlCal.toGregorianCalendar();
						SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);				
						out.print(sdf.format(cal.getTime()));
					}
					%>		&nbsp;			
				</div>
				<div>
					${notificarequest.datiAttivita.numeroProtocolloGenerale}&nbsp;
				</div>
				<div>
					<%
					xmlCal = dettaglioNotifica.getDatiAttivita().getDataProtocolloGenerale();
					if(null!=xmlCal){
						Calendar cal = xmlCal.toGregorianCalendar();
						SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);				
						out.print(sdf.format(cal.getTime()));
					}
					%>					&nbsp;
				</div>		
				<div>
					<a class="visualizzaDocColumn" target="_blank" href="${pageContext.request.contextPath}/file/ajaxDownload.htm?fileId=${movimento.oggettoNotifica.id.codice}" target="_blank"  
						title="<fmt:message key="form.oggetti.view" />">
					   	<label><fmt:message key="label.visualizza.image" /></label>
					</a>&nbsp;
				</div>
				 <c:if test="${not empty movimento.idAttDest }">
                       <div>
                       ${movimento.idAttDest}
                       </div>
                 </c:if>
			</div>
		</div>
		<br class="clear" />
		
				
		
		<c:if test="${not empty notificarequest.datiAttivita.parere}">
			<fieldset><legend><b><fmt:message key="label.parere" /></b></legend>
			<div>
				<c:out value="${notificarequest.datiAttivita.parere}" escapeXml="false"/>
			</div>
			</fieldset>
		</c:if>
		
		<c:if test="${not empty notificarequest.datiAttivita.note}">
			<fieldset><legend><b><fmt:message key="label.note" /></b></legend>
			<div>
				<c:out value="${notificarequest.datiAttivita.note}" escapeXml="false"/>
			</div>
			</fieldset>
		</c:if>
		<br class="clear" />
		
	
		<%int j=1; %>
		<c:if test="${not empty notificarequest.datiAttivita.procedimenti}">
			<br />
			
			<fieldset><legend><fmt:message key="form.stc.praticacollegata.procedimenti" /></legend>
			<div class="jmesa" id="procedimenti">
			
				<table border="0" width="100%" cellpadding="2" cellspacing="0">
					<thead>
						<tr class="header">
							<td><fmt:message key="form.stc.praticacollegata.procedimenti.descrizione"/></td>
							<td><fmt:message key="form.stc.praticacollegata.procedimenti.documenti"/></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<%j=1; %>
						<c:forEach items="${notificarequest.datiAttivita.procedimenti}" var="procedimento_var">
							<tr>
								<td class="<%=(j%2)==0?"odd":"even"%>" valign="top" style="text-transform: uppercase; font-weight: bold">${procedimento_var.descrizione}									
										
										<%
											ProcedimentoType ptype = (ProcedimentoType)pageContext.getAttribute("procedimento_var");
											if(ptype!=null){
											    if(ptype.isPrincipale()!=null){
													if(ptype.isPrincipale().booleanValue()){
													    %>
													    <b>[<fmt:message key="label.procedimento_principale"/>]
													    <%  
													}
											    }
											}
									
										%>
										</b>										

								</td>
								<td class="<%=(j%2)==0?"odd":"even"%>" valign="top" >
									<c:if test="${not empty procedimento_var.documenti}">
											<ol>
											<c:forEach items="${procedimento_var.documenti}" var="documentoendo_var">
												<li>
													${documentoendo_var.documento}
												</li>
											</c:forEach>
											</ol>							
									</c:if>
								</td>					
							</tr>
							<%j++; %>
						</c:forEach>
					</tbody>				
				</table>

			</div>
			</fieldset>
		</c:if>
		
		
		<c:if test="${not empty notificarequest.datiAttivita.documenti}">		
			<br />
			<fieldset><legend><fmt:message key="form.stc.praticacollegata.documenti" /></legend>
			<i><fmt:message key="label.dettaglio_notifica.documenti_warning" /></i>			
			<div class="jmesa" id="documenti">
			
					<table border="0" width="100%" cellpadding="2" cellspacing="0">
						<thead>
							<tr class="header">
								<td colspan="3"><fmt:message key="form.stc.praticacollegata.documenti.documento"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
						<%j=1; %>
							<c:forEach items="${notificarequest.datiAttivita.documenti}" var="documenti_var">
								<tr class="<%=(j%2)==0?"odd":"even"%>">
									<td>${documenti_var.documento}</td>
									<td>${documenti_var.allegati.allegato}</td>
									<td>	
										<a class="visualizzaDocColumn" target="_blank" 
											href="${pageContext.request.contextPath}/file/ajaxDownload.htm?fileId=${documenti_var.allegati.id}"											 
											title="<fmt:message key="form.oggetti.view" />">
										   	<label><fmt:message key="label.visualizza.image" /></label>
										</a>&nbsp;										
									</td>				
								</tr>
								<%j++; %>
							</c:forEach>
						</tbody>
					</table>
				
			</div>
			</fieldset>
		</c:if>	
	
	
		<c:if test="${not empty notificarequest.datiAttivita.altriDati}">
			<br />
			<fieldset><legend><fmt:message key="form.stc.notifica.schede" /></legend>
			<div class="jmesa" id="modelli">
			
				<table border="0" width="100%" cellpadding="2" cellspacing="0">
					<thead>
						<tr class="header">
							<td><fmt:message key="form.stc.notifica.modello"/></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<%j=1; %>
						<c:forEach items="${notificarequest.datiAttivita.altriDati}" var="ad_var">
							<c:if test="${ad_var.nome eq 'DYN2_MODELLIT.ID'}">
								<tr class="<%=(j%2)==0?"odd":"even"%>">
									<td>
										<c:forEach items="${ad_var.valore}" var="valore_var">
											<b>${valore_var.descrizione}</b> (${valore_var.codice})
										</c:forEach>						
									</td>
								</tr>	
								<%j++; %>
							</c:if>
						</c:forEach>
					</tbody>				
				</table>

			</div>
			</fieldset>
		</c:if>
	
	
	<div id="functions">
		<ul>
	       	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				 
		</ul>
	</div>
</div>
</body>
</html>