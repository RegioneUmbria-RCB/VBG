package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.PermcdsinvitatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Permcdsinvitati;
import it.gruppoinit.pal.gp.core.domain.PermcdsinvitatiId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.PermcdsinvitatiService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Luca Proietti
 */
@Service
public class PermcdsinvitatiServiceImpl extends BaseServiceImpl<Permcdsinvitati, PermcdsinvitatiId> implements PermcdsinvitatiService {

    private PermcdsinvitatiDAO permcdsinvitatiDAO;

    @Autowired
    public void setPermcdsinvitatiDAO(PermcdsinvitatiDAO permcdsinvitatiDAO) {

	this.permcdsinvitatiDAO = permcdsinvitatiDAO;
    }

    @Override
    protected Class<Permcdsinvitati> getEntityClass() {

	return Permcdsinvitati.class;
    }

    @Override
    public List<Permcdsinvitati> findAll(Integer firstResult, Integer maxResult) {

	return permcdsinvitatiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Permcdsinvitati entity) {

	if (validateEntity(entity)) {
	    permcdsinvitatiDAO.insert(entity);
	}
    }

    @Override
    public Permcdsinvitati findById(PermcdsinvitatiId id) {

	return permcdsinvitatiDAO.findById(id);
    }

    @Override
    public void update(Permcdsinvitati entity) {

	if (validateEntity(entity)) {
	    permcdsinvitatiDAO.update(entity);
	}
    }

    @Override
    public void delete(Permcdsinvitati entity) {

	if (isDeleteAllowed(entity)) {
	    permcdsinvitatiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Permcdsinvitati entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//TODO_validare_la_delete
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Permcdsinvitati> findByAnagrafe(Integer codiceAnagrafe, Integer firstResult, Integer maxResult) {

	if (codiceAnagrafe == null) {
	    throw new IllegalArgumentException("findByAnagrafe: il parametro codiceAnagrafe e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAnagrafe, "anagrafe", Integer.class));
	filterTable.addRestriction(fr);
	return permcdsinvitatiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
