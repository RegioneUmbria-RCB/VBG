/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliruoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliruoliId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ResponsabiliruoliService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author francescop
 * 
 */
@Service
public class ResponsabiliruoliServiceImpl extends BaseServiceImpl<Responsabiliruoli, ResponsabiliruoliId> implements ResponsabiliruoliService {

    private ResponsabiliruoliDAO responsabiliruoliDAO;

    @Autowired
    public void setResponsabiliruoliDAO(ResponsabiliruoliDAO responsabiliruoliDAO) {

	this.responsabiliruoliDAO = responsabiliruoliDAO;
    }

    @Override
    protected Class<Responsabiliruoli> getEntityClass() {

	return Responsabiliruoli.class;
    }

    @Override
    public void delete(Responsabiliruoli entity) {

	responsabiliruoliDAO.delete(entity);
    }

    @Override
    public List<Responsabiliruoli> findAll(Integer firstResult, Integer maxResult) {

	return responsabiliruoliDAO.findAll(null, null);
    }

    @Override
    public Responsabiliruoli findById(ResponsabiliruoliId id) {

	return responsabiliruoliDAO.findById(id);
    }

    @Override
    public void insert(Responsabiliruoli entity) {

	if (validateEntity(entity)) {
	    responsabiliruoliDAO.insert(entity);
	}
    }

    @Override
    public void update(Responsabiliruoli entity) {

	if (validateEntity(entity)) {
	    responsabiliruoliDAO.update(entity);
	}
    }

    @Override
    public List<Responsabiliruoli> findByResponsabile(Integer codiceResponsabile, String idcomune) {

	if (codiceResponsabile == null) {
	    throw new IllegalArgumentException("ResponsabiliruoliService#findByResponsabile: Il parametro codiceResponsabile non può essere nullo");
	}
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	if (StringUtils.isBlank(idcomune)) {
	    ft.setDefaultWhere(DAOEnum.FIND_BY_IDCOMUNE);
	} else {
	    fr.addFilterField(FilterUtils.equals("id.idcomune", idcomune, String.class));
	}
	fr.addFilterField(FilterUtils.equals("id.codiceresponsabile", codiceResponsabile, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.idruolo"));
	return responsabiliruoliDAO.findByFilterTable(ft);
    }

    @Override
    public boolean userHasRole(Integer codiceResponsabile, String... role) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceresponsabile", codiceResponsabile, Integer.class));
	fr.addFilterField(FilterUtils.in("ruolo", role, "ruolo", String.class));
	ft.addRestriction(fr);
	return responsabiliruoliDAO.countRecord(ft) > 0;
    }

    @Override
    public List<Integer> findCodiciRuoloByResponsabile(Integer codiceResponsabile) {

	List<Integer> codiciRuolo = responsabiliruoliDAO.findCodiciRuoloByResponsabile(codiceResponsabile);
	return codiciRuolo;
    }
}
