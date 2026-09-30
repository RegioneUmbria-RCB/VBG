package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IAttivitadyn2modellitDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellitId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IAttivitadyn2modellitService;

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
public class IAttivitadyn2modellitServiceImpl extends BaseServiceImpl<IAttivitadyn2modellit, IAttivitadyn2modellitId> implements
	IAttivitadyn2modellitService {

    private IAttivitadyn2modellitDAO iattivitadyn2modellitDAO;

    @Autowired
    public void setIAttivitadyn2modellitDAO(IAttivitadyn2modellitDAO iattivitadyn2modellitDAO) {

	this.iattivitadyn2modellitDAO = iattivitadyn2modellitDAO;
    }

    @Override
    protected Class<IAttivitadyn2modellit> getEntityClass() {

	return IAttivitadyn2modellit.class;
    }

    @Override
    public List<IAttivitadyn2modellit> findAll(Integer firstResult, Integer maxResult) {

	return iattivitadyn2modellitDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IAttivitadyn2modellit entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2modellitDAO.insert(entity);
	}
    }

    @Override
    public IAttivitadyn2modellit findById(IAttivitadyn2modellitId id) {

	return iattivitadyn2modellitDAO.findById(id);
    }

    @Override
    public void update(IAttivitadyn2modellit entity) {

	if (validateEntity(entity)) {
	    iattivitadyn2modellitDAO.update(entity);
	}
    }

    @Override
    public void delete(IAttivitadyn2modellit entity) {

	if (isDeleteAllowed(entity)) {
	    iattivitadyn2modellitDAO.delete(entity);
	}
    }

    @Override
    public List<IAttivitadyn2modellit> findByAttivita(Integer iAttivita, Integer firstResult, Integer maxResult) {

	FilterTable ft = createFilterTableByAttivita(iAttivita);
	return iattivitadyn2modellitDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public int countByAttivita(Integer iAttivita) {

	FilterTable ft = createFilterTableByAttivita(iAttivita);
	return iattivitadyn2modellitDAO.countRecord(ft);
    }

    private FilterTable createFilterTableByAttivita(Integer iAttivita) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.fkIaId", iAttivita, Integer.class));
	//dyn2Modellit
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "dyn2Modellit"));
	return ft;
    }

    @Override
    public IAttivitadyn2modellit findByAttivitaAndModello(Integer codiceAttivita, Integer codiceModello) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction id = new FilterRestriction();
	id.addFilterField(FilterUtils.equals("id.fkIaId", codiceAttivita, Integer.class));
	id.addFilterField(FilterUtils.equals("id.fkD2mtId", codiceModello, Integer.class));
	//Lion se l'argomento indice è null allora vengono recuperati i dati a tutti gli indici (in tutte le N schede)
	ft.addRestriction(id);
	//Lion modificato l'ordinamento per molteplicità crescente. Dalla prima riga all'ultima,
	List<IAttivitadyn2modellit> list = iattivitadyn2modellitDAO.findByFilterTable(ft);
	if (!list.isEmpty()) {
	    return list.get(0);
	} else {
	    return null;
	}
    }

    @Override
    public boolean existsRecordsByCodiceScheda(Integer codiceAttivita, String codiceScheda) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction id = new FilterRestriction();
	id.addFilterField(FilterUtils.equals("id.fkIaId", codiceAttivita, Integer.class));
	id.addFilterField(FilterUtils.equals("codiceScheda", codiceScheda, "dyn2Modellit", String.class));
	ft.addRestriction(id);
	return iattivitadyn2modellitDAO.existsRecords(ft);
    }

    protected boolean isDeleteAllowed(IAttivitadyn2modellit entity) {

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
