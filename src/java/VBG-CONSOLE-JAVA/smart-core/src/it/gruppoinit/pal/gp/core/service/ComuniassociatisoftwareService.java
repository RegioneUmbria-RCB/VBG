package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ComuniassociatisoftwareDAO;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.Comuniassociatisoftware;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author gianpaolot
 */
public interface ComuniassociatisoftwareService extends BaseService<Comuniassociatisoftware, PkId> {

    /**
     * @see ComuniassociatisoftwareDAO#findAll(Integer, Integer)
     */
    public List<Comuniassociatisoftware> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see ComuniassociatisoftwareDAO#findByComune(Comuni comuni)
     */
    public Comuniassociatisoftware findByComune(Comuni comuni);
}
