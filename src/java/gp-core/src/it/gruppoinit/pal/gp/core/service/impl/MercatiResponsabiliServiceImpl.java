package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiResponsabili;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiResponsabiliService;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.ResponsabiliService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatiResponsabiliServiceImpl extends BaseServiceImpl<MercatiResponsabili, PkId> implements MercatiResponsabiliService {

    private MercatiResponsabiliDAO mercatiResponsabiliDAO;
    private MercatiService mercatiService;
    private ResponsabiliService responsabiliService;

    @Autowired
    public void setResponsabiliService(ResponsabiliService responsabiliService) {

	this.responsabiliService = responsabiliService;
    }

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setMercatiResponsabiliDAO(MercatiResponsabiliDAO mercatiResponsabiliDAO) {

	this.mercatiResponsabiliDAO = mercatiResponsabiliDAO;
    }

    @Override
    public void insert(MercatiResponsabili entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiResponsabiliDAO.insert(entity);
	}
    }

    private void dataIntegration(MercatiResponsabili entity) {

    }

    @Override
    protected void fixMergeEntityProperties(MercatiResponsabili entity) {

	Mercati m = mercatiService.bindDomainObject(entity.getMercato(), PkId.class, "id.codice");
	entity.setMercato(m);
	Responsabili r = responsabiliService.bindDomainObject(entity.getResponsabili(), PkId.class, "id.codice");
	entity.setResponsabili(r);
    }

    @Override
    public void update(MercatiResponsabili entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiResponsabiliDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiResponsabili entity) {

	if (isDeleteAllowed(entity)) {
	    mercatiResponsabiliDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiResponsabili> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public MercatiResponsabili findById(PkId id) {

	return mercatiResponsabiliDAO.findById(id);
    }

    @Override
    protected Class<MercatiResponsabili> getEntityClass() {

	return MercatiResponsabili.class;
    }

    @Override
    public List<MercatiResponsabili> findByResponsabile(Integer codiceResponsabile, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("responsabiliId", codiceResponsabile, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercato"));
	return mercatiResponsabiliDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<MercatiResponsabili> findByMercato(Integer codiceMercato, Integer firstResult, Integer maxResult) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("responsabile", "responsabili"));
	return mercatiResponsabiliDAO.findByFilterTable(ft, firstResult, maxResult);
    }

    @Override
    public List<MercatiResponsabili> findByResponsabileAndData(Integer codiceResponsabile, Date date) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("responsabiliId", codiceResponsabile, Integer.class));
	fr.addFilterField(FilterUtils.equals("dataRegistrazione", date, "mercato.mercatipresenzeTs", Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercato"));
	return mercatiResponsabiliDAO.findByFilterTable(ft);
    }

    @Override
    public int countByResponsabile(Integer codiceResponsabile) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("responsabiliId", codiceResponsabile, Integer.class));
	// per software
	fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "mercato.software", String.class));
	ft.addRestriction(fr);
	return mercatiResponsabiliDAO.countRecord(ft);
    }

    @Override
    public List<MercatiResponsabili> findByResponsabileDallaDataAllaData(Integer codiceResponsabile, Date dallaData, Date allaData) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("responsabiliId", codiceResponsabile, Integer.class));
	fr.addFilterField(FilterUtils.greaterEqual("dataRegistrazione", dallaData, "mercato.mercatipresenzeTs", Date.class));
	fr.addFilterField(FilterUtils.smallerEqual("dataRegistrazione", allaData, "mercato.mercatipresenzeTs", Date.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderDesc("dataRegistrazione", "mercato.mercatipresenzeTs"));
	ft.addOrder(FilterUtils.orderAsc("descrizione", "mercato"));
	return mercatiResponsabiliDAO.findByFilterTable(ft);
    }

    @Override
    public int countByResponsabileAndMercato(Integer codiceResponsabile, Integer codiceMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("responsabiliId", codiceResponsabile, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	// per software
	fr.addFilterField(FilterUtils.equals("codice", ORMHelper.getSoftware(), "mercato.software", String.class));
	ft.addRestriction(fr);
	return mercatiResponsabiliDAO.countRecord(ft);
    }
}
