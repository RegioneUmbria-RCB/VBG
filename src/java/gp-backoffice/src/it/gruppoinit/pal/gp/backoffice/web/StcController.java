package it.gruppoinit.pal.gp.backoffice.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.StcNotificaBean;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiHelper;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.Dyn2ModellitValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.StcNotificaCommand;
import it.gruppoinit.pal.gp.core.domain.web.TipimovStcAltridatiValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.AmministrazioniCollegateService;
import it.gruppoinit.pal.gp.core.features.movimenti.stc.segnaposto.SegnapostiMovimentoSTCBuilder;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.stc.richiestapratica.RichiestaPraticaCollegataFactory;
import it.gruppoinit.pal.gp.core.features.stc.richiestapratica.RichiestaPraticaFromIstanzeParams;
import it.gruppoinit.pal.gp.core.features.stc.richiestapratica.RichiestaPraticaFromMovimentiParams;
import it.gruppoinit.pal.gp.core.features.stc.richiestapratica.RichiestaPraticaFromNotificaPraticaStoricaParams;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.DocumentiHelperService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.ResponsabilisoftwareService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.STCNotificaAttivitaException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.NodoNLAEnum;
import it.gruppoinit.pal.gp.core.service.impl.StcServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.types.DettaglioPraticaType;
import it.init.sigepro.rte.types.DettaglioPraticaVisuraType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Controller
@SessionAttributes(value = { "stcNotificaCommand", "istanze" })
public class StcController extends BaseController<StcNotificaCommand> {

    private Map<String, String> esecuzioneCopiaAllegati = new HashMap<String, String>();
    private static Logger log = LoggerFactory.getLogger(StcController.class);
    @Autowired
    private AmministrazioniService amministrazioniService;
    @Autowired
    private DocumentiHelperService documentiHelperService;
    @Autowired
    private Dyn2ModellitService dyn2ModellitService;
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private StcService stcService;
    @Autowired
    private DomandestcService domandestcService;
    @Autowired
    private ResponsabilisoftwareService responsabilisoftwareService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private InventarioprocEndoService inventarioprocEndoService;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private MovimentiZipLogicoService movimentiZipLogicoService;
    @Autowired
    private NlaHelperService nlaHelperService;
    @Autowired
    private AmministrazioniCollegateService amministrazioniCollegateService;

