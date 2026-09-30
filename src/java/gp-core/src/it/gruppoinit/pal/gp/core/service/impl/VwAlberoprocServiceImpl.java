package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwAlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwAlberoproc;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.service.VwAlberoprocService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwAlberoprocServiceImpl extends BaseServiceImpl<VwAlberoproc, PkId> implements VwAlberoprocService {

    private VwAlberoprocDAO vwAlberoprocDAO;

    @Autowired
    public void setVwAlberoprocDAO(VwAlberoprocDAO vwAlberoprocDAO) {

	this.vwAlberoprocDAO = vwAlberoprocDAO;
    }

    public List<VwAlberoproc> findByFilter(VwAlberoproc entity) {

	return vwAlberoprocDAO.findByFilter(entity);
    }

    @Override
    public void insert(VwAlberoproc entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwAlberoproc entity) {

	throw new NotImplementedException();
    }

    @Override
    public void delete(VwAlberoproc entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<VwAlberoproc> findAll(Integer firstResult, Integer maxResult) {

	return vwAlberoprocDAO.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "scDescrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public VwAlberoproc findById(PkId id) {

	return vwAlberoprocDAO.findById(id);
    }

    @Override
    public List<VwAlberoproc> findByFilterTable(FilterTable filterTable) {

	return vwAlberoprocDAO.findByFilterTable(filterTable);
    }

    @Override
    protected Class<VwAlberoproc> getEntityClass() {

	return VwAlberoproc.class;
    }
}
