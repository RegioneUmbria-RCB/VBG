package it.gruppoinit.pal.gp.core.features.bollettazione.arrotondamento;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.web.context.ContextLoader;

public class ElencoArrotondamentiCustom {

    @SuppressWarnings("unchecked")
    public Map<String, String> get() {

	Map<String, ArrotondamentoService> implementazioni = ContextLoader.getCurrentWebApplicationContext()
		.getBeansOfType(ArrotondamentoService.class);
	if (implementazioni.isEmpty()) {
	    return new TreeMap<String, String>();
	}
	Map<String, String> retVal = new TreeMap<String, String>();
	for (Map.Entry<String, ArrotondamentoService> implementazione : implementazioni.entrySet()) {
	    retVal.put(implementazione.getValue().getNome(), implementazione.getValue().getDescrizione());
	}
	return retVal;
    }
}
