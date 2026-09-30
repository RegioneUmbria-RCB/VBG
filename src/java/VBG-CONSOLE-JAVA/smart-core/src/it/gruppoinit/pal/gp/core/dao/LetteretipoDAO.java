package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.Letteretipo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface LetteretipoDAO extends BaseDAO<Letteretipo, PkId> {

    /**
     * Restituisce tutte le Tipologie di Lettere e Documenti (filtrando per idcomune e software) ordinandole per il
     * campo descrizione ascendente
     */
    public List<Letteretipo> findAll(Integer firstResult, Integer maxResult);

    public List<Letteretipo> findByDescrizione(String descrizione, boolean includiDisabilitate);

    public List<Letteretipo> findByDescrizioneAndSoftware(String descrizione, String software, boolean includiDisabilitate);
}
