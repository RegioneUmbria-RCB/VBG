package it.gruppoinit.pal.gp.core.features.manifestazioni.calendario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AutorizzazioneSpuntistaHelper;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiAnnoGiornoDTO;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniAttivita;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.BattitoriCsi;
import it.gruppoinit.pal.gp.core.domain.CalendariomercatoParametri;
import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Giorno;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Manifestazioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazione;
import it.gruppoinit.pal.gp.core.domain.MercatiConfigurazioneId;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Riepilogomercato;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.VwPosteggiconcessioni;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceNomeBean;
import it.gruppoinit.pal.gp.core.domain.helper.ComuneGraduatorieRestBean;
import it.gruppoinit.pal.gp.core.domain.helper.MercatipresenzeDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.TitolareAutorizzazioneGradRestBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.comunicazioni.massive.manifestazioni.IComunicazioniManifestazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoAssenzaGiustificata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoAssenzaGiustificataRevocata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoConcessionarioSegnatoPresente;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoGiornataChiusa;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoGiornataRiaperta;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaInserita;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.eventi.EventoPresenzaRevocata;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.movimenti.IBorsellinoMovimentiDAO;
import it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.eventi.EventoRchiestaAnnullamentoPagamento;
import it.gruppoinit.pal.gp.core.features.manifestazioni.rest.vigili.audting.GiornateNulleAuditLogger;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.IVerticalizzazioneComportamentiMercatiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniAttivitaService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.BattitoriCsiService;
import it.gruppoinit.pal.gp.core.service.ConcessioniusoService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.MercatiConfigurazioneService;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiSpunteService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTPrenotService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;
import it.gruppoinit.pal.gp.core.service.exception.MercatiAppException;
import it.gruppoinit.pal.gp.core.service.exception.VBGRuntimeException;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniGraduatoriaRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.GraduatorieMercatiBeanHelper;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.LoggerUpdaterecord;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class MercatipresenzeTServiceImpl extends BaseServiceImpl<MercatipresenzeT, PkId> implements MercatipresenzeTService {

    private static final Logger log = LoggerFactory.getLogger(MercatipresenzeTServiceImpl.class);
    @Autowired
    private AnagrafeService anagrafeService;
    @Autowired
    private AlberoprocService alberoprocService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatipresenzeTDAO mercatipresenzeTDAO;
    @Autowired
    private MercatipresenzeDService mercatipresenzeDService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    @Autowired
    private MercatiConfigurazioneService mercatiConfigurazioneService;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private ResponsabiliService responsabiliService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatipresenzeTPrenotService mercatipresenzeTPrenotService;
    @Autowired
    private MercatiSpunteService mercatiSpunteService;
    @Autowired
    private MercatiDattivitaistatService mercatiDattivitaistatService;
    @Autowired
    private BattitoriCsiService battitoriCsiService;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private ConcessioniusoService concessioniusoService;
    @Autowired
    private Istanzedyn2datiService istanzedyn2datiService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private IEventPublisher eventPublisher;
    @Autowired
    private AutorizzazioniAttivitaService autorizzazioniAttivitaService;
    @Autowired
    private AttivitaService attivitaService;
    private IComunicazioniManifestazioniService comunicazioniManifestazioniService;
    private IBorsellinoMovimentiDAO borsellinoMovimentiDAO;
    private IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService;

    @Autowired
    public void setComunicazioniManifestazioniService(IComunicazioniManifestazioniService comunicazioniManifestazioniService) {

	this.comunicazioniManifestazioniService = comunicazioniManifestazioniService;
    }

    @Autowired
    public void setBorsellinoMovimentiDAO(IBorsellinoMovimentiDAO borsellinoMovimentiDAO) {

	this.borsellinoMovimentiDAO = borsellinoMovimentiDAO;
    }

    @Autowired
    public void setComportamentiMercatiService(IVerticalizzazioneComportamentiMercatiService comportamentiMercatiService) {

	this.comportamentiMercatiService = comportamentiMercatiService;
    }

    @Override
    protected Class<MercatipresenzeT> getEntityClass() {

	return MercatipresenzeT.class;
    }

    @Override
    public void delete(MercatipresenzeT entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    entity = this.findById(new PkId(entity.getId().getCodice()));
	    mercatipresenzeTDAO.delete(entity);
	}
    }

    protected void childDelete(MercatipresenzeT entity) {

	//1. Cancellazione delle comunicazioni massive
	Responsabili operatore = (Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails();
	this.comunicazioniManifestazioniService.eliminaComunicazioniDellaGiornata(entity.getId().getCodice(), operatore);
	//2. Cancellazione del dettaglio ( MercatipresenzeD )
	List<MercatipresenzeDDTO> mds = this.mercatipresenzeDService.findByMercatipresenzaT(entity.getId().getCodice());
	for (MercatipresenzeDDTO mddto : mds) {
	    MercatipresenzeD md = this.mercatipresenzeDService.findById(new PkId(mddto.getId().getCodice()));
	    EventoRchiestaAnnullamentoPagamento annullamento = new EventoRchiestaAnnullamentoPagamento(md);
	    this.eventPublisher.publish(annullamento);
	    this.mercatipresenzeDService.delete(md);
	    this.mercatipresenzeTDAO.flush();
	    this.mercatipresenzeTDAO.commit();
	    this.mercatipresenzeTDAO.flush();
	    this.mercatipresenzeTDAO.clear();
	}
	//2. Cancellazione delle prenotazioni
	List<MercatipresenzeTPrenot> mpps = this.mercatipresenzeTPrenotService.findByMercatipresenzeT(entity.getId().getCodice());
	for (MercatipresenzeTPrenot mercatipresenzeTPrenot : mpps) {
	    this.mercatipresenzeTPrenotService.delete(mercatipresenzeTPrenot);
	}
    }

    /**
     * metodo per verificare se la cancellazione di un giorno di mercato è permessa.<br />
     * la cancellazione di un giorno è permessa solo se il giorno non è storicizzato e se il mercato non è storicizzato
     * 
     * @param entity
     * @return
     */
    protected boolean isDeleteAllowed(MercatipresenzeT entity) {

	boolean delete = true;
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	// una giornata di calendario è cancellabile solo se non ho gestito le presenze
	// FIXME modificare controllando solo che nono sia storicizzata o non abbia inserito le reg cont degli
	// spuntisti
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	String day = sdf.format(entity.getDataRegistrazione());
	if (entity.getFlagPresenze() || entity.getFlagPresenzeArchivio()) {
	    ivs.add(new InvalidValue("errors.mercatipresenzet.cancellazione.giorno", entity.getClass(), "", day, entity));
	    delete = false;
	}
	//2. Verifco la presenza di movimentazioni nel borsellino
	if (this.borsellinoMovimentiDAO.isPresenzaMovimentata(entity.getId().getCodice())) {
	    ivs.add(new InvalidValue("errors.mercatipresenzet.cancellazione.giorno.movimenti", entity.getClass(), "", day, entity));
	    delete = false;
	}
	if (!delete) {
	    throw new VBGRuntimeException(log, context, ivs);
	}
	return delete;
    }

    @Override
    public List<MercatipresenzeT> findAll(Integer firstResult, Integer maxResult) {

	return mercatipresenzeTDAO.findAll(null, null);
    }

    @Override
    public MercatipresenzeT findById(PkId id) {

	return mercatipresenzeTDAO.findById(id);
    }

    @Override
    public void insert(MercatipresenzeT entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatipresenzeTDAO.insert(entity);
	}
    }

    @Override
    public void update(MercatipresenzeT entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatipresenzeTDAO.update(entity);
	}
    }

    private void dataIntegration(MercatipresenzeT entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Documento passato è nullo");
	}
	if (entity.getFlagPresenze() == null) {
	    entity.setFlagPresenze(Boolean.FALSE);
	}
	if (entity.getFlagPresenzeArchivio() == null) {
	    entity.setFlagPresenzeArchivio(Boolean.FALSE);
	}
	if (entity.getFlagRegfatte() == null) {
	    entity.setFlagRegfatte(Boolean.FALSE);
	}
	if (entity.getMercato() != null && entity.getMercato().getId() != null && entity.getMercato().getId().getCodice() != null) {
	    if (entity.getMercatiSpunte() == null) {
		Integer codiceMercato = entity.getMercato().getId().getCodice();
		List<MercatiSpunte> mss = mercatiSpunteService.findByMercato(codiceMercato);
		if (mss.size() > 0) { // se gestisce le spunte mercato e non è stato settato nessun valore setto la prima in ordine crescente
		    entity.setMercatiSpunte(mss.get(0));
		}
	    }
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(MercatipresenzeT entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Responsabili responsabile = responsabiliService.bindDomainObject(entity.getResponsabile(), PkId.class, "id.codice");
	entity.setResponsabile(responsabile);
	Responsabili utenteSistema = responsabiliService.bindDomainObject(entity.getUtenteSistema(), PkId.class, "id.codice");
	entity.setUtenteSistema(utenteSistema);
	Mercati mercati = mercatiService.bindDomainObject(entity.getMercato(), PkId.class, "id.codice");
	entity.setMercato(mercati);
	MercatiUso mercatiUso = mercatiUsoService.bindDomainObject(entity.getMercatoUso(), PkId.class, "id.codice");
	entity.setMercatoUso(mercatiUso);
	MercatiSpunte ms = mercatiSpunteService.bindDomainObject(entity.getMercatiSpunte(), PkId.class, "id.codice");
	entity.setMercatiSpunte(ms);
    }

    @Override
    public List<MercatipresenzeT> findMercatipresenzeTByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	return mercatipresenzeTDAO.findMercatipresenzeTByMercatiAndMercatiUso(mercati, mercatiUso, anno);
    }

    public List<MercatipresenzeT> findMercatipresenzetFiereByMercatiAndMercatiUso(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	return mercatipresenzeTDAO.findMercatipresenzetFiereByMercatiAndMercatiUso(mercati, mercatiUso, anno);
    }

    @Override
    public boolean findDay(List<MercatipresenzeT> list, Calendar date) {

	return mercatipresenzeTDAO.findDay(list, date);
    }

    @Override
    public MercatipresenzeT findByDataregistrazioneAndMercatoAndMercatoUso(Calendar date, Mercati mercati, MercatiUso mercatiUso) {

	return mercatipresenzeTDAO.findByDataregistrazioneAndMercatoAndMercatoUso(date, mercati, mercatiUso);
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndGroupByAnnoAndMercatoUso(Mercati mercati) {

	return mercatipresenzeTDAO.findByMercatoAndGroupByAnnoAndMercatoUso(mercati);
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndAnnoGroupByMercatoUso(Mercati mercati, Integer anno) {

	return mercatipresenzeTDAO.findByMercatoAndAnnoGroupByMercatoUso(mercati, anno);
    }

    @Override
    public boolean segnaPresenzaConcessionario(MercatipresenzeT giorno, Integer idPosteggio, Autorizzazioni autorizzazioni, String catMerc,
	    PosteggiConcessioniHelper pch) {

	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	// recupero la presenza del giorno legata al posteggio 
	// se il posteggio è vuoto e c'è un concessionario
	// registrato allora lo segno presente
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	MercatipresenzeD presenza = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(giorno.getId().getCodice(),
		posteggio.getId().getCodice());
	return segnaPresenzaConcessionarioSuPresenza(presenza, autorizzazioni, catMerc, mercatiConfigurazione, pch);
    }

    @Override
    public boolean segnaPresenzaConcessionarioSuPresenza(MercatipresenzeD presenza, Autorizzazioni autorizzazioni, String catMerc,
	    MercatiConfigurazione mercatiConfigurazione, PosteggiConcessioniHelper pch) {

	if (presenza.getOccupante() != null || presenza.getConcessionario() == null) {
	    return true;
	}
	presenza = mercatipresenzeDService.findById(new PkId(presenza.getId().getCodice()));
	if (autorizzazioni == null) {
	    this.recuperaAutorizzazioneConcessionario(presenza.getMercatiPresenzeT(), presenza, mercatiConfigurazione,
		    presenza.getMercatiPresenzeT().getMercato().getManifestazione().getComportamentoPresenze(), pch);
	    if (presenza.getTransientAutDaSchedaDyn() != null && presenza.getTransientAutDaSchedaDyn().getId().getCodice() != null) {
		presenza.setAutorizzazioni(presenza.getTransientAutDaSchedaDyn());
	    } else {
		return false;
	    }
	} else {
	    presenza.setAutorizzazioni(autorizzazioni);
	    if (StringUtils.isNotBlank(catMerc)) {
		presenza.setCatMerc(catMerc);
	    } else {
		this.recuperaCategoriaMerceologicaConcessionario(presenza, mercatiConfigurazione, pch);
	    }
	}
	Attivita att = recuperaAttivitaIstatPresenzaConcessionario(presenza);
	presenza.setAttivita(att);
	presenza.setOccupante(presenza.getConcessionario());
	// Va ad eliminare, se ci sono, l'assenza giustificata e la motivazione
	presenza.setFlagAssenzaGiust(Boolean.FALSE);
	presenza.setMotivazione("");
	presenza.setMercatiSpunte(null);
	Integer numpresenze = 1;
	if (Boolean.FALSE.equals(presenza.getMercatiPresenzeT().getFlagConteggiaPresAss())) {
	    numpresenze = 0; // NON VENGONO CONTEGGIATE LE PRESENZE SE PREFESTIVO O DOMENICA
	}
	if (Boolean.FALSE.equals(presenza.getMercatiPresenzeT().getFlagPopolaConcessionari())) {
	    // Nel caso che si intende segnare presenti i concessionari ed il mercato è configurato come 
	    // MERCATIRPESENZE_T.FLAG_POPOLA_CONCEESSIONARI=0 allora devo considerare i concessionari come spuntisti
	    presenza.setSpuntista(Boolean.TRUE);
	}
	presenza.setNumeropresenze(numpresenze);
	mercatipresenzeDService.update(presenza);
	EventoConcessionarioSegnatoPresente evento = new EventoConcessionarioSegnatoPresente(presenza.getId().getCodice(),
		presenza.getMercatiPresenzeT().getDataRegistrazione());
	this.eventPublisher.publish(evento);
	return true;
    }

    private Attivita recuperaAttivitaIstatPresenzaConcessionario(MercatipresenzeD presenza) {

	log.debug("recuperaAttivitaIstatPresenzaConcessionario");
	if (presenza.getPosteggio().getId().getCodice() == null || presenza.getAutorizzazioni().getId().getCodice() == null) {
	    log.debug("recuperaAttivitaIstatPresenzaConcessionario presenza o autorizzazione nulli");
	    return null;
	}
	if (verificaSettorePosteggio(presenza)) {
	    return null;
	}
	// PRENDO LE CAT DEL POSTEGGIO
	List<MercatiDattivitaistat> attposteggio = mercatiDattivitaistatService.findAttivitaPosteggio(presenza.getPosteggio().getId().getCodice());
	Set<String> autP = new HashSet<String>();
	for (MercatiDattivitaistat mercatiDattivitaistat : attposteggio) {
	    autP.add(mercatiDattivitaistat.getId().getFkcodiceattivitaistat());
	}
	// PRENDO LE CAT DELLAUTORIZZAZIONE 
	List<AutorizzazioniAttivita> attauts = autorizzazioniAttivitaService.findByAutorizzazione(presenza.getAutorizzazioni().getId().getCodice(),
		null, null);
	Set<String> autS = new HashSet<String>();
	for (AutorizzazioniAttivita autorizzazioniAttivita : attauts) {
	    autS.add(autorizzazioniAttivita.getAttivita().getId().getCodiceistat());
	}
	// SE NE MATCHA UNA SOLA OK ALTRIMENTI ERRORE
	if (autP.isEmpty() && autS.isEmpty()) {
	    return null;
	}
	if (autP.isEmpty() && autS.size() == 1) {
	    return attivitaService.findById(new AttivitaId(autS.iterator().next()));
	}
	Set<String> trovata = new HashSet<String>();
	for (String a : autS) {
	    if (autP.contains(a)) {
		trovata.add(a);
	    }
	}
	if (trovata.size() == 1) {
	    return attivitaService.findById(new AttivitaId(trovata.iterator().next()));
	}
	if (autP.size() == 1) {
	    return attivitaService.findById(new AttivitaId(autP.iterator().next()));
	}
	String message = String.format(
		"Errore durante l'assegnazione della presenza per la concessione %s. Non è stato possibile risalire all'attività merceologica consentita sul posteggio %s. Verificare che il posteggio abbia una sola categoria merceologica o almeno una in comune con la concessiona attiva su quel posteggio",
		presenza.getAutorizzazioni().getTransientEstremiAut(), presenza.getPosteggio().getCodiceposteggio());
	log.error(message);
	throw new InvalidConfigurationException(message);
    }

    private boolean verificaSettorePosteggio(MercatipresenzeD presenza) {

	// recupera un parametro con la lista dei settori posteggio per i quali va esclusa il recupero della categoria merceologica
	// se trovato sul posteggio allora torno null
	if (presenza.getPosteggio().getPosteggiSettori() != null && presenza.getPosteggio().getPosteggiSettori().getId() != null
		&& presenza.getPosteggio().getPosteggiSettori().getId().getCodice() != null) {
	    String codiceSettoreDelPosteggio = presenza.getPosteggio().getPosteggiSettori().getCodicesettore();
	    log.debug(
		    "recuperaAttivitaIstatPresenzaConcessionario: il posteggio ha il settore {} configurato verifico se la verticalizzazione mi chiede di non salvare l'attivita istat ",
		    codiceSettoreDelPosteggio);
	    if (StringUtils.isNotBlank(codiceSettoreDelPosteggio)) {
		codiceSettoreDelPosteggio = StringUtils.remove(codiceSettoreDelPosteggio + ",", " ").toUpperCase(); // ha questo valore "PR" lo metto nella forma "PR,"
		String codiciSettori = this.comportamentiMercatiService.settoriNonSalvaMerceologie();
		String codiciSettoriVal = null;
		if (StringUtils.isNotBlank(codiciSettori)) {
		    codiciSettoriVal = StringUtils.remove(codiciSettori + ",", " ").toUpperCase(); // ha questo valore "PR" lo metto nella forma "PR,"
												   // ha questo valore "PR,FI,al" lo metto nella forma "PR,FI,AL," 
												   // PER CONFRONTARLO CON codicesettore del posteggio
		    log.debug("recuperaAttivitaIstatPresenzaConcessionario: la verticalizzazione è impostata ed ha il valore popoplato {} ",
			    codiciSettoriVal);
		    if (codiciSettoriVal.indexOf(codiceSettoreDelPosteggio) >= 0) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

    @Override
    public boolean segnaAssenzaGiustificataConcessionario(MercatipresenzeT giorno, Integer idPosteggio, Autorizzazioni autorizzazioni, String catMerc,
	    boolean flagAssenza, String motivazione, PosteggiConcessioniHelper p) {

	if (giorno == null || giorno.getId() == null || giorno.getId().getCodice() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la funzionalità segnaAssenzaGiustificataConcessionario senza passare il giorno di riferimento");
	}
	if (idPosteggio == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare la funzionalità segnaAssenzaGiustificataConcessionario senza passare il riferimento del posteggio");
	}
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	Mercati m = posteggio.getMercati();
	MercatipresenzeD presenza = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(giorno.getId().getCodice(),
		posteggio.getId().getCodice());
	/*
	* Potrebbe essere una assenza che va oltre le giornate già registrate per cui in quel caso devo prima inserire il dettaglio della giornata
	* e poi registrare l'assenza; in caso contrario, entrando nella giornata, vedrei solamente il record dell'assenza.
	* Questo caso si verifica quando si registra un assenza impostando "fino ad una certa data", che include altre giornate di calendario. 
	*/
	if (presenza == null) {
	    Anagrafe occupante = null;
	    if (p.isConcessionePresente()) {
		if (p.getCodiceoccupante() != null) {
		    occupante = anagrafeService.findById(new PkId(p.getCodiceoccupante()));
		}
		if (autorizzazioni == null) {
		    autorizzazioni = autorizzazioniService.findById(new PkId(p.getCodiceconcessione()));
		    if (occupante == null) {
			occupante = autorizzazioni.getAnagrafe();
		    }
		}
	    }
	    presenza = new MercatipresenzeD();
	    presenza.setConcessionario(occupante);
	    presenza.setAutorizzazioneConcessionarioAssente(autorizzazioni);
	    presenza.setPosteggio(posteggio);
	    presenza.setMercatiPresenzeT(giorno);
	    presenza.setSpuntista(Boolean.FALSE);
	    mercatipresenzeDService.insert(presenza);
	    mercatipresenzeTDAO.flush();
	    mercatipresenzeTDAO.clear();
	    presenza = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(giorno.getId().getCodice(), posteggio.getId().getCodice());
	}
	if (presenza.getConcessionario() == null) {
	    return true;
	}
	if (flagAssenza) {
	    if (autorizzazioni == null) {
		this.recuperaAutorizzazioneConcessionario(giorno, presenza, mercatiConfigurazione, m.getManifestazione().getComportamentoPresenze(),
			p);
		if (presenza.getTransientAutDaSchedaDyn() != null && presenza.getTransientAutDaSchedaDyn().getId().getCodice() != null) {
		    presenza.setAutorizzazioneConcessionarioAssente(presenza.getTransientAutDaSchedaDyn());
		} else {
		    return false;
		}
	    } else {
		presenza.setAutorizzazioneConcessionarioAssente(autorizzazioni);
		if (StringUtils.isNotBlank(catMerc)) {
		    presenza.setCatMerc(catMerc);
		} else {
		    recuperaCategoriaMerceologicaConcessionario(presenza, mercatiConfigurazione, p);
		}
	    }
	    presenza.setProprietario(Integer.valueOf(0));
	    presenza.setFlagAssenzaGiust(flagAssenza);
	    if (!presenza.isSpuntista()) {
		presenza.setOccupante(null);
		presenza.setAutorizzazioni(null);
	    }
	    presenza.setMotivazione(motivazione);
	    mercatipresenzeDService.update(presenza);
	    EventoAssenzaGiustificata assenza = new EventoAssenzaGiustificata(presenza.getId().getCodice());
	    this.eventPublisher.publish(assenza);
	} else {
	    presenza.setMotivazione(null);
	    presenza.setFlagAssenzaGiust(flagAssenza);
	    mercatipresenzeDService.update(presenza);
	    EventoAssenzaGiustificataRevocata revoca = new EventoAssenzaGiustificataRevocata(presenza.getId().getCodice());
	    this.eventPublisher.publish(revoca);
	}
	return true;
    }

    @Override
    public void segnaPresentiTuttiConcessionari(MercatipresenzeT giorno) {

	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	List<MercatipresenzeDDTO> listaPosteggi = mercatipresenzeDService.findListaPosteggi(giorno);
	Map<Integer, PosteggiConcessioniHelper> hlps = mercatiService.findPosteggiMercatoAllaData(giorno.getMercato().getId().getCodice(),
		giorno.getMercatoUso().getId().getCodice(), giorno.getDataRegistrazione());
	Mercati m = giorno.getMercato();
	MercatipresenzeD mercatipresenzeD = null;
	Autorizzazioni aut = null;
	for (MercatipresenzeDDTO posteggio : listaPosteggi) {
	    mercatipresenzeD = mercatipresenzeDService.findById(posteggio.getId());
	    if (mercatipresenzeD.getConcessionario() != null && mercatipresenzeD.getOccupante() == null) {
		// segno presente solo se sono riuscito ad inserire l'autorizzazione
		this.recuperaAutorizzazioneConcessionario(giorno, mercatipresenzeD, mercatiConfigurazione,
			m.getManifestazione().getComportamentoPresenze(), hlps.get(posteggio.getPosteggio().getId().getCodice()));
		if (mercatipresenzeD.getTransientAutDaSchedaDyn() != null && mercatipresenzeD.getTransientAutDaSchedaDyn().getId() != null
			&& mercatipresenzeD.getTransientAutDaSchedaDyn().getId().getCodice() != null) {
		    aut = autorizzazioniService.findById(mercatipresenzeD.getTransientAutDaSchedaDyn().getId());
		    mercatipresenzeD.setAutorizzazioni(aut);
		    mercatipresenzeD.setOccupante(mercatipresenzeD.getConcessionario());
		    // serve per annullare se un concessionario era stato gestito come assente giustificato
		    mercatipresenzeD.setFlagAssenzaGiust(Boolean.FALSE);
		    Integer numpresenze = 1;
		    if (Boolean.FALSE.equals(mercatipresenzeD.getMercatiPresenzeT().getFlagConteggiaPresAss())) {
			numpresenze = 0; // NON VENGONO CONTEGGIATE LE PRESENZE
		    }
		    mercatipresenzeD.setNumeropresenze(numpresenze);
		    mercatipresenzeD.setMotivazione("");
		    mercatipresenzeDService.update(mercatipresenzeD);
		    EventoPresenzaInserita presenza = new EventoPresenzaInserita(mercatipresenzeD);
		    this.eventPublisher.publish(presenza);
		}
	    }
	}
    }

    @Override
    public synchronized void inserisciTuttiConcessionari(MercatipresenzeT giorno) {

	if (this.isGiornataMercatoChiusa(giorno.getId().getCodice())) {
	    return;
	}
	log.debug("Il giorno gestisce i concessionari? {}", giorno.getFlagPopolaConcessionari());
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	boolean insConcessionari = this.comportamentiMercatiService.insConcessionariPresenti();
	boolean inserisciPresenti = false;
	if (Boolean.TRUE.equals(insConcessionari)) {
	    inserisciPresenti = giorno.getFlagPopolaConcessionari();
	}
	Map<Integer, PosteggiConcessioniHelper> posteggiMercatoAllaData = mercatiService.findPosteggiMercatoAllaData(
		giorno.getMercato().getId().getCodice(), giorno.getMercatoUso().getId().getCodice(), giorno.getDataRegistrazione());
	for (Entry<Integer, PosteggiConcessioniHelper> pch : posteggiMercatoAllaData.entrySet()) {
	    Integer idPosteggio = pch.getKey();
	    MercatipresenzeD presPerMercato = mercatipresenzeDService.findByMercatiPresenzeTAndPosteggio(giorno.getId().getCodice(), idPosteggio);
	    if (presPerMercato != null) {
		// presenza già scritta non la sovrascrivo
		continue;
	    }
	    MercatiD posteggio = mercatiDService.findById(new PkId(idPosteggio));
	    PosteggiConcessioniHelper vwPosteggiconcessioni = pch.getValue();
	    if (vwPosteggiconcessioni == null) {
		return;
	    }
	    MercatipresenzeD mercatipresenzeD = new MercatipresenzeD();
	    // l'occupante rappresenta il concessionario del posteggio.
	    // lo recupero dal campo codiceoccupante dell'autorizzazione
	    if (vwPosteggiconcessioni.getCodiceoccupante() != null) {
		Anagrafe a = anagrafeService.findById(new PkId(vwPosteggiconcessioni.getCodiceoccupante()));
		mercatipresenzeD.setConcessionario(a);
		// modifiche Task 8865: verifica giornata di mercato
		if (vwPosteggiconcessioni.isFlagcausaliaffitto() && vwPosteggiconcessioni.getDatafineaffitto() != null
			&& giorno.getDataRegistrazione().compareTo(vwPosteggiconcessioni.getDatafineaffitto()) > 0) {
		    Anagrafe anagrafe = anagrafeService.findById(new PkId(vwPosteggiconcessioni.getCodicetitolare()));
		    mercatipresenzeD.setConcessionario(anagrafe);
		}
	    }
	    // 20190311 BOCCI-TODINI METTIAMO SEMPRE IL CAMPO DELLA CONCESSIONE ATTIVA SUL POSTEGGIO IN QUANTO
	    // HO BISOGNO SUBITO DELLE INFORMAZIONI SULLA CONCESSIONE 
	    Autorizzazioni aut = null;
	    if (vwPosteggiconcessioni.getCodiceconcessione() != null) {
		aut = autorizzazioniService.findById(new PkId(vwPosteggiconcessioni.getCodiceconcessione()));
	    }
	    mercatipresenzeD.setAutorizzazioneConcessionarioAssente(aut);
	    mercatipresenzeD.setPosteggio(posteggio);
	    mercatipresenzeD.setMercatiPresenzeT(giorno);
	    mercatipresenzeD.setSpuntista(Boolean.FALSE);
	    mercatipresenzeDService.insert(mercatipresenzeD);
	    if (inserisciPresenti) {
		try {
		    this.segnaPresenzaConcessionarioSuPresenza(mercatipresenzeD, aut, "", mercatiConfigurazione, vwPosteggiconcessioni);
		} catch (Exception e) {
		    log.error("Errore in segnapresenza concessionario ", e);
		}
	    }
	    mercatipresenzeTDAO.commit();
	    mercatipresenzeTDAO.flush();
	    mercatipresenzeTDAO.clear();
	}
    }

    @Override
    public void recuperaCategoriaMerceologicaConcessionario(MercatipresenzeD posteggio, MercatiConfigurazione mercatiConfigurazione,
	    PosteggiConcessioniHelper vwPosteggiconcessioni) {

	Manifestazioni tipoManifestazione = posteggio.getPosteggio().getMercati().getManifestazione();
	boolean findCatMerc = checkSeUsareCatMerc(tipoManifestazione);
	if (!findCatMerc) {
	    return;
	}
	Dyn2Campi dynCatMerc = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCm();
	if (dynCatMerc == null) {
	    return;
	}
	if (vwPosteggiconcessioni.isConcessionePresente()) {
	    List<Istanzedyn2dati> istanzedyn2datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(vwPosteggiconcessioni.getCodiceistanza(),
		    dynCatMerc.getId().getCodice());
	    for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
		if (istanzedyn2dati.getDyn2Campi().getId().getCodice().equals(dynCatMerc.getId().getCodice())) {
		    posteggio.setCatMerc(istanzedyn2dati.getValore());
		    return;
		}
	    }
	}
    }

    @Override
    public String recuperaCategoriaMerceologicaDaIstanza(Istanze istanza, MercatiConfigurazione mercatiConfigurazione) {

	// §§§BEGIN§§§
	String catMerc = "";
	Mercati mercato = istanza.getAlberoproc().getMercato();
	if (EntityUtils.getNestedProperty(mercato, "id.codice") == null) {
	    List<Integer> s = alberoprocService.findGerarchiaNodiPadreInversa(istanza.getAlberoproc().getId().getCodice(), true);
	    if (s != null) {
		for (Integer acc : s) {
		    if (acc != null) {
			Alberoproc ap = alberoprocService.findById(new PkId(acc));
			if (ap.getMercato() != null) {
			    mercato = ap.getMercato();
			    break;
			}
		    }
		}
	    }
	}
	if (EntityUtils.getNestedProperty(mercato, "id.codice") == null) {
	    log.error("l'intervento dell'istanza:{} non è configurato per le manifestazioni", istanza.getNumeroistanza());
	    throw new RuntimeException("l'intervento dell'istanza " + istanza.getNumeroistanza() + " non è configurato per le manifestazioni.");
	}
	Manifestazioni tipoManifestazione = mercato.getManifestazione();
	boolean findCatMerc = checkSeUsareCatMerc(tipoManifestazione);
	if (findCatMerc) {
	    Dyn2Campi dynCatMerc = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCm();
	    Set<Istanzedyn2dati> istanzedyn2datis = istanza.getIstanzedyn2datis();
	    for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
		if (istanzedyn2dati.getDyn2Campi().getId().equals(dynCatMerc.getId())) {
		    catMerc = istanzedyn2dati.getValore();
		    break;
		}
	    }
	    if (StringUtils.isBlank(catMerc)) {
		log.error("La categoria merceologica dell'istanza:{} è vuota", istanza.getNumeroistanza());
		throw new RuntimeException("Il campo \"" + dynCatMerc.getEtichetta() + "\" dell'istanza " + istanza.getNumeroistanza() +
					   " è obbligatorio per il recupero delle presenze.");
	    }
	}
	return catMerc;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public boolean checkSeUsareCatMerc(Manifestazioni tipoManifestazione) {

	log.debug("checkSeUsareCatMerc: cerco la configurazione dei mercati per il software {}", ORMHelper.getSoftware());
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	log.debug("checkSeUsareCatMerc: mercatiConfigurazione={}", mercatiConfigurazione);
	String catMercUso = "";
	if (StringUtils.isNotBlank(mercatiConfigurazione.getCatMercUso())) {
	    catMercUso = mercatiConfigurazione.getCatMercUso();
	} else {
	    log.debug("checkSeUsareCatMerc:Il mercato non ha configurata una categoria merceologica ");
	    return false;
	}
	Integer tipoMan = tipoManifestazione.getComportamentoPresenze();
	log.debug("checkSeUsareCatMerc: tipoMan={}", tipoMan);
	Integer tipoManRef = -1;
	Dyn2Campi dynCatMerc = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCm();
	if (dynCatMerc != null && StringUtils.isNotBlank(catMercUso)) {
	    log.debug("checkSeUsareCatMerc: categoria Merceologica Uso={}", catMercUso);
	    if (catMercUso.equals(WebConstants.MERCATI_CAT_MERC_USO_MERCATI)) {
		tipoManRef = WebConstants.MANIFESTAZIONE_COMPORTAMENTO_MERCATO;
	    }
	    if (catMercUso.equals(WebConstants.MERCATI_CAT_MERC_USO_FIERE)) {
		tipoManRef = WebConstants.MANIFESTAZIONE_COMPORTAMENTO_FIERA;
	    }
	    if (catMercUso.equals(WebConstants.MERCATI_CAT_MERC_USO_ENTRAMBI)) {
		tipoManRef = tipoMan;
	    }
	}
	return tipoMan.intValue() == tipoManRef.intValue();
    }

    @Override
    public void eliminaPresenzaOccupante(Integer idPresenza) throws MercatiAppException {

	MercatipresenzeD mercatipresenzeD = mercatipresenzeDService.findById(new PkId(idPresenza));
	Autorizzazioni aut = mercatipresenzeD.getAutorizzazioni();
	boolean presenteSpuntista = mercatipresenzeD.isSpuntista();
	if (mercatipresenzeD.getPosteggio() == null) {
	    mercatipresenzeDService.delete(mercatipresenzeD);
	    return;
	}
	DettPosizioneDebitoria dett = mercatipresenzeD.getDettPosizioneDebitoria();
	if (presenteSpuntista) {
	    MercatipresenzeD mSPuntista = this.segnaPresenzaSpuntistaNoPosteggio(mercatipresenzeD.getMercatiPresenzeT(),
		    mercatipresenzeD.getAutorizzazioni().getId().getCodice(), null);
	    mercatipresenzeDService.copiaInformazioniMercatopresenzeDOrigine(mercatipresenzeD, mSPuntista);
	    if (mSPuntista.getDettPosizioneDebitoria() != null) {
		dett = mSPuntista.getDettPosizioneDebitoria();
		mSPuntista.setDettPosizioneDebitoria(null);
	    }
	    mercatipresenzeDService.update(mSPuntista);
	}
	mercatipresenzeD.setDettPosizioneDebitoria(dett);
	mercatipresenzeD.setOccupante(null);
	mercatipresenzeD.setSpuntista(Boolean.FALSE);
	mercatipresenzeD.setProprietario(Integer.valueOf(0));
	mercatipresenzeD.setNumeropresenze(Integer.valueOf(0));
	mercatipresenzeD.setAutorizzazioni(null);
	mercatipresenzeD.setCatMerc(null);
	mercatipresenzeD.setAttivita(null);
	mercatipresenzeD.setImporto(null);
	mercatipresenzeD.setTipimodalitapagamento(null);
	mercatipresenzeD.setRiferimentiPagamento(null);
	mercatipresenzeDService.update(mercatipresenzeD);
	EventoPresenzaRevocata revoca = new EventoPresenzaRevocata(mercatipresenzeD.getId().getCodice(), aut, presenteSpuntista);
	this.eventPublisher.publish(revoca);
    }

    @Override
    public MercatipresenzeD segnaPresenzaSpuntistaNoPosteggio(MercatipresenzeT giorno, Integer idAut, String catMerc) throws MercatiAppException {

	//1. Prendo la presenza dello spuntista NON registrata su uno specifico posteggio; dall'app è sempre presente, inizialmente, uno spuntista senza posteggio
	MercatipresenzeD giornata = mercatipresenzeDService.findSpuntistaNoPosteggio(giorno, idAut);
	int numeropresenze = 1;
	//2. Verifico se è stato chiuso l'appello
	giorno = this.findById(new PkId(giorno.getId().getCodice()));
	boolean appelloTerminato = false;
	if (giorno.getFlagChiusuraAppello() != null && giorno.getFlagChiusuraAppello().booleanValue()) {
	    numeropresenze = 0;
	    appelloTerminato = true;
	}
	//3. Verifico, se l'appello non è terminato, 
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(idAut));
	boolean flagPopolaConcessionari = giorno.getFlagPopolaConcessionari().booleanValue();
	log.debug("Devo fare i controlli del mercato normale? (flagPopolaConcessionari )={}", flagPopolaConcessionari);
	if (!appelloTerminato && flagPopolaConcessionari && giorno.getMercatoUso().getGiornisettimana() != null) {
	    List<BattitoriCsi> batts = battitoriCsiService.findByAutorizzazioniAndGiorno(idAut, giorno.getMercatoUso().getGiornisettimana().getId(),
		    0, 1);
	    if (!batts.isEmpty()) {
		throw new MercatiAppException("Presente su mercato " + batts.get(0).getSiapDenominazione());
	    }
	}
	if (giornata != null) {
	    return giornata;
	}
	MercatipresenzeD mercatipresenzeD = new MercatipresenzeD();
	if (giorno.getMercatiSpunte() != null) {
	    mercatipresenzeD.setMercatiSpunte(giorno.getMercatiSpunte());
	}
	mercatipresenzeD.setMercatiPresenzeT(giorno);
	mercatipresenzeD.setOccupante(autorizzazioni.getOccupante());
	mercatipresenzeD.setSpuntista(true);
	mercatipresenzeD.setCatMerc(catMerc);
	// In fase di inserimento verrà messo a defaulto come pagato
	mercatipresenzeD.setFlagPagato(true);
	mercatipresenzeD.setAutorizzazioni(autorizzazioni);
	// Controllo se nella configuazione delle manifestazioni per il software è configurata la proprietà
	// che permette di segnare la presenza anche agli spuntisti non assegnatari di posteggio
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findConfigurazione();
	if (mercatiConfigurazione != null && BooleanUtils.toBoolean(mercatiConfigurazione.getSegnaPresenzaSpuntistaNoPost())) {
	    log.debug("segnaPresenzaSpuntistaNoPosteggio# Allo spuntista {} non assegnatario di posteggio viene assegnata la presenta",
		    autorizzazioni.getOccupante().getDescrizioneRichiedente());
	    numeropresenze = 1;
	}
	if (Boolean.FALSE.equals(giorno.getFlagConteggiaPresAss())) {
	    numeropresenze = 0; // NON VENGONO CONTEGGIATE LE PRESENZE SE PREFESTIVO O DOMENICA
	}
	mercatipresenzeD.setNumeropresenze(numeropresenze);
	mercatipresenzeDService.insert(mercatipresenzeD);
	return mercatipresenzeD;
    }

    @Override
    public void inserisciCalendario(CalendariomercatoParametri calendariomercatoParametri, Mercati mercato, Responsabili utenteLoggato,
	    boolean bloccaPrimoGennaio) {

	// §§§BEGIN§§§
	if (log.isDebugEnabled()) {
	    log.debug("Inserimento calendario...");
	}
	Giorno data = null;
	MercatipresenzeT giornoMercato = null;
	List<Giorno> calendarioMercato = calendariomercatoParametri.getGiorniMercato();
	for (Iterator<Giorno> iterator = calendarioMercato.iterator(); iterator.hasNext();) {
	    data = iterator.next();
	    if (verificaInserisciGiorno(data, bloccaPrimoGennaio)) {
		String descrizione = calendariomercatoParametri.getAnno().toString() + " " +
				     getDescrizioneMercato(mercato.getDescrizione(), calendariomercatoParametri.getMercatiUso().getDescrizione());
		giornoMercato = new MercatipresenzeT(calendariomercatoParametri.getAnno(), data.getData().getTime(),
			calendariomercatoParametri.getMercatiUso(), calendariomercatoParametri.getMercatiUso().getConcessioniuso(), descrizione,
			mercato.getSoftware(), utenteLoggato);
		this.insert(giornoMercato);
	    }
	}
	mercatipresenzeTDAO.flush();
	mercatipresenzeTDAO.clear();
	if (log.isDebugEnabled()) {
	    log.debug("Inserimento calendario...done!");
	}
	// §§§END§§§
    }

    public boolean verificaInserisciGiorno(Giorno data, boolean bloccaPrimoGennaio) {

	log.debug("verificaInserisciGiorno...data: {}, bloccaPrimoGennaio:{}", data, bloccaPrimoGennaio);
	return !(data.getData().get(Calendar.MONTH) == Calendar.JANUARY && data.getData().get(Calendar.DATE) == 1 && bloccaPrimoGennaio);
    }

    @Override
    public void deleteCalendario(Mercati mercato, MercatiUso uso, Integer anno) {

	// §§§BEGIN§§§
	// recupero i giorni del calendario mercato
	List<MercatipresenzeT> calendarioMercato = this.findMercatipresenzeTByMercatiAndMercatiUso(mercato, uso, anno);
	// elimino i giorni del calendario mercato
	for (MercatipresenzeT giornoMercato : calendarioMercato) {
	    this.delete(giornoMercato);
	}
	// §§§END§§§
    }

    @Override
    public boolean verificaGestionePresenze(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	return mercatipresenzeTDAO.verificaGestionePresenze(mercati, mercatiUso, anno);
    }

    @Override
    public boolean verificaMercatoStoricizzato(Mercati mercati, MercatiUso mercatiUso, Integer anno) {

	// §§§BEGIN§§§
	return mercatipresenzeTDAO.verificaMercatoStoricizzato(mercati, mercatiUso, anno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return false;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void closeMarketDay(MercatipresenzeT mercatipresenzeT) {

	if (!mercatipresenzeDService.isCloseMarketDayAllowed(mercatipresenzeT)) {
	    String messageError = getMessageFromBundle("label.storicizzazionegiorno.noneseguita", null);
	    throw new RuntimeException(messageError);
	}
	if (isGiornateAperteNellAnno(mercatipresenzeT)) {
	    String messageError = getMessageFromBundle("label.storicizzazionegiorno.noneseguita_gg_precedenti_aperte", null);
	    throw new RuntimeException(messageError);
	}
	mercatipresenzeT.setFlagPresenze(true);
	mercatipresenzeTDAO.update(mercatipresenzeT);
	this.eventPublisher.publish(new EventoGiornataChiusa(mercatipresenzeT.getId().getCodice()));
    }

    private boolean isGiornateAperteNellAnno(MercatipresenzeT mercatipresenzeT) {

	if (comportamentiMercatiService.verificaChiusuraGiornatePrecedenti()) {
	    Integer codcieMercato = mercatipresenzeT.getMercato().getId().getCodice();
	    Integer codiceGiornata = mercatipresenzeT.getMercatoUso().getId().getCodice();
	    Integer annoMercato = mercatipresenzeT.getAnno();
	    Date dataReg = mercatipresenzeT.getDataRegistrazione();
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("id.codice", codcieMercato, "mercato", Integer.class));
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceGiornata, "mercatoUso", Integer.class));
	    fr.addFilterField(FilterUtils.equals("anno", annoMercato, Integer.class));
	    fr.addFilterField(FilterUtils.equals("flagPresenze", false, Integer.class));
	    fr.addFilterField(FilterUtils.smaller("dataRegistrazione", dataReg, Date.class));
	    ft.addRestriction(fr);
	    return mercatipresenzeTDAO.existsRecords(ft);
	}
	return false;
    }

    @Override
    public List<Riepilogomercato> findByFilterMercatoOrAnnoGroupByMercatoUso(Mercati mercati, Integer anno) {

	// §§§BEGIN§§§
	return mercatipresenzeTDAO.findByMercatoOrAnnoGroupByMercatoUso(mercati, anno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeT> findAnniMercatiPresenti() {

	// §§§BEGIN§§§
	return mercatipresenzeTDAO.findAnniMercatiPresenti();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    public boolean validateConcessionari(List<VwPosteggiconcessioni> listPosteggi) {

	boolean success = false;
	// §§§BEGIN§§§
	for (VwPosteggiconcessioni vwPosteggiconcessioni : listPosteggi) {
	    if (vwPosteggiconcessioni.getOccupante() != null && vwPosteggiconcessioni.getOccupante().getId() != null
		    && vwPosteggiconcessioni.getOccupante().getId().getCodice() != null) {
		success = true;
		return success;
	    }
	}
	// §§§END§§§
	return success;
    }

    @Override
    public void apriGiornoMercato(MercatipresenzeT giorno) {

	giorno.setFlagPresenze(false);
	this.mercatipresenzeTDAO.update(giorno);
	EventoGiornataRiaperta evento = new EventoGiornataRiaperta(giorno.getId().getCodice());
	this.eventPublisher.publish(evento);
    }

    @Override
    public MercatiPresenzeDTO findSommaDellePresenzeDaiCalendari(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno, MercatipresenzeT giorno) {

	// §§§BEGIN§§§
	MercatiPresenzeDTO presenzeDaiCalendari = mercatipresenzeDService.findSommaDellePresenzeDaiCalendari(autorizzazione, mercato, uso, posteggio,
		catMerc, anno, giorno, false);
	MercatiPresenzeDTO presenzeDaiCalendariAssenzeGiustificate = mercatipresenzeDService.findSommaDellePresenzeDaiCalendari(autorizzazione,
		mercato, uso, posteggio, catMerc, anno, giorno, true);
	presenzeDaiCalendari.addPresenze(presenzeDaiCalendariAssenzeGiustificate.getPresenze());
	presenzeDaiCalendari.addPresenzeComeProprietario(presenzeDaiCalendariAssenzeGiustificate.getPresenzeComeProprietario());
	return presenzeDaiCalendari;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatiAnnoGiornoDTO> findUltimoGiornoFieraPerAnno(Mercati mercato, MercatiUso uso, Integer anno, boolean groupByDataRegistrazione) {

	// §§§BEGIN§§§
	return mercatipresenzeTDAO.findUltimoGiornoFieraPerAnno(mercato, uso, anno, groupByDataRegistrazione);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public String[] findComboCategorieMerceologiche() {

	// §§§BEGIN§§§
	String[] catMercList = null;
	MercatiConfigurazioneId mercatiConfigurazioneId = new MercatiConfigurazioneId(softwareService.findById(ORMHelper.getSoftware()).getCodice());
	MercatiConfigurazione mercatiConfigurazione = mercatiConfigurazioneService.findById(mercatiConfigurazioneId);
	if (mercatiConfigurazione != null) {
	    Dyn2Campi dyn2CatMerc = mercatiConfigurazione.getDyn2CampiByFkMerconfDyn2campiCm();
	    if (dyn2CatMerc != null) {
		String tipoDato = dyn2CatMerc.getTipodato();
		Set<Dyn2Campiproprieta> dyn2CatMercProps = dyn2CatMerc.getDyn2Campiproprietas();
		String catMercElementiLista = "";
		if (tipoDato.equals("Lista")) {
		    for (Dyn2Campiproprieta dyn2Campiproprieta : dyn2CatMercProps) {
			String p = dyn2Campiproprieta.getId().getProprieta();
			if (p.equals("ElementiLista")) {
			    catMercElementiLista = dyn2Campiproprieta.getValore();
			}
		    }
		}
		catMercList = catMercElementiLista.split(";");
	    }
	}
	return catMercList;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void chiudiAnnoMercato(Mercati mercato, MercatiUso uso, Integer anno) {

	// §§§BEGIN§§§
	if (this.verificaGestionePresenze(mercato, uso, anno)) {
	    if (!this.verificaMercatoStoricizzato(mercato, uso, anno)) {
		// itero su tutte le giornate di mercato dell'anno e aggiorno il campo FLAG_PRESENZE_ARCHIVIO
		List<MercatipresenzeT> giorni = this.findMercatipresenzeTByMercatiAndMercatiUso(mercato, uso, anno);
		for (MercatipresenzeT giorno : giorni) {
		    giorno.setFlagPresenzeArchivio(true);
		    this.update(giorno);
		}
	    } else {
		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
		InvalidValue iv = new InvalidValue(getMessageFromBundle("service_error.mercato_chiuso", null), MercatipresenzeT.class, "", null,
			null);
		_ivs.add(iv);
		this.throwValidationMessages(_ivs);
	    }
	} else {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    InvalidValue iv = new InvalidValue(getMessageFromBundle("service_error.giorni_mercato_non_chiusi", null), MercatipresenzeT.class, "",
		    null, null);
	    _ivs.add(iv);
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
    }

    @Override
    public void recuperaAutorizzazioneConcessionario(MercatipresenzeT giorno, MercatipresenzeD mercatipresenzeD,
	    MercatiConfigurazione mercatiConfigurazione, Integer tipoManifestazione, PosteggiConcessioniHelper pch) {

	log.debug("recuperaAutorizzazioneConcessionario(). manifestazione:{}", giorno.getDescrizione());
	// itero la lista dei posteggi e concessioni	
	if (!pch.isConcessionePresente()) {
	    return;
	}
	if (pch.getCodiceoccupante() != null) {
	    Istanze istanzaIniziale = istanzeService.findById(new PkId(pch.getCodiceistanza()));
	    if (istanzaIniziale == null) {
		return;
	    }
	    if (tipoManifestazione.intValue() == WebConstants.MANIFESTAZIONE_COMPORTAMENTO_MERCATO) {
		// caso di Mercati
		log.debug("codice concessione:{}", pch.getCodiceconcessione());
		log.debug("numero posteggio:{}", mercatipresenzeD.getPosteggio().getCodiceposteggio());
		log.debug("numero istanza:{}", pch.getCodiceistanza());
		// trovato posteggio concessione corretto. recupero l'istanza iniziale
		istanzaIniziale = autorizzazioniService.findIstanzaInizialePerPresenzeManifestazione(pch.getCodiceconcessione(),
			pch.getCodiceistanza());
		log.debug("numero istanza iniziale:{}", istanzaIniziale.getNumeroistanza());
		Autorizzazioni aut = autorizzazioniService.findById(new PkId(pch.getCodiceconcessione()));
		mercatipresenzeD.setTransientAutDaSchedaDyn(aut);
		if (mercatipresenzeD.getTransientAutDaSchedaDyn() != null
			&& mercatipresenzeD.getTransientAutDaSchedaDyn().getId().getCodice() != null) {
		    log.debug("autorizzazione:{}", mercatipresenzeD.getTransientAutDaSchedaDyn().getTransientEstremiAut());
		    String catMerc = this.recuperaCategoriaMerceologicaDaIstanza(istanzaIniziale, mercatiConfigurazione);
		    mercatipresenzeD.setCatMerc(catMerc);
		    log.debug("cat. merceologica:{}", catMerc);
		}
	    } else {
		// in caso di Fiere
		//		Va seguita, nell'ordine, questa logica:
		//		    Si prende l'istanza legata alla concessione
		//	    Si cerca se nei dati dinamici dell'istanza sono presenti gli estremi dell'autorizzazione con la quale l'operatore ha chiesto di partecipare alla fiera		
		log.debug("recuperaAutorizzazioneConcessionario# Cerco l'autorizzazione sui dati dinamici dell'istanza = {}",
			istanzaIniziale.getNumeroistanza());
		Autorizzazioni aut = autorizzazioniService.populateEstremiByIstanzaDyn2Dati(istanzaIniziale, mercatiConfigurazione);
		if (aut != null) {
		    recuperaAutorizzazioneConcessionario(aut, istanzaIniziale, mercatiConfigurazione, mercatipresenzeD);
		} else {
		    //		    Se non sono presenti gli estremi, verifico se la concessione ha subito dei subentri ( unico caso conosciuto sono le fiere quadriennali )
		    List<AutorizzazioniSubentri> autSub = autorizzazioniSubentriService.findSubentriByConcessione(pch.getCodiceconcessione(), null,
			    null, OrderTypeEnum.DESC);
		    // recuperare il subentro precedente
		    if (autSub.isEmpty()) {
			//	        Se non ci sono subentri si passa al concessionario successivo
			return;
		    } else {
			//	        Se ci sono subentri prendere il subentro immediatamente prima e ripetere questa procedura partendo dal punto 2
			for (AutorizzazioniSubentri autorizzazioniSubentri : autSub) {
			    istanzaIniziale = autorizzazioniSubentri.getIstanze();
			    log.debug("recuperaAutorizzazioneConcessionario# Cerco l'autorizzazione sui dati dinamici dell'istanza di subentro = {}",
				    istanzaIniziale.getNumeroistanza());
			    aut = autorizzazioniService.populateEstremiByIstanzaDyn2Dati(istanzaIniziale, mercatiConfigurazione);
			    boolean trovata = false;
			    if (aut != null) {
				trovata = recuperaAutorizzazioneConcessionario(aut, istanzaIniziale, mercatiConfigurazione, mercatipresenzeD);
			    } else {
				log.debug("recuperaAutorizzazioneConcessionario# Autorizzazione non trovata per l'istana di subentro = {}",
					istanzaIniziale.getNumeroistanza());
			    }
			    if (trovata) {
				return;
			    }
			}
			log.debug("recuperaAutorizzazioneConcessionario# Fine ricerca ");
		    }
		}
	    }
	}
    }

    private boolean recuperaAutorizzazioneConcessionario(Autorizzazioni aut, Istanze istanzaIniziale, MercatiConfigurazione mercatiConfigurazione,
	    MercatipresenzeD mercatipresenzeD) {

	//		    Se presenti gli estremi, si cerca l'autorizzazione con quegli estremi e con titolare il concessionario
	aut = autorizzazioniService.findAutOConcAttivaByEstremi(aut.getAutoriznumero(), aut.getAutorizdata(),
		aut.getAutorizcomune().getCodicecomune(), aut.getTipologiaregistro().getId().getCodice());
	//		        Se l'autorizzazione esiste si prende quella per legarla alla presenza
	if (aut != null) {
	    mercatipresenzeD.setTransientAutDaSchedaDyn(aut);
	    if (mercatipresenzeD.getTransientAutDaSchedaDyn() != null && mercatipresenzeD.getTransientAutDaSchedaDyn().getId().getCodice() != null) {
		log.debug("autorizzazione:{}", mercatipresenzeD.getTransientAutDaSchedaDyn().getTransientEstremiAut());
		String catMerc = this.recuperaCategoriaMerceologicaDaIstanza(istanzaIniziale, mercatiConfigurazione);
		mercatipresenzeD.setCatMerc(catMerc);
		log.debug("cat. merceologica:{}", catMerc);
	    }
	    return true;
	} else {
	    //		        Se l'autorizzazione non esiste si passa al concessionario successivo
	    return false;
	}
    }

    @Override
    public List<Integer> findAnniDaConsolidare(Integer codiceMercato) {

	return mercatipresenzeTDAO.findAnniDaConsolidare(codiceMercato);
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndMercatoUsoAndDateInterval(Integer codiceMercato, Integer codiceUso, Calendar date,
	    Calendar dataFine) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatoUsoId", codiceUso, Integer.class));
	fr.addFilterField(FilterUtils.greater("dataRegistrazione", date.getTime(), Date.class));
	fr.addFilterField(FilterUtils.smallerEqual("dataRegistrazione", dataFine.getTime(), Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRegistrazione"));
	return mercatipresenzeTDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndMercatoUsoAndDate(Integer codiceMercato, Integer codiceUso, Date date) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatoUsoId", codiceUso, Integer.class));
	fr.addFilterField(FilterUtils.equals("dataRegistrazione", date, Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRegistrazione"));
	return mercatipresenzeTDAO.findByFilterTable(ft);
    }

    @Override
    public List<AutorizzazioneSpuntistaHelper> graduatoriaSpuntistiManifestazione(Integer codiceMercato, Integer codiceUso,
	    Integer idGiornataRiferimento) {

	return autorizzazioniService.findAutorizzazioniSpuntisti(codiceMercato, codiceUso, idGiornataRiferimento);
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndAnnoAndResponsabileGroupByMercatoUso(Mercati mercati, Integer anno, Integer codiceResponsabile) {

	return mercatipresenzeTDAO.findByMercatoAndAnnoAndResponsabileGroupByMercatoUso(mercati, anno, codiceResponsabile);
    }

    @Override
    public String getDescrizioneMercato(Integer codiceMercato, Integer codiceUso) {

	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	return this.getDescrizioneMercato(mercato, uso);
    }

    @Override
    public String getDescrizioneMercato(Mercati mercato, MercatiUso mercatiUso) {

	return this.getDescrizioneMercato(mercato.getDescrizione(), mercatiUso.getDescrizione());
    }

    @Override
    public String getDescrizioneMercato(String descrizioneMercato, String descrizioneUso) {

	String result = StringUtils.defaultString(descrizioneMercato);
	if (StringUtils.isNotBlank(descrizioneUso)) {
	    if (!result.endsWith(StringUtils.defaultString(descrizioneUso))) {
		result = result.concat(" ").concat(descrizioneUso);
	    }
	}
	return result;
    }

    @Override
    public List<MercatipresenzeT> findByMercatoAndMercatoUsoAndDateInterval(Set<Integer> codiceMercato, Set<Integer> codiceUso, Date date,
	    Date dataFine) {

	Integer[] mercati = codiceMercato.toArray(new Integer[codiceMercato.size()]);
	Integer[] giorni = codiceUso.toArray(new Integer[codiceUso.size()]);
	if (mercati.length == 0 || giorni.length == 0) {
	    return new ArrayList<MercatipresenzeT>();
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.in("mercatoId", mercati, Integer.class));
	fr.addFilterField(FilterUtils.in("mercatoUsoId", giorni, Integer.class));
	fr.addFilterField(FilterUtils.greaterEqual("dataRegistrazione", date, Date.class));
	fr.addFilterField(FilterUtils.smallerEqual("dataRegistrazione", dataFine, Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataRegistrazione"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercato"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercatoUso"));
	return mercatipresenzeTDAO.findByFilterTable(ft);
    }

    @Override
    public boolean isGiornataMercatoChiusa(Integer idGiornataMercato) {

	return isGiornataMercatoChiusa(this.findById(new PkId(idGiornataMercato)));
    }

    @Override
    public boolean isGiornataMercatoChiusa(MercatipresenzeT giornataMercato) {

	if (giornataMercato != null) {
	    return (giornataMercato.getFlagPresenze()) == null ? false : giornataMercato.getFlagPresenze().booleanValue();
	}
	throw new RuntimeException("Giornata nulla");
    }

    @Override
    public GraduatorieMercatiBeanHelper graduatoriaMercati(String mercato, String giorno) throws MercatiAppException {

	List<Mercati> mercs = mercatiService.findByDescrizione(mercato, MercatiEnum.ACTIVE);
	GraduatorieMercatiBeanHelper h = new GraduatorieMercatiBeanHelper();
	if (mercs.size() != 1) {
	    throw new MercatiAppException("GM-001", "Nessun mercato individuato con la descrizione " + mercato);
	}
	Mercati m = mercs.get(0);
	Integer codicemercato = m.getId().getCodice();
	List<MercatiUso> gs = mercatiUsoService.findDescrizioneAndMercato(giorno, m);
	if (gs.size() != 1) {
	    throw new MercatiAppException("GM-002", "Nessun giorno individuato con la descrizione " + giorno + " per il mercato " + mercato);
	}
	MercatiUso mercatiUso = gs.get(0);
	h.setId(codicemercato.intValue() + "-" + mercatiUso.getId().getCodice() + "-" + ORMHelper.getIdcomuneAlias() + "-" + ORMHelper.getSoftware() +
		"-" + System.currentTimeMillis());
	h.setCsv_stampabile(true);
	h.setPdf_stampabile(true);
	MercatipresenzeT mpt = this.findUltimagiornataChiusaMercatoUso(codicemercato, mercatiUso.getId().getCodice());
	Integer codiceMpt = null;
	Date datariferimento = Calendar.getInstance().getTime();
	if (mpt != null) {
	    codiceMpt = mpt.getId().getCodice();
	    datariferimento = mpt.getDataRegistrazione();
	}
	h.setData_riferimento_graduatoria(Utilities.formatDate(datariferimento, false));
	List<AutorizzazioneSpuntistaHelper> gsms = this.graduatoriaSpuntistiManifestazione(codicemercato, mercatiUso.getId().getCodice(), codiceMpt);
	CodiceNomeBean mercatoBean = new CodiceNomeBean();
	mercatoBean.setCodice(String.valueOf(codicemercato));
	mercatoBean.setNome(mercato);
	CodiceNomeBean giornoBean = new CodiceNomeBean();
	giornoBean.setCodice(String.valueOf(mercatiUso.getId().getCodice()));
	giornoBean.setNome(giorno);
	for (AutorizzazioneSpuntistaHelper ash : gsms) {
	    AutorizzazioniGraduatoriaRestHelper ag = new AutorizzazioniGraduatoriaRestHelper();
	    ag.setId(ash.getIdautorizzazione());
	    ag.setMercato(mercatoBean);
	    ag.setGiorno(giornoBean);
	    ag.setAnno(getAnnoDaData(ash.getAutorizdata()));
	    ag.setNumero(ash.getAutoriznumero());
	    ag.setProtocollo(ash.getProtocolloaut());
	    if (ash.getProtocolloaut() != null) {
		ag.setData_protocollo(Utilities.formatDate(ash.getDataprotocolloaut(), false));
	    }
	    if (StringUtils.isNotBlank(ash.getAutorizcomune())) {
		ComuneGraduatorieRestBean c = new ComuneGraduatorieRestBean();
		c.setNome(ash.getAutorizcomune());
		c.setNome_provincia(ash.getAutorizcomprov());
		c.setSigla_provincia(ash.getAutorizcomsiglaprov());
		ag.setComune_rilascio(c);
	    }
	    ag.setNumero_presenze(ash.getNumpresenze());
	    ag.setOriginaria(ash.getAutoriginnumero());
	    TitolareAutorizzazioneGradRestBean t = new TitolareAutorizzazioneGradRestBean();
	    if (ash.getCodiceGerente() != null) {
		t.setDenominazione(StringUtils.trim(ash.getNominativogerente() + " " + StringUtils.defaultString(ash.getNomegerente())));
		t.setCodice_fiscale(ash.getCodicefiscalegerente());
		t.setPartita_iva(ash.getPartitaivagerente());
	    } else {
		t.setDenominazione(StringUtils.trim(ash.getNominativo() + " " + StringUtils.defaultString(ash.getNome())));
		t.setCodice_fiscale(ash.getCodicefiscale());
		t.setPartita_iva(ash.getPartitaiva());
	    }
	    ag.setTitolare(t);
	    h.getAutorizzazioni().add(ag);
	}
	return h;
    }

    private Integer getAnnoDaData(Date autorizdata) {

	if (autorizdata == null) {
	    return null;
	}
	Calendar c = new GregorianCalendar();
	c.setTime(autorizdata);
	return c.get(Calendar.YEAR);
    }

    @Override
    public MercatipresenzeT findUltimagiornataChiusaMercatoUso(Integer codiceMercato, Integer codiceMercatoUso) {

	Date oggi = Calendar.getInstance().getTime();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatoUsoId", codiceMercatoUso, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagPresenze", Boolean.TRUE, Boolean.class));
	fr.addFilterField(FilterUtils.equals("flagConteggiaPresAss", Boolean.TRUE, Boolean.class));
	fr.addFilterField(FilterUtils.smallerEqual("dataRegistrazione", oggi, Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataRegistrazione"));
	List<MercatipresenzeT> ms = mercatipresenzeTDAO.findByFilterTable(ft, 0, 1);
	if (ms.isEmpty()) {
	    return null;
	}
	return ms.get(0);
    }

    @Override
    public List<MercatipresenzeT> findByData(Date d) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.smallerEqual("dataRegistrazione", d, Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataRegistrazione"));
	List<MercatipresenzeT> ms = mercatipresenzeTDAO.findByFilterTable(ft);
	return ms;
    }

    @Override
    public List<MercatipresenzeT> findGiornateOdierne() {

	return findMercatiPresenzeTDellaData(Calendar.getInstance().getTime());
    }

    private List<MercatipresenzeT> findMercatiPresenzeTDellaData(Date d) {

	Calendar cDa = GregorianCalendar.getInstance();
	cDa.setTime(d);
	//Calendar.HOUR non va bene perchè gestisce le 12h giornaliere e se si è dopo le 12 passa alla data successiva (??????)
	cDa.set(Calendar.HOUR_OF_DAY, 0);
	cDa.set(Calendar.SECOND, 0);
	cDa.set(Calendar.MINUTE, 0);
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	Date dalla = cDa.getTime();
	fr.addFilterField(FilterUtils.equals("dataRegistrazione", dalla, Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataRegistrazione"));
	List<MercatipresenzeT> ms = mercatipresenzeTDAO.findByFilterTable(ft);
	return ms;
    }

    @Override
    @Transactional(noRollbackFor = RuntimeException.class, propagation = Propagation.REQUIRED)
    public synchronized void inizializzaGiornateMercatoOdierne(String idcomunealias, String software) {

	List<MercatipresenzeT> findGiornateOdierne = this.findMercatiPresenzeTDellaData(Calendar.getInstance().getTime());
	for (MercatipresenzeT mpt : findGiornateOdierne) {
	    log.info("inizializzaGiornateMercatoOdierne# Processo la giornata di mercato {}, {}", mpt.getDescrizione(), mpt.getDataRegistrazione());
	    try {
		this.inserisciTuttiConcessionari(mpt);
		mercatipresenzeTDAO.flush();
		mercatipresenzeTDAO.commit();
		mercatipresenzeTDAO.flush();
		mercatipresenzeTDAO.clear();
	    } catch (Exception e) {
		log.error("inizializzaGiornateMercatoOdierne# il processamento della giornata di mercato {}, {} ha rilanciato errore {}",
			new Object[] { mpt.getDescrizione(), mpt.getDataRegistrazione(), e });
	    }
	}
    }

    @Override
    public void updateNoteGiornata(Integer codice, String note) {

	if (StringUtils.defaultString(note).length() > 4000) {
	    throw new BusinessValidationException("Il campo note può contenere al massimo 1000 caratteri");
	}
	MercatipresenzeT giorno = this.findById(new PkId(codice));
	if (giorno != null) {
	    String oldNote = StringUtils.defaultString(giorno.getNote());
	    giorno.setNote(note);
	    this.update(giorno);
	    Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    String responsabile = "";
	    if (r != null) {
		responsabile = r.toString();
	    }
	    LoggerUpdaterecord.log("L'operatore " + responsabile + " ha modificato le note della giornata di mercato [" + giorno.getId() + "] da [" +
				   oldNote + "] a [" + note + "]",
		    r);
	}
    }

    @Override
    public int countByMercatoUsoAnno(Integer codiceMercato, Integer codiceUso, Integer anno) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatoUsoId", codiceUso, Integer.class));
	fr.addFilterField(FilterUtils.equals("anno", anno, Integer.class));
	ft.addRestriction(fr);
	return mercatipresenzeTDAO.countRecord(ft);
    }

    @Override
    public void aggiornaFlagConteggiaPresAss(Integer idGiornata, boolean valoreSelezionato) {

	MercatipresenzeT gm = this.findById(new PkId(idGiornata));
	if (this.isGiornataMercatoChiusa(gm)) {
	    throw new SecurityException("Non è possibile modificare lo stato di una giornata chiusa");
	}
	gm.setFlagConteggiaPresAss(valoreSelezionato);
	this.update(gm);
	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	LoggerUpdaterecord.log("L'operatore " + r + " ha modificato lo stato della giornata di mercato " + gm.getId() +
			       " con valore FlagConteggiaPresAss " + gm.getFlagConteggiaPresAss(),
		r);
    }

    @Override
    public void aggiornaFlagPopolaconcessionari(Integer idGiornata, boolean valoreSelezionato) {

	MercatipresenzeT gm = this.findById(new PkId(idGiornata));
	if (this.isGiornataMercatoChiusa(gm)) {
	    throw new SecurityException("Non è possibile modificare lo stato di una giornata chiusa");
	}
	gm.setFlagPopolaConcessionari(valoreSelezionato);
	this.update(gm);
	Responsabili r = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	LoggerUpdaterecord.log("L'operatore " + r + " ha modificato lo stato della giornata di mercato " + gm.getId() +
			       " con valore FlagPopolaconcessionari " + gm.getFlagPopolaConcessionari(),
		r);
    }

    @Override
    public void aggiornaGiornataMercato(Integer idGiornata, Integer concUsoId, Boolean flagConteggiaPresAss, Boolean flagPopolaConcessionari) {

	MercatipresenzeT entity = this.findById(new PkId(idGiornata));
	if (this.isGiornataMercatoChiusa(entity)) {
	    throw new SecurityException("Non è possibile modificare lo stato di una giornata chiusa");
	}
	entity.setFlagConteggiaPresAss(flagConteggiaPresAss);
	entity.setFlagPopolaConcessionari(flagPopolaConcessionari);
	if (concUsoId != null) {
	    entity.setConcessioniuso(concessioniusoService.findById(new PkId(concUsoId)));
	}
	this.update(entity);
    }

    @Override
    public void inserisceNuovaGiornataMercato(Integer codicemercato, Integer codiceuso, Integer anno, Integer mese, Integer giorno, Integer concUsoId,
	    Boolean flagConteggiaPresAss, Boolean flagPopolaConcessionari, Responsabili currentlyAuthenticatedUserDetails) {

	Mercati mercati = mercatiService.findById(new PkId(codicemercato));
	Calendar c = Calendar.getInstance();
	c.set(anno, mese, giorno, 0, 0);
	MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(codiceuso));
	Concessioniuso conc = null;
	if (concUsoId != null) {
	    conc = concessioniusoService.findById(new PkId(concUsoId));
	}
	String descrizione = String.valueOf(anno) + " " + mercati.getDescrizione() + " " + mercatiUso.getDescrizione();
	MercatipresenzeT mercatipresenzeT = new MercatipresenzeT(anno, c.getTime(), mercatiUso, conc, descrizione, mercati.getSoftware(),
		currentlyAuthenticatedUserDetails, flagPopolaConcessionari, flagConteggiaPresAss);
	this.insert(mercatipresenzeT);
    }

    @Override
    public List<IdentificativoDescrizioneBean> checkPosizioniDebitorieCreatePerConcessionari(Date giornataDaControllare) {

	return mercatipresenzeTDAO.checkPosizioniDebitorieCreatePerConcessionari(giornataDaControllare);
    }

    @Override
    public void inizializzaGiornateMercati(List<Integer> codiciMercato, Date dallaData, Date allaData) {

	List<MercatipresenzeT> findGiornateOdierne = this.findMercatiPresenzeTByMercatiAndPeriodo(codiciMercato, dallaData, allaData);
	for (MercatipresenzeT mpt : findGiornateOdierne) {
	    log.info("inizializzaGiornateMercatoOdierne# Processo la giornata di mercato {}, {}", mpt.getDescrizione(), mpt.getDataRegistrazione());
	    try {
		this.inserisciTuttiConcessionari(mpt);
		mercatipresenzeTDAO.flush();
		mercatipresenzeTDAO.commit();
		mercatipresenzeTDAO.flush();
		mercatipresenzeTDAO.clear();
	    } catch (Exception e) {
		log.error("inizializzaGiornateMercatoOdierne# il processamento della giornata di mercato {}, {} ha rilanciato errore {}",
			new Object[] { mpt.getDescrizione(), mpt.getDataRegistrazione(), e });
	    }
	}
    }

    private List<MercatipresenzeT> findMercatiPresenzeTByMercatiAndPeriodo(List<Integer> codiciMercato, Date dallaData, Date allaData) {

	Calendar cDa = GregorianCalendar.getInstance();
	cDa.setTime(dallaData);
	cDa.set(Calendar.HOUR_OF_DAY, 0);
	cDa.set(Calendar.SECOND, 0);
	cDa.set(Calendar.MINUTE, 0);
	Date dalla = cDa.getTime();
	Calendar cA = GregorianCalendar.getInstance();
	cA.setTime(allaData);
	cA.set(Calendar.HOUR_OF_DAY, 23);
	cA.set(Calendar.SECOND, 59);
	cA.set(Calendar.MINUTE, 59);
	Date alla = cA.getTime();
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.greaterEqual("dataRegistrazione", dalla, Date.class));
	fr.addFilterField(FilterUtils.smallerEqual("dataRegistrazione", alla, Date.class));
	fr.addFilterField(FilterUtils.in("mercatoId", codiciMercato.toArray(new Integer[codiciMercato.size()]), Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataRegistrazione"));
	List<MercatipresenzeT> ms = mercatipresenzeTDAO.findByFilterTable(ft);
	return ms;
    }

    @Override
    public IdentificativoDescrizioneBean checkInserimentoConcessionariPerGiornata(MercatipresenzeT giornata) {

	if (Boolean.FALSE.equals(giornata.getFlagConteggiaPresAss()) && Boolean.FALSE.equals(giornata.getFlagPopolaConcessionari())) {
	    // con questa configurazione i concessionari DEVONO ESSERE AGGIUNTI COME SPUNTISTI
	    String messageError = getMessageFromBundle("label.mercati.errore.assegnazione.concessionari.giornate.festive",
		    new Object[] { giornata.getDescrizione() });
	    return new IdentificativoDescrizioneBean(500, messageError);
	}
	return new IdentificativoDescrizioneBean(200, null);
    }

    @Override
    public boolean segnaGiornataNulla(Integer idGiornata, String note) {

	MercatipresenzeT mercatiPresenzaT = this.findById(new PkId(idGiornata));
	mercatiPresenzaT.setAnnotazioniGiornataNulla(note);
	mercatiPresenzaT.setFlagGiornataNulla(1);
	boolean segnaGiornataNulla = mercatipresenzeTDAO.segnaGiornataNulla(mercatiPresenzaT);
	GiornateNulleAuditLogger giornateNulleAuditLogger = new GiornateNulleAuditLogger();
	if (segnaGiornataNulla) {
	    giornateNulleAuditLogger.messaggioSegnaGiornataNulla(idGiornata, note,
		    userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	}
	return segnaGiornataNulla;
    }

    @Override
    public boolean segnaGiornataNonNulla(Integer idGiornata) {

	MercatipresenzeT mercatiPresenzaT = this.findById(new PkId(idGiornata));
	mercatiPresenzaT.setAnnotazioniGiornataNulla(null);
	mercatiPresenzaT.setFlagGiornataNulla(0);
	boolean segnaGiornataNonNulla = mercatipresenzeTDAO.segnaGiornataNonNulla(mercatiPresenzaT);
	GiornateNulleAuditLogger giornateNulleNonAuditLogger = new GiornateNulleAuditLogger();
	if (!segnaGiornataNonNulla) {
	    giornateNulleNonAuditLogger.messaggioSegnaGiornataNonNulla(idGiornata,
		    userSecurityService.getCurrentlyAuthenticatedUserDetails().toString());
	}
	return segnaGiornataNonNulla;
    }
}
