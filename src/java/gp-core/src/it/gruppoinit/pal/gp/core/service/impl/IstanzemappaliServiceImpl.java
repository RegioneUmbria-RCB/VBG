package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzemappaliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Catasto;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzemappali;
import it.gruppoinit.pal.gp.core.domain.Istanzestradario;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.IstanzestradarioService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.CatastoService;
import it.gruppoinit.pal.gp.core.service.IstanzeService;
import it.gruppoinit.pal.gp.core.service.IstanzemappaliService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author francescop
 */
@Service
public class IstanzemappaliServiceImpl extends BaseServiceImpl<Istanzemappali, PkId> implements IstanzemappaliService {

    private CatastoService catastoService;
    private IstanzeService istanzeService;
    private IstanzemappaliDAO istanzemappaliDAO;
    private IstanzestradarioService istanzestradarioService;

    @Autowired
    public void setCatastoService(CatastoService catastoService) {

	this.catastoService = catastoService;
    }

    @Autowired
    public void setIstanzeService(IstanzeService istanzeService) {

	this.istanzeService = istanzeService;
    }

    @Autowired
    public void setIstanzemappaliDAO(IstanzemappaliDAO istanzemappaliDAO) {

	this.istanzemappaliDAO = istanzemappaliDAO;
    }

    @Autowired
    public void setIstanzestradarioService(IstanzestradarioService istanzestradarioService) {

	this.istanzestradarioService = istanzestradarioService;
    }

    @Override
    protected Class<Istanzemappali> getEntityClass() {

	return Istanzemappali.class;
    }

    @Override
    public List<Istanzemappali> findAll(Integer firstResult, Integer maxResult) {

	return istanzemappaliDAO.findAll(firstResult, maxResult);
    }

    @Override
    public void insert(Istanzemappali entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzemappaliDAO.insert(entity);
	}
    }

    @Override
    public Istanzemappali findById(PkId id) {

	Istanzemappali result = istanzemappaliDAO.findById(id);
	return result;
    }

    @Override
    public void update(Istanzemappali entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    istanzemappaliDAO.update(entity);
	}
    }

    private void dataIntegration(Istanzemappali entity) {

	if (entity != null) {
	    Istanze istanza = istanzeService.bindDomainObject(entity.getIstanza(), PkId.class, "id.codice");
	    entity.setIstanza(istanza);
	    Istanzestradario istanzestradario = istanzestradarioService.bindDomainObject(entity.getIstanzestradario(), PkId.class, "id.codice");
	    entity.setIstanzestradario(istanzestradario);
	    Catasto catasto = catastoService.bindDomainObject(entity.getCatasto(), String.class, "codice");
	    entity.setCatasto(catasto);
	    if (istanzestradario != null) {
		boolean isIstanzeStradarioPrimario = istanzestradario.getPrimario() == null ? false : istanzestradario.getPrimario().booleanValue();
		if (isIstanzeStradarioPrimario) {
		    // trova tutti i mappali legati a quello stradario e controlla se non ce ne sia già uno
		    // se non ce ne è nessuno allora quel mappale viene posto primario = true
		    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		    FilterRestriction fr = new FilterRestriction();
		    fr.addFilterField(FilterUtils.equals("istanzestradarioId", istanzestradario.getId().getCodice(), Integer.class));
		    fr.addFilterField(FilterUtils.equals("primario", Boolean.TRUE, Boolean.class));
		    ft.addRestriction(fr);
		    int count = istanzemappaliDAO.countRecord(ft);
		    if (count == 0) {
			entity.setPrimario(Boolean.TRUE);
		    }
		} else {
		    // Se la riga di istanzemappali non è di una istanzastradario primario allora di default non ci sono mappali primari 
		    entity.setPrimario(Boolean.FALSE);
		}
	    }
	    if (entity.getPrimario() == null) {
		entity.setPrimario(Boolean.FALSE);
	    }
	}
    }

    @Override
    public void delete(Istanzemappali entity) {

	if (isDeleteAllowed(entity)) {
	    String catasto = (String) EntityUtils.getNestedProperty(entity.getCatasto(), "codice");
	    if (StringUtils.isBlank(catasto)) {
		// FIX BugZilla id 468 
		List<Catasto> cList = catastoService.findAll(0, 1);
		Catasto cObj = null;
		if (cList.size() > 0) {
		    cObj = cList.get(0);
		    entity.setCatasto(cObj);
		    this.update(entity);
		}
	    }
	    childDelete(entity);
	    istanzemappaliDAO.delete(entity);
	}
    }

    @Override
    protected void childDelete(Istanzemappali entity) {

	// Se sto cancellando un mappale primario di uno stradario primario allora devo controllare se ci sono altri mappali e mettere uno di questi a primario
	Istanzestradario istanzestradario = istanzestradarioService.bindDomainObject(entity.getIstanzestradario(), PkId.class, "id.codice");
	if (istanzestradario != null) {
	    boolean isIstanzeStradarioPrimario = istanzestradario.getPrimario() == null ? false : istanzestradario.getPrimario().booleanValue();
	    if (isIstanzeStradarioPrimario) {
		boolean isMappalePrimario = entity.getPrimario() == null ? false : entity.getPrimario().booleanValue();
		if (isMappalePrimario) {
		    FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
		    FilterRestriction fr = new FilterRestriction();
		    fr.addFilterField(FilterUtils.equals("istanzestradarioId", istanzestradario.getId().getCodice(), Integer.class));
		    fr.addFilterField(FilterUtils.notEquals("id.codice", entity.getId().getCodice(), Integer.class));
		    ft.addOrder(FilterUtils.orderAsc("id.codice"));
		    ft.addRestriction(fr);
		    List<Istanzemappali> istanzeappalis = istanzemappaliDAO.findByFilterTable(ft);
		    for (Istanzemappali istanzemappali : istanzeappalis) {
			istanzemappali.setPrimario(Boolean.TRUE);
			istanzemappaliDAO.update(istanzemappali);
			break;
		    }
		}
	    }
	}
    }

    @Override
    public Istanzemappali findByPrimarioIstanza(Istanze istanza) {

	return istanzemappaliDAO.findByPrimarioIstanza(istanza);
    }

    @Override
    public List<Istanzemappali> findByIstanzaStradario(Integer codiceIstanzaStradario) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceIstanzaStradario, "istanzestradario", Integer.class));
	ft.addRestriction(fr);
	return istanzemappaliDAO.findByFilterTable(ft);
    }

    @Override
    public List<Istanzemappali> findByIstanza(Integer codice) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codice, "istanza", Integer.class));
	ft.addRestriction(fr);
	return istanzemappaliDAO.findByFilterTable(ft);
    }
}
