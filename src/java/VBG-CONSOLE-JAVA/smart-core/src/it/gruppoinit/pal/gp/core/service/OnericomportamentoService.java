package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.OnericomportamentoDAO;
import it.gruppoinit.pal.gp.core.domain.Onericomportamento;

import java.util.List;

/**
 * 
 * @author
 */
public interface OnericomportamentoService extends BaseService<Onericomportamento, Integer> {

    /**
     * @see OnericomportamentoDAO#findAll(Integer, Integer)
     */
    public List<Onericomportamento> findAll(Integer firstResult, Integer maxResult);

    /**
     * @see OnericomportamentoDAO#findByDescrizione(String descrizione)
     */
    public List<Onericomportamento> findByDescrizione(String descrizione);
}
