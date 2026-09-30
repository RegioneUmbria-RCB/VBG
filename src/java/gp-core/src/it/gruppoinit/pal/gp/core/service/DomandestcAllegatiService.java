package it.gruppoinit.pal.gp.core.service;

import it.gruppoinit.pal.gp.core.domain.DomandestcAllegati;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

public interface DomandestcAllegatiService extends BaseService<DomandestcAllegati, PkId> {

    /**
     * Trova tutti gli allegati legati ad una domandastc
     * 
     * @param codicedomandaStc
     * @return
     */
    public List<DomandestcAllegati> findByDomandestc(Integer codicedomandaStc);
}
