package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.dss.WSDSSClient;
import it.gruppoinit.dss.wsclient.WsCertPathRevocationAnalysis;
import it.gruppoinit.dss.wsclient.WsCertificateVerification;
import it.gruppoinit.dss.wsclient.WsSignatureInformation;
import it.gruppoinit.dss.wsclient.WsValidationReport;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants.SecurityParams;
import it.gruppoinit.pal.gp.core.domain.helper.ReportValidazioneFirmaDigitale;
import it.gruppoinit.pal.gp.core.domain.helper.ReportValidazioneFirmaDigitale.ESITO_VERIFICA;
import it.gruppoinit.pal.gp.core.service.FirmaDigitaleService;
import it.gruppoinit.pal.gp.core.service.MessagesService;
import it.init.sigepro.rte.types.ErroreType;

import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;

import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirmaDigitaleServiceImpl implements FirmaDigitaleService {

    private static final Logger log = LoggerFactory.getLogger(FirmaDigitaleServiceImpl.class);
    @Autowired
    private MessagesService messagesService;

    @Override
    public ReportValidazioneFirmaDigitale validaFile(String fileName, DataHandler fileBinary) {

	String url = WebConstants.getSecurityParamValue(SecurityParams.WSHOSTURL_FIRMADIGITALE);
	log.debug("validaFile: fileName=[{}], url=[{}]", fileName, url);
	WSDSSClient client = new WSDSSClient(url);
	WsValidationReport wsValidationReport = null;
	String genericError = "";
	List<ErroreType> errors = new ArrayList<ErroreType>();
	List<ErroreType> warnings = new ArrayList<ErroreType>();
	try {
	    wsValidationReport = client.validate(fileName, fileBinary, true);
	} catch (Exception e) {
	    log.error("validaFile: fileName=[{}], url=[{}]", new Object[] { fileName, url, e });
	    genericError = e.getMessage();
	    //throw new RuntimeException(e);
	}
	this.getErrorsOrWarnings(wsValidationReport, fileName, genericError, errors, warnings);
	return new ReportValidazioneFirmaDigitale(fileName, wsValidationReport, errors, warnings);
    }

    private ESITO_VERIFICA getSignatureValidationResult(WsValidationReport wsReport) {

	Boolean isValid = null;
	if (wsReport != null) {
	    if (!wsReport.getSignatureInformationList().isEmpty()) {
		for (WsSignatureInformation wsSignInfo : wsReport.getSignatureInformationList()) {
		    if (wsSignInfo.getSignatureVerification().getSignatureVerificationResult().equalsIgnoreCase("INVALID")) {
			return ESITO_VERIFICA.NON_VALIDO;
		    }
		    //QES,AdES_QC,AdES
		    boolean isCurrentSignatureValid = wsSignInfo.getFinalConclusion().equals("QES")
			    || wsSignInfo.getFinalConclusion().equals("AdES_QC") || wsSignInfo.getFinalConclusion().equals("AdES");
		    if (isValid == null) {
			isValid = Boolean.valueOf(isCurrentSignatureValid);
		    } else {
			isValid = Boolean.valueOf(isValid.booleanValue() && isCurrentSignatureValid);
		    }
		}
	    } else {
		return ESITO_VERIFICA.NON_FIRMATO;
	    }
	} else {
	    return ESITO_VERIFICA.ERRORE;
	}
	return BooleanUtils.isTrue(isValid) ? ESITO_VERIFICA.VALIDO : ESITO_VERIFICA.NON_VALIDO;
    }

    private ESITO_VERIFICA getCertificatePathRevocationValidationResult(WsValidationReport wsReport) {

	ESITO_VERIFICA invalido = null;
	ESITO_VERIFICA indeterminato = null;
	if (wsReport != null) {
	    if (!wsReport.getSignatureInformationList().isEmpty()) {
		for (WsSignatureInformation wsSignInfo : wsReport.getSignatureInformationList()) {
		    String revocationSummary = wsSignInfo.getCertPathRevocationAnalysis().getSummary();
		    if ("INVALID".equals(revocationSummary)) {
			if (invalido == null) {
			    invalido = checkRevoked(wsSignInfo.getCertPathRevocationAnalysis());
			}
		    }
		    if ("UNDETERMINED".equals(revocationSummary)) {
			if (indeterminato == null) {
			    indeterminato = ESITO_VERIFICA.VERIFICA_REVOCA_NON_DETERMINATO;
			}
		    }
		}
	    } else {
		return ESITO_VERIFICA.NON_FIRMATO;
	    }
	} else {
	    return ESITO_VERIFICA.ERRORE;
	}
	if (invalido != null) {
	    return invalido;
	}
	if (indeterminato != null) {
	    return indeterminato;
	}
	return ESITO_VERIFICA.VALIDO;
    }

    private ESITO_VERIFICA checkRevoked(WsCertPathRevocationAnalysis revocation) {

	if (revocation.getCertificatePathVerification() != null) {
	    if (revocation.getTrustedListInformation() != null) {
		if (!revocation.getTrustedListInformation().isServiceWasFound()) {
		    return ESITO_VERIFICA.ERRORE;
		}
	    } else {
		return ESITO_VERIFICA.ERRORE;
	    }
	    for (WsCertificateVerification certVer : revocation.getCertificatePathVerification()) {
		String status = certVer.getCertificateStatus().getStatus();
		if ("REVOKED".equals(status)) {
		    return ESITO_VERIFICA.REVOCATO;
		}
		if ("UNKNOWN".equals(status)) {
		    return ESITO_VERIFICA.ERRORE;
		}
	    }
	}
	return ESITO_VERIFICA.NON_VALIDO;
    }

    private void getErrorsOrWarnings(WsValidationReport wsReport, String fileName, String genericError, List<ErroreType> errors,
	    List<ErroreType> warnings) {

	if (errors == null) {
	    errors = new ArrayList<ErroreType>();
	}
	if (warnings == null) {
	    warnings = new ArrayList<ErroreType>();
	}
	ESITO_VERIFICA esitoVerifica = this.getSignatureValidationResult(wsReport);
	ESITO_VERIFICA esitoVerificaRevoche = this.getCertificatePathRevocationValidationResult(wsReport);
	if (esitoVerifica.name().equals(ESITO_VERIFICA.ERRORE.name())) {
	    ErroreType err = new ErroreType();
	    err.setNumeroErrore(esitoVerifica.name());
	    err.setDescrizione(messagesService.getMessage("error.controllo-firma-digitale", new Object[] { fileName, genericError }));
	    errors.add(err);
	} else {
	    if (esitoVerifica.name().equals(ESITO_VERIFICA.NON_VALIDO.name())) {
		ErroreType err = new ErroreType();
		err.setNumeroErrore(esitoVerifica.name());
		err.setDescrizione(messagesService.getMessage("error.controllo-firma-digitale",
			new Object[] { fileName, messagesService.getMessage("error.firma-non-valida") }));
		errors.add(err);
	    }
	    if (esitoVerifica.name().equals(ESITO_VERIFICA.NON_FIRMATO.name())) {
		ErroreType err = new ErroreType();
		err.setNumeroErrore(esitoVerifica.name());
		err.setDescrizione(messagesService.getMessage("error.controllo-firma-digitale",
			new Object[] { fileName, messagesService.getMessage("error.file-non-firmato") }));
		errors.add(err);
	    }
	    //verifica certificati
	    if (esitoVerificaRevoche.name().equals(ESITO_VERIFICA.NON_VALIDO.name())) {
		ErroreType err = new ErroreType();
		err.setNumeroErrore(esitoVerificaRevoche.name());
		err.setDescrizione(messagesService.getMessage("error.controllo-certificati",
			new Object[] { fileName, messagesService.getMessage("error.certificato-non-valido") }));
		warnings.add(err);
	    }
	    if (esitoVerificaRevoche.name().equals(ESITO_VERIFICA.NON_FIRMATO.name())) {
		ErroreType err = new ErroreType();
		err.setNumeroErrore(esitoVerificaRevoche.name());
		err.setDescrizione(messagesService.getMessage("error.controllo-certificati",
			new Object[] { fileName, messagesService.getMessage("error.file-non-firmato") }));
		warnings.add(err);
	    }
	    if (esitoVerificaRevoche.name().equals(ESITO_VERIFICA.ERRORE.name())) {
		ErroreType err = new ErroreType();
		err.setNumeroErrore(esitoVerificaRevoche.name());
		err.setDescrizione(messagesService.getMessage("error.controllo-certificati", new Object[] { fileName, "" }));
		warnings.add(err);
	    }
	    if (esitoVerificaRevoche.name().equals(ESITO_VERIFICA.VERIFICA_REVOCA_NON_DETERMINATO.name())) {
		ErroreType err = new ErroreType();
		err.setNumeroErrore(esitoVerificaRevoche.name());
		err.setDescrizione(messagesService.getMessage("warning.controllo-revoca-certificati", new Object[] { fileName, "" }));
		warnings.add(err);
	    }
	    if (esitoVerificaRevoche.name().equals(ESITO_VERIFICA.REVOCATO.name())) {
		ErroreType err = new ErroreType();
		err.setNumeroErrore(esitoVerificaRevoche.name());
		err.setDescrizione(messagesService.getMessage("error.controllo-certificati",
			new Object[] { fileName, messagesService.getMessage("error.certificato-revocato") }));
		errors.add(err);
	    }
	}
    }
}
