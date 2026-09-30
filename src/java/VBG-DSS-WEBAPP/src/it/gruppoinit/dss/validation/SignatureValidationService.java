package it.gruppoinit.dss.validation;

import java.util.List;

/**
 * Servizio di validazione delle firme digitali (DSS 5.x).
 */
public interface SignatureValidationService {

	/**
	 * Valida il documento firmato.
	 *
	 * @param documentBytes contenuto del file
	 * @param fileName      nome del file (usato per il tipo di documento)
	 * @param returnExtractedContent se true, nel risultato viene incluso il contenuto estratto (per p7m/p7d)
	 * @return risultato con report XML e eventuale contenuto estratto; in caso di errore ha validationErrorMessage
	 */
	ValidationResultDTO validate(byte[] documentBytes, String fileName, boolean returnExtractedContent, boolean isdatafirma);

	ValidationCfResultDTO checkCodiciFiscali(byte[] documentBytes, String fileName, List<String> cfs, boolean isdatafirma);

	EsitoFirmaDTO checkValidFirma(byte[] documentBytes, String fileName, boolean isdatafirma);

	byte[] estraiFile(byte[] documentBytes, String fileName);
}
