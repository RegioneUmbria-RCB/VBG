package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.Giorno;
import it.gruppoinit.pal.gp.core.jobs.InizializzaCalendariMercatoJob;
import it.gruppoinit.pal.gp.core.service.impl.CalendariomercatiParametriServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;

public class CalendarioMercatoTests {

    public List<String> calendariomercato(int anno, int giornoSettimana, String giorno, boolean bloccaPrimoGennaio) {

	CalendariomercatiParametriServiceImpl s = new CalendariomercatiParametriServiceImpl();
	List<Giornisettimana> gs = new ArrayList<Giornisettimana>();
	Giornisettimana g = new Giornisettimana();
	g.setGsValore(giornoSettimana);
	g.setGsDescrizione(giorno);
	g.setTransientSelected(true);
	gs.add(g);
	CalendariomercatoParametri ret = s.findGiorniMercatoInUnAnno(anno, null, gs);
	List<String> giorni = new ArrayList<String>();
	MercatipresenzeTServiceImpl service = new MercatipresenzeTServiceImpl();
	for (Iterator<Giorno> iterator = ret.getGiorniMercato().iterator(); iterator.hasNext();) {
	    Giorno data = iterator.next();
	    if (service.verificaInserisciGiorno(data, bloccaPrimoGennaio)) {
		giorni.add(Utilities.formatDate(data.getData().getTime(), false));
	    }
	}
	return giorni;
    }

    @Test()
    public void verificaCreazione31Dicembre2025() {

	CalendarioMercatoTests t = new CalendarioMercatoTests();
	List<String> ret = t.calendariomercato(2025, 4, "Mercoledì", true);
	Assert.assertTrue("31 Dicembre 2025 inserito ", ret.contains("31/12/2025"));
	Assert.assertFalse("01 Gennaio 2025 NON inserito", ret.contains("01/01/2025"));
	ret = t.calendariomercato(2025, 4, "Mercoledì", false);
	Assert.assertTrue("31 Dicembre 2025 inserito ", ret.contains("31/12/2025"));
	Assert.assertTrue("01 Gennaio 2025 inserito", ret.contains("01/01/2025"));
    }

    @Test
    public void testVerificaParametroBlocca1Gennaio() {

	InizializzaCalendariMercatoJob c = new InizializzaCalendariMercatoJob();
	Assert.assertTrue(c.bloccaPrimoGennaio("1"));
	Assert.assertTrue(c.bloccaPrimoGennaio(" 1 "));
	Assert.assertFalse(c.bloccaPrimoGennaio("0"));
	Assert.assertFalse(c.bloccaPrimoGennaio(" 0 "));
	Assert.assertFalse(c.bloccaPrimoGennaio(null));
    }

    @Test
    public void testVerifica1Gennaio2026() {

	MercatipresenzeTServiceImpl service = new MercatipresenzeTServiceImpl();
	Giorno data = new Giorno();
	Calendar primoGennaio2026 = Calendar.getInstance();
	primoGennaio2026.set(Calendar.YEAR, 2026);
	primoGennaio2026.set(Calendar.MONTH, 0);
	primoGennaio2026.set(Calendar.DATE, 1);
	primoGennaio2026.set(Calendar.HOUR, 0);
	primoGennaio2026.set(Calendar.MINUTE, 0);
	data.setData(primoGennaio2026);
	Assert.assertFalse("Il primo gennaio NON LO devo inserire", service.verificaInserisciGiorno(data, true));
	Assert.assertTrue("Il primo gennaio LO devo inserire", service.verificaInserisciGiorno(data, false));
    }

    @Test
    public void test31Dicembre1Gennaio() {

	CalendariomercatiParametriServiceImpl c = new CalendariomercatiParametriServiceImpl();
	List<Giornisettimana> l = new ArrayList<Giornisettimana>();
	Giornisettimana e = new Giornisettimana();
	e.setGsValore(5);
	e.setTransientSelected(true);
	l.add(e);
	CalendariomercatoParametri calendario = c.getNumeroGiorniSceltiInUnAnno(l, 2026);
	boolean trovato1Gennaio = false;
	boolean trovato31Dicembre = false;
	List<Giorno> giorniMercato = calendario.getGiorniMercato();
	for (Giorno giorno : giorniMercato) {
	    if (giorno.getData().get(Calendar.MONTH) == 0 && giorno.getData().get(Calendar.DATE) == 1) {
		trovato1Gennaio = true;
	    }
	    if (giorno.getData().get(Calendar.MONTH) == 11 && giorno.getData().get(Calendar.DATE) == 31) {
		trovato31Dicembre = true;
	    }
	}
	Assert.assertTrue("trovato1Gennaio: " + trovato1Gennaio, trovato1Gennaio);
	Assert.assertTrue("trovato31Dicembre: " + trovato31Dicembre, trovato31Dicembre);
    }
}
