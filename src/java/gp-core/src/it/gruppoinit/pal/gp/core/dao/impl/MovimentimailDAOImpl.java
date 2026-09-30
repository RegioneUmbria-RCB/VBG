package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentimailDAO;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Movimenti;
import it.gruppoinit.pal.gp.core.domain.Movimentimail;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 */
@Repository
public class MovimentimailDAOImpl extends BaseDAOImpl<Movimentimail, PkId> implements MovimentimailDAO {

    @Override
    public Class<Movimentimail> getEntityClass() {

	return Movimentimail.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movimentimail> findByIstanza(Istanze istanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("movimento", "_movimento");
	det.add(Restrictions.eq("_movimento.istanza.id.codice", istanza.getId().getCodice()));
	det.add(Restrictions.isNull("movimentimailPadre.id.codice"));
	det.addOrder(Order.desc("_movimento.data"));
	det.addOrder(Order.asc("_movimento.movimento"));
	det.addOrder(Order.desc("datainvio"));
	List<Movimentimail> movimentimails = getHibernateTemplate().findByCriteria(det);
	return movimentimails;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movimentimail> findByMovimento(Movimenti movimento) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("movimento", "_movimento");
	det.add(Restrictions.eq("_movimento.id.codice", movimento.getId().getCodice()));
	det.add(Restrictions.isNull("movimentimailPadre.id.codice"));
	det.addOrder(Order.asc("datainvio"));
	det.addOrder(Order.asc("id.codice"));
	List<Movimentimail> movimentimails = getHibernateTemplate().findByCriteria(det);
	return movimentimails;
    }
}
