package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.ComuniDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.service.helper.ComuniDTO;

public interface ComuniService extends BaseService<Comuni, String> {

    /**
     * @see ComuniDAO#findByDescrizione(String comune)
     */
    public List<Comuni> findByDescrizione(String comune);

    public List<Comuni> findByDescrizione(String comune, boolean escludiStatiEsteri);

    //Perché????
    public Comuni findByCodiceComune(Comuni entity);

    public Comuni findByComune(Comuni comuni);

    public ComuniDTO comuniToDTO(Comuni c);

    public Comuni findByCodiceIstat(String comuneresidenza);
}
