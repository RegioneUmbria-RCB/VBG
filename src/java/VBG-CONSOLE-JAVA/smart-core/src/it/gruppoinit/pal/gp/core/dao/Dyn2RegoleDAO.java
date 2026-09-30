/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Dyn2Regole;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * @author francol
 * 
 */
public interface Dyn2RegoleDAO extends BaseDAO<Dyn2Regole, PkId> {

    /**
     * Restituisce tutte le regole che effettuano delle verifiche sul valore del campo dinamico passato come argomento
     * 
     * @param idCampo
     * @param idModellot
     * @return
     */
    public List<Dyn2Regole> findByCampoDinamicoInModello(Integer idCampo, Integer idModellot);

    /**
     * Ricerca le regole filtrando per descrizione (ilike) e software
     * 
     * @param textToSearch
     * @param _codicesoftware
     * @return
     */
    public List<Dyn2Regole> findByDescrizioneAndSoftware(String textToSearch, String codicesoftware);
}
