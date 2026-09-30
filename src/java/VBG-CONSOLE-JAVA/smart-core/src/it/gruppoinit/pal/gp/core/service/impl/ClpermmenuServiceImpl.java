/**
 * 
 */
package it.gruppoinit.pal.gp.core.service.impl;

import it.gruppoinit.pal.gp.core.constants.BackofficeNETConstants;
import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.ClpermmenuDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ClpermenuComparator;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;
import it.gruppoinit.pal.gp.core.service.ClpermmenuService;
import it.gruppoinit.pal.gp.core.service.LayouttestiService;
import it.gruppoinit.pal.gp.core.service.VerticalizzazioniService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private List<Clpermmenu> findSubMenu(String menuLink, String software, Integer codiceOperatore, boolean onlyOneSubLevel) {

	return clpermmenuDAO.findSubMenu(menuLink, software, codiceOperatore, onlyOneSubLevel);
    }

    @Override
    public String findMenuPrimoLivello(Integer codiceResponsabile, String contextPath) {

	List<Clpermmenu> list = this.findSubMenu("", WebConstants.SOFTWARE_TT, codiceResponsabile, true);
	Collections.sort(list, new ClpermenuComparator());
	StringBuilder menu = new StringBuilder("<ul class=\"sf-menu\">");
	boolean isVerticalizzazioneAttiva = true;
	for (int i = 0; i < list.size(); i++) {
	    Clpermmenu clpermmenu = list.get(i);
	    Clmenu clmenu = clpermmenu.getMenu();
	    isVerticalizzazioneAttiva = true;
	    if (StringUtils.isNotBlank(clmenu.getVerticalizzazione())) {
		isVerticalizzazioneAttiva = verticalizzazioniService.isAttiva(clmenu.getVerticalizzazione());
	    }
	    if (isVerticalizzazioneAttiva) {
		String hrefAction = getSubMenuHref(clpermmenu, contextPath);
		List<Clpermmenu> submenuList = this.findSubMenu(clmenu.getMenulink(), clpermmenu.getSoftware().getCodice(), codiceResponsabile, true);
		if (submenuList.isEmpty()) {
		    menu.append("<li><a href=\"").append(hrefAction).append("\">").append(decodeDESCRIZIONE(clpermmenu)).append("</a></li>");
		} else {
		    String id = "m" + clmenu.getMenulink() + "_" + clpermmenu.getSoftware().getCodice();
		    hrefAction = "getSubMenu('" + id + "')";
		    menu.append("<li><a href=\"javascript: ").append(hrefAction).append("\">").append(decodeDESCRIZIONE(clpermmenu))
			    .append("</a><ul id=\"").append(id).append("\" class=\"sf-sub-menu-").append(id)
			    .append("\"><li><a href=\"#\" class=\"loading\" onmouseover=\"").append(hrefAction)
			    .append("\">Visualizza il sottomenu&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li></ul></li>");
		}
	    }
	}
	menu.append("</ul>");
	return menu.toString();
    }

    private String getSubMenuHref(Clpermmenu menu, String contextPath) {

	String returnToValueEncoded = "";
	try {
	    // FIXME BASE_URL
	    returnToValueEncoded = URLEncoder.encode(BackofficeNETConstants.getBASE_URL() + contextPath + "/", "UTF-8");
	} catch (UnsupportedEncodingException e1) {
	    e1.printStackTrace();
	}
	boolean isEnterprise = isEnterprise();
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

	List<Clpermmenu> clmenuItems = this.findSubMenu(menuLink, software, codiceresponsabile, true);
	Collections.sort(clmenuItems, new ClpermenuComparator());
	StringBuilder submenu = new StringBuilder("");
	boolean isVerticalizzazioneAttiva = true;
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
		    String hrefAction = getSubMenuHref(item, contextPath);
		    List<Clpermmenu> submenuList = this.findSubMenu(menu.getMenulink(), item.getSoftware().getCodice(), codiceresponsabile, true);
		    if (submenuList.isEmpty()) {
			submenu.append("<li><a href=\"").append(hrefAction).append("\">").append(decodeDESCRIZIONE(item)).append("</a></li>");
		    } else {
			String id = "m" + menu.getMenulink() + "_" + item.getSoftware().getCodice();
			hrefAction = "getSubMenu('" + id + "')";
			submenu.append("<li><a href=\"javascript: ").append(hrefAction).append("\">").append(decodeDESCRIZIONE(item))
				.append("</a><ul id=\"").append(id).append("\" class=\"sf-sub-menu-").append(id)
				.append("\"><li><a href=\"#\" class=\"loading\" onmouseover=\"").append(hrefAction)
				.append("\">Visualizza il sottomenu&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li></ul></li>");
		    }
		}
	    }
	}
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
