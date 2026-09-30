package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ClmenuDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.service.ClmenuService;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClmenuServiceImpl extends BaseServiceImpl<Clmenu, Integer> implements ClmenuService {

    private ClmenuDAO clmenuDAO;
    @Autowired(required = false)
    private CacheManager cacheManager;

    @Autowired
    public void setClmenuDAO(ClmenuDAO clmenuDAO) {

	this.clmenuDAO = clmenuDAO;
    }

    @Override
    public void delete(Clmenu entity) {

	clmenuDAO.delete(entity);
    }

    @Override
    public List<Clmenu> findAll(Integer firstResult, Integer maxResult) {

	return clmenuDAO.findAll(firstResult, maxResult);
    }

    @Override
    public Clmenu findById(Integer id) {

	return clmenuDAO.findById(id);
    }

    @Override
    public void insert(Clmenu entity) {

	if (validateEntity(entity))
	    clmenuDAO.insert(entity);
    }

    @Override
    public void update(Clmenu entity) {

	if (validateEntity(entity))
	    clmenuDAO.update(entity);
    }

    @Override
    public Class<Clmenu> getEntityClass() {

	return Clmenu.class;
    }

    @Override
    public List<Clmenu> findFirstLevelMenu() {

	return clmenuDAO.findFirstLevelMenu();
    }

    @Override
    public List<Clmenu> findMenu(String parentLevelCode, int length) {

	return clmenuDAO.findMenu(parentLevelCode, length);
    }

    public void removeMenuFromCache(Integer codiceResponsabile) {

	if (cacheManager != null) {
	    String menuKeyPrefix = ORMHelper.getIdcomuneAlias() + codiceResponsabile.toString();
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_MENU_KEY);
	    List keys = cache.getKeys();
	    for (Object key : keys) {
		String k = (String) key;
		if (k.startsWith(menuKeyPrefix)) {
		    cache.remove(key);
		}
	    }
	    cache.flush();
	}
    }

    @Override
    public List<Clmenu> findTreeBySoftware(String pSoftware) {

	return clmenuDAO.findTreeBySoftware(pSoftware);
    }
}
