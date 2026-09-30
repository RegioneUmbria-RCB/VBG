package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.LeggiDAO;
import it.gruppoinit.pal.gp.core.domain.Leggi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

@Repository
public class LeggiDAOImpl extends BaseDAOImpl<Leggi, PkId> implements LeggiDAO {

    @Override
    public Class<Leggi> getEntityClass() {

	return Leggi.class;
    }

    /*
     * Recupera tutte le leggi presenti e le ordina in modo ascendente in base al tipo (nazionale,comunale ,ect)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Leggi> findAllWithOrder(String campo) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.addOrder(Order.asc(campo));
	return (List<Leggi>) getHibernateTemplate().findByCriteria(det);
    }
}
