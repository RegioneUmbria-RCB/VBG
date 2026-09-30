package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.util.List;

public interface ComuniDAO extends BaseDAO<Comuni, String> {

    /**
     * ricerca tramite like sulla colonna COMUNE
     * 
     * @param entity
     * @return
     */
    public List<Comuni> findByDescrizione(String comune);

    public List<Comuni> findByDescrizione(String comune, boolean escludiStatiEsteri);

    public Comuni findByCodiceComune(Comuni entity);
}
