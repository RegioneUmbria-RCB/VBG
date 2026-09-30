package it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrdineEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocMovimenti;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzecollegate;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzecollegateHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.attivita.IAttivitaService;
import it.gruppoinit.pal.gp.core.features.buslightyear.interfaces.IEventPublisher;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.eventi.EventoIstanzaCollegata;
import it.gruppoinit.pal.gp.core.features.istanze.istanzecollegate.eventi.EventoIstanzaScollegata;
import it.gruppoinit.pal.gp.core.features.movimenti.eventi.EventoErroreInCancellazioneMovimentodaIstanzaCollegata;
import it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate.AlberoprocMovimentiService;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MovimentiService;
import it.gruppoinit.pal.gp.core.service.exception.OperazioniAutomaticheException;
import it.gruppoinit.pal.gp.core.service.helper.FlashMessages;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;
import it.gruppoinit.pal.gp.core.service.rules.IstanzeBusinessRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

/**
 * 
 * @author gianpaolot
 */
@Service
public class IstanzecollegateServiceImpl extends BaseServiceImpl<Istanzecollegate, PkId> implements IstanzecollegateService {

    private static final Logger log = LoggerFactory.getLogger(IstanzecollegateServiceImpl.class);
    private IstanzecollegateDAO istanzecollegateDAO;
    private IstanzeService istanzeService;
    private IAttivitaService iAttivitaService;
    private AlberoprocMovimentiService alberoprocMovimentiService;
    private MovimentiService movimentiService;
    private IEventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(IEventPublisher eventPublisher) {

	this.eventPublisher = eventPublisher;
    }

    @Autowired
    public void setMovimentiService(MovimentiService movimentiService) {

	this.movimentiService = movimentiService;
    }

    @Autowired
    public void setAlberoprocMovimentiService(AlberoprocMovimentiService alberoprocMovimentiService) {

	this.alberoprocMovimentiService = alberoprocMovimentiService;
    }

