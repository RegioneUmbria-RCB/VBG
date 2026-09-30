package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Ateco;

import java.util.List;

/**
 * 
 * @author Luca Proietti
 */
public interface AtecoDAO extends BaseDAO<Ateco, Integer> {

    /**
     * recupera tutti gli ateco (senza filtro per idcomune) ordinandoli per codice
     */
    public List<Ateco> findAll(Integer firstResult, Integer maxResult);
}
