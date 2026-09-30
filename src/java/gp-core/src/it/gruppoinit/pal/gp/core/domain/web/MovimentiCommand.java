package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;

import java.util.Map;

public class MovimentiCommand extends BaseCommand {

    public static final int NEW_SCADENZA = 7;
    private Movimenti entity;
    private Boolean flagRegistraOnere;
    private Tipicausalioneri tipicausalioneri;
    private Boolean inserimentoVeloce;
    private Statiistanza statiistanza;
    private CommissioniedilizieR commissioniedilizieR;
    private Boolean salvaEdEsci;
    private Letteretipo letteretipo;
    private DocumentiHelper documentiHelper;
    private Boolean flgZipLogico;

    public MovimentiCommand() {

	super();
	this.entity = new Movimenti();
	this.tipicausalioneri = new Tipicausalioneri();
	this.inserimentoVeloce = Boolean.FALSE;
	this.statiistanza = new Statiistanza();
	this.commissioniedilizieR = new CommissioniedilizieR();
	this.salvaEdEsci = Boolean.FALSE;
	this.letteretipo = new Letteretipo();
    }

    public Movimenti getEntity() {

	return entity;
    }

    public void setEntity(Movimenti entity) {

	this.entity = entity;
    }

    public Boolean getFlagRegistraOnere() {

	return flagRegistraOnere;
    }

    public void setFlagRegistraOnere(Boolean flagRegistraOnere) {

	this.flagRegistraOnere = flagRegistraOnere;
    }

    public Tipicausalioneri getTipicausalioneri() {

	return tipicausalioneri;
    }

    public void setTipicausalioneri(Tipicausalioneri tipicausalioneri) {

	this.tipicausalioneri = tipicausalioneri;
    }

    public Boolean getInserimentoVeloce() {

	return inserimentoVeloce;
    }

    public void setInserimentoVeloce(Boolean inserimentoVeloce) {

	this.inserimentoVeloce = inserimentoVeloce;
    }

    public Statiistanza getStatiistanza() {

	return statiistanza;
    }

    public void setStatiistanza(Statiistanza statiistanza) {

	this.statiistanza = statiistanza;
    }

    public CommissioniedilizieR getCommissioniedilizieR() {

	return commissioniedilizieR;
    }

    public void setCommissioniedilizieR(CommissioniedilizieR commissioniedilizieR) {

	this.commissioniedilizieR = commissioniedilizieR;
    }

    public Boolean getSalvaEdEsci() {

	return salvaEdEsci;
    }

    public void setSalvaEdEsci(Boolean salvaEdEsci) {

	this.salvaEdEsci = salvaEdEsci;
    }

    public Letteretipo getLetteretipo() {

	return letteretipo;
    }

    public void setLetteretipo(Letteretipo letteretipo) {

	this.letteretipo = letteretipo;
    }

    public DocumentiHelper getDocumentiHelper() {

	return documentiHelper;
    }

    public void setDocumentiHelper(DocumentiHelper documentiHelper) {

	this.documentiHelper = documentiHelper;
    }

    public Boolean getFlgZipLogico() {

	return flgZipLogico;
    }

    public void setFlgZipLogico(Boolean flgZipLogico) {

	this.flgZipLogico = flgZipLogico;
    }

    @Override
    public Map<String, Integer> getDisplayConstants() {

	Map<String, Integer> map = super.getDisplayConstants();
	map.put("NEW_SCADENZA", NEW_SCADENZA);
	return map;
    }
}
