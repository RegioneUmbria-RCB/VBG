package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.NormativeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Normative;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class NormativeDAOImpl extends BaseDAOImpl<Normative, PkId> implements NormativeDAO {

    @Override
    public Class<Normative> getEntityClass() {

	return Normative.class;
    }

    @Override
    public List<Normative> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "normativa", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Normative> findByNormativa(String normativa) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.ilike("normativa", normativa, MatchMode.ANYWHERE));
	return (List<Normative>) getHibernateTemplate().findByCriteria(det);
    }
}
