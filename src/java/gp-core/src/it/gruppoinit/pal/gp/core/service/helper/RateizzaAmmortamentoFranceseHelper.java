package it.gruppoinit.pal.gp.core.service.helper;

import it.gruppoinit.pal.gp.core.domain.helper.RataAmmortamentoFranceseHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RateizzaAmmortamentoFranceseHelper {

    private double rata;
    private double capitalePrestato;
    private double interesse;
    private PeriodicitaEnum periodicita;
    private int durataMesiMutuo;
    private Date dataPrimaScadenza;

    public double getRata() {

	return rata;
    }

    public void setRata(double rata) {

	this.rata = rata;
    }

    public RateizzaAmmortamentoFranceseHelper(double capitalePrestato, double interesse, PeriodicitaEnum periodicita, int durataMesiMutuo,
	    Date dataPrimaScadenza) {

	this.capitalePrestato = capitalePrestato;
	this.interesse = interesse;
	this.periodicita = periodicita;
	this.durataMesiMutuo = durataMesiMutuo;
	this.dataPrimaScadenza = dataPrimaScadenza;
	calcolaImportoRata();
    }

    public List<RataAmmortamentoFranceseHelper> getPianoAmmortamento() {

	List<RataAmmortamentoFranceseHelper> myList = new LinkedList<RataAmmortamentoFranceseHelper>();
	RataAmmortamentoFranceseHelper rIn = new RataAmmortamentoFranceseHelper();
	rIn.setDebitoResiduo(this.capitalePrestato);
	rIn.setDataScadenza(dataPrimaScadenza);
	Date dataScadenza = dataPrimaScadenza;
	int[] dati = getGiorniMesiAnnoFromDate(dataScadenza);
	int giorno = dati[0];
	double debitoResiduo = this.capitalePrestato;
	double quotaCapitale = 0;
	double interesse = 0;
	int numeroRata = 0;
	myList.add(rIn);
	do {
	    numeroRata++;
	    dataScadenza = getDataSuccessiva(dataScadenza);
	    int nuovoGiorno = getGiorniMesiAnnoFromDate(dataScadenza)[0];
	    if (nuovoGiorno != giorno) {
		if (isGiornoAmmissibilePerData(dataScadenza, giorno)) {
		    Calendar c = Calendar.getInstance();
		    c.setTime(dataScadenza);
		    c.set(Calendar.DAY_OF_MONTH, giorno);
		    dataScadenza = c.getTime();
		}
	    }
	    double[] quote = getQuoteDebito(debitoResiduo);
	    RataAmmortamentoFranceseHelper r = new RataAmmortamentoFranceseHelper();
	    quotaCapitale = quote[1];
	    interesse = quote[0];
	    debitoResiduo = debitoResiduo - quotaCapitale;
	    r.setImportoRata(rata);
	    r.setDataScadenza(dataScadenza);
	    r.setDebitoResiduo(debitoResiduo);
	    r.setQuotaCapitale(quotaCapitale);
	    r.setQuotaInteressi(interesse);
	    r.setNumeroRata(numeroRata);
	    myList.add(r);
	} while ((int) debitoResiduo != 0);
	return myList;
    }

    private void calcolaImportoRata() {

	rata = this.capitalePrestato * getFattoreA() * getFattoreB();
    }

    private double getFattoreB() {

	double numeratore = calcolaA() - (double) 1;
	double denominatore = getFattoreA() - 1;
	return numeratore / denominatore;
    }

    /**
     * Stiamo calcolando 1+ TA/PA dove TA è l'interesse e PA i periodi annui
     * 
     * @return
     */
    private double calcolaA() {

	return (double) 1 + this.interesse / (double) periodicita.getN();
    }

    private double esponenteA() {

	return (double) periodicita.getN() * anniRimborso();
    }

    private double getFattoreA() {

	Double esponenteA = esponenteA();
	Double calcolaA = calcolaA();
	return Math.pow(calcolaA, esponenteA);
    }

    private double anniRimborso() {

	return (double) this.durataMesiMutuo / (double) 12;
    }

    //    public static void main(String[] args) throws Exception {
    //
    //	Date dataPrimaScadenza = creaData(26, 12, 2011);
    //	PianoAmmortamentoFrancese p = new PianoAmmortamentoFrancese((double) 1500, 0.010, PERIODICITA.MENSILE, 12, dataPrimaScadenza);
    //	System.out.println(p.getPianoAmmortamento());
    //    }
    private double[] getQuoteDebito(double capitaleResiduo) {

	double[] retVal = new double[2];
	double quotaInteressi = (this.interesse / (double) periodicita.getN()) * capitaleResiduo;
	double quotaCapitale = rata - quotaInteressi;
	retVal[0] = quotaInteressi;
	retVal[1] = quotaCapitale;
	return retVal;
    }

    private int[] getGiorniMesiAnnoFromDate(Date d) {

	Calendar c = Calendar.getInstance();
	c.setTime(d);
	int anno = c.get(Calendar.YEAR);
	int mese = c.get(Calendar.MONTH);
	int giorno = c.get(Calendar.DAY_OF_MONTH);
	int[] lista = new int[3];
	lista[0] = giorno;
	lista[1] = mese;
	lista[2] = anno;
	return lista;
    }

    private Date getDataSuccessiva(Date d) {

	int p = 12 / this.periodicita.getN();
	Calendar c = Calendar.getInstance();
	c.setTime(d);
	c.add(Calendar.MONTH, p);
	return c.getTime();
    }

    private static Date creaData(int giorno, int mese, int anno) {

	Calendar c = Calendar.getInstance();
	c.set(Calendar.YEAR, anno);
	c.set(Calendar.MONTH, mese - 1);
	c.set(Calendar.DAY_OF_MONTH, giorno);
	return c.getTime();
    }

    private boolean isGiornoAmmissibilePerData(Date d, int giorno) {

	boolean retVal = false;
	if (giorno <= 28) {
	    retVal = true;
	} else {
	    int mese = getGiorniMesiAnnoFromDate(d)[1];
	    if (mese == Calendar.FEBRUARY) {
		if (isLeapYear(getGiorniMesiAnnoFromDate(d)[2]) && giorno == 29) {
		    retVal = true;
		} else {
		    retVal = false;
		}
	    } else if (giorno == 31) {
		if (mese != Calendar.APRIL && mese != Calendar.JUNE && mese != Calendar.SEPTEMBER && mese != Calendar.NOVEMBER) {
		    retVal = true;
		} else {
		    retVal = false;
		}
	    }
	}
	return retVal;
    }

    /**
     * Un anno è bisestile se:<br>
     * 1) divisibile per 4<br>
     * 2) non secolare (a parte i secolari divisibili per 400)<br>
     * 
     * @param anno
     * @return
     */
    private boolean isLeapYear(int anno) {

	boolean retVal = false;
	int div4 = anno % 4;
	if (div4 == 0) {
	    //divisibile per quattro
	    int div100 = anno % 100;
	    if (div100 == 0) {
		// è un anno "secolare"
		int div400 = anno % 400;
		if (div400 == 0) {
		    // però è divisibile anche per 400
		    retVal = true;
		} else {
		    // anno secolare non divisibile per 400
		    // non è bisestile
		    retVal = false;
		}
	    } else {
		// divisibile per 4 e non secolare è bisestile
		retVal = true;
	    }
	}
	return retVal;
    }
}
