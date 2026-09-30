package it.gruppoinit.pal.gp.core.service.impl;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.activation.DataHandler;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.MovimentiContromovimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Movimentidyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeListHelper;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeFilter;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiStoricoService;
import it.gruppoinit.pal.gp.core.features.protocollazione.logic.ProtocollazioneService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FieldOperationsEnum;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentisoftwareService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiContromovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.Movimentidyn2modellitService;
import it.gruppoinit.pal.gp.core.service.NlaHelperService;
import it.gruppoinit.pal.gp.core.service.NlaService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.StcService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.NodoNLAEnum;
import it.gruppoinit.pal.gp.core.service.helper.TipoDocumentoPratica;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.OggettiBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.StcWsClient;
import it.gruppoinit.protocollo.schemas.messages.DatiProtocolloResponseType;
import it.init.sigepro.rte.AggiungiDocumentiNLARequest;
import it.init.sigepro.rte.AggiungiDocumentiNLAResponse;
import it.init.sigepro.rte.AllegatoBinarioNLARequest;
import it.init.sigepro.rte.AllegatoBinarioNLAResponse;
import it.init.sigepro.rte.InserimentoAttivitaNLARequest;
import it.init.sigepro.rte.InserimentoAttivitaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticaNLARequest;
import it.init.sigepro.rte.RichiestaPraticaNLAResponse;
import it.init.sigepro.rte.RichiestaPraticheListaNLARequest;
import it.init.sigepro.rte.RichiestaPraticheListaNLAResponse;
import it.init.sigepro.rte.types.AnagrafeType;
import it.init.sigepro.rte.types.ComuneType;
import it.init.sigepro.rte.types.DettaglioPraticaBreveType;
import it.init.sigepro.rte.types.DocumentiType;
import it.init.sigepro.rte.types.ErroreType;
import it.init.sigepro.rte.types.FiltriPraticaType;
import it.init.sigepro.rte.types.FiltriUtenteType;
import it.init.sigepro.rte.types.InterventoType;
import it.init.sigepro.rte.types.LocalizzazioneNelComuneType;
import it.init.sigepro.rte.types.ParametroType;
import it.init.sigepro.rte.types.PersonaGiuridicaType;
import it.init.sigepro.rte.types.RichiedenteType;
import it.init.sigepro.rte.types.RiferimentiAttivitaType;
import it.init.sigepro.rte.types.SostituzioniDocumentaliType;
import it.init.sigepro.rte.types.StatoPraticaType;
import it.init.sigepro.rte.types.ValoreParametroType;

@Service
public class NlaServiceImpl extends NlaStcBaseServiceImpl implements NlaService {

    private static final Logger log = LoggerFactory.getLogger(NlaServiceImpl.class);
    private AnagrafeService anagrafeService;
    private IstanzeService istanzeService;
    private StcWsClient stcWsClient;
    private OggettiService oggettiService;
    private OggettiStoricoService oggettiStoricoService;
    private ContenttypesService contenttypesService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private Istanzedyn2modellitService istanzedyn2modellitService;
    private Dyn2ModellitService dyn2ModellitService;
    private Movimentidyn2modellitService movimentidyn2modellitService;
    private MovimentiContromovimentiService movimentiContromovimentiService;
    private MovimentiService movimentiService;
    private MovimentiNoSecurityService movimentiNoSecurityService;
    private NlaHelperService nlaHelperService;
    private DocumentiistanzaService documentiistanzaService;
    private IstanzeeventiService istanzeeventiService;
    private TipiMovimentoService tipiMovimentoService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService;
    private StatiistanzaService statiistanzaService;
    private ProtocollazioneService protocollazioneService;

