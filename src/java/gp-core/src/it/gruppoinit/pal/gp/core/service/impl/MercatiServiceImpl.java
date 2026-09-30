/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.dataimport.web.helper.ImportRunContext;
import it.gruppoinit.dataimport.web.helper.PosteggioImportato;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ConfigurazionePreferenzeUsoPerMercatoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiConcessioniHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentriConc;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConsorzi;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiD2cAssegnaz;
import it.gruppoinit.pal.gp.core.domain.MercatiDAvvisi;
import it.gruppoinit.pal.gp.core.domain.MercatiDConti;
import it.gruppoinit.pal.gp.core.domain.MercatiDCritass;
import it.gruppoinit.pal.gp.core.domain.MercatiDattivitaistat;
import it.gruppoinit.pal.gp.core.domain.MercatiElabpresenze;
import it.gruppoinit.pal.gp.core.domain.MercatiResponsabili;
import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Stradario;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.MercatiRestConIdGiornataBean;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni.AutorizzazioniConcessioniService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriConcService;
import it.gruppoinit.pal.gp.core.service.AutorizzazioniSubentriService;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.ComuniassociatiService;
import it.gruppoinit.pal.gp.core.service.ConcessionicausaliService;
import it.gruppoinit.pal.gp.core.service.MercatiConsorziService;
import it.gruppoinit.pal.gp.core.service.MercatiContiService;
import it.gruppoinit.pal.gp.core.service.MercatiD2cAssegnazService;
import it.gruppoinit.pal.gp.core.service.MercatiDAvvisiService;
import it.gruppoinit.pal.gp.core.service.MercatiDContiService;
import it.gruppoinit.pal.gp.core.service.MercatiDCritassService;
import it.gruppoinit.pal.gp.core.service.MercatiDattivitaistatService;
import it.gruppoinit.pal.gp.core.service.MercatiElabpresenzeService;
import it.gruppoinit.pal.gp.core.service.MercatiResponsabiliService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiSpunteService;
import it.gruppoinit.pal.gp.core.service.MercatiUsoService;
import it.gruppoinit.pal.gp.core.service.MercatistradarioService;
import it.gruppoinit.pal.gp.core.service.helper.MercatiRestHelper;
import it.gruppoinit.pal.gp.core.service.rules.ServiceValidationRules;
import it.gruppoinit.pal.gp.core.service.rules.SigeproBusinessRules;

/**
 * @author francescop
 * 
 */
@Service
public class MercatiServiceImpl extends BaseServiceImpl<Mercati, PkId> implements MercatiService {

    private static final Logger log = LoggerFactory.getLogger(MercatiServiceImpl.class);
    private MercatiDAO mercatiDAO;
    private OggettiService oggettiService;
    private MercatiDService mercatiDService;
    private MercatiConsorziService mercatiConsorziService;
    private AutorizzazioniConcessioniService autorizzazioniConcessioniService;
    private MercatiElabpresenzeService mercatiElabpresenzeService;
    private MercatiResponsabiliService mercatiResponsabiliService;
    private MercatiUsoService mercatiUsoService;
    private MercatipresenzeTService mercatipresenzeTService;
    private MercatistradarioService mercatistradarioService;
    private MercatiSpunteService mercatiSpunteService;
    private MercatiDattivitaistatService mercatiDattivitaistatService;
    private MercatiDAvvisiService mercatiDAvvisiService;
    private MercatiDCritassService mercatiDCritassService;
    private MercatiD2cAssegnazService mercatiD2cAssegnazService;
    private MercatiContiService mercatiContiService;
    private MercatiDContiService mercatiDContiService;
    private ConcessionicausaliService concessionicausaliService;
    private AutorizzazioniSubentriService autorizzazioniSubentriService;
    private AutorizzazioniSubentriConcService autorizzazioniSubentriConcService;
    private ComuniassociatiService comuniassociatiService;
    private ComuniService comuniService;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setComuniassociatiService(ComuniassociatiService comuniassociatiService) {

	this.comuniassociatiService = comuniassociatiService;
    }

    @Autowired
    public void setMercatiContiService(MercatiContiService mercatiContiService) {

	this.mercatiContiService = mercatiContiService;
    }

    @Autowired
    public void setMercatiDContiService(MercatiDContiService mercatiDContiService) {

	this.mercatiDContiService = mercatiDContiService;
    }

    @Autowired
    public void setAutorizzazioniSubentriConcService(AutorizzazioniSubentriConcService autorizzazioniSubentriConcService) {

	this.autorizzazioniSubentriConcService = autorizzazioniSubentriConcService;
    }

    @Autowired
    public void setAutorizzazioniSubentriService(AutorizzazioniSubentriService autorizzazioniSubentriService) {

	this.autorizzazioniSubentriService = autorizzazioniSubentriService;
    }

    @Autowired
    public void setConcessionicausaliService(ConcessionicausaliService concessionicausaliService) {

	this.concessionicausaliService = concessionicausaliService;
    }

    @Autowired
    public void setMercatiDCritassService(MercatiDCritassService mercatiDCritassService) {

	this.mercatiDCritassService = mercatiDCritassService;
    }

    @Autowired
    public void setMercatiD2cAssegnazService(MercatiD2cAssegnazService mercatiD2cAssegnazService) {

	this.mercatiD2cAssegnazService = mercatiD2cAssegnazService;
    }

    @Autowired
    public void setMercatiDAvvisiService(MercatiDAvvisiService mercatiDAvvisiService) {

	this.mercatiDAvvisiService = mercatiDAvvisiService;
    }

    @Autowired
    public void setMercatiDattivitaistatService(MercatiDattivitaistatService mercatiDattivitaistatService) {

	this.mercatiDattivitaistatService = mercatiDattivitaistatService;
    }

    @Autowired
    public void setMercatiSpunteService(MercatiSpunteService mercatiSpunteService) {

	this.mercatiSpunteService = mercatiSpunteService;
    }

    @Autowired
    public void setMercatistradarioService(MercatistradarioService mercatistradarioService) {

	this.mercatistradarioService = mercatistradarioService;
    }

    @Autowired
    public void setMercatipresenzeTService(MercatipresenzeTService mercatipresenzeTService) {

	this.mercatipresenzeTService = mercatipresenzeTService;
    }

    @Autowired
    public void setMercatiUsoService(MercatiUsoService mercatiUsoService) {

	this.mercatiUsoService = mercatiUsoService;
    }

