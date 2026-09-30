/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author riccardob
 * 
 */
public interface ClpermmenuDAO extends BaseDAO<Clpermmenu, PkId> {

    public List<Clpermmenu> findSubMenu(String menuLink, String software, Integer codiceOperatore, boolean onlyOneSubLevel, boolean useMenuLinkV2);

    public Clpermmenu findByOperatoreAndClMenuAndSoftware(Integer codiceOperatore, Integer codiceClmenu, String codiceSoftware);
}
