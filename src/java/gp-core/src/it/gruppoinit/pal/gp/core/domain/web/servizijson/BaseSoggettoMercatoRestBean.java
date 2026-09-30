package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniCsiRestBean;

public class BaseSoggettoMercatoRestBean {

    private AutorizzazioniCsiRestBean autorizzazioniCsi;
    private AutorizzazioneRestBean autorizzazione;
    private AutorizzazioneRestBean autorizzazionePrecedente;
    private AutorizzazioneRestBean autorizzazioneOriginaria;
    private AnagraferestBean coadiuvante;
    private AnagraferestBean altriDati;
    private List<AutorizzazioneRestBean> autorizzazioneCollegata;
    private List<String> avvisi;

    public AutorizzazioniCsiRestBean getAutorizzazioniCsi() {

	return autorizzazioniCsi;
    }

    public void setAutorizzazioniCsi(AutorizzazioniCsiRestBean autorizzazioniCsi) {

	this.autorizzazioniCsi = autorizzazioniCsi;
    }

    public AnagraferestBean getAltriDati() {

	return altriDati;
    }

    public void setAltriDati(AnagraferestBean altriDati) {

	this.altriDati = altriDati;
    }

    public AnagraferestBean getCoadiuvante() {

	return coadiuvante;
    }

    public void setCoadiuvante(AnagraferestBean coadiuvante) {

	this.coadiuvante = coadiuvante;
    }

    public AutorizzazioneRestBean getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(AutorizzazioneRestBean autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public AutorizzazioneRestBean getAutorizzazioneOriginaria() {

	return autorizzazioneOriginaria;
    }

    public void setAutorizzazioneOriginaria(AutorizzazioneRestBean autorizzazioneOriginaria) {

	this.autorizzazioneOriginaria = autorizzazioneOriginaria;
    }

    public AutorizzazioneRestBean getAutorizzazionePrecedente() {

	return autorizzazionePrecedente;
    }

    public void setAutorizzazionePrecedente(AutorizzazioneRestBean autorizzazionePrecedente) {

	this.autorizzazionePrecedente = autorizzazionePrecedente;
    }

    public List<AutorizzazioneRestBean> getAutorizzazioneCollegata() {

	return autorizzazioneCollegata;
    }

    public void setAutorizzazioneCollegata(List<AutorizzazioneRestBean> autorizzazioneCollegata) {

	this.autorizzazioneCollegata = autorizzazioneCollegata;
    }

    public List<String> getAvvisi() {

	if (avvisi == null) {
	    avvisi = new ArrayList<String>();
	}
	return avvisi;
    }

    public void setAvvisi(List<String> avvisi) {

	this.avvisi = avvisi;
    }
}
