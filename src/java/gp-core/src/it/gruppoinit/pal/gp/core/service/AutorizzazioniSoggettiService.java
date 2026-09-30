package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSoggettiDAO;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSoggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface AutorizzazioniSoggettiService extends BaseService<AutorizzazioniSoggetti, PkId> {

    /**
     * @see AutorizzazioniSoggettiDAO#findAll(Integer, Integer)
     */
    public List<AutorizzazioniSoggetti> findAll(Integer firstResult, Integer maxResult);

    public List<AutorizzazioniSoggetti> findByAutorizzazione(Integer codiceAut);

    public AutorizzazioniSoggetti findByAutorizzazioneAndAnagrafe(Integer idAutorizzazione, Integer codiceAnagrafe);
}
