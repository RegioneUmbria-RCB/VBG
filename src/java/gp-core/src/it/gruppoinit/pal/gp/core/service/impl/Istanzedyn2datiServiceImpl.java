package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.Istanzedyn2datiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campi;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellid;
import it.gruppoinit.pal.gp.core.domain.Dyn2Modellit;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2dati;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiId;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;
import it.gruppoinit.pal.gp.core.domain.helper.DocumentiistanzaDTO;
import it.gruppoinit.pal.gp.core.domain.helper.Istanzedyn2datiDTO;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.commissioni.dettaglio.pratiche.documenti.ICommissioniDocumentiPraticheService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiService;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Istanzedyn2datiServiceImpl extends BaseServiceImpl<Istanzedyn2dati, Istanzedyn2datiId> implements Istanzedyn2datiService {

    private DocumentiistanzaService documentiistanzaService;
    private Dyn2CampiService dyn2CampiService;
    private OggettiService oggettiService;
    private Istanzedyn2datiDAO istanzedyn2datiDAO;
    private IstanzeService istanzeService;
    private static final Logger log = LoggerFactory.getLogger(Istanzedyn2datiServiceImpl.class);
    private ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService;

    @Autowired
    public void setCommissioniDocumentiPraticheService(ICommissioniDocumentiPraticheService commissioniDocumentiPraticheService) {

	this.commissioniDocumentiPraticheService = commissioniDocumentiPraticheService;
    }

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setDyn2CampiService(Dyn2CampiService dyn2CampiService) {

	this.dyn2CampiService = dyn2CampiService;
    }

    @Autowired
    public void setIstanzedyn2datiDAO(Istanzedyn2datiDAO istanzedyn2datiDAO) {

	this.istanzedyn2datiDAO = istanzedyn2datiDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    private Dyn2ModellitService dyn2ModellitService;

    @Autowired
    public void setDyn2ModellitService(Dyn2ModellitService dyn2ModellitService) {

	this.dyn2ModellitService = dyn2ModellitService;
    }

    @Override
    protected Class<Istanzedyn2dati> getEntityClass() {

	return Istanzedyn2dati.class;
    }

    @Override
    public List<Istanzedyn2dati> findByIstanza(PkId idIstanza) {

	if (null == idIstanza || idIstanza.getCodice() == null) {
	    throwValidationMessage(new InvalidValue(WebConstants.ALERT_SERVICE_ERROR_PARAMETERS_NOT_VALID, getEntityClass(), "istanza", new String[] {
		    "findByIstanza(PkId idIstanza)", getClass().toString() }, null));
	}
	return istanzedyn2datiDAO.findByIstanza(idIstanza);
    }

    @Override
    public List<Istanzedyn2dati> findByIstanzaAndModello(PkId idIstanza, PkId idModello) {

	boolean isError = false;
	if (null == idIstanza || idIstanza.getCodice() == null) {
	    isError = true;
	}
	if (null == idModello || idModello.getCodice() == null) {
	    isError = true;
	}
	if (isError) {
	    throwValidationMessage(new InvalidValue(WebConstants.ALERT_SERVICE_ERROR_PARAMETERS_NOT_VALID, getEntityClass(), "istanza", new String[] {
		    "findByIstanzaAndModello(PkId idIstanza, PkId idModello)", getClass().toString() }, null));
	}
	List<Istanzedyn2dati> listaDati = istanzedyn2datiDAO.findByIstanza(idIstanza);
	List<Istanzedyn2dati> result = new ArrayList<Istanzedyn2dati>();
	Integer codiceCampo = null;
	Map<Integer, Dyn2Campi> mappaCampiModello = new HashMap<Integer, Dyn2Campi>();
	Dyn2Modellit modello = dyn2ModellitService.findById(idModello);
	Set<Dyn2Modellid> righeModello = modello.getDyn2Modellids();
	for (Dyn2Modellid dyn2Modellid : righeModello) {
	    if (dyn2Modellid.getDyn2Campi() != null) {
		if (dyn2Modellid.getDyn2Campi().getId() != null) {
		    if (dyn2Modellid.getDyn2Campi().getId().getCodice() != null) {
			codiceCampo = dyn2Modellid.getDyn2Campi().getId().getCodice();
			// inserisco nella mappa solo i campi appartenenti al modello selezionato
			mappaCampiModello.put(codiceCampo, dyn2Modellid.getDyn2Campi());
		    }
		}
	    }
	}
	if (!mappaCampiModello.isEmpty()) {
	    for (Istanzedyn2dati istanzedyn2dati : listaDati) {
		codiceCampo = istanzedyn2dati.getId().getFkD2cId();
		// confronto tra quelli presenti nell'istanza e quelli specifici del modello selezionato
		if (mappaCampiModello.get(codiceCampo) != null) {
		    result.add(istanzedyn2dati);
		}
	    }
	}
	return result;
    }

    @Override
    public void delete(Istanzedyn2dati entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    istanzedyn2datiDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Istanzedyn2dati entity) {

	if (isCampoUpload(entity)) {
	    String codiceOggettoStr = StringUtils.defaultString(entity.getValore()).trim();
	    if (StringUtils.isNotBlank(codiceOggettoStr)) {
		Integer codiceOggetto = null;
		try {
		    codiceOggetto = Integer.parseInt(codiceOggettoStr);
		} catch (NumberFormatException e) {
		    log.error("il valore [{}] salvato su istanzedyn2dati [{}] non rappresenta un intero", codiceOggettoStr, entity.getId());
		    throw new RuntimeException("il valore [" + codiceOggettoStr + "] salvato su istanzedyn2dati [" + entity.getId()
			    + "] non rappresenta un intero");
		}
		if (codiceOggetto != null) {
		    Oggetti oggetto = oggettiService.findByIdLazy(new PkId(codiceOggetto));
		    if (oggetto != null) {
			List<DocumentiistanzaDTO> docs = documentiistanzaService.findDocumentiistanzaDTOByIstanza(entity.getId().getCodiceistanza(),
				Boolean.TRUE);
			for (DocumentiistanzaDTO d : docs) {
			    Integer codiceOggettoDB = d.getCodiceOggetto();
			    if (codiceOggetto.equals(codiceOggettoDB)) {
				// DELETE
				Documentiistanza doc = documentiistanzaService.findById(new PkId(d.getId().getCodice()));
				if (doc != null) {
				    doc.setControllook(0 /*NON_VALIDO*/);
				    documentiistanzaService.update(doc);
				}
			    }
			}
		    } else {
			log.error(
				"il valore [{}] salvato su istanzedyn2dati [{}] non è legato a nessun dato della tabella oggetti. Codice oggetto non trovato.",
				codiceOggettoStr, entity.getId());
			throw new RuntimeException("il valore [" + codiceOggettoStr + "] salvato su istanzedyn2dati [" + entity.getId()
				+ "] non è legato a nessun dato della tabella oggetti. Codice oggetto non trovato.");
		    }
		}
	    }
	}
    }

    @Override
    public List<Istanzedyn2dati> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public Istanzedyn2dati findById(Istanzedyn2datiId id) {

	return istanzedyn2datiDAO.findById(id);
    }

    @Override
    public void insert(Istanzedyn2dati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzedyn2datiDAO.insert(entity);
	    istanzedyn2datiDAO.flush();
	    insertDocumentoIstanza(entity);
	}
    }

    private void insertDocumentoIstanza(Istanzedyn2dati entity) {

	if (isCampoUpload(entity)) {
	    String codiceOggettoStr = StringUtils.defaultString(entity.getValore()).trim();
	    if (StringUtils.isNotBlank(codiceOggettoStr) && !"null".equalsIgnoreCase(codiceOggettoStr)) {
		Integer codiceOggetto = null;
		try {
		    codiceOggetto = Integer.parseInt(codiceOggettoStr);
		} catch (NumberFormatException e) {
		    log.error("il valore [{}] salvato su istanzedyn2dati [{}] non rappresenta un intero", codiceOggettoStr, entity.getId());
		    throw new RuntimeException("il valore [" + codiceOggettoStr + "] salvato su istanzedyn2dati [" + entity.getId()
			    + "] non rappresenta un intero");
		}
		if (codiceOggetto != null) {
		    Oggetti oggetto = oggettiService.findByIdLazy(new PkId(codiceOggetto));
		    if (oggetto != null) {
			List<DocumentiistanzaDTO> docs = documentiistanzaService.findDocumentiistanzaDTOByIstanza(entity.getId().getCodiceistanza(),
				null);
			DocumentiistanzaDTO giaInserito = null;
			for (DocumentiistanzaDTO dto : docs) {
			    if (BooleanUtils.isTrue(dto.getFlgDaModelloDinamico())) {
				if (codiceOggetto.equals(dto.getCodiceOggetto())) {
				    // non lo inserisco esiste già nei documenti da dati dinamici
				    return;
				}
			    } else {
				if (codiceOggetto.equals(dto.getCodiceOggetto())) {
				    giaInserito = dto;
				    break;
				}
			    }
			}
			Istanze istanza = istanzeService.findById(new PkId(entity.getId().getCodiceistanza()));
			if (giaInserito == null) {
			    Documentiistanza doc = new Documentiistanza();
			    doc.setOggetto(oggetto);
			    doc.setFlgDaModelloDinamico(Boolean.TRUE);
			    doc.setIstanza(istanza);
			    doc.setData(Calendar.getInstance().getTime());
			    documentiistanzaService.insert(doc);
			} else {
			    Documentiistanza doc = documentiistanzaService.findById(new PkId(giaInserito.getId().getCodice()));
			    doc.setFlgDaModelloDinamico(Boolean.TRUE);
			    documentiistanzaService.update(doc);
			}
		    } else {
			log.error(
				"il valore [{}] salvato su istanzedyn2dati [{}] non è legato a nessun dato della tabella oggetti. Codice oggetto non trovato.",
				codiceOggettoStr, entity.getId());
			throw new RuntimeException("il valore [" + codiceOggettoStr + "] salvato su istanzedyn2dati [" + entity.getId()
				+ "] non è legato a nessun dato della tabella oggetti. Codice oggetto non trovato.");
		    }
		}
	    }
	}
    }

    private boolean isCampoUpload(Istanzedyn2dati entity) {

	return dyn2CampiService.isCampoUpload(entity.getDyn2Campi().getId().getCodice());
    }

    private void dataIntegration(Istanzedyn2dati entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro Istanzedyn2dati da validare è nullo");
	}
	if (entity.getId() == null) {
	    entity.setId(new Istanzedyn2datiId());
	}
	if (EntityUtils.getNestedProperty(entity.getId(), "indice") == null) {
	    entity.getId().setIndice(0);
	}
	if (EntityUtils.getNestedProperty(entity.getId(), "indiceMolteplicita") == null) {
	    entity.getId().setIndiceMolteplicita(0);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Istanzedyn2dati entity) {

	Istanze istanze = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	entity.setIstanza(istanze);
	Dyn2Campi dyn2Campi = dyn2CampiService.bindDomainObject(entity.getDyn2Campi(), PkId.class, "id.codice");
	entity.setDyn2Campi(dyn2Campi);
	if (dyn2Campi != null) {
	    entity.getId().setFkD2cId(dyn2Campi.getId().getCodice());
	}
	if (istanze != null) {
	    entity.getId().setCodiceistanza(istanze.getId().getCodice());
	}
    }

    @Override
    public void update(Istanzedyn2dati entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    if (isCampoUpload(entity)) {
		String valoreDB = this.findValoreById(entity.getId());
		if (!StringUtils.defaultString(valoreDB).equals(StringUtils.defaultString(entity.getValore()))) {
		    if (StringUtils.isNotBlank(StringUtils.defaultString(valoreDB).trim())) {
			Integer codiceOggetto = Integer.valueOf(StringUtils.defaultString(valoreDB).trim());
			if (codiceOggetto != null) {
			    Oggetti oggetto = oggettiService.findByIdLazy(new PkId(codiceOggetto));
			    if (oggetto != null) {
				List<DocumentiistanzaDTO> docs = documentiistanzaService.findDocumentiistanzaDTOByIstanza(entity.getId()
					.getCodiceistanza(), Boolean.TRUE);
				for (DocumentiistanzaDTO d : docs) {
				    Integer codiceOggettoDB = d.getCodiceOggetto();
				    if (codiceOggetto.equals(codiceOggettoDB)) {
					// DELETE
					Documentiistanza doc = documentiistanzaService.findById(new PkId(d.getId().getCodice()));
					if (doc != null) {
					    if (commissioniDocumentiPraticheService.existsByDocumentiIstanza(d.getId().getCodice())) {
						doc.setControllook(0 /*NON_VALIDO*/);
						documentiistanzaService.update(doc);
					    } else {
						documentiistanzaService.delete(doc);
					    }
					}
				    }
				}
			    } else {
				log.error(
					"il valore [{}] salvato su istanzedyn2dati [{}] non è legato a nessun dato della tabella oggetti. Codice oggetto non trovato.",
					valoreDB, entity.getId());
				throw new RuntimeException("il valore [" + valoreDB + "] salvato su istanzedyn2dati [" + entity.getId()
					+ "] non è legato a nessun dato della tabella oggetti. Codice oggetto non trovato.");
			    }
			}
		    }
		}
	    }
	    istanzedyn2datiDAO.update(entity);
	    insertDocumentoIstanza(entity);
	}
    }

    @Override
    public List<Istanzedyn2dati> findByIstanzaAndNomeCampo(Istanze istanza, String nomeCampo, String softwareDyn2Campi) {

	//
	//	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	//	FilterRestriction istanzaFr = new FilterRestriction();
	//	istanzaFr.addFilterField(FilterUtils.equals("id.codice", istanza.getId().getCodice(), "istanza", Integer.class));
	//	ft.addRestriction(istanzaFr);
	//	FilterRestriction dyn2CampiFr = new FilterRestriction();
	//	dyn2CampiFr.addFilterField(FilterUtils.equals("nomecampo", nomeCampo, "dyn2Campi", String.class));
	//	dyn2CampiFr.addFilterField(FilterUtils.equals("codice", softwareDyn2Campi, "dyn2Campi.software", String.class));
	//	//Lion aggiunto ordinamento per indice e indiceMolteplicità crescente
	//	ft.addOrder(FilterUtils.orderAsc("id.indice"));
	//	ft.addOrder(FilterUtils.orderAsc("id.indiceMolteplicita"));
	//	ft.addRestriction(dyn2CampiFr);
	//	return istanzedyn2datiDAO.findByFilterTable(ft);
	return this.findByIstanzaAndNomeCampo(istanza.getId().getCodice(), nomeCampo, softwareDyn2Campi);
    }

    @Override
    public int countByIstanzaAndNomecampo(Integer codiceIstanza, String nomeCampo) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanzaFr = new FilterRestriction();
	istanzaFr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	ft.addRestriction(istanzaFr);
	FilterRestriction dyn2CampiFr = new FilterRestriction();
	dyn2CampiFr.addFilterField(FilterUtils.equals("nomecampo", nomeCampo, "dyn2Campi", String.class));
	ft.addRestriction(dyn2CampiFr);
	return istanzedyn2datiDAO.countRecord(ft);
    }

    @Override
    public Istanzedyn2dati findByIstanzaAndNomeCampoAndMolteplicita(Integer codiceIstanza, String nomeCampo, Integer molteplicita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanzaFr = new FilterRestriction();
	istanzaFr.addFilterField(FilterUtils.equals("id.codice", codiceIstanza, "istanza", Integer.class));
	ft.addRestriction(istanzaFr);
	FilterRestriction dyn2CampiFr = new FilterRestriction();
	dyn2CampiFr.addFilterField(FilterUtils.equals("nomecampo", nomeCampo, "dyn2Campi", String.class));
	dyn2CampiFr.addFilterField(FilterUtils.equals("id.indiceMolteplicita", molteplicita, Integer.class));
	ft.addRestriction(dyn2CampiFr);
	List<Istanzedyn2dati> i = istanzedyn2datiDAO.findByFilterTable(ft);
	if (!i.isEmpty()) {
	    return i.get(0);
	}
	return null;
    }

    @Override
    public List<Istanzedyn2dati> findByIstanzaAndNomeCampo(Istanze istanza, String nomeCampo) {

	Dyn2Campi d2c = dyn2CampiService.findByNomeCampo(nomeCampo);
	String softwareCampo = ORMHelper.getSoftware();
	if (d2c != null) {
	    softwareCampo = d2c.getSoftware().getCodice();
	}
	return this.findByIstanzaAndNomeCampo(istanza, nomeCampo, softwareCampo);
    }

    @Override
    public List<Istanzedyn2dati> findByIstanzaAndNomeCampo(Integer codIst, String nomeCampo) {

	Dyn2Campi d2c = dyn2CampiService.findByNomeCampo(nomeCampo);
	String softwareCampo = ORMHelper.getSoftware();
	if (d2c != null) {
	    softwareCampo = d2c.getSoftware().getCodice();
	}
	return this.findByIstanzaAndNomeCampo(codIst, nomeCampo, softwareCampo);
    }

    @Override
    public List<Istanzedyn2dati> findByIstanzaAndNomeCampo(Integer codiceistanza, String nomeCampo, String softwareDyn2Campi) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanzaFr = new FilterRestriction();
	istanzaFr.addFilterField(FilterUtils.equals("id.codice", codiceistanza, "istanza", Integer.class));
	ft.addRestriction(istanzaFr);
	FilterRestriction dyn2CampiFr = new FilterRestriction();
	dyn2CampiFr.addFilterField(FilterUtils.equals("nomecampo", nomeCampo, "dyn2Campi", String.class));
	dyn2CampiFr.addFilterField(FilterUtils.equals("codice", softwareDyn2Campi, "dyn2Campi.software", String.class));
	//Lion aggiunto ordinamento per indice e indiceMolteplicità crescente
	ft.addOrder(FilterUtils.orderAsc("id.indice"));
	ft.addOrder(FilterUtils.orderAsc("id.indiceMolteplicita"));
	ft.addRestriction(dyn2CampiFr);
	return istanzedyn2datiDAO.findByFilterTable(ft);
    }

    @Override
    public int countByIstanza(Integer codiceIstanza) {

	if (codiceIstanza == null) {
	    throw new IllegalArgumentException("countByIstanza: il parametro codiceIstanza e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction istanza = new FilterRestriction();
	istanza.addFilterField(FilterUtils.equals("id.codiceistanza", codiceIstanza, Integer.class));
	filterTable.addRestriction(istanza);
	int count = istanzedyn2datiDAO.countRecord(filterTable);
	return count;
    }

    @Override
    public List<Istanzedyn2dati> findByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo) {

	return findByIstanzaAndDyn2Campi(codiceIstanza, codiceCampo, 0);
    }

    @Override
    public List<Istanzedyn2datiDTO> findDTOByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo, Integer indice,
	    Integer indiceMolteplicita) {

	return istanzedyn2datiDAO.findValoreDecodificatoByIstanzaAndDyn2Campi(codiceIstanza, codiceCampo, indice, indiceMolteplicita);
    }

    @Override
    public List<Istanzedyn2dati> findByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo, Integer indice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction id = new FilterRestriction();
	id.addFilterField(FilterUtils.equals("id.codiceistanza", codiceIstanza, Integer.class));
	id.addFilterField(FilterUtils.equals("id.fkD2cId", codiceCampo, Integer.class));
	//Lion se l'argomento indice è null allora vengono recuperati i dati a tutti gli indici (in tutte le N schede)
	if (indice != null) {
	    id.addFilterField(FilterUtils.equals("id.indice", indice, Integer.class));
	} else {
	    ft.addOrder(FilterUtils.orderAsc("id.indice"));
	}
	ft.addRestriction(id);
	//Lion modificato l'ordinamento per molteplicità crescente. Dalla prima riga all'ultima,
	ft.addOrder(FilterUtils.orderAsc("id.indiceMolteplicita"));
	return istanzedyn2datiDAO.findByFilterTable(ft);
    }

    @Override
    public void updateCopiaDyn2DatiIstanza(Istanze istanzaSorgente, Istanze istanzaDestinatario) {

	// Recupero la lista delle dei dati dinamici gia presenti nell'istanza di destinazione,verrà utilizzata per controllare
	// se stiamo duplicando delle schede.
	Set<Istanzedyn2dati> istanzedyn2datisIstanzaDestinatario = istanzaDestinatario.getIstanzedyn2datis();
	Set<Istanzedyn2dati> istanzedyn2datis = istanzaSorgente.getIstanzedyn2datis();
	Istanzedyn2dati istanzedyn2datiCopia = null;
	log.debug("Ciclo tutti i dati dinamici dell'istanza sorgente {}", new Object[] { istanzaSorgente.getNumeroistanza(),
		istanzaSorgente.getId().getCodice() });
	for (Istanzedyn2dati istanzedyn2dati : istanzedyn2datis) {
	    // Controllo che il dato dinamico da copiarenon sia presente nell'istanza destinataria, solo nel caso non lo sia faccio la copia 
	    //e lo inserisco
	    log.debug("Controllo se il dato dinamico {}[{}] è già presente nell'istanza destinataria", new Object[] {
		    istanzedyn2dati.getDyn2Campi().getDescrizione(), istanzedyn2dati.getDyn2Campi().getId().getCodice() });
	    boolean isDatoPresente = isDatoDinamicoPresente(istanzedyn2dati, istanzedyn2datisIstanzaDestinatario);
	    // Se il modello non è presnete alla copia creata setto il riferimento all'istanza destinatario
	    if (!isDatoPresente) {
		// Ritorno una copia del dato dinamico, la copia del dato dinamico non avrà riferimento a nessuna istanza 
		// Dovra essere impostata in modo esplicito.
		istanzedyn2datiCopia = createCopiaDyn2DatiIstanaza(istanzedyn2dati);
		Istanzedyn2datiId id = istanzedyn2datiCopia.getId();
		id.setCodiceistanza(istanzaDestinatario.getId().getCodice());
		istanzedyn2datiCopia.setId(id);
		istanzedyn2datiCopia.setIstanza(istanzaDestinatario);
		this.insert(istanzedyn2datiCopia);
		log.debug("Inserito il Dato dinamico {}[{}] ", new Object[] { istanzedyn2dati.getDyn2Campi().getDescrizione(),
			istanzedyn2dati.getDyn2Campi().getId().getCodice() });
	    } else {
		log.debug("Dato dinamico {}[{}] è già presente nell'istanza destinataria", new Object[] {
			istanzedyn2dati.getDyn2Campi().getDescrizione(), istanzedyn2dati.getDyn2Campi().getId().getCodice() });
	    }
	}
    }

    @Override
    public List<Istanzedyn2dati> findByIstanzasAndDyn2Campi(Integer idCampo, Integer codiceAttivita, List<Integer> listaIstanze, Integer firstResult,
	    Integer maxResult) {

	return istanzedyn2datiDAO.findByIstanzasAndDyn2Campi(idCampo, codiceAttivita, listaIstanze, firstResult, maxResult);
    }

    /**
     * Crea una copia dell' della scheda dell'istanza a partire da una passata
     * 
     * @param istanzedyn2datiSorgente
     * @return
     */
    private Istanzedyn2dati createCopiaDyn2DatiIstanaza(Istanzedyn2dati istanzedyn2datiSorgente) {

	log.debug("Creo la copia del dato dinamico");
	Istanzedyn2dati istanzedyn2dati = new Istanzedyn2dati();
	// creo e inizializzo l'id
	Istanzedyn2datiId id = new Istanzedyn2datiId();
	id.setIndice(istanzedyn2datiSorgente.getId().getIndice());
	id.setIndiceMolteplicita(istanzedyn2datiSorgente.getId().getIndiceMolteplicita());
	// Setto i campi del modello
	if (EntityUtils.getNestedProperty(istanzedyn2datiSorgente.getDyn2Campi(), "id.codice") != null) {
	    istanzedyn2dati.setDyn2Campi(istanzedyn2datiSorgente.getDyn2Campi());
	    // setto all'id il codice del campo dinamicco
	    id.setFkD2cId(istanzedyn2datiSorgente.getDyn2Campi().getId().getCodice());
	}
	istanzedyn2dati.setId(id);
	if (StringUtils.isNotBlank(istanzedyn2datiSorgente.getValore())) {
	    istanzedyn2dati.setValore(istanzedyn2datiSorgente.getValore());
	}
	if (StringUtils.isNotBlank(istanzedyn2datiSorgente.getValoredecodificato())) {
	    istanzedyn2dati.setValoredecodificato(istanzedyn2datiSorgente.getValoredecodificato());
	}
	log.debug("Copia del dato dinamico creata, Inserisco....");
	return istanzedyn2dati;
    }

    /**
     * Controlla se il dato dinamico che sto copiando nell'istanza destinataria sia gia presente
     * 
     */
    private boolean isDatoDinamicoPresente(Istanzedyn2dati istanzedyn2datiSorgente, Set<Istanzedyn2dati> istanzedyn2datisIstanzaDestinatario) {

	boolean isPrensente = false;
	for (Istanzedyn2dati istanzedyn2datiDest : istanzedyn2datisIstanzaDestinatario) {
	    // Consideriamo due Istanzedyn2dati uguali se hanno lo stesso dyn2Campi,id.indice,id.indiceMolteplicita
	    if (istanzedyn2datiDest.getId().getFkD2cId().equals(istanzedyn2datiSorgente.getId().getFkD2cId())
		    && istanzedyn2datiDest.getId().getIndice().equals(istanzedyn2datiSorgente.getId().getIndice())
		    && istanzedyn2datiDest.getId().getIndiceMolteplicita().equals(istanzedyn2datiSorgente.getId().getIndiceMolteplicita())) {
		isPrensente = true;
		break;
	    }
	}
	return isPrensente;
    }

    @Override
    public String findValoreById(Istanzedyn2datiId id) {

	return istanzedyn2datiDAO.findValoreById(id);
    }

    @Override
    public List<Istanzedyn2datiDTO> findBandoOutput(Integer graduatoriedId) {

	return istanzedyn2datiDAO.findBandoOutput(graduatoriedId);
    }

    //    @Override
    //    public void deleteAndInsertIstanzeDyn2Dati(Integer codiceIstanza, Integer codiceDyn2dato, List<Istanzedyn2dati> liIstanzedyn2datis) {
    //
    //	// Recupero la lista di Istanzedyn2dati filtrando per codiceIstanza e codiceDyn2dato
    //	log.debug("Recupero la lista di Istanzedyn2dati filtrando per codiceIstanza={}  e codiceDyn2dati={}", new Object[] { codiceIstanza,
    //		codiceDyn2dato });
    //	List<Istanzedyn2dati> listIstanzedyn2datiDelete = this.findByIstanzaAndDyn2Campi(codiceIstanza, codiceDyn2dato);
    //	// Cancello i recordo recuperati
    //	log.debug("Inizio cancellazione Istanzedyn2dati..... ");
    //	for (Istanzedyn2dati istanzedyn2dati : listIstanzedyn2datiDelete) {
    //	    this.delete(istanzedyn2dati);
    //	}
    //	log.debug("Fine cancellazione Istanzedyn2dati........");
    //	//Inserisco i nuovi valori
    //	log.debug("Inizio inserimento nuovi valori in istanzeDyn2Dati");
    //	for (Istanzedyn2dati istanzedyn2dati : liIstanzedyn2datis) {
    // TODO DA COMPLETARE
    //	}
    //	log.debug("Fine inserimento nuovi valori in istanzeDyn2Dati ");
    //    }
    @Override
    public List<CodiceDescrizioneBean> findModelliCheUsanoLocalizzazioneByUUID(Integer codiceistanza, String uuid, Integer firstResult,
	    Integer maxResults) {

	return istanzedyn2datiDAO.findModelliCheUsanoLocalizzazioneByUUID(codiceistanza, uuid, firstResult, maxResults);
    }

    @Override
    public List<Istanzedyn2datiDTO> findDTOByIstanzaAndDyn2Campi(Integer codiceIstanza, Integer codiceCampo) {

	return istanzedyn2datiDAO.findDTOByIstanzaAndDyn2Campi(codiceIstanza, codiceCampo);
    }
}