    @Autowired
    public void setProtocollazioneService(ProtocollazioneService protocollazioneService) {

	this.protocollazioneService = protocollazioneService;
    }

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setInventarioprocedimentisoftwareService(InventarioprocedimentisoftwareService inventarioprocedimentisoftwareService) {

	this.inventarioprocedimentisoftwareService = inventarioprocedimentisoftwareService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setStcWsClient(StcWsClient stcWsClient) {

	this.stcWsClient = stcWsClient;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setOggettiStoricoService(OggettiStoricoService oggettiStoricoService) {

	this.oggettiStoricoService = oggettiStoricoService;
    }

    @Autowired
    public void setContenttypesService(ContenttypesService contenttypesService) {

	this.contenttypesService = contenttypesService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setIstanzedyn2modellitService(Istanzedyn2modellitService istanzedyn2modellitService) {

	this.istanzedyn2modellitService = istanzedyn2modellitService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setMovimentidyn2modellitService(Movimentidyn2modellitService movimentidyn2modellitService) {

	this.movimentidyn2modellitService = movimentidyn2modellitService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setMovimentiContromovimentiService(MovimentiContromovimentiService movimentiContromovimentiService) {

	this.movimentiContromovimentiService = movimentiContromovimentiService;
    }

    @Autowired
    public void setNlaHelperService(NlaHelperService nlaHelperService) {

	this.nlaHelperService = nlaHelperService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    /**
     * <ul>
     * <li>logga il messaggio</li>
     * <li>inserisce l'evento</li>
     * <li>restituisce l'oggetto response con la sezione popolata con l'errore</li>
     * </ul>
     * 
     * @param messaggioEvento
     * @param categoriaEvento
     * @param istanza
     * @param movimento
     * @return
     */
    private InserimentoAttivitaNLAResponse esciConErrore(String messaggioEvento, String categoriaEvento, Istanze istanza, Movimenti movimento) {

	String riferimenti = "";
	if (istanza != null) {
	    riferimenti = ", riferimenti istanza: " + istanza.toString();
	}
	if (movimento != null) {
	    riferimenti = ", riferimenti movimento: " + movimento.toString();
	}
	messaggioEvento += riferimenti;
	istanzeeventiService.insert(messaggioEvento, categoriaEvento, movimento, istanza);
	log.error("{}", messaggioEvento);
	InserimentoAttivitaNLAResponse iar = new InserimentoAttivitaNLAResponse();
	ErroreType err = new ErroreType();
	err.setNumeroErrore(categoriaEvento);
	err.setDescrizione(messaggioEvento);
	iar.getDettaglioErrore().add(err);
	return iar;
    }

    @Override
    public InserimentoAttivitaNLAResponse inserimentoAttivita(InserimentoAttivitaNLARequest request, String token, boolean isPecOpRoto) {

	stcWsClient.checkToken(request.getToken());
	InserimentoAttivitaNLAResponse response = new InserimentoAttivitaNLAResponse();
	Integer codiceIstanza = Integer.valueOf(request.getDatiAttivita().getIdPratica());
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	if (istanza == null) {
	    String err = "Attenzione! Istanza non trovata. (codice=" + codiceIstanza + ").";
	    log.error(err);
	    throw new RuntimeException(err);
	}
	Integer movSoggettiEsterni = null;
	Integer movEsegueContromovDi = null;
	if (request.getDatiAttivita() != null) {
	    List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
	    ValoreParametroType movSoggEsterniPar = Utilities.getCampoDaAltriDati(altriDati, ALTRI_DATI_ESEGUI_MOV_FK_FO_SOGGETTI_ESTERNI);
	    if (movSoggEsterniPar != null) {
		String codiceMovSE = StringUtils.defaultString(movSoggEsterniPar.getCodice()).trim();
		if (Utilities.isInteger(codiceMovSE)) {
		    movSoggettiEsterni = Integer.valueOf(codiceMovSE);
		    if (log.isDebugEnabled()) {
			log.debug("inserimentoAttivita# settato il valore {} in altri dati del parametro {}", movSoggettiEsterni,
				ALTRI_DATI_ESEGUI_MOV_FK_FO_SOGGETTI_ESTERNI);
		    }
		}
	    }
	    ValoreParametroType movEsegueCMovDiPar = Utilities.getCampoDaAltriDati(altriDati, ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI);
	    if (movEsegueCMovDiPar != null) {
		String codiceEsegueCMovDi = StringUtils.defaultString(movEsegueCMovDiPar.getCodice()).trim();
		if (Utilities.isInteger(codiceEsegueCMovDi)) {
		    movEsegueContromovDi = Integer.valueOf(codiceEsegueCMovDi);
		    if (log.isDebugEnabled()) {
			log.debug("inserimentoAttivita# settato il valore {} in altri del parametro {}", movEsegueContromovDi,
				ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI);
		    }
		}
	    }
	}
	if (movEsegueContromovDi != null && movSoggettiEsterni != null) {
	    String message = "Nel messaggio (sezione altri dati) sono stati passati sia i dati relativi a [" + ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI +
			     "] che [" + ALTRI_DATI_ESEGUI_MOV_FK_FO_SOGGETTI_ESTERNI + "] con i valori " + movEsegueContromovDi + ", " +
			     movSoggettiEsterni;
	    return esciConErrore(message, IstanzeeventiConstants.CATEGORIA_STC_IA, istanza, null);
	}
	Tipimovimento tmSoggEsterniDefault = null;
	Integer codiceMovimentoEsistente = null;
	if (movSoggettiEsterni != null) {
	    String decodePuoEssereEseguito = movSoggettiEsterni.intValue() == 1 ? "Front Office Richiedenti" : "Front Office Amministrazioni";
	    if (log.isDebugEnabled()) {
		log.debug("inserimentoAttivita# {} cerco tra i movimenti da eseguire dell'istanza che possono essere eseguiti da {}",
			ALTRI_DATI_ESEGUI_MOV_FK_FO_SOGGETTI_ESTERNI, decodePuoEssereEseguito);
	    }
	    List<Movimenti> movimentiDaEseguire = movimentiService.findDaEseguireByIstanza(istanza);
	    boolean trovato = false;
	    if (movimentiDaEseguire != null) {
		for (Movimenti movimenti : movimentiDaEseguire) {
		    Tipimovimento tm = movimenti.getTipomovimento();
		    if (tm.getFoSoggettiesterni() != null && tm.getFoSoggettiesterni().getCodice() != null
			    && tm.getFoSoggettiesterni().getCodice().equals(movSoggettiEsterni)) {
			codiceMovimentoEsistente = movimenti.getId().getCodice();
			if (log.isDebugEnabled()) {
			    log.debug("inserimentoAttivita# {} trovato il movimento con codice {}", ALTRI_DATI_ESEGUI_MOV_FK_FO_SOGGETTI_ESTERNI,
				    codiceMovimentoEsistente);
			}
			trovato = true;
			break;
		    }
		}
	    }
	    if (!trovato) {
		String messaggioEvento = "Tra i movimenti da eseguire per l'istanza non è stato trovato quello che puo' essere eseguito da " +
					 decodePuoEssereEseguito;
		// CERCO SE LA VERTICALIZZAZIONE TIPOMOV_DEFAULT_SOGG_ESTERNI E' STATA DEFINITA SE SI INSERISCO UN NUOVO MOVIMENTO CON QUEL TIPO MOVIMENTO
		Verticalizzazioniparametri tipoMovdefault = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_STC,
			WebConstants.VERTICALIZZAZIONE_STC_TIPOMOV_DEFAULT_SOGG_ESTERNI);
		if (tipoMovdefault != null) {
		    String tipoMov = StringUtils.defaultString(tipoMovdefault.getValore()).trim();
		    if (StringUtils.isNotBlank(tipoMov)) {
			tmSoggEsterniDefault = tipiMovimentoService.findById(new TipimovimentoId(tipoMov));
			if (tmSoggEsterniDefault != null) {
			    trovato = true;
			} else {
			    messaggioEvento += ". Il tipomovimento [" + tipoMov + "] configurato nelle regole [" +
					       WebConstants.VERTICALIZZAZIONE_STC + "." +
					       WebConstants.VERTICALIZZAZIONE_STC_TIPOMOV_DEFAULT_SOGG_ESTERNI +
					       "] non è stato trovato nella base dati";
			}
		    }
		}
		if (!trovato) {
		    messaggioEvento = "Tra i movimenti da eseguire per l'istanza non è stato trovato quello che puo' essere eseguito da " +
				      decodePuoEssereEseguito;
		    return esciConErrore(messaggioEvento, IstanzeeventiConstants.CATEGORIA_STC_IA, istanza, null);
		}
	    }
	}
	if (movEsegueContromovDi != null) {
	    if (log.isDebugEnabled()) {
		log.debug("inserimentoAttivita# {} recupero il movimento con codice {}", ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI, movEsegueContromovDi);
	    }
	    Movimenti mov = movimentiNoSecurityService.findById(new PkId(movEsegueContromovDi));
	    if (mov == null) {
		String messaggioEvento = "Tra i movimenti da eseguire per l'istanza non è stato trovato quello con codice " +
					 movEsegueContromovDi.intValue();
		return esciConErrore(messaggioEvento, IstanzeeventiConstants.CATEGORIA_STC_IA, istanza, null);
	    }
	    if (log.isDebugEnabled()) {
		log.debug("inserimentoAttivita# {} cerco tra i contromovimenti", ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI);
	    }
	    List<MovimentiContromovimenti> cmovs = movimentiContromovimentiService.findByMovimentoByFkPadre(mov);
	    boolean trovato = false;
	    if (cmovs != null) {
		Movimenti cmov = null;
		if (cmovs.size() == 1) {
		    if (log.isDebugEnabled()) {
			log.debug("inserimentoAttivita# {} è presente un solo contromovimento", ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI);
		    }
		    cmov = cmovs.get(0).getMovimentoByFkFiglio();
		}
		if (cmovs.size() > 1) {
		    if (log.isDebugEnabled()) {
			log.debug("inserimentoAttivita# {} sono presenti {} contromovimenti", ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI, cmovs.size());
		    }
		    // Se vengono trovati due contromovimenti si prende quello il cui tipomovimento ha il FLAG_FK_SOGGETTIESTERNI=2. 
		    // Se non trovato lancia una eccezione, attacca un evento alla pratica.
		    for (MovimentiContromovimenti mcm : cmovs) {
			Movimenti cm = mcm.getMovimentoByFkFiglio();
			Tipimovimento tm = cm.getTipomovimento();
			if (tm.getFoSoggettiesterni() != null) {
			    if (tm.getFoSoggettiesterni().getCodice() != null) {
				if (tm.getFoSoggettiesterni().getCodice().intValue() == 2) {
				    if (log.isDebugEnabled()) {
					log.debug("inserimentoAttivita# {} trovato il contromovimenti con FLAG_FK_SOGGETTIESTERNI==2",
						ALTRI_DATI_ESEGUI_CONTROMOVIMENTO_DI);
				    }
				    cmov = cm;
				    break;
				}
			    }
			}
		    }
		}
		if (cmov == null) {
		    return esciConErrore("Non e' stato trovato il contromovimento di quello con codice " + movEsegueContromovDi,
			    IstanzeeventiConstants.CATEGORIA_STC_IA, istanza, null);
		}
		if (cmov != null) {
		    if (cmov.getData() != null) {
			return esciConErrore("Il movimento con codice " + cmov.getId().getCodice() + " risulta già eseguito",
				IstanzeeventiConstants.CATEGORIA_STC_IA, istanza, null);
		    }
		    codiceMovimentoEsistente = cmov.getId().getCodice();
		    trovato = true;
		}
	    }
	    if (!trovato) {
		String decodePuoEssereEseguito = movSoggettiEsterni.intValue() == 1 ? "Front Office Richiedenti" : "Front Office Amministrazioni";
		String messaggioEvento = "Tra i movimenti da eseguire per l'istanza non è stato trovato quello che puo' essere eseguito da " +
					 decodePuoEssereEseguito;
		return esciConErrore(messaggioEvento, IstanzeeventiConstants.CATEGORIA_STC_IA, istanza, null);
	    }
	}
	NodoNLAEnum tipoNodoMittente = nlaHelperService.getTipoNodo(request.getSportelloMittente());
	String codiceAmministrazione = "";
	if (tipoNodoMittente.equals(NodoNLAEnum.NLA_IDNODO_AREARISERVATA) && codiceMovimentoEsistente == null) {
	    // l'area riservata passa il codice movimento nel campo idattivita
	    String codiceMovimentoStr = StringUtils.defaultIfEmpty(request.getDatiAttivita().getIdAttivita(), "").trim();
	    if (StringUtils.isNotBlank(codiceMovimentoStr)) {
		try {
		    codiceMovimentoEsistente = Integer.valueOf(codiceMovimentoStr);
		} catch (Exception e) {
		    log.error("Il codice movimento passato non rappresenta un numero {}", codiceMovimentoStr);
		}
	    }
	}
	if (codiceMovimentoEsistente != null) {
	    Movimenti movimento = movimentiService.findById(new PkId(codiceMovimentoEsistente));
	    if (movimento != null) {
		if (movimento.getData() != null) {
		    // 
		    return esciConErrore(
			    "Il movimento " + movimento.getId() + " risulta già eseguito e non e' possibile eseguire una seconda notifica",
			    IstanzeeventiConstants.CATEGORIA_STC_IA, istanza, movimento);
		}
		if (request.getDatiAttivita() != null && request.getDatiAttivita().getTipoAttivita() != null
			&& StringUtils.isBlank(request.getDatiAttivita().getTipoAttivita().getCodice())) {
		    String tipomovimento = movimento.getTipomovimento().getId().getTipomovimento();
		    log.debug(
			    "inserimentoAttivita# il dato tipoattivita tornato è null setto il valore [{}] dal tipo movimento del movimento trovato",
			    tipomovimento);
		    request.getDatiAttivita().getTipoAttivita().setCodice(tipomovimento);
		}
		if (EntityUtils.getNestedProperty(movimento, "amministrazioni.id.codice") != null) {
		    codiceAmministrazione = String.valueOf(movimento.getAmministrazioni().getId().getCodice());
		}
		String codiceAmministrazioneDaAltriDati = decodeAmministrazioneFromAltriDati(request);
		if (StringUtils.isNotBlank(codiceAmministrazioneDaAltriDati)) {
		    log.warn(
			    "inserimentoAttivita# è presente CodiceAmministrazione del movimento eisstente {}, ma anche quella recuperata da altri  dati {}. uso questa ultima",
			    codiceAmministrazione, codiceAmministrazioneDaAltriDati);
		    codiceAmministrazione = codiceAmministrazioneDaAltriDati;
		}
	    }
	    log.debug("codiceAmministrazione recuperata dal movimento esistente: {}", codiceAmministrazione);
	} else {
	    if (tmSoggEsterniDefault != null) {
		// questo tipo di movimento viene popolato solamente in caso di ALTRI_DATI_ESEGUI_MOV_FK_FO_SOGGETTI_ESTERNI e le altre ricerche falliscano 
		// SOVRASCRIVO LA PROPRIETA CODICE DI TIPOATTIVITA' (altrimenti da errore nlaHelperService.populateMovimenti)
		request.getDatiAttivita().getTipoAttivita().setCodice(tmSoggEsterniDefault.getId().getTipomovimento());
	    }
	    codiceAmministrazione = decodeAmministrazioneFromAltriDati(request);
	    log.debug("codiceAmministrazione passata da altri dati: {}", codiceAmministrazione);
	    if (StringUtils.isBlank(codiceAmministrazione)) {
		Integer codiceAmministrazioneMovimentoMittente = this.decodeAmministrazioneMittenteFromAltriDati(request);
		codiceAmministrazione = decodeAmministrazioneFromSportelloType(request.getSportelloMittente(),
			codiceAmministrazioneMovimentoMittente);
	    }
	}
	if (StringUtils.isBlank(codiceAmministrazione)) {
	    codiceAmministrazione = decodeAmministrazioneFromAltriDati(request);
	    log.debug("codiceAmministrazione blank tento di recuperarla da altri dati: {}", codiceAmministrazione);
	}
	log.debug("codiceAmministrazione trovata {}", codiceAmministrazione);
	boolean passaProt = passaProt(request.getSportelloMittente(), request.getSportelloDestinatario());
	Movimenti mov = nlaHelperService.populateMovimenti(request, istanza, codiceAmministrazione, passaProt, isPecOpRoto);
	// se viene passato il GUID di origine impostarlo sul movimento
	ValoreParametroType guidOrigine = Utilities.getCampoDaAltriDati(request.getDatiAttivita().getAltriDati(),
		StcService.ALTRO_DATO_GUID_ZIP_LOGICO_DI_ORIGINE);
	if (guidOrigine != null) {
	    mov.setGuidOrigine(guidOrigine.getCodice());
	}
	log.debug("inserimentoAttivita: populate Movimenti");
	OggettiBusinessRules oggettiBusinessRules = new OggettiBusinessRules();
	oggettiBusinessRules.setInsert(true);
	SigeproBusinessRules.setClassRules(OggettiBusinessRules.class, oggettiBusinessRules);
	IstanzeBusinessRules istanzeBusinessRules = new IstanzeBusinessRules(true, true);
	istanzeBusinessRules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), true);
	SigeproBusinessRules.setClassRules(IstanzeBusinessRules.class, istanzeBusinessRules);
	boolean isNLA239 = nlaHelperService.checkSportello(request.getSportelloMittente(), NodoNLAEnum.NLA_IDNODO_RFC239);
	try {
	    Verticalizzazioniparametri protocollaprimadinotificare = verticalizzazioniService.getVerticalizzazioniparametri(
		    WebConstants.VERTICALIZZAZIONE_STC, WebConstants.VERTICALIZZAZIONE_STC_IATT_PROT_PRIMA_DI_INSERIRE);
	    if (protocollaprimadinotificare != null
		    && StringUtils.defaultString(protocollaprimadinotificare.getValore(), "0").equalsIgnoreCase("1")) {
		boolean isNLAAreaRiservata = nlaHelperService.checkSportello(request.getSportelloMittente(), NodoNLAEnum.NLA_IDNODO_AREARISERVATA);
		if (isNLAAreaRiservata) {
		    try {
			protocollaPrimadiInserire(mov, request, istanza);
		    } catch (FunzioneBusinessRemotaException e) {
			log.error("Errore in protocollazione ", e);
			throw new RuntimeException("Si è verificato un errore durante la protocollazione");
		    }
		}
	    }
	    // Verificare che non sia già presente tra i movimenti da eseguire (scadenze). Se esistente allora setto il codice del vecchio movimento
	    if (codiceMovimentoEsistente == null) {
		log.debug("inserimentoAttivita: verifico che il movimento non sia già presente tra i movimenti da eseguire (scadenze)");
		List<Movimenti> movimenti = movimentiService.findDaEseguireByIstanza(istanza);
		for (Movimenti movimento : movimenti) {
		    Integer codiceInventario = (Integer) EntityUtils.getNestedProperty(movimento.getEndoprocedimento(), "id.codice");
		    if (codiceInventario != null) {
			Integer codiceEndoMov = (Integer) EntityUtils.getNestedProperty(mov.getEndoprocedimento(), "id.codice");
			if (codiceEndoMov != null) {
			    if (codiceEndoMov.equals(codiceInventario)) {
				// stesso endo
				String tipoMovimento = (String) EntityUtils.getNestedProperty(movimento.getTipomovimento(), "id.tipomovimento");
				String tipoMovimentoMov = (String) EntityUtils.getNestedProperty(mov.getTipomovimento(), "id.tipomovimento");
				if (tipoMovimento.equals(tipoMovimentoMov)) {
				    // stesso tipomovimento
				    Integer codiceAmministrazioneMovimento = (Integer) EntityUtils.getNestedProperty(movimento.getAmministrazioni(),
					    "id.codice");
				    if (codiceAmministrazioneMovimento != null) {
					Integer codiceAmministrazioneMov = (Integer) EntityUtils.getNestedProperty(mov.getAmministrazioni(),
						"id.codice");
					if (codiceAmministrazioneMov != null) {
					    if (codiceAmministrazioneMovimento.equals(codiceAmministrazioneMov)) {
						codiceMovimentoEsistente = movimento.getId().getCodice();
						break;
					    } else {
						// ... trovo l'amministrazione dell'endo per il software e la setto
						// l'amministrazione mittente è salvata in CODICEAMMINISTRAZIONE_STC 
						Inventarioprocedimenti ip = inventarioprocedimentiService.findById(new PkId(codiceInventario));
						Integer codiceAmmEndo = null;
						if (ip != null) {
						    // String codiceSoftwareEP = StringUtils.defaultString(ip.getSoftware().getCodice());
						    // l'amministrazione è nell'endo
						    if (ip.getAmministrazioni() != null) {
							if (ip.getAmministrazioni().getId() != null) {
							    codiceAmmEndo = ip.getAmministrazioni().getId().getCodice();
							}
						    }
						    // if (codiceSoftwareEP.equalsIgnoreCase(WebConstants.SOFTWARE_TT)) {
						    // l'amministrazione è in inventarioprocedimentisoftware
						    List<Inventarioprocedimentisoftware> lists = inventarioprocedimentisoftwareService
							    .findByEndoprocedimentiAndSoftware(codiceEndoMov, ORMHelper.getSoftware());
						    if (lists.size() == 1) {
							Inventarioprocedimentisoftware ips = lists.get(0);
							if (ips.getAmministrazioni() != null) {
							    if (ips.getAmministrazioni().getId() != null) {
								if (ips.getAmministrazioni().getId().getCodice() != null) {
								    codiceAmmEndo = ips.getAmministrazioni().getId().getCodice();
								}
							    }
							}
						    } else {
							log.warn("Più endo configurati su inventarioprocedimentisoftware, salto l'elaborazione {}",
								codiceEndoMov);
						    }
						    // } 
						}
						if (codiceAmmEndo != null) {
						    log.debug("Recupero l'amministrazione dell'endo");
						    Amministrazioni amm = amministrazioniService.findById(new PkId(codiceAmmEndo));
						    mov.setAmministrazioni(amm);
						    break;
						}
					    }
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	    boolean movimentoEsistente = false;
	    if (codiceMovimentoEsistente != null) {
		movimentoEsistente = true;
		mov.getId().setCodice(codiceMovimentoEsistente);
	    }
	    if (isNLA239 && StringUtils.isBlank(mov.getIdAttDest()) && request.getDatiAttivita() != null
		    && StringUtils.isNotBlank(request.getDatiAttivita().getIdAttivita())) {
		mov.setIdAttDest(request.getDatiAttivita().getIdAttivita());
		mov.setStatoAttDest("OK");
	    }
	    movimentiService.insert(mov);
	    log.debug("inserimentoAttivita: movimento inserito");
	    if (!movimentoEsistente) {
		// NON DOVREBBE ACCADERE IN QUANTO LE CONDIZIONI SONO LE STESSE DELLE PRECEDENTI
		log.debug("Il movimento non era presente cerco eventuali movimenti simili per effettuare il bind");
		// Verificare che non sia già presente tra i movimenti da eseguire (scadenze)
		log.debug("inserimentoAttivita: verifico che il movimento non sia già presente tra i movimenti da eseguire (scadenze)");
		List<Movimenti> movimenti = movimentiService.findDaEseguireByIstanza(istanza);
		MovimentiHelper cm = movimentiService.findCaratteristicheMovimento(mov);
		if (cm.isEffettuaChiusura()) {
		    // cancello il movimento
		    for (Movimenti movimento : movimenti) {
			if (movimento.getTipomovimento().getId().getTipomovimento().equals(mov.getTipomovimento().getId().getTipomovimento())) {
			    List<MovimentiContromovimenti> mcs = movimentiContromovimentiService.findByMovimentoByFkFiglio(movimento);
			    log.debug(
				    "inserimentoAttivita: Ciclo le associazioni in MovimentiContromovimenti per il vecchio movimento non eseguito {}",
				    EntityUtils.getNestedProperty(movimento, "id.codice"));
			    for (MovimentiContromovimenti movimentiContromovimenti : mcs) {
				// aggiorno l'associazione con il nuovo movimento
				movimentiContromovimenti.setMovimentoByFkFiglio(mov);
				movimentiContromovimentiService.update(movimentiContromovimenti);
			    }
			    log.debug("inserimentoAttivita: cancello il vecchio movimento non eseguito {}",
				    EntityUtils.getNestedProperty(movimento, "id.codice"));
			    // cancello il vecchio movimento
			    movimentiService.delete(movimento);
			    break;
			}
		    }
		    if (mov.getEsito() != null) {
			// effettuo il cambio stato
			//			<option value="-1">Chiusa negativamente</option>
			//			<option value="1">Chiusa positivamente</option>
			Integer codiceStato = mov.getEsito().booleanValue() ? 1 : -1;
			List<Statiistanza> sc = statiistanzaService.findByStatocomportamentoChiuse();
			for (Statiistanza si : sc) {
			    if (si.getStaticomportamento().getCodcomportamento().intValue() == codiceStato.intValue()) {
				istanzeService.updateStatoIstanza(istanza, si.getId().getCodicestato());
				break;
			    }
			}
		    }
		} else {
		    for (Movimenti movimento : movimenti) {
			Integer codiceInventario = (Integer) EntityUtils.getNestedProperty(movimento.getEndoprocedimento(), "id.codice");
			if (codiceInventario != null) {
			    Integer codiceEndoMov = (Integer) EntityUtils.getNestedProperty(mov.getEndoprocedimento(), "id.codice");
			    if (codiceEndoMov != null) {
				if (codiceEndoMov.equals(codiceInventario)) {
				    // stesso endo
				    String tipoMovimento = (String) EntityUtils.getNestedProperty(movimento.getTipomovimento(), "id.tipomovimento");
				    String tipoMovimentoMov = (String) EntityUtils.getNestedProperty(mov.getTipomovimento(), "id.tipomovimento");
				    if (tipoMovimento.equals(tipoMovimentoMov)) {
					// stesso tipomovimento
					Integer codiceAmministrazioneMovimento = (Integer) EntityUtils
						.getNestedProperty(movimento.getAmministrazioni(), "id.codice");
					if (codiceAmministrazioneMovimento != null) {
					    Integer codiceAmministrazioneMov = (Integer) EntityUtils.getNestedProperty(mov.getAmministrazioni(),
						    "id.codice");
					    if (codiceAmministrazioneMov != null) {
						if (codiceAmministrazioneMovimento.equals(codiceAmministrazioneMov)) {
						    // stessa amministrazione
						    log.debug(
							    "inserimentoAttivita: trovato il movimento da eseguire ed associare all'attivita NLA {}",
							    EntityUtils.getNestedProperty(movimento, "id.codice"));
						    List<MovimentiContromovimenti> mcs = movimentiContromovimentiService
							    .findByMovimentoByFkFiglio(movimento);
						    log.debug(
							    "inserimentoAttivita: Ciclo le associazioni in MovimentiContromovimenti per il vecchio movimento non eseguito {}",
							    EntityUtils.getNestedProperty(movimento, "id.codice"));
						    for (MovimentiContromovimenti movimentiContromovimenti : mcs) {
							// aggiorno l'associazione con il nuovo movimento
							movimentiContromovimenti.setMovimentoByFkFiglio(mov);
							movimentiContromovimentiService.update(movimentiContromovimenti);
						    }
						    log.debug("inserimentoAttivita: cancello il vecchio movimento non eseguito {}",
							    EntityUtils.getNestedProperty(movimento, "id.codice"));
						    // cancello il vecchio movimento
						    movimentiService.delete(movimento);
						    break;
						}
					    }
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	    gestisciSchedeDinamiche(request, mov);
	    /////////////////////Inserimento istanzedyn2dati (schede dinamiche) a partire da altriDati ////////////////////////
	    insertIstanzedyn2datiFromAltriDati(request.getDatiAttivita().getAltriDati(), codiceIstanza);
	    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	    RiferimentiAttivitaType riferimentiAttivitaType = new RiferimentiAttivitaType();
	    if (passaProt) {
		if (request.getDatiAttivita().getDataProtocolloGenerale() != null) {
		    riferimentiAttivitaType.setDataProtocolloGenerale(request.getDatiAttivita().getDataProtocolloGenerale());
		}
		if (StringUtils.isNotBlank(request.getDatiAttivita().getNumeroProtocolloGenerale())) {
		    riferimentiAttivitaType.setNumeroProtocolloGenerale(request.getDatiAttivita().getNumeroProtocolloGenerale());
		}
	    }
	    riferimentiAttivitaType.setIdAttivita(mov.getId().getCodice().toString());
	    riferimentiAttivitaType.setIdPratica(request.getDatiAttivita().getIdPratica());
	    //se il mov ha il procedimento settato (nlaHelper findEndoMappato) allora lo setto altrimenti torno null
	    if (EntityUtils.getNestedProperty(mov.getEndoprocedimento(), "id.codice") != null) {
		riferimentiAttivitaType.setIdProcedimento(mov.getEndoprocedimento().getId().getCodice().toString());
	    }
	    response.setDettaglioAttivita(riferimentiAttivitaType);
	    // Gestione sostituzione documentale. Il processo controlla se è popolata la sezione "Documenti da sostituire"
	    // 1. popolata allora controlla se il documento da sostituire è presete nell'istanza, se si lo sostituisce con quello
	    // nuovo e storicizza l'operazione
	    if (request.getDatiAttivita() != null && !request.getDatiAttivita().getDocumentiDaSostituire().isEmpty()) {
		Integer codIstanz = mov.getIstanza().getId().getCodice();
		log.debug("inserimentoAttivita# Ci sono documenti da sostituire all'interno della pratica : {}[{}]",
			new Object[] { mov.getIstanza().getNumeroistanza(), mov.getIstanza().getId().getCodice() });
		List<SostituzioniDocumentaliType> sostituzioniDocumentaliTypes = request.getDatiAttivita().getDocumentiDaSostituire();
		for (SostituzioniDocumentaliType sostituzioniDocumentaliType : sostituzioniDocumentaliTypes) {
		    String tipoDocumento = sostituzioniDocumentaliType.getTipoDocumentoPratica();
		    if (tipoDocumento.equals("DOC_ISTANZA")) {
			log.debug("inserimentoAttivita# Tipo documento : ISTANZA, codice documento da sostituire: {}, codice documento nuovo: {} ",
				new Object[] { sostituzioniDocumentaliType.getDocumentoDaSostituire(),
					sostituzioniDocumentaliType.getNuovoDocumento().getId() });
			oggettiStoricoService.updateSostituisciOggetto(codIstanz,
				Integer.parseInt(sostituzioniDocumentaliType.getDocumentoDaSostituire()),
				Integer.parseInt(sostituzioniDocumentaliType.getNuovoDocumento().getId()), TipoDocumentoPratica.DOC_ISTANZA);
		    } else {
			// DOC_ENDO
			log.debug("inserimentoAttivita# Tipo documento : ENDO, codice documento da sostituire: {}, codice documento nuovo: {} ",
				new Object[] { sostituzioniDocumentaliType.getDocumentoDaSostituire(),
					sostituzioniDocumentaliType.getNuovoDocumento().getId() });
			oggettiStoricoService.updateSostituisciOggetto(codIstanz,
				Integer.parseInt(sostituzioniDocumentaliType.getDocumentoDaSostituire()),
				Integer.parseInt(sostituzioniDocumentaliType.getNuovoDocumento().getId()), TipoDocumentoPratica.DOC_ENDO);
		    }
		}
	    }
	} catch (Exception e) {
	    log.error("inserimentoAttivita: ", e);
	    throw new RuntimeException(e);
	} finally {
	    SigeproBusinessRules.buildDefaultRules();
	}
	return response;
    }

    private String decodeAmministrazioneFromAltriDati(InserimentoAttivitaNLARequest request) {

	boolean stessoNodoEnte = nlaHelperService.checkIsStessoNodoStessoEnte(request.getSportelloDestinatario(), request.getSportelloMittente());
	log.debug("decodeAmministrazioneFromAltriDati: stessoNodoEnte {}", stessoNodoEnte);
	if (stessoNodoEnte && (request.getDatiAttivita() != null && request.getDatiAttivita().getAltriDati() != null
		&& !request.getDatiAttivita().getAltriDati().isEmpty())) {
	    log.debug("decodeAmministrazioneFromAltriDati: request.getDatiAttivita().getAltriDati().isEmpty() {}",
		    request.getDatiAttivita().getAltriDati().isEmpty());
	    ValoreParametroType codiceAmministrazione = Utilities.getCampoDaAltriDati(request.getDatiAttivita().getAltriDati(),
		    NlaHelperService.NOTIFICA_ATTIVITA_CODICE_AMMINISTRAZIONE_STC);
	    if (codiceAmministrazione != null && StringUtils.isNotBlank(codiceAmministrazione.getCodice())) {
		log.debug("decodeAmministrazioneFromAltriDati: Utilities.getCampoDaAltriDati {}", codiceAmministrazione.getCodice());
		return codiceAmministrazione.getCodice();
	    }
	}
	return null;
    }

    private Integer decodeAmministrazioneMittenteFromAltriDati(InserimentoAttivitaNLARequest request) {

	boolean stessoNodoEnte = nlaHelperService.checkIsStessoNodoStessoEnte(request.getSportelloDestinatario(), request.getSportelloMittente());
	log.debug("decodeAmministrazioneFromAltriDati: stessoNodoEnte {}", stessoNodoEnte);
	if (stessoNodoEnte && (request.getDatiAttivita() != null && request.getDatiAttivita().getAltriDati() != null
		&& !request.getDatiAttivita().getAltriDati().isEmpty())) {
	    log.debug("decodeAmministrazioneFromAltriDati: request.getDatiAttivita().getAltriDati().isEmpty() {}",
		    request.getDatiAttivita().getAltriDati().isEmpty());
	    ValoreParametroType codiceAmministrazione = Utilities.getCampoDaAltriDati(request.getDatiAttivita().getAltriDati(),
		    NlaHelperService.CODICE_AMMINISTRAZIONE_MOVIMENTO_MITTENTE);
	    if (codiceAmministrazione != null && StringUtils.isNotBlank(codiceAmministrazione.getCodice())) {
		log.debug("decodeAmministrazioneMittenteFromAltriDati: {}", codiceAmministrazione.getCodice());
		return Integer.valueOf(codiceAmministrazione.getCodice());
	    }
	}
	return null;
    }

    private void protocollaPrimadiInserire(Movimenti mov, InserimentoAttivitaNLARequest request, Istanze istanza)
	    throws FunzioneBusinessRemotaException {

	// solo se area riservata perché gli oggetti con codiceoggetto già esistono
	try {
	    DatiProtocolloResponseType protocollaDomandaOnline = protocollazioneService.protocollaMovimentoOnline(mov, request, istanza);
	    mov.setNumeroprotocollo(protocollaDomandaOnline.getNumeroProtocollo());
	    mov.setDataprotocollo(Utilities.getDate(protocollaDomandaOnline.getDataProtocollo(), WebConstants.DATE_FORMAT_PATTERN).getTime());
	    mov.setFkidprotocollo(protocollaDomandaOnline.getIdProtocollo());
	} catch (Exception e) {
	    throw new FunzioneBusinessRemotaException(e);
	}
    }

    @Override
    public AllegatoBinarioNLAResponse richiestaAllegato(AllegatoBinarioNLARequest request) {

	stcWsClient.checkToken(request.getToken());
	AllegatoBinarioNLAResponse response = new AllegatoBinarioNLAResponse();
	String idAllegato = request.getRiferimentiAllegato().getIdAllegato();
	if (StringUtils.isNotBlank(idAllegato)) {
	    PkId idOggetto = new PkId(new Integer(idAllegato));
	    Oggetti file = oggettiService.findById(idOggetto);
	    if (file == null) {
		log.error("richiestaAllegato: Allegato con codice '{}' non trovato", idAllegato);
		throw new RuntimeException("Attenzione! Allegato con codice " + idAllegato + " non trovato");
	    }
	    response.setFileName(file.getNomefile());
	    String contenttype = contenttypesService.findMimeTypeByFileName(file.getNomefile());
	    response.setMimeType(contenttype);
	    DataHandler dh = Utilities.bytesToDataHandler(file.getOggetto());
	    response.setBinaryData(dh);
	    return response;
	}
	log.error("richiestaAllegato: Allegato con codice '{}' non trovato", idAllegato);
	throw new RuntimeException("Attenzione! Allegato con codice '" + idAllegato + "' non trovato");
    }

    private void validaRequest(RichiestaPraticaNLARequest request) {

	if (request == null) {
	    throw new BusinessValidationException("Nessun riferimento alla pratica specificato (RichiestaPraticaNLARequest nulla)");
	}
	if (request.getRifPratica() == null) {
	    throw new BusinessValidationException("Nessun riferimento alla pratica specificato (RifPratica nullo)");
	}
	String idPratica = StringUtils.defaultString(request.getRifPratica().getIdPratica());
	String numeroPratica = StringUtils.defaultString(request.getRifPratica().getNumeroPratica());
	String numeroprotocollo = StringUtils.defaultString(request.getRifPratica().getNumeroProtocolloGenerale());
	if (StringUtils.isBlank(idPratica)) {
	    if (StringUtils.isBlank(numeroPratica)) {
		if (StringUtils.isBlank(numeroprotocollo) && request.getRifPratica().getDataProtocolloGenerale() == null) {
		    throw new BusinessValidationException("Non sono stati trovati riferimenti per recuperare la pratica");
		}
	    }
	}
    }

    @Override
    public RichiestaPraticaNLAResponse richiestaPratica(RichiestaPraticaNLARequest request) {

	stcWsClient.checkToken(request.getToken());
	Istanze istanza = null;
	String errorMessage = "";
	RichiestaPraticaNLAResponse response = null;
	try {
	    validaRequest(request);
	} catch (BusinessValidationException bv) {
	    response = new RichiestaPraticaNLAResponse();
	    ErroreType errore = new ErroreType();
	    errore.setNumeroErrore(String.valueOf(NlaService.CODICE_ERRORE_RICERCA_PRATICA_SIGEPRO));
	    errore.setDescrizione(bv.getMessage());
	    response.getDettaglioErrore().add(errore);
	    return response;
	}
	if (StringUtils.isNotBlank(request.getRifPratica().getIdPratica())) {
	    try {
		Integer idPratica = new Integer(request.getRifPratica().getIdPratica());
		istanza = istanzeService.findById(new PkId(idPratica));
	    } catch (NumberFormatException e) {
		log.error("richiestaPratica: idPratica deve essere numerico [{}]", request.getRifPratica().getIdPratica());
	    }
	} else {
	    FilterTable filterTable = getFilterTable(request);
	    List<Istanze> istanzeList = istanzeService.findByFilterTable(filterTable, 0, 4);
	    if (null == istanzeList || istanzeList.size() == 0) {
		istanza = null;
		errorMessage = "L'istanza con i criteri: \nId Pratica: " + request.getRifPratica().getIdPratica() + ", \nNumero Pratica: " +
			       request.getRifPratica().getNumeroPratica() + ", \nNumero Protocollo: " +
			       request.getRifPratica().getNumeroProtocolloGenerale() + " \nnon è stata trovata.";
	    } else {
		if (istanzeList.size() > 1) {
		    istanza = null;
		    errorMessage = "La ricerca con i criteri: \niId Pratica: " + request.getRifPratica().getIdPratica() + ", \nNumero Pratica: " +
				   request.getRifPratica().getNumeroPratica() + ", \nNumero Protocollo: " +
				   request.getRifPratica().getNumeroProtocolloGenerale() + " \nha ritornato più di una pratica.";
		} else {
		    istanza = istanzeList.get(0);
		}
	    }
	}
	if (istanza == null) {
	    if (StringUtils.isNotBlank(request.getRifPratica().getIdPratica())) {
		String nodo = request.getSportelloDestinatario().getIdNodo() + "-" + request.getSportelloDestinatario().getIdEnte() + "-" +
			      request.getSportelloDestinatario().getIdSportello();
		log.error("La pratica con codice {} non esiste. Nodo [{}]", request.getRifPratica().getIdPratica(), nodo);
		errorMessage = "La pratica con codice " + request.getRifPratica().getIdPratica() + " non esiste nel nodo NLA " + nodo;
	    }
	    log.error(errorMessage);
	    ErroreType errore = new ErroreType();
	    errore.setNumeroErrore(String.valueOf(NlaService.CODICE_ERRORE_RICERCA_PRATICA_SIGEPRO));
	    errore.setDescrizione(errorMessage);
	    response = new RichiestaPraticaNLAResponse();
	    response.getDettaglioErrore().add(errore);
	} else {
	    boolean soloLeAttivitaEseguitePubblicate = true;
	    List<ParametroType> ad = request.getRifPratica().getAltriDati();
	    Set<Integer> soloDocESchedeDiQuestiEndo = new HashSet<Integer>();
	    if (ad != null && !ad.isEmpty()) {
		for (ParametroType pt : ad) {
		    if (StringUtils.defaultString(pt.getNome()).equalsIgnoreCase(RICHIESTA_PRATICA_ALTRO_DATO_TUTTE_LE_ATTIVITA_ESEGUITE_IN_VISURA)) {
			soloLeAttivitaEseguitePubblicate = false;
			break;
		    }
		}
		for (ParametroType pt : ad) {
		    if (StringUtils.defaultString(pt.getNome())
			    .equalsIgnoreCase(NlaHelperService.NOTIFICA_ATTIVITA_LISTA_ENDO_PROCEDIMENTI_DA_COPIARE)) {
			List<ValoreParametroType> listVP = pt.getValore();
			for (ValoreParametroType vpt : listVP) {
			    if (vpt != null && StringUtils.isNotBlank(vpt.getCodice())) {
				String codiceEndo = StringUtils.defaultString(vpt.getCodice(), "").trim();
				if (Utilities.isInteger(codiceEndo)) {
				    soloDocESchedeDiQuestiEndo.add(Integer.parseInt(codiceEndo));
				}
			    }
			}
		    }
		}
	    }
	    boolean isDomandaNLABackoffice = nlaHelperService.checkIsStessoNodoStessoEnte(request.getSportelloMittente(),
		    request.getSportelloDestinatario());
	    if (isDomandaNLABackoffice && soloLeAttivitaEseguitePubblicate) {
		soloLeAttivitaEseguitePubblicate = false; // nodo interno richiedo tutte le attività
	    }
	    response = nlaHelperService.populateRichiestaPraticaNLAResponse(istanza, soloLeAttivitaEseguitePubblicate, soloDocESchedeDiQuestiEndo);
	    // gestione protocollo
	    if (passaProt(request.getSportelloMittente(), request.getSportelloDestinatario())) {
		response.getDettaglioPratica().getDettaglioPratica().setNumeroProtocolloGenerale(istanza.getNumeroprotocollo());
		if (istanza.getDataprotocollo() != null) {
		    GregorianCalendar dataProtocollo = new GregorianCalendar();
		    dataProtocollo.setTime(istanza.getDataprotocollo());
		    response.getDettaglioPratica().getDettaglioPratica().setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtocollo));
		}
	    }
	}
	return response;
    }

    @Override
    public RichiestaPraticheListaNLAResponse richiestaPraticheLista(RichiestaPraticheListaNLARequest request) {

	stcWsClient.checkToken(request.getToken());
	RichiestaPraticheListaNLAResponse response = new RichiestaPraticheListaNLAResponse();
	try {
	    //	    if (request.getFiltriUtenteConnesso() == null || StringUtils.isBlank(request.getFiltriUtenteConnesso().getCodiceFiscale())) {
	    //		log.error("richiestaPraticheLista: filtroUtenteConnesso nullo o cf nullo");
	    //		throw new RuntimeException("Filtro utente non impostato");
	    //	    }
	    //XMLGregorianCalendar value=new
	    IstanzeFilter filter = getIstanzeFilter(request);
	    Integer firstResult = new Integer(0);
	    Integer maxResult = new Integer(100);
	    if (request.getFiltriPratica().getLimiteRecords() != null) {
		if (request.getFiltriPratica().getLimiteRecords().compareTo(maxResult) < 0) {
		    maxResult = request.getFiltriPratica().getLimiteRecords();
		}
	    }
	    boolean isDomandaNLABackoffice = nlaHelperService.checkIsStessoNodoStessoEnte(request.getSportelloMittente(),
		    request.getSportelloDestinatario());
	    if (isDomandaNLABackoffice) {
		// se la domanda proviene da un nodo backoffice
		// non escludo le pratiche dalla ricerca
		filter.setFlagEscludiRisultatiDaRicercaPubblica(Boolean.FALSE);
	    } else {
		// nel caso di domanda proveniente da altro nodo
		// escludo le pratiche i cui interventi hanno il flag escludi risultati da ricerca pubblica uguale a true
		// queste pratiche non compariranno quindi nella lista restituita dal servizio.
		filter.setFlagEscludiRisultatiDaRicercaPubblica(Boolean.TRUE);
	    }
	    List<IstanzeListHelper> listIstanze = istanzeService.findIstanzeListHelperByFilter(filter, firstResult, maxResult);
	    // if (istanzeList != null) {
	    if (listIstanze != null && !listIstanze.isEmpty()) {
		DettaglioPraticaBreveType detPraBreveType;
		//for (Istanze istanze : istanzeList) {
		for (IstanzeListHelper istanzeListHelper : listIstanze) {
		    //RichiestaPraticaNLAResponse _resp = nlaHelperService.populateRichiestaPraticaNLAResponse(istanza);
		    //RichiestaPraticaNLAResponse _resp = nlaHelperService.populateRichiestaPraticaNLAResponse(istanzeListHelper);
		    //DettaglioPraticaType _dettP = _resp.getDettaglioPratica().getDettaglioPratica();
		    detPraBreveType = new DettaglioPraticaBreveType();
		    PersonaGiuridicaType giuridicaType = new PersonaGiuridicaType();
		    // .AZIENDA RICHEDENTE OK
		    if (istanzeListHelper.getCodiceazienda() != null) {
			Anagrafe titolareLegale = anagrafeService.findById(new PkId(istanzeListHelper.getCodiceazienda().intValue()));
			if (titolareLegale != null) {
			    AnagrafeType aziendaRichiedente = nlaHelperService.populateAnagrafeType(titolareLegale);
			    if (aziendaRichiedente.getPersonaGiuridica() != null) {
				giuridicaType = aziendaRichiedente.getPersonaGiuridica();
				detPraBreveType.setAziendaRichiedente(giuridicaType);
			    }
			}
		    }
		    detPraBreveType.setAziendaRichiedente(giuridicaType);
		    // . CAMPI COMUNE OK
		    ComuneType comuneType = new ComuneType();
		    comuneType.setCodiceCatastale(istanzeListHelper.getIdcomune());
		    comuneType.setCodiceIstat(istanzeListHelper.getCodiceistat());
		    comuneType.setComune(istanzeListHelper.getComune());
		    detPraBreveType.setCodiceComune(comuneType);
		    //.DATA PRATICA OK
		    GregorianCalendar dataPratica = new GregorianCalendar();
		    dataPratica.setTime(istanzeListHelper.getData());
		    detPraBreveType.setDataPratica(Utilities.getXMLGregorianCalendar(dataPratica));
		    //.DATA PROTOCOLLO OK
		    GregorianCalendar dataProtocollogenerale = new GregorianCalendar();
		    if (istanzeListHelper.getDataprotocollo() != null) {
			dataProtocollogenerale.setTime(istanzeListHelper.getDataprotocollo());
			detPraBreveType.setDataProtocolloGenerale(Utilities.getXMLGregorianCalendar(dataProtocollogenerale));
		    }
		    // .CODICE ISTANZA OK
		    detPraBreveType.setIdPratica(istanzeListHelper.getCodiceistanza().toString());
		    // . PROFESSIONISTA (INTERMEDIARIO) OK
		    if (istanzeListHelper.getCodicetecnico() != null) {
			Anagrafe istruttore = anagrafeService.findById(new PkId(istanzeListHelper.getCodicetecnico().intValue()));
			if (EntityUtils.getNestedProperty(istruttore, "id.codice") != null) {
			    AnagrafeType _professionista = nlaHelperService.populateAnagrafeType(istanzeListHelper.getCodicetecnico().intValue());
			    detPraBreveType.setIntermediario(_professionista);
			}
		    }
		    // .INTERVENTO OK
		    InterventoType _intervento = new InterventoType();
		    _intervento.setCodice(istanzeListHelper.getCodiceinterventoproc().toString());
		    _intervento.setDescrizione(istanzeListHelper.getInterventoproc());
		    detPraBreveType.setIntervento(_intervento);
		    // . LOCALIZZAZIONE OK
		    //if (istanzeListHelper.getCodicestradarioprimario() != null) {
		    List<LocalizzazioneNelComuneType> listLoc = nlaHelperService
			    .populateLocalizzazione(istanzeListHelper.getCodiceistanza().intValue());
		    if (listLoc != null && !listLoc.isEmpty()) {
			detPraBreveType.getLocalizzazione().addAll(listLoc);
		    }
		    //}
		    //. OK NUMERO ISTANZA
		    detPraBreveType.setNumeroPratica(istanzeListHelper.getNumeroistanza());
		    //. OK NUMERO PROTOCOLLO
		    if (StringUtils.isNotBlank(istanzeListHelper.getNumeroprotocollo())) {
			detPraBreveType.setNumeroProtocolloGenerale(istanzeListHelper.getNumeroprotocollo());
		    }
		    //. OK OGGETTO (LAVORI)
		    if (StringUtils.isNotBlank(istanzeListHelper.getOggettoistanza())) {
			detPraBreveType.setOggetto(istanzeListHelper.getOggettoistanza());
		    }
		    //. OK RICHIEDENTE
		    // TODO NEL CASO DI RICHIEDENTI PERSONE GIURIDICHE???
		    Anagrafe richidente = anagrafeService.findById(new PkId(istanzeListHelper.getCodicerichiedente().intValue()));
		    if (EntityUtils.getNestedProperty(richidente, "id.codice") != null) {
			Integer codicetiposogg = null;
			if (istanzeListHelper.getCodicetiposoggetto() != null) {
			    codicetiposogg = istanzeListHelper.getCodicetiposoggetto().intValue();
			}
			String tiposogg = "";
			if (StringUtils.isNotBlank(istanzeListHelper.getTiposoggetto())) {
			    tiposogg = istanzeListHelper.getTiposoggetto();
			}
			RichiedenteType richiedenteType = nlaHelperService.populateRichiedenteType(richidente, codicetiposogg, tiposogg);
			detPraBreveType.setRichiedente(richiedenteType);
		    }
		    Statiistanza statoistanza = statiistanzaService.findById(new StatiistanzaId(istanzeListHelper.getCodicestatoistanza()));
		    detPraBreveType.setStatoPratica(nlaHelperService.decodeStatoPratica(statoistanza));
		    response.getDettaglioPratica().add(detPraBreveType);
		}
	    }
	} catch (Exception e) {
	    ErroreType et = new ErroreType();
	    et.setNumeroErrore(String.valueOf(NlaService.CODICE_ERRORE_RICERCA_PRATICHE_SIGEPRO));
	    et.setDescrizione("Errore durante la ricerca delle pratiche: " + e.getMessage());
	    response.getDettaglioErrore().add(et);
	    log.error("richiestaPraticheLista(): {}", e.getMessage(), e);
	}
	return response;
    }

    private IstanzeFilter getIstanzeFilter(RichiestaPraticheListaNLARequest request) {

	//	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	IstanzeFilter istanzeFilter = new IstanzeFilter();
	FiltriPraticaType filtriPratica = request.getFiltriPratica();
	FiltriUtenteType filtriUtente = request.getFiltriUtenteConnesso();
	if (filtriUtente != null) {
	    String cf = filtriUtente.getCodiceFiscale();
	    //	    FilterRestriction filterUtenteRestriction = new FilterRestriction();
	    //	    filterUtenteRestriction.setAndOrRestriction(AndOrRestriction.OR);
	    boolean searchAll = true;
	    if (BooleanUtils.isTrue(filtriUtente.isCercaComeRichiedente())) {
		istanzeFilter.getSoggettiIstanzaFilterCF().setRichiedenteCF(cf);
		//		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "richiedente", String.class));
		searchAll = false;
	    }
	    if (BooleanUtils.isTrue(filtriUtente.isCercaComeAziendaRichiedente())) {
		//filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "titolarelegale", String.class));
		istanzeFilter.getSoggettiIstanzaFilterCF().setTitolarelegaleCF(cf);
		searchAll = false;
	    }
	    if (BooleanUtils.isTrue(filtriUtente.isCercaComeIntermediario())) {
		//filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "professionista", String.class));
		istanzeFilter.getSoggettiIstanzaFilterCF().setProfessionistaCF(cf);
		searchAll = false;
	    }
	    if (BooleanUtils.isTrue(filtriUtente.isCercaNeiSoggettiCollegati())) {
		istanzeFilter.getSoggettiIstanzaFilterCF().setIstanzerichiedentisCF(cf);
		searchAll = false;
	    }
	    if (searchAll) {
		istanzeFilter.getSoggettiIstanzaFilterCF().setRichiedenteCF(cf);
		istanzeFilter.getSoggettiIstanzaFilterCF().setTitolarelegaleCF(cf);
		istanzeFilter.getSoggettiIstanzaFilterCF().setProfessionistaCF(cf);
		istanzeFilter.getSoggettiIstanzaFilterCF().setIstanzerichiedentisCF(cf);
	    }
	    //	    ft.addRestriction(filterUtenteRestriction);
	}
	//OK
	if (filtriPratica.getComune() != null) {
	    Comuni comuni = new Comuni();
	    if (StringUtils.isNotEmpty(filtriPratica.getComune().getCodiceCatastale())) {
		comuni.setCodicecomune(filtriPratica.getComune().getCodiceCatastale());
		//		filterPraticaRestriction.addFilterField(FilterUtils.equals("cf", filtriPratica.getComune().getCodiceCatastale(), "comune",
		//			String.class));
	    }
	    if (StringUtils.isNotEmpty(filtriPratica.getComune().getCodiceIstat())) {
		comuni.setCodiceistat(filtriPratica.getComune().getCodiceIstat());
		//		filterPraticaRestriction.addFilterField(FilterUtils.equals("codiceistat", filtriPratica.getComune().getCodiceCatastale(), "comune",
		//			String.class));
	    }
	    if (StringUtils.isNotEmpty(filtriPratica.getComune().getComune())) {
		comuni.setComune(filtriPratica.getComune().getComune());
		//		filterPraticaRestriction.addFilterField(FilterUtils.equals("comune", filtriPratica.getComune().getComune(), "comune", String.class));
	    }
	    istanzeFilter.setComune(comuni);
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getIdPratica())) {
	    try {
		Integer idPratica = Integer.valueOf(filtriPratica.getIdPratica());
		istanzeFilter.setCodiceIstanza(idPratica);
		//filterPraticaRestriction.addFilterField(FilterUtils.equals("id.codice", idPratica, Integer.class));
	    } catch (NumberFormatException e) {
		log.warn("getFilterTable: FiltriPraticaType#idPratica non è un numero: {}", filtriPratica.getIdPratica());
	    }
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getNumeroPratica())) {
	    istanzeFilter.setNumeroistanza(filtriPratica.getNumeroPratica());
	    // filterPraticaRestriction.addFilterField(FilterUtils.equals("numeroistanza", filtriPratica.getNumeroPratica(), String.class));
	}
	if (filtriPratica.getDataPresentazionePraticaA() != null) {
	    if (filtriPratica.getDataPresentazionePraticaDa() != null) {
		istanzeFilter.setAllaData(filtriPratica.getDataPresentazionePraticaA().toGregorianCalendar().getTime());
		istanzeFilter.setDallaData(filtriPratica.getDataPresentazionePraticaDa().toGregorianCalendar().getTime());
		//		filterPraticaRestriction.addFilterField(FilterUtils.between("data", filtriPratica.getDataPresentazionePraticaDa()
		//			.toGregorianCalendar().getTime(), filtriPratica.getDataPresentazionePraticaA().toGregorianCalendar().getTime(), Date.class));
	    } else {
		istanzeFilter.setAllaData(filtriPratica.getDataPresentazionePraticaA().toGregorianCalendar().getTime());
		//		filterPraticaRestriction.addFilterField(FilterUtils.smallerEqual("data", filtriPratica.getDataPresentazionePraticaA()
		//			.toGregorianCalendar().getTime(), Date.class));
	    }
	} else {
	    if (filtriPratica.getDataPresentazionePraticaDa() != null) {
		istanzeFilter.setDallaData(filtriPratica.getDataPresentazionePraticaDa().toGregorianCalendar().getTime());
		//		filterPraticaRestriction.addFilterField(FilterUtils.greaterEqual("data", filtriPratica.getDataPresentazionePraticaDa()
		//			.toGregorianCalendar().getTime(), Date.class));
	    }
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getNumeroProtocolloGenerale())) {
	    istanzeFilter.setNumeroprotocollo(filtriPratica.getNumeroProtocolloGenerale());
	    //	    filterPraticaRestriction
	    //		    .addFilterField(FilterUtils.equals("numeroprotocollo", filtriPratica.getNumeroProtocolloGenerale(), String.class));
	}
	//OK
	if (filtriPratica.getDataProtocolloGeneraleA() != null) {
	    if (filtriPratica.getDataProtocolloGeneraleDa() != null) {
		istanzeFilter.setDallaDataProtocollo(filtriPratica.getDataProtocolloGeneraleDa().toGregorianCalendar().getTime());
		istanzeFilter.setAllaDataProtocollo(filtriPratica.getDataProtocolloGeneraleA().toGregorianCalendar().getTime());
		//		filterPraticaRestriction.addFilterField(FilterUtils.between("dataprotocollo", filtriPratica.getDataProtocolloGeneraleDa()
		//			.toGregorianCalendar().getTime(), filtriPratica.getDataProtocolloGeneraleA().toGregorianCalendar().getTime(), Date.class));
	    } else {
		istanzeFilter.setAllaDataProtocollo(filtriPratica.getDataProtocolloGeneraleA().toGregorianCalendar().getTime());
		//		filterPraticaRestriction.addFilterField(FilterUtils.smallerEqual("dataprotocollo", filtriPratica.getDataProtocolloGeneraleA()
		//			.toGregorianCalendar().getTime(), Date.class));
	    }
	} else {
	    if (filtriPratica.getDataProtocolloGeneraleDa() != null) {
		istanzeFilter.setDallaDataProtocollo(filtriPratica.getDataProtocolloGeneraleDa().toGregorianCalendar().getTime());
		//		filterPraticaRestriction.addFilterField(FilterUtils.greaterEqual("dataprotocollo", filtriPratica.getDataProtocolloGeneraleDa()
		//			.toGregorianCalendar().getTime(), Date.class));
	    }
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getNumeroAtto())) {
	    //TODO verificare se recuperare solo le aut attive o anche i subentri
	    // istanzeFilter.setcon
	    //	    filterPraticaRestriction.addFilterField(FilterUtils.equals("autoriznumero", filtriPratica.getNumeroAtto(), "autorizzazionis",
	    //		    String.class));
	    Autorizzazioni autorizzazioni = new Autorizzazioni();
	    autorizzazioni.setAutoriznumero(filtriPratica.getNumeroAtto());
	    istanzeFilter.setDatiAutorizzazione(autorizzazioni);
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getCodiceIntervento())) {
	    try {
		Integer codIntervento = Integer.valueOf(filtriPratica.getCodiceIntervento());
		Alberoproc alberoproc = new Alberoproc();
		PkId id = new PkId(codIntervento);
		alberoproc.setId(id);
		istanzeFilter.setAlberoproc(alberoproc);
		//		filterPraticaRestriction.addFilterField(FilterUtils.equals("id.codice", codIntervento, "alberoproc", Integer.class));
	    } catch (NumberFormatException e) {
		log.warn("getFilterTable: FiltriPraticaType#codiceIntervento non è un numero: {}", filtriPratica.getCodiceIntervento());
	    }
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getOggetto())) {
	    istanzeFilter.setLavori(filtriPratica.getOggetto());
	    // filterPraticaRestriction.addFilterField(FilterUtils.like("lavori", filtriPratica.getOggetto()));
	}
	Istanzestradario istanzestradario = new Istanzestradario();
	///OK
	if (StringUtils.isNotBlank(filtriPratica.getLocalizzazioneCodiceViario())) {
	    istanzeFilter.setStradarioCodViario(filtriPratica.getLocalizzazioneCodiceViario());
	    //	    filterPraticaRestriction.addFilterField(FilterUtils.equals("codviario", filtriPratica.getLocalizzazioneCodiceViario(),
	    //		    "istanzestradarios.stradario", String.class));
	}
	///OK
	if (StringUtils.isNotBlank(filtriPratica.getLocalizzazioneIndirizzo())) {
	    istanzeFilter.setStradarioDescrizione(filtriPratica.getLocalizzazioneIndirizzo());
	    //	    filterPraticaRestriction.addFilterField(FilterUtils.like("descrizione", filtriPratica.getLocalizzazioneIndirizzo(),
	    //		    "istanzestradarios.stradario"));
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getLocalizzazioneCivico())) {
	    istanzestradario.setCivico(filtriPratica.getLocalizzazioneCivico());
	}
	istanzeFilter.setIstanzestradario(istanzestradario);
	Istanzemappali istanzemappali = new Istanzemappali();
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getRifCatastaliTipoCatasto())) {
	    String tipoCatasto = "";
	    if (filtriPratica.getRifCatastaliTipoCatasto().equals("Fabbricati")) {
		tipoCatasto = "F";
	    }
	    if (filtriPratica.getRifCatastaliTipoCatasto().equals("Terreni")) {
		tipoCatasto = "T";
	    }
	    if (StringUtils.isNotBlank(tipoCatasto)) {
		Catasto catasto = new Catasto();
		catasto.setCodice(tipoCatasto);
		istanzemappali.setCatasto(catasto);
		//		filterPraticaRestriction.addFilterField(FilterUtils.equals("codice", tipoCatasto, "istanzemappalis.catasto", String.class));
	    } else {
		log.warn("getFilterTable: FiltriPraticaType#rifCatastaliTipoCatasto non è codificato: {}",
			filtriPratica.getRifCatastaliTipoCatasto());
	    }
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getRifCatastaliFoglio())) {
	    istanzemappali.setFoglio(filtriPratica.getRifCatastaliFoglio());
	    //	    filterPraticaRestriction.addFilterField(FilterUtils.equals("foglio", filtriPratica.getRifCatastaliFoglio(), "istanzemappalis",
	    //		    String.class));
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getRifCatastaliParticella())) {
	    istanzemappali.setParticella(filtriPratica.getRifCatastaliParticella());
	    ;
	    //	    filterPraticaRestriction.addFilterField(FilterUtils.equals("particella", filtriPratica.getRifCatastaliParticella(), "istanzemappalis",
	    //		    String.class));
	}
	//OK
	if (StringUtils.isNotBlank(filtriPratica.getRifCatastaliSub())) {
	    istanzemappali.setSub(filtriPratica.getRifCatastaliSub());
	    //	    filterPraticaRestriction.addFilterField(FilterUtils.equals("sub", filtriPratica.getRifCatastaliSub(), "istanzemappalis", String.class));
	}
	//???????????? PERCHé?????????????????????????????
	if (StringUtils.isNotBlank(filtriPratica.getCodiceFiscaleRichiedente())) {
	    //	    filterPraticaRestriction.addFilterField(FilterUtils.equals("codicefiscale", filtriPratica.getCodiceFiscaleRichiedente(), "richiedente",
	    //		    String.class));
	}
	////////// ????????????????????????????????????????///
	if (filtriPratica.getStatoPratica() != null) {
	    // ATTENZIONE LA QUERY IN QUERYISTANZEHELPER CHE ESEGUE IL FILTRO SUGLI STATI NON LEGGE LA PROPRIETA' STATICOMPORTAMENTO MA USA UNA CONVENZIONE
	    // SECONDO LA LOGICA CHE SEGUE. SNIPPET DI CODICE RIPRESO DA QUELLA CLASSE
	    //	    if (EntityUtils.getNestedProperty(filter, "chiusura.id.codicestato") != null) {
	    //		String stato = filter.getChiusura().getId().getCodicestato();
	    //		if (!stato.equals("stato_tutte")) { // non esegue filtri su stato
	    //		    if (stato.equalsIgnoreCase("stato_chiuse") || stato.equalsIgnoreCase("stato_aperte")
	    //			    || stato.equalsIgnoreCase("stato_chiuse_negativamente") || stato.equalsIgnoreCase("stato_chiuse_positivamente")) {
	    Statiistanza si = new Statiistanza();
	    StatiistanzaId id = new StatiistanzaId();
	    si.setId(id);
	    Integer statoPratica = null;
	    if (filtriPratica.getStatoPratica().equals(StatoPraticaType.ATTIVA)) {
		statoPratica = new Integer(0);
		id.setCodicestato("stato_aperte");
	    }
	    if (filtriPratica.getStatoPratica().equals(StatoPraticaType.CHIUSA_NEGATIVAMENTE)) {
		statoPratica = new Integer(-1);
		id.setCodicestato("stato_chiuse_negativamente");
	    }
	    if (filtriPratica.getStatoPratica().equals(StatoPraticaType.CHIUSA_POSITIVAMENTE)) {
		statoPratica = new Integer(1);
		id.setCodicestato("stato_chiuse_positivamente");
	    }
	    if (statoPratica != null) {
		istanzeFilter.setChiusura(si);
		// NON VIENE LETTO istanzeFilter.setStaticomportamento(statoPratica);
	    } else {
		log.warn("getFilterTable: FiltriPraticaType#statoPratica non è codificato: {}", filtriPratica.getStatoPratica().name());
	    }
	}
	//	ft.addRestriction(filterPraticaRestriction);
	// BOCCI 2012-08-31 ORDINAMENTO DEFAULT DATA DESC, NUMEROPRATICA DESC
	istanzeFilter.setOrderBy("data");
	istanzeFilter.setOrderAscDesc(OrderTypeEnum.DESC);
	//	ft.addOrder(FilterUtils.orderDesc("data"));
	//	String[] padNumeroistanza = new String[] { "20", "' '" };
	//	ft.addOrder(FilterUtils.orderDesc("numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
	return istanzeFilter;
    }

    private FilterTable getFilterTable(RichiestaPraticheListaNLARequest request) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FiltriPraticaType filtriPratica = request.getFiltriPratica();
	FiltriUtenteType filtriUtente = request.getFiltriUtenteConnesso();
	FilterRestriction filterPraticaRestriction = new FilterRestriction();
	if (filtriUtente != null) {
	    String cf = filtriUtente.getCodiceFiscale();
	    FilterRestriction filterUtenteRestriction = new FilterRestriction();
	    filterUtenteRestriction.setAndOrRestriction(AndOrRestriction.OR);
	    boolean searchAll = true;
	    if (BooleanUtils.isTrue(filtriUtente.isCercaComeRichiedente())) {
		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "richiedente", String.class));
		searchAll = false;
	    }
	    if (BooleanUtils.isTrue(filtriUtente.isCercaComeAziendaRichiedente())) {
		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "titolarelegale", String.class));
		searchAll = false;
	    }
	    if (BooleanUtils.isTrue(filtriUtente.isCercaComeIntermediario())) {
		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "professionista", String.class));
		searchAll = false;
	    }
	    if (BooleanUtils.isTrue(filtriUtente.isCercaNeiSoggettiCollegati())) {
		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "istanzerichiedentis.richiedente", String.class));
		searchAll = false;
	    }
	    if (searchAll) {
		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "richiedente", String.class));
		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "titolarelegale", String.class));
		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "professionista", String.class));
		filterUtenteRestriction.addFilterField(FilterUtils.equals("codicefiscale", cf, "istanzerichiedentis.richiedente", String.class));
	    }
	    ft.addRestriction(filterUtenteRestriction);
	}
	if (filtriPratica.getComune() != null) {
	    if (StringUtils.isNotEmpty(filtriPratica.getComune().getCodiceCatastale())) {
		filterPraticaRestriction
			.addFilterField(FilterUtils.equals("cf", filtriPratica.getComune().getCodiceCatastale(), "comune", String.class));
	    }
	    if (StringUtils.isNotEmpty(filtriPratica.getComune().getCodiceIstat())) {
		filterPraticaRestriction
			.addFilterField(FilterUtils.equals("codiceistat", filtriPratica.getComune().getCodiceCatastale(), "comune", String.class));
	    }
	    if (StringUtils.isNotEmpty(filtriPratica.getComune().getComune())) {
		filterPraticaRestriction.addFilterField(FilterUtils.equals("comune", filtriPratica.getComune().getComune(), "comune", String.class));
	    }
	}
	if (StringUtils.isNotBlank(filtriPratica.getIdPratica())) {
	    try {
		Integer idPratica = Integer.valueOf(filtriPratica.getIdPratica());
		filterPraticaRestriction.addFilterField(FilterUtils.equals("id.codice", idPratica, Integer.class));
	    } catch (NumberFormatException e) {
		log.warn("getFilterTable: FiltriPraticaType#idPratica non è un numero: {}", filtriPratica.getIdPratica());
	    }
	}
	if (StringUtils.isNotBlank(filtriPratica.getNumeroPratica())) {
	    filterPraticaRestriction.addFilterField(FilterUtils.equals("numeroistanza", filtriPratica.getNumeroPratica(), String.class));
	}
	if (filtriPratica.getDataPresentazionePraticaA() != null) {
	    if (filtriPratica.getDataPresentazionePraticaDa() != null) {
		filterPraticaRestriction
			.addFilterField(FilterUtils.between("data", filtriPratica.getDataPresentazionePraticaDa().toGregorianCalendar().getTime(),
				filtriPratica.getDataPresentazionePraticaA().toGregorianCalendar().getTime(), Date.class));
	    } else {
		filterPraticaRestriction.addFilterField(
			FilterUtils.smallerEqual("data", filtriPratica.getDataPresentazionePraticaA().toGregorianCalendar().getTime(), Date.class));
	    }
	} else {
	    if (filtriPratica.getDataPresentazionePraticaDa() != null) {
		filterPraticaRestriction.addFilterField(
			FilterUtils.greaterEqual("data", filtriPratica.getDataPresentazionePraticaDa().toGregorianCalendar().getTime(), Date.class));
	    }
	}
	if (StringUtils.isNotBlank(filtriPratica.getNumeroProtocolloGenerale())) {
	    filterPraticaRestriction
		    .addFilterField(FilterUtils.equals("numeroprotocollo", filtriPratica.getNumeroProtocolloGenerale(), String.class));
	}
	if (filtriPratica.getDataProtocolloGeneraleA() != null) {
	    if (filtriPratica.getDataProtocolloGeneraleDa() != null) {
		filterPraticaRestriction.addFilterField(
			FilterUtils.between("dataprotocollo", filtriPratica.getDataProtocolloGeneraleDa().toGregorianCalendar().getTime(),
				filtriPratica.getDataProtocolloGeneraleA().toGregorianCalendar().getTime(), Date.class));
	    } else {
		filterPraticaRestriction.addFilterField(FilterUtils.smallerEqual("dataprotocollo",
			filtriPratica.getDataProtocolloGeneraleA().toGregorianCalendar().getTime(), Date.class));
	    }
	} else {
	    if (filtriPratica.getDataProtocolloGeneraleDa() != null) {
		filterPraticaRestriction.addFilterField(FilterUtils.greaterEqual("dataprotocollo",
			filtriPratica.getDataProtocolloGeneraleDa().toGregorianCalendar().getTime(), Date.class));
	    }
	}
	if (StringUtils.isNotBlank(filtriPratica.getNumeroAtto())) {
	    //TODO verificare se recuperare solo le aut attive o anche i subentri
	    filterPraticaRestriction
		    .addFilterField(FilterUtils.equals("autoriznumero", filtriPratica.getNumeroAtto(), "autorizzazionis", String.class));
	}
	if (StringUtils.isNotBlank(filtriPratica.getCodiceIntervento())) {
	    try {
		Integer codIntervento = Integer.valueOf(filtriPratica.getCodiceIntervento());
		filterPraticaRestriction.addFilterField(FilterUtils.equals("id.codice", codIntervento, "alberoproc", Integer.class));
	    } catch (NumberFormatException e) {
		log.warn("getFilterTable: FiltriPraticaType#codiceIntervento non è un numero: {}", filtriPratica.getCodiceIntervento());
	    }
	}
	if (StringUtils.isNotBlank(filtriPratica.getOggetto())) {
	    filterPraticaRestriction.addFilterField(FilterUtils.like("lavori", filtriPratica.getOggetto()));
	}
	if (StringUtils.isNotBlank(filtriPratica.getLocalizzazioneCodiceViario())) {
	    filterPraticaRestriction.addFilterField(
		    FilterUtils.equals("codviario", filtriPratica.getLocalizzazioneCodiceViario(), "istanzestradarios.stradario", String.class));
	}
	if (StringUtils.isNotBlank(filtriPratica.getLocalizzazioneIndirizzo())) {
	    filterPraticaRestriction
		    .addFilterField(FilterUtils.like("descrizione", filtriPratica.getLocalizzazioneIndirizzo(), "istanzestradarios.stradario"));
	}
	if (StringUtils.isNotBlank(filtriPratica.getLocalizzazioneCivico())) {
	    filterPraticaRestriction
		    .addFilterField(FilterUtils.equals("civico", filtriPratica.getLocalizzazioneIndirizzo(), "istanzestradarios", String.class));
	}
	if (StringUtils.isNotBlank(filtriPratica.getRifCatastaliTipoCatasto())) {
	    String tipoCatasto = "";
	    if (filtriPratica.getRifCatastaliTipoCatasto().equals("Fabbricati")) {
		tipoCatasto = "F";
	    }
	    if (filtriPratica.getRifCatastaliTipoCatasto().equals("Terreni")) {
		tipoCatasto = "T";
	    }
	    if (StringUtils.isNotBlank(tipoCatasto)) {
		filterPraticaRestriction.addFilterField(FilterUtils.equals("codice", tipoCatasto, "istanzemappalis.catasto", String.class));
	    } else {
		log.warn("getFilterTable: FiltriPraticaType#rifCatastaliTipoCatasto non è codificato: {}",
			filtriPratica.getRifCatastaliTipoCatasto());
	    }
	}
	if (StringUtils.isNotBlank(filtriPratica.getRifCatastaliFoglio())) {
	    filterPraticaRestriction
		    .addFilterField(FilterUtils.equals("foglio", filtriPratica.getRifCatastaliFoglio(), "istanzemappalis", String.class));
	}
	if (StringUtils.isNotBlank(filtriPratica.getRifCatastaliParticella())) {
	    filterPraticaRestriction
		    .addFilterField(FilterUtils.equals("particella", filtriPratica.getRifCatastaliParticella(), "istanzemappalis", String.class));
	}
	if (StringUtils.isNotBlank(filtriPratica.getRifCatastaliSub())) {
	    filterPraticaRestriction.addFilterField(FilterUtils.equals("sub", filtriPratica.getRifCatastaliSub(), "istanzemappalis", String.class));
	}
	if (StringUtils.isNotBlank(filtriPratica.getCodiceFiscaleRichiedente())) {
	    filterPraticaRestriction
		    .addFilterField(FilterUtils.equals("codicefiscale", filtriPratica.getCodiceFiscaleRichiedente(), "richiedente", String.class));
	}
	if (filtriPratica.getStatoPratica() != null) {
	    Integer statoPratica = null;
	    if (filtriPratica.getStatoPratica().equals(StatoPraticaType.ATTIVA)) {
		statoPratica = new Integer(0);
	    }
	    if (filtriPratica.getStatoPratica().equals(StatoPraticaType.CHIUSA_NEGATIVAMENTE)) {
		statoPratica = new Integer(-1);
	    }
	    if (filtriPratica.getStatoPratica().equals(StatoPraticaType.CHIUSA_POSITIVAMENTE)) {
		statoPratica = new Integer(1);
	    }
	    if (statoPratica != null) {
		filterPraticaRestriction
			.addFilterField(FilterUtils.equals("codcomportamento", statoPratica, "chiusura.staticomportamento", Integer.class));
	    } else {
		log.warn("getFilterTable: FiltriPraticaType#statoPratica non è codificato: {}", filtriPratica.getStatoPratica().name());
	    }
	}
	ft.addRestriction(filterPraticaRestriction);
	// BOCCI 2012-08-31 ORDINAMENTO DEFAULT DATA DESC, NUMEROPRATICA DESC
	ft.addOrder(FilterUtils.orderDesc("data"));
	String[] padNumeroistanza = new String[] { "20", "' '" };
	ft.addOrder(FilterUtils.orderDesc("numeroistanza", FunctionsEnum.LPAD_FUNCTION, padNumeroistanza));
	return ft;
    }

    private FilterTable getFilterTable(RichiestaPraticaNLARequest request) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	if (EntityUtils.getNestedProperty(request, "rifPratica.numeroPratica") != null) {
	    String numeroPratica = (String) EntityUtils.getNestedProperty(request, "rifPratica.numeroPratica");
	    if (StringUtils.isNotBlank(numeroPratica)) {
		FilterRestriction numeroIstanza = new FilterRestriction();
		numeroIstanza.addFilterField(
			new FilterField<String>("numeroistanza", FieldOperationsEnum.EQ, new String[] { numeroPratica }, String.class));
		ft.addRestriction(numeroIstanza);
	    }
	}
	if (EntityUtils.getNestedProperty(request, "rifPratica.numeroProtocolloGenerale") != null) {
	    String numeroProtocollo = (String) EntityUtils.getNestedProperty(request, "rifPratica.numeroProtocolloGenerale");
	    if (StringUtils.isNotBlank(numeroProtocollo)) {
		FilterRestriction dataProtocollo = new FilterRestriction();
		dataProtocollo.addFilterField(new FilterField<String>("numeroprotocollo", FieldOperationsEnum.EQ,
			new String[] { request.getRifPratica().getNumeroProtocolloGenerale() }, String.class));
		ft.addRestriction(dataProtocollo);
	    }
	}
	if (EntityUtils.getNestedProperty(request, "rifPratica.dataProtocolloGenerale") != null) {
	    FilterRestriction dataProtocollo = new FilterRestriction();
	    dataProtocollo.addFilterField(new FilterField<Date>("dataprotocollo", FieldOperationsEnum.EQ,
		    new Date[] { request.getRifPratica().getDataProtocolloGenerale().toGregorianCalendar().getTime() }, Date.class));
	    ft.addRestriction(dataProtocollo);
	}
	return ft;
    }

    /**
     * Gestisce la copia delle schede dinamiche da una pratica ad un'altra.<br />
     * Questo caso è possibile solo tra due NLA Sigepro e dello stesso idcomune condizione
     * sportellomittente.idente=sportellodestinatario.idente
     * 
     * @param request
     * @param movResp
     */
    private void gestisciSchedeDinamiche(InserimentoAttivitaNLARequest request, Movimenti movResp) {

	// NODI INTERNI
	if (request.getSportelloMittente().getIdNodo().equals(request.getSportelloDestinatario().getIdNodo())) {
	    if (request.getSportelloMittente().getIdEnte().equals(request.getSportelloDestinatario().getIdEnte())) {
		if (request.getSportelloMittente().getIdEnte().equalsIgnoreCase(ORMHelper.getIdcomuneAlias())) {
		    // 1) dalla request recupero i datiAttività.altriDati
		    Map<String, String> listaModelli = new HashMap<String, String>();
		    List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
		    // 2) dalla lista recupero solo quelli i cui nomeparametro == NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT
		    for (ParametroType parametroType : altriDati) {
			if (parametroType.getNome().equalsIgnoreCase(NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT)) {
			    List<ValoreParametroType> list = parametroType.getValore();
			    if (list != null && !list.isEmpty()) {
				listaModelli.put(list.get(0).getCodice(), list.get(0).getCodice());
			    }
			}
		    }
		    // 3) ogni valore recuperato rappresenta una scheda dinamica per la quale recuperare i dati ed inserirli
		    // legati alla nuova pratica presa da request.getDatiAttivita().getIdPratica().
		    Integer idPraticaMittente = Integer.parseInt(request.getRifPraticaMittente().getIdPratica());
		    PkId idIstanza = new PkId(idPraticaMittente);
		    Istanze istanzaMittente = istanzeService.findById(idIstanza);
		    Integer codiceIstanzaDestinatario = Integer.parseInt(request.getDatiAttivita().getIdPratica());
		    if (istanzaMittente == null) {
			// non lancio l'errore
			log.warn("non è stata trovata la pratica su NLA mittente con codice istanza={}", idPraticaMittente);
			return;
		    }
		    Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanzaDestinatario));
		    if (istanzaDestinatario == null) {
			// non lancio l'errore
			log.warn("non è stata trovata la pratica su NLA destinatario con codice istanza={}", codiceIstanzaDestinatario);
			return;
		    }
		    PkId movimentoId = new PkId();
		    int codiceMovimento = movResp.getId().getCodice();
		    movimentoId.setCodice(codiceMovimento);
		    Movimenti movimento = movimentiNoSecurityService.findById(movimentoId);
		    if (movimento == null) {
			// non lancio l'errore
			log.warn("non è stato trovato il movimento su NLA destinatario con codice={}", codiceMovimento);
			return;
		    }
		    // 4) devo inserire su istanzedyn2dati, istanzedyn2modellit, movimentidyn2modellit
		    for (Map.Entry<String, String> entry : listaModelli.entrySet()) {
			Integer idModelloInt = Integer.parseInt(entry.getKey());
			PkId idModello = new PkId(idModelloInt);
			Dyn2Modellit modello = dyn2ModellitService.findById(idModello);
			if (modello != null) {
			    // Inserisco istanzedyn2modellit
			    Istanzedyn2modellitId testataIdModello = new Istanzedyn2modellitId();
			    testataIdModello.setCodiceistanza(istanzaDestinatario.getId().getCodice());
			    testataIdModello.setFkD2mtId(idModelloInt);
			    Istanzedyn2modellit testataModelli = istanzedyn2modellitService.findById(testataIdModello);
			    if (testataModelli == null) {
				testataModelli = new Istanzedyn2modellit();
				testataModelli.setId(testataIdModello);
				testataModelli.setDyn2Modellit(modello);
				testataModelli.setIstanza(istanzaDestinatario);
				istanzedyn2modellitService.insert(testataModelli);
			    }
			    // Inserisco istanzedyn2dati
			    List<Istanzedyn2dati> datiIstanza = istanzedyn2datiService.findByIstanzaAndModello(idIstanza, idModello);
			    for (Istanzedyn2dati istanzedyn2dati : datiIstanza) {
				Istanzedyn2datiId idDatoDest = new Istanzedyn2datiId();
				idDatoDest.setCodiceistanza(istanzaDestinatario.getId().getCodice());
				idDatoDest.setFkD2cId(istanzedyn2dati.getId().getFkD2cId());
				idDatoDest.setIndice(istanzedyn2dati.getId().getIndice());
				idDatoDest.setIndiceMolteplicita(istanzedyn2dati.getId().getIndiceMolteplicita());
				Istanzedyn2dati datoDinamicoDest = istanzedyn2datiService.findById(idDatoDest);
				if (datoDinamicoDest == null) {
				    datoDinamicoDest = new Istanzedyn2dati();
				    datoDinamicoDest.setId(idDatoDest);
				    datoDinamicoDest.setValore(istanzedyn2dati.getValore());
				    datoDinamicoDest.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
				    datoDinamicoDest.setIstanza(istanzaDestinatario);
				    datoDinamicoDest.setDyn2Campi(istanzedyn2dati.getDyn2Campi());
				    istanzedyn2datiService.insert(datoDinamicoDest);
				} else {
				    datoDinamicoDest.setValore(istanzedyn2dati.getValore());
				    datoDinamicoDest.setValoredecodificato(istanzedyn2dati.getValoredecodificato());
				    istanzedyn2datiService.update(datoDinamicoDest);
				}
			    }
			    // Inserisco movimentidyn2modellit
			    Movimentidyn2modellitId idMovimento = new Movimentidyn2modellitId();
			    idMovimento.setCodicemovimento(codiceMovimento);
			    idMovimento.setFkD2mtId(idModelloInt);
			    Movimentidyn2modellit testataModelliMov = movimentidyn2modellitService.findById(idMovimento);
			    if (testataModelliMov == null) {
				testataModelliMov = new Movimentidyn2modellit();
				testataModelliMov.setId(idMovimento);
				testataModelliMov.setIstanza(istanzaDestinatario);
				testataModelliMov.setDyn2Modellit(modello);
				testataModelliMov.setMovimento(movimento);
				movimentidyn2modellitService.insert(testataModelliMov);
			    }
			}
		    }
		}
	    }
	}
	List<ParametroType> altriDati = request.getDatiAttivita().getAltriDati();
	if (altriDati != null) {
	    Set<String> listaModelli = listaModelliDaAltriDati(altriDati, NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT_NOMESCHEDA);
	    Set<String> codiciModelli = listaModelliDaAltriDati(altriDati, NOTIFICA_ATTIVITA_ALTRO_DATO_DYN2_MODELLIT_ID_SCHEDA_NODO_ESTERNO);
	    if (!listaModelli.isEmpty() || !codiciModelli.isEmpty()) {
		Integer codiceIstanzaDestinatario = Integer.parseInt(request.getDatiAttivita().getIdPratica());
		Istanze istanzaDestinatario = istanzeService.findById(new PkId(codiceIstanzaDestinatario));
		if (istanzaDestinatario == null) {
		    // non lancio l'errore
		    log.warn("non è stata trovata la pratica su NLA destinatario con codice istanza={}", codiceIstanzaDestinatario);
		    return;
		}
		PkId movimentoId = new PkId();
		int codiceMovimento = movResp.getId().getCodice();
		movimentoId.setCodice(codiceMovimento);
		Movimenti movimento = movimentiNoSecurityService.findById(movimentoId);
		if (movimento == null) {
		    // non lancio l'errore
		    log.warn("non è stato trovato il movimento su NLA destinatario con codice={}", codiceMovimento);
		    return;
		}
		for (String idModello : listaModelli) {
		    Dyn2Modellit modello = dyn2ModellitService.findByCodiceScheda(idModello);
		    gestisciSchedaByModello(modello, istanzaDestinatario, movimento, codiceMovimento);
		}
		for (String idModello : codiciModelli) {
		    if (Utilities.isInteger(idModello)) {
			Dyn2Modellit modello = dyn2ModellitService.findById(new PkId(Integer.parseInt(idModello)));
			if (modello != null) {
			    gestisciSchedaByModello(modello, istanzaDestinatario, movimento, codiceMovimento);
			}
		    }
		}
	    }
	}
    }

    private Set<String> listaModelliDaAltriDati(List<ParametroType> altriDati, String nomeAltroDato) {

	Set<String> ret = new HashSet<String>();
	for (ParametroType parametroType : altriDati) {
	    if (parametroType.getNome().equalsIgnoreCase(nomeAltroDato)) {
		List<ValoreParametroType> list = parametroType.getValore();
		if (list != null && !list.isEmpty()) {
		    if (list.get(0) != null && StringUtils.isNotBlank(list.get(0).getCodice())) {
			ret.add(list.get(0).getCodice().trim());
		    }
		}
	    }
	}
	return ret;
    }

    private void gestisciSchedaByModello(Dyn2Modellit modello, Istanze istanzaDestinatario, Movimenti movimento, Integer codiceMovimento) {

	if (modello != null) {
	    // Inserisco istanzedyn2modellit
	    Istanzedyn2modellitId testataIdModello = new Istanzedyn2modellitId();
	    testataIdModello.setCodiceistanza(istanzaDestinatario.getId().getCodice());
	    testataIdModello.setFkD2mtId(modello.getId().getCodice());
	    Istanzedyn2modellit testataModelli = istanzedyn2modellitService.findById(testataIdModello);
	    if (testataModelli == null) {
		testataModelli = new Istanzedyn2modellit();
		testataModelli.setId(testataIdModello);
		testataModelli.setDyn2Modellit(modello);
		testataModelli.setIstanza(istanzaDestinatario);
		istanzedyn2modellitService.insert(testataModelli);
	    }
	    // Inserisco movimentidyn2modellit
	    Movimentidyn2modellitId idMovimento = new Movimentidyn2modellitId();
	    idMovimento.setCodicemovimento(codiceMovimento);
	    idMovimento.setFkD2mtId(modello.getId().getCodice());
	    Movimentidyn2modellit testataModelliMov = movimentidyn2modellitService.findById(idMovimento);
	    if (testataModelliMov == null) {
		testataModelliMov = new Movimentidyn2modellit();
		testataModelliMov.setId(idMovimento);
		testataModelliMov.setIstanza(istanzaDestinatario);
		testataModelliMov.setDyn2Modellit(modello);
		testataModelliMov.setMovimento(movimento);
		movimentidyn2modellitService.insert(testataModelliMov);
	    }
	}
    }

    @Override
    public AggiungiDocumentiNLAResponse aggiungiDocumenti(AggiungiDocumentiNLARequest request) {

	AggiungiDocumentiNLAResponse aggiungiDocumentiNLAResponse = new AggiungiDocumentiNLAResponse();
	try {
	    Integer codiceIstanza = Integer.valueOf(request.getIdPraticaDest());
	    Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	    boolean isNodoInterno = nlaHelperService.isChiamataDaNodoInterno(request.getSportelloDestinatario(), request.getSportelloMittente(),
		    false);
	    if (istanza != null) {
		for (DocumentiType doc : request.getDocumenti()) {
		    Documentiistanza docIstanza = new Documentiistanza();
		    docIstanza.setData(new Date());
		    docIstanza.setPresente(true);
		    docIstanza.setDocumento(doc.getDocumento());
		    Oggetti oggetto = nlaHelperService.getOggettoFromDocumentiType(doc, isNodoInterno, request.getSportelloMittente(),
			    request.getSportelloDestinatario(), request.getIdPraticaDest(), request.getToken());
		    docIstanza.setOggetto(oggetto);
		    docIstanza.setIstanza(istanza);
		    documentiistanzaService.insert(docIstanza);
		}
	    } else {
		throw new Exception("Istanza non trovata con codiceIstanza=" + request.getIdPraticaDest());
	    }
	} catch (Exception e) {
	    log.error("aggiungiDocumenti", e);
	    ErroreType err = new ErroreType();
	    err.setNumeroErrore("ERR-ADD-DOC");
	    err.setDescrizione(e.getMessage());
	    aggiungiDocumentiNLAResponse.getDettaglioErrore().add(err);
	}
	return aggiungiDocumentiNLAResponse;
    }

    @DeletableCacheElements
    public void resetObjectCached() {

	mappaVerticalizzazioniListaNodiSTCInviaAllegati = new HashMap<String, String>();
	mappaVerticalizzazioniListaNodiSTCNonAggAnagrafe = new HashMap<String, String>();
    }
}
