package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.dao.ComuniItalianiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.ComuniItaliani;
import it.gruppoinit.pal.gp.core.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ComuniItalianiService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComuniItalianiServiceImpl extends BaseServiceImpl<ComuniItaliani, String> implements ComuniItalianiService {

    private static final Logger log = LoggerFactory.getLogger(ComuniServiceImpl.class);
    private ComuniItalianiDAO comuniItalianiDAO;

    @Autowired
    public void setComuniDAO(ComuniItalianiDAO comuniItalianiDAO) {

	this.comuniItalianiDAO = comuniItalianiDAO;
    }

    @Override
    public void delete(ComuniItaliani entity) {

	comuniItalianiDAO.delete(entity);
    }

    @Override
    public List<ComuniItaliani> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException("Metodo non implemenatato");
    }

    @Override
    public ComuniItaliani findById(String id) {

	return comuniItalianiDAO.findById(id);
    }

    @Override
    public void insert(ComuniItaliani entity) {

	comuniItalianiDAO.insert(entity);
    }

    @Override
    public void update(ComuniItaliani entity) {

	comuniItalianiDAO.update(entity);
    }

    @Override
    public Class<ComuniItaliani> getEntityClass() {

	return ComuniItaliani.class;
    }

    @Override
    public ComuniItaliani findByCodiceComune(ComuniItaliani entity) {

	return comuniItalianiDAO.findByCodiceComune(entity);
    }

    @Override
    public List<ComuniItaliani> findByDescrizione(String comune) {

	return comuniItalianiDAO.findByDescrizione(comune);
    }

    @Override
    public ComuniItaliani findByComune(ComuniItaliani comuni) {

	FilterTable filterTable = new FilterTable(DAOEnum.FIND_ALL);
	FilterRestriction restriction = new FilterRestriction();
	if (StringUtils.isNotBlank(comuni.getCodiceistat())) {
	    restriction.addFilterField(FilterUtils.equals("codiceistat", comuni.getCodiceistat(), String.class));
	}
	if (StringUtils.isNotBlank(comuni.getComune())) {
	    restriction.addFilterField(FilterUtils.equals("comune", comuni.getCodicecomune(), String.class));
	}
	filterTable.addRestriction(restriction);
	List<ComuniItaliani> list = comuniItalianiDAO.findByFilterTable(filterTable);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    protected ComuniItaliani customBindDomainObject(ComuniItaliani entity) {

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
	    List<ComuniItaliani> list = comuniItalianiDAO.findByFilterTable(filterTable);
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		log.warn("customBindDomainObject: la ricerca ha tornato {} records ", list.size());
	    }
	}
	return null;
    }
}
