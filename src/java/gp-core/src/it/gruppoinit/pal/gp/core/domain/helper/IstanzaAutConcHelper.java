package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Istanze;

import java.io.Serializable;
import java.util.List;

public class IstanzaAutConcHelper implements Serializable {

    private static final long serialVersionUID = 2315273586586274184L;
    private Istanze istanza;
    private List<Autorizzazioni> autorizzazioni;
    private List<AutorizzazioniSubentri> autorizzazioniSubentri;
    private List<AutorizzazioniConcessioni> concessioni;
    private List<AutorizzazioniSubentri> concessioniSubentri;

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public List<Autorizzazioni> getAutorizzazioni() {

	return autorizzazioni;
    }

    public void setAutorizzazioni(List<Autorizzazioni> autorizzazioni) {

	this.autorizzazioni = autorizzazioni;
    }

    public List<AutorizzazioniSubentri> getAutorizzazioniSubentri() {

	return autorizzazioniSubentri;
    }

    public void setAutorizzazioniSubentri(List<AutorizzazioniSubentri> autorizzazioniSubentri) {

	this.autorizzazioniSubentri = autorizzazioniSubentri;
    }

    public List<AutorizzazioniConcessioni> getConcessioni() {

	return concessioni;
    }

    public void setConcessioni(List<AutorizzazioniConcessioni> concessioni) {

	this.concessioni = concessioni;
    }

    public List<AutorizzazioniSubentri> getConcessioniSubentri() {

	return concessioniSubentri;
    }

    public void setConcessioniSubentri(List<AutorizzazioniSubentri> concessioniSubentri) {

	this.concessioniSubentri = concessioniSubentri;
    }
}
