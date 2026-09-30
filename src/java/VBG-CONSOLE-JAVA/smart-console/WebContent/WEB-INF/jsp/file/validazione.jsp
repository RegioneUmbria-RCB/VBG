<%@ page import="it.gruppoinit.dss.wsclient.WsSignatureVerification" %>
<%@ page import="javax.xml.datatype.XMLGregorianCalendar" %>
<%@ page import="it.gruppoinit.dss.wsclient.WsTimestampVerificationResult" %>
<%@ page import="java.util.Date" %>
<%@ page import="it.gruppoinit.dss.wsclient.WsCertificateVerification" %>
<%@ page import="javax.security.auth.x500.X500Principal" %>
<%@ page import="java.security.cert.X509Certificate" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.security.cert.CertificateFactory" %>
<%@ page import="it.gruppoinit.dss.wsclient.WsSignatureLevelBES" %>
<%@ page import="it.gruppoinit.dss.wsclient.WsValidationReport" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt' %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="images/favicon.ico" />
	<link rel="icon" type="image/gif" href="images/favicon_animated.gif" />
	<title>Verifica Firma Digitale</title>
	<style type="text/css">
		.label {font-style: italic; background-color: #C1C1C1;}
		.value {font-weight: bold;}
		.intestazioneEtichetta {font-size: 20px;}
		.intestazioneValore {font-size: 12px;}
		.title {font-size: 24px;position:absolute; top:24px; left:20px;"}
	</style>
</head>
<body>
<link type="text/css" rel="stylesheet" media="all" href="${pageContext.request.contextPath}/css/dss/dss.css" />		
<div id="mainContainer">
		<div id="header">
		  	<h1 style="position:absolute; top:20px; left:100x;">Verifica Firma Digitale</h1>
		</div>
		<div id="content">
			<fmt:setTimeZone value="Europe/Rome" scope="request" />
			<c:set var="datePattern" scope="request" value="EEEEEE dd MMMMMMMMM yyyy HH:mm:ss zz"/>
			<!--  Sezione errori -->
			<c:forEach var="e" items="${errors}">
				<font color="red">${e}<br/></font>
			</c:forEach>

		<div align="left">
			 <c:forEach var="pie" items="${pi.errors}">
				<font color="red">${pie}<br/></font>
			 </c:forEach>
			<c:if test="${empty pi.errors }">
			<!-- Sezione riepilogo numero firme e data -->
			<%SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss z"); %>
			<c:set var="report" value="${pi.report}" />
			<%WsValidationReport report = (WsValidationReport)pageContext.getAttribute("report"); %>
			<table border="0" cellpadding="5" width="100%">
				<tr><td class="label" nowrap="nowrap">Data verifica</td><td class="value"><%=sdf.format(report.getTimeInformation().getVerificationTime().toGregorianCalendar().getTime()) %></td></tr>
				<tr><td class="label" nowrap="nowrap">Totale firme presenti</td><td class="value"><%=report.getSignatureInformationList().size() %></td></tr>
			</table>
			<!-- SEZIONE RIEPILOGO SINGOLE FIRME PRESENTI -->
			<c:forEach items="${report.signatureInformationList }" var="signInfo" varStatus="signIdx">
				<table width="100%">
					<tr>
						<td rowspan="1" class="label"><b>Conclusione firma n. ${signIdx.index +1}</b></td>
					</tr>
					<tr>
						<td class="value">
								<%-- QES, AdES_QC, AdES, UNDETERMINED --%>
								<c:if test="${signInfo.finalConclusion eq 'QES'}">
								<img src="${pageContext.request.contextPath}/images/success.gif" alt="QES" />
								(QES) Firma Elettronica Qualificata. Costituisce una Firma Elettronica Avanzata basata su un Certificato Qualificato (QC) e creata da un Dispositivo Sicuro per la Creazione della Firma (SSCD), quale definita all'articolo 2 della direttiva 1999/93/CE.
								</c:if>
								<c:if test="${signInfo.finalConclusion eq 'AdES_QC'}">
								<img src="${pageContext.request.contextPath}/images/warning.gif" alt="AdES_QC" />
								(AdES_QC) Firma Elettronica Avanzata basata su un Certificato Qualificato
								</c:if>
								<c:if test="${signInfo.finalConclusion eq 'AdES'}">
								<img src="${pageContext.request.contextPath}/images/cross.gif" alt="AdES" />
								(AdES) Firma Elettronica Avanzata
								</c:if>
						</td>
					</tr>
					<c:set var="bes" value="${signInfo.signatureLevelAnalysis.levelBES}" />
					<tr>
						<td>
						<%
						WsSignatureLevelBES bes1 = (WsSignatureLevelBES)pageContext.getAttribute("bes");
						byte[] certBytes1 = bes1.getSigningCertificate();
						CertificateFactory certFactory1 = CertificateFactory.getInstance("X.509");
						InputStream in1 = new ByteArrayInputStream(certBytes1);
						X509Certificate cert1 = (X509Certificate) certFactory1.generateCertificate(in1);
						String certSubject1 = cert1.getSubjectX500Principal().getName(X500Principal.RFC1779);
						String certIssuer1 = cert1.getIssuerX500Principal().getName(X500Principal.RFC1779);
						pageContext.setAttribute("cert1", cert1);
						pageContext.setAttribute("certSubject1", certSubject1);
						pageContext.setAttribute("certIssuer1", certIssuer1);
					   %>
					    <!-- Sezione interna al riepilogo della singola firma che riporta il dettaglio del soggetto firmante -->
						<table border="0" cellpadding="2">
								<tr><td class="label" valign="top">Soggetto</td><td class="value">${certSubject1 }</td></tr>
								<tr><td class="label" valign="top">Emittente</td><td class="value">${certIssuer1 }</td></tr>
								<tr><td class="label" valign="top" nowrap="nowrap">Valido dal</td><td class="value"><fmt:formatDate value="${cert1.notBefore }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
								<tr><td class="label" valign="top" nowrap="nowrap">Valido al</td><td class="value"><fmt:formatDate value="${cert1.notAfter }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
								<tr><td class="label" valign="top">Seriale</td><td class="value">${cert1.serialNumber}</td></tr>
						</table>
						</td>
					</tr>	
					<tr>
			    
				     <% 
				        // Stile per non mostare il dettaglio della firma all'apertura del popup
						String displaySchedeAttivita = "display: none;";
						String styleSchedeAttivita = "sezioneDatiPiu";
					 %>
						<td width="100%">
						    <a
							class="<%=styleSchedeAttivita%>"
							id="id_link_dettaglio_firma_attivita${signIdx.index}"
							href="javascript:showHidePanelBase('id_dettaglio_firma_table${signIdx.index}','id_link_dettaglio_firma_attivita${signIdx.index}','','${pageContext.request.contextPath}/images/','tr',false);"
							title="<fmt:message key="label.mostra_nasconde_sezione" />Dettaglio firma n. ${signIdx.index +1 }">
							<label for="id_link_dettaglio_firma_attivita${signIdx.index}"><b>Dettaglio firma n. ${signIdx.index +1 }</b></label> 
							</a>
						</td>
					</tr>	
				    <!-- Sezione interna alla firma che riporta tutti i dettagli, di deafult non è visibile, deve essere 
				         espsnsa dall'operatore  -->
					<tr style="<%=displaySchedeAttivita%>" id="id_dettaglio_firma_table${signIdx.index}">	
					<td>
						<table border="0" cellpadding="2">
							<tr><td class="label" nowrap="nowrap">Risultato validazione</td>
								<td class="value">
									<jsp:include page="result.jsp">
										<jsp:param name="result" value="${signInfo.signatureVerification.signatureVerificationResult }" />
									</jsp:include>
								</td>
							</tr>
							<tr><td class="label" nowrap="nowrap">Algoritmo di firma</td><td class="value">${signInfo.signatureVerification.signatureAlgorithm }</td></tr>
							<tr><td class="label" nowrap="nowrap">Algoritmo di hashing</td><td class="value">${signInfo.signatureVerification.digestAlgorithm }</td></tr>
							<c:set var="sigTime" value="${signInfo.signatureVerification.referenceTime}" />
							<%
							XMLGregorianCalendar sigTime = (XMLGregorianCalendar)pageContext.getAttribute("sigTime");
							Date _sigTime = sigTime.toGregorianCalendar().getTime();
							pageContext.setAttribute("_sigTime", _sigTime);
							%>
							<tr><td class="label" nowrap="nowrap">Riferimento temporale</td><td class="value"><fmt:formatDate value="${_sigTime }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
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
										<tr><td class="label" valign="top" nowrap="nowrap">Valido dal</td><td class="value"><fmt:formatDate value="${cert.notBefore }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
										<tr><td class="label" valign="top" nowrap="nowrap">Valido al</td><td class="value"><fmt:formatDate value="${cert.notAfter }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
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
														<%
														WsTimestampVerificationResult ts = (WsTimestampVerificationResult)pageContext.getAttribute("ts");
														Date creationTime = ts.getCreationTime().toGregorianCalendar().getTime();
														pageContext.setAttribute("creationTime", creationTime);
														%>
														<tr><td class="label" valign="top">Data e ora certificata</td><td class="value"><fmt:formatDate value="${creationTime }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
														<tr><td class="label" valign="top">Emittente</td><td class="value">${ts.issuerName }</td></tr>
														<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione</td>
															<td class="value">
															<jsp:include page="result.jsp">
																<jsp:param name="result" value="${ts.sameDigest }" />
															</jsp:include>
															</td>
														</tr>
														<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione certificati</td>
															<td class="value">
															<jsp:include page="result.jsp">
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
													<jsp:include page="result.jsp">
														<jsp:param name="result" value="${counterSignature.signatureVerification.signatureVerificationResult }" />
													</jsp:include>
													</td>
												</tr>
												<tr>	
													<td class="label" valign="top" nowrap="nowrap">Data della firma</td>
													<c:set var="cSignature" value="${counterSignature.signatureVerification}" />
													<%
														WsSignatureVerification wssignver = (WsSignatureVerification)pageContext.getAttribute("cSignature");
														Date referenceTime = wssignver.getReferenceTime().toGregorianCalendar().getTime();
														pageContext.setAttribute("csignReferenceTime", referenceTime);
													%>
													<td class="value"><fmt:formatDate value="${csignReferenceTime }" pattern="dd/MM/yyyy HH:mm:ss z" /></td>
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
															<tr><td class="label" valign="top" nowrap="nowrap">Valido dal</td><td class="value"><fmt:formatDate value="${bescsignCert.notBefore }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
															<tr><td class="label" valign="top" nowrap="nowrap">Valido al</td><td class="value"><fmt:formatDate value="${bescsignCert.notAfter }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
															<tr><td class="label" valign="top">Seriale</td><td class="value">${bescsignCert.serialNumber }</td></tr>
														</table>
													</td>
												</tr>
												<tr>
													<td class="label" valign="top" nowrap="nowrap">Verifica certificato di firma</td>
													<td class="value">
													<jsp:include page="result.jsp">
														<jsp:param name="result" value="${counterSignature.signatureLevelAnalysis.levelBES.signingCertRefVerification }" />
													</jsp:include>
													</td>
												</tr>
												<tr>
													<td class="label">Verifica revoca certificati</td>
													<td class="value">
													<jsp:include page="result.jsp">
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
																			<%
																			WsTimestampVerificationResult tscs = (WsTimestampVerificationResult)pageContext.getAttribute("tscs");
																			Date creationTimecs = tscs.getCreationTime().toGregorianCalendar().getTime();
																			pageContext.setAttribute("creationTimecs", creationTimecs);
																			%>
																			<tr><td class="label" valign="top">Data e ora certificata</td><td class="value"><fmt:formatDate value="${creationTimecs }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
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
								<td class="label" valign="top" nowrap="nowrap">Verifica certificato di firma</td>
								<td class="value">
								<jsp:include page="result.jsp">
									<jsp:param name="result" value="${signInfo.signatureLevelAnalysis.levelBES.signingCertRefVerification }" />
								</jsp:include>
								</td>
							</tr>
							<tr>
								<td class="label" valign="top" nowrap="nowrap">Verifica revoca certificati</td>
								
								<td>
									<table border="0" cellpadding="2">
										<tr>
											<td class="label">Risultato</td>
											<td class="value">
											<jsp:include page="result.jsp">
												<jsp:param name="result" value="${signInfo.certPathRevocationAnalysis.summary }" />
											</jsp:include>
											</td>
										</tr>
										<tr>
											<td class="label" valign="top" nowrap="nowrap">Lista certificati</td>
											<td>
												<table border="0" cellpadding="2">
												<c:forEach items="${signInfo.certPathRevocationAnalysis.certificatePathVerification }" var="certPathVer" varStatus="certPathIdx">
												<%
												WsCertificateVerification certPathVer = (WsCertificateVerification)pageContext.getAttribute("certPathVer");
												byte[] certPathBytes = certPathVer.getCertificate();
												CertificateFactory certFactory3 = CertificateFactory.getInstance("X.509");
												InputStream in = new ByteArrayInputStream(certPathBytes);
												X509Certificate certPath = (X509Certificate) certFactory3.generateCertificate(in);
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
																<jsp:include page="result.jsp">
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
																			<jsp:include page="result.jsp">
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
											<td class="label" valign="top" nowrap="nowrap">Trusted List utilizzata</td>
											<td>
												<table border="0" cellpadding="2">
													<tr>	
														<td class="label" valign="top" nowrap="nowrap">Nome TSP</td>
														<td class="value">${signInfo.certPathRevocationAnalysis.trustedListInformation.tspName }</td>
													</tr>
													<tr>	
														<td class="label" valign="top" nowrap="nowrap">Nome</td>
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
								<td class="label" valign="top" nowrap="nowrap">QcStatements</td>
								<td>
									<table border="0" cellpadding="2">
										<tr>
											<td class="label" valign="top">QcCompliance: Certificato Qualificato secondo gli Allegati I e II della Direttiva Europea 1999/93/EC del 13 dicembre 1999.</td>
											<td class="value" nowrap="nowrap">
											<jsp:include page="result.jsp">
												<jsp:param name="result" value="${signInfo.qcStatementInformation.qcCompliancePresent }" />
											</jsp:include>
											</td>
										</tr>
										<tr>
											<td class="label" valign="top">QcRetentionPeriod: Il certificatore si impegna a conservare le informazioni di registrazione del titolare per 20 anni dopo la scadenza del certificato.</td>
											<td class="value" nowrap="nowrap">
											<jsp:include page="result.jsp">
												<jsp:param name="result" value="${signInfo.qcStatementInformation.qcRetentionPeriod }" />
											</jsp:include>
											</td>
										</tr>
										<tr>
											<td class="label" valign="top">QcSSCD: La chiave privata del titolare risiede in un Dispositivo Sicuro per la Creazione della Firma (SSCD) secondo l'Allegato III della Direttiva Europea 1999/93/EC del 13 dicembre 1999.</td>
											<td class="value" nowrap="nowrap">
											<jsp:include page="result.jsp">
												<jsp:param name="result" value="${signInfo.qcStatementInformation.qcSCCDPresent }" />
											</jsp:include>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				</c:forEach>
				<c:if test="${not empty report.detachedTsVerificationResult }">
				<tr>
					<td class="label" valign="top" nowrap="nowrap">Marche temporali TSD</td>
					<td>	
						<table border="0" cellpadding="2">
							<c:forEach items="${report.detachedTsVerificationResult }" var="tsd" varStatus="tsdIdx">
							<tr>
								<td class="label" valign="top" nowrap="nowrap">Marca n. ${tsdIdx.index + 1 }</td>
								<td>
									<table border="0" cellpadding="2">
										<%
										WsTimestampVerificationResult ts = (WsTimestampVerificationResult)pageContext.getAttribute("tsd");
										Date creationTime = ts.getCreationTime().toGregorianCalendar().getTime();
										pageContext.setAttribute("creationTime", creationTime);
										%>
										<tr><td class="label" valign="top">Data e ora certificata</td><td class="value"><fmt:formatDate value="${creationTime }" pattern="dd/MM/yyyy HH:mm:ss z" /></td></tr>
										<tr><td class="label" valign="top">Emittente</td><td class="value">${tsd.issuerName }</td></tr>
										<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione</td>
											<td class="value">
											<jsp:include page="result.jsp">
												<jsp:param name="result" value="${tsd.sameDigest }" />
											</jsp:include>
											</td>
										</tr>
										<tr><td class="label" valign="top" nowrap="nowrap">Risultato validazione certificati</td>
											<td class="value">
											<jsp:include page="result.jsp">
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
		</c:if>
</div>
<p>${headerVerifica}</p>

	<div id="functions">
		<ul>
			<li><a href="${downloadLink}"><fmt:message key="button.download" /></a></li>
			<li><a href="javascript:self.close();"><fmt:message key="button.back" /></a></li>
		</ul>
	</div>
</div>

</body>
</html>