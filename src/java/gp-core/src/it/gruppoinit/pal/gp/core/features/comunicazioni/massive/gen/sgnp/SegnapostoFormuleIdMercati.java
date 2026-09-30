package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp;

import org.apache.cxf.common.util.StringUtils;

public class SegnapostoFormuleIdMercati implements SegnapostoFormuleGen {

    public static final String SEGNAPOSTO = "[mercato]";
    public static final String DESCRIZIONE = "Sostituisce il valore con il valore del mercato";
    private int idmercato;

    public SegnapostoFormuleIdMercati(int idmercato) {

	this.idmercato = idmercato;
    }

    @Override
    public String sostituisci(String formula) {

	if (StringUtils.isEmpty(formula)) {
	    return null;
	}
	if (!formula.contains(SEGNAPOSTO)) {
	    return formula;
	}
	return formula.replace(SEGNAPOSTO, idmercato + "");
    }
}
