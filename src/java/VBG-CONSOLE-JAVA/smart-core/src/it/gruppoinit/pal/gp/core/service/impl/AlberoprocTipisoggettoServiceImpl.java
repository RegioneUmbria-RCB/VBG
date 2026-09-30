package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.AlberoprocTipisoggettoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTipisoggetto;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AlberoprocTipisoggettoService;

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
public class AlberoprocTipisoggettoServiceImpl extends BaseServiceImpl<AlberoprocTipisoggetto, PkId> implements AlberoprocTipisoggettoService {

    private AlberoprocTipisoggettoDAO alberoproctipisoggettoDAO;

    @Autowired
    public void setAlberoprocTipisoggettoDAO(AlberoprocTipisoggettoDAO alberoproctipisoggettoDAO) {

	this.alberoproctipisoggettoDAO = alberoproctipisoggettoDAO;
    }

    @Override
    protected Class<AlberoprocTipisoggetto> getEntityClass() {

	return AlberoprocTipisoggetto.class;
    }

    @Override
    public List<AlberoprocTipisoggetto> findAll(Integer firstResult, Integer maxResult) {

	return alberoproctipisoggettoDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(AlberoprocTipisoggetto entity) {

	if (validateEntity(entity)) {
	    alberoproctipisoggettoDAO.insert(entity);
	}
    }

    @Override
    public AlberoprocTipisoggetto findById(PkId id) {

	return alberoproctipisoggettoDAO.findById(id);
    }

    @Override
    public void update(AlberoprocTipisoggetto entity) {

	if (validateEntity(entity)) {
	    alberoproctipisoggettoDAO.update(entity);
	}
    }

    @Override
    public void delete(AlberoprocTipisoggetto entity) {

	if (isDeleteAllowed(entity)) {
	    alberoproctipisoggettoDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(AlberoprocTipisoggetto entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	// esempio:
	// if (entity.getList().size() > 0) {
	//	 _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "NOME_TABELLA", null));
	// }
	if (!_ivs.isEmpty()) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<AlberoprocTipisoggetto> findByAlberoprocId(String idcomune, Integer codiceIntervento, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	fr.addFilterField(FilterUtils.equals("alberoprocId", codiceIntervento, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine", "tipisoggetto"));
	ft.addOrder(FilterUtils.orderAsc("tiposoggetto", "tipisoggetto"));
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return alberoproctipisoggettoDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<AlberoprocTipisoggetto> findByTipiSoggettoId(Integer codiceTiposoggetto, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("tipisoggettoId", codiceTiposoggetto, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return alberoproctipisoggettoDAO.findByFilterTable(ft, firstResult, maxResult);
    }
}
