package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocFoTopDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocFoTop;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

import java.util.List;

/**
 * 
 * @author
 */
public interface AlberoprocFoTopService extends BaseService<AlberoprocFoTop, PkId> {

    /**
     * @see AlberoprocFoTopDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocFoTop> findAll(Integer firstResult, Integer maxResult);

    public List<IdentificativoDescrizioneBean> findInterventi(Integer firstResult, Integer maxResult);
}
