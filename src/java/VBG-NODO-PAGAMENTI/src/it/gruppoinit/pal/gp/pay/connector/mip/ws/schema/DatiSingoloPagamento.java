package it.gruppoinit.pal.gp.pay.connector.mip.ws.schema;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiSingoloPagamento", propOrder = { "documento", "singoloImportoPagato", "esitoSingoloPagamento", "dataEsitoSingoloPagamento",
	"iur", "causale", "causaleRPT" })
public class DatiSingoloPagamento {

    @XmlElement(name = "Documento")
    private Documento documento;
    @XmlElement(name = "SingoloImportoPagato")
    private Integer singoloImportoPagato;
    @XmlElement(name = "EsitoSingoloPagamento")
    private String esitoSingoloPagamento;
    @XmlElement(name = "DataEsitoSingoloPagamento")
    private String dataEsitoSingoloPagamento;
    @XmlElement(name = "IUR")
    private String iur;
    @XmlElement(name = "Causale")
    private String causale;
    @XmlElement(name = "CausaleRPT")
    private String causaleRPT;

    public Documento getDocumento() {

	return documento;
    }

    public void setDocumento(Documento documento) {

	this.documento = documento;
    }

    public Integer getSingoloImportoPagato() {

	return singoloImportoPagato;
    }

    public void setSingoloImportoPagato(Integer singoloImportoPagato) {

	this.singoloImportoPagato = singoloImportoPagato;
    }

    public String getEsitoSingoloPagamento() {

	return esitoSingoloPagamento;
    }

    public void setEsitoSingoloPagamento(String esitoSingoloPagamento) {

	this.esitoSingoloPagamento = esitoSingoloPagamento;
    }

    public String getDataEsitoSingoloPagamento() {

	return dataEsitoSingoloPagamento;
    }

    public void setDataEsitoSingoloPagamento(String dataEsitoSingoloPagamento) {

	this.dataEsitoSingoloPagamento = dataEsitoSingoloPagamento;
    }

    public String getIur() {

	return iur;
    }

    public void setIur(String iur) {

	this.iur = iur;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public String getCausaleRPT() {

	return causaleRPT;
    }

    public void setCausaleRPT(String causaleRPT) {

	this.causaleRPT = causaleRPT;
    }

    @Override
    public String toString() {

	return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
