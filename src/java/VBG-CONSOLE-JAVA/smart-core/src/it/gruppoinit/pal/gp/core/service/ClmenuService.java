package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ClmenuDAO;
import it.gruppoinit.pal.gp.core.domain.Clmenu;

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
     * @see ClmenuDAO#findTreeBySoftware(String pSoftware)
     * 
     * @return
     */
    public List<Clmenu> findTreeBySoftware(String pSoftware);
}
