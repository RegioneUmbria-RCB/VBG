package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.StpTipologieEndo1DAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.StpTipologieEndo1;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.StpTipologieEndo1Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StpTipologieEndo1ServiceImpl extends BaseServiceImpl<StpTipologieEndo1, PkId> implements StpTipologieEndo1Service {

    private StpTipologieEndo1DAO stpTipologieEndo1DAO;

    @Autowired
    public void setStpTipologieEndo1DAO(StpTipologieEndo1DAO stpTipologieEndo1DAO) {

	this.stpTipologieEndo1DAO = stpTipologieEndo1DAO;
    }

    @Override
    protected Class<StpTipologieEndo1> getEntityClass() {

	return StpTipologieEndo1.class;
    }

    @Override
    public void delete(StpTipologieEndo1 entity) {

	stpTipologieEndo1DAO.delete(entity);
    }

    @Override
    public List<StpTipologieEndo1> findAll(Integer firstResult, Integer maxResult) {

	return stpTipologieEndo1DAO.findAll(firstResult, maxResult);
    }

    @Override
    public StpTipologieEndo1 findById(PkId id) {

	return stpTipologieEndo1DAO.findById(id);
    }

    @Override
    public void insert(StpTipologieEndo1 entity) {

	if (validateEntity(entity)) {
	    stpTipologieEndo1DAO.insert(entity);
	}
    }

    @Override
    public void update(StpTipologieEndo1 entity) {

	if (validateEntity(entity)) {
	    stpTipologieEndo1DAO.update(entity);
	}
    }

    @Override
    public StpTipologieEndo1 findbyStpCodice(Integer stpCodice) {

	return stpTipologieEndo1DAO.findbyStpCodice(stpCodice);
    }

    @Override
    public StpTipologieEndo1 findbyTipifamiglieendo(Tipifamiglieendo tipifamiglieendo) {

	return stpTipologieEndo1DAO.findbyTipifamiglieendo(tipifamiglieendo);
    }

    @Override
    public StpTipologieEndo1 findbyStpCodiceAndSoftwareTipiFamEndo(int stpCodice, String codiceSoftware) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codiceStp", stpCodice, Integer.class));
	fr.addFilterField(FilterUtils.equals("codice", codiceSoftware, "tipifamiglieendo.software", String.class));
	ft.addRestriction(fr);
	List<StpTipologieEndo1> stpTipologieEndo1 = stpTipologieEndo1DAO.findByFilterTable(ft);
	if (!stpTipologieEndo1.isEmpty()) {
	    return stpTipologieEndo1.get(0);
	}
	return null;
    }
}
