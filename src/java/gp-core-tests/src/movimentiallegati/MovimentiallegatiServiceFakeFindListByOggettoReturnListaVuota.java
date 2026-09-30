package movimentiallegati;

import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;

import java.util.ArrayList;
import java.util.List;

public class MovimentiallegatiServiceFakeFindListByOggettoReturnListaVuota extends MovimentiallegatiServiceFake {

    public List<Movimentiallegati> findListByOggetto(Integer codice) {

	List<Movimentiallegati> result = new ArrayList<Movimentiallegati>();
	return result;
    }
}
