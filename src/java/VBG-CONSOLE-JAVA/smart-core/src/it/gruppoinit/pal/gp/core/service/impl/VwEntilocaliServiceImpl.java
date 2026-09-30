package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwEntilocaliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.VwEntilocali;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.VwEntilocaliService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwEntilocaliServiceImpl extends BaseServiceImpl<VwEntilocali, String> implements VwEntilocaliService {

    private VwEntilocaliDAO vwEntilocaliDAO;

    @Autowired
    public void setVwEntilocaliDAO(VwEntilocaliDAO vwEntilocaliDAO) {

	this.vwEntilocaliDAO = vwEntilocaliDAO;
    }

    @Override
    public void insert(VwEntilocali entity) {

    }

    @Override
    public void update(VwEntilocali entity) {

    }

    @Override
    public void delete(VwEntilocali entity) {

    }

    @Override
    public List<VwEntilocali> findAll(Integer firstResult, Integer maxResult) {

	return vwEntilocaliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public VwEntilocali findById(String id) {

	return vwEntilocaliDAO.findById(id);
    }

    @Override
    protected Class<VwEntilocali> getEntityClass() {

	return VwEntilocali.class;
    }

    @Override
    public List<VwEntilocali> findByDescrizione(String textToSearch) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isNotBlank(textToSearch)) {
	    if (!textToSearch.replaceAll("%", "").isEmpty()) {
		fr.setAndOrRestriction(AndOrRestriction.OR);
		fr.addFilterField(FilterUtils.startsWith("comune", textToSearch));
		fr.addFilterField(FilterUtils.startsWith("codicecomune", textToSearch));
	    }
	}
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("comune"));
	return vwEntilocaliDAO.findByFilterTable(ft);
    }
}
