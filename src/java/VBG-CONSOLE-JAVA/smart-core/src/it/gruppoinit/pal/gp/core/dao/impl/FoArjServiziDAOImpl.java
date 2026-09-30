package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.FoArjServiziDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.FoArjServizi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class FoArjServiziDAOImpl extends BaseDAOImpl<FoArjServizi, PkId> implements FoArjServiziDAO {

    @Override
    public Class<FoArjServizi> getEntityClass() {

	return FoArjServizi.class;
    }

    @Override
    public List<FoArjServizi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "urlServizio", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<FoArjServizi> findByAlberoproc(Alberoproc alberoproc) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("alberoproc", alberoproc));
	return (List<FoArjServizi>) getHibernateTemplate().findByCriteria(criteria);
    }
}