    @Autowired
    public void setMercatiResponsabiliService(MercatiResponsabiliService mercatiResponsabiliService) {

	this.mercatiResponsabiliService = mercatiResponsabiliService;
    }

    @Autowired
    public void setMercatiElabpresenzeService(MercatiElabpresenzeService mercatiElabpresenzeService) {

	this.mercatiElabpresenzeService = mercatiElabpresenzeService;
    }

    @Autowired
    public void setAutorizzazioniConcessioniService(AutorizzazioniConcessioniService autorizzazioniConcessioniService) {

	this.autorizzazioniConcessioniService = autorizzazioniConcessioniService;
    }

    @Autowired
    public void setMercatiConsorziService(MercatiConsorziService mercatiConsorziService) {

	this.mercatiConsorziService = mercatiConsorziService;
    }

    @Autowired
    public void setMercatiDService(MercatiDService mercatiDService) {

	this.mercatiDService = mercatiDService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setMercatiDAO(MercatiDAO mercatiDAO) {

	this.mercatiDAO = mercatiDAO;
    }

    @Override
    protected Class<Mercati> getEntityClass() {

	return Mercati.class;
    }

    @Override
    public void delete(Mercati entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    mercatiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    protected void childDelete(Mercati entity) {

	List<MercatiConsorzi> mercatiConsorzis = mercatiConsorziService.findByCodiceMercato(entity.getId().getCodice());
	for (MercatiConsorzi mercatiConsorzi : mercatiConsorzis) {
	    mercatiConsorziService.delete(mercatiConsorzi);
	}
	List<MercatiElabpresenze> melp = mercatiElabpresenzeService.findByMercati(entity.getId().getCodice());
	for (MercatiElabpresenze mercatiElabpresenze : melp) {
	    mercatiElabpresenzeService.delete(mercatiElabpresenze);
	}
	List<MercatiResponsabili> mrl = mercatiResponsabiliService.findByMercato(entity.getId().getCodice(), null, null);
	for (MercatiResponsabili mercatiResponsabili : mrl) {
	    mercatiResponsabiliService.delete(mercatiResponsabili);
	}
    }

    @Override
    public List<Mercati> findAll(Integer firstResult, Integer maxResult) {

	return mercatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Mercati findById(PkId id) {

	return mercatiDAO.findById(id);
    }

    @Override
    public void insert(Mercati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    if (entity.getNumeroPosteggi() == null) {
		mercatiDAO.insert(entity);
		// inserisce un mercato e e un un numero di posteggi che gli viene passato
	    } else {
		// recupera la lista dei codici dei posteggi creati
		mercatiDAO.insert(entity);
		List<String> listaCodiceposteggi = getCodicePosteggi(entity);
		MercatiD posteggio = null;
		for (Iterator<String> iterator = listaCodiceposteggi.iterator(); iterator.hasNext();) {
		    String codiceposteggio = iterator.next();
		    posteggio = new MercatiD();
		    posteggio.setCodiceposteggio(codiceposteggio);
		    posteggio.setMercati(entity);
		    mercatiDService.insert(posteggio);
		}
		// mercatiDAO.insert(entity);
	    }
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void insertDaImport(Mercati entity, ImportRunContext context) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Set<MercatiUso> mercatiUsos = entity.getMercatiUsos();
	    entity.setMercatiUsos(null);//Usato per evitare errori con hibernate in fase di insert
	    Set<MercatiD> posteggi = entity.getMercatiDs();
	    entity.setMercatiDs(null);//Usato per evitare errori con hibernate in fase di insert
	    mercatiDAO.insert(entity);
	    for (MercatiD posteggio : posteggi) {
		Integer codiceMercatoDOrig = posteggio.getId().getCodice();
		posteggio.setId(null);
		posteggio.setMercati(entity);
		mercatiDService.insert(posteggio);
		PosteggioImportato posteggioImportato = context.getPosteggiImportati().get(codiceMercatoDOrig);
		posteggioImportato.setCodiceMercatiDDestinazione(posteggio.getId().getCodice());
		context.putPosteggiImportati(posteggioImportato);
	    }
	    for (MercatiUso mercatiUso : mercatiUsos) {
		mercatiUso.setMercati(entity);
		mercatiUsoService.insert(mercatiUso);
	    }
	}
    }

    @Override
    public void update(Mercati entity) {

	dataIntegration(entity);
	if (validateEntity(entity) && validateAltriDati(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    mercatiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    protected boolean validateEntity(Mercati entity) {

	if (entity != null) {
	    ServiceValidationRules validationRule = (ServiceValidationRules) SigeproBusinessRules.getClassRules(ServiceValidationRules.class);
	    boolean doBusinessValidation = true;
	    if (validationRule != null) {
		doBusinessValidation = validationRule.getCustomRule(ServiceValidationRules.CustomRuleEnum.doBusinessValidation.name());
	    }
	    if (doBusinessValidation) {
		List<InvalidValue> ivs = new ArrayList<InvalidValue>();
		if (entity.getComune() == null || StringUtils.isBlank(entity.getComune().getCodicecomune())) {
		    InvalidValue iv = new InvalidValue("alert.required", entity.getClass(), "comune", null, entity);
		    ivs.add(iv);
		}
		if (!ivs.isEmpty()) {
		    throwValidationMessages(ivs);
		}
	    }
	}
	return super.validateEntity(entity);
    }

    private void dataIntegration(Mercati entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Bean Mercati non può essere nullo");
	}
	fixMergeEntityProperties(entity);
	if (entity.getManifestazione() != null && entity.getManifestazione().getComportamentoPresenze() != null
		&& entity.getManifestazione().getComportamentoPresenze().intValue() == WebConstants.MANIFESTAZIONE_COMPORTAMENTO_MERCATO) { // 0=FIERE, 1=MERCATO 
	    // il mercato può avere solamente il tipoconteggio 1
	    entity.setTipoconteggioPresenze(WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_PER_OGNI_GIORNATA);
	}
	if (entity.getTipoconteggioPresenze() == null) {
	    entity.setTipoconteggioPresenze(WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_PER_OGNI_GIORNATA);
	}
	if (entity.getComune() == null) {
	    List<Comuniassociati> cass = comuniassociatiService.findByIdcomune(ORMHelper.getIdcomune());
	    if (cass.size() == 1) {
		entity.setComune(cass.get(0).getComune());
	    }
	}
	if (entity.getFlagGestisciPosizioni() == null) {
	    entity.setFlagGestisciPosizioni(false);
	}
    }

    @Override
    protected void fixMergeEntityProperties(Mercati entity) {

	Comuni comune = comuniService.bindDomainObject(entity.getComune(), String.class, "codicecomune");
	entity.setComune(comune);
    }

    public List<Mercati> findByDescrizione(String descrizione) {

	return mercatiDAO.findByDescrizione(descrizione);
    }

    @Override
    public boolean verificaConfigurazioneContiMercato(Mercati mercato, int anno) {

	boolean answer = false;
	boolean flagContabilita = (mercato.getFlagContabilita() == null || mercato.getFlagContabilita().booleanValue() == false) ? false : true;
	// solo se flagContabilita è true controllo altrimenti torno true
	if (flagContabilita) {
	    Set<MercatiConti> mercatiContis = mercato.getMercatiContis();
	    for (MercatiConti mercatiConti : mercatiContis) {
		Integer annoDelConto = mercatiConti.getAnno();
		if (annoDelConto.intValue() == anno) {
		    answer = true;
		    break;
		}
	    }
	} else {
	    return true;
	}
	return answer;
    }

    @Override
    public List<Mercati> findByDescrizione(String descrizione, MercatiEnum mercatiEnum) {

	return mercatiDAO.findByDescrizione(descrizione, mercatiEnum);
    }

    @Override
    public List<Mercati> findByFlagContabilita(String descrizione, boolean isFlagContabilita, MercatiEnum mercatiEnum) {

	return mercatiDAO.findByFlagContabilita(descrizione, isFlagContabilita, mercatiEnum);
    }

    //    private Integer controllaCancellaOggetti(Mercati entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggetto())) {
    //	    if (!(null == entity.getOggetto().getId())) {
    //		if (!(null == entity.getOggetto().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggetto().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("MERCATI", "CODICEMERCATO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Mercati entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggetto())) {
    //		    if (!(null == entityCopy.getOggetto().getId())) {
    //			if (!(null == entityCopy.getOggetto().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggetto().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("MERCATI", "CODICEMERCATO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    // crea i codici per i posteggi secondo la logica che tutti i codice devono avere lunghezza di caratteri
    // pari a quello più grande.Per quelli minore di quello più grande si aggiugono tanti zeri quanti fino a portarli
    // uguali
    private List<String> getCodicePosteggi(Mercati entity) {

	StringBuffer codicePosteggio = new StringBuffer("");
	List<String> listaCodiciPosteggio = new ArrayList<String>();
	int ordineNumeroPosteggi = entity.getNumeroPosteggi().toString().length();
	for (int i = 1; i <= entity.getNumeroPosteggi(); i++) {
	    // vedo quanti caratteri di differenza ci sono tra l'ordine del numero dei posteggi e il posteggio che sto
	    // inserendo
	    int diff = ordineNumeroPosteggi - (String.valueOf(i).toString().length());
	    for (int j = 0; j < diff; j++) {
		// se la lunghezza è uguale significa che non serve il padding
		if (String.valueOf(i).length() == ordineNumeroPosteggi) {
		    break;
		    // aggiungo tanti zeri quanto è la differenza tra la lunghezza di "i" e quella dell'oerdine del
		    // numero di posteggi
		} else {
		    codicePosteggio.append(0);
		}
	    }
	    // creo il codice posteggio
	    codicePosteggio.append(String.valueOf(i));
	    listaCodiciPosteggio.add(codicePosteggio.toString());
	    // resetto la variabile
	    codicePosteggio = new StringBuffer("");
	}
	return listaCodiciPosteggio;
    }

    protected boolean isDeleteAllowed(Mercati entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	int concessioniPresenti = autorizzazioniConcessioniService.countConcessioniByCodiceMercato(entity.getId().getCodice());
	if (concessioniPresenti > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_SERVICE_ERROR_PRESENTI_CONCESSIONI_PER_MERCATO, null, null, "AUTORIZZAZIONI_CONCESSIONI",
		    null));
	}
	// controlli per la tabella MERCATI
	if (!entity.getAnagrafemercatipresenzes().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFEMERCATOPRESENZE", null));
	}
	if (!entity.getMercatipresenzeTs().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_T", null));
	}
	// FIXME controllare autorizzazioni,concessioni,subentri collegati!!!!
	// if (!entity.getConcessionis().isEmpty()) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CONCESSIONI", null));
	// }
	if (!entity.getAlberoprocs().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC", null));
	}
	if (!entity.getMercatiContis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATI_CONTI", null));
	}
	if (!entity.getMercatipresenzeStoricos().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_STORICO", null));
	}
	// Se trovo errori rilancio subito l'eccezione e blocco la cancallazione
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	// controlli per la tabella MERCATI_USO
	// controllo se al mercato è associato un uso
	Set<MercatiUso> mercatiUsos = entity.getMercatiUsos();
	if (!mercatiUsos.isEmpty()) {
	    // per ogni uso faccio i controlli sulle foreign key
	    for (Iterator<MercatiUso> iterator = mercatiUsos.iterator(); iterator.hasNext();) {
		MercatiUso mercatiUso = iterator.next();
		_ivs.add(new InvalidValue("mercati.service_error.foreign_uso", null, null, mercatiUso.getDescrizione(), null));
		if (!mercatiUso.getAnagrafemercatipresenzes().isEmpty()) {
		    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ANAGRAFEMERCATOPRESENZE", null));
		}
		if (!mercatiUso.getAlberoprocs().isEmpty()) {
		    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBEROPROC", null));
		}
		// FIXME controllare autorizzazioni,concessioni,subentri collegati!!!!
		// if (!mercatiUso.getConcessionis().isEmpty()) {
		// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "CONCESSIONI", null));
		// }
		if (!mercatiUso.getMercatipresenzeStoricos().isEmpty()) {
		    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_STORICO", null));
		}
		if (!mercatiUso.getMercatipresenzeTs().isEmpty()) {
		    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MERCATIPRESENZE_T", null));
		}
		if (!mercatiUso.getRegistrazionis().isEmpty()) {
		    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "REGISTRAZIONI", null));
		}
		// controllo se per il mercato uso corrente è presente un errore
		// se si aggiungo l'intestazione che indica per qualse uso sono presenti record collegati
		if (_ivs.size() == 1) {
		    _ivs.remove(0);
		}
	    }
	    // Se trovo errori rilancio subito l'eccezione e blocco la cancellazione
	    if (!_ivs.isEmpty()) {
		this.throwValidationMessages(_ivs);
	    }
	}
	// controlli per la tabella MERCATI_D
	// controllo se al mercato è associato un posteggio
	Set<MercatiD> mercatiDs = entity.getMercatiDs();
	boolean isError = false;
	String codiceposteggio = "";
	if (!mercatiDs.isEmpty()) {
	    // per ogni posteggio faccio i controlli sulle foreign key
	    for (Iterator<MercatiD> iterator = mercatiDs.iterator(); iterator.hasNext();) {
		MercatiD mercatiD = iterator.next();
		codiceposteggio = mercatiD.getCodiceposteggio();
		if (!mercatiD.getAnagrafemercatipresenzes().isEmpty()) {
		    isError = true;
		    break;
		}
		// FIXME controllare autorizzazioni,concessioni,subentri collegati!!!!
		// if (!mercatiD.getConcessionis().isEmpty()) {
		// isError = true;
		// break;
		// }
		if (!mercatiD.getMercatipresenzeDs().isEmpty()) {
		    isError = true;
		    break;
		}
		if (!mercatiD.getRegistrazionis().isEmpty()) {
		    isError = true;
		    break;
		}
		if (!mercatiD.getMercatidLettures().isEmpty()) {
		    isError = true;
		    break;
		}
		if (_ivs.size() == 1) {
		    _ivs.remove(0);
		    codiceposteggio = "";
		}
	    }
	    // Se trovo errori rilancio subito l'eccezione e blocco la cancellazione
	    if (isError) {
		_ivs.add(new InvalidValue("mercati.service_error.foreign_mercD", null, null, codiceposteggio, null));
		this.throwValidationMessages(_ivs);
	    }
	}
	return delete;
    }

