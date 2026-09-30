package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp.ana;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;

public class SegnapostoNomeCognome implements SegnapostoFormuleAnaGen {

    private static final String segnaposto = "[DESTINATARIO]";

    @Override
    public String sostituisci(String formula, Anagrafe ana) {

	if (formula == null || ana.getNominativo() == null) {
	    return formula;
	}
	return formula.replace(segnaposto, ana.getNome() == null ? ana.getNominativo() : ana.getNome() + " " + ana.getNominativo()); //Lascio questa per ora
    }
}
