package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.VwAlberoprocStp;
import it.gruppoinit.pal.gp.core.domain.VwAlberoprocStpId;

import java.util.List;

public interface VwAlberoprocStpService extends BaseService<VwAlberoprocStp, VwAlberoprocStpId> {

    public List<VwAlberoprocStp> findByCodiceStp(String idProcedimento);
}
