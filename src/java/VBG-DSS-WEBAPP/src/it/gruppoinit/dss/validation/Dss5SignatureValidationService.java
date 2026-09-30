package it.gruppoinit.dss.validation;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import eu.europa.esig.dss.diagnostic.CertificateWrapper;
import eu.europa.esig.dss.diagnostic.DiagnosticData;
import eu.europa.esig.dss.diagnostic.SignatureWrapper;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.InMemoryDocument;
import eu.europa.esig.dss.simplereport.SimpleReport;
import eu.europa.esig.dss.spi.signature.AdvancedSignature;
import eu.europa.esig.dss.validation.SignedDocumentValidator;
import eu.europa.esig.dss.validation.reports.Reports;

/**
 * Implementazione del servizio di validazione firme usando DSS 5.x/6.x (eu.europa.esig.dss).
 * In DSS 6.x CertificateVerifier è in dss.spi.validation.
 */
public class Dss5SignatureValidationService implements SignatureValidationService {
	
	private Logger log = Logger.getLogger(this.getClass().getName());

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALIAN);

	private final eu.europa.esig.dss.spi.validation.CertificateVerifier certificateVerifier;

	public Dss5SignatureValidationService(eu.europa.esig.dss.spi.validation.CertificateVerifier certificateVerifier) {
		this.certificateVerifier = certificateVerifier;
	}

	@Override
	public ValidationResultDTO validate(byte[] documentBytes, String fileName, boolean returnExtractedContent, boolean isdatafirma) {
		ValidationResultDTO result = new ValidationResultDTO();
		try {
			DSSDocument document = new InMemoryDocument(documentBytes, fileName);
			SignedDocumentValidator validator = SignedDocumentValidator.fromDocument(document);
			validator.setCertificateVerifier(certificateVerifier);
			
			if(isdatafirma) {
				Date dataFirma = recuperaDataFirma(validator);
				if (dataFirma != null) {
					validator.setValidationTime(dataFirma);
				}
			}

			
			Reports reports = validator.validateDocument();

			result.setSimpleReportXml(reports.getXmlSimpleReport());
			result.setDetailedReportXml(reports.getXmlDetailedReport());
			result.setDiagnosticDataXml(reports.getXmlDiagnosticData());
			result.setSimpleReportSummary(buildSimpleReportSummary(reports.getSimpleReport()));

			if (returnExtractedContent) {
				DSSDocument originalDoc = getExtractedDocument(validator);
				if (originalDoc != null) {
					result.setExtractedContent(readBytes(originalDoc.openStream()));
					result.setExtractedContentFileName(originalDoc.getName());
				}
			}
		} catch (Exception e) {
			result.setValidationErrorMessage("Errore nella validazione: " + e.getMessage());
		}
		return result;
	}
	
	@Override
	public ValidationCfResultDTO checkCodiciFiscali(byte[] documentBytes, String fileName, List<String> cfs, boolean isdatafirma) {
		ValidationCfResultDTO result = new ValidationCfResultDTO();
		try {
			DSSDocument document = new InMemoryDocument(documentBytes, fileName);
			SignedDocumentValidator validator = SignedDocumentValidator.fromDocument(document);
			validator.setCertificateVerifier(certificateVerifier);
			
			if(isdatafirma) {
				Date dataFirma = recuperaDataFirma(validator);
				if (dataFirma != null) {
					validator.setValidationTime(dataFirma);
				}
			}

			Reports reports = validator.validateDocument();
			
			List<String> serialNumbers = new ArrayList<>();
			DiagnosticData diagnostic = reports.getDiagnosticData();
			for (SignatureWrapper signature : diagnostic.getSignatures()) {
			    CertificateWrapper cert = signature.getSigningCertificate();
			    String serialnumber = cert.getSubjectSerialNumber();
			    if(serialnumber != null) {
			    	serialNumbers.add(serialnumber);
			    }
			}
			log.fine(() -> "serialNumbers: " + serialNumbers);
			
			for(String cf : cfs) {				
				if(checkCfBySerialNumbers(cf, serialNumbers)) {
					result.getCfpresenti().add(cf);
				}else {
					result.getCfassenti().add(cf);
				}				
			}
			
			SimpleReportSummary reportSummary = buildSimpleReportSummary(reports.getSimpleReport());
			List<SignatureSummaryItem> signatureSummaries = reportSummary.getSignatureSummaries();
			
			result.setEsitofirma(true);
			for(SignatureSummaryItem item : signatureSummaries) {
				if(!item.getIndication().endsWith("PASSED")) {
					result.setEsitofirma(false);
				}
			}
			
			result.setSimpleReportSummary(reportSummary);
			
			return result;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean checkCfBySerialNumbers(String cf, List<String> serialNumbers) {
		for(String serialNumber : serialNumbers) {
			if(serialNumber.contains(cf)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public EsitoFirmaDTO checkValidFirma(byte[] documentBytes, String fileName, boolean isdatafirma) {		
		EsitoFirmaDTO result = new EsitoFirmaDTO();
		try {
			DSSDocument document = new InMemoryDocument(documentBytes, fileName);
			SignedDocumentValidator validator = SignedDocumentValidator.fromDocument(document);
			validator.setCertificateVerifier(certificateVerifier);
			
			if(isdatafirma) {
				Date dataFirma = recuperaDataFirma(validator);
				if (dataFirma != null) {
					validator.setValidationTime(dataFirma);
				}
			}
						
			Reports reports = validator.validateDocument();
			reports.getSimpleReport();
			
			SimpleReportSummary reportSummary = buildSimpleReportSummary(reports.getSimpleReport());
			List<SignatureSummaryItem> signatureSummaries = reportSummary.getSignatureSummaries();			
			result.setEsitofirma(true);
			
			if(signatureSummaries == null || signatureSummaries.isEmpty()) {
				result.setEsitofirma(false);
				return result;
			}
			
			for(SignatureSummaryItem item : signatureSummaries) {
				if(!item.getIndication().endsWith("PASSED")) {
					result.setEsitofirma(false);
				}
			}						
			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public byte[] estraiFile(byte[] documentBytes, String fileName) {

		try {
			DSSDocument document = new InMemoryDocument(documentBytes, fileName);
			SignedDocumentValidator validator = SignedDocumentValidator.fromDocument(document);
			DSSDocument originalDoc = getExtractedDocument(validator);
			if(originalDoc != null) {
				return readBytes(originalDoc.openStream());
			}
			return new byte[0];            
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Recupera il documento originale estratto (es. per p7m/p7d) dalla prima firma se disponibile.
	 */
	private DSSDocument getExtractedDocument(SignedDocumentValidator validator) {
		try {
			for (eu.europa.esig.dss.spi.signature.AdvancedSignature sig : validator.getSignatures()) {
				java.util.Collection<DSSDocument> originals = validator.getOriginalDocuments(sig.getId());
				if (originals != null && !originals.isEmpty()) {
					return originals.iterator().next();
				}
			}
		} catch (Exception ignored) {
			// non disponibile
		}
		return null;
	}

	private SimpleReportSummary buildSimpleReportSummary(SimpleReport sr) {
		if (sr == null) return null;
		SimpleReportSummary summary = new SimpleReportSummary();
		summary.setDocumentName(sr.getDocumentFilename());
		Date vt = sr.getValidationTime();
		summary.setValidationTime(vt != null ? DATE_FORMAT.format(vt) : null);
		summary.setValidSignaturesCount(sr.getValidSignaturesCount());
		summary.setSignaturesCount(sr.getSignaturesCount());
		try {
			if (sr.getJaxbModel() != null && sr.getJaxbModel().getValidationPolicy() != null) {
				Object pn = sr.getJaxbModel().getValidationPolicy().getPolicyName();
				if (pn != null) summary.setPolicyName(pn.toString());
			}
		} catch (Exception ignored) {
			// policy name opzionale
		}
		List<String> ids = sr.getSignatureIdList();
		if (ids != null) {
			List<SignatureSummaryItem> items = new ArrayList<>();
			for (String sigId : ids) {
				SignatureSummaryItem item = new SignatureSummaryItem();
				item.setSignatureId(sigId);
				if (sr.getIndication(sigId) != null) item.setIndication(sr.getIndication(sigId).name());
				if (sr.getSubIndication(sigId) != null) item.setSubIndication(sr.getSubIndication(sigId).name());
				if (sr.getSignatureFormat(sigId) != null) item.setSignatureFormat(sr.getSignatureFormat(sigId).name());
				item.setSignedBy(sr.getSignedBy(sigId));
				item.setErrorsSummary(joinMessages(sr.getAdESValidationErrors(sigId), sr.getQualificationErrors(sigId)));
				item.setWarningsSummary(joinMessages(sr.getAdESValidationWarnings(sigId), sr.getQualificationWarnings(sigId)));
				items.add(item);
			}
			summary.setSignatureSummaries(items);
		}
		return summary;
	}

	@SuppressWarnings("unchecked")
	private static String joinMessages(List<?> list1, List<?> list2) {
		StringBuilder sb = new StringBuilder();
		appendMessageValues(sb, list1);
		appendMessageValues(sb, list2);
		return sb.length() > 0 ? sb.toString() : null;
	}

	private static void appendMessageValues(StringBuilder sb, List<?> list) {
		if (list == null) return;
		for (Object o : list) {
			if (o == null) continue;
			String v = null;
			if (o instanceof String) {
				v = (String) o;
			} else {
				try {
					java.lang.reflect.Method getVal = o.getClass().getMethod("getValue");
					Object val = getVal.invoke(o);
					if (val != null) v = val.toString();
				} catch (Exception ignored) { }
			}
			if (v != null && !v.isEmpty()) {
				if (sb.length() > 0) sb.append(" ");
				sb.append(v);
			}
		}
	}

	private static byte[] readBytes(InputStream is) throws IOException {
		java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
		byte[] buf = new byte[8192];
		int n;
		while ((n = is.read(buf)) > 0) {
			baos.write(buf, 0, n);
		}
		return baos.toByteArray();
	}
	
	private Date recuperaDataFirma(SignedDocumentValidator validator) {

		List<AdvancedSignature> signatures = validator.getSignatures();
		if (!signatures.isEmpty()) {
		    return  signatures.get(0).getSigningTime();
		}

		return null;
	}
}
