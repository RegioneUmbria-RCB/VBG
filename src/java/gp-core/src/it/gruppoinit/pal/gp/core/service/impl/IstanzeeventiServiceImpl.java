package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.IstanzeeventiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CategorieEventiMail;
import it.gruppoinit.pal.gp.core.domain.Categorieeventibase;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.Istanzeoneri;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilicomuni;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeeventiListHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.IstanzeeventiFilter;
import it.gruppoinit.pal.gp.core.exception.FunzioneBusinessRemotaException;
import it.gruppoinit.pal.gp.core.features.infrastructure.auditing.messages.MessaggioDiSistema;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.messaggi.CausaleImportoOnerePerMessaggio;
import it.gruppoinit.pal.gp.core.features.istanze.eventi.messaggi.MessaggioEventoOneriCopiatiDaPratica;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.movimenti.messaggi.MessaggioCancellazioneMovimentoDaIstanzaCollegata;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.messaggi.MessaggioErroreAnnullamentoPosizioneDebitoria;
import it.gruppoinit.pal.gp.core.features.oneri.IstanzeoneriService;
import it.gruppoinit.pal.gp.core.features.scadenzario.BatchScadenzarioFilter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.CategorieEventiMailService;
import it.gruppoinit.pal.gp.core.service.CategorieeventibaseService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.helper.BatchScadenzarioFilterHelper;
import it.gruppoinit.pal.gp.core.service.helper.BatchScadenzarioFilterHelper.QUERY_PER;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;
import it.gruppoinit.pal.gp.core.service.helper.TipoAccessoEnum;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

/**
 * 
 * @author fabrizioc
 */
@Service
public class IstanzeeventiServiceImpl extends BaseServiceImpl<Istanzeeventi, PkId> implements IstanzeeventiService {

    private IstanzeeventiDAO istanzeeventiDAO;
    private CategorieeventibaseService categorieeventibaseService;
    private ComuniassociatiService comuniassociatiService;
    private IstanzeService istanzeService;
    private MovimentiService movimentiService;
    private SoftwareService softwareService;
    private ResponsabiliService responsabiliService;
    private AlberoprocService alberoprocService;
    private StatiistanzaService statiistanzaService;
    private CategorieEventiMailService categorieEventiMailService;
    private MailtipoService mailtipoService;
    private MailServiceWSClient mailServiceWSClient;
    private IstanzeoneriService istanzeoneriService;
    private static final Logger log = LoggerFactory.getLogger(IstanzeeventiServiceImpl.class);

    @Autowired
    public void setIstanzeoneriService(IstanzeoneriService istanzeoneriService) {

	this.istanzeoneriService = istanzeoneriService;
    }

