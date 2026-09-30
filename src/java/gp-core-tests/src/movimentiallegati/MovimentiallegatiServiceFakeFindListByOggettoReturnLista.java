package movimentiallegati;

import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.ArrayList;
import java.util.List;

public class MovimentiallegatiServiceFakeFindListByOggettoReturnLista extends MovimentiallegatiServiceFake {

    public List<Movimentiallegati> findListByOggetto(Integer codice) {

	List<Movimentiallegati> result = new ArrayList<Movimentiallegati>();
	Movimentiallegati ma1 = new Movimentiallegati();
	Software s1 = new Software();
	s1.setCodice("CO");
	Istanze i1 = new Istanze();
	i1.setSoftware(s1);
	Movimenti m1 = new Movimenti();
	m1.setIstanza(i1);
	ma1.setMovimento(m1);
	result.add(ma1);
	return result;
    }
}
