package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.apache.cxf.common.util.StringUtils;

public class SegnapostoFormuleGG implements SegnapostoFormuleMercati {

    public static final String SEGNAPOSTO = "[GG]";
    public static final String DESCRIZIONE = "Giorni in cui la concessione ha diritto al posteggio";
    private static final Integer GIORNATE_DI_RIFERIMENTO = 1;

    public SegnapostoFormuleGG() {

	super();
    }

    @Override
    public String sostituisci(String formula) {

	if (StringUtils.isEmpty(formula)) {
	    return null;
	}
	if (formula.contains(SEGNAPOSTO)) {
	    formula = formula.replace(SEGNAPOSTO, GIORNATE_DI_RIFERIMENTO.toString());
	}
	return formula;
    }
}
