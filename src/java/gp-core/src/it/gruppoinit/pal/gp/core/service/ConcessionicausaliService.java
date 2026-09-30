package it.gruppoinit.pal.gp.core.service;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.ConcessionicausaliDAO;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface ConcessionicausaliService extends BaseService<Concessionicausali, PkId> {

    /**
     * @see ConcessionicausaliDAO#findAll(Integer, Integer)
     */
    public List<Concessionicausali> findAll(Integer firstResult, Integer maxResult);

    public List<Concessionicausali> findByDescrizioneAndFlagStorico(Concessionicausali entity);

    public List<Concessionicausali> findAllbyCausaleStorico(boolean isStorico);

    /**
     * Restituisce la lista delle concessioni causali con il flag affitto uguale a true
     * 
     * @param concessionicausali
     * @return
     */
    public List<Concessionicausali> findAllByAffitto(Concessionicausali concessionicausali);

    public boolean isAffitto(Integer codiceConcCausale);
}
