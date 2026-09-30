package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.helper.EquitaliatracciatoHelper;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.EquitaliatracciatoHelperService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EquitaliatracciatoHelperServiceImpl implements EquitaliatracciatoHelperService {

    private static final Logger log = LoggerFactory.getLogger(EquitaliatracciatoHelperServiceImpl.class);
    public static final String RECORD_M00 = "M00";
    public static final String RECORD_M10 = "M10";
    public static final String RECORD_M20 = "M20";
    public static final String RECORD_M21 = "M21";
    public static final String RECORD_M22 = "M22";
    public static final String RECORD_M23 = "M23";
    public static final String RECORD_M30 = "M30";
    public static final String RECORD_M40 = "M40";
    public static final String RECORD_M50 = "M50";
    public static final String RECORD_M51 = "M51";
    public static final String RECORD_M99 = "M99";
    //.
    public static final String ASSENZA_COOBBLIGATI = "1";
    public static final String PRESENZA_COOBBLIGATI = "2";
    public static final String COOBLIGATI = "C";
    public static final String COINTESTATARI = "K";
    //.
    public static final String SPESE_SAN_AMMINISTRAZIONE_COMUNALE = "5156";
    public static final String SAN_AMMINISTRAZIONE_COMUNALE = "5060";
    //.
    private MovimentiService movimentiService;
    private AutorizzazioniService autorizzazioniService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private IstanzeoneriService istanzeoneriService;
    private ComuniService comuniService;

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Override
    public String createRecordM00(EquitaliaTracciatiCfg equitaliaTracciatiCfg, String progressivoMinutaAnno, String importoTotaleArticoloDiRuolo,
	    String nomeResponsabile, String cognomeResponsabile) {

	StringBuffer sb = new StringBuffer();
	// [001 - 033]
	log.debug("createRecordM00# Creo intestazione per record = {}", RECORD_M00);
	String codiceEnteCreditore = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceEnteCreditore(), "");
	String tipoUfficio = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTipoUfficio(), "");
	String codiceUfficio = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceUfficio(), "");
	String intestazione = intestazioneRecord(RECORD_M00, "", codiceEnteCreditore, tipoUfficio, codiceUfficio);
	sb.append(intestazione);
	log.debug("createRecordM00# Estremi fornitura = {}", RECORD_M00);
	String estremiFornitura = estremiFornitura(progressivoMinutaAnno);
	sb.append(estremiFornitura);
	// TIPO MINUTA - assume sempre 1
	String tipoMinuta = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTipoMinuta(), "");
	sb = sb.append(tipoMinuta);
	// TIPO cartellazione - assume sempre 1: cartellazione ordinaria,5 Avvisi di pagamento
	String tipoCartellazione = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTipoCartellazione(), "");
	sb = sb.append(tipoCartellazione);
	// SPECIE DEL RUOLO  - assume sempre 1: ORDINARIO,3: straordinario
	String specieDelRuolo = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getSpecieDelRuolo(), "");
	sb = sb.append(specieDelRuolo);
	// TIPO ISCRIZIONE 1:Ruolo coattivo, 2:Ruolo non coattivo
	String tipoIscrizione = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTipoIscrizione(), "");
	sb = sb.append(tipoIscrizione);
	// TIPO COMPENSO - VALE SEMPRE 1
	String tipoCOMPENSO = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTipoCompenso(), "");
	sb = sb.append(tipoCOMPENSO);
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////// SEZIONE RATE NON GESTITE /////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// RATE DEL RUOLO - 01 NON SONO PREVISTE RATE
	String RATE_DEL_RUOLO = "01";
	sb = sb.append(RATE_DEL_RUOLO);
	// CADENZA_RUOLO_RATE - 000, NON SONO PREVISTE RATE
	String CADENZA_RUOLO_RATE = "000";
	sb = sb.append(CADENZA_RUOLO_RATE);
	// RATE_AVVISO - 00, NON SONO PREVISTE RATE
	String RATE_AVVISO = "00";
	sb = sb.append(RATE_AVVISO);
	// CADENZA_RATE_AVVISO - VUOTO , NON SONO PREVISTE RATE
	String CADENZA_RATE_AVVISO = "";
	sb = appendSpace(sb, CADENZA_RATE_AVVISO.length(), 3).append(CADENZA_RATE_AVVISO);
	// FLAG_ESCLUSIONE_AGOSTO - 00, NON SONO PREVISTE RATE
	String FLAG_ESCLUSIONE_AGOSTO = "0";
	sb = sb.append(FLAG_ESCLUSIONE_AGOSTO);
	String FILLER_1 = "";
	sb = appendSpace(sb, FILLER_1.length(), 1).append(FILLER_1);
	// TIPOLOGIA_IMPORTO_MINIMO - N,S,R
	String TIPOLOGIA_IMPORTO_MINIMO = "N";
	sb = sb.append(TIPOLOGIA_IMPORTO_MINIMO);
	// IMPORTO_MINIMO - N,R : VALE 0, S: VALORE DATO
	String IMPORTO_MINIMO = "";
	sb = paddingLeftValoriInteri(sb, IMPORTO_MINIMO, 15);
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////// END SEZIONE RATE NON GESTITE /////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// RELAESE - R01 O R02
	String RELAESE = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getRilascio(), "");
	sb = sb.append(RELAESE);
	// TOTALE GENERALE - IMPORTO TOTALE TRACCIATO (M50) PRESENTE ANCHE IN M99
	if (StringUtils.isNotBlank(importoTotaleArticoloDiRuolo)) {
	    String[] _oneri = importoTotaleArticoloDiRuolo.split("\\.");
	    sb = paddingLeftValoriInteri(sb, _oneri[0], 13);
	    log.debug("createRecordM00# Totale  (parte intera)= {}", _oneri[0]);
	    String centesimi = "";
	    if (StringUtils.contains(importoTotaleArticoloDiRuolo, "\\.") && StringUtils.isNotBlank(_oneri[1])) {
		centesimi = _oneri[1];
		log.debug("createRecordM00# Totale (centesimi)= {}", _oneri[1]);
	    }
	    sb = paddingRightValoriInteri(sb, centesimi, 2);
	}
	// TESTO MODALITA OPPOSIZIONE [085 - 085]
	String TESTO_MODALITA_OPPOSIZIONE = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTestoModalitaOpposizione(), "");
	sb = sb.append(TESTO_MODALITA_OPPOSIZIONE);
	// TESTO COMUNICAZIONE CONTRIBUENTI [086 - 086 ]
	String TESTO_COMUN_CONTRIBUENTE = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTestoComunicazioneContrib(), "");
	sb = sb.append(TESTO_COMUN_CONTRIBUENTE);
	// INVIO TESTO PER SINGOLO CONTRIBUENTE [087 - 087]
	String INVIO_TESTO_SINGOLO_CONTR = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getInvioTestoSingContrib(), "");
	sb = sb.append(INVIO_TESTO_SINGOLO_CONTR);
	//TIPOLOGIA ENTE [088 - 088]
	String TIPOLOGIA_ENTE = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTipologiaEnte(), "");
	sb = sb.append(TIPOLOGIA_ENTE);
	//SIGLA_PROV [089 - 090]
	String SIGLA_PROV = "";
	sb = appendSpace(sb, SIGLA_PROV.length(), 2).append(SIGLA_PROV);
	// SCADENZA PRIMA RATA [091-098]
	String SCADENZA_PRIMA_RATA = "00000000";
	sb = sb.append(SCADENZA_PRIMA_RATA);
	//
	// INVIO_VOLANTINO [099-099]
	String INVIO_VOLANTINO = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getInvioVolantino(), "");
	sb = sb.append(INVIO_VOLANTINO);
	// MODALITA_STAMPA_VOLANTINO [100-100]
	String MODALITA_STAMPA_VOLANTINO = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getModalitaStampaVolantino(), "");
	sb = appendSpace(sb, MODALITA_STAMPA_VOLANTINO.length(), 1).append(MODALITA_STAMPA_VOLANTINO);
	// INVIO_VOLANTINO [101-101]
	String COMUNICAZIONE_CONTRIB = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getComunicazioneContrib(), "");
	sb = sb.append(COMUNICAZIONE_CONTRIB);
	// NUMERO_FATTURA [102-109]
	String NUMERO_FATTURA = "";
	sb = appendSpace(sb, NUMERO_FATTURA.length(), 8).append(NUMERO_FATTURA);
	// DATA_FATTURA [110-117]
	String DATA_FATTURA = "00000000";
	sb = sb.append(DATA_FATTURA);
	// PROVENIENZA [118-118]
	String PROVENIENZA = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getFlagProvenienza(), "");
	sb = sb.append(PROVENIENZA);
	// COGNOME RESPONSABILE [118-118]
	String COGNOME_RESPONSABILE = cognomeResponsabile.toUpperCase();
	sb = sb.append(COGNOME_RESPONSABILE);
	sb = appendSpace(sb, COGNOME_RESPONSABILE.length(), 30);
	// NOME RESPONSABIL  [149-178]
	String NOME_RESPONSABILE = nomeResponsabile.toUpperCase();
	sb = sb.append(NOME_RESPONSABILE);
	sb = appendSpace(sb, NOME_RESPONSABILE.length(), 30);
	/////////////////////////////////////////////////// NON GESTITI, NON OBBGLIGATORI /////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// CODICE_ENTE_BENEFICIARIO  [179-183]
	String CODICE_ENTE_BENEFICIARIO = "00000";
	sb = appendSpace(sb, CODICE_ENTE_BENEFICIARIO.length(), 5).append(CODICE_ENTE_BENEFICIARIO);
	// TIPO_UFFICIO_ENTE_BENEFICIARIO [184-184]
	String TIPO_UFFICIO_ENTE_BENEFICIARIO = "";
	sb = appendSpace(sb, TIPO_UFFICIO_ENTE_BENEFICIARIO.length(), 1).append(TIPO_UFFICIO_ENTE_BENEFICIARIO);
	// CODICE_UFFICIO_ENTE_BENEFICIARIO  [185-190]
	String CODICE_UFFICIO_ENTE_BENEFICIARIO = "";
	sb = appendSpace(sb, CODICE_UFFICIO_ENTE_BENEFICIARIO.length(), 6).append(CODICE_UFFICIO_ENTE_BENEFICIARIO);
	// DESCRIZIONE_FORNITURA  [191-220]
	String DESCRIZIONE_FORNITURA = "";
	sb = appendSpace(sb, DESCRIZIONE_FORNITURA.length(), 30).append(DESCRIZIONE_FORNITURA);
	// DESCRIZIONE_FORNITURA  [221-228]
	String DATA_REGOLAMENTO = "00000000";
	sb = appendSpace(sb, DATA_REGOLAMENTO.length(), 8).append(DATA_REGOLAMENTO);
	// NUMERO_REGOLAMENTO  [229-234]
	String NUMERO_REGOLAMENTO = "";
	sb = appendSpace(sb, NUMERO_REGOLAMENTO.length(), 6).append(NUMERO_REGOLAMENTO);
	// FILLER_2  [235-348]
	String FILLER_2 = "";
	sb = appendSpace(sb, FILLER_2.length(), 114);
	// PRODUC_KEY  [349 - 372 ]
	String PRODUC_KEY = "";
	sb = appendSpace(sb, PRODUC_KEY.length(), 24);
	// DATA_RELEASE_FORMA  [373 - 380 ]
	String DATA_RELEASE_FORMA = "00000000";
	sb = sb.append(DATA_RELEASE_FORMA);
	// VERSIONE_FORMA  [381 - 382 ]
	String VERSIONE_FORMA = "00";
	sb = sb.append(VERSIONE_FORMA);
	// RELEASE_FORMA  [383 - 384 ]
	String RELEASE_FORMA = "00";
	sb = sb.append(RELEASE_FORMA);
	// PRODUC_KEY  [385 - 392 ]
	String DATA_RELEASE_SINERGIA = "00000000";
	sb = sb.append(DATA_RELEASE_SINERGIA);
	// VERSIONE_SINERGIA [393 - 394 ]
	String VERSIONE_SINERGIA = "00";
	sb = sb.append(VERSIONE_SINERGIA);
	// RELEASE_SINERGIA  [395 - 396]
	String RELEASE_SINERGIA = "00";
	sb = sb.append(RELEASE_SINERGIA);
	// FILLER_3  [397-450]
	String FILLER_3 = "";
	sb = appendSpace(sb, FILLER_3.length(), 54);
	return eliminaCaratteriNonValidi(sb.toString());
    }

    @Override
    public String createRecordM99(EquitaliaTracciatiCfg equitaliaTracciatiCfg, String progressivoMinutaAnno, String totaleImponibile,
	    String importoTotaleArticoloDiRuolo, Integer numRecordM10, Integer numRecordM40, Integer numRecordM20, Integer numRecordM21,
	    Integer numRecordM22, Integer numRecordM23, Integer numRecordM30, Integer numRecordM50, Integer numRecordM51) {

	StringBuffer sb = new StringBuffer();
	// [001 - 033]
	log.debug("createRecordM99# Creo intestazione per record = {}", RECORD_M99);
	String codiceEnteCreditore = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceEnteCreditore(), "");
	String tipoUfficio = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTipoUfficio(), "");
	String codiceUfficio = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceUfficio(), "");
	String intestazione = intestazioneRecord(RECORD_M99, "", codiceEnteCreditore, tipoUfficio, codiceUfficio);
	sb.append(intestazione);
	log.debug("createRecordM99# Estremi fornitura = {}", RECORD_M99);
	String estremiFornitura = estremiFornitura(progressivoMinutaAnno);
	sb.append(estremiFornitura);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M10, numRecordM10);
	// [034- 040]
	sb = paddingLeftValoriInteri(sb, numRecordM10.toString(), 7);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M40, numRecordM40);
	// [041- 047]
	sb = paddingLeftValoriInteri(sb, numRecordM40.toString(), 7);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M20, numRecordM20);
	// [048- 054]
	sb = paddingLeftValoriInteri(sb, numRecordM20.toString(), 7);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M21, numRecordM21);
	// [055- 061]
	sb = paddingLeftValoriInteri(sb, numRecordM21.toString(), 7);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M22, numRecordM22);
	// [062- 068]
	sb = paddingLeftValoriInteri(sb, numRecordM22.toString(), 7);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M23, numRecordM23);
	// [069- 075]
	sb = paddingLeftValoriInteri(sb, numRecordM23.toString(), 7);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M30, numRecordM30);
	// [076- 082]
	sb = paddingLeftValoriInteri(sb, numRecordM30.toString(), 7);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M50, numRecordM50);
	// [083- 089]
	sb = paddingLeftValoriInteri(sb, numRecordM50.toString(), 7);
	log.debug("createRecordM99# Record = {}, totale = {}", RECORD_M51, numRecordM51);
	// [090- 096]
	sb = paddingLeftValoriInteri(sb, numRecordM51.toString(), 7);
	// [097 - 110] - sulla documentazione è messo a filler sul tracciato inviato è messo a zero 
	// lo mettiamo a zero
	String FILLER_1 = "";
	//sb = appendSpace(sb, FILLER_1.length(), 7);
	sb = paddingLeftValoriInteri(sb, FILLER_1, 7);
	String FILLER_2 = "";
	// sb = appendSpace(sb, FILLER_2.length(), 7);
	sb = paddingLeftValoriInteri(sb, FILLER_2, 7);
	//
	// TOTALE IMPONIBILE [111- 125]
	if (StringUtils.isNotBlank(totaleImponibile)) {
	    String[] _oneri = totaleImponibile.split("\\.");
	    sb = paddingLeftValoriInteri(sb, _oneri[0], 13);
	    log.debug("createRecordM99# Totale imponibile (parte intera)= {}", _oneri[0]);
	    String centesimi = "";
	    if (StringUtils.contains(importoTotaleArticoloDiRuolo, "\\.") && StringUtils.isNotBlank(_oneri[1])) {
		centesimi = _oneri[1];
		log.debug("createRecordM99# Totale imponibile (centesimi)= {}", _oneri[1]);
	    }
	    sb = paddingRightValoriInteri(sb, centesimi, 2);
	}
	// TOTALE_IMPORTO_ARTICOLO [126- 140]
	if (StringUtils.isNotBlank(importoTotaleArticoloDiRuolo)) {
	    String[] _oneri = importoTotaleArticoloDiRuolo.split("\\.");
	    sb = paddingLeftValoriInteri(sb, _oneri[0], 13);
	    log.debug("createRecordM99# Totale importo articolo (oarte intera)= {}", _oneri[0]);
	    String centesimi = "";
	    if (StringUtils.contains(importoTotaleArticoloDiRuolo, "\\.") && StringUtils.isNotBlank(_oneri[1])) {
		centesimi = _oneri[1];
		log.debug("createRecordM99# Totale importo articolo (centesimi)= {}", _oneri[1]);
	    }
	    sb = paddingRightValoriInteri(sb, centesimi, 2);
	}
	// filler [141 - 450]
	String FILLER_3 = "";
	sb = appendSpace(sb, FILLER_3.length(), 310);
	return eliminaCaratteriNonValidi(sb.toString());
    }

    private String intestazioneRecord(String tipoRecord, String progressivo, String codiceEnteCreditore, String tipoUfficio, String codiceUfficio) {

	StringBuffer sb = new StringBuffer(tipoRecord);
	// per ora le differenze sono solo tra record M99 e M00
	if (tipoRecord.equals(RECORD_M99) || tipoRecord.equals(RECORD_M00)) {
	} else {
	    StringBuffer _progressivo = new StringBuffer();
	    _progressivo = paddingLeftValoriInteri(_progressivo, progressivo, 7);
	    sb = sb.append(_progressivo.toString());
	}
	sb = sb.append(codiceEnteCreditore);
	sb = sb.append(tipoUfficio);
	sb = sb.append(codiceUfficio);
	sb = appendSpace(sb, codiceUfficio.length(), 6);
	return sb.toString();
    }

    private String estremiFornitura(String progressivoMinutaAnno) {

	String anno = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
	String data = Utilities.formatDate(new Date(), "yyyyMMdd");
	StringBuffer sb = new StringBuffer();
	// estremi fornitura anno + progressivo minuta ( da calcolare)
	String _progressivoMinutaAnno = paddingLeftValoriInteri(new StringBuffer(), progressivoMinutaAnno.toString(), 6).toString();
	sb = sb.append(anno).append(_progressivoMinutaAnno);
	// data produzione file 
	sb = sb.append(data);
	return sb.toString();
    }

    private ChiaveValoreBean<String, String> _addCampoCsvExcel(String titolo, String valore) {

	ChiaveValoreBean<String, String> cv = new ChiaveValoreBean<String, String>();
	cv.setChiave(titolo);
	cv.setValore(valore);
	return cv;
    }

    @Override
    public EquitaliatracciatoHelper createRecordM20(EquitaliaTracciatiCfg equitaliaTracciatiCfg, Istanze istanze, Anagrafe anagrafe,
	    Integer progressivoRecord, Integer numeroPartita, String presenzaCoobligati) {

	List<ChiaveValoreBean<String, String>> LISTA_CAMPI_CSV_EXCEL = new ArrayList<ChiaveValoreBean<String, String>>();
	StringBuffer sb = new StringBuffer();
	log.debug("createRecordM20# Creo intestazione record...");
	// [001 - 111 ]
	sb = sb.append(identificativoPartitaPerRecord(equitaliaTracciatiCfg, istanze, RECORD_M20, progressivoRecord.toString(),
		numeroPartita.toString()));
	log.debug("createRecordM20# CF o PI - [112 - 127]");
	// usato per parte tracciato successiva
	String codicenaturaGiridica = "";
	if (WebConstants.PERSONA_FISICA.equals(anagrafe.getTipoanagrafe())) {
	    log.debug("createRecordM20# Natura giuridica = {}", anagrafe.getTipoanagrafe());
	    String cf = anagrafe.getCodicefiscale();
	    sb = sb.append(cf);
	    sb = appendSpace(sb, cf.length(), 16);
	    codicenaturaGiridica = "1";
	    //
	    LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("CF/PI", cf));
	    //
	} else {
	    log.debug("createRecordM20# Natura giuridica = {}", anagrafe.getTipoanagrafe());
	    String pi = anagrafe.getPartitaiva();
	    sb = sb.append(pi);
	    sb = appendSpace(sb, pi.length(), 16);
	    //
	    LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("CF/PI", pi));
	    //
	    codicenaturaGiridica = "2";
	}
	// [128 - 138] campo partita iva . non gestito messo a 0
	String piAnagrafe = "00000000000";
	sb = sb.append(piAnagrafe);
	// [139 - 139] i pacchetti vengono sempre fatti con soggetti non defunti (controllo preventivo)
	String flagDefunto = "0";
	sb = sb.append(flagDefunto);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("Defunto", flagDefunto));
	//
	// DATA INSINUAZIONE FALLIMENTO [140 - 147] NON GESTITO , TUTTI ZERO
	String dataInsinuazioneFallimento = "00000000";
	sb = sb.append(dataInsinuazioneFallimento);
	// 
	log.debug("createRecordM20# NATURA GIURIDICA SOGGETTO [148 - 148] = {}", codicenaturaGiridica);
	sb = sb.append(codicenaturaGiridica);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("Natura Giuridica", codicenaturaGiridica));
	//
	String PRESENZA_INDIRIZZO_NOTIFICA = "N";
	log.debug("createRecordM20# PRESENZA_INDIRIZZO_NOTIFICA  [149 - 149] = {}", PRESENZA_INDIRIZZO_NOTIFICA);
	sb = sb.append(PRESENZA_INDIRIZZO_NOTIFICA);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("PRESENZA_INDIRIZZO_NOTIFICA", PRESENZA_INDIRIZZO_NOTIFICA));
	//
	String PRESENZA_DATI_DITTA_INDIVIDUALE = "N";
	log.debug("createRecordM20# PRESENZA_DATI_DITTA_INDIVIDUALE [150 - 150] = {}", PRESENZA_DATI_DITTA_INDIVIDUALE);
	sb = sb.append(PRESENZA_DATI_DITTA_INDIVIDUALE);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("PRESENZA_DATI_DITTA_INDIVIDUALE", PRESENZA_DATI_DITTA_INDIVIDUALE));
	//
	String PRESENZA_ULTERIORI_DATI = " ";
	log.debug("createRecordM20# PRESENZA_INDIRIZZO_NOTIFICA  [151- 151] = {}", PRESENZA_ULTERIORI_DATI);
	sb = sb.append(PRESENZA_ULTERIORI_DATI);
	log.debug("createRecordM20# PRESENZA COOBBLIGATI [152 - 152] = {}", presenzaCoobligati);
	sb = sb.append(presenzaCoobligati);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("PRESENZA COOBBLIGATI", presenzaCoobligati));
	//
	String FLAG_TIPO_AVVISO = "0";
	log.debug("createRecordM20# FLAG_TIPO_AVVISO [153 - 153] = {}", FLAG_TIPO_AVVISO);
	sb = sb.append(FLAG_TIPO_AVVISO);
	String cognome = anagrafe.getNominativo();
	String nome = "";
	String sesso = "";
	String _dataNascita = "";
	String _dataNascitaCSVExcell = "";
	String belfiorenascita = "";
	String provnascita = "";
	if (codicenaturaGiridica.equals("1")) {
	    nome = anagrafe.getNome();
	    if (StringUtils.isNotBlank(anagrafe.getSesso())) {
		sesso = anagrafe.getSesso();
	    }
	    _dataNascita = Utilities.formatDate(anagrafe.getDatanascita(), "yyyyMMdd");
	    _dataNascitaCSVExcell = Utilities.formatDate(anagrafe.getDatanascita(), "dd/MM/yyyy");
	    belfiorenascita = "";
	    provnascita = "";
	    if (anagrafe.getComuneNascita() != null) {
		belfiorenascita = anagrafe.getComuneNascita().getCodicecomune();
		provnascita = anagrafe.getComuneNascita().getSiglaprovincia();
	    }
	    log.debug("createRecordM20# COGNOME [154 - 203] = {}", cognome);
	    sb = sb.append(cognome.toUpperCase());
	    sb = appendSpace(sb, cognome.length(), 50);
	    log.debug("createRecordM20# NOME [204 - 243] = {}", nome);
	    sb = sb.append(nome.toUpperCase());
	    sb = appendSpace(sb, nome.length(), 40);
	    log.debug("createRecordM20# SESSO [244 - 244] = {}", sesso);
	    sb = sb.append(sesso);
	    sb = appendSpace(sb, sesso.length(), 1);
	    //
	    LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("COGNOME NOME/DENOMINAZIONE", cognome + " " + nome + " (" + sesso + ")"));
	    //
	    log.debug("createRecordM20# DATA NASCITA [245 - 252] = {}", _dataNascita);
	    sb = sb.append(_dataNascita);
	    sb = appendSpace(sb, _dataNascita.length(), 8);
	    //
	    LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("DATA NASCITA", _dataNascitaCSVExcell));
	    //
	    log.debug("createRecordM20# CODICE BELFIORE COMUNE NASCITA [253 - 256] = {}", belfiorenascita);
	    sb = sb.append(belfiorenascita);
	    sb = appendSpace(sb, belfiorenascita.length(), 4);
	    log.debug("createRecordM20# PROVINCIA NASCITA [253 - 256] = {}", provnascita);
	    sb = sb.append(provnascita);
	    sb = appendSpace(sb, provnascita.length(), 2);
	    //
	    Comuni comuneNascita = comuniService.findById(belfiorenascita);
	    if (comuneNascita != null) {
		LISTA_CAMPI_CSV_EXCEL
			.add(_addCampoCsvExcel("COMUNE NASCITA", comuneNascita.getComune() + "[" + belfiorenascita + "] " + provnascita));
	    } else {
		LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("COMUNE NASCITA", ""));
	    }
	    //
	} else {
	    log.debug("createRecordM20# DENOMINAZIONE [154 - 229] = {}", cognome);
	    if (cognome.length() > 76) {
		cognome = StringUtils.substring(cognome, 0, 75);
	    }
	    sb = sb.append(cognome);
	    sb = appendSpace(sb, cognome.length(), 76);
	    //
	    LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("COGNOME NOME/DENOMINAZIONE", cognome));
	    //
	    LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("DATA NASCITA", ""));
	    LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("COMUNE NASCITA", ""));
	    //
	    //
	    log.debug("createRecordM20# FILLER  [230 - 258]");
	    String FILLER = "";
	    sb = sb.append(FILLER);
	    sb = appendSpace(sb, FILLER.length(), 29);
	}
	String indirizzo = anagrafe.getIndirizzo();
	log.debug("createRecordM20# INDIRIZZO [259 - 293] = {}", indirizzo);
	if (StringUtils.isNotBlank(indirizzo)) {
	    indirizzo = StringUtils.substring(indirizzo, 0, 34);
	}
	sb = sb.append(indirizzo);
	sb = appendSpace(sb, indirizzo.length(), 35);
	//
	String civico = "00000";
	log.debug("createRecordM20# NUMERO CIVICO [294 - 298] = {}", civico);
	sb = sb.append(civico);
	sb = appendSpace(sb, civico.length(), 5);
	String letteraCivico = "";
	log.debug("createRecordM20# LETTERA CIVICO [299 - 300] = {}", letteraCivico);
	sb = sb.append(letteraCivico);
	sb = appendSpace(sb, letteraCivico.length(), 2);
	String km = "000000";
	log.debug("createRecordM20# KM  [301 - 306] = {}", km);
	sb = sb.append(km);
	sb = appendSpace(sb, km.length(), 6);
	//
	// PALAZZINA, SCALA, PIANO,INTERNO
	String campoIndirizzoNonGestiti = "";
	log.debug("createRecordM20# Campi indirizzo non gestiti (PALAZZINA, SCALA, PIANO, INTERNO) [307 - 319]");
	sb = sb.append(campoIndirizzoNonGestiti);
	sb = appendSpace(sb, campoIndirizzoNonGestiti.length(), 13);
	//
	String cap = StringUtils.defaultIfEmpty(anagrafe.getCap(), "00000");
	if (cap.length() != 5) {
	    cap = "00000";
	}
	log.debug("createRecordM20# CAP [320 - 324] = {}", cap);
	sb = sb.append(cap);
	//	sb = appendSpace(sb, cap.length(), 5);
	StringBuffer _indirizzo_ = new StringBuffer(indirizzo + civico);
	_indirizzo_ = _indirizzo_.append(StringUtils.defaultIfEmpty("/" + letteraCivico, ""));
	_indirizzo_ = _indirizzo_.append(StringUtils.defaultIfEmpty("," + km, ""));
	_indirizzo_ = _indirizzo_.append(StringUtils.defaultIfEmpty("," + cap, ""));
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("INDIRIZZO RESIDENZA", indirizzo));
	Comuni comune = anagrafe.getComuneResidenza();
	String descComune = comune.getComune();
	log.debug("createRecordM20# DESCRIZIONE COMUNE [325 - 364] = {}", descComune);
	sb = sb.append(descComune);
	sb = appendSpace(sb, descComune.length(), 40);
	String siglaProv = comune.getSiglaprovincia();
	log.debug("createRecordM20# SIGLA PROV COMUNE [365 - 366] = {}", siglaProv);
	sb = sb.append(siglaProv);
	sb = appendSpace(sb, siglaProv.length(), 2);
	String belfiorecomune = comune.getCodicecomune();
	log.debug("createRecordM20# CODICE BELFIORE COMUNE [367 - 370] = {}", belfiorecomune);
	sb = sb.append(belfiorecomune);
	sb = appendSpace(sb, belfiorecomune.length(), 4);
	String localitaFrazione = "";
	log.debug("createRecordM20# LOCALITA FRAZIONE [371 - 391] = {}", localitaFrazione);
	sb = sb.append(localitaFrazione);
	sb = appendSpace(sb, localitaFrazione.length(), 21);
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("COMUNE RESIDENZA", descComune + "[" + belfiorecomune + "], " + siglaProv + ", "
		+ localitaFrazione));
	String tipologiaAtto = "";
	if ("001".equals(equitaliaTracciatiCfg.getCodificaTipologiaAtto())) {
	    // T1
	    tipologiaAtto = "T1";
	} else if ("002".equals(equitaliaTracciatiCfg.getCodificaTipologiaAtto())) {
	    //T2
	    tipologiaAtto = "T2";
	} else if ("003".equals(equitaliaTracciatiCfg.getCodificaTipologiaAtto())) {
	    //T3
	    tipologiaAtto = "T3";
	} else if ("004".equals(equitaliaTracciatiCfg.getCodificaTipologiaAtto())) {
	    //T4
	    tipologiaAtto = "T4";
	}
	log.debug("createRecordM20# TIPOLOGIA ATTO ISCRIZIONE RUOLO [392 - 393] = {}", tipologiaAtto);
	sb = sb.append(tipologiaAtto);
	sb = appendSpace(sb, tipologiaAtto.length(), 2);
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("TIPOLOGIA ATTO ISCRIZIONE RUOLO", tipologiaAtto));
	// [394 - 450]
	String FILLER = "";
	sb = sb.append(FILLER);
	sb = appendSpace(sb, FILLER.length(), 57);
	EquitaliatracciatoHelper helper = new EquitaliatracciatoHelper();
	helper.setLISTA_CAMPI_CSV_EXCEL(LISTA_CAMPI_CSV_EXCEL);
	helper.setRigoTracciato(eliminaCaratteriNonValidi(sb.toString()));
	//return sb.toString();
	return helper;
    }

    @Override
    public EquitaliatracciatoHelper createRecordM30(EquitaliaTracciatiCfg equitaliaTracciatiCfg, Istanze istanza, Integer progressivoRecord,
	    Integer numeroPartita) {

	List<ChiaveValoreBean<String, String>> LISTA_CAMPI_CSV_EXCEL = new ArrayList<ChiaveValoreBean<String, String>>();
	StringBuffer sb = new StringBuffer();
	log.debug("createRecordM30# Creo intestazione record...");
	// [001 - 111 ]
	sb = sb.append(identificativoPartitaPerRecord(equitaliaTracciatiCfg, istanza, RECORD_M30, progressivoRecord.toString(),
		numeroPartita.toString()));
	StringBuffer descrizionePartita = new StringBuffer();
	Autorizzazioni aut = autorizzazioniService
		.findByIstanzaRegistro(istanza.getId().getCodice(), Integer.parseInt(equitaliaTracciatiCfg.getCodiceRegAutOrdinanza())).get(0);
	String estremiAtto = Utilities.formatDate(aut.getAutorizdata(), "yyyy") + " / " + aut.getAutoriznumero();
	descrizionePartita = descrizionePartita.append("PROTOCOLLO ORDINANZA NUMERO:").append(" ").append(estremiAtto).append(" ");
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("N. ORDINANZA", aut.getAutoriznumero()));
	//
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("ANNO ORDINANZA", Utilities.formatDate(aut.getAutorizdata(), "yyyy")));
	//
	//sb = sb.append("PROTOCOLLO ORDINANZA NUMERO:").append(" ").append(estremiAtto).append(" ");
	// numero verbale e data verbale
	Istanzedyn2dati i2dNumeroVerbale = istanzedyn2datiService
		.findByIstanzaAndNomeCampo(istanza, equitaliaTracciatiCfg.getDynCampiNumeroVerbale()).get(0);
	String testoNumeroVerbale = StringUtils.defaultIfEmpty(i2dNumeroVerbale.getValoredecodificato(), "");
	Istanzedyn2dati i2dNumeroVerbaleDataVerbale = istanzedyn2datiService.findByIstanzaAndNomeCampo(istanza,
		equitaliaTracciatiCfg.getDynCampiDataVerbale()).get(0);
	String testoDataVerbale = StringUtils.defaultIfEmpty(i2dNumeroVerbaleDataVerbale.getValoredecodificato(), "");
	descrizionePartita = descrizionePartita.append("VERBALE NUMERO:").append(" ").append(testoNumeroVerbale).append(" DEL ")
		.append(testoDataVerbale);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("N. VERBALE", testoNumeroVerbale));
	//
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("DATA VERBALE", testoDataVerbale));
	//
	//sb = sb.append("VERBALE NUMERO:").append(" ").append(testoNumeroVerbale).append(" DEL ").append(testoDataVerbale);
	int numeroCaratteriDescPartita = descrizionePartita.toString().length();
	sb = sb.append(descrizionePartita);
	sb = appendSpace(sb, numeroCaratteriDescPartita, 150);
	log.debug("createRecordM30# FILLER [262 - 450]");
	String FILLER = "";
	sb = sb.append(FILLER);
	sb = appendSpace(sb, FILLER.length(), 189);
	EquitaliatracciatoHelper helper = new EquitaliatracciatoHelper();
	helper.setLISTA_CAMPI_CSV_EXCEL(LISTA_CAMPI_CSV_EXCEL);
	helper.setRigoTracciato(eliminaCaratteriNonValidi(sb.toString()));
	//return sb.toString();
	return helper;
    }

    @Override
    public EquitaliatracciatoHelper createRecordM40(EquitaliaTracciatiCfg equitaliaTracciatiCfg, Istanze istanza, Integer progressivoRecord,
	    Integer numeroPartita) {

	StringBuffer sb = new StringBuffer();
	List<ChiaveValoreBean<String, String>> LISTA_CAMPI_CSV_EXCEL = new ArrayList<ChiaveValoreBean<String, String>>();
	log.debug("createRecordM40# Creo intestazione record...");
	// [001 - 111 ]
	sb = sb.append(identificativoPartitaPerRecord(equitaliaTracciatiCfg, istanza, RECORD_M40, progressivoRecord.toString(),
		numeroPartita.toString()));
	String lunghezzariga = "080";
	log.debug("createRecordM40# LUNGHEZZA RIGA [112 - 114] = {}", lunghezzariga);
	sb = sb.append(lunghezzariga);
	Istanzedyn2dati i2d = istanzedyn2datiService.findByIstanzaAndNomeCampo(istanza, equitaliaTracciatiCfg.getDynCampiTestVerbale()).get(0);
	String testo = StringUtils.defaultIfEmpty(i2d.getValoredecodificato(), "");
	if (testo.length() > 320) {
	    // devo abbreviare la stringa a 240 caratteri
	    testo = StringUtils.reverse(testo);
	    testo = StringUtils.abbreviate(testo, 316);
	    testo = StringUtils.reverse(testo);
	}
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("VERBALE", testo));
	log.debug("createRecordM40# [115 - 434 (080 x 4)]");
	//	String string_1 = StringUtils.substring(testo, 0, 79);
	//	String string_2 = StringUtils.substring(testo, 80, 159);
	//	String string_3 = StringUtils.substring(testo, 160, 239);
	//	String string_4 = StringUtils.substring(testo, 240, 319);
	//	sb = sb.append(string_1);
	//	sb = sb.append(string_2);
	//	sb = sb.append(string_3);
	//	sb = sb.append(string_4);
	sb = sb.append(testo);
	sb = appendSpace(sb, testo.length(), 320);
	log.debug("createRecordM40# FILLER [435 - 450]");
	String FILLER = "";
	sb = sb.append(FILLER);
	sb = appendSpace(sb, FILLER.length(), 16);
	EquitaliatracciatoHelper helper = new EquitaliatracciatoHelper();
	helper.setLISTA_CAMPI_CSV_EXCEL(LISTA_CAMPI_CSV_EXCEL);
	helper.setRigoTracciato(eliminaCaratteriNonValidi(sb.toString()));
	//return sb.toString();
	return helper;
    }

    @Override
    public EquitaliatracciatoHelper createRecordM50(EquitaliaTracciatiCfg equitaliaTracciatiCfg, String codiceTipoOnere,
	    String codiceTipologiaSanzioneEquitalia, Istanze istanza, Integer progressivoRecord, Integer numeroPartita, Integer progressivoOnere,
	    boolean calcoladataDecorrenzaInteressi) {

	List<ChiaveValoreBean<String, String>> LISTA_CAMPI_CSV_EXCEL = new ArrayList<ChiaveValoreBean<String, String>>();
	StringBuffer sb = new StringBuffer();
	log.debug("createRecordM50# Creo intestazione record...");
	sb = sb.append(identificativoPartitaPerRecord(equitaliaTracciatiCfg, istanza, RECORD_M50, progressivoRecord.toString(),
		numeroPartita.toString()));
	log.debug("createRecordM50# progressivo onere articolo [112-114] = {}", progressivoOnere);
	sb = paddingLeftValoriInteri(sb, progressivoOnere.toString(), 3);
	//
	//	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("progressivo onere articolo", paddingLeftValoriInteri(sb, progressivoOnere.toString(), 3)
	//		.toString()));
	//
	log.debug("createRecordM50# Codice entrata [115 - 118] = {}", codiceTipologiaSanzioneEquitalia);
	sb = sb.append(codiceTipologiaSanzioneEquitalia);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("Codice entrata", codiceTipologiaSanzioneEquitalia));
	//
	String tipocodiceentrata = "I";
	log.debug("createRecordM50# Tipo Codice entrata [119 - 119] = {}", tipocodiceentrata);
	sb = sb.append(tipocodiceentrata);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("Tipo Codice entrata", tipocodiceentrata));
	//
	String competenzaResidui = "";
	log.debug("createRecordM50# Competenza/residui [120 - 120] = {}", competenzaResidui);
	sb = sb.append(competenzaResidui);
	sb = appendSpace(sb, competenzaResidui.length(), 1);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("Competenza/residui", competenzaResidui));
	//
	String[] _codiceTipoOnere = StringUtils.split(codiceTipoOnere, ",");
	BigDecimal onere = new BigDecimal(0);
	for (String cod : _codiceTipoOnere) {
	    List<Istanzeoneri> l = istanzeoneriService.findByIstanzaAndCausale(istanza.getId().getCodice(), Integer.parseInt(cod));
	    if (l != null && !l.isEmpty() && l.get(0).getPrezzo() != null) {
		onere = onere.add(l.get(0).getPrezzo());
	    }
	    //	    if (istanzeoneriSanzione.getPrezzo() != null) {
	    //		onere = onere.add(istanzeoneriSanzione.getPrezzo());
	    //	    }
	}
	String _onere = "";
	if (onere != null) {
	    log.debug("createRecordM50# IMPONIBILE [121 - 135] = {}", onere.toString());
	    _onere = onere.toString();
	    String[] _oneri = _onere.split("\\.");
	    sb = paddingLeftValoriInteri(sb, _oneri[0], 13);
	    String centesimi = "";
	    if (_oneri.length > 1 && StringUtils.isNotBlank(_oneri[1])) {
		centesimi = _oneri[1];
	    }
	    sb = paddingRightValoriInteri(sb, centesimi, 2);
	    // Il valore viene ripetuto due volte
	    log.debug("createRecordM50# IMPORTO ARTICOLO RUOLO [136 - 150] = {}", onere.toString());
	    sb = paddingLeftValoriInteri(sb, _oneri[0], 13);
	    sb = paddingRightValoriInteri(sb, centesimi, 2);
	    //
	    LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("IMPONIBILE", onere.toString()));
	    //
	}
	String tettoSommeAggiuntive = "";
	log.debug("createRecordM50# TETTO SOMME AGGIUNTIVE [151 - 165] = {}", tettoSommeAggiuntive);
	sb = sb.append(tettoSommeAggiuntive);
	sb = paddingLeftValoriInteri(sb, tettoSommeAggiuntive, 15);
	String semestri = "00";
	log.debug("createRecordM50# SEMESTRI [166 - 167] = {}", semestri);
	sb = sb.append(semestri);
	String REPARTO = "";
	log.debug("createRecordM50# REPARTO [168 - 169] = {}", REPARTO);
	sb = appendSpace(sb, REPARTO.length(), 2);
	String FILLER = "";
	log.debug("createRecordM50# FILELR [170 - 171] = {}", FILLER);
	sb = appendSpace(sb, FILLER.length(), 2);
	String CAPO = "00";
	log.debug("createRecordM50# CAPO [172 - 173] = {}", CAPO);
	sb = sb.append(CAPO);
	String CAPITOLO = "0000";
	log.debug("createRecordM50# CAPITOLO [174 - 177] = {}", CAPITOLO);
	sb = sb.append(CAPITOLO);
	String ARTICOLO = "00";
	log.debug("createRecordM50# ARTICOLO [178 - 179] = {}", ARTICOLO);
	sb = sb.append(ARTICOLO);
	String descArticoloPrimaRigaDiStampa = "";
	log.debug("createRecordM50# SEZIONE DESCRIZIONE PRIMO VALORE DI STAMPA [180 - 254] = {}", descArticoloPrimaRigaDiStampa);
	sb = appendSpace(sb, descArticoloPrimaRigaDiStampa.length(), 75);
	String descrizione = "";
	log.debug("createRecordM50# SEZIONE DESCRIZIONE VALORI NON OBBL. LASCIATI VUOTI [255 - 330]");
	sb = appendSpace(sb, descrizione.length(), 76);
	//.
	String dataDecorrenzaInteressi = "00000000";
	String dataDecorrenzaInteressiCSVExcell = "00000000";
	if (calcoladataDecorrenzaInteressi) {
	    Movimenti m = movimentiService.findMovimentiByTipoMovimento(istanza.getId().getCodice(),
		    equitaliaTracciatiCfg.getCodiceMovDataNotificaAtto());
	    Date date = m.getData();
	    Date _dataDecorrenzaInteressi = Utilities.addDays(date, 30);
	    dataDecorrenzaInteressi = Utilities.formatDate(_dataDecorrenzaInteressi, "yyyyMMdd");
	    dataDecorrenzaInteressiCSVExcell = Utilities.formatDate(_dataDecorrenzaInteressi, "dd/MM/yyyy");
	}
	log.debug("createRecordM50# DATA DECORRENZA INTERESSI [331 - 338] = {}", dataDecorrenzaInteressi);
	sb = sb.append(dataDecorrenzaInteressi);
	//
	LISTA_CAMPI_CSV_EXCEL.add(_addCampoCsvExcel("DECORRENZA INTERESSI", dataDecorrenzaInteressiCSVExcell));
	//
	String PRESENZA_RATE_VARIABILI = "";
	log.debug("createRecordM50# PRESENZA RATE VARIABILI [339 - 339] = {}", PRESENZA_RATE_VARIABILI);
	sb = appendSpace(sb, PRESENZA_RATE_VARIABILI.length(), 1);
	String FILLER_2 = "";
	log.debug("createRecordM50# PRESENZA RATE VARIABILI [340 - 450] = {}", FILLER_2);
	sb = appendSpace(sb, FILLER_2.length(), 111);
	EquitaliatracciatoHelper helper = new EquitaliatracciatoHelper();
	helper.setLISTA_CAMPI_CSV_EXCEL(LISTA_CAMPI_CSV_EXCEL);
	helper.setRigoTracciato(eliminaCaratteriNonValidi(sb.toString()));	
	return helper;
    }

    @Override
    public String identificativoPartitaPerRecord(EquitaliaTracciatiCfg equitaliaTracciatiCfg, Istanze istanza, String tipoRecord, String progressivo,
	    String numeroPartita) {

	StringBuffer sb = new StringBuffer();
	String codiceEnteCreditore = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceEnteCreditore(), "");
	String tipoUfficio = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getTipoUfficio(), "");
	String codiceUfficio = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceUfficio(), "");
	//String intestazione = intestazioneRecord(tipoRecord, numeroPartita, codiceEnteCreditore, tipoUfficio, codiceUfficio);
	String intestazione = intestazioneRecord(tipoRecord, progressivo, codiceEnteCreditore, tipoUfficio, codiceUfficio);
	sb = sb.append(intestazione);
	log.debug("identificativoPartita# Parte variabile dipendente dall'istanza");
	String codiceMovimentoAnno = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovAnnoDebito(), "");
	Movimenti movimentoMaturazioneAnnoDebito = this.isMovimentoPresente(codiceMovimentoAnno, istanza.getId().getCodice());
	log.debug("identificativoPartita# ANNO MATURAZIONE ATTO [023 - 026]. Calcolato come data motimento {} + 30 g ", codiceMovimentoAnno);
	//Date dataAtto = Utilities.addDays(movimentoMaturazioneAnnoDebito.getData(), 30);
	String _annoAtto = Utilities.formatDate(movimentoMaturazioneAnnoDebito.getData(), "yyyy");
	sb = sb.append(_annoAtto);
	log.debug("identificativoPartitaPerRecord# Codifica tipologia atto [027-029]");
	sb = sb.append(equitaliaTracciatiCfg.getCodificaTipologiaAtto());
	// per noi indica il numero istanze nel pacchetto ogni istanza è incremente di uno
	log.debug("identificativoPartitaPerRecord# numero Partita [030-038]");
	sb = paddingLeftValoriInteri(sb, numeroPartita, 9);
	log.debug("identificativoPartitaPerRecord# progressivo Partita [039-041]");
	// valore fisso [039 - 041]
	String progressivopartita = "001";
	sb = sb.append(progressivopartita);
	log.debug("identificativoPartitaPerRecord# codice tipo atto [042 - 043]");
	sb = sb.append(equitaliaTracciatiCfg.getCodiceTipoAtto());
	if ("001".equals(equitaliaTracciatiCfg.getCodificaTipologiaAtto())) {
	    // T1
	    String codiceMovDataAtto = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovDataAtto(), "");
	    Movimenti movimentoDataAtto = this.isMovimentoPresente(codiceMovDataAtto, istanza.getId().getCodice());
	    String data_atto = Utilities.formatDate(movimentoDataAtto.getDataprotocollo(), "yyyyMMdd");
	    sb = sb.append(data_atto);
	    log.debug("identificativoPartitaPerRecord# Estremi atto [052 - 63]");
	    Autorizzazioni aut = autorizzazioniService.findByIstanza(istanza.getId().getCodice()).get(0);
	    String estremiAtto = aut.getAutoriznumero();
	    //+ "/" + Utilities.formatDate(aut.getAutorizdata(), "yyyy"); // commentato perchè superava i 12 caratteri massimi
	    sb = sb.append(estremiAtto);
	    sb = appendSpace(sb, estremiAtto.length(), 12);
	    log.debug("identificativoPartitaPerRecord# data notifica atto [064 - 071]");
	    String codiceMovDataNotificaAtto = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovDataNotificaAtto(), "");
	    Movimenti movimentoDataNotificaAtto = this.isMovimentoPresente(codiceMovDataNotificaAtto, istanza.getId().getCodice());
	    String data_notifica_atto = Utilities.formatDate(movimentoDataNotificaAtto.getData(), "yyyyMMdd");
	    sb = sb.append(data_notifica_atto);
	    // Non obbligatori non gestiri
	    // ULTERIORE IDENTIFICATI [072-083] 
	    String ulterioreidentificativo = "";
	    sb = appendSpace(sb, ulterioreidentificativo.length(), 12);
	    //MOTIVAZIONE ISCRIZIONE [084-111]
	    String motivazioneiscirizione = "";
	    sb = appendSpace(sb, motivazioneiscirizione.length(), 28);
	} else if ("002".equals(equitaliaTracciatiCfg.getCodificaTipologiaAtto())) {
	    //T2
	    throw new NotImplementedException("Tipologia atto 2 non gestita");
	} else if ("003".equals(equitaliaTracciatiCfg.getCodificaTipologiaAtto())) {
	    //T3
	    throw new NotImplementedException("Tipologia atto3 non gestita");
	} else if ("004".equals(equitaliaTracciatiCfg.getCodificaTipologiaAtto())) {
	    //T4
	    throw new NotImplementedException("Tipologia atto 4 non gestita");
	}
	// Controllato in validazione che è presnete un autorizzazione
	return sb.toString();
    }

    private StringBuffer appendSpace(StringBuffer sb, int lunghezzaRealeCampo, int lunghezzaAttesaCampo) {

	int numberSpace = lunghezzaAttesaCampo - lunghezzaRealeCampo;
	for (int i = 0; i < numberSpace; i++) {
	    sb = sb.append(" ");
	}
	return sb;
    }

    private StringBuffer paddingLeftValoriInteri(StringBuffer sb, String valore, int lunghezzaAttesaCampo) {

	int numberPadding = lunghezzaAttesaCampo - valore.length();
	for (int i = 0; i < numberPadding; i++) {
	    sb = sb.append("0");
	}
	sb = sb.append(valore);
	return sb;
    }

    private StringBuffer paddingRightValoriInteri(StringBuffer sb, String valore, int lunghezzaAttesaCampo) {

	sb = sb.append(valore);
	int numberPadding = lunghezzaAttesaCampo - valore.length();
	for (int i = 0; i < numberPadding; i++) {
	    sb = sb.append("0");
	}
	return sb;
    }

    @Override
    public Movimenti isMovimentoPresente(String codiceTipoMovimento, Integer codiceIstanza) {

	Movimenti m = null;
	// sono data desc, preindo l'ultimo
	List<Movimenti> ms = movimentiService.findMovimentiIstanzaFattiByTipoMovimento(codiceTipoMovimento, codiceIstanza);
	if (!ms.isEmpty()) {
	    m = ms.get(0);
	}
	return m;
    }

    public static void main(String[] args) {

	//	//Date _date=Utilities.addDays(new Date(), 30);
	//	Date data = new Date(2019 - 1900, 11, 25);
	//	Date _date = Utilities.addDays(data, 30);
	//	String dataString = Utilities.formatDate(_date, "yyyy");
	//	System.out.println("data:" + dataString);
	//	String u = "violazione:art. 115 e art. 17 T.U.L.P.S. regio decreto 18/06/1931 773 e succ. modif. ;descrizione:in quanto apriva e conduceva agenzie d'affari, anche sotto forma di agenzie di vendita e di esposizioni senza aver presentato denuncia di inizio attivita";
	//	String string_1 = StringUtils.substring(u, 0, 79);
	//	String string_2 = StringUtils.substring(u, 80, 159);
	//	String string_3 = StringUtils.substring(u, 160, 239);
	//	String string_4 = StringUtils.substring(u, 240, 319);
	//	System.out.println("String 1: " + string_1);
	//	System.out.println("String 2: " + string_2);
	//	System.out.println("String 3: " + string_3);
	//	System.out.println("String 4: " + string_4);
	//BigDecimal d = new BigDecimal("200.99");
	BigDecimal d = new BigDecimal("2000");
	System.out.println(d.toString());
	String[] i = d.toString().split("/.");
	String in ="\"M40000011603496A10  `–’àèìòù#\\°^  2019001000000023001IN20190226548/2019\"";
	System.out.println(eliminaCaratteriNonValidi(in));
	
    }

    private static String eliminaCaratteriNonValidi(String in) {

	in = in.replace("à", "a");
	in = in.replace("è", "e");
	in = in.replace("ì", "i");
	in = in.replace("ò", "o");
	in = in.replace("ù", "u");
	in = in.replace("È", "E");
	in = in.replace("!", " ");
	in = in.replace("\"", " ");
	in = in.replace("#", " ");
	in = in.replace("$", " ");
	in = in.replace("%", " ");
	in = in.replace("&", " ");
	in = in.replace("<", " ");
	in = in.replace("=", " ");
	in = in.replace(">", " ");
	in = in.replace("?", " ");
	in = in.replace("@", " ");
	in = in.replace("[", " ");
	in = in.replace("\\", " ");
	in = in.replace("]", " ");
	in = in.replace("^", " ");
	in = in.replace("_", " ");
	in = in.replace("`", " ");
	in = in.replace("{", " ");
	in = in.replace("|", " ");
	in = in.replace("}", " ");
	in = in.replace("~", " ");
	return Utilities.rimuoviCaratteriNonAsciiDallaStringa(in, " ");
    }
}
