package it.gruppoinit.pal.gp.core.domain.web.servizijson;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

public class StatoEAvvisoDaRestHelper {

    private boolean avviso;
    private String statoAutorizzazione;
    private String causaleSospensione;

    public boolean isAvviso() {

	return avviso;
    }

    public void setAvviso(boolean avviso) {

	this.avviso = avviso;
    }

    public String getStatoAutorizzazione() {

	return statoAutorizzazione;
    }

    public void setStatoAutorizzazione(String statoAutorizzazione) {

	this.statoAutorizzazione = statoAutorizzazione;
    }

    public String getCausaleSospensione() {

	return causaleSospensione;
    }

    public void setCausaleSospensione(String causaleSospensione) {

	this.causaleSospensione = causaleSospensione;
    }

    public static StatoEAvvisoDaRestHelper fromAutorizzazioniRestHelper(AutorizzazioniRestHelper arh) {

	StatoEAvvisoDaRestHelper ret = new StatoEAvvisoDaRestHelper();
	boolean attiva = BooleanUtils.isTrue(arh.getFlagAttiva());
	String defaultStato = "Attiva";
	if (!attiva) {
	    defaultStato = "Cessata";
	}
	ret.setStatoAutorizzazione(defaultStato);
	if (StringUtils.isNotBlank(arh.getStatoAutorizzazione())) {
	    ret.setStatoAutorizzazione(arh.getStatoAutorizzazione());
	    if (StringUtils.isNotBlank(arh.getStatowarning())) {
		ret.setStatoAutorizzazione(arh.getStatowarning());
		ret.setAvviso(true);
	    }
	    ret.setCausaleSospensione(arh.getCausaleSospensione());
	}
	return ret;
    }
}
