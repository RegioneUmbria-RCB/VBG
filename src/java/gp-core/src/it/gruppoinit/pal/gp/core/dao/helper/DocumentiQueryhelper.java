package it.gruppoinit.pal.gp.core.dao.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocumentiQueryhelper {

    private Map<String, List<String>> valoriColonne;

    public Map<String, List<String>> getValoriColonne() {

	if (this.valoriColonne == null) {
	    this.valoriColonne = new HashMap<String, List<String>>();
	}
	return valoriColonne;
    }

    public void setValoriColonne(Map<String, List<String>> valoriColonne) {

	this.valoriColonne = valoriColonne;
    }
}
