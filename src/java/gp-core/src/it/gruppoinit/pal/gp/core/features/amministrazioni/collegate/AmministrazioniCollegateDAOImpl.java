package it.gruppoinit.pal.gp.core.features.amministrazioni.collegate;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniCollegate;
import it.gruppoinit.pal.gp.core.domain.AmministrazioniCollegateId;
import it.gruppoinit.pal.gp.core.domain.AtEsitiErroreTracciato;
import it.gruppoinit.pal.gp.core.features.amministrazioni.collegate.model.AmmCollComuneBean;

@Repository
public class AmministrazioniCollegateDAOImpl extends BaseDAOImpl<AmministrazioniCollegate, AmministrazioniCollegateId>
	implements AmministrazioniCollegateDAO {

    @Override
    public Class<AmministrazioniCollegate> getEntityClass() {

	return AmministrazioniCollegate.class;
    }

    @Override
    public Amministrazioni findCollegataByAmministrazioneAndComune(Integer codiceAmministrazione, String codiceComune) {

	Query query = getSession().createQuery(
		"from AmministrazioniCollegate a where a.id.idcomune=:idcomune and a.id.codiceamministrazione=:codiceamministrazione and a.id.codicecomune=:codicecomune");
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("codiceamministrazione", codiceAmministrazione);
	query.setString("codicecomune", codiceComune);
	List<AmministrazioniCollegate> list = query.list();
	if (list.size() > 0) {
	    return list.get(0).getAmmCollegata();
	}
	return null;
    }

    @Override
    public List<AmmCollComuneBean> comuniDisponibili(Integer codiceAmministrazione) {

	//	    private String codicecomune;
	//	    private String comune;
	//	    private String codicecatastale;
	//	    private String codiceistat;
	//	    private String siglaprovincia;
	//	    private String regione;
	String sql = "SELECT " +
		     "comuni.codicecomune,comuni.comune,comuni.cf AS codicecatastale,comuni.codiceistat, comuni.siglaprovincia, comuni.provincia,  comuni.regione" + //
		     " FROM comuniassociati INNER JOIN COMUNI ON " + //
		     " comuniassociati.CODICECOMUNE=COMUNI.CODICECOMUNE LEFT JOIN amministrazioni_collegate ON " + //
		     " amministrazioni_collegate.IDCOMUNE=comuniassociati.idcomune AND " + //
		     " amministrazioni_collegate.codicecomune=comuniassociati.codicecomune " + //
		     " AND  amministrazioni_collegate.codiceamministrazione=:codiceamministrazione " + //
		     " WHERE comuniassociati.idcomune=:idcomune AND amministrazioni_collegate.idcomune IS NULL";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	SQLQuery q = s.createSQLQuery(sql).addSynchronizedEntityClass(AtEsitiErroreTracciato.class);
	q.setInteger("codiceamministrazione", codiceAmministrazione);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.addScalar("codicecomune", Hibernate.STRING);
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("codiceistat", Hibernate.STRING);
	q.addScalar("siglaprovincia", Hibernate.STRING);
	q.addScalar("provincia", Hibernate.STRING);
	q.addScalar("regione", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(AmmCollComuneBean.class));
	return q.list();
    }
}
