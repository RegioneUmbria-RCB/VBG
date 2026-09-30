package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.ConcessionicausaliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Concessionicausali;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class ConcessionicausaliDAOImpl extends BaseDAOImpl<Concessionicausali, PkId> implements ConcessionicausaliDAO {

    @Override
    public Class<Concessionicausali> getEntityClass() {

	return Concessionicausali.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Concessionicausali> findByDescrizioneAndFlagStorico(Concessionicausali entity) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.ilike("descrizione", entity.getDescrizione(), MatchMode.ANYWHERE));
	det.add(Restrictions.eq("causalestorico", entity.isCausalestorico()));
	det.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Concessionicausali> findAllbyCausaleStorico(boolean isStorico) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("causalestorico", isStorico));
	det.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Concessionicausali> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Concessionicausali> findAllByAffitto(Concessionicausali concessionicausali) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.ilike("descrizione", concessionicausali.getDescrizione(), MatchMode.ANYWHERE));
	det.add(Restrictions.eq("flagCausaliAffitto", concessionicausali.isFlagCausaliAffitto()));
	det.addOrder(Order.asc("descrizione"));
	return getHibernateTemplate().findByCriteria(det);
    }
}
