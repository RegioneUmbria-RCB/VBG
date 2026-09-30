package it.sgp.middleware.security.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.sgp.middleware.security.dao.ComunisecurityAppDAO;
import it.sgp.middleware.security.domain.ComunisecurityApp;
import it.sgp.middleware.security.service.ComunisecurityAppService;
import it.sgp.middleware.security.utils.Utilities;

/**
 * 
 * @author
 */
@Service
@Transactional
public class ComunisecurityAppServiceImpl extends BaseServiceImpl<ComunisecurityApp, String> implements ComunisecurityAppService {

    private static final String ENCRYPTING_ALGORITHM = "MD5";
    private ComunisecurityAppDAO comunisecurityappDAO;

    @Autowired
    public void setComunisecurityAppDAO(ComunisecurityAppDAO comunisecurityappDAO) {

	this.comunisecurityappDAO = comunisecurityappDAO;
    }

    @Override
    protected Class<ComunisecurityApp> getEntityClass() {

	return ComunisecurityApp.class;
    }

    @Override
    public List<ComunisecurityApp> findAll(Integer firstResult, Integer maxResult) {

	if (firstResult == null || maxResult == null) {
	    return comunisecurityappDAO.findAll();
	}
	Page<ComunisecurityApp> allRecords = comunisecurityappDAO.findAll(PageRequest.of(firstResult, maxResult).withSort(Sort.by("id")));
	return allRecords.hasContent() ? allRecords.getContent() : Collections.emptyList();
    }

    @Override
    public void insert(ComunisecurityApp entity) {

	checkPassword(entity, false);
	if (validateEntity(entity)) {
	    comunisecurityappDAO.saveAndFlush(entity);
	}
    }

    @Override
    public ComunisecurityApp findById(String id) {

	return comunisecurityappDAO.findById(id).orElse(null);
    }

    @Override
    public void update(ComunisecurityApp entity) {

	checkPassword(entity, true);
	if (validateEntity(entity)) {
	    comunisecurityappDAO.saveAndFlush(entity);
	}
    }

    @Override
    public void delete(ComunisecurityApp entity) {

	if (isDeleteAllowed(entity)) {
	    comunisecurityappDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ComunisecurityApp entity) {

	boolean delete = true;
	// List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	//	if (!_ivs.isEmpty()) {
	//	    this.throwValidationMessages(_ivs);
	//	}
	return delete;
    }

    /**
     * La funzione controlla se la password è stata passata e nel caso la cripta con l'algoritmo MD5
     * 
     * @param entity
     * @param isUpdate
     */
    private void checkPassword(ComunisecurityApp entity, boolean isUpdate) {

	String password = "";
	if (StringUtils.isBlank(entity.getPasswordClear())) {
	    if (isUpdate) {
		ComunisecurityApp copy = this.findById(entity.getId());
		password = copy.getPassword();
		// comunisecurityappDAO.evict(copy); TODO //https://stackoverflow.com/questions/26795436/spring-jparepository-detach-and-attach-entity
		entity.setPassword(password);
		return;
	    }
	} else {
	    String passwordClear = entity.getPasswordClear();
	    password = Utilities.getHashText(passwordClear, ENCRYPTING_ALGORITHM, false);
	    entity.setPassword(password);
	    entity.setPasswordClear(null);
	}
    }

    @Override
    public List<ComunisecurityApp> findAll() {

	return this.findAll(null, null);
    }

    @Override
    public Page<ComunisecurityApp> findAllByExamplePaginated(PageRequest pageable, Example<ComunisecurityApp> exampleFromRequest) {

	return comunisecurityappDAO.findAll(exampleFromRequest, pageable);
    }
}
