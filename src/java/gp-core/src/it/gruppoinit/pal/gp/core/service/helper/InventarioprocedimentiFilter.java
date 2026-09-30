package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;

import java.util.HashMap;
import java.util.Map;

public class InventarioprocedimentiFilter {

    private Inventarioprocedimenti inventarioprocedimenti;
    Map<String, String> ordinamentoMap = new HashMap<String, String>();

    public Inventarioprocedimenti getInventarioprocedimenti() {

	return inventarioprocedimenti;
    }

    public void setInventarioprocedimenti(Inventarioprocedimenti inventarioprocedimenti) {

	this.inventarioprocedimenti = inventarioprocedimenti;
    }

    public Map<String, String> getOrdinamentoMap() {

	return ordinamentoMap;
    }

    public void setOrdinamentoMap(Map<String, String> ordinamentoMap) {

	this.ordinamentoMap = ordinamentoMap;
    }
}
