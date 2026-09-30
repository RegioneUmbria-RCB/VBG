package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.Infosuap;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

/**
 * 
 * @author lucap
 */
public interface InfosuapService extends BaseService<Infosuap, PkId> {

    /**
     * Ordinati per ordine, titolo asc
     */
    public List<Infosuap> findAll(Integer firstResult, Integer maxResult);
}
