package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.RiTipiinterventoDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.RiTipiintervento;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.RiTipiinterventoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class RiTipiinterventoServiceImpl extends BaseServiceImpl<RiTipiintervento, String> implements RiTipiinterventoService {

    private RiTipiinterventoDAO ritipiinterventoDAO;

    @Autowired
    public void setRiTipiinterventoDAO(RiTipiinterventoDAO ritipiinterventoDAO) {

	this.ritipiinterventoDAO = ritipiinterventoDAO;
    }

    @Override
    protected Class<RiTipiintervento> getEntityClass() {

	return RiTipiintervento.class;
    }

    @Override
    public List<RiTipiintervento> findAll(Integer firstResult, Integer maxResult) {

	return ritipiinterventoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(RiTipiintervento entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public RiTipiintervento findById(String id) {

	return ritipiinterventoDAO.findById(id);
    }

    @Override
    public void update(RiTipiintervento entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public void delete(RiTipiintervento entity) {

	throw new NotImplementedException("Metodo non implementato");
    }

    @Override
    public List<RiTipiintervento> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResults) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.startsWith("descrizione", textToSearch));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return ritipiinterventoDAO.findByFilterTable(ft, firstResult, maxResults);
    }
    //    protected boolean isDeleteAllowed(RiTipiintervento entity) {
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
