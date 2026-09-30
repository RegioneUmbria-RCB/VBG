package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.LdpDecodifiche;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface LdpDecodificheService extends BaseService<LdpDecodifiche, PkId> {

    /**
     * trova i record di un particolare contesto ordinati per dscrizione asc
     * 
     * @param contesto
     * @return
     */
    public List<LdpDecodifiche> findByContesto(String contesto);
}
