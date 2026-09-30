package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.hibernate.validator.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.CommissioniedilizieRDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CommedilizieTipopareri;
import it.gruppoinit.pal.gp.core.domain.CommedilizieVotazioni;
import it.gruppoinit.pal.gp.core.domain.CommedrDocist;
import it.gruppoinit.pal.gp.core.domain.CommedrIstall;
import it.gruppoinit.pal.gp.core.domain.CommedrMovAll;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;
import it.gruppoinit.pal.gp.core.exception.InvalidConfigurationException;
import it.gruppoinit.pal.gp.core.features.commissioni.allegati.ICommissioniAllegatiDAO;
import it.gruppoinit.pal.gp.core.features.commissioni.appello.ICommissioniAppelloDAO;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.ICommissioniAuditingService;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioCommissioneOrdinata;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioEsitoVotazioneEliminato;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioEsitoVotazioneInserito;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioEsitoVotazioneModificato;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioIstanzaCancellataDaCommissione;
import it.gruppoinit.pal.gp.core.features.commissioni.auditing.messaggi.MessaggioIstanzeAggiunteInCommissione;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.models.RigaCommissioneModel;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.ICommissioniDocumentiPraticheDAO;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CommedilizieTipopareriService;
import it.gruppoinit.pal.gp.core.service.CommedilizieVotazioniService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieRService;
import it.gruppoinit.pal.gp.core.service.CommissioniedilizieTService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.MovimentiNoSecurityService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.TipiMovimentoService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

/**
 * 
 * @author gianpaolot
 */
@Service
public class CommissioniedilizieRServiceImpl extends BaseServiceImpl<CommissioniedilizieR, PkId> implements CommissioniedilizieRService {

    private CommissioniedilizieRDAO commissioniedilizierDAO;
    private CommissioniedilizieTService commissioniedilizieTService;
    private MovimentiService movimentiService;
    private MovimentiNoSecurityService movimentiNoSecurityService;
    private CommedilizieTipopareriService commedilizieTipopareriService;
    private CommedilizieVotazioniService commedilizieVotazioniService;
    private TipiMovimentoService tipiMovimentoService;
    private UserSecurityService userSecurityService;
    @Autowired
    private DocumentiistanzaService documentiistanzaService;
    @Autowired
    private MovimentiallegatiService movimentiallegatiService;
    @Autowired
    private IstanzeallegatiService istanzeallegatiService;
    @Autowired
    private ICommissioniAllegatiDAO commissioniAllegatiDAO;
    @Autowired
    private ICommissioniDocumentiPraticheDAO commissioniDocumentiPraticheDAO;
    @Autowired
    private ICommissioniAppelloDAO appelloDAO;
    @Autowired
    private ICommissioniAuditingService auditingService;

    @Autowired
    public void setMovimentiNoSecurityService(MovimentiNoSecurityService movimentiNoSecurityService) {

	this.movimentiNoSecurityService = movimentiNoSecurityService;
    }

    @Autowired
    public void setCommissioniedilizieRDAO(CommissioniedilizieRDAO commissioniedilizierDAO) {

	this.commissioniedilizierDAO = commissioniedilizierDAO;
    }

