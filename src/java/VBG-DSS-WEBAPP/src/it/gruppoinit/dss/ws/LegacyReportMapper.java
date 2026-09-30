package it.gruppoinit.dss.ws;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import java.util.Collections;

import eu.europa.esig.dss.enumerations.Indication;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.enumerations.SignatureQualification;
import eu.europa.esig.dss.enumerations.SubIndication;
import eu.europa.esig.dss.simplereport.SimpleReport;
import eu.europa.esig.dss.diagnostic.DiagnosticData;
import eu.europa.esig.dss.diagnostic.SignatureWrapper;
import eu.europa.esig.dss.diagnostic.CertificateWrapper;
import eu.europa.esig.dss.diagnostic.TimestampWrapper;
import eu.europa.esig.dss.model.x509.CertificateToken;
import eu.europa.esig.dss.spi.signature.AdvancedSignature;
import eu.europa.esig.dss.spi.x509.CertificateSource;
import eu.europa.esig.dss.spi.x509.ListCertificateSource;

import it.gruppoinit.dss.ws.legacy.*;

/**
 * Maps DSS 6.x Reports to legacy WSValidationReport for backward compatibility.
 * Populates the legacy structure as closely as possible from DSS 6 data.
 */
public class LegacyReportMapper {

    private static final Logger LOG = Logger.getLogger(LegacyReportMapper.class.getName());

