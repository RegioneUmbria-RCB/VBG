package it.gruppoinit.dss.ws;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.xml.ws.soap.MTOM;

import org.apache.commons.io.IOUtils;

import eu.europa.esig.dss.enumerations.MimeTypeEnum;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.simplereport.SimpleReport;
import eu.europa.esig.dss.diagnostic.DiagnosticData;
import eu.europa.esig.dss.enumerations.TokenExtractionStrategy;
import eu.europa.esig.dss.validation.SignedDocumentValidator;
import eu.europa.esig.dss.validation.reports.Reports;
import eu.europa.esig.dss.spi.validation.CertificateVerifier;
import eu.europa.esig.dss.spi.signature.AdvancedSignature;

import it.gruppoinit.dss.ws.legacy.WSDocument;
import it.gruppoinit.dss.ws.legacy.WSValidationReport;

/**
 * Endpoint SOAP che espone il contratto legacy di validazione esattamente come su master
 * (DSS 2.0 / eu.europa.ec.markt.dss), delegando la validazione vera alla DSS 6.x.
 * <p>
 * Signature legacy: {@code validateDocument(WSDocument, WSDocument, Boolean) → WSValidationReport}.
 * Namespace: {@code http://impl.ws.dss.markt.ec.europa.eu/}.
 */
@MTOM(enabled = true, threshold = 0)
@WebService(targetNamespace = "http://impl.ws.dss.markt.ec.europa.eu/")
public class LegacyValidationServiceAdapter {

	private static final Logger LOG = Logger.getLogger(LegacyValidationServiceAdapter.class.getName());

	private CertificateVerifier certificateVerifier;

	@WebMethod(exclude = true)
	public void setCertificateVerifier(CertificateVerifier certificateVerifier) {
		this.certificateVerifier = certificateVerifier;
	}

	@WebResult(name = "response")
	@WebMethod(operationName = "validateDocument")
	public WSValidationReport validateDocument(
			@WebParam(name = "document") WSDocument document,
			@WebParam(name = "originalDocument") WSDocument originalContent,
			@WebParam(name = "giveBackContent") Boolean giveBackContent) throws IOException {

		if (document == null || document.getBinary() == null) {
			throw new IllegalArgumentException("Il parametro 'document' o il suo contenuto binario e' null");
		}

		byte[] docBytes = IOUtils.toByteArray(document.getBinary().getInputStream());
		String fileName = document.getName();

		LOG.info("[LegacyAdapter] Documento ricevuto: name=" + fileName
				+ ", size=" + docBytes.length + " bytes"
				+ ", primi 10 bytes=" + bytesToHex(docBytes, 10));

		if (docBytes.length == 0) {
			throw new IllegalArgumentException("Il documento ricevuto ha 0 bytes");
		}

		DSSDocument dssDocument = new InMemoryDocument(docBytes, fileName);

		DSSDocument originalDoc = null;
		if (originalContent != null && originalContent.getBinary() != null) {
			byte[] origBytes = IOUtils.toByteArray(originalContent.getBinary().getInputStream());
			LOG.info("[LegacyAdapter] OriginalDocument ricevuto: name=" + originalContent.getName()
					+ ", size=" + origBytes.length + " bytes");
			originalDoc = new InMemoryDocument(origBytes, originalContent.getName());
		}

		SignedDocumentValidator validator;
		try {
			validator = SignedDocumentValidator.fromDocument(dssDocument);
		} catch (UnsupportedOperationException e) {
			LOG.warning("[LegacyAdapter] DSS non riconosce il formato del documento '"
					+ fileName + "' (" + docBytes.length + " bytes). "
					+ "Assicurarsi di inviare un documento firmato (p7m, PDF firmato, XAdES, etc.).");
			throw e;
		}
		validator.setCertificateVerifier(certificateVerifier);
		validator.setTokenExtractionStrategy(TokenExtractionStrategy.EXTRACT_CERTIFICATES_ONLY);
		if (originalDoc != null) {
			validator.setDetachedContents(java.util.Collections.singletonList(originalDoc));
		}

		Reports reports = validator.validateDocument();
		SimpleReport simpleReport = reports.getSimpleReport();
		DiagnosticData diagnosticData = reports.getDiagnosticData();

		byte[] extractedContent = null;
		boolean doGiveBack = giveBackContent != null ? giveBackContent : true;
		if (doGiveBack) {
			try {
				extractedContent = getExtractedContent(validator, dssDocument);
			} catch (Exception e) {
				LOG.log(Level.FINE, "Could not extract content: " + e.getMessage(), e);
			}
		}

		java.util.List<AdvancedSignature> sigs = validator.getSignatures();
		return LegacyReportMapper.mapToLegacy(simpleReport, diagnosticData, extractedContent, fileName,
				sigs != null ? sigs : java.util.Collections.emptyList());
	}

	private static String bytesToHex(byte[] bytes, int maxLen) {
		StringBuilder sb = new StringBuilder();
		int len = Math.min(bytes.length, maxLen);
		for (int i = 0; i < len; i++) {
			sb.append(String.format("%02X ", bytes[i]));
		}
		if (bytes.length > maxLen) sb.append("...");
		return sb.toString().trim();
	}

	private byte[] getExtractedContent(SignedDocumentValidator validator, DSSDocument dssDocument) {
		try {
			java.util.List<DSSDocument> originals = null;
			if (!validator.getSignatures().isEmpty()) {
				originals = validator.getOriginalDocuments(validator.getSignatures().get(0).getId());
			}
			if (originals != null && !originals.isEmpty()) {
				return IOUtils.toByteArray(originals.get(0).openStream());
			}
		} catch (Exception e) {
			LOG.log(Level.FINE, "getOriginalDocuments failed, returning raw bytes", e);
		}
		try {
			return IOUtils.toByteArray(dssDocument.openStream());
		} catch (IOException e) {
			return null;
		}
	}
}
