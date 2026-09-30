package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AnagrafedocumentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.AnagrafedocumentiDurc;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.documenti.DocumentiAutorizzazioneService;
import it.gruppoinit.pal.gp.core.features.movimenti.ziplogico.MovimentiZipLogicoService;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiDurcService;
import it.gruppoinit.pal.gp.core.service.AnagrafedocumentiService;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class AnagrafedocumentiServiceImpl extends BaseServiceImpl<Anagrafedocumenti, PkId> implements AnagrafedocumentiService {

    private static final Logger log = LoggerFactory.getLogger(AnagrafedocumentiServiceImpl.class);
    private AnagrafedocumentiDAO anagrafedocumentiDAO;
    private OggettiService oggettiService;
    private AnagrafedocumentiDurcService anagrafedocumentiDurcService;
    private DocumentiAutorizzazioneService documentiautorizzazioneService;
    private MovimentiZipLogicoService movimentiZipLogicoService;
    private TipidocumentoService tipidocumentoService;
    private AnagrafeService anagrafeService;

    @Autowired
    public void setAnagrafeService(AnagrafeService anagrafeService) {

	this.anagrafeService = anagrafeService;
    }

    @Autowired
    public void setTipidocumentoService(TipidocumentoService tipidocumentoService) {

	this.tipidocumentoService = tipidocumentoService;
    }

    @Autowired
    public void setAnagrafedocumentiDurcService(AnagrafedocumentiDurcService anagrafedocumentiDurcService) {

	this.anagrafedocumentiDurcService = anagrafedocumentiDurcService;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setAnagrafedocumentiDAO(AnagrafedocumentiDAO anagrafedocumentiDAO) {

	this.anagrafedocumentiDAO = anagrafedocumentiDAO;
    }

    @Autowired
    public void setDocumentiautorizzazioneService(DocumentiAutorizzazioneService documentiautorizzazioneService) {

	this.documentiautorizzazioneService = documentiautorizzazioneService;
    }

    @Autowired
    public void setMovimentiZipLogicoService(MovimentiZipLogicoService movimentiZipLogicoService) {

	this.movimentiZipLogicoService = movimentiZipLogicoService;
    }

    @Override
    protected Class<Anagrafedocumenti> getEntityClass() {

	return Anagrafedocumenti.class;
    }

    @Override
    public List<Anagrafedocumenti> findAll(Integer firstResult, Integer maxResult) {

	return anagrafedocumentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Anagrafedocumenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    anagrafedocumentiDAO.insert(entity);
	}
    }

    private void dataIntegration(Anagrafedocumenti entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro entity non può essere nullo");
	}
	if (entity.getFlagXmlvisuraparix() == null) {
	    entity.setFlagXmlvisuraparix(Boolean.FALSE);
	}
	if (entity.getDataregistrazione() == null) {
	    entity.setDataregistrazione(Calendar.getInstance().getTime());
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Anagrafedocumenti entity) {

	Anagrafe a = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(a);
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetto(), PkId.class, "id.codice");
	entity.setOggetto(oggetto);
    }

    @Override
    public Anagrafedocumenti findById(PkId id) {

	return anagrafedocumentiDAO.findById(id);
    }

    @Override
    public void update(Anagrafedocumenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    anagrafedocumentiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(Anagrafedocumenti entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    anagrafedocumentiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    protected void childDelete(Anagrafedocumenti entity) {

	List<AnagrafedocumentiDurc> adurcs = anagrafedocumentiDurcService.findByAnagrafedocumenti(entity.getId().getCodice());
	for (AnagrafedocumentiDurc anagrafedocumentiDurc : adurcs) {
	    anagrafedocumentiDurcService.delete(anagrafedocumentiDurc);
	}
	anagrafedocumentiDAO.flush();
	anagrafedocumentiDAO.clear();
    }

    @Override
    public List<Anagrafedocumenti> findByIstanza(Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = setFilterIstanza(istanza);
	ft.addRestriction(fr);
	return anagrafedocumentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Anagrafedocumenti> findByIstanzaAndAnagrafe(Istanze istanza, Anagrafe anagrafe, boolean isDocumentoPresente) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (istanza != null) {
	    fr = setFilterIstanza(istanza);
	}
	fr.addFilterField(FilterUtils.equals("id.codice", anagrafe.getId().getCodice(), "anagrafe", Integer.class));
	if (isDocumentoPresente) {
	    fr.addFilterField(FilterUtils.isNotNull("id.codice", "oggetto"));
	}
	ft.addRestriction(fr);
	return anagrafedocumentiDAO.findByFilterTable(ft);
    }

    private FilterRestriction setFilterIstanza(Istanze istanza) {

	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", istanza.getId().getCodice(), "istanza", Integer.class));
	return fr;
    }

    @Override
    public List<AnagrafedocumentiDTO> findByIstanzaAndAnagrafeDTO(Istanze istanza, Anagrafe anagrafe, boolean isDocumentoPresente) {

	return anagrafedocumentiDAO.findByIstanzaAndAnagrafeDTO(istanza, anagrafe, isDocumentoPresente);
    }

    @Override
    public List<AnagrafedocumentiDTO> findByIstanzaAndAnagrafeDTONonInDocAutorizzazione(Istanze istanza, Anagrafe anagrafe,
	    Integer codiceautorizzazione) {

	List<AnagrafedocumentiDTO> listAnagrafedocumentiDTOs = this.findByIstanzaAndAnagrafeDTO(istanza, anagrafe, true);
	List<AnagrafedocumentiDTO> risultato = new ArrayList<AnagrafedocumentiDTO>();
	for (AnagrafedocumentiDTO anagrafedocumentiDTO : listAnagrafedocumentiDTOs) {
	    // controllo che per l'autorizzazione passata il documento anagrafe non sia già stato associato all'aut tramite doc autorizzazioni
	    boolean isPresente = documentiautorizzazioneService.isInAutorizzazione(codiceautorizzazione, anagrafedocumentiDTO.getId().getCodice(),
		    "anagrafedocumenti");
	    log.debug(
		    "findByIstanzaAndAnagrafeDTONonInDocAutorizzazione# Aut = {},codice doc anagrafe = {}, presente in Documentiautorizzazione = {}",
		    new Object[] { codiceautorizzazione, anagrafedocumentiDTO.getId().getCodice(), isPresente });
	    if (!isPresente) {
		risultato.add(anagrafedocumentiDTO);
	    }
	}
	return risultato;
    }

    protected boolean isDeleteAllowed(Anagrafedocumenti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	boolean isExistInDocumentiAutorizzazione = documentiautorizzazioneService.isInAutorizzazione(entity.getId().getCodice(), "anagrafedocumenti");
	// esempio:
	if (isExistInDocumentiAutorizzazione) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "DOCUMENTI_AUTORIZZAZIONE", null));
	}
	boolean isExistEntityInMovimentiZipLogico = movimentiZipLogicoService.isDocumentoPresenteInZipLogico(entity.getId().getCodice(),
		"anagrafedocumenti");
	if (isExistEntityInMovimentiZipLogico) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI_ZIP_LOGICO", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
    //    private Integer controllaCancellaOggetti(Anagrafedocumenti entity, boolean isDelete) {
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
    //		if (oggettiService.controllaCancellaOggetto("ANAGRAFEDOCUMENTI", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Anagrafedocumenti entityCopy = this.findById(entity.getId());
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
    //			if (oggettiService.controllaCancellaOggetto("ANAGRAFEDOCUMENTI", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }

    @Override
    public void salvaDocumento(Oggetti filePDF, Integer codiceAnagrafe, Date dataStampa, String uuid, Integer idtipoDocumento) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("anagrafeId", codiceAnagrafe, Integer.class));
	fr.addFilterField(FilterUtils.equals("tipidocumentoId", idtipoDocumento, Integer.class));
	ft.addRestriction(fr);
	List<Anagrafedocumenti> docEsistenti = anagrafedocumentiDAO.findByFilterTable(ft);
	for (Anagrafedocumenti ad : docEsistenti) {
	    ad.setDatafinevalidita(dataStampa);
	    this.update(ad);
	}
	oggettiService.insert(filePDF);
	Anagrafedocumenti ad = new Anagrafedocumenti();
	ad.setOggetto(filePDF);
	ad.setDataregistrazione(dataStampa);
	ad.setDatainiziovalidita(dataStampa);
	ad.setRifdocumento(uuid);
	ad.setTipidocumento(tipidocumentoService.findById(new PkId(idtipoDocumento)));
	ad.setAnagrafe(anagrafeService.findById(new PkId(codiceAnagrafe)));
	ad.setOggetto(filePDF);
	this.insert(ad);
    }
}
