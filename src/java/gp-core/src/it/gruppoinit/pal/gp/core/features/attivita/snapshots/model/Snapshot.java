package it.gruppoinit.pal.gp.core.features.attivita.snapshots.model;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitaSnapshot;
import it.gruppoinit.pal.gp.core.domain.IAttivitaTipologie;
import it.gruppoinit.pal.gp.core.domain.Istanze;

public class Snapshot extends SnapshotIdData {

    private IAttivita iattivita;
    private String denominazione;
    private Istanze istanza;
    private boolean attiva;
    private boolean operante;
    private Integer codiceOsservatorio;
    private IAttivitaTipologie tipologiaAttivita;
    private List<AttivitaDyn2DatiSnapshot> dyn2DatiSnapshots;
    private List<AttivitaDyn2ModelliSnapshot> dyn2ModelliSnapshots;

    public Snapshot() {

	super();
    }

    public static Snapshot fromAttivitaCessata(IAttivita attivita) {

	if (attivita == null) {
	    throw new IllegalArgumentException("Il parametro attivita non può essere nullo");
	}
	Snapshot s = new Snapshot();
	s.iattivita = attivita;
	s.attiva = false;
	s.operante = false;
	s.denominazione = attivita.getDenominazione();
	s.istanza = attivita.getIstanza();
	s.setData(attivita.getDataFine());
	s.codiceOsservatorio = attivita.getCodiceOsservatorio();
	s.tipologiaAttivita = attivita.getTipologiaAttivita();
	return s;
    }

    public Snapshot(IAttivitaSnapshot from) {

	this();
	this.setId(from.getId().getCodice());
	this.iattivita = from.getAttivita();
	this.attiva = from.getAttiva() == null ? false : from.getAttiva().booleanValue();
	this.operante = from.getOperante() == null ? false : from.getOperante().booleanValue();
	this.denominazione = from.getDenominazione();
	this.istanza = from.getIstanza();
	this.setData(from.getData());
	this.codiceOsservatorio = from.getCodiceOsservatorio();
	this.tipologiaAttivita = from.getTipologiaAttivita();
    }

    public IAttivita getIattivita() {

	return iattivita;
    }

    public void setIattivita(IAttivita iattivita) {

	this.iattivita = iattivita;
    }

    public String getDenominazione() {

	return denominazione;
    }

    public void setDenominazione(String denominazione) {

	this.denominazione = denominazione;
    }

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
	if (istanza == null) {
	    this.setData(null);
	} else {
	    this.setData(istanza.getDatavalidita());
	}
    }

    public boolean isAttiva() {

	return attiva;
    }

    public void setAttiva(boolean attiva) {

	this.attiva = attiva;
	if (!this.attiva) {
	    this.setOperante(false);
	}
    }

    public boolean isOperante() {

	return operante;
    }

    public void setOperante(boolean operante) {

	this.operante = operante;
    }

    public Integer getCodiceOsservatorio() {

	return codiceOsservatorio;
    }

    public void setCodiceOsservatorio(Integer codiceOsservatorio) {

	this.codiceOsservatorio = codiceOsservatorio;
    }

    public IAttivitaTipologie getTipologiaAttivita() {

	return tipologiaAttivita;
    }

    public void setTipologiaAttivita(IAttivitaTipologie tipologiaAttivita) {

	this.tipologiaAttivita = tipologiaAttivita;
    }

    public List<AttivitaDyn2DatiSnapshot> getDyn2DatiSnapshots() {

	if (this.dyn2DatiSnapshots == null) {
	    this.dyn2DatiSnapshots = new ArrayList<AttivitaDyn2DatiSnapshot>();
	}
	return dyn2DatiSnapshots;
    }

    public List<AttivitaDyn2ModelliSnapshot> getDyn2ModelliSnapshots() {

	if (this.dyn2ModelliSnapshots == null) {
	    this.dyn2ModelliSnapshots = new ArrayList<AttivitaDyn2ModelliSnapshot>();
	}
	return dyn2ModelliSnapshots;
    }
}
