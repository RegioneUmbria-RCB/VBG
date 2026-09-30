package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanze;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeIstanzeId;
import it.gruppoinit.pal.gp.core.service.BaseService;

public interface RicalcoloAreeIstanzeService extends BaseService<RicalcoloAreeIstanze, RicalcoloAreeIstanzeId> {
    
    List<String> getTestateDaRicalcolare();
}
