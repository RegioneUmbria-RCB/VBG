package it.gruppoinit.pal.gp.pay.connector.piemontepay.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "progressivo", "importo", "causale", "datiSpecificiRiscossione", "annoAccertamento", "numeroAccertamento" })
public class ComponentePagamentoType {

    @XmlElement(name = "progressivo", required = true)
    private Integer progressivo;
    @XmlElement(name = "importo", required = true)
    private BigDecimal importo;
    @XmlElement(name = "causale", required = true)
    private String causale;
    @XmlElement(name = "datiSpecificiRiscossione", required = true)
    private String datiSpecificiRiscossione;
    @XmlElement(name = "annoAccertamento", required = true)
    private Integer annoAccertamento;
    @XmlElement(name = "numeroAccertamento", required = true)
    private String numeroAccertamento;

    public ComponentePagamentoType() {

    }

    public ComponentePagamentoType(Integer progressivo, PayDettaglioImporti dettaglio) {

	this.setAnnoAccertamento(dettaglio.getAnnoAccertamento());
	this.setCausale(dettaglio.getDescCausale());
	this.setDatiSpecificiRiscossione(dettaglio.getDatiRiscossione());
	this.setImporto(dettaglio.getImporto());
	this.setNumeroAccertamento(dettaglio.getNumeroAccertamento());
	this.setProgressivo(progressivo);
    }

    public Integer getProgressivo() {

	return progressivo;
    }

    public void setProgressivo(Integer progressivo) {

	this.progressivo = progressivo;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public String getCausale() {

	return causale;
    }

    public void setCausale(String causale) {

	this.causale = causale;
    }

    public String getDatiSpecificiRiscossione() {

	return datiSpecificiRiscossione;
    }

    public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {

	this.datiSpecificiRiscossione = datiSpecificiRiscossione;
    }

    public Integer getAnnoAccertamento() {

	return annoAccertamento;
    }

    public void setAnnoAccertamento(Integer annoAccertamento) {

	this.annoAccertamento = annoAccertamento;
    }

    public String getNumeroAccertamento() {

	return numeroAccertamento;
    }

    public void setNumeroAccertamento(String numeroAccertamento) {

	this.numeroAccertamento = numeroAccertamento;
    }
}
