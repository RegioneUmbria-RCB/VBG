package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.CanoniConfigurazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazione;
import it.gruppoinit.pal.gp.core.domain.CanoniConfigurazioneId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.CanoniConfigurazioneService;

/**
 * 
 * @author
 */
@Service
public class CanoniConfigurazioneServiceImpl extends BaseServiceImpl<CanoniConfigurazione, CanoniConfigurazioneId>
	implements CanoniConfigurazioneService {

    private CanoniConfigurazioneDAO canoniconfigurazioneDAO;

    @Autowired
    public void setCanoniConfigurazioneDAO(CanoniConfigurazioneDAO canoniconfigurazioneDAO) {

	this.canoniconfigurazioneDAO = canoniconfigurazioneDAO;
    }

    @Override
    protected Class<CanoniConfigurazione> getEntityClass() {

	return CanoniConfigurazione.class;
    }

    @Override
    public List<CanoniConfigurazione> findAll(Integer firstResult, Integer maxResult) {

	return canoniconfigurazioneDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CanoniConfigurazione entity) {

	if (validateEntity(entity)) {
	    canoniconfigurazioneDAO.insert(entity);
	}
    }

    @Override
    public CanoniConfigurazione findById(CanoniConfigurazioneId id) {

	return canoniconfigurazioneDAO.findById(id);
    }

    @Override
    public void update(CanoniConfigurazione entity) {

	if (validateEntity(entity)) {
	    canoniconfigurazioneDAO.update(entity);
	}
    }

    @Override
    public void delete(CanoniConfigurazione entity) {

	if (isDeleteAllowed(entity)) {
	    canoniconfigurazioneDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CanoniConfigurazione entity) {

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

    @Override
    public boolean existRecordByCurrentSoftware() {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	return canoniconfigurazioneDAO.existsRecords(ft);
    }
}
