package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;

import java.io.Serializable;
import java.util.Comparator;

public class StpInventarioprocedimentiComparator implements Comparator<ChiaveValoreBean<Inventarioprocedimenti, Boolean>>, Serializable {

    private static final long serialVersionUID = -8998298096222832561L;

    @Override
    public int compare(ChiaveValoreBean<Inventarioprocedimenti, Boolean> o1, ChiaveValoreBean<Inventarioprocedimenti, Boolean> o2) {

	if (o1 == null && o2 == null) {
	    return 0;
	}
	if (o1 != null && o2 == null) {
	    return -1;
	}
	if (o1 == null && o2 != null) {
	    return 1;
	}
	Inventarioprocedimenti endo1 = o1.getChiave();
	Inventarioprocedimenti endo2 = o2.getChiave();
	Tipiendo tipoendo1 = endo1.getTipoendo();
	Tipiendo tipoendo2 = endo2.getTipoendo();
	if (tipoendo1 == null && tipoendo2 == null) {
	    return 0;
	}
	if (tipoendo1 != null && tipoendo2 == null) {
	    return -1;
	}
	if (tipoendo1 == null && tipoendo2 != null) {
	    return 1;
	}
	Tipifamiglieendo tipifamiglieendo1 = tipoendo1.getTipifamiglieendo();
	Tipifamiglieendo tipifamiglieendo2 = tipoendo2.getTipifamiglieendo();
	if (tipifamiglieendo1 == null && tipifamiglieendo2 == null) {
	    return 0;
	}
	if (tipifamiglieendo1 != null && tipifamiglieendo2 == null) {
	    return -1;
	}
	if (tipifamiglieendo1 == null && tipifamiglieendo2 != null) {
	    return 1;
	}
	String descrizione1 = tipifamiglieendo1.getTipo();
	String descrizione2 = tipifamiglieendo2.getTipo();
	return descrizione1.compareTo(descrizione2);
    }
}
