package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.CommissioniedilizieRDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieR;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class CommissioniedilizieRDAOImpl extends BaseDAOImpl<CommissioniedilizieR, PkId> implements CommissioniedilizieRDAO {

    @Override
    public Class<CommissioniedilizieR> getEntityClass() {

	return CommissioniedilizieR.class;
    }

    @Override
    public List<CommissioniedilizieR> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }

    @Override
    public Integer maxOrdineByCommissioniedilizieT(CommissioniedilizieT commissioniedilizieT) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("commissioniedilizieT", commissioniedilizieT));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("ordine"));
	criteria.setProjection(projectionList);
	@SuppressWarnings("unchecked")
	List<Integer> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty() && list.get(0) != null) {
	    return list.get(0);
	}
	return 0;
    }

    @Override
    public void delete(CommissioniedilizieR entity) {

	Session session = getSession();
	String sql = "delete from commissioniedilizie_r where idcomune=? and id=?";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, entity.getId().getCodice(), new IntegerType());
	q.executeUpdate();
    }

    @Override
    public Set<Integer> findCodiciCommissioniByMovimento(Integer codiceMovimento) {

	Set<Integer> ret = new HashSet<Integer>();
	Session session = getSession();
	String sql = "SELECT codicecommissione FROM commissioniedilizie_r WHERE idcomune=?  AND CODICEMOVIMENTO=?  GROUP BY CODICECOMMISSIONE";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, codiceMovimento);
	q.addScalar("codicecommissione", Hibernate.INTEGER);
	List<Integer> list = q.list();
	if (list.size() > 0) {
	    ret.addAll(list);
	}
	return ret;
    }

    @Override
    public List<CommissioniedilizieT> findCommissioniByIstanza(Integer codiceIstanza) {

	Session session = getSession();
	String sql = "SELECT codicecommissione FROM movimenti INNER JOIN commissioniedilizie_r ON " + //
		     " commissioniedilizie_r.idcomune=movimenti.idcomune AND " + // 
		     " commissioniedilizie_r.codicemovimento=movimenti.codicemovimento " + //
		     " WHERE movimenti.idcomune=?  AND movimenti.codiceistanza=? " + //
		     " GROUP BY CODICECOMMISSIONE";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, codiceIstanza);
	q.addScalar("codicecommissione", Hibernate.INTEGER);
	List<Integer> list = q.list();
	List<CommissioniedilizieT> ret = new ArrayList<CommissioniedilizieT>();
	for (Integer codicecommissione : list) {
	    ret.add(getById(CommissioniedilizieT.class, codicecommissione));
	}
	return ret;
    }

    @Override
    public int countCommissioniByIstanza(Integer codiceIstanza) {

	Session session = getSession();
	String sql = "SELECT count(*) as conta FROM movimenti INNER JOIN commissioniedilizie_r ON " + //
		     " commissioniedilizie_r.idcomune=movimenti.idcomune AND " + // 
		     " commissioniedilizie_r.codicemovimento=movimenti.codicemovimento " + //
		     " WHERE movimenti.idcomune=?  AND movimenti.codiceistanza=? " + //
		     " GROUP BY CODICECOMMISSIONE";
	SQLQuery q = session.createSQLQuery(sql).addSynchronizedEntityClass(CommissioniedilizieR.class);
	q.setParameter(0, ORMHelper.getIdcomune(), new StringType());
	q.setParameter(1, codiceIstanza);
	q.addScalar("conta", Hibernate.INTEGER);
	List<Integer> list = q.list();
	if (list.size() > 0) {
	    return list.get(0);
	}
	return 0;
    }
}
