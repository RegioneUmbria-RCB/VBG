package it.gruppoinit.pal.gp.core.features.alberoproc.tempi;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTempi;
import it.gruppoinit.pal.gp.core.domain.AlberoprocTempiId;

public interface AlberoprocTempiDAO extends BaseDAO<AlberoprocTempi, AlberoprocTempiId> {

    List<AlberoprocTempi> findByAlberoProcId(Integer codiceIntervento);

    void deleteById(Integer codiceIntervento, Integer codiceTempoFo);

    boolean existByIdTempoFoT(Integer codiceTempoFo);
}
