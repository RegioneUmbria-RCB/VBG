package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.MassiveDettaglio;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.commissioni.ComunicazioneCommissione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.AllegatoComunicazione;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.configurazione.SceltaTipoMailAnagrafeEnum;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class ComunicazioneCommissioneDetail {

    private Integer idTestataComunicazione;
    private Integer idCommissione;
    private String commissione;
    private Date data;
    private String comunicazione;
    private String escludiDestinatariSenzaMail;
    private String sceltaMailAnagrafe;
    private String sceltaMailAnagrafeCodice;
    private String convertiInPDF;
    private String oggettoProtocollo;
    private String accountInvioMail;
    private String templateMailTipo;
    private List<String> firmatari;
    private List<AllegatoComunicazione> allegatiFissi;
    private List<DettaglioLetteraComunicazione> lettereTipo;
    private List<RigaComunicazione> righe = new ArrayList<RigaComunicazione>();

    public Integer getIdCommissione() {

	return idCommissione;
    }

    public String getCommissione() {

	return commissione;
    }

    public Integer getIdTestataComunicazione() {

	return idTestataComunicazione;
    }

    public void setIdTestataComunicazione(Integer idTestataComunicazione) {

	this.idTestataComunicazione = idTestataComunicazione;
    }

    public Date getData() {

	return data;
    }

    public String getDataFormattata() {

	if (this.data == null)
	    return null;
	return Utilities.formatDate(this.data, false);
    }

    public String getComunicazione() {

	return comunicazione;
    }

    public String getEscludiDestinatariSenzaMail() {

	return escludiDestinatariSenzaMail;
    }

    public String getSceltaMailAnagrafe() {

	return sceltaMailAnagrafe;
    }

    public String getSceltaMailAnagrafeCodice() {

	return sceltaMailAnagrafeCodice;
    }

    public String getOggettoProtocollo() {

	return oggettoProtocollo;
    }

    public String getAccountInvioMail() {

	return accountInvioMail;
    }

    public String getTemplateMailTipo() {

	return templateMailTipo;
    }

    public List<AllegatoComunicazione> getAllegatiFissi() {

	return allegatiFissi;
    }

    public List<DettaglioLetteraComunicazione> getLettereTipo() {

	return lettereTipo;
    }

    public String getConvertiInPDF() {

	return convertiInPDF;
    }

    public List<String> getFirmatari() {

	return firmatari;
    }

    public List<RigaComunicazione> getRighe() {

	if (this.righe == null) {
	    this.righe = new ArrayList<RigaComunicazione>();
	}
	return this.righe;
    }

    public static ComunicazioneCommissioneDetail FromComunicazioneCommissione(ComunicazioneCommissione comunicazione) {

	if (comunicazione == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare il metodo statico FromComunicazioneBollettazione senza valorizzare il parametro comunicazione ");
	}
	ComunicazioneCommissioneDetail command = new ComunicazioneCommissioneDetail();
	command.idTestataComunicazione = comunicazione.getIdMassiveTestata();
	command.idCommissione = comunicazione.getIdCommissione();
	command.accountInvioMail = comunicazione.getSenderAccount();
	command.commissione = comunicazione.getDescrizioneCommissione();
	command.comunicazione = comunicazione.getDescrizioneComunicazione();
	command.data = comunicazione.getDataComunicazione();
	command.escludiDestinatariSenzaMail = comunicazione.isEscludiDestinatariSenzaMail() ? "SI" : "NO";
	command.sceltaMailAnagrafe = SceltaTipoMailAnagrafeEnum.valueOf(comunicazione.getSceltaMailAnagrafe()).getDescrizione();
	command.sceltaMailAnagrafeCodice = comunicazione.getSceltaMailAnagrafe();
	command.oggettoProtocollo = comunicazione.getOggettoProtocollo();
	command.convertiInPDF = comunicazione.isConvertiInPDF() ? "SI" : "NO";
	command.templateMailTipo = comunicazione.getTemplateMailTipo();
	command.allegatiFissi = comunicazione.getAllegatiFissi();
	command.lettereTipo = comunicazione.getLettereTipo();
	command.firmatari = comunicazione.getFirmatari();
	for (MassiveDettaglio riga : comunicazione.getRighe()) {
	    command.righe.add(RigaComunicazione.FromMassiveDettaglio(riga));
	}
	return command;
    }
}
