package it.gruppoinit.pal.gp.backoffice.web;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.security.LoggedUser;
import it.gruppoinit.pal.gp.core.service.ClpermmenuService;

import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuController extends BaseController<Clmenu> {

    @Autowired
    private ClpermmenuService clpermmenuService;
    @Autowired(required = false)
    private CacheManager cacheManager;

    @RequestMapping
    public String getMenuPrimoLivelloSF(HttpServletRequest request) {

	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	String htmlMenu = "";
	if (cacheManager != null) {
	    String key = ORMHelper.getIdcomuneAlias() + user.getCodiceResponsabile().toString();
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_MENU_KEY);
	    Element obj = cache.get(key);
	    if (null != obj) {
		htmlMenu = (String) obj.getValue();
	    } else {
		htmlMenu = clpermmenuService.findMenuPrimoLivello(user.getCodiceResponsabile(), request.getContextPath());
		Element element = new Element(key, htmlMenu);
		cache.put(element);
		cache.flush();
	    }
	} else {
	    htmlMenu = clpermmenuService.findMenuPrimoLivello(user.getCodiceResponsabile(), request.getContextPath());
	}
	request.setAttribute("menuPrimoLivello", htmlMenu);
	return "menu/menu";
    }

    @RequestMapping
    public String getSubMenu(@RequestParam("menu") String menuId, HttpServletRequest request) {

	LoggedUser user = (LoggedUser) getCurrentlyAuthenticatedUser();
	String htmlSubmenu = "";
	if (cacheManager != null) {
	    String key = ORMHelper.getIdcomuneAlias() + user.getCodiceResponsabile().toString() + menuId;
	    Cache cache = cacheManager.getCache(WebConstants.CACHE_MENU_KEY);
	    Element obj = cache.get(key);
	    if (null != obj) {
		htmlSubmenu = (String) obj.getValue();
	    } else {
		htmlSubmenu = clpermmenuService.findMenuSottoLivelli(user.getCodiceResponsabile(), menuId, request.getContextPath());
		Element element = new Element(key, htmlSubmenu);
		cache.put(element);
		cache.flush();
	    }
	} else {
	    htmlSubmenu = clpermmenuService.findMenuSottoLivelli(user.getCodiceResponsabile(), menuId, request.getContextPath());
	}
	request.setAttribute("submenu", htmlSubmenu);
	return "menu/submenu";
    }

    @Override
    protected void fixMergeEntityProperty(Clmenu entity) {

    }

    @Override
    protected void fixRenderEntityProperty(Clmenu entity) {

    }

    @Override
    protected void setPageAttributes(Model model) {

    }
}
