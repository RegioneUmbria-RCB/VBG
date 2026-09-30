package it.gruppoinit.pal.gp.core.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.EquitaliatracciatoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.EquitaliaTracciatiCfg;
import it.gruppoinit.pal.gp.core.domain.Equitaliatracciato;
import it.gruppoinit.pal.gp.core.domain.EquitaliatracciatoD;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.helper.EquitaliatracciatoHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.EquitaliaTracciatiCfgService;
import it.gruppoinit.pal.gp.core.service.EquitaliatracciatoDService;
import it.gruppoinit.pal.gp.core.service.EquitaliatracciatoHelperService;
import it.gruppoinit.pal.gp.core.service.EquitaliatracciatoService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzerichiedentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.helper.MessageTracciato450Helper;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class EquitaliatracciatoServiceImpl extends BaseServiceImpl<Equitaliatracciato, PkId> implements EquitaliatracciatoService {

    private static final Logger log = LoggerFactory.getLogger(EquitaliatracciatoServiceImpl.class);
    private IstanzeService istanzeService;
    private EquitaliatracciatoHelperService equitaliatracciatoHelperService;
    private EquitaliaTracciatiCfgService equitaliaTracciatiCfgService;
    private IstanzerichiedentiService istanzerichiedentiService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private IstanzeoneriService istanzeoneriService;
    private UserSecurityService userSecurityService;
    private SoftwareService softwareService;
    private EquitaliatracciatoDService equitaliatracciatoDService;
    private OggettiService oggettiService;
    private MovimentiService movimentiService;
    private TipiMovimentoService tipiMovimentoService;
    private AutorizzazioniService autorizzazioniService;

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setEquitaliaTracciatiCfgService(EquitaliaTracciatiCfgService equitaliaTracciatiCfgService) {

	this.equitaliaTracciatiCfgService = equitaliaTracciatiCfgService;
    }

    @Autowired
    public void setEquitaliatracciatoHelperService(EquitaliatracciatoHelperService equitaliatracciatoHelperService) {

	this.equitaliatracciatoHelperService = equitaliatracciatoHelperService;
    }

    @Autowired
    public void setIstanzerichiedentiService(IstanzerichiedentiService istanzerichiedentiService) {

	this.istanzerichiedentiService = istanzerichiedentiService;
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
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setEquitaliatracciatoDService(EquitaliatracciatoDService equitaliatracciatoDService) {

	this.equitaliatracciatoDService = equitaliatracciatoDService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    private EquitaliatracciatoDAO equitaliatracciatoDAO;

    @Autowired
    public void setEquitaliatracciatoDAO(EquitaliatracciatoDAO equitaliatracciatoDAO) {

	this.equitaliatracciatoDAO = equitaliatracciatoDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Override
    protected Class<Equitaliatracciato> getEntityClass() {

	return Equitaliatracciato.class;
    }

    @Override
    public List<Equitaliatracciato> findAll(Integer firstResult, Integer maxResult) {

	return equitaliatracciatoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Equitaliatracciato entity) {

	if (validateEntity(entity)) {
	    equitaliatracciatoDAO.insert(entity);
	}
    }

    @Override
    public Equitaliatracciato findById(PkId id) {

	return equitaliatracciatoDAO.findById(id);
    }

    @Override
    public void update(Equitaliatracciato entity) {

	if (validateEntity(entity)) {
	    equitaliatracciatoDAO.update(entity);
	}
    }

    @Override
    public void delete(Equitaliatracciato entity) {

	if (isDeleteAllowed(entity)) {
	    equitaliatracciatoDAO.delete(entity);
	}
    }

    @Override
    public ChiaveValoreBean<String, List<MessageTracciato450Helper>> validaInformazioniIstanza(Integer codiceIstanza,
	    EquitaliaTracciatiCfg equitaliaTracciatiCfg) {

	boolean isErroriPresenti = false;
	// Inizializzazione informazioni
	ChiaveValoreBean<String, List<MessageTracciato450Helper>> bean = new ChiaveValoreBean<String, List<MessageTracciato450Helper>>();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	// set chiave
	bean.setChiave(istanza.getNumeroistanza() + " [" + istanza.getId().getCodice() + "]");
	List<MessageTracciato450Helper> errori = new ArrayList<MessageTracciato450Helper>();
	log.debug("validaInformazioniIstanze# Validazione campi per record M20 per istanza = {}", codiceIstanza);
	String codiceTipoMovimentoAnnoMadurazioneDebito = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovAnnoDebito(), "");
	log.debug("validaInformazioniIstanze# Anno maturazione debito [ANNO] (023-026). Cerco tipo movimento = {}",
		codiceTipoMovimentoAnnoMadurazioneDebito);
	Movimenti movimentoMaturazioneAnnoDebito = equitaliatracciatoHelperService.isMovimentoPresente(codiceTipoMovimentoAnnoMadurazioneDebito,
		codiceIstanza);
	MessageTracciato450Helper messageTracciato450Helper = null;
	if (EntityUtils.getNestedProperty(movimentoMaturazioneAnnoDebito, "id.codice") == null) {
	    log.error("validaInformazioniIstanze# Istanza = {}, Movimento = {}. Movimento non trovato", codiceIstanza,
		    codiceTipoMovimentoAnnoMadurazioneDebito);
	    // Non è stato trovato il movimento con codice {0} per determnare l'anno di maturazione debito
	    String erroreAnnoMaturazione = getMessageFromBundle("service_error.tracciato_equitalia.mov_anno_maturazione_debito",
		    new Object[] { codiceTipoMovimentoAnnoMadurazioneDebito });
	    messageTracciato450Helper = new MessageTracciato450Helper(erroreAnnoMaturazione, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	}
	String codiceMovimentodataAtto = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovDataAtto(), "");
	log.debug("validaInformazioniIstanze# DATA ATTO (044-051). Cerco tipo movimento = {}", codiceMovimentodataAtto);
	Movimenti movimentoDataAtto = equitaliatracciatoHelperService.isMovimentoPresente(codiceMovimentodataAtto, codiceIstanza);
	if (EntityUtils.getNestedProperty(movimentoDataAtto, "id.codice") == null) {
	    log.error("validaInformazioniIstanze# Istanza = {}, Movimento = {}. Movimento non trovato", codiceIstanza, codiceMovimentodataAtto);
	    // Non è stato trovato il movimento con codice {0} per determnare data atto
	    String errore = getMessageFromBundle("service_error.tracciato_equitalia.mov_data_atto", new Object[] { codiceMovimentodataAtto });
	    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	} else {
	    if (movimentoDataAtto.getDataprotocollo() == null) {
		log.error("validaInformazioniIstanze# Istanza = {}, Movimento = {}. Data protocollo non presente", codiceIstanza,
			codiceMovimentodataAtto);
		// Il movimento con codice {0} non ha data di protocollo per determnare data atto
		String errore = getMessageFromBundle("service_error.tracciato_equitalia.mov_prot_data_atto",
			new Object[] { codiceMovimentodataAtto });
		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
		errori.add(messageTracciato450Helper);
		isErroriPresenti = true;
	    }
	}
	log.debug("validaInformazioniIstanze# ESTREMI ATTO (052-064). Verifico presenza autorizzazioni con registro = {}",
		equitaliaTracciatiCfg.getCodiceRegAutOrdinanza());
	Set<Integer> autOrdinanze = new HashSet<Integer>();
	List<Autorizzazioni> auts = autorizzazioniService.findByIstanza(codiceIstanza);
	Integer codiceRegistroOrdinanze = Integer.parseInt(equitaliaTracciatiCfg.getCodiceRegAutOrdinanza());
	boolean presenteParziale = false;
	String registro = null;
	for (Autorizzazioni a : auts) {
	    if (a.getTipologiaregistro().getId().getCodice().equals(codiceRegistroOrdinanze)) {
		autOrdinanze.add(a.getId().getCodice());
	    }
	    if (a.getTipologiaregistro().getTrDescrizione().toLowerCase().lastIndexOf("parziale") >= 0) {
		presenteParziale = true;
		registro = a.getTipologiaregistro().getTrDescrizione();
	    }
	}
	if (presenteParziale) {
	    String messaggio ="È presente un autorizzazione di tipo " + registro + " nella pratica ";
	    log.error("validaInformazioniIstanze# Istanza = {} - {} ", codiceIstanza, messaggio);
	    messageTracciato450Helper = new MessageTracciato450Helper(messaggio, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	} 
	if (autOrdinanze.isEmpty()) {
	    log.error("validaInformazioniIstanze# Istanza = {}. Autorizzazione con registro = {} non presente", codiceIstanza,
		    equitaliaTracciatiCfg.getCodiceRegAutOrdinanza());
	    // Non è stata trovata nessuna autorizzazione da cui recuperare gli estremi atto 
	    String errore = getMessageFromBundle("service_error.tracciato_equitalia.estremi_atto",
		    new Object[] { equitaliaTracciatiCfg.getCodiceRegAutOrdinanza() });
	    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	} else {
	    if (autOrdinanze.size() > 1) {
		log.error("validaInformazioniIstanze# Istanza = {}. E' presente più di un autorizzazione con tipologia registro ", codiceIstanza,
			equitaliaTracciatiCfg.getCodiceRegAutOrdinanza());
		// Non è stata trovata nessuna autorizzazione da cui recuperare gli estremi atto 
		String errore = getMessageFromBundle("service_error.tracciato_equitalia.estremi_atto_non_univoco",
			new Object[] { equitaliaTracciatiCfg.getCodiceRegAutOrdinanza() });
		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
		errori.add(messageTracciato450Helper);
		isErroriPresenti = true;
	    }
	}
	String _codiceTipoMovimentoDataNotificaAtto = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovDataNotificaAtto(), "");
	log.debug("validaInformazioniIstanze# DATA NOTIFICA ATTO (064-071). Cerco tipo movimento = {}", _codiceTipoMovimentoDataNotificaAtto);
	Movimenti movimentoDataNotificaAtto = equitaliatracciatoHelperService
		.isMovimentoPresente(_codiceTipoMovimentoDataNotificaAtto, codiceIstanza);
	if (EntityUtils.getNestedProperty(movimentoDataNotificaAtto, "id.codice") == null) {
	    log.error("validaInformazioniIstanze# Istanza = {}, Movimento = {}. Movimento non trovato", codiceIstanza,
		    _codiceTipoMovimentoDataNotificaAtto);
	    // Non è stato trovato il movimento con codice {0} per determnare la data notifica atto
	    String errore = getMessageFromBundle("service_error.tracciato_equitalia.mov_data_notif_atto",
		    new Object[] { _codiceTipoMovimentoDataNotificaAtto });
	    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	}
	// VALIDAZIONE DATI TRASGRESSORE (istanza.richiendete)
	Anagrafe trasgressore = istanza.getRichiedente();
	List<String> erroriTrasgressore = validazioneAnagrafe(trasgressore);
	for (String e : erroriTrasgressore) {
	    messageTracciato450Helper = new MessageTracciato450Helper(e, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	}
	List<Istanzerichiedenti> irs = istanzerichiedentiService.findByIstanza(istanza);
	for (Istanzerichiedenti istanzerichiedenti : irs) {
	    List<String> erroriSoggcollegati = validazioneAnagrafe(istanzerichiedenti.getRichiedente());
	    for (String e : erroriSoggcollegati) {
		messageTracciato450Helper = new MessageTracciato450Helper(e, true);
		errori.add(messageTracciato450Helper);
		isErroriPresenti = true;
	    }
	}
	log.debug("validaInformazioniIstanze# Validazione testo verbale. Campo dinamico = {}", equitaliaTracciatiCfg.getDynCampiTestVerbale());
	List<Istanzedyn2dati> id2d = istanzedyn2datiService.findByIstanzaAndNomeCampo(codiceIstanza, equitaliaTracciatiCfg.getDynCampiTestVerbale());
	if (id2d.isEmpty()) {
	    String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_testo_verbale",
		    new Object[] { equitaliaTracciatiCfg.getDynCampiTestVerbale() });
	    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	} else {
	    if (id2d.size() > 1) {
		String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_testo_verbale_maggiore_di_uno",
			new Object[] { equitaliaTracciatiCfg.getDynCampiTestVerbale() });
		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
		errori.add(messageTracciato450Helper);
		isErroriPresenti = true;
	    } else {
		String testo = id2d.get(0).getValoredecodificato();
		if (testo.length() > 320) {
		    String estremi = "";
		    if (!istanza.getAutorizzazionis().isEmpty()) {
			List<Autorizzazioni> a = new ArrayList<Autorizzazioni>(istanza.getAutorizzazionis());
			estremi = a.get(0).getAutoriznumero() + " del " + Utilities.formatDate(a.get(0).getAutorizdata(), false);
		    }
		    String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_testo_verbale_troppo_lungo", new Object[] {
			    equitaliaTracciatiCfg.getDynCampiTestVerbale(), testo.length(), 320, estremi });
		    messageTracciato450Helper = new MessageTracciato450Helper(errore, false);
		    errori.add(messageTracciato450Helper);
		    isErroriPresenti = true;
		}
	    }
	}
	log.debug("validaInformazioniIstanze# Validazione numero verbale. Campo dinamico = {}", equitaliaTracciatiCfg.getDynCampiNumeroVerbale());
	List<Istanzedyn2dati> id2dNumeroVerbale = istanzedyn2datiService.findByIstanzaAndNomeCampo(codiceIstanza,
		equitaliaTracciatiCfg.getDynCampiNumeroVerbale());
	if (id2dNumeroVerbale.isEmpty()) {
	    String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_numero_verbale",
		    new Object[] { equitaliaTracciatiCfg.getDynCampiNumeroVerbale() });
	    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	}
	log.debug("validaInformazioniIstanze# Validazione data verbale. Campo dinamico = {}", equitaliaTracciatiCfg.getDynCampiDataVerbale());
	List<Istanzedyn2dati> id2dDataVerbale = istanzedyn2datiService.findByIstanzaAndNomeCampo(codiceIstanza,
		equitaliaTracciatiCfg.getDynCampiDataVerbale());
	if (id2dDataVerbale.isEmpty()) {
	    String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_data_verbale",
		    new Object[] { equitaliaTracciatiCfg.getDynCampiDataVerbale() });
	    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	}
	log.debug("validaInformazioniIstanze# validazione oneri sanzioni......");
	String e_onere_1_sazione = validazioneOneriString(istanza, equitaliaTracciatiCfg.getMapEqInSanAmmComOner());
	if (StringUtils.isNotBlank(e_onere_1_sazione)) {
	    messageTracciato450Helper = new MessageTracciato450Helper(e_onere_1_sazione, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	}
	//String e_onere_2 = validazioneOneri(istanza, equitaliaTracciatiCfg.getMapEqInSpSanAmmComOner());
	String e_onere_2_spese_sanzione = validazioneOneriString(istanza, equitaliaTracciatiCfg.getMapEqInSpSanAmmComOner());
	if (StringUtils.isNotBlank(e_onere_2_spese_sanzione)) {
	    messageTracciato450Helper = new MessageTracciato450Helper(e_onere_2_spese_sanzione, true);
	    errori.add(messageTracciato450Helper);
	    isErroriPresenti = true;
	}
	if (isErroriPresenti) {
	    bean.setValore(errori);
	} else {
	    bean.setValore(new ArrayList<MessageTracciato450Helper>());
	}
	return bean;
    }

    @Override
    public List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> validaInformazioniIstanze(List<Integer> codiceIstanze) {

	log.debug("validaInformazioniIstanze# load equitaliaTracciatiCfg....");
	EquitaliaTracciatiCfg equitaliaTracciatiCfg = equitaliaTracciatiCfgService.findBySoftware();
	List<ChiaveValoreBean<String, List<MessageTracciato450Helper>>> result = new ArrayList<ChiaveValoreBean<String, List<MessageTracciato450Helper>>>();
	log.debug("validaInformazioniIstanze# Analisi campi pacchetto istanze per creazione tracciato....");
	boolean isErroriPresenti = false;
	for (Integer codIst : codiceIstanze) {
	    ChiaveValoreBean<String, List<MessageTracciato450Helper>> bean = validaInformazioniIstanza(codIst, equitaliaTracciatiCfg);
	    //	    // Inizializzazione informazioni
	    //	    ChiaveValoreBean<String, List<MessageTracciato450Helper>> bean = new ChiaveValoreBean<String, List<MessageTracciato450Helper>>();
	    //	    Istanze istanza = istanzeService.findById(new PkId(codIst));
	    //	    // set chiave
	    //	    bean.setChiave(istanza.getNumeroistanza() + " [" + istanza.getId().getCodice() + "]");
	    //	    List<MessageTracciato450Helper> errori = new ArrayList<MessageTracciato450Helper>();
	    //	    log.debug("validaInformazioniIstanze# Validazione campi per record M20 per istanza = {}", codIst);
	    //	    String codiceTipoMovimentoAnnoMadurazioneDebito = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovAnnoDebito(), "");
	    //	    log.debug("validaInformazioniIstanze# Anno maturazione debito [ANNO] (023-026). Cerco tipo movimento = {}",
	    //		    codiceTipoMovimentoAnnoMadurazioneDebito);
	    //	    Movimenti movimentoMaturazioneAnnoDebito = equitaliatracciatoHelperService.isMovimentoPresente(codiceTipoMovimentoAnnoMadurazioneDebito,
	    //		    codIst);
	    //	    MessageTracciato450Helper messageTracciato450Helper = null;
	    //	    if (EntityUtils.getNestedProperty(movimentoMaturazioneAnnoDebito, "id.codice") == null) {
	    //		log.error("validaInformazioniIstanze# Istanza = {}, Movimento = {}. Movimento non trovato", codiceIstanze,
	    //			codiceTipoMovimentoAnnoMadurazioneDebito);
	    //		// Non è stato trovato il movimento con codice {0} per determnare l'anno di maturazione debito
	    //		String erroreAnnoMaturazione = getMessageFromBundle("service_error.tracciato_equitalia.mov_anno_maturazione_debito",
	    //			new Object[] { codiceTipoMovimentoAnnoMadurazioneDebito });
	    //		messageTracciato450Helper = new MessageTracciato450Helper(erroreAnnoMaturazione, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    }
	    //	    String codiceMovimentodataAtto = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovDataAtto(), "");
	    //	    log.debug("validaInformazioniIstanze# DATA ATTO (044-051). Cerco tipo movimento = {}", codiceMovimentodataAtto);
	    //	    Movimenti movimentoDataAtto = equitaliatracciatoHelperService.isMovimentoPresente(codiceMovimentodataAtto, codIst);
	    //	    if (EntityUtils.getNestedProperty(movimentoDataAtto, "id.codice") == null) {
	    //		log.error("validaInformazioniIstanze# Istanza = {}, Movimento = {}. Movimento non trovato", codiceIstanze, codiceMovimentodataAtto);
	    //		// Non è stato trovato il movimento con codice {0} per determnare data atto
	    //		String errore = getMessageFromBundle("service_error.tracciato_equitalia.mov_data_atto", new Object[] { codiceMovimentodataAtto });
	    //		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    } else {
	    //		if (movimentoDataAtto.getDataprotocollo() == null) {
	    //		    log.error("validaInformazioniIstanze# Istanza = {}, Movimento = {}. Data protocollo non presente", codiceIstanze,
	    //			    codiceMovimentodataAtto);
	    //		    // Il movimento con codice {0} non ha data di protocollo per determnare data atto
	    //		    String errore = getMessageFromBundle("service_error.tracciato_equitalia.mov_prot_data_atto",
	    //			    new Object[] { codiceMovimentodataAtto });
	    //		    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		    errori.add(messageTracciato450Helper);
	    //		    isErroriPresenti = true;
	    //		}
	    //	    }
	    //	    log.debug("validaInformazioniIstanze# ESTREMI ATTO (052-064). Verifico presenza autorizzazioni con registro = {}",
	    //		    equitaliaTracciatiCfg.getCodiceRegAutOrdinanza());
	    //	    List<Autorizzazioni> auts = autorizzazioniService.findByIstanzaRegistro(codIst,
	    //		    Integer.parseInt(equitaliaTracciatiCfg.getCodiceRegAutOrdinanza()));
	    //	    if (auts.isEmpty()) {
	    //		log.error("validaInformazioniIstanze# Istanza = {}. Autorizzazione con registro = {} non presente", codiceIstanze,
	    //			equitaliaTracciatiCfg.getCodiceRegAutOrdinanza());
	    //		// Non è stata trovata nessuna autorizzazione da cui recuperare gli estremi atto 
	    //		String errore = getMessageFromBundle("service_error.tracciato_equitalia.estremi_atto",
	    //			new Object[] { equitaliaTracciatiCfg.getCodiceRegAutOrdinanza() });
	    //		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    } else {
	    //		if (auts.size() > 1) {
	    //		    log.error("validaInformazioniIstanze# Istanza = {}. E' presente più di un autorizzazione con tipologia registro ", codiceIstanze,
	    //			    equitaliaTracciatiCfg.getCodiceRegAutOrdinanza());
	    //		    // Non è stata trovata nessuna autorizzazione da cui recuperare gli estremi atto 
	    //		    String errore = getMessageFromBundle("service_error.tracciato_equitalia.estremi_atto_non_univoco",
	    //			    new Object[] { equitaliaTracciatiCfg.getCodiceRegAutOrdinanza() });
	    //		    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		    errori.add(messageTracciato450Helper);
	    //		    isErroriPresenti = true;
	    //		}
	    //	    }
	    //	    String _codiceTipoMovimentoDataNotificaAtto = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovDataNotificaAtto(), "");
	    //	    log.debug("validaInformazioniIstanze# DATA NOTIFICA ATTO (064-071). Cerco tipo movimento = {}", _codiceTipoMovimentoDataNotificaAtto);
	    //	    Movimenti movimentoDataNotificaAtto = equitaliatracciatoHelperService.isMovimentoPresente(_codiceTipoMovimentoDataNotificaAtto, codIst);
	    //	    if (EntityUtils.getNestedProperty(movimentoDataNotificaAtto, "id.codice") == null) {
	    //		log.error("validaInformazioniIstanze# Istanza = {}, Movimento = {}. Movimento non trovato", codiceIstanze,
	    //			_codiceTipoMovimentoDataNotificaAtto);
	    //		// Non è stato trovato il movimento con codice {0} per determnare la data notifica atto
	    //		String errore = getMessageFromBundle("service_error.tracciato_equitalia.mov_data_notif_atto",
	    //			new Object[] { _codiceTipoMovimentoDataNotificaAtto });
	    //		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    }
	    //	    // VALIDAZIONE DATI TRASGRESSORE (istanza.richiendete)
	    //	    Anagrafe trasgressore = istanza.getRichiedente();
	    //	    List<String> erroriTrasgressore = validazioneAnagrafe(trasgressore);
	    //	    for (String e : erroriTrasgressore) {
	    //		messageTracciato450Helper = new MessageTracciato450Helper(e, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    }
	    //	    List<Istanzerichiedenti> irs = istanzerichiedentiService.findByIstanza(istanza);
	    //	    for (Istanzerichiedenti istanzerichiedenti : irs) {
	    //		List<String> erroriSoggcollegati = validazioneAnagrafe(istanzerichiedenti.getRichiedente());
	    //		for (String e : erroriSoggcollegati) {
	    //		    messageTracciato450Helper = new MessageTracciato450Helper(e, true);
	    //		    errori.add(messageTracciato450Helper);
	    //		    isErroriPresenti = true;
	    //		}
	    //	    }
	    //	    log.debug("validaInformazioniIstanze# Validazione testo verbale. Campo dinamico = {}", equitaliaTracciatiCfg.getDynCampiTestVerbale());
	    //	    List<Istanzedyn2dati> id2d = istanzedyn2datiService.findByIstanzaAndNomeCampo(codIst, equitaliaTracciatiCfg.getDynCampiTestVerbale());
	    //	    if (id2d.isEmpty()) {
	    //		String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_testo_verbale",
	    //			new Object[] { equitaliaTracciatiCfg.getDynCampiTestVerbale() });
	    //		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    } else {
	    //		if (id2d.size() > 1) {
	    //		    String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_testo_verbale_maggiore_di_uno",
	    //			    new Object[] { equitaliaTracciatiCfg.getDynCampiTestVerbale() });
	    //		    messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		    errori.add(messageTracciato450Helper);
	    //		    isErroriPresenti = true;
	    //		} else {
	    //		    String testo = id2d.get(0).getValoredecodificato();
	    //		    if (testo.length() > 320) {
	    //			String estremi = "";
	    //			if (!istanza.getAutorizzazionis().isEmpty()) {
	    //			    List<Autorizzazioni> a = new ArrayList<Autorizzazioni>(istanza.getAutorizzazionis());
	    //			    estremi = a.get(0).getAutoriznumero() + " del " + Utilities.formatDate(a.get(0).getAutorizdata(), false);
	    //			}
	    //			String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_testo_verbale_troppo_lungo", new Object[] {
	    //				equitaliaTracciatiCfg.getDynCampiTestVerbale(), testo.length(), 320, estremi });
	    //			messageTracciato450Helper = new MessageTracciato450Helper(errore, false);
	    //			errori.add(messageTracciato450Helper);
	    //			isErroriPresenti = true;
	    //		    }
	    //		}
	    //	    }
	    //	    log.debug("validaInformazioniIstanze# Validazione numero verbale. Campo dinamico = {}", equitaliaTracciatiCfg.getDynCampiNumeroVerbale());
	    //	    List<Istanzedyn2dati> id2dNumeroVerbale = istanzedyn2datiService.findByIstanzaAndNomeCampo(codIst,
	    //		    equitaliaTracciatiCfg.getDynCampiNumeroVerbale());
	    //	    if (id2dNumeroVerbale.isEmpty()) {
	    //		String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_numero_verbale",
	    //			new Object[] { equitaliaTracciatiCfg.getDynCampiNumeroVerbale() });
	    //		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    }
	    //	    log.debug("validaInformazioniIstanze# Validazione data verbale. Campo dinamico = {}", equitaliaTracciatiCfg.getDynCampiDataVerbale());
	    //	    List<Istanzedyn2dati> id2dDataVerbale = istanzedyn2datiService.findByIstanzaAndNomeCampo(codIst,
	    //		    equitaliaTracciatiCfg.getDynCampiDataVerbale());
	    //	    if (id2dDataVerbale.isEmpty()) {
	    //		String errore = getMessageFromBundle("service_error.tracciato_equitalia.campo_data_verbale",
	    //			new Object[] { equitaliaTracciatiCfg.getDynCampiDataVerbale() });
	    //		messageTracciato450Helper = new MessageTracciato450Helper(errore, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    }
	    //	    log.debug("validaInformazioniIstanze# validazione oneri sanzioni......");
	    //	    String e_onere_1_sazione = validazioneOneriString(istanza, equitaliaTracciatiCfg.getMapEqInSanAmmComOner());
	    //	    if (StringUtils.isNotBlank(e_onere_1_sazione)) {
	    //		messageTracciato450Helper = new MessageTracciato450Helper(e_onere_1_sazione, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    }
	    //	    //String e_onere_2 = validazioneOneri(istanza, equitaliaTracciatiCfg.getMapEqInSpSanAmmComOner());
	    //	    String e_onere_2_spese_sanzione = validazioneOneriString(istanza, equitaliaTracciatiCfg.getMapEqInSpSanAmmComOner());
	    //	    if (StringUtils.isNotBlank(e_onere_2_spese_sanzione)) {
	    //		messageTracciato450Helper = new MessageTracciato450Helper(e_onere_2_spese_sanzione, true);
	    //		errori.add(messageTracciato450Helper);
	    //		isErroriPresenti = true;
	    //	    }
	    //	    // set valore bean 
	    //	    bean.setValore(errori);
	    result.add(bean);
	}
	log.debug("validaInformazioniIstanze# Analisi campi pacchetto istanze per creazione tracciato terminata con errori = {}", isErroriPresenti);
	return result;
    }

    private String validazioneOneriString(Istanze istanza, String codiceTipoOnere) {

	String errore = "";
	String[] _codiceTipoOnere = StringUtils.split(codiceTipoOnere, ",");
	Boolean isTrovatoAlmenoUno = false;
	for (String cod : _codiceTipoOnere) {
	    List<Istanzeoneri> onereSanAmministrativa = istanzeoneriService.findByIstanzaAndCausale(istanza.getId().getCodice(),
		    Integer.parseInt(cod));
	    if (!onereSanAmministrativa.isEmpty()) {
		isTrovatoAlmenoUno = true;
		break;
	    }
	}
	if (!isTrovatoAlmenoUno) {
	    errore = getMessageFromBundle("service_error.tracciato_equitalia.onere_sansione_non_presente", new Object[] { codiceTipoOnere });
	}
	//	List<Istanzeoneri> onereSanAmministrativa = istanzeoneriService.findByIstanzaAndEndoAndCausale(istanza.getId().getCodice(), codiceTipoOnere);
	//	if (onereSanAmministrativa.isEmpty()) {
	//	    errore = getMessageFromBundle("service_error.tracciato_equitalia.onere_sansione_non_presente", new Object[] { codiceTipoOnere });
	//	} else {
	//	    if (onereSanAmministrativa.size() > 1) {
	//		errore = getMessageFromBundle("service_error.tracciato_equitalia.onere_sansione_maggiore_di_uno", new Object[] { codiceTipoOnere });
	//	    }
	//	}
	return errore;
    }

    private String validazioneOneri(Istanze istanza, Integer codiceTipoOnere) {

	String errore = "";
	List<Istanzeoneri> onereSanAmministrativa = istanzeoneriService.findByIstanzaAndCausale(istanza.getId().getCodice(), codiceTipoOnere);
	if (onereSanAmministrativa.isEmpty()) {
	    errore = getMessageFromBundle("service_error.tracciato_equitalia.onere_sansione_non_presente", new Object[] { codiceTipoOnere });
	} else {
	    if (onereSanAmministrativa.size() > 1) {
		errore = getMessageFromBundle("service_error.tracciato_equitalia.onere_sansione_maggiore_di_uno", new Object[] { codiceTipoOnere });
	    }
	}
	return errore;
    }

    private List<String> validazioneAnagrafe(Anagrafe a) {

	List<String> e = new ArrayList<String>();
	log.debug("validazioneAnagrafe# DATA NASCITA TRASGRESSORE (245-252). Trasgressore = {}", a.getId().getCodice());
	if (a.getTipoanagrafe().equals(WebConstants.PERSONA_FISICA)) {
	    if (a.getDatanascita() == null) {
		log.error("validazioneAnagrafe# Trasgressore/Obbiglato = {}. Data nascita non trovata", a.getId().getCodice());
		// Non è stata trovata la data di nascita per il trasgressore {0}[{1}]
		String errore = getMessageFromBundle("service_error.tracciato_equitalia.data_nascita_trasgressore",
			new Object[] { a.getDescrizioneRichiedente(), a.getId().getCodice() });
		e.add(errore);
	    }
	}
	// Non obbligatori, non li controlliamo
	//	    trasgressore.getComuneNascita();
	//	    trasgressore.getProvincia();
	//.
	log.debug("validazioneAnagrafe# COMUNE RESIDENZA  [259 - 391]. Trasgressore = {}", a.getId().getCodice());
	if (a.getComuneResidenza() == null) {
	    log.error("validazioneAnagrafe# Trasgressore = {}. Comune residenza", a.getId().getCodice());
	    // Non è stato trovato il comune di residenza per il trasgressore {0}[{1}]
	    String errore = getMessageFromBundle("service_error.tracciato_equitalia.comune_residenza_trasgressore",
		    new Object[] { a.getDescrizioneRichiedente(), a.getId().getCodice() });
	    e.add(errore);
	}
	if (StringUtils.isBlank(a.getIndirizzo())) {
	    log.error("validazioneAnagrafe# INDIRIZZO [259 - 293]  Trasgressore = {}. Indirizzo residenza", a.getId().getCodice());
	    // Non è stato trovato l'indirizzo di residenza per il trasgressore {0}[{1}]
	    String errore = getMessageFromBundle("service_error.tracciato_equitalia.indirizzo_residenza_trasgressore",
		    new Object[] { a.getDescrizioneRichiedente(), a.getId().getCodice() });
	    e.add(errore);
	}
	if (a.getTipoanagrafe().equals(WebConstants.PERSONA_FISICA)) {
	    if (StringUtils.isBlank(a.getCodicefiscale())) {
		String errore = getMessageFromBundle("service_error.tracciato_equitalia.codicefiscale_non_presente",
			new Object[] { a.getDescrizioneRichiedente(), a.getId().getCodice() });
		e.add(errore);
	    }
	} else {
	    if (StringUtils.isBlank(a.getPartitaiva())) {
		String errore = getMessageFromBundle("service_error.tracciato_equitalia.pi_non_presente",
			new Object[] { a.getDescrizioneRichiedente(), a.getId().getCodice() });
		e.add(errore);
	    }
	}
	return e;
    }

    @Override
    public Integer insertTracciatoEquitalia450(EquitaliaTracciatiCfg equitaliaTracciatiCfg, List<Integer> codiceIstanze, String nomeResponsabile,
	    String cognomeResponsabile, boolean isForzaCreazioneTracciato) throws Exception {

	log.debug("insertTracciatoEquitalia450# Forza creazione tracciato con errori = {}", isForzaCreazioneTracciato);
	// Istanzio in nuovo file da creare
	// METTERE IN CONFIGURAZIONE TEMPLATE NOME
	// METTERE IN CONFIGURAZIONE ESTENSIONE FILE
	Equitaliatracciato equitaliatracciato = new Equitaliatracciato();
	StringBuffer nomefile = new StringBuffer("TRACCIATO-EQUITALIA-450");
	List<EquitaliatracciatoD> equitaliatracciatoDs = new ArrayList<EquitaliatracciatoD>();
	EquitaliatracciatoD equitaliatracciatoD = null;
	File tracciato450 = null;
	try {
	    populateEquitaliaTracciato(equitaliatracciato);
	    String progressivoMinutaAnno = equitaliatracciato.getProgressivoAnno().toString();
	    //nomefile = nomefile.append("_").append(date);
	    FileWriter fileWriter = null;
	    log.debug("insertTracciatoEquitalia450# Ciclo le istanze recuperate...");
	    // indica il progressivo dei record inseriti nel tracciato. E comulativo tra tutte le tipologie di record M20,M30,..,M50
	    Integer progressivoRecord = 1;
	    // indica il progressivo dei record istanza inseriti nel tracciato. 
	    Integer numeroRecordIstanza = 1;
	    Integer numeroRecordM20 = 0;
	    Integer numeroRecordM21 = 0;
	    Integer numeroRecordM22 = 0;
	    Integer numeroRecordM23 = 0;
	    Integer numeroRecordM30 = 0;
	    Integer numeroRecordM40 = 0;
	    Integer numeroRecordM50 = 0;
	    Integer numeroRecordM51 = 0;
	    List<String> records = new ArrayList<String>();
	    EquitaliatracciatoHelper M30 = null;
	    EquitaliatracciatoHelper M40 = null;
	    EquitaliatracciatoHelper M50_sazione = null;
	    EquitaliatracciatoHelper M50_spese_sazione = null;
	    BigDecimal sommaTotaleOneri = new BigDecimal(0);
	    for (Integer codIstanza : codiceIstanze) {
		boolean istanzaConErrori = false;
		if (isForzaCreazioneTracciato) {
		    ChiaveValoreBean<String, List<MessageTracciato450Helper>> chiaveValoreBean = this.validaInformazioniIstanza(codIstanza,
			    equitaliaTracciatiCfg);
		    if (!chiaveValoreBean.getValore().isEmpty()) {
			List<MessageTracciato450Helper> list = chiaveValoreBean.getValore();
			for (MessageTracciato450Helper messageTracciato450Helper : list) {
			    if (messageTracciato450Helper.getIsError()) {
				istanzaConErrori = true;
				break;
			    }
			}
		    }
		}
		if (!istanzaConErrori) {
		    Istanze istanza = istanzeService.findById(new PkId(codIstanza));
		    equitaliatracciatoD = new EquitaliatracciatoD();
		    equitaliatracciatoD.setEquitaliatracciato(equitaliatracciato);
		    equitaliatracciatoD.setIstanze(istanza);
		    equitaliatracciatoDs.add(equitaliatracciatoD);
		    log.debug("insertTracciatoEquitalia450# produco riga M20 ...");
		    List<Istanzerichiedenti> irs = istanzerichiedentiService.findByIstanza(codIstanza);
		    String presenzaCoobligatiRecordTrasgressore = "1";
		    if (!irs.isEmpty()) {
			presenzaCoobligatiRecordTrasgressore = "2";
		    }
		    EquitaliatracciatoHelper M20Richiedente = equitaliatracciatoHelperService.createRecordM20(equitaliaTracciatiCfg, istanza,
			    istanza.getRichiedente(), progressivoRecord, numeroRecordIstanza, presenzaCoobligatiRecordTrasgressore);
		    numeroRecordM20++;
		    progressivoRecord++;
		    // add record 
		    records.add(M20Richiedente.getRigoTracciato());
		    for (Istanzerichiedenti istanzerichiedenti : irs) {
			EquitaliatracciatoHelper M20SoggettiCollegati = equitaliatracciatoHelperService.createRecordM20(equitaliaTracciatiCfg,
				istanza, istanzerichiedenti.getRichiedente(), progressivoRecord, numeroRecordIstanza, "C");
			progressivoRecord++;
			numeroRecordM20++;
			// add record
			records.add(M20SoggettiCollegati.getRigoTracciato());
		    }
		    // produco i record M30
		    log.debug("insertTracciatoEquitalia450# produco riga M30 ...");
		    M30 = equitaliatracciatoHelperService.createRecordM30(equitaliaTracciatiCfg, istanza, progressivoRecord, numeroRecordIstanza);
		    records.add(M30.getRigoTracciato());
		    numeroRecordM30++;
		    progressivoRecord++;
		    // produco i record M40
		    log.debug("insertTracciatoEquitalia450# produco riga M40 ...");
		    M40 = equitaliatracciatoHelperService.createRecordM40(equitaliaTracciatiCfg, istanza, progressivoRecord, numeroRecordIstanza);
		    records.add(M40.getRigoTracciato());
		    numeroRecordM40++;
		    progressivoRecord++;
		    //numeroRecordIstanza++;
		    // produco i record M50
		    log.debug("insertTracciatoEquitalia450# produco riga M50 ...");
		    Integer progressivoOnere = 1;
		    M50_sazione = equitaliatracciatoHelperService.createRecordM50(equitaliaTracciatiCfg,
			    equitaliaTracciatiCfg.getMapEqInSanAmmComOner(), EquitaliatracciatoHelperServiceImpl.SAN_AMMINISTRAZIONE_COMUNALE,
			    istanza, progressivoRecord, numeroRecordIstanza, progressivoOnere, true);
		    records.add(M50_sazione.getRigoTracciato());
		    numeroRecordM50++;
		    progressivoRecord++;
		    progressivoOnere++;
		    M50_spese_sazione = equitaliatracciatoHelperService.createRecordM50(equitaliaTracciatiCfg,
			    equitaliaTracciatiCfg.getMapEqInSpSanAmmComOner(),
			    EquitaliatracciatoHelperServiceImpl.SPESE_SAN_AMMINISTRAZIONE_COMUNALE, istanza, progressivoRecord, numeroRecordIstanza,
			    progressivoOnere, false);
		    records.add(M50_spese_sazione.getRigoTracciato());
		    numeroRecordM50++;
		    progressivoRecord++;
		    // sommo tutti gli oneri ( logica da rivedere in futuro se vogliamo generelazzare la scelta della tipologi adegli oner per altri tracciati equitalia
		    // o se si vorrà gestire il mapping tra ineri vbg e codici oneri equitalia
		    // La somma totale degli serve per popolare il record M99
		    String[] _tipoOneriSanzione = StringUtils.split(equitaliaTracciatiCfg.getMapEqInSanAmmComOner(), ",");
		    for (String cod : _tipoOneriSanzione) {
			List<Istanzeoneri> oneriSanzione = istanzeoneriService.findByIstanzaAndCausale(codIstanza, Integer.parseInt(cod));
			if (oneriSanzione != null && !oneriSanzione.isEmpty() && oneriSanzione.get(0).getPrezzo() != null) {
			    sommaTotaleOneri = sommaTotaleOneri.add(oneriSanzione.get(0).getPrezzo());
			}
		    }
		    String[] _tipoOneriSanzioneSpese = StringUtils.split(equitaliaTracciatiCfg.getMapEqInSpSanAmmComOner(), ",");
		    for (String cod : _tipoOneriSanzioneSpese) {
			List<Istanzeoneri> oneriSanzioneSpese = istanzeoneriService.findByIstanzaAndCausale(codIstanza, Integer.parseInt(cod));
			if (oneriSanzioneSpese != null && !oneriSanzioneSpese.isEmpty() && oneriSanzioneSpese.get(0).getPrezzo() != null) {
			    sommaTotaleOneri = sommaTotaleOneri.add(oneriSanzioneSpese.get(0).getPrezzo());
			}
		    }
		    // Aggiorno il numero di istanze elaborate corrisponde al numero della minuta inserita nell'intestazione 
		    numeroRecordIstanza++;
		    Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(equitaliaTracciatiCfg.getCodiceMovIstanzaARuolo()));
		    Movimenti m = new Movimenti();
		    m.setTipomovimento(tm);
		    Date now = new Date();
		    m.setData(now);
		    m.setDatainserimento(now);
		    m.setIstanza(istanza);
		    movimentiService.insert(m);
		}
	    }
	    log.debug("insertTracciatoEquitalia450# produco riga M00 ...");
	    String M00 = equitaliatracciatoHelperService.createRecordM00(equitaliaTracciatiCfg, progressivoMinutaAnno, sommaTotaleOneri.toString(),
		    nomeResponsabile, cognomeResponsabile);
	    log.debug("insertTracciatoEquitalia450# produco riga M99 ...");
	    String M99 = equitaliatracciatoHelperService.createRecordM99(equitaliaTracciatiCfg, progressivoMinutaAnno, sommaTotaleOneri.toString(),
		    sommaTotaleOneri.toString(), 0, numeroRecordM40, numeroRecordM20, numeroRecordM21, numeroRecordM22, numeroRecordM23,
		    numeroRecordM30, numeroRecordM50, numeroRecordM51);
	    tracciato450 = File.createTempFile(nomefile.toString(), ".txt");
	    fileWriter = new FileWriter(tracciato450);
	    //System.out.println("M00: " + M00.length());
	    fileWriter.append(M00).append("\n");
	    for (String record : records) {
		//System.out.println("MXX: " + record.length());
		fileWriter.append(record).append("\n");
	    }
	    //System.out.println("M99: " + M99.length());
	    fileWriter.append(M99).append("\n");
	    log.debug("insertTracciatoEquitalia450# fileWriter flush... ");
	    fileWriter.flush();
	    log.debug("insertTracciatoEquitalia450# fileWriter close... ");
	    fileWriter.close();
	    log.debug("insertTracciatoEquitalia450# Insert record EquitaliaTracciato......");
	    Oggetti o = populateOggetto(nomefile + ".txt", tracciato450);
	    log.debug("insertTracciatoEquitalia450# Insert record EquitaliaTracciatoD..... Numero record da inserire = {}",
		    equitaliatracciatoDs.size());
	    oggettiService.insert(o);
	    equitaliatracciato.setOggetti(o);
	    this.insert(equitaliatracciato);
	    for (EquitaliatracciatoD ed : equitaliatracciatoDs) {
		equitaliatracciatoDService.insert(ed);
	    }
	} catch (IOException e) {
	    log.error("insertTracciatoEquitalia450# Errore durante la creazione del file = {}", nomefile.toString() + ".txt");
	    throw e;
	} catch (Exception e) {
	    log.error("insertTracciatoEquitalia450#{}", e);
	    throw e;
	} finally {
	    if (tracciato450 != null) {
		gracefullyDeleteFiles(tracciato450);
	    }
	}
	if (EntityUtils.getNestedProperty(equitaliatracciato, "id.codice") != null) {
	    return equitaliatracciato.getId().getCodice();
	}
	return null;
    }

    private void gracefullyDeleteFiles(File tempFile) {

	try {
	    FileUtils.forceDelete(tempFile);
	} catch (Exception e) {
	    log.error("gracefullyDeleteFiles# {}-{}", tempFile.getName(), e.getMessage());
	}
    }

    @Override
    public Integer findProgressivoAnno(String anno) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("anno", anno, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.order("progressivoAnno", OrderTypeEnum.DESC));
	List<Equitaliatracciato> l = equitaliatracciatoDAO.findByFilterTable(ft);
	if (!l.isEmpty()) {
	    return l.get(0).getProgressivoAnno();
	}
	return 0;
    }

    @Override
    public void deleteTracciato(Equitaliatracciato equitaliatracciato) {

	EquitaliaTracciatiCfg equitaliaTracciatiCfg = equitaliaTracciatiCfgService.findBySoftware();
	String codiceMovDaEliminare = equitaliaTracciatiCfg.getCodiceMovIstanzaARuolo();
	List<EquitaliatracciatoD> l = equitaliatracciatoDService.findByTracciato(equitaliatracciato.getId().getCodice());
	for (EquitaliatracciatoD equitaliatracciatoD : l) {
	    Movimenti mov = movimentiService.findMovimentiByTipoMovimento(equitaliatracciatoD.getIstanze().getId().getCodice(), codiceMovDaEliminare);
	    if (mov != null)
		movimentiService.delete(mov);
	    equitaliatracciatoDService.delete(equitaliatracciatoD);
	}
	this.delete(equitaliatracciato);
    }

    @Override
    public Map<String, String> checkFileRowLenth(Equitaliatracciato equitaliatracciato) {

	Map<String, String> r = new TreeMap<String, String>();
	Oggetti o = oggettiService.findById(new PkId(equitaliatracciato.getOggetti().getId().getCodice()));
	byte[] b = o.getOggetto();
	File tempFile = null;
	BufferedReader reader = null;
	try {
	    tempFile = File.createTempFile("TRACCIATO-450", "-DUPILCATE_FOR_CHECK");
	    FileUtils.writeByteArrayToFile(tempFile, b);
	    reader = new BufferedReader(new FileReader(tempFile));
	    String line = reader.readLine();
	    int i = 1;
	    while (line != null) {
		if (StringUtils.isNotBlank(line) && line.length() > 450) {
		    String contatore = StringUtils.leftPad(String.valueOf(i), 4, "0");
		    r.put(contatore + " [" + line.substring(0, 3) + "]", String.valueOf(line.length()));
		    //r.put(contatore, String.valueOf(line.length()));
		}
		line = reader.readLine();
		i++;
	    }
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    try {
		tempFile.deleteOnExit();
	    } catch (Exception e1) {
		log.error("checkFileRowLenth# Impossibile cancellare il file. {} ", e1);
	    }
	    try {
		reader.close();
	    } catch (Exception e2) {
		log.error("checkFileRowLenth# Impossibile chiurede il file reader. {} ", e2);
	    }
	}
	return r;
    }

    private void populateEquitaliaTracciato(Equitaliatracciato equitaliatracciato) {

	if (equitaliatracciato == null) {
	    equitaliatracciato = new Equitaliatracciato();
	}
	Calendar c = Calendar.getInstance();
	equitaliatracciato.setDataCreazione(c.getTime());
	equitaliatracciato.setAnno(String.valueOf(c.get(Calendar.YEAR)));
	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	equitaliatracciato.setResponsabili(r);
	Software sw = softwareService.findById(ORMHelper.getSoftware());
	equitaliatracciato.setSoftware(sw);
	Integer progressivoAnno = this.findProgressivoAnno(String.valueOf(c.get(Calendar.YEAR)));
	progressivoAnno = progressivoAnno + 1;
	equitaliatracciato.setProgressivoAnno(progressivoAnno);
    }

    private Oggetti populateOggetto(String nome, File file) throws Exception {

	Oggetti o = new Oggetti();
	o.setNomefile(file.getName());
	InputStream targetStream = null;
	try {
	    o.setNomefile(file.getName());
	    targetStream = new FileInputStream(file);
	    byte[] b = IOUtils.toByteArray(targetStream);
	    o.setOggetto(b);
	    o.setDimensioneFile(b.length);
	} catch (FileNotFoundException e) {
	    throw e;
	} catch (Exception e) {
	    throw e;
	}
	return o;
    }

    protected boolean isDeleteAllowed(Equitaliatracciato entity) {

	boolean delete = true;
	//	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	TODO_validare_la_delete
	//	// esempio:
	//	// if (entity.getList().size() > 0) {
	//	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//	// }
	//	if (!_ivs.isEmpty()) {
	//		this.throwValidationMessages(_ivs);
	//	}
	return delete;
    }

    @Override
    public ChiaveValoreBean<String, byte[]> downloadTracciatoEquitalia450Excel(EquitaliaTracciatiCfg equitaliaTracciatiCfg,
	    List<Integer> codiceIstanze, String nomeResponsabile, String cognomeResponsabile, boolean isForzaCreazioneTracciato) throws Exception {

	ChiaveValoreBean<String, byte[]> cv = new ChiaveValoreBean<String, byte[]>();
	StringBuffer nomefile = new StringBuffer("TRACCIATO-EQUITALIA-450");
	Oggetti o = null;
	File tracciato450 = null;
	try {
	    //nomefile = nomefile.append("_").append(date);
	    FileWriter fileWriter = null;
	    log.debug("downloadTracciatoEquitalia450Excel# Ciclo le istanze recuperate...");
	    // indica il progressivo dei record inseriti nel tracciato. E comulativo tra tutte le tipologie di record M20,M30,..,M50
	    Integer progressivoRecord = 1;
	    // indica il progressivo dei record istanza inseriti nel tracciato. 
	    Integer numeroRecordIstanza = 1;
	    Integer numeroRecordM20 = 0;
	    Integer numeroRecordM30 = 0;
	    Integer numeroRecordM40 = 0;
	    Integer numeroRecordM50 = 0;
	    List<String> records = new ArrayList<String>();
	    EquitaliatracciatoHelper M30 = null;
	    EquitaliatracciatoHelper M40 = null;
	    EquitaliatracciatoHelper M50_sazione = null;
	    EquitaliatracciatoHelper M50_spese_sazione = null;
	    List<ChiaveValoreBean<String, String>> m20_1 = new ArrayList<ChiaveValoreBean<String, String>>();
	    List<ChiaveValoreBean<String, String>> m20_2 = new ArrayList<ChiaveValoreBean<String, String>>();
	    List<ChiaveValoreBean<String, String>> m30 = new ArrayList<ChiaveValoreBean<String, String>>();
	    List<ChiaveValoreBean<String, String>> m40 = new ArrayList<ChiaveValoreBean<String, String>>();
	    List<ChiaveValoreBean<String, String>> m50_sazione = new ArrayList<ChiaveValoreBean<String, String>>();
	    List<ChiaveValoreBean<String, String>> m50_spese_sazione = new ArrayList<ChiaveValoreBean<String, String>>();
	    for (Integer codIstanza : codiceIstanze) {
		boolean istanzaConErrori = false;
		if (isForzaCreazioneTracciato) {
		    ChiaveValoreBean<String, List<MessageTracciato450Helper>> chiaveValoreBean = this.validaInformazioniIstanza(codIstanza,
			    equitaliaTracciatiCfg);
		    if (!chiaveValoreBean.getValore().isEmpty()) {
			List<MessageTracciato450Helper> list = chiaveValoreBean.getValore();
			for (MessageTracciato450Helper messageTracciato450Helper : list) {
			    if (messageTracciato450Helper.getIsError()) {
				istanzaConErrori = true;
				break;
			    }
			}
		    }
		}
		if (!istanzaConErrori) {
		    m20_1 = new ArrayList<ChiaveValoreBean<String, String>>();
		    m20_2 = new ArrayList<ChiaveValoreBean<String, String>>();
		    m30 = new ArrayList<ChiaveValoreBean<String, String>>();
		    m40 = new ArrayList<ChiaveValoreBean<String, String>>();
		    m50_sazione = new ArrayList<ChiaveValoreBean<String, String>>();
		    m50_spese_sazione = new ArrayList<ChiaveValoreBean<String, String>>();
		    Istanze istanza = istanzeService.findById(new PkId(codIstanza));
		    log.debug("downloadTracciatoEquitalia450Excel# produco riga M20 ...");
		    List<Istanzerichiedenti> irs = istanzerichiedentiService.findByIstanza(codIstanza);
		    String presenzaCoobligatiRecordTrasgressore = "1";
		    if (!irs.isEmpty()) {
			presenzaCoobligatiRecordTrasgressore = "2";
		    }
		    EquitaliatracciatoHelper M20Richiedente = equitaliatracciatoHelperService.createRecordM20(equitaliaTracciatiCfg, istanza,
			    istanza.getRichiedente(), progressivoRecord, numeroRecordIstanza, presenzaCoobligatiRecordTrasgressore);
		    numeroRecordM20++;
		    progressivoRecord++;
		    // add record 
		    m20_1 = M20Richiedente.getLISTA_CAMPI_CSV_EXCEL();
		    for (Istanzerichiedenti istanzerichiedenti : irs) {
			EquitaliatracciatoHelper M20SoggettiCollegati = equitaliatracciatoHelperService.createRecordM20(equitaliaTracciatiCfg,
				istanza, istanzerichiedenti.getRichiedente(), progressivoRecord, numeroRecordIstanza, "C");
			progressivoRecord++;
			numeroRecordM20++;
			// add record
			m20_2 = M20SoggettiCollegati.getLISTA_CAMPI_CSV_EXCEL();
		    }
		    // produco i record M30
		    log.debug("downloadTracciatoEquitalia450Excel# produco riga M30 ...");
		    M30 = equitaliatracciatoHelperService.createRecordM30(equitaliaTracciatiCfg, istanza, progressivoRecord, numeroRecordIstanza);
		    m30 = M30.getLISTA_CAMPI_CSV_EXCEL();
		    numeroRecordM30++;
		    progressivoRecord++;
		    // produco i record M40
		    log.debug("downloadTracciatoEquitalia450Excel# produco riga M40 ...");
		    M40 = equitaliatracciatoHelperService.createRecordM40(equitaliaTracciatiCfg, istanza, progressivoRecord, numeroRecordIstanza);
		    m40 = M40.getLISTA_CAMPI_CSV_EXCEL();
		    numeroRecordM40++;
		    progressivoRecord++;
		    //numeroRecordIstanza++;
		    // produco i record M50
		    log.debug("downloadTracciatoEquitalia450Excel# produco riga M50 ...");
		    Integer progressivoOnere = 1;
		    M50_sazione = equitaliatracciatoHelperService.createRecordM50(equitaliaTracciatiCfg,
			    equitaliaTracciatiCfg.getMapEqInSanAmmComOner(), EquitaliatracciatoHelperServiceImpl.SAN_AMMINISTRAZIONE_COMUNALE,
			    istanza, progressivoRecord, numeroRecordIstanza, progressivoOnere, true);
		    m50_sazione = M50_sazione.getLISTA_CAMPI_CSV_EXCEL();
		    numeroRecordM50++;
		    progressivoRecord++;
		    progressivoOnere++;
		    M50_spese_sazione = equitaliatracciatoHelperService.createRecordM50(equitaliaTracciatiCfg,
			    equitaliaTracciatiCfg.getMapEqInSpSanAmmComOner(),
			    EquitaliatracciatoHelperServiceImpl.SPESE_SAN_AMMINISTRAZIONE_COMUNALE, istanza, progressivoRecord, numeroRecordIstanza,
			    progressivoOnere, false);
		    m50_spese_sazione = M50_spese_sazione.getLISTA_CAMPI_CSV_EXCEL();
		    numeroRecordM50++;
		    progressivoRecord++;
		    // Intestazione
		    StringBuffer intestazione = new StringBuffer();
		    StringBuffer intetazioneM20 = new StringBuffer();
		    StringBuffer valori = new StringBuffer();
		    //
		    String codiceMovimentoAnno = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovAnnoDebito(), "");
		    Movimenti movimentoMaturazioneAnnoDebito = equitaliatracciatoHelperService.isMovimentoPresente(codiceMovimentoAnno, istanza
			    .getId().getCodice());
		    log.debug("identificativoPartita# ANNO MATURAZIONE ATTO");
		    //Date dataAtto = Utilities.addDays(movimentoMaturazioneAnnoDebito.getData(), 30);
		    String _annoAtto = Utilities.formatDate(movimentoMaturazioneAnnoDebito.getData(), "yyyy");
		    String codiceMovDataNotificaAtto = StringUtils.defaultIfEmpty(equitaliaTracciatiCfg.getCodiceMovDataNotificaAtto(), "");
		    Movimenti movimentoDataNotificaAtto = equitaliatracciatoHelperService.isMovimentoPresente(codiceMovDataNotificaAtto, istanza
			    .getId().getCodice());
		    String data_notifica_atto = Utilities.formatDate(movimentoDataNotificaAtto.getData(), "dd/MM/yyyy");
		    //
		    intestazione = intestazione.append("ANNO MATUR. ATTO;");
		    intestazione = intestazione.append("DATA NOTIF. ATTO;");
		    valori = valori.append(_annoAtto + ";");
		    valori = valori.append(data_notifica_atto + ";");
		    for (ChiaveValoreBean<String, String> chiaveValoreBean : m20_1) {
			if (numeroRecordIstanza == 1) {
			    intestazione = intestazione.append(chiaveValoreBean.getChiave()).append(";");
			    intetazioneM20 = intetazioneM20.append(chiaveValoreBean.getChiave()).append(";");
			}
			valori = valori.append(chiaveValoreBean.getValore()).append(";");
		    }
		    if (m20_2 != null && !m20_2.isEmpty()) {
			for (ChiaveValoreBean<String, String> chiaveValoreBean : m20_2) {
			    if (numeroRecordIstanza == 1) {
				intestazione = intestazione.append(chiaveValoreBean.getChiave()).append(";");
			    }
			    valori = valori.append(chiaveValoreBean.getValore()).append(";");
			}
		    } else {
			if (numeroRecordIstanza == 1) {
			    intestazione = intestazione.append(intetazioneM20);
			}
			valori = valori.append(StringUtils.repeat(";", 12));
		    }
		    for (ChiaveValoreBean<String, String> chiaveValoreBean : m30) {
			if (numeroRecordIstanza == 1) {
			    intestazione = intestazione.append(chiaveValoreBean.getChiave()).append(";");
			}
			valori = valori.append(chiaveValoreBean.getValore()).append(";");
		    }
		    for (ChiaveValoreBean<String, String> chiaveValoreBean : m40) {
			if (numeroRecordIstanza == 1) {
			    intestazione = intestazione.append(chiaveValoreBean.getChiave()).append(";");
			}
			valori = valori.append(chiaveValoreBean.getValore()).append(";");
		    }
		    for (ChiaveValoreBean<String, String> chiaveValoreBean : m50_sazione) {
			if (numeroRecordIstanza == 1) {
			    intestazione = intestazione.append(chiaveValoreBean.getChiave()).append(";");
			}
			valori = valori.append(chiaveValoreBean.getValore()).append(";");
		    }
		    for (ChiaveValoreBean<String, String> chiaveValoreBean : m50_spese_sazione) {
			if (numeroRecordIstanza == 1) {
			    intestazione = intestazione.append(chiaveValoreBean.getChiave()).append(";");
			}
			valori = valori.append(chiaveValoreBean.getValore()).append(";");
		    }
		    if (numeroRecordIstanza == 1) {
			records.add(intestazione.toString());
		    }
		    records.add(valori.toString());
		    //fileWriter.append(intestazione.toString()).append("\n");
		    numeroRecordIstanza++;
		}
		tracciato450 = File.createTempFile(nomefile.toString(), ".csv");
		String _nomefile = tracciato450.getName();
		cv.setChiave(_nomefile);
		fileWriter = new FileWriter(tracciato450);
		for (String string : records) {
		    fileWriter.append(string).append("\n");
		}
		//	    //System.out.println("M00: " + M00.length());
		//	    for (String record : records) {
		//		//System.out.println("MXX: " + record.length());
		//		fileWriter.append(record).append("\n");
		//	    }
		log.debug("downloadTracciatoEquitalia450Excel# fileWriter flush... ");
		fileWriter.flush();
		log.debug("downloadTracciatoEquitalia450Excel# fileWriter close... ");
		fileWriter.close();
		log.debug("downloadTracciatoEquitalia450Excel# Insert record EquitaliaTracciato......");
		o = populateOggetto(nomefile + ".csv", tracciato450);
	    }
	} catch (IOException e) {
	    log.error("downloadTracciatoEquitalia450Excel# Errore durante la creazione del file = {}", nomefile.toString() + ".txt");
	    throw e;
	} catch (Exception e) {
	    log.error("downloadTracciatoEquitalia450Excel#{}", e);
	    throw e;
	} finally {
	    if (tracciato450 != null) {
		gracefullyDeleteFiles(tracciato450);
	    }
	}
	cv.setValore(o.getOggetto());
	return cv;
    }

    public static void main(String[] args) {

	System.out.println();
    }

    @Override
    public void aggiornaDataCreazioneTracciato(Integer idTracciato, Date nuovaData) {

	Equitaliatracciato tracciato = this.findById(new PkId(idTracciato));
	tracciato.setDataCreazione(nuovaData);
	this.update(tracciato);
    }
}
