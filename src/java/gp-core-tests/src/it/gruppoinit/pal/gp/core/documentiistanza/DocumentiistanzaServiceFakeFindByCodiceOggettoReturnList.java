package it.gruppoinit.pal.gp.core.documentiistanza;

import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.ArrayList;
import java.util.List;

public class DocumentiistanzaServiceFakeFindByCodiceOggettoReturnList extends DocumentiistanzaServiceFake {

    public List<Documentiistanza> findByCodiceOggetto(Integer codiceOggetto) {

	List<Documentiistanza> result = new ArrayList<Documentiistanza>();
	Documentiistanza di = new Documentiistanza();
	Software s = new Software();
	s.setCodice("CO");
	Istanze i = new Istanze();
	i.setSoftware(s);
	di.setIstanza(i);
	result.add(di);
	return result;
    }
}
