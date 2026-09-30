/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocumenticatDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumenticatService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lucap
 * 
 */
@Service
public class AlberoprocDocumenticatServiceImpl extends BaseServiceImpl<AlberoprocDocumenticat, PkId> implements AlberoprocDocumenticatService {

    private AlberoprocDocumenticatDAO alberoprocDocumenticatDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setAlberoprocDocumenticatDAO(AlberoprocDocumenticatDAO alberoprocDocumenticatDAO) {

	this.alberoprocDocumenticatDAO = alberoprocDocumenticatDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<AlberoprocDocumenticat> getEntityClass() {

	return AlberoprocDocumenticat.class;
    }

    @Override
    public List<AlberoprocDocumenticat> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocDocumenticatDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AlberoprocDocumenticat findById(PkId id) {

	return alberoprocDocumenticatDAO.findById(id);
    }

    @Override
    public void insert(AlberoprocDocumenticat entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    alberoprocDocumenticatDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void update(AlberoprocDocumenticat entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    alberoprocDocumenticatDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(AlberoprocDocumenticat entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	alberoprocDocumenticatDAO.delete(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
    }
    //    private Integer controllaCancellaOggetti(AlberoprocDocumenticat entity, boolean isDelete) {
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
    //		if (oggettiService.controllaCancellaOggetto("ALBEROPROC_DOCUMENTICAT", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    AlberoprocDocumenticat entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// se non è nullo allora sono in modifica
    //		// in inserimento non devo fare il controllo
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
    //			if (oggettiService.controllaCancellaOggetto("ALBEROPROC_DOCUMENTICAT", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
}
