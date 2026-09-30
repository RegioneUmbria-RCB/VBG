package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiarchivioistanze;

import java.util.List;

public interface TipiarchivioistanzeDAO extends BaseDAO<Tipiarchivioistanze, PkId> {

    /**
     * Restituisce gli archivi istanze (filtrando per idcomune e software) ordinandole per il campo archivio
     */
    public List<Tipiarchivioistanze> findAll(Integer firstResult, Integer maxResult);
}
