package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.FACCTConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.MovimentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Domandestc;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.InventarioprocEndo;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.IstanzeprocedimentiId;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.StpEndoTipo1;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAlberoproc;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.TipimovStcMapping;
import it.gruppoinit.pal.gp.core.domain.TipimovStcModelli;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioni;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.Dyn2ModellitValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.TipimovStcAltridatiValoreBean;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.AmministrazioniCollegateService;
import it.gruppoinit.pal.gp.core.features.movimenti.stc.segnaposto.SegnapostiMovimentoSTCBuilder;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.features.protocollazione.verticalizzazione.VerticalizzazioneProtocolloAttivoServiceImpl;
import it.gruppoinit.pal.gp.core.service.AlberoprocProtocolloService;
import it.gruppoinit.pal.gp.core.service.CartPresentazioneDomandaService;
import it.gruppoinit.pal.gp.core.service.CategorieeventibaseService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.DomandestcService;
import it.gruppoinit.pal.gp.core.service.InventarioprocEndoService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.MovimentiBaseService.SceltaMovimentiEnum;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.gruppoinit.pal.gp.core.service.StpEndoTipo1Service;
import it.gruppoinit.pal.gp.core.service.TipimovStcAltridatiService;
import it.gruppoinit.pal.gp.core.service.TipimovStcMappingService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.STCNotificaAttivitaException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.NodoNLAEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;
import it.gruppoinit.pal.gp.core.utils.LoggerModificheIstanze;
import it.gruppoinit.pal.gp.core.utils.StcUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloLettoResponseType;
import it.gruppoinit.sigepro.cart.schema.rfcsuap.PresentazioneDomanda;
import it.init.sigepro.rte.AllegatoBinarioRequest;
import it.init.sigepro.rte.AllegatoBinarioResponse;
import it.init.sigepro.rte.CancellaAttivitaRequest;
import it.init.sigepro.rte.InserimentoPraticaRequest;
import it.init.sigepro.rte.InserimentoPraticaResponse;
import it.init.sigepro.rte.NotificaAttivitaRequest;
import it.init.sigepro.rte.NotificaAttivitaResponse;
import it.init.sigepro.rte.RichiestaPraticaCollegataDaAttivitaDestinatariaRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataDaAttivitaMittenteRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataRequest;
import it.init.sigepro.rte.RichiestaPraticaCollegataResponse;
import it.init.sigepro.rte.RichiestaPraticaRequest;
import it.init.sigepro.rte.RichiestaPraticaResponse;
import it.init.sigepro.rte.types.AllegatiType;
import it.init.sigepro.rte.types.DettaglioAttivitaType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.EstremiAttoType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.ProcedimentoType;
import it.init.sigepro.rte.types.RiferimentiAllegatoType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.RiferimentiPraticaType;
import it.init.sigepro.rte.types.SportelloType;
import it.init.sigepro.rte.types.TipoAttivitaType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Service
public class StcServiceImpl extends NlaStcBaseServiceImpl implements StcService {

    private static final String ALTRO_DATO_DOMANDESTC_ID_DOMANDAMITT = "$DOMANDESTC.ID_DOMANDAMITT$";
    public static final String STC_ALTRO_DATO_TIPOLOGIA_DOCUMENTI_NODO_DEST = "$TIPOLOGIA_DOCUMENTI_NODO_DEST$";
    private static final Logger log = LoggerFactory.getLogger(StcServiceImpl.class);
    @Autowired
    private MovimentiService movimentiService;
    @Autowired
    private MovimentiNoSecurityService movimentiNoSecurityService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private IstanzeprocedimentiService istanzeprocedimentiService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    //@Autowired
    //private ContenttypesService contenttypesService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private IstanzeDAO istanzeDAO;
    @Autowired
    private MovimentiDAO movimentiDAO;
    @Autowired
    private IstanzeService istanzeService;
    //@Autowired
    //private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private DomandestcService domandestcService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private TipimovStcAltridatiService tipimovStcAltridatiService;
    @Autowired
    private TipimovStcMappingService tipimovStcMappingService;
    @Autowired
    private NlaHelperService nlaHelperService;
    @Autowired
    private IstanzeprocureService istanzeprocureService;
    @Autowired
    private IstanzeeventiService istanzeeventiService;
    @Autowired
    private CategorieeventibaseService categorieeventibaseService;
    @Autowired
    private CartPresentazioneDomandaService cartPresentazioneDomandaService;
    @Autowired
    private StpEndoTipo1Service endoTipo1Service;
    @Autowired
    private InventarioprocedimentiService inventarioprocedimentiService;
    @Autowired
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private AlberoprocProtocolloService alberoprocProtocolloService;
    @Autowired
    private InventarioprocEndoService inventarioprocEndoService;
    @Autowired
    private AmministrazioniCollegateService amministrazioniCollegateService;
    @Autowired
    private ProtocollazioneService protocollazioneService;