    /**
     * Mappa i report DSS 6.x al formato legacy. Se {@code signatures} non è null, i byte dei
     * certificati vengono presi dalle firme (CertificateToken.getEncoded()) quando
     * DiagnosticData non li fornisce (getBinaries() == null).
     */
    public static WSValidationReport mapToLegacy(SimpleReport simpleReport, DiagnosticData diagnosticData,
            byte[] extractedContent, String fileName, List<AdvancedSignature> signatures) {
        WSValidationReport report = new WSValidationReport();

        WSTimeInformation timeInfo = new WSTimeInformation();
        timeInfo.setVerificationTime(diagnosticData.getValidationDate());
        report.setTimeInformation(timeInfo);

        List<String> sigIds = simpleReport.getSignatureIdList();
        List<WSSignatureInformation> sigInfoList = new ArrayList<>();
        for (int i = 0; i < sigIds.size(); i++) {
            String sigId = sigIds.get(i);
            AdvancedSignature signature = (signatures != null && i < signatures.size()) ? signatures.get(i) : null;
            WSSignatureInformation sigInfo = new WSSignatureInformation();

            Indication indication = simpleReport.getIndication(sigId);
	    SubIndication subIndication = simpleReport.getSubIndication(sigId);
	    setFinalConclusion(sigId, sigInfo, indication, subIndication, simpleReport);
	    
	    WSSignatureVerification sigVerif = new WSSignatureVerification();
	    sigVerif.setSignatureVerificationResult(indication != null ? indication.name() : null);
            
	    SignatureWrapper sigWrapper = diagnosticData.getSignatureById(sigId);
            if (sigWrapper != null) {
                sigVerif.setSignatureAlgorithm(sigWrapper.getEncryptionAlgorithm() != null ? sigWrapper.getEncryptionAlgorithm().getName() : null);
                sigVerif.setDigestAlgorithm(sigWrapper.getDigestAlgorithm() != null ? sigWrapper.getDigestAlgorithm().getName() : null);
                sigVerif.setReferenceTime(sigWrapper.getClaimedSigningTime());
            }
            sigInfo.setSignatureVerification(sigVerif);

            WSSignatureLevelAnalysis levelAnalysis = new WSSignatureLevelAnalysis();
            if (sigWrapper != null) {
                levelAnalysis.setSignatureFormat(sigWrapper.getSignatureFormat() != null ? sigWrapper.getSignatureFormat().name() : null);
            }

            WSSignatureLevelBES levelBES = new WSSignatureLevelBES();
            SignatureLevel sigLevel = simpleReport.getSignatureFormat(sigId);
            levelBES.setLevelReached(sigLevel != null ? "VALID" : null);

            if (sigWrapper != null) {
                CertificateWrapper signingCert = sigWrapper.getSigningCertificate();
                if (signingCert != null) {
                    levelBES.setSigningCertRefVerification("VALID");
                    byte[] certBin = getCertificateBytes(signingCert, signature, true);
                    if (certBin != null) {
                        levelBES.setSigningCertificate(certBin);
                    }
                }
                List<byte[]> certList = new ArrayList<>();
                List<CertificateWrapper> chain = sigWrapper.getCertificateChain();
                if (chain != null) {
                    List<CertificateToken> tokens = buildCertTokenListFromSignature(signature);
                    for (CertificateWrapper cw : chain) {
                        byte[] bin = getCertificateBytes(cw, tokens);
                        if (bin != null) certList.add(bin);
                    }
                }
                if (!certList.isEmpty()) {
                    levelBES.setCertificates(certList);
                }
            }
            levelAnalysis.setLevelBES(levelBES);

            if (sigWrapper != null) {
                List<TimestampWrapper> timestamps = sigWrapper.getTimestampList();
                if (timestamps != null && !timestamps.isEmpty()) {
                    WSSignatureLevelT levelT = new WSSignatureLevelT();
                    levelT.setLevelReached("VALID");
                    List<WSTimestampVerificationResult> tsResults = new ArrayList<>();
                    for (TimestampWrapper ts : timestamps) {
                        WSTimestampVerificationResult tsResult = new WSTimestampVerificationResult();
                        tsResult.setCreationTime(ts.getProductionTime());
                        tsResult.setSignatureAlgorithm(ts.getEncryptionAlgorithm() != null ? ts.getEncryptionAlgorithm().getName() : null);
                        tsResult.setSameDigest(ts.isMessageImprintDataFound() && ts.isMessageImprintDataIntact() ? "true" : "false");
                        tsResults.add(tsResult);
                    }
                    levelT.setSignatureTimestampsVerification(tsResults);
                    levelAnalysis.setLevelT(levelT);
                }
            }

            sigInfo.setSignatureLevelAnalysis(levelAnalysis);

            if (sigWrapper != null) {
                WSCertPathRevocationAnalysis certPath = new WSCertPathRevocationAnalysis();
                certPath.setSummary(indication != null ? indication.name() : null);
                List<WSCertificateVerification> certVerifs = new ArrayList<>();
                List<CertificateWrapper> certChain = sigWrapper.getCertificateChain();
                if (certChain != null) {
                    List<CertificateToken> tokens = buildCertTokenListFromSignature(signature);
                    for (CertificateWrapper cw : certChain) {
                        WSCertificateVerification cv = new WSCertificateVerification();
                        byte[] bin = getCertificateBytes(cw, tokens);
                        if (bin != null) cv.setCertificate(bin);
                        cv.setValidityPeriodVerification(
                            cw.getNotBefore() != null && cw.getNotAfter() != null ? "VALID" : null
                        );
                        certVerifs.add(cv);
                    }
                }
                certPath.setCertificatePathVerification(certVerifs);

                CertificateWrapper signingCert = sigWrapper.getSigningCertificate();
                if (signingCert != null) {
                    WSTrustedListInformation tlInfo = new WSTrustedListInformation();
                    tlInfo.setServiceWasFound(signingCert.isTrustedChain());
                    certPath.setTrustedListInformation(tlInfo);
                }

                sigInfo.setCertPathRevocationAnalysis(certPath);
            }

            sigInfoList.add(sigInfo);
        }
        report.setSignatureInformationList(sigInfoList);

        if (extractedContent != null && extractedContent.length > 0) {
            WSDocument contentDoc = new WSDocument();
            try {
                final byte[] data = extractedContent;
                final String docName = fileName;
                jakarta.activation.DataSource ds = new ReusableByteArrayDataSource(data, "application/octet-stream", docName);
                contentDoc.setBinary(new jakarta.activation.DataHandler(ds));
            } catch (Exception e) {
                LOG.warning("Could not set binary content on WSDocument: " + e.getMessage());
            }
            contentDoc.setName(fileName);
            report.setContent(contentDoc);
        }

        return report;
    }

    private static void setFinalConclusion(String sigId, WSSignatureInformation sigInfo, Indication indication, SubIndication subIndication,
	    SimpleReport simpleReport) {

	String finalC = null;
	SignatureQualification sq = simpleReport.getSignatureQualification(sigId);
	switch (sq) {
	case ADESEAL, ADESEAL_QC, ADESIG, ADESIG_QC, INDETERMINATE_ADESEAL, INDETERMINATE_ADESEAL_QC, INDETERMINATE_ADESIG, INDETERMINATE_ADESIG_QC:
	    finalC = "AdES";
	    break;
	case QESIG, QESEAL, INDETERMINATE_QESEAL, INDETERMINATE_QESIG:
	    finalC = "QES";
	    break;
	default:
	    finalC = SignatureQualification.INDETERMINATE_UNKNOWN.getReadable();
	}
	sigInfo.setFinalConclusion(finalC);
    }
    
