<%@ page import="it.gruppoinit.dss.validation.ValidationResultDTO" %>
<%@ page import="it.gruppoinit.dss.validation.SimpleReportSummary" %>
<%@ page import="it.gruppoinit.dss.validation.SignatureSummaryItem" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dss.css" media="all" />
	<link rel="shortcut icon" href="${pageContext.request.contextPath}/images/favicon.ico" />
	<title>Verifica Firma Digitale - DSS</title>
	<style type="text/css">
		.label { font-style: italic; background-color: #C1C1C1; }
		.value { font-weight: bold; }
		.report-xml { background: #f5f5f5; border: 1px solid #ccc; padding: 10px; overflow: auto; max-height: 70vh; font-size: 12px; white-space: pre-wrap; word-break: break-all; font-family: Consolas, monospace; }
		.details-toggle { margin-top: 10px; }
		.report-section { width: 100%; table-layout: fixed; }
		.report-section td.label { width: 200px; }
		.report-section td:last-child { vertical-align: top; }
		.summary-box { background: #f8f9fa; border: 1px solid #dee2e6; border-radius: 6px; padding: 1rem 1.25rem; margin-bottom: 1rem; }
		.summary-box h4 { margin: 0 0 0.5rem 0; color: #333; }
		.summary-row { margin: 0.35rem 0; }
		.indication-valid { color: #008758; font-weight: bold; }
		.indication-invalid { color: #d9364f; font-weight: bold; }
		.indication-indeterminate { color: #a66300; font-weight: bold; }
		.sig-block { margin: 1rem 0; padding: 0.75rem; background: #fff; border: 1px solid #dee2e6; border-radius: 4px; }
		.sig-block .sig-errors { color: #d9364f; font-size: 0.95em; margin-top: 0.5rem; }
		.sig-block .sig-warnings { color: #a66300; font-size: 0.95em; margin-top: 0.25rem; }
	</style>
</head>
<body>

<div id="mainContainer">
<div id="header">
	<a href="${pageContext.request.contextPath}/index.html"><img src="${pageContext.request.contextPath}/images/dss-logo.png" alt="Indietro" /></a>
	<h1 style="position:absolute; top:30px; left:130px;">Verifica Firma Digitale</h1>
</div>
<div id="content">

	<c:forEach var="e" items="${errors}">
		<font color="red">${e}<br/></font>
	</c:forEach>

	<div align="left">
		<c:forEach var="pi" items="${processedItems}">
			<c:if test="${pi.fileUpload}">
				<h3>${pi.fieldName}: <font color="blue">${pi.fileName}</font>, ${pi.size} bytes.</h3>

				<c:forEach var="pie" items="${pi.errors}">
					<font color="red">${pie}<br/></font>
				</c:forEach>

				<c:if test="${empty pi.errors && pi.validationResult != null}">
					<c:set var="result" value="${pi.validationResult}" />

					<c:if test="${result.hasError}">
						<p><strong style="color: red;">Errore validazione:</strong> ${result.validationErrorMessage}</p>
					</c:if>

					<c:if test="${!result.hasError}">
						<c:set var="sum" value="${result.simpleReportSummary}" />
						<c:if test="${sum != null}">
							<div class="summary-box">
								<h4>Riepilogo validazione</h4>
								<c:if test="${not empty sum.policyName}">
									<div class="summary-row"><span class="label">Politica:</span> <c:out value="${sum.policyName}" /></div>
								</c:if>
								<div class="summary-row"><span class="label">Ora validazione:</span> <c:out value="${sum.validationTime}" /></div>
								<div class="summary-row"><span class="label">Firme valide:</span> <strong>${sum.validSignaturesCount}</strong> su <strong>${sum.signaturesCount}</strong></div>
								<c:forEach var="sig" items="${sum.signatureSummaries}" varStatus="st">
									<div class="sig-block">
										<div class="summary-row"><span class="label">Firma ${st.index + 1}</span></div>
										<div class="summary-row"><span class="label">Formato:</span> <c:out value="${sig.signatureFormat}" /></div>
										<c:if test="${not empty sig.signedBy}">
											<div class="summary-row"><span class="label">Firmatario:</span> <c:out value="${sig.signedBy}" /></div>
										</c:if>
										<div class="summary-row">
											<span class="label">Esito:</span>
											<c:choose>
												<c:when test="${sig.indication == 'TOTAL_PASSED' || sig.indication == 'PASSED'}">
													<span class="indication-valid"><c:out value="${sig.indication}" /><c:if test="${not empty sig.subIndication}"> — <c:out value="${sig.subIndication}" /></c:if></span>
												</c:when>
												<c:when test="${sig.indication == 'FAILED' || sig.indication == 'TOTAL_FAILED'}">
													<span class="indication-invalid"><c:out value="${sig.indication}" /><c:if test="${not empty sig.subIndication}"> — <c:out value="${sig.subIndication}" /></c:if></span>
												</c:when>
												<c:otherwise>
													<span class="indication-indeterminate"><c:out value="${sig.indication}" /><c:if test="${not empty sig.subIndication}"> — <c:out value="${sig.subIndication}" /></c:if></span>
												</c:otherwise>
											</c:choose>
										</div>
										<c:if test="${not empty sig.errorsSummary}">
											<div class="sig-errors"><strong>Errori:</strong> <c:out value="${sig.errorsSummary}" /></div>
										</c:if>
										<c:if test="${not empty sig.warningsSummary}">
											<div class="sig-warnings"><strong>Avvertenze:</strong> <c:out value="${sig.warningsSummary}" /></div>
										</c:if>
									</div>
								</c:forEach>
							</div>
						</c:if>

						<details class="details-toggle">
							<summary>Mostra XML Simple Report</summary>
							<pre class="report-xml"><c:out value="${result.simpleReportXml}" escapeXml="true" /></pre>
						</details>
						<c:if test="${not empty result.detailedReportXml}">
							<details class="details-toggle">
								<summary>Mostra XML Detailed Report</summary>
								<pre class="report-xml"><c:out value="${result.detailedReportXml}" escapeXml="true" /></pre>
							</details>
						</c:if>

						<c:if test="${not empty pi.contentFileName}">
							<h3>File estratto: <a href="validazione/${pi.contentFileName}" target="_blank"><font color="blue">${pi.contentFileName}</font></a></h3>
						</c:if>
					</c:if>
				</c:if>
			</c:if>

			<c:if test="${!pi.fileUpload}">
				<li>${pi.fieldName}=${pi.string}</li>
			</c:if>
		</c:forEach>
	</div>

	<p>${headerVerifica}</p>
</div>
<input type="button" onclick="history.back()" value="Indietro">
</div>

</body>
</html>
