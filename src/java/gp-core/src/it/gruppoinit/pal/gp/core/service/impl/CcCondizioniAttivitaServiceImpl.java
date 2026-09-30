package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.CcCondizioniAttivitaDAO;
import it.gruppoinit.pal.gp.core.domain.CcCondizioniAttivita;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.CcCondizioniAttivitaService;

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
public class CcCondizioniAttivitaServiceImpl extends BaseServiceImpl<CcCondizioniAttivita, PkId> implements CcCondizioniAttivitaService {

    private CcCondizioniAttivitaDAO cccondizioniattivitaDAO;

    @Autowired
    public void setCcCondizioniAttivitaDAO(CcCondizioniAttivitaDAO cccondizioniattivitaDAO) {

	this.cccondizioniattivitaDAO = cccondizioniattivitaDAO;
    }

    @Override
    protected Class<CcCondizioniAttivita> getEntityClass() {

	return CcCondizioniAttivita.class;
    }

    @Override
    public List<CcCondizioniAttivita> findAll(Integer firstResult, Integer maxResult) {

	return cccondizioniattivitaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(CcCondizioniAttivita entity) {

	if (validateEntity(entity)) {
	    cccondizioniattivitaDAO.insert(entity);
	}
    }

    @Override
    public CcCondizioniAttivita findById(PkId id) {

	return cccondizioniattivitaDAO.findById(id);
    }

    @Override
    public void update(CcCondizioniAttivita entity) {

	if (validateEntity(entity)) {
	    cccondizioniattivitaDAO.update(entity);
	}
    }

    @Override
    public void delete(CcCondizioniAttivita entity) {

	if (isDeleteAllowed(entity)) {
	    cccondizioniattivitaDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(CcCondizioniAttivita entity) {

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
