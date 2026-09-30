/**
 * 
 */
package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Clpermmenu;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * @author francescop
 * 
 */
public interface ClpermmenuService extends BaseService<Clpermmenu, PkId> {

    public String findMenuPrimoLivello(Integer codiceResponsabile, String contextPath);

    public String findMenuSottoLivelli(Integer codiceResponsabile, String menuId, String contextPath);

    public String findPushMenuSottoLivelli(Integer codiceResponsabile, String menuId, String contextPath, boolean useV2Link);

    public Clpermmenu findByOperatoreAndClMenuAndSoftware(Integer codiceOperatore, Integer codiceClmenu, String codiceSoftware);

    public List<Clpermmenu> findByOperatore(Integer codiceResponsabile);
}
