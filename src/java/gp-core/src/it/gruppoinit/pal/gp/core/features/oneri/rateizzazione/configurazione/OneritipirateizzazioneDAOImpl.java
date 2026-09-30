package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Oneritipirateizzazione;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class OneritipirateizzazioneDAOImpl extends BaseDAOImpl<Oneritipirateizzazione, PkId> implements OneritipirateizzazioneDAO {

    @Override
    public Class<Oneritipirateizzazione> getEntityClass() {

	return Oneritipirateizzazione.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Oneritipirateizzazione> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Oneritipirateizzazione> findAllSenzaInteressiLegali() {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("flagInteressiLegali", false));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OneriTipiRateizzazioneListBean> findAllByIdcomuneSoftware(String idcomune, String software) {

	String sql = "select tiporateizzazione as id, descrizione from oneritipirateizzazione where idcomune = ? and software = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Oneritipirateizzazione.class);
	query.setString(0, idcomune);
	query.setString(1, software);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("descrizione", Hibernate.STRING);
	query.setResultTransformer(Transformers.aliasToBean(OneriTipiRateizzazioneListBean.class));
	return query.list();
    }

    @Override
    public OneriTipiRateizzazioneBean findById(int id) {

	return OneriTipiRateizzazioneBean.fromOneritipirateizzazione(this.findById(new PkId(id)));
    }
}
