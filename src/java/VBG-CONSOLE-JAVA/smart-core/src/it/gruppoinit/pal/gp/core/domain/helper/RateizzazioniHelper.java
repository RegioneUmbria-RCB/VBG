/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.TipiScadenzaHelper;
import it.gruppoinit.pal.gp.core.domain.CalcoloInteressiLegali;
import it.gruppoinit.pal.gp.core.domain.ImportiRateizzati;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper per il calcolo delle rateizzazioni
 * 
 * @author francescop
 * @author gianpaolot
 * 
 */
public class RateizzazioniHelper {

    private Integer nrorate;
    private BigDecimal[] ripartizionerate;
    private Integer[] frequenzarate;
    private TipiScadenza scadenzarate;
    private BigDecimal[] interessirate;
    private boolean flagInteressiLegali;
    private Integer tipoAnatocismo;
    private InteressiLegaliService interessiLegaliService;
    private static final Logger log = LoggerFactory.getLogger(RateizzazioniHelper.class);

    /**
     * Costruttore che setta le proprietà di oneritipirateizzazioni
     * 
     * @param oneritipirateizzazione
     * @param interessiLegaliService
     * 
     */
    public RateizzazioniHelper(Oneritipirateizzazione oneritipirateizzazione, InteressiLegaliService interessiLegaliService) {

	Integer numerorate = oneritipirateizzazione.getNumerorate();
	Integer[] frequenzarate;
	BigDecimal[] interessirate;
	BigDecimal[] ripartizionerate;
	Integer scadenzarateInteger;
	TipiScadenza tipiScadenza = new TipiScadenza();
	// Controllo se sono stati settati i tipi di raetizzazione.
	// Controllo numero di rate.
	// Se le rate sono zero allora ne setto una di default.
	if (numerorate == 0) {
	    numerorate = 1;
	    frequenzarate = new Integer[numerorate];
	    frequenzarate[0] = 30;
	    ripartizionerate = new BigDecimal[numerorate];
	    ripartizionerate[0] = new BigDecimal(100);
	    scadenzarateInteger = TipiScadenzaHelper.FINE_MESE;
	    interessirate = new BigDecimal[numerorate];
	    interessirate[0] = new BigDecimal(0);
	} else {
	    ripartizionerate = oneritipirateizzazione.getRipartizionerateArray();
	    numerorate = oneritipirateizzazione.getNumerorate();
	    frequenzarate = oneritipirateizzazione.getFrequenzarateArray();
	    interessirate = oneritipirateizzazione.getInteressirateArray();
	    if (frequenzarate == null) {
		Integer[] freqrate = new Integer[numerorate];
		freqrate[0] = 0;
		for (int i = 1; i < freqrate.length; i++) {
		    freqrate[i] = 30;
		}
		frequenzarate = freqrate;
	    }
	    scadenzarateInteger = oneritipirateizzazione.getScadenzarate().getId();
	}
	tipiScadenza.setId(scadenzarateInteger);
	this.nrorate = numerorate;
	this.ripartizionerate = ripartizionerate;
	this.frequenzarate = frequenzarate;
	this.scadenzarate = tipiScadenza;
	this.interessirate = interessirate;
	this.flagInteressiLegali = oneritipirateizzazione.getFlagInteressiLegali();
	this.tipoAnatocismo = oneritipirateizzazione.getTipoAnatocismo();
	this.interessiLegaliService = interessiLegaliService;
    }

    public Integer getNrorate() {

	return nrorate;
    }

    public void setNrorate(Integer nrorate) {

	this.nrorate = nrorate;
    }

    public BigDecimal[] getRipartizionerate() {

	return ripartizionerate;
    }

    public void setRipartizionerate(BigDecimal[] ripartizionerate) {

	this.ripartizionerate = ripartizionerate;
    }

    public Integer[] getFrequenzarate() {

	return frequenzarate;
    }

    public void setFrequenzarate(Integer[] frequenzarate) {

	this.frequenzarate = frequenzarate;
    }

    public TipiScadenza getScadenzarate() {

	return scadenzarate;
    }

