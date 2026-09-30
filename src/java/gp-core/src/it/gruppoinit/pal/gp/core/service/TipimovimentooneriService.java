package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.dao.TipimovimentooneriDAO;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimentooneri;
import it.gruppoinit.pal.gp.core.domain.TipimovimentooneriId;

import java.util.List;

/**
 * 
 * @author
 */
public interface TipimovimentooneriService extends BaseService<Tipimovimentooneri, TipimovimentooneriId> {

    /**
     * @see TipimovimentooneriDAO#findAll(Integer, Integer)
     */
    public List<Tipimovimentooneri> findAll(Integer firstResult, Integer maxResult);

    public List<Tipimovimentooneri> findByTipimovimento(String tipimovimento);

    /**
     * Ritorna un tipo movimenti oneri filtrato per tipo movimento e tipi causale onere, se non sono presenti record
     * ritorna null
     * 
     * @param tipomovimento
     * @param tipicausalioneri
     * @return
     */
    public Tipimovimentooneri findByTipomovimentoAndCausaleOnere(Tipimovimento tipomovimento, Tipicausalioneri tipicausalioneri);
}
