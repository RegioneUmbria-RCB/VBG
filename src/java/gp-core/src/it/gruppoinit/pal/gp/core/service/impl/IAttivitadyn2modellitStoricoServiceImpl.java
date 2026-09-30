package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modellitStoricoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStorico;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitStoricoId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiStoricoService;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitStoricoService;

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
public class IAttivitadyn2modellitStoricoServiceImpl extends BaseServiceImpl<IAttivitadyn2modellitStorico, IAttivitadyn2modellitStoricoId> implements
	IAttivitadyn2modellitStoricoService {

    private IAttivitadyn2modellitStoricoDAO iattivitadyn2modellitstoricoDAO;
    private IAttivitadyn2datiStoricoService iAttivitadyn2datiStoricoService;

    @Autowired
    public void setIAttivitadyn2modellitStoricoDAO(IAttivitadyn2modellitStoricoDAO iattivitadyn2modellitstoricoDAO) {

	this.iattivitadyn2modellitstoricoDAO = iattivitadyn2modellitstoricoDAO;
    }

    @Autowired
    public void setiAttivitadyn2datiStoricoService(IAttivitadyn2datiStoricoService iAttivitadyn2datiStoricoService) {

	this.iAttivitadyn2datiStoricoService = iAttivitadyn2datiStoricoService;
    }

    @Override
    protected Class<IAttivitadyn2modellitStorico> getEntityClass() {

	return IAttivitadyn2modellitStorico.class;
    }

    @Override
    public List<IAttivitadyn2modellitStorico> findAll(Integer firstResult, Integer maxResult) {

	return iattivitadyn2modellitstoricoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivitadyn2modellitStorico entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2modellitstoricoDAO.insert(entity);
	}
    }

    @Override
    public IAttivitadyn2modellitStorico findById(IAttivitadyn2modellitStoricoId id) {

	return iattivitadyn2modellitstoricoDAO.findById(id);
    }

    @Override
    public void update(IAttivitadyn2modellitStorico entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2modellitstoricoDAO.update(entity);
	}
    }

    @Override
    public void delete(IAttivitadyn2modellitStorico entity) {

	if (isDeleteAllowed(entity)) {
	    childDelete(entity);
	    iattivitadyn2modellitstoricoDAO.delete(entity);
	}
    }

    @Override
    public List<IAttivitadyn2modellitStorico> findByAttivita(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIaId", codice, Integer.class));
	ft.addRestriction(fr);
	return iattivitadyn2modellitstoricoDAO.findByFilterTable(ft, null, null);
    }

    @Override
    protected void childDelete(IAttivitadyn2modellitStorico entity) {

	List<IAttivitadyn2datiStorico> listDyn2DatiStorico = iAttivitadyn2datiStoricoService.findByAttivita(entity.getId().getFkIaId());
	for (IAttivitadyn2datiStorico iAttivitadyn2datiStorico : listDyn2DatiStorico) {
	    iAttivitadyn2datiStoricoService.delete(iAttivitadyn2datiStorico);
	}
    }

    protected boolean isDeleteAllowed(IAttivitadyn2modellitStorico entity) {

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
