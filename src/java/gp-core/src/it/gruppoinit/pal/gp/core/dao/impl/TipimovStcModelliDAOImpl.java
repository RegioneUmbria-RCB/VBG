package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovStcModelliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcModelli;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class TipimovStcModelliDAOImpl extends BaseDAOImpl<TipimovStcModelli, PkId> implements TipimovStcModelliDAO {

    @Override
    public Class<TipimovStcModelli> getEntityClass() {

	return TipimovStcModelli.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipimovStcModelli> findByTipimovimento(TipimovimentoId tipimovimentoId) {

	Assert.notNull(tipimovimentoId);
	Assert.hasText(tipimovimentoId.getTipomovimento());
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("tipimovimento.id", tipimovimentoId));
	return getHibernateTemplate().findByCriteria(det);
    }
}
