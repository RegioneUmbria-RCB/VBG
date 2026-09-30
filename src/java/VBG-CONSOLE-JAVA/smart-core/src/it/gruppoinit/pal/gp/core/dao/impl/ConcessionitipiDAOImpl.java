package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ConcessionitipiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Concessionitipi;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ConcessionitipiDAOImpl extends BaseDAOImpl<Concessionitipi, String> implements ConcessionitipiDAO {

    @Override
    public Class<Concessionitipi> getEntityClass() {

	return Concessionitipi.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Concessionitipi> findByDescrizione(Concessionitipi entity) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.ilike("descrizione", entity.getDescrizione(), MatchMode.ANYWHERE));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Concessionitipi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }
}
