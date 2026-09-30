package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface AnagrafeARJService extends BaseService<Anagrafe, PkId> {

    public Anagrafe findPFByCF(String cf);

    public Anagrafe findPGByCFoPI(String cfOpi);
    
    public Anagrafe findByUserId(String userid);
    
}
