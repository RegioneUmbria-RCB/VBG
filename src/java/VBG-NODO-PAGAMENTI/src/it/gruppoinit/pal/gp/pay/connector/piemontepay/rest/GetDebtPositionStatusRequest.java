package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GetDebtPositionStatusRequest {

    private String cfEnte;
    private String codiceVersamento;
    private String iuv;

    public GetDebtPositionStatusRequest(String cfEnte, String codiceVersamento, String iuv) {

	super();
	this.cfEnte = cfEnte;
	this.codiceVersamento = codiceVersamento;
	this.iuv = iuv;
    }

    public String getCfEnte() {

	return cfEnte;
    }

    public void setCfEnte(String cfEnte) {

	this.cfEnte = cfEnte;
    }

    public String getCodiceVersamento() {

	return codiceVersamento;
    }

    public void setCodiceVersamento(String codiceVersamento) {

	this.codiceVersamento = codiceVersamento;
    }

    public String getIuv() {

	return iuv;
    }

    public void setIuv(String iuv) {

	this.iuv = iuv;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
