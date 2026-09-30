package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliTmAvvDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvv;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmAvvId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ResponsabiliTmAvvService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author riccardob
 */
@Service
public class ResponsabiliTmAvvServiceImpl extends BaseServiceImpl<ResponsabiliTmAvv, ResponsabiliTmAvvId> implements ResponsabiliTmAvvService {

    private ResponsabiliTmAvvDAO responsabilitmavvDAO;

    @Autowired
    public void setResponsabiliTmAvvDAO(ResponsabiliTmAvvDAO responsabilitmavvDAO) {

	this.responsabilitmavvDAO = responsabilitmavvDAO;
    }

    @Override
    protected Class<ResponsabiliTmAvv> getEntityClass() {

	return ResponsabiliTmAvv.class;
    }

    @Override
    public List<ResponsabiliTmAvv> findAll(Integer firstResult, Integer maxResult) {

	return responsabilitmavvDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ResponsabiliTmAvv entity) {

	if (validateEntity(entity)) {
	    responsabilitmavvDAO.insert(entity);
	}
    }

    @Override
    public ResponsabiliTmAvv findById(ResponsabiliTmAvvId id) {

	return responsabilitmavvDAO.findById(id);
    }

    @Override
    public void update(ResponsabiliTmAvv entity) {

	if (validateEntity(entity)) {
	    responsabilitmavvDAO.update(entity);
	}
    }

    @Override
    public void delete(ResponsabiliTmAvv entity) {

	if (isDeleteAllowed(entity)) {
	    responsabilitmavvDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ResponsabiliTmAvv entity) {

	return true;
    }

    @Override
    public List<ResponsabiliTmAvv> findByResponsabile(Integer codiceresponsabile) {

	if (codiceresponsabile == null) {
	    throw new RuntimeException("Il codice responsabile non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceresponsabile", codiceresponsabile, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.tipomovimento"));
	return responsabilitmavvDAO.findByFilterTable(ft);
    }

    @Override
    public List<ResponsabiliTmAvv> findByResponsabile(Integer codiceresponsabile, Boolean flagEsclude) {

	if (codiceresponsabile == null) {
	    throw new RuntimeException("Il codice responsabile non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceresponsabile", codiceresponsabile, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagEsclude", flagEsclude, Boolean.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.tipomovimento"));
	return responsabilitmavvDAO.findByFilterTable(ft);
    }

    @Override
    public List<ResponsabiliTmAvv> findByTipomovimento(String tipomovimento) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new RuntimeException("Il codice tipo movimento non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.tipomovimento"));
	return responsabilitmavvDAO.findByFilterTable(ft);
    }
}
