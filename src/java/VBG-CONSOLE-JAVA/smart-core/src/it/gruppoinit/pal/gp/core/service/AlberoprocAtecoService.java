package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.AlberoprocAtecoDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAtecoId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface AlberoprocAtecoService extends BaseService<AlberoprocAteco, AlberoprocAtecoId> {

    /**
     * @see AlberoprocAtecoDAO#findAll(Integer, Integer)
     */
    public List<AlberoprocAteco> findAll(Integer firstResult, Integer maxResult);
    
    public List<AlberoprocAteco> findByAlberoproc(Alberoproc entity);
}
