package it.gruppoinit.pal.gp.core.features.sorteggi.testata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.mailservice.schemas.messages.AttachmentType;
import it.gruppoinit.mailservice.schemas.messages.AttachmentsType;
import it.gruppoinit.mailservice.schemas.messages.MailMessageType;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Naturaendo;
import it.gruppoinit.pal.gp.core.domain.NaturaendoId;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.SorteggiCategorie;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettaglio;
import it.gruppoinit.pal.gp.core.domain.Sorteggidettagliomovimenti;
import it.gruppoinit.pal.gp.core.domain.SorteggidettagliomovimentiId;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestata;
import it.gruppoinit.pal.gp.core.domain.Sorteggitestatainfo;
import it.gruppoinit.pal.gp.core.domain.SorteggitestatainfoId;
import it.gruppoinit.pal.gp.core.domain.Statiistanza;
import it.gruppoinit.pal.gp.core.domain.StatiistanzaId;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.domain.util.Clcg;
import it.gruppoinit.pal.gp.core.domain.web.SorteggitestataCommand;
import it.gruppoinit.pal.gp.core.features.mailtipo.MailtipoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeHelper;
import it.gruppoinit.pal.gp.core.features.segnaposto.DocumentMergeService;
import it.gruppoinit.pal.gp.core.features.sorteggi.FiltriSorteggioBean;
import it.gruppoinit.pal.gp.core.features.sorteggi.SorteggioResponse;
import it.gruppoinit.pal.gp.core.features.sorteggi.TipologiaStatoSorteggioIstanzaEnum;
import it.gruppoinit.pal.gp.core.features.sorteggi.categorie.SorteggiCategorieService;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggidettaglioDAO;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggidettaglioService;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggidettagliomovimentiService;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioDTO;
import it.gruppoinit.pal.gp.core.features.sorteggi.dettaglio.SorteggioDettaglioPerInserimentoDTO;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterField;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;
import it.gruppoinit.pal.gp.core.service.InventarioprocedimentiService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.NaturaendoService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.StatiistanzaService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.TipiarchivioistanzeService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.utils.Utilities;
import it.gruppoinit.pal.gp.core.ws.client.MailServiceWSClient;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class SorteggitestataServiceImpl extends BaseServiceImpl<Sorteggitestata, PkId> implements SorteggitestataService {

    private static final String DEBUG_SALVAMOVIMENTO_MSG = "salvaMovimento#Sortgeggio dettaglio = {}, Istanza  = {}, Sorteggiata = {}  Insert movimento = {} ";
    private static final Logger log = LoggerFactory.getLogger(SorteggitestataServiceImpl.class);
    private SorteggitestataDAO sorteggitestataDAO;
    private SorteggidettaglioDAO sorteggidettaglioDAO;
    private IstanzeService istanzeService;
    private AlberoprocService alberoprocService;
    private SorteggiCategorieService sorteggiCategorieService;
    private StatiistanzaService statiistanzaService;
    private TipiMovimentoService tipiMovimentoService;
    private SorteggitestatainfoService sorteggitestatainfoService;
    private SorteggidettaglioService sorteggidettaglioService;
    private ComuniService comuniService;
    private OggettiService oggettiService;
    private SoftwareService softwareService;
    private SorteggidettagliomovimentiService sorteggidettagliomovimentiService;
    private NaturaendoService naturaendoService;
    private MovimentiService movimentiService;
    private TipiarchivioistanzeService tipiarchivioistanzeService;
    private TipiprocedureService tipiprocedureService;
    private InventarioprocedimentiService inventarioprocedimentiService;
    private MailtipoService mailtipoService;
    private MailServiceWSClient mailServiceWSClient;
    private ContenttypesService contentTypesService;
    private DocumentMergeService documentMergeService;

    @Autowired
    public void setNaturaendoService(NaturaendoService naturaendoService) {

	this.naturaendoService = naturaendoService;
    }

    @Autowired
    public void setSorteggidettaglioDAO(SorteggidettaglioDAO sorteggidettaglioDAO) {

	this.sorteggidettaglioDAO = sorteggidettaglioDAO;
    }

    @Autowired
    public void setSorteggitestataDAO(SorteggitestataDAO sorteggitestataDAO) {

	this.sorteggitestataDAO = sorteggitestataDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setSorteggiCategorieService(SorteggiCategorieService sorteggiCategorieService) {

	this.sorteggiCategorieService = sorteggiCategorieService;
    }

    @Autowired
    public void setStatiistanzaService(StatiistanzaService statiistanzaService) {

	this.statiistanzaService = statiistanzaService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setSorteggitestatainfoService(SorteggitestatainfoService sorteggitestatainfoService) {

	this.sorteggitestatainfoService = sorteggitestatainfoService;
    }

    @Autowired
    public void setSorteggidettaglioService(SorteggidettaglioService sorteggidettaglioService) {

	this.sorteggidettaglioService = sorteggidettaglioService;
    }

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setSorteggidettagliomovimentiService(SorteggidettagliomovimentiService sorteggidettagliomovimentiService) {

	this.sorteggidettagliomovimentiService = sorteggidettagliomovimentiService;
    }

    @Autowired
    public void setTipiarchivioistanzeService(TipiarchivioistanzeService tipiarchivioistanzeService) {

	this.tipiarchivioistanzeService = tipiarchivioistanzeService;
    }

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Autowired
    public void setInventarioprocedimentiService(InventarioprocedimentiService inventarioprocedimentiService) {

	this.inventarioprocedimentiService = inventarioprocedimentiService;
    }

    @Autowired
    public void setMailtipoService(MailtipoService mailtipoService) {

	this.mailtipoService = mailtipoService;
    }

    @Autowired
    public void setMailServiceWSClient(MailServiceWSClient mailServiceWSClient) {

	this.mailServiceWSClient = mailServiceWSClient;
    }

    @Autowired
    public void setContentTypesService(ContenttypesService contentTypesService) {

	this.contentTypesService = contentTypesService;
    }

    @Autowired
    public void setDocumentMergeService(DocumentMergeService documentMergeService) {

	this.documentMergeService = documentMergeService;
    }

    @Override
    protected Class<Sorteggitestata> getEntityClass() {

	return Sorteggitestata.class;
    }

    @Override
    public List<Sorteggitestata> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return sorteggitestataDAO.findAll(firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insertConDettagli(Sorteggitestata entity, List<SorteggioDettaglioPerInserimentoDTO> dettagli) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<Sorteggitestatainfo> sorteggitestatainfos = entity.getSorteggitestatainfos();
	    entity.setSorteggitestatainfos(null);
	    entity.setSorteggidettaglios(null);
	    sorteggitestataDAO.insert(entity);
	    childDataInsert(entity, sorteggitestatainfos, dettagli);
	    entity.setSorteggitestatainfos(sorteggitestatainfos);
	}
    }

    @Deprecated
    @Override
    public void insert(Sorteggitestata entity) {

	// §§§BEGIN§§§
	throw new RuntimeException("Metodo non supportato");
	// §§§END§§§
    }

    private void childDataInsert(Sorteggitestata entity, Set<Sorteggitestatainfo> infoAggiuntive,
	    List<SorteggioDettaglioPerInserimentoDTO> dettaglio) {

	for (Sorteggitestatainfo sorteggitestatainfo : infoAggiuntive) {
	    sorteggitestatainfo.getId().setFkStid(entity.getId().getCodice());
	    if (!StringUtils.isBlank(sorteggitestatainfo.getValore()) && sorteggitestatainfo.getValore().length() > 4000) {
		sorteggitestatainfo.setValore(sorteggitestatainfo.getValore().substring(0, 3996) + "...");
	    }
	    sorteggitestatainfoService.insert(sorteggitestatainfo);
	}
	if (dettaglio != null) {
	    for (SorteggioDettaglioPerInserimentoDTO d : dettaglio) {
		Sorteggidettaglio sorteggidettaglio = new Sorteggidettaglio();
		Istanze istanza = istanzeService.findById(new PkId(d.getCodiceIstanza()));
		sorteggidettaglio.setSorteggitestata(entity);
		sorteggidettaglio.setSorteggiata(BooleanUtils.isTrue(d.getSorteggiata()));
		sorteggidettaglio.setFlagInterventoObbligatorio(BooleanUtils.isTrue(d.getFlagInterventoObbligatorio()));
		sorteggidettaglio.setIstanza(istanza);
		sorteggidettaglioService.insert(sorteggidettaglio);
	    }
	}
    }

    @Override
    public Sorteggitestata findById(PkId id) {

	return sorteggitestataDAO.findById(id);
    }

    @Override
    public void update(Sorteggitestata entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    sorteggitestataDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
	// §§§END§§§
    }

    @Override
    public void delete(Sorteggitestata entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    sorteggitestataDAO.delete(entity);
	}
	// §§§END§§§
    }

    protected void childDelete(Sorteggitestata entity) {

	// §§§BEGIN§§§
	// Sorteggitestatainfo
	Set<Sorteggitestatainfo> sorteggitestatainfos = entity.getSorteggitestatainfos();
	for (Sorteggitestatainfo sorteggitestatainfo : sorteggitestatainfos) {
	    sorteggitestatainfoService.delete(sorteggitestatainfo);
	}
	// Sorteggidettaglio e Sorteggidettagliomovimenti
	Set<Sorteggidettaglio> sorteggidettaglios = entity.getSorteggidettaglios();
	for (Sorteggidettaglio sorteggidettaglio : sorteggidettaglios) {
	    Set<Sorteggidettagliomovimenti> sorteggidettagliomovimentis = sorteggidettaglio.getSorteggidettagliomovimentis();
	    for (Sorteggidettagliomovimenti sorteggidettagliomovimenti : sorteggidettagliomovimentis) {
		sorteggidettagliomovimentiService.delete(sorteggidettagliomovimenti);
	    }
	    sorteggidettaglioService.delete(sorteggidettaglio);
	}
	// §§§END§§§
    }

    protected boolean isDeleteAllowed(Sorteggitestata entity) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public List<Sorteggitestata> findAllSenzaCategoria() {

	return sorteggitestataDAO.findAllSenzaCategoria();
    }

    @Override
    public List<Sorteggitestata> findAllSenzaCategoria(String software) {

	return sorteggitestataDAO.findAllSenzaCategoria(software);
    }

    /**
     * Effettua il sorteggio delle istanze attraverso i seguenti passi: 1. Filtra le istanze in base ai parametri
     * specificati in SorteggitestataFilter 2. Esclude le istanze in base ai parametri specificati in
     * SorteggitestataFilter 3. Effettua il sorteggio in base ai parametri: percentuale, arrotondamento e gruppiIstanze
     * 4. Set delle proprietà sorteggitestatainfos e sorteggidettaglios nella entity 5. Eventuale salvataggio della
     * entity
     */
    @Override
    public SorteggioResponse sorteggia(Sorteggitestata testata, FiltriSorteggioBean filter) {

	try {
	    // §§§BEGIN§§§
	    dataIntegrationFilter(filter);
	    // 1. Filtra le istanze in base ai parametri specificati in SorteggitestataFilter
	    List<Integer> istanzeFiltrate = filtraIstanze(filter);
	    // 3. Effettua il sorteggio in base ai parametri: percentuale, arrotondamento e gruppiIstanze
	    List<Integer> istanzeSorteggiate = eseguiSorteggio(filter, istanzeFiltrate);
	    // 4. creazione sorteggidettaglios
	    List<SorteggioDettaglioPerInserimentoDTO> dettaglio = toSorteggioDettaglioPerInserimentoDTO(istanzeSorteggiate, istanzeFiltrate);
	    // 6. creazione sorteggitestatainfos
	    Integer idTestata = (testata != null && testata.getId() != null) ? testata.getId().getCodice() : null;
	    Set<Sorteggitestatainfo> sorteggitestatainfos = findSorteggitestatainfos(idTestata, filter);
	    // 7. Set delle proprietà nella entity
	    testata.setComune(this.comuniService.findByCodiceComune(new Comuni(filter.getCodiceComune())));
	    if (filter.getDataSorteggio() != null) {
		testata.setStDatasorteggio(filter.getDataSorteggio());
	    } else {
		testata.setStDatasorteggio(new Date());
	    }
	    if (Boolean.TRUE.equals(filter.getSalvaSorteggio())) {
		if (filter.getDescrizioneSorteggio() != null) {
		    testata.setStDescrizione(filter.getDescrizioneSorteggio());
		}
		if (filter.getIdCategoriaSorteggio() != null) {
		    testata.setCategoria(this.sorteggiCategorieService.findById(new PkId(filter.getIdCategoriaSorteggio())));
		}
	    }
	    testata.setSorteggitestatainfos(sorteggitestatainfos);
	    // 8. Eventuale salvataggio della entity
	    if (Boolean.TRUE.equals(filter.getSalvaSorteggio())) {
		if (idTestata == null) {
		    testata.setId(null);
		}
		this.insertConDettagli(testata, dettaglio);
	    }
	    this.sorteggitestataDAO.commitFlush();
	    Integer codiceOggetto = null;
	    // 9. Verifico se va generato l'allegato
	    if (filter.getIdDocumentoTipo() != null) {
		DocumentMergeHelper dmh = new DocumentMergeHelper();
		dmh.getParams().put("ST_ID", testata.getId().getCodice().toString());
		Oggetti oggetto = this.documentMergeService.createAllegatoDaDocumentoTipo(filter.getIdDocumentoTipo(), null, null, dmh);
		codiceOggetto = oggetto.getId().getCodice();
		testata.setOggetto(oggetto);
		this.sorteggitestataDAO.update(testata);
		this.sorteggitestataDAO.commitFlush();
	    }
	    // 10. Verifico se va creato il movimento
	    if (filter.getTipoMovimentoDaCreare() != null) {
		Movimenti movDaCreare = new Movimenti();
		movDaCreare.setTipomovimento(filter.getTipoMovimentoDaCreare());
		movDaCreare.setData(testata.getStDatasorteggio());
		movDaCreare.setDatainserimento(testata.getStDatasorteggio());
		movDaCreare.setAmministrazioni(filter.getAmministrazione());
		movDaCreare.setResponsabile(filter.getResponsabile());
		movDaCreare.setFlagDisabilitato(Boolean.FALSE);
		movDaCreare.setFlagCmovObblig(Boolean.FALSE);
		movDaCreare.setOrdineInserimento(0);
		this.salvaMovimento(testata.getId().getCodice(), movDaCreare, TipologiaStatoSorteggioIstanzaEnum.SORTEGGIATE);
	    }
	    // 11. Verifico se va inviata la mail
	    if (filter.getMailTipo() != null && !StringUtils.isBlank(filter.getMailDestinatario())) {
		MailMessageType message = new MailMessageType();
		//11.1 Oggetto e corpo della mail
		Mailtipo mailSostituita = this.mailtipoService.replaceOggettoCorpo(filter.getMailTipo(), null, null, testata);
		//11.2 Attachment
		if (codiceOggetto != null) {
		    Oggetti oggetto = this.oggettiService.findById(new PkId(codiceOggetto));
		    AttachmentType attachment = new AttachmentType();
		    attachment.setId(codiceOggetto.toString());
		    attachment.setDescrizione(oggetto.getNomefile());
		    attachment.setFileName(oggetto.getNomefile());
		    attachment.setMimeType(this.contentTypesService.findMimeTypeByFileName(oggetto.getNomefile()));
		    attachment.setBinaryData(Utilities.bytesToDataHandler(oggetto.getOggetto()));
		    AttachmentsType attachments = new AttachmentsType();
		    attachments.getAttachment().add(attachment);
		    message.setAttachments(attachments);
		}
		message.setCorpoMail(mailSostituita.getCorpo());
		message.setDestinatari(filter.getMailDestinatario());
		message.setInviaComeHtml(Boolean.TRUE);
		message.setMessageID(null);
		message.setMittente(null);
		message.setOggetto(mailSostituita.getOggetto());
		try {
		    this.mailServiceWSClient.sendMail2(null, filter.getSoftware(), null, ORMHelper.getToken(), message);
		} catch (Exception ex) {
		    log.error("Errore durante l'invio della mail dai sorteggi schedulati: {}", ex);
		}
	    }
	    if (Boolean.TRUE.equals(filter.getSalvaSorteggio())) {
		return new SorteggioResponse(testata, this.findDettaglioDTO(testata.getId().getCodice()));
	    }
	    return new SorteggioResponse(testata, this.findDettaglioDTO(dettaglio));
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public Sorteggitestata sorteggiaLR152013(SorteggitestataCommand command, FiltriSorteggioBean filter) {

	// §§§BEGIN§§§
	Sorteggitestata testata = command.getEntity();
	dataIntegrationFilter(filter);
	// 1. Lista delle istanze obbligatorie
	List<Integer> istanzeObbligatorie = filtraIstanzeLR152013(filter, WebConstants.PRATICHE_OBBLIGATORIE, true);
	istanzeObbligatorie.addAll(filtraIstanzeLR152013(filter, WebConstants.PRATICHE_OBBLIGATORIE, false));
	// 2. Lista delle istanze restanti
	List<Integer> istanzeRestanti = filtraIstanzeLR152013(filter, WebConstants.PRATICHE_RESTANTI, true);
	istanzeRestanti.addAll(filtraIstanzeLR152013(filter, WebConstants.PRATICHE_RESTANTI, false));
	// 3. Lista totale delle istanze prendenti parte al campionamento
	List<Integer> istanzeTotali = new ArrayList<Integer>();
	istanzeTotali.addAll(istanzeObbligatorie);
	istanzeTotali.addAll(istanzeRestanti);
	// 4. Calcolo del numero delle pratiche da estrarre
	Integer praticheDaEstrarre = calcolaPraticheDaEstrarre(istanzeTotali.size(), istanzeObbligatorie.size(), filter.getPercentuale());
	// 5 Lista delle istanze restanti applicando peso e ordinamento
	List<Integer> istanzeRestantiOrdinatePesate = filtraIstanzeLR152013(filter, WebConstants.PRATICHE_RESTANTI_PESATE, true);
	istanzeRestantiOrdinatePesate.addAll(filtraIstanzeLR152013(filter, WebConstants.PRATICHE_RESTANTI_PESATE, false));
	// 6. Sorteggio delle istanze restanti	    
	Calendar cal = Calendar.getInstance();
	String seed = Integer.toString(cal.get(Calendar.DAY_OF_MONTH))
		+ StringUtils.leftPad(Integer.toString(cal.get(Calendar.MONTH) + 1), 2, "0")
		+ StringUtils.leftPad(Integer.toString(cal.get(Calendar.HOUR_OF_DAY)), 2, "0")
		+ StringUtils.leftPad(Integer.toString(cal.get(Calendar.MINUTE)), 2, "0");
	List<Integer> istanzeRestantiEstratte = trovaIstanzeRestantiEstratte(istanzeRestantiOrdinatePesate, 1, istanzeRestantiOrdinatePesate.size(),
		praticheDaEstrarre, Integer.valueOf(seed));
	// 6.1 Verifica se Doppie ed eventuale ripetizione del sorteggio
	Boolean praticheDoppie = false;
	Integer numeriDaGenerare = praticheDaEstrarre;
	do {
	    if (!verifyNumberElement(istanzeRestantiEstratte, praticheDaEstrarre)) {
		numeriDaGenerare++;
		istanzeRestantiEstratte = trovaIstanzeRestantiEstratte(istanzeRestantiOrdinatePesate, 1, istanzeRestantiOrdinatePesate.size(),
			numeriDaGenerare, Integer.valueOf(seed));
		praticheDoppie = true;
	    } else {
		praticheDoppie = false;
	    }
	} while (Boolean.TRUE.equals(praticheDoppie));
	// 7 Lista istanze totali sorteggiate
	List<Integer> istanzeSorteggiate = new ArrayList<Integer>();
	istanzeSorteggiate.addAll(istanzeObbligatorie);
	istanzeSorteggiate.addAll(istanzeRestantiEstratte);
	List<SorteggioDettaglioPerInserimentoDTO> dettaglio = toSorteggioDettaglioPerInserimentoDTO(istanzeSorteggiate, istanzeTotali);
	// 9.1 creazione sorteggitestatainfos
	Set<Sorteggitestatainfo> sorteggitestatainfos = findSorteggitestatainfos(testata.getId().getCodice(), filter);
	// 9.2 Ulteriori sorteggitestatainfos
	Sorteggitestatainfo sorteggitestatainfo = new Sorteggitestatainfo();
	SorteggitestatainfoId sorteggitestatainfoId = new SorteggitestatainfoId(testata.getId().getCodice(), "label.dati_generatore");
	sorteggitestatainfo.setId(sorteggitestatainfoId);
	sorteggitestatainfo.setEtichetta(getMessageFromBundle("label.dati_utilizzati_generatore", null));
	sorteggitestatainfo.setValore("Valore minimo: 1 | Valore massimo: " + istanzeRestantiOrdinatePesate.size() + " | Numeri da generare: " +
				      numeriDaGenerare + " | Seme generatore: " + seed);
	sorteggitestatainfo.setOrdine(sorteggitestatainfos.size());
	sorteggitestatainfos.add(sorteggitestatainfo);
	// 10 Set delle proprietà nella entity	
	testata.setComune(this.comuniService.findByCodiceComune(new Comuni(filter.getCodiceComune())));
	if (filter.getDataSorteggio() != null) {
	    testata.setStDatasorteggio(filter.getDataSorteggio());
	} else {
	    testata.setStDatasorteggio(new Date());
	}
	if (Boolean.TRUE.equals(filter.getSalvaSorteggio())) {
	    if (!StringUtils.isBlank(filter.getDescrizioneSorteggio())) {
		testata.setStDescrizione(filter.getDescrizioneSorteggio());
	    }
	    if (filter.getIdCategoriaSorteggio() != null) {
		testata.setCategoria(this.sorteggiCategorieService.findById(new PkId(filter.getIdCategoriaSorteggio())));
	    }
	}
	// Set delle proprietà sorteggitestatainfos nella entity
	testata.setSorteggitestatainfos(sorteggitestatainfos);
	// 11. Eventuale salvataggio della entity
	if (Boolean.TRUE.equals(filter.getSalvaSorteggio())) {
	    this.insertConDettagli(testata, dettaglio);
	}
	command.setSorteggidettaglioDTOList(this.findDettaglioDTO(testata.getId().getCodice()));
	return testata;
    }

    @Override
    public List<SorteggioDettaglioDTO> findDettaglioDTO(Integer idTestata) {

	return this.sorteggitestataDAO.findDettaglioDTO(idTestata);
    }

    private List<SorteggioDettaglioDTO> findDettaglioDTO(List<SorteggioDettaglioPerInserimentoDTO> dettagli) {

	List<SorteggioDettaglioDTO> retVal = new ArrayList<SorteggioDettaglioDTO>();
	if (dettagli == null) {
	    return retVal;
	}
	for (SorteggioDettaglioPerInserimentoDTO dettaglio : dettagli) {
	    Istanze istanza = this.istanzeService.findById(new PkId(dettaglio.getCodiceIstanza()));
	    retVal.add(SorteggioDettaglioDTO.fromIstanza(istanza, dettaglio.getSorteggiata(), dettaglio.getFlagInterventoObbligatorio()));
	}
	return retVal;
    }

    private boolean verifyNumberElement(List<Integer> data, Integer number) {

	eliminaDuplicati(data);
	return data.size() == number;
    }

    private void eliminaDuplicati(List<Integer> data) {

	LinkedHashSet<Integer> listSenzaDuplicati = new LinkedHashSet<Integer>();
	for (Integer x : data) {
	    listSenzaDuplicati.add(x);
	}
	data.clear();
	data.addAll(listSenzaDuplicati);
    }

    private List<Integer> trovaIstanzeRestantiEstratte(List<Integer> istanzeRestantiOrdinatePesate, Integer valoreMin, Integer valoreMax,
	    Integer numeriDaGenerare, Integer seed) {

	List<Integer> istanzeRestantiEstratte = new ArrayList<Integer>();
	if (!istanzeRestantiOrdinatePesate.isEmpty()) {
	    Clcg clcg = new Clcg();
	    clcg.setSeed(seed);
	    List<Integer> numeriEstratti = clcg.getListaEstratti(valoreMin, valoreMax, numeriDaGenerare);
	    for (Integer estratto : numeriEstratti) {
		istanzeRestantiEstratte.add(istanzeRestantiOrdinatePesate.get(estratto - 1));
	    }
	}
	return istanzeRestantiEstratte;
    }

    private Integer calcolaPraticheDaEstrarre(Integer istanzeTotali, Integer istanzeObbligatorie, Integer percentuale) {

	if (istanzeTotali != null && istanzeTotali != 0 && istanzeTotali != istanzeObbligatorie) {
	    Double praticheDaEstrarre = Math
		    .ceil(((istanzeTotali.doubleValue() * percentuale.doubleValue()) / 100) - istanzeObbligatorie.doubleValue());
	    if (praticheDaEstrarre < 1) {
		praticheDaEstrarre = 1.0;
	    }
	    return praticheDaEstrarre.intValue();
	} else {
	    return 0;
	}
    }

    private List<Integer> eseguiSorteggio(FiltriSorteggioBean filter, List<Integer> istanze4Sorteggio) {

	// 3. Effettua il sorteggio in base ai parametri: percentuale, arrotondamento e gruppiIstanze
	List<Integer> istanzeSorteggiate = new ArrayList<Integer>();
	// Sorteggio per gruppi di istanze
	if (filter.getGruppiDiIstanze() != null) {
	    List<Integer> istanzeRimaste = new ArrayList<Integer>();
	    istanzeRimaste.addAll(istanze4Sorteggio);
	    int i = 0;
	    // Calcolo il numero di gruppi
	    int numerogruppi = 0;
	    numerogruppi = istanze4Sorteggio.size() / filter.getGruppiDiIstanze();
	    while (i < numerogruppi) {
		List<Integer> gruppoIstanzeTemp = getGruppoIstanze(istanzeRimaste, filter.getGruppiDiIstanze());
		istanzeRimaste.removeAll(gruppoIstanzeTemp);
		istanzeSorteggiate.addAll(sorteggiaIstanze(gruppoIstanzeTemp, filter.getPercentuale(), filter.getArrotondamento()));
		i++;
	    }
	    istanze4Sorteggio.removeAll(istanzeRimaste);
	} else {
	    // Sorteggio senza gruppi di istanze
	    List<Integer> istanze4SorteggioAsList = new ArrayList<Integer>();
	    istanze4SorteggioAsList.addAll(istanze4Sorteggio);
	    istanzeSorteggiate = sorteggiaIstanze(istanze4SorteggioAsList, filter.getPercentuale(), filter.getArrotondamento());
	}
	return istanzeSorteggiate;
    }

    private List<Integer> filtraIstanze(FiltriSorteggioBean filtro) {

	return this.sorteggitestataDAO.getCodiciIstanza(filtro);
    }

    /**
     * Restituisce la lista delle istanze presentate nell'intervallo di campionamento specificato in
     * SorteggitestataFilter, filtrate in base alla LR 15/2013 e ordinate per numeroprotocollo
     * <p>
     * se tipoPratiche=0 restituisce le pratiche obbligatorie
     * <p>
     * se tipoPratiche=1 restituisce le pratiche restanti
     * <p>
     * se tipoPratiche=2 restituisce le pratiche restanti con gestione del peso (le pratiche con peso doppio vengono
     * inserite 2 volte nella lista in posizioni successive)
     * 
     * @param filter
     * @param tipoPratiche
     * @return
     */
    private List<Integer> filtraIstanzeLR152013(FiltriSorteggioBean filter, int tipoPratiche, Boolean filtroMovimento) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction sorteggiRestriction = new FilterRestriction();
	// Filtro Codicecomune
	if (!StringUtils.isBlank(filter.getCodiceComune())) {
	    sorteggiRestriction.addFilterField(FilterUtils.equals("comune.codicecomune", filter.getCodiceComune(), String.class));
	}
	if (BooleanUtils.isFalse(filtroMovimento)) {
	    // Filtro Data presentazione istanza dal al
	    if (filter.getIntervalloCampionamentoDal() != null) {
		sorteggiRestriction.addFilterField(FilterUtils.greaterEqual("data", filter.getIntervalloCampionamentoDal(), Timestamp.class));
	    }
	    if (filter.getIntervalloCampionamentoAl() != null) {
		sorteggiRestriction.addFilterField(FilterUtils.smallerEqual("data", filter.getIntervalloCampionamentoAl(), Timestamp.class));
	    }
	} else {
	    // Filtro data presentazione precedente a IntervalloCampionamentoDal	
	    if (filter.getIntervalloCampionamentoDal() != null) {
		sorteggiRestriction.addFilterField(FilterUtils.smaller("data", filter.getIntervalloCampionamentoDal(), Timestamp.class));
	    }
	    // Filtro il movimento effettuato nell'intervallo di campionamento
	    if (!StringUtils.isBlank(filter.getTipoMovimento())) {
		FilterField<?> tipoMovimento = FilterUtils.equals("tipomovimento.id.tipomovimento", filter.getTipoMovimento(), "istanzemovimentis",
			String.class);
		sorteggiRestriction.addFilterField(tipoMovimento);
		if (filter.getIntervalloCampionamentoDal() != null) {
		    sorteggiRestriction.addFilterField(
			    FilterUtils.greaterEqual("data", filter.getIntervalloCampionamentoDal(), "istanzemovimentis", Timestamp.class));
		}
		if (filter.getIntervalloCampionamentoAl() != null) {
		    sorteggiRestriction.addFilterField(
			    FilterUtils.smallerEqual("data", filter.getIntervalloCampionamentoAl(), "istanzemovimentis", Timestamp.class));
		}
	    } else {
		// Se il movimento non è specificato non deve restituire alcun risultato 
		FilterField<?> tipoMovimento = FilterUtils.equals("tipomovimento.id.tipomovimento", "-1", "istanzemovimentis", String.class);
		sorteggiRestriction.addFilterField(tipoMovimento);
	    }
	}
	// Filtro Intervento Multiplo		
	FilterRestriction padri = new FilterRestriction();
	padri.setAndOrRestriction(AndOrRestriction.OR);
	if (filter.getScCodiciInterventoProc() != null && !filter.getScCodiciInterventoProc().isEmpty()) {
	    for (String scCodice : filter.getScCodiciInterventoProc()) {
		padri.addFilterField(FilterUtils.startsWith("scCodice", scCodice, "alberoproc"));
	    }
	}
	filterTable.addRestriction(padri);
	// Filtro per pratiche obbligatorie
	if (BooleanUtils.isTrue(tipoPratiche == WebConstants.PRATICHE_OBBLIGATORIE)) {
	    // Filtro Archivio pratiche		
	    sorteggiRestriction
		    .addFilterField(FilterUtils.equals("archivio", WebConstants.TIPIARCHIVIOISTANZA_P0, "tipiarchivioistanza", String.class));
	}
	// Filtro per pratiche restanti e restanti pesate
	if (BooleanUtils.isTrue(tipoPratiche == WebConstants.PRATICHE_RESTANTI)
		|| BooleanUtils.isTrue(tipoPratiche == WebConstants.PRATICHE_RESTANTI_PESATE)) {
	    // Filtro Archivio pratiche		
	    FilterRestriction praticheRestanti = new FilterRestriction();
	    praticheRestanti.setAndOrRestriction(AndOrRestriction.OR);
	    praticheRestanti.addFilterField(FilterUtils.equals("archivio", WebConstants.TIPIARCHIVIOISTANZA_P1, "tipiarchivioistanza", String.class));
	    praticheRestanti.addFilterField(FilterUtils.equals("archivio", WebConstants.TIPIARCHIVIOISTANZA_P2, "tipiarchivioistanza", String.class));
	    filterTable.addRestriction(praticheRestanti);
	}
	filterTable.addRestriction(sorteggiRestriction);
	// aggiungo l'ordinamento solamente dopo la count
	String[] padNumeroprotocollo = new String[] { "20", "' '" };
	//ORDINE PER ANNO0000NUMPROT
	filterTable.addOrder(
		FilterUtils.orderAsc("dataprotocollo", FunctionsEnum.NVL_FUNCTION, "'01/01/2999'", OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	filterTable.addOrder(FilterUtils.orderAsc("numeroprotocollo", FunctionsEnum.LPAD_FUNCTION, padNumeroprotocollo));
	List<Integer> resultSet = new ArrayList<Integer>();
	//////////////////////////////////////////////////////////////////////////////////	
	List<Istanze> listIstanze = istanzeService.findByFilterTable(filterTable, null, null);
	for (Istanze istanza : listIstanze) {
	    resultSet.add(istanza.getId().getCodice());
	    if (tipoPratiche == WebConstants.PRATICHE_RESTANTI_PESATE) {
		// Controllo se tipiarchivioistanza=P2
		if (istanza.getTipiarchivioistanza().getArchivio().equals(WebConstants.TIPIARCHIVIOISTANZA_P2)) {
		    resultSet.add(istanza.getId().getCodice());
		}
	    }
	}
	//////////////////////////////////////////////////////////////////////////////////
	return resultSet;
    }

    /**
     * Restituisce la lista dei sorteggidettaglio di una sorteggitestata a partire dai valori di SorteggitestataFilter
     * 
     * @param command
     * @param istanzeSorteggiate
     * @param istanzeTotali
     * @return
     */
    private List<SorteggioDettaglioPerInserimentoDTO> toSorteggioDettaglioPerInserimentoDTO(List<Integer> istanzeSorteggiate,
	    List<Integer> codiciIstanzaTotali) {

	List<SorteggioDettaglioPerInserimentoDTO> dettaglio = new ArrayList<SorteggioDettaglioPerInserimentoDTO>();
	for (Integer codiceIstanza : codiciIstanzaTotali) {
	    SorteggioDettaglioPerInserimentoDTO dto = new SorteggioDettaglioPerInserimentoDTO();
	    dto.setCodiceIstanza(codiceIstanza);
	    dto.setSorteggiata(istanzeSorteggiate.contains(codiceIstanza));
	    dto.setFlagInterventoObbligatorio(Boolean.FALSE);
	    dettaglio.add(dto);
	}
	return dettaglio;
    }

    private List<Integer> getGruppoIstanze(List<Integer> istanzeRimaste, Integer gruppiIstanze) {

	// §§§BEGIN§§§
	List<Integer> gruppo = new ArrayList<Integer>();
	// itero dall'ultima istanza alla prima (poiché eventualmente devo scartare le ultime istanze)
	for (int i = (istanzeRimaste.size() - 1); i >= (istanzeRimaste.size() - gruppiIstanze) && (istanzeRimaste.size() - gruppiIstanze) >= 0; i--) {
	    gruppo.add(istanzeRimaste.get(i));
	}
	return gruppo;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    /**
     * Effettua il sorteggio delle istanze fornite in input in base ai parametri percentuale e arrotondamento
     * 
     * @param istanzes
     * @param percentuale
     * @param arrotondamento
     * @return
     */
    private List<Integer> sorteggiaIstanze(List<Integer> istanzes, Integer percentuale, Integer arrotondamento) {

	// §§§BEGIN§§§
	List<Integer> istanzeSorteggiate = new ArrayList<Integer>();
	Integer range = istanzes.size();
	Integer numeroIstanzeDaEstrarre = 0;
	// Difetto
	if (arrotondamento == 0) {
	    Double numeroIstanzeDaEstrarreDouble = Math.floor((range.doubleValue() / 100) * percentuale.doubleValue());
	    numeroIstanzeDaEstrarre = numeroIstanzeDaEstrarreDouble.intValue();
	}
	// Eccesso
	if (arrotondamento == 1) {
	    Double numeroIstanzeDaEstrarreDouble = Math.ceil((range.doubleValue() / 100) * percentuale.doubleValue());
	    numeroIstanzeDaEstrarre = numeroIstanzeDaEstrarreDouble.intValue();
	}
	// Troncamento
	if (arrotondamento == 2) {
	    Long numeroIstanzeDaEstrarreLong = Math.round((range.doubleValue() / 100) * percentuale.doubleValue());
	    numeroIstanzeDaEstrarre = numeroIstanzeDaEstrarreLong.intValue();
	}
	List<Integer> list = new ArrayList<Integer>();
	Random randomGenerator = new Random();
	int numeroEstratto;
	while (list.size() < numeroIstanzeDaEstrarre) {
	    do {
		numeroEstratto = randomGenerator.nextInt(range);
	    } while (list.contains(numeroEstratto));
	    list.add(numeroEstratto);
	    istanzeSorteggiate.add(istanzes.get(numeroEstratto));
	}
	return istanzeSorteggiate;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void dataIntegrationFilter(FiltriSorteggioBean filter) {

	if (filter == null) {
	    throw new IllegalArgumentException("Non sono stati specificati i parametri di sorteggio");
	}
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (filter.getCodiceAlgoritmo() != null && filter.getCodiceAlgoritmo().equals(WebConstants.SORTEGGIO_STANDARD)) {
	    if (filter.getPercentuale() == null) {
		ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", filter.getClass(), "percentuale", filter.getPercentuale(), filter));
		this.throwValidationMessages(ivs);
	    }
	    if (filter.getPercentuale() > 100 || filter.getPercentuale() < 0) {
		ivs.add(new InvalidValue("service_error.deve_essere_tra_zero_cento", filter.getClass(), "percentuale", filter.getPercentuale(),
			filter));
		this.throwValidationMessages(ivs);
	    }
	    if (Boolean.TRUE.equals(filter.getSalvaSorteggio()) && StringUtils.isBlank(filter.getDescrizioneSorteggio())) {
		ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", filter.getClass(), "descrizione", filter.getDescrizioneSorteggio(),
			filter));
		this.throwValidationMessages(ivs);
	    }
	    fixMergeFilterProperties(filter);
	} else if (filter.getCodiceAlgoritmo() != null && filter.getCodiceAlgoritmo().equals(WebConstants.SORTEGGIO_REGIONE_EMILIA_ROMAGNA)) {
	    // Aggiungere ulteriori controlli
	    if (Boolean.TRUE.equals(filter.getSalvaSorteggio()) && StringUtils.isBlank(filter.getDescrizioneSorteggio())) {
		ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", filter.getClass(), "descrizione", filter.getDescrizioneSorteggio(),
			filter));
		this.throwValidationMessages(ivs);
	    }
	    if (filter.getIntervalloCampionamentoDal() == null) {
		ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", filter.getClass(), "intervalloCampionamentoDal",
			filter.getIntervalloCampionamentoDal(), filter));
		this.throwValidationMessages(ivs);
	    }
	    if (filter.getIntervalloCampionamentoAl() == null) {
		ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", filter.getClass(), "intervalloCampionamentoAl",
			filter.getIntervalloCampionamentoAl(), filter));
		this.throwValidationMessages(ivs);
	    }
	    if (filter.getScCodiciInterventoProc() == null || filter.getScCodiciInterventoProc().isEmpty()) {
		ivs.add(new InvalidValue("service_error.selezionare_un_intervento", null, null, null, null));
		this.throwValidationMessages(ivs);
	    }
	    if (filter.getPercentuale() == null) {
		ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", filter.getClass(), "percentuale", filter.getPercentuale(), filter));
		this.throwValidationMessages(ivs);
	    }
	    if (filter.getPercentuale() > 100 || filter.getPercentuale() < 0) {
		ivs.add(new InvalidValue("service_error.deve_essere_tra_zero_cento", filter.getClass(), "percentuale", filter.getPercentuale(),
			filter));
		this.throwValidationMessages(ivs);
	    }
	}
	// §§§END§§§
    }

    protected void fixMergeFilterProperties(FiltriSorteggioBean filter) {

    }

    /**
     * Restituisce la lista dei sorteggitestatainfo di un sorteggitestata a partire dai valori di SorteggitestataFilter
     * e dal codiceAlgoritmo
     * 
     * @param entity
     * @param filter
     * @param codiceAlgoritmo
     * @return
     */
    private Set<Sorteggitestatainfo> findSorteggitestatainfos(Integer idTestata, FiltriSorteggioBean filtri) {

	// §§§BEGIN§§§
	// Ordine
	Integer ordine = 0;
	// Creazione lista info
	Set<Sorteggitestatainfo> sorteggitestatainfoList = new HashSet<Sorteggitestatainfo>();
	// Comune
	if (!StringUtils.isBlank(filtri.getCodiceComune())) {
	    Comuni c = comuniService.findById(filtri.getCodiceComune());
	    if (c != null) {
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.combocomuni", c.getComune(), ordine));
		ordine++;
	    }
	}
	//Archivio pratiche
	if (filtri.getIdTipiArchivioIstanza() != null) {
	    Tipiarchivioistanze archivio = this.tipiarchivioistanzeService.findById(new PkId(filtri.getIdTipiArchivioIstanza()));
	    if (archivio != null) {
		sorteggitestatainfoList
			.add(findSorteggitestatainfo(idTestata, "label.codice_archivio_pratiche", archivio.getId().getCodice().toString(), ordine));
		ordine++;
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.archivio_pratiche", archivio.getArchivio(), ordine));
		ordine++;
	    }
	}
	// Tipo Procedura
	if (filtri.getIdProcedura() != null) {
	    int i = 1;
	    for (Integer idProc : filtri.getIdProcedura()) {
		Tipiprocedure procedura = this.tipiprocedureService.findById(new PkId(idProc));
		if (procedura != null) {
		    sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.codice_tipo_procedura" + i, "label.codice_tipo_procedura",
			    procedura.getId().getCodice().toString(), ordine));
		    ordine++;
		    sorteggitestatainfoList.add(
			    findSorteggitestatainfo(idTestata, "label.tipo_procedura" + i, "label.tipo_procedura", procedura.getProcedura(), ordine));
		    ordine++;
		    i++;
		}
	    }
	}
	// Stati istanza
	if (filtri.getCodiciStatoIstanza() != null && !filtri.getCodiciStatoIstanza().isEmpty()) {
	    int i = 1;
	    for (String codiceStato : filtri.getCodiciStatoIstanza()) {
		StatiistanzaId id = new StatiistanzaId(codiceStato);
		Statiistanza statiistanza = this.statiistanzaService.findById(id);
		if (statiistanza != null) {
		    sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.codice_stato" + i, "label.codice_stato",
			    statiistanza.getId().getCodicestato(), ordine));
		    ordine++;
		    sorteggitestatainfoList
			    .add(findSorteggitestatainfo(idTestata, "label.stato" + i, "label.stato", statiistanza.getStato(), ordine));
		    ordine++;
		    i++;
		}
	    }
	}
	// Data Movimento Dal
	if (filtri.getDataDal() != null) {
	    sorteggitestatainfoList
		    .add(findSorteggitestatainfo(idTestata, "label.data_movimento_dal", Utilities.formatDate(filtri.getDataDal(), false), ordine));
	    ordine++;
	}
	// Data Movimento Al
	if (filtri.getDataAl() != null) {
	    sorteggitestatainfoList
		    .add(findSorteggitestatainfo(idTestata, "label.data_movimento_al", Utilities.formatDate(filtri.getDataAl(), false), ordine));
	    ordine++;
	}
	// Tipo movimento
	if (!StringUtils.isBlank(filtri.getTipoMovimento())) {
	    Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(filtri.getTipoMovimento()));
	    if (tipimovimento != null) {
		sorteggitestatainfoList
			.add(findSorteggitestatainfo(idTestata, "label.codice_tipomovimento", tipimovimento.getId().getTipomovimento(), ordine));
		ordine++;
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.tipomovimento",
			tipimovimento.getMovimento() + " (" + tipimovimento.getId().getTipomovimento() + ")", ordine));
		ordine++;
		// Tipologia ricerca movimento
		String tipoRicMov = "";
		if (filtri.getTipoRicercaMovimento() == 0) {
		    tipoRicMov = getMessageFromBundle("sorteggitestata.label.ricerca_mov_effettuati", null);
		}
		if (filtri.getTipoRicercaMovimento() == 1) {
		    tipoRicMov = getMessageFromBundle("sorteggitestata.label.ricerca_mov_da_effettuare", null);
		}
		if (filtri.getTipoRicercaMovimento() == 2) {
		    tipoRicMov = getMessageFromBundle("sorteggitestata.label.ricerca_tutti", null);
		}
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.tipo_ricerca_mov", tipoRicMov, ordine));
		ordine++;
		// Esito movimento
		String esitoMov = "";
		if (filtri.getEsito() == null) {
		    esitoMov = getMessageFromBundle("label.qualsiasi", null);
		}
		if (Boolean.FALSE.equals(filtri.getEsito())) {
		    esitoMov = getMessageFromBundle("label.negativo", null);
		}
		if (Boolean.TRUE.equals(filtri.getEsito())) {
		    esitoMov = getMessageFromBundle("label.positivo", null);
		}
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.tipologia_esito", esitoMov, ordine));
		ordine++;
	    }
	}
	// Percentuale	
	if (filtri.getPercentuale() != null) {
	    sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.percentuale", filtri.getPercentuale().toString(), ordine));
	    ordine++;
	}
	// Estrazione a gruppi
	if (filtri.getGruppiDiIstanze() != null) {
	    sorteggitestatainfoList
		    .add(findSorteggitestatainfo(idTestata, "label.estrazione_gruppi", filtri.getGruppiDiIstanze().toString(), ordine));
	    ordine++;
	}
	// Arrotondamento
	String arrotondamento = "";
	if (filtri.getArrotondamento() == 0) {
	    arrotondamento = getMessageFromBundle("label.approssimazione_difetto", null);
	}
	if (filtri.getArrotondamento() == 1) {
	    arrotondamento = getMessageFromBundle("label.approssimazione_eccesso", null);
	}
	if (filtri.getArrotondamento() == 2) {
	    arrotondamento = getMessageFromBundle("label.arrotondamento_intero", null);
	}
	if (filtri.getCodiceAlgoritmo() == 1) {
	    arrotondamento = getMessageFromBundle("label.approssimazione_eccesso", null);
	}
	sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.arrotondamento", arrotondamento, ordine));
	ordine++;
	// Esclude
	String esclude = "0";
	if (BooleanUtils.isTrue(filtri.getEscludiIstanzeSorteggiate())) {
	    esclude = "1";
	}
	sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.esclude", esclude, ordine));
	ordine++;
	// Se spuntato esclude effettuo anche questi controlli
	if (BooleanUtils.isTrue(filtri.getEscludiIstanzeSorteggiate())) {
	    // Lista estrazioni 
	    if (filtri.getIdSorteggiDaEscludere() != null && !filtri.getIdSorteggiDaEscludere().isEmpty()) {
		sorteggitestatainfoList
			.add(findSorteggitestatainfo(idTestata, "label.lista_estrazioni", filtri.getIdSorteggiDaEscludere().toString(), ordine));
		ordine++;
	    }
	    // Lista Categorie
	    if (filtri.getIdCategorieDaEscludere() != null && !filtri.getIdCategorieDaEscludere().isEmpty()) {
		sorteggitestatainfoList
			.add(findSorteggitestatainfo(idTestata, "label.lista_categorie", filtri.getIdCategorieDaEscludere().toString(), ordine));
		ordine++;
	    }
	}
	// Algoritmo Emilia Romagna
	// Codice Algoritmo
	if (filtri.getCodiceAlgoritmo() == 1) {
	    sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.codice_algoritmo", filtri.getCodiceAlgoritmo().toString(), ordine));
	    ordine++;
	}
	// Data Campionamento Dal
	if (filtri.getIntervalloCampionamentoDal() != null) {
	    sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.data_presentazione_dal",
		    Utilities.formatDate(filtri.getIntervalloCampionamentoDal(), false), ordine));
	    ordine++;
	}
	// Data Campionamento Al
	if (filtri.getIntervalloCampionamentoAl() != null) {
	    sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.data_presentazione_al",
		    Utilities.formatDate(filtri.getIntervalloCampionamentoAl(), false), ordine));
	    ordine++;
	}
	// Interventi Multipli	
	if (filtri.getScCodiciInterventoProc() != null && !filtri.getScCodiciInterventoProc().isEmpty()) {
	    int i = 1;
	    String software = StringUtils.isBlank(filtri.getSoftware()) ? ORMHelper.getSoftware() : filtri.getSoftware();
	    for (String scCodice : filtri.getScCodiciInterventoProc()) {
		Alberoproc alberoproc = this.alberoprocService.findBySoftwareAndScCodice(software, scCodice);
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.codice_tipo_intervento" + i, "label.codice_tipo_intervento",
			alberoproc.getId().getCodice().toString(), ordine));
		ordine++;
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.tipologia_intervento" + i, "label.tipologia_intervento",
			alberoproc.getVwAlberoproc().getScDescrizione(), ordine));
		ordine++;
		i++;
	    }
	}
	//nature endo
	if (filtri.getIdNatureEndo() != null && !filtri.getIdNatureEndo().isEmpty()) {
	    int i = 1;
	    for (Integer codice : filtri.getIdNatureEndo()) {
		NaturaendoId id = new NaturaendoId(codice);
		Naturaendo naturaendo = naturaendoService.findById(id);
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.natura" + i, "label.natura", naturaendo.getNatura(), ordine));
		ordine++;
		i++;
	    }
	}
	if (filtri.getIdEndoprocedimenti() != null && !filtri.getIdEndoprocedimenti().isEmpty()) {
	    int i = 1;
	    for (Integer id : filtri.getIdEndoprocedimenti()) {
		Inventarioprocedimenti endo = this.inventarioprocedimentiService.findById(new PkId(id));
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.codice_inventario" + i, "label.codice_inventario",
			String.valueOf(endo.getId().getCodice()), ordine));
		sorteggitestatainfoList.add(findSorteggitestatainfo(idTestata, "label.procedimento" + i, "label.procedimento",
			String.valueOf(endo.getProcedimento()), ordine));
		ordine++;
		i++;
	    }
	}
	return sorteggitestatainfoList;
    }

    private Sorteggitestatainfo findSorteggitestatainfo(Integer idTestata, String etichetta, String valore, Integer ordine) {

	return findSorteggitestatainfo(idTestata, null, etichetta, valore, ordine);
    }

    /**
     * Restituisce la testatainfo creata a partire dalla testata, dall'etichetta, dal valore e dall'ordine
     * 
     * @param entity
     * @param label
     * @param valore
     * @param ordine
     * @return Sorteggitestatainfo
     */
    private Sorteggitestatainfo findSorteggitestatainfo(Integer idTestata, String nome, String etichetta, String valore, Integer ordine) {

	// §§§BEGIN§§§
	Sorteggitestatainfo sorteggitestatainfo = new Sorteggitestatainfo();
	SorteggitestatainfoId sorteggitestatainfoId = new SorteggitestatainfoId(idTestata, etichetta);
	if (nome != null) {
	    sorteggitestatainfoId.setNome(nome);
	}
	sorteggitestatainfo.setId(sorteggitestatainfoId);
	sorteggitestatainfo.setEtichetta(getMessageFromBundle(etichetta, null));
	sorteggitestatainfo.setValore(valore);
	sorteggitestatainfo.setOrdine(ordine);
	return sorteggitestatainfo;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void dataIntegration(Sorteggitestata entity) {

	// §§§BEGIN§§§
	if (entity == null) {
	    throw new RuntimeException("Il parametro sorteggitestata è nullo");
	}
	fixMergeEntityProperties(entity);
	// §§§END§§§
    }

    @Override
    protected void fixMergeEntityProperties(Sorteggitestata entity) {

	// §§§BEGIN§§§
	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Comuni comune = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(comune);
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetto(), PkId.class, "id.codice");
	entity.setOggetto(oggetto);
	SorteggiCategorie sorteggiCategorie = sorteggiCategorieService.bindDomainObject(entity.getCategoria(), PkId.class, "id.codice");
	entity.setCategoria(sorteggiCategorie);
	// §§§END§§§
    }

    @Override
    public void salvaMovimento(Integer idTestataSorteggio, Movimenti movimento, TipologiaStatoSorteggioIstanzaEnum tipologiaStatoSorteggioIstanza) {

	if (!isInsertMovimentoAllowed(movimento, tipologiaStatoSorteggioIstanza)) {
	    return;
	}
	Tipimovimento tm = tipiMovimentoService.findById(new TipimovimentoId(movimento.getTipomovimento().getId().getTipomovimento()));
	if (tm != null) {
	    Sorteggitestatainfo sorteggitestatainfo = findSorteggitestatainfo(idTestataSorteggio, "label.movimento_generato",
		    tm.getDescrizioneEstesa(), 99);
	    sorteggitestatainfoService.insert(sorteggitestatainfo);
	}
	//Recupero la testata del sorteggio
	Sorteggitestata sorteggitestata = sorteggitestataDAO.findById(new PkId(idTestataSorteggio));
	//Recupero i dettagli del sorteggio
	List<Sorteggidettaglio> dettagli = this.sorteggidettaglioService.findBySorteggitestata(new Sorteggitestata(idTestataSorteggio));
	for (Sorteggidettaglio dettaglio : dettagli) {
	    //MODIFICA LUCA
	    dettaglio = sorteggidettaglioService.findById(dettaglio.getId());
	    sorteggidettaglioDAO.refreshEntity(dettaglio);
	    //END MODIFICA LUCA
	    switch (tipologiaStatoSorteggioIstanza) {
	    case NON_SORTEGGIATE:
		if (Boolean.FALSE.equals(dettaglio.getSorteggiata())) {
		    Istanze i = istanzeService.findById(new PkId(dettaglio.getIstanza().getId().getCodice()));
		    insertMovimentoSuPraticaInSorteggitestata(dettaglio, movimento);
		    log.debug(DEBUG_SALVAMOVIMENTO_MSG, new Object[] { sorteggitestata.getStDescrizione(), i.getNumeroistanza(),
			    TipologiaStatoSorteggioIstanzaEnum.NON_SORTEGGIATE, tm.getDescrizioneEstesa(), });
		}
		break;
	    case SORTEGGIATE:
		if (Boolean.TRUE.equals(dettaglio.getSorteggiata())) {
		    Istanze i = istanzeService.findById(new PkId(dettaglio.getIstanza().getId().getCodice()));
		    insertMovimentoSuPraticaInSorteggitestata(dettaglio, movimento);
		    log.debug(DEBUG_SALVAMOVIMENTO_MSG, new Object[] { sorteggitestata.getStDescrizione(), i.getNumeroistanza(),
			    TipologiaStatoSorteggioIstanzaEnum.SORTEGGIATE, tm.getDescrizioneEstesa(), });
		}
		break;
	    case TUTTE:
		Istanze i = istanzeService.findById(new PkId(dettaglio.getIstanza().getId().getCodice()));
		insertMovimentoSuPraticaInSorteggitestata(dettaglio, movimento);
		log.debug(DEBUG_SALVAMOVIMENTO_MSG, new Object[] { sorteggitestata.getStDescrizione(), i.getNumeroistanza(),
			TipologiaStatoSorteggioIstanzaEnum.TUTTE, tm.getDescrizioneEstesa(), });
		break;
	    default:
		break;
	    }
	}
    }

    private void insertMovimentoSuPraticaInSorteggitestata(Sorteggidettaglio sorteggidettaglio, Movimenti movimento) {

	// Movimento
	Movimenti movimentoTemp = new Movimenti();
	movimentoTemp.setData(movimento.getData());
	movimentoTemp.setTipomovimento(movimento.getTipomovimento());
	movimentoTemp.setAmministrazioni(movimento.getAmministrazioni());
	movimentoTemp.setResponsabile(movimento.getResponsabile());
	movimentoTemp.setIstanza(sorteggidettaglio.getIstanza());
	movimentiService.insert(movimentoTemp); //non deve protocollare
	// Sorteggidettagliomovimento
	Sorteggidettagliomovimenti sorteggidettagliomovimenti = new Sorteggidettagliomovimenti();
	SorteggidettagliomovimentiId id = new SorteggidettagliomovimentiId(sorteggidettaglio.getId().getCodice(), movimentoTemp.getId().getCodice());
	sorteggidettagliomovimenti.setId(id);
	sorteggidettagliomovimenti.setMovimenti(movimentoTemp);
	sorteggidettagliomovimenti.setSorteggidettaglio(sorteggidettaglio);
	sorteggidettagliomovimentiService.insert(sorteggidettagliomovimenti);
    }

    private boolean isInsertMovimentoAllowed(Movimenti movimento, TipologiaStatoSorteggioIstanzaEnum tipologiaStatoSorteggioIstanza) {

	boolean delete = true;
	// §§§BEGIN§§§
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (movimento.getData() == null) {
	    ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", movimento.getClass(), "movimento.data", movimento.getData(), movimento));
	}
	if (movimento.getTipomovimento().getId().getTipomovimento() == null || movimento.getTipomovimento().getId().getTipomovimento().equals("")) {
	    ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", SorteggitestataCommand.class, "movimento.tipomovimento",
		    movimento.getTipomovimento(), movimento));
	}
	if (tipologiaStatoSorteggioIstanza == null) {
	    ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", SorteggitestataCommand.class, "tipologiaStatoSorteggioIstanza",
		    movimento.getTipomovimento(), movimento));
	}
	if (!ivs.isEmpty()) {
	    this.throwValidationMessages(ivs);
	}
	// §§§END§§§
	return delete;
    }

    @Override
    public void aggiornaCategoria(Sorteggitestata sorteggitestata) {

	if (sorteggitestata == null) {
	    throw new RuntimeException("Il sorteggio è nullo");
	}
	if (validateEntity(sorteggitestata)) {
	    sorteggitestataDAO.update(sorteggitestata);
	}
    }
}
