package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipimodelliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimodelli;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipimodelliService extends BaseService<Tipimodelli, PkId> {

    /**
     * @see TipimodelliDAO#findAll(Integer, Integer)
     */
    public List<Tipimodelli> findAll(Integer firstResult, Integer maxResult);

    /**
     * Ritorna una lista di mosulistica filtrando (ilike) per descrzione e ordinanado per descrizione
     * 
     * @param textToSearch
     * @param object
     * @param object2
     * @return
     */
    public List<Tipifamiglieendo> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult);

    public List<Tipimodelli> findBySoftwareAndModulo(String codicesoftware);
}
