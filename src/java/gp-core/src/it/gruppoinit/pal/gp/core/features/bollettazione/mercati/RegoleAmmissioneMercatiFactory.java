package it.gruppoinit.pal.gp.core.features.bollettazione.mercati;

import java.util.ArrayList;
import java.util.List;

import it.gruppoinit.pal.gp.core.features.bollettazione.IRegolaAmmissione;

public class RegoleAmmissioneMercatiFactory {

    List<IRegolaAmmissione> regole = new ArrayList<IRegolaAmmissione>(0);

    public RegoleAmmissioneMercatiFactory(RegoleAmmissioneMercatiParams params) {

	this.regole.add(new RegolaAmmissioneFormulaAttiva(params.getDataInizio(), params.getDataFine(), params.getFormule()));
	this.regole.add(new RegolaAmmissioneContoAttivo(params.getDataInizio(), params.getDataFine(), params.getConti()));
    }

    public RegoleAmmissioneMercati getRegoleAmmissione() {

	return new RegoleAmmissioneMercati(regole);
    }
}
