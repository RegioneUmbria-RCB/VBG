package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.InventarioprocedimentiincompDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimenti;
import it.gruppoinit.pal.gp.core.domain.Inventarioprocedimentiincomp;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class InventarioprocedimentiincompDAOImpl extends BaseDAOImpl<Inventarioprocedimentiincomp, PkId> implements InventarioprocedimentiincompDAO {

    @Override
    public Class<Inventarioprocedimentiincomp> getEntityClass() {

	return Inventarioprocedimentiincomp.class;
    }

    @Override
    public List<Inventarioprocedimentiincomp> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Inventarioprocedimentiincomp> findByEndoprocedimento(Inventarioprocedimenti inventarioprocedimenti) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("inventarioprocedimento.id.codice", inventarioprocedimenti.getId().getCodice()));
	// criteria.addOrder(Order.asc("inventarioprocedimento.procedimento"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public Inventarioprocedimentiincomp findByEndoprocedimentoAndEndoprocedimentoIncomp(Inventarioprocedimenti inventarioprocedimenti,
	    Inventarioprocedimenti inventarioprocedimentoIncomp) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("inventarioprocedimento.id.codice", inventarioprocedimenti.getId().getCodice()));
	criteria.add(Restrictions.eq("inventarioprocedimentoincompatibile.id.codice", inventarioprocedimentoIncomp.getId().getCodice()));
	List<Inventarioprocedimentiincomp> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
}
