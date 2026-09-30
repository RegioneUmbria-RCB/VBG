package it.gruppoinit.pal.gp.core.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniService;

@Service
public class ComuniServiceImpl extends BaseServiceImpl<Comuni, String> implements ComuniService {

    private static final Logger log = LoggerFactory.getLogger(ComuniServiceImpl.class);
    private ComuniDAO comuniDAO;

    @Autowired
    public void setComuniDAO(ComuniDAO comuniDAO) {

	this.comuniDAO = comuniDAO;
    }

    @Override
    public void delete(Comuni entity) {

	comuniDAO.delete(entity);
    }

    @Override
    public List<Comuni> findAll(Integer firstResult, Integer maxResult) {

	return comuniDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Comuni findById(String id) {

	return comuniDAO.findById(id);
    }

    @Override
    public void insert(Comuni entity) {

	comuniDAO.insert(entity);
    }

    @Override
    public void update(Comuni entity) {

	comuniDAO.update(entity);
    }

    @Override
    public Class<Comuni> getEntityClass() {

	return Comuni.class;
    }

    @Override
    public Comuni findByCodiceComune(Comuni entity) {

	return comuniDAO.findByCodiceComune(entity);
    }

    @Override
    public List<Comuni> findByDescrizione(String comune, int maxResults) {

	return comuniDAO.findByDescrizione(comune, maxResults);
    }

    @Override
    public Comuni findByComune(Comuni comuni) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction restriction = new FilterRestriction();
	if (StringUtils.isNotBlank(comuni.getCodiceistat())) {
	    restriction.addFilterField(FilterUtils.equals("codiceistat", comuni.getCodiceistat(), String.class));
	}
	if (StringUtils.isNotBlank(comuni.getComune())) {
	    restriction.addFilterField(FilterUtils.equals("comune", comuni.getComune(), String.class));
	}
	filterTable.addRestriction(restriction);
	List<Comuni> list = comuniDAO.findByFilterTable(filterTable);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    protected Comuni customBindDomainObject(Comuni entity) {

	if (entity == null) {
	    return null;
	}
	if (StringUtils.isNotBlank(entity.getCf()) || StringUtils.isNotBlank(entity.getCodiceistat()) || StringUtils.isNotBlank(entity.getComune())) {
	    FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	    FilterRestriction restriction = new FilterRestriction();
	    if (StringUtils.isNotBlank(entity.getCodiceistat())) {
		log.debug("customBindDomainObject: Cerco il comune per codiceistat {}", entity.getCodiceistat());
		restriction.addFilterField(FilterUtils.equals("codiceistat", entity.getCodiceistat(), String.class));
	    }
	    if (StringUtils.isNotBlank(entity.getComune())) {
		log.debug("customBindDomainObject: Cerco il comune per comune {}", entity.getComune());
		restriction.addFilterField(FilterUtils.equalsIgnoreCase("comune", entity.getComune()));
	    }
	    if (StringUtils.isNotBlank(entity.getCf())) {
		log.debug("customBindDomainObject: Cerco il comune per cf {}", entity.getCf());
		restriction.addFilterField(FilterUtils.equals("cf", entity.getCf(), String.class));
	    }
	    filterTable.addRestriction(restriction);
	    List<Comuni> list = comuniDAO.findByFilterTable(filterTable);
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		log.warn("customBindDomainObject: la ricerca ha tornato {} records ", list.size());
	    }
	}
	return null;
    }

    @Override
    public Comuni findByCodiceIstat(String codiceistat) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction restriction = new FilterRestriction();
	restriction.addFilterField(FilterUtils.equals("codiceistat", codiceistat, String.class));
	filterTable.addRestriction(restriction);
	List<Comuni> list = comuniDAO.findByFilterTable(filterTable);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<Comuni> findComuniItalianiByDescrizione(String comune, int maxResults) {

	return comuniDAO.findComuniItalianiByDescrizione(comune, maxResults);
    }
}
