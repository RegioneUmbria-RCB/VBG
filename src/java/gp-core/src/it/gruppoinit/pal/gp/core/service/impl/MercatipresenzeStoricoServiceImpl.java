package it.gruppoinit.pal.gp.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatipresenzeStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiAnnoGiornoDTO;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiPresenzeDTO;
import it.gruppoinit.pal.gp.core.dao.helper.RicercaAnagraficeCollegateEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeStorico;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.PresenzeAutorizzazioniHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniMercatiPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.AutorizzazioniRestHelper;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.PresenzeStoricoGiornataRestHelper;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeStoricoService;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniPresenzeRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.AutorizzazioniConcessioniRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.GiorniMercatoPresenzeRestHelper;
import it.gruppoinit.pal.gp.core.service.helper.MercatiPresenzeStoricoRestHelper;
import it.gruppoinit.pal.gp.core.utils.TimeCalculator;

@Service
public class MercatipresenzeStoricoServiceImpl extends BaseServiceImpl<MercatipresenzeStorico, PkId> implements MercatipresenzeStoricoService {

    private static final Logger log = LoggerFactory.getLogger(MercatipresenzeStoricoServiceImpl.class);
    private MercatipresenzeStoricoDAO mercatipresenzeStoricoDAO;

    @Autowired
    public void setMercatipresenzeStoricoDAO(MercatipresenzeStoricoDAO mercatipresenzeStoricoDAO) {

	this.mercatipresenzeStoricoDAO = mercatipresenzeStoricoDAO;
    }

    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private AutorizzazioniService autorizzazioniService;
    @Autowired
    private IstanzeService istanzeService;
    @Autowired
    private MercatiService mercatiService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private MercatiUsoService mercatiUsoService;
    @Autowired
    private AnagrafeService anagrafeService;

    @Override
    public void delete(MercatipresenzeStorico entity) {

	// §§§BEGIN§§§
	mercatipresenzeStoricoDAO.delete(entity);
	// §§§END§§§
    }

    @Override
    public List<MercatipresenzeStorico> findAll(Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	return mercatipresenzeStoricoDAO.findAll(null, null);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatipresenzeStorico findById(PkId id) {

	// §§§BEGIN§§§
	return mercatipresenzeStoricoDAO.findById(id);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public void insert(MercatipresenzeStorico entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity, true)) {
	    mercatipresenzeStoricoDAO.insert(entity);
	}
	// §§§END§§§
    }

    private void dataIntegration(MercatipresenzeStorico entity) {

	fixMergeEntityProperties(entity);
	if (entity.getData() != null) {
	    Calendar c = Calendar.getInstance();
	    c.setTime(entity.getData());
	    entity.setAnno(c.get(Calendar.YEAR));
	}
    }

    protected boolean validateEntity(MercatipresenzeStorico entity, boolean isInsert) {

	super.validateEntity(entity);
	List<InvalidValue> ivs = new ArrayList<InvalidValue>();
	if (entity.getAnno() == null) {
	    InvalidValue iv = new InvalidValue("alert.required", entity.getClass(), "anno", null, entity);
	    ivs.add(iv);
	}
	if (entity.getData() == null) {
	    InvalidValue iv = new InvalidValue("alert.required", entity.getClass(), "data", null, entity);
	    ivs.add(iv);
	}
	Calendar c = Calendar.getInstance();
	c.setTime(entity.getData());
	Integer annoData = c.get(Calendar.YEAR);
	if (annoData.intValue() != entity.getAnno().intValue()) {
	    InvalidValue iv = new InvalidValue("L'anno specificato non è lo stesso della data", entity.getClass(), "data", null, entity);
	    ivs.add(iv);
	}
	if (isInsert && !this.findByIndiceUnivoco(entity.getMercato().getId().getCodice(), entity.getMercatoUso().getId().getCodice(),
		entity.getAnagrafe().getId().getCodice(), entity.getAutorizzazioni().getId().getCodice(), entity.getData()).isEmpty()) {
	    InvalidValue iv = new InvalidValue("Esiste già una riga di storico per questi dati. E' necessario aggiornare i dati già presenti.",
		    entity.getClass(), "data", null, entity);
	    ivs.add(iv);
	}
	if (!ivs.isEmpty()) {
	    throwValidationMessages(ivs);
	}
	return true;
    }

    @Override
    protected void fixMergeEntityProperties(MercatipresenzeStorico entity) {

	Mercati mercato = mercatiService.bindDomainObject(entity.getMercato(), PkId.class, "id.codice");
	entity.setMercato(mercato);
	MercatiUso mu = mercatiUsoService.bindDomainObject(entity.getMercatoUso(), PkId.class, "id.codice");
	entity.setMercatoUso(mu);
	Anagrafe an = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(an);
	MercatiD mercatod = mercatiDService.bindDomainObject(entity.getPosteggio(), PkId.class, "id.codice");
	entity.setPosteggio(mercatod);
	Autorizzazioni a = autorizzazioniService.bindDomainObject(entity.getAutorizzazioni(), PkId.class, "id.codice");
	entity.setAutorizzazioni(a);
    }

