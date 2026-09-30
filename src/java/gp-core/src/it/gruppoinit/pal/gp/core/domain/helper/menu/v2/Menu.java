package it.gruppoinit.pal.gp.core.domain.helper.menu.v2;

import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.utils.Utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Menu {

    public static void main(String[] args) throws Exception {

	//	Map<String, ClmenuBean> dic = new LinkedHashMap<String, ClmenuBean>();
	//	Map<Integer, ClmenuBean> mapPermessi = new LinkedHashMap<Integer, ClmenuBean>();
	//	Set<Software> softwares = new LinkedHashSet<Software>();
	//	//	softwares.add(new Software("TT", "Tutti", 0));
	//	//	softwares.add(new Software("CE", "Edilizia", 1));
	//	//	softwares.add(new Software("CO", "Commercio", 2));
	//	//	softwares.add(new Software("SS", "Sportello unico", 3));
	//	//	softwares.add(new Software("DM", "Demanio marittimo", 4));
	//	// connect
	//	//	BaseDAO dao = new BaseDAO();
	//	//	Connection c = dao.getConnection();
	//	//	//	String query = ("select decode(software,'*', 'SS,CO,CE',software) as software,  clmenu_java.id, clmenu_java.descrizione, "
	//	//	//		+ "clmenu_java.pagina,menulink,jsp,verticalizzazione, softwareesclusi, link_standard,tipo_funzionalita, layouttesti from "
	//	//	//		+ "clmenu_java order by menulink asc"); ///* where menulink like '0%' */
	//	//	String query = ("select * from clmenu_java_v3 where not (menulink like '9%' or menulink like 'B%') order by menulink asc");
	//	//	String queryPermMenu = "SELECT * FROM CLPERMMENU  WHERE clpermmenu.idcomune ='E256' AND CLPERMMENU.CODICERESPONSABILE=8 ";
	//	//	QueryRunner queryRunner = new QueryRunner();
	//	//	ResultSetHandler<List<ClmenuBean>> h = new BeanListHandler(ClmenuBean.class);
	//	//	ResultSetHandler<List<ClpermmenuBean>> perm = new BeanListHandler(ClpermmenuBean.class);
	//	List<ClmenuBean> list = new ArrayList<ClmenuBean>();
	//	List<ClpermmenuBean> permessi = new ArrayList<ClpermmenuBean>();
	//	//	try {
	//	//	    list = queryRunner.query(c, query, h);
	//	//	    permessi = queryRunner.query(c, queryPermMenu, perm);
	//	//	} catch (SQLException e) {
	//	//	    e.printStackTrace();
	//	//	}
	//	List<MenuHelper> menus = new ArrayList<MenuHelper>();
	//	for (ClmenuBean clmenuBean : list) {
	//	    if (clmenuBean.getMenulink().length() == 1) {
	//		MenuHelper h1 = new MenuHelper(clmenuBean, softwares);
	//		menus.add(h1);
	//	    }
	//	    mapPermessi.put(clmenuBean.getId(), clmenuBean);
	//	    dic.put(clmenuBean.getMenulink(), clmenuBean);
	//	    String idPadre = clmenuBean.getIdPadre();
	//	    if (null != idPadre) {
	//		ClmenuBean padre = dic.get(idPadre);
	//		if (padre == null) {
	//		    System.out.println("");
	//		}
	//		padre.getChilds().add(clmenuBean);
	//	    }
	//	}
	//	for (ClpermmenuBean clpermmenuBean : permessi) {
	//	    ClmenuBean mnu = mapPermessi.get(clpermmenuBean.getFkidmenu());
	//	    if (mnu != null) {
	//		mnu.aggiungiSoftware(clpermmenuBean.getSoftware());
	//	    }
	//	}
	//	for (MenuHelper mh : menus) {
	//	    ClmenuBean s = dic.get(mh.getMenulink());
	//	    mh.aggiungiFigli(s.getChilds());
	//	}
	//	Utilities.marshallObject(MenuHolder.class);
	// disconnect
	// c.close();
    }
}
