package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.GraduatorietDAO;
import it.gruppoinit.pal.gp.core.dao.Istanzedyn2datiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.GraduatorieDyn2DatiArrayComparator;
import it.gruppoinit.pal.gp.core.dao.helper.GraduatorieDyn2DatiComparator;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.BandiAlberoproc;
import it.gruppoinit.pal.gp.core.domain.Bandiinput;
import it.gruppoinit.pal.gp.core.domain.Campigraduatoria;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.GraduatorietCom;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Tipibando;
import it.gruppoinit.pal.gp.core.domain.Tipibandocampigraduat;
import it.gruppoinit.pal.gp.core.domain.Tipibandooutput;
import it.gruppoinit.pal.gp.core.domain.helper.EstremiAutDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.BandiAlberoprocService;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.GraduatorietComService;
import it.gruppoinit.pal.gp.core.service.GraduatorietService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import it.gruppoinit.pal.gp.core.utils.LoggerCancellazioni;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GraduatorietServiceImpl extends BaseServiceImpl<Graduatoriet, PkId> implements GraduatorietService {

    private static final Logger log = LoggerFactory.getLogger(GraduatorietServiceImpl.class);
    private GraduatorietDAO graduatorietDAO;
    private GraduatorietComService graduatorietComService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    private UserSecurityService userSecurityService;
    private BandiAlberoprocService bandiAlberoprocService;
    private Istanzedyn2datiDAO istanzedyn2datiDAO;
    private GraduatoriedService graduatoriedService;

    @Autowired
    public void setGraduatorietComService(GraduatorietComService graduatorietComService) {

	this.graduatorietComService = graduatorietComService;
    }

    @Autowired
    public void setGraduatorietDAO(GraduatorietDAO graduatorietDAO) {

	this.graduatorietDAO = graduatorietDAO;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

    @Autowired
    public void setBandiAlberoprocService(BandiAlberoprocService bandiAlberoprocService) {

	this.bandiAlberoprocService = bandiAlberoprocService;
    }

    @Autowired
    public void setIstanzedyn2datiDAO(Istanzedyn2datiDAO istanzedyn2datiDAO) {

	this.istanzedyn2datiDAO = istanzedyn2datiDAO;
    }

    @Autowired
    public void setGraduatoriedService(GraduatoriedService graduatoriedService) {

	this.graduatoriedService = graduatoriedService;
    }

    @Override
    protected Class<Graduatoriet> getEntityClass() {

	return Graduatoriet.class;
    }

    @Override
    public void delete(Graduatoriet entity) {

	// §§§BEGIN§§§
	childDelete(entity);
	graduatorietDAO.delete(entity);
	Responsabili userlogged = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String responsabile = (String) EntityUtils.getNestedProperty(userlogged, "responsabile");
	LoggerCancellazioni.logCancellazioneGraduatoriaBando(responsabile, entity.getDescrizione(), entity.getBandi().getDescrizione());
	// §§§END§§§
    }

    @Override
    public List<Graduatoriet> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Graduatoriet findById(PkId id) {

	// §§§BEGIN§§§
	return graduatorietDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(Graduatoriet entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    graduatorietDAO.insert(entity);
	}
	// §§§END§§§
    }

    @Override
    public void update(Graduatoriet entity) {

	// §§§BEGIN§§§
	if (validateEntity(entity)) {
	    graduatorietDAO.update(entity);
	}
	// §§§END§§§
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void compilaGraduatoria(Graduatoriet entity) {

	// §§§BEGIN§§§
	this.insert(entity);
	Alberoproc alberoproc = entity.getBandi().getAlberoproc();
	boolean isManifestazione = EntityUtils.getNestedProperty(alberoproc, "mercato.id.codice") != null;
	boolean isValidate = true;
	// Controllo se il tipo bando è multi intervento
	Tipibando tipibando = entity.getBandi().getTipibando();
	if (BooleanUtils.toBoolean(tipibando.getFlagMultiintervento())) {
	    log.debug("compilaGraduatoria# Il tipo bando configurato {} [cod: {}] è multi intervento", new Object[] { tipibando.getDescrizione(),
		    tipibando.getId().getCodice() });
	    this.compilaGraduatoriaMultiIntervento(entity);
	} else {
	    log.debug("compilaGraduatoria# Il tipo bando configurato {} [cod: {}] è standar (singolo intervento)",
		    new Object[] { tipibando.getDescrizione(), tipibando.getId().getCodice() });
	    if (entity.getTipigraduatoriet().getFlagValidaConfigurazione() && isManifestazione) {
		isValidate = validateIstanzeGraduatoria(entity);
	    }
	    if (isValidate) {
		graduatorietDAO.compilaGraduatoriaSingoloIntervento(entity);
	    }
	}
	// §§§END§§§
    }

    @Override
    public void compilaGraduatoriaMultiIntervento(Graduatoriet entity) {

	// §§§BEGIN§§§
	Set<Tipibandocampigraduat> tipibandocampigraduats = entity.getTipigraduatoriet().getTipibandocampigraduats();
	int numeroDiValoriOrdinamento = tipibandocampigraduats.size();
	Integer[] ordinamentoAscDesc = new Integer[numeroDiValoriOrdinamento];
	List dynList = creaListaIstanzaPerGraduatoria(entity, ordinamentoAscDesc);
	// PER NUOVE NODIFICHE DEVE IN CASO DI SELEZIONE MULTI INTERVENTO SENZA CONSIDERARE IL PESO
	// DOVRò APPLICARE L'ORDINAMENTO SU TUTTA LA LISTA COMPRESA DALLA SOMMA DI TUTTE LE PRATICHE
	// RECUPERATE PER OGNI TIPO INTERVENTO EFFETTUATO
	//	if (numeroDiValoriOrdinamento > 1) {
	//	    Collections.sort(dynList, new GraduatorieDyn2DatiArrayComparator(ordinamentoAscDesc));
	//	} else {
	//	    Collections.sort(dynList, new GraduatorieDyn2DatiComparator(ordinamentoAscDesc[0]));
	//	}
	graduatorietDAO.flush();
	graduatorietDAO.clear();
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * inserisco le informazioni recuperate al passo precedente in GRADUATORIED e CAMPIGRADUATORIA
	 */
	Campigraduatoria campigraduatoria = null;
	Graduatoried graduatoried = null;
	Set<Campigraduatoria> campigraduatorias = new HashSet<Campigraduatoria>(0);
	int pos = 1;/* indice utilizzato per salvare la posizione in graduatoria */
	Istanze istanze = null;
	for (Iterator iterator = dynList.iterator(); iterator.hasNext();) {
	    Object obj = iterator.next();
	    graduatoried = new Graduatoried();
	    graduatoried.setGraduatoriet(entity);
	    graduatoried.setPosizione(pos);
	    if (obj instanceof Object[]) {
		Object[] istanzedyn2dati = (Object[]) obj;
		for (int j = 0; j < istanzedyn2dati.length; j++) {
		    campigraduatoria = new Campigraduatoria();
		    campigraduatoria.setDyn2Campi(((Istanzedyn2dati) istanzedyn2dati[j]).getDyn2Campi());
		    campigraduatoria.setGraduatoried(graduatoried);
		    campigraduatoria.setValore(((Istanzedyn2dati) istanzedyn2dati[j]).getValore());
		    campigraduatoria.setOrdine(j);
		    campigraduatorias.add(campigraduatoria);
		    istanze = ((Istanzedyn2dati) istanzedyn2dati[j]).getIstanza();
		}
	    } else {
		Istanzedyn2dati istanzedyn2dati = (Istanzedyn2dati) obj;
		campigraduatoria = new Campigraduatoria();
		campigraduatoria.setDyn2Campi(istanzedyn2dati.getDyn2Campi());
		campigraduatoria.setGraduatoried(graduatoried);
		campigraduatoria.setValore(istanzedyn2dati.getValore());
		campigraduatoria.setOrdine(1);
		campigraduatorias.add(campigraduatoria);
		istanze = istanzedyn2dati.getIstanza();
	    }
	    graduatoried.setIstanza(istanze);
	    graduatoried.setCampigraduatorias(campigraduatorias);
	    graduatoriedService.insert(graduatoried);
	    pos++;
	}
	/**
	 * -DISTRIBUZIONE- eseguo la distribuzione dei valori in base ai tipocalcolo configurati per quel tipo di bando
	 */
	graduatorietDAO.updateDistribuisciValoriGraduatorie(entity);
	// §§§END§§§
    }

    private List creaListaIstanzaPerGraduatoria(Graduatoriet entity, Integer[] ordinamentoAscDesc) {

	//Recupero il numero dei posteggi disponibili, viene configurato al livello di creazione di bando
	// lo recupero dal primo record di Bandiinput, quindi deve essere posizionato in posizione 
	// zero della lista
	List<Tipibandooutput> tipibandooutputs = this.findTipiBandiOutput(entity.getTipigraduatoriet().getId().getCodice());
	List<Bandiinput> bandiinputs = this.findTipiBandiInput(entity.getBandi().getId().getCodice());
	// itero sui tipi di calcolo
	BigDecimal numeroPosteggi = new BigDecimal(0);
	for (Iterator iterator = tipibandooutputs.iterator(); iterator.hasNext();) {
	    Tipibandooutput tipibandooutput = (Tipibandooutput) iterator.next();
	    //	    BigDecimal valoreDaDistribuire = new BigDecimal(0);
	    // recupero il valore da distribuire
	    for (Iterator iterator2 = bandiinputs.iterator(); iterator2.hasNext();) {
		Bandiinput bandiinput = (Bandiinput) iterator2.next();
		if (bandiinput.getTipibandoinput().getId().equals(tipibandooutput.getTipibandoinput().getId())) {
		    numeroPosteggi = bandiinput.getValore();
		    break;
		}
	    }
	}
	//	BigDecimal numeroPosteggi = null;
	//	Set<Bandiinput> bandiinputs = entity.getBandi().getBandiinputs();
	//	for (Bandiinput bandiinput : bandiinputs) {
	//	    numeroPosteggi = bandiinput.getValore();
	//	    break;
	//	}
	boolean esci = false;
	Set<Tipibandocampigraduat> tipibandocampigraduats = entity.getTipigraduatoriet().getTipibandocampigraduats();
	int numeroDiValoriOrdinamento = tipibandocampigraduats.size();
	//Integer[] ordinamentoAscDesc = new Integer[numeroDiValoriOrdinamento];
	List<BandiAlberoproc> bandiAlberoprocs = bandiAlberoprocService.findByBandi(entity.getBandi().getId().getCodice());
	List dynList = new ArrayList();
	// Ciclo tutti gli interventi
	// Contiene una mappa chiave valore [codIntevento - indiceUltimaIstanzaInserita]
	// Permette di capire l'ultima istanza che è stata inserita per quella voce dell'albero nei
	// cicli precedenti. evita che una pratica venga inserita due volte nel caso che debbano essere ciclati
	// nuovamente gli interventi perhè non è stato completa la graduatoria.
	Map<Integer, Integer> mapNumeroIstanzePerIntevento = new HashMap<Integer, Integer>();
	// La variabile tiene conto dei cicli di assegnazione delle istanze in graduatororia.
	// La variabile viene incrementata quando sono stati ciclate tutte le voci dell'albero
	// presenti nella configurazione.
	Map<Integer, List> mapDynList = new HashMap<Integer, List>();
	for (int j = 0; j < bandiAlberoprocs.size(); j++) {
	    BandiAlberoproc bandiAlberoproc = bandiAlberoprocs.get(j);
	    //Object[] values = new Object[] { entity.getId().getCodice(), entity.getId().getIdcomune(), };
	    Object[] values = new Object[] { bandiAlberoproc.getAlberoproc().getId().getCodice(), entity.getId().getIdcomune(), };
	    // Estraggo le pratiche per il primo intervento
	    List dynListTemp = graduatorietDAO.findListaDyn2DatiPerGraduatoriaPerIntervento(entity, ordinamentoAscDesc, values, bandiAlberoproc
		    .getAlberoproc().getId().getCodice());
	    mapDynList.put(bandiAlberoproc.getAlberoproc().getId().getCodice(), dynListTemp);
	    // ORDINO SECONO I CRITERI DELLA GRADUATORIA
	    // SI ASSUME CHE I VALORI DA ORDINARE SIANO RICONDUCIBILI A VALORI NUMERICI
	    // ES. DATE IN YYYYMMDD
	    if (numeroDiValoriOrdinamento > 1) {
		Collections.sort(dynListTemp, new GraduatorieDyn2DatiArrayComparator(ordinamentoAscDesc));
	    } else {
		Collections.sort(dynListTemp, new GraduatorieDyn2DatiComparator(ordinamentoAscDesc[0]));
	    }
	}
	int controlloCicliAssegnazione = 0;
	for (int j = 0; j < bandiAlberoprocs.size(); j++) {
	    BandiAlberoproc bandiAlberoproc = bandiAlberoprocs.get(j);
	    Object[] values = new Object[] { bandiAlberoproc.getAlberoproc().getId().getCodice(), entity.getId().getIdcomune(), };
	    List dynListTemp = mapDynList.get(bandiAlberoproc.getAlberoproc().getId().getCodice());
	    // Calcolo la percentuale di pratiche per l'intervento
	    BigDecimal _numeroIstanzeInpercentuale = numeroPosteggi.divide(new BigDecimal(100));
	    BigDecimal numeroIstanzeInpercentuale = _numeroIstanzeInpercentuale.multiply(new BigDecimal(bandiAlberoproc.getPercentualeEstrazione()));
	    // Setto le N pratiche nella lista che verrà restituita dal metodo
	    int numeroPraticheDaEstrarre = 0;
	    if (numeroIstanzeInpercentuale.intValue() >= dynListTemp.size()) {
		// Numero numero pratiche da estrarre > di quello presente nella
		// lista
		numeroPraticheDaEstrarre = dynListTemp.size();
	    } else {
		// Numero numero pratiche da estrarre < di quello presente nella
		// lista
		numeroPraticheDaEstrarre = numeroIstanzeInpercentuale.intValue();
	    }
	    // Calcolo il codice dell'ultima pratica inserita per lo specifico intervento, cerco  nella mappa cercando per 
	    // chiave "codiceintevento", se non lo trova lo metto a zero
	    int indiceUltimaPratica = mapNumeroIstanzePerIntevento.get(bandiAlberoproc.getAlberoproc().getId().getCodice()) == null ? 0
		    : (mapNumeroIstanzePerIntevento.get(bandiAlberoproc.getAlberoproc().getId().getCodice()));
	    // Recupero dalla lista temporanee i valori e li aggiungo alla lista che deve ritornare il metodo.
	    if (indiceUltimaPratica < dynListTemp.size()) {
		for (int i = 0; i < numeroPraticheDaEstrarre; i++) {
		    if (dynListTemp.size() > indiceUltimaPratica) {
			dynList.add(dynListTemp.get(indiceUltimaPratica));
			// se ho raggiunto il numero massimo di posteggi esco
			indiceUltimaPratica++;
		    } else {
			log.debug("tutte le istanze sono state assegnate per l'intervento {}", bandiAlberoproc.getAlberoproc().getId().getCodice());
			esci = true;
			break;
		    }
		    if (dynList.size() == numeroPosteggi.intValue()) {
			log.debug("Raggiunto massimo numero di posteggi esco ...");
			esci = true;
			break;
		    }
		}
	    }
	    mapNumeroIstanzePerIntevento.put(bandiAlberoproc.getAlberoproc().getId().getCodice(), indiceUltimaPratica);
	    // esco anche dal ciclo degli interventi
	    if (esci) {
		break;
	    }
	    // Se è l'ultimo ciclo della lista degli interventi verifico se ho riempito tutti i posteggi
	    // FIXME : controllo controlloCicliAssegnazione permette solo due cicli completi di assegnazione, se la percentuale della
	    // prima voce dell'albero è minore di 50, potrebbero non essere assegnati tutti i posteggi.
	    if (j == bandiAlberoprocs.size() - 1 && dynList.size() < numeroPosteggi.intValue() && controlloCicliAssegnazione < 1) {
		log.debug("Non ho completato la graduatoria assegnando tutti i posteggi, ricomincio la ricerca delle istanza dalla prima voce"
			+ "dell'albero");
		j = -1;
		controlloCicliAssegnazione++;
	    }
	}
	// ciclo nuovamente gli interventi del bando per includere nella graduatoria anche le pratiche non assegnatarie
	for (int j = 0; j < bandiAlberoprocs.size(); j++) {
	    BandiAlberoproc bandiAlberoproc = bandiAlberoprocs.get(j);
	    List tempList = mapDynList.get(bandiAlberoproc.getAlberoproc().getId().getCodice());
	    //a questo punto prendo le rimanenze dai vari interventi senza valutare le percentuali scorrendo la lista delle istanze ed escludendo quelle già presenti
	    //in graduatoria
	    for (int c = 0; c < tempList.size(); c++) {
		Object tmpDyn = tempList.get(c);
		Integer tmpCodIstanza = null;
		if (tmpDyn instanceof Object[]) {
		    Object[] istanzedyn2dati = (Object[]) tmpDyn;
		    tmpCodIstanza = ((Istanzedyn2dati) istanzedyn2dati[0]).getId().getCodiceistanza();
		} else {
		    tmpCodIstanza = ((Istanzedyn2dati) tmpDyn).getId().getCodiceistanza();
		}
		boolean trovata = false;
		for (int i = 0; i < dynList.size(); i++) {
		    Object ist2d = dynList.get(i);
		    Integer codIstanza = null;
		    if (ist2d instanceof Object[]) {
			Object[] istanzedyn2dati = (Object[]) ist2d;
			codIstanza = ((Istanzedyn2dati) istanzedyn2dati[0]).getId().getCodiceistanza();
		    } else {
			codIstanza = ((Istanzedyn2dati) ist2d).getId().getCodiceistanza();
		    }
		    if (codIstanza.equals(tmpCodIstanza)) {
			trovata = true;
			break;
		    }
		}
		if (!trovata) {
		    dynList.add(tmpDyn);
		}
	    }
	}
	graduatorietDAO.flush();
	graduatorietDAO.clear();
	return dynList;
    }

    private boolean validateIstanzeGraduatoria(Graduatoriet entity) {

	// §§§BEGIN§§§
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<Tipibandocampigraduat> tipibandocampigraduats = entity.getTipigraduatoriet().getTipibandocampigraduats();
	int numeroDiValoriOrdinamento = tipibandocampigraduats.size();
	Integer[] ordinamentoAscDesc = new Integer[numeroDiValoriOrdinamento];
	if (ordinamentoAscDesc.length == 0) {
	    _ivs.add(new InvalidValue(
		    "Attenzione, non è configurato nessun criterio di ordinamento per il modello di graduatoria selezionato. Bisogna andare nella configurazione del bando, selezionare il modello in questione e configurare i criteri di ordinamento accedendovi tramite il bottone\"CRITERI DI ORDINAMENTO\"",
		    entity.getClass(), "", "", entity));
	    this.throwValidationMessages(_ivs);
	}
	Object[] values = new Object[] { entity.getId().getCodice(), entity.getId().getIdcomune() };
	List dynList = graduatorietDAO.findListaDyn2DatiPerGraduatoria(entity, ordinamentoAscDesc, values);
	// verifico la completezza degli estremi aut da dati dinamici dell'istanza
	// verifico che le autorizzazioni recuperate dagli estremi siano non cessate e non subentrate
	for (Iterator iterator = dynList.iterator(); iterator.hasNext();) {
	    Istanze istanze = null;
	    Object obj = iterator.next();
	    if (obj instanceof Object[]) {
		Object[] istanzedyn2dati = (Object[]) obj;
		for (int j = 0; j < istanzedyn2dati.length; j++) {
		    istanze = ((Istanzedyn2dati) istanzedyn2dati[j]).getIstanza();
		    break;
		}
	    } else {
		Istanzedyn2dati istanzedyn2dati = (Istanzedyn2dati) obj;
		istanze = istanzedyn2dati.getIstanza();
	    }
	    EstremiAutDTO aut = autorizzazioniService.populateEstremiByIstanzaDyn2Dati(istanze.getId().getCodice());
	    if (aut == null) {
		_ivs.add(new InvalidValue("Gli estremi dell'autorizzazione della scheda dell'istanza " + istanze.getNumeroistanza()
			+ " non sono completi.", entity.getClass(), "", "", entity));
	    } else {
		Autorizzazioni _aut = autorizzazioniService.findAutOConcByEstremi(aut.getAutNumero(), aut.getAutData(), aut.getAutCodiceComune(),
			aut.getAutTipologiaRegistro());
		if (_aut != null) {
		    if (_aut.getFlagAttiva() == null || !_aut.getFlagAttiva().booleanValue()) {
			_ivs.add(new InvalidValue("L'autorizzazione/concessione (" + _aut.getTransientEstremiAut() + ") dell'istanza "
				+ istanze.getNumeroistanza() + " è cessata.", entity.getClass(), "", "", entity));
		    }
		} else {
		    AutorizzazioniSubentri autSub = autorizzazioniSubentriService.findAutSubOConcSubByEstremi(aut.getAutNumero(), aut.getAutData(),
			    aut.getAutCodiceComune(), aut.getAutTipologiaRegistro());
		    if (autSub != null) {
			_ivs.add(new InvalidValue("L'autorizzazione/concessione (" + autSub.getTransientEstremiAut() + ") dell'istanza "
				+ istanze.getNumeroistanza() + " è cessata per subentro.", entity.getClass(), "", "", entity));
		    }
		}
	    }
	    graduatorietDAO.flush();
	    graduatorietDAO.clear();
	}
	if (_ivs.size() > 0) {
	    this.throwValidationMessages(_ivs);
	}
	// §§§END§§§
	return true;
    }

    @Override
    protected void childDelete(Graduatoriet entity) {

	// Vado a cancellare tutte le comunicazioni associate alla graduatoria (graduatorietcom)
	log.debug("childDelete# Inzio cancellazione comunicazioni graduatoria (graduatorietCom) assosiate alla graduatoria: {}({})", new Object[] {
		entity.getDescrizione(), entity.getId().getCodice() });
	List<GraduatorietCom> graduatorietComs = graduatorietComService.findByGraduatoriT(entity.getId().getCodice(), null, null);
	for (GraduatorietCom graduatorietCom : graduatorietComs) {
	    graduatorietComService.delete(graduatorietCom);
	}
	log.debug("childDelete# Fine cancellazione comunicazioni graduatoria (graduatorietCom).... ");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Graduatoriet> findByFilter(Graduatoriet entity) {

	// §§§BEGIN§§§
	return graduatorietDAO.findByFilter(entity);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<Tipibandooutput> findTipiBandiOutput(Integer tipiGraduatorieId) {

	return graduatorietDAO.findTipiBandiOutput(tipiGraduatorieId);
    }

    @Override
    public List<Bandiinput> findTipiBandiInput(Integer bandiId) {

	return graduatorietDAO.findTipiBandiInput(bandiId);
    }

    @Override
    public List<Graduatoriet> findByAndBandoDescrizione(String textToSearch, Integer codicebando) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.like("descrizione", textToSearch));
	if (codicebando != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", codicebando, "bandi", Integer.class));
	} else {
	    return new ArrayList<Graduatoriet>();
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return graduatorietDAO.findByFilterTable(ft);
    }
}
