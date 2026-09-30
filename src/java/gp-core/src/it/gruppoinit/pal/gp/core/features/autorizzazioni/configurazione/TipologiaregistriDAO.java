package it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;
import it.gruppoinit.pal.gp.core.features.autorizzazioni.configurazione.TipologiaregistriService.TIPO_RICERCA;

import java.util.List;

public interface TipologiaregistriDAO extends BaseDAO<Tipologiaregistri, PkId> {

    public List<Tipologiaregistri> findByDescrizione(Tipologiaregistri entity, String codicecomune, TIPO_RICERCA tipoRicerca);

    /**
     * Restituisce le Tipologie di registri (filtrando per idcomune e software) ordinandole per il campo descrizione
     */
    public List<Tipologiaregistri> findAll(Integer firstResult, Integer maxResult);
}
