/**
 * 
 */
package it.gruppoinit.pal.gp.core.domain.helper;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.TipiScadenzaHelper;
import it.gruppoinit.pal.gp.core.domain.CalcoloInteressiLegali;
import it.gruppoinit.pal.gp.core.domain.ImportiRateizzati;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.TipologiaRateizzazioneEnum;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.DataScadenzaResolver;
import it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione.tipiscadenze.TipoScadenzaEnum;
import it.gruppoinit.pal.gp.core.service.InteressiLegaliService;
import it.gruppoinit.pal.gp.core.service.helper.PeriodicitaEnum;
import it.gruppoinit.pal.gp.core.service.helper.RateizzaAmmortamentoFranceseHelper;

/**
 * Helper per il calcolo delle rateizzazioni
 * 
 * @author francescop
 * @author gianpaolot
 * 
 */
public class RateizzazioniHelper {

    private Integer nrorate;
    private BigDecimal interesseUnico;
    private BigDecimal[] ripartizionerate;
    private Integer[] frequenzarate;
    private TipiScadenza scadenzarate;
    private BigDecimal[] interessirate;
    private boolean flagInteressiLegali;
    private Integer tipoAnatocismo;
    private InteressiLegaliService interessiLegaliService;
    private PeriodicitaEnum periodicitaEnum;
    private TipologiaRateizzazioneEnum tipologiaRateizzazioneEnum;
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
	    log.debug("RateizzazioniHelper# numero rate : 0");
	    numerorate = 1;
	    frequenzarate = new Integer[numerorate];
	    frequenzarate[0] = 30;
	    ripartizionerate = new BigDecimal[numerorate];
	    ripartizionerate[0] = new BigDecimal(100);
	    scadenzarateInteger = TipiScadenzaHelper.FINE_MESE;
	    interessirate = new BigDecimal[numerorate];
	    interessirate[0] = new BigDecimal(0);
	} else {
	    log.debug("RateizzazioniHelper# numero rate : {}", numerorate);
	    ripartizionerate = oneritipirateizzazione.getRipartizionerateArray();
	    numerorate = oneritipirateizzazione.getNumerorate();
	    log.debug("RateizzazioniHelper# frequenza array rate {}", oneritipirateizzazione.getFrequenzarateArray());
	    frequenzarate = oneritipirateizzazione.getFrequenzarateArray();
	    log.debug("RateizzazioniHelper# interessi array rate {}", oneritipirateizzazione.getInteressirateArray());
	    interessirate = oneritipirateizzazione.getInteressirateArray();
	    if (frequenzarate == null) {
		log.debug("RateizzazioniHelper# Frequenza rate uguale a NULL");
		Integer[] freqrate = new Integer[numerorate];
		freqrate[0] = 0;
		for (int i = 1; i < freqrate.length; i++) {
		    freqrate[i] = 30;
		}
		frequenzarate = freqrate;
	    }
	    log.debug("RateizzazioniHelper# Scadenzarate rate id: {}", oneritipirateizzazione.getScadenzarate().getId());
	    scadenzarateInteger = oneritipirateizzazione.getScadenzarate().getId();
	}
	tipiScadenza.setId(scadenzarateInteger);
	log.debug("RateizzazioniHelper# Setto numero rate al valore {}", numerorate);
	this.nrorate = numerorate;
	if (StringUtils.isNotBlank(oneritipirateizzazione.getInteressirate())
		&& !StringUtils.contains(oneritipirateizzazione.getInteressirate(), ";")) {
	    log.debug("RateizzazioniHelper# Calcolo interesse unico...");
	    String interesse_unico = StringUtils.replaceChars(oneritipirateizzazione.getInteressirate(), ",", ".");
	    log.debug("RateizzazioniHelper# Interesse unico: {}", interesse_unico);
	    this.interesseUnico = new BigDecimal(interesse_unico);
	} else {
	    log.info("RateizzazioniHelper# Interesse multiplo. Es 1;3;7");
	}
	this.ripartizionerate = ripartizionerate;
	this.frequenzarate = frequenzarate;
	this.scadenzarate = tipiScadenza;
	this.interessirate = interessirate;
	log.debug("RateizzazioniHelper# set flagInteressiLegali");
	this.flagInteressiLegali = oneritipirateizzazione.getFlagInteressiLegali();
	log.debug("RateizzazioniHelper# set tipoAnatocismo");
	this.tipoAnatocismo = oneritipirateizzazione.getTipoAnatocismo();
	this.interessiLegaliService = interessiLegaliService;
	log.debug("RateizzazioniHelper# set periodicitaEnum");
	this.periodicitaEnum = getPeriodicitaEnumDaFrequenzaRate();
	this.tipologiaRateizzazioneEnum = getTipologiaRateizzazioniEnum(oneritipirateizzazione.getTipologiaRateizzazione());
    }

    public Integer getNrorate() {

	return nrorate;
    }

    public void setNrorate(Integer nrorate) {

	this.nrorate = nrorate;
    }

    public BigDecimal getInteresseUnico() {

	return interesseUnico;
    }

    public void setInteresseUnico(BigDecimal interesseUnico) {

	this.interesseUnico = interesseUnico;
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

    public TipologiaRateizzazioneEnum getTipologiaRateizzazioneEnum() {

	return tipologiaRateizzazioneEnum;
    }

    public void setTipologiaRateizzazioneEnum(TipologiaRateizzazioneEnum tipologiaRateizzazioneEnum) {

	this.tipologiaRateizzazioneEnum = tipologiaRateizzazioneEnum;
    }

    public PeriodicitaEnum getPeriodicitaEnum() {

	return periodicitaEnum;
    }

    public void setPeriodicitaEnum(PeriodicitaEnum periodicitaEnum) {

	this.periodicitaEnum = periodicitaEnum;
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
    public List<ImportiRateizzati> rateizzaImporto(BigDecimal importo, Date dataregistrazione, Date dataInizio, PeriodicitaEnum periodicita,
	    TipologiaRateizzazioneEnum tipologiaRateizzazioneEnum) {

	return rateizzaImporto(importo, dataregistrazione, dataInizio, null, periodicita, tipologiaRateizzazioneEnum);
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
    public List<ImportiRateizzati> rateizzaImporto(BigDecimal importo, Date dataregistrazione, Date dataInizio, Integer iva,
	    PeriodicitaEnum periodicita, TipologiaRateizzazioneEnum tipologiaRateizzazioneEnum) {

	List<ImportiRateizzati> importiRateizzatiList = new ArrayList<ImportiRateizzati>();
	boolean isUnicoInteresse = isUnicoInteresse();
	InteressiLegaliHelper interessiLegaliHelper = new InteressiLegaliHelper(this.interessiLegaliService);
	switch (tipologiaRateizzazioneEnum) {
	case DEFAULT:
	    log.debug("rateizzaImporto# Rateizzazione di tipo DEFAULT ");
	    importiRateizzatiList = calcolaRateLogicaDefault(importo, dataregistrazione, dataInizio, isUnicoInteresse);
	    break;
	case AMMORTAMENTO_FRANCESE:
	    log.debug("rateizzaImporto# Rateizzazione di tipo AMMORTAMENTO_FRANCESE ");
	    // mantenere compatibilità con importo istruttoria
	    //se importo passato è zero devo comunque creare n rate da zero
	    if (!importo.equals(new BigDecimal(0))) {
		importiRateizzatiList = calcolaRateAmmortamentoFrancese(importo, this.interesseUnico, interessiLegaliHelper, dataregistrazione,
			dataInizio, isUnicoInteresse, periodicita);
	    } else {
		// metodo che calcola le rate a importo zero. Il prezzo istruttoria non è più sempre presnete, ma la rateizzazione anche se a zero 
		// viene chimata sempre da .NET
		importiRateizzatiList = calcolaRateImportoZero(this.nrorate, dataregistrazione);
	    }
	    break;
	default:
	    importiRateizzatiList = calcolaRateLogicaDefault(importo, dataregistrazione, dataInizio, isUnicoInteresse);
	    break;
	}
	//	// Importo senza IVA 7 //FIXME al momento l'iva non può essere una costante gli interessi li calcolo sull'importo ivato 
	//	BigDecimal importoSenzaIva = new BigDecimal(importo.doubleValue());//importo.subtract(importo.multiply(new BigDecimal(20)).divide(new BigDecimal(100),WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP));
	//	// Importo da rateizzare
	//	BigDecimal importoIndividuale = importo;
	//	// /Recupero INTERESSI LEGALI
	//	// //////////////////////////
	//	// Importi rateizzati
	//	BigDecimal[] importiRateizzatiSenzaIva = getImportiRateizzati(importoSenzaIva);
	//	// Interessi legali rateizzati
	//	BigDecimal[] importiRateizzatiConIva = getImportiRateizzati(importoIndividuale);
	/// PORTATE DENTRO AL METODO ////////
	//Date scadenza = null;
	//Date dataTemp = dataregistrazione;
	//BigDecimal importoRateAssegnato = new BigDecimal(0);
	//	// Determino le rate degli importi
	//	for (int i = 0; i < this.nrorate; i++) {
	//	    ImportiRateizzati rateizzati = new ImportiRateizzati();
	//	    // setto numero della rata
	//	    rateizzati.setNumerorata(i + 1);
	//	    BigDecimal valoreInteresse = BigDecimal.ZERO;
	//	    // .. IMPORTANTE SETTARE LA SCALA DEL DECIMALE ALTRIMENTI DA ERRORE
	//	    // .. IL VALIDATORE DELL'OGGETTO DI DOMINIO
	//	    // ///////////calcolo importo rateizzato//////////////
	//	    if (log.isDebugEnabled()) {
	//		log.debug("rateizzaImporto(): Rata=" + (i + 1));
	//		log.debug("rateizzaImporto(): riga importo senza iva=" + importoSenzaIva);
	//		log.debug("rateizzaImporto(): percentuale rata=" + this.ripartizionerate[i]);
	//	    }
	//	    // Importo della singola rata
	//	    BigDecimal importoRateSenzaIva = importiRateizzatiSenzaIva[i];
	//	    BigDecimal importoRateConIva = importiRateizzatiConIva[i];
	//	    // Controllo se il flag interessi legali è true
	//	    scadenza = getCalcoloScadenza(frequenzarate[i], dataTemp, i);
	//	    rateizzati.setScadenza(scadenza);
	//	    if (log.isDebugEnabled()) {
	//		log.debug("rateizzaImporto(): data scadenza=" + scadenza.toString());
	//	    }
	//	    // Aggiorno la data temporanea che utilizzo per il calcolo della scadenza della prossima rata
	//	    dataTemp = scadenza;
	//	    if (this.flagInteressiLegali) {
	//		// BigDecimal interessiLegaliRata = BigDecimal.ZERO;
	//		List<CalcoloInteressiLegali> interessiLegaliList = interessiLegaliHelper
	//			.getInteressiLegali(importoRateSenzaIva, scadenza, dataInizio);
	//		// Scorro la lista e calcolo gli interessi legali totali
	//		for (CalcoloInteressiLegali calcoloInteressiLegali : interessiLegaliList) {
	//		    valoreInteresse = valoreInteresse.add(calcoloInteressiLegali.getInteressi());
	//		}
	//		valoreInteresse = valoreInteresse.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	//		importoRateSenzaIva = importoRateSenzaIva.add(valoreInteresse);
	//	    } else {
	//		// se abbiamo un unico interesse settatto per tutte le rate
	//		if (isUnicoInteresse) {
	//		    // L'ultima rata viene calcolata come differenza della somma delle n-1 rate e l'importo totale delle
	//		    // registrazioni
	//		    if (i == this.nrorate - 1) {
	//			BigDecimal importTemp = importoRateSenzaIva;
	//			// BigDecimal importoIndividualeConInteresseTot = importoSenzaIva.add(importoSenzaIva.multiply(this.interessirate[i]).divide(new BigDecimal(100)));
	//			BigDecimal importoIndividualeConInteresseTot = importoSenzaIva.add(importo.multiply(this.interessirate[i]).divide(
	//				new BigDecimal(100)));
	//			importoRateSenzaIva = importoIndividualeConInteresseTot.subtract(importoRateAssegnato);
	//			importoRateSenzaIva = importoRateSenzaIva.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	//			valoreInteresse = importoRateSenzaIva.subtract(importTemp);
	//		    } else {
	//			// valoreInteresse = (importoRateSenzaIva.multiply(this.interessirate[i])).divide(new BigDecimal(100));
	//			valoreInteresse = (importoRateConIva.multiply(this.interessirate[i])).divide(new BigDecimal(100));
	//			importoRateSenzaIva = importoRateSenzaIva.add(valoreInteresse);
	//			importoRateSenzaIva = importoRateSenzaIva.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	//			importoRateAssegnato = importoRateAssegnato.add(importoRateSenzaIva);
	//		    }
	//		}
	//		// se abbiamo interesse variabile per ogni rata
	//		if (this.interessirate != null && !isUnicoInteresse) {
	//		    // valoreInteresse = (importoRateSenzaIva.multiply(this.interessirate[i])).divide(new BigDecimal(100));
	//		    valoreInteresse = (importoRateConIva.multiply(this.interessirate[i])).divide(new BigDecimal(100));
	//		    importoRateSenzaIva = importoRateSenzaIva.add(valoreInteresse);
	//		    importoRateSenzaIva = importoRateSenzaIva.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	//		    if (log.isDebugEnabled()) {
	//			log.debug("rateizzaImporto(): interessi rata=" + this.interessirate[i]);
	//		    }
	//		}
	//	    }
	//	    // ////////fine calcolo importo rateizzato////////////
	//	    // Setto il valore di interesse
	//	    valoreInteresse = valoreInteresse.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	//	    rateizzati.setImportoInteresse(valoreInteresse);
	//	    // Setto l'importo rateizzato con IVA
	//	    // Nel caso in cui non abbiamo interesse da applicare setto l'importo calcolato dal metodo
	//	    // getImportiRateizzati
	//	    rateizzati.setImportoRateizzato(importoRateConIva.add(valoreInteresse));
	//	    // Calcolo della data di scadenza
	//	    // scadenza = getCalcoloScadenza(frequenzarate[i], dataTemp, i);
	//	    // Setto l'importo rateizzato senza l'interesse
	//	    rateizzati.setImportoRateizzatoSenzaInteresse(importoRateConIva);
	//	    importiRateizzatiList.add(rateizzati);
	//	}
	return importiRateizzatiList;
    }

    private List<ImportiRateizzati> calcolaRateAmmortamentoFrancese(BigDecimal importo, BigDecimal interesse,
	    InteressiLegaliHelper interessiLegaliHelper, Date dataregistrazione, Date dataInizio, boolean isUnicoInteresse,
	    PeriodicitaEnum periodicitaEnum) {

	List<ImportiRateizzati> importiRateizzatiList = new ArrayList<ImportiRateizzati>();
	ImportiRateizzati importiRateizzati = null;
	//	if (this.flagInteressiLegali) {
	//	    // BigDecimal interessiLegaliRata = BigDecimal.ZERO;
	//	    List<CalcoloInteressiLegali> interessiLegaliList = interessiLegaliHelper.getInteressiLegali(importo, scadenza, dataInizio);
	//	    // Scorro la lista e calcolo gli interessi legali totali
	//	    for (CalcoloInteressiLegali calcoloInteressiLegali : interessiLegaliList) {
	//		valoreInteresse = valoreInteresse.add(calcoloInteressiLegali.getInteressi());
	//	    }
	//	    valoreInteresse = valoreInteresse.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	//	}
	if (periodicitaEnum == null) {
	    log.debug("calcolaRateAmmortamentoFrancese# Periodicità non impostata, setto di default quella mensile");
	    periodicitaEnum = PeriodicitaEnum.MENSILE;
	}
	log.debug("calcolaRateAmmortamentoFrancese# Creao piano di ammortamento per importo {}, rate : {}, periodicità: {}, intersse: {}",
		new Object[] { importo.doubleValue(), this.nrorate, periodicitaEnum.toString(), interesse.doubleValue() });
	int durataInMesi = calcoloDuraraMutuoInMesi(periodicitaEnum, this.nrorate);
	RateizzaAmmortamentoFranceseHelper rateizzaAmmortamentoFranceseHelper = new RateizzaAmmortamentoFranceseHelper(importo.doubleValue(),
		interesse.doubleValue() / 100, periodicitaEnum, durataInMesi, dataregistrazione);
	log.debug("calcolaRateAmmortamentoFrancese# recupero piano ammortamento");
	List<RataAmmortamentoFranceseHelper> ammortamentoFranceseHelpers = rateizzaAmmortamentoFranceseHelper.getPianoAmmortamento();
	log.debug("calcolaRateAmmortamentoFrancese# Faccio il cast tra RataAmmortamentoFranceseHelper e  ImportiRateizzati");
	for (RataAmmortamentoFranceseHelper rataAmmortamentoFranceseHelper : ammortamentoFranceseHelpers) {
	    importiRateizzati = new ImportiRateizzati();
	    BigDecimal quotaInteresse = new BigDecimal(rataAmmortamentoFranceseHelper.getQuotaInteressi())
		    .setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	    BigDecimal quotacapitale = new BigDecimal(rataAmmortamentoFranceseHelper.getQuotaCapitale()).setScale(WebConstants.NUMERO_CIFRE_DECIMALI,
		    BigDecimal.ROUND_HALF_UP);
	    BigDecimal quotaRata = new BigDecimal(rataAmmortamentoFranceseHelper.getImportoRata()).setScale(WebConstants.NUMERO_CIFRE_DECIMALI,
		    BigDecimal.ROUND_HALF_UP);
	    Integer nRata = rataAmmortamentoFranceseHelper.getNumeroRata();
	    if (nRata != 0) {
		int gg = periodicitaToGiorni(periodicitaEnum);
		Date datascadenza = getCalcoloScadenza(gg, dataregistrazione, nRata - 1);
		dataregistrazione = datascadenza;
		importiRateizzati.setImportoInteresse(quotaInteresse);
		importiRateizzati.setImportoRateizzato(quotaRata);
		importiRateizzati.setImportoRateizzatoSenzaInteresse(quotacapitale);
		importiRateizzati.setNumerorata(rataAmmortamentoFranceseHelper.getNumeroRata());
		importiRateizzati.setScadenza(datascadenza);
		//importiRateizzati.setScadenza(rataAmmortamentoFranceseHelper.getDataScadenza());
		importiRateizzatiList.add(importiRateizzati);
	    }
	}
	return importiRateizzatiList;
    }

    private int calcoloDuraraMutuoInMesi(PeriodicitaEnum periodicitaEnum, int numerorate) {

	int gg = 0;
	switch (periodicitaEnum) {
	case MENSILE:
	    gg = numerorate * 1;
	    break;
	case BIMESTRALE:
	    gg = numerorate * 2;
	    break;
	case TRIMESTRALE:
	    gg = numerorate * 3;
	    break;
	case QUADRIMESTRALE:
	    gg = numerorate * 4;
	    break;
	case SEMESTRALE:
	    gg = numerorate * 6;
	    break;
	case ANNUALE:
	    gg = numerorate * 12;
	    break;
	default:
	    break;
	}
	return gg;
    }

    private int periodicitaToGiorni(PeriodicitaEnum periodicitaEnum) {

	int gg = 0;
	switch (periodicitaEnum) {
	case MENSILE:
	    gg = 30;
	    break;
	case BIMESTRALE:
	    gg = 60;
	    break;
	case TRIMESTRALE:
	    gg = 90;
	    break;
	case QUADRIMESTRALE:
	    gg = 120;
	    break;
	case SEMESTRALE:
	    gg = 180;
	    break;
	case ANNUALE:
	    gg = 360;
	    break;
	default:
	    break;
	}
	return gg;
    }

    private List<ImportiRateizzati> calcolaRateLogicaDefault(BigDecimal importo, Date dataregistrazione, Date dataInizio, boolean isUnicoInteresse) {

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
	BigDecimal importoRateAssegnato = new BigDecimal(0);
	Date scadenza = null;
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
	    scadenza = getCalcoloScadenza(frequenzarate[i], dataregistrazione, i);
	    rateizzati.setScadenza(scadenza);
	    if (log.isDebugEnabled()) {
		log.debug("rateizzaImporto(): data scadenza=" + scadenza.toString());
	    }
	    // Aggiorno la data temporanea che utilizzo per il calcolo della scadenza della prossima rata
	    dataregistrazione = scadenza;
	    if (this.flagInteressiLegali) {
		// BigDecimal interessiLegaliRata = BigDecimal.ZERO;
		List<CalcoloInteressiLegali> interessiLegaliList = interessiLegaliHelper.getInteressiLegali(importoRateSenzaIva, scadenza,
			dataInizio);
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
			BigDecimal importoIndividualeConInteresseTot = importoSenzaIva
				.add(importo.multiply(this.interessirate[i]).divide(new BigDecimal(100)));
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

    private List<ImportiRateizzati> calcolaRateImportoZero(int numerorate, Date dataregistrazione) {

	List<ImportiRateizzati> importiRateizzatiList = new ArrayList<ImportiRateizzati>();
	ImportiRateizzati importiRateizzati = null;
	//	if (this.flagInteressiLegali) {
	//	    // BigDecimal interessiLegaliRata = BigDecimal.ZERO;
	//	    List<CalcoloInteressiLegali> interessiLegaliList = interessiLegaliHelper.getInteressiLegali(importo, scadenza, dataInizio);
	//	    // Scorro la lista e calcolo gli interessi legali totali
	//	    for (CalcoloInteressiLegali calcoloInteressiLegali : interessiLegaliList) {
	//		valoreInteresse = valoreInteresse.add(calcoloInteressiLegali.getInteressi());
	//	    }
	//	    valoreInteresse = valoreInteresse.setScale(WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP);
	//	}
	if (periodicitaEnum == null) {
	    periodicitaEnum = PeriodicitaEnum.MENSILE;
	}
	for (int i = 0; i < numerorate; i++) {
	    importiRateizzati = new ImportiRateizzati();
	    BigDecimal quotaInteresse = new BigDecimal(0);
	    BigDecimal quotacapitale = new BigDecimal(0);
	    BigDecimal quotaRata = new BigDecimal(0);
	    Date datascadenza = getCalcoloScadenza(30, dataregistrazione, i - 1);
	    dataregistrazione = datascadenza;
	    importiRateizzati.setImportoInteresse(quotaInteresse);
	    importiRateizzati.setImportoRateizzato(quotaRata);
	    importiRateizzati.setImportoRateizzatoSenzaInteresse(quotacapitale);
	    importiRateizzati.setNumerorata(i + 1);
	    importiRateizzati.setScadenza(datascadenza);
	    importiRateizzatiList.add(importiRateizzati);
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
	scadenza = new DataScadenzaResolver(TipoScadenzaEnum.fromValue(this.scadenzarate.getId()), dataPeriodicita, numeroRata, "").calcolaScadenza();
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
	    BigDecimal importoSingolaRata = importoTotale.divide(new BigDecimal(this.nrorate)/*, WebConstants.NUMERO_CIFRE_DECIMALI,
											     BigDecimal.ROUND_HALF_UP*/);
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
		importiRateizzati[i] = (importoTotale.multiply(this.ripartizionerate[i]))
			.divide(new BigDecimal(100)/*,
						   WebConstants.NUMERO_CIFRE_DECIMALI, BigDecimal.ROUND_HALF_UP*/);
		importoTotAssegnato = importoTotAssegnato.add(importiRateizzati[i]);
	    }
	    // calcolo dell'ultima rate(n-esima) come differenza dell'importo totale meno la somma delle n-1 rate
	    BigDecimal ultimaRata = new BigDecimal(0);
	    ultimaRata = importoTotale.subtract(importoTotAssegnato);
	    importiRateizzati[this.nrorate - 1] = ultimaRata;
	}
	return importiRateizzati;
    }

    private PeriodicitaEnum getPeriodicitaEnumDaFrequenzaRate() {

	if (this.frequenzarate[0] != null) {
	    log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate {}", this.frequenzarate);
	    if (this.frequenzarate[0].equals(WebConstants.PERIODICITA_RATA_MENSILE)) {
		log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate Mesile");
		return PeriodicitaEnum.MENSILE;
	    } else if (this.frequenzarate[0].equals(WebConstants.PERIODICITA_RATA_BIMESTRALE)) {
		log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate Bimestrale");
		return PeriodicitaEnum.BIMESTRALE;
	    } else if (this.frequenzarate[0].equals(WebConstants.PERIODICITA_RATA_TRIMESTRALE)) {
		log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate Trimestrale");
		return PeriodicitaEnum.TRIMESTRALE;
	    } else if (this.frequenzarate[0].equals(WebConstants.PERIODICITA_RATA_QUADRIMESTRALE)) {
		log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate Quadrimestrale");
		return PeriodicitaEnum.QUADRIMESTRALE;
	    } else if (this.frequenzarate[0].equals(WebConstants.PERIODICITA_RATA_SEMESTRALE)) {
		log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate Semestrale");
		return PeriodicitaEnum.SEMESTRALE;
	    } else if (this.frequenzarate[0].equals(WebConstants.PERIODICITA_RATA_ANNUALE)) {
		log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate Annuale");
		return PeriodicitaEnum.ANNUALE;
	    } else {
		log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate non tra quelli attesi, uso di default frequenza Mesile");
		return PeriodicitaEnum.MENSILE;
	    }
	} else {
	    log.debug("getPeriodicitaEnumDaFrequenzaRate# Frequenza rate non impostato, uso di default frequenza Mesile");
	    return PeriodicitaEnum.MENSILE;
	}
    }

    private TipologiaRateizzazioneEnum getTipologiaRateizzazioniEnum(String tipologiaRateizzazione) {

	if (StringUtils.isNotBlank(tipologiaRateizzazione)) {
	    if (tipologiaRateizzazione.equals(WebConstants.TIPO_RATEIZZAZIONE_DEFAULT)) {
		log.debug("getTipologiaRateizzazioniEnum# Tipologia di rateizzazione impostata : DEFAULT");
		return TipologiaRateizzazioneEnum.DEFAULT;
	    } else if (tipologiaRateizzazione.equals(WebConstants.TIPO_RATEIZZAZIONE_AMMORTAMENTO_FRANCESE)) {
		log.debug("getTipologiaRateizzazioniEnum# Tipologia di rateizzazione impostata : AMMORTAMENTO_FRANCESE");
		return TipologiaRateizzazioneEnum.AMMORTAMENTO_FRANCESE;
	    } else {
		log.debug("getTipologiaRateizzazioniEnum# Tipologia di rateizzazione non tra quelle attese, uso quella di DEFAULT");
		return TipologiaRateizzazioneEnum.DEFAULT;
	    }
	} else {
	    log.debug("getTipologiaRateizzazioniEnum# Tipologia di rateizzazione non presente, uso quella di DEFAULT");
	    return TipologiaRateizzazioneEnum.DEFAULT;
	}
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
