package it.gruppoinit.pal.gp.core.features.istanze.eventi.messaggi;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioEventoOneriCopiatiDaPraticaTests {

    @Test(expected = IllegalArgumentException.class)
    public void costruttore_sollevaEccezione_seNumeroIstanzaOrigineNull() {

	List<CausaleImportoOnerePerMessaggio> causali = new ArrayList<CausaleImportoOnerePerMessaggio>();
	causali.add(new CausaleImportoOnerePerMessaggio("test", BigDecimal.valueOf(0)));
	new MessaggioEventoOneriCopiatiDaPratica(null, "123", causali);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costruttore_sollevaEccezione_seNumeroIstanzaDestinazioneNull() {

	List<CausaleImportoOnerePerMessaggio> causali = new ArrayList<CausaleImportoOnerePerMessaggio>();
	causali.add(new CausaleImportoOnerePerMessaggio("test", BigDecimal.valueOf(0)));
	new MessaggioEventoOneriCopiatiDaPratica("123", null, causali);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costruttore_sollevaEccezione_seOneriCopiatiNull() {

	new MessaggioEventoOneriCopiatiDaPratica("123", "456", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void costruttore_sollevaEccezione_seOneriCopiatiVuoto() {

	new MessaggioEventoOneriCopiatiDaPratica("123", "456", new ArrayList<CausaleImportoOnerePerMessaggio>(0));
    }

    @Test
    public void getTestoMessaggio_restituisce_messaggioAtteso() {

	String numIstSrc = "123";
	String numIstDst = "456";
	List<CausaleImportoOnerePerMessaggio> causali = new ArrayList<CausaleImportoOnerePerMessaggio>();
	causali.add(new CausaleImportoOnerePerMessaggio("Causale 1", BigDecimal.valueOf(1234.0)));
	causali.add(new CausaleImportoOnerePerMessaggio("Causale 2", BigDecimal.valueOf(4567.0)));
	String expected = "Oneri non pagati copiati dalla pratica 123 alla pratica 456:" +
		"\r\n- Causale 1: € 1.234,00" +
		"\r\n- Causale 2: € 4.567,00";
	MessaggioDiSistema msg = new MessaggioEventoOneriCopiatiDaPratica(numIstSrc, numIstDst, causali);
	String actual = msg.getTestoMessaggio();
	assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }
}
