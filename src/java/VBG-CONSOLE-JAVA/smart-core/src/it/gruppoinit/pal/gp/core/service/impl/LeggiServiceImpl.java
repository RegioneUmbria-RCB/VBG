package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.LeggiDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocLeggi;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.LeggiService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeggiServiceImpl extends BaseServiceImpl<Leggi, PkId> implements LeggiService {

    private LeggiDAO leggiDAO;
    private OggettiService oggettiservice;

    @Autowired
    public void setOggettiservice(OggettiService oggettiservice) {

	this.oggettiservice = oggettiservice;
    }

    @Autowired
    public void setLeggiDAO(LeggiDAO leggiDAO) {

	this.leggiDAO = leggiDAO;
    }

    @Override
    public void delete(Leggi entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    leggiDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiservice.findById(new PkId(codiceOggettoDaCancellare));
		oggettiservice.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public List<Leggi> findAll(Integer firstResult, Integer maxResult) {

	return leggiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Leggi findById(PkId id) {

	return leggiDAO.findById(id);
    }

    @Override
    public void insert(Leggi entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    leggiDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiservice.findById(new PkId(codiceOggettoDaCancellare));
		oggettiservice.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void update(Leggi entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    leggiDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiservice.findById(new PkId(codiceOggettoDaCancellare));
		oggettiservice.delete(oggettoDaCancellare);
	    }
	}
    }

    protected boolean isDeleteAllowed(Leggi entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	Set<AlberoprocLeggi> alberoprocLeggis = entity.getAlberoprocLeggis();
	if (alberoprocLeggis != null && alberoprocLeggis.size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, "", "ALBEROPROC_LEGGI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    //    private Integer controllaCancellaOggetti(Leggi entity, boolean isDelete) {
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
    //		if (oggettiservice.controllaCancellaOggetto("LEGGI", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Leggi entityCopy = this.findById(entity.getId());
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
    //			if (oggettiservice.controllaCancellaOggetto("LEGGI", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    @Override
    public Class<Leggi> getEntityClass() {

	return Leggi.class;
    }

    @Override
    public List<Leggi> findAllWithOrder(String campo) {

	return leggiDAO.findAllWithOrder(campo);
    }
}
