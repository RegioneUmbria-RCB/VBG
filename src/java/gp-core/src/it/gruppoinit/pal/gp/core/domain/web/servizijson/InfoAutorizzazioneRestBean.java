package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import java.util.List;

import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelper;

public class InfoAutorizzazioneRestBean {

    private AutorizzazioneRestBean autorizzazione;
    private AnagraferestBean altriDati;
    private List<DocumentiRestBean> documenti;
    private PagamentiMercatoPosizDebRestHelper posizioneDebitoriaSpuntista;
    private List<AutorizzazioniFrontRestBean> autorizzazioniConcessioniColl;

    public AutorizzazioneRestBean getAutorizzazione() {

	return autorizzazione;
    }

    public void setAutorizzazione(AutorizzazioneRestBean autorizzazione) {

	this.autorizzazione = autorizzazione;
    }

    public AnagraferestBean getAltriDati() {

	return altriDati;
    }

    public void setAltriDati(AnagraferestBean altriDati) {

	this.altriDati = altriDati;
    }

    public List<DocumentiRestBean> getDocumenti() {

	return documenti;
    }

    public void setDocumenti(List<DocumentiRestBean> documenti) {

	this.documenti = documenti;
    }

    public PagamentiMercatoPosizDebRestHelper getPosizioneDebitoriaSpuntista() {

	return posizioneDebitoriaSpuntista;
    }

    public void setPosizioneDebitoriaSpuntista(PagamentiMercatoPosizDebRestHelper posizioneDebitoriaSpuntista) {

	this.posizioneDebitoriaSpuntista = posizioneDebitoriaSpuntista;
    }

    public List<AutorizzazioniFrontRestBean> getAutorizzazioniConcessioniColl() {

	return autorizzazioniConcessioniColl;
    }

    public void setAutorizzazioniConcessioniColl(List<AutorizzazioniFrontRestBean> autorizzazioniConcessioniColl) {

	this.autorizzazioniConcessioniColl = autorizzazioniConcessioniColl;
    }
}
