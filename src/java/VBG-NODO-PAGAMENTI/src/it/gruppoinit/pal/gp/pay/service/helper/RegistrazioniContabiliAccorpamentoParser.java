package it.gruppoinit.pal.gp.pay.service.helper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.pay.service.PayRegistrazioniCausaliService;
import it.gruppoinit.pal.gp.pay.ws.schema.ImportoPagamentoWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.RegistrazioneContabileWsInType;
import it.gruppoinit.pal.gp.pay.ws.schema.SoggettoDebitoreType;

public class RegistrazioniContabiliAccorpamentoParser {

    private static final Logger log = LoggerFactory.getLogger(RegistrazioniContabiliAccorpamentoParser.class);
    private PayRegistrazioniCausaliService payRegistrazioniCausaliService;

    public RegistrazioniContabiliAccorpamentoParser(PayRegistrazioniCausaliService payRegistrazioniCausaliService) {

	super();
	this.payRegistrazioniCausaliService = payRegistrazioniCausaliService;
    }

    public List<RegistrazioneContabileWsInType> gestisciAccorpamento(boolean accorpaPosizioni, List<RegistrazioneContabileWsInType> regContabili,
	    String oggettoPosizione) {

	if (!accorpaPosizioni || //
		!verificaSoggettoDebitore(regContabili) || //
		unaSolaPosizione(regContabili) || //
		!unaSolaRata(regContabili)) {
	    log.warn("Per le posizioni accorpate non sono verificate le condizioni");
	    return regContabili;
	}
	// sono nella situazione che il soggetto debitore è lo stesso e non è una sola posizione e quindi
	// provo ad elaborare l'accorpamento
	log.debug("Provo ad accorpare le posizioni");
	List<RegistrazioneContabileWsInType> ret = new ArrayList<>();
	Map<String, List<PosizioneAccorpataParserHelper>> mapRc = new TreeMap<>(); // per mantenere ordinamento basato su 001, 002 del numero delle rate
	int i = 1;
	RegistrazioneContabileWsInType finale = null;
	for (RegistrazioneContabileWsInType rc : regContabili) {
	    if (finale == null) {
		finale = copiaInfoDaTestata(rc, oggettoPosizione);
	    }
	    log.debug("Processo la registrazione {} - {}", i, rc.getDescrizione());
	    List<PosizioneDebitoriaWsInType> rate = rc.getRate();
	    for (PosizioneDebitoriaWsInType posWsInType : rate) {
		BigInteger numeroRata = posWsInType.getNumeroRata();
		log.debug("Processo la rata {} della registrazione {} - {}", numeroRata, i, rc.getDescrizione());
		List<ImportoPagamentoWsInType> importi = posWsInType.getImporti();
		for (ImportoPagamentoWsInType imp : importi) {
		    String codiceMappatura = imp.getCodiceMappatura();
		    InfoCausaleBean info = payRegistrazioniCausaliService.findInfoCausale(codiceMappatura);
		    String codiceVersamento = info.getCodiceVersamento();
		    String key = StringUtils.leftPad(String.valueOf(numeroRata), 3, "0") + "_" + codiceVersamento;
		    log.debug("Chiave {} rata {} della registrazione {}", numeroRata, i, key);
		    List<PosizioneAccorpataParserHelper> posizioniAccorpate = mapRc.get(key);
		    if (posizioniAccorpate == null) {
			posizioniAccorpate = new ArrayList<>();
		    }
		    mapRc.put(key, aggiungiPosizioni(posizioniAccorpate, imp, posWsInType));
		}
	    }
	    i++;
	}
	log.debug("Termine elaborazione mappe trovate {}", mapRc);
	elaboraRate(finale, mapRc, oggettoPosizione);
	if (log.isDebugEnabled()) {
	    log.debug("Terminata elaborazione delle rate {}", ReflectionToStringBuilder.toString(finale, ToStringStyle.SHORT_PREFIX_STYLE));
	}
	ret.add(finale);
	return ret;
    }

    private void elaboraRate(final RegistrazioneContabileWsInType finale, Map<String, List<PosizioneAccorpataParserHelper>> mapRc,
	    final String oggettoPosizione) {

	int numeroPosizioni = mapRc.size();
	for (String key : mapRc.keySet(/*uso il key set perchè treemap li ordina*/)) {
	    List<PosizioneAccorpataParserHelper> value = mapRc.get(key);
	    log.debug("Itero la chiave {} con {} importi", key, value.size());
	    finale.getRate().add(rataFromPosizioniAccorpate(value, oggettoPosizione));
	}
	if (numeroPosizioni > 1) {
	    int contatore = 1;
	    for (PosizioneDebitoriaWsInType rata : finale.getRate()) {
		String descrizione = rata.getDescrizione();
		rata.setDescrizione(descrizione + " " + contatore + " di " + numeroPosizioni);
		contatore++;
	    }
	}
    }

