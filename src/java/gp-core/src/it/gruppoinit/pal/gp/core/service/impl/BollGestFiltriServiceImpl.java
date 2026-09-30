package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.domain.BollGestFiltri;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestFiltriDAO;
import it.gruppoinit.pal.gp.core.service.BollGestFiltriService;

/**
 * 
 * @author
 */
@Loggable(featureName = "bollettazione")
@Service
public class BollGestFiltriServiceImpl extends BaseServiceImpl<BollGestFiltri, PkId> implements BollGestFiltriService {

    private BollGestFiltriDAO bollgestfiltriDAO;

    @Autowired
    public void setBollGestFiltriDAO(BollGestFiltriDAO bollgestfiltriDAO) {

	this.bollgestfiltriDAO = bollgestfiltriDAO;
    }

    @Override
    protected Class<BollGestFiltri> getEntityClass() {

	return BollGestFiltri.class;
    }

    @Override
    public List<BollGestFiltri> findAll(Integer firstResult, Integer maxResult) {

	return bollgestfiltriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BollGestFiltri entity) {

	if (validateEntity(entity)) {
	    bollgestfiltriDAO.insert(entity);
	}
    }

    @Override
    public BollGestFiltri findById(PkId id) {

	return bollgestfiltriDAO.findById(id);
    }

    @Override
    public void update(BollGestFiltri entity) {

	if (validateEntity(entity)) {
	    bollgestfiltriDAO.update(entity);
	}
    }

    @Override
    public void delete(BollGestFiltri entity) {

	if (isDeleteAllowed(entity)) {
	    bollgestfiltriDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollGestFiltri entity) {

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
