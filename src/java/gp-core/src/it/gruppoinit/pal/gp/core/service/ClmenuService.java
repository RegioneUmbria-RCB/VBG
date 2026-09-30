package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ClmenuDAO;
import it.gruppoinit.pal.gp.core.domain.Clmenu;
import it.gruppoinit.pal.gp.core.domain.helper.menu.v2.MenuHolder;

import java.util.List;

public interface ClmenuService extends BaseService<Clmenu, Integer> {

    /**
     * @see ClmenuDAO#findFirstLevelMenu()
     * 
     * @return
     */
    public List<Clmenu> findFirstLevelMenu();

    /**
     * @see ClmenuDAO#findMenu(String parentLevelCode, int length)
     */
    public List<Clmenu> findMenu(String parentLevelCode, int length);

    /**
     * metodo per eliminare dalla cache i menu di un responsabile.
     * 
     * @param codiceResponsabile
     */
    public void removeMenuFromCache(Integer codiceResponsabile);

    /**
     * @param v2 
     * @see ClmenuDAO#findTreeBySoftware(String pSoftware)
     * 
     * @return
     */
    public List<Clmenu> findTreeBySoftware(String pSoftware, boolean v2);

    public MenuHolder getMenuV2(String contextPath, boolean useMenuLinkV2);

    void resetObjectCached();
}
