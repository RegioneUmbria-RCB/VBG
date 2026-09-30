package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.ProtocolloMezzi;
import it.gruppoinit.pal.gp.core.domain.ProtocolloModalitainvio;

public class ProtocolloSoggettoCommand {

    private Anagrafe anagrafe;
    private Amministrazioni amministrazioni;
    private Boolean perConoscenza;
    private Boolean perConoscenzaAmm;
    private ProtocolloMezzi mezzo;
    private ProtocolloModalitainvio modInvio;

    public ProtocolloSoggettoCommand() {

	this.anagrafe = new Anagrafe();
	this.amministrazioni = new Amministrazioni();
	this.mezzo = new ProtocolloMezzi();
	this.modInvio = new ProtocolloModalitainvio();
    }

    public Anagrafe getAnagrafe() {

	return anagrafe;
    }

    public void setAnagrafe(Anagrafe anagrafe) {

	this.anagrafe = anagrafe;
    }

    public Amministrazioni getAmministrazioni() {

	return amministrazioni;
    }

    public void setAmministrazioni(Amministrazioni amministrazioni) {

	this.amministrazioni = amministrazioni;
    }

    public Boolean getPerConoscenza() {

	return perConoscenza;
    }

    public void setPerConoscenza(Boolean perConoscenza) {

	this.perConoscenza = perConoscenza;
    }

    public Boolean getPerConoscenzaAmm() {

	return perConoscenzaAmm;
    }

    public void setPerConoscenzaAmm(Boolean perConoscenzaAmm) {

	this.perConoscenzaAmm = perConoscenzaAmm;
    }

    public ProtocolloMezzi getMezzo() {

	return mezzo;
    }

    public void setMezzo(ProtocolloMezzi mezzo) {

	this.mezzo = mezzo;
    }

    public ProtocolloModalitainvio getModInvio() {

	return modInvio;
    }

    public void setModInvio(ProtocolloModalitainvio modInvio) {

	this.modInvio = modInvio;
    }
}