    @Autowired
    public void setCommissioniedilizieTService(CommissioniedilizieTService commissioniedilizieTService) {

	this.commissioniedilizieTService = commissioniedilizieTService;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setCommedilizieTipopareriService(CommedilizieTipopareriService commedilizieTipopareriService) {

	this.commedilizieTipopareriService = commedilizieTipopareriService;
    }

    @Autowired
    public void setCommedilizieVotazioniService(CommedilizieVotazioniService commedilizieVotazioniService) {

	this.commedilizieVotazioniService = commedilizieVotazioniService;
    }

    @Autowired
    public void setTipiMovimentoService(TipiMovimentoService tipiMovimentoService) {

	this.tipiMovimentoService = tipiMovimentoService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Override
    protected Class<CommissioniedilizieR> getEntityClass() {

	return CommissioniedilizieR.class;
    }

    @Override
    public List<CommissioniedilizieR> findAll(Integer firstResult, Integer maxResult) {

	return commissioniedilizierDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CommissioniedilizieR entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    commissioniedilizierDAO.insert(entity);
	    childDataInsert(entity);
	}
    }

    @Override
    public CommissioniedilizieR findById(PkId id) {

	return commissioniedilizierDAO.findById(id);
    }

    @Override
    public void update(CommissioniedilizieR entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity)) {
	    commissioniedilizierDAO.update(entity);
	    childDataUpdate(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(CommissioniedilizieR entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    commissioniedilizierDAO.delete(entity);
	    // cancello se esiste il movimento di rientro che è stato generato dall'esito della conferenza
	    if (entity.getMovimentoRientro() != null && entity.getMovimentoRientro().getId().getCodice() != null) {
		Integer codiceMovimentoRientro = entity.getMovimentoRientro().getId().getCodice();
		Movimenti movimentoRientro = movimentiService.findById(new PkId(codiceMovimentoRientro));
		movimentiService.delete(movimentoRientro);
	    }
	}
	this.auditingService.log(entity.getCommissioniedilizieT().getId().getCodice(), new MessaggioIstanzaCancellataDaCommissione(
		this.getResponsabile(), entity.getCommissioniedilizieT().getId().getCodice(), entity.getMovimento().getIstanza().getNumeroistanza()));
	// §§§END§§§
    }

    @Override
    public List<CommissioniedilizieR> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return commissioniedilizierDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Integer maxOrdineByCommissioniedilizieT(CommissioniedilizieT commissioniedilizieT) {

	// §§§BEGIN§§§
	return commissioniedilizierDAO.maxOrdineByCommissioniedilizieT(commissioniedilizieT);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insertMultiploCommissioniedilizieR(List<String> codiciMovimenti, CommissioniedilizieT commissioniedilizieT) {

	// §§§BEGIN§§§
	Integer ordine = this.maxOrdineByCommissioniedilizieT(commissioniedilizieT);
	List<String> numeriIstanze = new ArrayList<String>();
	ordine++;
	CommissioniedilizieR commissioniedilizieR = null;
	for (String codice : codiciMovimenti) {
	    commissioniedilizieR = new CommissioniedilizieR();
	    commissioniedilizieR.setCommissioniedilizieT(commissioniedilizieT);
	    Movimenti movimento = movimentiNoSecurityService.findById(new PkId(Integer.parseInt(codice)));
	    commissioniedilizieR.setMovimento(movimento);
	    commissioniedilizieR.setOrdine(ordine);
	    this.insert(commissioniedilizieR);
	    numeriIstanze.add(movimento.getIstanza().getNumeroistanza());
	    ordine++;
	}
	this.auditingService.log(commissioniedilizieT.getId().getCodice(),
		new MessaggioIstanzeAggiunteInCommissione(getResponsabile(), numeriIstanze));
	// §§§END§§§
    }

    @Override
    public void updateEsitoCommissioneediliziaR(CommissioniedilizieR commissioniedilizieR, List<CommedilizieVotazioni> commedilizieVotazionis,
	    String parere) {

	// §§§BEGIN§§§
	if (isUpdateEsitoCommissioneediliziaRAllowed(commissioniedilizieR, parere)) {
	    Movimenti rientro = null;
	    // esiste il movimento di ritorno l'unica cosa che posso aggiornare è il suo parere
	    Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	    if (commissioniedilizieR.getMovimentoRientro() != null && commissioniedilizieR.getMovimentoRientro().getId().getCodice() != null) {
		rientro = commissioniedilizieR.getMovimentoRientro();
		if (responsabile != null) {
		    rientro.setResponsabile(responsabile);
		}
		rientro.setParere(commissioniedilizieR.getMovimentoRientro().getParere());
		movimentiService.update(rientro);
		//TODO inserire log per esito votazione commissione
		this.auditingService.log(commissioniedilizieR.getCommissioniedilizieT().getId().getCodice(),
			new MessaggioEsitoVotazioneInserito(getResponsabile(), commissioniedilizieR.getMovimento().getIstanza().getNumeroistanza()));
	    } else { // se non è stato configurato il movimento di rientro allora svolgo tutta la logica di inserimento del movimento di rientro
		     // recupero tutte le informazioni da inserire nel movimento di rientro
		Movimenti movimento = movimentiService.bindDomainObject(commissioniedilizieR.getMovimento(), PkId.class, "id.codice");
		List<Movimenti> movimentis = movimentiService.findContromovimentidaEffettuare(movimento);
		String tipoMovConfigurato = commedilizieTipopareriService.findTipomovPerTipologiaParereECommissioniEdilizieR(
			commissioniedilizieR.getCommedilizieTipopareri().getId().getCodice(), commissioniedilizieR.getId().getCodice());
		if (StringUtils.isBlank(tipoMovConfigurato)) {
		    String errore = "Configurazione non valida. Non è stato configurato il tipo movimento per la tipologia di parere \"" +
				    commissioniedilizieR.getCommedilizieTipopareri().getDescrizione() + "(" +
				    commissioniedilizieR.getCommedilizieTipopareri().getId().getCodice() + ")\" e software TT o " +
				    movimento.getIstanza().getSoftware().getCodice();
		    throw new InvalidConfigurationException(errore);
		}
		for (Movimenti movimentoRientro : movimentis) {
		    // controllo se il tipo movimento del contro movimento è uguale a quello configurato per il tipo parere scelto
		    if (movimentoRientro.getTipomovimento().getId().getTipomovimento().equals(tipoMovConfigurato)) {
			rientro = movimentoRientro;
			break;
		    }
		}
		// non esiste il contro movimento cercato
		// ne creo uno nuovo 
		if (rientro == null) {
		    rientro = new Movimenti();
		    rientro.setIstanza(movimento.getIstanza());
		    Tipimovimento tipimovimento = tipiMovimentoService.findById(new TipimovimentoId(tipoMovConfigurato));
		    rientro.setTipomovimento(tipimovimento);
		}
		// recupero l'amministrazione legata alla tipologia della commissione
		// SETTO DATA, PARERE, ESITO
		CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService
			.findById(commissioniedilizieR.getCommissioniedilizieT().getId());
		if (commissioniedilizieT.getCommedilizieTipologie() != null
			&& commissioniedilizieT.getCommedilizieTipologie().getAmministrazione() != null) {
		    rientro.setAmministrazioni(commissioniedilizieT.getCommedilizieTipologie().getAmministrazione());
		}
		rientro.setData(gestisciDataOra(commissioniedilizieT.getData()));
		rientro.setEsito(commissioniedilizieR.getCommedilizieTipopareri().getEsito());
		rientro.setParere(parere);
		if (responsabile != null) {
		    rientro.setResponsabile(responsabile);
		}
		// il movimento esisteva , facciamo l'update
		if (rientro.getId() != null && rientro.getId().getCodice() != null) {
		    movimentiService.update(rientro);
		} else // il movimento è stato creato , facciamo l'insert
		{
		    movimentiService.insert(rientro);
		}
		//TODO inserire log per esito votazione commissione
		this.auditingService.log(commissioniedilizieR.getCommissioniedilizieT().getId().getCodice(), new MessaggioEsitoVotazioneModificato(
			getResponsabile(), commissioniedilizieR.getMovimento().getIstanza().getNumeroistanza()));
	    }
	    //Setto alla commissioni edilizie r il contro movimento creato o aggiornato come movimento di riento 
	    commissioniedilizieR.setMovimentoRientro(rientro);
	    commissioniedilizierDAO.update(commissioniedilizieR);
	    // inserimento  delle votazioni nella tabella COMMEDILIZIE_VOTAZIONI
	    // recupero tutte le commedilizievotazione associate alla commissione
	    for (CommedilizieVotazioni commedilizieVotazioni : commedilizieVotazionis) {
		commedilizieVotazioniService.update(commedilizieVotazioni);
	    }
	}
	// §§§END§§§
    }

    private Date gestisciDataOra(Date data) {

	Calendar dataCommissione = Calendar.getInstance();
	dataCommissione.setTime(data);
	Calendar c = Calendar.getInstance();
	c.set(Calendar.DATE, dataCommissione.get(Calendar.DATE));
	c.set(Calendar.MONTH, dataCommissione.get(Calendar.MONTH));
	c.set(Calendar.YEAR, dataCommissione.get(Calendar.YEAR));
	return c.getTime();
    }

    private boolean isUpdateEsitoCommissioneediliziaRAllowed(CommissioniedilizieR commissioniedilizieR, String parere) {

	// recupera l'annotation associata alla variabile parere di MOVIMENTI
	Length length = Utilities.getMethohAnnotation(Movimenti.class, Length.class, "getParere");
	boolean isAllowed = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (StringUtils.isBlank(parere) && StringUtils.isBlank(commissioniedilizieR.getMovimentoRientro().getParere())) {
	    _ivs.add(new InvalidValue("service_error.non_puo_essere_vuoto", commissioniedilizieR.getClass(), "movimento.parere", null,
		    commissioniedilizieR));
	}
	if ((StringUtils.isNotBlank(parere) && parere.length() > length.max())
		|| (StringUtils.isBlank(parere) && StringUtils.isNotBlank(commissioniedilizieR.getMovimentoRientro().getParere())
			&& commissioniedilizieR.getMovimentoRientro().getParere().length() > length.max())) {
	    _ivs.add(new InvalidValue("service_error.lunghezza_minore_di" + " " + length.max(), commissioniedilizieR.getClass(), "movimento.parere",
		    null, commissioniedilizieR));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return isAllowed;
    }

    @Override
    public void deleteEsitoCommissioneediliziaR(CommissioniedilizieR commissioniedilizieR) {

	// §§§BEGIN§§§
	PkId idMovimentoRientro = commissioniedilizieR.getMovimentoRientro().getId();
	// aggiorno i campi delle commissioni edilizie che devono essere messi a null
	commissioniedilizieR.setCommedilizieTipopareri(null);
	commissioniedilizieR.setMovimentoRientro(null);
	this.update(commissioniedilizieR);
	// vado acancellare il movimento di rientro che era stato generato con l'esito della commissione
	Movimenti movimentoRientro = movimentiService.findById(idMovimentoRientro);
	// devo annulla
	movimentoRientro.setCommedilizieForFKMovimentoRientro(null);
	movimentiService.delete(movimentoRientro);
	//	// cancello tutte le votazoni presenti per la discussione di quella commissione
	//	List<CommedilizieVotazioni> commedilizieVotazionis = commedilizieVotazioniService.findByCommissioniedilizieR(commissioniedilizieR);
	//	for (CommedilizieVotazioni commedilizieVotazioni : commedilizieVotazionis) {
	//	    commedilizieVotazioniService.delete(commedilizieVotazioni);
	//	}
	//TODO loggare cancellazione esito
	this.auditingService.log(commissioniedilizieR.getCommissioniedilizieT().getId().getCodice(),
		new MessaggioEsitoVotazioneEliminato(getResponsabile(), commissioniedilizieR.getMovimento().getIstanza().getNumeroistanza()));
	// §§§END§§§
    }

    @Override
    public void riordinaEsitoCommissioneediliziaR(Integer idCommissione, String numeroProtocollo,
	    List<RigaCommissioneModel> listaCommissioniRInDiscussione) {

	// Riordina la lista (bubble sort)
	// Questo ci garantisce che i record siano ordinati in modo asc secondo il campo ordine
	List<CommissioniedilizieR> list = new ArrayList<CommissioniedilizieR>();
	for (int i = 0; i < listaCommissioniRInDiscussione.size(); i++) {
	    CommissioniedilizieR comR = this.findById(new PkId(listaCommissioniRInDiscussione.get(i).getId()));
	    comR.setOrdine(listaCommissioniRInDiscussione.get(i).getOrdine());
	    list.add(comR);
	}
	boolean ordinati = false;
	for (int top = listaCommissioniRInDiscussione.size() - 1; top > 0 && !ordinati; top--) {
	    ordinati = true;
	    for (int i = 0; i < top; i++) {
		if (list.get(i).getOrdine() > list.get(i + 1).getOrdine()) {
		    ordinati = false;
		    CommissioniedilizieR temp = list.get(i);
		    list.set(i, list.get(i + 1));
		    list.set(i + 1, temp);
		}
	    }
	}
	// Prende la lista ordinata e elimina i buchi tra due ordini sequenziali
	//(Es. Lista ordinata :2,6,9,10,11 ----> Lista senza spazi: 1,2,3,4,5)
	list.get(0).setOrdine(1);
	int i = 0;
	while (i < list.size()) {
	    if (i != list.size() - 1) {
		int temp = list.get(i + 1).getOrdine() - list.get(i).getOrdine();
		if (temp != 1) {
		    int valore = list.get(i).getOrdine() + 1;
		    list.get(i + 1).setOrdine(valore);
		}
	    }
	    i++;
	}
	// inserisce la lista riordinata
	for (CommissioniedilizieR commissioniedilizieR : list) {
	    commissioniedilizierDAO.update(commissioniedilizieR);
	}
	auditingService.log(idCommissione, new MessaggioCommissioneOrdinata(idCommissione, numeroProtocollo, this.getResponsabile()));
    }

    private String getResponsabile() {

	return ((Responsabili) this.userSecurityService.getCurrentlyAuthenticatedUserDetails()).getResponsabile();
    }

    @Override
    public CommissioniedilizieR findByMovimentorientro(Movimenti movimento) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("movimentoRientro", movimento, Movimenti.class));
	filterTable.addRestriction(restriction);
	List<CommissioniedilizieR> list = this.findByFilterTable(filterTable);
	if (list != null && !list.isEmpty()) {
	    return list.get(0);
	}
	return null;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    private void childDataInsert(CommissioniedilizieR entity) {

	//	COMMEDR_DOCIST -> Documenti presenti su DOCUMENTIISTANZA
	Integer codiceIstanza = entity.getMovimento().getIstanza().getId().getCodice();
	List<Documentiistanza> docIstanza = documentiistanzaService.findByIstanza(codiceIstanza);
	for (Documentiistanza documentiistanza : docIstanza) {
	    CommedrDocist el = CommedrDocist.fromDocumentiIstanza(documentiistanza, entity);
	    commissioniAllegatiDAO.saveEntity(el);
	}
	//	COMMEDR_MOVALL -> Documenti presenti su MOVIMENTIALLEGATI
	List<Movimentiallegati> movAllegati = movimentiallegatiService.findByIstanza(codiceIstanza);
	for (Movimentiallegati movimentiallegati : movAllegati) {
	    CommedrMovAll el = CommedrMovAll.fromIstanzeallegati(movimentiallegati, entity);
	    commissioniAllegatiDAO.saveEntity(el);
	}
	//	COMMEDR_ISTALL -> Documenti presenti su ISTANZEALLEGATI
	List<Istanzeallegati> istanzeallegatis = istanzeallegatiService.findByIstanza(codiceIstanza);
	for (Istanzeallegati istanzeallegati : istanzeallegatis) {
	    CommedrIstall el = CommedrIstall.fromIstanzeallegati(istanzeallegati, entity);
	    commissioniAllegatiDAO.saveEntity(el);
	}
    }

    private void childDataUpdate(CommissioniedilizieR entity) {

    }

    @Override
    protected void childDelete(CommissioniedilizieR entity) {

	Set<CommedilizieVotazioni> commedilizieVotazionis = entity.getCommedilizieVotazionis();
	for (CommedilizieVotazioni commedilizieVotazioni : commedilizieVotazionis) {
	    commedilizieVotazioniService.delete(commedilizieVotazioni);
	}
	this.commissioniDocumentiPraticheDAO.eliminaDocumenti(entity.getId().getCodice());
	this.appelloDAO.deleteAppelloPraticheByIdRiga(entity.getId().getCodice());
    }

    private void dataIntegration(CommissioniedilizieR entity) {

	// §§§BEGIN§§§
	if (entity == null) {
	    throw new IllegalArgumentException("La commissione edilizia R passata è nulla");
	}
	if (entity.getFlagrinviata() == null) {
	    entity.setFlagrinviata(Boolean.valueOf(false));
	}
	fixMergeEntityProperties(entity);
	// §§§END§§§
    }

    protected void fixMergeEntityProperties(CommissioniedilizieR entity) {

	// §§§BEGIN§§§
	CommissioniedilizieT commissioniedilizieT = commissioniedilizieTService.bindDomainObject(entity.getCommissioniedilizieT(), PkId.class,
		"id.codice");
	entity.setCommissioniedilizieT(commissioniedilizieT);
	Movimenti movimenti = movimentiService.bindDomainObject(entity.getMovimento(), PkId.class, "id.codice");
	entity.setMovimento(movimenti);
	Movimenti movimentiRientro = movimentiService.bindDomainObject(entity.getMovimentoRientro(), PkId.class, "id.codice");
	entity.setMovimentoRientro(movimentiRientro);
	CommedilizieTipopareri commedilizieTipopareri = commedilizieTipopareriService.bindDomainObject(entity.getCommedilizieTipopareri(), PkId.class,
		"id.codice");
	entity.setCommedilizieTipopareri(commedilizieTipopareri);
	// §§§END§§§
    }
    //    protected boolean isDeleteAllowed(CommissioniedilizieR entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }

    @Override
    public List<CommissioniedilizieR> findIstanzeByCommissioneEdiliziaT(Integer codiceCommissioniEdilizieT) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("commissioniedilizieTId", codiceCommissioniEdilizieT, Integer.class));
	filterTable.addRestriction(restriction);
	filterTable.addOrder(FilterUtils.orderAsc("ordine"));
	return this.findByFilterTable(filterTable);
    }

