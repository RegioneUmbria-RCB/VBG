package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

public class ComunicazioniBaseCommand {

    private String descrizione;
    private Date data;
    private Responsabili firmatario;
    private List<Responsabili> firmatari;
    private List<Integer> allegatiFissi = new ArrayList<Integer>();
    private Letteretipo letteratiposearch;
    private List<Letteretipo> allegaticompilabili;
    private ProtocollaParametriCommand protocollaParametriCommand;
    private ConfiguraParametriMailCommand configuraParametriMailCommand;
    private boolean convertiPDF;

    public ComunicazioniBaseCommand() {

	this.protocollaParametriCommand = new ProtocollaParametriCommand();
	this.configuraParametriMailCommand = new ConfiguraParametriMailCommand();
	this.letteratiposearch = new Letteretipo();
	this.setFirmatario(new Responsabili());
	this.allegaticompilabili = new ArrayList<Letteretipo>();
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public Date getData() {

	return data;
    }

    public void setData(Date data) {

	this.data = data;
    }

    public List<Responsabili> getFirmatari() {

	if (this.firmatari == null) {
	    this.firmatari = new ArrayList<Responsabili>();
	}
	return firmatari;
    }

    public void setFirmatari(List<Responsabili> firmatari) {

	this.firmatari = firmatari;
    }

    public List<Letteretipo> getAllegaticompilabili() {

	if (this.allegaticompilabili == null) {
	    this.allegaticompilabili = new ArrayList<Letteretipo>();
	}
	return allegaticompilabili;
    }

    public void setAllegaticompilabili(List<Letteretipo> allegaticompilabili) {

	this.allegaticompilabili = allegaticompilabili;
    }

    public ProtocollaParametriCommand getProtocollaParametriCommand() {

	return protocollaParametriCommand;
    }

    public void setProtocollaParametriCommand(ProtocollaParametriCommand protocollaParametriCommand) {

	this.protocollaParametriCommand = protocollaParametriCommand;
    }

    public ConfiguraParametriMailCommand getConfiguraParametriMailCommand() {

	return configuraParametriMailCommand;
    }

    public void setConfiguraParametriMailCommand(ConfiguraParametriMailCommand configuraParametriMailCommand) {

	this.configuraParametriMailCommand = configuraParametriMailCommand;
    }

    public boolean isConvertiPDF() {

	return convertiPDF;
    }

    public void setConvertiPDF(boolean convertiPDF) {

	this.convertiPDF = convertiPDF;
    }

    public List<Integer> getAllegatiFissi() {

	return allegatiFissi;
    }

    public void setAllegatiFissi(List<Integer> allegatiFissi) {

	this.allegatiFissi = allegatiFissi;
    }

    public Letteretipo getLetteratiposearch() {

	return letteratiposearch;
    }

    public void setLetteratiposearch(Letteretipo letteratiposearch) {

	this.letteratiposearch = letteratiposearch;
    }

    public Responsabili getFirmatario() {

	return firmatario;
    }

    public void setFirmatario(Responsabili firmatario) {

	this.firmatario = firmatario;
    }
}
