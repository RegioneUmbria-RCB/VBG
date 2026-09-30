package it.gruppoinit.pal.gp.core.features.nodopagamenti;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.opensaml.artifact.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paevolution.ws.pagamenti_types.AnnullaPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.AttivaSessionePagamentoResponseType;
import com.paevolution.ws.pagamenti_types.AttivaSessionePagamentoType;
import com.paevolution.ws.pagamenti_types.CaricamentoMassivoPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.DatiFatturaType;
import com.paevolution.ws.pagamenti_types.DatiPagamentoType;
import com.paevolution.ws.pagamenti_types.DocumentiPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.ElencoDocumentiEsitoType;
import com.paevolution.ws.pagamenti_types.ElencoDocumentiType;
import com.paevolution.ws.pagamenti_types.ElencoPagamentiOfflineType;
import com.paevolution.ws.pagamenti_types.ElencoPosizioniDebitorieEsitoType;
import com.paevolution.ws.pagamenti_types.ElencoPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.EsitoOperazionePosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.EsitoType;
import com.paevolution.ws.pagamenti_types.FormParamType;
import com.paevolution.ws.pagamenti_types.GeneraFattureType;
import com.paevolution.ws.pagamenti_types.ImportoPagamentoWsInType;
import com.paevolution.ws.pagamenti_types.InfoConnettoreType;
import com.paevolution.ws.pagamenti_types.InserisciPosizioniDebitorieType;
import com.paevolution.ws.pagamenti_types.InviaAvvisiPagamentoType;
import com.paevolution.ws.pagamenti_types.ModificaDataFineValiditaResponseType;
import com.paevolution.ws.pagamenti_types.ModificaDataFineValiditaType;
import com.paevolution.ws.pagamenti_types.ModificaDataScadenzaResponseType;
import com.paevolution.ws.pagamenti_types.ModificaDataScadenzaType;
import com.paevolution.ws.pagamenti_types.NotificaPagamentoOffline;
import com.paevolution.ws.pagamenti_types.OperazionePosizioniDebitorieResponseType;
import com.paevolution.ws.pagamenti_types.PagamentoOfflineType;
import com.paevolution.ws.pagamenti_types.PayRequestType;
import com.paevolution.ws.pagamenti_types.PosizioneDebitoriaWsInType;
import com.paevolution.ws.pagamenti_types.RegistrazioneContabileWsInType;
import com.paevolution.ws.pagamenti_types.RiferimentoPosizioneDebitoriaType;
import com.paevolution.ws.pagamenti_types.ScaricaRicevuteTelematicheType;
import com.paevolution.ws.pagamenti_types.TipoDocumentoType;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.BollettazioneBean;
import it.gruppoinit.pal.gp.core.domain.Borsellino;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Conti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.IstanzeoneriPosdebBatchId.TipoDocumentoDaGenerare;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PageResult;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipimodalitapagamento;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DettPosizioneDebitoriaParametriEnte;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioImportoHelper;
import it.gruppoinit.pal.gp.core.domain.helper.RigaImporto;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniFrontRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.StatiPosizioniDebitorieHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoFormParamsResponseBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.AttivaSessionePagamentoResponseBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.SoggettoDebitorePagamentoHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.StatoPagamentoNodoHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.StatoPagamentoPosDebHelper;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.RipartizioneContiHelper;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.posizionidebitorie.PosizioneDebitoriaBorsellino;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.ambulanti.model.PosizioneDebitoriaBorsellinoRest;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PosizioneDebitoriaDatiEstesi;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PosizioneDebitoriaModelEsteso;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DatiPagamento;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.DettPosizioneDebitoriaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.FiltroPagamentoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.dettposizionedebitoria.VerificaStatoSuVBGServiceImpl;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoModificaDataFVPosizioneDebitoriaSuNodo;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoModificaDataScadenzaPosizioneDebitoriaSuNodo;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.eventi.EventoPosizioneDebitoriaPagata;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.CausaliType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ConnettoreType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ConnettoriListType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.ParametriListType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.rest.PosizioneDebitoriaResponseType;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettiPendenzaEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.soggettopendenza.SoggettoPendenzaService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.IIdPosizioneSuNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.IVerificaStatoSuNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.IdPosizioneSuNodoPagamenti;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoPosizioniDebitorie;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.verificastato.VerificaStatoSuNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.oneri.nodopagamenti.PosizioneDebitoriaBeanIstanzeoneri;
import it.gruppoinit.pal.gp.core.features.oneri.nodopagamenti.RateizzazionePosizionedebitoriaBeanIstanzeoneri;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniCsiService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.QrcodeService;
import it.gruppoinit.pal.gp.core.service.QrcodeService.TipoImmagine;
import it.gruppoinit.pal.gp.core.service.TipicausalioneridettaglioService;
import it.gruppoinit.pal.gp.core.service.TipimodalitapagamentoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoGiornoRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelperComparator;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelperV2;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelperV2Paged;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoRestHelper;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.SecureIdUtils;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.NodoPagamentiWsClient;

@Service
public class NodoPagamentiServiceImpl implements NodoPagamentiService {

    private static final String CODICE_VERSAMENTO_NULLO = "codice_versamento_nullo_";
    private static final Logger log = LoggerFactory.getLogger(NodoPagamentiServiceImpl.class);
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatiAppService mercatiAppService;
    @Autowired
    private AutorizzazioniCsiService autorizzazioniCsiService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private QrcodeService qrcodeService;
    @Autowired
    private DettPosizioneDebitoriaService dettPosizioneDebitoriaService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private IstanzeoneriService istanzeoneriService;
    @Autowired
    private TipicausalioneridettaglioService tipicausalioneridettaglioService;
    @Autowired
    private ContiService contiService;
    @Autowired
    private ComuniService comuniService;
    @Autowired
    private TipimodalitapagamentoService modalitaPagamento;
    @Autowired
    private IEventPublisher publisher;
    @Autowired
    private SoggettoPendenzaService soggettoPendenzaService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private ComuniassociatiService comuniassociatiService;
    @Autowired
    private IstanzeService istanzeService;

