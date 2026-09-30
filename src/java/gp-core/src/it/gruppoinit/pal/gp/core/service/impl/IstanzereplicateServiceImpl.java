package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.IstanzereplicateDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzereplicate;
import it.gruppoinit.pal.gp.core.domain.IstanzereplicateId;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.AndOrRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzereplicateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IstanzereplicateServiceImpl extends BaseServiceImpl<Istanzereplicate, IstanzereplicateId> implements IstanzereplicateService {

    private IstanzereplicateDAO istanzereplicateDAO;
    private IstanzeService istanzeService;

    @Autowired
    public void setIstanzereplicateDAO(IstanzereplicateDAO istanzereplicateDAO) {

	this.istanzereplicateDAO = istanzereplicateDAO;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Override
    public void insert(Istanzereplicate entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzereplicateDAO.insert(entity);
	}
    }

    @Override
    public void update(Istanzereplicate entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzereplicateDAO.update(entity);
	}
    }

    private void dataIntegration(Istanzereplicate entity) {

	if (entity == null) {
	    throw new IllegalArgumentException("Il parametro istanzereplicate non può essere nullo.");
	}
    }

    @Override
    public void delete(Istanzereplicate entity) {

	if (isDeleteAllowed(entity)) {
	    istanzereplicateDAO.delete(entity);
	}
    }

    @Override
    public List<Istanzereplicate> findAll(Integer firstResult, Integer maxResult) {

	return istanzereplicateDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Istanzereplicate findById(IstanzereplicateId id) {

	return istanzereplicateDAO.findById(id);
    }

    @Override
    protected Class<Istanzereplicate> getEntityClass() {

	return Istanzereplicate.class;
    }

    @Override
    protected boolean isDeleteAllowed(Istanzereplicate entity) {

	return super.isDeleteAllowed(entity);
    }

    @Override
    public List<Istanze> findIstanzeReplicate(Istanze istanzaPadre) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.isNotEmpty("istanzesForFkIstanzafiglia"));
	fr.addFilterField(FilterUtils.equals("id.codiceistanzapadre", istanzaPadre.getId().getCodice(), "istanzesForFkIstanzafiglia", Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("id.codice"));
	return istanzeService.findByFilterTable(ft);
    }

    @Override
    public Istanze findIstanzaPadre(Istanze istanzaFiglia) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codiceistanzafiglia", istanzaFiglia.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	List<Istanzereplicate> istanzereplicates = istanzereplicateDAO.findByFilterTable(ft);
	for (Istanzereplicate istanzereplicate : istanzereplicates) {
	    Integer codiceIstanzaPadre = istanzereplicate.getId().getCodiceistanzapadre();
	    PkId idPadre = new PkId(codiceIstanzaPadre);
	    return istanzeService.findById(idPadre);
	}
	return null;
    }

    @Override
    public Istanzereplicate findSeIstanzaReplicata(Istanze istanza) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.setAndOrRestriction(AndOrRestriction.OR);
	fr.addFilterField(FilterUtils.equals("id.codiceistanzapadre", istanza.getId().getCodice(), Integer.class));
	fr.addFilterField(FilterUtils.equals("id.codiceistanzafiglia", istanza.getId().getCodice(), Integer.class));
	ft.addRestriction(fr);
	List<Istanzereplicate> istanzereplicates = istanzereplicateDAO.findByFilterTable(ft);
	if (!istanzereplicates.isEmpty()) {
	    return istanzereplicates.get(0);
	}
	return null;
    }
}