    @Autowired
    public void setMailServiceWSClient(MailServiceWSClient mailServiceWSClient) {

	this.mailServiceWSClient = mailServiceWSClient;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setCategorieEventiMailService(CategorieEventiMailService categorieEventiMailService) {

	this.categorieEventiMailService = categorieEventiMailService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setIstanzeeventiDAO(IstanzeeventiDAO istanzeeventiDAO) {

	this.istanzeeventiDAO = istanzeeventiDAO;
    }

    @Autowired
    public void setCategorieeventibaseService(CategorieeventibaseService categorieeventibaseService) {

	this.categorieeventibaseService = categorieeventibaseService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Override
    protected Class<Istanzeeventi> getEntityClass() {

	return Istanzeeventi.class;
    }

    @Override
    public List<Istanzeeventi> findAll(Integer firstResult, Integer maxResult) {

	return istanzeeventiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public List<Istanzeeventi> findAllByResponsabile(Responsabili responsabile, Integer firstResult, Integer maxResult) {

	IstanzeeventiFilter filter = new IstanzeeventiFilter();
	filter.setFlagLetto(Boolean.FALSE);
	// Gestione del filtro software
	if (ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    // se la chiamata arriva da TT allora recupero gli eventi per tutti i software attivi
	    List<Software> softwareAttiviList = new ArrayList<Software>();
	    if (EntityUtils.getNestedProperty(responsabile, "id.codice") != null) {
		softwareAttiviList = softwareService.findSoftwareAbilitati(responsabile);
	    } else {
		softwareAttiviList = softwareService.findSoftwareAttivi(false);
	    }
	    filter.setSoftwares(softwareAttiviList);
	}
	List<Istanzeeventi> eventi = this.findByFilter(filter, firstResult, maxResult);
	if (EntityUtils.getNestedProperty(responsabile, "id.codice") == null) {
	    return eventi;
	}
	List<Istanzeeventi> eventiVisualizzabili = new ArrayList<Istanzeeventi>();
	if (eventi != null) {
	    for (Istanzeeventi evento : eventi) {
		if (EntityUtils.getNestedProperty(evento.getIstanze(), "id.codice") != null) {
		    TipoAccessoEnum accesso = istanzeService.checkAccessoIstanza(evento.getIstanze(), responsabile);
		    if (accesso.equals(TipoAccessoEnum.CONSENTITO)) {
			eventiVisualizzabili.add(evento);
		    }
		} else {
		    if (EntityUtils.getNestedProperty(evento.getMovimenti(), "id.codice") != null) {
			boolean accesso = movimentiService.checkPermessiMovimento(evento.getMovimenti(), responsabile, false);
			if (accesso) {
			    eventiVisualizzabili.add(evento);
			}
		    }
		}
	    }
	}
	return eventiVisualizzabili;
    }

    @Override
    public void insert(Istanzeeventi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeeventiDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    private void childDataInsert(Istanzeeventi entity) {

	List<CategorieEventiMail> l = categorieEventiMailService.findByIstanzeEventi(entity);
	if (l != null && !l.isEmpty()) {
	    for (CategorieEventiMail cem : l) {
		try {		    
		    if(cem.getSoftware() != null && !StringUtils.isBlank(cem.getSoftware().getCodice()) 
			    && !"TT".equals(cem.getSoftware().getCodice())){			
			if(entity.getSoftware() == null || !cem.getSoftware().getCodice().equals(entity.getSoftware().getCodice())){
			    continue;
			}						
		    }		    
		    inviaMail(cem, entity);
		} catch (Exception e) {
		    // non fare niente
		    log.error("Errore nell'invio mail {}", e);
		}
	    }
	}
    }

    private void inviaMail(CategorieEventiMail cem, Istanzeeventi entity) throws FunzioneBusinessRemotaException {

	Mailtipo mt = cem.getMailtipo();
	if ((entity.getIstanze() != null && entity.getIstanze().getId() != null && entity.getIstanze().getId().getCodice() != null)
		|| (entity.getMovimenti() != null && entity.getMovimenti().getId() != null && entity.getMovimenti().getId().getCodice() != null)) {
	    Istanze istanza = null;
	    if (entity.getIstanze() != null && entity.getIstanze().getId() != null && entity.getIstanze().getId().getCodice() != null) {
		istanza = istanzeService.findById(new PkId(entity.getIstanze().getId().getCodice()));
	    }
	    Movimenti mov = null;
	    if (entity.getMovimenti() != null && entity.getMovimenti().getId() != null && entity.getMovimenti().getId().getCodice() != null) {
		mov = movimentiService.findById(new PkId(entity.getMovimenti().getId().getCodice()));
		if (istanza == null) {
		    istanza = istanzeService.findById(new PkId(entity.getMovimenti().getIstanza().getId().getCodice()));
		}
	    }
	    mt = mailtipoService.replaceOggettoCorpo(cem.getMailtipo(), istanza, mov);
	}
	MailMessageType mailMessageType = new MailMessageType();
	mailMessageType.setDestinatari(cem.getDestinatarimail());
	if (StringUtils.isNotBlank(cem.getDestinatariAggiuntivi())) {
	    String destinatariAggiuntivi = "";
	    destinatariAggiuntivi = getDestinatariAggiuntivi(cem.getDestinatariAggiuntivi(), entity);
	    if (StringUtils.isNotBlank(destinatariAggiuntivi)) {
		mailMessageType.setDestinatariInCopia(destinatariAggiuntivi);
	    }
	}
	mailMessageType.setOggetto(mt.getOggetto());
	String messageId = "ISTANZEEVENTI-" + ORMHelper.getIdcomuneAlias() + "-" + entity.getId().getCodice() + "-" + (new Date()).getTime();
	mailMessageType.setMessageID(messageId);
	mailMessageType.setInviaComeHtml(true);
	StringBuffer corpo = new StringBuffer(mt.getCorpo());
	corpo.append("<br /><br />");
	corpo.append(entity.getDescrizione());
	mailMessageType.setCorpoMail(corpo.toString());
	//mailServiceWSClient.sendMail(null, ORMHelper.getSoftware(), ORMHelper.getToken(), mailMessageType, 12000, 20000);
	
	Integer accountId = null;
	if(cem.getMailConfig() != null && cem.getMailConfig().getId() != null && cem.getMailConfig().getId().getCodice() != null){
	    accountId = cem.getMailConfig().getId().getCodice();
	}
	mailServiceWSClient.sendMail2(null, ORMHelper.getSoftware(), accountId, null, ORMHelper.getToken(), mailMessageType, 12000, 20000);
    }

    private String getDestinatariAggiuntivi(String destinatariAggiuntivi, Istanzeeventi entity) {

	if (entity.getIstanze() != null || entity.getMovimenti() != null) {
	    Istanze i = entity.getIstanze();
	    if (i == null) {
		i = entity.getMovimenti().getIstanza();
	    }
	    String result = "";
	    boolean isOperatore = destinatariAggiuntivi.indexOf(CategorieEventiMailService.TIPO_DESTINATARIO.OPERATORE.name()) >= 0;
	    boolean isResponsabileProc = destinatariAggiuntivi.indexOf(CategorieEventiMailService.TIPO_DESTINATARIO.RESPONSABILEPROC.name()) >= 0;
	    boolean isIstruttore = destinatariAggiuntivi.indexOf(CategorieEventiMailService.TIPO_DESTINATARIO.ISTRUTTORE.name()) >= 0;
	    if (isOperatore || isResponsabileProc || isIstruttore) {
		if (isOperatore) {
		    Responsabili r = i.getResponsabile();
		    if (r != null) {
			String mail = StringUtils.defaultString(r.getEmail()).trim();
			if (StringUtils.isNotBlank(mail)) {
			    result = mail;
			}
		    }
		}
		if (isResponsabileProc) {
		    Responsabili r = i.getResponsabileProcedimento();
		    if (r != null) {
			String mail = StringUtils.defaultString(r.getEmail()).trim();
			if (StringUtils.isNotBlank(mail)) {
			    result += ";" + mail;
			}
		    }
		}
		if (isIstruttore) {
		    Responsabili r = i.getIstruttore();
		    if (r != null) {
			String mail = StringUtils.defaultString(r.getEmail()).trim();
			if (StringUtils.isNotBlank(mail)) {
			    result += ";" + mail;
			}
		    }
		}
		if (StringUtils.isNotBlank(result)) {
		    if (result.startsWith(";")) {
			return result.substring(1);
		    } else {
			return result;
		    }
		}
	    }
	}
	return null;
    }

    private void dataIntegration(Istanzeeventi entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("L'evento passato è nullo");
	}
	if (entity.getFlagLetto() == null) {
	    entity.setFlagLetto(Boolean.FALSE);
	}
	if (entity.getData() == null) {
	    entity.setData(Calendar.getInstance().getTime());
	}
	if (StringUtils.isNotBlank(entity.getDescrizione())) {
	    if (entity.getDescrizione().length() > 4000) {
		entity.setDescrizione(StringUtils.left(entity.getDescrizione(), 3999));
	    }
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Istanzeeventi entity) {

	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanze(), PkId.class, "id.codice");
	entity.setIstanze(istanze);
	Movimenti movimento = movimentiService.bindDomainObject(entity.getMovimenti(), PkId.class, "id.codice");
	entity.setMovimenti(movimento);
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	// dato che l'id della categoria è settato a livello di codice senza la ricerca per controllare se esiste
	// catturo eventuali eccezioni nel caso di codici che non esistono
	if (EntityUtils.getNestedProperty(entity.getCategorieeventibase(), "id") != null) {
	    Categorieeventibase cat = categorieeventibaseService.findById(entity.getCategorieeventibase().getId());
	    entity.setCategorieeventibase(cat);
	} else {
	    entity.setCategorieeventibase(null);
	}
    }

    @Override
    public Istanzeeventi findById(PkId id) {

	return istanzeeventiDAO.findById(id);
    }

    @Override
    public void update(Istanzeeventi entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzeeventiDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzeeventi entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeeventiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Istanzeeventi entity) {

	// La cancellazione è sempre permessa
	return true;
    }

    @Override
    public List<Istanzeeventi> findByFilter(IstanzeeventiFilter filter, Integer firstResult, Integer maxResult) {

	return istanzeeventiDAO.findByFilter(filter, firstResult, maxResult);
    }

    @Override
    public void insert(String descrizione, String idCategoria, Movimenti mov, Istanze istanza) {

	Categorieeventibase cat = categorieeventibaseService.findById(idCategoria);
	Istanzeeventi evento = new Istanzeeventi();
	evento.setCategorieeventibase(cat);
	evento.setData(new Date());
	evento.setDescrizione(descrizione);
	evento.setFlagLetto(Boolean.FALSE);
	if (mov != null) {
	    evento.setMovimenti(mov);
	} else {
	    evento.setIstanze(istanza);
	}
	this.insert(evento);
    }

    @Override
    public void updateSegnaComeLetto(String idCategoria, Movimenti mov) {

	this.updateSegnaComeLetto(idCategoria, mov, false);
    }

    @Override
    public void updateSegnaComeLettoTutti(String idCategoria, Movimenti mov) {

	this.updateSegnaComeLetto(idCategoria, mov, true);
    }

    private void updateSegnaComeLetto(String idCategoria, Movimenti mov, boolean tutti) {

	if (StringUtils.isNotBlank(idCategoria) && (EntityUtils.getNestedProperty(mov, "id.codice") != null)) {
	    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.equals("id", idCategoria, "categorieeventibase", String.class));
	    fr.addFilterField(FilterUtils.equals("movimenti", mov, Movimenti.class));
	    ft.addRestriction(fr);
	    List<Istanzeeventi> eventi = istanzeeventiDAO.findByFilterTable(ft);
	    if (eventi != null && !eventi.isEmpty()) {
		if (tutti) {
		    for (Istanzeeventi istanzeeventi : eventi) {
			istanzeeventi.setFlagLetto(Boolean.TRUE);
			this.update(istanzeeventi);
		    }
		} else {
		    Istanzeeventi evento = eventi.get(0);
		    evento.setFlagLetto(Boolean.TRUE);
		    this.update(evento);
		}
	    }
	}
    }

    @Override
    public List<Istanzeeventi> findByFilterTable(FilterTable filterTable, Integer firstResult, Integer maxResult) {

	return istanzeeventiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<Istanzeeventi> findByIstanzaNonLetti(Istanze istanze) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanze", istanze, Istanze.class));
	fr.addFilterField(FilterUtils.equals("flagLetto", false, Boolean.class));
	ft.addRestriction(fr);
	return this.findByFilterTable(ft, null, null);
    }

    @Override
    public List<Istanzeeventi> findAllByScadenzarioFilter(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult, Integer maxResult) {

	BatchScadenzarioFilterHelper helper = new BatchScadenzarioFilterHelper(responsabiliService, softwareService, alberoprocService,
		statiistanzaService, comuniassociatiService);
	FilterTable ft = helper.getFilterTable(batchScadenzarioFilter, false, QUERY_PER.ISTANZE_EVENTI);
	FilterRestriction letto = new FilterRestriction();
	letto.addFilterField(FilterUtils.equals("flagLetto", Boolean.FALSE, Boolean.class));
	// Controllo se l'istanza è multi comune, nel saco devo aggiungere i filtro per codice comune dell'istanza.I record dovranno essere
	// filtrati per i soli comuni abilitati all'operatore
	if (log.isDebugEnabled()) {
	    log.debug("buildQuery# Controllo se si tratta di un installazione con idcomune {} è multi comune", ORMHelper.getIdcomune());
	}
	boolean isComuniAssociati = comuniassociatiService.isComuniassociati(ORMHelper.getIdcomune());
	if (isComuniAssociati) {
	    Responsabili responsabile = batchScadenzarioFilter.getResponsabile();
	    if (log.isDebugEnabled()) {
		log.debug("buildQuery# E' un installazione multicomune, recupero i comuni configurati per l'operatore {} ({})",
			new Object[] { responsabile.getResponsabile(), responsabile.getId().getCodice() });
	    }
	    List<Responsabilicomuni> responsabilicomunis = comuniassociatiService.checkComuniAbilitatiPerResponsabile(false);
	    if (responsabilicomunis != null && !responsabilicomunis.isEmpty()) {
		String[] codiceComune = new String[responsabilicomunis.size()];
		int i = 0;
		for (Responsabilicomuni responsabilicomuni : responsabilicomunis) {
		    codiceComune[i] = responsabilicomuni.getId().getCodicecomune();
		    i++;
		}
		letto.addFilterField(FilterUtils.in("codicecomune", codiceComune, "istanze.comune", String.class));
	    }
	}
	ft.addRestriction(letto);
	return istanzeeventiDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<IstanzeeventiListHelper> findByScadenzarioFilterHelper(BatchScadenzarioFilter batchScadenzarioFilter, Integer firstResult,
	    Integer maxResult) {

	return istanzeeventiDAO.findByScadenzarioFilterHelper(batchScadenzarioFilter, firstResult, maxResult);
    }

    @Override
    public int countByScadenzarioFilterHelper(BatchScadenzarioFilter batchScadenzarioFilter) {

	return istanzeeventiDAO.countByScadenzarioFilterHelper(batchScadenzarioFilter);
    }

    @Override
    public void clear() {

	istanzeeventiDAO.clear();
    }

    @Override
    public void insertEventoBackoffice(String descrizione, String idCategoria, String software) {

	Istanzeeventi evento = new Istanzeeventi();
	if (StringUtils.isNotBlank(idCategoria)) {
	    Categorieeventibase cat = categorieeventibaseService.findById(idCategoria);
	    evento.setCategorieeventibase(cat);
	}
	evento.setData(new Date());
	evento.setDescrizione(descrizione);
	evento.setFlagLetto(Boolean.FALSE);
	Software s = softwareService.findById(software);
	if (s == null) {
	    s = softwareService.findById(WebConstants.SOFTWARE_TT);
	}
	evento.setSoftware(s);
	this.insert(evento);
    }

    @Override
    public int countByEventiSistema(Responsabili responsabile, boolean isLetto) {

	FilterTable filterTable = getFilterForEventiSistema(responsabile, isLetto);
	filterTable.addOrder(FilterUtils.orderDesc("data"));
	return istanzeeventiDAO.countRecord(filterTable);
    }

    @Override
    public List<Istanzeeventi> findEventiSistema(Responsabili responsabile, Integer firstResult, Integer maxResult, boolean isLetto) {

	FilterTable filterTable = getFilterForEventiSistema(responsabile, isLetto);
	filterTable.addOrder(FilterUtils.orderDesc("data"));
	return istanzeeventiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    private FilterTable getFilterForEventiSistema(Responsabili responsabile, boolean isLetto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNull("id.codice", "istanze"));
	fr.addFilterField(FilterUtils.isNull("id.codice", "movimenti"));
	fr.addFilterField(FilterUtils.equals("flagLetto", isLetto, Boolean.class));
	//System.out.println(ORMHelper.getSoftware());
	if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT) || responsabile == null) {
	    fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "software", String.class));
	} else {
	    List<Software> softwareAttiviList = softwareService.findSoftwareAbilitati(responsabile, false);
	    List<String> codiciSoftware = new ArrayList<String>();
	    for (Iterator iterator = softwareAttiviList.iterator(); iterator.hasNext();) {
		Software software = (Software) iterator.next();
		codiciSoftware.add(software.getCodice());
	    }
	    fr.addFilterField(FilterUtils.in("codice", codiciSoftware.toArray(), "software", String.class));
	}
	ft.addRestriction(fr);
	return ft;
    }

