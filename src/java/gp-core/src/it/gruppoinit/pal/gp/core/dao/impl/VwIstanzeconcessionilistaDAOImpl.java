package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.VwIstanzeconcessionilistaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.VwIstanzeconcessionilista;

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
public class VwIstanzeconcessionilistaDAOImpl extends BaseDAOImpl<VwIstanzeconcessionilista, PkId> implements VwIstanzeconcessionilistaDAO {

    @Override
    public Class<VwIstanzeconcessionilista> getEntityClass() {

	return VwIstanzeconcessionilista.class;
    }

    @Override
    public List<VwIstanzeconcessionilista> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, null, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<VwIstanzeconcessionilista> findByPosteggioAndUso(MercatiD mercatid, MercatiUso mercatiUso) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	criteria.add(Restrictions.eq("posteggio.id.codice", mercatid.getId().getCodice()));
	criteria.add(Restrictions.eq("mercatiUso.id.codice", mercatiUso.getId().getCodice()));
	criteria.addOrder(Order.desc("concAttiva"));
	criteria.addOrder(Order.desc("autorizdata"));
	criteria.addOrder(Order.desc("datarilascio"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
