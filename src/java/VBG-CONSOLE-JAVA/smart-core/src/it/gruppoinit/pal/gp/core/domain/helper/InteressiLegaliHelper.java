/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.domain.CalcoloInteressiLegali;
import it.gruppoinit.pal.gp.core.domain.InteressiLegali;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Classe helper per il calcolo degli Interessi Legali
 * 
 * @author francescop
 * 
 */
public class InteressiLegaliHelper {

    private InteressiLegaliService interessiLegaliService;

    public InteressiLegaliService getInteressiLegaliService() {

	return interessiLegaliService;
    }

    public void setInteressiLegaliService(InteressiLegaliService interessiLegaliService) {

	this.interessiLegaliService = interessiLegaliService;
    }

    public InteressiLegaliHelper(InteressiLegaliService interessiLegaliService) {

	this.interessiLegaliService = interessiLegaliService;
    }

    /**
     * Metodo per determinare l'importo considerando gli interessi legali. La logica è la seguente: Effettuo una query
     * sulla tabella Interessi Legali con filtro su data inizio e data fine. <br/>
     * Ci sono quattro casi principali:<br/>
     * 1)la data inizio e la data fine inserite nel form sono contenute in un intervallo di date dell'oggetto Interessi
     * Legali. <br/>
     * 2) la data inizio è contenuta nell'intervallo (data inizio - data fine) dell'oggetto Interessi Legali. <br/>
     * 3) la data fine è contenuta nell'intervallo (data inizio - data fine) dell'oggetto Interessi Legali.<br/>
     * 4) l'intervallo (data inizio - data fine) dell'oggetto Interessi legali è contenuto nell'intervallo di date
     * inserite nel form,ma non li contiene. <br/>
     * <br/>
     * Formula per il calcolo degli interessi legali:<br/>
     * Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
     * 
     * @param importo
     * @param dataFine
     *            la data in cui finisce il conteggio degli interessi
     * @param dataInizio
     *            la data da cui parte il conteggio degli interessi
     * @return
     */
    public List<CalcoloInteressiLegali> getInteressiLegali(BigDecimal importo, Date dataFine, Date dataInizio) {

	List<CalcoloInteressiLegali> calcoloInteressiLegaliList = new ArrayList<CalcoloInteressiLegali>();
	BigDecimal importoInteressiLegaliTot = BigDecimal.ZERO;
	BigDecimal importoInteressiLegaliTemp = BigDecimal.ZERO;
	Integer giorniCivili = 0;
	Integer giorniInteressi = 0;
	BigDecimal percentualeInteresse = BigDecimal.ZERO;
	// Lista degli interessi legali filtrati per intervallo di date
	List<InteressiLegali> interessiLegaliList = this.interessiLegaliService.findByDataInizioFine(dataInizio, dataFine);
	for (InteressiLegali interessiLegali : interessiLegaliList) {
	    BigDecimal importoInteresse = BigDecimal.ZERO;
	    percentualeInteresse = interessiLegali.getTassoPercentuale();
	    // Caso in cui la data inizio e data fine sono compresi nello stesso intervallo di date dell'oggetto
	    // Interessi Legali
	    if ((dataInizio.compareTo(interessiLegali.getDataInizio()) >= 0 && (interessiLegali.getDataFine() == null || dataInizio
		    .compareTo(interessiLegali.getDataFine()) <= 0))
		    && (dataFine.compareTo(interessiLegali.getDataInizio()) >= 0 && (interessiLegali.getDataFine() == null || dataFine
			    .compareTo(interessiLegali.getDataFine()) <= 0))) {
		// creo i Calendar per data inizio e data fine
		Calendar dataInizioInteressiLegaliCalendar = GregorianCalendar.getInstance();
		dataInizioInteressiLegaliCalendar.setTime(dataInizio);
		// Rappresenta la data fine dell'oggetto interessi legali
		Calendar dataFineInteressiLegaliCalendar = GregorianCalendar.getInstance();
		dataFineInteressiLegaliCalendar.setTime(dataFine);
		// Controllo se le due date hanno lo stesso anno
		if (dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) == dataFineInteressiLegaliCalendar.get(Calendar.YEAR)) {
		    // Calcolo giorni civili e giorni interessi
		    giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		    giorniInteressi = getDifferenzaGiorni(dataInizioInteressiLegaliCalendar, dataFineInteressiLegaliCalendar);
		    if (giorniInteressi != 1) {
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(giorniInteressi),
				WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
				WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
		    }
		    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
		    CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
		    calcoloInteressiLegali.setImporto(importo);
		    calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
		    calcoloInteressiLegali.setGiorni(giorniInteressi);
		    calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
		    calcoloInteressiLegali.setDataFine(dataFineInteressiLegaliCalendar.getTime());
		    calcoloInteressiLegali.setDataInizio(dataInizioInteressiLegaliCalendar.getTime());
		    // Aggiungo alla lista l'oggetto
		    calcoloInteressiLegaliList.add(calcoloInteressiLegali);
		    // Calcolo il totale degli interessi legali.
		    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
		} else {
		    // Calcolo la differenza di anni tra data inizio e data fine
		    int difYear = dataFineInteressiLegaliCalendar.get(Calendar.YEAR) - dataInizioInteressiLegaliCalendar.get(Calendar.YEAR);
		    if (difYear == 0) {
			// Calcolo giorni civili e giorni interessi
			giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			giorniInteressi = getDifferenzaGiorni(dataInizioInteressiLegaliCalendar, dataFineInteressiLegaliCalendar);
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			if (giorniInteressi != 1) {
			    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili))
				    .divide(new BigDecimal(giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
					    BigDecimal.ROUND_HALF_UP), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
				    BigDecimal.ROUND_HALF_UP);
			}
			// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			calcoloInteressiLegali.setImporto(importo);
			calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			calcoloInteressiLegali.setGiorni(giorniInteressi);
			calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			calcoloInteressiLegali.setDataFine(dataFineInteressiLegaliCalendar.getTime());
			calcoloInteressiLegali.setDataInizio(dataInizioInteressiLegaliCalendar.getTime());
			// Aggiungo alla lista l'oggetto
			calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			// Calcolo il totale degli interessi legali.
			importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
		    } else {
			// Calcolo i giorni del primo anno di Interessi legali
			// Calcolo giorni civili e giorni interessi
			giorniCivili = dataInizioInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			Calendar dataFineTemp = GregorianCalendar.getInstance();
			// Creo una data al 31 dicembre dell'anno preso in considerazione
			dataFineTemp.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR), GregorianCalendar.DECEMBER, 31, 0, 0, 0);
			giorniInteressi = getDifferenzaGiorni(dataInizioInteressiLegaliCalendar, dataFineTemp);
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			if (giorniInteressi != 1) {
			    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili))
				    .divide(new BigDecimal(giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
					    BigDecimal.ROUND_HALF_UP), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
				    BigDecimal.ROUND_HALF_UP);
			}
			// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			calcoloInteressiLegali.setImporto(importo);
			calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			calcoloInteressiLegali.setGiorni(giorniInteressi);
			calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			calcoloInteressiLegali.setDataFine(dataFineTemp.getTime());
			calcoloInteressiLegali.setDataInizio(dataInizioInteressiLegaliCalendar.getTime());
			// Aggiungo alla lista l'oggetto
			calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			// Calcolo il totale degli interessi legali.
			importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			int i = 0;
			// Scorro gli anni completi tra le date dell'intervallo
			for (i = 1; i < difYear; i++) {
			    // Creo una data al 1 gennaio dell'anno preso in considerazione
			    Calendar dataInizioTemp = GregorianCalendar.getInstance();
			    dataInizioTemp.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.JANUARY, 1, 0, 0, 0);
			    // Calcolo giorni civili e giorni interessi
			    giorniCivili = dataInizioTemp.getActualMaximum(Calendar.DAY_OF_YEAR);
			    giorniInteressi = dataInizioTemp.getActualMaximum(Calendar.DAY_OF_YEAR);
			    // calcolo l'importo secondo la formula:
			    // Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di
			    // interesse)
			    if (giorniInteressi != 1) {
				importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    }
			    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			    CalcoloInteressiLegali calcoloInteressi = new CalcoloInteressiLegali();
			    calcoloInteressi.setImporto(importo);
			    calcoloInteressi.setTassoPercentuale(percentualeInteresse);
			    calcoloInteressi.setGiorni(giorniInteressi);
			    calcoloInteressi.setInteressi(importoInteressiLegaliTemp);
			    Calendar dataFineTemp2 = GregorianCalendar.getInstance();
			    dataFineTemp2.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.DECEMBER, 31, 0, 0, 0);
			    calcoloInteressi.setDataFine(dataFineTemp2.getTime());
			    calcoloInteressi.setDataInizio(dataInizioTemp.getTime());
			    // Aggiungo alla lista l'oggetto
			    calcoloInteressiLegaliList.add(calcoloInteressi);
			    // Calcolo il totale degli interessi legali.
			    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			}
			// Calcolo i giorni nel l'ultimo anno dell'intervallo di Interessi legali
			// Creo una data al 1 gennaio dell'anno preso in considerazione
			Calendar inizioUltimoAnno = GregorianCalendar.getInstance();
			// Creo una data al 1 gennaio dell'anno preso in considerazione
			inizioUltimoAnno.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.JANUARY, 1, 0, 0, 0);
			// Calcolo giorni civili e giorni interessi
			giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			giorniInteressi = getDifferenzaGiorni(inizioUltimoAnno, dataFineInteressiLegaliCalendar);
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			if (giorniInteressi != 1) {
			    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili))
				    .divide(new BigDecimal(giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
					    BigDecimal.ROUND_HALF_UP), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
				    BigDecimal.ROUND_HALF_UP);
			}
			// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			CalcoloInteressiLegali calcoloInteressi2 = new CalcoloInteressiLegali();
			calcoloInteressi2.setImporto(importo);
			calcoloInteressi2.setTassoPercentuale(percentualeInteresse);
			calcoloInteressi2.setGiorni(giorniInteressi);
			calcoloInteressi2.setInteressi(importoInteressiLegaliTemp);
			calcoloInteressi2.setDataFine(dataFineInteressiLegaliCalendar.getTime());
			calcoloInteressi2.setDataInizio(inizioUltimoAnno.getTime());
			// Aggiungo alla lista l'oggetto
			calcoloInteressiLegaliList.add(calcoloInteressi2);
			// Calcolo il totale degli interessi legali.
			importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
		    }
		}
	    } else {
		// Controllo se la data inizio è contenuto nell'intervallo(data inizio / data fine) nella riga di
		// Interessi
		// legali
		if ((dataInizio.compareTo(interessiLegali.getDataInizio()) >= 0 && (interessiLegali.getDataFine() == null || dataInizio
			.compareTo(interessiLegali.getDataFine()) <= 0))) {
		    // creo i Calendar per data inizio e data fine
		    Calendar dataInizioInteressiLegaliCalendar = GregorianCalendar.getInstance();
		    dataInizioInteressiLegaliCalendar.setTime(interessiLegali.getDataInizio());
		    // Rappresenta la data fine dell'oggetto interessi legali
		    Calendar dataFineInteressiLegaliCalendar = GregorianCalendar.getInstance();
		    // controllo se la data di fine è diversa da null
		    // se è uguale a null allora setto la data impostata dal form
		    if (interessiLegali.getDataFine() == null) {
			dataFineInteressiLegaliCalendar.setTime(dataFine);
		    } else {
			dataFineInteressiLegaliCalendar.setTime(interessiLegali.getDataFine());
		    }
		    // Data inizio inserita nel form
		    Calendar dataInizioCal = GregorianCalendar.getInstance();
		    dataInizioCal.setTime(dataInizio);
		    // Controllo se le due date hanno lo stesso anno
		    if (dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) == dataFineInteressiLegaliCalendar.get(Calendar.YEAR)) {
			// Calcolo giorni civili e giorni interessi
			giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			giorniInteressi = getDifferenzaGiorni(dataInizioCal, dataFineInteressiLegaliCalendar);
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			if (giorniInteressi != 1) {
			    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili))
				    .divide(new BigDecimal(giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
					    BigDecimal.ROUND_HALF_UP), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
				    BigDecimal.ROUND_HALF_UP);
			}
			// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			calcoloInteressiLegali.setImporto(importo);
			calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			calcoloInteressiLegali.setGiorni(giorniInteressi);
			calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			calcoloInteressiLegali.setDataFine(dataFineInteressiLegaliCalendar.getTime());
			calcoloInteressiLegali.setDataInizio(dataInizioCal.getTime());
			// Aggiungo alla lista l'oggetto
			calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			// Calcolo il totale degli interessi legali.
			importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
		    } else {
			// Calcolo la differenza di anni tra data inizio e data fine
			int difYear = dataFineInteressiLegaliCalendar.get(Calendar.YEAR) - dataInizioCal.get(Calendar.YEAR);
			if (difYear == 0) {
			    // Calcolo giorni civili e giorni interessi
			    giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			    giorniInteressi = getDifferenzaGiorni(dataInizioCal, dataFineInteressiLegaliCalendar);
			    // calcolo l'importo secondo la formula:
			    // Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			    if (giorniInteressi != 1) {
				importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    }
			    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			    CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			    calcoloInteressiLegali.setImporto(importo);
			    calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			    calcoloInteressiLegali.setGiorni(giorniInteressi);
			    calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			    calcoloInteressiLegali.setDataFine(dataFineInteressiLegaliCalendar.getTime());
			    calcoloInteressiLegali.setDataInizio(dataInizioCal.getTime());
			    // Aggiungo alla lista l'oggetto
			    calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			    // Calcolo il totale degli interessi legali.
			    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			} else {
			    // Calcolo i giorni del primo anno di Interessi legali
			    // Calcolo giorni civili e giorni interessi
			    giorniCivili = dataInizioCal.getActualMaximum(Calendar.DAY_OF_YEAR);
			    Calendar dataFineTemp = GregorianCalendar.getInstance();
			    // Creo una data al 31 dicembre dell'anno preso in considerazione
			    dataFineTemp.set(dataInizioCal.get(Calendar.YEAR), GregorianCalendar.DECEMBER, 31, 0, 0, 0);
			    giorniInteressi = getDifferenzaGiorni(dataInizioCal, dataFineTemp);
			    // calcolo l'importo secondo la formula:
			    // Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			    if (giorniInteressi != 1) {
				importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    }
			    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			    CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			    calcoloInteressiLegali.setImporto(importo);
			    calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			    calcoloInteressiLegali.setGiorni(giorniInteressi);
			    calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			    calcoloInteressiLegali.setDataFine(dataFineTemp.getTime());
			    calcoloInteressiLegali.setDataInizio(dataInizioCal.getTime());
			    // Aggiungo alla lista l'oggetto
			    calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			    // Calcolo il totale degli interessi legali.
			    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			    int i = 0;
			    // Scorro gli anni completi tra le date dell'intervallo
			    for (i = 1; i < difYear; i++) {
				// Creo una data al 1 gennaio dell'anno preso in considerazione
				Calendar dataInizioTemp = GregorianCalendar.getInstance();
				dataInizioTemp.set(dataInizioCal.get(Calendar.YEAR) + i, GregorianCalendar.JANUARY, 1, 0, 0, 0);
				// Calcolo giorni civili e giorni interessi
				giorniCivili = dataInizioTemp.getActualMaximum(Calendar.DAY_OF_YEAR);
				giorniInteressi = dataInizioTemp.getActualMaximum(Calendar.DAY_OF_YEAR);
				// calcolo l'importo secondo la formula:
				// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di
				// interesse)
				if (giorniInteressi != 1) {
				    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					    giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				}
				// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
				CalcoloInteressiLegali calcoloInteressi = new CalcoloInteressiLegali();
				calcoloInteressi.setImporto(importo);
				calcoloInteressi.setTassoPercentuale(percentualeInteresse);
				calcoloInteressi.setGiorni(giorniInteressi);
				calcoloInteressi.setInteressi(importoInteressiLegaliTemp);
				Calendar dataFineTemp2 = GregorianCalendar.getInstance();
				dataFineTemp2.set(dataInizioCal.get(Calendar.YEAR) + i, GregorianCalendar.DECEMBER, 31, 0, 0, 0);
				calcoloInteressi.setDataFine(dataFineTemp2.getTime());
				calcoloInteressi.setDataInizio(dataInizioTemp.getTime());
				// Aggiungo alla lista l'oggetto
				calcoloInteressiLegaliList.add(calcoloInteressi);
				// Calcolo il totale degli interessi legali.
				importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			    }
			    // Calcolo i giorni nel l'ultimo anno dell'intervallo di Interessi legali
			    // Creo una data al 1 gennaio dell'anno preso in considerazione
			    Calendar inizioUltimoAnno = GregorianCalendar.getInstance();
			    // Creo una data al 1 gennaio dell'anno preso in considerazione
			    inizioUltimoAnno.set(dataInizioCal.get(Calendar.YEAR) + i, GregorianCalendar.JANUARY, 1, 0, 0, 0);
			    // Calcolo giorni civili e giorni interessi
			    giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			    giorniInteressi = getDifferenzaGiorni(inizioUltimoAnno, dataFineInteressiLegaliCalendar);
			    // calcolo l'importo secondo la formula:
			    // Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			    if (giorniInteressi != 1) {
				importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    }
			    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			    CalcoloInteressiLegali calcoloInteressi2 = new CalcoloInteressiLegali();
			    calcoloInteressi2.setImporto(importo);
			    calcoloInteressi2.setTassoPercentuale(percentualeInteresse);
			    calcoloInteressi2.setGiorni(giorniInteressi);
			    calcoloInteressi2.setInteressi(importoInteressiLegaliTemp);
			    calcoloInteressi2.setDataFine(dataFineInteressiLegaliCalendar.getTime());
			    calcoloInteressi2.setDataInizio(inizioUltimoAnno.getTime());
			    // Aggiungo alla lista l'oggetto
			    calcoloInteressiLegaliList.add(calcoloInteressi2);
			    // Calcolo il totale degli interessi legali.
			    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			}
		    }
		} else if (dataFine.compareTo(interessiLegali.getDataInizio()) >= 0
			&& (interessiLegali.getDataFine() == null || dataFine.compareTo(interessiLegali.getDataFine()) <= 0)) {
		    // Caso in cui la data finale è compresa nell'intervallo di Interessi legali
		    // Rappresenta la data inizio dell'oggetto Interessi legali
		    Calendar dataInizioInteressiLegaliCalendar = GregorianCalendar.getInstance();
		    dataInizioInteressiLegaliCalendar.setTime(interessiLegali.getDataInizio());
		    // Rappresenta la data fine dell'oggetto Interessi legali
		    Calendar dataFineInteressiLegaliCalendar = GregorianCalendar.getInstance();
		    // Controllo se la data finale è diversa da null
		    if (interessiLegali.getDataFine() != null) {
			dataFineInteressiLegaliCalendar.setTime(interessiLegali.getDataFine());
		    } else {
			dataFineInteressiLegaliCalendar.setTime(dataFine);
		    }
		    Calendar dataFineCal = GregorianCalendar.getInstance();
		    // Data fine (o data registrazione) inserita nel form
		    dataFineCal.setTime(dataFine);
		    percentualeInteresse = interessiLegali.getTassoPercentuale();
		    // Controllo se le due date hanno lo stesso anno
		    if (dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) == dataFineInteressiLegaliCalendar.get(Calendar.YEAR)) {
			// Calcolo giorni civili e giorni interessi
			giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			giorniInteressi = getDifferenzaGiorni(dataInizioInteressiLegaliCalendar, dataFineCal);
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			if (giorniInteressi != 1) {
			    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili))
				    .divide(new BigDecimal(giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
					    BigDecimal.ROUND_HALF_UP), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
				    BigDecimal.ROUND_HALF_UP);
			}
			// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			calcoloInteressiLegali.setImporto(importo);
			calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			calcoloInteressiLegali.setGiorni(giorniInteressi);
			calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			calcoloInteressiLegali.setDataFine(dataFineCal.getTime());
			calcoloInteressiLegali.setDataInizio(dataInizioInteressiLegaliCalendar.getTime());
			// Aggiungo alla lista l'oggetto
			calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			// Calcolo il totale degli interessi legali.
			importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
		    } else {
			// Calcolo la differenza di anni tra data inizio e data fine
			int difYear = dataFineCal.get(Calendar.YEAR) - dataInizioInteressiLegaliCalendar.get(Calendar.YEAR);
			if (difYear == 0) {
			    // Calcolo giorni civili e giorni interessi
			    giorniCivili = dataInizioInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			    giorniInteressi = getDifferenzaGiorni(dataInizioInteressiLegaliCalendar, dataFineCal);
			    // calcolo l'importo secondo la formula:
			    // Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			    if (giorniInteressi != 1) {
				importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    }
			    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			    CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			    calcoloInteressiLegali.setImporto(importo);
			    calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			    calcoloInteressiLegali.setGiorni(giorniInteressi);
			    calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			    calcoloInteressiLegali.setDataFine(dataFineCal.getTime());
			    calcoloInteressiLegali.setDataInizio(dataInizioInteressiLegaliCalendar.getTime());
			    // Aggiungo alla lista l'oggetto
			    calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			    // Calcolo il totale degli interessi legali.
			    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			} else {
			    // Calcolo giorni civili e giorni interessi
			    giorniCivili = dataInizioInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			    Calendar dataFineTemp = GregorianCalendar.getInstance();
			    // Creo una data al 31 dicembre dell'anno preso in considerazione
			    dataFineTemp.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR), GregorianCalendar.DECEMBER, 31, 0, 0, 0);
			    giorniInteressi = getDifferenzaGiorni(dataInizioInteressiLegaliCalendar, dataFineTemp);
			    // calcolo l'importo secondo la formula:
			    // Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			    if (giorniInteressi != 1) {
				importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    }
			    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			    CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			    calcoloInteressiLegali.setImporto(importo);
			    calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			    calcoloInteressiLegali.setGiorni(giorniInteressi);
			    calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			    calcoloInteressiLegali.setDataFine(dataFineTemp.getTime());
			    calcoloInteressiLegali.setDataInizio(dataInizioInteressiLegaliCalendar.getTime());
			    // Aggiungo alla lista l'oggetto
			    calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			    // Calcolo il totale degli interessi legali.
			    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			    int i = 0;
			    // Scorro gli anni completi tra le date dell'intervallo
			    for (i = 1; i < difYear; i++) {
				Calendar dataInizioTemp = GregorianCalendar.getInstance();
				// Creo una data al 1 gennaio dell'anno preso in considerazione
				dataInizioTemp.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.JANUARY, 1, 0, 0, 0);
				// Calcolo giorni civili e giorni interessi
				giorniCivili = dataInizioTemp.getActualMaximum(Calendar.DAY_OF_YEAR);
				giorniInteressi = dataInizioTemp.getActualMaximum(Calendar.DAY_OF_YEAR);
				// calcolo l'importo secondo la formula:
				// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di
				// interesse)
				if (giorniInteressi != 1) {
				    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					    giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				}
				// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
				CalcoloInteressiLegali calcoloInteressiLegali2 = new CalcoloInteressiLegali();
				calcoloInteressiLegali2.setImporto(importo);
				calcoloInteressiLegali2.setTassoPercentuale(percentualeInteresse);
				calcoloInteressiLegali2.setGiorni(giorniInteressi);
				calcoloInteressiLegali2.setInteressi(importoInteressiLegaliTemp);
				Calendar dataFineTemp2 = GregorianCalendar.getInstance();
				dataFineTemp2.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.DECEMBER, 31, 0, 0, 0);
				calcoloInteressiLegali2.setDataFine(dataFineTemp2.getTime());
				calcoloInteressiLegali2.setDataInizio(dataInizioTemp.getTime());
				// Aggiungo alla lista l'oggetto
				calcoloInteressiLegaliList.add(calcoloInteressiLegali2);
				// Calcolo il totale degli interessi legali.
				importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			    }
			    Calendar inizioUltimoAnno = GregorianCalendar.getInstance();
			    inizioUltimoAnno.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.JANUARY, 1, 0, 0, 0);
			    // Calcolo giorni civili e giorni interessi
			    giorniCivili = dataFineCal.getActualMaximum(Calendar.DAY_OF_YEAR);
			    giorniInteressi = getDifferenzaGiorni(inizioUltimoAnno, dataFineCal);
			    // calcolo l'importo secondo la formula:
			    // Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			    if (giorniInteressi != 1) {
				importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    }
			    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			    CalcoloInteressiLegali calcoloInteressiLegali3 = new CalcoloInteressiLegali();
			    calcoloInteressiLegali3.setImporto(importo);
			    calcoloInteressiLegali3.setTassoPercentuale(percentualeInteresse);
			    calcoloInteressiLegali3.setGiorni(giorniInteressi);
			    calcoloInteressiLegali3.setInteressi(importoInteressiLegaliTemp);
			    calcoloInteressiLegali3.setDataFine(dataFineCal.getTime());
			    calcoloInteressiLegali3.setDataInizio(inizioUltimoAnno.getTime());
			    // Aggiungo alla lista l'oggetto
			    calcoloInteressiLegaliList.add(calcoloInteressiLegali3);
			    // Calcolo il totale degli interessi legali.
			    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			}
		    }
		} else {
		    // Data inizio dell'oggetto Interessi legali
		    Calendar dataInizioInteressiLegaliCalendar = GregorianCalendar.getInstance();
		    dataInizioInteressiLegaliCalendar.setTime(interessiLegali.getDataInizio());
		    // Data fine dell'oggetto Interessi legali
		    Calendar dataFineInteressiLegaliCalendar = GregorianCalendar.getInstance();
		    if (interessiLegali.getDataFine() != null) {
			dataFineInteressiLegaliCalendar.setTime(interessiLegali.getDataFine());
		    } else {
			dataFineInteressiLegaliCalendar.setTime(dataFine);
		    }
		    if (dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) == dataFineInteressiLegaliCalendar.get(Calendar.YEAR)) {
			// Calcolo giorni civili e giorni interessi
			giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			giorniInteressi = getDifferenzaGiorni(dataInizioInteressiLegaliCalendar, dataFineInteressiLegaliCalendar);
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			if (giorniInteressi != 1) {
			    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili))
				    .divide(new BigDecimal(giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
					    BigDecimal.ROUND_HALF_UP), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
				    BigDecimal.ROUND_HALF_UP);
			}
			// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			calcoloInteressiLegali.setImporto(importo);
			calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			calcoloInteressiLegali.setGiorni(giorniInteressi);
			calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			calcoloInteressiLegali.setDataFine(dataFineInteressiLegaliCalendar.getTime());
			calcoloInteressiLegali.setDataInizio(dataInizioInteressiLegaliCalendar.getTime());
			// Aggiungo alla lista l'oggetto
			calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			// Calcolo il totale degli interessi legali.
			importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
		    } else {
			int difYear = dataFineInteressiLegaliCalendar.get(Calendar.YEAR) - dataInizioInteressiLegaliCalendar.get(Calendar.YEAR);
			// Calcolo giorni civili e giorni interessi
			giorniCivili = dataInizioInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			Calendar dataFineTemp = GregorianCalendar.getInstance();
			// Creo una data al 31 dicembre dell'anno preso in considerazione
			dataFineTemp.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR), GregorianCalendar.DECEMBER, 31, 0, 0, 0);
			giorniInteressi = getDifferenzaGiorni(dataInizioInteressiLegaliCalendar, dataFineTemp);
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			if (giorniInteressi != 1) {
			    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili))
				    .divide(new BigDecimal(giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
					    BigDecimal.ROUND_HALF_UP), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
				    BigDecimal.ROUND_HALF_UP);
			}
			// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			CalcoloInteressiLegali calcoloInteressiLegali = new CalcoloInteressiLegali();
			calcoloInteressiLegali.setImporto(importo);
			calcoloInteressiLegali.setTassoPercentuale(percentualeInteresse);
			calcoloInteressiLegali.setGiorni(giorniInteressi);
			calcoloInteressiLegali.setInteressi(importoInteressiLegaliTemp);
			calcoloInteressiLegali.setDataFine(dataFineTemp.getTime());
			calcoloInteressiLegali.setDataInizio(dataInizioInteressiLegaliCalendar.getTime());
			// Aggiungo alla lista l'oggetto
			calcoloInteressiLegaliList.add(calcoloInteressiLegali);
			// Calcolo il totale degli interessi legali.
			importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			int i = 0;
			// Scorro gli anni completi tra le date dell'intervallo
			for (i = 1; i < difYear; i++) {
			    Calendar dataInizioTemp = GregorianCalendar.getInstance();
			    // Creo una data al 1 gennaio dell'anno preso in considerazione
			    dataInizioTemp.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.JANUARY, 1, 0, 0, 0);
			    // Calcolo giorni civili e giorni interessi
			    giorniCivili = dataInizioTemp.getActualMaximum(Calendar.DAY_OF_YEAR);
			    giorniInteressi = dataInizioTemp.getActualMaximum(Calendar.DAY_OF_YEAR);
			    // calcolo l'importo secondo la formula:
			    // Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			    if (giorniInteressi != 1) {
				importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
				importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili)).divide(new BigDecimal(
					giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP),
					WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    }
			    // Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			    CalcoloInteressiLegali calcoloInteressiLegali2 = new CalcoloInteressiLegali();
			    calcoloInteressiLegali2.setImporto(importo);
			    calcoloInteressiLegali2.setTassoPercentuale(percentualeInteresse);
			    calcoloInteressiLegali2.setGiorni(giorniInteressi);
			    calcoloInteressiLegali2.setInteressi(importoInteressiLegaliTemp);
			    Calendar dataFineTemp2 = GregorianCalendar.getInstance();
			    dataFineTemp2.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.DECEMBER, 31, 0, 0, 0);
			    calcoloInteressiLegali2.setDataFine(dataFineTemp2.getTime());
			    calcoloInteressiLegali2.setDataInizio(dataInizioTemp.getTime());
			    // Aggiungo alla lista l'oggetto
			    calcoloInteressiLegaliList.add(calcoloInteressiLegali2);
			    // Calcolo il totale degli interessi legali.
			    importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
			}
			// Creo una data al 1 gennaio dell'anno preso in considerazione
			Calendar inizioUltimoAnno = GregorianCalendar.getInstance();
			inizioUltimoAnno.set(dataInizioInteressiLegaliCalendar.get(Calendar.YEAR) + i, GregorianCalendar.JANUARY, 1, 0, 0, 0);
			// Calcolo giorni civili e giorni interessi
			giorniCivili = dataFineInteressiLegaliCalendar.getActualMaximum(Calendar.DAY_OF_YEAR);
			giorniInteressi = getDifferenzaGiorni(inizioUltimoAnno, dataFineInteressiLegaliCalendar);
			// calcolo l'importo secondo la formula:
			// Interessi maturati = Imponibile * (percentuale anno) / (gg anno civile/gg di interesse)
			if (giorniInteressi != 1) {
			    importoInteresse = importo.multiply(percentualeInteresse).divide(new BigDecimal(100),
				    WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI, BigDecimal.ROUND_HALF_UP);
			    importoInteressiLegaliTemp = importoInteresse.divide((new BigDecimal(giorniCivili))
				    .divide(new BigDecimal(giorniInteressi), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
					    BigDecimal.ROUND_HALF_UP), WebConstants.NUMERO_CIFRE_DECIMALI_INTERESSI_CONTABILI,
				    BigDecimal.ROUND_HALF_UP);
			}
			// Creo l'oggetto calcoloInteressiLegali e gli setto i valori.
			CalcoloInteressiLegali calcoloInteressiLegali3 = new CalcoloInteressiLegali();
			calcoloInteressiLegali3.setImporto(importo);
			calcoloInteressiLegali3.setTassoPercentuale(percentualeInteresse);
			calcoloInteressiLegali3.setGiorni(giorniInteressi);
			calcoloInteressiLegali3.setInteressi(importoInteressiLegaliTemp);
			calcoloInteressiLegali3.setDataFine(dataFineInteressiLegaliCalendar.getTime());
			calcoloInteressiLegali3.setDataInizio(inizioUltimoAnno.getTime());
			// Aggiungo alla lista l'oggetto
			calcoloInteressiLegaliList.add(calcoloInteressiLegali3);
			// Calcolo il totale degli interessi legali.
			importoInteressiLegaliTot = importoInteressiLegaliTot.add(importoInteressiLegaliTemp);
		    }
		}
	    }
	}
	return calcoloInteressiLegaliList;
    }

    /**
     * Metodo per calcolare la differenza in giorni
     * 
     * @param dataiinizio
     * @param datafine
     * @return
     */
    private int getDifferenzaGiorni(Calendar dataiinizio, Calendar datafine) {

	long dallaDataMilliSecondi = dataiinizio.getTimeInMillis();
	long allaDataMilliSecondi = datafine.getTimeInMillis();
	// differenza in millisecondi:
	long diff = allaDataMilliSecondi - dallaDataMilliSecondi;
	// 1 giorno medio = 1000*60*60*24 ms
	// = 86400000 ms
	double durataGiornoMedio = (24.0 * 60.0 * 60.0 * 1000.0);
	// differenza in giorni:
	double diffGiorni = Math.round(diff / durataGiornoMedio);
	// Sommo un giorno in più perchè il calcolo esclude i giorni iniziali e finali.
	return (int) diffGiorni + 1;
    }
}
