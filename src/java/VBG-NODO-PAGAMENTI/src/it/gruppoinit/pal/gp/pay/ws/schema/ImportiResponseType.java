package it.gruppoinit.pal.gp.pay.ws.schema;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import it.gruppoinit.pal.gp.pay.dao.utils.PosizioneDebitoriaFiltrata;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;

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

    public ImportiResponseType(PayDettaglioImporti dettaglioImporto) {

	if (dettaglioImporto != null) {
	    this.id = dettaglioImporto.getId().getCodice();
	    this.importo = dettaglioImporto.getImporto();
	    this.descrizioneCausale = dettaglioImporto.getDescCausale();
	    this.datiRiscossione = dettaglioImporto.getDatiRiscossione();
	    this.annoAccertamento = dettaglioImporto.getAnnoAccertamento();
	    this.numeroAccertamento = dettaglioImporto.getNumeroAccertamento();
	}
    }

    public ImportiResponseType(PosizioneDebitoriaFiltrata posizione) {

	if (posizione == null) {
	    return;
	}
	this.id = posizione.getIdDettaglioImporti();
	this.annoAccertamento = posizione.getAnnoAccertamento();
	this.datiRiscossione = posizione.getDatiRiscossione();
	this.descrizioneCausale = posizione.getDescrizioneCausale();
	this.importo = posizione.getImporto();
	this.numeroAccertamento = posizione.getNumeroAccertamento();
    }

    public Integer getId() {

	return id;
    }

    public void setImporto(BigDecimal importo) {

	this.importo = importo;
    }

    public void setDescrizioneCausale(String descrizioneCausale) {

	this.descrizioneCausale = descrizioneCausale;
    }

    public void setDatiRiscossione(String datiRiscossione) {

	this.datiRiscossione = datiRiscossione;
    }

    public void setAnnoAccertamento(Integer annoAccertamento) {

	this.annoAccertamento = annoAccertamento;
    }

    public void setNumeroAccertamento(String numeroAccertamento) {

	this.numeroAccertamento = numeroAccertamento;
    }
}
