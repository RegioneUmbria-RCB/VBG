package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentisoftwareDAO;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface InventarioprocedimentisoftwareService extends BaseService<Inventarioprocedimentisoftware, PkId> {

    /**
     * @see InventarioprocedimentisoftwareDAO#findAll(Integer, Integer)
     */
    public List<Inventarioprocedimentisoftware> findAll(Integer firstResult, Integer maxResult);

    public Inventarioprocedimentisoftware findByEndoAndSoftware(Inventarioprocedimenti endo, Software software);

    /**
     * Torna la lista di inventarioprocedimentisoftware dove TIPOMOVIMENTO=tipomovimento ordinati per
     * INVENTARIOPROCEDIMENTI.ORDINE, INVENTARIOPROCEDIMENTI.PROCEDIMENTO
     * 
     * @param tipomovimento
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Inventarioprocedimentisoftware> findByTipimovimento(String tipomovimento, Integer firstResult, Integer maxResult);

    /**
     * Ordinati per software.ordine, software.descrizione
     * 
     * @param codiceendo
     * @return
     */
    public List<Inventarioprocedimentisoftware> findByCodiceInventarioprocedimenti(Integer codiceendo);

    public List<Inventarioprocedimentisoftware> findByEndoprocedimentiAndSoftware(Integer codiceinventario, String software);

    /**
     * Torna la lista degli Inventarioprocedimentisoftware di un'Amministrazione
     * 
     * @param codiceAmministrazione
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Inventarioprocedimentisoftware> findByAmministrazioni(Integer codiceAmministrazione, Integer firstResult, Integer maxResult);
}
