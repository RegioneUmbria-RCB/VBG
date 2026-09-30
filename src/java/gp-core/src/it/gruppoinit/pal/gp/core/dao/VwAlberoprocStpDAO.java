package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.domain.VwAlberoprocStp;
import it.gruppoinit.pal.gp.core.domain.VwAlberoprocStpId;

import java.util.List;

public interface VwAlberoprocStpDAO extends BaseDAO<VwAlberoprocStp, VwAlberoprocStpId> {

    public List<VwAlberoprocStp> findByCodiceStp(String idProcedimento);

    @Override
    /**
     * Trova tutti i record di un determinato modulo software 
     *
     */
    public List<VwAlberoprocStp> findAll(Integer firstResult, Integer maxResult);
}
