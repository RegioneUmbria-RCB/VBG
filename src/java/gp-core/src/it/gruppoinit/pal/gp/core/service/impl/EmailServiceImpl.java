package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.EmailDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.Email;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.EmailService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl extends BaseServiceImpl<Email, PkId> implements EmailService {

    private EmailDAO emailDAO;

    @Autowired
    public void setEmailDAO(EmailDAO emailDAO) {

	this.emailDAO = emailDAO;
    }

    @Override
    protected Class<Email> getEntityClass() {

	return Email.class;
    }

    @Override
    public void delete(Email entity) {

	emailDAO.delete(entity);
    }

    @Override
    public List<Email> findAll(Integer firstResult, Integer maxResult) {

	return emailDAO.findAll(null, null);
    }

    @Override
    public Email findById(PkId id) {

	return emailDAO.findById(id);
    }

    @Override
    public void insert(Email entity) {

	if (validateEntity(entity))
	    emailDAO.insert(entity);
    }

    @Override
    public void update(Email entity) {

	if (validateEntity(entity))
	    emailDAO.update(entity);
    }

    @Override
    public List<Email> findAllOrderByData(Amministrazioni amministrazioni, DAOOrderTypeEnum orderTypeEnum) {

	return emailDAO.findAllOrderByData(amministrazioni, orderTypeEnum);
    }

    @Override
    public List<Email> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult) {

	if (codiceAmministrazione == null) {
	    throw new IllegalArgumentException("findByAmministrazioni: il parametro codiceAmministrazione e' nullo");
	}
	FilterTable filterTable = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceAmministrazione, "amministrazioni", Integer.class));
	filterTable.addRestriction(fr);
	return emailDAO.findByFilterTable(filterTable, firstResult, maxResult);
    }
}
