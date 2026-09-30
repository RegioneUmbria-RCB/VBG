<%@ page import="it.gruppoinit.dss.wsclient.WsCertificateVerification" %>
<%@ page import="it.gruppoinit.dss.wsclient.WsTimestampVerificationResult" %>
<%@ page import="it.gruppoinit.dss.wsclient.WsSignatureVerification" %>
<%@ page import="it.gruppoinit.dss.wsclient.WsSignatureLevelBES" %>
<%@ page import="it.gruppoinit.dss.wsclient.WsValidationReport" %>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="javax.security.auth.x500.X500Principal" %>
<%@ page import="java.security.cert.X509Certificate" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.security.cert.CertificateFactory" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<script type="text/javascript">
function showSection(id){
	$("#"+id).show();
	$("#show_img_"+id).hide();
	$("#hide_img_"+id).show();
}
function hideSection(id){
	$("#"+id).hide();
	$("#show_img_"+id).show();
	$("#hide_img_"+id).hide();
}
</script>
<fmt:setTimeZone value="Europe/Rome" scope="request" />
<%String datePattern = "dd/MM/yyyy HH:mm:ss z"; %>
<c:set var="datePattern" scope="request" value="<%=datePattern %>"/>		
<%SimpleDateFormat sdf = new SimpleDateFormat(datePattern); %>
<%WsValidationReport wsreport = (WsValidationReport)request.getAttribute("wsreport"); %>
<table border="0" cellpadding="5">
	<tr><td class="label" nowrap="nowrap">Data verifica</td><td class="value"><%=sdf.format(wsreport.getTimeInformation().getVerificationTime().toGregorianCalendar().getTime()) %></td></tr>
	<tr><td class="label" nowrap="nowrap">Totale firme presenti</td><td class="value"><%=wsreport.getSignatureInformationList().size() %></td></tr>
	<c:forEach items="${wsreport.signatureInformationList }" var="signInfo" varStatus="signIdx">
	<tr>
		<td class="label" valign="top" nowrap="nowrap">Dettaglio firma n. ${signIdx.index +1 }
		<img id="show_img_dettaglio${signIdx.index }" onclick='showSection("dettaglio${signIdx.index }")' src="${pageContext.request.contextPath}/images/signature/more.gif" />
		<img id="hide_img_dettaglio${signIdx.index }" onclick='hideSection("dettaglio${signIdx.index }")' style="display: none;" src="${pageContext.request.contextPath}/images/signature/less.gif" />
		</td>
		<td>
			<table border="0" cellpadding="2" style="display: none;" id="dettaglio${signIdx.index }">
				<tr><td class="label" nowrap="nowrap">Risultato validazione</td>
					<td class="value">
					<jsp:include page="signatureValidationImages.jsp">
						<jsp:param name="result" value="${signInfo.signatureVerification.signatureVerificationResult }" />
					</jsp:include>
					</td>
				</tr>
				<tr><td class="label" nowrap="nowrap">Algoritmo di firma</td><td class="value">${signInfo.signatureVerification.signatureAlgorithm }</td></tr>
				<tr><td class="label" nowrap="nowrap">Algoritmo di hashing</td><td class="value">${signInfo.signatureVerification.digestAlgorithm }</td></tr>
				<tr>
					<td class="label" nowrap="nowrap">Data della firma</td>
					<td class="value">
						<c:set var="signInfoRefTime" value="${signInfo.signatureVerification.referenceTime}" />
						<%
						XMLGregorianCalendar t = (XMLGregorianCalendar)pageContext.getAttribute("signInfoRefTime");
						pageContext.setAttribute("signInfoRefDate", t.toGregorianCalendar().getTime());
						%>
						<fmt:formatDate value="${signInfoRefDate }" pattern="${datePattern }" />
					</td>
				</tr>
				<tr><td class="label">Formato</td><td class="value">${signInfo.signatureLevelAnalysis.signatureFormat }</td></tr>
				<tr><td class="label">Livello</td>
					<td>
					<table border="0" cellpadding="2">
					<c:if test="${signInfo.signatureLevelAnalysis.levelBES.levelReached eq 'VALID' }"><tr><td class="label">BES<td><td class="value">Basic Electronic Signature<td></tr></c:if>
					<c:if test="${signInfo.signatureLevelAnalysis.levelT.levelReached eq 'VALID' }"><tr><td class="label">T<td><td class="value">Electronic Signature with Time<td></tr></c:if>
					<c:if test="${signInfo.signatureLevelAnalysis.levelEPES.levelReached eq 'VALID' }"><tr class="label"><td>EPES<td><td class="value">Explicit Policy-based Electronic Signature<td></tr></c:if>
					<c:if test="${signInfo.signatureLevelAnalysis.levelC.levelReached eq 'VALID' }"><tr><td class="label">C<td><td class="value">ES with Complete Validation Data References<td></tr></c:if>
					<c:if test="${signInfo.signatureLevelAnalysis.levelX.levelReached eq 'VALID' }"><tr><td class="label">X<td><td class="value">EXtended Long Electronic Signature<td></tr></c:if>
					<c:if test="${signInfo.signatureLevelAnalysis.levelXL.levelReached eq 'VALID' }"><tr><td class="label">XL<td><td class="value">EXtended Long Electronic Signature with Time<td></tr></c:if>
					<c:if test="${signInfo.signatureLevelAnalysis.levelA.levelReached eq 'VALID' }"><tr><td class="label">A<td><td class="value">Archival Electronic Signature<td></tr></c:if>
					<c:if test="${signInfo.signatureLevelAnalysis.levelLTV.levelReached eq 'VALID' }"><tr><td class="label">LTV<td><td class="value">Long-Term Validation<td></tr></c:if>
					</table>
					</td>
				</tr>	
				<c:if test="${signInfo.signatureLevelAnalysis.levelBES.levelReached eq 'VALID' }">
				<c:set var="bes" value="${signInfo.signatureLevelAnalysis.levelBES}" />
				<tr>
					<td class="label" valign="top" nowrap="nowrap">Verifica presenza certificato di firma</td>
					<td class="value">
					<jsp:include page="signatureValidationImages.jsp">
						<jsp:param name="result" value="${signInfo.signatureLevelAnalysis.levelBES.signingCertRefVerification }" />
					</jsp:include>
					</td>
				</tr>
				<tr>
					<td class="label" valign="top" nowrap="nowrap">Certificato di firma</td>
					<td>
						<%
						WsSignatureLevelBES bes = (WsSignatureLevelBES)pageContext.getAttribute("bes");
						byte[] certBytes = bes.getSigningCertificate();
						CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
						InputStream in = new ByteArrayInputStream(certBytes);
						X509Certificate cert = (X509Certificate) certFactory.generateCertificate(in);
						String certSubject = cert.getSubjectX500Principal().getName(X500Principal.RFC1779);
						String certIssuer = cert.getIssuerX500Principal().getName(X500Principal.RFC1779);
						pageContext.setAttribute("cert", cert);
						pageContext.setAttribute("certSubject", certSubject);
						pageContext.setAttribute("certIssuer", certIssuer);
						%>
						<table border="0" cellpadding="2">
							<tr><td class="label" valign="top">Soggetto</td><td class="value">${certSubject }</td></tr>
							<tr><td class="label" valign="top">Emittente</td><td class="value">${certIssuer }</td></tr>
							<tr><td class="label" valign="top" nowrap="nowrap">Valido dal</td><td class="value"><fmt:formatDate value="${cert.notBefore }" pattern="${datePattern }" /></td></tr>
							<tr><td class="label" valign="top" nowrap="nowrap">Valido al</td><td class="value"><fmt:formatDate value="${cert.notAfter }" pattern="${datePattern }" /></td></tr>
							<tr><td class="label" valign="top">Seriale</td><td class="value">${cert.serialNumber }</td></tr>
						</table>
					</td>
				</tr>
				<c:if test="${signInfo.signatureLevelAnalysis.levelT.levelReached eq 'VALID' }">
				<tr>
					<td class="label" valign="top" nowrap="nowrap">Marche temporali (CAdES-T)</td>
					<td>
						<c:forEach items="${signInfo.signatureLevelAnalysis.levelT.signatureTimestampsVerification }" var="ts" varStatus="tsIdx">
							<table border="0" cellpadding="2">
								<tr>
									<td class="label" valign="top" nowrap="nowrap">Marca n. ${tsIdx.index + 1 }</td>
									<td>
										<table border="0" cellpadding="2">
											<tr>
												<td class="label" valign="top">Data e ora certificata</td>
												<td class="value">
													<c:set var="cadestTimestamp" value="${ts.creationTime }"/>
													<%
													XMLGregorianCalendar xmlgc = (XMLGregorianCalendar)pageContext.getAttribute("cadestTimestamp");
													pageContext.setAttribute("cadestTimestampDate", xmlgc.toGregorianCalendar().getTime());
													%>
													<fmt:formatDate value="${cadestTimestampDate }" pattern="${datePattern }" />
												</td>
											</tr>
											<tr><td class="label" valign="top">Emittente</td><td class="value">${ts.issuerName }</td></tr>
											<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione</td>
												<td class="value">
												<jsp:include page="signatureValidationImages.jsp">
													<jsp:param name="result" value="${ts.sameDigest }" />
												</jsp:include>
												</td>
											</tr>
											<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione certificati</td>
												<td class="value">
												<jsp:include page="signatureValidationImages.jsp">
													<jsp:param name="result" value="${ts.certPathVerification }" />
												</jsp:include>
												</td>
											</tr>
											<tr><td class="label" valign="top">Seriale</td><td class="value">${ts.serialNumber }</td></tr>
										</table>
									</td>
								</tr>
							</table>
						</c:forEach>
					</td>
				</tr>
				</c:if>
				<c:if test="${not empty bes.counterSignatureVerification }">
				<tr>
					<td class="label" valign="top" nowrap="nowrap">Controfirme</td>
					<td valign="top">
					<table border="0" cellpadding="2">
						<c:forEach items="${bes.counterSignatureVerification }" var="counterSignature" varStatus="csIdx">
						<tr>	
							<td class="label" valign="top" nowrap="nowrap">Controfirma n. ${csIdx.index + 1}</td>
							<td>
								<table border="0" cellpadding="2">
									<tr>	
										<td class="label" valign="top" nowrap="nowrap">Risultato validazione</td>
										<td class="value">
										<jsp:include page="signatureValidationImages.jsp">
											<jsp:param name="result" value="${counterSignature.signatureVerification.signatureVerificationResult }" />
										</jsp:include>
										</td>
									</tr>
									<tr>	
										<td class="label" valign="top" nowrap="nowrap">Data della firma</td>
										<td class="value">
											<c:set var="cSignature" value="${counterSignature.signatureVerification}" />
											<%
												WsSignatureVerification wssignver = (WsSignatureVerification)pageContext.getAttribute("cSignature");						
												pageContext.setAttribute("csignReferenceTime", wssignver.getReferenceTime().toGregorianCalendar().getTime());
											%>
											<fmt:formatDate value="${csignReferenceTime }" pattern="${datePattern }" />
										</td>
									</tr>
									<tr>	
										<td class="label" valign="top" nowrap="nowrap">Algoritmo di firma</td>
										<td class="value">${counterSignature.signatureVerification.signatureAlgorithm }</td>
									</tr>
									<tr>	
										<td class="label" valign="top" nowrap="nowrap">Algoritmo di hasing</td>
										<td class="value">${counterSignature.signatureVerification.digestAlgorithm }</td>
									</tr>
									<c:if test="${counterSignature.signatureLevelAnalysis.levelBES.levelReached eq 'VALID' }">
									<c:set var="bescsign" value="${counterSignature.signatureLevelAnalysis.levelBES}" />
									<tr>
										<td class="label" valign="top" nowrap="nowrap">Certificato di firma</td>
										<td>
											<%
											WsSignatureLevelBES bescsign = (WsSignatureLevelBES)pageContext.getAttribute("bescsign");
											byte[] bescsignCertBytes = bescsign.getSigningCertificate();
											CertificateFactory certFactory2 = CertificateFactory.getInstance("X.509");
											InputStream in2 = new ByteArrayInputStream(bescsignCertBytes);
											X509Certificate bescsignCert = (X509Certificate) certFactory2.generateCertificate(in2);
											String bescsignCertSubject = bescsignCert.getSubjectX500Principal().getName(X500Principal.RFC1779);
											String bescsignCertIssuer = bescsignCert.getIssuerX500Principal().getName(X500Principal.RFC1779);
											pageContext.setAttribute("bescsignCert", bescsignCert);
											pageContext.setAttribute("bescsignCertSubject", bescsignCertSubject);
											pageContext.setAttribute("bescsignCertIssuer", bescsignCertIssuer);
											%>
											<table border="0" cellpadding="2">
												<tr><td class="label" valign="top">Soggetto</td><td class="value">${bescsignCertSubject }</td></tr>
												<tr><td class="label" valign="top">Emittente</td><td class="value">${bescsignCertIssuer }</td></tr>
												<tr><td class="label" valign="top" nowrap="nowrap">Valido dal</td><td class="value"><fmt:formatDate value="${bescsignCert.notBefore }" pattern="${datePattern }" /></td></tr>
												<tr><td class="label" valign="top" nowrap="nowrap">Valido al</td><td class="value"><fmt:formatDate value="${bescsignCert.notAfter }" pattern="${datePattern }" /></td></tr>
												<tr><td class="label" valign="top">Seriale</td><td class="value">${bescsignCert.serialNumber }</td></tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="label" valign="top" nowrap="nowrap">Verifica certificato di firma</td>
										<td class="value">
										<jsp:include page="signatureValidationImages.jsp">
											<jsp:param name="result" value="${counterSignature.signatureLevelAnalysis.levelBES.signingCertRefVerification }" />
										</jsp:include>
										</td>
									</tr>
									<tr>
										<td class="label">Verifica revoca certificati</td>
										<td class="value">
										<jsp:include page="signatureValidationImages.jsp">
											<jsp:param name="result" value="${counterSignature.certPathRevocationAnalysis.summary }" />
										</jsp:include>
										</td>
									</tr>
									</c:if>
									<c:if test="${counterSignature.signatureLevelAnalysis.levelT.levelReached eq 'VALID' }">
									<tr>
										<td class="label" valign="top" nowrap="nowrap">Marche temporali (CAdES-T)</td>
										<td>
											<c:forEach items="${counterSignature.signatureLevelAnalysis.levelT.signatureTimestampsVerification }" var="tscs" varStatus="tscsIdx">
												<table border="0" cellpadding="2">
													<tr>
														<td class="label" valign="top" nowrap="nowrap">Marca n. ${tscsIdx.index + 1 }</td>
														<td>
															<table border="0" cellpadding="2">
																<tr>
																	<td class="label" valign="top">Data e ora certificata</td>
																	<td class="value">
																		<%
																		WsTimestampVerificationResult tscs = (WsTimestampVerificationResult)pageContext.getAttribute("tscs");
																		pageContext.setAttribute("creationTimecs", tscs.getCreationTime().toGregorianCalendar().getTime());
																		%>
																		<fmt:formatDate value="${creationTimecs }" pattern="${datePattern }" />
																	</td>
																</tr>
																<tr><td class="label" valign="top">Emittente</td><td class="value">${tscs.issuerName }</td></tr>
																<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione</td><td class="value">${tscs.sameDigest }</td></tr>
																<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione certificati</td><td class="value">${tscs.certPathVerification }</td></tr>
																<tr><td class="label" valign="top">Seriale</td><td class="value">${tscs.serialNumber }</td></tr>
															</table>
														</td>
													</tr>
												</table>
											</c:forEach>
										</td>
									</tr>
									</c:if>
								</table>
							</td>	
						</tr>
						</c:forEach>
					</table>
					</td>
				</tr>
				</c:if>
				</c:if>
				
				<tr>
					<td class="label" valign="top" nowrap="nowrap">Verifica revoca certificati</td>
					
					<td>
						<table border="0" cellpadding="2">
							<tr>
								<td class="label">Risultato</td>
								<td class="value">
								<jsp:include page="signatureValidationImages.jsp">
									<jsp:param name="result" value="${signInfo.certPathRevocationAnalysis.summary }" />
								</jsp:include>
								</td>
							</tr>
							<tr>
								<td class="label" valign="top" nowrap="nowrap">Lista certificati
									<img id="show_img_lista_certificati${signIdx.index }" onclick='showSection("lista_certificati${signIdx.index }")' src="${pageContext.request.contextPath}/images/signature/more.gif" />
									<img id="hide_img_lista_certificati${signIdx.index }" onclick='hideSection("lista_certificati${signIdx.index }")' style="display: none;" src="${pageContext.request.contextPath}/images/signature/less.gif" />
								</td>
								<td>
									<table border="0" cellpadding="2" style="display: none" id="lista_certificati${signIdx.index }">
									<c:forEach items="${signInfo.certPathRevocationAnalysis.certificatePathVerification }" var="certPathVer" varStatus="certPathIdx">
									<%
									WsCertificateVerification certPathVer = (WsCertificateVerification)pageContext.getAttribute("certPathVer");
									byte[] certPathBytes = certPathVer.getCertificate();
									CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
									InputStream in = new ByteArrayInputStream(certPathBytes);
									X509Certificate certPath = (X509Certificate) certFactory.generateCertificate(in);
									String certPathSubject = certPath.getSubjectX500Principal().getName(X500Principal.RFC1779);
									String certPathIssuer = certPath.getIssuerX500Principal().getName(X500Principal.RFC1779);
									pageContext.setAttribute("certPath", certPath);
									pageContext.setAttribute("certPathSubject", certPathSubject);
									pageContext.setAttribute("certPathIssuer", certPathIssuer);
									%>
									<tr>	
										<td class="label" valign="top" nowrap="nowrap">Certificato n. ${certPathIdx.index + 1}</td>
										<td>
											<table border="0" cellpadding="2">
												<tr>	
													<td class="label" valign="top" nowrap="nowrap">Soggetto</td>
													<td class="value">${certPathSubject }</td>
												</tr>
												<tr>	
													<td class="label" valign="top" nowrap="nowrap">Emittente</td>
													<td class="value">${certPathIssuer }</td>
												</tr>
												<tr>	
													<td class="label" valign="top">Verifica validità al tempo della firma</td>
													<td class="value">
													<jsp:include page="signatureValidationImages.jsp">
														<jsp:param name="result" value="${certPathVer.validityPeriodVerification }" />
													</jsp:include>
													</td>
												</tr>
												<c:if test="${not empty certPathVer.certificateStatus }">
												<tr>	
													<td class="label" valign="top" nowrap="nowrap">Stato del certificato</td>
													<td>
														<table border="0" cellpadding="2">
															<tr>	
																<td class="label" valign="top" nowrap="nowrap">Stato</td>
																<td class="value">
																<jsp:include page="signatureValidationImages.jsp">
																	<jsp:param name="result" value="${certPathVer.certificateStatus.status }" />
																</jsp:include>
																</td>
															</tr>		
															<tr>	
																<td class="label" valign="top" nowrap="nowrap">Data di Revoca</td>
																<td class="value">${certPathVer.certificateStatus.revocationDate }</td>
															</tr>
														</table>
													</td>
												</tr>
												</c:if>
											</table>
										</td>	
									</tr>
									</c:forEach>
								</table>
								</td>
							</tr>
							<tr>
								<td class="label" valign="top" nowrap="nowrap">Ente certificatore
									<img id="show_img_tsp${signIdx.index }" onclick='showSection("tsp${signIdx.index }")' src="${pageContext.request.contextPath}/images/signature/more.gif" />
									<img id="hide_img_tsp${signIdx.index }" onclick='hideSection("tsp${signIdx.index }")' style="display: none;" src="${pageContext.request.contextPath}/images/signature/less.gif" />
								</td>
								<td>
									<table border="0" cellpadding="2" style="display: none" id="tsp${signIdx.index }">
										<tr>	
											<td class="label" valign="top" nowrap="nowrap">Nome</td>
											<td class="value">${signInfo.certPathRevocationAnalysis.trustedListInformation.tspName }</td>
										</tr>
										<tr>	
											<td class="label" valign="top" nowrap="nowrap">Servizio</td>
											<td class="value">${signInfo.certPathRevocationAnalysis.trustedListInformation.serviceName }</td>
										</tr>
										<tr>	
											<td class="label" valign="top" nowrap="nowrap">Stato</td>
											<td class="value">${signInfo.certPathRevocationAnalysis.trustedListInformation.currentStatus }</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td class="label" valign="top" nowrap="nowrap">Estensioni obbligarorie</td>
					<td>
						<table border="0" cellpadding="2">
							<tr>
								<td class="label" valign="top">Certificato Qualificato secondo gli Allegati I e II della Direttiva Europea 1999/93/EC del 13 dicembre 1999.</td>
								<td class="value" nowrap="nowrap">
								<jsp:include page="signatureValidationImages.jsp">
									<jsp:param name="result" value="${signInfo.qcStatementInformation.qcCompliancePresent }" />
								</jsp:include>
								</td>
							</tr>
							<tr>
								<td class="label" valign="top">Il certificatore si impegna a conservare le informazioni di registrazione del titolare per 20 anni dopo la scadenza del certificato.</td>
								<td class="value" nowrap="nowrap">
								<jsp:include page="signatureValidationImages.jsp">
									<jsp:param name="result" value="${signInfo.qcStatementInformation.qcRetentionPeriod }" />
								</jsp:include>
								</td>
							</tr>
							<tr>
								<td class="label" valign="top">La chiave privata del titolare risiede in un Dispositivo Sicuro per la Creazione della Firma (SSCD) secondo l'Allegato III della Direttiva Europea 1999/93/EC del 13 dicembre 1999.</td>
								<td class="value" nowrap="nowrap">
								<jsp:include page="signatureValidationImages.jsp">
									<jsp:param name="result" value="${signInfo.qcStatementInformation.qcSCCDPresent }" />
								</jsp:include>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr><td class="label">Conclusione</td>
				<td class="value">
				<c:if test="${signInfo.finalConclusion eq 'QES'}">
				<img src="${pageContext.request.contextPath}/images/signature/ok.gif" alt="QES" />
				(QES) Firma Elettronica Qualificata. Costituisce una Firma Elettronica Avanzata basata su un Certificato Qualificato (QC) e creata da un Dispositivo Sicuro per la Creazione della Firma (SSCD), quale definita all'articolo 2 della direttiva 1999/93/CE.
				</c:if>
				<c:if test="${signInfo.finalConclusion eq 'AdES_QC'}">
				<img src="${pageContext.request.contextPath}/images/signature/wa.gif" alt="AdES_QC" />
				(AdES_QC) Firma Elettronica Avanzata basata su un Certificato Qualificato
				</c:if>
				<c:if test="${signInfo.finalConclusion eq 'AdES'}">
				<img src="${pageContext.request.contextPath}/images/signature/nok.gif" alt="AdES" />
				(AdES) Firma Elettronica Avanzata
				</c:if>
				</td></tr>
			</table>
		</td>
	</tr>
	</c:forEach>
	<c:if test="${not empty wsreport.detachedTsVerificationResult }">
	<tr>
		<td class="label" valign="top" nowrap="nowrap">Marche temporali (TSR)
			<img id="show_img_marcheTSR" onclick='showSection("marcheTSR")' src="${pageContext.request.contextPath}/images/signature/more.gif" />
			<img id="hide_img_marcheTSR" onclick='hideSection("marcheTSR")' style="display: none;" src="${pageContext.request.contextPath}/images/signature/less.gif" />							
		</td>
		<td>	
			<table border="0" cellpadding="2" style="display: none;" id="marcheTSR">
				<c:forEach items="${wsreport.detachedTsVerificationResult }" var="tsd" varStatus="tsdIdx">
				<tr>
					<td class="label" valign="top" nowrap="nowrap">Marca n. ${tsdIdx.index + 1 }</td>
					<td>
						<table border="0" cellpadding="2">	
							<tr>
								<td class="label" valign="top">Data e ora certificata</td>
								<td class="value">
									<%
									WsTimestampVerificationResult ts = (WsTimestampVerificationResult)pageContext.getAttribute("tsd");
									pageContext.setAttribute("creationTimeTSR", ts.getCreationTime().toGregorianCalendar().getTime());
									%>
									<fmt:formatDate value="${creationTimeTSR }" pattern="${datePattern }" />
								</td>
							</tr>
							<tr><td class="label" valign="top">Emittente</td><td class="value">${tsd.issuerName }</td></tr>
							<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione</td>
								<td class="value">
								<jsp:include page="signatureValidationImages.jsp">
									<jsp:param name="result" value="${tsd.sameDigest }" />
								</jsp:include>
								</td>
							</tr>
							<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione certificati</td>
								<td class="value">
								<jsp:include page="signatureValidationImages.jsp">
									<jsp:param name="result" value="${tsd.certPathVerification }" />
								</jsp:include>
								</td>
							</tr>
							<tr><td class="label" valign="top">Seriale</td><td class="value">${tsd.serialNumber }</td></tr>
						</table>	
					</td>
				</tr>
				</c:forEach>
			</table>			
		</td>
	</tr>
	</c:if>
</table>