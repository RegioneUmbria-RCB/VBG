package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.SanAmbitiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.SanAmbiti;
import it.gruppoinit.pal.gp.core.service.SanAmbitiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class SanAmbitiServiceImpl extends BaseServiceImpl<SanAmbiti, Integer> implements SanAmbitiService {

    private SanAmbitiDAO sanambitiDAO;

    @Autowired
    public void setSanAmbitiDAO(SanAmbitiDAO sanambitiDAO) {

	this.sanambitiDAO = sanambitiDAO;
    }

    @Override
    protected Class<SanAmbiti> getEntityClass() {

	return SanAmbiti.class;
    }

    @Override
    public List<SanAmbiti> findAll(Integer firstResult, Integer maxResult) {

	return sanambitiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(SanAmbiti entity) {

	//	if (validateEntity(entity)) {
	//	    sanambitiDAO.insert(entity);
	//	}
	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public SanAmbiti findById(Integer id) {

	return sanambitiDAO.findById(id);
    }

    @Override
    public void update(SanAmbiti entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public void delete(SanAmbiti entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    protected boolean isDeleteAllowed(SanAmbiti entity) {

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