    @Override
    public void insertEventoDiFirmaSuDocumento(DocumentiDaFirmare documentiDaFirmare) {

	Istanzeeventi istanzeeventi = new Istanzeeventi();
	Categorieeventibase categorieeventibase = categorieeventibaseService.findById(IstanzeeventiConstants.CATEGORIA_FIRMA);
	istanzeeventi.setCategorieeventibase(categorieeventibase);
	istanzeeventi.setData(documentiDaFirmare.getDataFirma());
	// Composiszione delle descrizione :
	StringBuffer descrizione = new StringBuffer("");
	if (documentiDaFirmare.getFlagDaFirmare().equals(StatiDocumentiDaFirmare.FIRMA_COMPLETA.name())) {
	    descrizione.append("E’ stato firmato il documento");
	} else {
	    descrizione.append("Non è stato firmato il documento");
	}
	descrizione.append(" ").append(documentiDaFirmare.getMovimentiallegati().getDescrizione()).append("(")
		.append(documentiDaFirmare.getMovimentiallegati().getOggetto().getNomefile()).append(")");
	istanzeeventi.setDescrizione(descrizione.toString());
	istanzeeventi.setFlagLetto(false);
	istanzeeventi.setMovimenti(documentiDaFirmare.getMovimentiallegati().getMovimento());
	istanzeeventi.setSoftware(documentiDaFirmare.getIstanze().getSoftware());
	this.insert(istanzeeventi);
    }

