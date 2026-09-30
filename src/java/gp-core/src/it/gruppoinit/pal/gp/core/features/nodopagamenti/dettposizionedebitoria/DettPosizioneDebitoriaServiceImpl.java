package it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paevolution.ws.pagamenti_types.ElencoDocumentiEsitoType;
import com.paevolution.ws.pagamenti_types.EsitoDocumentoPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.InfoConnettoreType;
import com.paevolution.ws.pagamenti_types.StatoPosizioneType;
import com.paevolution.ws.pagamenti_types.VerificaStatoPosizioniResponseType;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.UpgradeDettPosizioniDebitorieBean;
import it.gruppoinit.pal.gp.core.domain.BollettazioneBean;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.PageResult;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoResponseBean;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatoPagamentiBreveJson;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatoPagamentiDettaglioJson;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaAnnullata;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaPagata;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.OperazioniConsentiteResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.StatoPosizionePerVerifica;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.DettPosizioneDebitoriaProvenienzaEnum;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

@Service
public class DettPosizioneDebitoriaServiceImpl extends BaseServiceImpl<DettPosizioneDebitoria, PkId> implements DettPosizioneDebitoriaService {

    private static final Logger log = LoggerFactory.getLogger(DettPosizioneDebitoriaServiceImpl.class);
    @Autowired
    private DettPosizioneDebitoriaDAO dettposizionedebitoriaDAO;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;

    @Override
    protected Class<DettPosizioneDebitoria> getEntityClass() {

	return DettPosizioneDebitoria.class;
    }

    @Override
    public List<DettPosizioneDebitoria> findAll(Integer firstResult, Integer maxResult) {

	return dettposizionedebitoriaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DettPosizioneDebitoria entity) {

	if (validateEntity(entity)) {
	    dettposizionedebitoriaDAO.insert(entity);
	}
    }

    @Override
    public DettPosizioneDebitoria findById(PkId id) {

	return dettposizionedebitoriaDAO.findById(id);
    }

    @Override
    public void update(DettPosizioneDebitoria entity) {

	if (validateEntity(entity)) {
	    //this.dettposizionedebitoriaDAO.evict(entity);
	    //DettPosizioneDebitoria old = new DettPosizioneDebitoria();
	    //old.setStato(this.dettposizionedebitoriaDAO.findStato(entity.getId().getCodice()));
	    dettposizionedebitoriaDAO.update(entity);
	    //if (!old.isPagata() && entity.isPagata()) {
	    //EventoPosizioneDebitoriaPagata evento = new EventoPosizioneDebitoriaPagata(entity.getIdPosizioneDebitoria());
	    //eventPublisher.publish(evento);
	    //}
	}
    }

    @Override
    public void delete(DettPosizioneDebitoria entity) {

	if (isDeleteAllowed(entity)) {
	    dettposizionedebitoriaDAO.delete(entity);
	}
    }

    @Override
    protected boolean isDeleteAllowed(DettPosizioneDebitoria entity) {

	return true;
    }

    @Override
    public Set<Integer> findFkIdPosizioneDebitoriaByIdList(Set<Integer> id) {

	return this.dettposizionedebitoriaDAO.findFkIdPosizioneDebitoriaByIdList(id);
    }

    @Override
    public StatoPagamentiBreveJson getStatoBreveById(Integer id) {

	if (id == null) {
	    return null;
	}
	DettPosizioneDebitoria pd = this.findById(new PkId(id));
	StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
	return new StatoPagamentiBreveJson(pd, c);
    }

