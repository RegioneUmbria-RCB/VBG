package it.gruppoinit.pal.gp.areariservata.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired(required = false)
    private CacheManager cacheManager;
    
    @RequestMapping
    public String view(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("view");
	return "admin/form";
    }

    @RequestMapping
    public String reload(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("reload");
	Map<String, String> map = WebConstants.reloadSecurityParamsMap();
	SortedMap<String, String> smap = new TreeMap<String, String>();
	smap.putAll(map);
	model.addAttribute("attrs", smap);
	return "admin/form";
    }

    @RequestMapping
    public String reset(Model model, HttpServletRequest request, HttpServletResponse response) {

	log.info("reset");
	if (cacheManager != null) {
	    String[] cacheNames = cacheManager.getCacheNames();
	    if (cacheNames != null) {
		for (String nomeCache : cacheNames) {
		    Cache cache = cacheManager.getCache(nomeCache);
		    cache.removeAll();
		}
	    }
	    model.addAttribute("caches", cacheNames);
	}
	return "admin/form";
    }
}
