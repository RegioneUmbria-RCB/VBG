package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.AlberoprocAteco;
import it.gruppoinit.pal.gp.core.domain.AlberoprocAtecoId;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface AlberoprocAtecoDAO extends BaseDAO<AlberoprocAteco, AlberoprocAtecoId> {

    /**
     * Lista degli AlberoProc Ateco filtrati per idcomune e ordinati per il codice Ateco ASC
     */
    public List<AlberoprocAteco> findAll(Integer firstResult, Integer maxResult);
}