    @Override
    public StatoPagamentiDettaglioJson getStatoDettagliatoById(Integer id) {

	if (id == null) {
	    return null;
	}
	boolean funzionalitaAvvisoAbilitatoPerConnettore = false;
	DettPosizioneDebitoria pd = this.findById(new PkId(id));
	StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
	if (c.convertStato(pd.getStato()).getId().equals(0) || c.convertStato(pd.getStato()).getId().equals(200)) {
	    try {
		funzionalitaAvvisoAbilitatoPerConnettore = nodoPagamentiService.isFunzionalitaAvvisoAbilitatoPerConnettore(pd);
	    } catch (FunzioneBusinessRemotaException e) {
		log.error("{}", e);
	    }
	}
	return new StatoPagamentiDettaglioJson(pd, c, funzionalitaAvvisoAbilitatoPerConnettore);
    }

    @Override
    public List<Integer> findCodiciDettaglioPosizioniNonPagate() {

	return this.dettposizionedebitoriaDAO.findCodiciDettaglioPosizioniNonPagate();
    }

    @Override
    public void upgradeDettPosizioniDebitorieDaBlackList() {

	List<UpgradeDettPosizioniDebitorieBean> result = dettposizionedebitoriaDAO.findDettaglioDaBlackList();
	for (UpgradeDettPosizioniDebitorieBean b : result) {
	    DettPosizioneDebitoria pd = this.findById(new PkId(b.getIdposizionedebitoria()));
	    if (pd == null) {
		// insert
		pd = new DettPosizioneDebitoria(b);
		pd.setAnagrafe(anagrafeService.findById(new PkId(b.getCodiceanagrafe())));
		this.insert(pd);
		dettposizionedebitoriaDAO.flush();
	    }
	}
    }

