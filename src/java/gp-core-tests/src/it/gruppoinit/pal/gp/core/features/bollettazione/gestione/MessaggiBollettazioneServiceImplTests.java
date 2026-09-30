package it.gruppoinit.pal.gp.core.features.bollettazione.gestione;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioBollettazione;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioNuovaRigaRettifica;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioRettificaAnnullata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioRigaAggiuntaManualmente;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioRigaEliminata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioRigaRettificata;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.messaggi.MessaggioValidazioneRiga;

public class MessaggiBollettazioneServiceImplTests {

    private Date getDataDefault() {

	Calendar c = new GregorianCalendar(2020, 0, 1, 12, 34, 56);
	return c.getTime();
    }

    @Test
    public void getMessaggioRettificaAnnullata_restituisce_messaggio() {

	String autore = "Mario Rossi";
	String expected = "Rettifica annullata da Mario Rossi in data 01/01/2020 12:34:56";
	MessaggioBollettazione msg = new MessaggioRettificaAnnullata(autore);
	msg.sovrascriviDataLog(getDataDefault());
	String actual = msg.getTestoMessaggio();
	Assert.assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }

    @Test
    public void getMessaggioRigaEliminata_restituisce_messaggio() {

	String autore = "Mario Rossi";
	String expected = "Eliminata da Mario Rossi in data 01/01/2020 12:34:56";
	MessaggioBollettazione msg = new MessaggioRigaEliminata(autore);
	msg.sovrascriviDataLog(getDataDefault());
	String actual = msg.getTestoMessaggio();
	Assert.assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }

    @Test
    public void getMessaggioNuovaRigaRettifica_restituisce_messaggio() {

	String autore = "Mario Rossi";
	String descrizioneVecchiaRiga = "Descrizione vecchia riga";
	String expected = "Rettifica della riga \"Descrizione vecchia riga\" effettuata da Mario Rossi in data 01/01/2020 12:34:56";
	MessaggioBollettazione msg = new MessaggioNuovaRigaRettifica(autore, descrizioneVecchiaRiga);
	msg.sovrascriviDataLog(getDataDefault());
	String actual = msg.getTestoMessaggio();
	Assert.assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }

    @Test
    public void getMessaggioRigaRettificata_restituisce_messaggio() {

	String autore = "Mario Rossi";
	String expected = "Modificato da Mario Rossi il 01/01/2020 12:34:56 da €456,78 a €123,45";
	BigDecimal nuovoImporto = BigDecimal.valueOf(123.45);
	BigDecimal vecchioImporto = BigDecimal.valueOf(456.78);
	MessaggioBollettazione msg = new MessaggioRigaRettificata(autore, nuovoImporto, vecchioImporto);
	msg.sovrascriviDataLog(getDataDefault());
	String actual = msg.getTestoMessaggio();
	Assert.assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }

    @Test
    public void getMessaggioAggiungiRiga_restituisce_messaggio() {

	String autore = "Mario Rossi";
	String expected = "Riga inserita da Mario Rossi in data 01/01/2020 12:34:56";
	MessaggioBollettazione msg = new MessaggioRigaAggiuntaManualmente(autore);
	msg.sovrascriviDataLog(getDataDefault());
	String actual = msg.getTestoMessaggio();
	Assert.assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }

    @Test
    public void getMessaggioValidaRiga_imposta_validata_restituisce_messaggio() {

	String autore = "Mario Rossi";
	String expected = "Contrassegnata come valida da Mario Rossi il 01/01/2020 12:34:56";
	MessaggioBollettazione msg = new MessaggioValidazioneRiga(autore, true);
	msg.sovrascriviDataLog(getDataDefault());
	String actual = msg.getTestoMessaggio();
	Assert.assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }

    @Test
    public void getMessaggioValidaRiga_imposta_non_validata_restituisce_messaggio() {

	String autore = "Mario Rossi";
	String expected = "Contrassegnata non valida da Mario Rossi il 01/01/2020 12:34:56";
	MessaggioBollettazione msg = new MessaggioValidazioneRiga(autore, false);
	msg.sovrascriviDataLog(getDataDefault());
	String actual = msg.getTestoMessaggio();
	Assert.assertEquals("Deve restituire il messaggio atteso", expected, actual);
    }
}