    @Override
    public void cancellaAttivita(SportelloType sportello, Integer codiceMovimento) {

	if (sportello == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo cancellaAttivita senza passare lo sportello di riferimento");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo cancellaAttivita senza passare il codiceMovimento di riferimento");
	}
	CancellaAttivitaRequest request = new CancellaAttivitaRequest();
	request.setSportello(sportello);
	request.setIdAttivita(String.valueOf(codiceMovimento));
	this.stcWsClient.cancellaAttivita(request);
    }

    @Override
    public String notificaAttivita(Integer codiceMovimento, List<TipimovStcAltridatiValoreBean> altriDatiList,
	    List<Dyn2ModellitValoreBean> modelliList, List<IstanzeallegatiDTO> istanzeallegatis, List<DocumentiistanzaDTO> documentiistanzas,
	    List<MovimentiallegatiDTO> movimentiallegatis, List<AnagrafedocumentiDTO> documentiAnagrafe, List<IstanzeprocureDTO> documentiprocuras,
	    RiferimentiPraticaType rifPraticaDestinatario, boolean requirePraticaDestinatario, boolean isNonInviareProcedimenti,
	    Amministrazioni amministrazioniStc, Integer codiceMovimentoRifProto) {

	if (!businessValidation(codiceMovimento, amministrazioniStc)) {
	    return null;
	}
	Movimenti mov = movimentiService.findById(new PkId(codiceMovimento));
	String tipoMovimento = mov.getTipomovimento().getId().getTipomovimento();
	Integer codiceAmministrazioneStc = amministrazioniStc.getId().getCodice();
	Integer codiceistanza = mov.getIstanza().getId().getCodice();
	Integer codiceInventario = null;
	if (mov.getEndoprocedimento() != null && mov.getEndoprocedimento().getId() != null && mov.getEndoprocedimento().getId().getCodice() != null) {
	    codiceInventario = mov.getEndoprocedimento().getId().getCodice();
	}
	String debugRif = "per il movimento  " + codiceMovimento + "[" + tipoMovimento + "] dell'istanza " + codiceistanza + "  con endo " +
			  codiceInventario;
	log.debug("notificaAttivita# {}", new Object[] { debugRif });
	String idProcedimento = decodeEndoProcedimento(mov.getEndoprocedimento());
	log.debug("notificaAttivita# decodeEndoProcedimento {} - {}", new Object[] { idProcedimento, debugRif });
	if (rifPraticaDestinatario == null && requirePraticaDestinatario) {
	    log.debug("notificaAttivita# requirePraticaDestinatario {} - {}", new Object[] { requirePraticaDestinatario, debugRif });
	    rifPraticaDestinatario = this.riferimentoPraticaDestinatarioDaRicercaPraticaCollegata(codiceistanza, mov.getId().getCodice(),
		    amministrazioniStc.getId().getCodice());
	}
	SportelloType destinatario = this.getSportelloDestinatario(amministrazioniStc);
	log.debug("notificaAttivita# sportelloDestinatario {} - {} - {} - {}",
		new Object[] { destinatario.getIdNodo(), destinatario.getIdEnte(), destinatario.getIdSportello(), debugRif });
	boolean isNlaRFC239 = nlaHelperService.checkSportello(destinatario, NodoNLAEnum.NLA_IDNODO_RFC239);
	this.verificaNotifica183ViaSEM(amministrazioniStc.getStcIdnodo());
	//verifico se la domanda STC proviene dal nodo RFC239 e non è una notifica 183 tramite SEM
	if (isNlaRFC239) {
	    List<Domandestc> dstcs = domandestcService.findByIstanza(codiceistanza);
	    if (!dstcs.isEmpty()) {
		Domandestc domandestc = dstcs.get(0);
		String idNodoPratica = domandestc.getIdNodo();
		SportelloType nodo183 = new SportelloType();
		nodo183.setIdNodo(idNodoPratica);
		nodo183.setIdEnte("");
		nodo183.setIdSportello("");
		boolean isNlaCartComunicazioniInterne = nlaHelperService.checkSportello(destinatario, NodoNLAEnum.NLA_IDNODO_CART_COMINTERNE);
		if (isNlaCartComunicazioniInterne) {
		    log.error("Tentativo di inoltrare una pratica 183 con la 239");
		    throw new RuntimeException(
			    "Attenzione!! Non è possibile notificare su una pratica recepita ricevuta tramite la modalita' RFC183");
		}
	    }
	}
	log.debug("notificaAttivita# creo la request {}", new Object[] { debugRif });
	NotificaAttivitaRequest request = new NotificaAttivitaRequest();
	request.setRifPraticaDestinatario(rifPraticaDestinatario);
	SportelloType mittente = getSportelloMittente();
	log.debug("notificaAttivita# sportelloMittente {} - {} - {} - {}",
		new Object[] { mittente.getIdNodo(), mittente.getIdEnte(), mittente.getIdSportello(), debugRif });
	request.setSportelloMittente(mittente);
	request.setSportelloDestinatario(destinatario);
	DettaglioAttivitaType attivita = new DettaglioAttivitaType();
	if (mov.getData() != null) {
	    GregorianCalendar dataMov = new GregorianCalendar();
	    dataMov.setTime(mov.getData());
	    attivita.setDataAttivita(Utilities.getXMLGregorianCalendar(dataMov));
	    //GIANPAOLO-ORA
	    String orario = Utilities.getOrario(dataMov.getTime());
	    attivita.setOraDataAttivita(orario);
	}
	// BOCCI 2013-03-18 REDMINE#50 non devo fare il controllo su passaprot in notifica attività
	attivita.setNumeroProtocolloGenerale(mov.getNumeroprotocollo());
	if (mov.getDataprotocollo() != null) {
	    GregorianCalendar dataProtMov = new GregorianCalendar();
	    dataProtMov.setTime(mov.getDataprotocollo());
	    attivita.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtMov));
	}
	// BOCCI 2012-09-20 : I VALORI NULLI DI ESITO DEVONO VENIRE CONSIDERATI COME ESITO POSITIVO
	boolean esito = mov.getEsito() == null ? Boolean.TRUE : mov.getEsito().booleanValue();
	attivita.setEsito(esito);
	attivita.setIdAttivita(codiceMovimento.toString());
	attivita.setIdPratica(codiceistanza.toString());
	attivita.setNote(StringUtils.defaultString(mov.getNote()));
	attivita.setParere(StringUtils.defaultString(mov.getParere()));
	TipoAttivitaType tipoAttivita = new TipoAttivitaType();
	// di default vengono inviati come codice tipoattività e descrizione tipo attività il tipomovimento del movimento che si esegue
	String codiceTipoAttivita = mov.getTipomovimento().getId().getTipomovimento();
	String descrizioneTipoAttivita = mov.getTipomovimento().getMovimento();
	// se endoprocedimento del movimento è 0 (WebConstants.CODICE_ENDO_ATTIVITA_SPORTELLO) allora devo settare idpProcedimento = null
	// gestione mappature (configurate sul tipo movimento)
	Set<TipimovStcMapping> mappings = mov.getTipomovimento().getTipimovStcMappings();
	boolean isNotificaInteraPratica = false;
	boolean isAllegaDocPratica = false;
	ProcedimentoType procedimentoPrincipale = this.getProcedimentoPrincipale(idProcedimento, mov.getEndoprocedimento(), codiceistanza, debugRif);
	if (StringUtils.isNotBlank(procedimentoPrincipale.getCodice())) {
	    attivita.getProcedimenti().add(procedimentoPrincipale);
	}
	for (TipimovStcMapping tipimovStcMapping : mappings) {
	    Amministrazioni ammTipiMov = tipimovStcMapping.getAmministrazioni();
	    if (ammTipiMov.getId().getCodice().intValue() == amministrazioniStc.getId().getCodice().intValue()) {
		log.debug("notificaAttivita# mapping per amministrazione {} - {}", new Object[] { amministrazioniStc.getId().getCodice(), debugRif });
		isNotificaInteraPratica = BooleanUtils.isTrue(tipimovStcMapping.getFlagNotificainterapratica());
		isAllegaDocPratica = BooleanUtils.isTrue(tipimovStcMapping.getFlagAllegaDocPratica());
		// se trovo codice attività lo sovrascrivo
		if (!(tipimovStcMapping.getCodiceAttDest() == null || tipimovStcMapping.getCodiceAttDest().equals(""))) {
		    codiceTipoAttivita = tipimovStcMapping.getCodiceAttDest();
		}
		// se trovo descrizione attività lo sovrascrivo
		if (!(tipimovStcMapping.getDescrizioneAttDest() == null || tipimovStcMapping.getDescrizioneAttDest().equals(""))) {
		    descrizioneTipoAttivita = tipimovStcMapping.getDescrizioneAttDest();
		}
		// se trovo codiceprocedimento lo sovrascrivo
		if (!(tipimovStcMapping.getCodiceprocedimento() == null || tipimovStcMapping.getCodiceprocedimento().equals(""))) {
		    procedimentoPrincipale.setCodice(tipimovStcMapping.getCodiceprocedimento());
		}
	    }
	}
	if (StringUtils.isEmpty(codiceTipoAttivita)) {
	    throw new RuntimeException(
		    "Non è stata mappata nessuna attività di destinazione per il tipomovimento " + mov.getTipomovimento().getId().getTipomovimento());
	}
	tipoAttivita.setCodice(codiceTipoAttivita);
	tipoAttivita.setDescrizione(descrizioneTipoAttivita);
	attivita.setTipoAttivita(tipoAttivita);
	if (isNotificaInteraPratica) {
	    log.debug("notificaAttivita# isNotificaInteraPratica {} - {}", new Object[] { isNotificaInteraPratica, debugRif });
	    // VEDI REDMINE #566 - STC: Possibilità di inviare la pratica Intera (Tutti gli endo)
	    // ELIMINO DAI FILE DA INVIARE QUELLI RELATIVI ALLA PRATICA E AGLI ENDO
	    // VERRANNO RECUPERATI DA STC IN RICHIESTA PRATICA
	    istanzeallegatis = new ArrayList<IstanzeallegatiDTO>();
	    documentiistanzas = new ArrayList<DocumentiistanzaDTO>();
	    this.valorizzaAltroDatoNotificaInteraPratica(attivita, debugRif);
	}
	//
	this.valorizzaDocumentiDaMovimentiAllegati(attivita, movimentiallegatis, debugRif);
	//
	this.valorizzaDocumentiDaIstanzeAllegati(attivita, istanzeallegatis, debugRif);
	//
	this.valorizzaDocumentiDaDocumentiIstanza(attivita, documentiistanzas, debugRif);
	//
	this.valorizzaDocumentiDaDocumentiAnagrafe(attivita, documentiAnagrafe, debugRif);
	//
	this.valorizzaDocumentiDaProcure(attivita, documentiprocuras, debugRif);
	//
	this.verificaSeNotificaAdEnteTerzoCART(altriDatiList, istanzeallegatis, mov.getIstanza(), mov.getEndoprocedimento(), debugRif);
	//
	this.valorizzaAltriDati(attivita, altriDatiList, debugRif);
	//
	this.valorizzaAltroDatoCodiceAmministrazioneSTC(attivita, mittente, destinatario, amministrazioniStc, debugRif);
	//
	this.valorizzaAltroDatoDaLogicaMittentiMultipli(attivita, mov.getIstanza(), debugRif);
	//
	this.valorizzaAltroDatoDaAmministrazioneMovimento(attivita, mov);
	//
	this.valorizzaAltroDatoPerNotificaSubendo(attivita, codiceMovimento, codiceInventario, tipoMovimento, codiceAmministrazioneStc, debugRif);
	//
	this.valorizzaAltroDatoPerNotificaSchedeDinamiche(attivita, mittente, destinatario, modelliList, debugRif);
	//
	this.valorizzaAltroDatoAlberoProcDestinatario(attivita, mov, amministrazioniStc, debugRif);
	//
	this.valorizzaAltroDatoSpostaAllegatiInPratica(attivita, isAllegaDocPratica, debugRif);
	//
	this.valorizzaAltroDatoOperatoreNotificaConOperatoreLoggato(attivita, debugRif);
	//
	this.valorizzaAltroDatoNonInviareProcedimenti(attivita, isNonInviareProcedimenti, debugRif);
	//
	this.valorizzaAltroDatoDomandaSTCIdDomandaMitt(attivita, codiceistanza, debugRif);
	//
	this.valorizzaAltroDatoDaCodiceMovimentoRifProto(attivita, codiceMovimentoRifProto, debugRif);
	//
	this.verificaSeEndoPresente(tipoMovimento, codiceAmministrazioneStc, mov.getEndoprocedimento());
	//
	this.valorizzaRifProtocolloIstanza(attivita, codiceistanza, debugRif, codiceAmministrazioneStc, tipoMovimento, mov.getIstanza());
	//
	request.setDatiAttivita(attivita);
	//
	log.debug("notificaAttivita# prima di notificare  {}", new Object[] { debugRif });
	String requestToString = Utilities.marshallObject(request);
	//
	//fabrizioc: eseguo una nuova find perchè il nodo nla-rfc-239 aggiorna la tabella durante la notifica tramite query jdbc
	log.debug("notificaAttivita# requestToString {} - {}", new Object[] { requestToString, debugRif });
	movimentiService.evict(mov);
	NotificaAttivitaResponse response = stcWsClient.notificaAttivita(request);
	log.debug("notificaAttivita# notifica effettuata {}", new Object[] { debugRif });
	mov = movimentiService.findById(new PkId(codiceMovimento));
	movimentiService.evict(mov);
	//
	this.verificaRispostaNotificaSTC(response, debugRif);
	//
	LoggerModificheIstanze.log("### NOTIFICA STC ### IN DATA " + Utilities.formatDate(Calendar.getInstance().getTime(), true) + " l'operatore " +
				   (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails() + " ha notificato il movimento " + mov +
				   " dell'istanza " + codiceistanza);
	RiferimentiAttivitaType riferimentiAttivitaType = response.getDettaglioattivita();
	String idAttivitaDest = riferimentiAttivitaType.getIdAttivita();
	//
	///////////fabrizioc: Inserimento istanzedyn2dati (schede dinamiche) a partire da altriDati ///////////////////////
	log.debug("notificaAttivita# prima di insertIstanzedyn2datiFromAltriDati {}", new Object[] { debugRif });
	this.insertIstanzedyn2datiFromAltriDati(response.getDettaglioattivita().getAltriDati(), codiceistanza);
	log.debug("notificaAttivita# dopo di insertIstanzedyn2datiFromAltriDati {}", new Object[] { debugRif });
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BOCCI 2012-08-22 [BUGZILLA 619] IL NOME DEL FILE SERVE ANCHE A CAPIRE LA DATA DI INVIO NOTIFICA NELLA PAGINA CHE RENDERIZZA
	// LE INFORMAZIONI
	String nomeFile = Utilities.getToday("yyyy-MM-dd'T'HH-mm-ss-SSSZ") + ".xml";
	Oggetti oggettoNotifica = Oggetti.fromNomeFileEContenuto(nomeFile, requestToString.getBytes());
	oggettiService.insert(oggettoNotifica);
	mov.setOggettoNotifica(oggettoNotifica);
	mov.setInviatoConStc(MovimentiService.STC_INVIATO);
	mov.setAmministrazioniStc(amministrazioniStc);
	log.debug("verifico se scrivere attività dest");
	GregorianCalendar oggi = new GregorianCalendar();
	if (StringUtils.isBlank(mov.getIdAttDest()) && StringUtils.isNotBlank(amministrazioniStc.getStcIdnodo()) && isNlaRFC239) {
	    log.debug("notificaAttivita# StringUtils.isBlank(mov.getIdAttDest()) {}", new Object[] { debugRif });
	    log.debug("=====================>è nodo stc");
	    //
	    this.registraEventiPerAllegatiNonInviati(riferimentiAttivitaType, mov);
	    //
	    log.debug("===============================>è nodo stc 239 setto idattivita dest {}", riferimentiAttivitaType.getIdAttivita());
	    //LOGICA DI VERIFICA NOTIFICA_ET			
	    String idAttivita = riferimentiAttivitaType.getIdAttivita();
	    if (riferimentiAttivitaType.getAltriDati() != null && !riferimentiAttivitaType.getAltriDati().isEmpty()) {
		ValoreParametroType notificaET = StcUtils.getCampoDaAltriDati(riferimentiAttivitaType.getAltriDati(), "RFC239_NOTIFICA_ET");
		log.debug("===================================>verifico se NotificaET");
		if (notificaET != null) {
		    //
		    String destinatarioStimolo = notificaET.getCodice();
		    log.debug("E' NotificaET per {}", destinatarioStimolo);
		    // è una risposta alla notifica enti terzi
		    // devo: MODIFICARE L'IDENTIFICATIVO DELL'ATTIVITA' <DESTINATARIO>###<IDSEM> 
		    // 				AD ESEMPIO DA-> "IDSEM-DSAWQERYWBCECQFH"  A-> "ASL###IDSEM-DSAWQERYWBCECQFH"
		    idAttivita = destinatarioStimolo + "###" + idAttivita;
		    log.debug("===================================>Assegno l'identificativo attività con codice {}", idAttivita);
		    //
		    this.aggiornaDestinatariRaggiunti(riferimentiAttivitaType, mov, oggi);
		    //
		    mov.setIdAttDest(idAttivita);
		    boolean settaStato = this.verificaSeSettareLoStato(amministrazioniStc);
		    if (settaStato) {
			log.debug("===================================>Settastato: {}, setto l'attività come ricevuta StatoAttDesk = OK", settaStato);
			mov.setStatoAttDest("OK");
		    }
		}
	    }
	}
	/*boolean isDisabilitaModificaDataNotifica = verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_STC,
		WebConstants.VERTICALIZZAZIONE_STC_DIS_MOD_DATA_NOTIFICA, "1");
	log.debug("notificaAttivita# isDisabilitaModificaDataNotifica {} - {}", new Object[] { isDisabilitaModificaDataNotifica, debugRif });
	if (!isDisabilitaModificaDataNotifica) {
	    mov.setData(oggi.getTime());
	}*/
	if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC_DIS_MOD_DATA_NOTIFICA)) {
	    String disModDataNotifica = verticalizzazioniService.getVerticalizzazioniparametriValore(WebConstants.VERTICALIZZAZIONE_STC,
		    WebConstants.VERTICALIZZAZIONE_STC_DIS_MOD_DATA_NOTIFICA);
	    if (StringUtils.defaultString(disModDataNotifica).indexOf("UFFICI") < 0
		    || StringUtils.defaultString(disModDataNotifica).indexOf("PEC") < 0
		    || StringUtils.defaultString(disModDataNotifica).indexOf("PROTOCOLLO") < 0) {
		mov.setData(oggi.getTime());
	    }
	}
	log.debug("notificaAttivita# prima di update {}", new Object[] { debugRif });
	movimentiService.update(mov);
	log.debug("notificaAttivita# dopo di update {}", new Object[] { debugRif });
	return idAttivitaDest;
    }

    private void valorizzaRifProtocolloIstanza(DettaglioAttivitaType attivita, Integer codiceistanza, String debugRif,
	    Integer codiceAmministrazioneStc, String tipoMovimento, Istanze istanza) {

	List<TipimovStcAltridati> datis = tipimovStcAltridatiService.findByTipimovimentoAndAmministrazione(tipoMovimento, codiceAmministrazioneStc);
	for (TipimovStcAltridati tm : datis) {
	    if (StcService.ALTRO_DATO_PARAMETRO_RECUPERA_INFO_FASCICOLO_PRATICA.equalsIgnoreCase(tm.getValoreDefaultCampo())) {
		if (StringUtils.isBlank(istanza.getNumeroprotocollo()) || istanza.getDataprotocollo() == null) {
		    throw new BusinessValidationException(
			    "L'istanza non è stata protocollata e la notifica richiede di recuperare il campo ANNO/NUMEROPRATICA");
		}
		DatiProtocolloLettoResponseType leggiProtocollo = protocollazioneService.leggiProtocollo(ORMHelper.getToken(), istanza);
		if (StringUtils.isBlank(leggiProtocollo.getAnnoNumeroPratica())) {
		    throw new BusinessValidationException("Non è stato possibile recuperare il campo anno/numeropratica dal protocollo");
		}
		ParametroType altroDatoNum = new ParametroType();
		altroDatoNum.setNome(StcService.ALTRO_DATO_PRATICA_NUMERO_ANNO_FASCICOLO);
		ValoreParametroType vptNum = new ValoreParametroType();
		vptNum.setCodice(leggiProtocollo.getAnnoNumeroPratica());
		vptNum.setDescrizione(leggiProtocollo.getAnnoNumeroPratica());
		altroDatoNum.getValore().add(vptNum);
		attivita.getAltriDati().add(altroDatoNum);
		return;
	    }
	}
    }

    private void verificaSeEndoPresente(String tipoMovimento, Integer codiceAmministrazioneStc, Inventarioprocedimenti inventarioprocedimenti) {

	TipimovStcMapping mapping = this.tipimovStcMappingService.findByTipimovimentoAndAmministrazione(tipoMovimento, codiceAmministrazioneStc);
	if (mapping.getFlagEndoObbligatorio() && inventarioprocedimenti == null) {
	    throw new RuntimeException("Non è stato inserito l'endoprocedimento! Inserirlo per procedere con la notifica.");
	}
    }

    private Amministrazioni recuperaAmministrazione(Istanzeprocedimenti procedimento) {

	if (procedimento.getInventarioprocedimenti() == null) {
	    return null;
	}
	Inventarioprocedimenti ip = this.inventarioprocedimentiService
		.findById(new PkId(procedimento.getInventarioprocedimenti().getId().getCodice()));
	if (WebConstants.SOFTWARE_TT.equalsIgnoreCase(ip.getSoftware().getCodice())) {
	    log.debug("E' endo di archivi di base verifico se configurato per il software corrente");
	    List<Inventarioprocedimentisoftware> ipss = inventarioprocedimentisoftwareService
		    .findByEndoprocedimentiAndSoftware(ip.getId().getCodice(), ORMHelper.getSoftware());
	    if (ipss != null) {
		for (Inventarioprocedimentisoftware ips : ipss) {
		    if (ips.getAmministrazioni() != null) {
			log.debug("E' endo di archivi di base.E' configurato per il software corrente, amministrazione: {}",
				ips.getAmministrazioni());
			return ips.getAmministrazioni();
		    }
		}
	    }
	}
	return ip.getAmministrazioni();
    }

    private void aggiornaDestinatariRaggiunti(RiferimentiAttivitaType riferimentiAttivitaType, Movimenti movimento, GregorianCalendar oggi) {

	List<ValoreParametroType> destRaggiunti = StcUtils.getCampiDaAltriDati(riferimentiAttivitaType.getAltriDati(),
		"RFC239_NOTIFICA_ET_DEST_RAGG");
	if (destRaggiunti == null || destRaggiunti.isEmpty()) {
	    return;
	}
	Set<String> dests = new HashSet<String>();
	for (ValoreParametroType vp : destRaggiunti) {
	    if (!StringUtils.defaultString(vp.getCodice()).trim().equals("")) {
		dests.add(StringUtils.defaultString(vp.getCodice()).trim());
	    }
	}
	if (dests.isEmpty()) {
	    return;
	}
	log.debug("===================================>Lista dei destinatari raggiunti {}", dests);
	// PER OGNI DESTINATARIO DEVO TROVARE GLI ENDO PROCEDIMENTI CHE NOTIFICANO E SEGNARE IL MOVIMENTO COME ESEGUITO 
	// E METTERE PER OGNI MOVIMENTO IDATTIVITA' COME <DESTINATARIO>###<IDSEM>
	log.debug("===================================>Ricerco gli endo procedimenti della pratica per segnare come eseguiti i movimenti");
	List<Istanzeprocedimenti> findByIstanze = istanzeprocedimentiService.findByIstanze(movimento.getIstanza().getId().getCodice());
	Integer codiceInventarioNotificato = null;
	if (movimento.getEndoprocedimento() != null && movimento.getEndoprocedimento().getId() != null
		&& movimento.getEndoprocedimento().getId().getCodice() != null) {
	    codiceInventarioNotificato = movimento.getEndoprocedimento().getId().getCodice();
	}
	for (Istanzeprocedimenti istanzeprocedimenti : findByIstanze) {
	    if (!istanzeprocedimenti.getId().getCodiceinventario().equals(codiceInventarioNotificato)) { // #2022111110000256 Escludo i movimenti dell'endo notificato
													 // non devo eseguire altri movimenti dello stesso endo					
		Amministrazioni amm = this.recuperaAmministrazione(istanzeprocedimenti);
		if (amm == null || StringUtils.isBlank(StringUtils.defaultString(amm.getStcIdnodo()).trim())
			|| !dests.contains(StringUtils.defaultString(amm.getStcIdsportello()).trim())) {
		    continue;
		}
		String idDestinatario = StringUtils.defaultString(amm.getStcIdsportello()).trim();
		log.debug("===================================>Trovata istanzeprocedimenti endo: {}, idNodo: {}, idSportello: {}",
			new Object[] { istanzeprocedimenti.getInventarioprocedimenti().getId(), amm.getStcIdnodo(), idDestinatario });
		log.debug("===================================>E' TRA I DESTINATARI RAGGIUNTI");
		log.debug("===================================>E' UN NODO 239 CERCO I MOVIMENTI");
		List<Movimenti> movs = movimentiService.findMovimentiByIstanzeprocedimenti(istanzeprocedimenti, SceltaMovimentiEnum.NON_ESEGUITI);
		for (Movimenti m : movs) {
		    if (!m.getId().getCodice().equals(movimento.getId().getCodice())) {
			m.setData(oggi.getTime());
			m.setInviatoConStc(MovimentiService.STC_INVIATO);
			m.setIdAttDest(idDestinatario + "###" + riferimentiAttivitaType.getIdAttivita());
			log.debug("===================================>trovato il movimento con codice {}, gli assegno l'identificativo attività: {}",
				m.getIdAttDest());
			movimentiService.update(m);
			istanzeeventiService
				.insert("Il movimento è stato eseguito in automatico a seguito di una notifica enti terzi (del movimento con id=" +
					movimento.getId().getCodice() + ")", IstanzeeventiConstants.CATEGORIA_STC_IA, m, null);
		    }
		}
	    }
	}
    }

    private void registraEventiPerAllegatiNonInviati(RiferimentiAttivitaType riferimentiAttivitaType, Movimenti movimento) {

	List<ValoreParametroType> allegatiNonInviati = StcUtils.getCampiDaAltriDati(riferimentiAttivitaType.getAltriDati(),
		"RFC239_WARNING_ALLEGATI");
	if (allegatiNonInviati == null || allegatiNonInviati.isEmpty()) {
	    return;
	}
	// non è stato possibile notificare gli allegati
	allegatiNonInviati = StcUtils.getCampiDaAltriDati(riferimentiAttivitaType.getAltriDati(), "RFC239_WARNING_ALLEGATI_FILE");
	StringBuilder docs = new StringBuilder();
	if (allegatiNonInviati != null && !allegatiNonInviati.isEmpty()) {
	    for (ValoreParametroType vpt : allegatiNonInviati) {
		docs.append("-").append(vpt.getDescrizione());
	    }
	}
	istanzeeventiService.insert("Il movimento è stato notificato ma non è stato possibile inviare gli allegati selezionati rif(" + docs + ")",
		IstanzeeventiConstants.CATEGORIA_STC_IA, movimento, null);
    }

    private boolean verificaSeSettareLoStato(Amministrazioni amministrazioniStc) {

	Verticalizzazioniparametri dest = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_RFC239,
		WebConstants.VERTICALIZZAZIONE_RFC239_LISTA_NODI_NO_RICEVUTA, ORMHelper.getSoftware());
	if (dest == null || StringUtils.isEmpty(dest.getValore())) {
	    return false;
	}
	return (StringUtils.defaultString(dest.getValore()).toUpperCase().indexOf(amministrazioniStc.getStcIdsportello().toUpperCase()) >= 0);
    }

    private void verificaRispostaNotificaSTC(NotificaAttivitaResponse response, String riferimentoPerDebug) {

	if (response == null) {
	    throw new RuntimeException("Tornata risposta null dalla richiesta di notifica STC");
	}
	List<ErroreType> errori = response.getDettaglioErrore();
	if (errori == null || errori.isEmpty()) {
	    return;
	}
	log.error("notificaAttivita# errori di notifica {}", new Object[] { riferimentoPerDebug });
	throw new RuntimeException(errori.get(0).getNumeroErrore() + ", " + errori.get(0).getDescrizione());
    }

    private void valorizzaAltroDatoDaCodiceMovimentoRifProto(DettaglioAttivitaType attivita, Integer codiceMovimentoRifProto,
	    String riferimentoPerDebug) {

	if (codiceMovimentoRifProto == null) {
	    return;
	}
	Movimenti movRifProto = movimentiNoSecurityService.findById(new PkId(codiceMovimentoRifProto));
	if (movRifProto == null) {
	    return;
	}
	log.debug("notificaAttivita# movRifProto {} - {}", new Object[] { movRifProto.getId(), riferimentoPerDebug });
	String numeroProtocollo = movRifProto.getNumeroprotocollo();
	ParametroType altroDatoNum = new ParametroType();
	altroDatoNum.setNome(StcService.ALTRO_DATO_RISERVATO_RIFPROTO_NUM);
	ValoreParametroType vptNum = new ValoreParametroType();
	vptNum.setCodice(numeroProtocollo);
	vptNum.setDescrizione(numeroProtocollo);
	altroDatoNum.getValore().add(vptNum);
	attivita.getAltriDati().add(altroDatoNum);
	if (movRifProto.getDataprotocollo() != null) {
	    String dataProtocollo = Utilities.formatDate(movRifProto.getDataprotocollo(), false);
	    ParametroType altroDato = new ParametroType();
	    altroDato.setNome(StcService.ALTRO_DATO_RISERVATO_RIFPROTO_DATA);
	    ValoreParametroType vpt = new ValoreParametroType();
	    vpt.setCodice(dataProtocollo);
	    vpt.setDescrizione(dataProtocollo);
	    altroDato.getValore().add(vpt);
	    attivita.getAltriDati().add(altroDato);
	}
    }

    private void valorizzaAltroDatoDomandaSTCIdDomandaMitt(DettaglioAttivitaType attivita, Integer codiceistanza, String riferimentoPerDebug) {

	List<Domandestc> dstcs = domandestcService.findByIstanza(codiceistanza);
	if (dstcs == null || dstcs.isEmpty() || dstcs.get(0) == null) {
	    return;
	}
	Domandestc dstc = dstcs.get(0);
	log.debug("notificaAttivita# dstcs.size() > 0 {} - {}", new Object[] { dstc.getIdDomandamitt(), riferimentoPerDebug });
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(ALTRO_DATO_DOMANDESTC_ID_DOMANDAMITT);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(dstc.getIdDomandamitt());
	vpt.setDescrizione(dstc.getIdDomandamitt());
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltroDatoNonInviareProcedimenti(DettaglioAttivitaType attivita, boolean isNonInviareProcedimenti,
	    String riferimentoPerDebug) {

	if (!isNonInviareProcedimenti) {
	    return;
	}
	log.debug("notificaAttivita# isNonInviareProcedimenti {} - {}", new Object[] { isNonInviareProcedimenti, riferimentoPerDebug });
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(NON_INVIARE_PROCEDIMENTI);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(NON_INVIARE_PROCEDIMENTI);
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltroDatoOperatoreNotificaConOperatoreLoggato(DettaglioAttivitaType attivita, String riferimentoPerDebug) {

	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	if (responsabile == null) {
	    return;
	}
	log.debug("notificaAttivita# valorizzaAltroDatoOperatoreNotifica responsabile: {} - {}",
		new Object[] { responsabile.getResponsabile(), riferimentoPerDebug });
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(OPERATORE_NOTIFICA);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(responsabile.getUserid());
	vpt.setDescrizione(responsabile.getResponsabile());
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltroDatoSpostaAllegatiInPratica(DettaglioAttivitaType attivita, boolean isAllegaDocPratica, String riferimentoPerDebug) {

	if (!isAllegaDocPratica) {
	    return;
	}
	// Sezione riferita a TIPIMOV_STC_MAPPING.flagAllegaDocPratica
	log.debug("notificaAttivita# isAllegaDocPratica {} - {}", new Object[] { isAllegaDocPratica, riferimentoPerDebug });
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(SPOSTA_ALLEGATI_IN_PRATICA);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(SPOSTA_ALLEGATI_IN_PRATICA);
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltroDatoNotificaInteraPratica(DettaglioAttivitaType attivita, String riferimentoPerDebug) {

	log.debug("notificaAttivita# isNotificaInteraPratica - {}", new Object[] { riferimentoPerDebug });
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(ALTRI_DATI_NOTIFICA_INTERA_PRATICA);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(ALTRI_DATI_NOTIFICA_INTERA_PRATICA);
	vpt.setDescrizione(ALTRI_DATI_NOTIFICA_INTERA_PRATICA);
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltroDatoAlberoProcDestinatario(DettaglioAttivitaType attivita, Movimenti movimento, Amministrazioni amministrazioneStc,
	    String riferimentoPerDebug) {

	// se è configurato il parametro di individuazione alberoproc nell'NLA destinatario TIPIMOV_STC_ALBEROPROC
	// lo metto nell'array di altri dati dell'attività
	log.debug("notificaAttivita# se è configurato il parametro di individuazione alberoproc nell'NLA destinatario TIPIMOV_STC_ALBEROPROC  {}",
		new Object[] { riferimentoPerDebug });
	Set<TipimovStcAlberoproc> alberoprocsConf = movimento.getTipomovimento().getTipimovStcAlberoprocs();
	for (TipimovStcAlberoproc tipimovStcAlberoproc : alberoprocsConf) {
	    if (tipimovStcAlberoproc != null && tipimovStcAlberoproc.getId() != null) {
		Amministrazioni ammTipiMov = tipimovStcAlberoproc.getAmministrazioni();
		log.debug("notificaAttivita# tipimovStcAlberoproc {} -  {}", new Object[] { tipimovStcAlberoproc.getId(), riferimentoPerDebug });
		if (ammTipiMov != null && ammTipiMov.getId() != null && ammTipiMov.getId().getCodice() != null) {
		    log.debug("notificaAttivita# tipimovStcAlberoproc ammTipiMov.getId().getCodice() {} - {}",
			    new Object[] { ammTipiMov.getId().getCodice(), riferimentoPerDebug });
		    if (ammTipiMov.getId().getCodice().intValue() == amministrazioneStc.getId().getCodice().intValue()) {
			ParametroType altroDato = new ParametroType();
			altroDato.setNome(NlaHelperService.NOTIFICA_ATTIVITA_ALTRO_DATO_ALBEROPROC);
			ValoreParametroType vpt = new ValoreParametroType();
			vpt.setCodice(String.valueOf(tipimovStcAlberoproc.getFkScid()));
			vpt.setDescrizione(String.valueOf(tipimovStcAlberoproc.getFkScid()));
			altroDato.getValore().add(vpt);
			attivita.getAltriDati().add(altroDato);
			break;
		    }
		}
	    }
	}
    }

    private void valorizzaAltroDatoPerNotificaSchedeDinamiche(DettaglioAttivitaType attivita, SportelloType mittente, SportelloType destinatario,
	    List<Dyn2ModellitValoreBean> modelliList, String riferimentoPerDebug) {

	// passaggio dei dati delle schede dinamiche. accodo agli altri dati una serie di parametri con i l nome
	// NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT
	// ogni valore di questo parametro rappresenta un modello dinamico che il NLA destinatario deve copiare
	// questa funzionalità è valida solo tra due NLA SIGEPRO e solo se appartengono alla stessa base dati ed
	// idcomune ossia possono utilizzare lo stesso modello di dati dinamici.
	// la condizione è che sportelloMittente.idEnte == sportelloDestinatario.idEnte
	if (!mittente.getIdEnte().equalsIgnoreCase(destinatario.getIdEnte()) || modelliList == null) {
	    return;
	}
	log.debug("notificaAttivita# mittente.getIdEnte().equalsIgnoreCase(destinatario.getIdEnte())  {}", new Object[] { riferimentoPerDebug });
	log.debug("notificaAttivita# modelliList!=null  {}", new Object[] { riferimentoPerDebug });
	for (Dyn2ModellitValoreBean modelloBean : modelliList) {
	    if (modelloBean != null && modelloBean.getValore() != null && modelloBean.getValore().booleanValue()) {
		Dyn2Modellit chiave = modelloBean.getChiave();
		if (chiave != null && chiave.getId() != null && chiave.getId().getCodice() != null) {
		    ParametroType altroDato = new ParametroType();
		    altroDato.setNome(NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT);
		    ValoreParametroType vpt = new ValoreParametroType();
		    vpt.setCodice(String.valueOf(chiave.getId().getCodice().intValue()));
		    // BOCCI 2012-08-22 BUGZILLA ID 619 nel caso di altri dati che individuano una scheda dinamica metto la descrizione e non più il codice 
		    // per poter leggere direttamente questa nella renderizzazione della notifica altrimenti devo fare un lookup nella base dati
		    vpt.setDescrizione(chiave.getDescrizione());
		    altroDato.getValore().add(vpt);
		    attivita.getAltriDati().add(altroDato);
		}
	    }
	}
    }

    private void valorizzaAltroDatoPerNotificaSubendo(DettaglioAttivitaType attivita, Integer codiceMovimento, Integer codiceInventario,
	    String tipoMovimento, Integer codiceAmministrazioneStc, String riferimentoPerDebug) {

	if (codiceInventario == null || !this.movimentiService.verificaSeNotificareSubEndo(tipoMovimento, codiceAmministrazioneStc)) {
	    return;
	}
	log.debug("notificaAttivita# verificaSeNotificareSubEndo  {}", new Object[] { riferimentoPerDebug });
	log.debug("Devo notificare i subendo subEndo del codice inventario {} per il movimento {} ", codiceInventario, codiceMovimento);
	List<InventarioprocEndo> subEndos = inventarioprocEndoService.findByInventarioprocT(ORMHelper.getIdcomune(), codiceInventario, null, null,
		null, null, false); // recupero tutti gli endo indipendentemente dal comune / se abilitati / pubblicati o meno
	log.debug("Verificati i subendo subEndo del codice inventario {} per il movimento {} ", codiceInventario, codiceMovimento);
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(NlaHelperService.NOTIFICA_ATTIVITA_LISTA_ENDO_PROCEDIMENTI_DA_COPIARE);
	if (!subEndos.isEmpty()) {
	    log.debug("trovati i subEndo del codice inventario {}", codiceInventario);
	    for (InventarioprocEndo ipe : subEndos) {
		ValoreParametroType vpt = new ValoreParametroType();
		vpt.setCodice(String.valueOf(ipe.getInventarioprocEndoD().getId().getCodice()));
		vpt.setDescrizione(vpt.getCodice());
		altroDato.getValore().add(vpt);
	    }
	}
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(String.valueOf(codiceInventario));
	vpt.setDescrizione(vpt.getCodice());
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltroDatoDaAmministrazioneMovimento(DettaglioAttivitaType attivita, Movimenti movimento) {

	if (movimento == null) {
	    return;
	}
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(NlaHelperService.CODICE_AMMINISTRAZIONE_MOVIMENTO_MITTENTE);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(String.valueOf(movimento.getAmministrazioni().getId().getCodice()));
	vpt.setDescrizione(String.valueOf(movimento.getAmministrazioni().getId().getCodice()));
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltroDatoDaLogicaMittentiMultipli(DettaglioAttivitaType attivita, Istanze istanza, String riferimentoPerDebug) {

	boolean isLogicaMittentiMultipli = this.verticalizzazioniService.isAttivaAndParametroEqualsToValore(
		VerticalizzazioneProtocolloAttivoServiceImpl.NOME_VERTICALIZZAZIONE,
		VerticalizzazioneProtocolloAttivoServiceImpl.VERTICALIZZAZIONE_PROTOCOLLO_ATTIVO_LOGICA_MITTENTI_MULTIPLI, "1");
	log.debug("Nuova Logica mittenti multipli {}", isLogicaMittentiMultipli);
	if (!isLogicaMittentiMultipli) {
	    return;
	}
	log.debug("notificaAttivita# isLogicaMittentiMultipli {} - {}", new Object[] { isLogicaMittentiMultipli, riferimentoPerDebug });
	log.debug("Logica mittenti multipli Alberoproc {}, comune {}", istanza.getAlberoproc().getId(), istanza.getComune().getCodicecomune());
	Integer amministrazione = alberoprocProtocolloService.findAmministrazioniByAlberoprocIdAndComune(istanza.getAlberoproc().getId().getCodice(),
		istanza.getComune().getCodicecomune());
	log.debug("Logica mittenti multipli configurazione: {}", amministrazione);
	if (amministrazione == null) {
	    return;
	}
	log.debug("Logica mittenti multipli configurazione: amministrazione trovata {}", amministrazione);
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(NlaHelperService.NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC_MITTENTE);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(String.valueOf(amministrazione));
	vpt.setDescrizione(String.valueOf(amministrazione));
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltroDatoCodiceAmministrazioneSTC(DettaglioAttivitaType attivita, SportelloType mittente, SportelloType destinatario,
	    Amministrazioni amministrazioneSTC, String riferimentoPerDebug) {

	boolean isStessoNodoEnte = nlaHelperService.checkIsStessoNodoStessoEnte(mittente, destinatario);
	if (!isStessoNodoEnte) {
	    return;
	}
	// AMMINISTRAZIONE MULTIPLA NEL NODO DESTINATARIO DEVE ESSERE INVIATA L'AMMINISTRAZIONE SCELTA DALL'OPERATORE
	log.debug("notificaAttivita# isStessoNodoEnte {} - {}", new Object[] { isStessoNodoEnte, riferimentoPerDebug });
	int countTuple = amministrazioniService.countAmministrazioniSTC(destinatario.getIdNodo(), destinatario.getIdEnte(),
		destinatario.getIdSportello());
	log.debug("notificaAttivita# countTuple {} -{}", new Object[] { countTuple, riferimentoPerDebug });
	if (countTuple == 1) {
	    return;
	}
	log.debug("notificaAttivita# imposto altro dato NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC {} -{}",
		new Object[] { amministrazioneSTC.getId().getCodice(), riferimentoPerDebug });
	ParametroType altroDato = new ParametroType();
	altroDato.setNome(NlaHelperService.NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC);
	ValoreParametroType vpt = new ValoreParametroType();
	vpt.setCodice(String.valueOf(amministrazioneSTC.getId().getCodice()));
	vpt.setDescrizione(String.valueOf(amministrazioneSTC.getId().getCodice()));
	altroDato.getValore().add(vpt);
	attivita.getAltriDati().add(altroDato);
    }

    private void valorizzaAltriDati(DettaglioAttivitaType attivita, List<TipimovStcAltridatiValoreBean> altriDatiList, String riferimentoPerDebug) {

	if (altriDatiList == null || altriDatiList.isEmpty()) {
	    return;
	}
	for (TipimovStcAltridatiValoreBean valoreBean : altriDatiList) {
	    log.debug("notificaAttivita# altriDatiList {} - {} - {}",
		    new Object[] { valoreBean.getChiave(), valoreBean.getValore(), riferimentoPerDebug });
	    ParametroType altroDato = new ParametroType();
	    TipimovStcAltridati chiave = valoreBean.getChiave();
	    altroDato.setNome(chiave.getNomeCampo());
	    ValoreParametroType vpt = new ValoreParametroType();
	    vpt.setCodice(valoreBean.getValore());
	    vpt.setDescrizione(valoreBean.getValore());
	    altroDato.getValore().add(vpt);
	    attivita.getAltriDati().add(altroDato);
	}
    }

    private void verificaSeNotificaAdEnteTerzoCART(List<TipimovStcAltridatiValoreBean> altriDatiList, List<IstanzeallegatiDTO> istanzeallegatis,
	    Istanze istanza, Inventarioprocedimenti procedimento, String riferimentoPerDebug) {

	//Lion se si tratta di una notifica ad ente terzo del CART ed è attiva la verticalizzazione per la generazione degli allegati per la notifica CART
	boolean isCartAttivo = this.verticalizzazioniService.isAttivaAndParametroEqualsToValore(WebConstants.VERTICALIZZAZIONE_CART,
		WebConstants.VERTICALIZZAZIONE_CART_GENERA_ALLEGATI_NOTIFICA, WebConstants.S);
	if (isCartAttivo && isNotificaCART(altriDatiList)) {
	    log.debug("notificaAttivita# isCartAttivo && isNotificaCART(altriDatiList)  - {}", new Object[] { riferimentoPerDebug });
	    //verifico che esistano le condizioni per procedere con la notifica CART, altrimenti genero gli allegati mancanti.
	    boolean generaAllegati = true;
	    //String errorMessage = null;
	    //verifico che esista fra gli allegati dell'istanza il file <codice_pratica>.SUAP.XML che contiene il messaggio di presentazione domanda CART
	    PresentazioneDomanda pd = null;
	    Integer idMessaggioPresentazione = trovaMessaggioPresentazioneDomandaCART(istanzeallegatis);
	    if (idMessaggioPresentazione == null) {
		//se il messaggio di presentazione SUAP.XML non è già stato selezionato dall'utente che richiede la notifica STC
		//interrogo i documenti dell'istanza per oggetto.nomefile LIKE '%.SUAP.XML'
		Documentiistanza presentazioneCart = this.cartPresentazioneDomandaService.findDocumentoIstanzaMessaggioPresentazioneDomanda(istanza);
		if (presentazioneCart != null && presentazioneCart.getOggetto() != null) {
		    idMessaggioPresentazione = presentazioneCart.getOggetto().getId().getCodice();
		}
	    }
	    if (idMessaggioPresentazione != null) {
		//verifico che il file individuato sia effettivamente il messaggio di presentazione di una domanda CART
		try {
		    pd = this.cartPresentazioneDomandaService.getMessaggioPresentazioneDomanda(idMessaggioPresentazione);
		} catch (Exception e) {
		    log.warn(
			    "notificaAttivita - notifica CART ente terzo: il documento dell'istanza .SUAP.XML non contiene un messaggio di presentazione domanda CART valido.");
		}
		if (pd != null) {
		    String codEndoReg = null;
		    if (procedimento != null) {
			String endoprocedimento = procedimento.getProcedimento() + " (" + procedimento.getId() + ")";
			StpEndoTipo1 endoReg = this.endoTipo1Service.findByInventarioProcedimenti(procedimento);
			if (endoReg == null) {
			    throw new RuntimeException("Attenzione! L'endoprocedimento specificato [" + endoprocedimento +
						       "]non è stato configurato come endoprocedimento regionale. (Errore: manca la riga in STP_ENDO_TIPO1)");
			}
			codEndoReg = endoReg.getCodiceEndoRegionale();
			if (StringUtils.isBlank(codEndoReg)) {
			    throw new RuntimeException("Attenzione! L'endoprocedimento specificato [" + endoprocedimento +
						       "] non è stato configurato come endoprocedimento regionale. (Errore: la riga in STP_ENDO_TIPO1 ha il codice endo regionale nullo)");
			}
		    }
		    //verifico che esistano anche gli altri allegati obbligatori per la notifica elencati nel messaggio di presentazione stesso.
		    String std02 = "STANDARD 0";//TODO determinare se siamo in std_0 o std_2
		    List<String> missingAttachments = cartPresentazioneDomandaService.getAllegatiMancantiPerNotificaEnteTerzo(pd,
			    istanza.getId().getCodice(), std02, codEndoReg);
		    generaAllegati = !missingAttachments.isEmpty();
		} else {
		    generaAllegati = true;
		}
	    }
	    if (generaAllegati) {
		throw new RuntimeException(
			"La notifica di pratiche non provenienti da STAR - ad esempio PEC o altri frontoffice - non è più supportata.");
	    }
	}
    }

    private void valorizzaDocumentiDaProcure(DettaglioAttivitaType attivita, List<IstanzeprocureDTO> documentiprocuras, String riferimentoPerDebug) {

	if (documentiprocuras == null || documentiprocuras.isEmpty()) {
	    return;
	}
	for (IstanzeprocureDTO istanzeprocureDTO : documentiprocuras) {
	    if (istanzeprocureDTO.getCodiceOggettoDocId() == null) {
		continue;
	    }
	    log.debug("notificaAttivita# istanzeprocureDTO  {} - {}",
		    new Object[] { istanzeprocureDTO.getId().getCodice().toString(), riferimentoPerDebug });
	    DocumentiType documento = new DocumentiType();
	    documento.setId(istanzeprocureDTO.getId().getCodice().toString());
	    documento.setDocumento("Documento della procura di " + istanzeprocureDTO.getAnagrafeProcuratore().getDescrizioneRichiedente());
	    if (istanzeprocureDTO.getCodiceOggetto() != null) {
		nlaHelperService.addAltroDato(NlaHelperService.ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_PROCURE, istanzeprocureDTO.getCodiceOggetto(),
			istanzeprocureDTO.getControllook(), attivita);
		AllegatiType allegato = new AllegatiType();
		allegato.setId(String.valueOf(istanzeprocureDTO.getCodiceOggetto()));
		allegato.setAllegato(istanzeprocureDTO.getNomeFile());
		documento.setAllegati(allegato);
		nlaHelperService.addMetadatiOggetto(istanzeprocureDTO.getCodiceOggetto(), documento, allegato);
		attivita.getDocumenti().add(documento);
	    }
	    //fabrizioc: se la notifica prevede la scelta del tipo documento allora passo questo invece 
	    // di quello presente nei metadati
	    if (StringUtils.isNotBlank(istanzeprocureDTO.getTipoDocumento())) {
		documento.setTipoDocumento(istanzeprocureDTO.getTipoDocumento());
	    }
	    DocumentiType documentoDocId = new DocumentiType();
	    documentoDocId.setId(istanzeprocureDTO.getId().getCodice().toString());
	    documentoDocId.setDocumento(
		    "Documento di identita' associato alla procura di " + istanzeprocureDTO.getAnagrafeProcuratore().getDescrizioneRichiedente());
	    AllegatiType allegato = new AllegatiType();
	    allegato.setId(String.valueOf(istanzeprocureDTO.getCodiceOggettoDocId()));
	    allegato.setAllegato(istanzeprocureDTO.getNomeFileDocId());
	    documentoDocId.setAllegati(allegato);
	    nlaHelperService.addMetadatiOggetto(istanzeprocureDTO.getCodiceOggettoDocId(), documentoDocId, allegato);
	    attivita.getDocumenti().add(documentoDocId);
	}
    }

    private void valorizzaDocumentiDaDocumentiAnagrafe(DettaglioAttivitaType attivita, List<AnagrafedocumentiDTO> documentiAnagrafe,
	    String riferimentoPerDebug) {

	if (documentiAnagrafe == null || documentiAnagrafe.isEmpty()) {
	    return;
	}
	for (AnagrafedocumentiDTO anagrafedocumenti : documentiAnagrafe) {
	    if (anagrafedocumenti.getCodiceOggetto() == null) {
		continue;
	    }
	    log.debug("notificaAttivita# anagrafedocumenti  {} - {}",
		    new Object[] { anagrafedocumenti.getId().getCodice().toString(), riferimentoPerDebug });
	    DocumentiType documento = new DocumentiType();
	    documento.setId(anagrafedocumenti.getId().getCodice().toString());
	    boolean descrizioneNonTrovata = false;
	    if (StringUtils.isNotBlank(anagrafedocumenti.getDocumento())) {
		documento.setDocumento(anagrafedocumenti.getDocumento());
	    } else if (StringUtils.isNotBlank(anagrafedocumenti.getTipoDocumento())) {
		documento.setDocumento(anagrafedocumenti.getTipoDocumento());
	    } else {
		descrizioneNonTrovata = true;
		documento.setDocumento("Documento non codificato"); // PER EVITARE ERRORE NELLA NOTIFICA
	    }
	    if (descrizioneNonTrovata) {
		documento.setDocumento(anagrafedocumenti.getNomeFile());
	    }
	    AllegatiType allegato = new AllegatiType();
	    allegato.setId(String.valueOf(anagrafedocumenti.getCodiceOggetto()));
	    allegato.setAllegato(anagrafedocumenti.getNomeFile());
	    documento.setAllegati(allegato);
	    nlaHelperService.addMetadatiOggetto(anagrafedocumenti.getCodiceOggetto(), documento, allegato);
	    //fabrizioc: se la notifica prevede la scelta del tipo documento allora passo questo invece di quello presente nei metadati
	    if (StringUtils.isNotBlank(anagrafedocumenti.getTipoDocumento())) {
		documento.setTipoDocumento(anagrafedocumenti.getTipoDocumento());
	    }
	    attivita.getDocumenti().add(documento);
	}
    }

    private void valorizzaDocumentiDaDocumentiIstanza(DettaglioAttivitaType attivita, List<DocumentiistanzaDTO> documentiistanzas,
	    String riferimentoPerDebug) {

	if (documentiistanzas == null || documentiistanzas.isEmpty()) {
	    return;
	}
	for (DocumentiistanzaDTO documentiistanza : documentiistanzas) {
	    if (documentiistanza.getCodiceOggetto() == null) {
		continue;
	    }
	    log.debug("notificaAttivita# documentiistanza  {} - {}",
		    new Object[] { documentiistanza.getId().getCodice().toString(), riferimentoPerDebug });
	    DocumentiType documento = new DocumentiType();
	    documento.setId(documentiistanza.getId().getCodice().toString());
	    documento.setDocumento(documentiistanza.getDocumento());
	    documento.setAnnotazioni(documentiistanza.getNote());
	    // Modifica - http://redmine/redmine/issues/872
	    if (documentiistanza.getData() != null) {
		GregorianCalendar dataIstaAllegato = new GregorianCalendar();
		dataIstaAllegato.setTime(documentiistanza.getData());
		XMLGregorianCalendar dataIstaAllegatoXml = Utilities.getXMLGregorianCalendar(dataIstaAllegato);
		documento.setData(dataIstaAllegatoXml);
	    }
	    nlaHelperService.addAltroDato(NlaHelperService.ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_MOVIMENTO, documentiistanza.getCodiceOggetto(),
		    documentiistanza.getControllook(), attivita);
	    AllegatiType allegato = new AllegatiType();
	    allegato.setId(String.valueOf(documentiistanza.getCodiceOggetto()));
	    allegato.setAllegato(documentiistanza.getNomeFile());
	    documento.setAllegati(allegato);
	    nlaHelperService.addMetadatiOggetto(documentiistanza.getCodiceOggetto(), documento, allegato);
	    //fabrizioc: se la notifica prevede la scelta del tipo documento allora passo questo invece di quello presente nei metadati
	    if (StringUtils.isNotBlank(documentiistanza.getTipoDocumento())) {
		documento.setTipoDocumento(documentiistanza.getTipoDocumento());
	    }
	    attivita.getDocumenti().add(documento);
	}
    }

    private void valorizzaDocumentiDaIstanzeAllegati(DettaglioAttivitaType attivita, List<IstanzeallegatiDTO> istanzeallegatis,
	    String riferimentoPerDebug) {

	if (istanzeallegatis == null || istanzeallegatis.isEmpty()) {
	    return;
	}
	for (IstanzeallegatiDTO istanzeallegati : istanzeallegatis) {
	    if (istanzeallegati.getCodiceOggetto() == null) {
		continue;
	    }
	    log.debug("notificaAttivita# istanzeallegati  {} - {}", new Object[] { istanzeallegati.getId().getCodice(), riferimentoPerDebug });
	    DocumentiType documento = new DocumentiType();
	    documento.setId(istanzeallegati.getId().getCodice().toString());
	    documento.setDocumento(istanzeallegati.getAllegatoextra());
	    documento.setAnnotazioni(istanzeallegati.getNote());
	    nlaHelperService.addAltroDato(NlaHelperService.ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_MOVIMENTO, istanzeallegati.getCodiceOggetto(),
		    istanzeallegati.getControllook(), attivita);
	    AllegatiType allegato = new AllegatiType();
	    allegato.setId(String.valueOf(istanzeallegati.getCodiceOggetto()));
	    allegato.setAllegato(istanzeallegati.getNomeFile());
	    documento.setAllegati(allegato);
	    nlaHelperService.addMetadatiOggetto(istanzeallegati.getCodiceOggetto(), documento, allegato);
	    //fabrizioc: se la notifica prevede la scelta del tipo documento allora passo questo invece di quello presente nei metadati
	    if (StringUtils.isNotBlank(istanzeallegati.getTipoDocumento())) {
		documento.setTipoDocumento(istanzeallegati.getTipoDocumento());
	    }
	    attivita.getDocumenti().add(documento);
	}
    }

    private void valorizzaDocumentiDaMovimentiAllegati(DettaglioAttivitaType attivita, List<MovimentiallegatiDTO> movimentiallegatis,
	    String riferimentoPerDebug) {

	if (movimentiallegatis == null || movimentiallegatis.isEmpty()) {
	    return;
	}
	for (MovimentiallegatiDTO movimentiallegato : movimentiallegatis) {
	    if (movimentiallegato.getCodiceOggetto() == null) {
		continue;
	    }
	    log.debug("notificaAttivita# movimento allegato {} - {}", new Object[] { movimentiallegato.getId().getCodice(), riferimentoPerDebug });
	    DocumentiType documento = new DocumentiType();
	    documento.setId(movimentiallegato.getId().getCodice().toString());
	    documento.setDocumento(movimentiallegato.getDescrizione());
	    documento.setAnnotazioni(movimentiallegato.getNote());
	    if (movimentiallegato.getDataregistrazione() != null) {
		GregorianCalendar dataMovAllegato = new GregorianCalendar();
		dataMovAllegato.setTime(movimentiallegato.getDataregistrazione());
		XMLGregorianCalendar dataMovAllegatoXml = Utilities.getXMLGregorianCalendar(dataMovAllegato);
		documento.setData(dataMovAllegatoXml);
	    }
	    log.debug("notificaAttivita# verifico se il codice oggetto del movimento allegato {} è diverso da NULL:",
		    movimentiallegato.getId().getCodice());
	    nlaHelperService.addAltroDato(NlaHelperService.ALTRI_DATI_DOC_VALIDO_NON_VALIDO_DOC_MOVIMENTO, movimentiallegato.getCodiceOggetto(),
		    movimentiallegato.getControllook(), attivita);
	    log.debug("notificaAttivita# il codice oggetto del movimento allegato {} è {}:",
		    new Object[] { movimentiallegato.getId().getCodice(), movimentiallegato.getCodiceOggetto() });
	    AllegatiType allegato = new AllegatiType();
	    allegato.setId(String.valueOf(movimentiallegato.getCodiceOggetto()));
	    allegato.setAllegato(movimentiallegato.getNomeFile());
	    documento.setAllegati(allegato);
	    nlaHelperService.addMetadatiOggetto(movimentiallegato.getCodiceOggetto(), documento, allegato);
	    //fabrizioc: se la notifica prevede la scelta del tipo documento allora passo questo invece di quello presente nei metadati
	    if (StringUtils.isNotBlank(movimentiallegato.getTipoDocumento())) {
		documento.setTipoDocumento(movimentiallegato.getTipoDocumento());
	    }
	    attivita.getDocumenti().add(documento);
	}
    }

    private void verificaNotifica183ViaSEM(String stcIdnodo) {

	if (StringUtils.isBlank(stcIdnodo)) {
	    return;
	}
	Verticalizzazioniparametri sem183 = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAIONE_RFC239,
		WebConstants.VERTICALIZZAZIONE_RFC239_NOTIFICA_183_VIA_SEM_ID_NODO, ORMHelper.getSoftware());
	if (sem183 == null || StringUtils.isEmpty(sem183.getValore()) || StringUtils.isEmpty(sem183.getValore().trim())) {
	    return;
	}
	String idNodo = sem183.getValore().trim();
	String[] listaNodi = idNodo.split(",");
	for (String n : listaNodi) {
	    if (StringUtils.defaultString(stcIdnodo, "AAA_321").equalsIgnoreCase(StringUtils.defaultString(n, "NNN_123"))) {
		throw new InvalidConfigurationException("Le notifiche 183 via SEM non sono più supportate. Verificare il parametro " +
							WebConstants.VERTICALIZZAZIONE_RFC239_NOTIFICA_183_VIA_SEM_ID_NODO +
							" della verticalizzazione " + WebConstants.VERTICALIZZAIONE_RFC239);
	    }
	}
    }

    private ProcedimentoType getProcedimentoPrincipale(String idProcedimento, Inventarioprocedimenti procedimento, Integer codiceIstanza,
	    String riferimentoLogDebug) {

	ProcedimentoType procedimentoPrincipale = new ProcedimentoType();
	procedimentoPrincipale.setPrincipale(true);
	if (StringUtils.isBlank(idProcedimento)) {
	    return procedimentoPrincipale;
	}
	procedimentoPrincipale.setCodice(idProcedimento);
	procedimentoPrincipale.setDescrizione(procedimento.getProcedimento());
	IstanzeprocedimentiId id = new IstanzeprocedimentiId(codiceIstanza, procedimento.getId().getCodice());
	Istanzeprocedimenti ip = istanzeprocedimentiService.findById(id);
	if (ip == null) {
	    return procedimentoPrincipale;
	}
	log.debug("notificaAttivita# ip non nullo {} -{}", new Object[] { ip.getId(), riferimentoLogDebug });
	Date dataAttivazione = ip.getDataattivazione();
	if (dataAttivazione != null) {
	    GregorianCalendar dataAttivazioneCal = new GregorianCalendar();
	    dataAttivazioneCal.setTime(dataAttivazione);
	    procedimentoPrincipale.setDataAttivazione(Utilities.getXMLGregorianCalendar(dataAttivazioneCal));
	}
	if (BooleanUtils.isTrue(ip.getAcquisito())) {
	    EstremiAttoType attoType = new EstremiAttoType();
	    if (StringUtils.isNotBlank(ip.getProtNum())) {
		attoType.setRiferimento(ip.getProtNum());
		if (ip.getProtDel() != null) {
		    GregorianCalendar calendar = new GregorianCalendar();
		    calendar.setTime(ip.getProtDel());
		    XMLGregorianCalendar xmlcalendar = Utilities.getXMLGregorianCalendar(calendar);
		    attoType.setData(xmlcalendar);
		    attoType.setTipoAtto(ip.getTipoAtto());
		    attoType.setRilasciatoDa(ip.getRilasciatoDa());
		    attoType.setNote(ip.getNote());
		    procedimentoPrincipale.setEstremiAtto(attoType);
		}
	    }
	}
	return procedimentoPrincipale;
    }

    private RiferimentiPraticaType riferimentoPraticaDestinatarioDaRicercaPraticaCollegata(Integer codiceIstanza, Integer codiceMovimento,
	    Integer codiceAmministrazioneSTC) {

	RichiestaPraticaCollegataResponse response = this.richiestaPraticaCollegata(codiceIstanza, codiceMovimento, codiceAmministrazioneSTC);
	if (!response.getDettaglioErrore().isEmpty()) {
	    log.error("Errore: " + response.getDettaglioErrore().get(0).getNumeroErrore() + " --- " +
		      response.getDettaglioErrore().get(0).getDescrizione());
	    throw new RuntimeException("Errore: " + response.getDettaglioErrore().get(0).getNumeroErrore() + " --- " +
				       response.getDettaglioErrore().get(0).getDescrizione());
	}
	RiferimentiPraticaType rifPraticaDestinatario = new RiferimentiPraticaType();
	rifPraticaDestinatario.setIdPratica(response.getDettaglio().getDettaglioPratica().getIdPratica());
	rifPraticaDestinatario.setNumeroPratica(response.getDettaglio().getDettaglioPratica().getNumeroPratica());
	return rifPraticaDestinatario;
    }

    private boolean businessValidation(Integer codiceMovimento, Amministrazioni amministrazioniStc) {

	List<InvalidValue> ivs = new ArrayList<InvalidValue>(0);
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	if (StringUtils.isBlank(movimento.getNumeroprotocollo()) || movimento.getDataprotocollo() == null) {
	    Tipimovimento tipimovimento = movimento.getTipomovimento();
	    TipimovStcMapping mapping = tipimovStcMappingService.findByTipimovimentoAndAmministrazione(tipimovimento.getId().getTipomovimento(),
		    amministrazioniStc.getId().getCodice());
	    if (EntityUtils.getNestedProperty(mapping, "id.codice") != null && BooleanUtils.isTrue(mapping.getFlagProtocolla())) {
		ivs.add(new InvalidValue("stc.service_error.notifica_automatica_non_selezionata", null, null, "", null));
	    }
	}
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	return true;
    }

    protected boolean isNotificaCART(List<TipimovStcAltridatiValoreBean> altriDatiStc) {

	boolean isNotificaCART = false;
	if (altriDatiStc != null) {
	    for (TipimovStcAltridatiValoreBean ad : altriDatiStc) {
		if (ad.getChiave().getNomeCampo().equalsIgnoreCase(StcService.ALTRO_DATO_RISERVATO_TIPO_OPERAZIONE_CART)
			&& ad.getValore().equalsIgnoreCase(StcService.ALTRO_DATO_RISERVATO_TIPO_OPERAZIONE_CART_NOTIFICA)) {
		    isNotificaCART = true;
		    break;
		}
	    }
	}
	return isNotificaCART;
    }

    private Integer trovaMessaggioPresentazioneDomandaCART(List<IstanzeallegatiDTO> allegatiIstanza) {

	Integer codiceOggetto = null;
	if (allegatiIstanza != null) {
	    for (IstanzeallegatiDTO allegato : allegatiIstanza) {
		if (StringUtils.isNotEmpty(allegato.getNomeFile())
			&& allegato.getNomeFile().toUpperCase().endsWith(FACCTConstants.PRESENTAZIONE_DOMANDA_MODELLO_RIEPILOGO_SUFFIX)) {
		    codiceOggetto = allegato.getCodiceOggetto();
		    break;
		}
	    }
	}
	return codiceOggetto;
    }

    //@Override
    private RichiestaPraticaCollegataResponse richiestaPraticaCollegata(Integer codiceIstanza, Integer codiceMovimento,
	    Integer codiceAmministrazioneSTC) {

	// BOCCI 2011-11-08: quando richiedo la pratica collegata creata da PEOPLE o per i nodi che Fanno INSERIMENTO_DIRETTO 
	// non viene creato il movimento con flag_creato_dat_stc=true in questo
	String idProcedimento = "";
	SportelloType destinatario = null;
	if (codiceMovimento == null) {
	    List<Domandestc> domandestcs = domandestcService.findByIstanza(codiceIstanza);
	    Domandestc domandaStc = null;
	    if (domandestcs.size() > 0) {
		domandaStc = domandestcs.get(0);
		// DEVONO ESSERE CONFIGURATI I PARAMETRI DELL'AMMINISTRAZIONE DEL MOVIMENTO
		if (StringUtils.isBlank(domandaStc.getIdSportellomitt()) || StringUtils.isBlank(domandaStc.getIdEntemitt())
			|| StringUtils.isBlank(domandaStc.getIdNodo())) {
		    String errorMessage = "Attenzione! Non sono stati trovati i parametri della comunicazione STC ( DOMANDESTC ) per l'amministrazione mittente (rif: " +
					  domandaStc.getId() + ")";
		    log.error("richiestaPraticaCollegata: {}", errorMessage);
		    throw new RuntimeException(errorMessage);
		}
		destinatario = new SportelloType();
		destinatario.setIdEnte(domandaStc.getIdEntemitt());
		destinatario.setIdSportello(domandaStc.getIdSportellomitt());
		destinatario.setIdNodo(domandaStc.getIdNodo());
	    } else {
		throw new RuntimeException(
			"Errore nel recupero dell'allegato non sono stati trovati i riferimenti della pratica/sportello (DOMANDESTC) che ha creato la presente " +
					   "istanza");
	    }
	} else {
	    // ATTENZIONE!! HO USATO IL DAO PER NON INCAPPARE NELLA SECURITY
	    Movimenti mov = movimentiDAO.findById(new PkId(codiceMovimento));
	    // ATTENZIONE!! HO USATO IL DAO PER NON INCAPPARE NELLA SECURITY
	    Amministrazioni amm = null;
	    if (codiceAmministrazioneSTC == null) {
		amm = mov.getAmministrazioniStc();
	    } else {
		amm = amministrazioniService.findById(new PkId(codiceAmministrazioneSTC));
	    }
	    idProcedimento = decodeEndoProcedimento(mov.getEndoprocedimento());
	    //TODO quando sarà presente nella tabella movimenti il campo codiceProcedimentoSTC(settato durante la notifica) andrà recuperato
	    //questo invece di quello delle mappature
	    // gestione mappature (configurate nel tipo procedimento)
	    Set<TipimovStcMapping> mappings = mov.getTipomovimento().getTipimovStcMappings();
	    for (TipimovStcMapping tipimovStcMapping : mappings) {
		Amministrazioni ammTipiMov = tipimovStcMapping.getAmministrazioni();
		// se trovo codiceprocedimento lo sovrascrivo
		if (ammTipiMov.getId().getCodice().intValue() == amm.getId().getCodice().intValue()
			&& StringUtils.isNotBlank(tipimovStcMapping.getCodiceprocedimento())) {
		    idProcedimento = tipimovStcMapping.getCodiceprocedimento();
		    break;
		}
	    }
	    destinatario = getSportelloDestinatario(amm);
	}
	RichiestaPraticaCollegataRequest request = new RichiestaPraticaCollegataRequest();
	SportelloType mittente = getSportelloMittente();
	request.setSportelloMittente(mittente);
	request.setSportelloDestinatario(destinatario);
	request.setIdPraticaMitt(String.valueOf(codiceIstanza));
	request.setIdProcedimentoMitt(idProcedimento);
	RichiestaPraticaCollegataResponse risposta = stcWsClient.richiestaPraticaCollegata(request);
	return risposta;
    }

    @Override
    public RichiestaPraticaCollegataResponse richiestaPraticaCollegata(SportelloType mittente, SportelloType destinatario, Integer codiceIstanza,
	    String idProcedimento) {

	RichiestaPraticaCollegataRequest request = new RichiestaPraticaCollegataRequest();
	request.setSportelloMittente(mittente);
	request.setSportelloDestinatario(destinatario);
	request.setIdPraticaMitt(String.valueOf(codiceIstanza));
	if (StringUtils.isNotBlank(idProcedimento)) {
	    request.setIdProcedimentoMitt(idProcedimento);
	}
	return stcWsClient.richiestaPraticaCollegata(request);
    }

    @Override
    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaMittente(SportelloType sportello, Integer codiceMovimento) {

	if (sportello == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo richiestaPraticaCollegataDaAttivitaMittente senza passare lo sportello mittente di riferimento");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo richiestaPraticaCollegataDaAttivitaMittente senza passare il codiceMovimento di riferimento");
	}
	RichiestaPraticaCollegataDaAttivitaMittenteRequest request = new RichiestaPraticaCollegataDaAttivitaMittenteRequest();
	request.setSportello(sportello);
	request.setIdAttivita(String.valueOf(codiceMovimento));
	return this.stcWsClient.richiestaPraticaCollegataDaAttivitaMittente(request);
    }

    @Override
    public RichiestaPraticaCollegataResponse richiestaPraticaCollegataDaAttivitaDestinataria(SportelloType sportello, Integer codiceMovimento) {

	if (sportello == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo richiestaPraticaCollegataDaAttivitaDadestinataria senza passare lo sportello di riferimento");
	}
	if (codiceMovimento == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo richiestaPraticaCollegataDaAttivitaDadestinataria senza passare il codiceMovimento di riferimento");
	}
	RichiestaPraticaCollegataDaAttivitaDestinatariaRequest request = new RichiestaPraticaCollegataDaAttivitaDestinatariaRequest();
	request.setSportello(sportello);
	request.setIdAttivita(String.valueOf(codiceMovimento));
	return this.stcWsClient.richiestaPraticaCollegataDaAttivitaDestinataria(request);
    }

    @Override
    public AllegatoBinarioResponse allegatoBinario(Integer codiceIstanza, Integer codiceMovimento, String stcIddocumento, String stcIdallegato) {

	if (StringUtils.isBlank(stcIdallegato)) {
	    throw new RuntimeException("Non è stato specificato l'id allegato");
	}
	log.debug("allegatoBinario: accedo al metodo");
	// BOCCI 2011-11-08: quando richiedo un allegato creato da PEOPLE o per i nodi che Fanno INSERIMENTO_DIRETTO non viene creato il movimento con flag_creato_dat_stc=true.
	// Sicuramente sto facendo unaa richiesta o da documentiistanza o da istanzeallegati dove gli allegati vengono creati da STC 
	// solamente nel caso di inserimento pratica e quindi non ho a disposizione in movimento da cui recuperare 
	// l'amministrazione che ha inviato la richiesta di inserimento pratica e quindi fare la richiesta pratica collegata 
	// in questo caso invece di fare richiesta pratica collegata recupero le informazioni da DOMANDESTC
	SportelloType destinatario = null;
	String idPraticaDest = "";
	if (codiceMovimento == null) {
	    log.debug("allegatoBinario: codiceMovimento è nullo cerco tra le domandestc");
	    List<Domandestc> domandestcs = domandestcService.findByIstanza(codiceIstanza);
	    Domandestc domandaStc = null;
	    if (domandestcs.size() > 0) {
		domandaStc = domandestcs.get(0);
		// DEVONO ESSERE CONFIGURATI I PARAMETRI DELL'AMMINISTRAZIONE DEL MOVIMENTO
		if (StringUtils.isBlank(domandaStc.getIdSportellomitt()) || StringUtils.isBlank(domandaStc.getIdEntemitt())
			|| StringUtils.isBlank(domandaStc.getIdNodo())) {
		    String errorMessage = "Attenzione! Non sono stati trovati i parametri della comunicazione STC ( DOMANDESTC ) per l'amministrazione mittente (rif: " +
					  domandaStc.getId() + ")";
		    log.error("richiestaPraticaCollegata: {}", errorMessage);
		    throw new RuntimeException(errorMessage);
		}
		destinatario = new SportelloType();
		destinatario.setIdEnte(domandaStc.getIdEntemitt());
		destinatario.setIdSportello(domandaStc.getIdSportellomitt());
		destinatario.setIdNodo(domandaStc.getIdNodo());
		idPraticaDest = domandaStc.getIdDomandamitt();
		log.debug("allegatoBinario: riferimenti destinatario idNodo:{}, idEnte:{}, idSportello:{}, idPratica:{}",
			new Object[] { destinatario.getIdNodo(), destinatario.getIdEnte(), destinatario.getIdSportello(), idPraticaDest });
	    } else {
		throw new RuntimeException(
			"Errore nel recupero dell'allegato non sono stati trovati i riferimenti della pratica/sportello (DOMANDESTC) che ha creato la presente " +
					   "istanza");
	    }
	} else {
	    log.debug("allegatoBinario: Il movimento è stato fornito({}).risalgo alla richiesta pratica collegata", codiceMovimento);
	    RichiestaPraticaCollegataResponse praticaCollegata = richiestaPraticaCollegata(codiceIstanza, codiceMovimento, null);
	    if (!praticaCollegata.getDettaglioErrore().isEmpty()) {
		ErroreType errore = praticaCollegata.getDettaglioErrore().get(0);
		throw new RuntimeException(
			"Errore nel recupero della pratica collegata (" + errore.getNumeroErrore() + ": " + errore.getDescrizione() + ")");
	    }
	    destinatario = praticaCollegata.getDettaglio().getSportello();
	    idPraticaDest = praticaCollegata.getDettaglio().getDettaglioPratica().getIdPratica();
	    log.debug("allegatoBinario: riferimenti destinatario idNodo:{}, idEnte:{}, idSportello:{}, idPratica:{}",
		    new Object[] { destinatario.getIdNodo(), destinatario.getIdEnte(), destinatario.getIdSportello(), idPraticaDest });
	}
	AllegatoBinarioRequest request = new AllegatoBinarioRequest();
	request.setSportelloDestinatario(destinatario);
	SportelloType mittente = getSportelloMittente();
	request.setSportelloMittente(mittente);
	RiferimentiAllegatoType rifAllegato = new RiferimentiAllegatoType();
	rifAllegato.setIdPratica(idPraticaDest);
	rifAllegato.setIdDocumento(stcIddocumento);
	rifAllegato.setIdAllegato(stcIdallegato);
	request.setRiferimentiAllegato(rifAllegato);
	log.debug("allegatoBinario: verifico se i nodi mittente e destinatario appartengono alla stessa base dati");
	// BOCCI 2011-11-08 se sportello mittente e destinatario coincidono per idnodo ed idente allora l'allegato va ricercato localmente nella tabella OGGETTI
	boolean isFileLocale = nlaHelperService.isChiamataDaNodoInterno(destinatario, mittente, false);
	// trick per test MTOM se file locale è false allora viene fatta sempre la chiamata ad AllegatoBinario
	// isFileLocale = false;
	// END trick per test MTOM
	AllegatoBinarioResponse response = null;
	if (isFileLocale) {
	    log.debug("allegatoBinario: il file è locale alla base dati dell'applicativo lo recupero dalla tabella OGGETTI ({},{})",
		    ORMHelper.getIdcomune(), stcIdallegato);
	    Integer codiceOggetto = null;
	    try {
		codiceOggetto = Integer.parseInt(stcIdallegato);
	    } catch (Exception e) {
		String errorMessage = "Attenzione! Il riferimento all'allegato da scaricare non è corretto (rif: OGGETTI-" + stcIdallegato + ")";
		log.error("allegatoBinario: {}", errorMessage);
		throw new RuntimeException(errorMessage);
	    }
	    Oggetti file = oggettiService.findById(new PkId(codiceOggetto));
	    if (file == null) {
		String errorMessage = "Attenzione! Il riferimento all'allegato da scaricare non è corretto (rif: OGGETTI-" + stcIdallegato + ")";
		log.error("allegatoBinario: {}", errorMessage);
		throw new RuntimeException(errorMessage);
	    }
	    response = new AllegatoBinarioResponse();
	    response.setFileName(file.getNomefile());
	    DataHandler dh = Utilities.bytesToDataHandler(file.getOggetto());
	    response.setBinaryData(dh);
	    if (StringUtils.isNotBlank(response.getFileName())) {
		String mimetype = contenttypesService.findMimeTypeByFileName(file.getNomefile());
		response.setMimeType(mimetype);
	    }
	} else {
	    response = stcAllegatoBinario(request);
	    if (response != null) {
		// cerco il mime type se non è stato passato dal webservice all'oggetto response
		String mimetype = response.getMimeType();
		if (StringUtils.isBlank(mimetype)) {
		    if (StringUtils.isNotBlank(response.getFileName())) {
			mimetype = contenttypesService.findMimeTypeByFileName(response.getFileName());
			response.setMimeType(mimetype);
		    }
		}
	    }
	}
	return response;
    }

    private AllegatoBinarioResponse stcAllegatoBinario(AllegatoBinarioRequest request) {

	return stcWsClient.allegatoBinario(request);
    }

    @Override
    public RichiestaPraticaResponse richiestaPratica(Integer codiceMovimento, RiferimentiPraticaType riferimentiPraticaType) {

	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	Amministrazioni amministrazioni = movimento.getAmministrazioniStc();
	SportelloType destinatario = getSportelloDestinatario(amministrazioni);
	return this.richiestaPratica(destinatario, riferimentiPraticaType);
    }

    @Override
    public RichiestaPraticaResponse richiestaPratica(SportelloType sportelloDestinatario, RiferimentiPraticaType riferimentiPraticaType) {

	String token = stcWsClient.login();
	SportelloType mittente = getSportelloMittente();
	RichiestaPraticaRequest richiestaPraticaRequest = new RichiestaPraticaRequest();
	richiestaPraticaRequest.setToken(token);
	richiestaPraticaRequest.setSportelloMittente(mittente);
	richiestaPraticaRequest.setSportelloDestinatario(sportelloDestinatario);
	richiestaPraticaRequest.setRifPratica(riferimentiPraticaType);
	return stcWsClient.richiestaPratica(richiestaPraticaRequest);
    }

    @Override
    public boolean validateConfigurazione(Movimenti movimento) {

	List<InvalidValue> ivs = new ArrayList<InvalidValue>(0);
	if (movimento == null) {
	    ivs.add(new InvalidValue("stc.service_error.movimento_null", null, null, "", null));
	}
	Verticalizzazioni verticalizzazione = verticalizzazioniService.findByModulo(WebConstants.VERTICALIZZAZIONE_STC);
	if (verticalizzazione == null) {
	    ivs.add(new InvalidValue("stc.service_error.verticalizzazione_stc_non_attiva", null, null, "", null));
	} else {
	    if (verticalizzazione.getAttivo() == null || verticalizzazione.getAttivo().intValue() == 0) {
		ivs.add(new InvalidValue("stc.service_error.verticalizzazione_stc_non_attiva", null, null, "", null));
	    }
	}
	if (movimento != null) {
	    Tipimovimento tipimovimento = movimento.getTipomovimento();
	    Set<TipimovStcMapping> mappings = tipimovimento.getTipimovStcMappings();
	    if (mappings == null || mappings.size() == 0) {
		ivs.add(new InvalidValue("stc.service_error.nessun_parametro_mapping_per_movimento", null, null, "", null));
	    } else {
		Integer codiceAmministrazione = (Integer) EntityUtils.getNestedProperty(movimento.getAmministrazioniStc(), "id.codice");
		boolean mappingTrovato = false;
		if (codiceAmministrazione != null) {
		    for (TipimovStcMapping tipimovStcMapping : mappings) {
			Integer codiceAmmMapping = tipimovStcMapping.getAmministrazioni().getId().getCodice();
			if (codiceAmministrazione.intValue() == codiceAmmMapping.intValue()) {
			    mappingTrovato = true;
			    if (BooleanUtils.isTrue(tipimovStcMapping.getFlagProtocolla())) {
				if (StringUtils.isBlank(movimento.getNumeroprotocollo()) || movimento.getDataprotocollo() == null) {
				    ivs.add(new InvalidValue("stc.service_error.notifica_automatica_non_selezionata", null, null, "", null));
				}
			    }
			}
		    }
		    if (!mappingTrovato) {
			ivs.add(new InvalidValue("stc.service_error.nessun_parametro_mapping_per_movimento_amministrazione", null, null, "", null));
		    }
		} else {
		    ivs.add(new InvalidValue("stc.service_error.movimento_senza_amministrazione", null, null, "", null));
		}
	    }
	}
	List<InvalidValue> ivsDati = validateDatiPerInvio(movimento);
	if (ivsDati != null && !ivsDati.isEmpty()) {
	    ivs.addAll(ivsDati);
	}
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	return true;
    }

    @Override
    public boolean checkSeAbilitareRichiestaPraticaCollegata(Integer codiceIstanza) {

	boolean isAbilitato = false;
	try {
	    List<Domandestc> domandeStc = domandestcService.findByIstanza(codiceIstanza);
	    if (domandeStc != null && !domandeStc.isEmpty()) {
		Domandestc domandaStc = domandeStc.get(0);
		String idNodo = domandaStc.getIdNodo();
		if (verticalizzazioniService.isAttiva(WebConstants.VERTICALIZZAZIONE_STC)) {
		    List<Verticalizzazioniparametri> params = verticalizzazioniService
			    .getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC);
		    for (Verticalizzazioniparametri param : params) {
			if (param.getVerticalizzazioniparametribase().getId().getParametro().startsWith("NLA_IDNODO")
				&& idNodo.equals(param.getValore())) {
			    if (NodoNLAEnum.NLA_IDNODO.name().equals(param.getVerticalizzazioniparametribase().getId().getParametro())) {
				isAbilitato = true;
			    }
			    break;
			}
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("checkSeAbilitareRichiestaPraticaCollegata(codiceIstanza={}): {}", codiceIstanza, e.getMessage());
	}
	return isAbilitato;
    }

    @Override
    public String notificaAutomaticaAttivita(Integer codiceMovimento, Amministrazioni amministrazioniStc) throws STCNotificaAttivitaException {

	// §§§BEGIN§§§
	Movimenti movimento = movimentiService.findById(new PkId(codiceMovimento));
	if (movimento == null) {
	    log.error("notificaAutomaticaAttivita(codMov={}): Non è stato trovato nessun movimento con questo codice.", codiceMovimento);
	    throw new STCNotificaAttivitaException(
		    "notificaAutomaticaAttivita(codMov=" + codiceMovimento + "): Non è stato trovato nessun movimento con questo codice.");
	}
	// StcNotificaBean entity = new StcNotificaBean();
	// entity.setMovimento(movimento);
	String tipoMovimento = movimento.getTipomovimento().getId().getTipomovimento();
	Integer codiceAmministrazioneStc = amministrazioniStc.getId().getCodice();
	TipimovStcMapping mapping = tipimovStcMappingService.findByTipimovimentoAndAmministrazione(tipoMovimento, codiceAmministrazioneStc);
	if (mapping == null) {
	    throw new STCNotificaAttivitaException("Non è stata trovata la configurazione in TIPIMOV_STC_MAPPING per il tipomovimento " +
						   movimento.getTipomovimento().getId().getTipomovimento() + " e l'amministrazione " +
						   amministrazioniStc.getId().getCodice());
	}
	List<InvalidValue> ivs = validateDatiPerInvio(movimento);
	if (ivs != null && !ivs.isEmpty()) {
	    StringBuilder messaggioDiErrore = new StringBuilder();
	    messaggioDiErrore.append("\n Valori non validi: [");
	    for (InvalidValue invalidValue : ivs) {
		messaggioDiErrore.append("\n");
		messaggioDiErrore.append(getMessageFromBundle(invalidValue.getMessage(), new Object[] { invalidValue.getValue() }));
	    }
	    messaggioDiErrore.append("\n]");
	    throw new STCNotificaAttivitaException(messaggioDiErrore.toString());
	}
	List<TipimovStcAltridatiValoreBean> altriDatiList = new ArrayList<TipimovStcAltridatiValoreBean>();
	List<TipimovStcAltridati> datis = tipimovStcAltridatiService.findByTipimovimentoAndAmministrazione(tipoMovimento, codiceAmministrazioneStc);
	for (TipimovStcAltridati tipimovStcAltridati : datis) {
	    TipimovStcAltridatiValoreBean tsavb = new TipimovStcAltridatiValoreBean();
	    tsavb.setChiave(tipimovStcAltridati);
	    String valore = new SegnapostiMovimentoSTCBuilder(movimento, amministrazioniCollegateService,
		    movimento.getIstanza().getComune().getCodicecomune()).sostituisciSegnaposto(tipimovStcAltridati.getValoreDefaultCampo());
	    tsavb.setValore(StringUtils.defaultIfEmpty(valore, ""));
	    altriDatiList.add(tsavb);
	}
	List<Dyn2ModellitValoreBean> modelliList = findListaIstanzeModelli(movimento, amministrazioniStc);
	List<IstanzeallegatiDTO> allegatiEndo = new ArrayList<IstanzeallegatiDTO>();
	if (!EntityUtils.isNestedPropertyBlank(movimento.getEndoprocedimento(), "id.codice")) {
	    Integer codiceInventario = movimento.getEndoprocedimento().getId().getCodice();
	    if (codiceInventario != null) {
		allegatiEndo = istanzeallegatiService.findIstanzeallegatiDTOByIstanzaAndEndo(movimento.getIstanza().getId().getCodice(),
			codiceInventario, TipoRicercaDocumentoEnum.RICERCA_TUTTI);
	    }
	}
	boolean inviaDocumentiIstanza = mapping.getFlagAllegaDocumentiIstanza() == null ? false
		: mapping.getFlagAllegaDocumentiIstanza().booleanValue();
	List<DocumentiistanzaDTO> docis = new ArrayList<DocumentiistanzaDTO>();
	if (inviaDocumentiIstanza) {
	    docis = documentiistanzaService.findDocumentiistanzaDTOByIstanza(movimento.getIstanza().getId().getCodice(), true);
	    List<DocumentiistanzaDTO> docis2 = documentiistanzaService.findDocumentiistanzaDTOByIstanza(movimento.getIstanza().getId().getCodice(),
		    false);
	    docis.addAll(docis2);
	}
	boolean creaInviaAllegati = mapping.getFlagCreainviaAllegati() == null ? false : mapping.getFlagCreainviaAllegati().booleanValue();
	List<MovimentiallegatiDTO> movall = new ArrayList<MovimentiallegatiDTO>();
	if (creaInviaAllegati) {
	    if (log.isDebugEnabled()) {
		log.debug("notificaAutomaticaAttivita# recupero la lista degli allegati per il movimento {}:", codiceMovimento);
	    }
	    movall = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(codiceMovimento);
	    if (log.isDebugEnabled()) {
		log.debug("notificaAutomaticaAttivita# la lista contiene elementi {}:", movall.isEmpty());
	    }
	}
	//Lista dei documenti del movimento passato o del movimento padre
	String codiceTipoMovPerRecuperoAllegati = mapping.getCodiceMovRecuperoAllegato();
	if (StringUtils.isNotBlank(codiceTipoMovPerRecuperoAllegati)) {
	    List<MovimentiallegatiDTO> movimentiAllegatis = movimentiallegatiService.findMovimentiallegatiDTOByMovimenti(
		    movimento.getIstanza().getId().getCodice(), movimento.getId().getCodice(), codiceTipoMovPerRecuperoAllegati);
	    movall.addAll(movimentiAllegatis);
	}
	//Lista dei documenti delle procure
	List<IstanzeprocureDTO> documentiProcures = new ArrayList<IstanzeprocureDTO>();
	if (inviaDocumentiIstanza) {
	    documentiProcures = istanzeprocureService.findIstanzeprocureDTOByIstanza(movimento.getIstanza().getId().getCodice(),
		    TipoRicercaDocumentoEnum.RICERCA_CON_OGGETTO);
	}
	RiferimentiPraticaType rifPraticaDestinatario = null;
	boolean requirePraticaDestinatario = mapping.getFlagRifpratStorica() == null ? false : mapping.getFlagRifpratStorica().booleanValue();
	String idAttDest = "";
	boolean isNonInviaEndo = mapping.getNonInviareProcedimenti() == null ? false : mapping.getFlagCreainviaAllegati().booleanValue();
	try {
	    if (log.isDebugEnabled()) {
		String debugMessage = "Movimento " + codiceMovimento + ", codiceAmministrazioneStc " + codiceAmministrazioneStc;
		log.debug("notificaAutomaticaAttivita# prima di chiamare la notifica automatica per {}", new Object[] { debugMessage });
	    }
	    idAttDest = notificaAttivita(codiceMovimento, altriDatiList, modelliList, allegatiEndo, docis, movall, null, documentiProcures,
		    rifPraticaDestinatario, requirePraticaDestinatario, isNonInviaEndo, amministrazioniStc, null);
	} catch (Exception e) {
	    log.error("notificaAutomaticaAttivita(codMov={}): {}", codiceMovimento, e.getMessage());
	    throw new STCNotificaAttivitaException(e.getMessage());
	}
	return idAttDest;
    }

    @Override
    public void insertAllegatoInLocale(Integer codiceIstanza, Integer codiceMovimento, String stcIddocumento, String stcIdallegato, String contesto,
	    Integer codice) {

	log.debug("INIZIO OPERAZIONE DI BACKUP  OGGETTI STC PER L'ISTANZA: {}", codiceIstanza);
	// L'inserimento viene fatto solo per un allegato di un determinato contesto (ENDO,DOCUMENTI,MOVIMENTI) 
	if (!contesto.equals(WebConstants.CONTESTO_TUTTI)) {
	    log.debug("Salvo gli allegati provenienti da STC per il contesto: ", contesto);
	    log.debug("Recupero l'oggetto da STC");
	    try {
		Istanze istanze = istanzeService.findById(new PkId(codiceIstanza));
		AllegatoBinarioResponse allegatoBinarioResponse = this.allegatoBinario(codiceIstanza, codiceMovimento, stcIddocumento, stcIdallegato);
		String fn = allegatoBinarioResponse.getFileName();
		if (fn == null) {
		    log.error("L'allegato recuperato è senza nome: [codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			    new Object[] { codiceMovimento, stcIddocumento, stcIdallegato });
		    popoluteAndInsertEvento("L'allegato dell'istanza " + istanze.getNumeroistanza() + " recuperato è senza nome", codiceIstanza,
			    codiceMovimento);
		}
		if (allegatoBinarioResponse.getMimeType() == null) {
		    log.error("L'allegato recuperato non ha nessun mime-type: [codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			    new Object[] { codiceMovimento, stcIddocumento, stcIdallegato });
		    popoluteAndInsertEvento("L'allegato dell'istanza " + istanze.getNumeroistanza() + " non ha nessun mime-type", codiceIstanza,
			    codiceMovimento);
		}
		byte[] data = Utilities.dataHandlerToBytes(allegatoBinarioResponse.getBinaryData());
		if (data == null) {
		    log.error("L'allegato recuperato è vuoto: [codiceIstanza={}, codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			    new Object[] { codiceIstanza, codiceMovimento, stcIddocumento, stcIdallegato });
		    popoluteAndInsertEvento(
			    "L'allegato dell'istanza " + istanze.getNumeroistanza() + " con nomefile=\"" + allegatoBinarioResponse.getFileName() +
					    "\" [" + contesto + ", id: " + codice + "]  è vuoto e non è stato aggiornato",
			    codiceIstanza, codiceMovimento);
		    FlashMessages.getWarnings().add("L'allegato [" + contesto + "] [id:" + codice +
						    "] ha il contenuto del file vuoto e non è stato aggiornato. Controllare gli eventi dell'istanza");
		    // throw new Exception("L'allegato recuperato è vuoto");
		} else {
		    log.debug("Oggetto recuperato");
		    // Creo a partire da allegato binario l'ogetto "OGGETTI" , non setto la dimensione;
		    // Devo legare l'ogetto inserito al riferimento della tabella di provenienza.
		    if (contesto.equals(WebConstants.CONTESTO_ALLEGATI_DOCUMENTI)) {
			Documentiistanza documentiistanza = documentiistanzaService.findById(new PkId(codice));
			log.debug("Aggiorno il record di documenti istanza con il codice: {}", documentiistanza.getId().getCodice());
			aggiornaDocumentiistanza(documentiistanza, data, allegatoBinarioResponse.getFileName());
		    }
		    if (contesto.equals(WebConstants.CONTESTO_ALLEGATI_MOVIMENTO)) {
			Movimentiallegati movimentiallegati = movimentiallegatiService.findById(new PkId(codice));
			log.debug("Aggiorno il record di allegati movimento con il codice: {}", movimentiallegati.getId().getCodice());
			aggiornaMovimentoallegato(movimentiallegati, data, allegatoBinarioResponse.getFileName());
		    }
		    if (contesto.equals(WebConstants.CONTESTO_ALLEGATI_ENDO)) {
			Istanzeallegati istanzeallegati = istanzeallegatiService.findById(new PkId(codice));
			log.debug("Aggiorno il record di allegati endo con il codice: {}", istanzeallegati.getId().getCodice());
			aggiornaIstanzaallegato(istanzeallegati, data, allegatoBinarioResponse.getFileName());
		    }
		    if (contesto.equals(WebConstants.CONTESTO_ALLEGATI_PROCURE)) {
			Istanzeprocure istanzeprocure = istanzeprocureService.findById(new PkId(codice));
			log.debug("Aggiorno il record di istanzeprocure con il codice: {}", istanzeprocure.getId().getCodice());
			aggiornaIstanzaprocura(istanzeprocure, data, allegatoBinarioResponse.getFileName());
		    }
		}
	    } catch (Exception e) {
		log.error("insertAllegatoInLocale(): {}", e.getMessage());
		popoluteAndInsertEvento(e.getMessage(), codiceIstanza, codiceMovimento);
		throw new RuntimeException(e.getMessage());
	    }
	    log.debug("FINE OPERAZIONE DI BACKUP DEGLI OGGETTI STC PER L'ISTANZA: {}", codiceIstanza);
	} else //L'inserimento viene fatto per tutti gli allegati che provengono da stc (stcIdallegato non vuoto)  e per  tutti i contesti 
	{
	    log.debug("Salvo gli allegati provenienti da STC per tutti i contesti");
	    // Variabile per recuperare ogni singolo allegtao da salvare
	    AllegatoBinarioResponse allegatoBinarioResponse = null;
	    // Recupero la lista dei documenti istanza provenienti da stc
	    List<Documentiistanza> listDocumentiIstanza = documentiistanzaService.findProvenientiDaSTC(codiceIstanza);
	    log.debug("Salvo gli allegati provenienti da STC per  il contesto documenti istanza");
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    String descrizioneIstanza = istanza.toString();
	    for (Documentiistanza documentiistanza : listDocumentiIstanza) {
		allegatoBinarioResponse = this.allegatoBinario(codiceIstanza, codiceMovimento, documentiistanza.getStcIddocumento(),
			documentiistanza.getStcIdallegato());
		byte[] data = Utilities.dataHandlerToBytes(allegatoBinarioResponse.getBinaryData());
		if (data == null) {
		    log.error("L'allegato recuperato è vuoto: [codiceIstanza={}, codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			    new Object[] { codiceIstanza, codiceMovimento, stcIddocumento, stcIdallegato });
		    popoluteAndInsertEvento("L'allegato [DOCUMENTIISTANZA] dell'istanza " + descrizioneIstanza + " con nomefile=\"" +
					    allegatoBinarioResponse.getFileName() + "\" è vuoto",
			    codiceIstanza, codiceMovimento);
		    FlashMessages.getWarnings()
			    .add("L'allegato [DOCUMENTIISTANZA] [" + documentiistanza.getDocumento() + ", id:" + documentiistanza.getId() +
				 "] ha il contenuto del file vuoto e non sarà aggiornato. Controllare gli eventi dell'istanza");
		    continue;
		}
		this.aggiornaDocumentiistanza(documentiistanza, data, allegatoBinarioResponse.getFileName());
		// Utilizzato il metodo flush e commit di istanzeDAO in quanto i metodi erano implementati
		istanzeDAO.commit();
		istanzeDAO.flush();
		istanzeDAO.clear();
		log.debug("Salvato l' allegati provenienti da STC con id della TABELLA DOCUMENTIISTANZA: {}", documentiistanza.getId().getCodice());
	    }
	    // Recupero la lista degli allegati del movimento provenienti da stc		
	    List<Movimentiallegati> listMovimentiallegati = movimentiallegatiService.findProvenientiDaSTC(codiceIstanza);
	    log.debug("Salvo gli allegati provenienti da STC per  il contesto movimenti");
	    for (Movimentiallegati movimentiallegati : listMovimentiallegati) {
		allegatoBinarioResponse = this.allegatoBinario(codiceIstanza, movimentiallegati.getMovimento().getId().getCodice(),
			movimentiallegati.getStcIddocumento(), movimentiallegati.getStcIdallegato());
		byte[] data = Utilities.dataHandlerToBytes(allegatoBinarioResponse.getBinaryData());
		if (data == null) {
		    log.error("L'allegato recuperato è vuoto: [codiceIstanza={}, codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			    new Object[] { codiceIstanza, codiceMovimento, stcIddocumento, stcIdallegato });
		    popoluteAndInsertEvento("L'allegato [ALLEGATIMOVIMENTO] del MOVIMENTO " + codiceMovimento + " con nomefile=\"" +
					    allegatoBinarioResponse.getFileName() + "\" è vuoto",
			    codiceIstanza, codiceMovimento);
		    FlashMessages.getWarnings()
			    .add("L'allegato [ALLEGATIMOVIMENTO] [" + movimentiallegati.getDescrizione() + ", id:" + movimentiallegati.getId() +
				 "] ha il contenuto del file vuoto e non sarà aggiornato. Controllare gli eventi dell'istanza");
		    continue;
		}
		aggiornaMovimentoallegato(movimentiallegati, data, allegatoBinarioResponse.getFileName());
		// Utilizzato il metodo flush e commit di istanzeDAO in quanto i metodi erano implementati
		istanzeDAO.commit();
		istanzeDAO.flush();
		istanzeDAO.clear();
		log.debug("Salvato l' allegato provenienti da STC con id della TABELLA MOVIMENTIALLEGATI: {}", movimentiallegati.getId().getCodice());
	    }
	    // Recupero la lista degli allegati dell'istanza (endo) provenienti da stc
	    List<Istanzeallegati> lististanzeallegati = istanzeallegatiService.findProvenientiDaSTC(codiceIstanza);
	    log.debug("Salvo gli allegati provenienti da STC per  il contesto endo");
	    for (Istanzeallegati istanzeallegati : lististanzeallegati) {
		allegatoBinarioResponse = this.allegatoBinario(codiceIstanza, codiceMovimento, istanzeallegati.getStcIddocumento(),
			istanzeallegati.getStcIdallegato());
		byte[] data = Utilities.dataHandlerToBytes(allegatoBinarioResponse.getBinaryData());
		if (data == null) {
		    log.error("L'allegato recuperato è vuoto: [codiceIstanza={}, codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			    new Object[] { codiceIstanza, codiceMovimento, stcIddocumento, stcIdallegato });
		    popoluteAndInsertEvento("L'allegato [ALLEGATIENDO]  dell'istanza " + descrizioneIstanza + " con nomefile=\"" +
					    allegatoBinarioResponse.getFileName() + "\" è vuoto",
			    codiceIstanza, codiceMovimento);
		    FlashMessages.getWarnings()
			    .add("L'allegato [ALLEGATIENDO] [" + istanzeallegati.getAllegatoextra() + ", id:" + istanzeallegati.getId() +
				 "] ha il contenuto del file vuoto e non sarà aggiornato. Controllare gli eventi dell'istanza");
		    continue;
		}
		aggiornaIstanzaallegato(istanzeallegati, data, allegatoBinarioResponse.getFileName());
		// Utilizzato il metodo flush e commit di istanzeDAO in quanto i metodi erano implementati
		istanzeDAO.commit();
		istanzeDAO.flush();
		istanzeDAO.clear();
		log.debug("Salvato l' allegato provenienti da STC con id della TABELLA ISTANZEALLEGATI: {}", istanzeallegati.getId().getCodice());
	    }
	    // Recupero la lista degli allegati delle procure dell'istanza provenienti da stc
	    List<Istanzeprocure> lististanzeprocure = istanzeprocureService.findProvenientiDaSTC(codiceIstanza);
	    log.debug("Salvo gli allegati provenienti da STC per  il contesto ISTANZEPROCURE");
	    for (Istanzeprocure istanzeprocure : lististanzeprocure) {
		allegatoBinarioResponse = this.allegatoBinario(codiceIstanza, codiceMovimento, istanzeprocure.getStcIdDocumento(),
			istanzeprocure.getStcIdAllegato());
		byte[] data = Utilities.dataHandlerToBytes(allegatoBinarioResponse.getBinaryData());
		if (data == null) {
		    log.error("L'allegato recuperato è vuoto: [codiceIstanza={}, codiceMovimento={}, stcIddocumento={}, stcIdallegato={}]",
			    new Object[] { codiceIstanza, codiceMovimento, stcIddocumento, stcIdallegato });
		    popoluteAndInsertEvento("L'allegato [ISTANZEPROCURE] dell'istanza " + descrizioneIstanza + " con nomefile=\"" +
					    allegatoBinarioResponse.getFileName() + "\" è vuoto",
			    codiceIstanza, codiceMovimento);
		    FlashMessages.getWarnings().add("L'allegato [ISTANZEPROCURE] [id:" + istanzeprocure.getId() +
						    "] ha il contenuto del file vuoto e non sarà aggiornato. Controllare gli eventi dell'istanza");
		    continue;
		}
		aggiornaIstanzaprocura(istanzeprocure, data, allegatoBinarioResponse.getFileName());
		// Utilizzato il metodo flush e commit di istanzeDAO in quanto i metodi erano implementati
		istanzeDAO.commit();
		istanzeDAO.flush();
		istanzeDAO.clear();
		log.debug("Salvato l' allegato provenienti da STC con id della TABELLA ISTANZEPROCURE: {}", istanzeprocure.getId().getCodice());
	    }
	    log.debug("FINE OPERAZIONE DI BACKUP DEGLI OGGETTI STC PER L'ISTANZA: {}", codiceIstanza);
	}
    }

    private void popoluteAndInsertEvento(String evento, Integer codiceIstanza, Integer codicemovimento) {

	// Creo un oggetto istanze Eventi che permette di memorizzare su db l'eventuale errore che non ha permesso di inserire un 
	// allegato
	Istanzeeventi istanzeeventi = new Istanzeeventi();
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	Date data = new Date();
	Categorieeventibase categorieeventibase = categorieeventibaseService.findById("");
	istanzeeventi.setCategorieeventibase(categorieeventibase);
	istanzeeventi.setData(data);
	istanzeeventi.setIstanze(istanza);
	istanzeeventi.setDescrizione(evento);
	if (codicemovimento != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codicemovimento));
	    istanzeeventi.setMovimenti(movimento);
	}
	istanzeeventiService.insert(istanzeeventi);
    }

    // Popolo l'oggetto e lo inserisce su BD
    private Oggetti populateOggetto(String nomefile, byte[] content) {

	Oggetti oggetto = new Oggetti();
	oggetto.setNomefile(nomefile);
	oggetto.setOggetto(content);
	oggettiService.insert(oggetto);
	return oggetto;
    }

    /**
     * 
     * <pre>
     * // devo mettere un ulteriore controllo per tutti e tre i metodi nel caso in cui :
     * // 1- Utente A esegue backup dell'oggetto
     * // 2- Utente B entra nella pagina dove è possibile fare il backup dell'ogetto e A non ha terminato l'operazione
     * // 3- Utente B non effettuta nessuna operazione di backup
     * // 4- Al termine dell'operazione di Backup, fino a quando l'utente B non fa il refresh della pagina vede ancora
     * // possibile il backup
     * // 5- Implemento un ulteriore controllo che non effettua l'operazioen di insert, in quanto già è stata fatta e sarà visibile
     *     all'operatore al ritorno nella pagina
     * </pre>
     */
    // Crea il collegamento tra l'oggetto inserito tramite la tabella di riferimento tra oggetti e documenti dell'istanza
    private void aggiornaDocumentiistanza(Documentiistanza documentiistanza, byte[] data, String nomefile) {

	Documentiistanza documentiistanzaControll = documentiistanzaService.findById(new PkId(documentiistanza.getId().getCodice()));
	if (EntityUtils.isNestedPropertyBlank(documentiistanzaControll.getOggetto(), "id.codice")) {
	    Oggetti oggetto = populateOggetto(nomefile, data);
	    documentiistanza.setOggetto(oggetto);
	    documentiistanzaService.update(documentiistanza);
	}
    }

    // Crea il collegamento tra l'oggetto inserito tramite la tabella di riferimento tra oggetti e allegtai movimenti
    private void aggiornaMovimentoallegato(Movimentiallegati movimentiallegati, byte[] data, String nomefile) {

	Movimentiallegati movimentiallegatiControll = movimentiallegatiService.findById(new PkId(movimentiallegati.getId().getCodice()));
	if (EntityUtils.isNestedPropertyBlank(movimentiallegatiControll.getOggetto(), "id.codice")) {
	    Oggetti oggetto = populateOggetto(nomefile, data);
	    movimentiallegati.setOggetto(oggetto);
	    movimentiallegatiService.update(movimentiallegati);
	}
    }

    // Crea il collegamento tra l'oggetto inserito tramite la tabella di riferimento tra oggetti e istanza allegati (allegati dell'endo)
    private void aggiornaIstanzaallegato(Istanzeallegati istanzeallegati, byte[] data, String nomefile) {

	Istanzeallegati istanzeallegatiControll = istanzeallegatiService.findById(new PkId(istanzeallegati.getId().getCodice()));
	if (EntityUtils.isNestedPropertyBlank(istanzeallegatiControll.getOggetto(), "id.codice")) {
	    Oggetti oggetto = populateOggetto(nomefile, data);
	    istanzeallegati.setOggetto(oggetto);
	    istanzeallegatiService.update(istanzeallegati);
	}
    }

    private void aggiornaIstanzaprocura(Istanzeprocure istanzeprocure, byte[] data, String nomefile) {

	Istanzeprocure istanzeprocureControll = istanzeprocureService.findById(new PkId(istanzeprocure.getId().getCodice()));
	if (EntityUtils.isNestedPropertyBlank(istanzeprocureControll.getOggetti(), "id.codice")) {
	    Oggetti oggetto = populateOggetto(nomefile, data);
	    istanzeprocure.setOggetti(oggetto);
	    istanzeprocureService.update(istanzeprocure);
	}
    }

    @Override
    public boolean isAllegatiStcExist(Integer codiceIstanza) {

	boolean exsist = istanzeallegatiService.isExistAllegatiProvenientiDaSTC(codiceIstanza);
	if (exsist) {
	    return true;
	}
	exsist = movimentiallegatiService.isExistAllegatiProvenientiDaSTC(codiceIstanza);
	if (exsist) {
	    return true;
	}
	exsist = documentiistanzaService.isExistAllegatiProvenientiDaSTC(codiceIstanza);
	return exsist;
    }

    public List<Dyn2ModellitValoreBean> findListaIstanzeModelli(Movimenti movimento, Amministrazioni amministrazioniSTC)
	    throws STCNotificaAttivitaException {

	// Se passo l' amministrazione STC allora utilizzo quella passta altrimenti la recupero dal movimento
	Amministrazioni amm = null;
	if (amministrazioniSTC == null) {
	    amm = movimento.getAmministrazioniStc();
	} else {
	    amm = amministrazioniSTC;
	}
	List<Dyn2ModellitValoreBean> modelliList = new ArrayList<Dyn2ModellitValoreBean>();
	Map<Integer, Dyn2ModellitValoreBean> modelliPresenti = new HashMap<Integer, Dyn2ModellitValoreBean>();
	// Recupero il valore che controlla se per il movimento che crea la notifica ha impostato a true il TipimovStcMapping.flagInviaschedeistanza
	TipimovStcMapping tipimovStcMapping = tipimovStcMappingService
		.findByTipimovimentoAndAmministrazione(movimento.getTipomovimento().getId().getTipomovimento(), amm.getId().getCodice());
	if (tipimovStcMapping == null) {
	    throw new STCNotificaAttivitaException("Non è stata trovata la configurazione in TIPIMOV_STC_MAPPING per il tipomovimento " +
						   movimento.getTipomovimento().getId().getTipomovimento() + " e l'amministrazione " +
						   movimento.getAmministrazioniStc().getId().getCodice());
	}
	// 1- FlagInviaschedeistanza: true CASO 1 - Tutte le schede sono scelte come da inviare (Vedi descrizione metodo)
	// 2- FlagInviaschedeistanza: true CASO 2 - Le schede da inviare sono solo quelle configurate nel tipo movimento (Vedi descrizione metodo)
	if (tipimovStcMapping.getFlagInviaschedeistanza() != null && tipimovStcMapping.getFlagInviaschedeistanza()) {
	    if (movimento != null && movimento.getIstanza() != null) {
		Istanze istanza = movimento.getIstanza();
		Set<Istanzedyn2modellit> istanzedyn2modellits = istanza.getIstanzedyn2modellit();
		for (Istanzedyn2modellit istanzedyn2modellit : istanzedyn2modellits) {
		    Dyn2ModellitValoreBean bean = new Dyn2ModellitValoreBean();
		    bean.setChiave(istanzedyn2modellit.getDyn2Modellit());
		    bean.setValore(Boolean.TRUE);
		    modelliPresenti.put(istanzedyn2modellit.getDyn2Modellit().getId().getCodice(), bean);
		}
	    }
	} else {
	    // variabile che serve a popolare la lista solamente se è stato configurato nel movimento almeno una scheda
	    if (modelliList.isEmpty()) {
		if (movimento != null && movimento.getIstanza() != null) {
		    Istanze istanza = movimento.getIstanza();
		    Set<Istanzedyn2modellit> istanzedyn2modellits = istanza.getIstanzedyn2modellit();
		    for (Istanzedyn2modellit istanzedyn2modellit : istanzedyn2modellits) {
			Dyn2ModellitValoreBean bean = new Dyn2ModellitValoreBean();
			bean.setChiave(istanzedyn2modellit.getDyn2Modellit());
			bean.setValore(Boolean.FALSE);
			modelliPresenti.put(istanzedyn2modellit.getDyn2Modellit().getId().getCodice(), bean);
		    }
		}
		// Per ogni schede recuperata dall'istanza controllo se è configurata nel mapping del tipo movimento che genera la notifica
		// quelle che sono configurate verranno poste con valore = true 
		if (movimento.getTipomovimento() != null && amm != null) {
		    Integer codiceAmministrazione = amm.getId().getCodice();
		    Set<TipimovStcModelli> modellis = movimento.getTipomovimento().getTipimovStcModellis();
		    for (TipimovStcModelli tipimovStcModellis : modellis) {
			Integer codiceAmm2 = tipimovStcModellis.getAmministrazioni().getId().getCodice();
			// solo se è configurato per l'amministrazione
			// solo se già inserito come scheda per la pratica
			// infatti può essere nella configurazione del movimento ma ancora non
			// inserito nella pratica, ovvero non presente nella tabella
			// ISTANZEDYN2MODELLIT. se presente lo verifico dalla mappa che contiene i modelli
			// istanziati
			// per quella istanza. La mappa mi serve per sovrascrivere un modello configurato e
			// metterlo come selezionato di default
			if (codiceAmministrazione.intValue() == codiceAmm2.intValue()
				&& modelliPresenti.get(tipimovStcModellis.getDyn2Modellit().getId().getCodice()) != null) {
			    Dyn2ModellitValoreBean bean = new Dyn2ModellitValoreBean();
			    bean.setChiave(tipimovStcModellis.getDyn2Modellit());
			    bean.setValore(Boolean.TRUE);
			    modelliPresenti.put(tipimovStcModellis.getDyn2Modellit().getId().getCodice(), bean);
			}
		    }
		}
	    }
	}
	if (!modelliPresenti.isEmpty()) {
	    int index = 0;
	    for (Map.Entry<Integer, Dyn2ModellitValoreBean> entry : modelliPresenti.entrySet()) {
		modelliList.add(index, entry.getValue());
		index++;
	    }
	}
	return modelliList;
    }

    public List<ChiaveValoreBean<String, List<Movimentiallegati>>> findListaMovimentiAllegati(Istanze istanza, Movimenti movimento)
	    throws STCNotificaAttivitaException {

	List<ChiaveValoreBean<String, List<Movimentiallegati>>> list = new ArrayList<ChiaveValoreBean<String, List<Movimentiallegati>>>();
	TipimovStcMapping tipimovStcMapping = tipimovStcMappingService.findByTipimovimentoAndAmministrazione(
		movimento.getTipomovimento().getId().getTipomovimento(), movimento.getAmministrazioniStc().getId().getCodice());
	if (tipimovStcMapping == null) {
	    throw new STCNotificaAttivitaException("Non è stata trovata la configurazione in TIPIMOV_STC_MAPPING per il tipomovimento " +
						   movimento.getTipomovimento().getId().getTipomovimento() + " e l'amministrazione " +
						   movimento.getAmministrazioniStc().getId().getCodice());
	}
	List<Movimenti> movimentis = movimentiService.findEseguitiByIstanza(istanza);
	for (Movimenti movimenti : movimentis) {
	    List<Movimentiallegati> movalls = movimentiallegatiService.findByIstanzaOggetto(istanza.getId().getCodice(),
		    movimenti.getId().getCodice());
	    if (!movalls.isEmpty()) {
		ChiaveValoreBean<String, List<Movimentiallegati>> bean = new ChiaveValoreBean<String, List<Movimentiallegati>>();
		List<Movimentiallegati> movalls2 = new ArrayList<Movimentiallegati>();
		// Controlla tutti gli allegati dei movimenti associati all'istanza ogni allegtao che 
		// appartiene al movimento che ha generato la notifica lo mette a true.
		for (Movimentiallegati movimentiallegati : movalls) {
		    if (movimento.getId().getCodice().equals(movimentiallegati.getMovimento().getId().getCodice())) {
			movimentiallegati.setTransientSegnaPerInvio(true);
		    }
		    movalls2.add(movimentiallegati);
		}
		String descrizioneMovimento = "<b>" + movimenti.getTipomovimento().getDescrizioneEstesa() + "</b>";
		if (movimenti.getAmministrazioni() != null) {
		    descrizioneMovimento += " - " + movimenti.getAmministrazioni().getAmministrazione();
		}
		if (movimenti.getEndoprocedimento() != null) {
		    descrizioneMovimento += " [" + movimenti.getEndoprocedimento().getProcedimento() + "]";
		}
		bean.setChiave(descrizioneMovimento);
		bean.setValore(movalls2);
		list.add(bean);
	    }
	}
	return list;
    }

    public List<ChiaveValoreBean<String, List<Istanzeallegati>>> findListaIstanzeallegati(Istanze istanza, Movimenti movimenti)
	    throws STCNotificaAttivitaException {

	List<ChiaveValoreBean<String, List<Istanzeallegati>>> list = new ArrayList<ChiaveValoreBean<String, List<Istanzeallegati>>>();
	List<Istanzeprocedimenti> ips = istanzeprocedimentiService.findByIstanze(istanza);
	TipimovStcMapping tipimovStcMapping = tipimovStcMappingService.findByTipimovimentoAndAmministrazione(
		movimenti.getTipomovimento().getId().getTipomovimento(), movimenti.getAmministrazioniStc().getId().getCodice());
	if (tipimovStcMapping == null) {
	    throw new STCNotificaAttivitaException("Non è stata trovata la configurazione in TIPIMOV_STC_MAPPING per il tipomovimento " +
						   movimenti.getTipomovimento().getId().getTipomovimento() + " e l'amministrazione " +
						   movimenti.getAmministrazioniStc().getId().getCodice());
	}
	boolean inviaAllegatiEndo = tipimovStcMapping.getFlagAllegaDocumentiEndo() == null ? Boolean.FALSE
		: tipimovStcMapping.getFlagAllegaDocumentiEndo().booleanValue();
	for (Istanzeprocedimenti procedimento : ips) {
	    List<Istanzeallegati> ialls = istanzeallegatiService.findByIstanzaAndEndo(istanza.getId().getCodice(),
		    procedimento.getId().getCodiceinventario());
	    if (!ialls.isEmpty()) {
		boolean almenoUno = false;
		List<Istanzeallegati> ialls2 = new ArrayList<Istanzeallegati>();
		for (Istanzeallegati istanzeallegati : ialls) {
		    if (EntityUtils.getNestedProperty(istanzeallegati.getOggetto(), "id.codice") != null) {
			ialls2.add(istanzeallegati);
			if (EntityUtils.getNestedProperty(movimenti.getEndoprocedimento(), "id.codice") != null && movimenti.getEndoprocedimento()
				.getId().getCodice().equals(istanzeallegati.getInventarioprocedimenti().getId().getCodice())) {
			    istanzeallegati.setTransientSegnaPerInvio(true);
			}
			if (inviaAllegatiEndo) {
			    istanzeallegati.setTransientSegnaPerInvio(true);
			}
			almenoUno = true;
		    }
		}
		if (almenoUno) {
		    ChiaveValoreBean<String, List<Istanzeallegati>> bean = new ChiaveValoreBean<String, List<Istanzeallegati>>();
		    bean.setChiave(procedimento.getDescrizioneEstesa());
		    bean.setValore(ialls2);
		    list.add(bean);
		}
	    }
	}
	return list;
    }

    @Override
    public List<Documentiistanza> findListaDocumentiistanza(Istanze istanza, Movimenti movimenti) throws STCNotificaAttivitaException {

	List<Documentiistanza> list = new ArrayList<Documentiistanza>();
	List<Documentiistanza> docs = documentiistanzaService.findByIstanzaOggetto(istanza.getId().getCodice());
	TipimovStcMapping tipimovStcMapping = tipimovStcMappingService.findByTipimovimentoAndAmministrazione(
		movimenti.getTipomovimento().getId().getTipomovimento(), movimenti.getAmministrazioniStc().getId().getCodice());
	if (tipimovStcMapping == null) {
	    throw new STCNotificaAttivitaException("Non è stata trovata la configurazione in TIPIMOV_STC_MAPPING per il tipomovimento " +
						   movimenti.getTipomovimento().getId().getTipomovimento() + " e l'amministrazione " +
						   movimenti.getAmministrazioniStc().getId().getCodice());
	}
	boolean inviaDocumentiIstanza = tipimovStcMapping.getFlagAllegaDocumentiIstanza() == null ? Boolean.FALSE
		: tipimovStcMapping.getFlagAllegaDocumentiIstanza().booleanValue();
	for (Documentiistanza documentiistanza : docs) {
	    documentiistanza.setTransientSegnaPerInvio(inviaDocumentiIstanza);
	    list.add(documentiistanza);
	}
	return list;
    }

    @Override
    public List<InvalidValue> validateDatiPerInvio(Movimenti movimento) {

	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	PkId idIstanza = new PkId(movimento.getIstanza().getId().getCodice());
	Istanze istanza = istanzeService.findById(idIstanza);
	if (StringUtils.isBlank(istanza.getLavori())) {
	    ivs.add(new InvalidValue("stc.service_error.nessuna_descrizione_lavori_per_istanza", null, null, "", null));
	}
	return ivs;
    }

    @Override
    public InserimentoPraticaResponse inviaPratica(InserimentoPraticaRequest request) {

	return stcWsClient.inserimentoPratica(request);
    }

    @Override
    public List<ValoreParametroType> getValoriFromParametroTypeByNome(List<ParametroType> parametri, String nome) {

	if (parametri != null) {
	    for (ParametroType parametro : parametri) {
		if (nome.equalsIgnoreCase(parametro.getNome())) {
		    return parametro.getValore();
		}
	    }
	}
	return new ArrayList<ValoreParametroType>(0);
    }
}
