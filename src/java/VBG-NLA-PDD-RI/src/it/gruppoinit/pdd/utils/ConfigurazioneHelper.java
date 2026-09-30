package it.gruppoinit.pdd.utils;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;

public class ConfigurazioneHelper {

    private String codiceAccreditamento;
    private String descrizioneComune;
    private String codiceAmministrazioneIpa;
    private String codiceAoo;

    public String getCodiceAmministrazioneIpa() {

	return codiceAmministrazioneIpa;
    }

    public void setCodiceAmministrazioneIpa(String codiceAmministrazioneIpa) {

	this.codiceAmministrazioneIpa = codiceAmministrazioneIpa;
    }

    public String getCodiceAoo() {

	return codiceAoo;
    }

    public void setCodiceAoo(String codiceAoo) {

	this.codiceAoo = codiceAoo;
    }

    public String getCodiceAccreditamento() {

	return codiceAccreditamento;
    }

    public void setCodiceAccreditamento(String codiceAccreditamento) {

	this.codiceAccreditamento = codiceAccreditamento;
    }

    public String getDescrizioneComune() {

	return descrizioneComune;
    }

    public void setDescrizioneComune(String descrizioneComune) {

	this.descrizioneComune = descrizioneComune;
    }

    public BigInteger convertCodiceAccreditamentoToBigInteger(boolean effettuaValidazione) {

	if (StringUtils.isBlank(getCodiceAccreditamento()) && effettuaValidazione) {
	    Utilities.logAndThrowException("Il campo CONFIGURAZIONE.CODICE_ACCREDITAMENTO non è stato configurato.", ConfigurazioneHelper.class);
	}
	try {
	    BigInteger result = new BigInteger(getCodiceAccreditamento().trim());
	    return result;
	} catch (Exception e) {
	    if (effettuaValidazione) {
		Utilities.logAndThrowException(
			"Il campo CONFIGURAZIONE.CODICE_ACCREDITAMENTO non è stato configurato correttamente o non rappresenta un numero [" +
					       getCodiceAccreditamento().trim() + "].",
			ConfigurazioneHelper.class);
	    }
	}
	return null;
    }
}
