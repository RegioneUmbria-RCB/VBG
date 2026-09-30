package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;

import java.util.Date;

public class AutorizzazioniConcessioniDatiGenerali {

    private Integer idAutorizzazione;
    private Autorizzazioni autorizzazioniByFkAutconcAutatt;
    private Autorizzazioni AutorizzazioniByFkAutconcAutcoll;
    private Concessionitipi concessionitipi;
    private String stagionaleda;
    private String stagionalea;
    private Date datascadenza;

    public Autorizzazioni getAutorizzazioniByFkAutconcAutatt() {

	return autorizzazioniByFkAutconcAutatt;
    }

    public void setAutorizzazioniByFkAutconcAutatt(Autorizzazioni autorizzazioniByFkAutconcAutatt) {

	this.autorizzazioniByFkAutconcAutatt = autorizzazioniByFkAutconcAutatt;
    }

    public Autorizzazioni getAutorizzazioniByFkAutconcAutcoll() {

	return AutorizzazioniByFkAutconcAutcoll;
    }

    public void setAutorizzazioniByFkAutconcAutcoll(Autorizzazioni autorizzazioniByFkAutconcAutcoll) {

	AutorizzazioniByFkAutconcAutcoll = autorizzazioniByFkAutconcAutcoll;
    }

    public Integer getIdAutorizzazione() {

	return idAutorizzazione;
    }

    public void setIdAutorizzazione(Integer idAutorizzazione) {

	this.idAutorizzazione = idAutorizzazione;
    }

    public Concessionitipi getConcessionitipi() {

	return concessionitipi;
    }

    public void setConcessionitipi(Concessionitipi concessionitipi) {

	this.concessionitipi = concessionitipi;
    }

    public String getStagionaleda() {

	return stagionaleda;
    }

    public void setStagionaleda(String stagionaleda) {

	this.stagionaleda = stagionaleda;
    }

    public String getStagionalea() {

	return stagionalea;
    }

    public void setStagionalea(String stagionalea) {

	this.stagionalea = stagionalea;
    }

    public Date getDatascadenza() {

	return datascadenza;
    }

    public void setDatascadenza(Date datascadenza) {

	this.datascadenza = datascadenza;
    }

    public String getStagionaledaTransient() {

	String answer = null;
	if (this.stagionaleda != null && this.stagionaleda.length() == 4) {
	    answer = this.stagionaleda.substring(0, 2) + "/" + this.stagionaleda.substring(2, 4);
	}
	if (this.stagionaleda != null && this.stagionaleda.length() == 8) {
	    answer = this.stagionaleda.substring(0, 2) + "/" + this.stagionaleda.substring(2, 4) + "/" + this.stagionaleda.substring(4, 8);
	}
	return answer;
    }

    public void setStagionaledaTransient(String stagionaledaTransient) {

	if (stagionaledaTransient != null) {
	    this.setStagionaleda(stagionaledaTransient.replace("/", ""));
	} else {
	    this.stagionaleda = null;
	}
    }

    /**
     * Proprietà utilizzata per visualizzare il periodo nella forma gg/MM/yyyy oppure gg/MM
     * 
     */
    public String getStagionaleaTransient() {

	String answer = null;
	if (this.stagionalea != null && this.stagionalea.length() == 4) {
	    answer = this.stagionalea.substring(0, 2) + "/" + this.stagionalea.substring(2, 4);
	}
	if (this.stagionalea != null && this.stagionalea.length() == 8) {
	    answer = this.stagionalea.substring(0, 2) + "/" + this.stagionalea.substring(2, 4) + "/" + this.stagionalea.substring(4, 8);
	}
	return answer;
    }

    public void setStagionaleaTransient(String stagionaleaTransient) {

	if (stagionaleaTransient != null) {
	    this.setStagionalea(stagionaleaTransient.replace("/", ""));
	} else {
	    this.stagionalea = null;
	}
    }
}
