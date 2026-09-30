package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.domain.CcConfigurazione;
import it.gruppoinit.pal.gp.core.domain.CcConfigurazioneId;
import it.gruppoinit.pal.gp.core.service.CcConfigurazioneService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class CcConfigurazioneServiceImpl extends BaseServiceImpl<CcConfigurazione, CcConfigurazioneId> implements CcConfigurazioneService {

    private CcConfigurazioneDAO ccconfigurazioneDAO;

    @Autowired
    public void setCcConfigurazioneDAO(CcConfigurazioneDAO ccconfigurazioneDAO) {

	this.ccconfigurazioneDAO = ccconfigurazioneDAO;
    }

    @Override
    protected Class<CcConfigurazione> getEntityClass() {

	return CcConfigurazione.class;
    }

    @Override
    public List<CcConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	return ccconfigurazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcConfigurazione entity) {

	if (validateEntity(entity)) {
	    ccconfigurazioneDAO.insert(entity);
	}
    }

    @Override
    public CcConfigurazione findById(CcConfigurazioneId id) {

	return ccconfigurazioneDAO.findById(id);
    }

    @Override
    public void update(CcConfigurazione entity) {

	if (validateEntity(entity)) {
	    ccconfigurazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(CcConfigurazione entity) {

	if (isDeleteAllowed(entity)) {
	    ccconfigurazioneDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcConfigurazione entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// TODO _validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	// _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
