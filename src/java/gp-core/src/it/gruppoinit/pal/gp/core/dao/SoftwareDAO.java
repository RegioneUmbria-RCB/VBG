package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Software;

import java.util.List;

public interface SoftwareDAO extends BaseDAO<Software, String> {

    /**
     * metodo per la ricerca dei software tramite like sulla proprietà descrizione
     * 
     * @param entity
     * @return
     */
    List<Software> findByFilter(Software entity);

    List<Software> findSoftwareAbilitati(Responsabili responsabile, boolean escludiNonOpzionali);

    List<Software> findSoftwareAttivi(boolean frontoffice);
}
