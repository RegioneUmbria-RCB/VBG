package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ProtocolloDAO;
import it.gruppoinit.pal.gp.core.domain.Faq;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Protocollo;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.ProtocolloService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class ProtocolloServiceImpl extends BaseServiceImpl<Protocollo, PkId> implements ProtocolloService {

    private ProtocolloDAO protocolloDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setProtocolloDAO(ProtocolloDAO protocolloDAO) {

	this.protocolloDAO = protocolloDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<Protocollo> getEntityClass() {

	return Protocollo.class;
    }

    @Override
    public List<Protocollo> findAll(Integer firstResult, Integer maxResult) {

	return protocolloDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Protocollo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    protocolloDAO.insert(entity);
	}
    }

    @Override
    public Protocollo findById(PkId id) {

	return protocolloDAO.findById(id);
    }

    @Override
    public void update(Protocollo entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    protocolloDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(Protocollo entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    protocolloDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(Faq entity) {

	boolean delete = true;
	return delete;
    }

    //    private Integer controllaCancellaOggetti(Protocollo entity, boolean isDelete) {
    //
    //	Integer codiceOggetto = null;
    //	Integer codiceOggettoOld = null;
    //	if (!(null == entity.getOggetti())) {
    //	    if (!(null == entity.getOggetti().getId())) {
    //		if (!(null == entity.getOggetti().getId().getCodice())) {
    //		    codiceOggetto = entity.getOggetti().getId().getCodice();
    //		}
    //	    }
    //	}
    //	if (isDelete) {
    //	    // sono in cancellazione
    //	    if (!(null == codiceOggetto)) {
    //		if (oggettiService.controllaCancellaOggetto("PROTOCOLLO", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Protocollo entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
    //		// se non è nullo allora sono in modifica
    //		// in inserimento non devo fare il controllo
    //		// recupero il vecchio id
    //		if (!(null == entityCopy.getOggetti())) {
    //		    if (!(null == entityCopy.getOggetti().getId())) {
    //			if (!(null == entityCopy.getOggetti().getId().getCodice())) {
    //			    codiceOggettoOld = entityCopy.getOggetti().getId().getCodice();
    //			}
    //		    }
    //		}
    //		if (null != codiceOggettoOld) {
    //		    if (!codiceOggettoOld.equals(codiceOggetto)) {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("PROTOCOLLO", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    private void dataIntegration(Protocollo entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il Protocollo passato è nullo");
	}
	fixMergeEntityProperties(entity);
    }

    protected void fixMergeEntityProperties(Protocollo entity) {

	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetti(), PkId.class, "id.codice");
	entity.setOggetti(oggetto);
    }
}