    public void setScadenzarate(TipiScadenza scadenzarate) {

	this.scadenzarate = scadenzarate;
    }

    public BigDecimal[] getInteressirate() {

	return interessirate;
    }

    public void setInteressirate(BigDecimal[] interessirate) {

	this.interessirate = interessirate;
    }

    public Boolean getFlagInteressiLegali() {

	return flagInteressiLegali;
    }

    public Integer getTipoAnatocismo() {

	return tipoAnatocismo;
    }

    public void setTipoAnatocismo(Integer tipoAnatocismo) {

	this.tipoAnatocismo = tipoAnatocismo;
    }

    public void setFlagInteressiLegali(boolean flagInteressiLegali) {

	this.flagInteressiLegali = flagInteressiLegali;
    }

    public void setInteressiLegaliService(InteressiLegaliService interessiLegaliService) {

	this.interessiLegaliService = interessiLegaliService;
    }

    public InteressiLegaliService getInteressiLegaliService() {

	return interessiLegaliService;
    }

    /**
     * Metodo che rateizza un importo.
     * 
     * @param dataregistrazione
     *            In caso di Interessi legali rappresenta la data di fine
     * @param dataInizio
     *            In caso di Interessi legali passare come parametro la data di inizio
     * @param importo
     *            l'importo da rateizzare<br />
     *            N.B. per il calcolo degli interessi viene considerata l'IVA applicata al valore di @see
     *            WebConstants.CONST_IVA
     * @see WebConstants.CONST_IVA
     * 
     * @return lista di registrazioni importi.
     */
    public List<ImportiRateizzati> rateizzaImporto(BigDecimal importo, Date dataregistrazione, Date dataInizio) {

	return rateizzaImporto(importo, dataregistrazione, dataInizio, null);
    }

