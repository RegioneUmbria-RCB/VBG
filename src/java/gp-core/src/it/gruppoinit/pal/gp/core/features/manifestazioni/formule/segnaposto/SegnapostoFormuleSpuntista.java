package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import org.apache.cxf.common.util.StringUtils;

public class SegnapostoFormuleSpuntista implements SegnapostoFormuleMercati {

    public static final String SEGNAPOSTO = "[SPUNTISTA]";
    public static final String DESCRIZIONE = "Prende il valore 1 se lo spuntista è presente, altrimenti 0";
    private boolean isPresente;

    public SegnapostoFormuleSpuntista(boolean isPresente) {

	super();
	this.isPresente = isPresente;
    }

    @Override
    public String sostituisci(String formula) {

	if (StringUtils.isEmpty(formula)) {
	    return null;
	}
	if (!formula.contains(SEGNAPOSTO)) {
	    return formula;
	}
	Integer valore = (this.isPresente) ? 1 : 0;
	return formula.replace(SEGNAPOSTO, valore.toString());
    }
}