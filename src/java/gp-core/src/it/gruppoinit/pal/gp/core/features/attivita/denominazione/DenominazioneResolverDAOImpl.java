package it.gruppoinit.pal.gp.core.features.attivita.denominazione;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.dao.impl.SostituzioniValoriConQueryWork;
import it.gruppoinit.pal.gp.core.domain.IAttivita;

@SuppressWarnings("rawtypes")
@Repository
public class DenominazioneResolverDAOImpl extends BaseDAOImpl implements IDenominazioneResolverDAO {

    @Override
    public Class<IAttivita> getEntityClass() {

	return null;
    }

    @Override
    public String findDenominazioneDaQuery(final String queryDenominazioneStr) {

	if (StringUtils.isEmpty(queryDenominazioneStr)) {
	    throw new IllegalArgumentException("Impossibile ricavare il valore dalla query senza passare la query");
	}
	if (!SostituzioniValoriConQueryWork.controllaSicurezzaQuery(queryDenominazioneStr)) {
	    throw new IllegalArgumentException("Query non corretta verificarla");
	}
	return (String) this.getHibernateTemplate().execute(new HibernateCallback() {

	    @Override
	    public Object doInHibernate(Session session) throws HibernateException, SQLException {

		SQLQuery query = session.createSQLQuery(queryDenominazioneStr);
		query.addScalar("denominazione", Hibernate.STRING);
		return (!query.list().isEmpty()) ? StringUtils.defaultIfEmpty((String) query.list().get(0), "") : "";
	    }
	});
    }
}
