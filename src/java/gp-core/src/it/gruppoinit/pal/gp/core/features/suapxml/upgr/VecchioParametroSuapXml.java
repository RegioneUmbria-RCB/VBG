package it.gruppoinit.pal.gp.core.features.suapxml.upgr;

import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;

public class VecchioParametroSuapXml {

    private String idComune;
    private String software;
    private String nomeParametro;
    private String valore;

    public String getIdComune() {

	return idComune;
    }

    public String getSoftware() {

	return software;
    }

    public String getNomeParametro() {

	return nomeParametro;
    }

    public String getValore() {

	return valore;
    }

    public static VecchioParametroSuapXml fromVerticalizzazioniparametri(Verticalizzazioniparametri parametro) {

	if (parametro == null || parametro.getId() == null || parametro.getVerticalizzazioniparametribase() == null
		|| parametro.getVerticalizzazioniparametribase().getId() == null) {
	    return new VecchioParametroSuapXml();
	}
	VecchioParametroSuapXml par = new VecchioParametroSuapXml();
	par.idComune = parametro.getId().getIdcomune();
	if (parametro.getSoftware() != null) {
	    par.software = parametro.getSoftware().getCodice();
	}
	par.nomeParametro = parametro.getVerticalizzazioniparametribase().getId().getParametro();
	par.valore = parametro.getValore();
	return par;
    }
}
