package it.gruppoinit.pal.gp.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.annotations.cache.DeletableCacheElements;
import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ClmenuDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.ClmenuBean;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.ClmenuBeanComparator;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.ClpermmenuBean;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.MenuHelper;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.MenuHolder;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.MenuSezione;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.MenuSoftware;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.VoceMenuBean;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ClmenuService;
import it.gruppoinit.pal.gp.core.service.ClpermmenuService;
import it.gruppoinit.pal.gp.core.service.SoftwareService;
import it.gruppoinit.pal.gp.core.service.UserSecurityService;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

@Service
public class ClmenuServiceImpl extends BaseServiceImpl<Clmenu, Integer> implements ClmenuService {

    private ClmenuDAO clmenuDAO;
    @Autowired(required = false)
    private CacheManager cacheManager;
    private ClpermmenuService clpermmenuService;
    private SoftwareService softwareService;
    private UserSecurityService userSecurityService;
    private HashMap<String, MenuHolder> mapMenuV2 = new HashMap<String, MenuHolder>();
    private LayouttestiService layouttestiService;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setLayouttestiService(LayouttestiService layouttestiService) {

	this.layouttestiService = layouttestiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Autowired
    public void setClpermmenuService(ClpermmenuService clpermmenuService) {

	this.clpermmenuService = clpermmenuService;
    }

    @Autowired
    public void setSoftwareService(SoftwareService softwareService) {

	this.softwareService = softwareService;
    }

    @Autowired
    public void setUserSecurityService(UserSecurityService userSecurityService) {

	this.userSecurityService = userSecurityService;
    }

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
	if (mapMenuV2.get(getMenuCachev2(codiceResponsabile)) != null) {
	    mapMenuV2.remove(getMenuCachev2(codiceResponsabile));
	}
    }

    @Override
    public List<Clmenu> findTreeBySoftware(String pSoftware, boolean v2) {

	return clmenuDAO.findTreeBySoftware(pSoftware, v2);
    }

    private String getMenuCachev2(Integer codiceResponsabile) {

	return ORMHelper.getIdcomuneAlias() + "_" + codiceResponsabile.toString() + "_menu_v2";
    }

