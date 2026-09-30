package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.LdpDecodificheDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.LdpDecodifiche;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.LdpDecodificheService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LdpDecodificheServiceImpl extends BaseServiceImpl<LdpDecodifiche, PkId> implements LdpDecodificheService {

    private LdpDecodificheDAO ldpDecodificheDAO;

    @Autowired
    public void setLdpDecodificheDAO(LdpDecodificheDAO ldpDecodificheDAO) {

	this.ldpDecodificheDAO = ldpDecodificheDAO;
    }

    @Override
    public void insert(LdpDecodifiche entity) {

	ldpDecodificheDAO.insert(entity);
    }

    @Override
    public void update(LdpDecodifiche entity) {

	ldpDecodificheDAO.update(entity);
    }

    @Override
    public void delete(LdpDecodifiche entity) {

	ldpDecodificheDAO.delete(entity);
    }

    @Override
    public List<LdpDecodifiche> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public LdpDecodifiche findById(PkId id) {

	return ldpDecodificheDAO.findById(id);
    }

    @Override
    public List<LdpDecodifiche> findByContesto(String contesto) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("contesto", contesto, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione"));
	return ldpDecodificheDAO.findByFilterTable(ft);
    }

    @Override
    protected Class<LdpDecodifiche> getEntityClass() {

	return LdpDecodifiche.class;
    }
}
