package it.gruppoinit.pal.gp.core.dao;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.MovimentiTempistica;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author
 */
public interface MovimentiTempisticaDAO extends BaseDAO<MovimentiTempistica, PkId> {

    /**
     * non implementato
     * 
     * @throws new
     *             {@link NotImplementedException}
     */
    public List<MovimentiTempistica> findAll(Integer firstResult, Integer maxResult);

    public int findDurataProrogaPerIstanza(Integer codiceIstanza);

    public Date findDataUltimaInterruzione(Integer codiceIstanza);
}
