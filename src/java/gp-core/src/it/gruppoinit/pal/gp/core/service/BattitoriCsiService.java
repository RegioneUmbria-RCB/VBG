package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.BattitoriCsiDAO;
import it.gruppoinit.pal.gp.core.domain.BattitoriCsi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface BattitoriCsiService extends BaseService<BattitoriCsi, PkId> {

    /**
     * @see BattitoriCsiDAO#findAll(Integer, Integer)
     */
    public List<BattitoriCsi> findAll(Integer firstResult, Integer maxResult);

    public List<BattitoriCsi> findByAutorizzazioniAndGiorno(Integer idAut, Integer idGiornoSettimana, Integer firstResult, Integer maxResults);

    public int countByAutorizzazioniAndGiorno(Integer idAut, Integer idGiornoSettimana);
}
