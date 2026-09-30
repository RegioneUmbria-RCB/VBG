package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.ComuniItaliani;

import java.util.List;

public interface ComuniItalianiDAO extends BaseDAO<ComuniItaliani, String> {

    /**
     * ricerca tramite like sulla colonna COMUNE
     * 
     * @param entity
     * @return
     */
    public List<ComuniItaliani> findByDescrizione(String comune);

    public ComuniItaliani findByCodiceComune(ComuniItaliani entity);
}
