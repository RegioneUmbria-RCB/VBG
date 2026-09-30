package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Clmenu;

import java.util.List;

public interface ClmenuDAO extends BaseDAO<Clmenu, Integer> {

    /**
     * recupera tutti i menu tramite: <code>findByExample(new Clmenu())</code>
     */
    public List<Clmenu> findAll(Integer firstResult, Integer maxResult);

    /**
     * recupera il primo livello del menu tramite la query hql:<br />
     * <code>FROM Clmenu _clmenu WHERE length(_clmenu.menulink) = ? order by _clmenu.menulink asc</code>
     * 
     * @return
     */
    public List<Clmenu> findFirstLevelMenu();

    /**
     * recupera il sottolivello del menu tramite la query hql:<br />
     * <code>FROM Clmenu _clmenu WHERE  length(_clmenu.menulink) = ? and _clmenu.menulink like ? order by _clmenu.menulink asc</code>
     * 
     * @param parentLevelCode
     *            clmenu.menulink like 'parentLevelCode%'
     * 
     * @param length
     *            length(_clmenu.menulink) = length
     * @return
     */
    public List<Clmenu> findMenu(String parentLevelCode, int length);

    /**
     * recupera la lista dei menu che possono essere abilitati per un responsabile per il software fornito in input, la
     * lista è ordinata per il campo menulink ASC<br />
     * Query hql:<br />
     * <code>FROM Clmenu _clmenu WHERE length(_clmenu.menulink) = ? or _clmenu.software in (?,?) order by _clmenu.menulink asc</code>
     * 
     * @param pSoftware
     * @param v2 
     */
    public List<Clmenu> findTreeBySoftware(String pSoftware, boolean v2);
}
