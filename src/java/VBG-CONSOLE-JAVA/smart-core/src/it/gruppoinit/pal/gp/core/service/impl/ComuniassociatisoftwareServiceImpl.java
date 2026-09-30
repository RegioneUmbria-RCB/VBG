package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatisoftwareDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ComuniassociatisoftwareService;
import it.gruppoinit.pal.gp.core.service.OggettiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class ComuniassociatisoftwareServiceImpl extends BaseServiceImpl<Comuniassociatisoftware, PkId> implements ComuniassociatisoftwareService {

    private ComuniassociatisoftwareDAO comuniassociatisoftwareDAO;
    private OggettiService oggettiService;

    @Autowired
    public void setOggettiService(OggettiService oggettiService) {

	this.oggettiService = oggettiService;
    }

    @Autowired
    public void setComuniassociatisoftwareDAO(ComuniassociatisoftwareDAO comuniassociatisoftwareDAO) {

	this.comuniassociatisoftwareDAO = comuniassociatisoftwareDAO;
    }

    @Override
    protected Class<Comuniassociatisoftware> getEntityClass() {

	return Comuniassociatisoftware.class;
    }

    @Override
    public List<Comuniassociatisoftware> findAll(Integer firstResult, Integer maxResult) {

	return comuniassociatisoftwareDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Comuniassociatisoftware entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    comuniassociatisoftwareDAO.insert(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public Comuniassociatisoftware findById(PkId id) {

	return comuniassociatisoftwareDAO.findById(id);
    }

    @Override
    public void update(Comuniassociatisoftware entity) {

	if (validateEntity(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", false, entity.getId());
	    comuniassociatisoftwareDAO.update(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public void delete(Comuniassociatisoftware entity) {

	if (isDeleteAllowed(entity)) {
	    Integer codiceOggettoDaCancellare = controllaCancellaOggetti(entity, "oggetti", true, entity.getId());
	    comuniassociatisoftwareDAO.delete(entity);
	    if (codiceOggettoDaCancellare != null) {
		Oggetti oggettoDaCancellare = oggettiService.findById(new PkId(codiceOggettoDaCancellare));
		oggettiService.delete(oggettoDaCancellare);
	    }
	}
    }

    @Override
    public Comuniassociatisoftware findByComune(Comuni comuni) {

	return comuniassociatisoftwareDAO.findByComune(comuni);
    }
    // protected boolean isDeleteAllowed(Comuniassociatisoftware entity) {
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
    //    private Integer controllaCancellaOggetti(Comuniassociatisoftware entity, boolean isDelete) {
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
    //		if (oggettiService.controllaCancellaOggetto("COMUNIASSOCIATISOFTWARE", "SI_STEMMA", codiceOggetto)) {
    //		    return codiceOggetto;
    //		}
    //	    }
    //	} else {
    //	    // sono in modifica / insert
    //	    // vedo se posso cancellare il vecchio oggetto
    //	    Comuniassociatisoftware entityCopy = this.findById(entity.getId());
    //	    if (null != entityCopy) {
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
    //			if (oggettiService.controllaCancellaOggetto("COMUNIASSOCIATISOFTWARE", "SI_STEMMA", codiceOggettoOld)) {
    //			    return codiceOggettoOld;
    //			}
    //		    }
    //		}
    //	    }
    //	}
    //	return null;
    //    }
}
