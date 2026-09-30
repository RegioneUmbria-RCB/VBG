package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.RiTipiprocedimentoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.RiTipiprocedimento;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.RiTipiprocedimentoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class RiTipiprocedimentoServiceImpl extends BaseServiceImpl<RiTipiprocedimento, String> implements RiTipiprocedimentoService {

    private RiTipiprocedimentoDAO ritipiprocedimentoDAO;

    @Autowired
    public void setRiTipiprocedimentoDAO(RiTipiprocedimentoDAO ritipiprocedimentoDAO) {

	this.ritipiprocedimentoDAO = ritipiprocedimentoDAO;
    }

    @Override
    protected Class<RiTipiprocedimento> getEntityClass() {

	return RiTipiprocedimento.class;
    }

    @Override
    public List<RiTipiprocedimento> findAll(Integer firstResult, Integer maxResult) {

	return ritipiprocedimentoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(RiTipiprocedimento entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public RiTipiprocedimento findById(String id) {

	return ritipiprocedimentoDAO.findById(id);
    }

    @Override
    public void update(RiTipiprocedimento entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public void delete(RiTipiprocedimento entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public List<RiTipiprocedimento> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.startsWith("descrizione", textToSearch));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return ritipiprocedimentoDAO.findByFilterTable(ft, firstResult, maxResults);
    }
    //    protected boolean isDeleteAllowed(RiTipiprocedimento entity) {
    //
    //		boolean delete = true;
    //		List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
    //		TODO_validare_la_delete
    //		// esempio:
    //		// if (entity.getList().size() > 0) {
    //		//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
    //		// }
    //		if (!_ivs.isEmpty()) {
    //			this.throwValidationMessages(_ivs);
    //		}
    //		return delete;
    //    }
}
