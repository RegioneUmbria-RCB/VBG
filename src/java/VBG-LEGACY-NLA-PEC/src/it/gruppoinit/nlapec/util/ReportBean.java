package it.gruppoinit.nlapec.util;

import java.util.ArrayList;
import java.util.List;

public class ReportBean {

    private List<ReportDettaglioBean> dettaglio;
    private String error;
    private String warn;
    private String descrizioneEnte;
    private String idComuneAlias;
    private String idComune;

    public ReportBean() {

	this.idComuneAlias = "";
	this.descrizioneEnte = "";
	this.idComune = "";
	this.error = null;
	this.dettaglio = new ArrayList<ReportDettaglioBean>();
    }

    public ReportBean(String idComuneAlias, String descrizioneEnte) {

	this.idComuneAlias = idComuneAlias;
	this.descrizioneEnte = descrizioneEnte;
	this.idComune = "";
	this.error = null;
	this.dettaglio = new ArrayList<ReportDettaglioBean>();
    }

    public List<ReportDettaglioBean> getDettaglio() {

	return dettaglio;
    }

    public void setDettaglio(List<ReportDettaglioBean> dettaglio) {

	this.dettaglio = dettaglio;
    }

    public void addDettaglio(ReportDettaglioBean dettaglio) {

	if (this.dettaglio == null) {
	    this.dettaglio = new ArrayList<ReportDettaglioBean>();
	}
	this.dettaglio.add(dettaglio);
    }

    public String getError() {

	return error;
    }

    public void setError(String error) {

	this.error = error;
    }

    public String getDescrizioneEnte() {

	return descrizioneEnte;
    }

    public void setDescrizioneEnte(String descrizioneEnte) {

	this.descrizioneEnte = descrizioneEnte;
    }

    public String getIdComuneAlias() {

	return idComuneAlias;
    }

    public void setIdComuneAlias(String idComuneAlias) {

	this.idComuneAlias = idComuneAlias;
    }

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public String getWarn() {

	return warn;
    }

    public void setWarn(String warn) {

	this.warn = warn;
    }
}
