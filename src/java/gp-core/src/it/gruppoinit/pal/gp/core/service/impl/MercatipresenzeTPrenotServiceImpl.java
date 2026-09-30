package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.MercatipresenzeTPrenotDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeT;
import it.gruppoinit.pal.gp.core.domain.MercatipresenzeTPrenot;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.manifestazioni.calendario.MercatipresenzeTService;
import it.gruppoinit.pal.gp.core.features.manifestazioni.configurazione.MercatiDService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.AnagrafeService;
import it.gruppoinit.pal.gp.core.service.MercatipresenzeTPrenotService;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MercatipresenzeTPrenotServiceImpl extends BaseServiceImpl<MercatipresenzeTPrenot, PkId> implements MercatipresenzeTPrenotService {

    @Autowired
    private MercatipresenzeTPrenotDAO mercatipresenzeTPrenotDAO;
    @Autowired
    private MercatipresenzeTService mercatipresenzeTService;
    @Autowired
    private MercatiDService mercatiDService;
    @Autowired
    private AnagrafeService anagrafeService;

    @Override
    public void insert(MercatipresenzeTPrenot entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatipresenzeTPrenotDAO.insert(entity);
	}
    }

    private void dataIntegration(MercatipresenzeTPrenot entity) {

	if (entity == null) {
	    throw new RuntimeException("Entity nulla");
	}
	if (entity.getDataInserimento() == null) {
	    entity.setDataInserimento(Calendar.getInstance().getTime());
	}
	fixMergeEntityProperties(entity);
    }

    @Override
    protected void fixMergeEntityProperties(MercatipresenzeTPrenot entity) {

	Anagrafe a = anagrafeService.bindDomainObject(entity.getAnagrafe(), PkId.class, "id.codice");
	entity.setAnagrafe(a);
	MercatiD d = mercatiDService.bindDomainObject(entity.getMercatiD(), PkId.class, "id.codice");
	entity.setMercatiD(d);
	MercatipresenzeT t = mercatipresenzeTService.bindDomainObject(entity.getMercatipresenzeT(), PkId.class, "id.codice");
	entity.setMercatipresenzeT(t);
    }

    @Override
    public void update(MercatipresenzeTPrenot entity) {

	dataIntegration(entity);
	if (validateEntity(entity)) {
	    mercatipresenzeTPrenotDAO.update(entity);
	}
    }

    @Override
    public void delete(MercatipresenzeTPrenot entity) {

	if (isDeleteAllowed(entity)) {
	    mercatipresenzeTPrenotDAO.delete(entity);
	}
    }

    @Override
    public List<MercatipresenzeTPrenot> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @Override
    public MercatipresenzeTPrenot findById(PkId id) {

	return mercatipresenzeTPrenotDAO.findById(id);
    }

    @Override
    protected Class<MercatipresenzeTPrenot> getEntityClass() {

	return MercatipresenzeTPrenot.class;
    }

    @Override
    public List<MercatipresenzeTPrenot> findByMercatipresenzeT(Integer mercatipresenzeTId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatipresenzeTId", mercatipresenzeTId, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInserimento"));
	return mercatipresenzeTPrenotDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatipresenzeTPrenot> findByMercatiD(Integer mercatiDId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatiDId", mercatiDId, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInserimento"));
	return mercatipresenzeTPrenotDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatipresenzeTPrenot> findByAnagrafe(Integer anagrafeId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("anagrafeId", anagrafeId, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInserimento"));
	return mercatipresenzeTPrenotDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatipresenzeTPrenot> findByMercatipresenzeTAndPosteggioAndAnagrafe(Integer mercatipresenzeTId, Integer mercatiDId,
	    Integer anagrafeId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatipresenzeTId", mercatipresenzeTId, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiDId", mercatiDId, Integer.class));
	fr.addFilterField(FilterUtils.equals("anagrafeId", anagrafeId, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInserimento"));
	return mercatipresenzeTPrenotDAO.findByFilterTable(ft);
    }

    @Override
    public List<MercatipresenzeTPrenot> findByMercatipresenzeTAndPosteggio(Integer mercatipresenzeTId, Integer mercatiDId) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("mercatipresenzeTId", mercatipresenzeTId, Integer.class));
	fr.addFilterField(FilterUtils.equals("mercatiDId", mercatiDId, Integer.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("dataInserimento"));
	return mercatipresenzeTPrenotDAO.findByFilterTable(ft);
    }
}
