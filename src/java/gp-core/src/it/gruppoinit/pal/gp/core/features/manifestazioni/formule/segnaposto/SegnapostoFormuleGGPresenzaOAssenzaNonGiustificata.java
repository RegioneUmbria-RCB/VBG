package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.apache.commons.lang.BooleanUtils;
import org.apache.cxf.common.util.StringUtils;

public class SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata implements SegnapostoFormuleMercati {

    public static final String SEGNAPOSTO = "[GG_PRES_OR_NON_GIUS]";
    public static final String DESCRIZIONE = "Giorni in cui il concessionario è presente o assente ingiustificato";
    private static final String GIORNATE_DI_RIFERIMENTO = "1";
    private static final String GIORNATA_NULLA = "0";
    private String provenienza;
    private Boolean assenzaGiustificata;

    /**
     * Sostituisce la formula in caso di segnaposto {@link #SEGNAPOSTO} se la provenienza è da PRESENZE o nel caso di
     * ASSENZE se è una assenza giustificata
     * 
     * @param rigaDettaglioCalcoloMercati
     */
    public SegnapostoFormuleGGPresenzaOAssenzaNonGiustificata(String provenienza, Boolean assenzaGiustificata) {

	super();
	this.provenienza = provenienza;
	this.assenzaGiustificata = assenzaGiustificata;
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
	    } else if (SegnapostoFormuleBuilderRequest.PROVENIENZA.ASSENZE.name().equalsIgnoreCase(provenienza)
		    && BooleanUtils.isFalse(assenzaGiustificata)) {
		formula = formula.replace(SEGNAPOSTO, GIORNATE_DI_RIFERIMENTO);
	    } else {
		formula = formula.replace(SEGNAPOSTO, GIORNATA_NULLA);
	    }
	}
	return formula;
    }
}
