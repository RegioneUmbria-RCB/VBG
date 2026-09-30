package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwAssenzeconsessionariDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.AssenzeFilter;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionari;
import it.gruppoinit.pal.gp.core.domain.VwAssenzeconcessionariId;
import it.gruppoinit.pal.gp.core.service.VwAssenzeconcessionariService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwAssenzeconcessionariServiceImpl extends BaseServiceImpl<VwAssenzeconcessionari, VwAssenzeconcessionariId> implements
	VwAssenzeconcessionariService {

    private VwAssenzeconsessionariDAO vwAssenzeconsessionariDAO;

    @Autowired
    public void setVwAssenzeconsessionariDAO(VwAssenzeconsessionariDAO vwAssenzeconsessionariDAO) {

	this.vwAssenzeconsessionariDAO = vwAssenzeconsessionariDAO;
    }

    @Override
    protected Class<VwAssenzeconcessionari> getEntityClass() {

	return VwAssenzeconcessionari.class;
    }

    @Override
    public void delete(VwAssenzeconcessionari entity) {

	throw new NotImplementedException();
    }

    @Override
    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_VW_ASSENZECONCESSIONARI" })
    public List<VwAssenzeconcessionari> findAll(Integer firstResult, Integer maxResult) {

	return vwAssenzeconsessionariDAO.findAll(null, null);
    }

    @Override
    public VwAssenzeconcessionari findById(VwAssenzeconcessionariId id) {

	return vwAssenzeconsessionariDAO.findById(id);
    }

    @Override
    public void insert(VwAssenzeconcessionari entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(VwAssenzeconcessionari entity) {

	throw new NotImplementedException();
    }

    @RolesAllowed({ "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_VW_ASSENZECONCESSIONARI" })
    @Override
    public List<VwAssenzeconcessionari> findByAssenzeFilter(AssenzeFilter assenzeFilter) {

	List<VwAssenzeconcessionari> list = vwAssenzeconsessionariDAO.findByAssenzeFilter(assenzeFilter);
	return list;
    }
}
