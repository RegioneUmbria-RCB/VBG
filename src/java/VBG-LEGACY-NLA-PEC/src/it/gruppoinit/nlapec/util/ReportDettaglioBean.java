package it.gruppoinit.nlapec.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReportDettaglioBean {

    private String software;
    private Map<String, String> listaTipologiePECProcessate;
    private int numeroMessaggiTotali;
    private int numeroMimeMessage;
    private int numeroMessageConErrori;
    private List<String> dettaglio;
    private String error;

    public ReportDettaglioBean() {

	this.numeroMessaggiTotali = 0;
	this.numeroMimeMessage = 0;
	this.numeroMessageConErrori = 0;
	this.error = null;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public Map<String, String> getListaTipologiePECProcessate() {

	return listaTipologiePECProcessate;
    }

    public void setListaTipologiePECProcessate(Map<String, String> listaTipologiePECProcessate) {

	this.listaTipologiePECProcessate = listaTipologiePECProcessate;
    }

    public int getNumeroMessaggiTotali() {

	return numeroMessaggiTotali;
    }

    public void setNumeroMessaggiTotali(int numeroMessaggiTotali) {

	this.numeroMessaggiTotali = numeroMessaggiTotali;
    }

    public List<String> getDettaglio() {

	return dettaglio;
    }

    public void setDettaglio(List<String> dettaglio) {

	this.dettaglio = dettaglio;
    }

    public void addDettaglio(String dettaglio) {

	if (this.dettaglio == null) {
	    this.dettaglio = new ArrayList<String>();
	}
	this.dettaglio.add(dettaglio);
    }

    public int getNumeroMimeMessage() {

	return numeroMimeMessage;
    }

    public void setNumeroMimeMessage(int numeroMimeMessage) {

	this.numeroMimeMessage = numeroMimeMessage;
    }

    public int getNumeroMessageConErrori() {

	return numeroMessageConErrori;
    }

    public void setNumeroMessageConErrori(int numeroMessageConErrori) {

	this.numeroMessageConErrori = numeroMessageConErrori;
    }

    public String getError() {

	return error;
    }

    public void setError(String error) {

	this.error = error;
    }
}