    /**
     * Metodo che rateizza un importo.
     * 
     * @param dataregistrazione
     *            In caso di Interessi legali rappresenta la data di fine
     * @param dataInizio
     *            In caso di Interessi legali passare come parametro la data di inizio
     * @param importo
     *            l'importo da rateizzare
     * @param iva
     *            l'iva applicata all'import utile in caso di calcolo interessi
     * @return lista di registrazioni importi.
     */
    public List<ImportiRateizzati> rateizzaImporto(BigDecimal importo, Date dataregistrazione, Date dataInizio, Integer iva) {

	List<ImportiRateizzati> importiRateizzatiList = new ArrayList<ImportiRateizzati>();
	// Importo senza IVA 7 //FIXME al momento l'iva non può essere una costante gli interessi li calcolo sull'importo ivato 
	BigDecimal importoSenzaIva = new BigDecimal(importo.doubleValue());//importo.subtract(importo.multiply(new BigDecimal(20)).divide(new BigDecimal(100),WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP));
	// Importo da rateizzare
	BigDecimal importoIndividuale = importo;
	// /Recupero INTERESSI LEGALI
	InteressiLegaliHelper interessiLegaliHelper = new InteressiLegaliHelper(this.interessiLegaliService);
	// //////////////////////////
	// Importi rateizzati
	BigDecimal[] importiRateizzatiSenzaIva = getImportiRateizzati(importoSenzaIva);
	// Interessi legali rateizzati
	BigDecimal[] importiRateizzatiConIva = getImportiRateizzati(importoIndividuale);
	Date scadenza = null;
	Date dataTemp = dataregistrazione;
	boolean isUnicoInteresse = isUnicoInteresse();
	BigDecimal importoRateAssegnato = new BigDecimal(0);
	// Determino le rate degli importi
	for (int i = 0; i < this.nrorate; i++) {
	    ImportiRateizzati rateizzati = new ImportiRateizzati();
	    // setto numero della rata
	    rateizzati.setNumerorata(i + 1);
	    BigDecimal valoreInteresse = BigDecimal.ZERO;
	    // .. IMPORTANTE SETTARE LA SCALA DEL DECIMALE ALTRIMENTI DA ERRORE
	    // .. IL VALIDATORE DELL'OGGETTO DI DOMINIO
	    // ///////////calcolo importo rateizzato//////////////
	    if (log.isDebugEnabled()) {
		log.debug("rateizzaImporto(): Rata=" + (i + 1));
		log.debug("rateizzaImporto(): riga importo senza iva=" + importoSenzaIva);
		log.debug("rateizzaImporto(): percentuale rata=" + this.ripartizionerate[i]);
	    }
	    // Importo della singola rata
	    BigDecimal importoRateSenzaIva = importiRateizzatiSenzaIva[i];
	    BigDecimal importoRateConIva = importiRateizzatiConIva[i];
	    // Controllo se il flag interessi legali è true
	    scadenza = getCalcoloScadenza(frequenzarate[i], dataTemp, i);
	    rateizzati.setScadenza(scadenza);
	    if (log.isDebugEnabled()) {
		log.debug("rateizzaImporto(): data scadenza=" + scadenza.toString());
	    }
	    // Aggiorno la data temporanea che utilizzo per il calcolo della scadenza della prossima rata
	    dataTemp = scadenza;
	    if (this.flagInteressiLegali) {
		// BigDecimal interessiLegaliRata = BigDecimal.ZERO;
		List<CalcoloInteressiLegali> interessiLegaliList = interessiLegaliHelper
			.getInteressiLegali(importoRateSenzaIva, scadenza, dataInizio);
		// Scorro la lista e calcolo gli interessi legali totali
		for (CalcoloInteressiLegali calcoloInteressiLegali : interessiLegaliList) {
		    valoreInteresse = valoreInteresse.add(calcoloInteressiLegali.getInteressi());
		}
		valoreInteresse = valoreInteresse.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
		importoRateSenzaIva = importoRateSenzaIva.add(valoreInteresse);
	    } else {
		// se abbiamo un unico interesse settatto per tutte le rate
		if (isUnicoInteresse) {
		    // L'ultima rata viene calcolata come differenza della somma delle n-1 rate e l'importo totale delle
		    // registrazioni
		    if (i == this.nrorate - 1) {
			BigDecimal importTemp = importoRateSenzaIva;
			// BigDecimal importoIndividualeConInteresseTot = importoSenzaIva.add(importoSenzaIva.multiply(this.interessirate[i]).divide(new BigDecimal(100)));
			BigDecimal importoIndividualeConInteresseTot = importoSenzaIva.add(importo.multiply(this.interessirate[i]).divide(
				new BigDecimal(100)));
			importoRateSenzaIva = importoIndividualeConInteresseTot.subtract(importoRateAssegnato);
			importoRateSenzaIva = importoRateSenzaIva.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
			valoreInteresse = importoRateSenzaIva.subtract(importTemp);
		    } else {
			// valoreInteresse = (importoRateSenzaIva.multiply(this.interessirate[i])).divide(new BigDecimal(100));
			valoreInteresse = (importoRateConIva.multiply(this.interessirate[i])).divide(new BigDecimal(100));
			importoRateSenzaIva = importoRateSenzaIva.add(valoreInteresse);
			importoRateSenzaIva = importoRateSenzaIva.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
			importoRateAssegnato = importoRateAssegnato.add(importoRateSenzaIva);
		    }
		}
		// se abbiamo interesse variabile per ogni rata
		if (this.interessirate != null && !isUnicoInteresse) {
		    // valoreInteresse = (importoRateSenzaIva.multiply(this.interessirate[i])).divide(new BigDecimal(100));
		    valoreInteresse = (importoRateConIva.multiply(this.interessirate[i])).divide(new BigDecimal(100));
		    importoRateSenzaIva = importoRateSenzaIva.add(valoreInteresse);
		    importoRateSenzaIva = importoRateSenzaIva.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
		    if (log.isDebugEnabled()) {
			log.debug("rateizzaImporto(): interessi rata=" + this.interessirate[i]);
		    }
		}
	    }
	    // ////////fine calcolo importo rateizzato////////////
	    // Setto il valore di interesse
	    valoreInteresse = valoreInteresse.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	    rateizzati.setImportoInteresse(valoreInteresse);
	    // Setto l'importo rateizzato con IVA
	    // Nel caso in cui non abbiamo interesse da applicare setto l'importo calcolato dal metodo
	    // getImportiRateizzati
	    rateizzati.setImportoRateizzato(importoRateConIva.add(valoreInteresse));
	    // Calcolo della data di scadenza
	    // scadenza = getCalcoloScadenza(frequenzarate[i], dataTemp, i);
	    // Setto l'importo rateizzato senza l'interesse
	    rateizzati.setImportoRateizzatoSenzaInteresse(importoRateConIva);
	    importiRateizzatiList.add(rateizzati);
	}
	return importiRateizzatiList;
    }

