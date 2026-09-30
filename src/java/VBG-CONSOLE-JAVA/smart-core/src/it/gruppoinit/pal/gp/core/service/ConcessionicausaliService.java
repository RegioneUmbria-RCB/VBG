package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.ConcessionicausaliDAO;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface ConcessionicausaliService extends BaseService<Concessionicausali, PkId> {

    /**
     * @see ConcessionicausaliDAO#findAll(Integer, Integer)
     */
    public List<Concessionicausali> findAll(Integer firstResult, Integer maxResult);

    public List<Concessionicausali> findByDescrizioneAndFlagStorico(Concessionicausali entity);

    public List<Concessionicausali> findAllbyCausaleStorico(boolean isStorico);
}
