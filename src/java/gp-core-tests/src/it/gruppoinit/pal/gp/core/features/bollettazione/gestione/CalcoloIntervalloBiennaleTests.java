package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloBiennale;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.CalcoloIntervalloStrategy;

import org.junit.Test;

public class CalcoloIntervalloBiennaleTests {

    @Test(expected = NotImplementedException.class)
    public void costruttore_sollevaNotImplementedException() {

	CalcoloIntervalloStrategy calcolo = new CalcoloIntervalloBiennale();
    }
}
