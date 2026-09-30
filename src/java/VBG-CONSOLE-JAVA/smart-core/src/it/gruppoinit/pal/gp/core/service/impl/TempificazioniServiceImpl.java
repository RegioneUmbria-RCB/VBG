package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.TempificazioniDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tempificazioni;
import it.gruppoinit.pal.gp.core.service.TempificazioniService;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class TempificazioniServiceImpl extends BaseServiceImpl<Tempificazioni, PkId> implements TempificazioniService {

    private TempificazioniDAO tempificazioniDAO;

    @Autowired
    public void setTempificazioniDAO(TempificazioniDAO tempificazioniDAO) {

	this.tempificazioniDAO = tempificazioniDAO;
    }

    @Override
    protected Class<Tempificazioni> getEntityClass() {

	return Tempificazioni.class;
    }

    @Override
    public List<Tempificazioni> findAll(Integer firstResult, Integer maxResult) {

	return tempificazioniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tempificazioni entity) {

	if (validateEntity(entity)) {
	    tempificazioniDAO.insert(entity);
	}
    }

    @Override
    public Tempificazioni findById(PkId id) {

	return tempificazioniDAO.findById(id);
    }

    @Override
    public void update(Tempificazioni entity) {

	if (validateEntity(entity)) {
	    tempificazioniDAO.update(entity);
	}
    }

    @Override
    public void delete(Tempificazioni entity) {

	if (isDeleteAllowed(entity)) {
	    tempificazioniDAO.delete(entity);
	}
    }

    @Override
    public List<Tempificazioni> findByDescrizione(String tempificazione) {

	return tempificazioniDAO.findByDescrizione(tempificazione);
    }

    protected boolean isDeleteAllowed(Tempificazioni entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	Set<Inventarioprocedimenti> inventarioprocedimentis = entity.getInventarioprocedimentis();
	//	if (!inventarioprocedimentis.isEmpty()) {
	//	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROCEDIMENTI", null));
	//	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }
}
