package it.gruppoinit.pal.gp.core.domain.web;

import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

public class ResponsabiliCommand extends BaseCommand {

    private Responsabili entity;
    private ScadenzarioOperatoreChiaveValore scadOperatore;
    private Set<Responsabilisoftware> responsabilisoftwareList;
    private Set<Responsabilicomuni> responsabilicomuniList;
    private List<Clpermmenu> clpermmenuList;
    private Set<Clpermmenu> nuoviPermessi;
    private Tipimovimento tipimovimentoSca;
    private Tipimovimento tipimovimentoAvv;
    private String valoreOrdinamentoData;
    private int ngiorniDaOggi;

    public ResponsabiliCommand() {

	super();
	this.entity = new Responsabili();
	this.tipimovimentoSca = new Tipimovimento();
	this.tipimovimentoAvv = new Tipimovimento();
	this.scadOperatore = new ScadenzarioOperatoreChiaveValore();
    }

    public Responsabili getEntity() {

	return entity;
    }

    public void setEntity(Responsabili entity) {

	this.entity = entity;
    }

    public Set<Responsabilisoftware> getResponsabilisoftwareList() {

	return responsabilisoftwareList;
    }

    public void setResponsabilisoftwareList(Set<Responsabilisoftware> responsabilisoftwareList) {

	this.responsabilisoftwareList = responsabilisoftwareList;
    }

    public Set<Responsabilicomuni> getResponsabilicomuniList() {

	return responsabilicomuniList;
    }

    public void setResponsabilicomuniList(Set<Responsabilicomuni> responsabilicomuniList) {

	this.responsabilicomuniList = responsabilicomuniList;
    }

    public List<Clpermmenu> getClpermmenuList() {

	return clpermmenuList;
    }

    public void setClpermmenuList(List<Clpermmenu> clpermmenuList) {

	this.clpermmenuList = clpermmenuList;
    }

    public Set<Clpermmenu> getNuoviPermessi() {

	return nuoviPermessi;
    }

    public void setNuoviPermessi(Set<Clpermmenu> nuoviPermessi) {

	this.nuoviPermessi = nuoviPermessi;
    }

    public Tipimovimento getTipimovimentoSca() {

	return tipimovimentoSca;
    }

    public void setTipimovimentoSca(Tipimovimento tipimovimentoSca) {

	this.tipimovimentoSca = tipimovimentoSca;
    }

    public Tipimovimento getTipimovimentoAvv() {

	return tipimovimentoAvv;
    }

    public void setTipimovimentoAvv(Tipimovimento tipimovimentoAvv) {

	this.tipimovimentoAvv = tipimovimentoAvv;
    }

    public ScadenzarioOperatoreChiaveValore getScadOperatore() {

	return scadOperatore;
    }

    public void setScadOperatore(ScadenzarioOperatoreChiaveValore scadOperatore) {

	this.scadOperatore = scadOperatore;
    }

    public String getValoreOrdinamentoData() {

	return valoreOrdinamentoData;
    }

    public void setValoreOrdinamentoData(String valoreOrdinamentoData) {

	this.valoreOrdinamentoData = valoreOrdinamentoData;
    }

    public int getNgiorniDaOggi() {

	return ngiorniDaOggi;
    }

    public void setNgiorniDaOggi(int ngiorniDaOggi) {

	this.ngiorniDaOggi = ngiorniDaOggi;
    }
}
