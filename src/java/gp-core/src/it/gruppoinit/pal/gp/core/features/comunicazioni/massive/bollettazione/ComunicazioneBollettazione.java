package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.bollettazione;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web.DettaglioLetteraComunicazione;

public class ComunicazioneBollettazione {

    private Integer idMassiveTestata;
    private Integer idBollettazione;
    private String descrizioneBollettazione;
    private Date dataComunicazione;
    private String descrizioneComunicazione;
    private String sceltaMailAnagrafe;
    private boolean escludiDestinatariSenzaMail;
    private boolean soloPosizioniNonPagate;
    private boolean allegaAvvisoPagamento;
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

    public Integer getIdBollettazione() {

	return idBollettazione;
    }

    public void setIdBollettazione(Integer idBollettazione) {

	this.idBollettazione = idBollettazione;
    }

    public String getDescrizioneBollettazione() {

	return descrizioneBollettazione;
    }

    public void setDescrizioneBollettazione(String descrizioneBollettazione) {

	this.descrizioneBollettazione = descrizioneBollettazione;
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

    public boolean isEscludiDestinatariSenzaMail() {

	return escludiDestinatariSenzaMail;
    }

    public void setEscludiDestinatariSenzaMail(boolean escludiDestinatariSenzaMail) {

	this.escludiDestinatariSenzaMail = escludiDestinatariSenzaMail;
    }

    public String getSceltaMailAnagrafe() {

	return sceltaMailAnagrafe;
    }

    public void setSceltaMailAnagrafe(String sceltaMailAnagrafe) {

	this.sceltaMailAnagrafe = sceltaMailAnagrafe;
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

    public void setSoloPosizioniNonPagate(boolean soloPosizioniNonPagate) {

	this.soloPosizioniNonPagate = soloPosizioniNonPagate;
    }

    public boolean isSoloPosizioniNonPagate() {

	return soloPosizioniNonPagate;
    }

    public List<AllegatoComunicazione> getAllegatiFissi() {

	return allegatiFissi;
    }

    public void setAllegatiFissi(List<AllegatoComunicazione> allegatiFissi) {

	if (allegatiFissi == null) {
	    this.allegatiFissi = new ArrayList<AllegatoComunicazione>();
	    return;
	}
	this.allegatiFissi = allegatiFissi;
    }

    public List<DettaglioLetteraComunicazione> getLettereTipo() {

	return lettereTipo;
    }

    public void setLettereTipo(List<DettaglioLetteraComunicazione> lettereTipo) {

	if (lettereTipo == null) {
	    this.lettereTipo = new ArrayList<DettaglioLetteraComunicazione>();
	    return;
	}
	this.lettereTipo = lettereTipo;
    }

    public List<MassiveDettaglio> getRighe() {

	return righe;
    }

    public void setRighe(List<MassiveDettaglio> righe) {

	if (righe == null) {
	    this.righe = new ArrayList<MassiveDettaglio>();
	    return;
	}
	this.righe = righe;
    }

    public boolean isAllegaAvvisoPagamento() {

	return allegaAvvisoPagamento;
    }

    public void setAllegaAvvisoPagamento(boolean allegaAvvisoPagamento) {

	this.allegaAvvisoPagamento = allegaAvvisoPagamento;
    }

    public boolean isConvertiInPDF() {

	return convertiInPDF;
    }

    public void setConvertiInPDF(boolean convertiInPDF) {

	this.convertiInPDF = convertiInPDF;
    }

    public List<String> getFirmatari() {

	return firmatari;
    }

    public void setFirmatari(List<String> firmatari) {

	this.firmatari = firmatari;
    }
}