    /** Chiamata senza firme: i byte dei certificati solo da DiagnosticData (può restare null). */
    public static WSValidationReport mapToLegacy(SimpleReport simpleReport, DiagnosticData diagnosticData,
            byte[] extractedContent, String fileName) {
        return mapToLegacy(simpleReport, diagnosticData, extractedContent, fileName, null);
    }

    private static byte[] getCertificateBytes(CertificateWrapper cw, AdvancedSignature signature, boolean signingCert) {
        if (cw == null) return null;
        try {
            byte[] bin = cw.getBinaries();
            if (bin != null) return bin;
        } catch (Exception e) {
            LOG.fine("CertificateWrapper.getBinaries(): " + e.getMessage());
        }
        if (signature != null && signingCert) {
            CertificateToken token = signature.getSigningCertificateToken();
            if (token != null) {
                try {
                    return token.getEncoded();
                } catch (Exception e) {
                    LOG.fine("SigningCertificateToken.getEncoded(): " + e.getMessage());
                }
            }
        }
        return null;
    }

    private static byte[] getCertificateBytes(CertificateWrapper cw, List<CertificateToken> tokens) {
        if (cw == null) return null;
        try {
            byte[] bin = cw.getBinaries();
            if (bin != null) return bin;
        } catch (Exception e) {
            LOG.fine("CertificateWrapper.getBinaries(): " + e.getMessage());
        }
        if (tokens != null) {
            String wrapperDN = cw.getCertificateDN();
            String wrapperSerial = cw.getSerialNumber();
            for (CertificateToken token : tokens) {
                if (token == null) continue;
                try {
                    boolean match = false;
                    if (wrapperDN != null && token.getCertificate() != null) {
                        String tokenDN = token.getCertificate().getSubjectX500Principal().getName();
                        String tokenSerial = token.getCertificate().getSerialNumber().toString();
                        match = wrapperDN.equals(tokenDN)
                                || (wrapperSerial != null && wrapperSerial.equals(tokenSerial));
                    }
                    if (!match) {
                        String wId = cw.getId();
                        String tId = token.getDSSIdAsString();
                        match = wId != null && tId != null && wId.equals(tId);
                    }
                    if (match) {
                        byte[] enc = token.getEncoded();
                        if (enc != null) return enc;
                    }
                } catch (Exception e) {
                    LOG.fine("getCertificateBytes token match: " + e.getMessage());
                }
            }
        }
        return null;
    }

    private static List<CertificateToken> buildCertTokenListFromSignature(AdvancedSignature signature) {
        if (signature == null) return Collections.emptyList();
        List<CertificateToken> result = new ArrayList<>();
        try {
            CertificateSource source = signature.getCertificateSource();
            if (source != null && source.getCertificates() != null) {
                result.addAll(source.getCertificates());
            }
        } catch (Exception e) {
            LOG.fine("buildCertTokenListFromSignature getCertificateSource: " + e.getMessage());
        }
        try {
            ListCertificateSource completeSource = signature.getCompleteCertificateSource();
            if (completeSource != null) {
                for (CertificateSource cs : completeSource.getSources()) {
                    if (cs != null && cs.getCertificates() != null) {
                        for (CertificateToken t : cs.getCertificates()) {
                            if (t != null && !result.contains(t)) result.add(t);
                        }
                    }
                }
            }
        } catch (Exception | NoSuchMethodError e) {
            LOG.fine("buildCertTokenListFromSignature getCompleteCertificateSource: " + e.getMessage());
        }
        return result;
    }

    /**
     * DataSource che non lancia da getOutputStream(), per compatibilità con client CXF
     * che possono invocarlo durante la serializzazione MTOM ("Could not write attachments").
     */
    private static final class ReusableByteArrayDataSource implements jakarta.activation.DataSource {
        private final byte[] data;
        private final String contentType;
        private final String name;

        ReusableByteArrayDataSource(byte[] data, String contentType, String name) {
            this.data = data != null ? data : new byte[0];
            this.contentType = contentType != null ? contentType : "application/octet-stream";
            this.name = name != null ? name : "";
        }

        @Override
        public InputStream getInputStream() {
            return new ByteArrayInputStream(data);
        }

        @Override
        public OutputStream getOutputStream() {
            return new OutputStream() {
                private final java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
                @Override
                public void write(int b) { buffer.write(b); }
                @Override
                public void write(byte[] b, int off, int len) { buffer.write(b, off, len); }
                @Override
                public void flush() {}
                @Override
                public void close() {}
            };
        }

        @Override
        public String getContentType() { return contentType; }

        @Override
        public String getName() { return name; }
    }
}