    @Override
    public int countEventiNonLettiByMovimento(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "movimenti", Integer.class));
	fr.addFilterField(FilterUtils.equals("flagLetto", false, Boolean.class));
	ft.addRestriction(fr);
	return istanzeeventiDAO.countRecord(ft);
    }

    @Override
    public void insertEventoOnereCopiato(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione, List<Integer> istanzeOneriCopiati) {

	if (codiceIstanzaOrigine == null) {
	    throw new IllegalArgumentException("Codice istanza origine non impostato");
	}
	if (codiceIstanzaDestinazione == null) {
	    throw new IllegalArgumentException("Codice istanza destinazione non impostato");
	}
	if (istanzeOneriCopiati == null || istanzeOneriCopiati.isEmpty()) {
	    return;
	}
	Istanze src = istanzeService.findById(new PkId(codiceIstanzaOrigine));
	Istanze dest = istanzeService.findById(new PkId(codiceIstanzaDestinazione));
	List<CausaleImportoOnerePerMessaggio> oneri = new ArrayList<CausaleImportoOnerePerMessaggio>();
	for (Integer codIstOnere : istanzeOneriCopiati) {
	    Istanzeoneri o = istanzeoneriService.findById(new PkId(codIstOnere));
	    oneri.add(CausaleImportoOnerePerMessaggio.daIstanzeOneri(o));
	}
	MessaggioDiSistema msg = new MessaggioEventoOneriCopiatiDaPratica(src.getNumeroistanza(), src.getNumeroistanza(), oneri);
	this.insert(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, src, msg);
	this.insert(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, dest, msg);
    }

    protected void insert(String categoriaEvento, Istanze istanza, MessaggioDiSistema messaggio) {

	Categorieeventibase categorieeventibase = categorieeventibaseService.findById(categoriaEvento);
	this.insert(Istanzeeventi.nuovoEventoIstanza(istanza, messaggio, categorieeventibase));
    }

    @Override
    public void insertEventoErroreInCancellazioneMovimentoDaIstanzeCollegate(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione,
	    Integer codiceMovimentoDestinazione, String messaggio) {

	if (codiceIstanzaOrigine == null) {
	    throw new IllegalArgumentException("Codice istanza origine non impostato");
	}
	if (codiceIstanzaDestinazione == null) {
	    throw new IllegalArgumentException("Codice istanza destinazione non impostato");
	}
	if (codiceMovimentoDestinazione == null) {
	    throw new IllegalArgumentException("Codice codice movimento non impostato");
	}
	Istanze src = istanzeService.findById(new PkId(codiceIstanzaOrigine));
	Istanze dest = istanzeService.findById(new PkId(codiceIstanzaDestinazione));
	Movimenti mov = movimentiService.findById(new PkId(codiceMovimentoDestinazione));
	MessaggioDiSistema msg = new MessaggioCancellazioneMovimentoDaIstanzaCollegata(src, dest, mov, messaggio);
	this.insert(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, src, msg);
	this.insert(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, dest, msg);
    }

    @Override
    public void insertEventoErroreAnnullamentoPosizioneDebitoria(Integer idIstanzeOneri, Integer idDettPosizioneDebitoria, String messaggio) {

	if (idIstanzeOneri == null) {
	    throw new IllegalArgumentException("idIstanzeOneri non impostato");
	}
	if (idDettPosizioneDebitoria == null) {
	    throw new IllegalArgumentException("idDettPosizioneDebitoria non impostato");
	}
	Istanzeoneri ionere = istanzeeventiDAO.getById(Istanzeoneri.class, idIstanzeOneri);
	DettPosizioneDebitoria posizioneDebitoria = istanzeeventiDAO.getById(DettPosizioneDebitoria.class, idDettPosizioneDebitoria);
	MessaggioDiSistema msg = new MessaggioErroreAnnullamentoPosizioneDebitoria(ionere, posizioneDebitoria, messaggio);
	this.insert(IstanzeeventiConstants.CATEGORIA_AVVERTIMENTI, ionere.getIstanza(), msg);
    }
}
