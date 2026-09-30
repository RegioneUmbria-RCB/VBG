package it.gruppoinit.pal.gp.core.features.autorizzazioni.numerazione.custom;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.web.context.ContextLoader;

public class ElencoImplementazioniCustom {

    public Map<String, String> get() {

	Map<String, NumerazioneCustomService> implementazioni = ContextLoader.getCurrentWebApplicationContext()
		.getBeansOfType(NumerazioneCustomService.class);
	if (implementazioni.isEmpty()) {
	    return new TreeMap<String, String>();
	}
	Map<String, String> retVal = new TreeMap<String, String>();
	for (Map.Entry<String, NumerazioneCustomService> implementazione : implementazioni.entrySet()) {
	    retVal.put(implementazione.getKey(), implementazione.getValue().getDescrizione());
	    //retVal.put(implementazione.getKey(), "Elemento 1");
	}
	return retVal;
    }
}
