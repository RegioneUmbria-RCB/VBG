package it.gruppoinit.stc.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.stc.dao.AttivitaDAO;
import it.gruppoinit.stc.domain.Attivita;
import it.init.sigepro.rte.types.SportelloType;

@Repository
public class AttivitaDAOImpl extends BaseDAOImpl<Attivita, Integer> implements AttivitaDAO {

    @Override
    public Class<Attivita> getEntityClass() {

	return Attivita.class;
    }

    private static StringBuffer SQL = new StringBuffer();
    static {
	SQL.append("select ");
	SQL.append("  att_richieste.idprocedimento att_mitt_idproc ");
	SQL.append("from attivita att_richieste ");
	SQL.append("inner join messaggiattivita ");
	SQL.append("on messaggiattivita.fkidrichiesta=att_richieste.id ");
	SQL.append("inner join attivita att_risposte ");
	SQL.append("on messaggiattivita.fkidrisposta =att_risposte.id ");
	SQL.append("where att_richieste.fkidpratiche =?  "); // pratica destinataria
	SQL.append("and att_risposte.fkidpratiche    =?  "); // pratica mittente
	SQL.append("and att_risposte.idprocedimento is null ");
	SQL.append("and att_richieste.idprocedimento is not null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Attivita findByUniqueKey(Attivita example) {

	DetachedCriteria criteria = getDefaultCriteria();
	criteria.add(Restrictions.eq("idattivita", example.getIdattivita()));
	criteria.createAlias("pratiche", "pra");
	criteria.add(Restrictions.eq("pra.idpratica", example.getPratiche().getIdpratica()));
	criteria.add(Restrictions.eq("pra.configurazioneByFkidnodo.idnodo", example.getPratiche().getConfigurazioneByFkidnodo().getIdnodo()));
	criteria.add(Restrictions.eq("pra.idente", example.getPratiche().getIdente()));
	criteria.add(Restrictions.eq("pra.idsportello", example.getPratiche().getIdsportello()));
	if (example.getIdprocedimento() != null) {
	    criteria.add(Restrictions.eq("idprocedimento", example.getIdprocedimento()));
	} else {
	    criteria.add(Restrictions.isNull("idprocedimento"));
	}
	List<Attivita> list = this.getHibernateTemplate().findByCriteria(criteria);
	return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public String findIdProcedimentoPrecedentiComunicazioni(Integer idPraticaMittente, Integer idPraticaDestinataria) {

	SQLQuery q = getSession().createSQLQuery(SQL.toString());
	q.setFirstResult(0);
	q.setMaxResults(1);
	q.setInteger(0, idPraticaDestinataria); // idPraticaDestinataria   
	q.setInteger(1, idPraticaMittente); // idPraticaMIttente
	q.addScalar("att_mitt_idproc", Hibernate.STRING);
	List<String> result = (List<String>) q.list();
	if (result != null) {
	    if (result.size() > 0) {
		return result.get(0);
	    }
	}
	return null;
    }

    @Override
    public Attivita findBySportelloTypeAndIdAttivita(SportelloType sportello, String idAttivita) {

	DetachedCriteria criteria = getDefaultCriteria();
	criteria.add(Restrictions.eq("idattivita", idAttivita));
	criteria.createAlias("pratiche", "pra");
	criteria.add(Restrictions.eq("pra.idente", sportello.getIdEnte()));
	criteria.add(Restrictions.eq("pra.idsportello", sportello.getIdSportello()));
	criteria.add(Restrictions.eq("pra.configurazioneByFkidnodo.id", Integer.valueOf(sportello.getIdNodo())));
	List<Attivita> list = this.getHibernateTemplate().findByCriteria(criteria);
	return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public SportelloType findSportelloDestinatarioByIdAttivitaMittente(Integer idAttivitaMittente) {

	StringBuilder sql = new StringBuilder();
	sql.append("select " +
		" pratiche.idente as idEnte, " +
		" pratiche.idsportello as idSportello, " +
		" pratiche.fkidnodo as idNodo " +
		"from " +
		" messaggiattivita " +
		"   inner join attivita on messaggiattivita.fkidrisposta = attivita.id " +
		"   inner join pratiche on attivita.fkidpratiche = pratiche.id " +
		"where " +
		" messaggiattivita.fkidrichiesta = ?");
	SQLQuery q = getSession().createSQLQuery(sql.toString());
	q.setFirstResult(0);
	q.setMaxResults(1);
	q.setInteger(0, idAttivitaMittente);
	q.addScalar("idEnte", Hibernate.STRING);
	q.addScalar("idSportello", Hibernate.STRING);
	q.addScalar("idNodo", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(SportelloType.class));
	List<SportelloType> result = q.list();
	if (!result.isEmpty()) {
	    return result.get(0);
	}
	return new SportelloType();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PraticheHelper findPraticaMittenteByIdAttivitaDestinataria(Integer idAttivitaDestinataria) {

	StringBuilder sql = new StringBuilder();
	sql.append("select " +
		" pratiche.id," +
		" pratiche.idpratica as idPratica, " +
		" pratiche.idente as idEnte, " +
		" pratiche.fkidnodo as idNodo," +
		" pratiche.idsportello as idSportello " +
		"from " +
		" messaggiattivita" +
		"  inner join attivita on messaggiattivita.fkidrichiesta = attivita.id " +
		"  inner join pratiche on attivita.fkidpratiche = pratiche.id " +
		"where " +
		" messaggiattivita.fkidrisposta = ?");
	SQLQuery q = getSession().createSQLQuery(sql.toString());
	q.setFirstResult(0);
	q.setMaxResults(1);
	q.setInteger(0, idAttivitaDestinataria);
	q.addScalar("id", Hibernate.INTEGER);
	q.addScalar("idPratica", Hibernate.STRING);
	q.addScalar("idEnte", Hibernate.STRING);
	q.addScalar("idNodo", Hibernate.INTEGER);
	q.addScalar("idSportello", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(PraticheHelper.class));
	List<PraticheHelper> result = q.list();
	if (!result.isEmpty()) {
	    return result.get(0);
	}
	return new PraticheHelper();
    }
}
