package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.EquitaliatracciatoDDAO;
import it.gruppoinit.pal.gp.core.domain.EquitaliatracciatoD;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author
 */
public interface EquitaliatracciatoDService extends BaseService<EquitaliatracciatoD, PkId> {

    /**
     * @see EquitaliatracciatoDDAO#findAll(Integer, Integer)
     */
    public List<EquitaliatracciatoD> findAll(Integer firstResult, Integer maxResult);

    public List<EquitaliatracciatoD> findByTracciato(Integer codiceTracciato);
}
