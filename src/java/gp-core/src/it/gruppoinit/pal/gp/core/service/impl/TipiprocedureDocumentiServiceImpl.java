package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureDocumentiDAO;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureDocumenti;
import it.gruppoinit.pal.gp.core.features.oggetti.OggettiService;
import it.gruppoinit.pal.gp.core.service.TipiprocedureDocumentiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class TipiprocedureDocumentiServiceImpl extends BaseServiceImpl<TipiprocedureDocumenti, PkId> implements TipiprocedureDocumentiService {

    private TipiprocedureDocumentiDAO tipiproceduredocumentiDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setTipiprocedureDocumentiDAO(TipiprocedureDocumentiDAO tipiproceduredocumentiDAO) {

	this.tipiproceduredocumentiDAO = tipiproceduredocumentiDAO;
    }

    @Override
    protected Class<TipiprocedureDocumenti> getEntityClass() {

	return TipiprocedureDocumenti.class;
    }

    @Override
    public List<TipiprocedureDocumenti> findAll(Integer firstResult, Integer maxResult) {

	return tipiproceduredocumentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(TipiprocedureDocumenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggetto = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    tipiproceduredocumentiDAO.insert(entity);
	    if (codiceOggetto != null) {
		Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
		oggettiService.delete(oggetto);
	    }
	}
    }

    @Override
    public TipiprocedureDocumenti findById(PkId id) {

	return tipiproceduredocumentiDAO.findById(id);
    }

    @Override
    public void update(TipiprocedureDocumenti entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    Integer codiceOggetto = controllaCancellaOggetti(entity, "oggetto", false, entity.getId());
	    tipiproceduredocumentiDAO.update(entity);
	    if (codiceOggetto != null) {
		Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
		oggettiService.delete(oggetto);
	    }
	}
    }

    @Override
    public void delete(TipiprocedureDocumenti entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggetto = controllaCancellaOggetti(entity, "oggetto", true, entity.getId());
	    tipiproceduredocumentiDAO.delete(entity);
	    entity.setOggetto(null);
	    if (codiceOggetto != null) {
		Oggetti oggetto = oggettiService.findById(new PkId(codiceOggetto));
		oggettiService.delete(oggetto);
	    }
	}
    }

    private void dataIntegration(TipiprocedureDocumenti entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro è nullo TipiprocedureDocumenti");
	}
	if (entity.getRichiesto() == null) {
	    entity.setRichiesto(Boolean.FALSE);
	}
	if (entity.getFoRichiedefirma() == null) {
	    entity.setFoRichiedefirma(Boolean.FALSE);
	}
    }
    //    private Integer controllaCancellaOggetti(TipiprocedureDocumenti entity, boolean isDelete) {
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
    //		if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE_DOCUMENTI", "TP_ID", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    TipiprocedureDocumenti entityCopy = this.findById(entity.getId());
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
    //			if (oggettiService.controllaCancellaOggetto("TIPIPROCEDURE_DOCUMENTI", "TP_ID", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
    // protected boolean isDeleteAllowed(TipiprocedureDocumenti entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
}
