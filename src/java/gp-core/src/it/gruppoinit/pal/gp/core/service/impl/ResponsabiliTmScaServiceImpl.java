package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliTmScaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmSca;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliTmScaId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ResponsabiliTmScaService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author riccardob
 */
@Service
public class ResponsabiliTmScaServiceImpl extends BaseServiceImpl<ResponsabiliTmSca, ResponsabiliTmScaId> implements ResponsabiliTmScaService {

    private ResponsabiliTmScaDAO responsabilitmscaDAO;

    @Autowired
    public void setResponsabiliTmScaDAO(ResponsabiliTmScaDAO responsabilitmscaDAO) {

	this.responsabilitmscaDAO = responsabilitmscaDAO;
    }

    @Override
    protected Class<ResponsabiliTmSca> getEntityClass() {

	return ResponsabiliTmSca.class;
    }

    @Override
    public List<ResponsabiliTmSca> findAll(Integer firstResult, Integer maxResult) {

	return responsabilitmscaDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(ResponsabiliTmSca entity) {

	if (validateEntity(entity)) {
	    responsabilitmscaDAO.insert(entity);
	}
    }

    @Override
    public ResponsabiliTmSca findById(ResponsabiliTmScaId id) {

	return responsabilitmscaDAO.findById(id);
    }

    @Override
    public void update(ResponsabiliTmSca entity) {

	if (validateEntity(entity)) {
	    responsabilitmscaDAO.update(entity);
	}
    }

    @Override
    public void delete(ResponsabiliTmSca entity) {

	if (isDeleteAllowed(entity)) {
	    responsabilitmscaDAO.delete(entity);
	}
    }

    protected boolean isDeleteAllowed(ResponsabiliTmSca entity) {

	return true;
    }

    @Override
    public List<ResponsabiliTmSca> findByResponsabile(Integer codiceresponsabile) {

	if (codiceresponsabile == null) {
	    throw new RuntimeException("Il codice responsabile non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceresponsabile", codiceresponsabile, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.tipomovimento"));
	return responsabilitmscaDAO.findByFilterTable(ft);
    }

    @Override
    public List<ResponsabiliTmSca> findByResponsabile(Integer codiceresponsabile, Boolean flagEsclude) {

	if (codiceresponsabile == null) {
	    throw new RuntimeException("Il codice responsabile non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceresponsabile", codiceresponsabile, Integer.class));
	fr.addFilterField(FilterUtils.equals("flagEsclude", flagEsclude, Boolean.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.tipomovimento"));
	return responsabilitmscaDAO.findByFilterTable(ft);
    }

    @Override
    public List<ResponsabiliTmSca> findByTipomovimento(String tipomovimento) {

	if (StringUtils.isBlank(tipomovimento)) {
	    throw new RuntimeException("Il codice tipo movimento non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.tipomovimento", tipomovimento, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.tipomovimento"));
	return responsabilitmscaDAO.findByFilterTable(ft);
    }
}
