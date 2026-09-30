package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.util.List;

public interface ComuniService extends BaseService<Comuni, String> {

    /**
     * @see ComuniDAO#findByDescrizione(String comune)
     * @param comune
     * @return
     */
    public List<Comuni> findByDescrizione(String comune);

    /**
     * @see ComuniDAO#findByCodiceComune(Comuni)
     * @param entity
     * @return
     */
    public Comuni findByCodiceComune(Comuni entity);
}
