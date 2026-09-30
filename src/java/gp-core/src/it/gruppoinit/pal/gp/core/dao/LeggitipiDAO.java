package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface LeggitipiDAO extends BaseDAO<Leggitipi, PkId> {

    /**
     * Ritorna la lista delle legge tipi (filtrato per idcomune) e ordinata per il campo ltDescrizione
     * 
     */
    public List<Leggitipi> findAll(Integer firstResult, Integer maxResult);

    public List<Leggitipi> findByFilter(Leggitipi entity);
}
