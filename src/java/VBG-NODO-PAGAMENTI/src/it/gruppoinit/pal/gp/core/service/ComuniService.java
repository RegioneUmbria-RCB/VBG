package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;

import java.util.List;

public interface ComuniService extends BaseService<Comuni, String> {

    /**
     * @see ComuniDAO#findByDescrizione(String comune)
     */
    public List<Comuni> findByDescrizione(String comune);

    public List<Comuni> findByDescrizione(String comune, boolean escludiStatiEsteri);

    public Comuni findByCodiceComune(Comuni entity);

    public Comuni findByComune(Comuni comuni);

    public Comuni findByCodiceIstat(String comuneresidenza);
}