    protected boolean validateAltriDati(Mercati entity) {

	Boolean insert = true;
	Time orarioIngressoInizio = null;
	Time orarioIngressoFine = null;
	Time orarioVenditaInizio = null;
	Time orarioVenditaFine = null;
	Time orarioSgomberoInizio = null;
	Time orarioSgombroFine = null;
	if (entity.getOraIngressoInizio() != null && !entity.getOraIngressoInizio().equals("")) {
	    orarioIngressoInizio = getTime(entity.getOraIngressoInizio());
	}
	if (entity.getOraIngressoFine() != null && !entity.getOraIngressoFine().equals("")) {
	    orarioIngressoFine = getTime(entity.getOraIngressoFine());
	}
	if (entity.getOraVenditaInizio() != null && !entity.getOraVenditaInizio().equals("")) {
	    orarioVenditaInizio = getTime(entity.getOraVenditaInizio());
	}
	if (entity.getOraVenditaFine() != null && !entity.getOraVenditaFine().equals("")) {
	    orarioVenditaFine = getTime(entity.getOraVenditaFine());
	}
	if (entity.getOraSgomberoInizio() != null && !entity.getOraSgomberoInizio().equals("")) {
	    orarioSgomberoInizio = getTime(entity.getOraSgomberoInizio());
	}
	if (entity.getOraSgomberoFine() != null && !entity.getOraSgomberoFine().equals("")) {
	    orarioSgombroFine = getTime(entity.getOraSgomberoFine());
	}
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (orarioIngressoInizio != null && orarioIngressoFine != null && orarioIngressoInizio.after(orarioIngressoFine)) {
	    _ivs.add(new InvalidValue("service_error.sequenza_orario_errata", entity.getClass(), "oraIngressoFine", entity, null));
	}
	if (orarioIngressoInizio != null && orarioVenditaInizio != null && orarioIngressoInizio.after(orarioVenditaInizio)) {
	    _ivs.add(new InvalidValue("service_error.sequenza_orario_errata", entity.getClass(), "oraVenditaInizio", entity, null));
	}
	if (orarioVenditaInizio != null && orarioVenditaFine != null && orarioVenditaInizio.after(orarioVenditaFine)) {
	    _ivs.add(new InvalidValue("service_error.sequenza_orario_errata", entity.getClass(), "oraVenditaFine", entity, null));
	}
	if (orarioVenditaFine != null && orarioSgomberoInizio != null && orarioVenditaFine.after(orarioSgomberoInizio)) {
	    _ivs.add(new InvalidValue("service_error.sequenza_orario_errata", entity.getClass(), "oraSgomberoInizio", entity, null));
	}
	if (orarioSgomberoInizio != null && orarioSgombroFine != null && orarioSgomberoInizio.after(orarioSgombroFine)) {
	    _ivs.add(new InvalidValue("service_error.sequenza_orario_errata", entity.getClass(), "oraSgomberoFine", entity, null));
	}
	// Se trovo errori rilancio subito l'eccezione e blocco la cancellazione
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return insert;
    }

