package it.gruppoinit.pal.gp.core.service.impl;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniServiceImpl;

public class AutorizzazioniServiceImplTests {

    @Test()
    public void valorizzaInfoAggiuntive_causalecessazione_null_torna_autorizzazione_senza_causale_cessazione() {

	AutorizzazioniServiceImpl service = new AutorizzazioniServiceImpl();
	Autorizzazioni autorizzazione = new Autorizzazioni();
	autorizzazione.setConcessionicausaliByFkAutConccausCess(new Concessionicausali());
	service.valorizzaInfoAggiuntive(autorizzazione, null, null);
	Assert.assertNull("La causale di cessazione è nulla se l'autorizzazione è attiva", autorizzazione.getConcessionicausaliByFkAutConccausCess());
    }

    @Test()
    public void valorizzaInfoAggiuntive_causale_cessazione_valorizzata_torna_autorizzazione_con_causale_cessazione() {

	Integer expected = 1;
	AutorizzazioniServiceImpl service = new AutorizzazioniServiceImpl();
	Autorizzazioni autorizzazione = new Autorizzazioni();
	Concessionicausali causaleCessazione = new Concessionicausali();
	causaleCessazione.setId(new PkId(expected));
	autorizzazione.setConcessionicausaliByFkAutConccausCess(causaleCessazione);
	service.valorizzaInfoAggiuntive(autorizzazione, null, causaleCessazione);
	Assert.assertEquals("La causale di cessazione è la 1", expected,
		autorizzazione.getConcessionicausaliByFkAutConccausCess().getId().getCodice());
    }

    @Test()
    public void valorizzaInfoAggiuntive_causale_acquisizione_null_torna_autorizzazione_senza_causale_acquisizione() {

	AutorizzazioniServiceImpl service = new AutorizzazioniServiceImpl();
	Autorizzazioni autorizzazione = new Autorizzazioni();
	autorizzazione.setConcessionicausaliByFkAutConccausAcq(new Concessionicausali());
	service.valorizzaInfoAggiuntive(autorizzazione, null, null);
	Assert.assertNull("La causale di cessazione della concessione è null", autorizzazione.getConcessionicausaliByFkAutConccausAcq());
    }

    @Test()
    public void valorizzaInfoAggiuntive_causale_acquisizione_valorizzata_torna_autorizzazione_con_causale_acquisizione() {

	Integer expected = 1;
	AutorizzazioniServiceImpl service = new AutorizzazioniServiceImpl();
	Autorizzazioni autorizzazione = new Autorizzazioni();
	Concessionicausali causaleAcquisizione = new Concessionicausali();
	causaleAcquisizione.setId(new PkId(expected));
	autorizzazione.setConcessionicausaliByFkAutConccausAcq(causaleAcquisizione);
	service.valorizzaInfoAggiuntive(autorizzazione, causaleAcquisizione, null);
	Assert.assertEquals("La causale di acquisizione è la 1", expected,
		autorizzazione.getConcessionicausaliByFkAutConccausAcq().getId().getCodice());
    }

    @Test()
    public void valorizzaInfoAggiuntive_impostando_non_attiva_svuota_la_data_di_cessazione() {

	AutorizzazioniServiceImpl service = new AutorizzazioniServiceImpl();
	Autorizzazioni autorizzazione = new Autorizzazioni();
	autorizzazione.setFlagAttiva(Boolean.TRUE);
	autorizzazione.setDataCessazione(new Date());
	service.valorizzaInfoAggiuntive(autorizzazione, null, null);
	Assert.assertNull("La data di cessazione è nulla se l'autorizzazione è attiva", autorizzazione.getDataCessazione());
    }

    @Test()
    public void valorizzaInfoAggiuntive_impostando_non_attiva_svuota_la_causale_di_cessazione() {

	AutorizzazioniServiceImpl service = new AutorizzazioniServiceImpl();
	Autorizzazioni autorizzazione = new Autorizzazioni();
	autorizzazione.setFlagAttiva(Boolean.TRUE);
	Concessionicausali causaleCessazione = new Concessionicausali();
	causaleCessazione.setId(new PkId(1));
	autorizzazione.setConcessionicausaliByFkAutConccausCess(causaleCessazione);
	service.valorizzaInfoAggiuntive(autorizzazione, null, null);
	Assert.assertNull("La causale di cessazione è nulla se l'autorizzazione è attiva", autorizzazione.getConcessionicausaliByFkAutConccausCess());
    }
}
