package it.gruppoinit.pal.gp.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Istanzedyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellidService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService.TipoControlloEnum;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitService;
import it.gruppoinit.pal.gp.core.service.ModelliDinamiciFormuleService;
import it.gruppoinit.pal.gp.core.utils.Utilities;

@Service
public class Istanzedyn2modellitServiceImpl extends BaseServiceImpl<Istanzedyn2modellit, Istanzedyn2modellitId>
	implements Istanzedyn2modellitService {

    private Dyn2CampiService dyn2CampiService;
    private Dyn2ModellidService dyn2ModellidService;
    private Dyn2ModellitService dyn2ModellitService;
    private Istanzedyn2modellitDAO istanzedyn2modellitDAO;
    private IstanzeService istanzeService;
    private Istanzedyn2datiService istanzedyn2datiService;
    private ModelliDinamiciFormuleService modelliDinamiciFormuleService;
    private OggettiService oggettiService;
    private static final Logger log = LoggerFactory.getLogger(Istanzedyn2modellitServiceImpl.class);

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setDyn2ModellidService(Dyn2ModellidService dyn2ModellidService) {

	this.dyn2ModellidService = dyn2ModellidService;
    }

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Autowired
    public void setIstanzedyn2datiService(Istanzedyn2datiService istanzedyn2datiService) {

	this.istanzedyn2datiService = istanzedyn2datiService;
    }

    @Autowired
    public void setIstanzedyn2modellitDAO(Istanzedyn2modellitDAO istanzedyn2modellitDAO) {

	this.istanzedyn2modellitDAO = istanzedyn2modellitDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setModelliDinamiciFormuleService(ModelliDinamiciFormuleService modelliDinamiciFormuleService) {

	this.modelliDinamiciFormuleService = modelliDinamiciFormuleService;
    }

    @Override
    protected Class<Istanzedyn2modellit> getEntityClass() {

	return Istanzedyn2modellit.class;
    }

    @Override
    public void delete(Istanzedyn2modellit entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    entity = this.findById(entity.getId());
	    istanzedyn2modellitDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Istanzedyn2modellit entity) {

	Dyn2Modellit modello = entity.getDyn2Modellit();
	modello = dyn2ModellitService.findById(modello.getId());
	List<Dyn2Modellid> righe = dyn2ModellidService.findRigheModello(modello.getId().getCodice());
	List<Istanzedyn2modellit> listaSchede = this.findByIstanza(entity.getIstanza().getId());
	for (Dyn2Modellid riga : righe) {
	    if (riga.getDyn2Campi() != null) {
		Integer codiceCampo = riga.getDyn2Campi().getId().getCodice();
		boolean isUsed = false;
		for (Istanzedyn2modellit istanzedyn2modellit : listaSchede) {
		    if (!istanzedyn2modellit.getId().getFkD2mtId().equals(modello.getId().getCodice())) {
			isUsed = dyn2ModellitService.checkCampoUsedForModello(modello.getId().getCodice(), codiceCampo);
		    }
		    if (isUsed) {
			break;
		    }
		}
		if (!isUsed) {
		    List<Istanzedyn2dati> datis = istanzedyn2datiService.findByIstanzaAndDyn2Campi(entity.getIstanza().getId().getCodice(),
			    codiceCampo);
		    for (Istanzedyn2dati istanzedyn2dati : datis) {
			istanzedyn2datiService.delete(istanzedyn2dati);
		    }
		}
	    }
	}
    }

    @Override
    public List<Istanzedyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Istanzedyn2modellit findById(Istanzedyn2modellitId id) {

	return istanzedyn2modellitDAO.findById(id);
    }

    @Override
    public List<Integer> findIdSchedeByIstanza(Integer idIstanza) {

	return istanzedyn2modellitDAO.findIdSchedeByIstanza(idIstanza);
    }

    @Override
    public void insert(Istanzedyn2modellit entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzedyn2modellitDAO.insert(entity);
	}
    }

    private void dataIntegration(Istanzedyn2modellit entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro istaznedyn2modellit è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Istanzedyn2modellit entity) {

	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanze);
	Dyn2Modellit dyn2Modellit = dyn2ModellitService.bindDomainObject(entity.getDyn2Modellit(), PkId.class, "id.codice");
	entity.setDyn2Modellit(dyn2Modellit);
	if (istanze != null) {
	    if (EntityUtils.getNestedProperty(entity, "id.codiceistanza") == null) {
		entity.getId().setCodiceistanza(istanze.getId().getCodice());
	    }
	}
	if (dyn2Modellit != null) {
	    if (EntityUtils.getNestedProperty(entity, "id.fkD2mtId") == null) {
		entity.getId().setFkD2mtId(dyn2Modellit.getId().getCodice());
	    }
	}
    }

    @Override
    public void update(Istanzedyn2modellit entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzedyn2modellitDAO.update(entity);
	}
    }

    @Override
    public List<Istanzedyn2modellit> findByIstanza(PkId idIstanza) {

	if (null == idIstanza || idIstanza.getCodice() == null) {
	    throwValidationMessage(new InvalidValue(WebConstants.ALERT_SERVICE_ERROR_PARAMETERS_NOT_VALID, getEntityClass(), "istanza",
		    new String[] { "findByIstanza(PkId idIstanza)", getClass().toString() }, null));
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction id = new FilterRestriction();
	id.addFilterField(FilterUtils.equals("id.codiceistanza", idIstanza.getCodice(), Integer.class));
	ft.addRestriction(id);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "dyn2Modellit"));
	return istanzedyn2modellitDAO.findByFilterTable(ft);
    }

    @Override
    public boolean existsRecords(FilterTable filterTable) {

	return istanzedyn2modellitDAO.existsRecords(filterTable);
    }

    @Override
    public List<Istanzedyn2modellit> findByFilterTable(FilterTable filterTable) {

	return istanzedyn2modellitDAO.findByFilterTable(filterTable);
    }

    @Override
    public void salvaSchedaDaModel(Integer codiceIstanza, Integer codiceModello, ModellidinamiciHelper helperScheda, Map<String, String> valori,
	    List<Integer> oggettiDaEliminare) {

	// elimino i dati già registrati
	Dyn2Modellit modello = dyn2ModellitService.findById(new PkId(codiceModello));
	Istanze istanza = istanzeService.findById(new PkId(codiceIstanza));
	//modello = dyn2ModellitService.findById(modello.getId());
	modelliDinamiciFormuleService.updateSaveModelloIstanza(codiceIstanza, codiceModello);
	List<Dyn2Modellid> righe = dyn2ModellidService.findRigheModello(modello.getId().getCodice());
	Set<String> codiciCampoDelModello = new TreeSet<String>();
	for (Dyn2Modellid riga : righe) {
	    if (riga.getDyn2Campi() != null) {
		Integer codiceCampo = riga.getDyn2Campi().getId().getCodice();
		codiciCampoDelModello.add(String.valueOf(codiceCampo));
	    }
	}
	Set<String> id2dRequest = new TreeSet<String>();
	Set<String> id2dDb = new TreeSet<String>();
	Set<String> id2dDaCancellare = new TreeSet<String>();
	for (Map.Entry<String, String> valore : valori.entrySet()) {
	    String k = estraiChiaveDatoDinamicoDaValoreRequest(valore.getKey());
	    if (k != null) {
		id2dRequest.add(k);
	    }
	}
	List<Istanzedyn2dati> id2ds = istanzedyn2datiService.findByIstanza(new PkId(codiceIstanza));
	for (Istanzedyn2dati istanzedyn2dati : id2ds) {
	    String k = estraiChiaveDatoDinamicoDaValoreDB(istanzedyn2dati.getId());
	    if (k != null) {
		id2dDb.add(k);
	    }
	}
	for (String chiaveDB : id2dDb) {
	    if (!id2dRequest.contains(chiaveDB)) {
		String codiceCampo = chiaveDB.split("-")[0];
		if (codiciCampoDelModello.contains(codiceCampo)) {
		    id2dDaCancellare.add(chiaveDB);
		}
	    }
	}
	// cancello i dati non presenti
	for (String daCancellare : id2dDaCancellare) {
	    Istanzedyn2datiId id = estraiIstanzeDyn2DatiIdDaCodice(daCancellare, codiceIstanza);
	    Istanzedyn2dati id2d = istanzedyn2datiService.findById(id);
	    if (id2d != null) {
		istanzedyn2datiService.delete(id2d);
	    }
	}
	// recupero i dati dinamici dalla mappa
	List<Istanzedyn2dati> daInserire = new ArrayList<Istanzedyn2dati>();
	for (Map.Entry<String, String> valore : valori.entrySet()) {
	    String key = valore.getKey();
	    String value = valore.getValue();
	    Istanzedyn2dati dato = estraiDatoDaValori(key);
	    if (!(key.endsWith("_ID") || key.endsWith("_DESC"))) {
		if (dato != null) {
		    String[] valoriDecodificati = decodeValoriForCampo(dato.getId().getFkD2cId(), value);
		    dato.setValore(valoriDecodificati[0]);
		    dato.setValoredecodificato(valoriDecodificati[1]);
		    daInserire.add(dato);
		}
	    } else {
		if (key.endsWith("_ID")) {
		    // recupero valore e valore decodificato;
		    String valoreSemplice = value;
		    String valoreDecodificato = valori.get(key.replaceAll("_ID", "_DESC"));
		    dato.setValore(valoreSemplice);
		    dato.setValoredecodificato(valoreDecodificato);
		    daInserire.add(dato);
		}
	    }
	}
	// data integration
	for (Istanzedyn2dati dato : daInserire) {
	    dato.getId().setCodiceistanza(codiceIstanza);
	    dato.setIstanza(istanza);
	    Dyn2Campi d2c = dyn2CampiService.findById(new PkId(dato.getId().getFkD2cId()));
	    dato.setDyn2Campi(d2c);
	    Istanzedyn2dati id2ddb = istanzedyn2datiService.findById(dato.getId());
	    if (id2ddb == null) {
		istanzedyn2datiService.insert(dato);
	    } else {
		String valoreDB = StringUtils.defaultString(id2ddb.getValore());
		String valoreReq = StringUtils.defaultString(dato.getValore());
		if (!valoreDB.equals(valoreReq)) {
		    istanzedyn2datiService.update(dato);
		}
	    }
	    modelliDinamiciFormuleService.updateSaveCampoIstanza(codiceIstanza, dato.getId().getFkD2cId());
	}
	//cancello da OGGETTI i file uploadati e poi rimossi dalla scheda dinamica
	if (oggettiDaEliminare != null && oggettiDaEliminare.size() > 0) {
	    this.oggettiService.deleteAll(oggettiDaEliminare);
	}
    }

    private Istanzedyn2datiId estraiIstanzeDyn2DatiIdDaCodice(String daCancellare, Integer codiceIstanza) {

	String[] valori = daCancellare.split("-");
	Istanzedyn2datiId id = new Istanzedyn2datiId(codiceIstanza, Integer.valueOf(valori[0]), Integer.valueOf(valori[1]),
		Integer.valueOf(valori[2]));
	return id;
    }

    private String estraiChiaveDatoDinamicoDaValoreDB(Istanzedyn2datiId id) {

	return String.valueOf(id.getFkD2cId()) + "-" + String.valueOf(id.getIndice()) + "-" + String.valueOf(id.getIndiceMolteplicita());
    }

    private String estraiChiaveDatoDinamicoDaValoreRequest(String valore) {

	String[] oggetti = valore.split("_");
	String nulV = oggetti[0]; // FLD
	String codiceCampo = oggetti[1];
	String indice = oggetti[2];
	String indiceMolteplicita = oggetti[3];
	if (!codiceCampo.equals("NULL")) {
	    return codiceCampo + "-" + indice + "-" + indiceMolteplicita;
	}
	return null;
    }

    private String[] decodeValoriForCampo(Integer fkD2cId, String value) {

	Dyn2Campi d2c = dyn2CampiService.findById(new PkId(fkD2cId));
	String[] result = new String[2];
	if (d2c == null) {
	    throw new RuntimeException("Campo non trovato per il codice [" + fkD2cId + "," + ORMHelper.getIdcomune() + "]");
	}
	if (d2c.getTipodato().equalsIgnoreCase(TipoControlloEnum.Data.name())) {
	    if (StringUtils.isNotBlank(value)) {
		Calendar d = Utilities.getDate(value, WebConstants.DATE_FORMAT_PATTERN);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		result[0] = sdf.format(d.getTime());
		result[1] = value;
	    }
	} else {
	    result[0] = value;
	    result[1] = value;
	}
	return result;
    }

    private Istanzedyn2dati estraiDatoDaValori(String key) {

	String[] oggetti = key.split("_");
	String nulV = oggetti[0]; // FLD
	String codiceCampo = oggetti[1];
	String indice = oggetti[2];
	String indiceMolteplicita = oggetti[3];
	if (!codiceCampo.equals("NULL")) {
	    Istanzedyn2datiId id = new Istanzedyn2datiId(0, Integer.valueOf(codiceCampo), Integer.valueOf(indice),
		    Integer.valueOf(indiceMolteplicita));
	    Istanzedyn2dati dato = new Istanzedyn2dati(id);
	    return dato;
	}
	return null;
    }

    @Override
    public void updateCopiaDyn2ModelliIstanza(Istanze istanzaSorgente, Istanze istanzaDestinatario) {

	// Recupero la lista delle schede gia presenti nell'istanza di destinazione,verrà utilizzata per controllare
	// se stiamo duplicando delle schede.
	Set<Istanzedyn2modellit> istanzedyn2modellitsIstanzaDestinatario = istanzaDestinatario.getIstanzedyn2modellit();
	Set<Istanzedyn2modellit> istanzedyn2modellits = istanzaSorgente.getIstanzedyn2modellit();
	Istanzedyn2modellit istanzedyn2modellitCopia = null;
	log.debug("Ciclo tutti i modelli dell'istanza sorgente {}",
		new Object[] { istanzaSorgente.getNumeroistanza(), istanzaSorgente.getId().getCodice() });
	for (Istanzedyn2modellit istanzedyn2modellit : istanzedyn2modellits) {
	    // Controllo che il modello da copiare non sia presente nell'istanza destinatario, solo nel caso non esistesse 
	    // faccio la copia e poi lo insirisco
	    log.debug("Controllo se il modello {}[{}] è già presente nell'istanza destinataria", new Object[] {
		    istanzedyn2modellit.getDyn2Modellit().getDescrizione(), istanzedyn2modellit.getDyn2Modellit().getId().getCodice() });
	    boolean isModelloPresente = isModellopresente(istanzedyn2modellit, istanzedyn2modellitsIstanzaDestinatario);
	    if (!isModelloPresente) {
		// Ritorno una copia del modello, la copia del modello non avrà riferimento a nessuna istanza 
		// Dovra essere impostata in modo esplicito.
		istanzedyn2modellitCopia = createCopiaSchedaIstanaza(istanzedyn2modellit);
		// Se il modello non è presnete alla copia creata setto il riferimento all'istanza destinatario
		Istanzedyn2modellitId id = istanzedyn2modellitCopia.getId();
		id.setCodiceistanza(istanzaDestinatario.getId().getCodice());
		istanzedyn2modellitCopia.setId(id);
		istanzedyn2modellitCopia.setIstanza(istanzaDestinatario);
		this.insert(istanzedyn2modellitCopia);
		log.debug("Inserito Modello {}[{}] ", new Object[] { istanzedyn2modellit.getDyn2Modellit().getDescrizione(),
			istanzedyn2modellit.getDyn2Modellit().getId().getCodice() });
	    } else {
		log.debug("Modello {}[{}] è già presente nell'istanza destinataria", new Object[] {
			istanzedyn2modellit.getDyn2Modellit().getDescrizione(), istanzedyn2modellit.getDyn2Modellit().getId().getCodice() });
	    }
	}
    }

    /**
     * Crea una copia dell' della scheda dell'istanza a partire da una passata
     * 
     * @param istanzedyn2modellitSorgente
     * @return
     */
    private Istanzedyn2modellit createCopiaSchedaIstanaza(Istanzedyn2modellit istanzedyn2modellitSorgente) {

	log.debug("Creo la copia del modello");
	Istanzedyn2modellit istanzedyn2modellit = new Istanzedyn2modellit();
	// creo l'id
	Istanzedyn2modellitId id = new Istanzedyn2modellitId();
	// Setto i campi del modello
	if (EntityUtils.getNestedProperty(istanzedyn2modellitSorgente.getDyn2Modellit(), "id.codice") != null) {
	    istanzedyn2modellit.setDyn2Modellit(istanzedyn2modellitSorgente.getDyn2Modellit());
	    // Inizializzo l'id
	    id.setFkD2mtId(istanzedyn2modellitSorgente.getDyn2Modellit().getId().getCodice());
	}
	log.debug("Copia del modello creata, Inserisco....");
	return istanzedyn2modellit;
    }

    /**
     * Controlla se la scheda che sto copiando nell'istanza destinataria sia gia presente
     * 
     * @param istanzedyn2modellitSorgente
     * @return
     */
    private boolean isModellopresente(Istanzedyn2modellit istanzedyn2modellitSorgente,
	    Set<Istanzedyn2modellit> istanzedyn2modellitsIstanzaDestinatario) {

	boolean isPrensente = false;
	for (Istanzedyn2modellit istanzedyn2modellitDest : istanzedyn2modellitsIstanzaDestinatario) {
	    // Consideriamo due istanzedyn2modellit uguali se hanno lo stesso dyn2Modellit
	    if (istanzedyn2modellitDest.getId().getFkD2mtId().equals(istanzedyn2modellitSorgente.getId().getFkD2mtId())) {
		isPrensente = true;
		break;
	    }
	}
	return isPrensente;
    }

    @Override
    public int countByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("countByIstanza: il parametro codiceIstanza e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	filterTable.addRestriction(istanza);
	int count = istanzedyn2modellitDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public List<Integer> findIdModelloByIstanzaAndIdCampo(Integer codiceIstanza, Integer idDyn2Campi) {

	return istanzedyn2modellitDAO.findIdModelloByIstanzaAndIdCampo(codiceIstanza, idDyn2Campi);
    }
}
