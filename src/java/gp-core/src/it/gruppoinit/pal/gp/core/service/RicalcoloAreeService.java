package it.gruppoinit.pal.gp.core.service;

import java.util.List;


import it.gruppoinit.pal.gp.core.domain.RicalcoloAree;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeId;

public interface RicalcoloAreeService extends BaseService<RicalcoloAree, RicalcoloAreeId>{
    
    public List<RicalcoloAree> getMonitorRicalcolaAree();
}
