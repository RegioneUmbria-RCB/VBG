package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LeggitipiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Leggitipi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class LeggitipiDAOImpl extends BaseDAOImpl<Leggitipi, PkId> implements LeggitipiDAO {

    @Override
    public Class<Leggitipi> getEntityClass() {

	return Leggitipi.class;
    }

    @Override
    public List<Leggitipi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ltDescrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Leggitipi> findByFilter(Leggitipi entity) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.ilike("ltDescrizione", entity.getLtDescrizione(), MatchMode.ANYWHERE));
	det.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	return (List<Leggitipi>) getHibernateTemplate().findByCriteria(det);
    }
}
