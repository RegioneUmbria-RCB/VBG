package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.apache.cxf.common.util.StringUtils;

public class SegnapostoFormuleGGPres implements SegnapostoFormuleMercati {

    public static final String SEGNAPOSTO = "[GG_PRES]";
    public static final String DESCRIZIONE = "Giorni in cui il concessionario è presente";
    private static final String GIORNATE_DI_RIFERIMENTO = "1";
    private static final String GIORNATA_NULLA = "0";
    private String provenienza;

    /**
     * Sostituisce la formula in caso di segnaposto {@link #SEGNAPOSTO} se la provenienza è da PRESENZE
     * 
     * @param rigaDettaglioCalcoloMercati
     */
    public SegnapostoFormuleGGPres(String provenienza) {

	super();
	this.provenienza = provenienza;
    }

    @Override
    public String sostituisci(String formula) {

	if (StringUtils.isEmpty(formula)) {
	    return null;
	}
	if (formula.contains(SEGNAPOSTO)) {
	    if (SegnapostoFormuleBuilderRequest.PROVENIENZA.PRESENZE.name().equalsIgnoreCase(provenienza)
		    || SegnapostoFormuleBuilderRequest.PROVENIENZA.PROIEZIONE.name().equalsIgnoreCase(provenienza)
		    || SegnapostoFormuleBuilderRequest.PROVENIENZA.PROIEZIONE_SUBENTRI.name().equalsIgnoreCase(provenienza)) {
		formula = formula.replace(SEGNAPOSTO, GIORNATE_DI_RIFERIMENTO);
	    } else {
		formula = formula.replace(SEGNAPOSTO, GIORNATA_NULLA);
	    }
	}
	return formula;
    }
}
