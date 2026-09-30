package it.gruppoinit.pal.gp.core.dao.helper;

import it.gruppoinit.pal.gp.core.domain.Giorno;
import it.gruppoinit.pal.gp.core.domain.Periodicita;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PeriodicitaHelper {

    public static final int ANNUALE = 1;
    public static final int SEMESTRALE = 2;
    public static final int QUADRIMESTRALE = 3;
    public static final int TRIMESTRALE = 4;
    public static final int MENSILE = 12;

    /**
     * Torna la descrizione del periodo es. annuale, semestrale, quadrimestrale, ecc
     * 
     * @param periodicita
     * @return
     */
    public static String getPeriodoToString(int periodicita) {

	switch (periodicita) {
	case ANNUALE:
	    return "Annuale";
	case SEMESTRALE:
	    return "Semestrale";
	case QUADRIMESTRALE:
	    return "Quadrimestrale";
	case TRIMESTRALE:
	    return "Trimestrale";
	case MENSILE:
	    return "Mensile";
	default:
	    return "Non codificata";
	}
    }

    /**
     * Torna la descrizione dell'elemento del periodo es. anno, semestre, quadrimestre, ecc
     * 
     * @param periodicita
     * @return
     */
    public static String getDescrizioneElementoPeriodo(int periodicita) {

	switch (periodicita) {
	case ANNUALE:
	    return "Anno";
	case SEMESTRALE:
	    return "Semestre";
	case QUADRIMESTRALE:
	    return "Quadrimestre";
	case TRIMESTRALE:
	    return "Trimestre";
	case MENSILE:
	    return "Mese";
	default:
	    return "Non codificata";
	}
    }

    /**
     * 
     * @param listGiorni
     * @param periodicita
     * @param year
     * @return
     */
    public static Map<Integer, PeriodicitaHelperBean> trovaGiorniInPeriodicita(List<Giorno> listGiorni, Periodicita periodicita, int year) {

	Map<Integer, PeriodicitaHelperBean> answer = new HashMap<Integer, PeriodicitaHelperBean>(0);
	Calendar oggi = GregorianCalendar.getInstance();
	oggi.set(Calendar.YEAR, year);
	switch (periodicita.getNumeroPeriodo()) {
	case ANNUALE:
	    PeriodicitaHelperBean phb = new PeriodicitaHelperBean(listGiorni.size(), "01/01/" + year, listGiorni);
	    answer.put(0, phb);
	    break;
	case SEMESTRALE: // 2
	    int giorniPrimoSemestre = 0;
	    int giorniSecondoSemestre = 0;
	    oggi.set(Calendar.DATE, 30);
	    oggi.set(Calendar.MONTH, Calendar.JUNE);
	    List<Giorno> listGiorni1Sem = new ArrayList<Giorno>();
	    List<Giorno> listGiorni2Sem = new ArrayList<Giorno>();
	    // ho settato il calendario al 30 giugno per dividere i giorni del primo da quelli del secondo semestre
	    for (Giorno giorno : listGiorni) {
		Calendar giornoMercato = giorno.getData();
		if (giornoMercato.compareTo(oggi) <= 0) {
		    giorniPrimoSemestre++;
		    listGiorni1Sem.add(giorno);
		} else {
		    giorniSecondoSemestre++;
		    listGiorni2Sem.add(giorno);
		}
	    }
	    PeriodicitaHelperBean phb1s = new PeriodicitaHelperBean(giorniPrimoSemestre, "01/01/" + year, listGiorni1Sem);
	    PeriodicitaHelperBean phb2s = new PeriodicitaHelperBean(giorniSecondoSemestre, "01/07/" + year, listGiorni2Sem);
	    answer.put(0, phb1s);
	    answer.put(1, phb2s);
	    break;
	case QUADRIMESTRALE: // 4
	    int giorniPrimoQ = 0;
	    int giorniSecondoQ = 0;
	    int giorniTerzoQ = 0;
	    Calendar primoQ = GregorianCalendar.getInstance();
	    primoQ.set(Calendar.DATE, 30);
	    primoQ.set(Calendar.MONTH, Calendar.APRIL);
	    primoQ.set(Calendar.YEAR, year);
	    Calendar secondoQ = GregorianCalendar.getInstance();
	    secondoQ.set(Calendar.DATE, 31);
	    secondoQ.set(Calendar.MONTH, Calendar.AUGUST);
	    secondoQ.set(Calendar.YEAR, year);
	    Calendar terzoQ = GregorianCalendar.getInstance();
	    terzoQ.set(Calendar.DATE, 31);
	    terzoQ.set(Calendar.MONTH, Calendar.DECEMBER);
	    terzoQ.set(Calendar.YEAR, year);
	    List<Giorno> listGiorni1Quad = new ArrayList<Giorno>();
	    List<Giorno> listGiorni2Quad = new ArrayList<Giorno>();
	    List<Giorno> listGiorni3Quad = new ArrayList<Giorno>();
	    for (Giorno giorno : listGiorni) {
		Calendar giornoMercato = giorno.getData();
		if (giornoMercato.compareTo(primoQ) <= 0) {
		    giorniPrimoQ++;
		    listGiorni1Quad.add(giorno);
		} else if (giornoMercato.compareTo(primoQ) > 0 && giornoMercato.compareTo(secondoQ) <= 0) {
		    giorniSecondoQ++;
		    listGiorni2Quad.add(giorno);
		} else if (giornoMercato.compareTo(secondoQ) > 0 && giornoMercato.compareTo(terzoQ) <= 0) {
		    giorniTerzoQ++;
		    listGiorni3Quad.add(giorno);
		}
	    }
	    PeriodicitaHelperBean phb1q = new PeriodicitaHelperBean(giorniPrimoQ, "01/01/" + year, listGiorni1Quad);
	    PeriodicitaHelperBean phb2q = new PeriodicitaHelperBean(giorniSecondoQ, "01/05/" + year, listGiorni2Quad);
	    PeriodicitaHelperBean phb3q = new PeriodicitaHelperBean(giorniTerzoQ, "01/09/" + year, listGiorni3Quad);
	    answer.put(0, phb1q);
	    answer.put(1, phb2q);
	    answer.put(2, phb3q);
	    break;
	case TRIMESTRALE:
	    int giorniPrimoT = 0;
	    int giorniSecondoT = 0;
	    int giorniTerzoT = 0;
	    int giorniQuartoT = 0;
	    Calendar primoT = GregorianCalendar.getInstance();
	    primoT.set(Calendar.DATE, 31);
	    primoT.set(Calendar.MONTH, Calendar.MARCH);
	    primoT.set(Calendar.YEAR, year);
	    Calendar secondoT = GregorianCalendar.getInstance();
	    secondoT.set(Calendar.DATE, 30);
	    secondoT.set(Calendar.MONTH, Calendar.JUNE);
	    secondoT.set(Calendar.YEAR, year);
	    Calendar terzoT = GregorianCalendar.getInstance();
	    terzoT.set(Calendar.DATE, 30);
	    terzoT.set(Calendar.MONTH, Calendar.SEPTEMBER);
	    terzoT.set(Calendar.YEAR, year);
	    Calendar quartoT = GregorianCalendar.getInstance();
	    quartoT.set(Calendar.DATE, 31);
	    quartoT.set(Calendar.MONTH, Calendar.DECEMBER);
	    quartoT.set(Calendar.YEAR, year);
	    List<Giorno> listGiorni1Trim = new ArrayList<Giorno>();
	    List<Giorno> listGiorni2Trim = new ArrayList<Giorno>();
	    List<Giorno> listGiorni3Trim = new ArrayList<Giorno>();
	    List<Giorno> listGiorni4Trim = new ArrayList<Giorno>();
	    for (Giorno giorno : listGiorni) {
		Calendar giornoMercato = giorno.getData();
		if (giornoMercato.compareTo(primoT) <= 0) {
		    giorniPrimoT++;
		    listGiorni1Trim.add(giorno);
		} else if (giornoMercato.compareTo(primoT) > 0 && giornoMercato.compareTo(secondoT) <= 0) {
		    giorniSecondoT++;
		    listGiorni2Trim.add(giorno);
		} else if (giornoMercato.compareTo(secondoT) > 0 && giornoMercato.compareTo(terzoT) <= 0) {
		    giorniTerzoT++;
		    listGiorni3Trim.add(giorno);
		} else if (giornoMercato.compareTo(terzoT) > 0 && giornoMercato.compareTo(quartoT) <= 0) {
		    giorniQuartoT++;
		    listGiorni4Trim.add(giorno);
		}
	    }
	    PeriodicitaHelperBean phb1t = new PeriodicitaHelperBean(giorniPrimoT, "01/01/" + year, listGiorni1Trim);
	    PeriodicitaHelperBean phb2t = new PeriodicitaHelperBean(giorniSecondoT, "01/04/" + year, listGiorni2Trim);
	    PeriodicitaHelperBean phb3t = new PeriodicitaHelperBean(giorniTerzoT, "01/07/" + year, listGiorni3Trim);
	    PeriodicitaHelperBean phb4t = new PeriodicitaHelperBean(giorniQuartoT, "01/10/" + year, listGiorni4Trim);
	    answer.put(0, phb1t);
	    answer.put(1, phb2t);
	    answer.put(2, phb3t);
	    answer.put(3, phb4t);
	    break;
	case MENSILE:
	    int giorniPrimoM = 0;
	    int giorniSecondoM = 0;
	    int giorniTerzoM = 0;
	    int giorniQuartoM = 0;
	    int giorniQuintoM = 0;
	    int giorniSestoM = 0;
	    int giorniSettimoM = 0;
	    int giorniOttavoM = 0;
	    int giorniNonoM = 0;
	    int giorniDecimoM = 0;
	    int giorniUndecimoM = 0;
	    int giorniDodicesimoM = 0;
	    Calendar gennaio = GregorianCalendar.getInstance();
	    gennaio.set(Calendar.DATE, 31);
	    gennaio.set(Calendar.MONTH, Calendar.JANUARY);
	    gennaio.set(Calendar.YEAR, year);
	    Calendar febbraio = GregorianCalendar.getInstance();
	    febbraio.set(Calendar.DATE, 28);
	    febbraio.set(Calendar.MONTH, Calendar.FEBRUARY);
	    febbraio.set(Calendar.YEAR, year);
	    Calendar marzo = GregorianCalendar.getInstance();
	    marzo.set(Calendar.DATE, 31);
	    marzo.set(Calendar.MONTH, Calendar.MARCH);
	    marzo.set(Calendar.YEAR, year);
	    Calendar aprile = GregorianCalendar.getInstance();
	    aprile.set(Calendar.DATE, 30);
	    aprile.set(Calendar.MONTH, Calendar.APRIL);
	    aprile.set(Calendar.YEAR, year);
	    Calendar maggio = GregorianCalendar.getInstance();
	    maggio.set(Calendar.DATE, 31);
	    maggio.set(Calendar.MONTH, Calendar.MAY);
	    maggio.set(Calendar.YEAR, year);
	    Calendar giugno = GregorianCalendar.getInstance();
	    giugno.set(Calendar.DATE, 30);
	    giugno.set(Calendar.MONTH, Calendar.JUNE);
	    giugno.set(Calendar.YEAR, year);
	    Calendar luglio = GregorianCalendar.getInstance();
	    luglio.set(Calendar.DATE, 31);
	    luglio.set(Calendar.MONTH, Calendar.JULY);
	    luglio.set(Calendar.YEAR, year);
	    Calendar agosto = GregorianCalendar.getInstance();
	    agosto.set(Calendar.DATE, 31);
	    agosto.set(Calendar.MONTH, Calendar.AUGUST);
	    agosto.set(Calendar.YEAR, year);
	    Calendar settembre = GregorianCalendar.getInstance();
	    settembre.set(Calendar.DATE, 30);
	    settembre.set(Calendar.MONTH, Calendar.SEPTEMBER);
	    settembre.set(Calendar.YEAR, year);
	    Calendar ottobre = GregorianCalendar.getInstance();
	    ottobre.set(Calendar.DATE, 31);
	    ottobre.set(Calendar.MONTH, Calendar.OCTOBER);
	    ottobre.set(Calendar.YEAR, year);
	    Calendar novembre = GregorianCalendar.getInstance();
	    novembre.set(Calendar.DATE, 30);
	    novembre.set(Calendar.MONTH, Calendar.NOVEMBER);
	    novembre.set(Calendar.YEAR, year);
	    Calendar dicembre = GregorianCalendar.getInstance();
	    dicembre.set(Calendar.DATE, 31);
	    dicembre.set(Calendar.MONTH, Calendar.DECEMBER);
	    dicembre.set(Calendar.YEAR, year);
	    List<Giorno> listGiorniGennaio = new ArrayList<Giorno>();
	    List<Giorno> listGiorniFebbraio = new ArrayList<Giorno>();
	    List<Giorno> listGiorniMarzo = new ArrayList<Giorno>();
	    List<Giorno> listGiorniAprile = new ArrayList<Giorno>();
	    List<Giorno> listGiorniMaggio = new ArrayList<Giorno>();
	    List<Giorno> listGiorniGiugno = new ArrayList<Giorno>();
	    List<Giorno> listGiorniLuglio = new ArrayList<Giorno>();
	    List<Giorno> listGiorniAgosto = new ArrayList<Giorno>();
	    List<Giorno> listGiorniSettembre = new ArrayList<Giorno>();
	    List<Giorno> listGiorniOttobre = new ArrayList<Giorno>();
	    List<Giorno> listGiorniNovembre = new ArrayList<Giorno>();
	    List<Giorno> listGiorniDicembre = new ArrayList<Giorno>();
	    for (Giorno giorno : listGiorni) {
		Calendar giornoMercato = giorno.getData();
		if (giornoMercato.compareTo(gennaio) <= 0) {
		    giorniPrimoM++;
		    listGiorniGennaio.add(giorno);
		} else if (giornoMercato.compareTo(gennaio) > 0 && giornoMercato.compareTo(febbraio) <= 0) {
		    giorniSecondoM++;
		    listGiorniFebbraio.add(giorno);
		} else if (giornoMercato.compareTo(febbraio) > 0 && giornoMercato.compareTo(marzo) <= 0) {
		    giorniTerzoM++;
		    listGiorniMarzo.add(giorno);
		} else if (giornoMercato.compareTo(marzo) > 0 && giornoMercato.compareTo(aprile) <= 0) {
		    giorniQuartoM++;
		    listGiorniAprile.add(giorno);
		} else if (giornoMercato.compareTo(aprile) > 0 && giornoMercato.compareTo(maggio) <= 0) {
		    giorniQuintoM++;
		    listGiorniMaggio.add(giorno);
		} else if (giornoMercato.compareTo(maggio) > 0 && giornoMercato.compareTo(giugno) <= 0) {
		    giorniSestoM++;
		    listGiorniGiugno.add(giorno);
		} else if (giornoMercato.compareTo(giugno) > 0 && giornoMercato.compareTo(luglio) <= 0) {
		    giorniSettimoM++;
		    listGiorniLuglio.add(giorno);
		} else if (giornoMercato.compareTo(luglio) > 0 && giornoMercato.compareTo(agosto) <= 0) {
		    giorniOttavoM++;
		    listGiorniAgosto.add(giorno);
		} else if (giornoMercato.compareTo(agosto) > 0 && giornoMercato.compareTo(settembre) <= 0) {
		    giorniNonoM++;
		    listGiorniSettembre.add(giorno);
		} else if (giornoMercato.compareTo(settembre) > 0 && giornoMercato.compareTo(ottobre) <= 0) {
		    giorniDecimoM++;
		    listGiorniOttobre.add(giorno);
		} else if (giornoMercato.compareTo(ottobre) > 0 && giornoMercato.compareTo(novembre) <= 0) {
		    giorniUndecimoM++;
		    listGiorniNovembre.add(giorno);
		} else if (giornoMercato.compareTo(novembre) > 0 && giornoMercato.compareTo(dicembre) <= 0) {
		    giorniDodicesimoM++;
		    listGiorniDicembre.add(giorno);
		}
	    }
	    PeriodicitaHelperBean phbgennaio = new PeriodicitaHelperBean(giorniPrimoM, "01/01/" + year, listGiorniGennaio);
	    PeriodicitaHelperBean phbfebbraio = new PeriodicitaHelperBean(giorniSecondoM, "01/02/" + year, listGiorniFebbraio);
	    PeriodicitaHelperBean phbmarzo = new PeriodicitaHelperBean(giorniTerzoM, "01/03/" + year, listGiorniMarzo);
	    PeriodicitaHelperBean phbaprile = new PeriodicitaHelperBean(giorniQuartoM, "01/04/" + year, listGiorniAprile);
	    PeriodicitaHelperBean phbmaggio = new PeriodicitaHelperBean(giorniQuintoM, "01/05/" + year, listGiorniMaggio);
	    PeriodicitaHelperBean phbgiugno = new PeriodicitaHelperBean(giorniSestoM, "01/06/" + year, listGiorniGiugno);
	    PeriodicitaHelperBean phbluglio = new PeriodicitaHelperBean(giorniSettimoM, "01/07/" + year, listGiorniLuglio);
	    PeriodicitaHelperBean phbagosto = new PeriodicitaHelperBean(giorniOttavoM, "01/08/" + year, listGiorniAgosto);
	    PeriodicitaHelperBean phbsettembre = new PeriodicitaHelperBean(giorniNonoM, "01/09/" + year, listGiorniSettembre);
	    PeriodicitaHelperBean phbottobre = new PeriodicitaHelperBean(giorniDecimoM, "01/10/" + year, listGiorniOttobre);
	    PeriodicitaHelperBean phbnovembre = new PeriodicitaHelperBean(giorniUndecimoM, "01/11/" + year, listGiorniNovembre);
	    PeriodicitaHelperBean phbdicembre = new PeriodicitaHelperBean(giorniDodicesimoM, "01/12/" + year, listGiorniDicembre);
	    answer.put(0, phbgennaio);
	    answer.put(1, phbfebbraio);
	    answer.put(2, phbmarzo);
	    answer.put(3, phbaprile);
	    answer.put(4, phbmaggio);
	    answer.put(5, phbgiugno);
	    answer.put(6, phbluglio);
	    answer.put(7, phbagosto);
	    answer.put(8, phbsettembre);
	    answer.put(9, phbottobre);
	    answer.put(10, phbnovembre);
	    answer.put(11, phbdicembre);
	    break;
	default:
	    break;
	}
	return answer;
    }
}
