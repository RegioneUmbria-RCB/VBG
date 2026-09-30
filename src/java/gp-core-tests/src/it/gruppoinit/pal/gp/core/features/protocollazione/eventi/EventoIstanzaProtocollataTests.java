package it.gruppoinit.pal.gp.core.features.protocollazione.eventi;

import org.junit.Test;

public class EventoIstanzaProtocollataTests {

    @Test(expected = IllegalArgumentException.class)
    public void costruttore_codiceIstanza_nonPuoEssereNull() {

	new EventoIstanzaProtocollata(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getDocumenti_nonPuoRestituireNull() {

	new EventoIstanzaProtocollata(1, null);
    }
}
