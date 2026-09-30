package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.util.List;

public interface ComuniDAO extends BaseDAO<Comuni, String> {

    /**
     * ricerca tramite like sulla colonna COMUNE
     * 
     * @param comune
     * @return
     */
    public List<Comuni> findByDescrizione(String comune);

    /**
     * ricerca tramite equal su colonna CODICECOMUNE
     * 
     * @param entity
     * @return
     */
    public Comuni findByCodiceComune(Comuni entity);
}
