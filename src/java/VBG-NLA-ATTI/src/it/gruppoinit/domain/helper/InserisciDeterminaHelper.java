package it.gruppoinit.domain.helper;

import it.init.sigepro.rte.types.DocumentiType;

import java.util.ArrayList;
import java.util.List;

public class InserisciDeterminaHelper {

    private String trattamento;
    private String proponente;
    private String dirigente;
    private String classifica;
    private String utente;
    private String tipo;
    private String ruolo;
    private String oggetto;
    private List<DocumentiType> documenti = new ArrayList<DocumentiType>();
    private String token;

    public String getTrattamento() {

	return trattamento;
    }

    public void setTrattamento(String trattamento) {

	this.trattamento = trattamento;
    }

    public String getProponente() {

	return proponente;
    }

    public void setProponente(String proponente) {

	this.proponente = proponente;
    }

    public String getDirigente() {

	return dirigente;
    }

    public void setDirigente(String dirigente) {

	this.dirigente = dirigente;
    }

    public String getClassifica() {

	return classifica;
    }

    public void setClassifica(String classifica) {

	this.classifica = classifica;
    }

    public String getUtente() {

	return utente;
    }

    public void setUtente(String utente) {

	this.utente = utente;
    }

    public String getTipo() {

	return tipo;
    }

    public void setTipo(String tipo) {

	this.tipo = tipo;
    }

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }

    public List<DocumentiType> getDocumenti() {

	return documenti;
    }

    public void setDocumenti(List<DocumentiType> documenti) {

	this.documenti = documenti;
    }

    public String getToken() {

	return token;
    }

    public void setToken(String token) {

	this.token = token;
    }

    public String getOggetto() {

	return oggetto;
    }

    public void setOggetto(String oggetto) {

	this.oggetto = oggetto;
    }
}