    /**
     * metodo per controllare se la rateizzazione ha un solo interesse per tutte le rate
     * 
     * @return boolean
     */
    private boolean isUnicoInteresse() {

	boolean isUnicoInteresse = true;
	// Controllo se gli interessi sono uguali per tutte le rate
	if (this.interessirate != null) {
	    for (int i = 1; i < this.interessirate.length; i++) {
		if (this.interessirate[0].compareTo(this.interessirate[i]) != 0) {
		    isUnicoInteresse = false;
		    break;
		}
	    }
	} else {
	    isUnicoInteresse = false;
	}
	return isUnicoInteresse;
    }

    /**
     * Metodo per il calcolo della scadenza
     * 
     * @param frequenzarate
     * @param data
     * @return
     */
    private Date getCalcoloScadenza(Integer frequenzarate, Date data, int numeroRata) {

	Date dataPeriodicita = null;
	Date scadenza = null;
	Calendar cal = GregorianCalendar.getInstance();
	cal.setTime(data);
	if (log.isDebugEnabled()) {
	    log.debug("getCalcoloScadenza: frequenzarate={}, data={}, numeroRata={}", new Object[] { frequenzarate, cal.getTime(), numeroRata });
	}
	double resto = (double) frequenzarate % WebConstants.NUMERO_GIORNI_CONTABILI;
	if (resto == 0) {
	    if (numeroRata > 0) {
		int numeroMesi = frequenzarate / WebConstants.NUMERO_GIORNI_CONTABILI;
		cal.add(Calendar.MONTH, numeroMesi);
	    }
	} else {
	    cal.add(Calendar.DAY_OF_MONTH, frequenzarate);
	}
	dataPeriodicita = cal.getTime();
	scadenza = TipiScadenzaHelper.trovaScadenza(dataPeriodicita, this.scadenzarate.getId(), numeroRata);
	if (log.isDebugEnabled()) {
	    log.debug("getCalcoloScadenza: scadenza={}", scadenza);
	}
	return scadenza;
    }

    public static void main(String[] args) {

	Oneritipirateizzazione oneritipirateizzazione = new Oneritipirateizzazione();
	RateizzazioniHelper rh = new RateizzazioniHelper(oneritipirateizzazione, null);
	TipiScadenza ts = new TipiScadenza();
	// TIPO_STADENZA valori:
	// 0 - fine mese
	// 1 - 15 del mese
	// 2 - Fine mese ( esclusa la 1° rata )
	// 3 - 15 del mese ( esclusa la 1° rata )
	// 4 - Lascia inalterato
	// 5 - 15 del mese successivo
	// 6 - 15 del mese successivo ( esclusa la 1° rata )
	ts.setId(4);
	rh.setScadenzarate(ts);
	Date data = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	System.out.println("data: " + sdf.format(data));
	Integer[] frequenzarate = new Integer[] { 30, 30, 30 };
	for (int i = 0; i < frequenzarate.length; i++) {
	    Date dataScadenza = rh.getCalcoloScadenza(frequenzarate[i], data, i);
	    System.out.println("rata: " + i + ",data scadenza: " + sdf.format(dataScadenza));
	    data = dataScadenza;
	}
    }

