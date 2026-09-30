package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.GraduatorietPianorotazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.GiorniSettimanaEnum;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Graduatoried;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.GraduatorietPianorotazione;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeeventi;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipigradtCfgRotazione;
import it.gruppoinit.pal.gp.core.domain.Tipigraduatoriet;
import it.gruppoinit.pal.gp.core.domain.helper.GiorniHelper;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatoriedDTO;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatorietPianoRotazioneDTO;
import it.gruppoinit.pal.gp.core.domain.helper.Istanzedyn2datiDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiDDTO;
import it.gruppoinit.pal.gp.core.domain.helper.MercatiUsoDTO;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioPianoRotazioneHelper;
import it.gruppoinit.pal.gp.core.domain.helper.PosteggioPianoRotazioneHelperComparator;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.GraduatorietPianorotazioneFilter;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.GraduatoriedService;
import it.gruppoinit.pal.gp.core.service.GraduatorietPianorotazioneService;
import it.gruppoinit.pal.gp.core.service.GraduatorietService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.IstanzeeventiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.helper.IstanzeeventiConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class GraduatorietPianorotazioneServiceImpl extends BaseServiceImpl<GraduatorietPianorotazione, PkId> implements
	GraduatorietPianorotazioneService {

    private static final Logger log = LoggerFactory.getLogger(GraduatorietPianorotazioneServiceImpl.class);
    private GraduatorietPianorotazioneDAO graduatorietpianorotazioneDAO;
    private GraduatoriedService graduatoriedService;
    private GraduatorietService graduatorietService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private MercatiUsoService mercatiUsoService;
    private MercatiDService mercatiDService;
    private IstanzeService istanzeService;
    private AutorizzazioniService autorizzazioniService;
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private IstanzeeventiService istanzeeventiService;

    @Autowired
    public void setGraduatorietPianorotazioneDAO(GraduatorietPianorotazioneDAO graduatorietpianorotazioneDAO) {

	this.graduatorietpianorotazioneDAO = graduatorietpianorotazioneDAO;
    }

    @Autowired
    public void setGraduatoriedService(GraduatoriedService graduatoriedService) {

	this.graduatoriedService = graduatoriedService;
    }

    @Autowired
    public void setGraduatorietService(GraduatorietService graduatorietService) {

	this.graduatorietService = graduatorietService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setAutorizzazioniConcessioniService(AutorizzazioniConcessioniService autorizzazioniConcessioniService) {

	this.autorizzazioniConcessioniService = autorizzazioniConcessioniService;
    }

    @Autowired
    public void setIstanzeeventiService(IstanzeeventiService istanzeeventiService) {

	this.istanzeeventiService = istanzeeventiService;
    }

    @Override
    protected Class<GraduatorietPianorotazione> getEntityClass() {

	return GraduatorietPianorotazione.class;
    }

    @Override
    public List<GraduatorietPianorotazione> findAll(Integer firstResult, Integer maxResult) {

	return graduatorietpianorotazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(GraduatorietPianorotazione entity) {

	if (validateEntity(entity)) {
	    graduatorietpianorotazioneDAO.insert(entity);
	}
    }

    @Override
    public GraduatorietPianorotazione findById(PkId id) {

	return graduatorietpianorotazioneDAO.findById(id);
    }

    @Override
    public void update(GraduatorietPianorotazione entity) {

	if (validateEntity(entity)) {
	    graduatorietpianorotazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(GraduatorietPianorotazione entity) {

	if (isDeleteAllowed(entity)) {
	    graduatorietpianorotazioneDAO.delete(entity);
	}
    }

    @Override
    public List<GraduatorietPianorotazione> findByGraduatoriaD(Integer graduatoriaid) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", graduatoriaid, "graduatoried", Integer.class));
	ft.addRestriction(fr);
	return graduatorietpianorotazioneDAO.findByFilterTable(ft);
    }

    @Override
    public void creaPianoRotazione(Integer graduatoriat) {

	System.out.println();
	long t1 = System.currentTimeMillis();
	// Recupero la graduatoria d
	Graduatoriet graduatoriet = graduatorietService.findById(new PkId(graduatoriat));
	// Valido le configurazione necessarie per poter creare il piano di rotazione, altrimenti rilancio l'errore
	validaCreazionePianoRotazione(graduatoriet);
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////// GESTIONE ASSEGNAZIONE POSTEGGI STORICO /////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Recupero la configurazione per creare il piano di rotazione
	Tipigraduatoriet tipigraduatoriet = graduatoriet.getTipigraduatoriet();
	Set<TipigradtCfgRotazione> tipigradtCfgRotaziones = tipigraduatoriet.getTipigradtCfgRotaziones();
	// Estraggo l'unica configurazione esistente.
	TipigradtCfgRotazione tipigradtCfgRotazione = new TipigradtCfgRotazione();
	for (TipigradtCfgRotazione tipigradtCfgRotazioneTemp : tipigradtCfgRotaziones) {
	    tipigradtCfgRotazione = tipigradtCfgRotazioneTemp;
	}
	// Recupero tutte i record che compongono la graduatorie (ogni record contiene l'istanza con cui il richiedente
	// ha partecipato al bando)
	Set<Graduatoried> graduatorieds = graduatoriet.getGraduatorieds();
	creaEdAssegnaPosteggiStoriciPerPianoRotazione(graduatorieds, tipigradtCfgRotazione);
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////// FINE GESTIONE ASSEGNAZIONE POSTEGGI STORICO //////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////// ASSEGNAZIONE POSTEGGI RIMANENTI /////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Recupero la lista dei posteggi per giorno ordinandoli per peso giorno e peso posteggio
	creaPosteggiPerPianoRotazione(graduatoriet);
	long t2 = System.currentTimeMillis();
	System.out.println(t2 - t1);
    }

    private void validaCreazionePianoRotazione(Graduatoriet graduatoriet) {

	Tipigraduatoriet tipigraduatoriet = graduatoriet.getTipigraduatoriet();
	Set<TipigradtCfgRotazione> tipigradtCfgRotaziones = tipigraduatoriet.getTipigradtCfgRotaziones();
	// Ne puo esistere solo una 
	log.debug("creaPianoRotazione# Controllo se esiste uan sola configurazione il tipo graduatoria {} [{}]", new java.lang.Object[] {
		tipigraduatoriet.getDescrizione(), tipigraduatoriet.getId().getCodice() });
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (tipigradtCfgRotaziones != null) {
	    if (tipigradtCfgRotaziones.size() > 1) {
		_ivs.add(new InvalidValue("service_error.non_possono_esistere_piu_configurazioni_rotazione", null, null, null, null));
	    }
	    if (tipigradtCfgRotaziones.size() == 0) {
		_ivs.add(new InvalidValue("service_error.deve_esiste_esistere_una_configurazioni_rotazione", null, null, null, null));
	    }
	}
	if (EntityUtils.getNestedProperty(graduatoriet.getBandi().getAlberoproc(), "id.codice") == null) {
	    _ivs.add(new InvalidValue("service_error.deve_essere_configurato_un_intevento_per_bando", null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	if (EntityUtils.getNestedProperty(graduatoriet.getBandi().getAlberoproc().getMercato(), "id.codice") == null) {
	    _ivs.add(new InvalidValue("service_error.deve_essere_configurato_un_mercato_per_intervento", null, null, null, null));
	    this.throwValidationMessages(_ivs);
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
    }

    private List<PosteggioPianoRotazioneHelper> creaEdAssegnaPosteggiStoriciPerPianoRotazione(Set<Graduatoried> graduatorieds,
	    TipigradtCfgRotazione tipigradtCfgRotazione) {

	List<PosteggioPianoRotazioneHelper> pianoRotazioneHelpers = null;
	PosteggioPianoRotazioneHelper posteggioPianoRotazioneHelper = null;
	// Ciclo la graduatori
	log.debug("creaPianoRotazione# Ciclo la graduatoria");
	for (Graduatoried graduatoried : graduatorieds) {
	    pianoRotazioneHelpers = new ArrayList<PosteggioPianoRotazioneHelper>();
	    // Recupero tutti i campi dinamici associati all'istanza che hanno lo stesso campo dinamico presenete nella configurazione
	    // del piano di rotazione associato alla proprietà "CampiMercatoUso"
	    List<Istanzedyn2datiDTO> istanzedyn2datisUso = istanzedyn2datiService.findDTOByIstanzaAndDyn2Campi(graduatoried.getIstanza().getId()
		    .getCodice(), tipigradtCfgRotazione.getCampiMercatoUso().getId().getCodice(), null, null);
	    // Per ogni record trovato creo un mercato uso 
	    for (Istanzedyn2datiDTO istanzedyn2datiDTO : istanzedyn2datisUso) {
		boolean isInsertposteggioPianoRotazione = true;
		posteggioPianoRotazioneHelper = new PosteggioPianoRotazioneHelper();
		PkId id = new PkId();
		MercatiUsoDTO mercatiUsodto = new MercatiUsoDTO();
		mercatiUsodto.setDescrizione(istanzedyn2datiDTO.getValoredecodificato());
		id.setCodice(Integer.parseInt(istanzedyn2datiDTO.getValore()));
		mercatiUsodto.setId(id);
		posteggioPianoRotazioneHelper.setGiorno(mercatiUsodto);
		// Per ogni mercato uso creato recupero i campi dinamici associati all'istanza che hanno il campo dinamico uguale a quello
		// presente nella configurazione nella proprietà "CampiPosteggio"
		List<Istanzedyn2datiDTO> istanzedyn2datisPosteggi = istanzedyn2datiService.findDTOByIstanzaAndDyn2Campi(graduatoried.getIstanza()
			.getId().getCodice(), tipigradtCfgRotazione.getCampiPosteggio().getId().getCodice(), null,
			istanzedyn2datiDTO.getMolteplicita());
		// Creo n posteggi (mercatDDTO), quanti sono i dati dinamici presenti sull'istanza che rappresentano 
		// i posteggi
		MercatiDDTO mercatiDdto = new MercatiDDTO();
		if (!istanzedyn2datisPosteggi.isEmpty()) {
		    mercatiDdto.setCodiceposteggio(istanzedyn2datisPosteggi.get(0).getValoredecodificato());
		    mercatiDdto.getId().setCodice(Integer.parseInt(istanzedyn2datisPosteggi.get(0).getValore()));
		    // devo verificare se ha dei critri di assegnazione e se sono compatibili
		    MercatiD mercatiD = mercatiDService.findById(new PkId(Integer.parseInt(istanzedyn2datisPosteggi.get(0).getValore())));
		    // controllo se il posteggio ha dei criteri di assegnazione, nel caso il posteggio non sia compatibile con
		    // con l'istanza allora il metodo ritorna "false", andrò a popolare direttamente la variabile "isInsertposteggioPianoRotazione"
		    // in modo che non venga inserito tra i posteggi da assegnare.
		    isInsertposteggioPianoRotazione = mercatiDService.checkCompatibilitaPosteggio(mercatiD.getMercatidcritasses(), graduatoried
			    .getIstanza().getId().getCodice());
		    // Inserisco evento di posteggio non assegnato per incompatibilità???????
		} else {
		    //Manca il posteggio, non pò essere inseito.
		    isInsertposteggioPianoRotazione = false;
		}
		posteggioPianoRotazioneHelper.setPosteggio(mercatiDdto);
		// Sempre dai dati dinamici recupero per ogni uso e posteggio l'ordine (il tipo di dato dinamico che rappresenta
		// l'ordine lo recupero sempre dalla configurazione )
		// 
		List<Istanzedyn2datiDTO> istanzedyn2datisOrdine = istanzedyn2datiService.findDTOByIstanzaAndDyn2Campi(graduatoried.getIstanza()
			.getId().getCodice(), tipigradtCfgRotazione.getCampiOrdine().getId().getCodice(), null, istanzedyn2datiDTO.getMolteplicita());
		if (!istanzedyn2datisOrdine.isEmpty()) {
		    posteggioPianoRotazioneHelper.setOrdine(Integer.parseInt(istanzedyn2datisOrdine.get(0).getValore()));
		} else {
		    //Manca l' ordine, non pò essere inseito.
		    isInsertposteggioPianoRotazione = false;
		}
		if (isInsertposteggioPianoRotazione) {
		    pianoRotazioneHelpers.add(posteggioPianoRotazioneHelper);
		}
	    }
	    // Se la lista creata per un record della graduatoria è non vuoto inizio ad assegnare i posteggi storici secondo le regole
	    // 1- Si verifica se la postazazione sia libera
	    // 2- Si verifica che per ogni soggetto in graduatoria non siano stati scelti, più di due giorni del fine settimana
	    // (venerdì/Sabato/Domenica), nel caso si prende solo il primo
	    // 3- Non possono essere assegnate più di tre postazione storiche
	    if (!pianoRotazioneHelpers.isEmpty()) {
		Collections.sort(pianoRotazioneHelpers, new PosteggioPianoRotazioneHelperComparator());
		// prima verifico che sia libero il posto e poi inserisco
		GraduatorietPianorotazione graduatorietPianorotazione = null;
		// Ne posso inserire un massimo di 3 per istanza
		int contatore = 0;
		int contatoreFineSettimana = 0;
		boolean isInsert = true;
		for (PosteggioPianoRotazioneHelper pianoRotazioneHelper : pianoRotazioneHelpers) {
		    // Devo controllare prima di inserirlo se esistono più giorni che ppartengono a fine settimana
		    //(Vnerdi,sabato, domenica), quando ne inserisco uno aumento il contatore, mi permettereà di escludere 
		    // altre postazioni storiche del fine settimana per la stesso richiedente
		    if (pianoRotazioneHelper.getGiorno().getDescrizione().equalsIgnoreCase("Venerdì")
			    || pianoRotazioneHelper.getGiorno().getDescrizione().equalsIgnoreCase("Sabato")
			    || pianoRotazioneHelper.getGiorno().getDescrizione().equalsIgnoreCase("Domenica")) {
			contatoreFineSettimana++;
			if (contatoreFineSettimana > 1) {
			    isInsert = false;
			}
		    }
		    // Controllo se isInsert==true  e se non sono già stati inseriti 3 posteggi storici
		    if (contatore < 3 && isInsert) {
			FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
			FilterRestriction restriction = new FilterRestriction();
			restriction.addFilterField(FilterUtils.equals("id.codice", graduatoried.getGraduatoriet().getId().getCodice(),
				"graduatoried.graduatoriet", Integer.class));
			restriction.addFilterField(FilterUtils.equals("id.codice", pianoRotazioneHelper.getGiorno().getId().getCodice(),
				"mercatiUso", Integer.class));
			restriction.addFilterField(FilterUtils.equals("id.codice", pianoRotazioneHelper.getPosteggio().getId().getCodice(),
				"mercatiD", Integer.class));
			filterTable.addRestriction(restriction);
			// vedo se il posteggio già è stato assegnato
			List<GraduatorietPianorotazione> list = graduatorietpianorotazioneDAO.findByFilterTable(filterTable);
			if (list.isEmpty()) {
			    graduatorietPianorotazione = new GraduatorietPianorotazione();
			    graduatorietPianorotazione.setGraduatoried(graduatoried);
			    graduatorietPianorotazione.setIstanze(graduatoried.getIstanza());
			    MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(pianoRotazioneHelper.getGiorno().getId().getCodice()));
			    graduatorietPianorotazione.setMercatiUso(mercatiUso);
			    MercatiD mercatiD = mercatiDService.findById(new PkId(pianoRotazioneHelper.getPosteggio().getId().getCodice()));
			    graduatorietPianorotazione.setMercatiD(mercatiD);
			    GraduatorietPianorotazioneFilter filter = new GraduatorietPianorotazioneFilter();
			    filter.setCodiceGraduatoriad(graduatoried.getId().getCodice());
			    filter.setCodiceIstanza(graduatoried.getIstanza().getId().getCodice());
			    filter.setCodiceMercato(mercatiD.getId().getCodice());
			    filter.setCodiceMercatoUso(mercatiUso.getId().getCodice());
			    List<GraduatorietPianorotazione> pianorotaziones = this.findByGraduatorietPianorotazioneFilter(filter);
			    if (pianorotaziones.isEmpty()) {
				this.insert(graduatorietPianorotazione);
			    } else {
				// metto un evento di giorno già assegnato, inutile avere nello stesso giorno più posteggi
			    }
			    graduatorietpianorotazioneDAO.commit();
			    graduatorietpianorotazioneDAO.flush();
			    graduatorietpianorotazioneDAO.clear();
			} else {
			    GraduatorietPianorotazione gp = list.get(0);
			    Istanze istanza = gp.getIstanze();
			    Istanzeeventi istanzeeventi = new Istanzeeventi();
			    istanzeeventi.setIstanze(gp.getIstanze());
			    istanzeeventi.getCategorieeventibase().setId(IstanzeeventiConstants.CATEGORIA_ASSEGNAZIONE_POSTEGGIO_DA_GRADUATORIA);
			    istanzeeventi.setData(Calendar.getInstance().getTime());
			    StringBuffer buffer = new StringBuffer("Non è stato possibile assegnare il posteggio ")
				    .append(gp.getMercatiD().getCodiceposteggio()).append(" per il giorno ")
				    .append(gp.getMercatiUso().getDescrizione()).append(". Posteggio già occupato da ")
				    .append(gp.getIstanze().getTransientRichiedenteQualitaAzienda());
			    istanzeeventi.setDescrizione(buffer.toString());
			    istanzeeventi.setSoftware(istanza.getSoftware());
			    istanzeeventiService.insert(istanzeeventi);
			}
		    }
		    contatore++;
		}
	    }
	}
	graduatorietpianorotazioneDAO.commit();
	graduatorietpianorotazioneDAO.flush();
	graduatorietpianorotazioneDAO.clear();
	return pianoRotazioneHelpers;
    }

    private void creaPosteggiPerPianoRotazione(Graduatoriet graduatoriet) {

	Graduatoriet graduatorietTemp = graduatorietService.findById(new PkId(graduatoriet.getId().getCodice()));
	boolean posteggioAssegnatoAdIstanza = true;
	//List<GraduatoriedDTO> graduatoriedsTemp = graduatoriedService.findByGraduatoriet(graduatoriet, DAOOrderTypeEnum.ASC);
	List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(graduatoriet.getBandi().getAlberoproc().getMercato());
	List<MercatiD> mercatiDs = mercatiDService
		.findByMercatoOrderByPeso(graduatoriet.getBandi().getAlberoproc().getMercato(), PosteggiEnum.ACTIVE);
	List<GiorniHelper> list = createListGiorniHelper(mercatiUsos, mercatiDs);
	while (posteggioAssegnatoAdIstanza) {
	    posteggioAssegnatoAdIstanza = false;
	    List<GraduatoriedDTO> graduatoriedsTemp = createGraduatoriedDTO(graduatoriet, DAOOrderTypeEnum.ASC, mercatiDs.size());
	    for (GraduatoriedDTO graduatoriedDTO : graduatoriedsTemp) {
		int dimLista = list.size();
		list = assegnaPosteggio(graduatorietTemp, graduatoriedDTO, list);
		if (list.size() != dimLista) {
		    posteggioAssegnatoAdIstanza = true;
		}
	    }
	}
    }

    private List<GraduatoriedDTO> createGraduatoriedDTO(Graduatoriet graduatoriet, DAOOrderTypeEnum order, int sizeMercato) {

	List<GraduatoriedDTO> ris = new ArrayList<GraduatoriedDTO>();
	List<GraduatoriedDTO> graduatoriedsTemp = graduatoriedService.findByGraduatoriet(graduatoriet, DAOOrderTypeEnum.ASC, 0, sizeMercato);
	for (GraduatoriedDTO graduatoriedDTO : graduatoriedsTemp) {
	    List<GraduatorietPianorotazione> list = this.findByGraduatoriaD(graduatoriedDTO.getId().getCodice());
	    if (list.size() != 7) {
		ris.add(graduatoriedDTO);
	    }
	}
	return ris;
    }

    /**
     * Creerà una lista di oggetti giorni helper (saranno ordinati per peso mercatouso desc e peso posteggio desc)
     * 
     * @param mercatiUsos
     * @param mercatiDs
     * @return
     */
    private List<GiorniHelper> createListGiorniHelper(List<MercatiUso> mercatiUsos, List<MercatiD> mercatiDs) {

	List<GiorniHelper> list = new ArrayList<GiorniHelper>();
	GiorniHelper giorniHelper = null;
	for (MercatiUso mercatiUso : mercatiUsos) {
	    Integer codiceUso = mercatiUso.getId().getCodice();
	    String desc = mercatiUso.getDescrizione();
	    for (MercatiD mercatiD : mercatiDs) {
		giorniHelper = new GiorniHelper();
		giorniHelper.setCodiceGiorno(codiceUso);
		giorniHelper.setDescrizioneGiorno(desc);
		giorniHelper.setCodiceMercatoD(mercatiD.getId().getCodice());
		list.add(giorniHelper);
	    }
	}
	return list;
    }

    private List<GiorniHelper> assegnaPosteggio(Graduatoriet graduatoriet, GraduatoriedDTO graduatoriedDTO, List<GiorniHelper> giorniHelpers) {

	boolean ris = false;
	//	List<MercatiUso> mercatiUsos = mercatiUsoService.findByMercato(graduatoriet.getBandi().getAlberoproc().getMercato());
	int ordine = 0;
	for (GiorniHelper giorniHelper : giorniHelpers) {
	    MercatiD mercatiD = mercatiDService.findById(new PkId(giorniHelper.getCodiceMercatoD()));
	    //for (MercatiUso mercatiUso : mercatiUsos) {
	    //	    List<MercatiD> mercatiDs = mercatiDService.findByMercatoOrderByPeso(graduatoriet.getBandi().getAlberoproc().getMercato(),
	    //		    PosteggiEnum.ACTIVE);
	    //for (MercatiD mercatiD : mercatiDs) {
	    // Prima di andare avanti controllo che l'istanza sia compatibile con il posteggio; se il posteggio ha dei criteri di 
	    // assegnazione, verifico che sono compatibili con quelli presenti sull'istanza. In caso contrario non assegno il posteggio
	    boolean isPosteggioCompatibile = mercatiDService.checkCompatibilitaPosteggio(mercatiD.getMercatidcritasses(), graduatoriedDTO
		    .getIstanza().getId().getCodice());
	    if (isPosteggioCompatibile) {
		FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		FilterRestriction restriction = new FilterRestriction();
		restriction.addFilterField(FilterUtils.equals("id.codice", graduatoriet.getId().getCodice(), "graduatoried.graduatoriet",
			Integer.class));
		restriction.addFilterField(FilterUtils.equals("id.codice", giorniHelper.getCodiceGiorno(), "mercatiUso", Integer.class));
		restriction.addFilterField(FilterUtils.equals("id.codice", mercatiD.getId().getCodice(), "mercatiD", Integer.class));
		filterTable.addRestriction(restriction);
		List<GraduatorietPianorotazione> list = graduatorietpianorotazioneDAO.findByFilterTable(filterTable);
		if (list.isEmpty()) {
		    PosteggioPianoRotazioneHelper pos = new PosteggioPianoRotazioneHelper();
		    //PkId id=new PkId();
		    MercatiUsoDTO _mercatiUsoDTO = new MercatiUsoDTO();
		    PkId idUso = new PkId(giorniHelper.getCodiceGiorno());
		    _mercatiUsoDTO.setId(idUso);
		    _mercatiUsoDTO.setDescrizione(giorniHelper.getDescrizioneGiorno());
		    MercatiDDTO _mercatiDDTO = new MercatiDDTO();
		    _mercatiDDTO.setId(mercatiD.getId());
		    _mercatiDDTO.setCodiceposteggio(mercatiD.getCodiceposteggio());
		    pos.setGiorno(_mercatiUsoDTO);
		    pos.setPosteggio(_mercatiDDTO);
		    pos.setOrdine(ordine);
		    pos.setCodiceIstanza(graduatoriedDTO.getIstanza().getId().getCodice());
		    pos.setCodiceGraduatoriad(graduatoriedDTO.getId().getCodice());
		    GraduatorietPianorotazioneFilter filter = new GraduatorietPianorotazioneFilter();
		    filter.setCodiceGraduatoriad(graduatoriedDTO.getId().getCodice());
		    filter.setCodiceIstanza(graduatoriedDTO.getIstanza().getId().getCodice());
		    filter.setCodiceMercato(graduatoriet.getBandi().getAlberoproc().getMercato().getId().getCodice());
		    filter.setCodiceMercatoUso(_mercatiUsoDTO.getId().getCodice());
		    List<GraduatorietPianorotazione> _pianorotaziones = this.findByGraduatorietPianorotazioneFilter(filter);
		    if (_pianorotaziones.isEmpty()) {
			GraduatorietPianorotazione graduatorietPianorotazione = new GraduatorietPianorotazione();
			Istanze istanza = istanzeService.findById(new PkId(graduatoriedDTO.getIstanza().getId().getCodice()));
			graduatorietPianorotazione.setIstanze(istanza);
			MercatiUso mercatiUso = mercatiUsoService.findById(new PkId(giorniHelper.getCodiceGiorno()));
			graduatorietPianorotazione.setMercatiUso(mercatiUso);
			// MercatiD mercatiD = mercatiDService.findById(new PkId(pianoRotazioneHelper.getPosteggio().getId().getCodice()));
			Graduatoried graduatoried = graduatoriedService.findById(new PkId(graduatoriedDTO.getId().getCodice()));
			graduatorietPianorotazione.setGraduatoried(graduatoried);
			graduatorietPianorotazione.setMercatiD(mercatiD);
			// Elimino dalla lista il posteggio assegnato
			// Controllare se da errore?
			giorniHelpers.remove(giorniHelper);
			this.insert(graduatorietPianorotazione);
			graduatorietpianorotazioneDAO.commit();
			graduatorietpianorotazioneDAO.flush();
			graduatorietpianorotazioneDAO.clear();
			// Posteggio assegnato , esco dal metodo
			return giorniHelpers;
		    }
		}
	    } else {
		// Inserisco evento di posteggio non assegnato per incompatibilità
	    }
	    // }
	    //}
	}
	return giorniHelpers;
    }

    @Override
    public List<GraduatorietPianorotazione> findByGraduatorit(Integer codiceGraduatorit) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("id.codice", codiceGraduatorit, "graduatoried.graduatoriet", Integer.class));
	filterTable.addRestriction(restriction);
	return graduatorietpianorotazioneDAO.findByFilterTable(filterTable);
    }

    @Override
    public List<GraduatorietPianoRotazioneDTO> findByGraduatorieT(Graduatoriet graduatoriat, MercatiD mercatiD,
	    GiorniSettimanaEnum giorniSettimanaEnum) {

	return graduatorietpianorotazioneDAO.findByGraduatorieT(graduatoriat, mercatiD, giorniSettimanaEnum);
    }

    @Override
    public List<GraduatorietPianoRotazioneDTO> findByGraduatorieTAndIstanza(Graduatoriet graduatoriat, Integer codiceIstanza) {

	return graduatorietpianorotazioneDAO.findByGraduatorieTAndIstanza(graduatoriat, codiceIstanza);
    }

    /**
     * Il metodo crea una struttura adatta alla visualizzazione a griglia della jsp
     */
    @Override
    public List<PosteggioHelper> createMapPosteggiPianorotazione(Graduatoriet graduatoriat) {

	List<PosteggioHelper> posteggioHelpers = new ArrayList<PosteggioHelper>();
	PosteggioHelper posteggioHelper = new PosteggioHelper();
	int numeroposteggio = graduatoriat.getBandi().getAlberoproc().getMercato().getMercatiDs().size();
	Set<MercatiD> mercatiDs = graduatoriat.getBandi().getAlberoproc().getMercato().getMercatiDs();
	for (MercatiD mercatiD : mercatiDs) {
	    //}
	    //for (int i = 0; i < numeroposteggio; i++) {
	    List<GiorniHelper> giorniHelpers = new ArrayList<GiorniHelper>();
	    posteggioHelper = new PosteggioHelper();
	    posteggioHelper.setCodiceposteggio(mercatiD.getCodiceposteggio());
	    for (GiorniSettimanaEnum g : GiorniSettimanaEnum.values()) {
		List<GraduatorietPianoRotazioneDTO> list = this.findByGraduatorieT(graduatoriat, mercatiD, g);
		if (!list.isEmpty()) {
		    //if (posteggioHelper == null) {
		    //posteggioHelper = new PosteggioHelper();
		    //		    posteggioHelper.setCodiceposteggio(list.get(i).getMercatiD().getCodiceposteggio());
		    //}
		    GiorniHelper giorniHelper = new GiorniHelper();
		    giorniHelper.setDescrizioneGiorno(list.get(0).getMercatiUso().getDescrizione());
		    giorniHelper.setNumeroIstanza(list.get(0).getIstanze().getNumeroistanza());
		    giorniHelper.setOccupante(list.get(0).getIstanze().getTransientDescrizioneRichiedenteQualitaAzienda());
		    giorniHelpers.add(giorniHelper);
		} else {
//		    GiorniHelper giorniHelper = new GiorniHelper();
//		    giorniHelpers.add(giorniHelper);
		    //break;
		}
	    }
	    posteggioHelper.setGiornosHelper(giorniHelpers);
	    posteggioHelpers.add(posteggioHelper);
	}
	return posteggioHelpers;
    }

    @Override
    public void deletePianoRotazione(List<GraduatorietPianorotazione> graduatorietPianorotaziones) {

	for (GraduatorietPianorotazione graduatorietPianorotazione : graduatorietPianorotaziones) {
	    Integer codiceIstanza = graduatorietPianorotazione.getIstanze().getId().getCodice();
	    Set<Autorizzazioni> auts = graduatorietPianorotazione.getIstanze().getAutorizzazionis();
	    for (Autorizzazioni autorizzazioni : auts) {
		Set<AutorizzazioniConcessioni> autConc = autorizzazioni.getAutorizzazioniConcessionisForFkAutconcAutatt();
		for (AutorizzazioniConcessioni autorizzazioniConcessioni : autConc) {
		    autorizzazioniConcessioniService.delete(autorizzazioniConcessioni);
		}
		autorizzazioniService.delete(autorizzazioni);
	    }
	    this.delete(graduatorietPianorotazione);
	}
    }

    @Override
    public List<GraduatorietPianorotazione> findByGraduatorietPianorotazioneFilter(GraduatorietPianorotazioneFilter filter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (filter.getCodiceGraduatoriad() != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getCodiceGraduatoriad(), "graduatoried", Integer.class));
	}
	if (filter.getCodiceIstanza() != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getCodiceIstanza(), "istanze", Integer.class));
	}
	if (filter.getCodiceMercato() != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getCodiceMercato(), "mercatiUso.mercati", Integer.class));
	}
	if (filter.getCodiceMercatoUso() != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", filter.getCodiceMercatoUso(), "mercatiUso", Integer.class));
	}
	ft.addRestriction(fr);
	return graduatorietpianorotazioneDAO.findByFilterTable(ft);
    }
    //    protected boolean isDeleteAllowed(GraduatorietPianorotazione entity) {
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
}
