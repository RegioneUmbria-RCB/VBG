package it.gruppoinit.pal.gp.core.features.bollettazione.mercati;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import it.gruppoinit.pal.gp.core.domain.MercatiContabilitaTributi;
import it.gruppoinit.pal.gp.core.domain.MercatiFormuleCalcolo;

public class RegoleAmmissioneMercatiParams {

    private Date dataInizio;
    private Date dataFine;
    private List<MercatiFormuleCalcolo> formule = new ArrayList<MercatiFormuleCalcolo>(0);
    private List<MercatiContabilitaTributi> conti = new ArrayList<MercatiContabilitaTributi>(0);

    public RegoleAmmissioneMercatiParams(Date dataInizio, Date dataFine, List<MercatiFormuleCalcolo> formule) {

	this.dataInizio = dataInizio;
	this.dataFine = dataFine;
	this.formule = formule;
	if (this.formule != null) {
	    for (MercatiFormuleCalcolo formula : formule) {
		Set<MercatiContabilitaTributi> lista = formula.getMercatiContabilitaTributis();
		if (lista.size() > 0) {
		    this.conti.addAll(formula.getMercatiContabilitaTributis());
		}
	    }
	}
    }

    public Date getDataInizio() {

	return dataInizio;
    }

    public Date getDataFine() {

	return dataFine;
    }

    public List<MercatiFormuleCalcolo> getFormule() {

	return formule;
    }

    public List<MercatiContabilitaTributi> getConti() {

	return conti;
    }
}
