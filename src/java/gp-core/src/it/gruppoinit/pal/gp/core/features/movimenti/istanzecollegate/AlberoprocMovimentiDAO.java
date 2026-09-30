package it.gruppoinit.pal.gp.core.features.movimenti.istanzecollegate;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.AlberoprocMovimenti;
import it.gruppoinit.pal.gp.core.domain.PkId;

public interface AlberoprocMovimentiDAO extends BaseDAO<AlberoprocMovimenti, PkId> {

    public List<AlberoprocMovimenti> findByAlberoproc(Integer codiceAlberoproc);
}
