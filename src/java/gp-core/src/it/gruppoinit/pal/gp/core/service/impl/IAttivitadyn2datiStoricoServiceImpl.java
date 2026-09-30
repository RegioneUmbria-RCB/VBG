package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStoricoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiStoricoService;

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
public class IAttivitadyn2datiStoricoServiceImpl extends BaseServiceImpl<IAttivitadyn2datiStorico, IAttivitadyn2datiStoricoId> implements
	IAttivitadyn2datiStoricoService {

    private IAttivitadyn2datiStoricoDAO iattivitadyn2datistoricoDAO;

    @Autowired
    public void setIAttivitadyn2datiStoricoDAO(IAttivitadyn2datiStoricoDAO iattivitadyn2datistoricoDAO) {

	this.iattivitadyn2datistoricoDAO = iattivitadyn2datistoricoDAO;
    }

    @Override
    protected Class<IAttivitadyn2datiStorico> getEntityClass() {

	return IAttivitadyn2datiStorico.class;
    }

    @Override
    public List<IAttivitadyn2datiStorico> findAll(Integer firstResult, Integer maxResult) {

	return iattivitadyn2datistoricoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivitadyn2datiStorico entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2datistoricoDAO.insert(entity);
	}
    }

    @Override
    public IAttivitadyn2datiStorico findById(IAttivitadyn2datiStoricoId id) {

	return iattivitadyn2datistoricoDAO.findById(id);
    }

    @Override
    public void update(IAttivitadyn2datiStorico entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2datistoricoDAO.update(entity);
	}
    }

    @Override
    public void delete(IAttivitadyn2datiStorico entity) {

	if (isDeleteAllowed(entity)) {
	    iattivitadyn2datistoricoDAO.delete(entity);
	}
    }

    @Override
    public List<IAttivitadyn2datiStorico> findByAttivita(Integer codiceAttivita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIaId", codiceAttivita, Integer.class));
	ft.addRestriction(fr);
	return iattivitadyn2datistoricoDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(IAttivitadyn2datiStorico entity) {

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
