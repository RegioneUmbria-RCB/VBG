package it.sgp.middleware.security.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sgp.middleware.security.dao.ComunisecurityConnectionDAO;
import it.sgp.middleware.security.domain.ComunisecurityConnection;
import it.sgp.middleware.security.domain.ComunisecurityConnectionId;
import it.sgp.middleware.security.service.ComunisecurityConnectionService;

/**
 * 
 * @author
 */
@Service
@Transactional
public class ComunisecurityConnectionServiceImpl extends BaseServiceImpl<ComunisecurityConnection, ComunisecurityConnectionId>
	implements ComunisecurityConnectionService {

    private ComunisecurityConnectionDAO comunisecurityconnectionDAO;

    @Autowired
    public void setComunisecurityConnectionDAO(ComunisecurityConnectionDAO comunisecurityconnectionDAO) {

	this.comunisecurityconnectionDAO = comunisecurityconnectionDAO;
    }

    @Override
    protected Class<ComunisecurityConnection> getEntityClass() {

	return ComunisecurityConnection.class;
    }

    @Override
    public List<ComunisecurityConnection> findAll(Integer firstResult, Integer maxResult) {

	if (firstResult == null || maxResult == null) {
	    return comunisecurityconnectionDAO.findAll();
	}
	Page<ComunisecurityConnection> allRecords = comunisecurityconnectionDAO
		.findAll(PageRequest.of(firstResult, maxResult).withSort(Sort.by("id.ambiente")));
	return allRecords.hasContent() ? allRecords.getContent() : Collections.emptyList();
	//	
    }

    @Override
    public void insert(ComunisecurityConnection entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    comunisecurityconnectionDAO.saveAndFlush(entity);
	}
    }

    private void dataIntegration(ComunisecurityConnection entity) {

	if (entity == null) {
	    return;
	}
	if (entity.getOverrideConf() == null) {
	    entity.setOverrideConf(Boolean.FALSE);
	}
    }

    @Override
    public ComunisecurityConnection findById(ComunisecurityConnectionId id) {

	return comunisecurityconnectionDAO.findById(id).orElse(null);
    }

    @Override
    public void update(ComunisecurityConnection entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    comunisecurityconnectionDAO.saveAndFlush(entity);
	}
    }

    @Override
    public void delete(ComunisecurityConnection entity) {

	if (isDeleteAllowed(entity)) {
	    comunisecurityconnectionDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ComunisecurityConnection entity) {

	boolean delete = true;
	//	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	// esempio:
	//	// if (entity.getList().size() > 0) {
	//	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//	// }
	//	if (!_ivs.isEmpty()) {
	//	    this.throwValidationMessages(_ivs);
	//	}
	return delete;
    }

    @Override
    public List<ComunisecurityConnection> findAll() {

	return this.findAll(null, null);
    }

    @Override
    public Page<ComunisecurityConnection> findAllByExamplePaginated(PageRequest pageable, Example<ComunisecurityConnection> exampleFromRequest) {

	return comunisecurityconnectionDAO.findAll(exampleFromRequest, pageable);
    }
}
