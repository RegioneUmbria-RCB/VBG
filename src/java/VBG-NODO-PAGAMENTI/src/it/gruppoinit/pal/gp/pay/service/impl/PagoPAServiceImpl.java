/**
 * 
 */
package it.gruppoinit.pal.gp.pay.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.service.PagoPAService;
import it.gruppoinit.pal.gp.pay.service.helper.IUVHelper;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;

/**
 * @author francol
 *
 */
@Service
public class PagoPAServiceImpl implements PagoPAService {

    private static final Logger log = LoggerFactory.getLogger(PagoPAServiceImpl.class);
    public static final String QRCODE_IDENTIFYER = "PAGOPA";
    public static final String QRCODE_SEPARATOR = "|";
    public static final String QRCODE_VERSION = "002";
    public static final int IUV_CHECK_DIGIT_DIVISOR = 93;

    @Override
    public String generaQRCode(PayPosizioniDebitorie posDeb) {

	if (posDeb != null && StringUtils.isNotBlank(posDeb.getCodiceAvviso())) {
	    StringBuilder qr = new StringBuilder(QRCODE_IDENTIFYER).append(QRCODE_SEPARATOR);
	    qr.append(QRCODE_VERSION).append(QRCODE_SEPARATOR).append(posDeb.getCodiceAvviso()).append(QRCODE_SEPARATOR);
	    PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
	    if (profEnte != null && StringUtils.isNotBlank(profEnte.getCfEnteQrcodePagopa())) {
		qr.append(profEnte.getCfEnteQrcodePagopa()).append(QRCODE_SEPARATOR);
		BigDecimal importo = BigDecimal.ZERO;
		Set<PayDettaglioImporti> imps = posDeb.getDettagliImporto();
		for (PayDettaglioImporti imp : imps) {
		    importo = importo.add(imp.getImporto());
		}
		importo = importo.multiply(new BigDecimal(100));
		String impString = importo.toBigInteger().toString();
		qr.append(impString);
		return qr.toString();
	    } else {
		if (log.isInfoEnabled()) {
		    log.info("impossibile generare il qrcode, identificativo dell'ente mancante.");
		}
		return null;
	    }
	} else {
	    if (log.isInfoEnabled()) {
		log.info("impossibile generare il qrcode, codice avviso mamcante.");
	    }
	    return null;
	}
    }
    //    /**
    //     * Generazione di IUV di tipo 3 (AUX DIGIT = 3) in cui per ciascun ente si tiene conto della possibiule esistenza di
    //     * più punti diversi di emissione degli IUV non coordinati fra loro. Si vedano le specifiche "SPECIFICHE ATTUATIVE
    //     * DEI CODICI IDENTIFICATIVI DI VERSAMENTO, RIVERSAMENTO E RENDICONTAZIONE" di PagoPA paragrafo 2.2.2.4 disponibile
    //     * online all'URL: <a href=
    //     * "https://www.agid.gov.it/sites/default/files/repository_files/linee_guida/specifiche_attuative_pagamenti_1_3.pdf">https://www.agid.gov.it/sites/default/files/repository_files/linee_guida/specifiche_attuative_pagamenti_1_3.pdf</a>
    //     */
    //    @Override
    //    public IUVHelper generaIUV(PayPosizioniDebitorie posDeb, String identificativoCausalePerCalcolo) {
    //
    //	StringBuilder iuv = new StringBuilder();
    //	PayProfiliEntiCreditori profEnte = PayConfigurationHelper.getProfiloEnteCreditore();
    //	if (profEnte != null && posDeb != null) {
    //	    //L'AUX_DIGIT va solo sul codice avviso e lo aggiunga automaticamente lo IUVHelper
    //	    //iuv.append("3");
    //	    int segregationCode = 0;
    //	    if (profEnte.getCodiceSegregazione() != null) {
    //		segregationCode = profEnte.getCodiceSegregazione();
    //	    }
    //	    //2 caratteri numerici che rappresentano il codice segregazione dello IUV configurato nel profilo dell'ente creditore
    //	    iuv.append(StringUtils.leftPad(Integer.toString(segregationCode), 2, '0'));
    //	    //13 caratteri numerici univoci per idcomune	   
    //	    // composti da 4 cifre che codificano l'id della causale di versamento deve essere numerico
    //	    iuv.append(StringUtils.leftPad(identificativoCausalePerCalcolo, 4, '0'));
    //	    //e 9 che codificano l'id della posizione debitoria
    //	    iuv.append(StringUtils.leftPad(Integer.toString(posDeb.getId().getCodice()), 9, '0'));
    //	    //2 caratteri numerici di controllo calcolati come resto della divisione per 93 della parte precedente dello IUV. 
    //	    BigInteger checkDigit = new BigInteger(iuv.toString());
    //	    checkDigit = checkDigit.remainder(BigInteger.valueOf(IUV_CHECK_DIGIT_DIVISOR));
    //	    iuv.append(StringUtils.leftPad(checkDigit.toString(), 2, '0'));
    //	}
    //	return new IUVHelper(iuv.toString());
    //    }
}
