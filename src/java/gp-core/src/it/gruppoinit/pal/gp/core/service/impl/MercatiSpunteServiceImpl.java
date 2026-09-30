package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiSpunteDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiSpunte;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.MercatiService;
import it.gruppoinit.pal.gp.core.service.MercatiSpunteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatiSpunteServiceImpl extends BaseServiceImpl<MercatiSpunte, PkId> implements MercatiSpunteService {

    private MercatiSpunteDAO mercatiSpunteDAO;
    private MercatiService mercatiService;

    @Autowired
    public void setMercatiService(MercatiService mercatiService) {

	this.mercatiService = mercatiService;
    }

    @Autowired
    public void setMercatiSpunteDAO(MercatiSpunteDAO mercatiSpunteDAO) {

	this.mercatiSpunteDAO = mercatiSpunteDAO;
    }

    @Override
    public void insert(MercatiSpunte entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiSpunteDAO.insert(entity);
	}
    }

    private void dataIntegration(MercatiSpunte entity) {

	//	if (entity.getFlagSegnaPres() == null) {
	//	    entity.setFlagSegnaPres(Boolean.FALSE);
	//	}
	if (entity.getOrdine() == null) {
	    entity.setOrdine(0);
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(MercatiSpunte entity) {

	Mercati m = mercatiService.bindDomainObject(entity.getMercato(), PkId.class, "id.codice");
	entity.setMercato(m);
    }

    @Override
    public void update(MercatiSpunte entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatiSpunteDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatiSpunte entity) {

	if (isDeleteAllowed(entity)) {
	    mercatiSpunteDAO.delete(entity);
	}
    }

    @Override
    public List<MercatiSpunte> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public MercatiSpunte findById(PkId id) {

	return mercatiSpunteDAO.findById(id);
    }

    @Override
    protected Class<MercatiSpunte> getEntityClass() {

	return MercatiSpunte.class;
    }

    @Override
    public List<MercatiSpunte> findByMercato(Integer codiceMercato) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatoId", codiceMercato, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("ordine"));
	return mercatiSpunteDAO.findByFilterTable(ft);
    }
}
