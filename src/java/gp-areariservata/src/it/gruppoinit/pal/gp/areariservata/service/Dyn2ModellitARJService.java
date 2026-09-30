package it.gruppoinit.pal.gp.areariservata.service;

import it.gruppoinit.pal.gp.areariservata.domain.SchedaHelper;
import it.gruppoinit.pal.gp.core.domain.helper.ModellidinamiciHelper;
import it.gruppoinit.pal.gp.core.service.Dyn2ModellitService;

public interface Dyn2ModellitARJService extends Dyn2ModellitService {

    public ModellidinamiciHelper populateScheda(SchedaHelper schedaH);
    
}
