package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.DehorsMqIstanzeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.DehorsAree;
import it.gruppoinit.pal.gp.core.domain.DehorsMqIstanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.DehorsMqIstanzeHelper;
import it.gruppoinit.pal.gp.core.service.DehorsAreeService;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class DehorsMqIstanzeDAOImpl extends BaseDAOImpl<DehorsMqIstanze, PkId> implements DehorsMqIstanzeDAO {

    private DehorsAreeService dehorsAreeService;

    @Autowired
    public void setDehorsAreeService(DehorsAreeService dehorsAreeService) {

	this.dehorsAreeService = dehorsAreeService;
    }

    @Override
    public Class<DehorsMqIstanze> getEntityClass() {

	return DehorsMqIstanze.class;
    }

    @Override
    public List<DehorsMqIstanze> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public DehorsMqIstanzeHelper findDehorsMqIstanzeHelper(Integer codiceArea) {

	//log.debug("findConcessioniListHelper: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	StringBuffer query = new StringBuffer();
	//Select
	query.append("SELECT DEHORS_AREE.MQDISPONIBILI AS MQDISPONIBILI,coalesce(SUM(DEHORS_MQ_ISTANZE.MQASSEGNATI),0) AS ASSEGNATI,(DEHORS_AREE.MQDISPONIBILI - coalesce(SUM(DEHORS_MQ_ISTANZE.MQASSEGNATI),0)) AS DISPONIBILI");
	// From
	query.append(" FROM DEHORS_AREE LEFT JOIN DEHORS_MQ_ISTANZE ON DEHORS_AREE.FK_CODICEAREA=DEHORS_MQ_ISTANZE.FK_AREE"
		+ " AND DEHORS_AREE.IDCOMUNE=DEHORS_MQ_ISTANZE.IDCOMUNE ");
	//Where
	query.append(" WHERE DEHORS_AREE.FK_CODICEAREA=? AND CESSATA=? AND DEHORS_MQ_ISTANZE.IDCOMUNE=? ");
	//Group by
	query.append(" GROUP BY DEHORS_AREE.MQDISPONIBILI");
	//SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	//	QueryConcessioniListHelper qih = new QueryConcessioniListHelper(sessimpl, vwConcessionilista, false);
	//	String sql = qih.buildQuery();
	SQLQuery q = getSession().createSQLQuery(query.toString());
	q.setParameter(0, codiceArea);
	q.setParameter(1, 0);
	q.setParameter(2, ORMHelper.getIdcomune());
	q.addScalar("mqdisponibili", Hibernate.BIG_DECIMAL);
	q.addScalar("assegnati", Hibernate.BIG_DECIMAL);
	q.addScalar("disponibili", Hibernate.BIG_DECIMAL);
	q.setResultTransformer(Transformers.aliasToBean(DehorsMqIstanzeHelper.class));
	//List<Object> result = q.list();
	List<DehorsMqIstanzeHelper> result = (List<DehorsMqIstanzeHelper>) q.list();
	if (!result.isEmpty()) {
	    return result.get(0);
	}
	DehorsMqIstanzeHelper dehorsMqIstanzeHelper = new DehorsMqIstanzeHelper();
	DehorsAree dehorsAree = dehorsAreeService.findByArea(codiceArea).get(0);
	dehorsMqIstanzeHelper.setAssegnati(new BigDecimal(0));
	dehorsMqIstanzeHelper.setDisponibili(dehorsAree.getMqdisponibili());
	dehorsMqIstanzeHelper.setMqdisponibili(dehorsAree.getMqdisponibili());
	return dehorsMqIstanzeHelper;
    }
}
