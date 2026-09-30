/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.MercatiDAO;
import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ConfigurazionePreferenzeUsoPerMercatoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.MercatiEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiConsorzi;
import it.gruppoinit.pal.gp.core.domain.MercatiConti;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiElabpresenze;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.MercatiConsorziService;
import it.gruppoinit.pal.gp.core.service.MercatiElabpresenzeService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class MercatiServiceImpl extends BaseServiceImpl<Mercati, PkId> implements MercatiService {

    private static final Logger log = LoggerFactory.getLogger(MercatiServiceImpl.class);
    private MercatiDAO mercatiDAO;
    private OggettiService oggettiService;
    private MercatiDDAO mercatiDDAO;
    private MercatiConsorziService mercatiConsorziService;
    private MercatiElabpresenzeService mercatiElabpresenzeService;

    @Autowired
    public void setMercatiElabpresenzeService(MercatiElabpresenzeService mercatiElabpresenzeService) {

	this.mercatiElabpresenzeService = mercatiElabpresenzeService;
    }

    @Autowired
    public void setMercatiConsorziService(MercatiConsorziService mercatiConsorziService) {

	this.mercatiConsorziService = mercatiConsorziService;
    }

    @Autowired
    public void setMercatiDDAO(MercatiDDAO mercatiDDAO) {

	this.mercatiDDAO = mercatiDDAO;
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
		    mercatiDDAO.insert(posteggio);
		}
		mercatiDAO.insert(entity);
	    }
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
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

    private void dataIntegration(Mercati entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Bean Mercati non può essere nullo");
	}
	if (entity.getManifestazione() != null) {
	    if (entity.getManifestazione().getCodice() != null) {
		if (entity.getManifestazione().getCodice().intValue() == 1) { // 0=FIERE, 1=MERCATO
		    // il mercato può avere solamente il tipoconteggio 1
		    entity.setTipoconteggioPresenze(WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_PER_OGNI_GIORNATA);
		}
	    }
	}
	if (entity.getTipoconteggioPresenze() == null) {
	    entity.setTipoconteggioPresenze(WebConstants.MERCATI_CONTEGGIO_PRESENZE_ASSEGNA_PER_OGNI_GIORNATA);
	}
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
}
