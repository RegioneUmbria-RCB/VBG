package it.gruppoinit.pal.gp.core.features.commissioni.auditing;

import java.util.List;

import it.gruppoinit.pal.gp.core.dao.BaseDAO;
import it.gruppoinit.pal.gp.core.domain.CommissioniEdilizieLog;

@SuppressWarnings("rawtypes")
public interface ICommissioniAuditingDAO extends BaseDAO {

    public List<CommissioniEdilizieLog> findByCodiceCommissione(Integer codiceCommissione);

    public void deleteByIdCommissione(Integer idCommissione);
}
