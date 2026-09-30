package it.gruppoinit.pal.gp.core.features.esportazioni.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Parametriesportazione;

public class EsportazioniPentahoEsportazioneModel {

    private Integer id;
    private String idComune;
    private String descrizione;
    private String tipoContesto;
    private String trasformazione;
    private String software;
    private List<EsportazioniPentahoParametri> parametri;

    public Integer getId() {

	return id;
    }

    public void setId(Integer id) {

	this.id = id;
    }

    public String getIdComune() {

	return idComune;
    }

    public void setIdComune(String idComune) {

	this.idComune = idComune;
    }

    public String getDescrizione() {

	return descrizione;
    }

    public void setDescrizione(String descrizione) {

	this.descrizione = descrizione;
    }

    public String getTipoContesto() {

	return tipoContesto;
    }

    public void setTipoContesto(String tipoContesto) {

	this.tipoContesto = tipoContesto;
    }

    public String getTrasformazione() {

	return trasformazione;
    }

    public void setTrasformazione(String trasformazione) {

	this.trasformazione = trasformazione;
    }

    public String getSoftware() {

	return software;
    }

    public void setSoftware(String software) {

	this.software = software;
    }

    public void setParametri(List<EsportazioniPentahoParametri> parametri) {

	this.parametri = parametri;
    }

    public List<EsportazioniPentahoParametri> getParametri() {

	if (parametri == null) {
	    parametri = new ArrayList<EsportazioniPentahoParametri>();
	}
	return parametri;
    }

    public static EsportazioniPentahoEsportazioneModel fromEsportazione(Esportazioni esportazioni) {

	EsportazioniPentahoEsportazioneModel ret = new EsportazioniPentahoEsportazioneModel();
	ret.setId(esportazioni.getId().getCodice());
	ret.setIdComune(esportazioni.getId().getIdcomune());
	ret.setDescrizione(esportazioni.getDescrizione());
	ret.setSoftware(esportazioni.getSoftware().getCodice());
	ret.setTipoContesto(esportazioni.getTipicontestoesportazione().getCodice());
	ret.setTrasformazione(esportazioni.getTrasformazione());
	Set<Parametriesportazione> parametriesportaziones = esportazioni.getParametriesportaziones();
	for (Parametriesportazione pe : parametriesportaziones) {
	    EsportazioniPentahoParametri pem = new EsportazioniPentahoParametri();
	    pem.setParametro(pe.getParametro());
	    pe.setDescrizione(pe.getDescrizione());
	    ret.getParametri().add(pem);
	}
	return ret;
    }
}