    @Override
    public void updateDaRiferimentoNodoPagamenti(String cf_ente_creditore, VerificaStatoPosizioniResponseType statoPosizione) {

	List<StatoPosizioneType> stati = statoPosizione.getStatoPosizioni().getStatoPosizioni();
	for (StatoPosizioneType statoType : stati) {
	    DettPosizioneDebitoria entity = this.findByFkIdPosizioneDebitoriaAndCfEnteCreditore(statoType.getIdPosizione().intValue(),
		    cf_ente_creditore);
	    if (entity != null) {
		log.debug("updateDaRiferimentoNodoPagamenti: {}, stato precedente {}", entity.getId().getCodice(), entity.getStato());
		boolean statoPrecedenteChiusoPositivamente = new StatiPosizioniDebitorieConverter().isStatoChiusoPositivamente(entity.getStato());
		ORMHelper.setSoftware(entity.getCodiceSoftware());
		String codiceComune = null;
		if (entity.getComune() != null) {
		    codiceComune = entity.getComune().getCodicecomune();
		}
		VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
			verticalizzazioniService, codiceComune);
		VerificaStatoPosizioniDebitorie stato = VerificaStatoPosizioniDebitorie.fromStatoPosizioneType(
			new StatoPosizionePerVerifica(statoType, verticalizzazioneNodoPagamentiServiceImpl.idModalitaPagamento()));
		entity.impostaStatoPagamento(stato);
		this.update(entity);
		if (stato.getDatiPagamento() != null && !statoPrecedenteChiusoPositivamente) {
		    StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
		    if (c.isStatoChiusoPositivamente(stato.getStatoAttuale().getCodiceStato())) {
			EventoPosizioneDebitoriaPagata evento = EventoPosizioneDebitoriaPagata.fromDatiPagamento(stato.getDatiPagamento(),
				cf_ente_creditore, false);
			this.eventPublisher.publish(evento);
		    }
		}
	    }
	}
    }

    @Override
    public DettPosizioneDebitoriaResponseType dettaglioPosizione(String baseUrl, int idDettPosizioneDebitoria) {

	DettPosizioneDebitoria posizione = this.findById(new PkId(idDettPosizioneDebitoria));
	if (posizione == null) {
	    throw new IllegalArgumentException("Impossibile trovare il record di dett_posizione_debitoria con id " + idDettPosizioneDebitoria);
	}
	DettPosizioneDebitoriaResponseType retVal = new DettPosizioneDebitoriaResponseType(posizione);
	//1.Recupero le info del connettore
	try {
	    InfoConnettoreType info = this.nodoPagamentiService.getInfoConnettoreByIdPosizioneDebitoria(posizione);
	    retVal.setOperazioniConsentite(new OperazioniConsentiteResponseType(info, baseUrl, posizione));
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e);
	} catch (Exception e) {
	    log.error("{}", e);
	}
	//2. Recupero gli importi
	retVal.setImporti(this.dettposizionedebitoriaDAO.recuperaImporti(idDettPosizioneDebitoria));
	return retVal;
    }

    @Override
    public DettPosizioneDebitoriaResponseType verificaStato(String baseUrl, int idDettPosizioneDebitoria) {

	DettPosizioneDebitoria posizione = this.findById(new PkId(idDettPosizioneDebitoria));
	if (posizione == null) {
	    throw new IllegalArgumentException("Impossibile trovare il record di dett_posizione_debitoria con id " + idDettPosizioneDebitoria);
	}
	DettPosizioneDebitoriaResponseType retVal = new DettPosizioneDebitoriaResponseType(posizione);
	try {
	    String cfEnteCreditore = posizione.getCfEnteCreditore();
	    //1. Se la posizione è in uno stato finale, non c'è bisogno di chiamare il verifica stato del nodo dei pagamenti altrimenti si richiama la verifica 
	    if (!posizione.isConclusa()) {
		this.nodoPagamentiService.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idDettPosizioneDebitoria);
		retVal = new DettPosizioneDebitoriaResponseType(this.findById(new PkId(idDettPosizioneDebitoria)));
	    }
	    //2.Recupero le info del connettore
	    InfoConnettoreType info = this.nodoPagamentiService.getInfoConnettoreByIdPosizioneDebitoria(posizione);
	    retVal.setOperazioniConsentite(new OperazioniConsentiteResponseType(info, baseUrl, posizione));
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e);
	} catch (Exception e) {
	    log.error("{}", e);
	}
	//2. Recupero gli importi
	retVal.setImporti(this.dettposizionedebitoriaDAO.recuperaImporti(idDettPosizioneDebitoria));
	return retVal;
    }

    @Override
    public EsitoDocumentoPosizioneDebitoriaType generaAvviso(Integer idDettPosizioneDebitoria) {

	try {
	    ElencoDocumentiEsitoType esito = this.nodoPagamentiService.inviaAvviso(idDettPosizioneDebitoria);
	    if (esito.getEsitoPosizione().size() > 1) {
		throw new RuntimeException("Sono state trovati più avvisi per la posizione debitoria con id " + idDettPosizioneDebitoria);
	    }
	    if (esito.getEsitoPosizione().isEmpty()) {
		return new EsitoDocumentoPosizioneDebitoriaType();
	    }
	    return esito.getEsitoPosizione().get(0);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e);
	    throw new RuntimeException(e);
	}
    }

    @Override
    public EsitoDocumentoPosizioneDebitoriaType generaFattura(Integer idDettPosizioneDebitoria) {

	try {
	    ElencoDocumentiEsitoType esito = this.nodoPagamentiService.generaFattura(idDettPosizioneDebitoria);
	    if (esito.getEsitoPosizione().size() > 1) {
		throw new RuntimeException("Sono state trovate più fatture per la posizione debitoria con id " + idDettPosizioneDebitoria);
	    }
	    if (esito.getEsitoPosizione().isEmpty()) {
		return new EsitoDocumentoPosizioneDebitoriaType();
	    }
	    return esito.getEsitoPosizione().get(0);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e);
	    throw new RuntimeException(e);
	}
    }

    @Override
    public EsitoDocumentoPosizioneDebitoriaType generaRicevutaTelematica(Integer idDettPosizioneDebitoria) {

	try {
	    ElencoDocumentiEsitoType esito = this.nodoPagamentiService.scaricaRicevutaTelematica(idDettPosizioneDebitoria);
	    if (esito.getEsitoPosizione().size() > 1) {
		throw new RuntimeException(
			"Sono state trovate più ricevute telematiche per la posizione debitoria con id " + idDettPosizioneDebitoria);
	    }
	    if (esito.getEsitoPosizione().isEmpty()) {
		return new EsitoDocumentoPosizioneDebitoriaType();
	    }
	    return esito.getEsitoPosizione().get(0);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e);
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void pagaOffline(Integer idDettPosizioneDebitoria) {

	try {
	    this.nodoPagamentiService.updatePosizioneDebitoriaSegnaPagataOfflineSenzaRiferimentiPagamento(idDettPosizioneDebitoria);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e);
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void registraPagamentoOffline(DatiPagamento datiPagamento) {

	try {
	    this.nodoPagamentiService.updatePosizioneDebitoriaSegnaPagataOfflineConRiferimentiPagamento(datiPagamento);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e);
	    throw new RuntimeException(e);
	}
    }

    @Override
    public AttivaSessionePagamentoResponseBean attivaSessionePagamento(Integer idDettPosizioniDebitoria) {

	try {
	    AttivaSessionePagamentoBean attivaSessionePagamentoBean = new AttivaSessionePagamentoBean();
	    attivaSessionePagamentoBean.setId(idDettPosizioniDebitoria);
	    attivaSessionePagamentoBean.setUrl_ritorno("");
	    return this.nodoPagamentiService.attivaSessionPagamento(attivaSessionePagamentoBean);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("{}", e);
	    throw new RuntimeException(e);
	}
    }

    @Override
    public DettPosizioneDebitoria findByFkIdPosizioneDebitoriaAndCfEnteCreditore(Integer fkIdPosizioneDebitoria, String cfEnteCreditore) {

	return this.dettposizionedebitoriaDAO.findByFkIdPosizioneDebitoriaAndCfEnteCreditore(fkIdPosizioneDebitoria, cfEnteCreditore);
    }

    @Override
    public void annullaPosizioneDebitoria(Integer idDettPosizioniDebitoria, String noteAnnullamento) throws RuntimeException {

	if (noteAnnullamento == null || noteAnnullamento.length() == 0) {
	    throw new RuntimeException("Errore nell'annullamento della posizione debitoria " + idDettPosizioniDebitoria.toString() +
				       ", specificare la ragione dell'annullamento");
	}
	try {
	    this.nodoPagamentiService.annullaPosizioneDebitoria(idDettPosizioniDebitoria);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("Errore durante l'annullamento della posizione {}: {}", idDettPosizioniDebitoria, e);
	    throw new RuntimeException(
		    "Errore nell'annullamento della posizione debitoria " + idDettPosizioniDebitoria.toString() + ", errore: " + e.getMessage(), e);
	}
	this.eventPublisher.publish(new EventoPosizioneDebitoriaAnnullata(idDettPosizioniDebitoria, noteAnnullamento));
    }

    @Override
    public DettPosizioneDebitoria findByCfEnteEUuid(String cfEnte, String uuid) {

	return this.dettposizionedebitoriaDAO.findByCfEnteEUuid(cfEnte, uuid);
    }

    @Override
    public DettPosizioneDebitoriaProvenienzaEnum provenienza(Integer idPosizioneDebitoria) {

	// recupero la provenienza a partire dall'id pos deb
	DettPosizioneDebitoriaProvenienzaEnum provenienzaEnum = dettposizionedebitoriaDAO.calcolaProvenienza(idPosizioneDebitoria);
	return provenienzaEnum;
    }

    @Override
    public PageResult<BollettazioneBean> getPosizioneDebitorieBollettazione(Integer[] autorizzazioniIds, boolean validata,
	    FiltroPagamentoEnum filtroPagamentoEnum, int pagina, int sizePagina) {

	return dettposizionedebitoriaDAO.getPosizioneDebitorieBollettazione(autorizzazioniIds, validata, filtroPagamentoEnum, pagina, sizePagina);
    }
}
