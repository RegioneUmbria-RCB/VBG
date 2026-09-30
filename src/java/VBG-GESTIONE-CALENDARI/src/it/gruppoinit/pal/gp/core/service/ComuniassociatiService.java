package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatiDAO;
import it.gruppoinit.pal.gp.core.domain.Comuniassociati;
import it.gruppoinit.pal.gp.core.domain.ComuniassociatiId;

import java.util.List;

public interface ComuniassociatiService extends BaseService<Comuniassociati, ComuniassociatiId> {

    /**
     * @see ComuniassociatiDAO#findAll()
     * @param idcomune
     * @return
     */
    public List<Comuniassociati> findAll();

    public List<Comuniassociati> findAllImportAutomatico();

    public List<Comuniassociati> findByDescrizione(String comune);
}
