package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.LetteretipoDAO;
import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipidocumento;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;
import it.gruppoinit.pal.gp.core.service.LetteretipoService;
import it.gruppoinit.pal.gp.core.service.OggettiService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.TipidocumentoService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class LetteretipoServiceImpl extends BaseServiceImpl<Letteretipo, PkId> implements LetteretipoService {

    private LetteretipoDAO letteretipoDAO;
    private OggettiService oggettiService;
    private SoftwareService softwareService;
    private TipidocumentoService tipidocumentoService;
    private TipiprocedureService tipiprocedureService;

    @Autowired
    public void setLetteretipoDAO(LetteretipoDAO letteretipoDAO) {

	this.letteretipoDAO = letteretipoDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setTipidocumentoService(TipidocumentoService tipidocumentoService) {

	this.tipidocumentoService = tipidocumentoService;
    }

    @Autowired
    public void setTipiprocedureService(TipiprocedureService tipiprocedureService) {

	this.tipiprocedureService = tipiprocedureService;
    }

    @Override
    protected Class<Letteretipo> getEntityClass() {

	return Letteretipo.class;
    }

    @Override
    public void delete(Letteretipo entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "file", true, entity.getId());
	if (isDeleteAllowed(entity)) {
	    letteretipoDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(Letteretipo entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getMercatiConfiguraziones().isEmpty()) {
	    delete = false;
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "MERCATI_CONFIGURAZIONE", null));
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Letteretipo> findAll(Integer firstResult, Integer maxResult) {

	return letteretipoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Letteretipo findById(PkId id) {

	return letteretipoDAO.findById(id);
    }

    @Override
    public void insert(Letteretipo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "file", false, entity.getId());
	    letteretipoDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    private void dataIntegration(Letteretipo entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Documento passato è nullo");
	}
	if (entity.getFlagDisabilitato() == null) {
	    entity.setFlagDisabilitato(Boolean.FALSE);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(Letteretipo entity) {

	Software software = softwareService.bindDomainObject(entity.getSoftware(), String.class, "codice");
	entity.setSoftware(software);
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getFile(), PkId.class, "id.codice");
	entity.setFile(oggetto);
    }

    @Override
    public void update(Letteretipo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "file", false, entity.getId());
	    letteretipoDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<Letteretipo> findByDescrizione(Letteretipo entity, boolean includiDisabilitate) {

	return letteretipoDAO.findByDescrizione(entity.getDescrizione(), includiDisabilitate);
    }

    @Override
    public List<Letteretipo> findByDescrizioneAndSoftware(String descrizione, String software, boolean includiDisabilitate) {

	return letteretipoDAO.findByDescrizioneAndSoftware(descrizione, software, includiDisabilitate);
    }

    //    private Integer controllaCancellaOggetti(Letteretipo entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getFile())) {
    //	    if (!(null == entity.getFile().getId())) {
    //		if (!(null == entity.getFile().getId().getCodice())) {
    //		    codiceOggetto = entity.getFile().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("LETTERETIPO", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Letteretipo entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// se non è nullo allora sono in modifica
    //		// in inserimento non devo fare il controllo
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getFile())) {
    //		    if (!(null == entityCopy.getFile().getId())) {
    //			if (!(null == entityCopy.getFile().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getFile().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("LETTERETIPO", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    @Override
    public boolean checkSeDisabilitare(Letteretipo letteretipo) {

	List<Tipidocumento> docs = tipidocumentoService.findByLetteretipo(letteretipo, 0, 2);
	if (docs.size() > 0) {
	    return false;
	}
	List<Tipiprocedure> procs = tipiprocedureService.findByLetteretipo(letteretipo, 0, 2);
	if (procs.size() > 0) {
	    return false;
	}
	return true;
    }
}