    /**
     * Metodo che restituisce la ripartizione delle rate a partire dall'importo totale.
     * 
     * @param importoTotale
     * @return
     */
    private BigDecimal[] getImportiRateizzati(BigDecimal importoTotale) {

	// Array restituito dal metodo
	BigDecimal[] importiRateizzati = new BigDecimal[this.nrorate];
	// Se this.ripartizionerate è nullo allora divido l'importo totale per il numero delle rate
	// Altrimenti determino le rate in base alla proprietà this.ripartizionerate
	if (this.ripartizionerate == null) {
	    // Calcolo delle n-1 rate
	    BigDecimal importoSingolaRata = importoTotale.divide(new BigDecimal(this.nrorate), WebConstants.NUMERO_CIFRE_DECIMALI,
		    BigDecimal.ROUND_HALF_UP);
	    for (int i = 0; i < (this.nrorate - 1); i++) {
		importiRateizzati[i] = importoSingolaRata;
	    }
	    // calcolo dell'ultima rate(n-esima) come differenza dell'importo totale meno la somma delle n-1 rate
	    BigDecimal ultimaRata = new BigDecimal(0);
	    ultimaRata = importoTotale.subtract(importoSingolaRata.multiply(new BigDecimal(this.nrorate - 1)));
	    importiRateizzati[this.nrorate - 1] = ultimaRata;
	} else {
	    // Calcolo delle n-1 rate
	    BigDecimal importoTotAssegnato = new BigDecimal(0);
	    for (int i = 0; i < (this.nrorate - 1); i++) {
		importiRateizzati[i] = (importoTotale.multiply(this.ripartizionerate[i])).divide(new BigDecimal(100),
			WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
		importoTotAssegnato = importoTotAssegnato.add(importiRateizzati[i]);
	    }
	    // calcolo dell'ultima rate(n-esima) come differenza dell'importo totale meno la somma delle n-1 rate
	    BigDecimal ultimaRata = new BigDecimal(0);
	    ultimaRata = importoTotale.subtract(importoTotAssegnato);
	    importiRateizzati[this.nrorate - 1] = ultimaRata;
	}
	return importiRateizzati;
    }
    //    /**
    //     * Metodo per calcolare gli interessi legali rateizzati.
    //     * 
    //     * @param interessiLegali
    //     * @return
    //     */
    //    private BigDecimal[] getInteressiLegaliRateizzati(BigDecimal interessiLegali) {
    //
    //	// Array restituito dal metodo
    //	BigDecimal[] interessiLegaliRateizzati = new BigDecimal[this.nrorate];
    //	// Se this.ripartizionerate è nullo allora divido l'importo totale per il numero delle rate
    //	// Altrimenti determino le rate in base alla proprietà this.ripartizionerate
    //	if (this.ripartizionerate == null) {
    //	    // Calcolo delle n-1 rate
    //	    BigDecimal importoSingolaRata = interessiLegali.divide(new BigDecimal(this.nrorate), WebConstants.NUMERO_CIFRE_DECIMALI,
    //		    BigDecimal.ROUND_HALF_UP);
    //	    for (int i = 0; i < (this.nrorate - 1); i++) {
    //		interessiLegaliRateizzati[i] = importoSingolaRata;
    //	    }
    //	    // calcolo dell'ultima rate(n-esima) come differenza dell'importo totale meno la somma delle n-1 rate
    //	    BigDecimal ultimaRata = new BigDecimal(0);
    //	    ultimaRata = interessiLegali.subtract(importoSingolaRata.multiply(new BigDecimal(this.nrorate - 1)));
    //	    interessiLegaliRateizzati[this.nrorate - 1] = ultimaRata;
    //	} else {
    //	    // Calcolo delle n-1 rate
    //	    BigDecimal importoTotAssegnato = new BigDecimal(0);
    //	    for (int i = 0; i < (this.nrorate - 1); i++) {
    //		interessiLegaliRateizzati[i] = (interessiLegali.multiply(this.ripartizionerate[i])).divide(new BigDecimal(100),
    //			WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
    //		importoTotAssegnato = importoTotAssegnato.add(interessiLegaliRateizzati[i]);
    //	    }
    //	    // calcolo dell'ultima rate(n-esima) come differenza dell'importo totale meno la somma delle n-1 rate
    //	    BigDecimal ultimaRata = new BigDecimal(0);
    //	    ultimaRata = interessiLegali.subtract(importoTotAssegnato);
    //	    interessiLegaliRateizzati[this.nrorate - 1] = ultimaRata;
    //	}
    //	return interessiLegaliRateizzati;
    //    }
}
