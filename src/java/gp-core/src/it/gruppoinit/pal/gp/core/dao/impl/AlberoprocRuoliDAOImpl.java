/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.AlberoprocRuoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.AlberoProcRuoliBean;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoli;
import it.gruppoinit.pal.gp.core.domain.AlberoprocRuoliId;

/**
 * @author francescop
 * 
 */
@Repository
public class AlberoprocRuoliDAOImpl extends BaseDAOImpl<AlberoprocRuoli, AlberoprocRuoliId> implements AlberoprocRuoliDAO {

    @Override
    public Class<AlberoprocRuoli> getEntityClass() {

	return AlberoprocRuoli.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlberoProcRuoliBean> findRuoliPerVoce() {

	String sql = "SELECT " + //
		"alberoproc.sc_id as codice,alberoproc.sc_codice as percorso,alberoproc.sc_descrizione as descrizione ,alberoproc_ruoli.fk_idruolo as idruolo " + //
		"FROM alberoproc " + //
		" LEFT JOIN alberoproc_ruoli ON alberoproc_ruoli.idcomune=alberoproc.idcomune AND alberoproc_ruoli.fk_sc_id=alberoproc.sc_id " + //
		" WHERE alberoproc.idcomune=? AND alberoproc.software=? " + //
		" ORDER BY sc_codice";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.addScalar("codice", Hibernate.INTEGER);
	q.addScalar("percorso", Hibernate.STRING);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("idruolo", Hibernate.INTEGER);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, ORMHelper.getSoftware());
	q.setResultTransformer(Transformers.aliasToBean(AlberoProcRuoliBean.class));
	return (List<AlberoProcRuoliBean>) q.list();
    }
}
