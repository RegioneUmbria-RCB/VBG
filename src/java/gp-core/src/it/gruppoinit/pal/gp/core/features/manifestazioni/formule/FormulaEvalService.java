package it.gruppoinit.pal.gp.core.features.manifestazioni.formule;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormulaEvalService {

    private static final Logger log = LoggerFactory.getLogger(FormulaEvalService.class);
    private final ScriptEngine jsEngine;

    public FormulaEvalService() {

	log.debug("FormulaEvalService: Inizializzo jsEngine");
	this.jsEngine = new ScriptEngineManager().getEngineByExtension("js");
    }

    public BigDecimal importoFormula(String testoFormulaDaValutare) {

	try {
	    String daValutare = "var result = " + testoFormulaDaValutare.replace(',', '.');
	    log.debug("formula da valutare: {}", daValutare);
	    jsEngine.eval(daValutare);
	    Object o = jsEngine.get("result");
	    log.debug("result: {}", o);
	    Double result = null;
	    if (o instanceof Integer) {
		result = Double.valueOf((Integer) o);
	    } else {
		result = (Double) o;
	    }
	    return BigDecimal.valueOf(result).setScale(2, RoundingMode.HALF_EVEN);
	} catch (Exception e) {
	    log.error("importoFormula: {}", e.getMessage(), e);
	    throw new RuntimeException("La formula utilizzata " + testoFormulaDaValutare + " per il calcolo non è sintatticamente corretta!", e);
	}
    }
}
