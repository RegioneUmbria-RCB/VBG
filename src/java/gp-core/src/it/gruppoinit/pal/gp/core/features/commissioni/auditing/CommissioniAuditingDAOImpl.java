package it.gruppoinit.pal.gp.core.features.commissioni.auditing;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.CommissioniEdilizieLog;
import it.gruppoinit.pal.gp.core.filters.FilterRestriction;
import it.gruppoinit.pal.gp.core.filters.FilterTable;
import it.gruppoinit.pal.gp.core.filters.FilterUtils;

@SuppressWarnings("rawtypes")
@Repository
public class CommissioniAuditingDAOImpl extends BaseDAOImpl implements ICommissioniAuditingDAO {

    @Override
    public Class<CommissioniEdilizieLog> getEntityClass() {

	return CommissioniEdilizieLog.class;
    }

    @Override
    public List<CommissioniEdilizieLog> findByCodiceCommissione(Integer codiceCommissione) {

	FilterTable ft = new FilterTable(DAOEnum.FIND_BY_IDCOMUNE);
	FilterRestriction fr = new FilterRestriction();
	fr.addFilterField(FilterUtils.equals("id.codice", codiceCommissione, "commissioniedilizieT", Integer.class));
	ft.addRestriction(fr);
	//ft.addOrder(FilterUtils.orderAsc("id.codice"));
	ft.addOrder(FilterUtils.orderDesc("data"));
	return findByFilterTable(ft);
    }

    @Override
    public void deleteByIdCommissione(Integer idCommissione) {

	Session session = getSession();
	String sql = "delete from commissioni_edilizie_log where idcomune=? and fk_commissione=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniEdilizieLog.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, idCommissione, new IntegerType());
	q.executeUpdate();
    }
}
