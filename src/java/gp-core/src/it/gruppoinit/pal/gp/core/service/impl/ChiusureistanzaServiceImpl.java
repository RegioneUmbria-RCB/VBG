package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ChiusureistanzaDAO;
import it.gruppoinit.pal.gp.core.domain.Chiusureistanza;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.ChiusureistanzaService;

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
public class ChiusureistanzaServiceImpl extends BaseServiceImpl<Chiusureistanza, PkId> implements ChiusureistanzaService {

    private ChiusureistanzaDAO chiusureistanzaDAO;

    @Autowired
    public void setChiusureistanzaDAO(ChiusureistanzaDAO chiusureistanzaDAO) {

	this.chiusureistanzaDAO = chiusureistanzaDAO;
    }

    @Override
    protected Class<Chiusureistanza> getEntityClass() {

	return Chiusureistanza.class;
    }

    @Override
    public List<Chiusureistanza> findAll(Integer firstResult, Integer maxResult) {

	return chiusureistanzaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Chiusureistanza entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    chiusureistanzaDAO.insert(entity);
	}
    }

    @Override
    public Chiusureistanza findById(PkId id) {

	return chiusureistanzaDAO.findById(id);
    }

    @Override
    public void update(Chiusureistanza entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    chiusureistanzaDAO.update(entity);
	}
    }

    private void dataIntegration(Chiusureistanza entity) {

	if (entity == null) {
	    throw new RuntimeException("Il parametro chiusura istanza è nullo");
	}
	if (entity.getAnalqual() == null) {
	    entity.setAnalqual(Boolean.FALSE);
	}
	if (entity.getCds() == null) {
	    entity.setCds(Boolean.FALSE);
	}
	if (entity.getEndo() == null) {
	    entity.setEndo(Boolean.FALSE);
	}
	if (entity.getIstanza() == null) {
	    entity.setIstanza(Boolean.FALSE);
	}
	if (entity.getOneri() == null) {
	    entity.setOneri(Boolean.FALSE);
	}
    }

    @Override
    public void delete(Chiusureistanza entity) {

	if (isDeleteAllowed(entity)) {
	    chiusureistanzaDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Chiusureistanza entity) {

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
