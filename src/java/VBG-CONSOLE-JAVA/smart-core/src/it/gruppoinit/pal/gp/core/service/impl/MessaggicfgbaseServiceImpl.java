package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MessaggicfgbaseDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Messaggicfgbase;
import it.gruppoinit.pal.gp.core.service.MessaggicfgbaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessaggicfgbaseServiceImpl extends BaseServiceImpl<Messaggicfgbase, String> implements MessaggicfgbaseService {

    private MessaggicfgbaseDAO messaggicfgbaseDAO;

    @Autowired
    public void setMessaggicfgbaseDAO(MessaggicfgbaseDAO messaggicfgbaseDAO) {

	this.messaggicfgbaseDAO = messaggicfgbaseDAO;
    }

    @Override
    protected Class<Messaggicfgbase> getEntityClass() {

	return Messaggicfgbase.class;
    }

    @Override
    public void delete(Messaggicfgbase entity) {

	throw new NotImplementedException();
    }

    @Override
    public List<Messaggicfgbase> findAll(Integer firstResult, Integer maxResult) {

	return messaggicfgbaseDAO.findAll(null, null);
    }

    @Override
    public Messaggicfgbase findById(String id) {

	return messaggicfgbaseDAO.findById(id);
    }

    @Override
    public void insert(Messaggicfgbase entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Messaggicfgbase entity) {

	throw new NotImplementedException();
    }
}