    @SuppressWarnings("deprecation")
    private Time getTime(String hour) {

	String[] fild = hour.split(":");
	Time time = new Time(Integer.valueOf(fild[0]), Integer.valueOf(fild[1]), 0);
	return time;
    }

    @Override
    public List<Mercati> findAllMercatiAttivi(Integer firstResult, Integer maxResult) {

	return mercatiDAO.findAllMercatiAttivi(firstResult, maxResult);
    }

    @Override
    public boolean existsRecords(FilterTable filterTable) {

	return mercatiDAO.existsRecords(filterTable);
    }

    @Override
    public ConfigurazionePreferenzeUsoPerMercatoEnum isPreferenzaUsoConfigurata(Integer codiceMercato) {

	Mercati mercati = this.findById(new PkId(codiceMercato));
	if (mercati == null) {
	    throw new RuntimeException("Mercato non esistente");
	}
	if (EntityUtils.getNestedProperty(mercati.getDyn2Campi(), "id.codice") != null) {
	    return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_CONFIG_DA_MERCATI;
	} else {
	    if (log.isDebugEnabled()) {
		log.debug("isPreferenzaUsoConfigurata# Non è stata configura una presenza sull'uso per il mercato in esame");
	    }
	    return ConfigurazionePreferenzeUsoPerMercatoEnum.MERCATO_USO_NON_CONFIG_DA_MERCATI;
	}
    }

