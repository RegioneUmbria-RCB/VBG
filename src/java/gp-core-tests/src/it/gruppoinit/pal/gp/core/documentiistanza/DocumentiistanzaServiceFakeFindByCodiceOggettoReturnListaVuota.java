package it.gruppoinit.pal.gp.core.documentiistanza;

import it.gruppoinit.pal.gp.core.domain.Documentiistanza;

import java.util.ArrayList;
import java.util.List;

public class DocumentiistanzaServiceFakeFindByCodiceOggettoReturnListaVuota extends DocumentiistanzaServiceFake {

    public List<Documentiistanza> findByCodiceOggetto(Integer codiceOggetto) {

	List<Documentiistanza> result = new ArrayList<Documentiistanza>();
	return result;
    }
}
