package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.dao.PeopleprocsportelliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Peopleprocsportelli;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.PeopleprocsportelliHelper;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.PeopleprocsportelliService;
import it.gruppoinit.pal.gp.core.service.exception.BusinessValidationException;

/**
 * 
 * @author
 */
@Service
public class PeopleprocsportelliServiceImpl extends BaseServiceImpl<Peopleprocsportelli, PkId> implements PeopleprocsportelliService {

    private PeopleprocsportelliDAO peopleprocsportelliDAO;
    private ComuniDAO comuniDAO;

    @Autowired
    public void setPeopleprocsportelliDAO(PeopleprocsportelliDAO peopleprocsportelliDAO) {

	this.peopleprocsportelliDAO = peopleprocsportelliDAO;
    }

    @Autowired
    public void setComuniDAO(ComuniDAO comuniDAO) {

	this.comuniDAO = comuniDAO;
    }

    @Override
    protected Class<Peopleprocsportelli> getEntityClass() {

	return Peopleprocsportelli.class;
    }

    @Override
    public List<Peopleprocsportelli> findAll(Integer firstResult, Integer maxResult) {

	return peopleprocsportelliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Peopleprocsportelli entity) {

	if (validateEntity(entity)) {
	    peopleprocsportelliDAO.insert(entity);
	}
    }

    @Override
    public Peopleprocsportelli findById(PkId id) {

	return peopleprocsportelliDAO.findById(id);
    }

    @Override
    public void update(Peopleprocsportelli entity) {

	if (validateEntity(entity)) {
	    peopleprocsportelliDAO.update(entity);
	}
    }

    @Override
    public void delete(Peopleprocsportelli entity) {

	if (isDeleteAllowed(entity)) {
	    peopleprocsportelliDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(Peopleprocsportelli entity) {

	boolean delete = true;
	return delete;
    }

    @Override
    public void creaRecord(PeopleprocsportelliHelper helper) {

	for (String codicecomune : helper.getCodicecomune()) {
	    if (!recordEsistente(helper.getPeopleProc(), codicecomune)) {
		throw new BusinessValidationException("Il codice " + helper.getPeopleProc() + " è già presente per il comune " + codicecomune);
	    }
	    Peopleprocsportelli peopleprocsportelli = new Peopleprocsportelli();
	    Comuni comune = comuniDAO.findById(codicecomune);
	    peopleprocsportelli.setCodicecomune(comune);
	    peopleprocsportelli.setPeopleProc(helper.getPeopleProc());
	    peopleprocsportelli.setSoftware(helper.getSoftware());
	    this.peopleprocsportelliDAO.insert(peopleprocsportelli);
	}
    }

    private boolean recordEsistente(String peopleProc, String codiceComune) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codicecomune", codiceComune, "codicecomune", String.class));
	fr.addFilterField(FilterUtils.equals("peopleProc", peopleProc, String.class));
	ft.addRestriction(fr);
	List<Peopleprocsportelli> list = peopleprocsportelliDAO.findByFilterTable(ft);
	return list.isEmpty();
    }
}
