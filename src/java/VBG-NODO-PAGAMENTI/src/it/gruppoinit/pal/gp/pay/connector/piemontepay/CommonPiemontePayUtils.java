package it.gruppoinit.pal.gp.pay.connector.piemontepay;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.utils.CryptoUtils;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.exception.PayConfigurationException;
import it.gruppoinit.pal.gp.pay.exception.PayException;
import it.gruppoinit.pal.gp.pay.service.helper.PayConfigurationHelper;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParamType;
import it.gruppoinit.pal.gp.pay.ws.schema.FormParametersType;

public class CommonPiemontePayUtils {

    public String generaDigestSessionePagamento(PayPosizioniDebitorie payPos, String idSessionePagamento) throws PayException {

	String sep = "%%%%";
	PayProfiliEntiCreditori profile = PayConfigurationHelper.getProfiloEnteCreditore();
	String pwd = profile.getPayConnector().getPwdPortalePagamenti();
	if (StringUtils.isBlank(pwd)) {
	    throw new PayConfigurationException("password del portale pagamenti non configurata. E' obbligatoria per PiemontePay");
	}
	StringBuilder sb = new StringBuilder(CryptoUtils.base64Encode(pwd.getBytes()));
	sb.append(sep);
	sb.append(profile.getIdAppPSP());
	sb.append(idSessionePagamento);
	sb.append(payPos.getIuv());
	sb.append(profile.getUrlEsitoPagamento());
	sb.append(sep);
	byte[] hashedBytes = CryptoUtils.hashWithAlgorithm(sb.toString(), "SHA-256");
	String digest = CryptoUtils.base64Encode(hashedBytes);
	if (digest.length() > 50) {
	    digest = digest.substring(0, 50);
	}
	return digest;
    }

    public FormParametersType generaParametriSessionePagamento(PayPosizioniDebitorie posDeb, String idSessione, String digest) throws PayException {

	//parametri da postare al portale dei pagamenti di PiemontePAY
	PayProfiliEntiCreditori ppayProf = PayConfigurationHelper.getProfiloEnteCreditore();
	FormParametersType params = new FormParametersType();
	FormParamType param = new FormParamType();
	param.setParamName("codiceChiamante");
	param.setValue(ppayProf.getIdAppPSP());
	params.getParam().add(param);
	param = new FormParamType();
	param.setParamName("digest");
	param.setValue(digest);
	params.getParam().add(param);
	param = new FormParamType();
	param.setParamName("iuv");
	param.setValue(posDeb.getIuv());
	params.getParam().add(param);
	param = new FormParamType();
	param.setParamName("codiceFiscale");
	param.setValue(posDeb.getSoggettoDebitore().getCfPi());
	params.getParam().add(param);
	param = new FormParamType();
	param.setParamName("identificativoPagamento");
	param.setValue(idSessione);
	params.getParam().add(param);
	return params;
    }
}