    private MenuHolder getMenuHolder(Responsabili responsabile, String contextPath, boolean useMenuLinkV2) {

	Map<String, ClmenuBean> dic = new LinkedHashMap<String, ClmenuBean>();
	Map<Integer, ClmenuBean> mapPermessi = new LinkedHashMap<Integer, ClmenuBean>();
	List<ClmenuBean> list = new ArrayList<ClmenuBean>();
	FilterTable ft = new FilterTable(DAOEnum.FIND_ALL);
	if (useMenuLinkV2) {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.isNotNull("menulinkV2"));
	    ft.addRestriction(fr);
	    ft.addOrder(FilterUtils.orderAsc("menulinkV2"));
	} else {
	    FilterRestriction fr = new FilterRestriction();
	    fr.addFilterField(FilterUtils.isNotNull("menulink"));
	    ft.addRestriction(fr);
	    ft.addOrder(FilterUtils.orderAsc("menulink"));
	}
	List<Clmenu> clmenus = clmenuDAO.findByFilterTable(ft);
	for (Clmenu clmenu : clmenus) {
	    ClmenuBean b = new ClmenuBean();
	    b.setId(clmenu.getId());
	    b.setDescrizione(clmenu.getDescrizione());
	    b.setJsp(clmenu.getJsp());
	    b.setLayouttesti(clmenu.getLayouttesti());
	    b.setLinkStandard(clmenu.getLinkStandard());
	    if (useMenuLinkV2) {
		b.setMenulink(clmenu.getMenulinkV2());
	    } else {
		b.setMenulink(clmenu.getMenulink());
	    }
	    b.setPagina(clmenu.getPagina());
	    b.setSoftware(clmenu.getSoftware());
	    b.setSoftwareesclusi(clmenu.getSoftwareesclusi());
	    b.setTipoFunzionalita(clmenu.getTipoFunzionalita());
	    b.setVerticalizzazione(clmenu.getVerticalizzazione());
	    b.setOrdinamento(clmenu.getMenulink());
	    list.add(b);
	}
	List<Software> softwares = softwareService.findSoftwareAbilitati(responsabile);
	List<ClpermmenuBean> permessi = new ArrayList<ClpermmenuBean>();
	List<Clpermmenu> pmenus = clpermmenuService.findByOperatore(responsabile.getId().getCodice());
	for (Clpermmenu clpermmenu : pmenus) {
	    ClpermmenuBean cpb = new ClpermmenuBean();
	    cpb.setFkidmenu(clpermmenu.getMenu().getId());
	    cpb.setCodiceresponsabile(clpermmenu.getResponsabile().getId().getCodice());
	    cpb.setId(clpermmenu.getId().getCodice());
	    cpb.setIdcomune(clpermmenu.getId().getIdcomune());
	    cpb.setSoftware(clpermmenu.getSoftware().getCodice());
	    permessi.add(cpb);
	}
	List<MenuHelper> menus = new ArrayList<MenuHelper>();
	for (ClmenuBean clmenuBean : list) {
	    if (clmenuBean.getMenulink().length() == 1) {
		MenuHelper h1 = new MenuHelper(clmenuBean, softwares);
		menus.add(h1);
	    }
	    mapPermessi.put(clmenuBean.getId(), clmenuBean);
	    dic.put(clmenuBean.getMenulink(), clmenuBean);
	    String idPadre = clmenuBean.getIdPadre();
	    if (null != idPadre) {
		ClmenuBean padre = dic.get(idPadre);
		if (padre != null) {
		    padre.getChilds().add(clmenuBean);
		    Collections.sort(padre.getChilds(), new ClmenuBeanComparator());
		}
	    }
	}
	for (ClpermmenuBean clpermmenuBean : permessi) {
	    ClmenuBean mnu = mapPermessi.get(clpermmenuBean.getFkidmenu());
	    if (mnu != null) {
		mnu.aggiungiSoftware(clpermmenuBean.getSoftware());
	    }
	}
	for (MenuHelper mh : menus) {
	    ClmenuBean s = dic.get(mh.getMenulink());
	    mh.aggiungiFigli(s.getChilds());
	}
	sistemaLinks(menus, contextPath);
	return new MenuHolder(menus);
    }

    private void sistemaLinks(List<MenuHelper> menus, String contextPath) {

	Verticalizzazioniparametri p = verticalizzazioniService.getVerticalizzazioniparametri(WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE,
		WebConstants.VERTICALIZZAZIONE_TIPO_INSTALLAZIONE_OVERRIDE_MENU_STANDARD, WebConstants.SOFTWARE_TT);
	Set<String> idOverrideNET = new HashSet<String>();
	if (p != null) {
	    String v = StringUtils.defaultString(p.getValore()).trim();
	    if (StringUtils.isNotBlank(v)) {
		String vals[] = v.split(",");
		for (String s : vals) {
		    s = StringUtils.trim(s);
		    if (StringUtils.isNotBlank(s)) {
			idOverrideNET.add(s);
		    }
		}
	    }
	}
	for (MenuHelper mh : menus) {
	    List<MenuSoftware> s = mh.getSoftware();
	    for (MenuSoftware ms : s) {
		List<MenuSezione> sz = ms.getSezioni();
		for (MenuSezione msz : sz) {
		    if ("sezione base".equalsIgnoreCase(msz.getNome())) {
			msz.setNome(mh.getNome() + " / " + ms.getNome());
		    }
		    List<VoceMenuBean> vocis = msz.getVoci();
		    for (VoceMenuBean voceMenuBean : vocis) {
			voceMenuBean.setLink(getSubMenuHref(voceMenuBean, contextPath, ms.getId(), idOverrideNET));
		    }
		}
	    }
	}
    }

    @Override
    @DeletableCacheElements
    public void resetObjectCached() {

	this.mapMenuV2 = new HashMap<String, MenuHolder>();
    }

    @Override
    public MenuHolder getMenuV2(String contextPath, boolean useMenuLinkV2) {

	MenuHolder result = null;
	Responsabili responsabile = (Responsabili) userSecurityService.getCurrentlyAuthenticatedUserDetails();
	String key = getMenuCachev2(responsabile.getId().getCodice());
	if (mapMenuV2.get(key) != null) {
	    return mapMenuV2.get(key);
	} else {
	    result = getMenuHolder(responsabile, contextPath, useMenuLinkV2);
	    mapMenuV2.put(key, result);
	}
	return result;
    }

    private String getSubMenuHref(VoceMenuBean menu, String contextPath, String software, Set<String> idOverrideNET) {

	String returnToValueEncoded = "";
	try {
	    returnToValueEncoded = URLEncoder.encode(BackofficeNETConstants.getBASE_URL() + contextPath + "/", "UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    e1.printStackTrace();
	}
	boolean isEnterprise = isEnterprise();
	if (!(idOverrideNET == null || idOverrideNET.isEmpty())) {
	    if (idOverrideNET.contains(menu.getId())) {
		isEnterprise = true;
	    }
	}
	String hrefAction = menu.getPaginaInternal();
	if (isEnterprise == false) {
	    hrefAction = menu.getLinkStandardInternal();
	}
	if (StringUtils.isBlank(hrefAction)) {
	    hrefAction = "javascript: void(0);";
	    return hrefAction;
	}
	String tipoApplicativo = menu.getJspInternal();
	if (StringUtils.isBlank(tipoApplicativo)) {
	    tipoApplicativo = "";
	}
	if (isEnterprise == false) {
	    try {
		hrefAction = URLEncoder.encode("/" + hrefAction, "UTF-8");
		hrefAction = URLEncoder.encode(hrefAction, "UTF-8");
		hrefAction = URLEncoder.encode(hrefAction, "UTF-8");
	    } catch (UnsupportedEncodingException e) {
		e.printStackTrace();
	    }
	    hrefAction = "javascript: historyClear('" + hrefAction + "');";
	} else {
	    if (tipoApplicativo.equalsIgnoreCase(WebConstants.TIPO_JAVA)) {
		try {
		    hrefAction = URLEncoder.encode("/" + hrefAction, "UTF-8");
		    hrefAction = URLEncoder.encode(hrefAction, "UTF-8");
		    hrefAction = URLEncoder.encode(hrefAction, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		hrefAction = "javascript: historyClear('" + hrefAction + "');";
	    } else if (tipoApplicativo.equalsIgnoreCase(WebConstants.TIPO_NET)) {
		String aspxLink = hrefAction;
		String params = "&" + BackofficeNETConstants.RETURNTO + "=" + returnToValueEncoded;
		aspxLink = BackofficeNETConstants.getURL_APP_ASPNET() + "/" + aspxLink;
		try {
		    aspxLink += params;
		    aspxLink = URLEncoder.encode(aspxLink, "UTF-8");
		    aspxLink = URLEncoder.encode(aspxLink, "UTF-8");
		    aspxLink = URLEncoder.encode(aspxLink, "UTF-8");
		    aspxLink = URLEncoder.encode(aspxLink, "UTF-8");
		    aspxLink = URLEncoder.encode(aspxLink, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		hrefAction = "javascript: historyClear('/externalresource/goTo.htm?url=" + aspxLink + "');";
	    } else if (tipoApplicativo.equalsIgnoreCase(WebConstants.TIPO_ASP)) {
		String aspLink = hrefAction;
		String params = "&" + BackofficeNETConstants.RETURNTO + "=" + returnToValueEncoded;
		aspLink = BackofficeNETConstants.getURL_APP_ASP() + "/" + aspLink;
		try {
		    aspLink += params;
		    aspLink = URLEncoder.encode(aspLink, "UTF-8");
		    aspLink = URLEncoder.encode(aspLink, "UTF-8");
		    aspLink = URLEncoder.encode(aspLink, "UTF-8");
		    aspLink = URLEncoder.encode(aspLink, "UTF-8");
		    aspLink = URLEncoder.encode(aspLink, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		hrefAction = "javascript: historyClear('/externalresource/goTo.htm?url=" + aspLink + "');";
	    }
	}
	if (hrefAction != null) {
	    hrefAction = hrefAction.replaceAll("SOFTWARE", software);
	    hrefAction = hrefAction.replaceAll("Software", "software");
	}
	return hrefAction;
    }

    private String decodeDESCRIZIONE(Clpermmenu menu) {

	String descrizione = menu.getMenu().getDescrizione();
	if (StringUtils.isNotBlank(menu.getMenu().getLayouttesti())) {
	    String chiave = menu.getMenu().getLayouttesti();
	    String label = layouttestiService.resolveCode(chiave, menu.getSoftware().getCodice());
	    if (!chiave.equalsIgnoreCase(label)) {
		descrizione = label;
	    }
	}
	if (descrizione != null) {
	    descrizione = descrizione.replaceAll("SOFTWARE", menu.getSoftware().getDescrizione());
	    descrizione = descrizione.replaceAll("Software", "software");
	}
	return descrizione;
    }

    private boolean isEnterprise() {

	return verticalizzazioniService.isInstallazioneEnterprise();
    }
}
