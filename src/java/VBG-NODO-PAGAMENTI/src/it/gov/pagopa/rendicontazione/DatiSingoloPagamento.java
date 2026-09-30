package it.gov.pagopa.rendicontazione;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "identificativoUnivocoVersamento" //
	, "identificativoUnivocoRiscossione" //
	, "indiceDatiSingoloPagamento"//
	, "singoloImportoPagato"//
	, "codiceEsitoSingoloPagamento"//
	, "dataEsitoSingoloPagamento"//
})
public class DatiSingoloPagamento {

    @XmlElement
    private String identificativoUnivocoVersamento;
    @XmlElement
    private String identificativoUnivocoRiscossione;
    @XmlElement
    private Integer indiceDatiSingoloPagamento;
    @XmlElement
    private Double singoloImportoPagato;
    @XmlElement
    private Integer codiceEsitoSingoloPagamento;
    @XmlElement
    private String dataEsitoSingoloPagamento;

    public String getIdentificativoUnivocoVersamento() {

	return identificativoUnivocoVersamento;
    }

    public void setIdentificativoUnivocoVersamento(String identificativoUnivocoVersamento) {

	this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
    }

    public String getIdentificativoUnivocoRiscossione() {

	return identificativoUnivocoRiscossione;
    }

    public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {

	this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
    }

    public Integer getIndiceDatiSingoloPagamento() {

	return indiceDatiSingoloPagamento;
    }

    public void setIndiceDatiSingoloPagamento(Integer indiceDatiSingoloPagamento) {

	this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
    }

    public Double getSingoloImportoPagato() {

	return singoloImportoPagato;
    }

    public void setSingoloImportoPagato(Double singoloImportoPagato) {

	this.singoloImportoPagato = singoloImportoPagato;
    }

    public Integer getCodiceEsitoSingoloPagamento() {

	return codiceEsitoSingoloPagamento;
    }

    public void setCodiceEsitoSingoloPagamento(Integer codiceEsitoSingoloPagamento) {

	this.codiceEsitoSingoloPagamento = codiceEsitoSingoloPagamento;
    }

    public String getDataEsitoSingoloPagamento() {

	return dataEsitoSingoloPagamento;
    }

    public void setDataEsitoSingoloPagamento(String dataEsitoSingoloPagamento) {

	this.dataEsitoSingoloPagamento = dataEsitoSingoloPagamento;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
