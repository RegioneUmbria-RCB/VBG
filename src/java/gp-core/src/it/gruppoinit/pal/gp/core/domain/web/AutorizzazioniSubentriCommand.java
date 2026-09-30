package it.gruppoinit.pal.gp.core.domain.web;

import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DehorsMqIstanzeHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.subentri.EsitoElaborazioneSubentri;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

public class AutorizzazioniSubentriCommand {

    private AutorizzazioniFilter filter;
    private Istanze istanzaDiSubentro;
    private Set<AutorizzazioniHelper> listAutDaRicerca = new LinkedHashSet<AutorizzazioniHelper>(0);
    private Set<AutorizzazioniHelper> listAutDaSubentrare = new LinkedHashSet<AutorizzazioniHelper>(0);
    private Concessionicausali causaleAcquisizione;
    private Concessionicausali causaleCessazione;
    private Date dataCessazione;
    private DehorsMqIstanze dehorsMqIstanzePrecedente;
    private DehorsMqIstanze dehorsMqIstanzeNuova;
    private DehorsMqIstanzeHelper dehorsMqIstanzeHelper;
    private String subentriComportamentoOneri;
    private EsitoElaborazioneSubentri esito;

    public AutorizzazioniSubentriCommand() {

	this.filter = new AutorizzazioniFilter();
	this.causaleAcquisizione = new Concessionicausali();
	this.causaleCessazione = new Concessionicausali();
	this.dehorsMqIstanzePrecedente = new DehorsMqIstanze();
	this.dehorsMqIstanzeNuova = new DehorsMqIstanze();
	this.dehorsMqIstanzeHelper = new DehorsMqIstanzeHelper();
    }

    public Concessionicausali getCausaleAcquisizione() {

	return causaleAcquisizione;
    }

    public void setCausaleAcquisizione(Concessionicausali causaleAcquisizione) {

	this.causaleAcquisizione = causaleAcquisizione;
    }

    public Concessionicausali getCausaleCessazione() {

	return causaleCessazione;
    }

    public void setCausaleCessazione(Concessionicausali causaleCessazione) {

	this.causaleCessazione = causaleCessazione;
    }

    public void setIstanzaDiSubentro(Istanze istanzaDiSubentro) {

	this.istanzaDiSubentro = istanzaDiSubentro;
    }

    public Istanze getIstanzaDiSubentro() {

	return istanzaDiSubentro;
    }

    public void setListAutDaRicerca(Set<AutorizzazioniHelper> listAutDaRicerca) {

	this.listAutDaRicerca = listAutDaRicerca;
    }

    public Set<AutorizzazioniHelper> getListAutDaRicerca() {

	return listAutDaRicerca;
    }

    public void setListAutDaSubentrare(Set<AutorizzazioniHelper> listAutDaSubentrare) {

	this.listAutDaSubentrare = listAutDaSubentrare;
    }

    public Set<AutorizzazioniHelper> getListAutDaSubentrare() {

	return listAutDaSubentrare;
    }

    public void setFilter(AutorizzazioniFilter filter) {

	this.filter = filter;
    }

    public AutorizzazioniFilter getFilter() {

	return filter;
    }

    public Date getDataCessazione() {

	return dataCessazione;
    }

    public void setDataCessazione(Date dataCessazione) {

	this.dataCessazione = dataCessazione;
    }

    public DehorsMqIstanze getDehorsMqIstanzePrecendete() {

	return dehorsMqIstanzePrecedente;
    }

    public void setDehorsMqIstanzePrecendete(DehorsMqIstanze dehorsMqIstanzePrecedente) {

	this.dehorsMqIstanzePrecedente = dehorsMqIstanzePrecedente;
    }

    public DehorsMqIstanze getDehorsMqIstanzeNuova() {

	return dehorsMqIstanzeNuova;
    }

    public void setDehorsMqIstanzeNuova(DehorsMqIstanze dehorsMqIstanzeNuova) {

	this.dehorsMqIstanzeNuova = dehorsMqIstanzeNuova;
    }

    public DehorsMqIstanzeHelper getDehorsMqIstanzeHelper() {

	return dehorsMqIstanzeHelper;
    }

    public void setDehorsMqIstanzeHelper(DehorsMqIstanzeHelper dehorsMqIstanzeHelper) {

	this.dehorsMqIstanzeHelper = dehorsMqIstanzeHelper;
    }

    public String getSubentriComportamentoOneri() {

	return subentriComportamentoOneri;
    }

    public void setSubentriComportamentoOneri(String subentriComportamentoOneri) {

	this.subentriComportamentoOneri = subentriComportamentoOneri;
    }

    public EsitoElaborazioneSubentri getEsito() {

	return esito;
    }

    public void setEsito(EsitoElaborazioneSubentri esito) {

	this.esito = esito;
    }

    public boolean isPresenteEsito() {

	if (esito == null) {
	    return false;
	}
	return esito.isErroreOWarning();
    }

    public boolean isPossoSubentrare() {

	if (esito == null) {
	    return false;
	}
	return !esito.isErrore();
    }
}
