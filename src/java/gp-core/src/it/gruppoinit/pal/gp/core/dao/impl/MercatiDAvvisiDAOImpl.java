package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDAvvisiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiDAvvisi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class MercatiDAvvisiDAOImpl extends BaseDAOImpl<MercatiDAvvisi, PkId> implements MercatiDAvvisiDAO {

    @Override
    public Class<MercatiDAvvisi> getEntityClass() {

	return MercatiDAvvisi.class;
    }

    @Override
    public List<MercatiDAvvisi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Integer> findAvvisiByPosteggioAndAnagrafeDistincCausaliOneri(Integer codiceAnagrafe, Integer codicePosteggio) {

	/**
	 * private MercatiD mercatiD; private Tipicausalioneri tipicausalioneri; private Anagrafe anagrafe;
	 */
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("mercatiD", "_mercatiD", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("anagrafe", "_anagrafe", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.eq("_mercatiD.id.codice", codicePosteggio));
	//criteria.add(Restrictions.eq("_anagrafe.id.codice", codiceAnagrafe));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.distinct(Projections.property("tipicausalioneriId")));
	criteria.setProjection(projectionList);
	List<Integer> codiciamministrazionis = getHibernateTemplate().findByCriteria(criteria);
	return codiciamministrazionis;
    }
}
