package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.StpModalitaAperturaDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.TipoSchedaEndo;
import it.gruppoinit.pal.gp.core.domain.StpModalitaApertura;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.StpModalitaAperturaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author
 */
@Service
public class StpModalitaAperturaServiceImpl extends BaseServiceImpl<StpModalitaApertura, String> implements StpModalitaAperturaService {

    private StpModalitaAperturaDAO stpmodalitaaperturaDAO;

    @Autowired
    public void setStpModalitaAperturaDAO(StpModalitaAperturaDAO stpmodalitaaperturaDAO) {

	this.stpmodalitaaperturaDAO = stpmodalitaaperturaDAO;
    }

    @Override
    protected Class<StpModalitaApertura> getEntityClass() {

	return StpModalitaApertura.class;
    }

    @Override
    public List<StpModalitaApertura> findAll(Integer firstResult, Integer maxResult) {

	return stpmodalitaaperturaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(StpModalitaApertura entity) {

	//	if (validateEntity(entity)) {
	//	    stpmodalitaaperturaDAO.insert(entity);
	//	}
	throw new NotImplementedException();
    }

    @Override
    public StpModalitaApertura findById(String id) {

	return stpmodalitaaperturaDAO.findById(id);
    }

    @Override
    public void update(StpModalitaApertura entity) {

	//	if (validateEntity(entity)) {
	//	    stpmodalitaaperturaDAO.update(entity);
	//	}
	throw new NotImplementedException();
    }

    @Override
    public void delete(StpModalitaApertura entity) {

	//	if (isDeleteAllowed(entity)) {
	//	    stpmodalitaaperturaDAO.delete(entity);
	//	}
	throw new NotImplementedException();
    }

    @Override
    public List<StpModalitaApertura> findStpModalitaByTipoScheda(TipoSchedaEndo schedaTipoEndo1) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	switch (schedaTipoEndo1) {
	case SCHEDA_TIPO_ENDO1:
	    fr.addFilterField(FilterUtils.equals("tipoScheda", WebConstants.SCHEDA_TIPO_ENDO1, String.class));
	    break;
	case SCHEDA_TIPO_ENDO2:
	    fr.addFilterField(FilterUtils.equals("tipoScheda", WebConstants.SCHEDA_TIPO_ENDO2, String.class));
	    break;
	default:
	    throw new RuntimeException("wrong switch value!");
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return stpmodalitaaperturaDAO.findByFilterTable(ft);
    }
    //
    //    protected boolean isDeleteAllowed(StpModalitaApertura entity) {
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
