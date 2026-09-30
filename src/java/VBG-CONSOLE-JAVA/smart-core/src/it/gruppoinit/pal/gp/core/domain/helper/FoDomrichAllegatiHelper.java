package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.FoDomrichAllegati;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

public class FoDomrichAllegatiHelper {

    private List<FoDomrichAllegati> comunicazioniFormali = new ArrayList<FoDomrichAllegati>();
    private Map<String, List<FoDomrichAllegati>> documentiRichiesti = new TreeMap<String, List<FoDomrichAllegati>>();

    private FoDomrichAllegatiHelper() {

	super();
    }

    public FoDomrichAllegatiHelper(List<FoDomrichAllegati> documentiDellaRichiesta) {

	this();
	setAllegati(documentiDellaRichiesta);
    }

    private void setAllegati(List<FoDomrichAllegati> allegati) {

	if (allegati.size() > 0) {
	    for (FoDomrichAllegati a : allegati) {
		if (StringUtils.isBlank(a.getModulo())) {
		    comunicazioniFormali.add(a);
		} else {
		    String modulo = a.getModulo();
		    List<FoDomrichAllegati> alls = documentiRichiesti.get(modulo);
		    if (alls == null) {
			alls = new ArrayList<FoDomrichAllegati>();
		    }
		    alls.add(a);
		    documentiRichiesti.put(modulo, alls);
		}
	    }
	}
    }

    public List<FoDomrichAllegati> getComunicazioniFormali() {

	return comunicazioniFormali;
    }

    public Map<String, List<FoDomrichAllegati>> getDocumentiRichiesti() {

	return documentiRichiesti;
    }
}