    @Override
    public List<MercatiRestHelper> findAttiviByDescrizione(String filtroDescrizione, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction a = new FilterRestriction();
	a.addFilterField(FilterUtils.equals("attivo", Boolean.TRUE, Boolean.class));
	if (!StringUtils.defaultString(filtroDescrizione, "-1").equalsIgnoreCase("-1")) {
	    a.addFilterField(FilterUtils.like("descrizione", filtroDescrizione));
	}
	ft.addRestriction(a);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	List<Mercati> ms = mercatiDAO.findByFilterTable(ft, firstResult, maxResults);
	List<MercatiRestHelper> ret = new ArrayList<MercatiRestHelper>();
	for (Mercati mercati : ms) {
	    List<MercatiUso> giorni = mercatiUsoService.findByMercato(mercati);
	    for (MercatiUso g : giorni) {
		MercatiRestHelper mh = newMercatiRestHelper(mercati, g);
		if (mercati.getOggetto() != null && mercati.getOggetto().getId() != null && mercati.getOggetto().getId().getCodice() != null) {
		    mh.setGestisciMappa(Boolean.TRUE);
		}
		ret.add(mh);
	    }
	}
	return ret;
    }

    private MercatiRestHelper newMercatiRestHelper(Mercati mercati, MercatiUso g) {

	MercatiRestHelper ret = new MercatiRestHelper();
	ret.setId(mercati.getId().getCodice());
	ret.setDescrizione(mercati.getDescrizione());
	CodiceDescrizioneBean giorno = new CodiceDescrizioneBean();
	giorno.setDescrizione(g.getDescrizione());
	giorno.setCodice(g.getId().getCodice().toString());
	ret.setGiorno(giorno);
	ret.setTipoManifestazione(mercati.getTipoMercato());
	List<String> stradario = popolaStradario(mercati);
	ret.setStradario(stradario);
	return ret;
    }

    private List<String> popolaStradario(Mercati mercati) {

	List<String> indirizzi = new ArrayList<String>();
	List<Mercatistradario> msl = mercatistradarioService.findByMercato(mercati);
	for (Mercatistradario ms : msl) {
	    Stradario stradario = ms.getStradario();
	    String indirizzo = stradario.getDescrizioneAndLocalita();
	    indirizzi.add(indirizzo);
	}
	return indirizzi;
    }

    @Override
    public List<Mercati> findByDescrizioneAndResponsabile(String descrizione, Integer codiceResponsabile, MercatiEnum mercatiEnum,
	    Integer firstResult, Integer maxResults) {

	return mercatiDAO.findByDescrizioneAndResponsabile(descrizione, codiceResponsabile, mercatiEnum, firstResult, maxResults);
    }