    @Autowired
    public void setIstanzecollegateDAO(IstanzecollegateDAO istanzecollegateDAO) {

	this.istanzecollegateDAO = istanzecollegateDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setiAttivitaService(IAttivitaService iAttivitaService) {

	this.iAttivitaService = iAttivitaService;
    }

    @Override
    protected Class<Istanzecollegate> getEntityClass() {

	return Istanzecollegate.class;
    }

    @Override
    public List<Istanzecollegate> findAll(Integer firstResult, Integer maxResult) {

	return istanzecollegateDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzecollegate entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    istanzecollegateDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public Istanzecollegate findById(PkId id) {

	// §§§BEGIN§§§
	return istanzecollegateDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void update(Istanzecollegate entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    istanzecollegateDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    public void delete(Istanzecollegate entity) {

	// §§§BEGIN§§§
	if (isDeleteAllowed(entity)) {
	    Integer codiceIstanzaOrigine = null;
	    if (entity.getIstanza() != null && entity.getIstanza().getId() != null && entity.getIstanza().getId().getCodice() != null) {
		codiceIstanzaOrigine = entity.getIstanza().getId().getCodice();
	    }
	    Integer codiceIstanzaDestinazione = null;
	    if (entity.getIstanzaDacollegare() != null && entity.getIstanzaDacollegare().getId() != null
		    && entity.getIstanzaDacollegare().getId().getCodice() != null) {
		codiceIstanzaDestinazione = entity.getIstanzaDacollegare().getId().getCodice();
	    }
	    istanzecollegateDAO.delete(entity);
	    if (codiceIstanzaOrigine != null && codiceIstanzaDestinazione != null) {
		EventoIstanzaScollegata evento = new EventoIstanzaScollegata(codiceIstanzaOrigine, codiceIstanzaDestinazione);
		this.eventPublisher.publish(evento);
	    }
	}
	// §§§END§§§
    }

    @Override
    public List<Istanzecollegate> findByFilterTable(FilterTable filterTable) {

	// §§§BEGIN§§§
	return istanzecollegateDAO.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Integer maxOrdineByProgressivo(Integer progressivo) {

	// §§§BEGIN§§§
	return istanzecollegateDAO.maxOrdineByProgressivo(progressivo);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Integer maxProgressivo() {

	// §§§BEGIN§§§
	return istanzecollegateDAO.maxProgressivo();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Istanzecollegate> findIstanzeCollegateByIstanza(Istanze istanze) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("id.codice", istanze.getId().getCodice(), "istanza", Integer.class));
	filterTable.addRestriction(criterio);
	filterTable.addOrder(FilterUtils.orderAsc("data", "istanza"));
	List<Istanzecollegate> listIstanzeCollegateByIstanza = this.findByFilterTable(filterTable);
	return listIstanzeCollegateByIstanza;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Boolean isExistIstanzeCollegateByIstanza(Integer codiceIstanze) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("istanzaId", codiceIstanze, Integer.class));
	filterTable.addRestriction(criterio);
	return istanzecollegateDAO.existsRecords(filterTable);
    }

    @Override
    public boolean isExistIstanzeCollegateByIstanza(String[] codiciIstanze) {

	for (String cod : codiciIstanze) {
	    if (this.isExistIstanzeCollegateByIstanza(Integer.parseInt(cod)).equals(true)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public void insertCollegamento(Istanze istanzeDacollegare, Istanze istanzaPrincipale, boolean rilanciaEccezioneSeNonCollegabile) {

	insertCollegamento(istanzeDacollegare, istanzaPrincipale, rilanciaEccezioneSeNonCollegabile, true);
    }

    @Override
    public void insertCollegamento(Istanze istanzeDacollegare, Istanze istanzaPrincipale, boolean rilanciaEccezioneSeNonCollegabile,
	    boolean isCollegaAttivita) {

	// §§§BEGIN§§§
	// Ricerco se esistono già record nella tabella istanzecollegate con codice istanza passato (codice dell'istanza ricercata 
	//che si vuole collegare), ed eventualmente ritorno la lista.
	// Questo mi pemette di controlare se l'istanza che vogliamo collegare già appartiene ad altre catene di collegamento
	List<Istanzecollegate> listIstanzeCollegateByIstanza = this.findIstanzeCollegateByIstanza(istanzeDacollegare);
	// Se la lista è non vuota andrò ad aggiungere questo nuovo collegameto alle catene preesistenti senza crearne una nuova
	// (non genero nessun nuovo progressivo)
	if (!listIstanzeCollegateByIstanza.isEmpty()) {
	    //ciclo tutte le istanze trovate e per ognuna provo a fare un nuovo collegamento
	    // è possibile che un collegamento sia già presente quindi non verrà duplicato.
	    Istanzecollegate istanzecollegateDaInserire = null;
	    for (Istanzecollegate istanzecollegate : listIstanzeCollegateByIstanza) {
		// Creo un nuovo oggetto Istanzecollegate con i seguenti campi:
		// 1- campo istanza: istanza principale (quella di partenza)
		// 2- campo istanzaDaCollegare: istanza da collegare (quella ricercata)
		// 3- campo ordine: l'intero successiso al max ordinie per la catena di collegamento in esame (oggetti IstanzaCollegate con 
		//                  stesso progressivo)
		// 4- campo pregressivo : lo stesso dell'istanzacollegata che si sta esaminando.
		istanzecollegateDaInserire = new Istanzecollegate();
		istanzecollegateDaInserire.setIstanza(istanzaPrincipale);
		istanzecollegateDaInserire.setIstanzaDacollegare(istanzeDacollegare);
		Integer ordine = this.maxOrdineByProgressivo(istanzecollegate.getProgressivo()) + 1;
		istanzecollegateDaInserire.setOrdine(ordine);
		istanzecollegateDaInserire.setProgressivo(istanzecollegate.getProgressivo());
		// Controllo se l'istanza collegata creata può essere inserita.
		// 1- Non sto colleganto la stessa istanza
		// 2- le due istanze non sono già collegate anche se non direttamente tra loro(appartengano già alla stessa catena)
		if (isCollegamentoAllowed(istanzeDacollegare, istanzaPrincipale, istanzecollegate, false, rilanciaEccezioneSeNonCollegabile)) {
		    this.insert(istanzecollegateDaInserire);
		    pubblicaeventoIstanzaCollegata(istanzaPrincipale, istanzeDacollegare);
		}
	    }
	} else {// non ho trovato nessuna catena già esistente, ne creo una nuova 
		// inserisco due record nella tabella ISTANZECOLLEGATE 
		//con ordine 1 e 2
		//con progressivo il max + 1 (calcolato per idcomune)
	    if (isCollegamentoAllowed(istanzeDacollegare, istanzaPrincipale, null, false, rilanciaEccezioneSeNonCollegabile)) {
		createAndInsertNewCatena(istanzeDacollegare, istanzaPrincipale);
	    }
	}
	if (isCollegaAttivita) {
	    log.debug("insertCollegamento# isCollegaAttivita = {}", isCollegaAttivita);
	    // verifico se una delle due istanze ha un attività collegata, nel caso associo l'attività anche all' istanza.
	    collegatAttivitaAdIstanza(istanzeDacollegare, istanzaPrincipale);
	} else {
	    log.debug("insertCollegamento# isCollegaAttivita = {}", isCollegaAttivita);
	}
	// §§§END§§§
    }

    @Override
    public void insertCollegamentoMultiplo(String lista_istanze_da_collegare, Istanze istanze, boolean rilanciaEccezioneSeNonCollegabile) {

	String[] codiciIstanza = StringUtils.split(lista_istanze_da_collegare, ",");
	for (int i = 0; i < codiciIstanza.length; i++) {
	    log.debug("insertCollegamentoMultiplo# Collego l'istanza con codice {}", codiciIstanza[i]);
	    Istanze istanzaDaCollegare = istanzeService.findById(new PkId(Integer.parseInt(codiciIstanza[i])));
	    this.insertCollegamento(istanzaDaCollegare, istanze, true);
	}
    }

    @Override
    public void insertCollegamentoPrecedenteMultiplo(String lista_istanze_da_collegare, Istanze istanze, boolean b) {

	String[] codiciIstanza = StringUtils.split(lista_istanze_da_collegare, ",");
	for (int i = 0; i < codiciIstanza.length; i++) {
	    log.debug("insertCollegamentoPrecedenteMultiplo# Collego l'istanza con codice {}", codiciIstanza[i]);
	    Istanze istanzaDaCollegare = istanzeService.findById(new PkId(Integer.parseInt(codiciIstanza[i])));
	    this.insertCollegamentoPrecedente(istanzaDaCollegare, istanze, true);
	}
    }

    /**
     * <pre>
     *     1. A associata ad un attività , B associata ad un attività : collego solo le istanze
     *     2. A associata ad un attività , B non associata ad un attività : collego solo le istanze e associo a B l'attività
     *     3. A non associata ad un attività , B associata ad un attività : collego solo le istanze e associo a A l'attività
     *     4. A non associata ad un attività , B non associata ad un attività : collego solo le istanze : collego solo le istanze
     * </pre>
     */
    private void collegatAttivitaAdIstanza(Istanze istanzeDacollegare, Istanze istanzaPrincipale) {

	IAttivita attivitaA = istanzaPrincipale.getAttivita();
	IAttivita attivitaB = istanzeDacollegare.getAttivita();
	if ((attivitaA != null && attivitaB != null) || (attivitaA == null && attivitaB == null)) {
	    log.debug("collegatAttivitaAdIstanza# Entrambe le istanze non hanno o hanno un attività collegata, non procedo con l'operazione");
	} else {
	    // L'istanza di origine è associata ad un attivtà, associo l'attivtà all'istanza da collegare
	    if (attivitaA != null) {
		attivitaA = this.iAttivitaService.findById(new PkId(attivitaA.getId().getCodice()));
		istanzecollegateDAO.refreshEntity(attivitaA);
		log.debug("collegatAttivitaAdIstanza# Collego l'attività {} [{}] all' istanza {}[{}]", new Object[] { attivitaA.getDenominazione(),
			attivitaA.getId().getCodice(), istanzeDacollegare.getNumeroistanza(), istanzeDacollegare.getId().getCodice() });
		this.iAttivitaService.collegaIstanza(attivitaA, istanzeDacollegare);
	    }
	    // L'istanza da collegare è associata ad un attivtà, associo l'attivtà all'istanza di origine 
	    if (attivitaB != null) {
		attivitaB = this.iAttivitaService.findById(new PkId(attivitaB.getId().getCodice()));
		istanzecollegateDAO.refreshEntity(attivitaB);
		log.debug("collegatAttivitaAdIstanza# Collego l'attività {} [{}] all' istanza {}[{}]", new Object[] { attivitaB.getDenominazione(),
			attivitaB.getId().getCodice(), istanzaPrincipale.getNumeroistanza(), istanzaPrincipale.getId().getCodice() });
		this.iAttivitaService.collegaIstanza(attivitaB, istanzaPrincipale);
	    }
	}
    }

    @Override
    public void insertCollegamentoPrecedente(Istanze istanzeDacollegare, Istanze istanze, boolean rilanciaEccezioneSeNonCollegabile) {

	//Devo controllare che l'istanza che andiamo a colleare non appartenga ad altre catene
	if (isCollegamentoAllowed(istanzeDacollegare, istanze, null, true, rilanciaEccezioneSeNonCollegabile)) {
	    // L'istanza da associare non è contenuta in nessun' altra catena.
	    // Controlliamo se la se l'istanza a cui staimo facendo il collegamanto appartenga a uno o più catene
	    //(il fatto che non appartenga a più catene non viene controllato in quanto già da web non è possibile 
	    // eseguire questo tipo di collegamento).
	    if (isContenutaInUnaCatena(istanzeDacollegare)) {
		List<Istanzecollegate> listIstanzeCollegateByIstanza = this.findIstanzeCollegateByIstanza(istanze);
		//CASO 1:l'istanza di partenza ha un solo collegamento (appartiene a una sola catena)
		if (listIstanzeCollegateByIstanza.size() == 1) {
		    //Recupero il record di ISTANZECOLLEGATE che ha codiceistanza il codice dell'istanza passata e 
		    //codiceistanzacollegata =null, 
		    Istanzecollegate istanzecollegateUnica = listIstanzeCollegateByIstanza.get(0);
		    // Recupero il progressivo 
		    Integer progressivo = istanzecollegateUnica.getProgressivo();
		    //Aggiorno il record mettendo il codice dell'istanza passata sul campo codiceistanzacollegata e il codice dell'istanza da collegare
		    // sul campo codice istanza.
		    istanzecollegateUnica.setIstanzaDacollegare(istanzeDacollegare);
		    istanzecollegateUnica.setIstanza(istanze);
		    istanzecollegateUnica.setOrdine(2);
		    this.update(istanzecollegateUnica);
		    pubblicaeventoIstanzaCollegata(istanze, istanzeDacollegare);
		    // Inserisco un nuovo record in ISTANZECOLLEGATE con codiceistaza uguale al codice dell'istanza 
		    //       da collegare e codiceistanzacollegata=NULL.
		    Istanzecollegate istanzecollegateNew = new Istanzecollegate();
		    istanzecollegateNew.setIstanza(istanzeDacollegare);
		    istanzecollegateNew.setIstanzaDacollegare(null);
		    istanzecollegateNew.setOrdine(1);
		    istanzecollegateNew.setProgressivo(progressivo);
		    this.insert(istanzecollegateNew);
		} else {
		    // 2- CASO 2: l'istanza di partenza appartiene a due o più catene. 
		    //record 1: codiceistanza uguale al codice dell'istanza da collegare e codiceistanza da collegare uguale a NULL
		    //record 2: codiceistanza uguale al codice dell'istanza passata e codiceistanzacollegata quello dell'istanza da collegare  
		    createAndInsertNewCatena(istanzeDacollegare, istanze);
		}
	    }
	}
    }

    private void createAndInsertNewCatena(Istanze istanzaPrecedente, Istanze istanzaSuccessiva) {

	// Creo una nuova catena (max progressivo + 1)
	Integer progressivo = (this.maxProgressivo() != null ? this.maxProgressivo() + 1 : 1);
	Istanzecollegate istanzecollegateDaInserire1 = new Istanzecollegate();
	istanzecollegateDaInserire1.setIstanza(istanzaSuccessiva);
	istanzecollegateDaInserire1.setIstanzaDacollegare(istanzaPrecedente);
	istanzecollegateDaInserire1.setOrdine(2);
	istanzecollegateDaInserire1.setProgressivo(progressivo);
	this.insert(istanzecollegateDaInserire1);
	pubblicaeventoIstanzaCollegata(istanzaSuccessiva, istanzaPrecedente);
	Istanzecollegate istanzecollegateDaInserire2 = new Istanzecollegate();
	istanzecollegateDaInserire2.setIstanza(istanzaPrecedente);
	istanzecollegateDaInserire2.setIstanzaDacollegare(null);
	istanzecollegateDaInserire2.setOrdine(1);
	istanzecollegateDaInserire2.setProgressivo(progressivo);
	this.insert(istanzecollegateDaInserire2);
    }

    private boolean isContenutaInUnaCatena(Istanze istanze) {

	boolean isContenuta = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// Recupero le cantene a cui è associata l'istanza a cui vogliamo fare il collegamento
	List<Istanzecollegate> listIstanzeCollegateByIstanza = this.findIstanzeCollegateByIstanza(istanze);
	if (listIstanzeCollegateByIstanza != null && !listIstanzeCollegateByIstanza.isEmpty()) {
	    _ivs.add(new InvalidValue("service_error.istanza_non_contenuta_in_altre_catene", null, null, null, null));
	    isContenuta = false;
	}
	if (!isContenuta) {
	    this.throwValidationMessages(_ivs);
	}
	return isContenuta;
    }

    /**
     * metodo per la procedura di import
     */
    @Override
    public void clear() {

	istanzecollegateDAO.clear();
    }

    /**
     * <pre>
     * Metodo che controlla se il collegamento che stiamo facendo è possibile
     *  
     * 	 1- Controlla che stiamo collegando un istanza a se stessa 
     * 	 2- Controlla che il collegamento che stiamo tentando di fare non esiste già
     * 	 3- Nel caso di collegamento come precedente, deve controllare che l'istanza che si sta collegando, non appartenga ad altre catene
     *      (in questo caso non permettiamo il collegamanto automatico)
     * 
     * &#64;param istanzeDacollegare
     * &#64;param istanzaPrincipale
     * &#64;param istanzacollegate
     * &#64;return
     * 
     * </pre>
     */
    private boolean isCollegamentoAllowed(Istanze istanzeDacollegare, Istanze istanzaPrincipale, Istanzecollegate istanzacollegate,
	    boolean isCollegamentoPrecedente, boolean rilanciaEccezioneSeNonCollegabile) {

	boolean isCollegabile = true;
	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// Controllo che non sto collegando la stessa istanza istanza
	if (EntityUtils.equals(istanzeDacollegare, istanzaPrincipale)) {
	    _ivs.add(new InvalidValue("service_error.collegamento_stessa_istanza", null, null, null, null));
	    isCollegabile = false;
	}
	// Controllo che l'istanza che si vogliono collegare già non facciano parte di una catena
	// Se istanze istanzacollegate!=null sono nel caso del collegamento all'interno di una catena esistente
	if (istanzacollegate != null) {
	    // Verifico se l'istanza di partenza (istanza principale) appartine già alla catena dell'istanza che gli si vuole collegare.
	    // Ricerco nella tabella istanze collegate un record con campo istanza uguale all'istanza principale e il campo  progressivo 
	    // uguale a quello dell'istanza che si vuole collegare (l'istanza ricercata). 
	    // Se la lista ritornata è diversa dal vuoto significa che le due istanze già appartengono alla stessa catena.
	    // Blocco l'inserimento e genero un messaggio di alert.
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	    FilterRestriction criterio = new FilterRestriction();
	    criterio.addFilterField(FilterUtils.equals("progressivo", istanzacollegate.getProgressivo(), Integer.class));
	    criterio.addFilterField(FilterUtils.equals("istanza", istanzaPrincipale, Istanze.class));
	    filterTable.addRestriction(criterio);
	    List<Istanzecollegate> list = istanzecollegateDAO.findByFilterTable(filterTable);
	    if (!list.isEmpty()) {
		List<String> msgs = FlashMessages.getWarnings();
		msgs.add(getMessageFromBundle("alert.istanza_no_collegata",
			null) + " " + istanzeDacollegare.getNumeroistanza() + " " + getMessageFromBundle("alert.istanza_gia_collegate", null));
		FlashMessages.setWarnings(msgs);
		return false;
	    }
	}
	if (isCollegamentoPrecedente) {
	    // Questo mi pemette di controlare se l'istanza che vogliamo collegare già appartiene ad altre catene di collegamento
	    List<Istanzecollegate> listIstanzeCollegateByIstanza = this.findIstanzeCollegateByIstanza(istanzeDacollegare);
	    if (listIstanzeCollegateByIstanza != null && !listIstanzeCollegateByIstanza.isEmpty()) {
		String msgError = getMessageFromBundle("service_error.collegamento_precendente_impossibile_istanza_appartiene_gia_collegata",
			new Object[] { istanzeDacollegare.getNumeroistanza(), istanzaPrincipale.getNumeroistanza() });
		_ivs.add(new InvalidValue(msgError, null, null, null, null));
		isCollegabile = false;
	    }
	}
	if (!isCollegabile) {
	    if (rilanciaEccezioneSeNonCollegabile) {
		this.throwValidationMessages(_ivs);
	    }
	}
	// §§§END§§§
	return isCollegabile;
    }

    @Override
    public void deleteCollegamento(Istanzecollegate objToDelete) {

	// §§§BEGIN§§§
	// controllo quanti record esistono con il progressivo uguale all 'istanza collegata che si vuole cancellare
	List<Istanzecollegate> listIstanzeCollegateConStessoProgressivo = this.findByProgressivo(objToDelete.getProgressivo());
	Istanze istanze = istanzeService.bindDomainObject(objToDelete.getIstanza(), PkId.class, "id.codice");
	// controllo quanti sono presenti.
	// se sono due allora cancello il record che vogliamo scollegare e anche l'unico record rimanente 
	//(non ha senso avere un solo record con un progressivo)
	if (listIstanzeCollegateConStessoProgressivo.size() <= 2) {
	    for (Istanzecollegate istanzecollegate : listIstanzeCollegateConStessoProgressivo) {
		this.delete(istanzecollegate);
	    }
	}
	// se non sono uguali a due saranno sicuramente >2 per la logica che crea i collegametnti(altrimenti c'è un anomalia sul DB)
	// cancello solo quello il record passato
	else {
	    // possono esiste più di una istanza con stesso codice istanza ,stesso progressivo e stesso ordine 
	    //(dovranno essere cancellate tutte)
	    List<Istanzecollegate> istanzecollegateDaEliminare = this.findByIstanzaAndProgressivo(objToDelete.getIstanza(),
		    objToDelete.getProgressivo());
	    for (Istanzecollegate istanzecollegate : istanzecollegateDaEliminare) {
		listIstanzeCollegateConStessoProgressivo.remove(istanzecollegate);
		this.delete(istanzecollegate);
	    }
	    // in questo caso si devono ricalcolare tutti gli ordini
	    // recupero l'ordine del record eliminato
	    Integer ordineElininato = objToDelete.getOrdine();
	    // controllo tutta la lista dei record di istanze collegate 
	    for (Istanzecollegate istanzecollegate : listIstanzeCollegateConStessoProgressivo) {
		// se l'ordine del record corrente è maggiore di quello eliminato allora lo decremento di uno
		// Il primo record che modificheremo sarà sicuramente quello successivo a quello eliminato
		// questo perchè l'ordine dei record è creato automaticamente all'inserimento di un nuovo record 
		// secondo la logica maxOrdine+1.
		if (istanzecollegate.getOrdine() > ordineElininato) {
		    Integer ordineCorrente = istanzecollegate.getOrdine();
		    istanzecollegate.setOrdine(ordineCorrente - 1);
		    this.update(istanzecollegate);
		}
	    }
	}
	// 3. elimino i riferimenti alla colonna codiceistanzacollegata
	List<Istanzecollegate> istanzecollegateDelProgressivo = this.findByProgressivo(objToDelete.getProgressivo());
	for (Istanzecollegate istanzecollegate : istanzecollegateDelProgressivo) {
	    Istanze istanzaDacollegare = istanzecollegate.getIstanzaDacollegare();
	    if (istanzaDacollegare != null) {
		Integer codiceIstanza = istanzaDacollegare.getId().getCodice();
		if (codiceIstanza.intValue() == istanze.getId().getCodice().intValue()) {
		    istanzecollegate.setIstanzaDacollegare(null);
		    this.update(istanzecollegate);
		}
	    }
	}
	// §§§END§§§
    }

    @Override
    public List<Istanzecollegate> findByProgressivo(Integer progressivo) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("progressivo", progressivo, Integer.class));
	filterTable.addRestriction(criterio);
	return this.findByFilterTable(filterTable);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void updateOrdine(Istanzecollegate istanzecollegate, OrdineEnum upOrDown) {

	// §§§BEGIN§§§
	Integer ordineXFiltro = null;
	Integer codiceistanzaRigaDaSpostare = istanzecollegate.getIstanza().getId().getCodice();
	Istanzecollegate istanzecollegateOrdineInferiore = null;
	Integer codiceistanzaRigaDaSpostarePiu1 = null;
	int maxOrder = maxOrdineByProgressivo(istanzecollegate.getProgressivo());
	ordineXFiltro = istanzecollegate.getOrdine() + 1;
	if (maxOrder >= ordineXFiltro) {
	    istanzecollegateOrdineInferiore = this.findByProgressivoAndOrdine(istanzecollegate.getProgressivo(), ordineXFiltro);
	    codiceistanzaRigaDaSpostarePiu1 = istanzecollegateOrdineInferiore.getIstanza().getId().getCodice();
	}
	switch (upOrDown) {
	case UP:
	    /*
	     UP:
	        riga da spostare
	        codiceistanza--> codiceistanza riga da spostare -1
	        codiceistanzacollegata --> codiceistanza riga da spostare
	        riga da spostare -1
	        codiceistanza --> codiceistanza riga da spostare
	        riga da spostare +1
	        codiceistanzacollegata --> codiceistanza riga da spostare-1
	     */
	    // recupero il record con stesso progressivo ,ma di ordine inferiore
	    ordineXFiltro = istanzecollegate.getOrdine() - 1;
	    //check se ordineXFiltro e' 1
	    if (ordineXFiltro > 0) {
		//recupero il record istanza precedente a partire dal progressivo e numero ordine
		// riga da spostare -1
		Istanzecollegate istanzecollegateOrdineSuperiore = this.findByProgressivoAndOrdine(istanzecollegate.getProgressivo(), ordineXFiltro);
		Integer codiceistanzaRigaDaSpostareMeno1 = istanzecollegateOrdineSuperiore.getIstanza().getId().getCodice();
		istanzecollegateOrdineSuperiore.getIstanza().getId().setCodice(codiceistanzaRigaDaSpostare);
		this.update(istanzecollegateOrdineSuperiore);
		if (istanzecollegateOrdineInferiore != null) {
		    //riga da spostare +1
		    istanzecollegateOrdineInferiore.setIstanzaDacollegareId(codiceistanzaRigaDaSpostareMeno1);
		    this.update(istanzecollegateOrdineInferiore);
		}
		//istanza corrente (riga da spostare)
		istanzecollegate.getIstanza().getId().setCodice(codiceistanzaRigaDaSpostareMeno1);
		istanzecollegate.setIstanzaDacollegareId(codiceistanzaRigaDaSpostare);
		this.update(istanzecollegate);
	    }
	    break;
	case DOWN:
	    /*
	    DOWN:
	    riga da spostare (dove si effettua il click sulla freccia)
	    codiceistanza--> codiceistanza riga da spostare +1
	        riga da spostare +1
	        codiceistanza--> codiceistanza riga da spostare
	        codiceistanzacollegata --> codiceistanza riga da spostare+1
	        riga da spostare +2
	        codiceistanzacollegata --> codiceistanza riga da spostare
	    */
	    //istanza successiva alla successiva (riga da spostare +2 )
	    ordineXFiltro = istanzecollegate.getOrdine() + 2;
	    if (maxOrder >= ordineXFiltro) {
		Istanzecollegate istanzecollegateOrdineInferiore2 = this.findByProgressivoAndOrdine(istanzecollegate.getProgressivo(), ordineXFiltro);
		if (istanzecollegateOrdineInferiore2 != null && istanzecollegateOrdineInferiore2.getId() != null) {
		    istanzecollegateOrdineInferiore2.setIstanzaDacollegareId(codiceistanzaRigaDaSpostare);
		    this.update(istanzecollegateOrdineInferiore2);
		}
	    }
	    if (istanzecollegateOrdineInferiore != null) {
		//istanza successiva (riga da spostare +1 )
		istanzecollegateOrdineInferiore.getIstanza().getId().setCodice(codiceistanzaRigaDaSpostare);
		istanzecollegateOrdineInferiore.setIstanzaDacollegareId(codiceistanzaRigaDaSpostarePiu1);
		this.update(istanzecollegateOrdineInferiore);
	    }
	    //istanza corrente
	    istanzecollegate.getIstanza().getId().setCodice(codiceistanzaRigaDaSpostarePiu1);
	    this.update(istanzecollegate);
	    break;
	default:
	    throw new IllegalArgumentException("istanzecollegateservice.updateOrdine: OrdineEnum non può essere null ");
	}
	// §§§END§§§
    }

    @Override
    public Istanzecollegate findByProgressivoAndOrdine(Integer progressivo, Integer ordine) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("progressivo", progressivo, Integer.class));
	criterio.addFilterField(FilterUtils.equals("ordine", ordine, Integer.class));
	filterTable.addRestriction(criterio);
	List<Istanzecollegate> list = this.findByFilterTable(filterTable);
	return list.get(0);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Istanzecollegate> findByIstanzaAndProgressivo(Istanze istanze, Integer progressivo) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("progressivo", progressivo, Integer.class));
	criterio.addFilterField(FilterUtils.equals("istanza", istanze, Istanze.class));
	filterTable.addRestriction(criterio);
	List<Istanzecollegate> list = this.findByFilterTable(filterTable);
	return list;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Istanzecollegate findByIstanzaAndProgressivoAndOrdine(Istanze istanza, Integer progressivo, Integer ordine) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("istanza", istanza, Istanze.class));
	criterio.addFilterField(FilterUtils.equals("progressivo", progressivo, Integer.class));
	criterio.addFilterField(FilterUtils.equals("ordine", ordine, Integer.class));
	filterTable.addRestriction(criterio);
	List<Istanzecollegate> list = this.findByFilterTable(filterTable);
	if (list.size() > 1) {
	    throw new RuntimeException("Anomalia nel DB: Non è possibile che esitano due record con stesso progressivo e ordine ");
	} else {
	    return list.get(0);
	}
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Istanzecollegate> findIstanzeCollegateByIstanzaCollegata(Istanze istanze) {

	// §§§BEGIN§§§
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("id.codice", istanze.getId().getCodice(), "istanzaDacollegare", Integer.class));
	filterTable.addRestriction(criterio);
	filterTable.addOrder(FilterUtils.orderAsc("data", "istanzaDacollegare"));
	List<Istanzecollegate> listIstanzeCollegateByIstanza = this.findByFilterTable(filterTable);
	return listIstanzeCollegateByIstanza;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void deleteCollegamenti(Istanze istanze) {

	// §§§BEGIN§§§
	List<Istanzecollegate> istanzecollegates = this.findIstanzeCollegateByIstanza(istanze);
	for (Istanzecollegate istanzecollegate : istanzecollegates) {
	    this.deleteCollegamento(istanzecollegate);
	}
	// §§§END§§§
    }

    @Override
    public IstanzecollegateHelper getSchemaPrecedentiAndSuccessive(Istanze istanza) {

	// §§§BEGIN§§§
	return istanzecollegateDAO.getSchemaPrecedentiAndSuccessive(istanza);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public int countIstanzeCollegateByIstanza(Integer codiceIstanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.setAndOrRestriction(AndOrRestriction.OR);
	criterio.addFilterField(FilterUtils.equals("istanzaId", codiceIstanza, Integer.class));
	criterio.addFilterField(FilterUtils.equals("istanzaDacollegareId", codiceIstanza, Integer.class));
	filterTable.addRestriction(criterio);
	return istanzecollegateDAO.countRecord(filterTable);
    }

    private void pubblicaeventoIstanzaCollegata(Istanze istanzaPrincipale, Istanze istanzaDaCollegare) {

	if (istanzaPrincipale != null && istanzaDaCollegare != null) {
	    EventoIstanzaCollegata evento = new EventoIstanzaCollegata(istanzaPrincipale.getId().getCodice(), istanzaDaCollegare.getId().getCodice());
	    eventPublisher.publish(evento);
	}
    }

    @Override
    public void insertMovimentoInIstanzaCollegata(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione) {

	if (codiceIstanzaOrigine == null || codiceIstanzaDestinazione == null) {
	    throw new IllegalArgumentException("I parametri codiceIstanzaOrigine e codiceIstanzaDestinazione non possono essere nulli");
	}
	Istanze origine = istanzeService.findById(new PkId(codiceIstanzaOrigine));
	Istanze destinazione = istanzeService.findById(new PkId(codiceIstanzaDestinazione));
	if (origine == null) {
	    throw new IllegalArgumentException("Istanza con codice " + codiceIstanzaOrigine + " non trovata ");
	}
	if (destinazione == null) {
	    throw new IllegalArgumentException("Istanza con codice " + destinazione + " non trovata ");
	}
	List<AlberoprocMovimenti> conf = alberoprocMovimentiService
		.findConfigurazioneAttivaByAlberoprocId(origine.getAlberoproc().getId().getCodice());
	if (!conf.isEmpty()) {
	    for (AlberoprocMovimenti apm : conf) {
		Movimenti movimenti = new Movimenti();
		movimenti.setIstanza(destinazione);
		movimenti.setTipomovimento(apm.getTipimovimento());
		movimenti.setAmministrazioni(apm.getAmministrazioni());
		movimenti.setData(origine.getData()); // ci metto la data della pratica?? La pratica potrebbe essere chiusa e rilanciare l'errore
		IstanzeBusinessRules rules = (IstanzeBusinessRules) SigeproBusinessRules.getClassRules(IstanzeBusinessRules.class);
		boolean isInserimentoDaStc = rules.getCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name());
		rules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), true);
		try {
		    movimentiService.insert(movimenti);
		} finally {
		    rules.setCustomRule(IstanzeBusinessRules.CustomRuleEnum.inserimentoDaStc.name(), isInserimentoDaStc);
		}
	    }
	}
    }

    @Override
    public void deleteMovimentoInIstanzaCollegata(Integer codiceIstanzaOrigine, Integer codiceIstanzaDestinazione)
	    throws OperazioniAutomaticheException {

	if (codiceIstanzaOrigine == null || codiceIstanzaDestinazione == null) {
	    throw new IllegalArgumentException("I parametri codiceIstanzaOrigine e codiceIstanzaDestinazione non possono essere nulli");
	}
	Istanze origine = istanzeService.findById(new PkId(codiceIstanzaOrigine));
	Istanze destinazione = istanzeService.findById(new PkId(codiceIstanzaDestinazione));
	if (origine == null) {
	    throw new IllegalArgumentException("Istanza con codice " + codiceIstanzaOrigine + " non trovata ");
	}
	if (destinazione == null) {
	    throw new IllegalArgumentException("Istanza con codice " + destinazione + " non trovata ");
	}
	List<AlberoprocMovimenti> conf = alberoprocMovimentiService
		.findConfigurazioneAttivaByAlberoprocId(origine.getAlberoproc().getId().getCodice());
	if (!conf.isEmpty()) {
	    for (AlberoprocMovimenti apm : conf) {
		Movimenti mov = movimentiService.findMovimentiByTipoMovimentoAndDataAndAmministrazione(codiceIstanzaDestinazione,
			apm.getTipimovimento().getId().getTipomovimento(), origine.getData(), apm.getAmministrazioni().getId().getCodice());
		if (mov != null) {
		    Integer codiceMovimento = mov.getId().getCodice();
		    try {
			movimentiService.delete(mov);
		    } catch (Exception e) {
			log.error("deleteMovimentoInIstanzaCollegata {}-{}-{}",
				new Object[] { codiceIstanzaOrigine, codiceIstanzaDestinazione, codiceMovimento, e });
			String message = "Non è stato possibile cancellare il movimento " +
				mov +
				" dell'istanza " +
				destinazione.toString() +
				" a causa di " +
				e.getMessage();
			this.eventPublisher.publish(new EventoErroreInCancellazioneMovimentodaIstanzaCollegata(codiceIstanzaOrigine,
				codiceIstanzaDestinazione, codiceMovimento, e.getMessage()));
			throw new OperazioniAutomaticheException(message, e);
		    }
		}
	    }
	}
    }

    @Override
    public List<IstanzecollegateHelper> findIstanzecollegateByIstanzaPerVisualizzazione(Integer codiceIstanza) {

	return istanzecollegateDAO.findIstanzecollegateByIstanzaPerVisualizzazione(codiceIstanza);
    }
}
