package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Giornisettimana;
import it.gruppoinit.pal.gp.core.domain.Giorno;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.service.CalendariomercatoParametriService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class CalendariomercatiParametriServiceImpl implements CalendariomercatoParametriService {

    @Override
    public CalendariomercatoParametri findGiorniMercatoInUnAnno(Mercati mercati, int anno, MercatiUso mercatiUso,
	    List<Giornisettimana> giorniSettimana) {

	CalendariomercatoParametri calendariomercatoParametri = getNumeroGiorniSceltiInUnAnno(giorniSettimana, anno);
	return calendariomercatoParametri;
    }

    @Override
    public List<Giorno> findGiornifestivi(int anno) {

	List<Giorno> list = new ArrayList<Giorno>();
	Giorno giorno = null;
	for (String temp : gioniFestivi) {
	    giorno = new Giorno();
	    String[] field = temp.split("/");
	    Calendar datetemp = new GregorianCalendar(anno, Integer.parseInt(field[1]), Integer.parseInt(field[0]));
	    giorno.setData(datetemp);
	    list.add(giorno);
	}
	try {
	    // setto il giorno di pasqua
	    Calendar easter = findEaster(anno);
	    giorno = new Giorno();
	    giorno.setData(easter);
	    list.add(giorno);
	    // setto il giorno dopo pasqua
	    Calendar aftereaster = new GregorianCalendar(anno, easter.get(Calendar.MONTH), (easter.get(Calendar.DAY_OF_MONTH) + 1));
	    giorno.setData(aftereaster);
	    list.add(giorno);
	} catch (YearOutOfRangeException e) {
	    // TODO gestire errore!!
	    e.printStackTrace();
	}
	return list;
    }

    @Override
    public boolean isFindDayAndRemove(List<Giorno> list, Calendar date) {

	boolean success = false;
	for (Giorno giorno : list) {
	    if (date.get(Calendar.YEAR) == giorno.getData().get(Calendar.YEAR) && date.get(Calendar.MONTH) == giorno.getData().get(Calendar.MONTH)
		    && date.get(Calendar.DAY_OF_MONTH) == giorno.getData().get(Calendar.DAY_OF_MONTH)) {
		success = true;
		list.remove(giorno);
		break;
	    }
	}
	return success;
    }

    /**
     * metodo per il calcolo dei giorni del mercato per l'anno scelto
     * 
     * @param giorniSettimana
     *            lista dei giorni della settimana scelti (flag transientSelected=true)
     * @param anno
     *            anno per cui creare il calendario
     */
    private CalendariomercatoParametri getNumeroGiorniSceltiInUnAnno(List<Giornisettimana> giorniSettimana, int anno) {

	int numeroGiorniAnno = WebConstants.NUMERO_GIORNI_ANNO;
	Calendar calendar;
	List<Giorno> list = new ArrayList<Giorno>();
	CalendariomercatoParametri calendariomercatoParametri = new CalendariomercatoParametri();
	Giorno giorno;
	// verifico se l'anno è bisestile
	if (isAnnoBisetile(anno)) {
	    numeroGiorniAnno = WebConstants.NUMERO_GIORNI_ANNO_BISESTILE;
	}
	// per ogni giorno dell'anno se questo è un giorno del mercato allora lo
	// aggiungo alla lista dei giorni del mercato
	for (int i = 1; i <= numeroGiorniAnno; i++) {
	    calendar = new GregorianCalendar(anno, 0, i, 0, 0, 0);
	    for (Giornisettimana giornoDelMercato : giorniSettimana) {
		if (giornoDelMercato.getGsValore() != null && calendar.get(Calendar.DAY_OF_WEEK) == giornoDelMercato.getGsValore()
			&& giornoDelMercato.isTransientSelected()) {
		    giorno = new Giorno();
		    giorno.setData(calendar);
		    list.add(giorno);
		}
	    }
	}
	// inserisco la lista dei giorni del mercato nell'oggetto
	// registrazionimercato
	calendariomercatoParametri.setGiorniMercato(list);
	return calendariomercatoParametri;
    }

    /**
     * metodo per verificare se l'anno è bisestile
     * 
     * @param anno
     * @return true se l'anno è bisestile, false altrimenti
     */
    private boolean isAnnoBisetile(Integer anno) {

	// FIXME utilizzare Calendar per il calcolo
	return ((anno % 4 == 0 && anno % 100 != 0) || anno % 400 == 0);
    }

    // Classe che crea Il tipo di eccezione OutOfRange
    public static class YearOutOfRangeException extends Exception {

	private static final long serialVersionUID = 5394938690797595980L;
    }

    /**
     * metodo per il calcolo della Pasqua
     * 
     * @param year
     * @return
     * @throws YearOutOfRangeException
     */
    private Calendar findEaster(int year) throws YearOutOfRangeException {

	if ((year < 1573) || (year > 2499)) {
	    throw new YearOutOfRangeException();
	}
	int a = year % 19;
	int b = year % 4;
	int c = year % 7;
	int m = 0;
	int n = 0;
	if ((year >= 1583) && (year <= 1699)) {
	    m = 22;
	    n = 2;
	}
	if ((year >= 1700) && (year <= 1799)) {
	    m = 23;
	    n = 3;
	}
	if ((year >= 1800) && (year <= 1899)) {
	    m = 23;
	    n = 4;
	}
	if ((year >= 1900) && (year <= 2099)) {
	    m = 24;
	    n = 5;
	}
	if ((year >= 2100) && (year <= 2199)) {
	    m = 24;
	    n = 6;
	}
	if ((year >= 2200) && (year <= 2299)) {
	    m = 25;
	    n = 0;
	}
	if ((year >= 2300) && (year <= 2399)) {
	    m = 26;
	    n = 1;
	}
	if ((year >= 2400) && (year <= 2499)) {
	    m = 25;
	    n = 1;
	}
	int d = (19 * a + m) % 30;
	int e = (2 * b + 4 * c + 6 * d + n) % 7;
	Calendar calendar = new GregorianCalendar();
	calendar.set(Calendar.YEAR, year);
	if (d + e < 10) {
	    calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.MONTH, Calendar.MARCH);
	    calendar.set(Calendar.DAY_OF_MONTH, d + e + 22);
	} else {
	    calendar.set(Calendar.MONTH, Calendar.APRIL);
	    int day = d + e - 9;
	    if (26 == day) {
		day = 19;
	    }
	    if ((25 == day) && (28 == d) && (e == 6) && (a > 10)) {
		day = 18;
	    }
	    calendar.set(Calendar.DAY_OF_MONTH, day);
	}
	return calendar;
    }

    // Blocco statico che contiene le festività a gioni fissi
    static List<String> gioniFestivi;
    static {
	List<String> list = new ArrayList<String>();
	list.add("1/0"); // Capo d'anno
	list.add("6/0"); // befana
	list.add("25/3"); // 25 aprile
	list.add("1/4"); // festa lavoratori
	list.add("2/5"); // festa della repubblica
	list.add("15/7"); // ferragosto
	list.add("1/10"); // festa dei morti
	list.add("8/11"); // immacolata concezione
	list.add("25/11"); // natale
	list.add("26/11"); // santo Stefano
	gioniFestivi = list;
    }
    protected BindingResult result;

    @Override
    public BindingResult getBindingResult() {

	return this.result;
    }

    @Override
    public void setBindingResult(BindingResult result) {

	this.result = result;
    }
}
