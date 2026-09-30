package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAree;
import it.gruppoinit.pal.gp.core.domain.RicalcoloAreeId;

public interface RicalcoloAreeDAO extends BaseDAO<RicalcoloAree, RicalcoloAreeId> {

    public String creaRicalcoloAreeIstanza();

    public List<RicalcoloAree> findAll(Integer firstResult, Integer maxResult);

    public List<RicalcoloAree> getMonitorRicalcolaAree();
}
