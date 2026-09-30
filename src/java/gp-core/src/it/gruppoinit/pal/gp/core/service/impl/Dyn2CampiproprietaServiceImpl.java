package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiproprietaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Dyn2Campiproprieta;
import it.gruppoinit.pal.gp.core.domain.Dyn2CampiproprietaId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Dyn2CampiproprietaService;

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
public class Dyn2CampiproprietaServiceImpl extends BaseServiceImpl<Dyn2Campiproprieta, Dyn2CampiproprietaId> implements Dyn2CampiproprietaService {

    private Dyn2CampiproprietaDAO dyn2campiproprietaDAO;

    @Autowired
    public void setDyn2CampiproprietaDAO(Dyn2CampiproprietaDAO dyn2campiproprietaDAO) {

	this.dyn2campiproprietaDAO = dyn2campiproprietaDAO;
    }

    @Override
    protected Class<Dyn2Campiproprieta> getEntityClass() {

	return Dyn2Campiproprieta.class;
    }

    @Override
    public List<Dyn2Campiproprieta> findAll(Integer firstResult, Integer maxResult) {

	return dyn2campiproprietaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Dyn2Campiproprieta entity) {

	if (validateEntity(entity)) {
	    dyn2campiproprietaDAO.insert(entity);
	}
    }

    @Override
    public Dyn2Campiproprieta findById(Dyn2CampiproprietaId id) {

	return dyn2campiproprietaDAO.findById(id);
    }

    @Override
    public void update(Dyn2Campiproprieta entity) {

	if (validateEntity(entity)) {
	    dyn2campiproprietaDAO.update(entity);
	}
    }

    @Override
    public void delete(Dyn2Campiproprieta entity) {

	if (isDeleteAllowed(entity)) {
	    dyn2campiproprietaDAO.delete(entity);
	}
    }

    @Override
    public List<Dyn2Campiproprieta> findByDyn2Campi(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkD2cId", codice, Integer.class));
	ft.addRestriction(fr);
	return dyn2campiproprietaDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(Dyn2Campiproprieta entity) {

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
