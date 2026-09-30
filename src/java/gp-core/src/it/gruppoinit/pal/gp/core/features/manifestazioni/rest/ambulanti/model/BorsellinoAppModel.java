package it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@XmlRootElement
public class BorsellinoAppModel {

    @XmlElement
    private String uuid;
    @XmlElement(name = "data_creazione")
    private String dataCreazione;
    @XmlElement
    private String descrizione;
    @XmlElement(name = "saldo_attuale")
    private BigDecimal saldoAttuale;
    @XmlElement(name = "saldo_contabile")
    private BigDecimal saldoContabile;
    @XmlElement
    private List<BorsellinoAppMovimenti> movimenti;
    @XmlElement(name = "autorizzazioni_collegate")
    private List<BorsellinoAppAutorizzazioni> autorizzazioniCollegate;
    @XmlElement(name = "autorizzazioni_collegabili")
    private List<BorsellinoAppAutorizzazioni> autorizzazioniCollegabili;

    public String getUuid() {

	return uuid;
    }

    public String getDataCreazione() {

	return dataCreazione;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public List<BorsellinoAppMovimenti> getMovimenti() {

	if (this.movimenti == null) {
	    this.movimenti = new ArrayList<BorsellinoAppMovimenti>();
	}
	return movimenti;
    }

    public void setMovimenti(List<BorsellinoAppMovimenti> movimenti) {

	this.movimenti = movimenti;
    }

    public List<BorsellinoAppAutorizzazioni> getAutorizzazioniCollegate() {

	if (this.autorizzazioniCollegate == null) {
	    this.autorizzazioniCollegate = new ArrayList<BorsellinoAppAutorizzazioni>();
	}
	return autorizzazioniCollegate;
    }

    public List<BorsellinoAppAutorizzazioni> getAutorizzazioniCollegabili() {

	if (this.autorizzazioniCollegabili == null) {
	    this.autorizzazioniCollegabili = new ArrayList<BorsellinoAppAutorizzazioni>();
	}
	return autorizzazioniCollegabili;
    }

    public BigDecimal getSaldoAttuale() {

	return saldoAttuale;
    }

    public BigDecimal getSaldoContabile() {

	return saldoContabile;
    }

    public static BorsellinoAppModel fromBorsellino(Borsellino borsellino) {

	BorsellinoAppModel ret = new BorsellinoAppModel();
	ret.dataCreazione = Utilities.formatDateISO8601(borsellino.getDataCreazione());
	ret.uuid = borsellino.getUuid();
	ret.descrizione = borsellino.getDescrizione();
	return ret;
    }

    @XmlTransient
    public void impostaSaldo() {

	BigDecimal saldoAttualeCalcolato = BigDecimal.ZERO;
	BigDecimal saldoContabileCalcolato = BigDecimal.ZERO;
	for (BorsellinoAppMovimenti bam : this.getMovimenti()) {
	    //
	    saldoAttualeCalcolato = saldoAttualeCalcolato.add(bam.getImportoAttuale());
	    saldoContabileCalcolato = saldoContabileCalcolato.add(bam.getImportoContabile());
	}
	this.saldoAttuale = saldoAttualeCalcolato;
	this.saldoContabile = saldoContabileCalcolato;
    }
}