    @Override
    public void ordinaEsitoCommissioneediliziaR(Integer idCommissione, String numeroProtocollo,
	    List<RigaCommissioneModel> listaCommissioniRInDiscussione) {

	List<CommissioniedilizieR> list = new ArrayList<CommissioniedilizieR>();
	for (int i = 0; i < listaCommissioniRInDiscussione.size(); i++) {
	    CommissioniedilizieR comR = this.findById(new PkId(listaCommissioniRInDiscussione.get(i).getId()));
	    comR.setOrdine(listaCommissioniRInDiscussione.get(i).getOrdine());
	    list.add(comR);
	}
	// inserisce la lista riordinata
	for (CommissioniedilizieR commissioniedilizieR : list) {
	    commissioniedilizierDAO.update(commissioniedilizieR);
	}
	auditingService.log(idCommissione, new MessaggioCommissioneOrdinata(idCommissione, numeroProtocollo, this.getResponsabile()));
    }

    @Override
    public Set<Integer> findCodiciCommissioniByMovimento(Integer codiceMovimento) {

	return commissioniedilizierDAO.findCodiciCommissioniByMovimento(codiceMovimento);
    }

    @Override
    public List<CommissioniedilizieT> findCommissioniByIstanza(Integer codiceIstanza) {

	return commissioniedilizierDAO.findCommissioniByIstanza(codiceIstanza);
    }

    @Override
    public int countCommissioniByIstanza(Integer codiceIstanza) {

	return commissioniedilizierDAO.countCommissioniByIstanza(codiceIstanza);
    }
}
