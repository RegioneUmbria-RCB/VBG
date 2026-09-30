/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzeprocedimentiDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocedimenti;
import it.gruppoinit.pal.gp.core.domain.IstanzeprocedimentiId;

/**
 * @author francescop
 * 
 */
@Repository
public class IstanzeprocedimentiDAOImpl extends BaseDAOImpl<Istanzeprocedimenti, IstanzeprocedimentiId> implements IstanzeprocedimentiDAO {

    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";

    @Override
    public Class<Istanzeprocedimenti> getEntityClass() {

	return Istanzeprocedimenti.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzeprocedimenti> findByIstanze(Istanze istanze) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanza.id.codice", istanze.getId().getCodice()));
	det.createAlias("inventarioprocedimenti", "_inventarioprocedimenti", Criteria.LEFT_JOIN);
	det.createAlias("_inventarioprocedimenti.tipoendo", "_tipoendo", Criteria.LEFT_JOIN);
	det.createAlias("_tipoendo.tipifamiglieendo", "_tipifamiglieendo", Criteria.LEFT_JOIN);
	det.addOrder(Order.asc("_tipifamiglieendo.ordine"));
	det.addOrder(Order.asc("_tipifamiglieendo.tipo"));
	det.addOrder(Order.asc("_tipoendo.ordine"));
	det.addOrder(Order.asc("_tipoendo.tipo"));
	det.addOrder(Order.asc("_inventarioprocedimenti.ordine"));
	det.addOrder(Order.asc("_inventarioprocedimenti.procedimento"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Istanzeprocedimenti> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }
}
