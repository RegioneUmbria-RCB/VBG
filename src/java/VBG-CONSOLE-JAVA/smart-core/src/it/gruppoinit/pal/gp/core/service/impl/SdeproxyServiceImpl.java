package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.SdeproxyDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Sdecomuniassociati;
import it.gruppoinit.pal.gp.core.domain.Sdeproxy;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;
import it.gruppoinit.pal.gp.core.service.SdecomuniassociatiService;
import it.gruppoinit.pal.gp.core.service.SdeproxyService;
import it.gruppoinit.pal.gp.core.service.helper.ComuniComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SdeproxyServiceImpl extends BaseServiceImpl<Sdeproxy, String> implements SdeproxyService {

    private static Logger log = LoggerFactory.getLogger(SdeproxyServiceImpl.class);
    private SdeproxyDAO sdeproxyDAO;
    private SdecomuniassociatiService sdecomuniassociatiService;
    private ComuniService comuniService;

    @Autowired
    public void setComuniService(ComuniService comuniService) {

	this.comuniService = comuniService;
    }

    @Autowired
    public void setSdecomuniassociatiService(SdecomuniassociatiService sdecomuniassociatiService) {

	this.sdecomuniassociatiService = sdecomuniassociatiService;
    }

    @Autowired
    public void setSdeproxyDAO(SdeproxyDAO sdeproxyDAO) {

	this.sdeproxyDAO = sdeproxyDAO;
    }

    @Override
    public void insert(Sdeproxy entity) {

	if (validateEntity(entity)) {
	    sdeproxyDAO.insert(entity);
	}
    }

    @Override
    public void update(Sdeproxy entity) {

	if (validateEntity(entity)) {
	    sdeproxyDAO.update(entity);
	}
    }

    @Override
    public void delete(Sdeproxy entity) {

	if (isDeleteAllowed(entity)) {
	    sdeproxyDAO.delete(entity);
	}
    }

    @Override
    public List<Sdeproxy> findAll(Integer firstResult, Integer maxResult) {

	return sdeproxyDAO.findAll(null, null, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Sdeproxy findById(String id) {

	return sdeproxyDAO.findById(id);
    }

    @Override
    protected Class<Sdeproxy> getEntityClass() {

	return Sdeproxy.class;
    }

    @Override
    public List<Comuni> findComuniAssociati(String idente) {

	if (StringUtils.isBlank(idente)) {
	    log.error("IdEnte nullo");
	    throw new SecurityException("Inizializzazione applicativo non riuscita");
	}
	Sdeproxy sdeproxy = this.findById(idente);
	if (sdeproxy == null) {
	    log.error("IdEnte non valido: {}", idente);
	    throw new SecurityException("Inizializzazione applicativo non riuscita. idente [" + idente + "] non valido");
	}
	Comuni capofila = comuniService.findById(sdeproxy.getCodicecatastalecomune());
	List<Comuni> result = new ArrayList<Comuni>();
	result.add(capofila);
	List<Sdecomuniassociati> list = sdecomuniassociatiService.findByIdente(idente);
	for (Sdecomuniassociati ca : list) {
	    result.add(ca.getComune());
	}
	Collections.sort(result, new ComuniComparator());
	return result;
    }

    @Override
    public Sdeproxy findByAlias(String idcomunealias) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("aliasEnte", idcomunealias, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("idente"));
	List<Sdeproxy> l = sdeproxyDAO.findByFilterTable(ft, 0, 1);
	if (l != null) {
	    if (l.size() > 0) {
		return l.get(0);
	    }
	}
	return null;
    }

    @Override
    public Sdeproxy findByCodiceCatastale(String codiceCatastale) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codicecatastalecomune", codiceCatastale, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("idente"));
	List<Sdeproxy> l = sdeproxyDAO.findByFilterTable(ft, 0, 1);
	if (l != null) {
	    if (l.size() > 0) {
		return l.get(0);
	    }
	}
	// magari il codice catastale sta nella tabella sdecomuniassociati
	List<Sdecomuniassociati> cas = sdecomuniassociatiService.findByCodiceCatastale(codiceCatastale);
	if (cas != null) {
	    if (cas.size() > 0) {
		Sdeproxy sde = this.findById(cas.get(0).getId().getIdente());
		return sde;
	    }
	}
	return null;
    }

    @Override
    public Sdeproxy findByIdEnte(String idente) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("idente", idente, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("idente"));
	List<Sdeproxy> l = sdeproxyDAO.findByFilterTable(ft, 0, 1);
	if (l != null) {
	    if (l.size() > 0) {
		return l.get(0);
	    }
	}
	return null;
    }

    @Override
    public Sdeproxy findByCodiceUnioneRFC53(String codiceUnioneRFC53) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("codiceUnioneRfc53", codiceUnioneRFC53, String.class));
	ft.addRestriction(fr);
	ft.addOrder(FilterUtils.orderAsc("idente"));
	List<Sdeproxy> l = sdeproxyDAO.findByFilterTable(ft, 0, 1);
	if (l != null) {
	    if (l.size() > 0) {
		return l.get(0);
	    }
	}
	return null;
    }
}
