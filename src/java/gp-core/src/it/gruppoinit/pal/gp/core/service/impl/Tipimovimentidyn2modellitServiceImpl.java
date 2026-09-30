package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Tipimovimentidyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentidyn2modellitId;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Tipimovimentidyn2modellitService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author gianpaolot
 */
@Service
public class Tipimovimentidyn2modellitServiceImpl extends BaseServiceImpl<Tipimovimentidyn2modellit, Tipimovimentidyn2modellitId> implements
	Tipimovimentidyn2modellitService {

    private Tipimovimentidyn2modellitDAO tipimovimentidyn2modellitDAO;

    @Autowired
    public void setTipimovimentidyn2modellitDAO(Tipimovimentidyn2modellitDAO tipimovimentidyn2modellitDAO) {

	this.tipimovimentidyn2modellitDAO = tipimovimentidyn2modellitDAO;
    }

    @Override
    protected Class<Tipimovimentidyn2modellit> getEntityClass() {

	return Tipimovimentidyn2modellit.class;
    }

    @Override
    public List<Tipimovimentidyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return tipimovimentidyn2modellitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Tipimovimentidyn2modellit entity) {

	if (validateEntity(entity)) {
	    tipimovimentidyn2modellitDAO.insert(entity);
	}
    }

    @Override
    public Tipimovimentidyn2modellit findById(Tipimovimentidyn2modellitId id) {

	return tipimovimentidyn2modellitDAO.findById(id);
    }

    @Override
    public void update(Tipimovimentidyn2modellit entity) {

	if (validateEntity(entity)) {
	    tipimovimentidyn2modellitDAO.update(entity);
	}
    }

    @Override
    public void delete(Tipimovimentidyn2modellit entity) {

	if (isDeleteAllowed(entity)) {
	    tipimovimentidyn2modellitDAO.delete(entity);
	}
    }

    // protected boolean isDeleteAllowed(Tipimovimentidyn2modellit entity) {
    //
    // boolean delete = true;
    // List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    // TODO_validare_la_delete
    // // esempio:
    // // if (entity.getList().size() > 0) {
    // // _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    // // }
    // if (!_ivs.isEmpty()) {
    // this.throwValidationMessages(_ivs);
    // }
    // return delete;
    // }
    @Override
    public List<Tipimovimentidyn2modellit> findByTipimovimento(Tipimovimento tipimovimento) {

	if (EntityUtils.getNestedProperty(tipimovimento, "id.tipomovimento") == null) {
	    throw new IllegalArgumentException("Il parametro tipo movimento non è valido");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction criterio = new FilterRestriction();
	criterio.addFilterField(FilterUtils.equals("id.tipomovimento", tipimovimento.getId().getTipomovimento(), String.class));
	ft.addRestriction(criterio);
	List<Tipimovimentidyn2modellit> list = tipimovimentidyn2modellitDAO.findByFilterTable(ft);
	return list;
    }
}
