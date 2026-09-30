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

import it.sgp.middleware.security.dao.ComunisecurityDAO;
import it.sgp.middleware.security.domain.Comunisecurity;
import it.sgp.middleware.security.domain.ComunisecurityConnection;
import it.sgp.middleware.security.service.ComunisecurityService;

/**
 * 
 * @author
 */
@Service
@Transactional
public class ComunisecurityServiceImpl extends BaseServiceImpl<Comunisecurity, String> implements ComunisecurityService {

    private ComunisecurityDAO comunisecurityDAO;

    @Autowired
    public void setComunisecurityDAO(ComunisecurityDAO comunisecurityDAO) {

	this.comunisecurityDAO = comunisecurityDAO;
    }

    @Override
    protected Class<Comunisecurity> getEntityClass() {

	return Comunisecurity.class;
    }

    @Override
    public List<Comunisecurity> findAll(Integer firstResult, Integer maxResult) {

	if (firstResult == null || maxResult == null) {
	    return comunisecurityDAO.findAll();
	}
	Page<Comunisecurity> allRecords = comunisecurityDAO.findAll(PageRequest.of(firstResult, maxResult).withSort(Sort.by("id")));
	return allRecords.hasContent() ? allRecords.getContent() : Collections.emptyList();
    }

    @Override
    public void insert(Comunisecurity entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    comunisecurityDAO.saveAndFlush(entity);
	}
    }

    private void dataIntegration(Comunisecurity entity) {

	for (ComunisecurityConnection connection : entity.getComunisecurityConnections()) {
	    connection.getId().setFkAlias(entity.getId());
	}
    }

    @Override
    public Comunisecurity findById(String id) {

	return comunisecurityDAO.findById(id).orElse(null);
    }

    @Override
    public void update(Comunisecurity entity) {

	if (validateEntity(entity)) {
	    comunisecurityDAO.saveAndFlush(entity);
	}
    }

    @Override
    public void delete(Comunisecurity entity) {

	if (isDeleteAllowed(entity)) {
	    comunisecurityDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Comunisecurity entity) {

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
    public List<Comunisecurity> findByDescrizioneOrAlias(String descrizione) {

	return comunisecurityDAO.findByDescrizioneContainingOrIdContainingAllIgnoreCaseOrderByDescrizioneAsc(descrizione, descrizione);
    }

    @Override
    public List<Comunisecurity> findAll() {

	return this.findAll(null, null);
    }

    @Override
    public Page<Comunisecurity> findAllByExamplePaginated(PageRequest pageable, Example<Comunisecurity> exampleFromRequest) {

	return comunisecurityDAO.findAll(exampleFromRequest, pageable);
    }
}
