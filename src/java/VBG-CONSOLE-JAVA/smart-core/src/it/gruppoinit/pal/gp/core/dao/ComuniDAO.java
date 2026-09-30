package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.Comuni;

public interface ComuniDAO extends BaseDAO<Comuni, String> {

    /**
     * ricerca tramite like sulla colonna COMUNE
     * 
     * @param entity
     * @return
     */
    public List<Comuni> findByDescrizione(String comune, int maxResults);

    public Comuni findByCodiceComune(Comuni entity);

    public List<Comuni> findComuniItalianiByDescrizione(String comune, int maxResults);
}