    private PosizioneDebitoriaWsInType rataFromPosizioniAccorpate(List<PosizioneAccorpataParserHelper> value, String oggettoPosizione) {

	PosizioneDebitoriaWsInType ret = new PosizioneDebitoriaWsInType();
	XMLGregorianCalendar ds = null;
	BigInteger numeroRata = BigInteger.ONE;
	Set<String> riferimentiClient = new HashSet<>();
	String descrizioneRata = null;
	Map<String, ImportoPagamentoWsInType> importiPerCodiceMappatura = new TreeMap<>();
	for (PosizioneAccorpataParserHelper elemento : value) {
	    descrizioneRata = elemento.getDescrizioneRata(oggettoPosizione);
	    ds = elemento.getDataScadenzaRata();
	    numeroRata = elemento.getNumeroRata();
	    Set<String> rcs = elemento.getRiferimentiClient();
	    if (!rcs.isEmpty()) {
		riferimentiClient.addAll(rcs);
	    }
	    // sommo gli importi per lo stesso codice mappatura
	    elemento.getMappaImporti(importiPerCodiceMappatura);
	    log.debug("importiPerCodiceVersamento {}", importiPerCodiceMappatura);
	}
	ret.setDataScadenza(ds);
	ret.setDescrizione(descrizioneRata);
	ret.setNumeroRata(numeroRata);
	ret.getRiferimentiClient().addAll(riferimentiClient);
	importiPerCodiceMappatura.forEach((key, val) -> {
	    ret.getImporti().add(val);
	});
	return ret;
    }

    private List<PosizioneAccorpataParserHelper> aggiungiPosizioni(List<PosizioneAccorpataParserHelper> posizioniAccorpate,
	    ImportoPagamentoWsInType imp, PosizioneDebitoriaWsInType posWsInType) {

	posizioniAccorpate.add(PosizioneAccorpataParserHelper.fromDati(imp, posWsInType));
	return posizioniAccorpate;
    }

    /**
     * Copio le informazioni per tornare una sola registrazione contabile
     * 
     * @param rc
     * @param oggettoPosizione
     * @return
     */
    private RegistrazioneContabileWsInType copiaInfoDaTestata(RegistrazioneContabileWsInType rc, String oggettoPosizione) {

	RegistrazioneContabileWsInType ret = new RegistrazioneContabileWsInType();
	ret.setAnno(rc.getAnno());
	ret.setData(rc.getData());
	ret.setDescrizione(rc.getDescrizione());
	if (StringUtils.isNotBlank(oggettoPosizione)) {
	    ret.setDescrizione(oggettoPosizione);
	}
	ret.setNote(rc.getNote());
	ret.setSoggettoDebitore(rc.getSoggettoDebitore());
	return ret;
    }

    /**
     * Se una sola posizione non faccio ulteriori elaborazioni
     * 
     * @param regContabili
     * @return
     */
    private boolean unaSolaPosizione(List<RegistrazioneContabileWsInType> regContabili) {

	int i = 0;
	for (RegistrazioneContabileWsInType rc : regContabili) {
	    for (PosizioneDebitoriaWsInType posWsInType : rc.getRate()) {
		i += posWsInType.getImporti().size();
	    }
	}
	return i == 1;
    }

    /**
     * Processo solo se tutte le posizioni hanno una sola rata
     * 
     * @param regContabili
     * @return
     */
    private boolean unaSolaRata(List<RegistrazioneContabileWsInType> regContabili) {

	Set<BigInteger> rate = new HashSet<>();
	for (RegistrazioneContabileWsInType rc : regContabili) {
	    for (PosizioneDebitoriaWsInType posWsInType : rc.getRate()) {
		rate.add(posWsInType.getNumeroRata() == null ? BigInteger.ZERO : posWsInType.getNumeroRata());
	    }
	}
	return rate.size() == 1;
    }

    /**
     * Se il soggetto è differente tra le n registrazioni non faccio ulteriori elaborazioni
     * 
     * @param regContabili
     * @return
     */
    private boolean verificaSoggettoDebitore(List<RegistrazioneContabileWsInType> regContabili) {

	String soggetto = null;
	for (RegistrazioneContabileWsInType rc : regContabili) {
	    log.debug("Processo la registrazione  {}", rc.getDescrizione());
	    SoggettoDebitoreType soggettoDebitore = rc.getSoggettoDebitore();
	    if (soggettoDebitore == null) {
		return false;
	    }
	    if (soggetto == null) {
		soggetto = soggettoDebitore.getNome() + "_" + soggettoDebitore.getCognome() + "_" + soggettoDebitore.getCfpi();
		continue;
	    }
	    String confronto = soggettoDebitore.getNome() + "_" + soggettoDebitore.getCognome() + "_" + soggettoDebitore.getCfpi();
	    log.debug("verifico i soggetti {}={}", soggetto, confronto);
	    if (!confronto.equalsIgnoreCase(soggetto)) {
		log.warn("Per le posizioni accorpate i soggetti non sono uguali {}={}", soggetto, confronto);
		return false;
	    }
	    soggetto = soggettoDebitore.getNome() + "_" + soggettoDebitore.getCognome() + "_" + soggettoDebitore.getCfpi();
	}
	return true;
    }
}
