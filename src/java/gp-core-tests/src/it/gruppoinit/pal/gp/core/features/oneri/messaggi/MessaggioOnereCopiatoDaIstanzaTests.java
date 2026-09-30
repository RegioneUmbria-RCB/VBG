package it.gruppoinit.pal.gp.core.features.oneri.messaggi;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;

public class MessaggioOnereCopiatoDaIstanzaTests {

    @Test(expected = IllegalArgumentException.class)
    public void costruttore_sollevaEccezione_seNumeroIstanzaNonSpecificato() {

	Calendar c = new GregorianCalendar(2020, 0, 1, 12, 34, 56);
	MessaggioDiSistema msg = new MessaggioOnereCopiatoDaIstanza(null, c.getTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void costruttore_sollevaEccezione_seDataNonSpecificato() {

	MessaggioDiSistema msg = new MessaggioOnereCopiatoDaIstanza("test", null);
    }

    @Test
    public void getTestoMessaggio_restituisce_messaggio() {

	String numeroIstanza = "123/456";
	Calendar c = new GregorianCalendar(2020, 0, 1, 12, 34, 56);
	MessaggioDiSistema msg = new MessaggioOnereCopiatoDaIstanza(numeroIstanza, c.getTime());
	String expected = "Oneri copiati dalla pratica 123/456 in data 01/01/2020 12:34:56";
	String actual = msg.getTestoMessaggio();
	assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }
}
