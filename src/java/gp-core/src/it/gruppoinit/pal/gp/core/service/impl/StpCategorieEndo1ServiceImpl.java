package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StpCategorieEndo1DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpCategorieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipiendo;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.StpCategorieEndo1Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StpCategorieEndo1ServiceImpl extends BaseServiceImpl<StpCategorieEndo1, PkId> implements StpCategorieEndo1Service {

    private StpCategorieEndo1DAO stpCategorieEndo1DAO;

    @Autowired
    public void setStpCategorieEndo1DAO(StpCategorieEndo1DAO stpCategorieEndo1DAO) {

	this.stpCategorieEndo1DAO = stpCategorieEndo1DAO;
    }

    @Override
    protected Class<StpCategorieEndo1> getEntityClass() {

	return StpCategorieEndo1.class;
    }

    @Override
    public void delete(StpCategorieEndo1 entity) {

	stpCategorieEndo1DAO.delete(entity);
    }

    @Override
    public List<StpCategorieEndo1> findAll(Integer firstResult, Integer maxResult) {

	return stpCategorieEndo1DAO.findAll(firstResult, maxResult);
    }

    @Override
    public StpCategorieEndo1 findById(PkId id) {

	return stpCategorieEndo1DAO.findById(id);
    }

    @Override
    public void insert(StpCategorieEndo1 entity) {

	if (validateEntity(entity)) {
	    stpCategorieEndo1DAO.insert(entity);
	}
    }

    @Override
    public void update(StpCategorieEndo1 entity) {

	if (validateEntity(entity)) {
	    stpCategorieEndo1DAO.update(entity);
	}
    }

    @Override
    public StpCategorieEndo1 findByStpCodice(Integer stpCodice) {

	return stpCategorieEndo1DAO.findByStpCodice(stpCodice);
    }

    @Override
    public StpCategorieEndo1 findByTipiendo(Tipiendo tipiendo) {

	return stpCategorieEndo1DAO.findByTipiendo(tipiendo);
    }

    @Override
    public StpCategorieEndo1 findByStpCodiceAndSoftwareTipiEndo(Integer stpCodice, String codicesoftware) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codiceStp", stpCodice, Integer.class));
	fr.addFilterField(FilterUtils.equals("codice", codicesoftware, "tipiendo.software", String.class));
	ft.addRestriction(fr);
	List<StpCategorieEndo1> stpCategorieEndo1 = stpCategorieEndo1DAO.findByFilterTable(ft);
	if (!stpCategorieEndo1.isEmpty()) {
	    return stpCategorieEndo1.get(0);
	}
	return null;
    }
}
