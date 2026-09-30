package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipimovStcAlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.TipimovStcAlberoproc;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

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
public class TipimovStcAlberoprocDAOImpl extends BaseDAOImpl<TipimovStcAlberoproc, PkId> implements TipimovStcAlberoprocDAO {

    @Override
    public Class<TipimovStcAlberoproc> getEntityClass() {

	return TipimovStcAlberoproc.class;
    }

    @Override
    public List<TipimovStcAlberoproc> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipimovStcAlberoproc> findByTipimovimento(TipimovimentoId tipimovimentoId) {

	Assert.notNull(tipimovimentoId);
	Assert.hasText(tipimovimentoId.getTipomovimento());
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("tipimovimento.id", tipimovimentoId));
	return getHibernateTemplate().findByCriteria(det);
    }
}
