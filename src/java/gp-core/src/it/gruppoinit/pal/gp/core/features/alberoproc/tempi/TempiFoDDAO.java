package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TempiFoD;

public interface TempiFoDDAO extends BaseDAO<TempiFoD, PkId> {

    void deleteByIdTestata(Integer idTempot);
}
