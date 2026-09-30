package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.apache.cxf.common.util.StringUtils;

public class SegnapostoFormuleBattitore implements SegnapostoFormuleMercati {

    public static final String SEGNAPOSTO = "[BATTITORE]";
    public static final String DESCRIZIONE = "Prende il valore 1 se il coefficente merceologico salvato nella presenza mercatipresenze_d.fk_codiceistat è di tipo Battitore (Regola comportamento_mercati.codice_istat_battitori)";
    private boolean isMerceologiaBattitore = false;

    public SegnapostoFormuleBattitore(boolean isMerceologiaBattitore) {

	this.isMerceologiaBattitore = isMerceologiaBattitore;
    }

    @Override
    public String sostituisci(String formula) {

	if (StringUtils.isEmpty(formula)) {
	    return null;
	}
	if (!formula.contains(SEGNAPOSTO)) {
	    return formula;
	}
	return formula.replace(SEGNAPOSTO, (this.isMerceologiaBattitore) ? "1" : "0");
    }
}
