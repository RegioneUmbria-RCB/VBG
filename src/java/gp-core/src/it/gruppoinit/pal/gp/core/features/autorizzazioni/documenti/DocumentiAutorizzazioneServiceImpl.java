package it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazione;
import it.gruppoinit.pal.gp.core.domain.DocumentiAutorizzazioneId;
import it.gruppoinit.pal.gp.core.domain.Documentiistanza;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.AutorizzazioniService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.DocumentiistanzaService;
import it.gruppoinit.pal.gp.core.service.IstanzeallegatiService;
import it.gruppoinit.pal.gp.core.service.IstanzeprocureService;
import it.gruppoinit.pal.gp.core.service.MovimentiallegatiService;
import it.gruppoinit.pal.gp.core.service.impl.BaseServiceImpl;

/**
 * 
 * @author
 */
@Service
public class DocumentiAutorizzazioneServiceImpl extends BaseServiceImpl<DocumentiAutorizzazione, DocumentiAutorizzazioneId>
	implements DocumentiAutorizzazioneService {

    private DocumentiAutorizzazioneDAO documentiautorizzazioneDAO;
    private DocumentiistanzaService documentiistanzaService;
    private MovimentiallegatiService movimentiallegatiService;
    private IstanzeallegatiService istanzeallegatiService;
    private AnagrafedocumentiService anagrafedocumentiService;
    private IstanzeprocureService istanzeprocureService;
    private AutorizzazioniService autorizzazioniService;
    private OggettiService oggettiService;

    @Autowired
    public void setDocumentiistanzaService(DocumentiistanzaService documentiistanzaService) {

	this.documentiistanzaService = documentiistanzaService;
    }

    @Autowired
    public void setMovimentiallegatiService(MovimentiallegatiService movimentiallegatiService) {

	this.movimentiallegatiService = movimentiallegatiService;
    }

    @Autowired
    public void setIstanzeallegatiService(IstanzeallegatiService istanzeallegatiService) {

	this.istanzeallegatiService = istanzeallegatiService;
    }

    @Autowired
    public void setAnagrafedocumentiService(AnagrafedocumentiService anagrafedocumentiService) {

	this.anagrafedocumentiService = anagrafedocumentiService;
    }

    @Autowired
    public void setDocumentiautorizzazioneDAO(DocumentiAutorizzazioneDAO documentiautorizzazioneDAO) {

	this.documentiautorizzazioneDAO = documentiautorizzazioneDAO;
    }

    @Autowired
    public void setIstanzeprocureService(IstanzeprocureService istanzeprocureService) {

	this.istanzeprocureService = istanzeprocureService;
    }

    @Autowired
    public void setAutorizzazioniService(AutorizzazioniService autorizzazioniService) {

	this.autorizzazioniService = autorizzazioniService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<DocumentiAutorizzazione> getEntityClass() {

	return DocumentiAutorizzazione.class;
    }

    @Override
    public List<DocumentiAutorizzazione> findAll(Integer firstResult, Integer maxResult) {

	return documentiautorizzazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(DocumentiAutorizzazione entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    documentiautorizzazioneDAO.insert(entity);
	}
    }

    @Override
    public DocumentiAutorizzazione findById(DocumentiAutorizzazioneId id) {

	return documentiautorizzazioneDAO.findById(id);
    }

    @Override
    public void update(DocumentiAutorizzazione entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    documentiautorizzazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(DocumentiAutorizzazione entity) {

	if (isDeleteAllowed(entity)) {
	    documentiautorizzazioneDAO.delete(entity);
	}
    }

    @Override
    public void insertDocumentoAutorizzazione(Integer codiceOggetto, Integer idAutorizzazione, Integer codice, boolean principale,
	    Integer tipocodice) {

	DocumentiAutorizzazioneId id = new DocumentiAutorizzazioneId();
	id.setCodiceoggetto(codiceOggetto);
	id.setIdautorizzazione(idAutorizzazione);
	DocumentiAutorizzazione entity = new DocumentiAutorizzazione();
	entity.setId(id);
	Autorizzazioni autorizzazioni = autorizzazioniService.findById(new PkId(idAutorizzazione));
	entity.setAutorizzazioni(autorizzazioni);
	Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
	entity.setOggetti(oggetto);
	entity.setPrincipale(principale);
	if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_DOCUMENTIISTANZA) {
	    Documentiistanza documentiistanza = documentiistanzaService.findById(new PkId(codice));
	    entity.setDocumentiistanza(documentiistanza);
	} else if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_MOVIMENTIALLEGATI) {
	    Movimentiallegati movimentiallegati = movimentiallegatiService.findById(new PkId(codice));
	    entity.setMovimentiallegati(movimentiallegati);
	} else if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_ISTANZEALLEGATI) {
	    Istanzeallegati istanzeallegati = istanzeallegatiService.findById(new PkId(codice));
	    entity.setIstanzeallegati(istanzeallegati);
	} else if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_ANAGRAFEDOCUMENTI) {
	    Anagrafedocumenti anagrafedocumenti = anagrafedocumentiService.findById(new PkId(codice));
	    entity.setAnagrafedocumenti(anagrafedocumenti);
	} else if (tipocodice == WebConstants.DOCAUTORIZZAZIONE_CODICE_ISTANZEPROCURE) {
	    Istanzeprocure istanzeprocure = istanzeprocureService.findById(new PkId(codice));
	    entity.setIstanzeprocure(istanzeprocure);
	}
	insert(entity);
    }

    @Override
    public void insertDocumentoAutorizzazione(Integer codiceOggetto, Integer idAutorizzazione, Integer codice, Integer tipocodice) {

	this.insertDocumentoAutorizzazione(codiceOggetto, idAutorizzazione, codice, false, tipocodice);
    }

    private void dataIntegration(DocumentiAutorizzazione entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("I documenti dell'autorizzazione sono nulli");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(DocumentiAutorizzazione entity) {

	Documentiistanza documentiistanza = documentiistanzaService.bindDomainObject(entity.getDocumentiistanza(), PkId.class, "id.codice");
	entity.setDocumentiistanza(documentiistanza);
	Movimentiallegati movimentiallegati = movimentiallegatiService.bindDomainObject(entity.getMovimentiallegati(), PkId.class, "id.codice");
	entity.setMovimentiallegati(movimentiallegati);
	Istanzeallegati istanzeallegati = istanzeallegatiService.bindDomainObject(entity.getIstanzeallegati(), PkId.class, "id.codice");
	entity.setIstanzeallegati(istanzeallegati);
	Anagrafedocumenti anagrafedocumenti = anagrafedocumentiService.bindDomainObject(entity.getAnagrafedocumenti(), PkId.class, "id.codice");
	entity.setAnagrafedocumenti(anagrafedocumenti);
	Istanzeprocure istanzeprocure = istanzeprocureService.bindDomainObject(entity.getIstanzeprocure(), PkId.class, "id.codice");
	entity.setIstanzeprocure(istanzeprocure);
    }

    protected boolean isDeleteAllowed(DocumentiAutorizzazione entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<DocumentiAutorizzazione> findByAutorizzazioni(Integer codiceautorizzazione, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idautorizzazione", codiceautorizzazione, Integer.class));
	filterTable.addRestriction(fr);
	return documentiautorizzazioneDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public List<DocumentiAutorizzazioneDTO> findDocumentiAutorizzazioneDTOByAutorizzazione(Integer codiceAutorizzazione) {

	return documentiautorizzazioneDAO.findDocumentiAutorizzazioneDTOByAutorizzazione(codiceAutorizzazione);
    }

    @Override
    public List<DocumentiAutorizzazione> findDocumentiAutorizzazioneByOggetto(Integer codiceOggetto, Integer firstResult, Integer maxResult) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceoggetto", codiceOggetto, Integer.class));
	filterTable.addRestriction(fr);
	return documentiautorizzazioneDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }

    @Override
    public Boolean isInAutorizzazione(Integer codiceautorizzazione, Integer codicedocumento, String associationPath) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (codiceautorizzazione != null) {
	    fr.addFilterField(FilterUtils.equals("id.codice", codiceautorizzazione, "autorizzazioni", Integer.class));
	}
	fr.addFilterField(FilterUtils.equals("id.codice", codicedocumento, associationPath, Integer.class));
	filterTable.addRestriction(fr);
	return documentiautorizzazioneDAO.existsRecords(filterTable);
    }

    @Override
    public Boolean isInAutorizzazione(Integer codicedocumento, String associationPath) {

	return isInAutorizzazione(null, codicedocumento, associationPath);
    }

    @Override
    public void impostaDocumentoPrincipale(Integer codiceAutorizzazione, Integer codiceOggetto) {

	this.documentiautorizzazioneDAO.impostaDocumentoPrincipale(codiceAutorizzazione, codiceOggetto);
    }

    @Override
    public boolean documentPrincipalePresente(Integer idAutorizzazione) {

	return this.documentiautorizzazioneDAO.documentPrincipalePresente(idAutorizzazione);
    }
}