    @Override
    public StatoPagamentoNodoHelper getStatoPagamentoSpuntistaHelper(Integer idPresenza) {

	StatoPagamentoNodoHelper h = new StatoPagamentoNodoHelper();
	h.setAttivato(false);
	MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(idPresenza));
	if (mpd == null || !mpd.isSpuntista()) {
	    return h;
	}
	if (mpd.getImporto() != null) {
	    h.setImportoRegistrato(mpd.getImporto().doubleValue());
	}
	try {
	    PosteggioInfoRestBean costo = mercatiAppService.calcolaCostoPosteggio(mpd.getMercatiPresenzeT().getId().getCodice(),
		    mpd.getAutorizzazioni().getId().getCodice(), mpd.getPosteggio().getId().getCodice());
	    if (costo.getImporto() != null) {
		h.setImportoCalcolato(costo.getImporto());
	    }
	} catch (MercatiAppException e) {
	}
	DettPosizioneDebitoria pds = mpd.getDettPosizioneDebitoria();
	if (pds == null) {
	    return h;
	}
	h.setAttivato(true);
	if (mpd.getImporto() != null) {
	    h.setImportoRegistrato(mpd.getImporto().doubleValue());
	}
	StatoPagamentoPosDebHelper pd = getStatoPagamentoPosDebHelper(pds.getId().getCodice(), true);
	h.setPosizioneDebitoria(pd);
	return h;
    }

    @Override
    public StatoPagamentoPosDebHelper getStatoPagamentoPosDebHelper(Integer dettPosizioneDebitoriaId, boolean errore) {

	DettPosizioneDebitoria pds = dettPosizioneDebitoriaService.findById(new PkId(dettPosizioneDebitoriaId));
	StatoPagamentoPosDebHelper pd = new StatoPagamentoPosDebHelper();
	pd.setId(pds.getId().getCodice());
	pd.setCausale(pds.getDescrizioneCausale());
	BigDecimal importo = pds.getImportoIvato();
	pd.setImporto(importo.doubleValue());
	pd.setIuv(pds.getIuv());
	Anagrafe sd = pds.getAnagrafe();
	if (sd != null) {
	    SoggettoDebitorePagamentoHelper d = new SoggettoDebitorePagamentoHelper();
	    d.setId(sd.getId().getCodice());
	    d.setCognome(sd.getNominativo());
	    d.setNome(sd.getNome());
	    d.setEmail(sd.getEmail());
	    d.setCfPiva(StringUtils.defaultIfEmpty(sd.getCodicefiscale(), sd.getPartitaiva()));
	    pd.setDebitore(d);
	}
	StatiPosizioniDebitorieHelper stato = payStatoToBean(pds);
	pd.setStatoAttuale(stato);
	return pd;
    }

    private StatiPosizioniDebitorieHelper payStatoToBean(DettPosizioneDebitoria s) {

	StatiPosizioniDebitorieHelper r = new StatiPosizioniDebitorieHelper();
	r.setDataEvento(s.getDataUltimoStato());
	r.setDescrizione(s.getDescStato());
	r.setId(s.getId().getCodice());
	r.setStato(s.getStato());
	return r;
    }

    @Override
    public boolean isAttivoNodoPagamenti(Integer mercatipresenzeTID) {

	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(mercatipresenzeTID));
	Mercati mercato = mpt.getMercato();
	boolean isAttivo = isAttivoNodoPagamentiVerticalizzazione(mercato.getComune());
	return (isAttivo && mercato.getFlagAttivanodoPagam() != null && mercato.getFlagAttivanodoPagam().booleanValue());
    }

    private boolean isAttivoNodoPagamentiVerticalizzazione(Comuni comune) {

	String codiceComune = null;
	if (comune != null && StringUtils.isNotBlank(comune.getCodicecomune())) {
	    codiceComune = comune.getCodicecomune();
	}
	return new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
    }

    private boolean _isAttivoNodoPagamenti(Integer idMercatiPresenzaId) {

	MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(idMercatiPresenzaId));
	return this.isAttivoNodoPagamenti(mpd.getMercatiPresenzeT().getId().getCodice());
    }

    @Override
    public List<Integer> registraNuovaPosizioneDebitoria(PosizioneDebitoriaBean datiPosizione, boolean caricamentoMassivo,
	    String identificativoOperazione) throws FunzioneBusinessRemotaException {

	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, datiPosizione.getCodiceComune());
	OperazionePosizioniDebitorieResponseType responseInserimento = null;
	InserisciPosizioniDebitorieType inserisciPosizioneDebitoriaType = datiPosizione.toInserisciPosizioneDebitoriaType();
	BigDecimal importoTotale = datiPosizione.getImportoTotale();
	if (caricamentoMassivo) {
	    responseInserimento = this.caricamentoMassivoPosizioneDebitoria(inserisciPosizioneDebitoriaType,
		    new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs()), identificativoOperazione);
	} else {
	    responseInserimento = this.inserisciPosizioneDebitoria(inserisciPosizioneDebitoriaType,
		    new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs()));
	}
	//
	List<Integer> elencoPosizioni = new ArrayList<Integer>();
	if (responseInserimento.getEsito().equals(EsitoType.OK)) {
	    for (EsitoOperazionePosizioneDebitoriaType esito : responseInserimento.getPosizioniInserite().getEsitoPosizione()) {
		Integer idPosizione = esito.getIdPosizione().intValue();
		log.debug("Id posizione debitoria ricevuto {}", idPosizione);
		Set<IIdPosizioneSuNodoPagamenti> posizioni = new HashSet<IIdPosizioneSuNodoPagamenti>();
		posizioni.add(new IdPosizioneSuNodoPagamenti(datiPosizione.getCodiceFiscaleEnteCreditore(), idPosizione));
		List<VerificaStatoPosizioniDebitorie> statoPosizionis = new VerificaStatoSuNodoPagamentiServiceImpl(
			verticalizzazioneNodoPagamentiServiceImpl).verificaStato(posizioni);
		VerificaStatoPosizioniDebitorie statoPosizione = statoPosizionis.get(0);
		Anagrafe anagrafe = anagrafeService.findById(new PkId(datiPosizione.getSoggettoDebitore().getCodiceAnagrafe()));
		String uuid = esito.getUuid();
		DettPosizioneDebitoria dettPosizioneDebitoria = new DettPosizioneDebitoria(statoPosizione, importoTotale, anagrafe,
			new DettPosizioneDebitoriaParametriEnte(datiPosizione.getCodiceFiscaleEnteCreditore(), datiPosizione.getCodiceComune(),
				ORMHelper.getSoftware()),
			comuniService, uuid);
		dettPosizioneDebitoria.setCfEnteCreditore(datiPosizione.getCodiceFiscaleEnteCreditore());
		this.dettPosizioneDebitoriaService.insert(dettPosizioneDebitoria);
		elencoPosizioni.add(dettPosizioneDebitoria.getId().getCodice());
	    }
	    return elencoPosizioni;
	} else {
	    StringBuilder messaggioErrore = new StringBuilder("Inserimento posizione debitoria nel nodo dei pagamenti non andato a buon fine: ")
		    .append(responseInserimento.getMessaggio());
	    if (responseInserimento.getPosizioniInserite() != null && responseInserimento.getPosizioniInserite().getEsitoPosizione() != null
		    && !responseInserimento.getPosizioniInserite().getEsitoPosizione().isEmpty()) {
		List<EsitoOperazionePosizioneDebitoriaType> esitoPosizione = responseInserimento.getPosizioniInserite().getEsitoPosizione();
		for (EsitoOperazionePosizioneDebitoriaType eodt : esitoPosizione) {
		    messaggioErrore.append("\n posizione ").append(datiPosizione.getDescrizioneRegistrazioneContabile()).append(", errore: ")
			    .append(eodt.getCodiceErrore()).append(", messaggio: ").append(eodt.getMessaggio());
		    log.error("Errore in inserimento posizione debitoria {}, errore {}",
			    new Object[] { datiPosizione.getDescrizioneRegistrazioneContabile(), eodt.getCodiceErrore() });
		}
	    }
	    throw new FunzioneBusinessRemotaException(messaggioErrore.toString());
	}
    }

    private OperazionePosizioniDebitorieResponseType inserisciPosizioneDebitoria(InserisciPosizioniDebitorieType posizione,
	    NodoPagamentiWsClient nodoPagamentiWsClient) throws FunzioneBusinessRemotaException {

	if (posizione == null) {
	    throw new IllegalArgumentException("Impossibile inserire una posizione debitoria nulla");
	}
	OperazionePosizioniDebitorieResponseType response = nodoPagamentiWsClient.inserisciPosizioneDebitoria(posizione);
	log.debug("Esito dell'inserimento della posizione debitoria response.getEsito() {}", response.getEsito());
	log.debug("response.getEsito().equals(EsitoType.OK) {}", response.getEsito().equals(EsitoType.OK));
	return response;
    }

    private OperazionePosizioniDebitorieResponseType caricamentoMassivoPosizioneDebitoria(InserisciPosizioniDebitorieType posizione,
	    NodoPagamentiWsClient nodoPagamentiWsClient, String identificativoOperazione) throws FunzioneBusinessRemotaException {

	if (posizione == null) {
	    throw new IllegalArgumentException("Impossibile inserire una posizione debitoria nulla");
	}
	CaricamentoMassivoPosizioniDebitorieType cm = new CaricamentoMassivoPosizioniDebitorieType();
	cm.getRegistrazione().addAll(posizione.getRegistrazione());
	cm.setCfEnteCreditore(posizione.getCfEnteCreditore());
	cm.setIdentificativoOperazione(identificativoOperazione);
	OperazionePosizioniDebitorieResponseType response = nodoPagamentiWsClient.caricamentoMassivoPosizioneDebitoria(cm);
	log.debug("Esito dell'inserimento della posizione debitoria response.getEsito() {}", response.getEsito());
	log.debug("response.getEsito().equals(EsitoType.OK) {}", response.getEsito().equals(EsitoType.OK));
	return response;
    }

    private List<PosizioniDebitorieIstanzeoneriBean> inserisciPosizioneDebitoriaRateizzateSuIstanzeOneri(
	    RateizzazionePosizionedebitoriaBeanIstanzeoneri r) throws FunzioneBusinessRemotaException {

	List<PosizioniDebitorieIstanzeoneriBean> ret = new ArrayList<PosizioniDebitorieIstanzeoneriBean>();
	InserisciPosizioniDebitorieType inserisciPosizioneDebitorieType = r.toInserisciPosizioneDebitorieType();
	VerticalizzazioneNodoPagamentiServiceImpl vertService = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService,
		r.getCodiceComune());
	OperazionePosizioniDebitorieResponseType res = this.inserisciPosizioneDebitoria(inserisciPosizioneDebitorieType,
		new NodoPagamentiWsClient(vertService.urlWs()));
	StringBuilder messaggioErrore = new StringBuilder();
	log.debug("Esito dell'inserimento della posizione debitoria res.getEsito() {}", res.getEsito());
	log.debug("res.getEsito().equals(EsitoType.OK) {}", res.getEsito().equals(EsitoType.OK));
	if (res.getEsito().equals(EsitoType.OK) || res.getEsito().equals(EsitoType.PARZIALE)) { // SOLO SE INVIO
												// POSIZIONE DEBITORIA
												// SINGOLA ALTRIMENTI
												// PARZIALE
	    ElencoPosizioniDebitorieEsitoType poss = res.getPosizioniInserite();
	    for (EsitoOperazionePosizioneDebitoriaType eodt : poss.getEsitoPosizione()) {
		if (eodt.getRiferimentoClient().isEmpty()) {
		    throw new FunzioneBusinessRemotaException(
			    "La chiamata non è andata a buon fine non è stato tornato l'identificativo di istanzeoneri per la posizione debitoria ");
		}
		Integer idPosizione = eodt.getIdPosizione().intValue();
		log.debug("Id posizione debitoria ricevuto {}", idPosizione);
		Set<IIdPosizioneSuNodoPagamenti> posizioni = new HashSet<IIdPosizioneSuNodoPagamenti>();
		posizioni.add(new IdPosizioneSuNodoPagamenti(inserisciPosizioneDebitorieType.getCfEnteCreditore(), idPosizione));
		List<VerificaStatoPosizioniDebitorie> statoPosizionis = new VerificaStatoSuNodoPagamentiServiceImpl(vertService)
			.verificaStato(posizioni);
		VerificaStatoPosizioniDebitorie statoPosizione = statoPosizionis.get(0);
		Anagrafe anagrafe = anagrafeService
			.findById(new PkId(r.getPosizioneDebitoriaBeanIstanzeOneri(eodt.getRiferimentoClient()).getCodiceAnagrafe()));
		String uuid = eodt.getUuid();
		DettPosizioneDebitoria dettPosizioneDebitoria = new DettPosizioneDebitoria(statoPosizione,
			r.getImportoPosizioneDebitoria(eodt.getRiferimentoClient()), anagrafe,
			new DettPosizioneDebitoriaParametriEnte(inserisciPosizioneDebitorieType.getCfEnteCreditore(), r.getCodiceComune(),
				ORMHelper.getSoftware()),
			comuniService, uuid);// devo recuperare
                                		// importo dalla
                                		// posizione debitoria
                                		// della registrazione
                                		// contabile passata
                                		// come argomento;
		dettPosizioneDebitoria.setCfEnteCreditore(inserisciPosizioneDebitorieType.getCfEnteCreditore());
		this.dettPosizioneDebitoriaService.insert(dettPosizioneDebitoria);
		for (String codiceIo : eodt.getRiferimentoClient()) {
		    if (StringUtils.isNotEmpty(codiceIo)) {
			Istanzeoneri istOn = istanzeoneriService.findById(new PkId(Integer.parseInt(codiceIo.trim())));
			istanzeoneriService.inserisciPosizioneDebitoriaSuOnere(istOn.getId().getCodice(), dettPosizioneDebitoria.getId().getCodice());
			ret.add(new PosizioniDebitorieIstanzeoneriBean(istOn.getId().getCodice(), dettPosizioneDebitoria.getId().getCodice()));
		    }
		}
	    }
	    return ret;
	} else {
	    messaggioErrore.append("Inserimento posizione debitoria nel nodo dei pagamenti non andato a buon fine: ").append(res.getMessaggio());
	    if (res.getPosizioniInserite() != null && res.getPosizioniInserite().getEsitoPosizione() != null
		    && !res.getPosizioniInserite().getEsitoPosizione().isEmpty()) {
		List<EsitoOperazionePosizioneDebitoriaType> esitoPosizione = res.getPosizioniInserite().getEsitoPosizione();
		for (EsitoOperazionePosizioneDebitoriaType eodt : esitoPosizione) {
		    messaggioErrore.append("\n posizione ").append(eodt.getRiferimentoClient()).append(", errore: ").append(eodt.getCodiceErrore())
			    .append(", messaggio: ").append(eodt.getMessaggio());
		    log.error("Errore in inserimento posizione debitoria {}, errore {}",
			    new Object[] { eodt.getRiferimentoClient(), eodt.getCodiceErrore() });
		}
	    }
	}
	throw new FunzioneBusinessRemotaException(messaggioErrore.toString());
    }

    @Override
    public Integer registraPosizioneDebitoriaDaPresenzaSuMercato(Integer idMercatiPresenzaId) throws FunzioneBusinessRemotaException {

	CodiceDescrizioneBean es = new CodiceDescrizioneBean();
	if (!_isAttivoNodoPagamenti(idMercatiPresenzaId)) {
	    return null;
	}
	MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(idMercatiPresenzaId));
	PosteggioInfoRestBean pirb;
	try {
	    pirb = mercatiAppService.calcolaCostoPosteggio(idMercatiPresenzaId);
	} catch (MercatiAppException e1) {
	    throw new FunzioneBusinessRemotaException(e1);
	}
	PosteggioImportoHelper costoPosteggioSpuntista = pirb.getImportoHelper();
	if (costoPosteggioSpuntista != null && costoPosteggioSpuntista.getImporto() != null
		&& costoPosteggioSpuntista.getImporto().compareTo(BigDecimal.ZERO) > 0) {
	    InserisciPosizioniDebitorieType pos = new InserisciPosizioniDebitorieType();
	    pos.setAccorpaPosizioni(false);
	    String codiceComune = null;
	    if (mpd.getMercatiPresenzeT().getMercato().getComune() != null) {
		codiceComune = mpd.getMercatiPresenzeT().getMercato().getComune().getCodicecomune();
	    }
	    String software = mpd.getMercatiPresenzeT().getMercato().getSoftware().getCodice();
	    VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		    verticalizzazioniService, codiceComune);
	    String cfEnteCreditore = verticalizzazioneNodoPagamentiServiceImpl.arCodFiscEnteCreditore();
	    pos.setCfEnteCreditore(cfEnteCreditore);
	    RegistrazioneContabileWsInType r = new RegistrazioneContabileWsInType();
	    Autorizzazioni aut = mpd.getAutorizzazioni();
	    SoggettoDebitoreDaAnagrafe s = populateSoggettoDebitoreFromAutorizzazione(aut);
	    r.setSoggettoDebitore(s.toSoggettoDebitoreType());
	    Date dataregistrazione = mpd.getMercatiPresenzeT().getDataRegistrazione();
	    r.setData(Utilities.getXMLGregorianCalendar(dataregistrazione));
	    Calendar c = Calendar.getInstance();
	    c.setTime(dataregistrazione);
	    r.setAnno(c.get(Calendar.YEAR));
	    String descrizionePagamento = calcolaDescrizionePerSpuntista(mpd);
	    r.setDescrizione(descrizionePagamento);
	    BigDecimal impTotale = BigDecimal.ZERO;
	    PosizioneDebitoriaWsInType pd = new PosizioneDebitoriaWsInType();
	    List<RigaImporto> listaImporti = costoPosteggioSpuntista.getListaImporti();
	    for (RigaImporto rigaImporto : listaImporti) {
		ImportoPagamentoWsInType im = new ImportoPagamentoWsInType();
		BigDecimal importo = rigaImporto.getImporto().setScale(2, BigDecimal.ROUND_HALF_UP);
		impTotale = impTotale.add(importo);
		im.setImporto(importo);
		Conti conto = contiService.findById(rigaImporto.getConto().getId());
		String mappaturaNodoPag = conto.getMappaturanodopag();
		im.setCodiceMappatura(mappaturaNodoPag);
		pd.getImporti().add(im);
	    }
	    // per CSI al momento la data di scadenza non deve essere impostata
	    pd.setDescrizione(descrizionePagamento);
	    pd.setNumeroRata(BigInteger.valueOf(1));
	    r.getRate().add(pd);
	    r.setNote(null);
	    pos.getRegistrazione().add(r);
	    log.debug("InserisciPosizioniDebitorieType: {}", ReflectionToStringBuilder.toString(pos, ToStringStyle.MULTI_LINE_STYLE));
	    try {
		OperazionePosizioniDebitorieResponseType res = new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs())
			.inserisciPosizioneDebitoria(pos);
		String messaggioErrore = null;
		log.debug("Sono entrato res.getEsito() {}", res.getEsito());
		log.debug("Sono entrato res.getEsito().equals(EsitoType.OK) {}", res.getEsito().equals(EsitoType.OK));
		if (res.getEsito().equals(EsitoType.OK)) {
		    ElencoPosizioniDebitorieEsitoType poss = res.getPosizioniInserite();
		    EsitoOperazionePosizioneDebitoriaType eodt = poss.getEsitoPosizione().get(0);
		    log.debug("Sono entrato eodt.getStato(){}", eodt.getStato());
		    log.debug("Sono entrato");
		    Integer idPosizione = eodt.getIdPosizione().intValue();
		    log.debug("Id posizione debitoria ricevuto {}", idPosizione);
		    Set<IIdPosizioneSuNodoPagamenti> posizioni = new HashSet<IIdPosizioneSuNodoPagamenti>();
		    posizioni.add(new IdPosizioneSuNodoPagamenti(cfEnteCreditore, idPosizione));
		    List<VerificaStatoPosizioniDebitorie> statoPosizionis = new VerificaStatoSuNodoPagamentiServiceImpl(
			    verticalizzazioneNodoPagamentiServiceImpl).verificaStato(posizioni);
		    VerificaStatoPosizioniDebitorie statoPosizione = statoPosizionis.get(0);
		    Anagrafe a = anagrafeService.findById(new PkId(s.getCodiceAnagrafe()));
		    String uuid = eodt.getUuid();
		    DettPosizioneDebitoria dettPosizioneDebitoria = new DettPosizioneDebitoria(statoPosizione, impTotale, a,
			    new DettPosizioneDebitoriaParametriEnte(cfEnteCreditore, codiceComune, software), comuniService, uuid);
		    dettPosizioneDebitoria.setCfEnteCreditore(cfEnteCreditore);
		    this.dettPosizioneDebitoriaService.insert(dettPosizioneDebitoria);
		    return dettPosizioneDebitoria.getId().getCodice();
		} else {
		    messaggioErrore = "Inserimento posizione debitoria non andato a buon fine: " + res.getMessaggio();
		    if (res.getPosizioniInserite() != null && !res.getPosizioniInserite().getEsitoPosizione().isEmpty()) {
			List<EsitoOperazionePosizioneDebitoriaType> esitoPosizione = res.getPosizioniInserite().getEsitoPosizione();
			for (EsitoOperazionePosizioneDebitoriaType eodt : esitoPosizione) {
			    if (StringUtils.isNotBlank(eodt.getCodiceErrore())) {
				log.error("Errore in inserimento posizione debitoria mpd_id {}, errore {}",
					new Object[] { idMercatiPresenzaId, eodt.getCodiceErrore() });
			    }
			}
		    }
		}
		es.setCodice("500");
		es.setDescrizione(messaggioErrore);
		throw new FunzioneBusinessRemotaException(messaggioErrore);
	    } catch (FunzioneBusinessRemotaException e) {
		es.setCodice("505");
		es.setDescrizione(e.getMessage());
		throw e;
	    }
	} else {
	    es.setCodice("505");
	    es.setDescrizione("Importo non Valido. Pagamento non inserito");
	    throw new FunzioneBusinessRemotaException(es.getDescrizione());
	}
    }

    private String calcolaDescrizionePerSpuntista(MercatipresenzeD mpd) {

	return "Pagamento posteggio " + mpd.getPosteggio().getCodiceposteggio() + " del mercato " +
	       mpd.getMercatiPresenzeT().getMercato().getDescrizione() + " in data: " +
	       Utilities.formatDate(mpd.getMercatiPresenzeT().getDataRegistrazione(), false);
    }

    private SoggettoDebitoreDaAnagrafe populateSoggettoDebitoreFromAutorizzazione(Autorizzazioni aut) {

	AutorizzazioniCsi autCsi = autorizzazioniCsiService.findByAutorizzazione(aut.getId().getCodice());
	Anagrafe anagrafe = aut.getOccupante();
	if (anagrafe == null) {
	    anagrafe = aut.getAnagrafe();
	}
	if (autCsi != null && autCsi.getAnagrafe() != null) {
	    anagrafe = autCsi.getAnagrafe();
	}
	return new SoggettoDebitoreDaAnagrafe(anagrafe);
    }
    // private CausaleRegistrazioneType
    // popolaRegistrazioneCausalePerSpuntista(MercatipresenzeD mpd, String
    // descrizioneCausale) {
    //
    // // TODO DA IMPLEMENTARE DA CAPIRE SE PASSARE PER GESTIONE DI DIFFERENTI
    // TRIBUTI
    // return null;
    // }

    @Override
    public void annullaPosizioneDebitoriaSpuntista(Integer idPresenza) throws FunzioneBusinessRemotaException {

	//1. Prendo la registrazione della presenza
	MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(idPresenza));
	if (mpd == null) {
	    throw new InvalidArgumentException("Non è presente nessuna presenza con id " + mpd);
	}
	if (mpd.getDettPosizioneDebitoria() == null) {
	    return;
	}
	if (!isAttivoNodoPagamenti(mpd.getMercatiPresenzeT().getId().getCodice())) {
	    return;
	}
	AnnullaPosizioniDebitorieType pd = new AnnullaPosizioniDebitorieType();
	pd.setCfEnteCreditore(mpd.getDettPosizioneDebitoria().getCfEnteCreditore());
	ElencoPosizioniDebitorieType epa = new ElencoPosizioniDebitorieType();
	RiferimentoPosizioneDebitoriaType rpd = new RiferimentoPosizioneDebitoriaType();
	rpd.setIdPosizione(BigInteger.valueOf(mpd.getDettPosizioneDebitoria().getIdPosizioneDebitoria()));
	epa.getPosizione().add(rpd);
	pd.setPosizioniAnnullate(epa);
	String codiceComune = null;
	if (mpd.getMercatiPresenzeT().getMercato().getComune() != null) {
	    codiceComune = mpd.getMercatiPresenzeT().getMercato().getComune().getCodicecomune();
	}
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	OperazionePosizioniDebitorieResponseType res = new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs())
		.annullaPosizioneDebitoria(pd);
	if (res.getEsito().equals(EsitoType.OK)) {
	    Integer idDettPosizioneDebitoria = mpd.getDettPosizioneDebitoria().getId().getCodice();
	    mpd.setDettPosizioneDebitoria(null);
	    mercatipresenzeDService.update(mpd); // azzero la posizione debitoria della presenza
	    aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idDettPosizioneDebitoria);
	    LoggerUpdaterecord.log("#ANNULLAMENTOPOSIZIONEDEBITORIA# " + idPresenza + " " + mpd.getDettPosizioneDebitoria(),
		    userSecurityService.getCurrentlyAuthenticatedUserDetails());
	    return;
	}
	String riferimentoMessaggio = System.currentTimeMillis() + ORMHelper.getIdcomuneAlias() + "-" + ORMHelper.getSoftware();
	if (res.getPosizioniInserite() != null && !res.getPosizioniInserite().getEsitoPosizione().isEmpty()) {
	    List<EsitoOperazionePosizioneDebitoriaType> esitoPosizione = res.getPosizioniInserite().getEsitoPosizione();
	    for (EsitoOperazionePosizioneDebitoriaType eodt : esitoPosizione) {
		if (StringUtils.isNotBlank(eodt.getCodiceErrore())) {
		    log.error("RIF: {} Errore in annullamento posizione debitoria mpd_id {}, errore {}",
			    new Object[] { riferimentoMessaggio, idPresenza, eodt.getCodiceErrore() });
		}
	    }
	}
	throw new FunzioneBusinessRemotaException(
		"Non è stato possibile annullare la posizione debitoria a causa di " + res.getMessaggio() + ". rif errore:" + riferimentoMessaggio);
    }

    @Override
    public List<PagamentiMercatoRestHelper> getPagamentoByAutorizzazioneComune(MercatiService mercatiService, String numeroAutorizzazione,
	    String codiceComune) {

	Map<String, Boolean> ricevutePDFSupportate = new HashMap<String, Boolean>();
	Map<String, Boolean> downloadBollettiniSupoortato = new HashMap<String, Boolean>();
	Autorizzazioni aut = autorizzazioniService.findByNumeroAndComune(numeroAutorizzazione, codiceComune);
	if (aut == null) {
	    return new ArrayList<PagamentiMercatoRestHelper>();
	}
	Integer[] autorizzazioni = new Integer[1];
	autorizzazioni[0] = aut.getId().getCodice();
	Date dallaData = null; // le prendo tutte in questo caso l'autorizzazione è singola
	List<PagamentiMercatoRestHelper> retVal = mercatipresenzeDService.getPosizioniDebitoriePerAutorizzazione(autorizzazioni, false,
		FiltroPagamentoEnum.TUTTE, dallaData);
	for (PagamentiMercatoRestHelper mercato : retVal) {
	    Mercati m = mercatiService.findById(new PkId(mercato.getId()));
	    if (!ricevutePDFSupportate.containsKey(m.getComune().getCodicecomune())) {
		ricevutePDFSupportate.put(m.getComune().getCodicecomune(), this.nodoPagamentiSupportaRicevutaPDF(m.getComune().getCodicecomune()));
	    }
	    if (!downloadBollettiniSupoortato.containsKey(m.getComune().getCodicecomune())) {
		downloadBollettiniSupoortato.put(m.getComune().getCodicecomune(),
			this.nodoPagamentiSupportaDownloadBollettini(m.getComune().getCodicecomune()));
	    }
	    for (PagamentiMercatoGiornoRestHelper giorno : mercato.getGiorno()) {
		for (PagamentiMercatoPosizDebRestHelper pagamento : giorno.getPagamenti()) {
		    if (Boolean.TRUE.equals(pagamento.getEffettuato())) {
			if (Boolean.TRUE.equals(ricevutePDFSupportate.get(m.getComune().getCodicecomune()))) {
			    pagamento.setIdRicevuta(this.codificaIdPagamento(pagamento.getId_pagamento(), m.getComune().getCodicecomune()));
			}
		    } else {
			if (Boolean.TRUE.equals(downloadBollettiniSupoortato.get(m.getComune().getCodicecomune()))) {
			    pagamento.setIdBollettino((this.codificaIdPagamento(pagamento.getId_pagamento(), m.getComune().getCodicecomune())));
			}
		    }
		}
	    }
	}
	return retVal;
    }

    @Override
    public AttivaSessionePagamentoResponseBean attivaSessionPagamento(AttivaSessionePagamentoBean bean) throws FunzioneBusinessRemotaException {

	DettPosizioneDebitoria ppdeb = dettPosizioneDebitoriaService.findById(new PkId(bean.getId()));
	String stato = ppdeb.getStato();
	verificaStatoPerAttivaSessione(ppdeb, stato); // se già pagata/annullata non faccio ulteriori verifiche
	log.debug("Prima di chiamare l'aggiornamento dello stato per la posizione {}", bean.getId());
	VerificaStatoPosizioniDebitorie statoAttuale = this.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(bean.getId());
	stato = statoAttuale.getStatoAttuale().getCodiceStato();
	log.debug("Stato del dettaglio posizione debitoria dopo l'aggiornamento {} = {}", bean.getId(), stato);
	verificaStatoPerAttivaSessione(ppdeb, stato); // richiamo il metodo dopo l'aggiornamento
	AttivaSessionePagamentoType parameters = new AttivaSessionePagamentoType();
	parameters.setUrlRedirectEsito(bean.getUrl_ritorno());
	parameters.setCfEnteCreditore(ppdeb.getCfEnteCreditore());
	RiferimentoPosizioneDebitoriaType rpd = new RiferimentoPosizioneDebitoriaType();
	rpd.setIdPosizione(BigInteger.valueOf(ppdeb.getIdPosizioneDebitoria()));
	rpd.setIUV(ppdeb.getIuv());
	parameters.setRiferimentoPosizione(rpd);
	String codiceComune = null;
	if (ppdeb.getComune() != null) {
	    codiceComune = ppdeb.getComune().getCodicecomune();
	}
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	AttivaSessionePagamentoResponseType asp = new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs())
		.attivaSessionePagamento(parameters);
	return newAttivaSessionePagamentoResponseBean(asp);
    }

    private void verificaStatoPerAttivaSessione(DettPosizioneDebitoria dettPosizione, String statoAttuale) throws FunzioneBusinessRemotaException {

	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	String posizione = dettPosizione.getDescrizioneCausale() + " [" + dettPosizione.getId() + "], iuv: " + dettPosizione.getIuv();
	if (s.isStatoChiusoPositivamente(statoAttuale)) {
	    throw new FunzioneBusinessRemotaException("La posizione " + posizione + " risulta già pagata");
	} else if (s.isStatoAnnullato(statoAttuale)) {
	    throw new FunzioneBusinessRemotaException("La posizione " + posizione + " risulta annullata");
	}
    }

    private AttivaSessionePagamentoResponseBean newAttivaSessionePagamentoResponseBean(AttivaSessionePagamentoResponseType asp) {

	AttivaSessionePagamentoResponseBean response = new AttivaSessionePagamentoResponseBean();
	response.setEsito(asp.isEsito());
	response.setDescEsito(asp.getDescEsito());
	if (!asp.isEsito()) {
	    return response;
	}
	response.setIdSessione(asp.getIdSessione());
	response.setPayUrl(asp.getPayUrl());
	response.setSecurityDigest(asp.getSecurityDigest());
	response.setHttpMethod(asp.getHttpMethodRequired().value());
	if (asp.getFormParams() != null && asp.getFormParams().getParam() != null) {
	    List<FormParamType> params = asp.getFormParams().getParam();
	    for (FormParamType fp : params) {
		AttivaSessionePagamentoFormParamsResponseBean afp = new AttivaSessionePagamentoFormParamsResponseBean();
		afp.setParamName(fp.getParamName());
		afp.setValue(fp.getValue());
		response.getFormParams().add(afp);
	    }
	}
	return response;
    }

    @Override
    public byte[] getQrCodePagamento(Integer dettPosizioneDebitoriaId) {

	DettPosizioneDebitoria ppd = dettPosizioneDebitoriaService.findById(new PkId(dettPosizioneDebitoriaId));
	String qrcode = StringUtils.defaultIfEmpty(ppd.getQrcode(), "");
	if (StringUtils.isBlank(qrcode)) {
	    throw new RuntimeException("IUV nullo per la posizione debitoria " + ppd);
	}
	return qrcodeService.createQRcode(qrcode, 199, 199, TipoImmagine.GIF);
    }

    @Override
    public List<PagamentiMercatoRestHelper> getPagamentiAttiviByUtente(MercatiService mercatiService, Anagrafe r, boolean verificaStatoPosizione,
	    boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo, FiltroPagamentoEnum statoPagamento, Date dallaData,
	    boolean soloAutorizzazioniAttive) {

	Map<String, Boolean> ricevutePDFSupportate = new HashMap<String, Boolean>();
	Map<String, Boolean> downloadBollettiniSupoortato = new HashMap<String, Boolean>();
	List<AutorizzazioniFrontRestBean> result = mercatiAppService.findAutorizzazioniHelperByUtente(r, consideraAncheIlProprietarioTraLeAnagrafiche,
		ruolo, soloAutorizzazioniAttive);
	Integer[] autorizzazioni = new Integer[result.size()];
	int i = 0;
	for (AutorizzazioniFrontRestBean afb : result) {
	    autorizzazioni[i] = afb.getId();
	    i++;
	}
	List<PagamentiMercatoRestHelper> retVal = mercatipresenzeDService.getPosizioniDebitoriePerAutorizzazione(autorizzazioni,
		verificaStatoPosizione, statoPagamento, dallaData);
	for (PagamentiMercatoRestHelper mercato : retVal) {
	    Mercati m = mercatiService.findById(new PkId(mercato.getId()));
	    if (!ricevutePDFSupportate.containsKey(m.getComune().getCodicecomune())) {
		ricevutePDFSupportate.put(m.getComune().getCodicecomune(), this.nodoPagamentiSupportaRicevutaPDF(m.getComune().getCodicecomune()));
	    }
	    if (!downloadBollettiniSupoortato.containsKey(m.getComune().getCodicecomune())) {
		downloadBollettiniSupoortato.put(m.getComune().getCodicecomune(),
			this.nodoPagamentiSupportaDownloadBollettini(m.getComune().getCodicecomune()));
	    }
	    for (PagamentiMercatoGiornoRestHelper giorno : mercato.getGiorno()) {
		for (PagamentiMercatoPosizDebRestHelper pagamento : giorno.getPagamenti()) {
		    if (Boolean.TRUE.equals(pagamento.getEffettuato())) {
			if (Boolean.TRUE.equals(ricevutePDFSupportate.get(m.getComune().getCodicecomune()))) {
			    pagamento.setIdRicevuta(this.codificaIdPagamento(pagamento.getId_pagamento(), m.getComune().getCodicecomune()));
			}
		    } else {
			if (Boolean.TRUE.equals(downloadBollettiniSupoortato.get(m.getComune().getCodicecomune()))) {
			    pagamento.setIdBollettino((this.codificaIdPagamento(pagamento.getId_pagamento(), m.getComune().getCodicecomune())));
			}
		    }
		}
	    }
	}
	return retVal;
    }

    @Override
    public String codificaIdPagamento(Integer idPosizioneDebitoria, String codiceComune) {

	return SecureIdUtils.encode(idPosizioneDebitoria, Utilities.getToday(false), codiceComune);
    }

    private boolean nodoPagamentiSupportaDownloadBollettini(String codiceComune) {

	try {
	    InfoConnettoreType info = getInfoConnettore(codiceComune);
	    return info.isSupportaInvioAvviso();
	} catch (FunzioneBusinessRemotaException e) {
	    log.error(e.getMessage());
	    return false;
	}
    }

    private boolean nodoPagamentiSupportaRicevutaPDF(String codiceComune) {

	try {
	    InfoConnettoreType info = getInfoConnettore(codiceComune);
	    return info.isSupportaDownloadRicevuta();
	} catch (FunzioneBusinessRemotaException e) {
	    log.error(e.getMessage());
	    return false;
	}
    }

    @Override
    public void annullaPosizioneDebitoria(Integer dettPosizioneDebitoriaId) throws FunzioneBusinessRemotaException {

	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(dettPosizioneDebitoriaId));
	StatiPosizioniDebitorieConverter c = new StatiPosizioniDebitorieConverter();
	if (!c.isStatoAnnullamentoAmmesso(dett.getStato())) {
	    throw new FunzioneBusinessRemotaException("Errore nell'annullamento della posizione debitoria  " + dett.getIdPosizioneDebitoria() +
						      ", lo stato attuale " + dett.getStato() + " non consente l'annullamento");
	}
	AnnullaPosizioniDebitorieType pd = new AnnullaPosizioniDebitorieType();
	pd.setCfEnteCreditore(dett.getCfEnteCreditore());
	ElencoPosizioniDebitorieType epa = new ElencoPosizioniDebitorieType();
	RiferimentoPosizioneDebitoriaType rpd = new RiferimentoPosizioneDebitoriaType();
	rpd.setIdPosizione(BigInteger.valueOf(dett.getIdPosizioneDebitoria()));
	epa.getPosizione().add(rpd);
	pd.setPosizioniAnnullate(epa);
	String codiceComune = null;
	if (dett.getComune() != null) {
	    codiceComune = dett.getComune().getCodicecomune();
	}
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	OperazionePosizioniDebitorieResponseType res = new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs())
		.annullaPosizioneDebitoria(pd);
	if (res.getEsito().equals(EsitoType.OK)) {
	    this.aggiornaStatoPagamentoByIdDettPosizioneDebitoria(dett.getId().getCodice());
	    LoggerUpdaterecord.log("#ANNULLAMENTOPOSIZIONEDEBITORIA_2# " + dett, userSecurityService.getCurrentlyAuthenticatedUserDetails());
	    return;
	}
	String riferimentoMessaggio = System.currentTimeMillis() + ORMHelper.getIdcomuneAlias() + "-" + ORMHelper.getSoftware();
	if (res.getPosizioniInserite() != null && !res.getPosizioniInserite().getEsitoPosizione().isEmpty()) {
	    List<EsitoOperazionePosizioneDebitoriaType> esitoPosizione = res.getPosizioniInserite().getEsitoPosizione();
	    for (EsitoOperazionePosizioneDebitoriaType eodt : esitoPosizione) {
		if (StringUtils.isNotBlank(eodt.getCodiceErrore())) {
		    log.error("RIF: {} Errore in annullamento posizione debitoria mpd_id {}, errore {}",
			    new Object[] { riferimentoMessaggio, dett, eodt.getCodiceErrore() });
		}
	    }
	}
	throw new FunzioneBusinessRemotaException(
		"Non è stato possibile annullare la posizione debitoria a causa di " + res.getMessaggio() + ". rif errore:" + riferimentoMessaggio);
    }

    private DatiPagamentoType creaDatiPagamentoTypeDaDettagli(Date dataPagamento, String descrizionePagamento, BigDecimal importoPagato,
	    String modalitaPagamento) {

	DatiPagamentoType datiPagamento = new DatiPagamentoType();
	datiPagamento.setDataOraPagamento(Utilities.getXMLGregorianCalendar(dataPagamento));
	datiPagamento.setDescrizioneCausale(descrizionePagamento);
	datiPagamento.setImportoPagato(importoPagato);
	datiPagamento.setModalitaPagamento(modalitaPagamento);
	return datiPagamento;
    }

    @Override
    public void updatePosizioneDebitoriaSegnaPagataOfflineSenzaRiferimentiPagamento(Integer dettPosizioneDebitoriaId)
	    throws FunzioneBusinessRemotaException {

	this.updatePosizioneDebitoriaSegnaPagata(dettPosizioneDebitoriaId, null);
    }

    @Override
    public void updatePosizioneDebitoriaSegnaPagataOfflineConRiferimentiPagamento(DatiPagamento datiPagamento)
	    throws FunzioneBusinessRemotaException {

	DettPosizioneDebitoria dett = this.updatePosizioneDebitoriaSegnaPagata(datiPagamento.getIdDettPosizioneDebitoria(), datiPagamento);
	EventoPosizioneDebitoriaPagata evento = EventoPosizioneDebitoriaPagata.fromDatiPagamento(datiPagamento, dett.getCfEnteCreditore(), true);
	this.publisher.publish(evento);
    }

    private DettPosizioneDebitoria updatePosizioneDebitoriaSegnaPagata(Integer dettPosizioneDebitoriaId, DatiPagamento datiPagamento)
	    throws FunzioneBusinessRemotaException {

	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(dettPosizioneDebitoriaId));
	NotificaPagamentoOffline request = new NotificaPagamentoOffline();
	request.setCfEnteCreditore(dett.getCfEnteCreditore());
	ElencoPagamentiOfflineType ept = new ElencoPagamentiOfflineType();
	PagamentoOfflineType pft = new PagamentoOfflineType();
	pft.setIdPosizione(BigInteger.valueOf(dett.getIdPosizioneDebitoria()));
	pft.setIUV(dett.getIuv());
	if (datiPagamento != null) {
	    String modalitaPagamentoLocal = null;
	    Tipimodalitapagamento tmp = this.modalitaPagamento.findById(new PkId(datiPagamento.getIdModalitaPagamento()));
	    if (tmp != null) {
		modalitaPagamentoLocal = tmp.getMpDescrestesa();
	    }
	    pft.setDatiPagamento(this.creaDatiPagamentoTypeDaDettagli(datiPagamento.getDataPagamento(), datiPagamento.getDescrizione(),
		    datiPagamento.getImporto(), modalitaPagamentoLocal));
	}
	ept.getPosizione().add(pft);
	request.setPosizione(ept);
	String codiceComune = null;
	if (dett.getComune() != null) {
	    codiceComune = dett.getComune().getCodicecomune();
	}
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	OperazionePosizioniDebitorieResponseType res = new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs())
		.registraPagamentiOffline(request);
	String riferimentoMessaggio = System.currentTimeMillis() + ORMHelper.getIdcomuneAlias() + "-" + ORMHelper.getSoftware();
	LoggerUpdaterecord.log("#REGISTRA_PAGAMENTO_OFFLINE# " + dett + ", RIF: " + riferimentoMessaggio,
		userSecurityService.getCurrentlyAuthenticatedUserDetails());
	if (res.getEsito().equals(EsitoType.OK)) {
	    aggiornaStatoPagamentoByIdDettPosizioneDebitoria(dett.getId().getCodice());
	    LoggerUpdaterecord.log("#REGISTRA_PAGAMENTO_OFFLINE# ESITO OK RIF: " + riferimentoMessaggio,
		    userSecurityService.getCurrentlyAuthenticatedUserDetails());
	    return dett;
	}
	LoggerUpdaterecord.log("#REGISTRA_PAGAMENTO_OFFLINE# ESITO KO RIF: " + riferimentoMessaggio,
		userSecurityService.getCurrentlyAuthenticatedUserDetails());
	if (res.getPosizioniInserite() != null && !res.getPosizioniInserite().getEsitoPosizione().isEmpty()) {
	    List<EsitoOperazionePosizioneDebitoriaType> esitoPosizione = res.getPosizioniInserite().getEsitoPosizione();
	    for (EsitoOperazionePosizioneDebitoriaType eodt : esitoPosizione) {
		if (StringUtils.isNotBlank(eodt.getCodiceErrore())) {
		    log.error("RIF: {} Non è stato segnare come pagata la posizione debitoria {}, errore {}",
			    new Object[] { riferimentoMessaggio, dett, eodt.getCodiceErrore() });
		}
	    }
	}
	throw new FunzioneBusinessRemotaException(
		"Non è stato possibile segnare come pagata la posizione debitoria. Il messaggio di errore è il seguente: " + res.getMessaggio() +
						  ". rif errore:" + riferimentoMessaggio);
    }

    @Override
    public List<VerificaStatoPosizioniDebitorie> verificaStatoPosizioniDebitorieByIdPosizioneDebitoria(Set<Integer> fkIdPosizioniDebitorie,
	    String cfEnteCreditore) throws FunzioneBusinessRemotaException {

	Set<IIdPosizioneSuNodoPagamenti> posizioni = new HashSet<IIdPosizioneSuNodoPagamenti>();
	String codiceComune = null;
	for (Integer idPosizione : fkIdPosizioniDebitorie) {
	    if (codiceComune == null) {
		DettPosizioneDebitoria dettPosizione = dettPosizioneDebitoriaService.findByFkIdPosizioneDebitoriaAndCfEnteCreditore(idPosizione,
			cfEnteCreditore);
		codiceComune = dettPosizione.getComune().getCodicecomune();
	    }
	    posizioni.add(new IdPosizioneSuNodoPagamenti(cfEnteCreditore, idPosizione));
	}
	return new VerificaStatoSuNodoPagamentiServiceImpl(new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune))
		.verificaStato(posizioni);
    }

    @Override
    public VerificaStatoPosizioniDebitorie verificaStatoPosizioneDebitoriaByIdDettaglio(Integer idDettaglioPosizioneDebitoria)
	    throws FunzioneBusinessRemotaException {

	Set<Integer> posizioni = new HashSet<Integer>();
	posizioni.add(idDettaglioPosizioneDebitoria);
	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(idDettaglioPosizioneDebitoria));
	String codiceComune = null;
	if (dett.getComune() != null) {
	    codiceComune = dett.getComune().getCodicecomune();
	}
	IVerificaStatoSuNodoPagamenti verificaStatoService = new VerificaStatoSuNodoPagamentiServiceImpl(
		new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune));
	List<VerificaStatoPosizioniDebitorie> retVal = new VerificaStatoSuVBGServiceImpl(this.dettPosizioneDebitoriaService, verificaStatoService)
		.verificaStato(posizioni);
	return retVal.isEmpty() ? null : retVal.get(0);
    }

    @Override
    public boolean isPagamentoEffettuato(Integer idDettaglioPosizioneDebitoria) {

	if (idDettaglioPosizioneDebitoria == null) {
	    throw new InvalidArgumentException("Chiamata al metodo isPagamentoEffettuato con posizione debitoria null");
	}
	DettPosizioneDebitoria posizione = this.dettPosizioneDebitoriaService.findById(new PkId(idDettaglioPosizioneDebitoria));
	return posizione.isPagata();
    }

    @Override
    public VerificaStatoPosizioniDebitorie aggiornaStatoPagamentoByIdDettPosizioneDebitoria(Integer idDettaglioPosizioneDebitoria)
	    throws FunzioneBusinessRemotaException {

	log.debug("aggiornaStatoPagamentoByIdDettPosizioneDebitoria: {}", idDettaglioPosizioneDebitoria);
	VerificaStatoPosizioniDebitorie stato = this.verificaStatoPosizioneDebitoriaByIdDettaglio(idDettaglioPosizioneDebitoria);
	DettPosizioneDebitoria dettPosizioneDebitoria = this.dettPosizioneDebitoriaService.findById(new PkId(idDettaglioPosizioneDebitoria));
	log.debug("aggiornaStatoPagamentoByIdDettPosizioneDebitoria: {}, stato precedente {}", idDettaglioPosizioneDebitoria,
		dettPosizioneDebitoria.getStato());
	boolean statoPrecedenteChiusoPositivamente = new StatiPosizioniDebitorieConverter()
		.isStatoChiusoPositivamente(dettPosizioneDebitoria.getStato());
	dettPosizioneDebitoria.impostaStatoPagamento(stato);
	this.dettPosizioneDebitoriaService.update(dettPosizioneDebitoria);
	if (stato.getDatiPagamento() != null && !statoPrecedenteChiusoPositivamente) {
	    log.debug(
		    "aggiornaStatoPagamentoByIdDettPosizioneDebitoria: datiPagamento non nulli per la posizione debitoria {}. Lancio l'evento EventoPosizioneDebitoriaPagata",
		    idDettaglioPosizioneDebitoria);
	    EventoPosizioneDebitoriaPagata evento = EventoPosizioneDebitoriaPagata.fromDatiPagamento(stato.getDatiPagamento(),
		    dettPosizioneDebitoria.getCfEnteCreditore(), false);
	    this.publisher.publish(evento);
	}
	return stato;
    }

    @Override
    public Set<VerificaStatoPosizioniDebitorie> aggiornaStatoPagamentoByIdDettPosizioneDebitoria(Set<Integer> idDettaglioPosizioniDebitorie,
	    String cfEnteCreditore) throws FunzioneBusinessRemotaException {

	Set<Integer> rifPosizioneDebitoria = new HashSet<Integer>();
	for (Integer idDettaglioPos : idDettaglioPosizioniDebitorie) {
	    DettPosizioneDebitoria posizione = this.dettPosizioneDebitoriaService.findById(new PkId(idDettaglioPos));
	    rifPosizioneDebitoria.add(posizione.getIdPosizioneDebitoria());
	}
	return this.aggiornaStatoPagamentoByIdRiferimentoPosizioneDebitoria(rifPosizioneDebitoria, cfEnteCreditore);
    }

    @Override
    public Set<VerificaStatoPosizioniDebitorie> aggiornaStatoPagamentoByIdRiferimentoPosizioneDebitoria(Set<Integer> fkIdPosizioniDebitorie,
	    String cfEnteCreditore) throws FunzioneBusinessRemotaException {

	Set<VerificaStatoPosizioniDebitorie> ret = new HashSet<VerificaStatoPosizioniDebitorie>();
	List<VerificaStatoPosizioniDebitorie> stati = this.verificaStatoPosizioniDebitorieByIdPosizioneDebitoria(fkIdPosizioniDebitorie,
		cfEnteCreditore);
	for (VerificaStatoPosizioniDebitorie stato : stati) {
	    DettPosizioneDebitoria dettPosizioneDebitoria = this.dettPosizioneDebitoriaService
		    .findByFkIdPosizioneDebitoriaAndCfEnteCreditore(stato.getIdPosizioneDebitoria().intValue(), cfEnteCreditore);
	    boolean statoPrecedenteChiusoPositivamente = new StatiPosizioniDebitorieConverter()
		    .isStatoChiusoPositivamente(dettPosizioneDebitoria.getStato());
	    log.debug("aggiornaStatoPagamentoByIdRiferimentoPosizioneDebitoria: {}, stato precedente {}", dettPosizioneDebitoria.getId().getCodice(),
		    dettPosizioneDebitoria.getStato());
	    dettPosizioneDebitoria.impostaStatoPagamento(stato);
	    this.dettPosizioneDebitoriaService.update(dettPosizioneDebitoria);
	    if (stato.getDatiPagamento() != null && !statoPrecedenteChiusoPositivamente) {
		EventoPosizioneDebitoriaPagata evento = EventoPosizioneDebitoriaPagata.fromDatiPagamento(stato.getDatiPagamento(), cfEnteCreditore,
			false);
		this.publisher.publish(evento);
	    }
	    ret.add(stato);
	}
	return ret;
    }

    @Override
    public List<String> validaInserimentoPosizioniDebitorieDaIstanzeOneri(Set<Integer> codiciIstanzeoneri) {

	// 1. Verifico la verticalizzazione attiva
	String codiceComune = getCodComuneDaIstOneri(codiciIstanzeoneri);
	List<String> errori = new ArrayList<String>();
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	if (!verticalizzazioneNodoPagamentiServiceImpl.isAttiva()) {
	    errori.add("Attenzione, non è possibile utilizzare il nodo dei pagamenti in quanto la verticalizzazione " +
		       VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE + " non è attiva");
	    return errori;
	}
	// 2. Verifico la presenza in configurazione del codicefiscale ente creditore
	String codiceFiscaleEnteCreditore = verticalizzazioneNodoPagamentiServiceImpl.arCodFiscEnteCreditore();
	if (StringUtils.isEmpty(codiceFiscaleEnteCreditore)) {
	    errori.add("Il parametro " + VerticalizzazioneNodoPagamentiServiceImpl.AR_COD_FISC_ENTE_CREDITORE + " della verticalizzazione " +
		       VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE + " non è valorizzato!");
	}
	// 3. Verifico il set di istanze oneri
	String prefisso = "L'onere dell'istanza con id ";
	Integer codiceIstanza = null;
	String codiceVersamento = null;
	String urlWs = verticalizzazioneNodoPagamentiServiceImpl.urlWs();
	ConnettoreType mappaturaConnettore = getMappaturaConnettore(codiceFiscaleEnteCreditore, urlWs);
	if (mappaturaConnettore == null) {
	    log.error("Mappatura del connettore {} all'url {} è tornata nulla ", codiceFiscaleEnteCreditore, urlWs);
	    throw new IllegalArgumentException(
		    "Non è stata trovata una configurazione valida per " + codiceFiscaleEnteCreditore + " nel nodo dei pagamenti");
	}
	Set<String> codiciVersamento = new HashSet<String>();
	for (Integer co : codiciIstanzeoneri) {
	    Istanzeoneri iston = istanzeoneriService.findById(new PkId(co));
	    if (iston == null) {
		errori.add(prefisso + co + " non è presente nella base dati");
		continue;
	    }
	    Integer findContoAttivoByCausaleOneri = tipicausalioneridettaglioService
		    .findIdContoAttivoByCausaleOneri(iston.getTipicausalioneri().getId().getCodice());
	    if (findContoAttivoByCausaleOneri == null) {
		errori.add(prefisso + iston.getId() + " non ha impostato un conto attivo per la causale oneri \"" +
			   iston.getTipicausalioneri().getCoDescrizione() + "\" [" + iston.getTipicausalioneri().getId() + "]");
	    }
	    Conti conto = contiService.findById(new PkId(findContoAttivoByCausaleOneri));
	    if (codiceIstanza == null) {
		codiceIstanza = iston.getIstanza().getId().getCodice();
	    }
	    if (!iston.getIstanza().getId().getCodice().equals(codiceIstanza)) {
		errori.add(prefisso + co + " non è riferito alla stessa istanza degli altri oneri passati");
		continue;
	    }
	    String codiceMappaturaClient = conto.getMappaturanodopag();
	    if (StringUtils.isEmpty(codiceMappaturaClient)) {
		errori.add(prefisso + co +
			   " non è valorizzato il campo Mappatura nodo pagamenti della causale e non è possibile ricondurla ad una causale accettata dal sistema dei pagamenti");
		continue;
	    }
	    log.debug("Processo istanzeoneri {}, conto {}, mappatura {} ", new Object[] { co, conto.getId().getCodice(), codiceMappaturaClient });
	    codiciVersamento.add(getCodiceVersamentoOrDefaultFromMappatura(mappaturaConnettore, codiceMappaturaClient,
		    CODICE_VERSAMENTO_NULLO + codiceMappaturaClient));
	    log.debug("Processo istanzeoneri {}, conto {}, mappatura {}, codiceversamento {} ",
		    new Object[] { co, conto.getId().getCodice(), codiceMappaturaClient, codiceVersamento });
	    if (codiciVersamento.size() > 1) {
		errori.add(prefisso + co + " non ha la stessa codifica della causale degli altri oneri presenti");
		continue;
	    }
	    if (iston.isPresentiPosizioniDebitorie()) {
		errori.add(prefisso + iston.getId() + " ha già collegata una posizione debitoria");
	    }
	    try {
		PosizioneDebitoriaBeanIstanzeoneri.validateInput(iston, codiceFiscaleEnteCreditore, conto);
	    } catch (Exception e) {
		errori.add(prefisso + co + ". Dettaglio errore = " + e.getMessage());
	    }
	}
	return errori;
    }

    private String getCodComuneDaIstOneri(Set<Integer> codiciIstanzeoneri) {

	Integer codiceIstanzaOneri = null;
	Iterator<Integer> it = codiciIstanzeoneri.iterator();
	if (it.hasNext()) {
	    codiceIstanzaOneri = it.next();
	}
	Istanzeoneri istanzaOn = istanzeoneriService.findById(codiceIstanzaOneri);
	return istanzaOn.getIstanza().getComune().getCodicecomune();
    }

    @Override
    public List<PosizioniDebitorieIstanzeoneriBean> inserisciPosizionidebitorieDaIstanzeOneri(Set<Integer> codiciIstanzeoneri, boolean isRateizzato)
	    throws FunzioneBusinessRemotaException {

	return this.inserisciPosizionidebitorieDaIstanzeOneri(codiciIstanzeoneri, new HashSet<Integer>(), isRateizzato);
    }

    public List<PosizioniDebitorieIstanzeoneriBean> inserisciPosizionidebitorieDaIstanzeOneri(Set<Integer> codiciIstanzeoneri,
	    Set<Integer> codiciAnagrafeSoggettiAggiuntivi, boolean isRateizzato) throws FunzioneBusinessRemotaException {

	if (codiciIstanzeoneri == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo inserisciPosizionidebitorieDaIstanzeOneri senza passare il parametro codiciIstanzeoneri");
	}
	if (codiciIstanzeoneri.isEmpty()) {
	    return new ArrayList<PosizioniDebitorieIstanzeoneriBean>();
	}
	if (codiciAnagrafeSoggettiAggiuntivi == null) {
	    codiciAnagrafeSoggettiAggiuntivi = new HashSet<Integer>();
	}
	// 1. Verifico la presenza di eventuali anomalie da risollevare
	List<String> errori = validaInserimentoPosizioniDebitorieDaIstanzeOneri(codiciIstanzeoneri);
	if (!errori.isEmpty()) {
	    log.error("Errore di validazione inserimento posizione debitorie da istanze oneri:{}", errori);
	    throw new RuntimeException("Errore di validazione inserimento posizione debitorie da istanze oneri");
	}
	List<PosizioniDebitorieIstanzeoneriBean> ret = new ArrayList<PosizioniDebitorieIstanzeoneriBean>();
	// 2. Recupero il codice fiscale ente creditore
	String codiceComune = getCodComuneDaIstOneri(codiciIstanzeoneri);
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	String codiceFiscaleEnteCreditore = verticalizzazioneNodoPagamentiServiceImpl.arCodFiscEnteCreditore();
	// 3. Recupero la mappa per mappatura nodo pagamenti degli oneri passati
	Map<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>> oneri = this.findOneriConMappaturaNPValorizzataById(codiciIstanzeoneri,
		verticalizzazioneNodoPagamentiServiceImpl, codiceComune, codiceFiscaleEnteCreditore);
	// 4. In caso di rateizzazione viene mantenuto il vecchio comportamento,
	// altrimenti verranno create posizioni debitorie raggruppate a seconda della
	// mappatura
	// Recupero il richiedente dalla verticalizzazione
	SoggettiPendenzaEnum soggettoPendenza = verticalizzazioneNodoPagamentiServiceImpl.soggettoPendenza();
	if (isRateizzato) {
	    Anagrafe richiedente = soggettoPendenzaService.getSoggettoPendenza(soggettoPendenza,
		    istanzeoneriService.findById(new PkId(codiciIstanzeoneri.iterator().next())));
	    inserisciPosizioniRateizzatePerAnagrafe(oneri, richiedente, codiceFiscaleEnteCreditore, ret);
	    for (Integer codiceAnagrafe : codiciAnagrafeSoggettiAggiuntivi) {
		if (!richiedente.getId().getCodice().equals(codiceAnagrafe)) {
		    Anagrafe soggCollegato = anagrafeService.findById(new PkId(codiceAnagrafe));
		    inserisciPosizioniRateizzatePerAnagrafe(oneri, soggCollegato, codiceFiscaleEnteCreditore, ret);
		}
	    }
	    return ret;
	} else {
	    for (Map.Entry<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>> onere : oneri.entrySet()) {
		Anagrafe richiedente = soggettoPendenzaService.getSoggettoPendenza(soggettoPendenza, onere.getValue().iterator().next());
		inserisciPosizioneDebitoriaNonRateizzataPerAnagrafe(onere, richiedente, codiceFiscaleEnteCreditore, ret, codiciIstanzeoneri,
			codiceComune);
		for (Integer codiceAnagrafe : codiciAnagrafeSoggettiAggiuntivi) {
		    if (!richiedente.getId().getCodice().equals(codiceAnagrafe)) {
			Anagrafe soggCollegato = anagrafeService.findById(new PkId(codiceAnagrafe));
			inserisciPosizioneDebitoriaNonRateizzataPerAnagrafe(onere, soggCollegato, codiceFiscaleEnteCreditore, ret, codiciIstanzeoneri,
				codiceComune);
		    }
		}
	    }
	}
	return ret;
    }

    private void inserisciPosizioniRateizzatePerAnagrafe(Map<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>> oneri, Anagrafe richiedente,
	    String codiceFiscaleEnteCreditore, List<PosizioniDebitorieIstanzeoneriBean> ret) throws FunzioneBusinessRemotaException {

	List<PosizioneDebitoriaBeanIstanzeoneri> s = new ArrayList<PosizioneDebitoriaBeanIstanzeoneri>();
	for (Map.Entry<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>> onere : oneri.entrySet()) {
	    // raggruppa le posizioni debitorie per rate
	    // per ogni rata deve inviare una sola posizione debitoria, questo solo nel caso
	    // che le causali abbiano stesso codice causale people
	    // es FIRENZE SANZIONI
	    Map<Integer, Set<Istanzeoneri>> posizioniPerRata = new HashMap<Integer, Set<Istanzeoneri>>();
	    Set<Istanzeoneri> iston = onere.getValue();
	    for (Istanzeoneri io : iston) {
		Integer numeroRata = io.getNumerorata();
		Set<Istanzeoneri> oneriDellaRata = posizioniPerRata.get(numeroRata);
		if (oneriDellaRata == null) {
		    oneriDellaRata = new HashSet<Istanzeoneri>();
		}
		oneriDellaRata.add(io);
		posizioniPerRata.put(numeroRata, oneriDellaRata);
	    }
	    for (Entry<Integer, Set<Istanzeoneri>> posizioneDebitoriaBeanIstanzeoneri : posizioniPerRata.entrySet()) {
		PosizioneDebitoriaBeanIstanzeoneri pdbio = PosizioneDebitoriaBeanIstanzeoneri.newInstance(codiceFiscaleEnteCreditore,
			posizioneDebitoriaBeanIstanzeoneri.getValue(), this.tipicausalioneridettaglioService, richiedente);
		s.add(pdbio);
	    }
	}
	RateizzazionePosizionedebitoriaBeanIstanzeoneri r = new RateizzazionePosizionedebitoriaBeanIstanzeoneri(s);
	ret.addAll(this.inserisciPosizioneDebitoriaRateizzateSuIstanzeOneri(r));
    }

    private void inserisciPosizioneDebitoriaNonRateizzataPerAnagrafe(Entry<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>> onere,
	    Anagrafe richiedente, String codiceFiscaleEnteCreditore, List<PosizioniDebitorieIstanzeoneriBean> ret, Set<Integer> codiciIstanzeoneri,
	    String codiceComune) {

	PosizioneDebitoriaBeanIstanzeoneri pdbio = PosizioneDebitoriaBeanIstanzeoneri.newInstance(codiceFiscaleEnteCreditore, onere.getValue(),
		this.tipicausalioneridettaglioService, richiedente);
	Integer idDettPosizioneDebitoria = null;
	try {
	    idDettPosizioneDebitoria = this.registraNuovaPosizioneDebitoria(pdbio, false, UUID.randomUUID().toString()).get(0);
	    for (Istanzeoneri istanzeoneri : onere.getValue()) {
		istanzeoneriService.inserisciPosizioneDebitoriaSuOnere(istanzeoneri.getId().getCodice(), idDettPosizioneDebitoria);
		ret.add(new PosizioniDebitorieIstanzeoneriBean(istanzeoneri.getId().getCodice(), idDettPosizioneDebitoria));
	    }
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("Errore nell'invio della posizione debitoria per istanzeoneri {}", StringUtils.join(codiciIstanzeoneri.toArray(), ","), e);
	    ret.add(new PosizioniDebitorieIstanzeoneriBean(codiciIstanzeoneri.iterator().next(), e.getMessage()));
	}
    }

    @Override
    public boolean checkDocumentiForDettPosizioneDebitoria(Integer idDettPosizioneDebitoria, TipoDocumentoDaGenerare tipoDocumento,
	    String codiceComune) throws FunzioneBusinessRemotaException {

	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
	if (StringUtils.isEmpty(verticalizzazioneNodoPagamentiServiceImpl.arCodFiscEnteCreditore())) {
	    throw new IllegalArgumentException(
		    "Il parametro " + VerticalizzazioneNodoPagamentiServiceImpl.AR_COD_FISC_ENTE_CREDITORE + " della verticalizzazione " +
					       VerticalizzazioneNodoPagamentiServiceImpl.NOME_VERTICALIZZAZIONE + " non è valorizzato!");
	}
	DocumentiPosizioneDebitoriaType r = new DocumentiPosizioneDebitoriaType();
	r.setCfEnteCreditore(dett.getCfEnteCreditore());
	RiferimentoPosizioneDebitoriaType rp = new RiferimentoPosizioneDebitoriaType();
	rp.setIdPosizione(BigInteger.valueOf(dett.getIdPosizioneDebitoria()));
	TipoDocumentoType tdt = TipoDocumentoType.fromValue(tipoDocumento.name());
	r.setTipoDocumento(tdt);
	r.setRiferimentoPosizione(rp);
	ElencoDocumentiType documentiPosizione = new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs()).documentiPosizione(r);
	if (StringUtils.isNotBlank(documentiPosizione.getErroreTipoDocumento())) {
	    log.error("checkDocumentiForDettPosizioneDebitoria: {} ", documentiPosizione.getErroreTipoDocumento());
	    return false;
	}
	return documentiPosizione.getTipoDocumento().contains(tdt);
    }

    @Override
    public ElencoDocumentiEsitoType generaFattura(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException {

	if (idDettPosizioneDebitoria == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo generaFattura(Integer idDettPosizioneDebitoria) senza passare il parametro idDettPosizioneDebitoria");
	}
	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
	GeneraFattureType request = new GeneraFattureType();
	request.setCfEnteCreditore(dett.getCfEnteCreditore());
	DatiFatturaType df = new DatiFatturaType();
	df.setIdPosizione(BigInteger.valueOf(dett.getIdPosizioneDebitoria()));
	request.getFatturaPerPosizione().add(df);
	String codiceComune = null;
	if (dett.getComune() != null) {
	    codiceComune = dett.getComune().getCodicecomune();
	}
	String urlWs = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).urlWs();
	return new NodoPagamentiWsClient(urlWs).generaFattura(request);
    }

    @Override
    public ElencoDocumentiEsitoType scaricaRicevutaTelematica(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException {

	if (idDettPosizioneDebitoria == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo scaricaRicevutaTelematica(Integer idDettPosizioneDebitoria) senza passare il parametro idDettPosizioneDebitoria");
	}
	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
	ScaricaRicevuteTelematicheType request = new ScaricaRicevuteTelematicheType();
	request.setCfEnteCreditore(dett.getCfEnteCreditore());
	RiferimentoPosizioneDebitoriaType riferimentoPosizione = new RiferimentoPosizioneDebitoriaType();
	riferimentoPosizione.setIdPosizione(BigInteger.valueOf(dett.getIdPosizioneDebitoria()));
	request.getRiferimentoPosizione().add(riferimentoPosizione);
	String codiceComune = null;
	if (dett.getComune() != null) {
	    codiceComune = dett.getComune().getCodicecomune();
	}
	String urlWs = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).urlWs();
	return new NodoPagamentiWsClient(urlWs).scaricaRicevutaTelematica(request);
    }

    @Override
    public ElencoDocumentiEsitoType inviaAvviso(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException {

	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
	if (StringUtils.isEmpty(dett.getCfEnteCreditore())) {
	    throw new IllegalArgumentException("Nella posizione debitoria non è indicato il codice dell'ente creditore");
	}
	InviaAvvisiPagamentoType request = new InviaAvvisiPagamentoType();
	request.setCfEnteCreditore(dett.getCfEnteCreditore());
	RiferimentoPosizioneDebitoriaType pd = new RiferimentoPosizioneDebitoriaType();
	pd.setIdPosizione(BigInteger.valueOf(dett.getIdPosizioneDebitoria()));
	request.getRiferimentoPosizione().add(pd);
	String codiceComune = null;
	if (dett.getComune() != null) {
	    codiceComune = dett.getComune().getCodicecomune();
	}
	String urlWs = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).urlWs();
	return new NodoPagamentiWsClient(urlWs).inviaAvvisoPagamento(request);
    }

    @Override
    public PosizioneDebitoriaResponseType dettaglioPosizioneDebitoria(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException {

	DettPosizioneDebitoria dett = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
	String codiceComune = null;
	if (dett.getComune() != null) {
	    codiceComune = dett.getComune().getCodicecomune();
	}
	String urlWs = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).urlWs();
	return new NodoPagamentiWsClient(urlWs).getDettaglioPosizioneDebitoria(dett.getCfEnteCreditore(), dett.getIdPosizioneDebitoria());
    }

    @Override
    public boolean isFunzionalitaAvvisoAbilitatoPerConnettore(DettPosizioneDebitoria dettPosizioneDebitoria) throws FunzioneBusinessRemotaException {

	return this.getInfoConnettoreByIdPosizioneDebitoria(dettPosizioneDebitoria).isSupportaInvioAvviso();
    }

    private Map<String, InfoConnettoreType> mappaInfoConnettore = new HashMap<String, InfoConnettoreType>();

    @Override
    public InfoConnettoreType getInfoConnettoreByIdPosizioneDebitoria(DettPosizioneDebitoria dettPosizioneDebitoria)
	    throws FunzioneBusinessRemotaException {

	if (dettPosizioneDebitoria == null) {
	    throw new IllegalArgumentException(
		    "Impossibile richiamare getInfoConnettore(dettPosizioneDebitoria) senza passare il parametro dettPosizioneDebitoria");
	}
	String key = getInfoConnettoreKey(dettPosizioneDebitoria.getCfEnteCreditore());
	InfoConnettoreType ret = mappaInfoConnettore.get(key);
	if (ret == null) {
	    PayRequestType rt = new PayRequestType();
	    rt.setCfEnteCreditore(dettPosizioneDebitoria.getCfEnteCreditore());
	    String codiceComune = null;
	    if (dettPosizioneDebitoria.getComune() != null) {
		codiceComune = dettPosizioneDebitoria.getComune().getCodicecomune();
	    }
	    VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		    verticalizzazioniService, codiceComune);
	    ret = new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs()).infoConnettore(rt);
	    mappaInfoConnettore.put(key, ret);
	}
	return ret;
    }

    @Override
    public InfoConnettoreType getInfoConnettore(String codiceComune) throws FunzioneBusinessRemotaException {

	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	String cfEnteCreditore = verticalizzazioneNodoPagamentiServiceImpl.arCodFiscEnteCreditore();
	String key = getInfoConnettoreKey(cfEnteCreditore);
	InfoConnettoreType ret = mappaInfoConnettore.get(key);
	if (ret != null) {
	    return ret;
	}
	PayRequestType rt = new PayRequestType();
	rt.setCfEnteCreditore(cfEnteCreditore);
	ret = new NodoPagamentiWsClient(verticalizzazioneNodoPagamentiServiceImpl.urlWs()).infoConnettore(rt);
	mappaInfoConnettore.put(key, ret);
	return ret;
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	mappaInfoConnettore = new HashMap<String, InfoConnettoreType>();
    }

    private String getInfoConnettoreKey(String cfEnteCreditore) {

	return ORMHelper.getIdcomune() + "_" + cfEnteCreditore;
    }

    @Override
    public void modificaDataScadenzaPosizioneDebitoria(Integer idDettPosizioneDebitoria, Date datascadenza) throws FunzioneBusinessRemotaException {

	log.debug("modificaDataScadenzaPosizioneDebitoria: {},{}", idDettPosizioneDebitoria, datascadenza);
	DettPosizioneDebitoria dpd = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
	if (dpd == null) {
	    log.error("modificaDataScadenzaPosizioneDebitoria: posizione debitoria non trovata {},{}", idDettPosizioneDebitoria, datascadenza);
	    throw new InvalidArgumentException("Posizione debitoria non trovata con il codice " + idDettPosizioneDebitoria);
	}
	if (datascadenza == null) {
	    log.error("modificaDataScadenzaPosizioneDebitoria: datascadenza nulla o non valida {},{}", idDettPosizioneDebitoria, datascadenza);
	    throw new InvalidArgumentException("Datascadenza nulla o non valida ");
	}
	ModificaDataScadenzaType request = new ModificaDataScadenzaType();
	request.setCfEnteCreditore(dpd.getCfEnteCreditore());
	RiferimentoPosizioneDebitoriaType rif = new RiferimentoPosizioneDebitoriaType();
	rif.setIdPosizione(BigInteger.valueOf(dpd.getIdPosizioneDebitoria()));
	request.setDataScadenza(Utilities.getXMLGregorianCalendar(datascadenza));
	request.setRiferimentoPosizione(rif);
	ModificaDataScadenzaResponseType response = null;
	String codiceComune = null;
	if (dpd.getComune() != null) {
	    codiceComune = dpd.getComune().getCodicecomune();
	}
	String urlWs = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).urlWs();
	response = new NodoPagamentiWsClient(urlWs).modificaDataScadenzaPosizioneDebitoria(request);
	if (response.getEsito().compareTo(EsitoType.OK) == 0) {
	    // rilancio l'evento EventoModificaDataScadenzaPosizioneDebitoriaSuNodo
	    EventoModificaDataScadenzaPosizioneDebitoriaSuNodo evento = new EventoModificaDataScadenzaPosizioneDebitoriaSuNodo(
		    idDettPosizioneDebitoria, datascadenza);
	    this.publisher.publish(evento);
	} else {
	    String messaggio = "Si sono verificati dei problemi nell'aggiornamento della data di scadenza della posizione debitoria con id " +
			       dpd.getId() + ". Dettaglio: " + response.getDescrizioneEsito();
	    log.error("modificaDataScadenzaPosizioneDebitoria {}", messaggio);
	    throw new FunzioneBusinessRemotaException(messaggio);
	}
    }

    @Override
    public void modificaDataFineValiditaPosizioneDebitoria(Integer idDettPosizioneDebitoria, Integer idBlackList, Date datafinevalidita)
	    throws FunzioneBusinessRemotaException {

	log.debug("modificaDataFineValiditaPosizioneDebitoria: {},{}", idDettPosizioneDebitoria, datafinevalidita);
	DettPosizioneDebitoria dpd = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
	if (dpd == null) {
	    log.error("modificaDataFineValiditaPosizioneDebitoria: posizione debitoria non trovata {},{}", idDettPosizioneDebitoria,
		    datafinevalidita);
	    throw new InvalidArgumentException("Posizione debitoria non trovata con il codice " + idDettPosizioneDebitoria);
	}
	if (datafinevalidita == null) {
	    log.error("modificaDataScadenzaPosizioneDebitoria: datascadenza nulla o non valida {},{}", idDettPosizioneDebitoria, datafinevalidita);
	    throw new InvalidArgumentException("Datascadenza nulla o non valida ");
	}
	ModificaDataFineValiditaType request = new ModificaDataFineValiditaType();
	request.setCfEnteCreditore(dpd.getCfEnteCreditore());
	RiferimentoPosizioneDebitoriaType rif = new RiferimentoPosizioneDebitoriaType();
	rif.setIdPosizione(BigInteger.valueOf(dpd.getIdPosizioneDebitoria()));
	//	GregorianCalendar gc = new GregorianCalendar();
	//	gc.setTime(datafinevalidita);
	//	gc.set(Calendar.HOUR_OF_DAY, 0);
	//	gc.set(Calendar.MINUTE, 0);
	//	gc.set(Calendar.SECOND, 0);
	//	gc.set(Calendar.MILLISECOND, 0);
	//	gc.add(Calendar.DAY_OF_MONTH, -1);
	//	
	//	request.setDataFineValidita(Utilities.getXMLGregorianCalendar(gc.getTime()));
	request.setDataFineValidita(Utilities.getXMLGregorianCalendar(datafinevalidita));
	request.setRiferimentoPosizione(rif);
	ModificaDataFineValiditaResponseType response = null;
	String codiceComune = null;
	if (dpd.getComune() != null) {
	    codiceComune = dpd.getComune().getCodicecomune();
	}
	String urlWs = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).urlWs();
	response = new NodoPagamentiWsClient(urlWs).modificaDataFineValiditaPosizioneDebitoria(request);
	if (response.getEsito().compareTo(EsitoType.OK) == 0) {
	    // rilancio l'evento EventoModificaDataScadenzaPosizioneDebitoriaSuNodo
	    EventoModificaDataFVPosizioneDebitoriaSuNodo evento = new EventoModificaDataFVPosizioneDebitoriaSuNodo(idDettPosizioneDebitoria,
		    idBlackList, datafinevalidita);
	    this.publisher.publish(evento);
	} else {
	    String messaggio = "Si sono verificati dei problemi nell'aggiornamento della data di finevalidita della posizione debitoria con id " +
			       dpd.getId() + ". Dettaglio: " + response.getDescrizioneEsito();
	    log.error("modificaDataFineValiditaPosizioneDebitoria {}", messaggio);
	    throw new FunzioneBusinessRemotaException(messaggio);
	}
    }

    @Override
    public List<ConnettoriListType> getMappaturaConnettore() {

	List<Comuniassociati> cs = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	String codiceComune = cs.get(0).getComune().getCodicecomune();
	VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamenti = new VerticalizzazioneNodoPagamentiServiceImpl(
		verticalizzazioniService, codiceComune);
	// estraggo la lista di tutti i connettori
	List<String> cfEntiCreditori = verticalizzazioneNodoPagamenti.findCfEntiCreditoriConfigurati();
	ParametriListType connettori = popolaParametri(cfEntiCreditori);
	try {
	    String urlWs = verticalizzazioneNodoPagamenti.findUrlConfigurato();
	    return new NodoPagamentiWsClient(urlWs).getMappaturaConnettore(connettori);
	} catch (Exception e) {
	    log.error("getMappaturaConnettore {}", e.getMessage());
	}
	return new ArrayList<ConnettoriListType>();
    }

    private ParametriListType popolaParametri(List<String> cfEntiCreditori) {

	ParametriListType connettori = new ParametriListType();
	List<String> parametri = new ArrayList<String>();
	for (String cf : cfEntiCreditori) {
	    parametri.add(cf);
	}
	connettori.setParametri(parametri);
	return connettori;
    }

    @Override
    public List<Istanzeoneri> findOneriInviabiliANodoPagamenti(Integer codiceIstanza, Integer codiceTipiCausali) throws Exception {

	if (codiceIstanza == null || codiceTipiCausali == null) {
	    return new ArrayList<Istanzeoneri>(0);
	}
	Conti conto = tipicausalioneridettaglioService.findContoAttivoByCausaleOneri(codiceTipiCausali);
	if (conto == null || StringUtils.isBlank(conto.getMappaturanodopag())) {
	    return new ArrayList<Istanzeoneri>(0);
	}
	String mappaturaContoCausaleInviata = conto.getMappaturanodopag();
	List<Integer> causali = getCausaliPerVersamento(codiceIstanza, mappaturaContoCausaleInviata);
	causali.add(codiceTipiCausali);
	//	cerco istanzeoneri per quel tipo causale
	//	se non ha conto attivo esco
	//	se ha conto attivo allora verifico la mappatura il cfentecreditore e
	//	chiamo il metodo rest del nodo dei pagamenti per verificare quali altre causali hanno lo stesso codicec versasmento di questa
	//	dalla mappatura risalgo alla causale e a istanzeoneri;
	return istanzeoneriService.findOneriNonPagatiESenzaPosizioniDebitoriePerCausali(codiceIstanza, causali);
    }

    private List<Integer> getCausaliPerVersamento(Integer codiceIstanza, String mappaturaContoCausaleInviata) throws Exception {

	String codComune = getComuneDaIstanza(codiceIstanza);
	VerticalizzazioneNodoPagamentiServiceImpl v = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codComune);
	if (!v.isAttiva()) {
	    return new ArrayList<Integer>();
	}
	String cfEnteCreditore = v.arCodFiscEnteCreditore();
	NodoPagamentiWsClient client = new NodoPagamentiWsClient(v.urlWs());
	ParametriListType connettori = new ParametriListType();
	List<String> ps = new ArrayList<String>();
	ps.add(cfEnteCreditore);
	connettori.setParametri(ps);
	List<ConnettoriListType> mappature = client.getMappaturaConnettore(connettori);
	ConnettoreType mappaturaConnettore = null;
	for (ConnettoriListType connettoriListType : mappature) {
	    if (connettoriListType.getConnettore().getCfCodiceProfilo().equalsIgnoreCase(cfEnteCreditore)) {
		mappaturaConnettore = connettoriListType.getConnettore();
		break;
	    }
	}
	Set<String> listaMappaturePerVersamento = getMappatureClientPerVersamento(mappaturaConnettore, mappaturaContoCausaleInviata);
	// cerco le causali oneri configurate con conto con le mappature tornate
	return istanzeoneriService.findCausaliPerMappatureConti(codiceIstanza, listaMappaturePerVersamento);
    }

    private Set<String> getMappatureClientPerVersamento(ConnettoreType mappaturaConnettore, String mappaturaContoCausaleInviata) {

	String codiceVersamento = getCodiceVersamentoPerMappatura(mappaturaConnettore, mappaturaContoCausaleInviata);
	Set<String> ret = new HashSet<String>();
	if (StringUtils.isBlank(codiceVersamento)) {
	    return ret;
	}
	List<CausaliType> causali = mappaturaConnettore.getCausali();
	for (CausaliType caus : causali) {
	    String versamento2 = caus.getCodiceVersamento();
	    // causali con lo stesso versamento, escludo la mappatura passata
	    if (StringUtils.isNotBlank(versamento2) && versamento2.equalsIgnoreCase(codiceVersamento)
		    && !caus.getMappaturaClient().equalsIgnoreCase(mappaturaContoCausaleInviata)) {
		ret.add(caus.getMappaturaClient());
	    }
	}
	return ret;
    }

    private String getCodiceVersamentoPerMappatura(ConnettoreType mappaturaConnettore, String mappaturaContoCausaleInviata) {

	List<CausaliType> causali = mappaturaConnettore.getCausali();
	log.debug("getCodiceVersamentoPerMappatura {}", mappaturaContoCausaleInviata);
	for (CausaliType caus : causali) {
	    log.debug("getCodiceVersamentoPerMappatura caus.getMappaturaClient() {}={}", caus.getMappaturaClient(), mappaturaContoCausaleInviata);
	    if (caus.getMappaturaClient().equalsIgnoreCase(mappaturaContoCausaleInviata)) {
		return caus.getCodiceVersamento();
	    }
	}
	log.debug("getCodiceVersamentoPerMappatura {} return null", mappaturaContoCausaleInviata);
	return null;
    }

    private String getComuneDaIstanza(Integer codiceIstanza) {

	return istanzeService.findById(new PkId(codiceIstanza)).getComune().getCodicecomune();
    }

    @Override
    public ConnettoreType getMappaturaConnettore(String arCfEnteCreditore, String urlWs) {

	log.debug("Cerco le mappature per il connettore {} all'url {}", arCfEnteCreditore, urlWs);
	// estraggo la lista di tutti i connettori
	List<String> cfEntiCreditori = new ArrayList<String>();
	cfEntiCreditori.add(arCfEnteCreditore);
	ParametriListType connettori = popolaParametri(cfEntiCreditori);
	try {
	    List<ConnettoriListType> mappaturaConnettore = new NodoPagamentiWsClient(urlWs).getMappaturaConnettore(connettori);
	    if (mappaturaConnettore != null && !mappaturaConnettore.isEmpty()) {
		return mappaturaConnettore.get(0).getConnettore();
	    }
	} catch (Exception e) {
	    log.error("getMappaturaConnettore", e);
	}
	return null;
    }

    private Map<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>> findOneriConMappaturaNPValorizzataById(Set<Integer> codiciIstanzeoneri,
	    VerticalizzazioneNodoPagamentiServiceImpl verticalizzazioneNodoPagamentiServiceImpl, String codiceComune,
	    String codiceFiscaleEnteCreditore) {

	if (codiciIstanzeoneri == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo findOneriConMappaturaNPValorizzataById(Set<Integer> codiciIstanzeoneri) senza passare il parametro codiciIstanzeoneri");
	}
	if (codiciIstanzeoneri.isEmpty()) {
	    return new HashMap<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>>();
	}
	List<Integer> lista = this.istanzeoneriService.findOneriConMappaturaNPById(codiciIstanzeoneri);
	if (lista.isEmpty()) {
	    log.error("non sono stati trovate mappature per gli oneri con identificativi {}", codiciIstanzeoneri);
	    throw new InvalidConfigurationException(
		    "Non sono stati trovati oneri per gli id passati: " + StringUtils.join(codiciIstanzeoneri.toArray(), ","));
	}
	String urlsWs = verticalizzazioneNodoPagamentiServiceImpl.urlWs();
	ConnettoreType mappaturaConnettore = getMappaturaConnettore(codiceFiscaleEnteCreditore, urlsWs);
	if (mappaturaConnettore == null) {
	    log.error("Mappatura del connettore {} all'url {} è tornata nulla ", codiceFiscaleEnteCreditore, urlsWs);
	    throw new IllegalArgumentException(
		    "Non è stata trovata una configurazione valida per " + codiceFiscaleEnteCreditore + " nel nodo dei pagamenti");
	}
	Map<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>> mappa = new HashMap<RiferimentiCausaleNodoPagamentoBean, Set<Istanzeoneri>>();
	for (Integer codiceIstOnere : lista) {
	    log.debug("Processo istanzeoneri {}", codiceIstOnere);
	    Istanzeoneri istanzeoneri = istanzeoneriService.findById(new PkId(codiceIstOnere));
	    Conti conto = contiService.findContoAttivoByIdCausaleOnere(istanzeoneri.getTipicausalioneri().getId().getCodice());
	    if (conto == null) {
		log.error("Nessun conto attivo per gli oneri con identificativi {}", codiciIstanzeoneri);
		throw new InvalidConfigurationException(
			"Nessun conto attivo per gli oneri con identificativi: " + StringUtils.join(codiciIstanzeoneri.toArray(), ","));
	    }
	    String codiceMappaturaClient = conto.getMappaturanodopag();
	    if (StringUtils.isBlank(codiceMappaturaClient)) {
		log.error("Conto attivo senza mappatura client per gli oneri con identificativi {}", codiciIstanzeoneri);
		throw new InvalidConfigurationException("Conto attivo senza mappatura client per gli oneri con identificativi: " +
							StringUtils.join(codiciIstanzeoneri.toArray(), ","));
	    }
	    log.debug("Processo istanzeoneri {}, conto {}, mappatura {} ",
		    new Object[] { codiceIstOnere, conto.getId().getCodice(), codiceMappaturaClient });
	    String codiceVersamento = getCodiceVersamentoOrDefaultFromMappatura(mappaturaConnettore, codiceMappaturaClient,
		    CODICE_VERSAMENTO_NULLO + codiceMappaturaClient);
	    log.debug("Processo istanzeoneri {}, conto {}, mappatura {}, codiceversamento {} ",
		    new Object[] { codiceIstOnere, conto.getId().getCodice(), codiceMappaturaClient, codiceVersamento });
	    RiferimentiCausaleNodoPagamentoBean key = new RiferimentiCausaleNodoPagamentoBean(codiceVersamento, true);
	    if (!mappa.containsKey(key)) {
		mappa.put(key, new HashSet<Istanzeoneri>());
	    }
	    mappa.get(key).add(istanzeoneri);
	}
	return mappa;
    }

    @Override
    public String getCodiceVersamentoOrDefaultFromMappatura(ConnettoreType mappaturaConnettore, String codiceMappaturaClient,
	    String defaultVersamento) {

	return StringUtils.defaultIfEmpty(getCodiceVersamentoPerMappatura(mappaturaConnettore, codiceMappaturaClient), defaultVersamento);
    }

    @Override
    public DettPosizioneDebitoria creaPosizionePerBorsellino(String codiceComune, BigDecimal importo, Borsellino borsellino,
	    RipartizioneContiHelper ripartizione) throws FunzioneBusinessRemotaException {

	VerticalizzazioneNodoPagamentiServiceImpl vnp = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune);
	String cfEnteCreditore = vnp.arCodFiscEnteCreditore();
	InserisciPosizioniDebitorieType pos = new PosizioneDebitoriaBorsellino(borsellino, vnp.arCodFiscEnteCreditore(), importo, ripartizione)
		.toInsericiPosizioneDebitoria();
	String messaggioErrore = "Inserimento posizione debitoria non andato a buon fine: ";
	try {
	    OperazionePosizioniDebitorieResponseType res = new NodoPagamentiWsClient(vnp.urlWs()).inserisciPosizioneDebitoria(pos);
	    log.debug("Sono entrato res.getEsito() {}", res.getEsito());
	    log.debug("Sono entrato res.getEsito().equals(EsitoType.OK) {}", res.getEsito().equals(EsitoType.OK));
	    if (res.getEsito().equals(EsitoType.OK)) {
		ElencoPosizioniDebitorieEsitoType poss = res.getPosizioniInserite();
		EsitoOperazionePosizioneDebitoriaType eodt = poss.getEsitoPosizione().get(0);
		log.debug("Sono entrato eodt.getStato(){}", eodt.getStato());
		log.debug("Sono entrato");
		Integer idPosizione = eodt.getIdPosizione().intValue();
		log.debug("Id posizione debitoria ricevuto {}", idPosizione);
		Set<IIdPosizioneSuNodoPagamenti> posizioni = new HashSet<IIdPosizioneSuNodoPagamenti>();
		posizioni.add(new IdPosizioneSuNodoPagamenti(cfEnteCreditore, idPosizione));
		List<VerificaStatoPosizioniDebitorie> statoPosizionis = new VerificaStatoSuNodoPagamentiServiceImpl(vnp).verificaStato(posizioni);
		VerificaStatoPosizioniDebitorie statoPosizione = statoPosizionis.get(0);
		Anagrafe a = anagrafeService.findById(new PkId(borsellino.getAnagrafe().getId().getCodice()));
		String uuid = eodt.getUuid();
		DettPosizioneDebitoria dettPosizioneDebitoria = new DettPosizioneDebitoria(statoPosizione, importo, a,
			new DettPosizioneDebitoriaParametriEnte(cfEnteCreditore, codiceComune, ORMHelper.getSoftware()), comuniService, uuid);
		dettPosizioneDebitoria.setCfEnteCreditore(cfEnteCreditore);
		this.dettPosizioneDebitoriaService.insert(dettPosizioneDebitoria);
		return dettPosizioneDebitoria;
	    } else {
		messaggioErrore = "Inserimento posizione debitoria non andato a buon fine: " + res.getMessaggio();
		if (res.getPosizioniInserite() != null && !res.getPosizioniInserite().getEsitoPosizione().isEmpty()) {
		    List<EsitoOperazionePosizioneDebitoriaType> esitoPosizione = res.getPosizioniInserite().getEsitoPosizione();
		    for (EsitoOperazionePosizioneDebitoriaType eodt : esitoPosizione) {
			if (StringUtils.isNotBlank(eodt.getCodiceErrore())) {
			    messaggioErrore += eodt.getMessaggio() + "(" + eodt.getCodiceErrore() + ")" + ", ";
			    eodt.getCodiceErrore();
			}
		    }
		}
	    }
	    log.error("Errore in inserimento posizione debitoria borsellino id {}, errore {}", new Object[] { borsellino.getId(), messaggioErrore });
	    throw new FunzioneBusinessRemotaException(messaggioErrore);
	} catch (FunzioneBusinessRemotaException e) {
	    messaggioErrore += e.getMessage();
	    log.error("Errore in inserimento posizione debitoria borsellino id {}, errore {}", new Object[] { borsellino.getId(), messaggioErrore });
	    throw new FunzioneBusinessRemotaException(messaggioErrore);
	}
    }

    @Override
    public PosizioneDebitoriaBorsellinoRest populatePosizioneDebitoriaBorsellino(Integer idPosizioneDebitoria) {

	if (idPosizioneDebitoria == null) {
	    return null;
	}
	DettPosizioneDebitoria dettPosizioneDebitoria = dettPosizioneDebitoriaService.findById(new PkId(idPosizioneDebitoria));
	if (dettPosizioneDebitoria == null) {
	    return null;
	}
	PosizioneDebitoriaBorsellinoRest pagam = new PosizioneDebitoriaBorsellinoRest();
	pagam.setIuv(dettPosizioneDebitoria.getIuv());
	pagam.setIdPosizioneDebitoria(idPosizioneDebitoria);
	BigDecimal importo = dettPosizioneDebitoria.getImportoIvato();
	pagam.setImporto(importo.doubleValue());
	StatiPosizioniDebitorieConverter s = new StatiPosizioniDebitorieConverter();
	boolean isPagabile = s.isStatoPosizionePagabileEAttivate(dettPosizioneDebitoria.getStato());
	if (isPagabile) {
	    try {
		aggiornaStatoPagamentoByIdDettPosizioneDebitoria(idPosizioneDebitoria);
	    } catch (FunzioneBusinessRemotaException e) {
		log.error("Errore nel recupero delle informazioni del connettore per la posizione debitoria " + idPosizioneDebitoria + ": " +
			  e.getMessage(),
			e);
	    }
	    dettPosizioneDebitoria = dettPosizioneDebitoriaService.findById(new PkId(idPosizioneDebitoria));
	}
	pagam.setPagabile(s.isStatoPosizionePagabileEAttivate(dettPosizioneDebitoria.getStato()));
	pagam.setPagata(s.isStatoChiusoPositivamente(dettPosizioneDebitoria.getStato()));
	pagam.setAnnullata(s.isStatoAnnullato(dettPosizioneDebitoria.getStato()));
	InfoConnettoreType infoConnettore = null;
	String codiceComune = null;
	if (dettPosizioneDebitoria.getComune() != null) {
	    codiceComune = dettPosizioneDebitoria.getComune().getCodicecomune();
	}
	try {
	    infoConnettore = this.getInfoConnettore(codiceComune);
	} catch (FunzioneBusinessRemotaException e) {
	    log.error("Errore nel recupero delle informazioni del connettore per la posizione debitoria " + idPosizioneDebitoria + ": " +
		      e.getMessage(),
		    e);
	}
	if (infoConnettore != null) {
	    if (pagam.isPagata()) {
		if (infoConnettore.isSupportaDownloadRicevuta()) {
		    pagam.setIdRicevuta(this.codificaIdPagamento(pagam.getIdPosizioneDebitoria(), codiceComune));
		}
	    } else {
		if (infoConnettore.isSupportaInvioAvviso()) {
		    pagam.setIdBollettino((this.codificaIdPagamento(pagam.getIdPosizioneDebitoria(), codiceComune)));
		}
	    }
	}
	return pagam;
    }

    @Override
    public PosizioneDebitoriaModelEsteso getPosizioneDebitoriaModelEsteso(Integer idDettPosizioneDebitoria) throws FunzioneBusinessRemotaException {

	DettPosizioneDebitoria dettPd = dettPosizioneDebitoriaService.findById(new PkId(idDettPosizioneDebitoria));
	// è importante che sia stata aggiornata la posizione debitoria 
	PosizioneDebitoriaResponseType datiNodo = dettaglioPosizioneDebitoria(idDettPosizioneDebitoria);
	PosizioneDebitoriaModelEsteso ret = PosizioneDebitoriaModelEsteso.fromDettPosizioneDebitoria(dettPd);
	ret.setDatiEstesi(PosizioneDebitoriaDatiEstesi.fromDatiNodoPagamento(datiNodo));
	return ret;
    }

    @Override
    public List<PagamentiMercatoPosizDebRestHelperV2> getPagamentiAttiviByUtenteV2(MercatiService mercatiService, Anagrafe r,
	    boolean verificaStatoPosizioni, boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo,
	    FiltroPagamentoEnum statoPagamento, Date consideraIPagamentiDallaData, boolean soloAttive) {

	List<PagamentiMercatoPosizDebRestHelperV2> ret = new ArrayList<PagamentiMercatoPosizDebRestHelperV2>();
	List<PagamentiMercatoRestHelper> pagamentiAttiviByUtente = getPagamentiAttiviByUtente(mercatiService, r, verificaStatoPosizioni,
		consideraAncheIlProprietarioTraLeAnagrafiche, ruolo, statoPagamento, consideraIPagamentiDallaData, soloAttive);
	for (PagamentiMercatoRestHelper p : pagamentiAttiviByUtente) {
	    String mercato = p.getDescrizione_mercato();
	    for (PagamentiMercatoGiornoRestHelper g : p.getGiorno()) {
		for (PagamentiMercatoPosizDebRestHelper pag : g.getPagamenti()) {
		    ret.add(fromPagamentiMercatoPosizDebRestHelper(pag, mercato, r));
		}
	    }
	}
	Collections.sort(ret, new PagamentiMercatoPosizDebRestHelperComparator());
	return ret;
    }

    private PagamentiMercatoPosizDebRestHelperV2 fromPagamentiMercatoPosizDebRestHelper(PagamentiMercatoPosizDebRestHelper p, String mercato,
	    Anagrafe r) {

	PagamentiMercatoPosizDebRestHelperV2 ret = PagamentiMercatoPosizDebRestHelperV2.fromPagamentiMercatoPosizDebRestHelper(p);
	// individuaRuolo
	if (p.getAutorizzazione() != null) {
	    ret.setRuolo(p.getAutorizzazione().individuaRuolo(p.getAutorizzazione().getTitCodicefiscale()).name());
	}
	ret.setDescrizioneMercato(mercato);
	return ret;
    }

    @Override
    public PagamentiMercatoPosizDebRestHelperV2Paged getPagamentiAttiviByUtenteV2Paged(MercatiService mercatiService, Anagrafe r,
	    boolean verificaStatoPosizioni, boolean consideraAncheIlProprietarioTraLeAnagrafiche, RuoloAutorizzazioneEnum ruolo,
	    FiltroPagamentoEnum statoPagamento, Date consideraIPagamentiDallaData, boolean soloAttive, int page, int pageSize) {

	List<PagamentiMercatoPosizDebRestHelperV2> items = getPagamentiAttiviByUtenteV2(mercatiService, r, verificaStatoPosizioni,
		consideraAncheIlProprietarioTraLeAnagrafiche, ruolo, statoPagamento, consideraIPagamentiDallaData, soloAttive);
	int fromIndex = page * pageSize;
	int toIndex = Math.min(fromIndex + pageSize, items.size());
	if (log.isDebugEnabled()) {
	    log.debug("getPagamentiAttiviByUtenteV2Paged: page {}, pageSize {}", page, pageSize);
	    log.debug("getPagamentiAttiviByUtenteV2Paged: items.size {}", items.size());
	    log.debug("getPagamentiAttiviByUtenteV2Paged: fromIndex {}, toIndex {}", fromIndex, toIndex);
	}
	if (items.isEmpty() || fromIndex >= items.size() || fromIndex < 0) {
	    log.debug("getPagamentiAttiviByUtenteV2Paged: esco senza risultati");
	    return new PagamentiMercatoPosizDebRestHelperV2Paged(items, items.size(), 1);
	}
	return new PagamentiMercatoPosizDebRestHelperV2Paged(items.subList(fromIndex, toIndex), items.size(),
		(int) Math.ceil(new Double(items.size()) / new Double(pageSize)));
    }

    @Override
    public PageResult<BollettazioneBean> getPosizioneDebitorieBollettazione(Integer[] autorizzazioniIds, boolean validata,
	    FiltroPagamentoEnum filtroPagamentoEnum, int pagina, int sizePagina) {

	return dettPosizioneDebitoriaService.getPosizioneDebitorieBollettazione(autorizzazioniIds, validata, filtroPagamentoEnum, pagina, sizePagina);
    }
}
