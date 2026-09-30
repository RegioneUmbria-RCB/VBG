package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LavoritipiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Lavoricategorie;
import it.gruppoinit.pal.gp.core.domain.Lavoritipi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class LavoritipiDAOImpl extends BaseDAOImpl<Lavoritipi, PkId> implements LavoritipiDAO {

    @Override
    public Class<Lavoritipi> getEntityClass() {

	return Lavoritipi.class;
    }

    @Override
    public List<Lavoritipi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "lavoro", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Lavoritipi> findByLavoricategorie(Lavoricategorie categoria) {

	Assert.notNull(categoria);
	Assert.notNull(categoria.getId());
	Assert.notNull(categoria.getId().getCodice());
	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.add(Restrictions.eq("lavoricategorie.id", categoria.getId()));
	criteria.addOrder(Order.asc("lavoro"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Lavoritipi> findByDescrizione(String descrizione) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (StringUtils.isNotBlank(descrizione)) {
	    criteria.add(Restrictions.ilike("lavoro", "%" + descrizione + "%"));
	}
	criteria.addOrder(Order.asc("lavoro"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
