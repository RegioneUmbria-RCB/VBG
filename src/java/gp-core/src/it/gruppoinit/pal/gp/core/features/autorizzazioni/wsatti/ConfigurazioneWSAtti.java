package it.gruppoinit.pal.gp.core.features.autorizzazioni.wsatti;

import java.util.HashSet;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

public class ConfigurazioneWSAtti {

    private String classifica;
    private String codiceDirigente;
    private String codiceProponente;
    private String trattamento;
    private String ruolo;
    private Set<String> tipiMovimento = new HashSet<String>();
    private Tipimovimento movimentoAttoCompletato;

    public String getClassifica() {

	return classifica;
    }

    public void setClassifica(String classifica) {

	this.classifica = classifica;
    }

    public String getCodiceDirigente() {

	return codiceDirigente;
    }

    public void setCodiceDirigente(String codiceDirigente) {

	this.codiceDirigente = codiceDirigente;
    }

    public String getCodiceProponente() {

	return codiceProponente;
    }

    public void setCodiceProponente(String codiceProponente) {

	this.codiceProponente = codiceProponente;
    }

    public String getTrattamento() {

	return trattamento;
    }

    public void setTrattamento(String trattamento) {

	this.trattamento = trattamento;
    }

    public String getRuolo() {

	return ruolo;
    }

    public void setRuolo(String ruolo) {

	this.ruolo = ruolo;
    }

    public Set<String> getTipiMovimento() {

	if (this.tipiMovimento == null) {
	    this.tipiMovimento = new HashSet<String>();
	}
	return tipiMovimento;
    }

    public void setTipiMovimento(Set<String> tipiMovimento) {

	this.tipiMovimento = tipiMovimento;
    }

    public Tipimovimento getMovimentoAttoCompletato() {

	return movimentoAttoCompletato;
    }

    public void setMovimentoAttoCompletato(Tipimovimento movimentoAttoCompletato) {

	this.movimentoAttoCompletato = movimentoAttoCompletato;
    }
}
