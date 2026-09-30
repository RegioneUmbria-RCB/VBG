package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ContenttypesDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Contenttypes;
import it.gruppoinit.pal.gp.core.domain.ContenttypesId;
import it.gruppoinit.pal.gp.core.service.ContenttypesService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContenttypesServiceImpl extends BaseServiceImpl<Contenttypes, ContenttypesId> implements ContenttypesService {

    private CacheManager cacheManager;
    private ContenttypesDAO contenttypesDAO;
    private Map<String, String> contentTypesMap = new HashMap<String, String>();
    private static String DEFAULT_MIME_TYPE = "text/plain";

    @Autowired(required = false)
    public void setCacheManager(CacheManager cacheManager) {

	this.cacheManager = cacheManager;
    }

    public String getContentType(String ext) {

	String cType = "";
	String returnKey = "";
	Set<String> keys = getContentTypesMap().keySet();
	for (String key : keys) {
	    cType = contentTypesMap.get(key);
	    if (StringUtils.defaultIfEmpty(cType, "").toLowerCase().indexOf(StringUtils.defaultIfEmpty(ext, "").toLowerCase()) > -1) {
		returnKey = key;
		break;
	    }
	}
	return returnKey;
    }

    @Autowired
    public void setContenttypesDAO(ContenttypesDAO contenttypesDAO) {

	this.contenttypesDAO = contenttypesDAO;
    }

    @Override
    public String findMimeTypeByFileName(String nomeFile) {

	return findMimeTypeByFileName(nomeFile, DEFAULT_MIME_TYPE);
    }

    @Override
    public String findMimeTypeByFileName(String nomeFile, String defaultMime) {

	if (StringUtils.isBlank(nomeFile)) {
	    return defaultMime;
	}
	String ext = extractExtension(nomeFile);
	String result = getContentType(ext);
	if (StringUtils.isBlank(result)) {
	    result = defaultMime;
	}
	return result;
    }

    private Map<String, String> getContentTypesMap() {

	if (this.contentTypesMap == null || this.contentTypesMap.isEmpty()) {
	    this.contentTypesMap = new HashMap<String, String>();
	    List<Contenttypes> list = this.findAll(null, null);
	    for (Contenttypes contenttypes : list) {
		this.contentTypesMap.put(contenttypes.getId().getCtMimetype(), contenttypes.getId().getCtExtension());
	    }
	    return this.contentTypesMap;
	} else {
	    return contentTypesMap;
	}
    }

    @Override
    public void delete(Contenttypes entity) {

	throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Contenttypes> findAll(Integer firstResult, Integer maxResult) {

	List<Contenttypes> resultList = null;
	if (cacheManager != null) {
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_MIME_TYPES_KEY);
	    Element obj = cache.get(WebConstants.CACHE_MIME_TYPES_KEY_ELEMENTS);
	    if (obj != null) {
		resultList = (List<Contenttypes>) obj.getObjectValue();
	    } else {
		resultList = contenttypesDAO.findAll(null, null);
		Element element = new Element(WebConstants.CACHE_MIME_TYPES_KEY_ELEMENTS, resultList);
		cache.put(element);
		cache.flush();
	    }
	} else {
	    resultList = contenttypesDAO.findAll(null, null);
	}
	return resultList;
    }

    @Override
    public Contenttypes findById(ContenttypesId id) {

	throw new NotImplementedException();
    }

    @Override
    public void insert(Contenttypes entity) {

	throw new NotImplementedException();
    }

    @Override
    public void update(Contenttypes entity) {

	throw new NotImplementedException();
    }

    @Override
    protected Class<Contenttypes> getEntityClass() {

	return Contenttypes.class;
    }

    private String extractExtension(String nomeFile) {

	if (nomeFile == null || nomeFile.equals("")) {
	    throw new RuntimeException("Attenzione!! il nome del file non può essere nullo o vuoto");
	}
	return FilenameUtils.getExtension(nomeFile);
    }

    @Override
    public void resetObjectCached() {

	this.contentTypesMap = new HashMap<String, String>();
    }
}