    @Override
    public void update(MercatipresenzeStorico entity) {

	// §§§BEGIN§§§
	dataIntegration(entity);
	if (validateEntity(entity, false)) {
	    mercatipresenzeStoricoDAO.update(entity);
	}
	// §§§END§§§
    }

    @Override
    protected Class<MercatipresenzeStorico> getEntityClass() {

	return MercatipresenzeStorico.class;
    }

    @Override
    public List<Integer> findAnniDaStorico() {

	// §§§BEGIN§§§
	return mercatipresenzeStoricoDAO.findAnniDaStorico();
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public Set<Integer> findAnni() {

	// §§§BEGIN§§§
	Set<Integer> listaAnni = new LinkedHashSet<Integer>();
	List<Integer> anniDaStorico = this.findAnniDaStorico();
	List<MercatipresenzeT> anniDaCalendari = mercatipresenzeTService.findAnniMercatiPresenti();
	for (MercatipresenzeT mercatipresenzeT : anniDaCalendari) {
	    listaAnni.add(mercatipresenzeT.getAnno());
	}
	for (Integer anno : anniDaStorico) {
	    listaAnni.add(anno);
	}
	return listaAnni;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatiPresenzeDTO findSommaDellePresenze(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno) {

	// §§§BEGIN§§§
	MercatiPresenzeDTO presenze = new MercatiPresenzeDTO();
	if (mercato.getManifestazione().getComportamentoPresenze().intValue() == WebConstants.MANIFESTAZIONE_COMPORTAMENTO_FIERA) {
	    // calcolare l'ultimo giorno di ogni anno della fiera
	    List<MercatiAnnoGiornoDTO> list = null;
	    Integer tipoCalcoloPresenze = mercato.getTipoconteggioPresenze();
	    if (tipoCalcoloPresenze == null) {
		tipoCalcoloPresenze = WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_PER_OGNI_GIORNATA;
	    }
	    if (tipoCalcoloPresenze.equals(WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_PER_OGNI_GIORNATA)) {
		list = mercatipresenzeTService.findUltimoGiornoFieraPerAnno(mercato, uso, anno, true);
	    } else {
		// in caso di tipoconteggio == 2 non considero l'uso
		list = mercatipresenzeTService.findUltimoGiornoFieraPerAnno(mercato, null, anno, false);
	    }
	    // calcolo presenze per fiere
	    for (MercatiAnnoGiornoDTO mercatiAnnoGiornoDTO : list) {
		MercatipresenzeT giorno = new MercatipresenzeT();
		giorno.setDataRegistrazione(mercatiAnnoGiornoDTO.getGiornoReg());
		MercatiPresenzeDTO presenzeDaiCalendari = null;
		if (tipoCalcoloPresenze.equals(WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_PER_OGNI_GIORNATA)) {
		    presenzeDaiCalendari = mercatipresenzeTService.findSommaDellePresenzeDaiCalendari(autorizzazione, mercato, uso, posteggio,
			    catMerc, mercatiAnnoGiornoDTO.getAnnoReg(), giorno);
		} else {
		    // in caso di tipoconteggio == 2 non considero il giorno e l'uso
		    presenzeDaiCalendari = mercatipresenzeTService.findSommaDellePresenzeDaiCalendari(autorizzazione, mercato, null, posteggio,
			    catMerc, mercatiAnnoGiornoDTO.getAnnoReg(), null);
		    // SE TORNA PIù DI UNA PRESENZA PER ANNO NON VA BENE PERCHE' DEVONO ESSERE RAGGRUPPATE PER ANNO
		    if (presenzeDaiCalendari.getPresenze() > 1) {
			presenzeDaiCalendari.setPresenze(1);
		    }
		    if (presenzeDaiCalendari.getPresenzeComeProprietario() > 1) {
			presenzeDaiCalendari.setPresenzeComeProprietario(1);
		    }
		}
		// sommo le presenze di ogni anno
		presenze.addPresenze(presenzeDaiCalendari.getPresenze());
		presenze.addPresenzeComeProprietario(presenzeDaiCalendari.getPresenzeComeProprietario());
	    }
	} else {
	    // calcolo presenze per mercati
	    presenze = mercatipresenzeTService.findSommaDellePresenzeDaiCalendari(autorizzazione, mercato, uso, posteggio, catMerc, anno, null);
	}
	// calcolo presenze da storico
	MercatiPresenzeDTO presenzeDaStorico = this.findSommaDellePresenzeDaStorico(autorizzazione, mercato, uso, posteggio, catMerc, anno);
	// sommo alle presenze dei calendari quelle dello storico
	presenze.addPresenze(presenzeDaStorico.getPresenze());
	presenze.addPresenzeComeProprietario(presenzeDaStorico.getPresenzeComeProprietario());
	return presenze;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public MercatiPresenzeDTO findSommaDellePresenzeDaStorico(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, MercatiD posteggio,
	    String catMerc, Integer anno) {

	// §§§BEGIN§§§
	return mercatipresenzeStoricoDAO.findSommaDellePresenzeDaStorico(autorizzazione, mercato, uso, posteggio, catMerc, anno);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@ 
    }

    @Override
    public MercatiPresenzeDTO getSommaDellePresenze(Integer codiceIstanza, Autorizzazioni estremi, String catMerc, boolean inserisciAutSeNonTrovata,
	    Integer codiceMercato, Integer codiceUso, boolean recuperaCodiceUsoSeNull, Integer presenzeDaAggiungere) {

	// §§§BEGIN§§§
	log.debug("getSommaDellePresenze: codiceistanza={}, catmerc={},inserisciAut={}",
		new Object[] { codiceIstanza, catMerc, inserisciAutSeNonTrovata });
	MercatiPresenzeDTO dto = null;
	Istanze istanza = null;
	if (codiceIstanza != null) {
	    istanza = istanzeService.findById(new PkId(codiceIstanza));
	}
	if (codiceMercato == null) {
	    if (istanza != null && istanza.getAlberoproc().getMercato() != null) {
		codiceMercato = istanza.getAlberoproc().getMercato().getId().getCodice();
	    } else {
		log.error("getSommaDellePresenze: Attenzione sull'albero dei procedimenti non è configurato il mercato");
		throw new RuntimeException("Attenzione sull'albero dei procedimenti non è configurato il mercato");
	    }
	}
	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
	if (mercato == null) {
	    log.error("getSommaDellePresenze: Attenzione il codice mercato non è corretto: {}", codiceMercato);
	    throw new RuntimeException("Attenzione il codice mercato non è corretto: " + codiceMercato);
	}
	if (codiceUso == null) {
	    if (recuperaCodiceUsoSeNull) {
		if (istanza != null && istanza.getAlberoproc().getMercato() != null) {
		    codiceUso = istanza.getAlberoproc().getMercatoUso().getId().getCodice();
		} else {
		    log.error("getSommaDellePresenze: Attenzione sull'albero dei procedimenti non è configurato l'uso del mercato");
		    throw new RuntimeException("Attenzione sull'albero dei procedimenti non è configurato il mercato");
		}
	    } else {
		List<MercatiUso> usiPerMercato = mercatiUsoService.findByMercato(codiceMercato);
		if (usiPerMercato.size() != 1) {
		    //se mercato ha solo un uso allora prendo quello altrimenti rilancio errore
		    String msg = "getSommaDellePresenze: E' stato passato un codice uso null e non è possibile recuperarlo dalla configurazione per via di recuperaCodiceUsoSeNull = false (Sono presenti " +
				 usiPerMercato.size() + " MercatiUso)";
		    log.error(msg);
		    throw new RuntimeException(msg);
		}
		codiceUso = usiPerMercato.get(0).getId().getCodice();
	    }
	}
	MercatiUso uso = mercatiUsoService.findById(new PkId(codiceUso));
	if (uso == null) {
	    log.error("getSommaDellePresenze: Attenzione il codice uso del mercato non è corretto: {}", codiceUso);
	    throw new RuntimeException("Attenzione il codice uso del mercato non è corretto: " + codiceUso);
	}
	if (mercatipresenzeTService.checkSeUsareCatMerc(mercato.getManifestazione()) && StringUtils.isBlank(catMerc)) {
	    throw new RuntimeException("Specificare la categoria merceologica.");
	}
	Autorizzazioni autorizzazione = null;
	boolean inseritaAutorizzazione = false;
	boolean inseritoSpuMerc = false;
	boolean inseritePresenze = false;
	if (BooleanUtils.isFalse(inserisciAutSeNonTrovata)) {
	    log.debug("getSommaDellePresenze: prima di autorizzazioniService.findAutOConcPerMercatiWS");
	    autorizzazione = autorizzazioniService.findAutOConcPerMercatiWS(estremi);
	} else {
	    log.debug("getSommaDellePresenze: prima di autorizzazioniService.insertAutOConcPerMercatiWS(istanza, estremi)");
	    autorizzazione = autorizzazioniService.insertAutOConcPerMercatiWS(istanza, estremi, catMerc);
	    inseritaAutorizzazione = true;
	    //prendo l'anno di rilascio dell'autorizzazione
	    if (presenzeDaAggiungere != null && presenzeDaAggiungere > 0) {
		SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
		String currentYear = getYearFormat.format(autorizzazione.getAutorizdata());
		//inserisce spuntisti_mercati, solo se non presente nella tabella e se attiva la graduatoria spuntisti.
		// TODO FIX: TODINI/BOCCI 20240219 ERA SBAGLIATA LA LOGICA
		// 1. LA CONFIGURAZIONE DOVREBBE ESSERE MODIFICATA A SECONDA DELLA TIPOLOGIA DI MANIFESTAZIONE
		//		if (mercatiConfigurazioneService.isAttivaConfigurazioneSpuntista()) {
		//		    SpuntistiMercati spuntistiMercati = spuntistiMercatiService.findAutMercatoAndUso(autorizzazione.getId().getCodice(),
		//			    codiceMercato, codiceUso);
		//		    if (spuntistiMercati == null) {
		//			spuntistiMercatiService.insertSpuntistiMercati(codiceIstanza, autorizzazione.getId().getCodice());
		//			inseritoSpuMerc = true;
		//		    }
		//		}
		//Metodo che a partire da autorizzazione, Mercato, Uso e anno ritorna lo storico
		//Se non trova record inserisce le presenze su MercatipresenzeStorico
		List<MercatipresenzeStorico> storici = this.findByAutoMercUsoAndAnno(autorizzazione, mercato, uso, Integer.valueOf(currentYear));
		if (storici.isEmpty()) {
		    MercatipresenzeStorico mps = new MercatipresenzeStorico();
		    mps.setAnno(Integer.valueOf(currentYear));
		    mps.setAutorizzazioni(autorizzazione);
		    mps.setMercato(mercato);
		    mps.setMercatoUso(uso);
		    mps.setNumeropresenze(presenzeDaAggiungere);
		    mps.setNumPresProprietario(0);
		    mps.setAnagrafe(autorizzazione.getAnagrafe());
		    Calendar cal = Calendar.getInstance();
		    cal.set(Calendar.DAY_OF_YEAR, 1);
		    cal.set(Calendar.HOUR_OF_DAY, 0);
		    cal.set(Calendar.MINUTE, 0);
		    cal.set(Calendar.SECOND, 0);
		    cal.set(Calendar.MILLISECOND, 0);
		    Date firstDayOfYear = cal.getTime();
		    mps.setData(firstDayOfYear);
		    try {
			this.insert(mps);
			inseritePresenze = true;
		    } catch (Exception e) {
			log.error("Si è verificato un errore durante l'inserimento dello storico. " + e.getMessage(), e);
			throw new RuntimeException("Si è verificato un errore durante l'inserimento dello storico." + e.getMessage());
		    }
		}
	    }
	}
	//mercatipresenzeStoricoDAO.flush()
	//mercatipresenzeStoricoDAO.clear()
	//Commentato perchè non necessita di rilettura in quanto viene passata l'autorizzazione ma effettivamente viene usato solo l'id
	//autorizzazione = autorizzazioniService.findById(new PkId(autorizzazione.getId().getCodice()))
	dto = this.findSommaDellePresenze(autorizzazione, mercato, uso, null, catMerc, null);
	dto.setInseritoAutorizzazione(inseritaAutorizzazione);
	dto.setInseritoPresenze(inseritePresenze);
	dto.setInseritoSpuntistiMercato(inseritoSpuMerc);
	return dto;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeStorico> findByAutorizzazione(Autorizzazioni aut) {

	// §§§BEGIN§§§
	return mercatipresenzeStoricoDAO.findByAutorizzazione(aut);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeStorico> findSommaDellePresenze(MercatipresenzeStorico mercatipresenzeStorico) {

	// §§§BEGIN§§§
	List<MercatipresenzeStorico> listPresenze = new ArrayList<MercatipresenzeStorico>();
	Mercati mercato = null;
	if (mercatipresenzeStorico.getMercato() != null && mercatipresenzeStorico.getMercato().getId().getCodice() != null) {
	    mercato = mercatiService.findById(new PkId(mercatipresenzeStorico.getMercato().getId().getCodice()));
	    mercatipresenzeStorico.setMercato(mercato);
	}
	MercatiUso uso = null;
	if (mercatipresenzeStorico.getMercatoUso() != null && mercatipresenzeStorico.getMercatoUso().getId().getCodice() != null) {
	    uso = mercatiUsoService.findById(new PkId(mercatipresenzeStorico.getMercatoUso().getId().getCodice()));
	    mercatipresenzeStorico.setMercatoUso(uso);
	}
	Autorizzazioni autorizzazione = null;
	if (mercatipresenzeStorico.getAutorizzazioni() != null && mercatipresenzeStorico.getAutorizzazioni().getId().getCodice() != null) {
	    autorizzazione = autorizzazioniService.findById(new PkId(mercatipresenzeStorico.getAutorizzazioni().getId().getCodice()));
	    mercatipresenzeStorico.setAutorizzazioni(autorizzazione);
	}
	Anagrafe anagrafe = null;
	if (mercatipresenzeStorico.getAnagrafe() != null && mercatipresenzeStorico.getAnagrafe().getId().getCodice() != null) {
	    anagrafe = anagrafeService.findById(mercatipresenzeStorico.getAnagrafe().getId());
	    mercatipresenzeStorico.setAnagrafe(anagrafe);
	}
	MercatiD posteggio = null;
	if (mercatipresenzeStorico.getPosteggio() != null && mercatipresenzeStorico.getPosteggio().getId().getCodice() != null) {
	    posteggio = mercatiDService.findById(new PkId(mercatipresenzeStorico.getPosteggio().getId().getCodice()));
	    mercatipresenzeStorico.setPosteggio(posteggio);
	}
	String catMerc = mercatipresenzeStorico.getCatMerc();
	Integer anno = Integer.valueOf(0);
	if (mercatipresenzeStorico.getAnno() != null) {
	    anno = mercatipresenzeStorico.getAnno();
	}
	Collection<Mercati> mercati = new ArrayList<Mercati>();
	if (mercato == null) {
	    mercati = mercatiService.findAllMercatiAttivi(null, null);
	} else {
	    mercati.add(mercato);
	}
	for (Mercati _mercato : mercati) {
	    boolean isAssegnaPresenzaSingola = false;
	    if (_mercato.getTipoconteggioPresenze() != null
		    && _mercato.getTipoconteggioPresenze().equals(WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_SINGOLA)) {
		isAssegnaPresenzaSingola = true;
		// IN CASO DI CONTEGGIO == 2 (MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_SINGOLA) DEVO CONTEGGIARE E PRESENTARE I RECORD INDIPENDENTEMENTE DALL'USO
	    }
	    Collection<MercatiUso> mercatoUsi = new ArrayList<MercatiUso>();
	    if (!isAssegnaPresenzaSingola) {
		if (uso == null) {
		    mercatoUsi = mercatiUsoService.findByMercato(_mercato);
		} else {
		    mercatoUsi.add(uso);
		}
	    } else {
		mercatoUsi = new ArrayList<MercatiUso>();
		mercatoUsi.add(new MercatiUso());
	    }
	    for (MercatiUso _uso : mercatoUsi) {
		if (isAssegnaPresenzaSingola) {
		    _uso = null;
		}
		if (autorizzazione != null) {
		    MercatiPresenzeDTO dto = this.findSommaDellePresenze(autorizzazione, _mercato, _uso, posteggio, catMerc, anno);
		    if ((dto.getPresenze() != null && dto.getPresenze() != 0)
			    || (dto.getPresenzeComeProprietario() != null && dto.getPresenzeComeProprietario() != 0)) {
			MercatipresenzeStorico presenza = new MercatipresenzeStorico();
			presenza.setNumPresProprietario(dto.getPresenzeComeProprietario());
			presenza.setNumeropresenze(dto.getPresenze());
			presenza.setAutorizzazioni(autorizzazione);
			Anagrafe a = anagrafeService.findById(new PkId(autorizzazione.getAnagrafe().getId().getCodice()));
			presenza.setAnagrafe(a);
			presenza.setAnno(mercatipresenzeStorico.getAnno());
			presenza.setCatMerc(mercatipresenzeStorico.getCatMerc());
			presenza.setMercato(_mercato);
			presenza.setMercatoUso(_uso);
			MercatiD p = mercatiDService.findById(new PkId(mercatipresenzeStorico.getPosteggio().getId().getCodice()));
			presenza.setPosteggio(p);
			listPresenze.add(presenza);
		    }
		} else {
		    List<Integer> mtts = mercatipresenzeStoricoDAO.findAutorizzazioniPerCalcoloPresenze(_mercato, _uso, posteggio, catMerc, anno);
		    for (Integer autid : mtts) {
			Autorizzazioni aut = autorizzazioniService.findById(new PkId(autid));
			MercatiPresenzeDTO dto = this.findSommaDellePresenze(aut, _mercato, _uso, posteggio, catMerc, anno);
			if ((dto.getPresenze() != null && dto.getPresenze() != 0)
				|| (dto.getPresenzeComeProprietario() != null && dto.getPresenzeComeProprietario() != 0)) {
			    MercatipresenzeStorico presenza = new MercatipresenzeStorico();
			    presenza.setNumPresProprietario(dto.getPresenzeComeProprietario());
			    presenza.setNumeropresenze(dto.getPresenze());
			    presenza.setAutorizzazioni(aut);
			    Anagrafe a = anagrafeService.findById(new PkId(aut.getAnagrafe().getId().getCodice()));
			    presenza.setAnagrafe(a);
			    presenza.setAnno(mercatipresenzeStorico.getAnno());
			    presenza.setCatMerc(mercatipresenzeStorico.getCatMerc());
			    presenza.setMercato(_mercato);
			    presenza.setMercatoUso(_uso);
			    MercatiD p = mercatiDService.findById(new PkId(mercatipresenzeStorico.getPosteggio().getId().getCodice()));
			    presenza.setPosteggio(p);
			    listPresenze.add(presenza);
			}
		    }
		}
	    }
	}
	return listPresenze;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    public List<MercatipresenzeStorico> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	// §§§BEGIN§§§
	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return mercatipresenzeStoricoDAO.findByFilterTable(filterTable, firstResult, maxResult);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return new java.util.ArrayList();@@@ENDALTERNATIVEEXIT@@@
    }

    //    @Override
    //    public List<MercatiPresenzeStoricoRestHelper> findSommaDellePresenze(Integer codiceMercato, Integer codiceMercatoUso, String cfAnagrafe) {
    //
    //	List<MercatiPresenzeStoricoRestHelper> ret = new ArrayList<MercatiPresenzeStoricoRestHelper>();
    //	MercatipresenzeStorico mercatipresenzeStorico = new MercatipresenzeStorico();
    //	Mercati mercato = mercatiService.findById(new PkId(codiceMercato));
    //	mercatipresenzeStorico.setMercato(mercato);
    //	MercatiUso mercatoUso = mercatiUsoService.findById(new PkId(codiceMercatoUso));
    //	mercatipresenzeStorico.setMercatoUso(mercatoUso);
    //	MercatiPresenzeStoricoRestHelper mph = new MercatiPresenzeStoricoRestHelper();
    //	List<MercatipresenzeStorico> prs = this.findSommaDellePresenze(mercatipresenzeStorico);
    //	for (MercatipresenzeStorico mp : prs) {
    //	    if (mp.getAnagrafe() != null && mp.getAnagrafe().getCodicefiscale().equalsIgnoreCase(cfAnagrafe)) {
    //		MercatiPresenzeStoricoRestHelper mph = new MercatiPresenzeStoricoRestHelper();
    //		AutorizzazioniConcessioniRestHelper aut = autorizzazioniService.newAutorizzazioniHelper(mp.getAutorizzazioni());
    //		mph.setAut(aut);
    //		mph.setNumPresenze(mp.getNumeropresenze());
    //		mph.setNumPresenzeComeProprietario(mp.getNumPresProprietario());
    //		ret.add(mph);
    //	    }
    //	}
    //	return ret;
    //    }
    @Override
    public List<MercatiPresenzeStoricoRestHelper> findSommaDellePresenze(String cf) {

	List<AutorizzazioniConcessioniRestHelper> auts = autorizzazioniService.findByCodiceFiscaleAnagrafe(cf, null, null);
	// tiene la lista dei mercato
	Map<Integer, MercatiPresenzeStoricoRestHelper> m = new HashMap<Integer, MercatiPresenzeStoricoRestHelper>();
	// tiene la lista dei giorni per il mercato
	Map<Integer, List<GiorniMercatoPresenzeRestHelper>> gm = new HashMap<Integer, List<GiorniMercatoPresenzeRestHelper>>();
	List<MercatiPresenzeStoricoRestHelper> ret = new ArrayList<MercatiPresenzeStoricoRestHelper>();
	for (AutorizzazioniConcessioniRestHelper aut : auts) {
	    MercatipresenzeStorico mercatipresenzeStorico = new MercatipresenzeStorico();
	    Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(aut.getId()));
	    mercatipresenzeStorico.setAutorizzazioni(autorizzazioni);
	    List<MercatipresenzeStorico> prs = this.findSommaDellePresenze(mercatipresenzeStorico);
	    for (MercatipresenzeStorico mp : prs) {
		Integer codiceMercato = mp.getMercato().getId().getCodice();
		MercatiPresenzeStoricoRestHelper hlp = m.get(codiceMercato);
		if (hlp == null) {
		    hlp = new MercatiPresenzeStoricoRestHelper();
		    hlp.setCodiceMercato(mp.getMercato().getId().getCodice());
		    hlp.setDescrizioneMercato(mp.getMercato().getDescrizione());
		}
		List<GiorniMercatoPresenzeRestHelper> gms = gm.get(codiceMercato);
		if (gms == null) {
		    gms = new ArrayList<GiorniMercatoPresenzeRestHelper>();
		    gm.put(codiceMercato, gms);
		}
		Integer codiceGiorno = mp.getMercatoUso().getId().getCodice();
		GiorniMercatoPresenzeRestHelper gmprhl = null;
		for (GiorniMercatoPresenzeRestHelper gmprh : gms) {
		    if (gmprh.getCodiceGiorno().equals(codiceGiorno)) {
			gmprhl = gmprh;
			break;
		    }
		}
		if (gmprhl == null) {
		    gmprhl = new GiorniMercatoPresenzeRestHelper();
		    gmprhl.setCodiceGiorno(codiceGiorno);
		    gmprhl.setDescrizioneGiorno(mp.getMercatoUso().getDescrizione());
		    gms.add(gmprhl);
		}
		AutorizzazioniConcessioniPresenzeRestHelper autret = new AutorizzazioniConcessioniPresenzeRestHelper();
		autret.setAutorizzazione(aut);
		autret.setNumeroPresenze(mp.getNumeropresenze());
		autret.setNumeroPresenzeProprietario(mp.getNumPresProprietario());
		gmprhl.getPresenze().add(autret);
		m.put(codiceMercato, hlp);
	    }
	}
	for (Entry<Integer, MercatiPresenzeStoricoRestHelper> ah : m.entrySet()) {
	    Integer codiceMercato = ah.getKey();
	    MercatiPresenzeStoricoRestHelper hlp = ah.getValue();
	    List<GiorniMercatoPresenzeRestHelper> listgm = gm.get(codiceMercato);
	    hlp.setGiorniMercato(listgm);
	    ret.add(hlp);
	}
	return ret;
    }

    @Override
    public void updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato(Integer codiceAutorizzazione, Integer codiceMercato, Integer codiceuso) {

	mercatipresenzeStoricoDAO.updateAzzeraPresenzeStoricheByAutorizzazioneAndMercato(codiceAutorizzazione, codiceMercato, codiceuso);
    }

    @Override
    public List<AutorizzazioniPresenzeStoricoRestHelper> findSommaDellePresenzeSpuntisti(Anagrafe anagrafe) {

	Set<Integer> codiciAnagrafe = anagrafeService.findCodiciAnagraficheCollegate(anagrafe.getId().getCodice(),
		RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI);
	codiciAnagrafe.add(anagrafe.getId().getCodice());
	List<AutorizzazioniRestHelper> hlps = autorizzazioniService.findAutorizzazioniAnagrafiche(codiciAnagrafe, false, null, true);
	List<AutorizzazioniPresenzeStoricoRestHelper> result = new ArrayList<AutorizzazioniPresenzeStoricoRestHelper>();
	Set<Integer> autsS = new HashSet<Integer>();
	for (AutorizzazioniRestHelper arh : hlps) {
	    autsS.add(arh.getIdAutorizzazione());
	}
	List<PresenzeAutorizzazioniHelper> list = mercatipresenzeStoricoDAO.findPresenzeSpuntistiByAutorizzazioni(autsS, null);
	Map<Integer, AutorizzazioniPresenzeStoricoRestHelper> autorizs = new HashMap<Integer, AutorizzazioniPresenzeStoricoRestHelper>();
	Map<String, AutorizzazioniMercatiPresenzeStoricoRestHelper> mapMercatiPresenze = new HashMap<String, AutorizzazioniMercatiPresenzeStoricoRestHelper>();
	Map<Integer, Set<String>> autorizzazioniMercati = new HashMap<Integer, Set<String>>();
	for (PresenzeAutorizzazioniHelper pah : list) {
	    Integer idAutorizzazione = pah.getAutorizzazioneid();
	    AutorizzazioniPresenzeStoricoRestHelper hlp = autorizs.get(idAutorizzazione);
	    if (hlp == null) {
		hlp = new AutorizzazioniPresenzeStoricoRestHelper();
		hlp.setAut_numero(pah.getAutoriznumero());
		hlp.setAut_comune(pah.getComuneautorizzazione());
		hlp.setAut_originaria(pah.getAutorizzazioneoriginaria());
		hlp.setAut_precedente(pah.getAutprecedentenumero());
		hlp.setId_autorizzazione(idAutorizzazione);
	    }
	    String kMercatiPresenze = "A" + idAutorizzazione.intValue() + "-M" + pah.getMercato() + "-G" + pah.getGiorno();
	    AutorizzazioniMercatiPresenzeStoricoRestHelper amh = mapMercatiPresenze.get(kMercatiPresenze);
	    if (amh == null) {
		amh = new AutorizzazioniMercatiPresenzeStoricoRestHelper();
		amh.setMercato(pah.getMercato());
		amh.setGiorno(pah.getGiorno());
		amh.setPresenze(new ArrayList<PresenzeStoricoGiornataRestHelper>());
	    }
	    PresenzeStoricoGiornataRestHelper p = new PresenzeStoricoGiornataRestHelper();
	    p.setData(pah.getDatapresenzamercato());
	    p.setNumero_presenze(pah.getNumeropresenze());
	    p.setPosteggio(pah.getCodiceposteggio());
	    p.setSuperficie(pah.getSuperficie());
	    p.setId_presenza(pah.getPresenzaid());
	    amh.getPresenze().add(p);
	    mapMercatiPresenze.put(kMercatiPresenze, amh);
	    Set<String> s = autorizzazioniMercati.get(idAutorizzazione);
	    if (s == null) {
		s = new HashSet<String>();
	    }
	    s.add(kMercatiPresenze);
	    autorizzazioniMercati.put(idAutorizzazione, s);
	    autorizs.put(idAutorizzazione, hlp);
	}
	for (Map.Entry<Integer, AutorizzazioniPresenzeStoricoRestHelper> ph : autorizs.entrySet()) {
	    AutorizzazioniPresenzeStoricoRestHelper m = ph.getValue();
	    List<AutorizzazioniMercatiPresenzeStoricoRestHelper> mercati = new ArrayList<AutorizzazioniMercatiPresenzeStoricoRestHelper>();
	    Set<String> setMercati = autorizzazioniMercati.get(ph.getKey());
	    int numeropresenzeAutorizzazione = 0;
	    for (String kMercatiPresenze : setMercati) {
		AutorizzazioniMercatiPresenzeStoricoRestHelper amhr = mapMercatiPresenze.get(kMercatiPresenze);
		List<PresenzeStoricoGiornataRestHelper> presenze = amhr.getPresenze();
		int numeropresenzeMercato = 0;
		for (PresenzeStoricoGiornataRestHelper pgrh : presenze) {
		    numeropresenzeMercato += pgrh.getNumero_presenze();
		}
		numeropresenzeAutorizzazione += numeropresenzeMercato;
		amhr.setNumero_presenze_mercato(numeropresenzeMercato);
		mercati.add(amhr);
	    }
	    m.setNumero_presenze_autorizzazione(numeropresenzeAutorizzazione);
	    m.setMercati(mercati);
	    result.add(m);
	}
	return result;
    }

    @Override
    public Integer findConteggioUltimoAnnoDellePresenzeSpuntisti(Anagrafe anagrafe) {

	TimeCalculator t = new TimeCalculator("findConteggioUltimoAnnoDellePresenzeSpuntisti");
	log.error("findConteggioUltimoAnnoDellePresenzeSpuntisti==>START: {}", t.getTimeElapsed());
	Set<Integer> codiciAnagrafe = anagrafeService.findCodiciAnagraficheCollegate(anagrafe.getId().getCodice(),
		RicercaAnagraficeCollegateEnum.SOLO_ISTANZE_CON_AUTORIZZAZIONI_MERCATI);
	log.error("findConteggioUltimoAnnoDellePresenzeSpuntisti==>ANAGRAFICHE_COLLEGATE: {}", t.getTimeElapsed());
	codiciAnagrafe.add(anagrafe.getId().getCodice());
	List<AutorizzazioniRestHelper> hlps = autorizzazioniService.findAutorizzazioniAnagrafiche(codiciAnagrafe, false, null, true);
	log.error("findConteggioUltimoAnnoDellePresenzeSpuntisti==>AUTORIZZAZIONI_QUERY: {}", t.getTimeElapsed());
	Set<Integer> autsS = new HashSet<Integer>();
	for (AutorizzazioniRestHelper arh : hlps) {
	    autsS.add(arh.getIdAutorizzazione());
	}
	Integer anno = Calendar.getInstance().get(Calendar.YEAR);
	int result = 0;
	List<PresenzeAutorizzazioniHelper> list = mercatipresenzeStoricoDAO.findPresenzeSpuntistiByAutorizzazioni(autsS, anno);
	log.error("findConteggioUltimoAnnoDellePresenzeSpuntisti==>PRESENZE_SPUNTISTI_QUERY: {}", t.getTimeElapsed());
	for (PresenzeAutorizzazioniHelper pah : list) {
	    result += pah.getNumeropresenze() == null ? 0 : pah.getNumeropresenze().intValue();
	}
	log.error("findConteggioUltimoAnnoDellePresenzeSpuntisti==>STOP: {}", t.tempistiche());
	return result;
    }

    @Override
    public List<MercatipresenzeStorico> findByAutoMercUsoAndAnno(Autorizzazioni autorizzazione, Mercati mercato, MercatiUso uso, Integer anno) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", autorizzazione.getId().getCodice(), "autorizzazioni", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", mercato.getId().getCodice(), "mercato", Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codice", uso.getId().getCodice(), "mercatoUso", Integer.class));
	fr.addFilterField(FilterUtils.equals("anno", anno, Integer.class));
	ft.addRestriction(fr);
	List<MercatipresenzeStorico> list = mercatipresenzeStoricoDAO.findByFilterTable(ft);
	return list;
    }

    @Override
    public List<MercatipresenzeStorico> findByIndiceUnivoco(Integer codiceMercato, Integer codiceUso, Integer codiceAnagrafe,
	    Integer idAutorizzazione, Date dataStorico) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiId", codiceMercato, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiUsoId", codiceUso, Integer.class));
	fr.addFilterField(FilterUtils.equals("anagrafeId", codiceAnagrafe, Integer.class));
	fr.addFilterField(FilterUtils.equals("autorizzazioniId", idAutorizzazione, Integer.class));
	fr.addFilterField(FilterUtils.equals("data", dataStorico, Date.class));
	ft.addRestriction(fr);
	return mercatipresenzeStoricoDAO.findByFilterTable(ft);
    }
}
