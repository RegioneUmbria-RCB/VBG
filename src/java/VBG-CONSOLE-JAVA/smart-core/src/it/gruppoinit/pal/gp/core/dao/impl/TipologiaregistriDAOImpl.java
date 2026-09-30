package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipologiaregistriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipologiaregistri;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipologiaregistriDAOImpl extends BaseDAOImpl<Tipologiaregistri, PkId> implements TipologiaregistriDAO {

    @Override
    public Class<Tipologiaregistri> getEntityClass() {

	return Tipologiaregistri.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipologiaregistri> findByDescrizione(Tipologiaregistri entity) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.ilike("trDescrizione", entity.getTrDescrizione(), MatchMode.ANYWHERE));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Tipologiaregistri> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "trDescrizione", DAOOrderTypeEnum.ASC);
    }
}
