package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LavoritipiCausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.LavoritipiCausalioneri;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class LavoritipiCausalioneriDAOImpl extends BaseDAOImpl<LavoritipiCausalioneri, PkId> implements LavoritipiCausalioneriDAO {

    @Override
    public Class<LavoritipiCausalioneri> getEntityClass() {

	return LavoritipiCausalioneri.class;
    }

    @Override
    public List<LavoritipiCausalioneri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "tipicausalioneri", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<LavoritipiCausalioneri> findByLavoritipi(Lavoritipi lavoritipi) {

	Assert.notNull(lavoritipi);
	Assert.notNull(lavoritipi.getId());
	Assert.notNull(lavoritipi.getId().getCodice());
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.add(Restrictions.eq("lavoritipi.id", lavoritipi.getId()));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
