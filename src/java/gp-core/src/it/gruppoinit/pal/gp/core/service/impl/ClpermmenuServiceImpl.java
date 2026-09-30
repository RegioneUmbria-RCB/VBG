/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ClpermmenuDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Verticalizzazioniparametri;
import it.gruppoinit.pal.gp.core.domain.helper.ClpermenuComparator;
import it.gruppoinit.pal.gp.core.features.infrastructure.layout.testi.LayouttestiService;
import it.gruppoinit.pal.gp.core.features.verticalizzazioni.VerticalizzazioniService;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ClpermmenuService;

/**
 * @author francescop
 * 
 */
@Service
public class ClpermmenuServiceImpl extends BaseServiceImpl<Clpermmenu, PkId> implements ClpermmenuService {

    private static final Logger log = LoggerFactory.getLogger(ClpermmenuServiceImpl.class);
    private LayouttestiService layouttestiService;
    private ClpermmenuDAO clpermmenuDAO;
    private VerticalizzazioniService verticalizzazioniService;

    @Autowired
    public void setClpermmenuDAO(ClpermmenuDAO clpermmenuDAO) {

	this.clpermmenuDAO = clpermmenuDAO;
    }

    @Autowired
    public void setLayouttestiService(LayouttestiService layouttestiService) {

	this.layouttestiService = layouttestiService;
    }

    @Autowired
    public void setVerticalizzazioniService(VerticalizzazioniService verticalizzazioniService) {

	this.verticalizzazioniService = verticalizzazioniService;
    }

    @Override
    protected Class<Clpermmenu> getEntityClass() {

	return Clpermmenu.class;
    }

    @Override
    public void delete(Clpermmenu entity) {

	clpermmenuDAO.delete(entity);
    }

    @Override
    public List<Clpermmenu> findAll(Integer firstResult, Integer maxResult) {

	return clpermmenuDAO.findAll(null, null);
    }

    @Override
    public Clpermmenu findById(PkId id) {

	return clpermmenuDAO.findById(id);
    }

    @Override
    public void insert(Clpermmenu entity) {

	if (validateEntity(entity)) {
	    clpermmenuDAO.insert(entity);
	}
    }

    @Override
    public void update(Clpermmenu entity) {

	if (validateEntity(entity)) {
	    clpermmenuDAO.update(entity);
	}
    }

    @Override
    public Clpermmenu findByOperatoreAndClMenuAndSoftware(Integer codiceOperatore, Integer codiceClmenu, String codiceSoftware) {

	return clpermmenuDAO.findByOperatoreAndClMenuAndSoftware(codiceOperatore, codiceClmenu, codiceSoftware);
    }

    private List<Clpermmenu> findSubMenu(String menuLink, String software, Integer codiceOperatore, boolean onlyOneSubLevel, boolean useLinkV2) {

	return clpermmenuDAO.findSubMenu(menuLink, software, codiceOperatore, onlyOneSubLevel, useLinkV2);
    }

    @Override
    public String findMenuPrimoLivello(Integer codiceResponsabile, String contextPath) {

	List<Clpermmenu> list = this.findSubMenu("", WebConstants.SOFTWARE_TT, codiceResponsabile, true, false);
	Collections.sort(list, new ClpermenuComparator());
	StringBuilder menu = new StringBuilder("<ul class=\"sf-menu\">");
	boolean isVerticalizzazioneAttiva = true;
	Set<String> idOverrideNET = getMenuOverride();
	for (int i = 0; i < list.size(); i++) {
	    Clpermmenu clpermmenu = list.get(i);
	    Clmenu clmenu = clpermmenu.getMenu();
	    isVerticalizzazioneAttiva = true;
	    if (StringUtils.isNotBlank(clmenu.getVerticalizzazione())) {
		isVerticalizzazioneAttiva = verticalizzazioniService.isAttiva(clmenu.getVerticalizzazione());
	    }
	    if (isVerticalizzazioneAttiva) {
		String hrefAction = getSubMenuHref(clpermmenu, contextPath, idOverrideNET);
		List<Clpermmenu> submenuList = this.findSubMenu(clmenu.getMenulink(), clpermmenu.getSoftware().getCodice(), codiceResponsabile, true,
			false);
		if (submenuList.isEmpty()) {
		    menu.append("<li><a href=\"").append(hrefAction).append("\">").append(decodeDESCRIZIONE(clpermmenu)).append("</a></li>");
		} else {
		    String id = "m" + clmenu.getMenulink() + "_" + clpermmenu.getSoftware().getCodice();
		    hrefAction = "getSubMenu('" + id + "')";
		    menu.append("<li><a href=\"javascript: ").append(hrefAction).append("\">").append(decodeDESCRIZIONE(clpermmenu))
			    .append("</a><ul id=\"").append(id).append("\" class=\"sf-sub-menu-").append(id)
			    .append("\"><li><a href=\"#\" class=\"loading\" onmouseover=\"").append(hrefAction)
			    .append("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li></ul></li>");
		}
	    }
	}
	menu.append("</ul>");
	return menu.toString();
    }

