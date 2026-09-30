package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettaglioLetteraComunicazione;

public class ComunicazioneCommissione {

    private Integer idMassiveTestata;
    private Integer idCommissione;
    private String descrizioneCommissione;
    private Date dataComunicazione;
    private String descrizioneComunicazione;
    private String sceltaMailAnagrafe;
    private boolean escludiDestinatariSenzaMail;
    private boolean convertiInPDF;
    private String oggettoProtocollo;
    private String senderAccount;
    private String templateMailTipo;
    private List<String> firmatari = new ArrayList<String>();
    private List<AllegatoComunicazione> allegatiFissi = new ArrayList<AllegatoComunicazione>();
    private List<DettaglioLetteraComunicazione> lettereTipo = new ArrayList<DettaglioLetteraComunicazione>();
    private List<MassiveDettaglio> righe = new ArrayList<MassiveDettaglio>();

    public Integer getIdMassiveTestata() {

	return idMassiveTestata;
    }

    public void setIdMassiveTestata(Integer idMassiveTestata) {

	this.idMassiveTestata = idMassiveTestata;
    }

    public Integer getIdCommissione() {

	return idCommissione;
    }

    public void setIdCommissione(Integer idCommissione) {

	this.idCommissione = idCommissione;
    }

    public String getDescrizioneCommissione() {

	return descrizioneCommissione;
    }

    public void setDescrizioneCommissione(String descrizioneCommissione) {

	this.descrizioneCommissione = descrizioneCommissione;
    }

    public Date getDataComunicazione() {

	return dataComunicazione;
    }

    public void setDataComunicazione(Date dataComunicazione) {

	this.dataComunicazione = dataComunicazione;
    }

    public String getDescrizioneComunicazione() {

	return descrizioneComunicazione;
    }

    public void setDescrizioneComunicazione(String descrizioneComunicazione) {

	this.descrizioneComunicazione = descrizioneComunicazione;
    }

    public String getSceltaMailAnagrafe() {

	return sceltaMailAnagrafe;
    }

    public void setSceltaMailAnagrafe(String sceltaMailAnagrafe) {

	this.sceltaMailAnagrafe = sceltaMailAnagrafe;
    }

    public boolean isEscludiDestinatariSenzaMail() {

	return escludiDestinatariSenzaMail;
    }

    public void setEscludiDestinatariSenzaMail(boolean escludiDestinatariSenzaMail) {

	this.escludiDestinatariSenzaMail = escludiDestinatariSenzaMail;
    }

    public boolean isConvertiInPDF() {

	return convertiInPDF;
    }

    public void setConvertiInPDF(boolean convertiInPDF) {

	this.convertiInPDF = convertiInPDF;
    }

    public String getOggettoProtocollo() {

	return oggettoProtocollo;
    }

    public void setOggettoProtocollo(String oggettoProtocollo) {

	this.oggettoProtocollo = oggettoProtocollo;
    }

    public String getSenderAccount() {

	return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {

	this.senderAccount = senderAccount;
    }

    public String getTemplateMailTipo() {

	return templateMailTipo;
    }

    public void setTemplateMailTipo(String templateMailTipo) {

	this.templateMailTipo = templateMailTipo;
    }

    public List<String> getFirmatari() {

	return firmatari;
    }

    public void setFirmatari(List<String> firmatari) {

	this.firmatari = firmatari;
    }

    public List<AllegatoComunicazione> getAllegatiFissi() {

	return allegatiFissi;
    }

    public void setAllegatiFissi(List<AllegatoComunicazione> allegatiFissi) {

	this.allegatiFissi = allegatiFissi;
    }

    public List<DettaglioLetteraComunicazione> getLettereTipo() {

	return lettereTipo;
    }

    public void setLettereTipo(List<DettaglioLetteraComunicazione> lettereTipo) {

	this.lettereTipo = lettereTipo;
    }

    public List<MassiveDettaglio> getRighe() {

	return righe;
    }

    public void setRighe(List<MassiveDettaglio> righe) {

	this.righe = righe;
    }
}
