package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AmministrazionireferentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Amministrazionireferenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AmministrazionireferentiService;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.hibernate.validator.InvalidValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmministrazionireferentiServiceImpl extends BaseServiceImpl<Amministrazionireferenti, PkId> implements AmministrazionireferentiService {

    private AmministrazionireferentiDAO amministrazionireferentiDAO;

    @Autowired
    public void setAmministrazionireferentiDAO(AmministrazionireferentiDAO amministrazionireferentiDAO) {

	this.amministrazionireferentiDAO = amministrazionireferentiDAO;
    }

    @Override
    protected Class<Amministrazionireferenti> getEntityClass() {

	return Amministrazionireferenti.class;
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_DELETE", "PERM_DELETE_AMMINISTRAZIONIREFERENTI" })
    public void delete(Amministrazionireferenti entity) {

	if (isDeleteAllowed(entity)) {
	    amministrazionireferentiDAO.delete(entity);
	}
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_LIST", "PERM_LIST_AMMINISTRAZIONIREFERENTI" })
    public List<Amministrazionireferenti> findAll(Integer firstResult, Integer maxResult) {

	return amministrazionireferentiDAO.findAll(null, null);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_VIEW", "PERM_VIEW_AMMINISTRAZIONIREFERENTI" })
    public Amministrazionireferenti findById(PkId id) {

	return amministrazionireferentiDAO.findById(id);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_INSERT", "PERM_INSERT_AMMINISTRAZIONIREFERENTI" })
    public void insert(Amministrazionireferenti entity) {

	if (validateEntity(entity))
	    amministrazionireferentiDAO.insert(entity);
    }

    @Override
    @RolesAllowed(value = { "ROLE_ADMINISTRATOR", "PERM_UPDATE", "PERM_UPDATE_AMMINISTRAZIONIREFERENTI" })
    public void update(Amministrazionireferenti entity) {

	if (validateEntity(entity))
	    amministrazionireferentiDAO.update(entity);
    }

    @Override
    public List<Amministrazionireferenti> findByAmministrazioni(Amministrazioni amministrazioni) {

	return amministrazionireferentiDAO.findByAmministrazioni(amministrazioni);
    }

    protected boolean isDeleteAllowed(Amministrazionireferenti entity) {

	boolean delete = true;
	List<InvalidValue> _ivs = new ArrayList<InvalidValue>();
	if (!entity.getAlbopubblicazionis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "ALBO_PUBBLICAZIONI", null));
	    delete = false;
	}
	if (!entity.getMovimentis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "MOVIMENTI", null));
	    delete = false;
	}
	if (!entity.getInventarioprocedimentis().isEmpty()) {
	    _ivs.add(new InvalidValue(WebConstants.ALERT_FOREIGN_KEY, null, null, "INVENTARIOPROCEDIMENTI", null));
	    delete = false;
	}
	if (!delete) {
	    this.throwValidationMessages(_ivs);
	}
	return delete;
    }

    @Override
    public List<Amministrazionireferenti> findByFilterTable(FilterTable ft) {

	return amministrazionireferentiDAO.findByFilterTable(ft);
    }

    @Override
    public List<Amministrazionireferenti> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return amministrazionireferentiDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
