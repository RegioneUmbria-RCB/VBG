package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.CommissioniedilizieTDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.CommissioniedilizieT;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CommissioniTHelper;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class CommissioniedilizieTDAOImpl extends BaseDAOImpl<CommissioniedilizieT, PkId> implements CommissioniedilizieTDAO {

    @Override
    public Class<CommissioniedilizieT> getEntityClass() {

	return CommissioniedilizieT.class;
    }

    @Override
    public List<CommissioniedilizieT> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "data", DAOOrderTypeEnum.DESC);
    }

    @Override
    public CommissioniTHelper findByCodiceIstanza(Integer codiceIstanza) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	String sql = "select "//
		     + "commissioniedilizie_t.data as data, "//
		     + "commissioniedilizie_t.numprotocollo as numero "//
		     + "from "//
		     + "commissioniedilizie_t "//
		     + "inner join commissioniedilizie_r on commissioniedilizie_t.idcomune = commissioniedilizie_r.idcomune "//
		     + "and commissioniedilizie_t.codicecommissione = commissioniedilizie_r.codicecommissione "//
		     + "inner join movimenti on commissioniedilizie_r.idcomune = movimenti.idcomune "//
		     + "and commissioniedilizie_r.codicemovimento = movimenti.codicemovimento "//
		     + "where "//
		     + "movimenti.idcomune = ? " //
		     + "and   movimenti.codiceistanza = ? " //
		     + " order by " + "movimenti.data desc";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceIstanza);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("numero", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(CommissioniTHelper.class));
	List<CommissioniTHelper> list = (List<CommissioniTHelper>) q.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
}
