package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2datiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IAttivita;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2dati;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2datiId;
import it.gruppoinit.pal.gp.core.domain.web.IAttivitadyn2datiFilter;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2datiService;

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
public class IAttivitadyn2datiServiceImpl extends BaseServiceImpl<IAttivitadyn2dati, IAttivitadyn2datiId> implements IAttivitadyn2datiService {

    private IAttivitadyn2datiDAO iattivitadyn2datiDAO;

    @Autowired
    public void setIAttivitadyn2datiDAO(IAttivitadyn2datiDAO iattivitadyn2datiDAO) {

	this.iattivitadyn2datiDAO = iattivitadyn2datiDAO;
    }

    @Override
    protected Class<IAttivitadyn2dati> getEntityClass() {

	return IAttivitadyn2dati.class;
    }

    @Override
    public List<IAttivitadyn2dati> findAll(Integer firstResult, Integer maxResult) {

	return iattivitadyn2datiDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivitadyn2dati entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2datiDAO.insert(entity);
	}
    }

    @Override
    public IAttivitadyn2dati findById(IAttivitadyn2datiId id) {

	return iattivitadyn2datiDAO.findById(id);
    }

    @Override
    public void update(IAttivitadyn2dati entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2datiDAO.update(entity);
	}
    }

    @Override
    public void delete(IAttivitadyn2dati entity) {

	if (isDeleteAllowed(entity)) {
	    iattivitadyn2datiDAO.delete(entity);
	}
    }

    @Override
    public List<IAttivitadyn2dati> findByAttivita(IAttivita iAttivita, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIaId", iAttivita.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	return iattivitadyn2datiDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<IAttivitadyn2dati> findByFilter(IAttivitadyn2datiFilter filter) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	if (filter.getFkIaId() != null) {
	    fr.addFilterField(FilterUtils.equals("id.fkIaId", filter.getFkIaId(), Integer.class));
	}
	if (filter.getFkD2cId() != null) {
	    fr.addFilterField(FilterUtils.equals("id.fkD2cId", filter.getFkD2cId(), Integer.class));
	}
	if (filter.getIndice() != null) {
	    fr.addFilterField(FilterUtils.equals("id.indice", filter.getIndice(), Integer.class));
	}
	if (filter.getIndiceMolteplicita() != null) {
	    fr.addFilterField(FilterUtils.equals("id.indiceMolteplicita", filter.getIndiceMolteplicita(), Integer.class));
	}
	ft.addRestriction(fr);
	List<IAttivitadyn2dati> risultato = iattivitadyn2datiDAO.findByFilterTable(ft);
	return risultato;
    }

    @Override
    public List<IAttivitadyn2dati> findByAttivitaAndDyn2Campi(Integer codiceattivita, Integer codice, Integer indice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction id = new FilterRestriction();
	id.addFilterField(FilterUtils.equals("id.fkIaId", codiceattivita, Integer.class));
	id.addFilterField(FilterUtils.equals("id.fkD2cId", codice, Integer.class));
	//Lion se l'argomento indice è null allora vengono recuperati i dati a tutti gli indici (in tutte le N schede)
	if (indice != null) {
	    id.addFilterField(FilterUtils.equals("id.indice", indice, Integer.class));
	} else {
	    ft.addOrder(FilterUtils.orderAsc("id.indice"));
	}
	ft.addRestriction(id);
	//Lion modificato l'ordinamento per molteplicità crescente. Dalla prima riga all'ultima,
	ft.addOrder(FilterUtils.orderAsc("id.indiceMolteplicita"));
	return iattivitadyn2datiDAO.findByFilterTable(ft);
    }

    protected boolean isDeleteAllowed(IAttivitadyn2dati entity) {

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
