package it.gruppoinit.pal.gp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.OValiditacoefficientiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.OValiditacoefficienti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.OValiditacoefficientiService;

/**
 * 
 * @author
 */
@Service
public class OValiditacoefficientiServiceImpl extends BaseServiceImpl<OValiditacoefficienti, PkId> implements OValiditacoefficientiService {

    private OValiditacoefficientiDAO ovaliditacoefficientiDAO;

    @Autowired
    public void setOValiditacoefficientiDAO(OValiditacoefficientiDAO ovaliditacoefficientiDAO) {

	this.ovaliditacoefficientiDAO = ovaliditacoefficientiDAO;
    }

    @Override
    protected Class<OValiditacoefficienti> getEntityClass() {

	return OValiditacoefficienti.class;
    }

    @Override
    public List<OValiditacoefficienti> findAll(Integer firstResult, Integer maxResult) {

	return ovaliditacoefficientiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(OValiditacoefficienti entity) {

	if (validateEntity(entity)) {
	    ovaliditacoefficientiDAO.insert(entity);
	}
    }

    @Override
    public OValiditacoefficienti findById(PkId id) {

	return ovaliditacoefficientiDAO.findById(id);
    }

    @Override
    public void update(OValiditacoefficienti entity) {

	if (validateEntity(entity)) {
	    ovaliditacoefficientiDAO.update(entity);
	}
    }

    @Override
    public void delete(OValiditacoefficienti entity) {

	if (isDeleteAllowed(entity)) {
	    ovaliditacoefficientiDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(OValiditacoefficienti entity) {

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
	return ovaliditacoefficientiDAO.existsRecords(ft);
    }
}
