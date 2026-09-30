package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.audit.Loggable;
import it.gruppoinit.pal.gp.core.domain.BollGestTestata;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.bollettazione.gestione.dao.BollGestTestataDAO;
import it.gruppoinit.pal.gp.core.service.BollGestTestataService;

/**
 * 
 * @author
 */
@Loggable(featureName = "bollettazione")
@Service
public class BollGestTestataServiceImpl extends BaseServiceImpl<BollGestTestata, PkId> implements BollGestTestataService {

    private BollGestTestataDAO bollgesttestataDAO;

    @Autowired
    public void setBollGestTestataDAO(BollGestTestataDAO bollgesttestataDAO) {

	this.bollgesttestataDAO = bollgesttestataDAO;
    }

    @Override
    protected Class<BollGestTestata> getEntityClass() {

	return BollGestTestata.class;
    }

    @Override
    public List<BollGestTestata> findAll(Integer firstResult, Integer maxResult) {

	return bollgesttestataDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(BollGestTestata entity) {

	if (validateEntity(entity)) {
	    bollgesttestataDAO.insert(entity);
	}
    }

    @Override
    public BollGestTestata findById(PkId id) {

	return bollgesttestataDAO.findById(id);
    }

    @Override
    public void update(BollGestTestata entity) {

	if (validateEntity(entity)) {
	    bollgesttestataDAO.update(entity);
	}
    }

    @Override
    public void delete(BollGestTestata entity) {

	if (isDeleteAllowed(entity)) {
	    bollgesttestataDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(BollGestTestata entity) {

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

    @Override
    public String findImplementazioneByPosDeb(Integer codicePosDeb) {

	return bollgesttestataDAO.findImplementazioneByPosDeb(codicePosDeb);
    }

    @Override
    public Integer findCodIstanzaByDettPosDebitoria(Integer codicePosDeb) {

	return bollgesttestataDAO.findCodIstanzaByDettPosDebitoria(codicePosDeb);
    }
}
