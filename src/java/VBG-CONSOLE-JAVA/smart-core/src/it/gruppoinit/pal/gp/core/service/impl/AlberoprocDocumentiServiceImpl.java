package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocDocumentiDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenti;
import it.gruppoinit.pal.gp.core.domain.AlberoprocDocumenticat;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumentiService;
import it.gruppoinit.pal.gp.core.service.AlberoprocDocumenticatService;
import it.gruppoinit.pal.gp.core.service.AlberoprocService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlberoprocDocumentiServiceImpl extends BaseServiceImpl<AlberoprocDocumenti, PkId> implements AlberoprocDocumentiService {

    private AlberoprocDocumentiDAO alberoprocDocumentiDAO;
    private AlberoprocDocumenticatService alberoprocDocumenticatService;
    private OggettiService oggettiService;
    private AlberoprocService alberoprocService;

    @Autowired
    public void setAlberoprocDocumenticatService(AlberoprocDocumenticatService alberoprocDocumenticatService) {

	this.alberoprocDocumenticatService = alberoprocDocumenticatService;
    }

    @Autowired
    public void setAlberoprocService(AlberoprocService alberoprocService) {

	this.alberoprocService = alberoprocService;
    }

    @Autowired
    public void setAlberoprocDocumentiDAO(AlberoprocDocumentiDAO alberoprocDocumentiDAO) {

	this.alberoprocDocumentiDAO = alberoprocDocumentiDAO;
    }

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Override
    protected Class<AlberoprocDocumenti> getEntityClass() {

	return AlberoprocDocumenti.class;
    }

    @Override
    public void delete(AlberoprocDocumenti entity) {

	Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	alberoprocDocumentiDAO.delete(entity);
	if (codiceOggettoDaCancellare != null) {
	    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
	    oggettiService.delete(oggettoDaCancellare);
	}
	// alberoprocService.updateAlberoprocCache();
    }

    @Override
    public List<AlberoprocDocumenti> findAll(Integer firstResult, Integer maxResult) {

	return alberoprocDocumentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public AlberoprocDocumenti findById(PkId id) {

	return alberoprocDocumentiDAO.findById(id);
    }

    @Override
    public void insert(AlberoprocDocumenti entity) {

	dataIntegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
		alberoprocDocumentiDAO.insert(entity);
		if (codiceOggettoDaCancellare != null) {
		    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		    oggettiService.delete(oggettoDaCancellare);
		}
		// alberoprocService.updateAlberoprocCache();
	    }
	}
    }

    @Override
    public void update(AlberoprocDocumenti entity) {

	dataIntegration(entity);
	if (isInsertUpdateAllowed(entity)) {
	    if (validateEntity(entity)) {
		Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
		alberoprocDocumentiDAO.update(entity);
		if (codiceOggettoDaCancellare != null) {
		    Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		    oggettiService.delete(oggettoDaCancellare);
		}
		// alberoprocService.updateAlberoprocCache();
	    }
	}
    }

    private void dataIntegration(AlberoprocDocumenti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro alberoprocdocumenti non può essere vuoto");
	}
	if (entity.getFlagInserimentoAut() == null) {
	    entity.setFlagInserimentoAut(Boolean.FALSE);
	}
	if (entity.getFlgDomandafo() == null) {
	    entity.setFlgDomandafo(Boolean.FALSE);
	}
	if (entity.getFoRichiedefirma() == null) {
	    entity.setFoRichiedefirma(Boolean.FALSE);
	}
	if (entity.getOrdine() == null) {
	    entity.setOrdine(new Integer(0));
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(AlberoprocDocumenti entity) {

	AlberoprocDocumenticat abdc = alberoprocDocumenticatService.bindDomainObject(entity.getAlberoprocDocumenticat(), PkId.class, "id.codice");
	entity.setAlberoprocDocumenticat(abdc);
	Oggetti oggetto = oggettiService.bindDomainObject(entity.getOggetto(), PkId.class, "id.codice");
	entity.setOggetto(oggetto);
	Alberoproc alberoproc = alberoprocService.bindDomainObject(entity.getAlberoproc(), PkId.class, "id.codice");
	entity.setAlberoproc(alberoproc);
    }

    @Override
    public List<AlberoprocDocumenti> findByAlberoProc(Integer codice) {

	return alberoprocDocumentiDAO.findByAlberoProc(codice);
    }

    //    private Integer controllaCancellaOggetti(AlberoprocDocumenti entity, boolean isDelete) {
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
    //		if (oggettiService.controllaCancellaOggetto("ALBEROPROC_DOCUMENTI", "CODICEOGGETTO", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    AlberoprocDocumenti entityCopy = this.findById(entity.getId());
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
    //		    if (null != codiceOggetto) {
    //			if (codiceOggettoOld.compareTo(codiceOggetto) != 0) {
    //			    // cancello solo se sono diversi altrimenti no
    //			    if (oggettiService.controllaCancellaOggetto("ALBEROPROC_DOCUMENTI", "CODICEOGGETTO", codiceOggettoOld)) {
    //				return codiceOggettoOld;
    //			    }
    //			}
    //		    } else {
    //			// cancello solo se sono diversi altrimenti no
    //			if (oggettiService.controllaCancellaOggetto("ALBEROPROC_DOCUMENTI", "CODICEOGGETTO", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    private boolean isInsertUpdateAllowed(AlberoprocDocumenti entity) {

	boolean insertOrUpdate = true;
	if (entity.getFlgDomandafo()) {
	    List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	    Alberoproc alberoproc = alberoprocService.findById(entity.getAlberoproc().getId());
	    Set<AlberoprocDocumenti> alberoprocDocumentiSet = alberoproc.getAlberoprocDocumentis();
	    for (AlberoprocDocumenti alberoprocDocumenti : alberoprocDocumentiSet) {
		if (alberoprocDocumenti.getFlgDomandafo() != null && alberoprocDocumenti.getFlgDomandafo()
			&& !EntityUtils.equals(alberoprocDocumenti.getId(), entity.getId())) {
		    _ivs.add(new InvalidValue("alert.alberoprocDocumenti.flgDomandafo_presente", null, "", "", null));
		    insertOrUpdate = false;
		    break;
		}
	    }
	    if (!insertOrUpdate) {
		this.throwValidationMessages(_ivs);
	    }
	}
	return insertOrUpdate;
    }

    @Override
    public int findMaxOrder() {

	return alberoprocDocumentiDAO.findMaxOrder();
    }
}
