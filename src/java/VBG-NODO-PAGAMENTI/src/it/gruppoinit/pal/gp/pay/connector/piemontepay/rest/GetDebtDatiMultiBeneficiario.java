package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class GetDebtDatiMultiBeneficiario {

    @XmlElement
    private String enteBeneficiario;
    @XmlElement
    private String codiceFiscaleEnteBeneficiario;
    @XmlElement
    private String tipologiaVersamento;
    @XmlElement
    private BigDecimal importoPagato;

    public String getEnteBeneficiario() {

	return enteBeneficiario;
    }

    public void setEnteBeneficiario(String enteBeneficiario) {

	this.enteBeneficiario = enteBeneficiario;
    }

    public String getCodiceFiscaleEnteBeneficiario() {

	return codiceFiscaleEnteBeneficiario;
    }

    public void setCodiceFiscaleEnteBeneficiario(String codiceFiscaleEnteBeneficiario) {

	this.codiceFiscaleEnteBeneficiario = codiceFiscaleEnteBeneficiario;
    }

    public String getTipologiaVersamento() {

	return tipologiaVersamento;
    }

    public void setTipologiaVersamento(String tipologiaVersamento) {

	this.tipologiaVersamento = tipologiaVersamento;
    }

    public BigDecimal getImportoPagato() {

	return importoPagato;
    }

    public void setImportoPagato(BigDecimal importoPagato) {

	this.importoPagato = importoPagato;
    }

    @XmlTransient
    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
