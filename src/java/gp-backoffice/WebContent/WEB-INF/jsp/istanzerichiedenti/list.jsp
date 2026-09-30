<%@page import="it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants"%>
<%@ include file="../includes/taglibs.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@page import="it.gruppoinit.pal.gp.core.constants.WebConstants"%><html>
	<head>
		<META HTTP-EQUIV="content-type" CONTENT="text/html; charset=UTF-8">
		<title><fmt:message key="label.soggetti_collegati_all_istanza" /></title>
	</head>
	<body>
		<span class="titoloPagina"><fmt:message key="label.soggetti_collegati_all_istanza" /></span>
		<jsp:include page="../includes/innerNavigation.jsp">
			<jsp:param name="navmode" value="list" />
		</jsp:include>
		<jsp:include page="../includes/history.jsp">
	    	<jsp:param name="path" value="../istanzerichiedenti/list" />
		</jsp:include>				
		<c:import url="/ajax/dettaglioIstanza.htm">
			<c:param name="codIstanza">${istanza.id.codice}</c:param>
		</c:import>
		<br class="clear" />	
		
		 <%
		 pageContext.setAttribute("URL_STAMPA_ANAGRAFE_DOCUMENTI", BackofficeNETConstants.getURL_STAMPA_DOCUMENTI_TIPO_PER_ANAGRAFE());
		 %>
		<div id="subcontent">
			<fieldset>
			<legend>${istanza.transientRichiedenteQualitaAzienda}</legend>
			<table width="100%">			 
			<c:if test="${not empty richiedente.anagrafedocumentis}">			
				<tr>
					<td>						
						<div class="jmesa">
							<b><fmt:message key="label.richiedente" /></b>:
							${istanza.richiedente.nominativo} ${istanza.richiedente.nome}
							<table class="table" width="30%" >
								<thead>
									<tr class="header">
										<td><fmt:message key="label.codice" /></td>
										<td><fmt:message key="label.riferimento_documento" /></td>
										<td><fmt:message key="label.tipo_documento" /></td>
										<td><fmt:message key="label.numero_istanza" /></td>
										<td><fmt:message key="label.inizio_validita" /></td>
										<td><fmt:message key="label.fine_validita" /></td>
										<td><fmt:message key="label.oggetto" /></td>
										<td><fmt:message key="label.note" /></td>
										<td>&nbsp;</td>
									</tr>
								</thead>
								<tbody class="tbody">	
									<c:forEach items="${richiedente.anagrafedocumentis}" var="current" varStatus="a">
										<c:choose>
											<c:when test="${(a.index mod 2) eq 0 }">
												<c:set var="className">odd</c:set>
											</c:when>
											<c:otherwise><c:set var="className">even</c:set></c:otherwise>
										</c:choose>
										<tr class="${className}">
											<td><a href="javascript:viewDocumento(${current.id.codice});">${current.id.codice}</a></td>
											<td>${current.rifdocumento}</td>
											<td>${current.tipidocumento.documento}</td>
											<td>${current.istanza.numeroistanza}</td>
											<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datainiziovalidita}"/></td>
											<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datafinevalidita}"/></td>
											<td>
												<c:if test="${not empty current.oggetto.id.codice}">					            	 
									            	 <jsp:include page="../includes/visualizzaOggetto.jsp" >
	       												<jsp:param name="idElemento" value="Rich${current.id.codice}" />
	       												<jsp:param name="fileId" value="${current.oggetto.id.codice}" />
	   												 </jsp:include>
									            </c:if>											
											</td>
											<td>${current.annotazioni}</td>
											<td width="5%">
												
													<c:choose>
														<c:when test="${current.flagXmlvisuraparix eq true}">
															<a class="visualizzaDocPDFColumn" href="../istanzerichiedenti/ajaxStampaPdfParix.htm?codiceIstanza=${istanza.id.codice}&codice=${current.oggetto.id.codice}" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
													            <label><fmt:message key="label.visualizza.image"/></label></a>
														</c:when>
														<c:otherwise>
															<c:if test="${not empty current.tipidocumento.letteretipo.id.codice}">
																<c:set var="_URL_STAMPA_ANAGRAFE_DOCUMENTI" value="${URL_STAMPA_ANAGRAFE_DOCUMENTI}?CodiceIstanza%3D${istanza.id.codice}%26CodiceLettera%3D${current.tipidocumento.letteretipo.id.codice}%26CodiceAnagrafe%3D${current.anagrafe.id.codice}"/>    
													            <a class="stampaColumn" href="javascript:void 0" onclick="window.open('${inite:geturlto(pageContext.request, _URL_STAMPA_ANAGRAFE_DOCUMENTI,_urlback ,null, true)}',69,'');" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
													            <label><fmt:message key="button.stampa"/></label></a>
													        </c:if>
												        </c:otherwise>
											        </c:choose>
											       
									         </td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>						
					</td>
				</tr>				
			</c:if>			
			<tr>		
				<c:if test="${empty richiedente.anagrafedocumentis}">	
					<td>
						<b><fmt:message key="label.richiedente" /></b>:				
						${istanza.richiedente.nominativo} ${istanza.richiedente.nome}
					</td>					
				</c:if>	
			</tr>	
			<tr>	 
				<td align="right">
					<c:if test="${istanza.richiedente.tipoanagrafe eq 'G'}">
						<div id="functions">
							<ul>
								<c:if test="${isWSDURC eq true }">
								<li>
									<jsp:include page="../anagrafe/funzioniDURC.jsp">
									   <jsp:param name="uniquePageIdentifier" value="${istanza.richiedente.id.codice}" />
									   <jsp:param name="codiceAnagrafe" value="${istanza.richiedente.id.codice}" />
									   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
									   <jsp:param name="showAsButton" value="true" />
									   <jsp:param name="function" value="verifica"/>
									   <jsp:param name="returnToUrl" value="${_urlback}"/>
									</jsp:include>		
								</li>
								<li>
									<jsp:include page="../anagrafe/funzioniDURC.jsp">
									   <jsp:param name="uniquePageIdentifier" value="${istanza.richiedente.id.codice}" />
									   <jsp:param name="codiceAnagrafe" value="${istanza.richiedente.id.codice}" />
									   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
									   <jsp:param name="showAsButton" value="true" />
									   <jsp:param name="function" value="nuovoDURC"/>
									   <jsp:param name="returnToUrl" value="${_urlback}"/>
									</jsp:include>		
								</li>
								</c:if>
								<c:if test="${isParixGate eq true }">								
									<li><a href="javascript:historySet('${_urlback}', '../istanzerichiedenti/insertVisuraParix.htm?codiceAnagrafe=${istanza.richiedente.id.codice}&codiceIstanza=${istanza.id.codice}', '');"><fmt:message key="button.visura_infocamere" /></a></li>								
								</c:if>
							</ul>							
						</div>	
					</c:if>				
					<a class="vbg-btn btn-aggiungi" href="javascript:nuovoDocumento(${richiedente.id.codice});" title="<fmt:message key="label.nuovo_documento_anagrafe" />">
					</a>
				</td>
			</tr>			
			<!-- Titolarelegale  -->			
			<c:if test="${istanza.titolarelegale!=null}">	
				<tr>
					<td>
						<b>Azienda richiedente</b>:
						${istanza.titolarelegale.nominativo} ${istanza.titolarelegale.nome}
					</td>
				</tr>			
				<c:if test="${not empty istanza.titolarelegale.anagrafedocumentis}">
				<tr>
					<td>						
						<div class="jmesa">
							<table class="table" width="30%">
								<thead>
									<tr class="header">
										<td><fmt:message key="label.codice" /></td>
										<td><fmt:message key="label.riferimento_documento" /></td>
										<td><fmt:message key="label.tipo_documento" /></td>
										<td><fmt:message key="label.numero_istanza" /></td>
										<td><fmt:message key="label.inizio_validita" /></td>
										<td><fmt:message key="label.fine_validita" /></td>
										<td><fmt:message key="label.oggetto" /></td>
										<td><fmt:message key="label.note" /></td>
										<td>&nbsp;</td>
									</tr>
								</thead>
								<tbody class="tbody">	
									<c:forEach items="${istanza.titolarelegale.anagrafedocumentis}" var="current" varStatus="a">
										<c:choose>
											<c:when test="${(a.index mod 2) eq 0 }">
												<c:set var="className">odd</c:set>
											</c:when>
											<c:otherwise><c:set var="className">even</c:set></c:otherwise>
										</c:choose>
										<tr class="${className}">
											<td><a href="javascript:viewDocumento(${current.id.codice});">${current.id.codice}</a></td>
											<td>${current.rifdocumento}</td>
											<td>${current.tipidocumento.documento}</td>
											<td>${current.istanza.numeroistanza}</td>
											<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datainiziovalidita}"/></td>
											<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datafinevalidita}"/></td>
											<td>
												<c:if test="${not empty current.oggetto.id.codice}">					            	 
									            	 <jsp:include page="../includes/visualizzaOggetto.jsp" >
	       												<jsp:param name="idElemento" value="Rich${current.id.codice}" />
	       												<jsp:param name="fileId" value="${current.oggetto.id.codice}" />
	   												 </jsp:include>
									            </c:if>											
											</td>
											<td>${current.annotazioni}</td>
											<td width="5%">
												
													<c:choose>
														<c:when test="${current.flagXmlvisuraparix eq true}">
															<a class="visualizzaDocPDFColumn" href="../istanzerichiedenti/ajaxStampaPdfParix.htm?codiceIstanza=${istanza.id.codice}&codice=${current.oggetto.id.codice}" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
													            <label><fmt:message key="label.visualizza.image"/></label></a>
														</c:when>
														<c:otherwise>
															<c:if test="${not empty current.tipidocumento.letteretipo.id.codice}">
																<c:set var="_URL_STAMPA_ANAGRAFE_DOCUMENTI" value="${URL_STAMPA_ANAGRAFE_DOCUMENTI}?CodiceIstanza%3D${istanza.id.codice}%26CodiceLettera%3D${current.tipidocumento.letteretipo.id.codice}%26CodiceAnagrafe%3D${current.anagrafe.id.codice}"/>    
													            <a class="stampaColumn" href="javascript:void 0" onclick="window.open('${inite:geturlto(pageContext.request, _URL_STAMPA_ANAGRAFE_DOCUMENTI,_urlback ,null, true)}',69,'');" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
													            <label><fmt:message key="button.stampa"/></label></a>
												        	</c:if>
											        	</c:otherwise>
											        </c:choose>	
											            
									           </td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
						
					</td>
				</tr>
				</c:if>
				<tr>
					<td align="right">
					   <div id="functions">
							<ul>
								<c:if test="${isWSDURC eq true }">
								<li>
									<jsp:include page="../anagrafe/funzioniDURC.jsp">
									   <jsp:param name="uniquePageIdentifier" value="${istanza.titolarelegale.id.codice}" />
									   <jsp:param name="codiceAnagrafe" value="${istanza.titolarelegale.id.codice}" />
									   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
									   <jsp:param name="showAsButton" value="true" />
									   <jsp:param name="function" value="verifica"/>
									   <jsp:param name="returnToUrl" value="${_urlback}"/>
									</jsp:include>		
								</li>
								<li>
									<jsp:include page="../anagrafe/funzioniDURC.jsp">
									   <jsp:param name="uniquePageIdentifier" value="${istanza.titolarelegale.id.codice}" />
									   <jsp:param name="codiceAnagrafe" value="${istanza.titolarelegale.id.codice}" />
									   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
									   <jsp:param name="showAsButton" value="true" />
									   <jsp:param name="function" value="nuovoDURC"/>
									   <jsp:param name="returnToUrl" value="${_urlback}"/>
									</jsp:include>		
								</li>
								</c:if>
								<c:if test="${isParixGate eq true }">
									<li><a href="javascript:historySet('${_urlback}', '../istanzerichiedenti/insertVisuraParix.htm?codiceAnagrafe=${istanza.titolarelegale.id.codice}&codiceIstanza=${istanza.id.codice}', '');"><fmt:message key="button.visura_infocamere" /></a></li>								
								</c:if>
							</ul>							
							</div>	
						<a class="vbg-btn btn-aggiungi"  href="javascript:nuovoDocumento(${istanza.titolarelegale.id.codice});" title="<fmt:message key="label.nuovo_documento_anagrafe" />">
						</a>					
					</td>
				</tr>		
			</c:if>
				
			<!-- End Titolarelegale  -->					
			</table>
			</fieldset>			
			<br class="break" />	
			<c:forEach items="${istanzerichiedentiList}" var="istRichiedente" varStatus="a">
			<fieldset>
			<legend>
				<c:choose>
				<c:when test="${istRichiedente.procuratore == null}">
					<tr>
						<td>
							<a href="javascript:dettaglioSoggetto(${istRichiedente.id.codice})">${istRichiedente.transientRichiedenteQualitaAzienda}</a>						
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td>${istRichiedente.procuratore.descrizioneRichiedente} 
							<fmt:message key="label.in_qualita_di" />&nbsp;<fmt:message key="label.procuratore" />
							: 
							<a href="javascript:dettaglioSoggetto(${istRichiedente.id.codice})">${istRichiedente.richiedente.nominativo} ${istRichiedente.richiedente.nome}</a>
							<c:if test="${not empty istRichiedente.oggettoProcuratore.id.codice}">
								<jsp:include page="../includes/visualizzaOggetto.jsp" >
		       						<jsp:param name="idElemento" value="proc${istRichiedente.id.codice}" />
		       						<jsp:param name="fileId" value="${istRichiedente.oggettoProcuratore.id.codice}" />
		   						</jsp:include>
							</c:if>		
						</td>
					</tr>
				</c:otherwise>
				</c:choose>
			</legend>
			<table style="width: 100%">					
				<!-- Start Istanzerichiedenti procuratore -->	
				<c:if test="${istRichiedente.procuratore!=null}">
					<tr>
						<td>
							${istRichiedente.procuratore.nominativo} ${istRichiedente.procuratore.nome}
						</td> 
					</tr>			
					<c:if test="${not empty istRichiedente.procuratore.anagrafedocumentis}">
						<tr>
							<td>						
								<div class="jmesa">
									<table class="table" width="30%">
										<thead>
											<tr class="header">
												<td><fmt:message key="label.codice" /></td>
												<td><fmt:message key="label.riferimento_documento" /></td>
												<td><fmt:message key="label.tipo_documento" /></td>
												<td><fmt:message key="label.numero_istanza" /></td>
												<td><fmt:message key="label.inizio_validita" /></td>
												<td><fmt:message key="label.fine_validita" /></td>
												<td><fmt:message key="label.oggetto" /></td>
												<td><fmt:message key="label.note" /></td>
												<td>&nbsp;</td>
											</tr>
										</thead>
										<tbody class="tbody">	
											<c:forEach items="${istRichiedente.procuratore.anagrafedocumentis}" var="current" varStatus="a">
												<c:choose>
													<c:when test="${(a.index mod 2) eq 0 }">
														<c:set var="className">odd</c:set>
													</c:when>
													<c:otherwise><c:set var="className">even</c:set></c:otherwise>
												</c:choose>
												<tr class="${className}">
													<td><a href="javascript:viewDocumento(${current.id.codice});">${current.id.codice}</a></td>
													<td>${current.rifdocumento}</td>
													<td>${current.tipidocumento.documento}</td>
													<td>${current.istanza.numeroistanza}</td>
													<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datainiziovalidita}"/></td>
													<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datafinevalidita}"/></td>
													<td>
														<c:if test="${not empty current.oggetto.id.codice}">
														 <jsp:include page="../includes/visualizzaOggetto.jsp" >
		       												<jsp:param name="idElemento" value="richA${current.id.codice}" />
		       												<jsp:param name="fileId" value="${current.oggetto.id.codice}" />	   													
		   												 </jsp:include>
											            </c:if>
													</td>
													<td>${current.annotazioni}</td>
													<td width="5%">											
														
														<c:choose>
															<c:when test="${current.flagXmlvisuraparix eq true}">
																<a class="visualizzaDocPDFColumn" href="../istanzerichiedenti/ajaxStampaPdfParix.htm?codiceIstanza=${istanza.id.codice}&codice=${current.oggetto.id.codice}" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
																	<label><fmt:message key="label.visualizza.image"/></label></a>
															</c:when>
															<c:otherwise>
																<c:if test="${not empty current.tipidocumento.letteretipo.id.codice}">
																	<c:set var="_URL_STAMPA_ANAGRAFE_DOCUMENTI" value="${URL_STAMPA_ANAGRAFE_DOCUMENTI}?CodiceIstanza%3D${istanza.id.codice}%26CodiceLettera%3D${current.tipidocumento.letteretipo.id.codice}%26CodiceAnagrafe%3D${current.anagrafe.id.codice}"/>    
														            <a class="stampaColumn" href="javascript:void 0" onclick="window.open('${inite:geturlto(pageContext.request, _URL_STAMPA_ANAGRAFE_DOCUMENTI,_urlback ,null, true)}',69,'');" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
														            <label><fmt:message key="button.stampa"/></label></a>
														        </c:if>
													        </c:otherwise>
													     </c:choose> 
											        													
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>							
							</td>
						</tr>											
					</c:if>	
						<tr>
							<td align="right">
							<c:if test="${istRichiedente.procuratore.tipoanagrafe eq 'G'}">
							    <div id="functions">
								<ul>
							        <c:if test="${isWSDURC eq true }">
									<li>
										<jsp:include page="../anagrafe/funzioniDURC.jsp">
										   <jsp:param name="uniquePageIdentifier" value="${istRichiedente.procuratore.id.codice}" />
										   <jsp:param name="codiceAnagrafe" value="${istRichiedente.procuratore.id.codice}" />
										   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
										   <jsp:param name="showAsButton" value="true" />
										   <jsp:param name="function" value="verifica"/>
										   <jsp:param name="returnToUrl" value="${_urlback}"/>
										</jsp:include>		
									</li>
									<li>
										<jsp:include page="../anagrafe/funzioniDURC.jsp">
										   <jsp:param name="uniquePageIdentifier" value="${istRichiedente.procuratore.id.codice}" />
										   <jsp:param name="codiceAnagrafe" value="${istRichiedente.procuratore.id.codice}" />
										   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
										   <jsp:param name="showAsButton" value="true" />
										   <jsp:param name="function" value="nuovoDURC"/>
										   <jsp:param name="returnToUrl" value="${_urlback}"/>
										</jsp:include>		
									</li>
									</c:if>
					            	<c:if test="${isParixGate eq true }">								
										<li><a href="javascript:historySet('${_urlback}', '../istanzerichiedenti/insertVisuraParix.htm?codiceAnagrafe=${istRichiedente.procuratore.id.codice}&codiceIstanza=${istanza.id.codice}', '');"><fmt:message key="button.visura_infocamere" /></a></li>								
									</c:if>
								</ul>							
								</div>	
								</c:if>
								<a class="vbg-btn btn-aggiungi" href="javascript:nuovoDocumento(${istRichiedente.procuratore.id.codice});" title="<fmt:message key="label.nuovo_documento_anagrafe" />">
								</a>
							</td>
						</tr>	
				</c:if>		
				<!-- End Istanzerichiedenti procuratore -->					
						
				<!-- Start Istanzerichiedenti richiedente -->
				<tr>
					<td>
						${istRichiedente.richiedente.nominativo} ${istRichiedente.richiedente.nome}
					</td> 
				</tr>
				<c:if test="${not empty istRichiedente.richiedente.anagrafedocumentis}">
					<tr>
						<td>						
							<div class="jmesa">
								
								<table class="table" width="30%">
									<thead>
										<tr class="header">
											<td><fmt:message key="label.codice" /></td>
											<td><fmt:message key="label.riferimento_documento" /></td>
											<td><fmt:message key="label.tipo_documento" /></td>
											<td><fmt:message key="label.numero_istanza" /></td>
											<td><fmt:message key="label.inizio_validita" /></td>
											<td><fmt:message key="label.fine_validita" /></td>
											<td><fmt:message key="label.oggetto" /></td>
											<td><fmt:message key="label.note" /></td>
											<td>&nbsp;</td>
										</tr>
									</thead>
									<tbody class="tbody">	
										<c:forEach items="${istRichiedente.richiedente.anagrafedocumentis}" var="current" varStatus="a">
											<c:choose>
												<c:when test="${(a.index mod 2) eq 0 }">
													<c:set var="className">odd</c:set>
												</c:when>
												<c:otherwise><c:set var="className">even</c:set></c:otherwise>
											</c:choose>
											<tr class="${className}">
												<td><a href="javascript:viewDocumento(${current.id.codice});">${current.id.codice}</a></td>
												<td>${current.rifdocumento}</td>
												<td>${current.tipidocumento.documento}</td>
												<td>${current.istanza.numeroistanza}</td>
												<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datainiziovalidita}"/></td>
												<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datafinevalidita}"/></td>
												<td>
													<c:if test="${not empty current.oggetto.id.codice}">
													 <jsp:include page="../includes/visualizzaOggetto.jsp" >
	       												<jsp:param name="idElemento" value="richA${current.id.codice}" />
	       												<jsp:param name="fileId" value="${current.oggetto.id.codice}" />	   													
	   												 </jsp:include>
										            </c:if>
												</td>
												<td>${current.annotazioni}</td>
												<td width="5%">											
													
												
													<c:choose>
														<c:when test="${current.flagXmlvisuraparix eq true}">
															<a class="visualizzaDocPDFColumn" href="../istanzerichiedenti/ajaxStampaPdfParix.htm?codiceIstanza=${istanza.id.codice}&codice=${current.oggetto.id.codice}" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
																<label><fmt:message key="label.visualizza.image"/></label></a>
														</c:when>
														<c:otherwise>
															<c:if test="${not empty current.tipidocumento.letteretipo.id.codice}">
																<c:set var="_URL_STAMPA_ANAGRAFE_DOCUMENTI" value="${URL_STAMPA_ANAGRAFE_DOCUMENTI}?CodiceIstanza%3D${istanza.id.codice}%26CodiceLettera%3D${current.tipidocumento.letteretipo.id.codice}%26CodiceAnagrafe%3D${current.anagrafe.id.codice}"/>    
													            <a class="stampaColumn" href="javascript:void 0" onclick="window.open('${inite:geturlto(pageContext.request, _URL_STAMPA_ANAGRAFE_DOCUMENTI,_urlback ,null, true)}',69,'');" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
													            <label><fmt:message key="button.stampa"/></label></a>
													        </c:if>
												        </c:otherwise>
												      </c:choose> 
										        													
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>							
						</td>
					</tr>
				</c:if>				
				<tr>
					<td align="right">
					    <c:if test="${istRichiedente.richiedente.tipoanagrafe eq 'G'}">
							<div id="functions">
							<ul>
								<c:if test="${isWSDURC eq true }">
								<li>
									<jsp:include page="../anagrafe/funzioniDURC.jsp">
									   <jsp:param name="uniquePageIdentifier" value="${istRichiedente.richiedente.id.codice}" />
									   <jsp:param name="codiceAnagrafe" value="${istRichiedente.richiedente.id.codice}" />
									   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
									   <jsp:param name="showAsButton" value="true" />
									   <jsp:param name="function" value="verifica"/>
									   <jsp:param name="returnToUrl" value="${_urlback}"/>
									</jsp:include>		
								</li>
								<li>
									<jsp:include page="../anagrafe/funzioniDURC.jsp">
									   <jsp:param name="uniquePageIdentifier" value="${istRichiedente.richiedente.id.codice}" />
									   <jsp:param name="codiceAnagrafe" value="${istRichiedente.richiedente.id.codice}" />
									   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
									   <jsp:param name="showAsButton" value="true" />
									   <jsp:param name="function" value="nuovoDURC"/>
									   <jsp:param name="returnToUrl" value="${_urlback}"/>
									</jsp:include>		
								</li>
								</c:if>
								<c:if test="${isParixGate eq true }">								
									<li><a href="javascript:historySet('${_urlback}', '../istanzerichiedenti/insertVisuraParix.htm?codiceAnagrafe=${istRichiedente.richiedente.id.codice}&codiceIstanza=${istanza.id.codice}', '');"><fmt:message key="button.visura_infocamere" /></a></li>								
								</c:if>
							</ul>
							</div>	
						</c:if>		
						<a class="vbg-btn btn-aggiungi" href="javascript:nuovoDocumento(${istRichiedente.richiedente.id.codice});" title="<fmt:message key="label.nuovo_documento_anagrafe" />">
						</a>
					</td>
				</tr>				
				<!-- End Istanzerichiedenti richiedente -->
								
				<!-- Start Istanzerichiedenti AnagrafeCollegata -->	
				<c:if test="${istRichiedente.anagrafeCollegata!=null}">
					<tr>
						<td>
							${istRichiedente.anagrafeCollegata.nominativo} ${istRichiedente.anagrafeCollegata.nome}
						</td> 
					</tr>			
					<c:if test="${not empty istRichiedente.anagrafeCollegata.anagrafedocumentis}">
						<tr>
							<td>						
								<div class="jmesa">
									<table class="table" width="30%">
										<thead>
											<tr class="header">
												<td><fmt:message key="label.codice" /></td>
												<td><fmt:message key="label.riferimento_documento" /></td>
												<td><fmt:message key="label.tipo_documento" /></td>
												<td><fmt:message key="label.numero_istanza" /></td>
												<td><fmt:message key="label.inizio_validita" /></td>
												<td><fmt:message key="label.fine_validita" /></td>
												<td><fmt:message key="label.oggetto" /></td>
												<td><fmt:message key="label.note" /></td>
												<td>&nbsp;</td>
											</tr>
										</thead>
										<tbody class="tbody">	
											<c:forEach items="${istRichiedente.anagrafeCollegata.anagrafedocumentis}" var="current" varStatus="a">
												<c:choose>
													<c:when test="${(a.index mod 2) eq 0 }">
														<c:set var="className">odd</c:set>
													</c:when>
													<c:otherwise><c:set var="className">even</c:set></c:otherwise>
												</c:choose>
												<tr class="${className}">
													<td><a href="javascript:viewDocumento(${current.id.codice});">${current.id.codice}</a></td>
													<td>${current.rifdocumento}</td>
													<td>${current.tipidocumento.documento}</td>
													<td>${current.istanza.numeroistanza}</td>
													<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datainiziovalidita}"/></td>
													<td><fmt:formatDate pattern="<%=WebConstants.DATE_FORMAT_PATTERN %>" value="${current.datafinevalidita}"/></td>
													<td>
														<c:if test="${not empty current.oggetto.id.codice}">
														 <jsp:include page="../includes/visualizzaOggetto.jsp" >
		       												<jsp:param name="idElemento" value="richA${current.id.codice}" />
		       												<jsp:param name="fileId" value="${current.oggetto.id.codice}" />	   													
		   												 </jsp:include>
											            </c:if>
													</td>
													<td>${current.annotazioni}</td>
													<td width="5%">											
														
														<c:choose>
															<c:when test="${current.flagXmlvisuraparix eq true}">
																<a class="visualizzaDocPDFColumn" href="../istanzerichiedenti/ajaxStampaPdfParix.htm?codiceIstanza=${istanza.id.codice}&codice=${current.oggetto.id.codice}" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
														            <label><fmt:message key="label.visualizza.image"/></label></a>
															</c:when>
															<c:otherwise>
															<c:if test="${not empty current.tipidocumento.letteretipo.id.codice}">
																<c:set var="_URL_STAMPA_ANAGRAFE_DOCUMENTI" value="${URL_STAMPA_ANAGRAFE_DOCUMENTI}?CodiceIstanza%3D${istanza.id.codice}%26CodiceLettera%3D${current.tipidocumento.letteretipo.id.codice}%26CodiceAnagrafe%3D${current.anagrafe.id.codice}"/>    
													            <a class="stampaColumn" href="javascript:void 0" onclick="window.open('${inite:geturlto(pageContext.request, _URL_STAMPA_ANAGRAFE_DOCUMENTI,_urlback ,null, true)}',69,'');" title="<fmt:message key="button.stampa" /> ${current.tipidocumento.documento}">
													            <label><fmt:message key="button.stampa"/></label></a>
													        </c:if>
													        </c:otherwise>
													    </c:choose>    
											         													
													</td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>							
							</td>
						</tr>											
					</c:if>	
						<tr>
							<td align="right">
							<div id="functions">
							<ul>
							<c:if test="${istRichiedente.richiedente.tipoanagrafe eq 'G'}">
							
								<c:if test="${isWSDURC eq true }">
								<li>
									<jsp:include page="../anagrafe/funzioniDURC.jsp">
									   <jsp:param name="uniquePageIdentifier" value="${istRichiedente.richiedente.id.codice}" />
									   <jsp:param name="codiceAnagrafe" value="${istRichiedente.richiedente.id.codice}" />
									   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
									   <jsp:param name="showAsButton" value="true" />
									   <jsp:param name="function" value="verifica"/>
									   <jsp:param name="returnToUrl" value="${_urlback}"/>
									</jsp:include>		
								</li>
								<li>
									<jsp:include page="../anagrafe/funzioniDURC.jsp">
									   <jsp:param name="uniquePageIdentifier" value="${istRichiedente.richiedente.id.codice}" />
									   <jsp:param name="codiceAnagrafe" value="${istRichiedente.richiedente.id.codice}" />
									   <jsp:param name="codiceIstanza" value="${istanza.id.codice}" />
									   <jsp:param name="showAsButton" value="true" />
									   <jsp:param name="function" value="nuovoDURC"/>
									   <jsp:param name="returnToUrl" value="${_urlback}"/>
									</jsp:include>		
								</li>
								</c:if>
							    </c:if>
								<c:if test="${isParixGate eq true }">
									<li><a href="javascript:historySet('${_urlback}', '../istanzerichiedenti/insertVisuraParix.htm?codiceAnagrafe=${istRichiedente.anagrafeCollegata.id.codice}&codiceIstanza=${istanza.id.codice}', '');"><fmt:message key="button.visura_infocamere" /></a></li>								
								</c:if>
								</ul>
								</div>	
								<a class="vbg-btn btn-aggiungi" href="javascript:nuovoDocumento(${istRichiedente.anagrafeCollegata.id.codice});" title="<fmt:message key="label.nuovo_documento_anagrafe" />">
								</a>
							</td>
						</tr>	
				</c:if>		
				<!-- End Istanzerichiedenti AnagrafeCollegata -->								
				</table>
				</fieldset>
				<br class="break" />
			</c:forEach>
			<!-- Pannello che compare al click sul tasto copia soggetti collegati -->
			<!-- --------------------------------------------START------------------------------------- -->
			<div dojoType="dijit.Dialog" id="istanze_collegateDiv" title="<fmt:message key="label.istanze_collegate" />: ">
					<div dojoType="dijit.layout.ContentPane" class="generic_dialog" style="width: 500px">
						<div id="istanze_collegate_tab"></div>
					</div>
			</div>
		</div>
		<script type="text/javascript">
			function viewDocumento(codiceDocumento){
				var urlTo="../anagrafe/viewDocumenti.htm?codice="+codiceDocumento;
				historySet('${_urlback}', urlTo, '');
			}
			function nuovoDocumento(codiceAnagrafe){
				var urlTo="../anagrafe/createDocumenti.htm?codiceanagrafe="+codiceAnagrafe+"&codiceIstanza=${istanza.id.codice}";
				historySet('${_urlback}', urlTo, '');
			}
			function dettaglioSoggetto(codiceSoggetto){
				var urlTo="../istanzerichiedenti/view.htm?codice="+codiceSoggetto;
				historySet('${_urlback}', urlTo, '');
			}
			
			function tabIstanzeCollegate(divId,idIstanza){
				
				dijit.byId(divId).show();
				IstanzeCollegateTab(idIstanza);
			}
			
			function IstanzeCollegateTab(idIstanza) {
					new Ajax.Request(
							'${pageContext.request.contextPath}/ajax/istanzeCollegate.htm?codice='+ idIstanza,
							{
								method : 'post',
								onSuccess : function(transport) {							
									var response = transport.responseText;					
									$("istanze_collegate_tab").innerHTML =response;
									applyStyle();
								},
								onFailure : function(transport) {
									var response = transport.responseText;
									alert(response);
								}
							});
			}
			
			
			function StampaDocumento(CodAnagrafe,CodLettera,CodIstanza) {
				
				var	w2=window.open("cl_StampaDocumentiTipoPerAnagrafe.asp?CodiceIstanza=" + CodIstanza + "&CodiceLettera=" + CodLettera + "&CodiceAnagrafe="+CodAnagrafe,w2,"width=550,height=420,top=20,left=20,menubar=yes,scrollbar=auto,resizable=yes");
			
			}			
			
		</script>
		<div id="functions">
			<ul>
				<li><a href="javascript:historySet('${_urlback}', '../istanzerichiedenti/create.htm?codiceIstanza=${istanza.id.codice}', '');"><fmt:message key="button.new" /></a></li>
				<li><a href="javascript:tabIstanzeCollegate('istanze_collegateDiv',${istanza.id.codice})";><fmt:message key="button.copia_soggetti_collegati" /></a></li>
				<li><a href="javascript:doHref('../history/back.htm?<%=WebConstants.GOTO%>=%2F','')"><fmt:message key="button.back" /></a></li>
			</ul>
		</div>
	</body>
</html>