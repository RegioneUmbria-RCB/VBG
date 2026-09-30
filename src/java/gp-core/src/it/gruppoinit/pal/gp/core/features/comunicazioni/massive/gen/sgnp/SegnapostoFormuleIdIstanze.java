package it.gruppoinit.pal.gp.core.features.comunicazioni.massive.gen.sgnp;

import org.apache.cxf.common.util.StringUtils;

public class SegnapostoFormuleIdIstanze implements SegnapostoFormuleGen {

    public static final String SEGNAPOSTO = "[istanza]";
    public static final String DESCRIZIONE = "Sostituisce il valore con il valore dell' istanza";
    private int idistanza;

    public SegnapostoFormuleIdIstanze(int idistanza) {

	this.idistanza = idistanza;
    }

    @Override
    public String sostituisci(String formula) {

	if (StringUtils.isEmpty(formula)) {
	    return null;
	}
	if (!formula.contains(SEGNAPOSTO)) {
	    return formula;
	}
	return formula.replace(SEGNAPOSTO, idistanza + "");
    }
}
