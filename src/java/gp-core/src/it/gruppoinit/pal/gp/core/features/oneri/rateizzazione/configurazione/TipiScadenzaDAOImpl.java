/**
 * 
 */
package it.gruppoinit.pal.gp.core.features.oneri.rateizzazione.configurazione;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.TipiScadenza;

/**
 * @author francescop
 * 
 */
@Repository
public class TipiScadenzaDAOImpl extends BaseDAOImpl<TipiScadenza, Integer> implements TipiScadenzaDAO {

    @Override
    public Class<TipiScadenza> getEntityClass() {

	return TipiScadenza.class;
    }

    // E' stato fatto l'override del metodo findByExample
    // perchè il metodo di BaseDAOImpl aggiunge idcomune come restriction
    @Override
    @SuppressWarnings("unchecked")
    public List<TipiScadenza> findAll(Integer firstResult, Integer maxResult) {

	if (null != firstResult && null != maxResult) {
	    return (List<TipiScadenza>) getHibernateTemplate().findByExample(new TipiScadenza(), firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<TipiScadenza>) getHibernateTemplate().findByExample(new TipiScadenza());
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<TipoScadenzaBean> findTipiScadenzaConsentiti() {

	String sql = "select id, descrizione from tipi_scadenza order by id asc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(TipiScadenza.class);
	query.addScalar("id", Hibernate.INTEGER);
	query.addScalar("descrizione", Hibernate.STRING);
	query.setResultTransformer(Transformers.aliasToBean(TipoScadenzaBean.class));
	return query.list();
    }
}
