package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipimodelliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimodelli;
import it.gruppoinit.pal.gp.core.service.TipimodelliService;

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
public class TipimodelliServiceImpl extends BaseServiceImpl<Tipimodelli, PkId> implements TipimodelliService {

    private TipimodelliDAO tipimodelliDAO;

    @Autowired
    public void setTipimodelliDAO(TipimodelliDAO tipimodelliDAO) {

	this.tipimodelliDAO = tipimodelliDAO;
    }

    @Override
    protected Class<Tipimodelli> getEntityClass() {

	return Tipimodelli.class;
    }

    @Override
    public List<Tipimodelli> findAll(Integer firstResult, Integer maxResult) {

	return tipimodelliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipimodelli entity) {

	if (validateEntity(entity)) {
	    tipimodelliDAO.insert(entity);
	}
    }

    @Override
    public Tipimodelli findById(PkId id) {

	return tipimodelliDAO.findById(id);
    }

    @Override
    public void update(Tipimodelli entity) {

	if (validateEntity(entity)) {
	    tipimodelliDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipimodelli entity) {

	if (isDeleteAllowed(entity)) {
	    tipimodelliDAO.delete(entity);
	}
    }

    @Override
    public List<Tipifamiglieendo> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult) {

	return tipimodelliDAO.findByDescrizione(textToSearch, firstResult, maxResult);
    }
    
    @Override
    public List<Tipimodelli> findBySoftwareAndModulo(String codicesoftware) {

	
	return tipimodelliDAO.findBySoftwareAndModulo(codicesoftware);
    }
    

    protected boolean isDeleteAllowed(Tipimodelli entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	if (entity.getModellis().size() > 0) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MODULISTICA", null));
	}
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

   
}
