package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.VwRegistrazionidebitiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RateNonpagateFilter;
import it.gruppoinit.pal.gp.core.domain.VwRegistrazionidebiti;
import it.gruppoinit.pal.gp.core.service.VwRegistrazionidebitiService;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VwRegistrazionedebitiServiceImpl extends BaseServiceImpl<VwRegistrazionidebiti, PkId> implements VwRegistrazionidebitiService {

    private VwRegistrazionidebitiDAO vwRegistrazionidebitiDAO;

    @Autowired
    public void setVwRegistrazionidebitiDAO(VwRegistrazionidebitiDAO vwRegistrazionidebitiDAO) {

	this.vwRegistrazionidebitiDAO = vwRegistrazionidebitiDAO;
    }

    @Override
    protected Class<VwRegistrazionidebiti> getEntityClass() {

	return VwRegistrazionidebiti.class;
    }

    @Override
    @RolesAllowed( { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_VW_REGISTRAZIONI_DEBITI" })
    public void delete(VwRegistrazionidebiti entity) {

	vwRegistrazionidebitiDAO.delete(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_VW_REGISTRAZIONI_DEBITI" })
    public List<VwRegistrazionidebiti> findAll(Integer firstResult, Integer maxResult) {

	return vwRegistrazionidebitiDAO.findAll(null, null);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VW_REGISTRAZIONI_DEBITI" })
    public VwRegistrazionidebiti findById(PkId id) {

	return vwRegistrazionidebitiDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_VW_REGISTRAZIONI_DEBITI" })
    public void insert(VwRegistrazionidebiti entity) {

	if (validateEntity(entity)) {
	    vwRegistrazionidebitiDAO.insert(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_VW_REGISTRAZIONI_DEBITI" })
    public void update(VwRegistrazionidebiti entity) {

	if (validateEntity(entity)) {
	    vwRegistrazionidebitiDAO.update(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_VW_REGISTRAZIONI_DEBITI" })
    public List<VwRegistrazionidebiti> findByFilter(RateNonpagateFilter rateNonpagateFilter) {

	return vwRegistrazionidebitiDAO.findByFilter(rateNonpagateFilter);
    }
}
