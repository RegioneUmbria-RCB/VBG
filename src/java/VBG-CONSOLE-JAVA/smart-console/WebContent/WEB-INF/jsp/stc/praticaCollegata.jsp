<%@page import="it.init.sigepro.rte.types.DettaglioAttivitaType"%>
<%@page import="it.init.sigepro.rte.types.EstremiAttoEstesoType"%>
<%@page import="it.gruppoinit.pal.gp.core.dao.helper.ORMHelper"%>
<%@ include file="../includes/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%>
<%@ page import="it.init.sigepro.rte.types.DettaglioPraticaType"%>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="java.net.URLEncoder"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><fmt:message key="form.stc.praticacollegata.title" /></title>
</head>
<body>
<span class="titoloPagina"><fmt:message key="form.stc.praticacollegata.title" /></span>
<jsp:include page="../includes/innerNavigation.jsp">
	<jsp:param name="navmode" value="form" />
</jsp:include>
<div id="subcontent">
	<c:if test="${not empty errori}">
		<div id="status_msg" class="error_header">
			<fmt:message key="03"/>
			<c:forEach items="${errori}" var="errore" varStatus="a">
				<div style="margin-top: 5px;">
					${errore.descrizione}
					<c:if test="${not empty errore.numeroErrore}"> (cod. ${errore.numeroErrore})</c:if>
				</div>
			</c:forEach>
		</div>
	</c:if>
	<c:if test="${empty errori}">
	<br />
	<div class="parametriDiv">
		<div class="etichetta">
			<fmt:message key="form.stc.praticacollegata.sportello" />:
		</div>	
		<div class="parametro">
	   		 <div>
	           	${amministrazione.amministrazione} 
	      	</div>
	    </div>
	</div>
	<div class="clear"/>
		<fieldset><legend><fmt:message key="form.stc.praticacollegata.pratica" /></legend>
		<table>
			<tr>
				<td><fmt:message key="form.stc.praticacollegata.pratica.numeropratica" />:</td>
				<td colspan="3">
				<c:choose>
					<c:when test="${nla_locale eq true}">
							<%
                            String codiceMovimento = request.getParameter("codiceMovimento");
                            if(codiceMovimento == null){
                            	codiceMovimento = "";
                            }
							String urlBack = "../stc/praticaCollegata.htm?software="+ORMHelper.getSoftware()+"&codiceIstanza=" + request.getParameter("codiceIstanza") + "&codiceMovimento=" + codiceMovimento;
							urlBack = URLEncoder.encode(urlBack,"UTF-8");
							urlBack = URLEncoder.encode(urlBack,"UTF-8");
							pageContext.setAttribute("URL_BACK", urlBack);
							%>	
							<a href ="javascript:historySet('${URL_BACK}','../istanze/view.htm?codice=${pratica.idPratica}&software=${nla_dest_software}');" >${pratica.numeroPratica}</a >
					</c:when>
					<c:otherwise>
						<b>${pratica.numeroPratica}</b>	    
					</c:otherwise>
				</c:choose>				
				</td>
			</tr>
			<tr>
				<td><fmt:message key="form.stc.praticacollegata.pratica.numeroProtocolloGenerale" />:</td>
				<td><b>${pratica.numeroProtocolloGenerale} </b></td>
				<td><fmt:message key="form.stc.praticacollegata.pratica.dataProtocolloGenerale" />:</td>
				<td><b>
					<%
					DettaglioPraticaType dettaglioPratica = (DettaglioPraticaType)request.getAttribute("pratica");
					XMLGregorianCalendar xmlCal = dettaglioPratica.getDataProtocolloGenerale();
					if(null!=xmlCal){
						Calendar cal = xmlCal.toGregorianCalendar();
						SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);				
						out.print(sdf.format(cal.getTime()));
					}
					%>				
					</b>
				</td>
			</tr>
			<tr>
				<td><fmt:message key="form.stc.praticacollegata.pratica.richiedente" />:</td>
				<td colspan="3">
					<b>
					${pratica.richiedente.anagrafica.nome}&nbsp;
					${pratica.richiedente.anagrafica.cognome}
					</b>
					<c:if test="${not empty pratica.richiedente.anagrafica.codiceFiscale}">
						(${pratica.richiedente.anagrafica.codiceFiscale})
					</c:if>
					<c:if test="${not empty pratica.richiedente.ruolo}">
						<c:if test="${not empty pratica.richiedente.ruolo.ruolo}">	
							<br />${pratica.richiedente.ruolo.ruolo}<br />
						</c:if>	
					</c:if>
					<c:if test="${not empty pratica.aziendaRichiedente.ragioneSociale}">
					<b>
						${pratica.aziendaRichiedente.ragioneSociale}
					</b>	
						<c:if test="${not empty pratica.aziendaRichiedente.partitaIva}">
							(P.Iva: ${pratica.aziendaRichiedente.partitaIva})
						</c:if>
						<c:if test="${not empty pratica.aziendaRichiedente.codiceFiscale}">
							(CF: ${pratica.aziendaRichiedente.codiceFiscale})
						</c:if>
					</c:if>
					
					
				</td>
			</tr>
			<tr>
				<td><fmt:message key="form.stc.praticacollegata.pratica.intermediario" />:</td>
				<td colspan="3">
					<c:if test="${not empty pratica.intermediario.personaFisica.nome}">
					<b>
					${pratica.intermediario.personaFisica.nome}&nbsp;
					${pratica.intermediario.personaFisica.cognome}					
					</b>
					<c:if test="${not empty pratica.intermediario.personaFisica.codiceFiscale}">
						(${pratica.intermediario.personaFisica.codiceFiscale})
					</c:if>
					</c:if>
					<c:if test="${not empty pratica.intermediario.personaGiuridica.ragioneSociale}">
						<b>${pratica.intermediario.personaGiuridica.ragioneSociale}</b>
						<c:if test="${not empty pratica.intermediario.personaGiuridica.partitaIva}">
							(P.Iva: ${pratica.intermediario.personaGiuridica.partitaIva})
						</c:if>
						<c:if test="${not empty pratica.intermediario.personaGiuridica.codiceFiscale}">
							(CF: ${pratica.intermediario.personaGiuridica.codiceFiscale})
						</c:if>
					</c:if>
					
				</td>
			</tr>			
			<tr>
				<td><fmt:message key="form.stc.praticacollegata.pratica.intervento" />:</td>
				<td colspan="3"><b>${pratica.intervento.descrizione}</b></td>
			</tr>
			<tr>
				<td><fmt:message key="form.stc.praticacollegata.pratica.oggetto" />:</td>
				<td colspan="3"><b>${pratica.oggetto}</b></td>
			</tr>
			<c:if test="${not empty visura.responsabileProcedimento}">
				<tr>
					<td><fmt:message key="label.responsabile_procedimento" />:</td>
					<td colspan="3"><b>${visura.responsabileProcedimento}</b></td>
				</tr>
			</c:if>
			<c:if test="${not empty visura.istruttorePratica}">
				<tr>
					<td><fmt:message key="label.responsabile_istruttoria" />:</td>
					<td colspan="3"><b>${visura.istruttorePratica}</b></td>
				</tr>			
			</c:if>
			<c:if test="${not empty visura.statoIter}">
				<tr>
					<td><fmt:message key="label.responsabile_istruttoria" />:</td>
					<td colspan="3"><b>${visura.istruttorePratica}</b></td>
				</tr>			
			</c:if>
			<c:if test="${not empty visura.statoPratica}">
				<tr>
					<td><fmt:message key="label.stato_attuale_dell_istanza" />:</td>
					<td colspan="3"><b>${visura.statoPratica}</b></td>
				</tr>
			</c:if>
		</table>
		</fieldset>
	
		<%int j=1; %>
		<c:if test="${not empty pratica.altriSoggetti}">
			<br />
			<fieldset><legend><fmt:message key="form.stc.praticacollegata.altrisoggetti" /></legend>
			<div class="jmesa" id="altrisoggetti">
			
				<table border="0" width="100%" cellpadding="2" cellspacing="0">
					<thead>
						<tr class="header">						
							<td><fmt:message key="form.stc.praticacollegata.altrisoggetti.soggetto"/></td>
						</tr>
					</thead>
					<tbody class="tbody">
					<%j=1; %>
						<c:forEach items="${pratica.altriSoggetti}" var="altrisoggetti_var">
							<tr>							
								<td class="<%=(j%2)==0?"odd":"even"%>">
								
									<c:if test="${not empty altrisoggetti_var.soggetto.personaFisica}">
										<b>
											${altrisoggetti_var.soggetto.personaFisica.nome}&nbsp;
											${altrisoggetti_var.soggetto.personaFisica.cognome}
										</b>
										<c:if test="${not empty altrisoggetti_var.soggetto.personaFisica.codiceFiscale}">
											(${altrisoggetti_var.soggetto.personaFisica.codiceFiscale})
										</c:if>
									</c:if>
				
									<c:if test="${not empty altrisoggetti_var.soggetto.personaGiuridica}">
									<b>
										${altrisoggetti_var.soggetto.personaGiuridica.ragioneSociale}
									</b>	
										<c:if test="${not empty altrisoggetti_var.soggetto.personaGiuridica.partitaIva}">
											(P.Iva: ${altrisoggetti_var.soggetto.personaGiuridica.partitaIva})
										</c:if>
										<c:if test="${not empty altrisoggetti_var.soggetto.personaGiuridica.codiceFiscale}">
											(CF: ${altrisoggetti_var.soggetto.personaGiuridica.codiceFiscale})
										</c:if>
									</c:if>
									<c:if test="${not empty altrisoggetti_var.tipoRapporto.ruolo}">
											<br />${altrisoggetti_var.tipoRapporto.ruolo}<br />
									</c:if>								
				
									<c:if test="${not empty altrisoggetti_var.anagraficaCollegata.personaFisica}">
										<b>
											${altrisoggetti_var.anagraficaCollegata.personaFisica.nome}&nbsp;
											${altrisoggetti_var.anagraficaCollegata.personaFisica.cognome}
										</b>
										<c:if test="${not empty altrisoggetti_var.anagraficaCollegata.personaFisica.codiceFiscale}">
											(${altrisoggetti_var.anagraficaCollegata.personaFisica.codiceFiscale})
										</c:if>
									</c:if>
				
									<c:if test="${not empty altrisoggetti_var.anagraficaCollegata.personaGiuridica}">
									<b>
										${altrisoggetti_var.anagraficaCollegata.personaGiuridica.ragioneSociale}
									</b>	
										<c:if test="${not empty altrisoggetti_var.anagraficaCollegata.personaGiuridica.partitaIva}">
											(P.Iva: ${altrisoggetti_var.anagraficaCollegata.personaGiuridica.partitaIva})
										</c:if>
										<c:if test="${not empty altrisoggetti_var.anagraficaCollegata.personaGiuridica.codiceFiscale}">
											(CF: ${altrisoggetti_var.anagraficaCollegata.personaGiuridica.codiceFiscale})
										</c:if>
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
		
		<br />
		<fieldset><legend><fmt:message key="form.stc.praticacollegata.localizzazione" /></legend>
		<div class="jmesa" id="localizzazione">
			<table border="0" width="100%" cellpadding="2" cellspacing="0">
				<thead>
					<tr class="header">
						<td><fmt:message key="form.stc.praticacollegata.localizzazione.denominazione"/></td>
					</tr>
				</thead>
				<tbody class="tbody">
				<%j=1; %>
					<c:forEach items="${pratica.localizzazione}" var="localizzazione_var">
						<tr>
							<td class="<%=(j%2)==0?"odd":"even"%>">${localizzazione_var.denominazione}&nbsp;${localizzazione_var.civico}</td>						
						</tr>
						<%j++; %>
					</c:forEach>
				</tbody>
			</table>
		</div>
		</fieldset>
		
		<c:if test="${not empty pratica.procedimenti}">
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
						<c:forEach items="${pratica.procedimenti}" var="procedimento_var">
							<tr>
								<td class="<%=(j%2)==0?"odd":"even"%>" valign="top" style="text-transform: uppercase; font-weight: bold">${procedimento_var.descrizione}</td>
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
		
		
		<c:if test="${not empty pratica.documenti}">		
			<br />
			<fieldset><legend><fmt:message key="form.stc.praticacollegata.documenti" /></legend>
			<div class="jmesa" id="documenti">
			
					<table border="0" width="100%" cellpadding="2" cellspacing="0">
						<thead>
							<tr class="header">
								<td><fmt:message key="form.stc.praticacollegata.documenti.documento"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
						<%j=1; %>
							<c:forEach items="${pratica.documenti}" var="documenti_var">
								<tr>
									<td class="<%=(j%2)==0?"odd":"even"%>">${documenti_var.documento}</td>				
								</tr>
								<%j++; %>
							</c:forEach>
						</tbody>
					</table>
				
			</div>
			</fieldset>
		</c:if>
		
		<c:if test="${not empty visura.listaAtti}">		
			<br />
			<fieldset><legend><fmt:message key="form.stc.praticacollegata.listaAtti" /></legend>
			<div class="jmesa" id="listaAtti">
			
					<table border="0" width="100%" cellpadding="2" cellspacing="0">
						<thead>
							<tr class="header">
								<td><fmt:message key="form.stc.praticacollegata.listaAtti.atto"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
						<%j=1; %>
							<c:forEach items="${visura.listaAtti}" var="atto_var">
								<tr>
									<td class="<%=(j%2)==0?"odd":"even"%>">
										<fmt:message key="label.numero"/>: <b>${atto_var.numero}</b>
										<div style="padding-left: 10px;">
										<fmt:message key="alberoproc.label.tipologiaregistro"/>: <b>${atto_var.tipoRegistro}</b>
										<br /> 
										<c:if test="${not empty atto_var.data}">
										
								<%
								EstremiAttoEstesoType atto = (EstremiAttoEstesoType)pageContext.getAttribute("atto_var");
								XMLGregorianCalendar xmlCalAtto = atto.getData();
								if(null!=xmlCalAtto){
								    %>
								    <fmt:message key="label.data"/>: <b>
								    <%
									Calendar cal = xmlCalAtto.toGregorianCalendar();
									SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);				
									out.print(sdf.format(cal.getTime()));
								}
								%></b><br />
								</c:if>
								<c:if test="${not empty atto_var.note}">
										
										<fmt:message key="label.note"/>: <b>${atto_var.note}</b>
										<br/>
								</c:if>					
										</div>
										</td>				
								</tr>
								<%j++; %>
							</c:forEach>
						</tbody>
					</table>
				
			</div>
			</fieldset>
		</c:if>
		
			
			<c:if test="${not empty visura.listaAttivita}">		
			<br />
			<fieldset><legend><fmt:message key="form.stc.praticacollegata.listaAttivita" /></legend>
			<div class="jmesa" id="listaAttivita">
			
					<table border="0" width="100%" cellpadding="2" cellspacing="0">
						<thead>
							<tr class="header">
								<td><fmt:message key="form.stc.praticacollegata.listaAttivita.attivita"/></td>
							</tr>
						</thead>
						<tbody class="tbody">
						<%j=1; %>
							<c:forEach items="${visura.listaAttivita}" var="att_var">
								<tr>
									<td class="<%=(j%2)==0?"odd":"even"%>">
										<b>${att_var.tipoAttivita.descrizione} (${att_var.tipoAttivita.codice})</b>
										<div style="padding-left: 20px;">
											<c:if test="${not empty att_var.parere}">			
												<fmt:message key="label.parere"/>: <b>${att_var.parere}</b>
												<br />
											</c:if>
											<c:if test="${not empty att_var.note}">
																				
												<fmt:message key="label.note"/>: <b>${att_var.note}</b>
												<br />		
											</c:if>
											
											<c:if test="${not empty att_var.dataAttivita}">
												
											<%
											DettaglioAttivitaType attivita = (DettaglioAttivitaType)pageContext.getAttribute("att_var");
											XMLGregorianCalendar xmlCalAttivita = attivita.getDataAttivita();
											if(null!=xmlCalAttivita){
											    %>
											    <fmt:message key="label.data"/>:<b>
											    <%
												Calendar cal = xmlCalAttivita.toGregorianCalendar();
												SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);				
												out.print(sdf.format(cal.getTime()));
											}
											%></b><br />
											</c:if>
											<c:if test="${not empty att_var.numeroProtocolloGenerale}">
												
												<fmt:message key="label.numero_protocollo"/>: <b>${att_var.numeroProtocolloGenerale}</b>
												<br />
												<c:if test="${not empty att_var.dataAttivita}">
													
													<%
													DettaglioAttivitaType attivita2 = (DettaglioAttivitaType)pageContext.getAttribute("att_var");
													XMLGregorianCalendar xmlCalProt = attivita2.getDataProtocolloGenerale();
													if(null!=xmlCalProt){
													    %>
													    <fmt:message key="label.data_protocollo"/>:<b>
													    <%
														Calendar cal = xmlCalProt.toGregorianCalendar();
														SimpleDateFormat sdf = new SimpleDateFormat(WebConstants.DATE_FORMAT_PATTERN);				
														out.print(sdf.format(cal.getTime()));
													}
													%></b><br />
													</c:if>
												
											</c:if>
											<c:if test="${not empty att_var.documenti}">
												<div style="padding-left: 10px;">
													<fieldset><legend><fmt:message key="label.documenti"/></legend>
													
													<c:forEach items="${att_var.documenti}" var="documento_var">
														-&nbsp;<b>${documento_var.documento}</b> 
														<%-- <c:if test="${not empty documento_var.allegati.allegato}">(${ documento_var.allegati.allegato})</c:if>
														--%>
														<br />
													</c:forEach>
													</fieldset>
													
												</div>
											
											</c:if>
											
										</div>
										</td>				
								</tr>
								<%j++; %>
							</c:forEach>
						</tbody>
					</table>
				
			</div>
			</fieldset>
		</c:if>
			
			
			
	</c:if>		
	<div id="functions">
		<ul>
	       	<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>				 
		</ul>
	</div>
</div>
</body>
</html>