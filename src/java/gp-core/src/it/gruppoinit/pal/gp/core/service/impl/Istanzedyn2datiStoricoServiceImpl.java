package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.Istanzedyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.Istanzedyn2datiStoricoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.Istanzedyn2datiStoricoService;

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
public class Istanzedyn2datiStoricoServiceImpl extends BaseServiceImpl<Istanzedyn2datiStorico, Istanzedyn2datiStoricoId> implements
	Istanzedyn2datiStoricoService {

    private Istanzedyn2datiStoricoDAO istanzedyn2datistoricoDAO;

    @Autowired
    public void setIstanzedyn2datiStoricoDAO(Istanzedyn2datiStoricoDAO istanzedyn2datistoricoDAO) {

	this.istanzedyn2datistoricoDAO = istanzedyn2datistoricoDAO;
    }

    @Override
    protected Class<Istanzedyn2datiStorico> getEntityClass() {

	return Istanzedyn2datiStorico.class;
    }

    @Override
    public List<Istanzedyn2datiStorico> findAll(Integer firstResult, Integer maxResult) {

	return istanzedyn2datistoricoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzedyn2datiStorico entity) {

	if (validateEntity(entity)) {
	    istanzedyn2datistoricoDAO.insert(entity);
	}
    }

    @Override
    public Istanzedyn2datiStorico findById(Istanzedyn2datiStoricoId id) {

	return istanzedyn2datistoricoDAO.findById(id);
    }

    @Override
    public void update(Istanzedyn2datiStorico entity) {

	if (validateEntity(entity)) {
	    istanzedyn2datistoricoDAO.update(entity);
	}
    }

    @Override
    public void delete(Istanzedyn2datiStorico entity) {

	if (isDeleteAllowed(entity)) {
	    istanzedyn2datistoricoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Istanzedyn2datiStorico entity) {

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
    public List<Istanzedyn2datiStorico> findByIstanza(Integer codiceistanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceistanza", codiceistanza, Integer.class));
	ft.addRestriction(fr);
	return istanzedyn2datistoricoDAO.findByFilterTable(ft);
    }
}
