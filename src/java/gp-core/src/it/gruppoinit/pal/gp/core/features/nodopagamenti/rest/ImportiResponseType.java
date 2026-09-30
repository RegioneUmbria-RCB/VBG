package it.gruppoinit.pal.gp.core.features.nodopagamenti.rest;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImportiResponseType {

    @XmlElement(name = "id")
    private Integer id;
    @XmlElement(name = "importo")
    private BigDecimal importo;
    @XmlElement(name = "descrizione_causale")
    private String descrizioneCausale;
    @XmlElement(name = "dati_riscossione")
    private String datiRiscossione;
    @XmlElement(name = "anno_accertamento")
    private Integer annoAccertamento;
    @XmlElement(name = "numero_accertamento")
    private String numeroAccertamento;

    public ImportiResponseType() {

    }

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public BigDecimal getImporto() {

	return importo;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public String getDescrizioneCausale() {

	return descrizioneCausale;
    }

    public void setDescrizioneCausale(String descrizioneCausale) {

	this.descrizioneCausale = descrizioneCausale;
    }

    public String getDatiRiscossione() {

	return datiRiscossione;
    }

    public void setDatiRiscossione(String datiRiscossione) {

	this.datiRiscossione = datiRiscossione;
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
