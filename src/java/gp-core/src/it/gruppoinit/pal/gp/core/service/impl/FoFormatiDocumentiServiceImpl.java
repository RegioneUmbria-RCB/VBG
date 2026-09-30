package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.FoFormatiDocumentiDAO;
import it.gruppoinit.pal.gp.core.domain.FoFormatiDocumenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.FoFormatiDocumentiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 
 */
@Service
public class FoFormatiDocumentiServiceImpl extends BaseServiceImpl<FoFormatiDocumenti, PkId> implements FoFormatiDocumentiService {

    private FoFormatiDocumentiDAO foformatidocumentiDAO;

    @Autowired
    public void setFoFormatiDocumentiDAO(FoFormatiDocumentiDAO foformatidocumentiDAO) {

	this.foformatidocumentiDAO = foformatidocumentiDAO;
    }

    @Override
    protected Class<FoFormatiDocumenti> getEntityClass() {

	return FoFormatiDocumenti.class;
    }

    @Override
    public List<FoFormatiDocumenti> findAll(Integer firstResult, Integer maxResult) {

	return foformatidocumentiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(FoFormatiDocumenti entity) {

	if (validateEntity(entity)) {
	    foformatidocumentiDAO.insert(entity);
	}
    }

    @Override
    public FoFormatiDocumenti findById(PkId id) {

	return foformatidocumentiDAO.findById(id);
    }

    @Override
    public void update(FoFormatiDocumenti entity) {

	if (validateEntity(entity)) {
	    foformatidocumentiDAO.update(entity);
	}
    }

    @Override
    public void delete(FoFormatiDocumenti entity) {

	if (isDeleteAllowed(entity)) {
	    foformatidocumentiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(FoFormatiDocumenti entity) {

		boolean delete = true;
	//		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//		TODO_validare_la_delete
	//		// esempio:
	//		// if (entity.getList().size() > 0) {
	//		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//		// }
	//		if (!_ivs.isEmpty()) {
	//			this.throwValidationMessages(_ivs);
	//		}
		return delete;
    }
}
