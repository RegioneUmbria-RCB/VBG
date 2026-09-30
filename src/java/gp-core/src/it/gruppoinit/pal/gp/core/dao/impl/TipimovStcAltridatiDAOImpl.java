package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovStcAltridatiDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAltridati;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class TipimovStcAltridatiDAOImpl extends BaseDAOImpl<TipimovStcAltridati, PkId> implements TipimovStcAltridatiDAO {

    @Override
    public Class<TipimovStcAltridati> getEntityClass() {

	return TipimovStcAltridati.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<TipimovStcAltridati> findByTipimovimento(TipimovimentoId tipimovimentoId) {

	Assert.notNull(tipimovimentoId);
	Assert.hasText(tipimovimentoId.getTipomovimento());
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("tipimovimento.id", tipimovimentoId));
	return getHibernateTemplate().findByCriteria(det);
    }
}
