package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Softwareattivi;
import it.gruppoinit.pal.gp.core.domain.SoftwareattiviId;

import java.util.List;

/**
 * 
 * @author fabrizioc
 */
public interface SoftwareattiviDAO extends BaseDAO<Softwareattivi, SoftwareattiviId> {

    /**
     * metodo per il recupero di tutti i software attivi per idcomune.<br />
     * ordinati per software.descrizione ASC
     * 
     */
    public List<Softwareattivi> findAll(Integer firstResult, Integer maxResult);
}