    @Override
    public Mercati findByPosteggio(Integer idPosteggio) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction a = new FilterRestriction();
	a.addFilterField(FilterUtils.equals("id.codice", idPosteggio, "mercatiDs", Integer.class));
	ft.addRestriction(a);
	List<Mercati> l = mercatiDAO.findByFilterTable(ft, 0, 1);
	if (l != null && l.isEmpty()) {
	    return l.get(0);
	}
	return null;
    }

    @Override
    public List<MercatiRestConIdGiornataBean> findAttiviOggi(String filtroDescrizione) {

	List<MercatipresenzeT> ms = mercatipresenzeTService.findGiornateOdierne();
	List<MercatiRestConIdGiornataBean> ret = new ArrayList<MercatiRestConIdGiornataBean>();
	for (MercatipresenzeT mpt : ms) {
	    MercatiRestConIdGiornataBean mr = new MercatiRestConIdGiornataBean();
	    MercatiRestHelper mh = newMercatiRestHelper(mpt.getMercato(), mpt.getMercatoUso());
	    if (mpt.getMercato().getOggetto() != null && mpt.getMercato().getOggetto().getId() != null
		    && mpt.getMercato().getOggetto().getId().getCodice() != null) {
		mh.setGestisciMappa(Boolean.TRUE);
	    }
	    mr.setIdGiornata(mpt.getId().getCodice());
	    mr.setMercatiRestHelper(mh);
	    ret.add(mr);
	}
	return ret;
    }

    @Override
    public void updateCopiaInfoMercato(Integer codiceMercato, boolean spostaConcessioni, Integer idCausaleCessazione, Integer idCausaleAcquisizione) {

	Mercati m = this.findById(new PkId(codiceMercato));
	if (m != null) {
	    Mercati copia = copiaInfo(m);
	    this.insert(copia);
	    List<MercatiUso> usos = mercatiUsoService.findByMercato(m);
	    MercatiUso copiaMu = null;
	    for (MercatiUso mercatiUso : usos) {
		copiaMu = copiaMercatiUso(mercatiUso);
		copiaMu.setMercati(copia);
		mercatiUsoService.insert(copiaMu);
	    }
	    List<MercatiD2cAssegnaz> mass = mercatiD2cAssegnazService.findByMercato(codiceMercato);
	    for (MercatiD2cAssegnaz mas : mass) {
		MercatiD2cAssegnaz copiaMas = copiaMercatiD2cAssegnaz(mas);
		copiaMas.setMercati(copia);
		mercatiD2cAssegnazService.insert(copiaMas);
	    }
	    List<MercatiD> mds = mercatiDService.findByMercato(m, PosteggiEnum.ALL);
	    for (MercatiD md : mds) {
		MercatiD copiaMd = copiaMercatiD(md);
		copiaMd.setMercati(copia);
		mercatiDService.insert(copiaMd);
		List<MercatiDattivitaistat> apss = mercatiDattivitaistatService.findAttivitaPosteggio(md.getId().getCodice());
		for (MercatiDattivitaistat mad : apss) {
		    MercatiDattivitaistat copiaMad = copiaMercatiDAttivitaIstat(mad);
		    copiaMad.setPosteggio(copiaMd);
		    copiaMad.setMercato(copia);
		    copiaMad.getId().setFkidposteggio(copiaMd.getId().getCodice());
		    copiaMad.getId().setFkcodicemercato(copia.getId().getCodice());
		    mercatiDattivitaistatService.insert(copiaMad);
		}
		List<MercatiDCritass> madss = mercatiDCritassService.findByPosteggio(md.getId().getCodice());
		for (MercatiDCritass mas : madss) {
		    MercatiDCritass copiaMas = copiaMercatiDCritass(mas);
		    copiaMas.setMercatiD(copiaMd);
		    mercatiDCritassService.insert(copiaMas);
		}
		List<MercatiDAvvisi> madds = mercatiDAvvisiService.findAllByPosteggio(md.getId().getCodice(), null, null);
		for (MercatiDAvvisi mad : madds) {
		    MercatiDAvvisi copiaMav = copiaMercatiAvvisi(mad);
		    copiaMav.setMercatiD(copiaMd);
		    mercatiDAvvisiService.insert(copiaMav);
		}
		List<MercatiDConti> mcs = mercatiDContiService.findByPosteggio(md);
		for (MercatiDConti mc : mcs) {
		    MercatiDConti copiaMC = copiaMercatiDConti(mc);
		    copiaMC.setPosteggio(copiaMd);
		    mercatiDContiService.insert(copiaMC);
		}
	    }
	    List<MercatiResponsabili> mrs = mercatiResponsabiliService.findByMercato(codiceMercato, null, null);
	    for (MercatiResponsabili mr : mrs) {
		MercatiResponsabili copiaMr = copiaMercatiresponsabili(mr);
		copiaMr.setMercato(copia);
		mercatiResponsabiliService.insert(copiaMr);
	    }
	    List<Mercatistradario> mss = mercatistradarioService.findByMercato(m);
	    for (Mercatistradario ms : mss) {
		Mercatistradario copiaMs = copiaMercatiStradario(ms);
		copiaMs.setMercato(copia);
		mercatistradarioService.insert(copiaMs);
	    }
	    List<MercatiConsorzi> mercatiConsorzis = mercatiConsorziService.findByCodiceMercato(codiceMercato);
	    for (MercatiConsorzi mc : mercatiConsorzis) {
		MercatiConsorzi copiaMc = copiaMercatiConsorzi(mc);
		copiaMc.setMercato(copia);
		mercatiConsorziService.insert(copiaMc);
	    }
	    List<MercatiSpunte> mssps = mercatiSpunteService.findByMercato(codiceMercato);
	    for (MercatiSpunte ms : mssps) {
		MercatiSpunte copiaMs = copiaMercatiSpunte(ms);
		copiaMs.setMercato(copia);
		mercatiSpunteService.insert(copiaMs);
	    }
	    // da non fare MercatiElabpresenze
	    //	    List<MercatiElabpresenze> melp = mercatiElabpresenzeService.findByMercati(codiceMercato);
	    //	    for (MercatiElabpresenze mep : melp) {
	    //		MercatiElabpresenze copiaMep = copiaMercatiElabPres(mep);
	    //		copiaMep.setMercati(copia);
	    //		mercatiElabpresenzeService.insert(copiaMep);
	    //	    }
	    List<MercatiConti> mcs = mercatiContiService.findByCodiceMercato(m.getId().getCodice());
	    for (MercatiConti mc : mcs) {
		MercatiConti copiaMC = copiaMercatiConti(mc);
		copiaMC.setMercati(copia);
		mercatiContiService.insert(copiaMC);
	    }
	    if (spostaConcessioni) {
		mercatiDAO.flush();
		mercatiDAO.clear();
		Concessionicausali cauAcq = concessionicausaliService.findById(new PkId(idCausaleAcquisizione));
		Concessionicausali cauCess = concessionicausaliService.findById(new PkId(idCausaleCessazione));
		List<AutorizzazioniConcessioni> concs = autorizzazioniConcessioniService.findConcessioniByMercato(m);
		for (AutorizzazioniConcessioni aut_conc : concs) {
		    aut_conc = autorizzazioniConcessioniService.findById(new PkId(aut_conc.getId().getCodice()));
		    Autorizzazioni a = aut_conc.getAutorizzazioniByFkAutconcAutatt();
		    AutorizzazioniSubentri sub = new AutorizzazioniSubentri();
		    sub.setAnagrafe(a.getAnagrafe());
		    sub.setAutorizzazioni(a);
		    sub.setAutorizcomune(a.getAutorizcomune());
		    sub.setAutorizdata(a.getAutorizdata());
		    sub.setAutorizdataregistr(a.getAutorizdataregistr());
		    sub.setAutoriznumero(a.getAutoriznumero());
		    sub.setAutorizresponsabile(a.getAutorizresponsabile());
		    sub.setDataCessazione(Calendar.getInstance().getTime());
		    sub.setDataRilascio(a.getDataRilascio());
		    sub.setDatascadenza(a.getDatascadenza());
		    sub.setIstanze(a.getIstanza());
		    sub.setMovimenti(a.getMovimenti());
		    sub.setTipologiaregistro(a.getTipologiaregistro());
		    sub.setConcessionicausaliByFkAutsubConccausAcq(cauAcq);
		    sub.setConcessionicausaliByFkAutsubConccausCess(cauCess);
		    autorizzazioniSubentriService.insert(sub);
		    AutorizzazioniSubentriConc subConc = new AutorizzazioniSubentriConc();
		    subConc.setAutorizzazioniByFkAutconcAutatt(a);
		    subConc.setAutorizzazioniSubentri(sub);
		    subConc.setConcessionitipi(aut_conc.getConcessionitipi());
		    subConc.setMercati(m);
		    subConc.setMercatiD(aut_conc.getMercatiD());
		    subConc.setMercatiUso(aut_conc.getMercatiUso());
		    subConc.setStagionalea(aut_conc.getStagionalea());
		    subConc.setStagionaleda(aut_conc.getStagionalea());
		    autorizzazioniSubentriConcService.insert(subConc);
		    aut_conc.setMercati(copia);
		    aut_conc.setMercatiUso(copiaMu);
		    autorizzazioniConcessioniService.update(aut_conc);
		    mercatiDAO.flush();
		    mercatiDAO.clear();
		}
	    }
	}
    }

    private MercatiDConti copiaMercatiDConti(MercatiDConti mc) {

	MercatiDConti copia = new MercatiDConti();
	copia.setAnno(mc.getAnno());
	copia.setContesto(mc.getContesto());
	copia.setConto(mc.getConto());
	copia.setFlagCanone(mc.getFlagCanone());
	copia.setFlagImportomensile(mc.getFlagImportomensile());
	copia.setFlagValore(mc.getFlagValore());
	copia.setPercentualeConsorzio(mc.getPercentualeConsorzio());
	copia.setValore(mc.getValore());
	return copia;
    }

    private MercatiConti copiaMercatiConti(MercatiConti mc) {

	MercatiConti copia = new MercatiConti();
	copia.setAnno(mc.getAnno());
	copia.setContesto(mc.getContesto());
	copia.setConti(mc.getConti());
	copia.setFlagCanone(mc.getFlagCanone());
	copia.setFlagImportomensile(mc.getFlagImportomensile());
	copia.setFlagValore(mc.getFlagValore());
	copia.setPercentualeConsorzio(mc.getPercentualeConsorzio());
	copia.setValore(mc.getValore());
	return copia;
    }

    private MercatiDCritass copiaMercatiDCritass(MercatiDCritass mas) {

	MercatiDCritass copia = new MercatiDCritass();
	copia.setDyn2Campi(mas.getDyn2Campi());
	copia.setFlagConsentito(mas.getFlagConsentito());
	copia.setValore(mas.getValore());
	copia.setValoredecodificato(mas.getValoredecodificato());
	return copia;
    }

    private MercatiDAvvisi copiaMercatiAvvisi(MercatiDAvvisi mad) {

	MercatiDAvvisi copia = new MercatiDAvvisi();
	copia.setAnagrafe(mad.getAnagrafe());
	copia.setFlagVerificato(mad.getFlagVerificato());
	copia.setTipicausalioneri(mad.getTipicausalioneri());
	return copia;
    }

    private MercatiD2cAssegnaz copiaMercatiD2cAssegnaz(MercatiD2cAssegnaz mas) {

	MercatiD2cAssegnaz copia = new MercatiD2cAssegnaz();
	copia.setDyn2Campi(mas.getDyn2Campi());
	copia.setDyn2CampiPrefPosteggio(mas.getDyn2CampiPrefPosteggio());
	copia.setDyn2Modellit(mas.getDyn2Modellit());
	return copia;
    }

    private MercatiSpunte copiaMercatiSpunte(MercatiSpunte ms) {

	MercatiSpunte copia = new MercatiSpunte();
	copia.setDescrizione(ms.getDescrizione());
	copia.setFlagFiltroCatmerc(ms.getFlagFiltroCatmerc());
	copia.setOrdine(ms.getOrdine());
	return copia;
    }

    private MercatiDattivitaistat copiaMercatiDAttivitaIstat(MercatiDattivitaistat mad) {

	MercatiDattivitaistat copia = new MercatiDattivitaistat();
	copia.setFlagConsentito(mad.isFlagConsentito());
	copia.setAttivita(mad.getAttivita());
	copia.setPosteggio(mad.getPosteggio());
	copia.getId().setFkcodiceattivitaistat(mad.getId().getFkcodiceattivitaistat());
	copia.getId().setFkidposteggio(mad.getId().getFkidposteggio());
	return copia;
    }

    private MercatiElabpresenze copiaMercatiElabPres(MercatiElabpresenze mep) {

	MercatiElabpresenze copia = new MercatiElabpresenze();
	copia.setAnno(mep.getAnno());
	copia.setDataElaborazione(mep.getDataElaborazione());
	return copia;
    }

    private MercatiConsorzi copiaMercatiConsorzi(MercatiConsorzi mc) {

	MercatiConsorzi copia = new MercatiConsorzi();
	copia.setConsorzio(mc.getConsorzio());
	copia.setDataFineEsercizio(mc.getDataFineEsercizio());
	copia.setDataInizioEsercizio(mc.getDataInizioEsercizio());
	return copia;
    }

    private Mercatistradario copiaMercatiStradario(Mercatistradario ms) {

	Mercatistradario copia = new Mercatistradario();
	copia.setCoefficienteViario(ms.getCoefficienteViario());
	copia.setMercato(ms.getMercato());
	copia.setStradario(ms.getStradario());
	return copia;
    }

    private MercatiResponsabili copiaMercatiresponsabili(MercatiResponsabili mr) {

	MercatiResponsabili copia = new MercatiResponsabili();
	copia.setResponsabili(mr.getResponsabili());
	return copia;
    }

    private MercatiD copiaMercatiD(MercatiD md) {

	MercatiD copia = new MercatiD();
	copia.setCodiceposteggio(md.getCodiceposteggio());
	copia.setConsentita(md.getConsentita());
	copia.setCoordinate(md.getCoordinate());
	copia.setDisabilitato(md.getDisabilitato());
	copia.setIdentificativoPercorso(md.getIdentificativoPercorso());
	copia.setLarghezza(md.getLarghezza());
	copia.setLunghezza(md.getLunghezza());
	copia.setNote(md.getNote());
	copia.setPeso(md.getPeso());
	copia.setPosizione(md.getPosizione());
	copia.setPosteggiSettori(md.getPosteggiSettori());
	copia.setStradario(md.getStradario());
	copia.setSuperficie(md.getSuperficie());
	copia.setTipoSpazio(md.getTipoSpazio());
	return copia;
    }

    private MercatiUso copiaMercatiUso(MercatiUso mercatiUso) {

	MercatiUso copia = new MercatiUso();
	copia.setConcessioniuso(mercatiUso.getConcessioniuso());
	copia.setDescrizione(mercatiUso.getDescrizione());
	copia.setGiornisettimana(mercatiUso.getGiornisettimana());
	copia.setPeso(mercatiUso.getPeso());
	return copia;
    }

    private Mercati copiaInfo(Mercati m) {

	Mercati copia = new Mercati();
	copia.setAttivo(m.isAttivo());
	copia.setCircoscrizione(m.getCircoscrizione());
	copia.setDescrizione("COPIA - " + m.getDescrizione());
	copia.setDetDirigenzialeData(m.getDetDirigenzialeData());
	copia.setDyn2Campi(m.getDyn2Campi());
	copia.setDyn2CampiPrefPosteggio(m.getDyn2CampiPrefPosteggio());
	copia.setFlagAttivanodoPagam(m.getFlagAttivanodoPagam());
	copia.setFlagConsorzio(m.getFlagConsorzio());
	copia.setFlagContabilita(m.getFlagContabilita());
	copia.setFlagGestisciPosizioni(m.getFlagGestisciPosizioni());
	copia.setFlagRegContAssenza(m.getFlagRegContAssenza());
	copia.setManifestazione(m.getManifestazione());
	copia.setNote(m.getNote());
	copia.setOggetto(m.getOggetto());
	copia.setOraIngressoFine(m.getOraIngressoFine());
	copia.setOraIngressoInizio(m.getOraIngressoInizio());
	copia.setOraSgomberoFine(m.getOraSgomberoFine());
	copia.setOraSgomberoInizio(m.getOraSgomberoInizio());
	copia.setOraVenditaFine(m.getOraVenditaFine());
	copia.setOraVenditaInizio(m.getOraVenditaInizio());
	copia.setSoftware(m.getSoftware());
	copia.setTipoconteggioPresenze(m.getTipoconteggioPresenze());
	copia.setTipoMercato(m.getTipoMercato());
	copia.setComune(m.getComune());
	return copia;
    }

    @Override
    public int countByCategorieMercato(Integer idCategoriaMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction a = new FilterRestriction();
	a.addFilterField(FilterUtils.equals("mercatiCategorieId", idCategoriaMercato, Integer.class));
	ft.addRestriction(a);
	return mercatiDAO.countRecord(ft);
    }

    @Override
    public Map<Integer, PosteggiConcessioniHelper> findPosteggiMercatoAllaData(Integer codiceMercato, Integer codiceUso, Date dataGiornataMercato) {

	Map<Integer, List<PosteggiConcessioniHelper>> daelaborare = new HashMap<Integer, List<PosteggiConcessioniHelper>>();
	List<PosteggiConcessioniHelper> hlps = mercatiDAO.findPosteggiMercatoAllaData(codiceMercato, codiceUso, dataGiornataMercato);
	// ripulisco le doppie autorizzazioni
	Map<Integer, List<PosteggiConcessioniHelper>> rimuoviAutDoppie = new HashMap<Integer, List<PosteggiConcessioniHelper>>();
	for (PosteggiConcessioniHelper pch : hlps) {
	    if (!pch.isPosteggiodisabilitato()) {
		Integer codiceConcessione = pch.getCodiceconcessione();
		List<PosteggiConcessioniHelper> p = rimuoviAutDoppie.get(codiceConcessione);
		if (p == null) {
		    p = new ArrayList<PosteggiConcessioniHelper>();
		}
		p.add(pch);
		rimuoviAutDoppie.put(codiceConcessione, p);
	    }
	}
	List<PosteggiConcessioniHelper> hlpselaborato = new ArrayList<PosteggiConcessioniHelper>();
	for (PosteggiConcessioniHelper pch : hlps) {
	    boolean aggiungi = true;
	    if (pch.getCodiceconcessione() != null && rimuoviAutDoppie.containsKey(pch.getCodiceconcessione())) {
		aggiungi = false;
	    }
	    if (aggiungi) {
		// ci metto solo quello dove le autorizzazioni non sono presenti più volte 
		hlpselaborato.add(pch);
	    }
	}
	if (!rimuoviAutDoppie.isEmpty()) {
	    List<PosteggiConcessioniHelper> hlpsautSistemate = sistemaAutorizzazioniDoppie(rimuoviAutDoppie, dataGiornataMercato);
	    hlpselaborato.addAll(hlpsautSistemate);
	}
	// 
	for (PosteggiConcessioniHelper pch : hlpselaborato) {
	    if (!pch.isPosteggiodisabilitato()) {
		Integer idPosteggio = pch.getIdposteggio();
		List<PosteggiConcessioniHelper> p = daelaborare.get(idPosteggio);
		if (p == null) {
		    p = new ArrayList<PosteggiConcessioniHelper>();
		}
		p.add(pch);
		daelaborare.put(idPosteggio, p);
	    }
	}
	Map<Integer, PosteggiConcessioniHelper> ret = new HashMap<Integer, PosteggiConcessioniHelper>();
	for (Entry<Integer, List<PosteggiConcessioniHelper>> e : daelaborare.entrySet()) {
	    Integer idPosteggio = e.getKey();
	    List<PosteggiConcessioniHelper> list = daelaborare.get(idPosteggio);
	    if (list.size() == 1) {
		ret.put(idPosteggio, list.get(0));
	    } else {
		ret.put(idPosteggio, getAttivoAllaData(list));
	    }
	}
	// ciclo i posteggi attivi per i quali non ho trovato record o eliminatohelper
	List<Integer> idPosteggi = mercatiDService.findByCodiceByMercato(codiceMercato, PosteggiEnum.ACTIVE);
	for (Integer idPosteggio : idPosteggi) {
	    if (ret.get(idPosteggio) == null) {
		PosteggiConcessioniHelper pchnew = new PosteggiConcessioniHelper();
		pchnew.setCodicemercato(codiceMercato);
		pchnew.setIdcomune(ORMHelper.getIdcomune());
		pchnew.setSoftware(ORMHelper.getSoftware());
		pchnew.setIdposteggio(idPosteggio);
		ret.put(idPosteggio, pchnew);
	    }
	}
	return ret;
    }

    private List<PosteggiConcessioniHelper> sistemaAutorizzazioniDoppie(Map<Integer, List<PosteggiConcessioniHelper>> rimuoviAutDoppie,
	    Date dataGiornataMercato) {

	// Ho una mappa con chiave idautorizzazione ne posso avere solo una in una giornata devo escludere le altre es
	//	idautsub	idcomune	codicemercato	iduso	idposteggio	codiceposteggio	nominativo	software	codiceconcessione	codiceistanza	codicetitolare	codiceoccupante	datacessazione	datafineaffitto	flagcausaliaffitto	posteggiodisabilitato
	//	99036574	D969	149	95	10689	01	_DAVIDE FARINELLA	CO	36574	309469	114881	114881	2123-11-13 13:00:42		0	0
	//	25459		D969	149	95	10690	02	_DAVIDE FARINELLA	CO	36574	309469	114881	114881	2023-11-07 00:00:00		0	0
	// in questo caso deve stare sul posteggio 02 e non 01
	List<PosteggiConcessioniHelper> ret = new ArrayList<PosteggiConcessioniHelper>();
	for (Entry<Integer, List<PosteggiConcessioniHelper>> e : rimuoviAutDoppie.entrySet()) {
	    ret.add(getAttivoAllaData(e.getValue()));
	}
	return ret;
    }

    private PosteggiConcessioniHelper getAttivoAllaData(List<PosteggiConcessioniHelper> lista) {

	/**
	 * ordinati per datacessazione asc, idsubentro asc
	 */
	Collections.sort(lista);
	return lista.get(0);
    }
}
