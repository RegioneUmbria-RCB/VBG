package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioniCreaPerSoggettiAdapter.TIPORESTITUITO;

public class VerticalizzazioniNodoPagamentiTest {

    @Test
    public void parametroCreaPerSoggettiCollegatiNonSpecificatoTornaFalse() {

	VerticalizzazioneNodoPagamentiServiceImpl s = getVerticalizzazioni(TIPORESTITUITO.NULL);
	Assert.assertFalse(s.creaPerSoggettiCollegati());
    }

    @Test
    public void parametroCreaPerSoggettiCollegatiConValoreNTornaFalse() {

	VerticalizzazioneNodoPagamentiServiceImpl s = getVerticalizzazioni(TIPORESTITUITO.N);
	Assert.assertFalse(s.creaPerSoggettiCollegati());
    }

    @Test
    public void parametroCreaPerSoggettiCollegatiConValoreSTornaTrue() {

	VerticalizzazioneNodoPagamentiServiceImpl s = getVerticalizzazioni(TIPORESTITUITO.S);
	Assert.assertTrue(s.creaPerSoggettiCollegati());
    }

    @Test
    public void parametroCreaPerSoggettiCollegatiConValoreVuotoSTornaFalse() {

	VerticalizzazioneNodoPagamentiServiceImpl s = getVerticalizzazioni(TIPORESTITUITO.VUOTO);
	Assert.assertFalse(s.creaPerSoggettiCollegati());
    }

    VerticalizzazioneNodoPagamentiServiceImpl getVerticalizzazioni(TIPORESTITUITO t) {

	VerticalizzazioneNodoPagamentiServiceImpl ret = new VerticalizzazioneNodoPagamentiServiceImpl(new VerticalizzazioniCreaPerSoggettiAdapter(t),
		"E256");
	return ret;
    }
}
