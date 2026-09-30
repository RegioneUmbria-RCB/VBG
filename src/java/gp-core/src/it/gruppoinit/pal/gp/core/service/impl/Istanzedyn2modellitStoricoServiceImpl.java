package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2modellitStoricoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2modellitStoricoService;

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
public class Istanzedyn2modellitStoricoServiceImpl extends BaseServiceImpl<Istanzedyn2modellitStorico, Istanzedyn2modellitStoricoId> implements
	Istanzedyn2modellitStoricoService {

    private Istanzedyn2modellitStoricoDAO istanzedyn2modellitstoricoDAO;

    @Autowired
    public void setIstanzedyn2modellitStoricoDAO(Istanzedyn2modellitStoricoDAO istanzedyn2modellitstoricoDAO) {

	this.istanzedyn2modellitstoricoDAO = istanzedyn2modellitstoricoDAO;
    }

    @Override
    protected Class<Istanzedyn2modellitStorico> getEntityClass() {

	return Istanzedyn2modellitStorico.class;
    }

    @Override
    public List<Istanzedyn2modellitStorico> findAll(Integer firstResult, Integer maxResult) {

	return istanzedyn2modellitstoricoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzedyn2modellitStorico entity) {

	if (validateEntity(entity)) {
	    istanzedyn2modellitstoricoDAO.insert(entity);
	}
    }

    @Override
    public Istanzedyn2modellitStorico findById(Istanzedyn2modellitStoricoId id) {

	return istanzedyn2modellitstoricoDAO.findById(id);
    }

    @Override
    public void update(Istanzedyn2modellitStorico entity) {

	if (validateEntity(entity)) {
	    istanzedyn2modellitstoricoDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzedyn2modellitStorico entity) {

	if (isDeleteAllowed(entity)) {
	    istanzedyn2modellitstoricoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Istanzedyn2modellitStorico entity) {

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
    public List<Istanzedyn2modellitStorico> findByIstanza(Integer codiceistanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceistanza", codiceistanza, Integer.class));
	ft.addRestriction(fr);
	return istanzedyn2modellitstoricoDAO.findByFilterTable(ft);
    }

    @Override
    public int calcolaProgressivoVersione(Integer codiceistanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceistanza", codiceistanza, Integer.class));
	ft.addRestriction(fr);
	Object max = istanzedyn2modellitstoricoDAO.max(ft, "id.idversione");
	if (max == null) {
	    return 1;
	}
	int maxInt = (Integer) max;
	if (maxInt == 0) {
	    return 1;
	}
	return ++maxInt;
    }
}
