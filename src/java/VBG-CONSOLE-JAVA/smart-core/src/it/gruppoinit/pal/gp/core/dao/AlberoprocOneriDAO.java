package it.gruppoinit.pal.gp.core.dao;

import java.util.List;

import it.gruppoinit.pal.gp.core.domain.AlberoprocOneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author francescop
 */
public interface AlberoprocOneriDAO extends BaseDAO<AlberoprocOneri, PkId> {

    public List<AlberoprocOneri> findAllByAlberoproc(String idcomune, Integer codiceAlberoproc);
}