    @RequestMapping
    public String createNotificaInitialized(@RequestParam("codiceMovimento") Integer codiceMovimento, Model model,
	    @ModelAttribute("stcNotificaCommand") StcNotificaCommand stcNotificaCommand, BindingResult result, HttpServletRequest request)
	    throws STCNotificaAttivitaException {

	if (stcNotificaCommand == null) {
	    stcNotificaCommand = new StcNotificaCommand();
	}
	stcNotificaCommand.setCodiceMovimento(codiceMovimento);
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	String tipomovimento = movimento.getTipomovimento().getId().getTipomovimento();
	String codiceComune = movimento.getIstanza().getComune().getCodicecomune();
	Integer codiceAmministrazioneStc = null;
	if (movimento.getAmministrazioniStc() != null && movimento.getAmministrazioniStc().getId() != null
		&& movimento.getAmministrazioniStc().getId().getCodice() != null) {
	    codiceAmministrazioneStc = movimento.getAmministrazioniStc().getId().getCodice();
	}
	if (!validateNotifica(movimento, result)) {
	    return "stc/notificaError";
	}
	Integer codiceInventario = null;
	if (movimento.getEndoprocedimento() != null && movimento.getEndoprocedimento().getId() != null
		&& movimento.getEndoprocedimento().getId().getCodice() != null) {
	    codiceInventario = movimento.getEndoprocedimento().getId().getCodice();
	}
	//Lion recupero gli errori che hanno impedito la creazione degli alleagati per la notifica CART
	BindingResult errors = (BindingResult) request.getSession().getAttribute(FACCTConstants.SESSION_KEY_ERRORI_GENERA_ALLEGATI_NOTIFICA);
	if (errors != null) {
	    result.addAllErrors(errors);
	    request.getSession().removeAttribute(FACCTConstants.SESSION_KEY_ERRORI_GENERA_ALLEGATI_NOTIFICA);
	}
	//end Lion
	if (movimento != null) {
	    stcNotificaCommand.setCodiceIstanza(movimento.getIstanza().getId().getCodice());
	}
	//verifico se è presente uno zip logico
	stcNotificaCommand.setFlagTrasmettiZipLogico(this.movimentiZipLogicoService.isZipLogicoExistInMovimento(codiceMovimento));
	setPageAttributes(model, movimento);
	// Recupero tutte le schede configurate per  l'istanza .
	List<Dyn2ModellitValoreBean> modelliPresenti = stcService.findListaIstanzeModelli(movimento, null);
	StcNotificaBean entity = new StcNotificaBean();
	// Popolo i documenti da inviare con la trasmissione STC (doc istanza, doc endo, doc mov)
	/////////////////////////////////////////////////////////////////////////////////////////////////////
	DocumentiHelper documentiHelper = documentiHelperService.findDocumentiInvioDocumentiSTC(codiceMovimento);
	if (stcNotificaCommand.getEntity() != null) {
	    if (stcNotificaCommand.getEntity().getAltriDatiList() != null) {
		verificaSelezioneFile(documentiHelper, stcNotificaCommand.getEntity().getAltriDatiList());
	    }
	}
	entity.setDocumentiHelper(documentiHelper);
	////////////////////////////////////////////////////////////////////////////////////////////////////
	boolean isNotificaInteraPratica = Boolean.valueOf(entity.isNotificainterapratica());
	model.addAttribute("isNotificaInteraPratica", Boolean.valueOf(isNotificaInteraPratica));
	if (isNotificaInteraPratica && !movimento.getMovimentiZipLogicos().isEmpty()) {
	    FlashMessages.getWarnings()
		    .add("Attenzione! Il movimento è configurato per \"notificare l'intera pratica\" " +
			    "(MOVIMENTI --> CONFIGURAZIONI NOTIFICHE --> ENTI DESTINTATARI) ma è stato registrato anche " +
			    "uno zip logico e l'impostazione non è compatibile con la notifica se si decide di inviare lo zip logico.");
	}
	entity.setModelliList(modelliPresenti);
	entity.setMovimento(movimento);
	stcNotificaCommand.setEntity(entity);
	model.addAttribute("stcNotificaCommand", stcNotificaCommand);
	if (entity.isPraticaStorica()) {
	    RichiestaPraticaFromNotificaPraticaStoricaParams params = new RichiestaPraticaFromNotificaPraticaStoricaParams(this.stcService,
		    this.verticalizzazioniService, movimento);
	    RichiestaPraticaCollegataResponse praticaCollegataResp = RichiestaPraticaCollegataFactory.fromNotificaPraticaStoricaParams(params)
		    .getPraticaCollegata();
	    //RichiestaPraticaCollegataResponse praticaCollegataResp = stcService.richiestaPraticaCollegata(movimento.getIstanza().getId().getCodice(),
	    //    movimento.getId().getCodice(), null);
	    List<ErroreType> errori = praticaCollegataResp.getDettaglioErrore();
	    if (errori.isEmpty()) {
		String idEnte = movimento.getAmministrazioniStc().getStcIdente();
		String idSportello = movimento.getAmministrazioniStc().getStcIdsportello();
		// se la pratica tornata da richiestaPraticaCollegata appartiene allo sportello la lego
		if (idEnte.equalsIgnoreCase(praticaCollegataResp.getDettaglio().getSportello().getIdEnte())
			&& idSportello.equalsIgnoreCase(praticaCollegataResp.getDettaglio().getSportello().getIdSportello())) {
		    DettaglioPraticaType praticaCollegata = praticaCollegataResp.getDettaglio().getDettaglioPratica();
		    stcNotificaCommand.setPraticaCollegata(praticaCollegata);
		    stcNotificaCommand.setDisplayMode(StcNotificaCommand.NEW);
		} else {// altrimenti dico all'utente di cercarla con i parametri
		    stcNotificaCommand.setDisplayMode(StcNotificaCommand.PRATICA_STORICA);
		}
	    } else {
		stcNotificaCommand.setDisplayMode(StcNotificaCommand.PRATICA_STORICA);
	    }
	} else {
	    stcNotificaCommand.setDisplayMode(StcNotificaCommand.NEW);
	}
	model.addAttribute("isNotificaInteraPratica", Boolean.valueOf(entity.isNotificainterapratica()));
	//LION CHECK notifica_cart fra gli altri dati (per sapere se visualizzare il bottone 'genera allegati per notifica CART')
	boolean isNotificaCART = false;
	//END LION
	// check $_RIF_PROT_$ TRA GLI ALTRI DATI
	// se presente allora devo mettere una lista di movimenti da cui scegliere quelli per i riferimenti 
	// della protocollazione VEDI redmine http://redmine/redmine/issues/411
	if (stcNotificaCommand.getEntity() != null) {
	    if (stcNotificaCommand.getEntity().getAltriDatiList() != null) {
		if (stcNotificaCommand.getEntity().getAltriDatiList().size() > 0) {
		    String tipiMovRifProto = "";
		    boolean isRifProt = false;
		    for (TipimovStcAltridatiValoreBean ad : stcNotificaCommand.getEntity().getAltriDatiList()) {
			//LION nello stesso ciclo sugli altri dati cerco anche di individuare la chiave 
			if (ad.getChiave().getNomeCampo().equalsIgnoreCase(StcService.ALTRO_DATO_RISERVATO_RIFPROTO)) {
			    isRifProt = true;
			    tipiMovRifProto = ad.getValore();
			    //break; don't break my...    for loop
			}
			if (ad.getChiave().getNomeCampo().equalsIgnoreCase(StcService.ALTRO_DATO_RISERVATO_TIPO_OPERAZIONE_CART)
				&& ad.getValore().equalsIgnoreCase(StcService.ALTRO_DATO_RISERVATO_TIPO_OPERAZIONE_CART_NOTIFICA)) {
			    isNotificaCART = true;
			}
			//END LION
			//sostituzione dei segnaposto
			String valore = new SegnapostiMovimentoSTCBuilder(movimento, amministrazioniCollegateService, codiceComune)
				.sostituisciSegnaposto(ad.getValore());
			ad.setValore(valore);
		    }
		    if (isRifProt) {
			tipiMovRifProto = StringUtils.defaultString(tipiMovRifProto).trim();
			List<Movimenti> listMovimenti = new ArrayList<Movimenti>();
			if (StringUtils.isNotBlank(tipiMovRifProto)) {
			    // SE VALORE DEFAULT E' SETTATO ES: SUAP001 ALLORA PROPONGO SULLA COMBO SOLAMENTE L'ULTIMO MOVIMENTO DI QUEL TIPO A PARITA' DI DATA ORDINE.
			    List<Movimenti> movs = movimentiNoSecurityService.findMovimentiIstanzaFattiByTipoMovimento(tipiMovRifProto,
				    movimento.getIstanza().getId().getCodice());
			    for (Movimenti mov : movs) {
				if (StringUtils.isNotBlank(mov.getNumeroprotocollo()) && mov.getDataprotocollo() != null) {
				    stcNotificaCommand.setMovimentoPerRiferimentiProt(mov);
				    listMovimenti.add(mov);
				    break;
				}
			    }
			} else {
			    // SE VALORE DEFAULT NULLO PROPONGO COME VALORE DEL CAMPO UNA COMBO CON LA LISTA DEI MOVIMENTI PROTOCOLLATI
			    List<Movimenti> movs = movimentiNoSecurityService.findEseguitiByIstanza(movimento.getIstanza());
			    for (Movimenti mov : movs) {
				if (StringUtils.isNotBlank(mov.getNumeroprotocollo()) && mov.getDataprotocollo() != null) {
				    listMovimenti.add(mov);
				}
			    }
			}
			stcNotificaCommand.setListMovimentiRifProto(listMovimenti);
		    }
		    stcNotificaCommand.setRifProto(isRifProt);
		}
	    }
	}
	boolean isPresentiSubEndo = false;
	List<IdentificativoDescrizioneBean> idbs = new ArrayList<IdentificativoDescrizioneBean>();
	if (!isNotificaInteraPratica) {
	    if (codiceInventario != null) {
		if (movimentiService.verificaSeNotificareSubEndo(tipomovimento, codiceAmministrazioneStc)) {
		    // 
		    Inventarioprocedimenti ip = inventarioprocedimentiService.findById(new PkId(codiceInventario));
		    log.debug("Devo notificare i subendo subEndo del codice inventario {} per il movimento {} ", codiceInventario, codiceMovimento);
		    List<InventarioprocEndo> subEndos = inventarioprocEndoService.findByInventarioprocT(ORMHelper.getIdcomune(), codiceInventario,
			    null, null, null, null, false); // recupero tutti gli endo indipendentemente dal comune / se abilitati / pubblicati o meno
		    isPresentiSubEndo = true;
		    if (subEndos.size() > 0) {
			log.debug("trovati i subEndo del codice inventario {}", codiceInventario);
			for (InventarioprocEndo ipe : subEndos) {
			    IdentificativoDescrizioneBean idb = new IdentificativoDescrizioneBean();
			    idb.setId(ipe.getInventarioprocEndoD().getId().getCodice());
			    idb.setDescrizione(ipe.getInventarioprocEndoD().getProcedimento());
			    idbs.add(idb);
			}
		    }
		    IdentificativoDescrizioneBean idb = new IdentificativoDescrizioneBean();
		    idb.setId(codiceInventario);
		    idb.setDescrizione(ip.getProcedimento());
		    idbs.add(idb);
		}
	    }
	}
	request.setAttribute("isPresentiSubEndo", Boolean.valueOf(isPresentiSubEndo));
	request.setAttribute("listaSubEndo", idbs);
	/*
	 * Lion verifica se il parametro GENERA_ALLEGATI_NOTIFICA della verticalizzazione CART ha valore 'S':
	 * in tal caso si visualizza il bottone 'Genera allegati per notifica CART'
	 */
	boolean isCartAttivo = this.verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_GENERA_ALLEGATI_NOTIFICA, WebConstants.S);
	request.setAttribute(WebConstants.VERTICALIZZAZIONE_CART_GENERA_ALLEGATI_NOTIFICA, isCartAttivo && isNotificaCART);
	//END Lion
	//fabrizioc
	List<ChiaveValoreBean<String, String>> tipoDocList = getTipoDocList(movimento.getAmministrazioniStc().getStcIdnodo(),
		stcNotificaCommand.getEntity().getAltriDatiList());
	if (!tipoDocList.isEmpty()) {
	    request.setAttribute("tipoDocList", tipoDocList);
	}
	return "stc/notificaForm";
    }

    /**
     * questo metodo ritorna una lista di tipi documento da utilizzare nella notifica stc per popolare la sezione
     * <tipoDocumento> di <documentiType>
     * 
     * @param idNodo
     * @return
     */
    private List<ChiaveValoreBean<String, String>> getTipoDocList(String idNodo, List<TipimovStcAltridatiValoreBean> altriDati) {

	boolean isRFC239Attivo = this.verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAIONE_RFC239);
	List<ChiaveValoreBean<String, String>> list = new ArrayList<ChiaveValoreBean<String, String>>();
	if (isRFC239Attivo && idNodo.equals("239")) {
	    String stimolo = "";
	    if (altriDati != null && !altriDati.isEmpty()) {
		for (TipimovStcAltridatiValoreBean tipimovStcAltridatiValoreBeaneValoreBean : altriDati) {
		    if ("TIPO_OPERAZIONE".equals(tipimovStcAltridatiValoreBeaneValoreBean.getChiave().getNomeCampo())) {
			if ("INVIA_STIMOLO".equals(tipimovStcAltridatiValoreBeaneValoreBean.getValore())) {
			    //
			    for (TipimovStcAltridatiValoreBean tipimovStcAltridatiValoreBeaneValoreBean2 : altriDati) {
				if ("TIPO_STIMOLO".equals(tipimovStcAltridatiValoreBeaneValoreBean2.getChiave().getNomeCampo())) {
				    stimolo = tipimovStcAltridatiValoreBeaneValoreBean2.getValore();
				}
			    }
			}
		    }
		}
	    }
	    ChiaveValoreBean<String, String> element1 = new ChiaveValoreBean<String, String>();
	    element1.setChiave("ALLEGATO_RICHIESTO_" + stimolo);
	    element1.setValore("Allegato richiesto");
	    list.add(element1);
	    ChiaveValoreBean<String, String> element1_1 = new ChiaveValoreBean<String, String>();
	    element1_1.setChiave("ALLEGATO_RICHIESTO_FIRMATO_" + stimolo);
	    element1_1.setValore("Allegato richiesto firmato");
	    list.add(element1_1);
	    ChiaveValoreBean<String, String> element2 = new ChiaveValoreBean<String, String>();
	    element2.setChiave("COMUNICAZIONE_FORMALE_" + stimolo);
	    element2.setValore("Comunicazione formale");
	    list.add(element2);
	    ChiaveValoreBean<String, String> element3 = new ChiaveValoreBean<String, String>();
	    element3.setChiave("COMUNICAZIONE_SECONDARIA_" + stimolo);
	    element3.setValore("Comunicazione secondaria");
	    list.add(element3);
	}
	if (altriDati != null && !altriDati.isEmpty()) {
	    for (TipimovStcAltridatiValoreBean tmad : altriDati) {
		TipimovStcAltridati tmvb = tmad.getChiave();
		if (StcServiceImpl.STC_ALTRO_DATO_TIPOLOGIA_DOCUMENTI_NODO_DEST.equals(tmvb.getNomeCampo())) {
		    String listadocumenti = tmvb.getValoreDefaultCampo();
		    if (StringUtils.isNotBlank(listadocumenti)) {
			String[] listaDoc = listadocumenti.split(",");
			for (int i = 0; i < listaDoc.length; i++) {
			    String key = listaDoc[i];
			    String value = listaDoc[i];
			    if (key.indexOf("#") >= 0) {
				String[] kv = key.split("#");
				key = kv[1];
				value = kv[0];
			    }
			    ChiaveValoreBean<String, String> cvb = new ChiaveValoreBean<String, String>();
			    cvb.setChiave(key);
			    cvb.setValore(value);
			    list.add(cvb);
			}
			break;
		    }
		}
	    }
	}
	return list;
    }

    @RequestMapping
    public String createNotifica(@RequestParam("codiceMovimento") Integer codiceMovimento, Model model) {

	StcNotificaCommand stcNotificaCommand = new StcNotificaCommand();
	model.addAttribute("stcNotificaCommand", stcNotificaCommand);
	return "redirect:createNotificaInitialized.htm?codiceMovimento=" + codiceMovimento;
    }

    @RequestMapping
    public String view(Model model, @ModelAttribute("stcNotificaCommand") StcNotificaCommand stcNotificaCommand, HttpServletRequest request) {

	Movimenti movimento = movimentiService.findById(new PkId(stcNotificaCommand.getCodiceMovimento()));
	StcNotificaBean entity = stcNotificaCommand.getEntity();
	entity.setMovimento(movimento);
	stcNotificaCommand.setEntity(entity);
	model.addAttribute("stcNotificaCommand", stcNotificaCommand);
	if (stcNotificaCommand.getEntity().isPraticaStorica()) {
	    stcNotificaCommand.setDisplayMode(StcNotificaCommand.PRATICA_STORICA);
	} else {
	    stcNotificaCommand.setDisplayMode(StcNotificaCommand.NEW);
	}
	return "stc/notificaForm";
    }

    @RequestMapping
    public String notificaAttivita(Model model, @ModelAttribute("stcNotificaCommand") StcNotificaCommand stcNotificaCommand, BindingResult result,
	    SessionStatus status, HttpServletRequest request) {

	if (ORMHelper.getSoftware().equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
	    Responsabili r = getCurrentlyAuthenticatedUserDetails();
	    log.error("PROBLEMA NOTIFICA_ATTIVITA CON SOFTWARE TT {}[{}]", r.getResponsabile(), r.getId());
	    throw new RuntimeException(
		    "Errore nella notifica attività è stato impostato il software TT e non è possibile procedere. Contattare l'assistenza.");
	}
	StcNotificaBean entity = stcNotificaCommand.getEntity();
	Integer codiceMovimento = entity.getMovimento().getId().getCodice();
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	DocumentiHelper documentiHelper = null;
	documentiHelper = documentiHelperService.findDocumentiInvioTrue(stcNotificaCommand.getEntity().getDocumentiHelper());
	if (stcNotificaCommand.getFlagTrasmettiZipLogico()) {
	    if (entity.isNotificainterapratica()) {
		throw new InvalidConfigurationException("Attenzione! Il movimento è configurato per \"notificare l'intera pratica\" " +
			"(MOVIMENTI --> CONFIGURAZIONI NOTIFICHE --> ENTI DESTINTATARI) ma è stato registrato anche " +
			"uno zip logico e l'impostazione non è compatibile con la notifica se si decide di inviare lo zip logico.");
	    }
	    documentiHelper = this.movimentiZipLogicoService.manageDocumentiZipLogico(codiceMovimento, documentiHelper);
	    entity.aggiungiAltroDato(StcService.ALTRO_DATO_GUID_ZIP_LOGICO_DI_ORIGINE, documentiHelper.getGuidZipLogico());
	}
	//Recupero dall'helper le 4 liste documentiistanza,istanzaallegati e movimentiallegati (del movimento e degli altri movimenti)
	List<DocumentiistanzaDTO> dcumentiIstanza = documentiHelperService.findDocumentiIstanza(documentiHelper.getDocumentiIstanzaList());
	List<IstanzeallegatiDTO> documentiEndo = documentiHelperService.findDocumentiEndo(documentiHelper.getDocumentiEndoprocedimentiList());
	List<MovimentiallegatiDTO> documentiMovimento = documentiHelperService.findDocumentiMovimento(documentiHelper.getDocumentiMovimentoList());
	List<MovimentiallegatiDTO> documentiAltriMovimenti = documentiHelperService
		.findDocumentiMovimento(documentiHelper.getDocumentiAltriMovimentiList());
	List<AnagrafedocumentiDTO> documentiAnagrafe = documentiHelperService.findDocumentiAnagrafe(documentiHelper.getDocumentiAnagrafeList());
	List<MovimentiallegatiDTO> documentiMovimentoTotali = new ArrayList<MovimentiallegatiDTO>();
	documentiMovimentoTotali.addAll(documentiMovimento);
	documentiMovimentoTotali.addAll(documentiAltriMovimenti);
	List<IstanzeprocureDTO> documentiProcure = documentiHelperService.findDocumentiProcure(documentiHelper.getIstanzeprocureList());
	entity.setMovimento(movimento);
	RiferimentiPraticaType rifPraticaDestinatario = null;
	if (stcNotificaCommand.isTrovataPraticaCollegata()) {
	    rifPraticaDestinatario = new RiferimentiPraticaType();
	    String idpratica = stcNotificaCommand.getPraticaCollegata().getIdPratica();
	    rifPraticaDestinatario.setIdPratica(idpratica);
	    rifPraticaDestinatario.setNumeroPratica(stcNotificaCommand.getPraticaCollegata().getNumeroPratica());
	}
	try {
	    boolean requirePraticaDestinatario = false;
	    if (entity.isPraticaStorica()) {
		if (!stcNotificaCommand.isOverridePraticaStorica()) {
		    requirePraticaDestinatario = true;
		}
	    }
	    boolean isNonInviareProcedimenti = entity.isNonInviareProcedimenti();
	    Integer codiceMovimentoRifProto = null;
	    if (stcNotificaCommand.isRifProto()) {
		if (stcNotificaCommand.getMovimentoPerRiferimentiProt() != null) {
		    if (stcNotificaCommand.getMovimentoPerRiferimentiProt().getId() != null) {
			codiceMovimentoRifProto = stcNotificaCommand.getMovimentoPerRiferimentiProt().getId().getCodice();
		    }
		}
	    }
	    stcService.notificaAttivita(codiceMovimento, entity.getAltriDatiList(), entity.getModelliList(), documentiEndo, dcumentiIstanza,
		    documentiMovimentoTotali, documentiAnagrafe, documentiProcure, rifPraticaDestinatario, requirePraticaDestinatario,
		    isNonInviareProcedimenti, movimento.getAmministrazioniStc(), codiceMovimentoRifProto);
	} catch (Exception e) {
	    String messaggioErrore = getMessageFromBundle("errors.operazione.fallita.messaggio", null);
	    List<InvalidValue> validationMessages = null;
	    String validationMessage = "";
	    if (e instanceof BusinessValidationException) {
		validationMessages = ((BusinessValidationException) e).getInvalidValues();
		validationMessage = getMessageFromBundle(validationMessages.get(0).getMessage(), null);
	    } else {
		validationMessage = e.getMessage();
	    }
	    result.reject("", messaggioErrore + "<br />" + validationMessage);
	    fixRenderEntityProperty(stcNotificaCommand);
	    setPageAttributes(model);
	    log.error("notificaAttivita: ", e);
	    return "stc/notificaError";
	}
	status.setComplete();
	return "redirect:successNotifica.htm?codiceMovimento=" + codiceMovimento + "&status_msg=01";
    }

    @RequestMapping
    public String successNotifica(Model model) {

	return "stc/notificaSuccess";
    }

    @RequestMapping
    public String ricercaPratica(Model model, @ModelAttribute("stcNotificaCommand") StcNotificaCommand stcNotificaCommand, BindingResult result,
	    SessionStatus status) throws STCNotificaAttivitaException {

	Movimenti movimento = movimentiService.findById(new PkId(stcNotificaCommand.getCodiceMovimento()));
	setPageAttributes(model, movimento);
	StcNotificaBean entity = stcNotificaCommand.getEntity();
	entity.setMovimento(movimento);
	// Eseguo una fixRender dell'oggetto StcNotificaBean prima che venga nuovamente settato al Command
	fixRenderStcNotificaBeanProperty(entity);
	stcNotificaCommand.setEntity(entity);
	RiferimentiPraticaType rifPratica = new RiferimentiPraticaType();
	rifPratica.setNumeroPratica(stcNotificaCommand.getEntity().getNumeroPratica());
	if (!(null == stcNotificaCommand.getEntity().getNumeroProtocolloGenerale()
		|| stcNotificaCommand.getEntity().getNumeroProtocolloGenerale().equals(""))) {
	    rifPratica.setNumeroProtocolloGenerale(stcNotificaCommand.getEntity().getNumeroProtocolloGenerale());
	}
	if (null != stcNotificaCommand.getEntity().getDataProtocolloGenerale()) {
	    GregorianCalendar cal = new GregorianCalendar();
	    cal.setTime(stcNotificaCommand.getEntity().getDataProtocolloGenerale());
	    rifPratica.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(cal));
	    //GIANPAOLO-ORA
	    String orarario = Utilities.getOrario(cal.getTime());
	    rifPratica.setOraDataPratica(orarario);
	}
	List<TipimovStcAltridatiValoreBean> altriDati = entity.getAltriDatiList();
	for (TipimovStcAltridatiValoreBean bean : altriDati) {
	    if (!(null == bean.getValore() || bean.getValore().equals(""))) {
		ParametroType parametro = new ParametroType();
		parametro.setNome(bean.getChiave().getNomeCampo());
		ValoreParametroType vpt = new ValoreParametroType();
		vpt.setCodice(bean.getValore());
		vpt.setDescrizione(bean.getValore());
		parametro.getValore().add(vpt);
		//
		rifPratica.getAltriDati().add(parametro);
	    }
	}
	RichiestaPraticaResponse pratRemota = null;
	try {
	    pratRemota = stcService.richiestaPratica(stcNotificaCommand.getCodiceMovimento(), rifPratica);
	} catch (Exception e) {
	    String messaggioErrore = getMessageFromBundle("errors.operazione.fallita.messaggio", null);
	    result.reject("", messaggioErrore + e.getMessage());
	    movimento = movimentiService.findById(new PkId(stcNotificaCommand.getCodiceMovimento()));
	    entity = stcNotificaCommand.getEntity();
	    entity.setMovimento(movimento);
	    stcNotificaCommand.setEntity(entity);
	    fixRenderEntityProperty(stcNotificaCommand);
	    setPageAttributes(model);
	    return "stc/notificaForm";
	}
	if (pratRemota.getDettaglioErrore().size() > 0) {
	    List<ErroreType> errori = pratRemota.getDettaglioErrore();
	    for (ErroreType erroreType : errori) {
		String messaggioErrore = getMessageFromBundle("errors.operazione.fallita.messaggio", null);
		result.reject("", messaggioErrore + erroreType.getDescrizione());
	    }
	    return "stc/notificaForm";
	} else {
	    stcNotificaCommand.setPraticaCollegata(pratRemota.getDettaglioPratica().getDettaglioPratica());
	    stcNotificaCommand.setDisplayMode(StcNotificaCommand.NEW);
	}
	//////////////////////////////////////////////CONTROLLA SE è UNA NOTIFICA INTERNA//////////////////////////////////////////
	// se è notifica interna uguale a true non deve mostrare la lista dei documenti dell'istanze e degli nedo procedimenti
	boolean isNotificaInteraPratica = Boolean.valueOf(entity.isNotificainterapratica());
	model.addAttribute("isNotificaInteraPratica", Boolean.valueOf(isNotificaInteraPratica));
	//////////////////////////////////////////////////////////////////////////////////////////////////
	DocumentiHelper documentiHelper = documentiHelperService.findDocumentiInvioDocumentiSTC(stcNotificaCommand.getCodiceMovimento());
	entity.setDocumentiHelper(documentiHelper);
	return "stc/notificaForm";
    }

    @RequestMapping
    public String praticaCollegata(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento,
	    @RequestParam(value = "mittente", required = false) Integer mittente, HttpServletRequest request, HttpServletResponse response) {

	RichiestaPraticaCollegataResponse praticaResponse = this
		.getRichiestaPraticaCollegataFactory(codiceIstanza, codiceMovimento, Integer.valueOf(1).equals(mittente), null).getPraticaCollegata();
	if (!(praticaResponse.getDettaglioErrore().size() > 0)) {
	    DettaglioPraticaType pratica = praticaResponse.getDettaglio().getDettaglioPratica();
	    SportelloType sportello = praticaResponse.getDettaglio().getSportello();
	    String idEnte = sportello.getIdEnte();
	    String idSportello = sportello.getIdSportello();
	    Amministrazioni amministrazione = null;
	    int countTuple = amministrazioniService.countAmministrazioniSTC(sportello.getIdNodo(), sportello.getIdEnte(), sportello.getIdSportello());
	    amministrazione = amministrazioniService.findAmministrazioneSTC(sportello.getIdNodo(), sportello.getIdEnte(), sportello.getIdSportello(),
		    null);
	    if (countTuple != 1) {
		if (codiceMovimento != null) {
		    Movimenti mov = movimentiService.findById(new PkId(codiceMovimento));
		    if (mov != null && mov.getAmministrazioniStc() != null && mov.getAmministrazioniStc().getId() != null
			    && mov.getAmministrazioniStc().getId().getCodice() != null)
			amministrazione = amministrazioniService.findById(new PkId(mov.getAmministrazioniStc().getId().getCodice()));
		}
	    }
	    // controllo se la pratica collegata è dello stesso idcomune del mio NLA.
	    // se si allora posso creare il link alla pratica
	    if (ORMHelper.getIdcomuneAlias().equalsIgnoreCase(idEnte) && nlaHelperService.checkSportello(sportello, NodoNLAEnum.NLA_IDNODO)) {
		Responsabili responsabile = getCurrentlyAuthenticatedUserDetails();
		if (softwareService.isSoftwareAbilitato(responsabile, idSportello)) {
		    model.addAttribute("nla_locale", true);
		    model.addAttribute("nla_dest_software", idSportello);
		}
	    }
	    RiferimentiPraticaType rif = new RiferimentiPraticaType();
	    rif.setNumeroPratica(pratica.getNumeroPratica());
	    rif.setIdPratica(pratica.getIdPratica());
	    rif.setNumeroProtocolloGenerale(pratica.getNumeroProtocolloGenerale());
	    rif.setDataPratica(pratica.getDataPratica());
	    rif.setDataProtocolloGenerale(pratica.getDataProtocolloGenerale());
	    RichiestaPraticaResponse resp = stcService.richiestaPratica(praticaResponse.getDettaglio().getSportello(), rif);
	    DettaglioPraticaVisuraType visura = resp.getDettaglioPratica();
	    model.addAttribute("amministrazione", amministrazione);
	    model.addAttribute("pratica", visura.getDettaglioPratica());
	    model.addAttribute("visura", visura);
	} else {
	    model.addAttribute("errori", praticaResponse.getDettaglioErrore());
	}
	return "stc/praticaCollegata";
    }

    @RequestMapping
    public String gotoPraticaCollegata(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request,
	    HttpServletResponse response) {

	return "redirect:praticaCollegata.htm?codiceIstanza=" + codiceIstanza + "&software=" + ORMHelper.getSoftware() + "&codiceMovimento=";
    }

    @RequestMapping
    public String visualizzaAllegato(@RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento,
	    @RequestParam(value = "codiceIstanza") Integer codiceIstanza, @RequestParam("stcIddocumento") String stcIddocumento,
	    @RequestParam("stcIdallegato") String stcIdallegato, HttpServletRequest request, HttpServletResponse response) {

	AllegatoBinarioResponse allegatoBinarioResponse = null;
	try {
	    allegatoBinarioResponse = stcService.allegatoBinario(codiceIstanza, codiceMovimento, stcIddocumento, stcIdallegato);
	    String fn = allegatoBinarioResponse.getFileName();
	    if (fn == null) {
		log.error("L'allegato recuperato è senza nome: [codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			new Object[] { codiceMovimento, stcIddocumento, stcIdallegato });
		throw new Exception("L'allegato recuperato è senza nome");
	    }
	    if (allegatoBinarioResponse.getMimeType() == null) {
		log.error("L'allegato recuperato non ha nessun mime-type: [codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			new Object[] { codiceMovimento, stcIddocumento, stcIdallegato });
	    }
	    DataHandler data = allegatoBinarioResponse.getBinaryData();
	    if (data == null) {
		log.error("L'allegato recuperato è vuoto: [codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			new Object[] { codiceMovimento, stcIddocumento, stcIdallegato });
		throw new Exception("L'allegato recuperato è vuoto");
	    }
	    //FIXME CXF gestire data length
	    //response.setContentLength(data.length);
	    response.setContentType(allegatoBinarioResponse.getMimeType());
	    response.setHeader("Content-Disposition", "attachment;filename=\"" + fn + "\"");
	    response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
	    response.setHeader("Pragma", "public");
	    response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));
	    data.writeTo(response.getOutputStream());
	    return null;
	} catch (Exception e) {
		log.error("visualizzaAllegato(): {}", e.getMessage());
		throw new RuntimeException(e.getMessage());
	}
    }

    /**
     * <pr> Il metodo ha il compito di disaccoppiare due chiamatate alla stessa funzionalità "copiaAllegatoInLocale()"
     * in modo che se tale funzionalità è già stata chiamata da un operatore,per la stessa instanza,l'utente venga
     * rediretto a auna pagina che lo avverte. Altrimenti esegue la procedura di backup e setta tramite una variabile di
     * classe la procedura come attiva.
     * 
     * @param model
     * @param codiceIstanza
     * @param codiceMovimento
     * @param codice
     * @param contesto
     * @param stcIddocumento
     * @param stcIdallegato
     * @param request
     * @param response
     * @return
     *         </pre>
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping
    public String prepareCopiaAllegatoInLocale(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento,
	    @RequestParam(value = "codice", required = false) Integer codice, @RequestParam("contesto") String contesto,
	    @RequestParam(value = "stcIddocumento", required = false) String stcIddocumento,
	    @RequestParam(value = "stcIdallegato", required = false) String stcIdallegato, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

	//Creo la stringa che mi richiama il controlloer che avvia l'attività di backup
	StringBuffer goToUrl = new StringBuffer("redirect:../stc/copiaAllegatoInLocale.htm?codiceIstanza=" + codiceIstanza);
	// Alla stringa base vado ad aggiungere i parametri opzionali presenti
	goToUrl = goToUrl.append((codiceMovimento != null ? "&codiceMovimento=" + codiceMovimento : ""));
	goToUrl = goToUrl.append((codice != null ? "&codice=" + codice : ""));
	goToUrl = goToUrl.append((contesto != null ? "&contesto=" + contesto : ""));
	goToUrl = goToUrl.append((stcIddocumento != null ? "&stcIddocumento=" + URLEncoder.encode(stcIddocumento, "UTF-8") : ""));
	goToUrl = goToUrl.append((stcIdallegato != null ? "&stcIdallegato=" + URLEncoder.encode(stcIdallegato, "UTF-8") : ""));
	String esecuzione = esecuzioneCopiaAllegati.get(getCodiceIstanzaDaCopiare(codiceIstanza));
	//esecuzione = "N";
	if (StringUtils.defaultIfEmpty(esecuzione, "N").equalsIgnoreCase("E")) {
	    // Pagina di alert a cui reindirizzo se l'operazione è già in esecuzione.
	    return "stc/copiaAllegatiStcInEsecuzione";
	} else {
	    esecuzioneCopiaAllegati.put(getCodiceIstanzaDaCopiare(codiceIstanza), "E");
	}
	return goToUrl.toString();
    }

    /**
     * <pre>
     * Il metodo scarica il file tramite una richiesta ad stc e lo salva sulla tabella OGGETTI , inoltre creata il collegamento tra la tabella oggetti
     * inoltre lega il riferimento alla rispettiva tabella di provenienza (DOCUMENTIISTANZA, ISTANZEALLEGATI, MOVIMENTIALLEGATI).
     * 
     * &#64;param model
     * &#64;param codiceIstanza : istanza per cui si stanno scaricando gli allegati da stc
     * &#64;param codice	: rappresenta il codice del record della tabella per cui vogliamo salvare l'allegato(Es. codice del recordo nella tabella documentiistanza o movimentiallegati)
     * &#64;param contesto	: il contesto in cui è salvato il file (movimenti ,endoprocedimenti,documenti)
     * &#64;param request
     * &#64;param response
     * &#64;return
     * </pre>
     */
    @RequestMapping
    public String copiaAllegatoInLocale(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza,
	    @RequestParam(value = "codiceMovimento", required = false) Integer codiceMovimento,
	    @RequestParam(value = "codice", required = false) Integer codice, @RequestParam("contesto") String contesto,
	    @RequestParam(value = "stcIddocumento", required = false) String stcIddocumento,
	    @RequestParam(value = "stcIdallegato", required = false) String stcIdallegato, HttpServletRequest request, HttpServletResponse response) {

	try {
	    stcService.insertAllegatoInLocale(codiceIstanza, codiceMovimento, stcIddocumento, stcIdallegato, contesto, codice);
	} catch (Exception e) {
	    log.error("copiaAllegatoInLocale(): {}", e.getMessage());
	    throw new RuntimeException(e.getMessage());
	} finally {
	    esecuzioneCopiaAllegati.remove(getCodiceIstanzaDaCopiare(codiceIstanza));
	}
	return getHistoryBack();
    }

    @RequestMapping
    public String selectIstanzaResetOperazioneBackupSTC(Model model, HttpServletRequest request, HttpServletResponse response) {

	Istanze istanze = new Istanze();
	model.addAttribute("istanze", istanze);
	setPageAttributes(model);
	return "stc/formSelectIstanzatoReset";
    }

    @RequestMapping
    public String popupPannelloRicerca(Model model, HttpServletRequest request, HttpServletResponse response) {

	setPageAttributes(model);
	return "stc/pannelloRicercaPratica";
    }

    @RequestMapping
    public String popupcercapratica(Model model, @RequestParam(value = "modulo", required = false) String modulo,
	    @RequestParam(value = "numeroistanza", required = false) String numeroistanza, HttpServletRequest request, HttpServletResponse response) {

	if (StringUtils.isBlank(modulo) || StringUtils.isBlank(numeroistanza)) {
	    FlashMessages.getWarnings().add("Modulo e  numeroistanza sono obbligatori");
	    setPageAttributes(model);
	    return "stc/pannelloRicercaPratica";
	}
	RiferimentiPraticaType rif = new RiferimentiPraticaType();
	rif.setNumeroPratica(numeroistanza);
	SportelloType destinatario = new SportelloType();
	destinatario.setIdEnte(ORMHelper.getIdcomuneAlias());
	destinatario.setIdSportello(modulo);
	Verticalizzazioniparametri vparam = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC, "NLA_IDNODO");
	if (vparam == null) {
	    String error = "Attenzione! non è stato configurato il parametro NLA_IDNODO della verticalizzazione STC";
	    FlashMessages.getWarnings().add(error);
	    setPageAttributes(model);
	    return "stc/pannelloRicercaPratica";
	}
	destinatario.setIdNodo(vparam.getValore());
	RichiestaPraticaResponse resp = stcService.richiestaPratica(destinatario, rif);
	if (resp == null) {
	    String error = "Attenzione! non è stata trovata nessunap ratica";
	    FlashMessages.getWarnings().add(error);
	    setPageAttributes(model);
	    return "stc/pannelloRicercaPratica";
	}
	if (!resp.getDettaglioErrore().isEmpty()) {
	    List<ErroreType> errori = resp.getDettaglioErrore();
	    for (ErroreType erroreType : errori) {
		FlashMessages.getWarnings().add(erroreType.getDescrizione() + "(" + erroreType.getNumeroErrore() + ")");
	    }
	    setPageAttributes(model);
	    return "stc/pannelloRicercaPratica";
	}
	DettaglioPraticaVisuraType visura = resp.getDettaglioPratica();
	model.addAttribute("pratica", visura.getDettaglioPratica());
	model.addAttribute("visura", visura);
	return "stc/praticaCollegata";
    }

    @RequestMapping
    public String resetOperazioneBackupSTC(Model model, @ModelAttribute("istanze") Istanze istanze, BindingResult result, SessionStatus status,
	    HttpServletRequest request, HttpServletResponse response) {

	esecuzioneCopiaAllegati.remove(getCodiceIstanzaDaCopiare(istanze.getId().getCodice()));
	return "redirect:../admin/view.htm";
    }

    @RequestMapping
    public String resetBackupSTC(Model model, @RequestParam("codiceIstanza") Integer codiceIstanza, HttpServletRequest request,
	    HttpServletResponse response) {

	esecuzioneCopiaAllegati.remove(getCodiceIstanzaDaCopiare(codiceIstanza));
	return getHistoryBack();
    }

    /**
     * Metodo di utilità che permette di sbloccare il metodo che non permetteil backup dei dati in quanto già attivo. Il
     * metodo deve essere usare se per un errore imprevisto la variabile che memorizza lo stato (backup attivo/non
     * attivo) rimane nello stato attivo anche se in realtà nessuno sta facendo un operazione.
     * 
     * @param codiceIstanza
     * @return
     */
    //    @RequestMapping
    //    public void ajaxCheckEsecuzioneCopiaAllegatiPerIstanza(@RequestParam(value = "codiceIstanza") Integer codiceIstanza, HttpServletRequest request,
    //	    HttpServletResponse response) throws IOException {
    //
    //	String esecuzione = esecuzioneCopiaAllegati.get(getCodiceIstanzaDaCopiare(codiceIstanza));
    //	if (StringUtils.defaultIfEmpty(esecuzione, "N").equalsIgnoreCase("E")) {
    //	    response.getOutputStream().write("E".getBytes());
    //	} else {
    //	    response.getOutputStream().write("N".getBytes());
    //	}
    //    }
    private String getCodiceIstanzaDaCopiare(Integer codiceIstanza) {

	// la copia allegati può essere usata da solo su istanza per volta per ogni installazione
	//	return ORMHelper.getIdcomune() + "-" + codiceIstanza;
	return ORMHelper.getIdcomune();
    }

    @Override
    protected void fixMergeEntityProperty(StcNotificaCommand entity) {

    }

    @Override
    protected void fixRenderEntityProperty(StcNotificaCommand entity) {

    }

    private void fixRenderStcNotificaBeanProperty(StcNotificaBean entity) {

	// Recupera tutti i modelli dal DB e li setta nuovamente sulla lista (evita l'errore di no proxy session quando si ritorna sulla jsp)
	List<Dyn2ModellitValoreBean> modellits = entity.getModelliList();
	for (Dyn2ModellitValoreBean dyn2ModellitValoreBean : modellits) {
	    Dyn2Modellit dyn2Modellit = dyn2ModellitService.findById(new PkId(dyn2ModellitValoreBean.getChiave().getId().getCodice()));
	    dyn2ModellitValoreBean.setChiave(dyn2Modellit);
	}
    }

    private void setPageAttributes(Model model, Movimenti movimento) throws STCNotificaAttivitaException {

	// Recupero tutti gli allegati presenti in documenti istanza.
	List<Documentiistanza> documentiistanzas = stcService.findListaDocumentiistanza(movimento.getIstanza(), movimento);
	model.addAttribute("documentiistanzas", documentiistanzas);
	// Recupero tutti gli allegati dei movimenti associati all'istanza.
	List<ChiaveValoreBean<String, List<Movimentiallegati>>> movalls = stcService.findListaMovimentiAllegati(movimento.getIstanza(), movimento);
	model.addAttribute("movimentiallegatis", movalls);
	// Recupero tutti gli allegati associati agli endo configurati per l'istanza
	List<ChiaveValoreBean<String, List<Istanzeallegati>>> ialls = stcService.findListaIstanzeallegati(movimento.getIstanza(), movimento);
	model.addAttribute("istanzeallegatis", ialls);
    }

    @Override
    protected void setPageAttributes(Model model) {

	List<Software> softwareList = new ArrayList<Software>();
	if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    List<Responsabilisoftware> responsabilisoftwares = responsabilisoftwareService.findBySoftware(getCurrentlyAuthenticatedUserDetails(),
		    new Software());
	    for (Responsabilisoftware responsabilisoftware : responsabilisoftwares) {
		if (BooleanUtils.isTrue(responsabilisoftware.getSoftware().getModuloopzionale())) {
		    softwareList.add(responsabilisoftware.getSoftware());
		}
	    }
	} else {
	    Software software = softwareService.findById(ORMHelper.getSoftware());
	    softwareList.add(software);
	}
	model.addAttribute("softwareList", softwareList);
    }

    private boolean validateNotifica(Movimenti movimento, BindingResult result) {

	boolean isValid = true;
	try {
	    isValid = stcService.validateConfigurazione(movimento);
	} catch (Exception e) {
	    isValid = false;
	    if (e instanceof BusinessValidationException) {
		List<InvalidValue> validationMessages = ((BusinessValidationException) e).getInvalidValues();
		if (validationMessages != null) {
		    for (InvalidValue invalidValue : validationMessages) {
			result.reject(null, null, getMessageFromBundle(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }));
		    }
		}
	    }
	}
	return isValid;
    }

    private void verificaSelezioneFile(DocumentiHelper documentiHelper, List<TipimovStcAltridatiValoreBean> altriDati) {

	boolean deselezionaTuttiIFile = false;
	for (TipimovStcAltridatiValoreBean ad : altriDati) {
	    if (StcService.ALTRO_DATO_RISERVATO_DESELEZIONA_TUTTI_I_FILE.equalsIgnoreCase(ad.getChiave().getNomeCampo())) {
		deselezionaTuttiIFile = true;
		break;
	    }
	}
	if (deselezionaTuttiIFile) {
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiAltriMovimentiList = documentiHelper.getDocumentiAltriMovimentiList();
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> cvb : documentiAltriMovimentiList) {
		List<MovimentiallegatiDTO> docs = cvb.getValore();
		if (docs != null && docs.size() > 0) {
		    for (MovimentiallegatiDTO maDTO : docs) {
			maDTO.setTransientSegnaPerInvio(false);
		    }
		}
	    }
	    List<ChiaveValoreBean<String, List<AnagrafedocumentiDTO>>> documentiAnagrafeList = documentiHelper.getDocumentiAnagrafeList();
	    for (ChiaveValoreBean<String, List<AnagrafedocumentiDTO>> cvb : documentiAnagrafeList) {
		List<AnagrafedocumentiDTO> docs = cvb.getValore();
		if (docs != null && docs.size() > 0) {
		    for (AnagrafedocumentiDTO maDTO : docs) {
			maDTO.setTransientSegnaPerInvio(false);
		    }
		}
	    }
	    List<ChiaveValoreBean<String, List<IstanzeallegatiDTO>>> documentiEndoprocedimentiList = documentiHelper
		    .getDocumentiEndoprocedimentiList();
	    for (ChiaveValoreBean<String, List<IstanzeallegatiDTO>> cvb : documentiEndoprocedimentiList) {
		List<IstanzeallegatiDTO> docs = cvb.getValore();
		if (docs != null && docs.size() > 0) {
		    for (IstanzeallegatiDTO maDTO : docs) {
			maDTO.setTransientSegnaPerInvio(false);
		    }
		}
	    }
	    List<ChiaveValoreBean<String, List<DocumentiistanzaDTO>>> documentiIstanzaList = documentiHelper.getDocumentiIstanzaList();
	    for (ChiaveValoreBean<String, List<DocumentiistanzaDTO>> cvb : documentiIstanzaList) {
		List<DocumentiistanzaDTO> docs = cvb.getValore();
		if (docs != null && docs.size() > 0) {
		    for (DocumentiistanzaDTO maDTO : docs) {
			maDTO.setTransientSegnaPerInvio(false);
		    }
		}
	    }
	    List<ChiaveValoreBean<String, List<MovimentiallegatiDTO>>> documentiMovimentoList = documentiHelper.getDocumentiMovimentoList();
	    for (ChiaveValoreBean<String, List<MovimentiallegatiDTO>> cvb : documentiMovimentoList) {
		List<MovimentiallegatiDTO> docs = cvb.getValore();
		if (docs != null && docs.size() > 0) {
		    for (MovimentiallegatiDTO maDTO : docs) {
			maDTO.setTransientSegnaPerInvio(false);
		    }
		}
	    }
	    List<ChiaveValoreBean<String, List<IstanzeprocureDTO>>> istanzeprocureList = documentiHelper.getIstanzeprocureList();
	    for (ChiaveValoreBean<String, List<IstanzeprocureDTO>> cvb : istanzeprocureList) {
		List<IstanzeprocureDTO> docs = cvb.getValore();
		if (docs != null && docs.size() > 0) {
		    for (IstanzeprocureDTO maDTO : docs) {
			maDTO.setTransientSegnaPerInvio(false);
		    }
		}
	    }
	}
    }

    private RichiestaPraticaCollegataFactory getRichiestaPraticaCollegataFactory(Integer codiceIstanza, Integer codiceMovimento, boolean mittente,
	    SportelloType destinatario) {

	if (codiceMovimento != null) {
	    RichiestaPraticaFromMovimentiParams params = new RichiestaPraticaFromMovimentiParams(this.stcService, this.verticalizzazioniService,
		    codiceMovimento, mittente);
	    return RichiestaPraticaCollegataFactory.fromMovimentiParams(params);
	}
	if (null == destinatario) {
	    destinatario = getDestinatario(domandestcService, codiceIstanza);
	}
	RichiestaPraticaFromIstanzeParams params = new RichiestaPraticaFromIstanzeParams(this.stcService, this.verticalizzazioniService,
		codiceIstanza, destinatario);
	return RichiestaPraticaCollegataFactory.fromIstanzeParams(params);
    }

    private SportelloType getDestinatario(DomandestcService domandeStcService, Integer codiceIstanza) {

	List<Domandestc> domande = domandeStcService.findByIstanza(codiceIstanza);
	if (domande.isEmpty()) {
	    throw new RuntimeException(
		    "Errore nel recupero della pratica collegata. Non sono stati trovati i riferimenti della pratica/sportello (DOMANDESTC) che ha creato la presente " +
			    "istanza");
	}
	Domandestc domanda = domande.get(0);
	if (StringUtils.isBlank(domanda.getIdSportellomitt()) || StringUtils.isBlank(domanda.getIdEntemitt())
		|| StringUtils.isBlank(domanda.getIdNodo())) {
	    String errorMessage = "Attenzione! Non sono stati trovati i parametri della comunicazione STC ( DOMANDESTC ) per l'amministrazione mittente (rif: " +
		    domanda.getId() +
		    ")";
	    throw new RuntimeException(errorMessage);
	}
	SportelloType destinatario = new SportelloType();
	destinatario.setIdEnte(domanda.getIdEntemitt());
	destinatario.setIdSportello(domanda.getIdSportellomitt());
	destinatario.setIdNodo(domanda.getIdNodo());
	return destinatario;
    }
}
