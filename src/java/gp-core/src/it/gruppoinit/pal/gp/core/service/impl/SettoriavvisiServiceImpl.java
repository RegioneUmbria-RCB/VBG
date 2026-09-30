package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.SettoriavvisiDAO;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Settoriavvisi;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.SettoriavvisiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 * 
 */
@Service
public class SettoriavvisiServiceImpl extends BaseServiceImpl<Settoriavvisi, PkId> implements SettoriavvisiService {

    private SettoriavvisiDAO sectoriavvisiDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setSettoriavvisiDAO(SettoriavvisiDAO sectoriavvisiDAO) {

	this.sectoriavvisiDAO = sectoriavvisiDAO;
    }

    @Override
    protected Class<Settoriavvisi> getEntityClass() {

	return Settoriavvisi.class;
    }

    @Override
    public void delete(Settoriavvisi entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	sectoriavvisiDAO.delete(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }

    @Override
    public List<Settoriavvisi> findAll(Integer firstResult, Integer maxResult) {

	return sectoriavvisiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Settoriavvisi findById(PkId id) {

	return sectoriavvisiDAO.findById(id);
    }

    @Override
    public void insert(Settoriavvisi entity) {

	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    sectoriavvisiDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void update(Settoriavvisi entity) {

	if (validateEntity(entity) && isInsertAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    sectoriavvisiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<Settoriavvisi> findByFilter(Settoriavvisi filter) {

	return sectoriavvisiDAO.findByFilter(filter);
    }

    //    private Integer controllaCancellaOggetti(Settoriavvisi entity, boolean isDelete) {
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
    //		if (oggettiService.controllaCancellaOggetto("SETTORIAVVISI", "IDAVVISO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Settoriavvisi entityCopy = this.findById(entity.getId());
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
    //			if (oggettiService.controllaCancellaOggetto("SETTORIAVVISI", "IDAVVISO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    protected boolean isInsertAllowed(Settoriavvisi entity) {

	boolean isAllowed = true;
	// controlla che il range di mq iniziale non sia maggiore di quello finale
	if (entity.getRangea() != null && entity.getRangeda() != null && entity.getRangeda() > entity.getRangea()) {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    _ivs.add(new InvalidValue("service_error.range", entity.getClass(), "rangeda", null, entity));
	    this.throwValidationMessages(_ivs);
	}
	return isAllowed;
    }
}
