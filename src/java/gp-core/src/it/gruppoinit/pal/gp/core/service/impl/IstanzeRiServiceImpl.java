package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeRiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.IstanzeRi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeRiService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class IstanzeRiServiceImpl extends BaseServiceImpl<IstanzeRi, PkId> implements IstanzeRiService {

    private IstanzeRiDAO istanzeriDAO;

    @Autowired
    public void setIstanzeRiDAO(IstanzeRiDAO istanzeriDAO) {

	this.istanzeriDAO = istanzeriDAO;
    }

    @Override
    protected Class<IstanzeRi> getEntityClass() {

	return IstanzeRi.class;
    }

    @Override
    public List<IstanzeRi> findAll(Integer firstResult, Integer maxResult) {

	return istanzeriDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(IstanzeRi entity) {

	if (validateEntity(entity)) {
	    istanzeriDAO.insert(entity);
	}
    }

    @Override
    public IstanzeRi findById(PkId id) {

	return istanzeriDAO.findById(id);
    }

    @Override
    public void update(IstanzeRi entity) {

	if (validateEntity(entity)) {
	    istanzeriDAO.update(entity);
	}
    }

    @Override
    public void delete(IstanzeRi entity) {

	if (isDeleteAllowed(entity)) {
	    istanzeriDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(IstanzeRi entity) {

	return true;
	//	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	//	// TODO_validare_la_delete
	//	// esempio:
	//	// if (entity.getList().size() > 0) {
	//	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	//	// }
	//	if (!_ivs.isEmpty()) {
	//	    this.throwValidationMessages(_ivs);
	//	}
	//	return delete;
    }

    @Override
    public List<IstanzeRi> findByIstanza(Integer codiceIstanza) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("istanzeId", codiceIstanza, Integer.class));
	filterTable.addRestriction(fr);
	filterTable.addOrder(FilterUtils.orderAsc("id.codice"));
	return istanzeriDAO.findByFilterTable(filterTable);
    }
}
