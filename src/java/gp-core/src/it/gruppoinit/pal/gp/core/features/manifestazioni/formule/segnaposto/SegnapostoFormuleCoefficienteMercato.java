package it.gruppoinit.pal.gp.core.features.manifestazioni.formule.segnaposto;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;

public class SegnapostoFormuleCoefficienteMercato implements SegnapostoFormuleMercati {

    public static final String SEGNAPOSTO = "[COEFFICIENTE_MERCATO]";
    public static final String DESCRIZIONE = "Riporta l'eventuale coefficiente di mercato configurato";
    private BigDecimal coefficiente;

    public SegnapostoFormuleCoefficienteMercato(BigDecimal coefficiente) {

	super();
	this.coefficiente = coefficiente;
    }

    @Override
    public String sostituisci(String formula) {

	if (StringUtils.isEmpty(formula)) {
	    return null;
	}
	if (!formula.contains(SEGNAPOSTO)) {
	    return formula;
	}
	if (coefficiente == null) {
	    throw new InvalidConfigurationException(
		    "Non è stato configurato il coefficiente merceologico sulla presenza ed il calcolo del posteggio lo richiede");
	}
	return formula.replace(SEGNAPOSTO, coefficiente.toString());
    }
}
