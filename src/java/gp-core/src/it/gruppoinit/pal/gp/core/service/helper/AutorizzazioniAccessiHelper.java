package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;

import java.util.ArrayList;
import java.util.List;

public class AutorizzazioniAccessiHelper {

    private Istanze istanza;
    private Autorizzazioni autorizzazione;
    private List<AutorizzazioniAccessiOperazioniHelper> autorizzazioniAccessiOperazioniHelper;
    private int numeroAccessiRimanenti;
    private Integer numeroTransitiConsentiti;
    private boolean proroga;
    private boolean preavviso;
    private boolean rinnovo;

    public Istanze getIstanza() {

	return istanza;
    }

    public void setIstanza(Istanze istanza) {

	this.istanza = istanza;
    }

    public Autorizzazioni getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(Autorizzazioni autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public List<AutorizzazioniAccessiOperazioniHelper> getAutorizzazioniAccessiOperazioniHelper() {

	if (this.autorizzazioniAccessiOperazioniHelper == null) {
	    this.autorizzazioniAccessiOperazioniHelper = new ArrayList<AutorizzazioniAccessiOperazioniHelper>();
	}
	return autorizzazioniAccessiOperazioniHelper;
    }

    public void setAutorizzazioniAccessiOperazioniHelper(List<AutorizzazioniAccessiOperazioniHelper> autorizzazioniAccessiOperazioniHelper) {

	this.autorizzazioniAccessiOperazioniHelper = autorizzazioniAccessiOperazioniHelper;
    }

    public int getNumeroAccessiRimanenti() {

	return numeroAccessiRimanenti;
    }

    public void setNumeroAccessiRimanenti(int numeroAccessiRimanenti) {

	this.numeroAccessiRimanenti = numeroAccessiRimanenti;
    }

    public boolean isProroga() {

	return proroga;
    }

    public void setProroga(boolean proroga) {

	this.proroga = proroga;
    }

    public boolean isPreavviso() {

	return preavviso;
    }

    public void setPreavviso(boolean preavviso) {

	this.preavviso = preavviso;
    }

    public boolean isRinnovo() {

	return rinnovo;
    }

    public void setRinnovo(boolean rinnovo) {

	this.rinnovo = rinnovo;
    }

    public Integer getNumeroTransitiConsentiti() {

	return numeroTransitiConsentiti;
    }

    public void setNumeroTransitiConsentiti(Integer numeroTransitiConsentiti) {

	this.numeroTransitiConsentiti = numeroTransitiConsentiti;
    }
}
