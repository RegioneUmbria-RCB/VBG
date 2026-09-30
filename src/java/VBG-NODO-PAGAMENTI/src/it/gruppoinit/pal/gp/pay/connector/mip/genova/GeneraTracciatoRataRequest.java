package it.gruppoinit.pal.gp.pay.connector.mip.genova;

import java.util.Date;

public class GeneraTracciatoRataRequest {

    private Integer annoDebito;
    private String idDebito;
    private Integer numeroRata;
    private String idRata;
    private Date dataScadenza;
    private String descrizioneRata;
    private FlagPagamento flagPagamento;
    private String versione;
    private Integer importoDaPagare;

    public Integer getAnnoDebito() {

	return annoDebito;
    }

    public void setAnnoDebito(Integer annoDebito) {

	this.annoDebito = annoDebito;
    }

    public String getIdDebito() {

	return idDebito;
    }

    public void setIdDebito(String idDebito) {

	this.idDebito = idDebito;
    }

    public Integer getNumeroRata() {

	return numeroRata;
    }

    public void setNumeroRata(Integer numeroRata) {

	this.numeroRata = numeroRata;
    }

    public String getIdRata() {

	return idRata;
    }

    public void setIdRata(String idRata) {

	this.idRata = idRata;
    }

    public Date getDataScadenza() {

	return dataScadenza;
    }

    public void setDataScadenza(Date dataScadenza) {

	this.dataScadenza = dataScadenza;
    }

    public String getDescrizioneRata() {

	return descrizioneRata;
    }

    public void setDescrizioneRata(String descrizioneRata) {

	this.descrizioneRata = descrizioneRata;
    }

    public FlagPagamento getFlagPagamento() {

	return flagPagamento;
    }

    public void setFlagPagamento(FlagPagamento flagPagamento) {

	this.flagPagamento = flagPagamento;
    }

    public String getVersione() {

	return versione;
    }

    public void setVersione(String versione) {

	this.versione = versione;
    }

    public Integer getImportoDaPagare() {

	return importoDaPagare;
    }

    public void setImportoDaPagare(Integer importoDaPagare) {

	this.importoDaPagare = importoDaPagare;
    }
}
