package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatistradarioDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.Mercatistradario;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author gianpaolot
 */
@Repository
public class MercatistradarioDAOImpl extends BaseDAOImpl<Mercatistradario, PkId> implements MercatistradarioDAO {

    @Override
    public Class<Mercatistradario> getEntityClass() {

	return Mercatistradario.class;
    }

    @Override
    public List<Mercatistradario> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mercatistradario> findByMercato(Mercati mercati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercato.id.codice", mercati.getId().getCodice()));
	criteria.createAlias("stradario", "_stradario");
	criteria.addOrder(Order.asc("_stradario.descrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
