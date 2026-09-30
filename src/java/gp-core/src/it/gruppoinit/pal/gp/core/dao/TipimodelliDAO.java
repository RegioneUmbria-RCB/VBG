package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimodelli;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipimodelliDAO extends BaseDAO<Tipimodelli, PkId> {

    public List<Tipimodelli> findAll(Integer firstResult, Integer maxResult);

    /**
     * 
     * @param textToSearch
     * @param firstResult
     * @param maxResult
     * @return
     */
    public List<Tipifamiglieendo> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult);

    /**
     * Il metodo ritorna quali tipi di modelli sono associati ai modelli per il software passato
     * 
     * @param codicesoftware
     * @return
     */
    public List<Tipimodelli> findBySoftwareAndModulo(String codicesoftware);
}
