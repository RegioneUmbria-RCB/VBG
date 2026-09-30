package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempiFoT;

public interface TempiFoTDAO extends BaseDAO<TempiFoT, PkId> {

    void deleteById(Integer idTempot);
}