    private Set<String> getMenuOverride() {

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
	return idOverrideNET;
    }

    private String getSubMenuHref(Clpermmenu menu, String contextPath, Set<String> idOverrideNET) {

	String returnToValueEncoded = "";
	try {
	    // FIXME BASE_URL
	    returnToValueEncoded = URLEncoder.encode(BackofficeNETConstants.getBASE_URL() + contextPath + "/", "UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    e1.printStackTrace();
	}
	boolean isEnterprise = isEnterprise();
	if (idOverrideNET != null && idOverrideNET.contains(String.valueOf(menu.getMenu().getId()))) {
	    isEnterprise = true;
	}
	String hrefAction = menu.getMenu().getPagina();
	if (isEnterprise == false) {
	    hrefAction = menu.getMenu().getLinkStandard();
	}
	if (StringUtils.isBlank(hrefAction)) {
	    hrefAction = "javascript: void(0);";
	    return hrefAction;
	}
	String tipoApplicativo = menu.getMenu().getJsp();
	if (StringUtils.isBlank(tipoApplicativo)) {
	    tipoApplicativo = "";
	}
	// if (StringUtils.defaultIfEmpty(menu.getMenu().getPagina(), "").equalsIgnoreCase(
	// StringUtils.defaultIfEmpty(menu.getMenu().getLinkStandard(), ""))) {
	// // L'installazione non è enterprise allora recupero l'url dalla colonna linkStandard
	// // FIXME Risolvere fino al rilascio definitivo. (non funziona il link stampe CR)
	// isEnterprise = true;
	// // fino al rilascio considero i menù con le voci uguali come enterprise
	// }
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
	    hrefAction = hrefAction.replaceAll("SOFTWARE", menu.getSoftware().getCodice());
	    hrefAction = hrefAction.replaceAll("Software", "software");
	}
	if (log.isDebugEnabled()) {
	    log.debug("MENU LINK: {}", hrefAction);
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

    @Override
    public String findMenuSottoLivelli(Integer codiceResponsabile, String menuId, String contextPath) {

	String submenu = "";
	menuId = menuId.substring(1);// elimino la m iniziale
	String[] params = menuId.split("_");
	String menuLink = params[0];
	String software = params[1];
	submenu = getSubMenu(menuLink, software, codiceResponsabile, contextPath);
	return submenu;
    }

    private String getSubMenu(String menuLink, String software, Integer codiceresponsabile, String contextPath) {

	List<Clpermmenu> clmenuItems = this.findSubMenu(menuLink, software, codiceresponsabile, true, false);
	Collections.sort(clmenuItems, new ClpermenuComparator());
	StringBuilder submenu = new StringBuilder("");
	boolean isVerticalizzazioneAttiva = true;
	Set<String> idOverrideNET = getMenuOverride();
	if (!clmenuItems.isEmpty()) {
	    for (Clpermmenu item : clmenuItems) {
		Clmenu menu = item.getMenu();
		isVerticalizzazioneAttiva = true;
		if (StringUtils.isNotBlank(menu.getVerticalizzazione())) {
		    isVerticalizzazioneAttiva = verticalizzazioniService.isAttiva(menu.getVerticalizzazione(), item.getSoftware().getCodice());
		}
		if (isVerticalizzazioneAttiva) {
		    // List<Clpermmenu> subItems = this.findSubMenu(menu.getMenulink(), item.getSoftware().getCodice(), codiceresponsabile, true);
		    // String hrefAction = getSubMenuHref(item, contextPath);
		    // submenu.append("<li>");
		    // submenu.append("<a href=\"").append(hrefAction).append("\">").append(decodeDESCRIZIONE(item)).append("</a>");
		    // if (!subItems.isEmpty()) {
		    // submenu.append("<ul>");
		    // submenu.append(getSubMenu(menu.getMenulink(), item.getSoftware().getCodice(), codiceresponsabile, contextPath));
		    // submenu.append("</ul>");
		    // }
		    // submenu.append("</li>");
		    String hrefAction = getSubMenuHref(item, contextPath, idOverrideNET);
		    List<Clpermmenu> submenuList = this.findSubMenu(menu.getMenulink(), item.getSoftware().getCodice(), codiceresponsabile, true,
			    false);
		    if (submenuList.isEmpty()) {
			submenu.append("<li><a href=\"").append(hrefAction).append("\">").append(decodeDESCRIZIONE(item)).append("</a></li>");
		    } else {
			String id = "m" + menu.getMenulink() + "_" + item.getSoftware().getCodice();
			hrefAction = "getSubMenu('" + id + "')";
			submenu.append("<li><a href=\"javascript: ").append(hrefAction).append("\">").append(decodeDESCRIZIONE(item))
				.append("</a><ul id=\"").append(id).append("\" class=\"sf-sub-menu-").append(id)
				.append("\"><li><a href=\"#\" class=\"loading\" onmouseover=\"").append(hrefAction)
				.append("\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li></ul></li>");
		    }
		}
	    }
	}
	return submenu.toString();
    }

    @Override
    public String findPushMenuSottoLivelli(Integer codiceResponsabile, String menuId, String contextPath, boolean useV2Link) {

	String submenu = "";
	String menuLink = "";
	String software = WebConstants.SOFTWARE_TT;
	if (menuId != null) {
	    menuId = menuId.substring(1);// elimino la m iniziale
	    String[] params = menuId.split("_");
	    menuLink = params[0];
	    software = params[1];
	}
	submenu = getSubMenuPush(menuLink, software, codiceResponsabile, contextPath, useV2Link);
	return submenu;
    }

    private String getSubMenuPush(String menuLink, String software, Integer codiceresponsabile, String contextPath, boolean useV2Link) {

	List<Clpermmenu> clmenuItems = this.findSubMenu(menuLink, software, codiceresponsabile, true, useV2Link);
	Collections.sort(clmenuItems, new ClpermenuComparator());
	StringBuilder submenu = new StringBuilder("[");
	boolean isVerticalizzazioneAttiva = true;
	String tmpl = "{\"name\": \"MENU_TITLE\", \"id\": \"ID_MENU\", \"icon\": \"ICON\", \"link\": \"COLLEGAMENTO\"},";
	String tmplItems = "{\"name\": \"MENU_TITLE\", \"id\": \"ID_MENU\", \"icon\": \"ICON\", \"link\": \"COLLEGAMENTO\", \"items\": [{\"title\": \"MENU_TITLE\",\"items\": []  }]},";
	Set<String> idOverrideNET = getMenuOverride();
	if (!clmenuItems.isEmpty()) {
	    for (Clpermmenu item : clmenuItems) {
		Clmenu menu = item.getMenu();
		String mlink = menu.getMenulink();
		if (useV2Link) {
		    mlink = menu.getMenulinkV2();
		}
		isVerticalizzazioneAttiva = true;
		if (StringUtils.isNotBlank(menu.getVerticalizzazione())) {
		    isVerticalizzazioneAttiva = verticalizzazioniService.isAttiva(menu.getVerticalizzazione(), item.getSoftware().getCodice());
		}
		String id = "m" + mlink + "_" + item.getSoftware().getCodice();
		if (isVerticalizzazioneAttiva) {
		    String hrefAction = getSubMenuHref(item, contextPath, idOverrideNET);
		    List<Clpermmenu> submenuList = this.findSubMenu(mlink, item.getSoftware().getCodice(), codiceresponsabile, true, useV2Link);
		    String descrizione = decodeDESCRIZIONE(item).replace("'", "\'");
		    if (submenuList.isEmpty()) {
			String mnu = tmpl.replaceAll("MENU_TITLE", descrizione).replaceAll("ID_MENU", id).replaceAll("ICON", "")
				.replaceAll("COLLEGAMENTO", hrefAction);
			submenu.append(mnu);
		    } else {
			hrefAction = "#";
			String mnu = tmplItems.replaceAll("MENU_TITLE", descrizione).replaceAll("ID_MENU", id).replaceAll("ICON", "")
				.replaceAll("COLLEGAMENTO", hrefAction);
			submenu.append(mnu);
		    }
		}
	    }
	}
	submenu.append("]");
	return submenu.toString();
    }

    private boolean isEnterprise() {

	return verticalizzazioniService.isInstallazioneEnterprise();
    }

    @Override
    public List<Clpermmenu> findByOperatore(Integer codiceResponsabile) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceResponsabile, "responsabile", Integer.class));
	ft.addRestriction(fr);
	return clpermmenuDAO.findByFilterTable(ft);
    }
}
