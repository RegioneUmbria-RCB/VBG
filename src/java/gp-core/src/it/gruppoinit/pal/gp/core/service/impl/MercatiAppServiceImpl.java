package it.gruppoinit.pal.gp.core.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MultivaluedMap;

import org.apache.axis.encoding.Base64;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.opensaml.artifact.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AnagrafeDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.AnagrafeTrovataInEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.dao.helper.RicercaAnagraficeCollegateEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniCsi;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSoggetti;
import it.gruppoinit.pal.gp.core.domain.BattitoriCsi;
import it.gruppoinit.pal.gp.core.domain.BlacklistAutorizzazioni;
import it.gruppoinit.pal.gp.core.domain.BlacklistMotivi;
import it.gruppoinit.pal.gp.core.domain.BlacklistSrcPDebSp;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.Esportazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiResponsabili;
import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.domain.TmpEsportazioni;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.AutorizzazioniCsiRestBean;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.ChiaveValoreBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AnagraferestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniFrontRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListDettaglio;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.BlackListOggettoDettaglio;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ConcessionarioRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DettaglioAnagrafeRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.DocumentiRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.FasciaMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoFaseRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoPosteggioRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.GiornataMercatoSpuntistaRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.InfoAutorizzazioneRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.MappaMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PostRestNuovoSpuntistaBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioInfoRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PosteggioMercatoBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ResponsabileRestBean;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.StatoEAvvisoDaRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.StatoPagamentoSpuntistaRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.AppAmbulantiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.AppAmbulantiAutorizzazioniResponse;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.AppAmbulantiAutorizzazioniStampa;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.AppAmbulantiStampaPDFResponse;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.FiltroSoggetti;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.RuoloAutorizzazioneEnum;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.ambulanti.StampaPDFRiferimentiDocumento;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.AutorizzazioniMercatoSrv;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.mercatosrv.MercatoSrvRequest;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.nodopagamenti.StatoPagamentoNodoHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.helper.AutorizzazioniMercatiSrvHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.AutConcPerAutMercatoGiornoComparator;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.AutorizzazioniMercatoSrvBean;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.model.MercatoGiorniSettimanaEnum;
import it.gruppoinit.pal.gp.core.features.buslightyear.exceptions.EventAbortedException;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione;
import it.gruppoinit.pal.gp.core.features.common.bean.BaseEsitoOperazione.ESITO;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.PresenzaDaRegistrareBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListAttivaBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlackListContestoEnum;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistAutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistMotiviService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist.BlacklistSrcPDebSpService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService.ManifestazioniStatoPosteggio;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaInserita;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaRevocata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.IAbbonamentoService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.SituazioneBorsellinoPerSoglia;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.messaggi.MessaggioBorsellinoSottoSoglia;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.IVerticalizzazioneAbbonamentoPosteggiService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.verticalizzazione.VerticalizzazioneAbbonamentoPosteggiServiceImpl;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.PosizioneDebitoriaModel;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.NodoPagamentiService;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.VerticalizzazioneNodoPagamentiServiceImpl;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.oneri.ContiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AmministrazioniService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniAttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniCsiService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSoggettiService;
import it.gruppoinit.pal.gp.core.service.BattitoriCsiService;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService.TipoInserimento;
import it.gruppoinit.pal.gp.core.service.MercatiAppService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;
import it.gruppoinit.pal.gp.core.service.MercatiResponsabiliService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiSpunteService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.TmpEsportazioniService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.VwEntilocaliService;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;
import it.gruppoinit.pal.gp.core.service.helper.PagamentiMercatoPosizDebRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.RestBeanHelper;
import it.gruppoinit.pal.gp.core.service.helper.TipicontestoesportazioniEnum;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class MercatiAppServiceImpl extends BaseServiceImpl<Mercati, PkId> implements MercatiAppService {

    private static final Logger log = LoggerFactory.getLogger(MercatiAppServiceImpl.class);
    @Autowired
    private MercatiResponsabiliService mercatiResponsabiliService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private AttivitaService attivitaService;
    @Autowired
    private ContiService contiService;
    private MailtipoService mailTipoService;
    private AmministrazioniService amministrazioniService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDattivitaistatService mercatiDattivitaistatService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AnagrafeDAO anagrafeDAO;
    @Autowired
    private AutorizzazioniAttivitaService autorizzazioniAttivitaService;
    @Autowired
    private AutorizzazioniCsiService autorizzazioniCsiService;
    @Autowired
    private LayouttestiService layouttestiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private TipiprocedureService tipiprocedureService;
    @Autowired
    private VwEntilocaliService vwEntilocaliService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private VerticalizzazioniService verticalizzazioniService;
    @Autowired
    private MercatiSpunteService mercatiSpunteService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private AutorizzazioniSoggettiService autorizzazioniSoggettiService;
    @Autowired
    private OggettiService oggettiService;
    @Autowired
    private AnagrafedocumentiService anagrafedocumentiService;
    @Autowired
    private TmpEsportazioniService tmpEsportazioniService;
    @Autowired
    private NodoPagamentiService nodoPagamentiService;
    @Autowired
    private BlacklistAutorizzazioniService blacklistAutorizzazioniService;
    @Autowired
    private BlacklistMotiviService blacklistMotiviService;
    @Autowired
    private BlacklistSrcPDebSpService blacklistSrcPDebSpService;
    @Autowired
    private ConcessioniusoService concessioniusoService;
    @Autowired
    private IVerticalizzazioneComportamentiMercatiService vertComportamentiMercatiService;
    private IEventPublisher eventPublisher;
    @Autowired
    private BattitoriCsiService battitoriCsiService;
    @Autowired
    private IAbbonamentoService abbonamentoService;
    @Autowired
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setMailTipoService(MailtipoService mailTipoService) {

	this.mailTipoService = mailTipoService;
    }

    @Autowired
    public void setAmministrazioniService(AmministrazioniService amministrazioniService) {

	this.amministrazioniService = amministrazioniService;
    }

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @Override
    public MercatipresenzeD spuntistaPresenteAndAggiungiCatMerceologica(Integer idGiornata, Integer idAutorizzazione, String idCategoria)
	    throws MercatiAppException {

	log.debug("spuntistaPresenteAndAggiungiCatMerceologica# Segna presenza. idGiornata = {}, idAutorizzazione = {}", idGiornata,
		idAutorizzazione);
	MercatipresenzeD mpd = this.spuntistaPresente(idGiornata, idAutorizzazione);
	if (mpd != null) {
	    log.debug(
		    "spuntistaPresenteAndAggiungiCatMerceologica# Imposta cat. merceologica per prensenza. Id = {}, Idcomune = {}, Id cat. Merceologica = {}",
		    new Object[] { mpd.getId().getCodice(), mpd.getId().getIdcomune(), idCategoria });
	    impostaCategoriaMerceologica(mpd, idCategoria);
	}
	return mpd;
    }

    @Override
    public List<String> spuntistaPresenteAndAggiungiCatMerceologicaReturnMessage(Integer idGiornata, Integer idAutorizzazione, String idCategoria)
	    throws MercatiAppException {

	log.debug("spuntistaPresenteAndAggiungiCatMerceologica# Segna presenza. idGiornata = {}, idAutorizzazione = {}", idGiornata,
		idAutorizzazione);
	List<String> r = new ArrayList<String>();
	// AVVISI - WARNING. TUTTI I FIX SI INTENDONO CON QUERY CHE RITOPRNANO SOLO I DUE DATI NECESSARI PER POPOLARE I WARNING
	//. PRESENZA IN ALTRO MERCATO - FIXME : POSSIBILITà DI VELOCIZZARE SE NECESSARIO (BUON GUADAGNO)
	List<String> warningPresenzaAltroMercato = mercatipresenzeDService.verificaAutorizzazionePresenteSuAltriMercati(idGiornata, idAutorizzazione);
	if (!warningPresenzaAltroMercato.isEmpty()) {
	    throw new MercatiAppException("50001", StringUtils.join(warningPresenzaAltroMercato.toArray(), "; "));
	}
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
	if (!BooleanUtils.toBoolean(aut.getFlagAttiva())) {
	    String msg = "L'autorizzazione risulta cessata";
	    if (aut.getDataCessazione() != null) {
		msg += " in data " + Utilities.formatDate(aut.getDataCessazione(), false);
	    }
	    throw new MercatiAppException("50001", msg);
	}
	// AVVISI SU AUT_CSI - FIXME POSSIBILITA DI VELOCIZZARE SE NECESSARIO (MINIMO GUADAGNO)
	AutorizzazioniCsi autorizzazioniCsi = autorizzazioniCsiService.findByAutorizzazione(idAutorizzazione);
	if (EntityUtils.getNestedProperty(autorizzazioniCsi, "id.codice") != null) {
	    if (StringUtils.isNotBlank(autorizzazioniCsi.getStatoWarning())) {
		log.debug(
			"spuntistaPresenteAndAggiungiCatMerceologicaReturnMessage# Avvisi su recuperati da tabella AUTORIZZAIONI_CSI.STATO_WARNING");
		StringBuilder sb = new StringBuilder("");
		String dataSospDa = autorizzazioniCsi.getDataSospDa() != null ? Utilities.formatDate(autorizzazioniCsi.getDataSospDa(), false) : " ";
		String dataSospA = autorizzazioniCsi.getDataSospA() != null ? Utilities.formatDate(autorizzazioniCsi.getDataSospA(), false) : " ";
		sb.append(autorizzazioniCsi.getStatoWarning()).append(" Da ").append(dataSospDa).append(" ").append("A ").append(dataSospA);
		r.add(autorizzazioniCsi.getStatoWarning());
	    }
	    if (!BooleanUtils.toBoolean(autorizzazioniCsi.getValidaSpunta())) {
		r.add(getMessageFromBundle("label.non_valido_spunta", null));
	    }
	    log.debug("spuntistaPresenteAndAggiungiCatMerceologicaReturnMessage# Avvisi da CSI. Valida spunta = {},Stato warning = {}",
		    new Object[] { BooleanUtils.toBoolean(autorizzazioniCsi.getValidaSpunta()),
			    StringUtils.defaultIfEmpty(autorizzazioniCsi.getStatoWarning(), "") });
	}
	// AVVISO MANCATA MATURAZIONE PRESENZE - FIXME : POSSIBILITÀ DI VELOCIZZARE SE NECESSARIO (BUON GUADAGNO)
	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	log.debug("spuntistaPresenteAndAggiungiCatMerceologicaReturnMessage# Controllo se nella fase attuale non deve essere data presenza..");
	if (mpt.getFlagConteggiaPresAss().booleanValue() && BooleanUtils.toBoolean(mpt.getFlagChiusuraAppello())) {
	    // se non gestisce il conteggio delle presenze/assenze non metto avviso della presenza non maturata
	    r.add(getMessageFromBundle("label.presenza_non_maturata", null));
	}
	//. INSERIMENTO  PRESENZA
	MercatipresenzeD mpd = this.spuntistaPresente(idGiornata, idAutorizzazione);
	if (mpd != null) {
	    log.debug(
		    "spuntistaPresenteAndAggiungiCatMerceologica# Imposta cat. merceologica per prensenza. Id = {}, Idcomune = {}, Id cat. Merceologica = {}",
		    new Object[] { mpd.getId().getCodice(), mpd.getId().getIdcomune(), idCategoria });
	    impostaCategoriaMerceologica(mpd, idCategoria);
	}
	return r;
    }

    private boolean isNodoPagamentiAttivato(Mercati mercato) {

	if (mercato == null) {
	    return false;
	}
	String codiceComune = null;
	if (mercato.getComune() != null) {
	    codiceComune = mercato.getComune().getCodicecomune();
	}
	boolean nodoPagamenti = new VerticalizzazioneNodoPagamentiServiceImpl(verticalizzazioniService, codiceComune).isAttiva();
	return (nodoPagamenti && mercato.getFlagAttivanodoPagam() != null && mercato.getFlagAttivanodoPagam().booleanValue());
    }

    @Override
    public GiornataMercatoRestBean getGiornataMercatoRestBean(Integer idGiornata) throws MercatiAppException {

	GiornataMercatoRestBean result = new GiornataMercatoRestBean(this.vertComportamentiMercatiService.nascondiBottoneConcPres());
	MercatipresenzeT mercatiprest = mercatipresenzeTService.findById(new PkId(idGiornata));
	if (mercatiprest == null) {
	    return result;
	}
	if (mercatiprest.getMercato() == null || mercatiprest.getMercato().getComune() == null
		|| StringUtils.isBlank(mercatiprest.getMercato().getComune().getCodicecomune())) {
	    log.error("getGiornataMercatoRestBean {}# il mercato {} non ha configurato il comune", idGiornata,
		    mercatiprest.getMercato().getDescrizione());
	    throw new MercatiAppException("Il mercato " + mercatiprest.getMercato() + " non ha configurato il comune");
	}
	String codiceComune = mercatiprest.getMercato().getComune().getCodicecomune();
	if (mercatiprest.getMercato().getOggetto() != null && mercatiprest.getMercato().getOggetto().getId() != null
		&& mercatiprest.getMercato().getOggetto().getId().getCodice() != null) {
	    result.setMappaPresente(Boolean.TRUE);
	}
	result.setFlagVerificaPagamentoConcessionari(
		this.vertComportamentiMercatiService.dataPosDebConcessionari().compareTo(mercatiprest.getDataRegistrazione()) <= 0);
	result.setBloccaChiusuraGiornataSePosteggiNonAssegnati(
		this.vertComportamentiMercatiService.bloccaChiusuraGiornataAlCheckPosteggiNonOccupati());
	result.setMessaggioCheckChiusuraGiornataSePosteggiNonAssegnati(
		this.vertComportamentiMercatiService.messaggioGiornataAlCheckPosteggiNonOccupati());
	result.setMessaggioChiusuraGiornata(this.vertComportamentiMercatiService.messaggioChiusuraGiornataMercato());
	result.setMostraTerminaAppello(this.vertComportamentiMercatiService.visualizzaTerminaAppello());
	result.setNodoPagamentiAttivo(this.isNodoPagamentiAttivato(mercatiprest.getMercato()));
	mercatipresenzeTService.inserisciTuttiConcessionari(mercatiprest);
	//rileggo la classe perchè sono state valorizzate alcune proprietà lazy
	mercatiprest = mercatipresenzeTService.findById(new PkId(idGiornata));
	this.popolaConcessioniUsoGiornata(mercatiprest, result);
	Integer codiceMercato = mercatiprest.getMercato().getId().getCodice();
	Integer codiceUso = mercatiprest.getMercatoUso().getId().getCodice();
	Integer codiceFase = null;
	if (mercatiprest.getMercatiSpunte() != null) {
	    codiceFase = mercatiprest.getMercatiSpunte().getId().getCodice();
	}
	String descrizione = mercatipresenzeTService.getDescrizioneMercato(codiceMercato, codiceUso);
	descrizione = Utilities.replaceDescrizioneGiorno(descrizione, mercatiprest.getDataRegistrazione());
	result.setFlagPopolaConcessionari(BooleanUtils.toBoolean(mercatiprest.getFlagPopolaConcessionari()));
	String attivitaBattitori = "";
	if (BooleanUtils.isFalse(mercatiprest.getFlagPopolaConcessionari())) {
	    String codiceSc = this.comportamentiMercatiService.codiceIstatBattitori();
	    if (StringUtils.isNotBlank(StringUtils.defaultString(codiceSc).trim())) {
		attivitaBattitori = codiceSc.trim();
	    }
	}
	result.setFlagSegnaPresAssenze(BooleanUtils.toBoolean(mercatiprest.getFlagConteggiaPresAss()));
	result.setId(idGiornata);
	result.setNome(descrizione);
	result.setAppelloTerminato(BooleanUtils.toBoolean(mercatiprest.getFlagChiusuraAppello()));
	result.setGiornataChiusa(BooleanUtils.toBoolean(mercatiprest.getFlagPresenze()));
	Calendar annoPrec = Calendar.getInstance();
	annoPrec.setTimeInMillis(mercatiprest.getDataRegistrazione().getTime());
	annoPrec.set(Calendar.YEAR, (annoPrec.get(Calendar.YEAR) - 1));
	result.setNomeAnnoPrecedente(Utilities.formatDateLocale(mercatiprest.getDataRegistrazione(), "MMM yyyy", Locale.ITALIAN));
	result.setData(Utilities.formatDateLocale(mercatiprest.getDataRegistrazione(), "E d MMM yyyy", Locale.ITALIAN));
	result.setBloccaAssegnazioneCreditoInsufficiente(verificaBloccaAssegnazioneCreditoInsufficiente(codiceMercato));
	boolean attiva = false;
	List<MercatiSpunte> fasi = mercatiSpunteService.findByMercato(codiceMercato);
	for (MercatiSpunte ms : fasi) {
	    GiornataMercatoFaseRestBean b = new GiornataMercatoFaseRestBean();
	    b.setId(ms.getId().getCodice());
	    attiva = (codiceFase == null) ? false : codiceFase.equals(ms.getId().getCodice());
	    b.setAttiva(attiva);
	    b.setDescrizione(ms.getDescrizione());
	    b.setOrdine(ms.getOrdine());
	    if (BooleanUtils.isFalse(mercatiprest.getFlagPopolaConcessionari())) {
		// nel caso che il flag popolaconcessionari sia true allora la mercerologia deve essere messa subito
		b.setPermetteFiltroCategoria(Boolean.FALSE);
	    } else {
		b.setPermetteFiltroCategoria(BooleanUtils.toBoolean(ms.getFlagFiltroCatmerc()));
	    }
	    b.setContaPresenze(result.getAppelloTerminato());
	    result.getFasi().add(b);
	}
	List<CodiceDescrizioneBean> listaCategorieMerceologicheGiornataMercatoAmmesse = new ArrayList<CodiceDescrizioneBean>();
	List<CodiceDescrizioneBean> tutte = autorizzazioniService.findAttivitaInAutorizzazioni();
	String attivitaEscluse = "";
	String codiceSc = this.comportamentiMercatiService.codiceIstatBattitori();
	if (StringUtils.isNotBlank(StringUtils.defaultString(codiceSc).trim())) {
	    attivitaEscluse = codiceSc.trim();
	}
	boolean add = true;
	for (CodiceDescrizioneBean codiceDescrizioneBean : tutte) {
	    add = true;
	    if (StringUtils.isNotBlank(attivitaEscluse) && attivitaEscluse.indexOf(codiceDescrizioneBean.getCodice()) >= 0) {
		add = false;
	    }
	    if (add) {
		listaCategorieMerceologicheGiornataMercatoAmmesse.add(codiceDescrizioneBean);
	    }
	}
	result.setCategorieMerceologicheGiornataMercato(listaCategorieMerceologicheGiornataMercatoAmmesse);
	if (!result.getCategorieMerceologicheGiornataMercato().isEmpty()) {
	    CodiceDescrizioneBean cb = new CodiceDescrizioneBean();
	    cb.setCodice("*");
	    cb.setDescrizione("MISTA");
	    result.getCategorieMerceologicheGiornataMercato().add(cb);
	} else {
	    CodiceDescrizioneBean cb = new CodiceDescrizioneBean();
	    cb.setCodice("*");
	    cb.setDescrizione("MISTA");
	    result.getCategorieMerceologicheGiornataMercato().add(cb);
	}
	List<MercatipresenzeDDTO> listaPosteggi = mercatipresenzeDService.findListaPosteggi(mercatiprest);
	Set<Integer> auts = new HashSet<Integer>();
	for (MercatipresenzeDDTO md : listaPosteggi) {
	    GiornataMercatoPosteggioRestBean p = new GiornataMercatoPosteggioRestBean();
	    ConcessionarioRestBean concessionarioRestBean = new ConcessionarioRestBean();
	    // Avvisi
	    log.debug("getGiornataMercatoRestBean# Avviso - valido per la spunta. valore = {}", BooleanUtils.toBoolean(md.getValidaspuntacon()));
	    // BOCCI/PIALLI/CARZEDDA 2019-04-04 nel caso di concessionari non ha senso mostrare non valido per spunta
	    // non metto negli gli avvisi dei concessionari questo tipo di avvisi
	    StringBuilder statoWarning = new StringBuilder("");
	    if (StringUtils.isNotBlank(md.getStatowarningcon())) {
		log.debug("getGiornataMercatoRestBean# Avviso - stato warning.");
		statoWarning = statoWarning.append(md.getStatowarningcon());
		if (md.getDatasospdacon() != null) {
		    statoWarning = statoWarning.append(" Da ").append(Utilities.formatDate(md.getDatasospdacon(), false));
		}
		if (md.getDatasospacon() != null) {
		    statoWarning = statoWarning.append(" A ").append(Utilities.formatDate(md.getDatasospacon(), false));
		}
	    }
	    if (StringUtils.isNotBlank(statoWarning.toString())) {
		concessionarioRestBean.getAvvisi().add(statoWarning.toString());
	    }
	    // Collaboratore (Coadiuvante)
	    log.debug("getGiornataMercatoRestBean# Popolo collaboratore (Coadiuvante). Oggetti di tipo AnagraferestBean");
	    AnagraferestBean coadiuvante = new AnagraferestBean();
	    RestBeanHelper.populateAnagrafeRestBeanDaDTO(coadiuvante, md.getCollaboratore());
	    concessionarioRestBean.setCoadiuvante(coadiuvante);
	    p.setNote(md.getPosteggio().getNote());
	    // Autorizzazione
	    AutorizzazioneRestBean autorizzazione = new AutorizzazioneRestBean();
	    if (md.getAutorizzazioneConcessionarioAssente() != null) {
		if (md.getAutorizzazioneConcessionarioAssente().getAutorizdata() != null) {
		    autorizzazione.setData(Utilities.formatDate(md.getAutorizzazioneConcessionarioAssente().getAutorizdata(), false));
		}
		if (md.getAutorizzazioneConcessionarioAssente().getDataCessazione() != null) {
		    autorizzazione.setDataChiusura(Utilities.formatDate(md.getAutorizzazioneConcessionarioAssente().getDataCessazione(), false));
		}
		String autNumero = md.getAutorizzazioneConcessionarioAssente().getAutoriznumero();
		autorizzazione.setId(md.getAutorizzazioneConcessionarioAssente().getId().getCodice());
		autorizzazione.setNumero(autNumero);
		autorizzazione.setRilasciataDa(md.getAutorizzazioneConcessionarioAssente().getAutorizcomune());
		autorizzazione.setAnnotazioniOperatore(md.getAutorizzazioneConcessionarioAssente().getNote());
		autorizzazione.setAnnotazioniSistema(md.getAutorizzazioneConcessionarioAssente().getNotesistema());
		AnagraferestBean propr = new AnagraferestBean();
		String nomeproprietario = StringUtils.isNotBlank(md.getAutorizzazioneConcessionarioAssente().getNometitolareaut())
			? md.getAutorizzazioneConcessionarioAssente().getCognometitolareaut() + " " +
			  md.getAutorizzazioneConcessionarioAssente().getNometitolareaut()
			: md.getAutorizzazioneConcessionarioAssente().getCognometitolareaut();
		propr.setRagionesociale(nomeproprietario);
		propr.setCodiceFiscale(md.getAutorizzazioneConcessionarioAssente().getCftitolareaut());
		if (md.getAutorizzazioneConcessionarioAssente().getPivatitolareaut() != null) {
		    propr.setPartitaIva(md.getAutorizzazioneConcessionarioAssente().getPivatitolareaut());
		}
		autorizzazione.setProprietario(propr);
		concessionarioRestBean.setAutorizzazione(autorizzazione);
		// AUTORIZ ORIG
		AutorizzazioneRestBean ap = new AutorizzazioneRestBean();
		ap.setNumero(md.getAutorizzazioneConcessionarioAssente().getAutorignumero());
		if (md.getAutorizzazioneConcessionarioAssente().getAutorigdata() != null) {
		    ap.setData(Utilities.formatDate(md.getAutorizzazioneConcessionarioAssente().getAutorigdata(), false));
		}
		ap.setRilasciataDa(md.getAutorizzazioneConcessionarioAssente().getAutorigcomune());
		concessionarioRestBean.setAutorizzazioneOriginaria(ap);
		if (autorizzazione.getId() != null) {
		    auts.add(autorizzazione.getId());
		}
		List<AutorizzazioniConcessioni> autorizzazioniConcessioni = autorizzazioniConcessioniService
			.findByAutorizzazioneAttuale(autorizzazione.getId());
		if (!autorizzazioniConcessioni.isEmpty()) {
		    List<AutorizzazioneRestBean> aut = new ArrayList<AutorizzazioneRestBean>();
		    boolean done = false; // CSI posso avere n record su autorizzazioni_concessioni per la stessa autorizzazione
					  // la setto una sola volta l'autorizzazione collegata 	
		    for (AutorizzazioniConcessioni autC : autorizzazioniConcessioni) {
			if (autC.getAutorizzazioniByFkAutconcAutcoll() != null
				&& autC.getAutorizzazioniByFkAutconcAutcoll().getId().getCodice() != null && !done) {
			    AutorizzazioneRestBean autColl = new AutorizzazioneRestBean();
			    autColl.setId(autC.getAutorizzazioniByFkAutconcAutcoll().getId().getCodice());
			    autColl.setNumero(autC.getAutorizzazioniByFkAutconcAutcoll().getAutoriznumero());
			    autColl.setRilasciataDa(autC.getAutorizzazioniByFkAutconcAutcoll().getAutorizcomune().getComune());
			    autColl.setData(Utilities.formatDate(autC.getAutorizzazioniByFkAutconcAutcoll().getAutorizdata(), false));
			    AnagraferestBean prop = new AnagraferestBean();
			    Anagrafe a = autC.getAutorizzazioniByFkAutconcAutcoll().getAnagrafe();
			    nomeproprietario = StringUtils.isNotBlank(a.getNome()) ? a.getNominativo() + " " + a.getNome() : a.getNominativo();
			    prop.setRagionesociale(nomeproprietario);
			    prop.setCodiceFiscale(autC.getAutorizzazioniByFkAutconcAutcoll().getAnagrafe().getCodicefiscale());
			    if (autC.getAutorizzazioniByFkAutconcAutcoll().getAnagrafe().getPartitaiva() != null) {
				prop.setPartitaIva(autC.getAutorizzazioniByFkAutconcAutcoll().getAnagrafe().getPartitaiva());
			    }
			    prop.setDescrizioneCompleta(prop.getDescrizioneCompleta());
			    autColl.setProprietario(prop);
			    aut.add(autColl);
			    autNumero = autNumero + " (Aut. coll. " + autC.getAutorizzazioniByFkAutconcAutcoll().getAutoriznumero() + ")";
			    done = true;
			}
			concessionarioRestBean.setAutorizzazioneCollegata(aut);
		    }
		}
		autorizzazione.setNumero(autNumero);
	    }
	    //----- AUTORIZZAZIONE PRECEDENTE----
	    //Autorizzazione csi concessionario
	    AutorizzazioniCsiRestBean autCsi = null;
	    if (md.getAutorizzazioneConcessionarioAssente() != null && md.getAutorizzazioneConcessionarioAssente().getId() != null
		    && md.getAutorizzazioneConcessionarioAssente().getId().getCodice() != null) {
		autCsi = new AutorizzazioniCsiRestBean();
		autCsi.setCausaleSospensione(md.getCausalesospensionecon());
		autCsi.setDataFineGerenza(md.getDatafinegerenzacon());
		autCsi.setDataSospA(md.getDatasospdacon());
		autCsi.setDataSospDa(md.getDatasospacon());
		String nomeGerente = "";
		if (md.getGerentecon() != null) {
		    autCsi.setCodiceGerente(md.getGerentecon().getId().getCodice());
		    nomeGerente = StringUtils.isNotBlank(md.getGerentecon().getNome())
			    ? md.getGerentecon().getNominativo() + " " + md.getGerentecon().getNome()
			    : md.getGerentecon().getNominativo();
		    if (StringUtils.isNotBlank(md.getGerentecon().getCodicefiscale())) {
			nomeGerente = nomeGerente + " CF: " + md.getGerentecon().getCodicefiscale();
		    }
		}
		if (StringUtils.isNotBlank(nomeGerente)) {
		    autCsi.setNomeGerente(nomeGerente);
		}
		autCsi.setNomeGerente(nomeGerente);
		autCsi.setStatoWarning(md.getStatowarningcon());
		autCsi.setValidaSpunta(md.getValidaspuntacon() == null ? Boolean.TRUE : md.getValidaspuntacon().booleanValue());
		if (StringUtils.isNotBlank(md.getStatoautorizzazioneconc())) {
		    autCsi.setStatoAutorizzazione(md.getStatoautorizzazioneconc());
		}
		if (StringUtils.isNotBlank(md.getAutprecedentenumero())) {
		    AutorizzazioneRestBean ap = new AutorizzazioneRestBean();
		    ap.setNumero(md.getAutprecedentenumero());
		    if (md.getAutprecedentedata() != null) {
			ap.setData(Utilities.formatDate(md.getAutprecedentedata(), false));
		    }
		    ap.setRilasciataDa(md.getAutprecedentecomune());
		    concessionarioRestBean.setAutorizzazionePrecedente(ap);
		}
	    }
	    concessionarioRestBean.setAutorizzazioniCsi(autCsi);
	    //
	    p.setIdMercatiPresenzaD(md.getId().getCodice());
	    p.setId(md.getPosteggio().getId().getCodice());
	    p.setNumero(md.getPosteggio().getCodiceposteggio());
	    // anagrafe (Altri dati)
	    log.debug("getGiornataMercatoRestBean# Popola altri dati (concessionario). Oggetto di tipo AnagraferestBean");
	    if (md.getConcessionario() != null && md.getConcessionario().getId() != null && md.getConcessionario().getId().getCodice() != null) {
		AnagraferestBean altriDati = new AnagraferestBean();
		concessionarioRestBean.setNominativo(md.getConcessionario().getDescrizioneRichiedente());
		concessionarioRestBean.setIdConcessionario(md.getConcessionario().getId().getCodice());
		RestBeanHelper.populateAnagrafeRestBeanDaDTO(altriDati, md.getConcessionario());// 
		concessionarioRestBean.setAltriDati(altriDati);
		p.setConcessionario(concessionarioRestBean);
	    }
	    if (md.getPosteggio().getSuperficie() != null) {
		p.setSuperficie(String.valueOf(md.getPosteggio().getSuperficie()));
	    }
	    if (md.getPosteggio().getLunghezza() != null) {
		p.setLunghezza(String.valueOf(md.getPosteggio().getLunghezza()));
	    }
	    if (md.getPosteggio().getLarghezza() != null) {
		p.setLarghezza(String.valueOf(md.getPosteggio().getLarghezza()));
	    }
	    if (md.getPosteggio() != null && md.getPosteggio().getIdentificativoPercorso() != null) {
		p.setOrdinamentoPercorso(md.getPosteggio().getIdentificativoPercorso());
	    } else {
		p.setOrdinamentoPercorso(Integer.valueOf(0));
	    }
	    ManifestazioniStatoPosteggio stato = ManifestazioniStatoPosteggio.PosteggioLibero;
	    if (md.getOccupante() != null && md.getOccupante().getId() != null && md.getOccupante().getId().getCodice() != null) {
		stato = ManifestazioniStatoPosteggio.ConcessionarioPresente;
	    }
	    if (md.getSpuntista() != null && md.getSpuntista().booleanValue()) {
		stato = ManifestazioniStatoPosteggio.PostoAssegnatoASpuntista;
		p.setIdSpuntistaAssociato(md.getAutorizzazioni().getId().getCodice());
	    }
	    if (StringUtils.isNotBlank(md.getPosteggio().getCodiceSettore())) {
		CodiceDescrizioneBean cam = new CodiceDescrizioneBean();
		cam.setCodice(md.getPosteggio().getCodiceSettore());
		cam.setDescrizione(md.getPosteggio().getDescrizioneSettore());
		p.getCategorieMerceologicheAmmesse().add(cam);
	    }
	    List<MercatiDattivitaistat> ap = mercatiDattivitaistatService.findAttivitaPosteggio(md.getPosteggio().getId().getCodice());
	    for (MercatiDattivitaistat mai : ap) {
		if (mai.isFlagConsentito() && !mai.getAttivita().getFlagDisabilitato()) {
		    CodiceDescrizioneBean cam = new CodiceDescrizioneBean();
		    cam.setCodice(mai.getAttivita().getId().getCodiceistat());
		    cam.setDescrizione(mai.getAttivita().getIstat());
		    p.getFiltraCategorie().add(cam);
		}
	    }
	    if (!result.getFlagPopolaConcessionari()) {
		// nel caso che non vengono popotali i concessionari (esempio mercato di domenica)
		// se il posteggio è di tipo BATTITORE Lo promuovo a categoria mista perché deve chiedere la merceologia all'assegnazione del posteggio
		// e qualora lo spuntista è battitore se il posteggio è battitore non richiede la scelta della categoria merceologica
		// DEVO RIMUOVERE LE CATEGORIE BATTITORI (SE PRESENTI) E METTERE MISTA
		boolean rimuoviBattitori = false;
		if (StringUtils.isNotBlank(attivitaBattitori) && p.getFiltraCategorie() != null && !p.getFiltraCategorie().isEmpty()) {
		    List<CodiceDescrizioneBean> s = p.getFiltraCategorie();
		    for (CodiceDescrizioneBean mai : s) {
			if (attivitaBattitori.indexOf(mai.getCodice()) >= 0) {
			    rimuoviBattitori = true;
			    break;
			}
		    }
		}
		if (rimuoviBattitori && !listaCategorieMerceologicheGiornataMercatoAmmesse.isEmpty()) {
		    p.setFiltraCategorie(listaCategorieMerceologicheGiornataMercatoAmmesse);
		}
	    }
	    p.setStato(stato.name());
	    result.getPosteggi().add(p);
	}
	situazioneBorsellinoPosteggi(result.getPosteggi(), codiceComune, auts);
	//. SPUNTISTA GIORNATA MERCATO
	List<GiornataMercatoSpuntistaRestBean> findSpuntistiGiornataMercatoRest = mercatipresenzeDService
		.findSpuntistiGiornataMercatoRest(idGiornata);
	result.setSpuntisti(findSpuntistiGiornataMercatoRest);
	result.setIsNascondiInserimentoSpuntista(this.vertComportamentiMercatiService.isNascondiInserimantoSpuntista());
	IVerticalizzazioneAbbonamentoPosteggiService vert = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService, codiceComune);
	result.setNascondiPagamentoEffettuato((vert.isAttiva() && mercatiprest.getMercato().getActiveWalletMarket() == true));
	return result;
    }

    private void popolaConcessioniUsoGiornata(MercatipresenzeT mercatiprest, GiornataMercatoRestBean result) {

	List<Concessioniuso> fasce = concessioniusoService.findByCurrentSoftware();
	Integer concUsoSelezionata = null;
	if (mercatiprest != null && mercatiprest.getConcessioniuso() != null && mercatiprest.getConcessioniuso().getId() != null
		&& mercatiprest.getConcessioniuso().getId().getCodice() != null) {
	    concUsoSelezionata = mercatiprest.getConcessioniuso().getId().getCodice();
	}
	if (concUsoSelezionata == null) {
	    MercatiUso uso = mercatiprest.getMercatoUso();
	    if (uso != null && uso.getConcessioniuso() != null && uso.getConcessioniuso().getId() != null
		    && uso.getConcessioniuso().getId().getCodice() != null) {
		concUsoSelezionata = uso.getConcessioniuso().getId().getCodice();
	    }
	}
	for (Concessioniuso concessioniuso : fasce) {
	    FasciaMercatoBean fmb = new FasciaMercatoBean(concessioniuso.getId().getCodice(), concessioniuso.getDescrizione(), null, false);
	    if (concUsoSelezionata != null && fmb.getId().equals(concUsoSelezionata)) {
		fmb.setAttiva(true);
	    }
	    result.getListaFasceMercato().add(fmb);
	}
    }

    @Override
    public MercatipresenzeD impostaCategoriaMerceologica(MercatipresenzeD mpd, String idCategoria) throws MercatiAppException {

	Attivita a = new Attivita();
	a.setId(new AttivitaId(idCategoria));
	a = attivitaService.findById(a.getId());
	mpd.setAttivita(a);
	mercatipresenzeDService.update(mpd);
	return mpd;
    }

    @Override
    public void impostaCategoriaMerceologica(Integer idGiornata, Integer idAutorizzazione, String idCategoria) throws MercatiAppException {

	Attivita a = new Attivita();
	a.setId(new AttivitaId(idCategoria));
	a = attivitaService.findById(a.getId());
	List<MercatipresenzeDDTO> presenze = mercatipresenzeDService.findByMercatipresenzaT(idGiornata);
	for (MercatipresenzeDDTO mercatipresenzeDDTO : presenze) {
	    if (BooleanUtils.isTrue(mercatipresenzeDDTO.getSpuntista())
		    && (mercatipresenzeDDTO.getAutorizzazioni() != null && mercatipresenzeDDTO.getAutorizzazioni().getId() != null
			    && mercatipresenzeDDTO.getAutorizzazioni().getId().getCodice().equals(idAutorizzazione))) {
		MercatipresenzeD mpd = mercatipresenzeDService.findById(new PkId(mercatipresenzeDDTO.getId().getCodice()));
		mpd.setAttivita(a);
		mercatipresenzeDService.update(mpd);
		break;
	    }
	}
    }

    @Override
    public MercatipresenzeD spuntistaPresente(Integer idGiornata, Integer idAutorizzazione) throws MercatiAppException {

	if (idGiornata == null) {
	    throw new InvalidArgumentException("Impossibile utilizzare il metodo spuntistaPresente senza passare il riferimento della giornata");
	}
	if (idAutorizzazione == null) {
	    throw new InvalidArgumentException("Impossibile utilizzare il metodo spuntistaPresente senza passare il riferimento dall'autorizzazione");
	}
	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	MercatipresenzeD mpd = mercatipresenzeDService.isAssegnatoPosteggioASpuntistaAndAutNellaGiornata(giorno.getMercato().getId().getCodice(),
		giorno.getMercatoUso().getId().getCodice(), idAutorizzazione, giorno.getDataRegistrazione());
	if (mpd != null && mpd.getId() != null && mpd.getId().getCodice() != null) {
	    return mpd;
	}
	MercatipresenzeD mpdInserita = mercatipresenzeTService.segnaPresenzaSpuntistaNoPosteggio(giorno, idAutorizzazione, null);
	log.debug("spuntistaPresente# Nuova presenza inserita. Id = {}, Idcomune = {}", mpdInserita.getId().getCodice(),
		mpdInserita.getId().getIdcomune());
	return mpdInserita;
    }

    @Override
    public void spuntistaAssente(Integer idGiornata, Integer idAutorizzazione) throws MercatiAppException {

	List<MercatipresenzeDDTO> presenze = mercatipresenzeDService.findByMercatipresenzaT(idGiornata);
	for (MercatipresenzeDDTO mercatipresenzeDDTO : presenze) {
	    if (mercatipresenzeDDTO.getSpuntista() != null && mercatipresenzeDDTO.getSpuntista().booleanValue()) {
		if (mercatipresenzeDDTO.getAutorizzazioni() != null) {
		    if (mercatipresenzeDDTO.getAutorizzazioni().getId() != null
			    && mercatipresenzeDDTO.getAutorizzazioni().getId().getCodice().equals(idAutorizzazione)) {
			mercatipresenzeTService.eliminaPresenzaOccupante(mercatipresenzeDDTO.getId().getCodice());
			return;
		    }
		}
	    }
	}
	throw new MercatiAppException("Non è stato possibile segnare assente lo spuntista");
    }

    @Override
    public void concessionariPresenti(Integer idGiornata, List<Long> idPosteggi) throws MercatiAppException {

	log.debug("concessionariPresenti# start...");
	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	Map<Integer, PosteggiConcessioniHelper> pchs = mercatiService.findPosteggiMercatoAllaData(giorno.getMercato().getId().getCodice(),
		giorno.getMercatoUso().getId().getCodice(), giorno.getDataRegistrazione());
	for (Long id : idPosteggi) {
	    this.concessionarioPresente(idGiornata, id.intValue(), pchs.get(id.intValue()));
	}
	log.debug("concessionariPresenti# end...");
    }

    @Override
    public void concessionariAssenti(Integer idGiornata, List<Long> idPosteggi) throws MercatiAppException {

	log.debug("concessionariAssenti# start...");
	for (Long id : idPosteggi) {
	    this.concessionarioAssente(idGiornata, id.intValue());
	}
	log.debug("concessionariAssenti# end...");
    }

    @Override
    public void collegaPosteggioASpuntista(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione, Attivita categoriaMerceologica)
	    throws MercatiAppException {

	//Questo metodo gestisce il fatto che lo spuntista potrebbe finire 
	//	su un posteggio di un altro spuntista
	//	su un posteggio di un concessionario
	//	su un posteggio libero
	//Pertanto vengono effettuate tutte le operazioni necessarie, 
	//e poi rilanciati i vari eventi
	//
	//1. Recupero la riga di mercatipresenze_d da eliminare
	//( lo spuntista viene temporaneamente appoggiato in una riga di mercatipresenze_d
	//senza essere associato a nessun posteggio
	MercatipresenzeD daEliminare = this.mercatipresenzeDService.findByMercatiPresenzeTAndAutorizzazione(idGiornata, idAutorizzazione);
	//2. Se non trovata la riga da eliminare, errore
	if (daEliminare == null) {
	    String mesg = String.format("Non è stata trovata la presenza da eliminare idGiornata=%d, idPosteggio=%d , idAutorizzazione= %d",
		    idGiornata, idPosteggio, idAutorizzazione);
	    log.error("collegaPosteggioASpuntista ==> {}", mesg);
	    throw new IllegalArgumentException(mesg);
	}
	//3. Recupero la giornata di riferimento
	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	//4. Prima di qualsiasi variazione recupero i dati che mi serviranno per gestire gli eventi
	MercatipresenzeD vecchiaRiga = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(giorno.getId().getCodice(), idPosteggio);
	Integer idPrecedente = vecchiaRiga.getId().getCodice();
	boolean precedenteOccupanteSpuntista = vecchiaRiga.isSpuntista();
	boolean precedenteOccupanteConcessionario = vecchiaRiga.isConcessionarioPresente();
	log.debug(
		"collegaPosteggioASpuntista# idPrecedente {} - precedenteOccupanteSpuntista {} - precedenteOccupanteConcessionario {} - prima di segnare la presenza",
		new Object[] { idPrecedente, precedenteOccupanteSpuntista, precedenteOccupanteConcessionario });
	if (precedenteOccupanteSpuntista && idAutorizzazione.equals(daEliminare.getAutorizzazioni().getId().getCodice())) {
	    // se sto mettendo lo stesso occupante / autorizzazione allora esco senza fare niente
	    // FIX spuntista doppio CSI 2024-01-24
	    log.warn(
		    "collegaPosteggioASpuntista# idPrecedente {} - precedenteOccupanteSpuntista {}, idAutorizzazione{}, daEliminare.getAutorizzazioni().getId().getCodice()){} ",
		    new Object[] { idPrecedente, idAutorizzazione, precedenteOccupanteSpuntista,
			    daEliminare.getAutorizzazioni().getId().getCodice() });
	    return;
	}
	Autorizzazioni precedenteAutorizzazione = vecchiaRiga.getAutorizzazioni();
	//5. Il metodo effettua vari interventi/controlli sulla vecchia riga e poi ci mette i dati del nuovo spuntista
	MercatipresenzeD presenza = this.segnaPresenzaSpuntista(giorno, idPosteggio, daEliminare.getAutorizzazioni().getId().getCodice(), null);
	//6. Copio le altre informazioni leggendole dalla riga di mercatipresenze_d con cui ho registrato la presenze dello spuntista sulla giornata,
	//   senza però avergli assegnato un posteggio, alla riga di mercatipresenze_d di destinazione in cui lo spuntista avrà il posteggio assegnato
	log.debug("collegaPosteggioASpuntista# Copio i dati della presenza di origine = {} e li copio in quella di destinazione = {}",
		daEliminare.getId().getCodice(), presenza.getId().getCodice());
	mercatipresenzeDService.copiaInformazioniMercatopresenzeDOrigine(daEliminare, presenza);
	if (categoriaMerceologica != null) {
	    presenza.setAttivita(categoriaMerceologica);
	}
	mercatipresenzeDService.update(presenza);
	boolean elimina = true;
	if (presenza.getId().getCodice().equals(daEliminare.getId().getCodice())) {
	    elimina = false;
	}
	if (elimina) {
	    log.debug("collegaPosteggioASpuntista# elimino la vecchia presenza {}", daEliminare.getId().getCodice());
	    mercatipresenzeDService.delete(daEliminare);
	}
	//7. Sollevo l'evento per l'assegnazione della presenza
	try {
	    if (precedenteOccupanteSpuntista || precedenteOccupanteConcessionario) {
		EventoPresenzaRevocata evRevoca = new EventoPresenzaRevocata(idPrecedente, precedenteAutorizzazione, precedenteOccupanteSpuntista);
		this.eventPublisher.publishThrowOnFailure(evRevoca);
	    }
	    EventoPresenzaInserita evPresenza = new EventoPresenzaInserita(presenza);
	    this.eventPublisher.publishThrowOnFailure(evPresenza);
	    //8. In base al tipo di operazione effettuata rilancio l'evento corretto
	} catch (EventAbortedException e) {
	    log.error("Errore nellasottoscrizione degli eventi ", e);
	    throw new MercatiAppException(e);
	}
    }

    @Override
    public void scollegaPosteggioDaSPuntista(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione) throws MercatiAppException {

	List<MercatipresenzeDDTO> presenze = mercatipresenzeDService.findByMercatipresenzaT(idGiornata);
	for (MercatipresenzeDDTO mercatipresenzeDDTO : presenze) {
	    if (BooleanUtils.isTrue(mercatipresenzeDDTO.getSpuntista())
		    && (mercatipresenzeDDTO.getAutorizzazioni() != null && mercatipresenzeDDTO.getAutorizzazioni().getId() != null
			    && mercatipresenzeDDTO.getAutorizzazioni().getId().getCodice().equals(idAutorizzazione))) {
		mercatipresenzeTService.eliminaPresenzaOccupante(mercatipresenzeDDTO.getId().getCodice());
		return;
	    }
	}
	throw new MercatiAppException("Non è stato possibile scollegare il posteggio dallo spuntista");
    }

    @Override
    public void concessionarioPresente(Integer idGiornata, Integer idPosteggio, PosteggiConcessioniHelper pch) throws MercatiAppException {

	try {
	    MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	    MercatipresenzeD p = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	    mercatipresenzeTService.segnaPresenzaConcessionario(giorno, p.getPosteggio().getId().getCodice(), p.getAutorizzazioni(), null, pch);
	} catch (Exception e) {
	    throw new MercatiAppException("CONC_PRES", e.getMessage());
	}
    }

    @Override
    public void concessionarioAssente(Integer idGiornata, Integer idPosteggio) throws MercatiAppException {

	MercatipresenzeD p = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	mercatipresenzeTService.eliminaPresenzaOccupante(p.getId().getCodice());
    }

    @Override
    public void terminaAppello(Integer idGiornata) throws MercatiAppException {

	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	giorno.setFlagChiusuraAppello(Boolean.TRUE);
	giorno.setDataChiusuraAppello(Calendar.getInstance().getTime());
	giorno.setResponsabileChiusuraAppello((Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails());
	mercatipresenzeTService.update(giorno);
    }

    @Override
    public void riapriAppello(Integer idGiornata) throws MercatiAppException {

	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	giorno.setFlagChiusuraAppello(Boolean.FALSE);
	giorno.setDataChiusuraAppello(null);
	giorno.setResponsabileChiusuraAppello(null);
	mercatipresenzeTService.update(giorno);
    }

    @Override
    public void rifiutaPosteggio(Integer idGiornata, Integer idAutorizzazione, Integer idPosteggio) throws MercatiAppException {

	MercatipresenzeD p = null;
	if (idPosteggio == -1) {
	    idPosteggio = null;
	}
	p = mercatipresenzeDService.findByMercatiPresenzeTAndAutorizzazione(idGiornata, idAutorizzazione);
	mercatipresenzeDService.updateSegnaRinunciaPosteggio(p.getId().getCodice(), idPosteggio);
    }

    @Override
    public void annullaRifiutaPosteggio(Integer idGiornata, Integer idAutorizzazione) throws MercatiAppException {

	List<MercatipresenzeDDTO> presenze = mercatipresenzeDService.findByMercatipresenzaT(idGiornata);
	for (MercatipresenzeDDTO mercatipresenzeDDTO : presenze) {
	    if (BooleanUtils.isTrue(mercatipresenzeDDTO.getSpuntista())
		    && (mercatipresenzeDDTO.getAutorizzazioni() != null && mercatipresenzeDDTO.getAutorizzazioni().getId() != null
			    && mercatipresenzeDDTO.getAutorizzazioni().getId().getCodice().equals(idAutorizzazione))) {
		mercatipresenzeDService.updateRimuoviRinunciaPosteggio(mercatipresenzeDDTO.getId().getCodice());
		break;
	    }
	}
    }

    @Override
    public void rollbackPosteggioASPuntista(Integer idGiornata, Integer idPosteggio) throws MercatiAppException {

	MercatipresenzeD p = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	if (!p.isSpuntista()) {
	    throw new MercatiAppException("Non è stato scollegato lo spuntista dal posteggio");
	}
	mercatipresenzeTService.eliminaPresenzaOccupante(p.getId().getCodice());
    }

    @Override
    public void rollbackPosteggioASPuntistaPrecedente(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione) throws MercatiAppException {

	MercatipresenzeT giorno = mercatipresenzeTService.findById(new PkId(idGiornata));
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
	//1. Prima di qualsiasi variazione recupero i dati che mi serviranno per gestire gli eventi
	MercatipresenzeD attuale = this.mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	Integer attualeId = attuale.getId().getCodice();
	boolean attualeOccupanteSpuntista = attuale.isSpuntista();
	boolean attualeOccupanteConcessionario = attuale.isConcessionarioPresente();
	Autorizzazioni attualeAutorizzazione = attuale.getAutorizzazioni();
	//2. Inserisco lo spuntista	
	this.segnaPresenzaSpuntista(giorno, idPosteggio, idAutorizzazione, null);
	//3. Ricarico lo stato attuale della presenza
	attuale = this.mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	//3. Sollevo l'evento per l'assegnazione della presenza
	EventoPresenzaInserita evPresenza = new EventoPresenzaInserita(attuale);
	try {
	    this.eventPublisher.publishThrowOnFailure(evPresenza);
	    //4. In base al tipo di operazione effettuata rilancio l'evento corretto
	    if (attualeOccupanteSpuntista || attualeOccupanteConcessionario) {
		EventoPresenzaRevocata evRevoca = new EventoPresenzaRevocata(attualeId, attualeAutorizzazione, attualeOccupanteSpuntista);
		this.eventPublisher.publishThrowOnFailure(evRevoca);
		return;
	    }
	} catch (EventAbortedException e) {
	    throw new MercatiAppException(e.getMessage());
	}
    }

    /////////////////// METODI NON IMPLEMENTATI
    @Override
    public it.gruppoinit.pal.gp.core.domain.Mercati bindDomainObject(it.gruppoinit.pal.gp.core.domain.Mercati entity, Class<?> idClass,
	    String idPath) {

	throw new NotImplementedException();
    }

    @Override
    public it.gruppoinit.pal.gp.core.domain.PkId newIdFromSequencetable(Mercati entity) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(Mercati entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Mercati entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(Mercati entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Mercati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public it.gruppoinit.pal.gp.core.domain.Mercati findById(it.gruppoinit.pal.gp.core.domain.PkId id) {

	throw new NotImplementedException();
    }

    @Override
    protected Class<Mercati> getEntityClass() {

	return Mercati.class;
    }

    @Override
    public List<GiornataMercatoSpuntistaRestBean> ricercaAnagrafe(Integer idGiornataMercato, String testo, int numMaxRecords)
	    throws MercatiAppException {

	List<GiornataMercatoSpuntistaRestBean> result = new ArrayList<GiornataMercatoSpuntistaRestBean>();
	Anagrafe entity = new Anagrafe();
	entity.setNominativo(testo);
	Integer firstResult = 0;
	Integer maxResults = numMaxRecords;
	List<AutorizzazioniRestHelper> hlps = autorizzazioniService.findAnagraficheConAutorizzazione(testo, firstResult, maxResults);
	//	il servizio REST verifica che l'autorizzazione NON si a di un concessionario dei posteggi della giornata se si l'esclude dai risultati
	//	verificare i BATTORI e la giornata domenicale		
	Set<Integer> iautConcDellaGiornata = this.findConcessioniDaEscludereNellaRicercaDellaGiornata(idGiornataMercato);
	Set<Integer> autTrovate = new HashSet<Integer>();
	for (AutorizzazioniRestHelper arh : hlps) {
	    Integer idAutorizzazione = arh.getIdAutorizzazione();
	    Integer fkIdautAttuale = autorizzazioniConcessioniService.getFkIdautAttualePerAutCollegata(idAutorizzazione);
	    if (BooleanUtils.isTrue(arh.getFlagAttiva())) {
		if (fkIdautAttuale != null) {
		    // Task 20857: Autorizzazione con doppio atto, nascondere le autorizzazioni Collegate alla concessione
		    // ==> Devo poter ricercare anche per autorizzazione collegata e non presentarla, ovvero la presenza va solamente alla concessione ma il filtro di ricerca deve considerare anche le collegate in quanto non so con quale atto si presenta l'ambulante
		    // SE NON RITORNA NULLA LA RICERCA:
		    // Significa che il riferimento dell'autorizzazione trovata è una autorizzazione collegata ad una concessione
		    // il vero id autorizzazione da cercare è idConcessionePerAutCollegata
		    idAutorizzazione = fkIdautAttuale;
		}
		if (!autTrovate.contains(idAutorizzazione) && // POTREI AVERLA GIà INSERITA COME AUTORIZZAZIONE COLLEGATA
			!iautConcDellaGiornata.contains(idAutorizzazione)) { // ESCLUDO QUELLE DEI CONCESSIONARI DI QUELLA GIORNATA
		    GiornataMercatoSpuntistaRestBean ah = mercatipresenzeDService.findSpuntistiGiornataMercatoRest(idGiornataMercato,
			    idAutorizzazione);
		    result.add(ah);
		    autTrovate.add(idAutorizzazione);
		}
	    }
	}
	return result;
    }

    @Override
    public void updatePagato(Integer idGiornata, Integer idAutorizzazione, boolean isPagato) {

	MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findByMercatiPresenzeTAndAutorizzazione(idGiornata, idAutorizzazione);
	mercatipresenzeD.setFlagPagato(BooleanUtils.toBoolean(isPagato));
	mercatipresenzeDService.update(mercatipresenzeD);
    }

    @Override
    public AnagraferestBean ricercaAnagrafeCFI(String testo) {

	log.debug("ricercaAnagrafeCFI# start....");
	AnagraferestBean anagraferestBean = new AnagraferestBean();
	ChiaveValoreBean<AnagrafeTrovataInEnum, Anagrafe> ris = findAnagrafeInternoOrWs(testo);
	if (ris != null && ris.getValore() != null) {
	    anagraferestBean = new AnagraferestBean();
	    anagraferestBean.setId(ris.getValore().getId().getCodice());
	    anagraferestBean.setCodiceFiscale(ris.getValore().getCodicefiscale());
	    anagraferestBean.setRagionesociale(ris.getValore().getNominativo());
	    anagraferestBean.setAnagrafeTrovataInEnum(ris.getChiave().toString());
	} else {
	    anagraferestBean = new AnagraferestBean();
	    anagraferestBean.setAnagrafeTrovataInEnum(AnagrafeTrovataInEnum.NON_TROVATA.toString());
	}
	log.debug("ricercaAnagrafeCFI# End....");
	return anagraferestBean;
    }

    private ChiaveValoreBean<AnagrafeTrovataInEnum, Anagrafe> findAnagrafeInternoOrWs(String cf) {

	ChiaveValoreBean<AnagrafeTrovataInEnum, Anagrafe> ris = new ChiaveValoreBean<AnagrafeTrovataInEnum, Anagrafe>();
	Anagrafe a = new Anagrafe();
	a.setCodicefiscale(cf);
	a.setPartitaiva(cf);
	a.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
	log.debug("ricercaAnagrafeCFI# Cerco sulla tabella ANAGRAFE. cf = {}, pi = {}", cf, cf);
	a = anagrafeService.bindDomainObject(a, PkId.class, "id.codice");
	ris.setChiave(AnagrafeTrovataInEnum.TROVATA_BACKEND);
	if (EntityUtils.getNestedProperty(a, "id.codice") == null) {
	    log.debug("findAnagrafeInternoOrWs# Cerco tramite il servizio anagrafe ws. cfi = {}", cf);
	    a = new Anagrafe();
	    a.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
	    try {
		a = anagrafeService.findDatiAnagrafeDaWs(cf, a, false);
		ris.setChiave(AnagrafeTrovataInEnum.TROVATA_SERVIZIO_WS);
		if (a == null) {
		    ris.setChiave(AnagrafeTrovataInEnum.NON_TROVATA);
		}
	    } catch (Exception be) {
		log.debug("findAnagrafeInternoOrWs# Anagrafe con cf = {} non trovata", cf);
		ris.setChiave(AnagrafeTrovataInEnum.NON_TROVATA);
		a = null;
	    }
	}
	ris.setValore(a);
	return ris;
    }

    @Override
    public Integer inserisceNuovoSpuntista(PostRestNuovoSpuntistaBean nuovoSpuntista) throws MercatiAppException {

	try {
	    log.debug("inserisceNuovoSpuntista# Start....");
	    log.debug("inserisceNuovoSpuntista# Recupero/inserisco anagrafica");
	    VwEntilocali entelocale = vwEntilocaliService.findById(nuovoSpuntista.getComuneAutorizzazione());
	    MercatiConfigurazione mcfg = mercatiConfigurazioneService.findConfigurazione();
	    Tipologiaregistri tr = mcfg.getRegistroAutorizzazioni();
	    if (mcfg == null || EntityUtils.getNestedProperty(mcfg.getRegistroAutorizzazioni(), "id.codice") == null) {
		log.error("inserisceNuovoSpuntista# Tipologia registro non trovata in configurazioni mercati");
		throw new MercatiAppException("Tipologia registro non trovata in configurazioni mercati");
	    }
	    Autorizzazioni autpresente = null;
	    if (autorizzazioniService.overrideUniqueConstraint()) {
		autpresente = autorizzazioniService.findByNumeroAndComune(nuovoSpuntista.getNumeroAutorizzazione(), entelocale.getCodicecomune());
	    } else {
		autpresente = autorizzazioniService.findAutOConcByEstremi(nuovoSpuntista.getNumeroAutorizzazione(),
			Utilities.parseDateString(nuovoSpuntista.getDataAutorizzazione(), "yyyy-MM-dd"), entelocale.getCodicecomune(),
			tr.getId().getCodice());
	    }
	    if (autpresente != null) {
		throw new MercatiAppException("AUT_PRESENTE",
			"L'Autorizzazione con identificativo [" + autpresente.getTransientEstremiAut() + "] risulta già censita nella base dati");
	    }
	    ChiaveValoreBean<AnagrafeTrovataInEnum, Anagrafe> anagrafe = findAnagrafeInternoOrWs(nuovoSpuntista.getCodiceFiscaleImpresa());
	    //. INSERISCO O RECUPERO ANAGRAFE
	    boolean verificaCF = this.comportamentiMercatiService.restVerificaCFPIva();
	    if (Boolean.TRUE.equals(verificaCF)) {
		String cf = StringUtils.defaultString(nuovoSpuntista.getCodiceFiscaleImpresa());
		if (StringUtils.isBlank(cf)) {
		    throw new MercatiAppException("CF_NON_VALIDO", "Il codice fiscale impresa non è valido");
		}
		int length = 16;
		if (Utilities.isInteger(cf)) {
		    length = 11;
		}
		if (cf.length() != length) {
		    throw new MercatiAppException("CF_NON_VALIDO",
			    "Il codice fiscale impresa non è valido puo' contenere al massimo 11 caratteri numerici o 16 caratteri alfanumerici");
		}
	    }
	    Anagrafe anagrafeDaInserire = null;
	    if (anagrafe != null) {
		log.debug("inserisceNuovoSpuntista# ");
		switch (anagrafe.getChiave()) {
		case TROVATA_BACKEND:
		    log.debug("inserisceNuovoSpuntista# spuntista recuperato dal backend. Id anagrafica = {}",
			    anagrafe.getValore().getId().getCodice());
		    anagrafeDaInserire = anagrafeService.findById(new PkId(anagrafe.getValore().getId().getCodice()));
		    break;
		case TROVATA_SERVIZIO_WS:
		    log.debug("inserisceNuovoSpuntista# spuntista recuperato dal servizio ws.");
		    anagrafeDaInserire = anagrafe.getValore();
		    anagrafeService.insert(anagrafeDaInserire);
		    anagrafeDAO.flush();
		    log.debug("inserisceNuovoSpuntista# Anagrafica inserita. Id anagrafica = {}", anagrafeDaInserire.getId().getCodice());
		    break;
		case NON_TROVATA:
		    anagrafeDaInserire = new Anagrafe();
		    anagrafeDaInserire.setTipoanagrafe(WebConstants.PERSONA_GIURIDICA);
		    if (StringUtils.isNotBlank(nuovoSpuntista.getNome()) || StringUtils.isNotBlank(nuovoSpuntista.getCognome())) {
			anagrafeDaInserire.setNominativo(StringUtils.defaultIfEmpty(nuovoSpuntista.getNome(), "").toUpperCase() + " " +
							 StringUtils.defaultIfEmpty(nuovoSpuntista.getCognome(), "").toUpperCase());
			//titolare[FRANCESCO/ABATE]
			anagrafeDaInserire.setNote("titolare[" + StringUtils.defaultIfEmpty(nuovoSpuntista.getNome(), "").toUpperCase() + "/" +
						   StringUtils.defaultIfEmpty(nuovoSpuntista.getCognome(), "").toUpperCase() + "]".toUpperCase());
		    } else {
			anagrafeDaInserire.setNominativo(StringUtils.defaultIfEmpty(nuovoSpuntista.getRagioneSociale(), "").toUpperCase());
		    }
		    anagrafeDaInserire.setCodicefiscale(nuovoSpuntista.getCodiceFiscaleImpresa().toUpperCase());
		    if (StringUtils.defaultString(nuovoSpuntista.getCodiceFiscaleImpresa()).trim().length() == 11) {
			anagrafeDaInserire.setPartitaiva(nuovoSpuntista.getCodiceFiscaleImpresa().trim().toUpperCase());
		    }
		    anagrafeService.insert(anagrafeDaInserire);
		    break;
		}
	    }
	    //. INSERISCO L'ISTANZA
	    Istanze istanza = new Istanze();
	    Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    istanza.setData(new Date());
	    istanza.setResponsabile(r);
	    if (EntityUtils.getNestedProperty(anagrafeDaInserire, "id.codice") != null) {
		istanza.setRichiedente(anagrafeDaInserire);
	    } else {
		log.error("inserisceNuovoSpuntista# Anagrafica non recupera o non inserita. Impossibile procedere");
	    }
	    log.debug("inserisceNuovoSpuntista# Cerco comune per l'istanza. codice comune = {}", ORMHelper.getIdcomune());
	    // FIXME - NON è STATA APPLICATA LA LOGICA DI RISALIRE L'ALBERO, TROPPO LENTA, LA VOCE SCELTA DEVE AVERE : PROCEDURA E MOVIMENTO DI AVVIO
	    String codice_sc = this.comportamentiMercatiService.scCodiceIstNuovSpuntista();
	    if (StringUtils.isBlank(codice_sc)) {
		String messaggio = "Parametro " + IVerticalizzazioneComportamentiMercatiService.nomeVerticalizzazione + "." +
				   IVerticalizzazioneComportamentiMercatiService.parScCodiceIstNuovSpuntista + " non configurato";
		log.error("inserisceNuovoSpuntista# {}", messaggio);
		throw new MercatiAppException(messaggio);
	    }
	    Alberoproc alberoproc = alberoprocService.findByScCodice(codice_sc);
	    if (EntityUtils.getNestedProperty(alberoproc, "id.codice") != null) {
		log.debug("inserisceNuovoSpuntista# Albero proc = {}", alberoproc.getVwAlberoproc().getScDescrizione());
		istanza.setAlberoproc(alberoproc);
		if (EntityUtils.getNestedProperty(alberoproc.getTipoProcedura(), "id.codice") != null) {
		    Tipiprocedure procedura = tipiprocedureService.findById(new PkId(alberoproc.getTipoProcedura().getId().getCodice()));
		    log.debug("inserisceNuovoSpuntista# Procedura = {}", alberoproc.getTipoProcedura().getProcedura());
		    istanza.setProcedura(procedura);
		    boolean movAvvioTrovato = false;
		    Set<Tipiprocedureavvio> tpAvvios = procedura.getTipiProcedureavvios();
		    for (Tipiprocedureavvio tipiprocedureavvio : tpAvvios) {
			if (BooleanUtils.isTrue(tipiprocedureavvio.getDefaultsn())) {
			    movAvvioTrovato = true;
			    istanza.setTipoMovimentoAvvio(tipiprocedureavvio.getTipoMovimento());
			    break;
			}
		    }
		    if (!movAvvioTrovato) {
			log.error("inserisceNuovoSpuntista# Movimento di avvio non trovato nella procedura: {}",
				alberoproc.getTipoProcedura().getProcedura());
			throw new MercatiAppException(
				"Movimento di avvio non trovato nella procedura: " + alberoproc.getTipoProcedura().getProcedura());
		    }
		} else {
		    log.error("inserisceNuovoSpuntista# procedura non trovata nella voce dell'albero: {}", alberoproc.getDescrizioneCompleta());
		    throw new MercatiAppException("procedura non trovata nella voce dell'albero: " + alberoproc.getDescrizioneCompleta());
		}
	    } else {
		log.error("inserisceNuovoSpuntista# Voce dell'albero non trovata per il codice passato in verticalizzazione COMPORTAMENTO_MERCATI:");
		throw new MercatiAppException("Voce dell'albero non trovata per il codice passato in verticalizzazione COMPORTAMENTO_MERCATI:");
	    }
	    log.debug("inserisceNuovoSpuntista# Inserimento istanza.....");
	    istanzeService.insert(istanza, TipoInserimento.PROTOCOLLAZIONE_PARAMETRI_INSERIMENTO_NORMALE);
	    //. INSERISCO AUTORIZZAZIONE LEGATA ALL'ISTANZA E ALL'ANAGRAFICA CREATE/RECUPERATE
	    Autorizzazioni autorizzazioni = new Autorizzazioni();
	    autorizzazioni.setFlagAttiva(true);
	    autorizzazioni.setIstanza(istanza);
	    // stesso di istanza
	    autorizzazioni.setAnagrafe(anagrafeDaInserire);
	    autorizzazioni.setOccupante(anagrafeDaInserire);
	    // non metto controlli sul NULL perchè: 
	    // 1.il codice che mi viene passato è preso dalla ricerca comuni nel nostro DB
	    // 2. non vengono inviati i dati se non tutti presenti sulla maschera dell'app
	    autorizzazioni.setAutoriznumero(nuovoSpuntista.getNumeroAutorizzazione());
	    autorizzazioni.setAutorizdata(Utilities.parseDateString(nuovoSpuntista.getDataAutorizzazione(), "yyyy-MM-dd"));
	    autorizzazioni.setDataRilascio(Utilities.parseDateString(nuovoSpuntista.getDataAutorizzazione(), "yyyy-MM-dd"));
	    log.debug("inserisceNuovoSpuntista# Comune autorizzazione = {}.", entelocale.getComune());
	    autorizzazioni.setAutorizcomune(entelocale);
	    autorizzazioni.setTipologiaregistro(tr);
	    log.debug("inserisceNuovoSpuntista# Inserimento autorizzazione....");
	    autorizzazioniService.insert(autorizzazioni);
	    //. INSERISCO ATTIVITA ALL'AUTORIZZAZIONE
	    log.debug("inserisceNuovoSpuntista# Inserimento mercelogia (attivita) nell'autorizzazione.....");
	    if ("*".equals(nuovoSpuntista.getCategoriaMerceologica())) {
		List<Attivita> attivitas = attivitaService.findAll(null, null);
		String attivitaEscluse = "";
		String istatBattitori = this.comportamentiMercatiService.codiceIstatBattitori();
		if (StringUtils.isNotBlank(StringUtils.defaultString(istatBattitori).trim())) {
		    attivitaEscluse = istatBattitori.trim();
		}
		boolean add = true;
		for (Attivita attivita : attivitas) {
		    add = true;
		    if (StringUtils.isNotBlank(attivitaEscluse)) {
			if (attivitaEscluse.indexOf(attivita.getId().getCodiceistat()) >= 0) {
			    add = false;
			}
		    }
		    if (add) {
			insertAttivita(attivita, autorizzazioni);
		    }
		}
	    } else {
		Attivita attivita = attivitaService.findById(new AttivitaId(nuovoSpuntista.getCategoriaMerceologica()));
		insertAttivita(attivita, autorizzazioni);
	    }
	    // INSERISCO PRESENZA PER L'AUTORIZZAZIONE INSERITA
	    log.debug("inserisceNuovoSpuntista# Inserimento giornata ed presenza");
	    this.spuntistaPresenteAndAggiungiCatMerceologica(nuovoSpuntista.getIdGiornata(), autorizzazioni.getId().getCodice(),
		    nuovoSpuntista.getCategoriaMerceologicaGiornata());
	    //GiornataMercatoSpuntistaRestBean spuntisti=new GiornataMercatoSpuntistaRestBean();
	    log.debug("inserisceNuovoSpuntista# End....");
	    // INSERISCO ALLEGATI PASSITI NELL'ANAGRAFICA
	    log.debug("inserisceNuovoSpuntista# Insert allegati in anagrafe = {}", anagrafeDaInserire.getId().getCodice());
	    List<Long> listAllegati = nuovoSpuntista.getAllegati();
	    Anagrafedocumenti anagrafeDoc = null;
	    for (Long codiceOggetto : listAllegati) {
		Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto.intValue()));
		if (oggetto != null) {
		    anagrafeDoc = new Anagrafedocumenti();
		    anagrafeDoc.setAnagrafe(anagrafeDaInserire);
		    anagrafeDoc.setDataregistrazione(new Date());
		    anagrafeDoc.setOggetto(oggetto);
		    anagrafeDoc.setRifdocumento(oggetto.getNomefile());
		    log.debug("inserisceNuovoSpuntista# Insert allegato. Anagrafe = {}, CodiceOggetto = {}", anagrafeDaInserire.getId().getCodice(),
			    codiceOggetto);
		    anagrafedocumentiService.insert(anagrafeDoc);
		} else {
		    log.error("inserisceNuovoSpuntista# oggetto con codiceoggetto {} nullo", codiceOggetto);
		}
	    }
	    return autorizzazioni.getId().getCodice();
	} catch (RuntimeException e) {
	    throw new MercatiAppException(e.getMessage(), e);
	}
    }

    private void insertAttivita(Attivita attivita, Autorizzazioni autorizzazioni) {

	AutorizzazioniAttivita autorizzazioniAttivita = new AutorizzazioniAttivita();
	autorizzazioniAttivita.setAttivita(attivita);
	autorizzazioniAttivita.setAutorizzazioni(autorizzazioni);
	autorizzazioniAttivitaService.insert(autorizzazioniAttivita);
    }

    @Override
    public List<CodiceDescrizioneBean> ricercaComuni(String testo) throws MercatiAppException {

	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	CodiceDescrizioneBean bean = null;
	String comuniEsclusi = "";
	String codice_sc = this.comportamentiMercatiService.restCercaComuneListEsclusi();
	if (StringUtils.isNotBlank(StringUtils.defaultString(codice_sc).trim())) {
	    comuniEsclusi = codice_sc.trim();
	}
	List<VwEntilocali> ris = vwEntilocaliService.findByDescrizione(testo);
	boolean add = true;
	for (VwEntilocali comuni : ris) {
	    add = true;
	    if (StringUtils.isNotBlank(comuniEsclusi)) {
		if (comuniEsclusi.indexOf(comuni.getCodicecomune()) >= 0) {
		    add = false;
		}
	    }
	    if (add) {
		bean = new CodiceDescrizioneBean();
		bean.setCodice(comuni.getCodicecomune());
		bean.setDescrizione(comuni.getComune());
		result.add(bean);
	    }
	}
	return result;
    }

    @Override
    public List<IdentificativoDescrizioneBean> findGiornateByResponsabileDallaDataAllaData(Integer codiceResponsabile, Date dallaData,
	    Date allaData) {

	List<IdentificativoDescrizioneBean> result = new ArrayList<IdentificativoDescrizioneBean>();
	List<MercatiResponsabili> mercatiResponsabilis = mercatiResponsabiliService.findByResponsabileDallaDataAllaData(codiceResponsabile, dallaData,
		allaData);
	Set<Integer> mercati = new HashSet<Integer>();
	Set<Integer> giorni = new HashSet<Integer>();
	for (MercatiResponsabili mercatiResponsabili : mercatiResponsabilis) {
	    Mercati mercato = mercatiService.findById(new PkId(mercatiResponsabili.getMercato().getId().getCodice()));
	    List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(mercato);
	    mercati.add(mercato.getId().getCodice());
	    for (MercatiUso mercatiUso : mercatiUsos) {
		Integer codiceuso = mercatiUso.getId().getCodice();
		giorni.add(codiceuso);
	    }
	}
	if (!(giorni.isEmpty() || mercati.isEmpty())) {
	    List<MercatipresenzeT> mpts = mercatipresenzeTService.findByMercatoAndMercatoUsoAndDateInterval(mercati, giorni, dallaData, allaData);
	    for (MercatipresenzeT mpt : mpts) {
		if (!mercatipresenzeTService.isGiornataMercatoChiusa(mpt)) {
		    String descrizione = mercatipresenzeTService.getDescrizioneMercato(mpt.getMercato().getDescrizione(),
			    mpt.getMercato().getDescrizione());
		    descrizione += " " + Utilities.formatDate(mpt.getDataRegistrazione(), false);
		    descrizione = Utilities.replaceDescrizioneGiorno(descrizione, mpt.getDataRegistrazione());
		    IdentificativoDescrizioneBean b = new IdentificativoDescrizioneBean();
		    b.setId(mpt.getId().getCodice());
		    b.setDescrizione(descrizione);
		    result.add(b);
		}
	    }
	}
	return result;
    }

    @Override
    public void updateNoteAutorizzazione(Integer id, String note) throws MercatiAppException {

	try {
	    autorizzazioniService.updateNoteAutorizzazione(id, note);
	} catch (Exception e) {
	    throw new MercatiAppException("UPD_AUT", e.getMessage());
	}
    }

    @Override
    public boolean verificaMercatoPerOperatore(Integer idGiornata, Integer codiceResponsabile) {

	if (codiceResponsabile == null || idGiornata == null) {
	    return false;
	}
	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	if (mpt == null) {
	    return false;
	}
	Integer codiceMercato = mpt.getMercato().getId().getCodice();
	int c = mercatiResponsabiliService.countByResponsabile(codiceResponsabile);
	if (c == 0) {
	    return true;
	}
	c = mercatiResponsabiliService.countByResponsabileAndMercato(codiceResponsabile, codiceMercato);
	return c > 0;
    }

    @Override
    public void updatePassaAFase(Integer codiceResponsabile, Integer idGiornata, Integer idFase) throws MercatiAppException {

	if (!(verificaMercatoPerOperatore(idGiornata, codiceResponsabile))) {
	    throw new MercatiAppException("SEC-00001", "Utenza non abilitata all'informazione");
	}
	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	MercatiSpunte spunta = mercatiSpunteService.findById(new PkId(idFase));
	mpt.setMercatiSpunte(spunta);
	mercatipresenzeTService.update(mpt);
	Responsabili r = responsabiliService.findById(new PkId(codiceResponsabile));
	LoggerUpdaterecord.log("L'operatore " + r + " ha modificato lo stato della giornata di mercato " + mpt.getId() + " " + mpt.getDescrizione(),
		r);
    }

    @Override
    public InfoAutorizzazioneRestBean infoAutorizzazione(Integer idAutorizzazione, Integer idGiornata) throws MercatiAppException {

	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(idAutorizzazione));
	InfoAutorizzazioneRestBean bean = new InfoAutorizzazioneRestBean();
	if (EntityUtils.getNestedProperty(autorizzazioni, "id.codice") != null) {
	    log.debug("infoAutorizzazione# populate AutorizzazioneRestBean...");
	    AutorizzazioneRestBean arb = new AutorizzazioneRestBean();
	    RestBeanHelper.populateAutorizzazioneRestBean(arb, autorizzazioni);
	    AnagraferestBean propr = new AnagraferestBean();
	    String nomeproprietario = StringUtils.isNotBlank(autorizzazioni.getAnagrafe().getNome())
		    ? autorizzazioni.getAnagrafe().getNominativo() + " " + autorizzazioni.getAnagrafe().getNome()
		    : autorizzazioni.getAnagrafe().getNominativo();
	    propr.setRagionesociale(nomeproprietario);
	    propr.setCodiceFiscale(autorizzazioni.getAnagrafe().getCodicefiscale());
	    if (autorizzazioni.getAnagrafe().getPartitaiva() != null) {
		propr.setPartitaIva(autorizzazioni.getAnagrafe().getPartitaiva());
	    }
	    arb.setProprietario(propr);
	    List<AutorizzazioniSoggetti> autorizzazioniSoggettis = autorizzazioniSoggettiService.findByAutorizzazione(idAutorizzazione);
	    if (!autorizzazioniSoggettis.isEmpty()) {
		List<AnagraferestBean> anagraferestBeans = new ArrayList<AnagraferestBean>();
		AnagraferestBean anagrafeBean = null;
		log.debug("infoAutorizzazione# populate AutorizzazioneRestBean.anagrafeBean ...");
		for (AutorizzazioniSoggetti autorizzazioniSoggetti : autorizzazioniSoggettis) {
		    anagrafeBean = new AnagraferestBean();
		    RestBeanHelper.populateAnagrafeRestBeanDaAnagrafe(anagrafeBean, autorizzazioniSoggetti.getAnagrafe());
		    anagraferestBeans.add(anagrafeBean);
		    arb.setCoadiuvanti(anagraferestBeans);
		}
	    }
	    bean.setAutorizzazione(arb);
	    Integer codiceAnagrafe = null;
	    AutorizzazioniCsi autcsi = autorizzazioniCsiService.findByAutorizzazione(idAutorizzazione);
	    Anagrafe a = autorizzazioni.getOccupante();
	    if (a == null) {
		a = autorizzazioni.getAnagrafe();
	    }
	    if (autcsi != null && autcsi.getAnagrafe() != null) {
		a = autcsi.getAnagrafe();
	    }
	    codiceAnagrafe = a.getId().getCodice();
	    a = anagrafeService.findById(new PkId(codiceAnagrafe));
	    // evita che la chiamata successiva "anagrafeService.findDatiAnagrafeDaWs(a.getCodicefiscale(), a, false);"
	    // che recupera i dati dal servizio AEEP e li setta all'oggetto "a" di tipo anagrafe faccia un update
	    // automatico (Hibernate)
	    anagrafeDAO.evict(a);
	    String nome = StringUtils.defaultIfEmpty(a.getNome(), "");
	    String nominativo = StringUtils.defaultIfEmpty(a.getNominativo(), "");
	    AnagraferestBean anagrafeBean = new AnagraferestBean();
	    Anagrafe result = null;
	    try {
		log.debug("infoAutorizzazione# prima di chiamare il WS per AEEP CF = {}, idAutorizzazione = {}",
			new Object[] { a.getCodicefiscale(), idAutorizzazione });
		result = anagrafeService.findDatiAnagrafeDaWs(a.getCodicefiscale(), a, false);
		// Il nome/nominativo presentato sull'app non deve essere quello di AEEP, ma quello salvato
		// sul backoffice. In qusro caso l'anagrafe non potra mai essere null e quindi avremo questa 
		// informazione.
		// Il servizio viene utilizzato solo per recuperari altri dati come "residenza", "numero rea", etc
		// che dovranno essere quelli registari su AEEP.
		if (result != null) {
		    result.setNome(nome);
		    result.setNominativo(nominativo);
		}
	    } catch (Exception be) {
		log.error("infoAutorizzazione# errore ", be);
	    }
	    if (result == null) {
		result = a;
	    }
	    RestBeanHelper.populateAnagrafeRestBeanDaAnagrafe(anagrafeBean, result);
	    bean.setAltriDati(anagrafeBean);
	    List<AnagrafedocumentiDTO> docs = anagrafedocumentiService.findByIstanzaAndAnagrafeDTO(null, a, true);
	    List<DocumentiRestBean> drb = new ArrayList<DocumentiRestBean>();
	    for (AnagrafedocumentiDTO adto : docs) {
		if (adto.getCodiceOggetto() != null) {
		    Oggetti objLazy = oggettiService.findByIdLazy(new PkId(adto.getCodiceOggetto()));
		    if (objLazy != null) {
			DocumentiRestBean d = new DocumentiRestBean();
			d.setNomeFile(objLazy.getNomefile());
			d.setDescrizione(d.getNomeFile());
			String uuid = oggettiService.insertOrGetUID(adto.getCodiceOggetto());
			d.setDownloadUrl("download/" + uuid);
			drb.add(d);
		    }
		}
	    }
	    if (!drb.isEmpty()) {
		bean.setDocumenti(drb);
	    }
	    MercatipresenzeD mpd = mercatipresenzeDService.findByMercatiPresenzeTAndAutorizzazione(idGiornata, idAutorizzazione);
	    if (mpd != null && mpd.isSpuntista() && mpd.getAutorizzazioni() != null && mpd.getAutorizzazioni().getId() != null
		    && mpd.getAutorizzazioni().getId().getCodice() != null && idAutorizzazione.equals(mpd.getAutorizzazioni().getId().getCodice())
		    && mpd.getDettPosizioneDebitoria() != null && mpd.getDettPosizioneDebitoria().getId() != null
		    && mpd.getDettPosizioneDebitoria().getId().getCodice() != null) {
		PagamentiMercatoPosizDebRestHelper posizioneDebitoriaSpuntista = mercatipresenzeDService.populatePosizioneDebitoriaHelper(mpd);
		bean.setPosizioneDebitoriaSpuntista(posizioneDebitoriaSpuntista);
	    }
	    List<AutorizzazioniConcessioni> autConc = autorizzazioniConcessioniService
		    .findByAutorizzazioneAttuale(autorizzazioni.getId().getCodice());
	    List<AutorizzazioniFrontRestBean> aut = new ArrayList<AutorizzazioniFrontRestBean>();
	    for (AutorizzazioniConcessioni autorizzazioniConcessioni : autConc) {
		if (autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll() != null
			&& autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getId().getCodice() != null) {
		    AutorizzazioniFrontRestBean autColl = new AutorizzazioniFrontRestBean();
		    autColl.setId(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getId().getCodice());
		    autColl.setNumero(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getAutoriznumero());
		    autColl.setComuneRilascio(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getAutorizcomune().getComune());
		    autColl.setData(Utilities.formatDate(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getAutorizdata(), false));
		    AnagraferestBean prop = new AnagraferestBean();
		    nomeproprietario = StringUtils.isNotBlank(a.getNome()) ? a.getNominativo() + " " + a.getNome() : a.getNominativo();
		    prop.setRagionesociale(nomeproprietario);
		    prop.setCodiceFiscale(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getAnagrafe().getCodicefiscale());
		    if (autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getAnagrafe().getPartitaiva() != null) {
			prop.setPartitaIva(autorizzazioniConcessioni.getAutorizzazioniByFkAutconcAutcoll().getAnagrafe().getPartitaiva());
		    }
		    autColl.setProprietario(prop);
		    aut.add(autColl);
		}
	    }
	    bean.setAutorizzazioniConcessioniColl(aut);
	}
	return bean;
    }

    @Override
    public void impostaIncaricatoVendita(Integer idGiornata, Integer idAutorizzazione, Integer idAnagrafe) throws MercatiAppException {

	MercatipresenzeD d = mercatipresenzeDService.findByMercatiPresenzeTAndAutorizzazione(idGiornata, idAutorizzazione);
	if (idAnagrafe != null) {
	    log.debug("impostaIncaricatoVendita# Inserisco collaboratore idAnagrafe = {} nella presenza idGiornata = {}, idAutorizzazione = {}",
		    new Object[] { idAnagrafe, idGiornata, idAutorizzazione });
	    Anagrafe anagrafe = anagrafeService.findById(new PkId(idAnagrafe));
	    d.setCollaboratore(anagrafe);
	} else {
	    log.debug("impostaIncaricatoVendita# Elimino coadiovante nella presenza idAutorizzazione = {}, idAutorizzazione = {}",
		    new Object[] { idGiornata, idAutorizzazione });
	    d.setCollaboratore(null);
	}
	try {
	    log.debug("impostaIncaricatoVendita# update mercatopresenze_d = {}", d.getId().getCodice());
	    mercatipresenzeDService.update(d);
	} catch (Exception e) {
	    throw new MercatiAppException("UPD_MER_PRES_D", e.getMessage());
	}
    }

    @Override
    public AutorizzazioniSoggetti insertAnagrafeInAutorizzazioniSoggetti(String codiceFiscale, String nome, String cognome, Integer idAutorizzazione)
	    throws MercatiAppException {

	Anagrafe a = new Anagrafe();
	a.setCodicefiscale(codiceFiscale);
	a.setTipoanagrafe(WebConstants.PERSONA_FISICA);
	a = anagrafeService.bindDomainObject(a, PkId.class, "id.codice");
	boolean isNuovoCollaboratore = false;
	if (EntityUtils.getNestedProperty(a, "id.codice") != null) {
	    log.debug("inserAnagrafeInAutorizzazioniSoggetti# Angrafe trovato per cf = {}. IdAnagrafe = {}",
		    new Object[] { codiceFiscale, a.getId().getCodice() });
	    a.setNome(nome);
	    a.setNominativo(cognome);
	    anagrafeService.update(a);
	} else {
	    isNuovoCollaboratore = true;
	    log.debug("inserAnagrafeInAutorizzazioniSoggetti# Angrafe trovato per cf = {}. Insert Anagrafe nome = {}, cognome, cf = {}.",
		    new Object[] { codiceFiscale, nome, cognome, codiceFiscale });
	    a = new Anagrafe();
	    a.setCodicefiscale(codiceFiscale);
	    a.setNome(nome);
	    a.setNominativo(cognome);
	    a.setTipoanagrafe(WebConstants.PERSONA_FISICA);
	    anagrafeService.insert(a);
	    log.debug("inserAnagrafeInAutorizzazioniSoggetti# Angrafe inserito. IdAnagrafe = {}", new Object[] { a.getId().getCodice() });
	}
	AutorizzazioniSoggetti autSogg = null;
	if (!isNuovoCollaboratore) {
	    log.debug(
		    "insertAnagrafeInAutorizzazioniSoggetti# Angrafe già presente.Devo controllare se già era agganciata come collaboratore per autorizzazione. CF = {}, id Aut = {}",
		    codiceFiscale, idAutorizzazione);
	    autSogg = autorizzazioniSoggettiService.findByAutorizzazioneAndAnagrafe(idAutorizzazione, a.getId().getCodice());
	    if (EntityUtils.getNestedProperty(autSogg, "id.codice") != null) {
		log.error("insertAnagrafeInAutorizzazioniSoggetti# Collaboratore già presente. IdAutorizzazione = {}, IdAnagrafe = {} ",
			idAutorizzazione, a.getId().getCodice());
		String err = getMessageFromBundle("autorizzazioni_soggetti.service_error.collaboratore_presente", null);
		throw new MercatiAppException("5001", err);
	    }
	}
	log.debug("inserAnagrafeInAutorizzazioniSoggetti# find autorizzazione. idAutorizzazione = {}", idAutorizzazione);
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
	log.debug("inserAnagrafeInAutorizzazioniSoggetti# inserisco collaboratore. IdAutorizzazione = {}, IdAnagrafe = {}", idAutorizzazione,
		a.getId().getCodice());
	autSogg = new AutorizzazioniSoggetti();
	autSogg.setAnagrafe(a);
	autSogg.setAutorizzazioni(aut);
	autorizzazioniSoggettiService.insert(autSogg);
	return autSogg;
	//AnagraferestBean coadiuvante
    }

    @Override
    public AutorizzazioniSoggetti insertAnagrafeInAutorizzazioniSoggettiAndUpdateSuPosteggio(String codiceFiscale, String nome, String cognome,
	    Integer idAutorizzazione, Integer idGiornata) throws MercatiAppException {

	AutorizzazioniSoggetti autorizzazioniSoggetti = this.insertAnagrafeInAutorizzazioniSoggetti(codiceFiscale, nome, cognome, idAutorizzazione);
	this.impostaIncaricatoVendita(idGiornata, idAutorizzazione, autorizzazioniSoggetti.getAnagrafe().getId().getCodice());
	return autorizzazioniSoggetti;
    }

    @Override
    public ResponsabileRestBean infoUtente() throws MercatiAppException {

	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	ResponsabileRestBean responsabileRestBean = new ResponsabileRestBean();
	RestBeanHelper.populateResponsabileRestBean(responsabileRestBean, r);
	return responsabileRestBean;
    }

    @Override
    public List<IdentificativoDescrizioneBean> uploadFile(List<Attachment> allAttachments) throws MercatiAppException {

	List<IdentificativoDescrizioneBean> identificativoDescrizioneBeans = new ArrayList<IdentificativoDescrizioneBean>();
	IdentificativoDescrizioneBean file = null;
	if (!allAttachments.isEmpty()) {
	    try {
		for (Attachment attachment : allAttachments) {
		    file = new IdentificativoDescrizioneBean();
		    log.debug("uploadFile# getByte array...");
		    byte[] b = Utilities.dataHandlerToBytes(attachment.getDataHandler());
		    log.debug("uploadFile# getFileName...");
		    MultivaluedMap<String, String> header = attachment.getHeaders();
		    String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
		    log.debug("uploadFile# prepare oggetto...");
		    String nameFile = getNameFile(contentDisposition);
		    Oggetti o = new Oggetti();
		    o.setDimensioneFile(b.length);
		    o.setNomefile(nameFile);
		    o.setOggetto(b);
		    log.debug("uploadFile# insert oggetto...");
		    oggettiService.insert(o);
		    file.setDescrizione(o.getNomefile());
		    file.setId(o.getId().getCodice());
		    identificativoDescrizioneBeans.add(file);
		}
	    } catch (Exception e) {
		log.error("uploadFile# {}", e);
		throw new MercatiAppException("5001", e);
	    }
	}
	return identificativoDescrizioneBeans;
    }

    private String getNameFile(String[] contentDisposition) {

	String exactFileName = "";
	for (String filename : contentDisposition) {
	    if ((filename.trim().startsWith("filename"))) {
		String[] name = filename.split("=");
		exactFileName = name[1].trim().replaceAll("\"", "");
	    }
	}
	return exactFileName;
    }

    @Override
    public boolean isGiornataNelFuturo(Integer idGiornata) {

	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	{
	    Date dataReg = mpt.getDataRegistrazione();
	    Date today = new Date();
	    if (Utilities.compareDates(dataReg, today) == 1) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public PosteggioInfoRestBean calcolaCostoPosteggio(Integer idMercatipresenzeD) throws MercatiAppException {

	try {
	    return this.mercatipresenzeDService.calcolaCostoPosteggio(idMercatipresenzeD);
	} catch (Exception e) {
	    throw new MercatiAppException("-999", e.getMessage());
	}
    }

    @Override
    public PosteggioInfoRestBean calcolaCostoPosteggio(Integer idGiornata, Integer idAutorizzazione, Integer idPosteggio) throws MercatiAppException {

	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	if (mpt == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. La giornata con identificativo " + idGiornata + " non esiste.");
	}
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	if (posteggio == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. Il posteggio con identificativo " + idPosteggio + " non esiste.");
	}
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
	if (aut == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. L' autorizzazione con identificativo " + idAutorizzazione + " non esiste.");
	}
	MercatipresenzeD pres = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	return this.calcolaCostoPosteggio(pres.getId().getCodice());
    }

    @Override
    public List<AutorizzazioniFrontRestBean> findAutorizzazioniHelperByUtente(Anagrafe r, boolean consideraAncheIlProprietarioTraLeAnagrafiche,
	    RuoloAutorizzazioneEnum ruolo, boolean soloAutorizzazioniAttive) {

	List<AutorizzazioniFrontRestBean> result = new ArrayList<AutorizzazioniFrontRestBean>();
	Set<Integer> codiciAnagrafe = anagrafeService.findCodiciAnagraficheCollegate(r.getId().getCodice(),
		RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI);
	codiciAnagrafe.add(r.getId().getCodice());
	List<AutorizzazioniRestHelper> hlps = autorizzazioniService.findAutorizzazioniAnagrafiche(codiciAnagrafe,
		consideraAncheIlProprietarioTraLeAnagrafiche, ruolo, soloAutorizzazioniAttive);
	Set<Integer> autTrovate = new HashSet<Integer>();
	for (AutorizzazioniRestHelper arh : hlps) {
	    Integer idAutorizzazione = arh.getIdAutorizzazione();
	    Integer fkIdautAttuale = autorizzazioniConcessioniService.getFkIdautAttualePerAutCollegata(idAutorizzazione);
	    if (fkIdautAttuale != null) {
		// Task 20857: Autorizzazione con doppio atto, nascondere le autorizzazioni Collegate alla concessione
		// ==> Devo poter ricercare anche per autorizzazione collegata e non presentarla, ovvero la presenza va solamente alla concessione ma il filtro di ricerca deve considerare anche le collegate in quanto non so con quale atto si presenta l'ambulante
		// SE NON RITORNA NULLA LA RICERCA:
		// Significa che il riferimento dell'autorizzazione trovata è una autorizzazione collegata ad una concessione
		// il vero id autorizzazione da cercare è idConcessionePerAutCollegata
		// non ritorna l'autorizzazione del doppio atto
		continue;
	    }
	    if (!autTrovate.contains(idAutorizzazione)) { // POTREI AVERLA GIA' INSERITA COME CONCESSIONE
		AutorizzazioniFrontRestBean b = autorizzazioniRestHelperToFrontRestBean(arh);
		result.add(b);
		autTrovate.add(idAutorizzazione);
	    }
	}
	return result;
    }

    private AutorizzazioniFrontRestBean autorizzazioniRestHelperToFrontRestBean(AutorizzazioniRestHelper arh) {

	AutorizzazioniFrontRestBean b = new AutorizzazioniFrontRestBean();
	b.setId(arh.getIdAutorizzazione());
	if (arh.getAutorizdata() != null) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(arh.getAutorizdata());
	    b.setAnno(c.get(Calendar.YEAR));
	    b.setData(Utilities.formatDate(arh.getAutorizdata(), false));
	}
	b.setNumero(arh.getAutoriznumero());
	b.setComuneRilascio(arh.getAutorizcomune());
	b.setNumeroAutOrig(arh.getAutorignumero());
	b.setAutPrecedenteNumero(arh.getAutPrecedenteNumero());
	b.setAutPrecedenteComune(arh.getAutPrecComune());
	//
	StatoEAvvisoDaRestHelper stato = StatoEAvvisoDaRestHelper.fromAutorizzazioniRestHelper(arh);
	b.setStatoAutorizzazione(stato.getStatoAutorizzazione());
	b.setAvviso(stato.isAvviso());
	b.setCausaleSospensione(stato.getCausaleSospensione());
	//
	b.setValidaPerSpunta(true);
	if (arh.getValidaSpunta() != null) {
	    b.setValidaPerSpunta(arh.getValidaSpunta());
	}
	AnagraferestBean proprietario = new AnagraferestBean();
	String nomeproprietario = StringUtils.isNotBlank(arh.getTitNome()) ? arh.getTitNominativo() + " " + arh.getTitNome() : arh.getTitNominativo();
	proprietario.setRagionesociale(nomeproprietario);
	proprietario.setCodiceFiscale(arh.getTitCodicefiscale());
	b.setProprietario(proprietario);
	if (arh.getGerenteNominativo() != null) {
	    AnagraferestBean gerente = new AnagraferestBean();
	    String nomeGerente = StringUtils.isNotBlank(arh.getGerenteNome()) ? arh.getGerenteNominativo() + " " + arh.getGerenteNome()
		    : arh.getGerenteNominativo();
	    gerente.setRagionesociale(nomeGerente);
	    gerente.setCodiceFiscale(arh.getGerenteCodicefiscale());
	    b.setGerente(gerente);
	    b.setRuoloAutorizzazione("Titolare gerente");
	} else {
	    b.setRuoloAutorizzazione("Titolare proprietario");
	}
	return b;
    }

    @Override
    public AutorizzazioniFrontRestBean findAutorizzazioneHelperById(Integer idAutorizzazione) {

	AutorizzazioniRestHelper hlp = autorizzazioniService.findAutorizzazioneRestHelper(idAutorizzazione);
	AutorizzazioniFrontRestBean b = autorizzazioniRestHelperToFrontRestBean(hlp);
	return b;
    }

    @Override
    public String exportModalitaPentaho(Integer giornoMercato, Esportazioni esportazioni, String email, boolean isInvioMail) {

	log.debug("exportModalitaPentaho# Start export pentaho, contesto : {}", TipicontestoesportazioniEnum.GIORNATA_MERCATO);
	String sessionId = ORMHelper.getToken();
	log.debug("exportModalitaPentaho# Cancello i record su tmp_esportazioni con sessionId: {}", sessionId);
	tmpEsportazioniService.deleteBysessionId(sessionId);
	TmpEsportazioni entity = new TmpEsportazioni();
	entity.setCodice(giornoMercato);
	entity.setCodicecomune(null);
	entity.setData(Calendar.getInstance().getTime());
	entity.setIdcomune(ORMHelper.getIdcomune());
	entity.setSessionid(ORMHelper.getToken());
	tmpEsportazioniService.insert(entity);
	return sessionId;
    }

    @Override
    public List<CodiceDescrizioneBean> ricercaTuttiComuni() throws MercatiAppException {

	List<CodiceDescrizioneBean> result = new ArrayList<CodiceDescrizioneBean>();
	CodiceDescrizioneBean bean = null;
	List<VwEntilocali> ris = vwEntilocaliService.findByDescrizione(null);
	for (VwEntilocali comuni : ris) {
	    bean = new CodiceDescrizioneBean();
	    bean.setCodice(comuni.getCodicecomune());
	    bean.setDescrizione(comuni.getComune());
	    result.add(bean);
	}
	return result;
    }

    @Override
    public CodiceDescrizioneBean updateInfoUtente(DettaglioAnagrafeRestBean bean) {

	Integer codiceAnagrafe = bean.getId();
	Anagrafe r = anagrafeService.findById(new PkId(codiceAnagrafe));
	if (r == null) {
	    return newNVBean("Nessun dato passato", "500");
	}
	String mail = "";
	String telefono = "";
	if (bean.getPersona_fisica() == null && bean.getPersona_giuridica() == null) {
	    return newNVBean("Nessun dato passato", "500");
	}
	if (bean.getPersona_fisica() != null) {
	    mail = bean.getPersona_fisica().getEmail();
	    telefono = bean.getPersona_fisica().getTelefono();
	} else {
	    mail = bean.getPersona_giuridica().getEmail();
	    telefono = bean.getPersona_giuridica().getTelefono();
	}
	if (StringUtils.isBlank(mail)) {
	    return newNVBean("Indirizzo mail vuoto", "500");
	}
	boolean validaIndirizzoMail = Utilities.validaIndirizzoMail(mail);
	if (!validaIndirizzoMail) {
	    return newNVBean("Indirizzo mail non valido", "500");
	}
	r.setEmail(mail);
	r.setTelefono(telefono);
	anagrafeService.update(r);
	List<AutorizzazioniFrontRestBean> hlp = findAutorizzazioniHelperByUtente(r, false, null, true);
	for (AutorizzazioniFrontRestBean afrb : hlp) {
	    Integer codiceAnagrafeAut = null;
	    if (afrb.getGerente() != null && afrb.getGerente().getId() != null) {
		codiceAnagrafeAut = afrb.getGerente().getId();
	    }
	    if (codiceAnagrafeAut == null) {
		if (afrb.getProprietario() != null && afrb.getProprietario().getId() != null) {
		    codiceAnagrafeAut = afrb.getProprietario().getId();
		}
	    }
	    if (codiceAnagrafeAut != null && !codiceAnagrafe.equals(codiceAnagrafeAut)) {
		Anagrafe a = anagrafeService.findById(new PkId(codiceAnagrafeAut));
		a.setEmail(mail);
		a.setTelefono(telefono);
		anagrafeService.update(r);
	    }
	}
	return newNVBean("", "200");
    }

    protected CodiceDescrizioneBean newNVBean(String descrizione, String codice) {

	CodiceDescrizioneBean result = new CodiceDescrizioneBean();
	result.setDescrizione(descrizione);
	result.setCodice(codice);
	return result;
    }

    @Override
    public StatoPagamentoSpuntistaRestHelper verificaStatoPagamentoSpuntista(Integer idGiornata, Integer idAutorizzazione, Integer idPosteggio)
	    throws MercatiAppException {

	StatoPagamentoSpuntistaRestHelper result = new StatoPagamentoSpuntistaRestHelper();
	PosteggioInfoRestBean hlp = this.calcolaCostoPosteggio(idGiornata, idAutorizzazione, idPosteggio);
	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	if (mpt == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. La giornata con identificativo " + idGiornata + " non esiste.");
	}
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	if (posteggio == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. Il posteggio con identificativo " + idPosteggio + " non esiste.");
	}
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
	if (aut == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. L' autorizzazione con identificativo " + idAutorizzazione + " non esiste.");
	}
	result.setPosteggioInfo(hlp);
	MercatipresenzeD pres = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	boolean nodoPagamentiAttivo = nodoPagamentiService.isAttivoNodoPagamenti(idGiornata);
	result.setNodoPagamentiAttivo(nodoPagamentiAttivo);
	if (nodoPagamentiAttivo) {
	    StatoPagamentoNodoHelper statoPagamento = new StatoPagamentoNodoHelper();
	    if (pres.getDettPosizioneDebitoria() != null) {
		statoPagamento = nodoPagamentiService.getStatoPagamentoSpuntistaHelper(pres.getId().getCodice());
		result.setStatoPagamento(statoPagamento);
		result.setPagato(Boolean.FALSE);
		if (mercatipresenzeDService.verificaAndAggiornaStatoPagamenti(pres.getDettPosizioneDebitoria().getId().getCodice(), null, true)) {
		    result.setPagato(Boolean.TRUE);
		}
	    } else {
		result.setLabel("Sembra che non sia stato possibile contattare il servizio di pagamenti");
	    }
	} else {
	    result.setPagato(pres.getFlagPagato());
	    result.setLabel("PAGAMENTO EFFETTUATO");
	}
	return null;
    }

    /*
    @Override
    public CodiceDescrizioneBean annullaPagamentoASpuntista(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione)
    	    throws MercatiAppException {
    
    	if (!nodoPagamentiService.isAttivoNodoPagamenti(idGiornata)) {
    	    return newNVBean("", "200");
    	}
    	MercatipresenzeD pres = getPresenza(idGiornata, idPosteggio, idAutorizzazione);
    	try {
    	    nodoPagamentiService.annullaPosizioneDebitoriaSpuntista(pres.getId().getCodice());
    	} catch (FunzioneBusinessRemotaException e) {
    	    throw new MercatiAppException(e);
    	}
    	return newNVBean("", "200");
    }
    */
    @Override
    public MercatipresenzeD getPresenza(Integer idGiornata, Integer idPosteggio, Integer idAutorizzazione) throws MercatiAppException {

	MercatipresenzeT mpt = mercatipresenzeTService.findById(new PkId(idGiornata));
	if (mpt == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. La giornata con identificativo " + idGiornata + " non esiste.");
	}
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	if (posteggio == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. Il posteggio con identificativo " + idPosteggio + " non esiste.");
	}
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
	if (aut == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. L' autorizzazione con identificativo " + idAutorizzazione + " non esiste.");
	}
	MercatipresenzeD pres = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(idGiornata, idPosteggio);
	if (pres == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. La presenza per i dati giorno: " + idGiornata + ", posteggio: " + idPosteggio +
						  ", aut: " + idAutorizzazione + "   non esiste.");
	}
	if (pres.getAutorizzazioni() == null) {
	    throw new MercatiAppException("-999", "Dati non corretti. L'autorizzazione per i dati giorno: " + idGiornata + ", posteggio: " +
						  idPosteggio + ", aut: " + idAutorizzazione + "   non esiste.");
	}
	if (!pres.getAutorizzazioni().getId().getCodice().equals(idAutorizzazione)) {
	    throw new MercatiAppException("-999",
		    "Dati non corretti. La presenza con codice " + pres.getId().getCodice() +
						  " non appartiene all'autorizzazione per i dati giorno: " + idGiornata + ", posteggio: " +
						  idPosteggio + ", aut: " + idAutorizzazione + "   non esiste.");
	}
	return pres;
    }

    @Override
    public void updateCfAnagrafeSpuntista(Integer idAutSpuntista, String codiceFiscale) throws MercatiAppException {

	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutSpuntista));
	if (aut == null) {
	    log.error("Aut non trovata con il codice {}", idAutSpuntista);
	    throw new MercatiAppException("Autorizzazione non trovata con id: " + idAutSpuntista);
	}
	Anagrafe a = aut.getOccupante();
	if (a == null) {
	    a = aut.getAnagrafe();
	}
	AutorizzazioniCsi autcsi = autorizzazioniCsiService.findByAutorizzazione(idAutSpuntista);
	if (autcsi != null) {
	    if (autcsi.getAnagrafe() != null) {
		a = autcsi.getAnagrafe();
	    }
	}
	if (a == null) {
	    log.error("Anagrafe non trovata per l'autorizzazione {}", aut.getTransientEstremiAut());
	    throw new MercatiAppException("Autorizzazione non trovata con id: " + idAutSpuntista);
	}
	if (StringUtils.isNotBlank(a.getCodicefiscale())) {
	    log.error("L'anagrafe trovata per l'autorizzazione {} ha già il codice fiscale impostato {}", aut.getTransientEstremiAut(), a);
	    throw new MercatiAppException(
		    "L'anagrafe trovata per l'autorizzazione " + aut.getTransientEstremiAut() + " ha già il codice fiscale impostato " + a);
	}
	try {
	    anagrafeService.updateCfAnagrafe(a.getId().getCodice(), codiceFiscale);
	} catch (Exception e) {
	    log.error("Errore nell'aggiornamento del codice fiscale per l'anagrafe {}", a, e);
	    throw new MercatiAppException("Errore nell'aggiornamento del codice fiscale per l'anagrafe {}" + a, e);
	}
    }

    @Override
    public boolean verificaInserimentoPagamento(MercatipresenzeD pres, Integer idPosteggio, Integer idAutorizzazione, boolean throwException) {

	return true;
    }

    @Override
    public List<BlackListAttivaBean> findAutorizzazioniInBlackList() {

	return blacklistAutorizzazioniService.findAutorizzazioniInBlackListAttive();
    }

    @Override
    public BlackListDettaglio findDettaglioBlackListAutorizzazioneEGiornata(Integer idAutorizzazione, Integer idGiornata, BlackListContestoEnum[] contesti) {

	BlackListDettaglio result = new BlackListDettaglio();
	// TROVO I vari inserimenti nella blacklist per quella autorizzazione
	Integer idMercatiUso = null;
	if (idGiornata != null) {
	    MercatipresenzeT giornata = this.mercatipresenzeTService.findById(new PkId(idGiornata));
	    idMercatiUso = giornata.getMercatoUso().getId().getCodice();
	}
	List<BlacklistMotivi> motivis = blacklistMotiviService.findByAutorizzazioneEUso(idAutorizzazione, idMercatiUso, true, contesti);
	List<BlackListOggettoDettaglio> oggettoResult = new ArrayList<BlackListOggettoDettaglio>();
	// li ciclo
	for (BlacklistMotivi blm : motivis) {
	    BlackListOggettoDettaglio dettaglio = new BlackListOggettoDettaglio();
	    dettaglio.setMotivo(blm.getMotivo());
	    dettaglio.setData_inserimento_black_list(Utilities.formatDate(blm.getDataInizioBl(), false));
	    List<BlacklistAutorizzazioni> auts = blacklistAutorizzazioniService.findByBlackListMotivo(blm.getId().getCodice());
	    AutorizzazioniFrontRestBean autorizzazione = null;
	    for (BlacklistAutorizzazioni bla : auts) {
		autorizzazione = findAutorizzazioneHelperById(bla.getAutorizzazioni().getId().getCodice());
		if (BooleanUtils.isTrue(bla.getFlagPrincipale())) {
		    dettaglio.setAutorizzazione(autorizzazione);
		} else {
		    dettaglio.getAutorizzazioniCollegate().add(autorizzazione);
		}
	    }
	    PosizioneDebitoriaModel posizioneDebitoria = null;
	    List<BlacklistSrcPDebSp> pd = blacklistSrcPDebSpService.findByBlackListMotivo(blm.getId().getCodice());
	    for (BlacklistSrcPDebSp blpd : pd) {
		// prendo la prima
		posizioneDebitoria = PosizioneDebitoriaModel.fromDettPosizioneDebitoria(blpd.getDettPosizioneDebitoria());
		break;
	    }
	    dettaglio.setPosizioneDebitoria(posizioneDebitoria);
	    oggettoResult.add(dettaglio);
	}
	result.setDettaglioBlackList(oggettoResult);
	return result;
    }

    @Override
    public void modificaFasciaGiornata(Integer idGiornata, Integer idFasciaMercato) throws MercatiAppException {

	MercatipresenzeT entity = mercatipresenzeTService.findById(new PkId(idGiornata));
	if (entity == null) {
	    throw new MercatiAppException("Giornata di mercato non trovata con il riferimento " + idGiornata);
	}
	if (idFasciaMercato == null) {
	    throw new MercatiAppException("La fascia da impostare non può essere nulla");
	}
	Integer oldStato = null;
	if (entity.getConcessioniuso() != null && entity.getConcessioniuso().getId() != null
		&& entity.getConcessioniuso().getId().getCodice() != null) {
	    oldStato = entity.getConcessioniuso().getId().getCodice();
	}
	Concessioniuso concuso = null;
	concuso = concessioniusoService.findById(new PkId(idFasciaMercato));
	if (null == concuso) {
	    throw new MercatiAppException("Giornata di mercato non trovata con il riferimento " + idGiornata);
	}
	entity.setConcessioniuso(concuso);
	mercatipresenzeTService.update(entity);
	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	LoggerUpdaterecord.log("Modificato la fascia della giornata di mercato " + entity.getId() + " " + entity.getDescrizione() + " dal codice " +
			       oldStato + " al codice " + idFasciaMercato,
		r);
    }

    @Override
    public MappaMercatoBean getMappaMercato(Integer idGiornata) throws MercatiAppException {

	MappaMercatoBean result = new MappaMercatoBean();
	MercatipresenzeT mercatipresenzeT = mercatipresenzeTService.findById(new PkId(idGiornata));
	if (mercatipresenzeT == null) {
	    throw new IllegalArgumentException("errore [getMappaMercato] idGiornata non valido");
	}
	Mercati mercato = mercatiService.findById(new PkId(mercatipresenzeT.getMercato().getId().getCodice()));
	if (mercato.getOggetto() != null) {
	    Oggetti oggetti = oggettiService.findById(mercato.getOggetto().getId());
	    result.setMappa(Base64.encode(oggetti.getOggetto()));
	}
	List<PosteggioMercatoBean> posteggi = new ArrayList<PosteggioMercatoBean>();
	List<PosteggioMercatoBean> posteggiMercato = mercatiDService.findByIdGiornata(idGiornata);
	posteggi.addAll(posteggiMercato);
	result.setPosteggi(posteggi);
	return result;
    }

    @Override
    public MercatipresenzeD updatePresenzaSpuntistaNoPosteggio(Integer presenzaDId, Integer codiceMercato, Integer idPosteggio, Integer usoMercato,
	    Date giornoMercato) throws MercatiAppException {

	MercatipresenzeD daEliminare = mercatipresenzeDService.findById(new PkId(presenzaDId));
	// recupero il mercato
	Mercati mercati = mercatiService.findById(new PkId(codiceMercato));
	// recupero l'uso del mercato
	MercatiUso uso = mercatiUsoService.findById(new PkId(usoMercato));
	// recupero il giorno del mercato
	Calendar data = Calendar.getInstance();
	data.setTime(giornoMercato);
	MercatipresenzeT giorno = this.mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercati, uso);
	MercatiSpunte origine = null;
	if (daEliminare.getMercatiSpunte() != null) {
	    origine = daEliminare.getMercatiSpunte(); // se aveva già settato la FASE gliela reimposto
	}
	MercatipresenzeD presenza = this.segnaPresenzaSpuntista(giorno, idPosteggio, daEliminare.getAutorizzazioni().getId().getCodice(), null);
	if (origine != null && presenza != null) {
	    // se aveva già settato la FASE gliela reimposto
	    presenza.setMercatiSpunte(origine);
	}
	mercatipresenzeDService.delete(daEliminare);
	//Non c'è evento di revoca in qanto lo spuntista non aveva il posteggio prima
	mercatipresenzeDService.update(presenza);
	EventoPresenzaInserita evPresenza = new EventoPresenzaInserita(presenza);
	try {
	    this.eventPublisher.publishThrowOnFailure(evPresenza);
	} catch (EventAbortedException e) {
	    throw new MercatiAppException(e.getMessage());
	}
	return presenza;
    }

    @Override
    public MercatipresenzeD segnaPresenzaSpuntistaDaGraduatoria(Integer idPresenza, Integer idAutorizzazione) throws MercatiAppException {

	MercatipresenzeD presD = mercatipresenzeDService.findById(new PkId(idPresenza));
	// recupero l'uso del mercato
	MercatiD posteggio = presD.getPosteggio();
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(idAutorizzazione));
	// inserisco lo spuntista
	MercatipresenzeD presenza = this.segnaPresenzaSpuntista(presD.getMercatiPresenzeT(), posteggio.getId().getCodice(), idAutorizzazione, null);
	this.mercatipresenzeDService.update(presenza);
	EventoPresenzaInserita evPresenza = new EventoPresenzaInserita(presenza);
	try {
	    this.eventPublisher.publishThrowOnFailure(evPresenza);
	} catch (EventAbortedException e) {
	    throw new MercatiAppException(e.getMessage());
	}
	return presenza;
    }

    @Override
    public MercatipresenzeD segnaPresenzaSpuntistaDaLista(Integer idPresenzaSrc, Integer idPresenzaDst) throws MercatiAppException {

	MercatipresenzeD daEliminare = this.mercatipresenzeDService.findById(new PkId(idPresenzaSrc));
	Autorizzazioni aut = autorizzazioniService.findById(new PkId(daEliminare.getAutorizzazioni().getId().getCodice()));
	//
	MercatiSpunte origine = null;
	if (daEliminare.getMercatiSpunte() != null) {
	    origine = daEliminare.getMercatiSpunte(); // se aveva già settato la FASE gliela reimposto
	}
	MercatipresenzeD daAggiornare = this.mercatipresenzeDService.findById(new PkId(idPresenzaDst));
	boolean isSpuntista = daAggiornare.isSpuntista();
	boolean isPosteggioOccupato = daAggiornare.isPosteggioOccupato();
	// recupero l'uso del mercato
	MercatiD posteggio = daAggiornare.getPosteggio();
	// inserisco lo spuntista
	MercatipresenzeD presenza = this.segnaPresenzaSpuntista(daAggiornare.getMercatiPresenzeT(), posteggio.getId().getCodice(),
		aut.getId().getCodice(), null);
	if (origine != null && presenza != null) {
	    // se aveva già settato la FASE gliela reimposto
	    presenza.setMercatiSpunte(origine);
	}
	this.mercatipresenzeDService.delete(daEliminare);
	try {
	    if (isPosteggioOccupato) {
		EventoPresenzaRevocata revoca = new EventoPresenzaRevocata(idPresenzaDst, aut, isSpuntista);
		this.eventPublisher.publishThrowOnFailure(revoca);
	    }
	    this.mercatipresenzeDService.update(presenza);
	    EventoPresenzaInserita evPresenza = new EventoPresenzaInserita(presenza);
	    this.eventPublisher.publishThrowOnFailure(evPresenza);
	} catch (EventAbortedException e) {
	    throw new MercatiAppException(e.getMessage());
	}
	return presenza;
    }

    @Override
    public MercatipresenzeD segnaPresenzaSpuntista(PresenzaDaRegistrareBean dati) throws MercatiAppException {

	//1. Verifica dei dati passati
	if (dati == null) {
	    throw new IllegalArgumentException("Impossibile assegnare la presenza senza passare nessun dato necessario");
	}
	if (dati.getCodiceMercato() == null) {
	    throw new IllegalArgumentException("Impossibile assegnare la presenza senza passare il mercato di riferimento");
	}
	if (dati.getCodiceUso() == null) {
	    throw new IllegalArgumentException("Impossibile assegnare la presenza senza passare l'uso di riferimento");
	}
	if (dati.getGiornata() == null) {
	    throw new IllegalArgumentException("Impossibile assegnare la presenza senza passare la giornata di riferimento");
	}
	if (dati.getCodicePosteggio() == null) {
	    throw new IllegalArgumentException("Impossibile assegnare la presenza senza passare il posteggio di riferimento");
	}
	if (dati.getCodiceAutorizzazione() == null) {
	    throw new IllegalArgumentException("Impossibile assegnare la presenza senza passare l'autorizzazione dello spuntista");
	}
	//2. Recupero il mercato
	Mercati mercato = this.mercatiService.findById(new PkId(dati.getCodiceMercato()));
	//3. recupero l'uso del mercato
	MercatiUso uso = this.mercatiUsoService.findById(new PkId(dati.getCodiceUso()));
	//4. recupero il giorno del mercato
	Calendar data = Calendar.getInstance();
	data.setTime(dati.getGiornata());
	MercatipresenzeT giorno = this.mercatipresenzeTService.findByDataregistrazioneAndMercatoAndMercatoUso(data, mercato, uso);
	// inserisco lo spuntista
	MercatipresenzeD presenza = this.segnaPresenzaSpuntista(giorno, dati.getCodicePosteggio(), dati.getCodiceAutorizzazione(),
		dati.getCategoriaMerceologica());
	Attivita catMerceologica = null;
	if (StringUtils.isNotBlank(dati.getCategoriaMerceologica())) {
	    AttivitaId id = new AttivitaId(dati.getCategoriaMerceologica());
	    catMerceologica = attivitaService.findById(id);
	    if (catMerceologica != null) {
		presenza.setAttivita(catMerceologica);
	    }
	}
	this.mercatipresenzeDService.update(presenza);
	EventoPresenzaInserita evPresenza = new EventoPresenzaInserita(presenza);
	try {
	    this.eventPublisher.publishThrowOnFailure(evPresenza);
	} catch (EventAbortedException e) {
	    throw new MercatiAppException(e);
	}
	return presenza;
    }

    /**
     * metodo per inserire la presenza dello spuntista per quel giorno di mercato su quel posteggio con una specifica
     * autorizzazione e categoria merceologica(opzionale)
     * 
     * @param giorno
     * @param idPosteggio
     * @param idAut
     *            pk autorizzazione
     * @param catMerc
     */
    private MercatipresenzeD segnaPresenzaSpuntista(MercatipresenzeT giorno, Integer idPosteggio, Integer idAut, String catMerc)
	    throws MercatiAppException {

	giorno = this.mercatipresenzeTService.findById(new PkId(giorno.getId().getCodice()));
	MercatipresenzeD vecchiaPresenza = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(giorno.getId().getCodice(), idPosteggio);
	//1. Esecuzione dei controlli sui battitori
	this.verificaBattitori(giorno, idPosteggio, idAut);
	//2. Verifica per cambio posteggio tra spuntisti
	if (vecchiaPresenza.isSpuntista()) {
	    /*
	    * serve per non perdere le informazioni quando il posteggio è occupato da uno spuntista e viene sostituito con un altro
	    * spuntista. Inserisco un nuoco record su mercatipresenze_d con le informazioni dello spuntista che era associato al posteggio
	    * senza riportare l'id del posteggio che verrà annullato
	    */
	    MercatipresenzeD vecchioSpuntista = this.mercatipresenzeTService.segnaPresenzaSpuntistaNoPosteggio(vecchiaPresenza.getMercatiPresenzeT(),
		    vecchiaPresenza.getAutorizzazioni().getId().getCodice(), null);
	    mercatipresenzeDService.copiaInformazioniMercatopresenzeDOrigine(vecchiaPresenza, vecchioSpuntista);
	    mercatipresenzeDService.update(vecchioSpuntista);
	}
	//3. Imposto la presenza, a meno che non sia già terminato l'appello
	int numeropresenze = 1;
	if (BooleanUtils.isTrue(giorno.getFlagChiusuraAppello())) {
	    numeropresenze = 0;
	}
	//4. Verifica della proprietà MercatiSpunte
	if (giorno.getMercatiSpunte() != null) {
	    vecchiaPresenza.setMercatiSpunte(giorno.getMercatiSpunte());
	}
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(idAut));
	//5. Assegno lo spuntista come occupante
	vecchiaPresenza.setOccupante(autorizzazioni.getOccupante());
	//6. Recupero e setto l'autorizzazione dello spuntista e la categoria merceologica	
	vecchiaPresenza.setAutorizzazioni(autorizzazioni);
	vecchiaPresenza.setSpuntista(Boolean.TRUE);
	vecchiaPresenza.setCatMerc(catMerc);
	//7. Verifica se devono essere conteggiate o meno le presenze/assenze maturate
	if (Boolean.FALSE.equals(vecchiaPresenza.getMercatiPresenzeT().getFlagConteggiaPresAss())) {
	    numeropresenze = 0; // NON VENGONO CONTEGGIATE LE PRESENZE SE PREFESTIVO O DOMENICA
	    if (vecchiaPresenza.getAutorizzazioneConcessionarioAssente() != null
		    && vecchiaPresenza.getAutorizzazioneConcessionarioAssente().getId() != null
		    && vecchiaPresenza.getAutorizzazioneConcessionarioAssente().getId().getCodice() != null) {
		vecchiaPresenza.setFlagAssenzaGiust(Boolean.TRUE);
		vecchiaPresenza.setMotivazione("Assenza festiva");
	    }
	}
	vecchiaPresenza.setNumeropresenze(numeropresenze);
	return vecchiaPresenza;
    }

    private void verificaBattitori(MercatipresenzeT giorno, Integer idPosteggio, Integer idAut) throws MercatiAppException {

	// Nel metodo che associa un posteggio (NON BATTITORE) ad una autorizzazione di tipo BATTITORE deve 
	// essere verificata la condizione: Errore "presente su mercato BATT. 1/2 ROTAZIONE " se, 
	// in quel momento, è Presente su mercati fittizi
	if (giorno.getFlagPopolaConcessionari().booleanValue()) {
	    // la verifica sui battitori la faccio solamente se il mercato.flag_popola_concessionari=1
	    String codiceIstatBattitori = this.comportamentiMercatiService.codiceIstatBattitori();
	    if (StringUtils.isNotBlank(codiceIstatBattitori)) {
		List<MercatiDattivitaistat> att = mercatiDattivitaistatService.findAttivitaPosteggio(idPosteggio);
		if (!att.isEmpty()) {
		    boolean trovato = verificaPosteggioBattitore(idPosteggio, codiceIstatBattitori);
		    if (!trovato && (giorno.getMercatoUso().getGiornisettimana() != null)) {
			List<BattitoriCsi> batts = battitoriCsiService.findByAutorizzazioniAndGiorno(idAut,
				giorno.getMercatoUso().getGiornisettimana().getId(), 0, 1);
			if (!batts.isEmpty()) {
			    BattitoriCsi battitoriCsi = batts.get(0);
			    throw new MercatiAppException("Presente su mercato " + battitoriCsi.getSiapDenominazione());
			}
		    }
		}
	    }
	}
    }

    private boolean verificaPosteggioBattitore(Integer idPosteggio, String codiceIstatBattitori) {

	if (StringUtils.isNotBlank(codiceIstatBattitori)) {
	    List<MercatiDattivitaistat> att = mercatiDattivitaistatService.findAttivitaPosteggio(idPosteggio);
	    if (!att.isEmpty()) {
		String[] nodis = codiceIstatBattitori.trim().split(",");
		for (MercatiDattivitaistat mda : att) {
		    for (String n : nodis) {
			if (StringUtils.defaultString(n).equals(mda.getId().getFkcodiceattivitaistat())) {
			    return true;
			}
		    }
		}
	    }
	}
	return false;
    }

    private boolean verificaBloccaAssegnazioneCreditoInsufficiente(Integer codiceMercato) {

	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	if (mercato == null || mercato.getComune() == null || StringUtils.isBlank(mercato.getComune().getCodicecomune())) {
	    throw new RuntimeException("Non è stato specificato correttamente il comune del mercato " + mercato);
	}
	VerticalizzazioneAbbonamentoPosteggiServiceImpl vert = new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService,
		this.contiService, this.mailTipoService, this.amministrazioniService, mercato.getComune().getCodicecomune());
	return vert.isBloccaAssegnazioni();
    }

    private void situazioneBorsellinoPosteggi(List<GiornataMercatoPosteggioRestBean> posteggi, String codiceComune, Set<Integer> auts) {

	Map<Integer, SituazioneBorsellinoPerSoglia> situazioneBorsellinoPerSoglia = abbonamentoService.situazioneBorsellinoPerSoglia(auts,
		codiceComune);
	for (GiornataMercatoPosteggioRestBean posteggio : posteggi) {
	    if (posteggio.getConcessionario() != null && posteggio.getConcessionario().getAutorizzazione() != null) {
		SituazioneBorsellinoPerSoglia sb = situazioneBorsellinoPerSoglia.get(posteggio.getConcessionario().getAutorizzazione().getId());
		if (sb != null && sb.isSottosoglia()) {
		    posteggio.getConcessionario().getAvvisi()
			    .add(new MessaggioBorsellinoSottoSoglia(posteggio.getConcessionario().getAutorizzazione(),
				    new VerticalizzazioneAbbonamentoPosteggiServiceImpl(verticalizzazioniService, this.contiService,
					    this.mailTipoService, this.amministrazioniService, codiceComune)).getTestoMessaggio());
		}
	    }
	}
    }

    /**
     * Il Metodo ritorna la lista degli identificativi autorizzazione dei concessionari dei posteggi.
     * 
     * @param idGiornataMercato
     * @return
     */
    private Set<Integer> findConcessioniDaEscludereNellaRicercaDellaGiornata(Integer idGiornataMercato) {

	MercatipresenzeT giornata = mercatipresenzeTService.findById(new PkId(idGiornataMercato));
	if (!BooleanUtils.toBoolean(giornata.getFlagPopolaConcessionari())) { // giornata di mercato dove anche i concessionari possono prendere la spunta
	    return new HashSet<Integer>(0);
	}
	Set<Integer> result = new HashSet<Integer>();
	List<MercatipresenzeDDTO> listaPosteggi = mercatipresenzeDService.findListaPosteggi(giornata);
	String codiceIstatBattitori = this.comportamentiMercatiService.codiceIstatBattitori();
	for (MercatipresenzeDDTO md : listaPosteggi) {
	    if (md.getPosteggio() != null && //
		    md.getPosteggio().getId() != null && //
		    md.getPosteggio().getId().getCodice() != null && // posteggio non nullo
		    md.getAutorizzazioneConcessionarioAssente() != null && //
		    md.getAutorizzazioneConcessionarioAssente().getId() != null && //
		    md.getAutorizzazioneConcessionarioAssente().getId().getCodice() != null && // posteggio con concessione
		    !this.verificaPosteggioBattitore(md.getPosteggio().getId().getCodice(), codiceIstatBattitori) /* NON è POSTEGGIO BATTITORE */) {
		// escludo le aut dei battitori
		result.add(md.getAutorizzazioneConcessionarioAssente().getId().getCodice()); // è una autorizzazione di un concessionario NON battitore
	    }
	}
	return result;
    }

    @Override
    public List<AutorizzazioniMercatoSrv> findAutorizzazioniMercatoSrvByRequest(MercatoSrvRequest req, boolean escludiAutorizzazioniDateInAffitto) {

	List<AutorizzazioniMercatoSrvBean> list = autorizzazioniService.findAutorizzazioniMercatoSrvBean(req);
	AutorizzazioniMercatiSrvHelper hlp = new AutorizzazioniMercatiSrvHelper(mercatipresenzeDService, layouttestiService, autorizzazioniCsiService,
		list, req.getListaCfUnivoci());
	return hlp.elaboraAutorizzazioni(escludiAutorizzazioniDateInAffitto);
    }

    @Override
    public AppAmbulantiAutorizzazioniResponse findAutorizzazioniAppAmbulantiByUtente(Anagrafe a) {

	AppAmbulantiAutorizzazioniResponse r = new AppAmbulantiAutorizzazioniResponse();
	Set<FiltroSoggetti> anagRoles = anagrafeService.findSoggettiPersoneCollegate(a.getId().getCodice(),
		RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI);
	List<String> recuperaCFDaAnagrafeRuoli = recuperaCFDaAnagrafeRuoli(anagRoles);
	if (recuperaCFDaAnagrafeRuoli.isEmpty()) {
	    recuperaCFDaAnagrafeRuoli.add(a.getCodicefiscale());
	    if (anagRoles.isEmpty()) {
		FiltroSoggetti f = new FiltroSoggetti();
		f.setCf(a.getCodicefiscale());
		f.setNominativo(a.getNominativo() + " " + a.getNome());
		f.setTitolarita(false);
		anagRoles.add(f);
	    }
	}
	r.setSoggetti(new ArrayList<FiltroSoggetti>(anagRoles));
	List<AutorizzazioniMercatoSrv> list = this
		.findAutorizzazioniMercatoSrvByRequest(new MercatoSrvRequest(recuperaCFDaAnagrafeRuoli, null, null, false), false);
	adattaAResponse(r, list);
	return r;
    }

    private void adattaAResponse(AppAmbulantiAutorizzazioniResponse r, List<AutorizzazioniMercatoSrv> list) {

	Map<String, List<AppAmbulantiAutorizzazione>> autPerCf = new HashMap<String, List<AppAmbulantiAutorizzazione>>();
	for (AutorizzazioniMercatoSrv aut : list) {
	    String codiceFiscale = aut.getCodiceFiscale();
	    List<AppAmbulantiAutorizzazione> listAuts = autPerCf.get(codiceFiscale);
	    if (null == listAuts) {
		listAuts = new ArrayList<AppAmbulantiAutorizzazione>();
	    }
	    listAuts.add(adattaAutorizzazione(aut));
	    autPerCf.put(codiceFiscale, listAuts);
	}
	// RIDURRE LE CONCESSIONI CON LO STESSO ID/TITOLARE/ AD UNA CON LA LISTA DEI POSTEGGI
	r.getAutorizzazioni().addAll(unisciConcessioni(autPerCf));
    }

    private List<AppAmbulantiAutorizzazione> unisciConcessioni(Map<String, List<AppAmbulantiAutorizzazione>> autPerCf) {

	List<AppAmbulantiAutorizzazione> ret = new ArrayList<AppAmbulantiAutorizzazione>();
	// le concessioni ovvero quelle con Area Pubblica hanno più record e devono essere riportate ad una con la lista dei posteggi
	// le autorizzazioni le lascio invariate 
	for (Entry<String, List<AppAmbulantiAutorizzazione>> e : autPerCf.entrySet()) {
	    String cfRiferimento = e.getKey();
	    log.debug("Processo le autorizzazioni concessioni di {}", cfRiferimento);
	    List<AppAmbulantiAutorizzazione> autorizzazioni = new ArrayList<AppAmbulantiAutorizzazione>();
	    Map<Integer, List<AppAmbulantiAutorizzazione>> concessioniMap = new HashMap<Integer, List<AppAmbulantiAutorizzazione>>();
	    for (AppAmbulantiAutorizzazione appAutorizzazione : e.getValue()) {
		if (appAutorizzazione.isAreaPubblica()) {
		    List<AppAmbulantiAutorizzazione> concessioni = concessioniMap.get(appAutorizzazione.getId());
		    if (null == concessioni) {
			concessioni = new ArrayList<AppAmbulantiAutorizzazione>();
		    }
		    concessioni.add(appAutorizzazione);
		    concessioniMap.put(appAutorizzazione.getId(), concessioni);
		} else {
		    autorizzazioni.add(appAutorizzazione);
		}
	    }
	    for (Entry<Integer, List<AppAmbulantiAutorizzazione>> conc : concessioniMap.entrySet()) {
		// ciclo le concessioni con stesso ID
		AppAmbulantiAutorizzazione uscita = null;
		String posteggio = "";
		String mercato = "";
		Set<String> giorni = new HashSet<String>();
		Set<String> posteggiPresenti = new HashSet<String>();
		for (AppAmbulantiAutorizzazione appAutorizzazione : conc.getValue()) {
		    uscita = appAutorizzazione;
		    mercato = uscita.getTransientAreaPubblica().getTipologia() + " " + uscita.getTransientAreaPubblica().getDenominazione();
		    giorni.add(uscita.getTransientAreaPubblica().getGiorno());
		    if (!posteggiPresenti.contains(uscita.getTransientAreaPubblica().getPosteggio())) {
			posteggio += uscita.getTransientAreaPubblica().getPosteggio() + ",";
		    }
		    posteggiPresenti.add(StringUtils.defaultString(uscita.getTransientAreaPubblica().getPosteggio()));
		}
		if (uscita != null) {
		    String posteggioPrefix = ", posteggio ";
		    if (StringUtils.countMatches(posteggio, ",") > 1) {
			posteggioPrefix = ", posteggi ";
		    }
		    if (posteggio.endsWith(",")) {
			posteggio = posteggioPrefix + posteggio.substring(0, posteggio.length() - 1);
		    }
		    uscita.setPosteggio(
			    StringUtils.capitalize(mercato + " " + MercatoGiorniSettimanaEnum.ordinaGiorniETornaStringa(giorni, ", ") + posteggio));
		    ret.add(uscita);
		}
	    }
	    ret.addAll(autorizzazioni);
	}
	return ret;
    }

    private AppAmbulantiAutorizzazione adattaAutorizzazione(AutorizzazioniMercatoSrv aut) {

	AppAmbulantiAutorizzazione ret = new AppAmbulantiAutorizzazione();
	AutorizzazioniRestHelper hlp = autorizzazioniService.findAutorizzazioneRestHelper(aut.getId());
	ret.setId(aut.getId());
	ret.setCfRiferimento(aut.getCodiceFiscale());
	ret.setComuneRilascio(aut.getComuneRilascio());
	ret.setDataCessazione(aut.getDataCessazione());
	ret.setDataRilascio(aut.getDataRilascio());
	ret.setNumero(aut.getNumero());
	String occupante = descrizioneFromAnagrafe(hlp.getNominativo(), hlp.getNome());
	if (hlp.getGerenteNominativo() != null) {
	    occupante = descrizioneFromAnagrafe(hlp.getGerenteNominativo(), hlp.getGerenteNome());
	}
	ret.setOccupante(occupante);
	ret.setTitolare(descrizioneFromAnagrafe(hlp.getTitNominativo(), hlp.getTitNome()));
	ret.setTransientAreaPubblica(aut.getAreaPubblica());
	ret.setRuolo(aut.getRuoloEnum());
	StatoEAvvisoDaRestHelper sav = statoEAvvisoForAutorizzazione(hlp);
	ret.setAvviso(sav.isAvviso());
	ret.setStatoAutorizzazione(sav.getStatoAutorizzazione());
	boolean attiva = BooleanUtils.isTrue(hlp.getFlagAttiva());
	String defaultStato = "Attiva";
	if (!attiva) {
	    defaultStato = "Non Attiva";
	}
	ret.setStatoAutorizzazione(defaultStato);
	return ret;
    }

    private StatoEAvvisoDaRestHelper statoEAvvisoForAutorizzazione(AutorizzazioniRestHelper hlp) {

	return StatoEAvvisoDaRestHelper.fromAutorizzazioniRestHelper(hlp);
    }

    private String descrizioneFromAnagrafe(String nominativo, String nome) {

	return StringUtils.trim(StringUtils.defaultString(nominativo) + " " + StringUtils.defaultString(nome));
    }

    private List<String> recuperaCFDaAnagrafeRuoli(Set<FiltroSoggetti> anagRoles) {

	Set<String> ret = new HashSet<String>();
	for (FiltroSoggetti filtroSoggetti : anagRoles) {
	    ret.add(filtroSoggetti.getCf().toUpperCase());
	}
	return new ArrayList<String>(ret);
    }

    @Override
    public AppAmbulantiStampaPDFResponse updateStampaAutorizzazioniAppAmbulantiByUtente(String cf, Integer codiceAnagrafe) {

	List<String> cfs = new ArrayList<String>();
	cfs.add(cf);
	AppAmbulantiStampaPDFResponse ret = new AppAmbulantiStampaPDFResponse();
	List<AutorizzazioniMercatoSrv> list = this.findAutorizzazioniMercatoSrvByRequest(new MercatoSrvRequest(cfs, null, null, false), true);
	String nominativo = cf;
	for (AutorizzazioniMercatoSrv a : list) {
	    if (a.getCodiceFiscale().equalsIgnoreCase(cf)) {
		nominativo = a.getNominativo();
		break;
	    }
	}
	Collections.sort(list, new AutConcPerAutMercatoGiornoComparator());
	// TO DO ORDINA PER AUTORIZZAZIONE / MANIFESTAZIONE  / USO
	AppAmbulantiAutorizzazioniStampa pdfresponse = new AppAmbulantiAutorizzazioniStampa();
	pdfresponse.setAutorizzazioni(list);
	StampaPDFRiferimentiDocumento rifDoc = new StampaPDFRiferimentiDocumento();
	rifDoc.setCodiceFiscale(cf);
	Date dataStampa = Calendar.getInstance().getTime();
	String uuid = UUID.randomUUID().toString();
	rifDoc.setNominativo(nominativo);
	rifDoc.setDataDocumento(Utilities.formatDate(dataStampa, false));
	rifDoc.setOraDocumento(Utilities.getOrario(dataStampa));
	rifDoc.setIdDocumento(uuid);
	pdfresponse.setRifDocumento(rifDoc);
	String file = Utilities.marshallObject(pdfresponse);
	log.debug("dati autorizzazioni {}", file);
	Oggetti xsl = comportamentiMercatiService.tipoDocStampaAutXsl();
	if (xsl == null) {
	    BaseEsitoOperazione esito = new BaseEsitoOperazione(ESITO.ERROR);
	    esito.getErrori().add("Non è stata configurata nessuna lettera XSL nella tabella TIPIDOCUMENTO");
	    ret.setEsito(esito);
	    return ret;
	}
	String nomeFile = "autorizzazioni_" + cf + ".pdf";
	try {
	    Oggetti filePDF = Utilities.convertXmlToPdf(file.getBytes(), xsl.getOggetto(), nomeFile);
	    ret.setContenuto(new ByteArrayInputStream(filePDF.getOggetto()));
	    anagrafedocumentiService.salvaDocumento(filePDF, codiceAnagrafe, dataStampa, uuid, comportamentiMercatiService.codTipodocStampa());
	} catch (Exception e) {
	    log.error("Errore durante la conversione del file", e);
	    BaseEsitoOperazione esito = new BaseEsitoOperazione(ESITO.ERROR);
	    esito.getErrori().add("Errore durante la conversione del file " + e.getMessage());
	    ret.setEsito(esito);
	    return ret;
	}
	ret.setNomeFile(nomeFile);
	return ret;
    }
}