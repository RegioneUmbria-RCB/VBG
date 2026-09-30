package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.dao.impl.IstanzeDAOImpl;
import it.gruppoinit.pal.gp.core.domain.IAttivitadyn2modellit;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzearee;
import it.gruppoinit.pal.gp.core.domain.IstanzeareeId;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.RicalcoloFilter;
import it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi.aree.QueryPerRicalcoloHelper;

/**
 * 
 * @author francescop
 */
@Repository
public class IstanzeareeDAOImpl extends BaseDAOImpl<Istanzearee, IstanzeareeId> implements IstanzeareeDAO {

    private static final Logger log = LoggerFactory.getLogger(IstanzeDAOImpl.class);
    
    @Override
    public Class<Istanzearee> getEntityClass() {

	return Istanzearee.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Istanzearee findByPrimarioIstanza(Istanze istanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codiceistanza", istanza.getId().getCodice()));
	det.add(Restrictions.eq("primario", true));
	List<Istanzearee> istanzearees = getHibernateTemplate().findByCriteria(det);
	if (!istanzearees.isEmpty()) {
	    return istanzearees.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzearee> findByIstanza(Istanze istanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codiceistanza", istanza.getId().getCodice()));
	List<Istanzearee> istanzearees = getHibernateTemplate().findByCriteria(det);
	return istanzearees;
    }

    @Override
    public List<Istanzearee> findByIstanza(Integer codiceistanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codiceistanza", codiceistanza));
	List<Istanzearee> istanzearees = getHibernateTemplate().findByCriteria(det);
	return istanzearees;
    }

    @Override
    public boolean exists(Integer codiceistanza, Integer codiceArea) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codiceistanza", codiceistanza));
	det.add(Restrictions.eq("id.codicearea", codiceArea));
	return getHibernateTemplate().findByCriteria(det).size() > 0;
    }

    @Override
    public boolean existsPrimario(Integer codiceistanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("id.codiceistanza", codiceistanza));
	det.add(Restrictions.eq("primario", Boolean.TRUE));
	return getHibernateTemplate().findByCriteria(det).size() > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findCodiciIstanzaPerRicalcolo(RicalcoloFilter filter, String[] comuniAbilitati) {

	log.debug("findCodiciIstanzaPerRicalcolo: recupero la sessionfactory e la casto a SessionFactoryImplementor");
	QueryPerRicalcoloHelper helper = new QueryPerRicalcoloHelper(filter, comuniAbilitati);
	String sql = helper.buildQuery();
	SQLQuery q = getSession().createSQLQuery(sql);
	helper.setFilterValues(q);
	helper.setScalarProperties(q);
	return q.list();
	
    }

    @Override
    public void eliminaAreeAutoins(Integer codiceistanza) {

	if (codiceistanza == null) {
	    throw new IllegalArgumentException("Impossibile cancellare le aree inserite automaticamente in un'istanza senza passare il codice dell'istanza");
	}
	
	String sql = "delete from istanzearee where idcomune = ? and codiceistanza = ? and autoins = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Istanzearee.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceistanza);
	query.setInteger(2, 1);
	query.executeUpdate();
    }
}
