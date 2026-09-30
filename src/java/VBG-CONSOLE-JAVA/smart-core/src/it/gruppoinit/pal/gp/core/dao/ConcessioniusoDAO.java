package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Concessioniuso;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author Riccardo Bocci
 */
public interface ConcessioniusoDAO extends BaseDAO<Concessioniuso, PkId> {

    /**
     * Torna la lista delle concessioni uso di un modulo software ordinate per descrizione dalla A alla Z
     * 
     */
    public List<Concessioniuso> findAll(Integer firstResult, Integer maxResult);

    /**
     * Restituisce una lista di concessioni uso filtrare per oggetto concessioni uso e ordinandole per il campo
     * descrizione (il filtro applicato sarà dato solo da in campi settati all'interno dell'oggetto filter)
     * 
     */
    public List<Concessioniuso> findByFilter(Concessioniuso entity);
}
