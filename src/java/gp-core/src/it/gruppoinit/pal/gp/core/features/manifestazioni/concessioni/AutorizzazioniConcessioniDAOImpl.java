package it.gruppoinit.pal.gp.core.features.manifestazioni.concessioni;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Autorizzazioni;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniConcessioni;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiUso;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.web.servizijson.IdentificativoDescrizioneBean;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class AutorizzazioniConcessioniDAOImpl extends BaseDAOImpl<AutorizzazioniConcessioni, PkId> implements AutorizzazioniConcessioniDAO {

    @Override
    public Class<AutorizzazioniConcessioni> getEntityClass() {

	return AutorizzazioniConcessioni.class;
    }

    @Override
    public List<AutorizzazioniConcessioni> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniConcessioni> findConcessioniByIstanza(Integer codiceIstanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniByFkAutconcAutatt", "aut", DetachedCriteria.INNER_JOIN);
	det.add(Restrictions.eq("aut.istanza.id.codice", codiceIstanza));
	List<AutorizzazioniConcessioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniConcessioni> findConcessioniAttiveByMercatoEUso(Mercati mercato, MercatiUso uso) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniByFkAutconcAutatt", "aut", DetachedCriteria.INNER_JOIN);
	det.createCriteria("mercatiD", "posteggio", DetachedCriteria.INNER_JOIN);
	det.add(Restrictions.eq("mercati.id.codice", mercato.getId().getCodice()));
	det.add(Restrictions.eq("mercatiUso.id.codice", uso.getId().getCodice()));
	det.add(Restrictions.eq("aut.flagAttiva", true));
	det.addOrder(Order.asc("posteggio.codiceposteggio"));
	List<AutorizzazioniConcessioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniConcessioni> findConcessioniAttiveByMercato(Mercati mercato) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniByFkAutconcAutatt", "aut", DetachedCriteria.INNER_JOIN);
	det.createCriteria("mercatiD", "posteggio", DetachedCriteria.INNER_JOIN);
	det.add(Restrictions.eq("mercati.id.codice", mercato.getId().getCodice()));
	det.add(Restrictions.eq("aut.flagAttiva", true));
	det.addOrder(Order.asc("posteggio.codiceposteggio"));
	List<AutorizzazioniConcessioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @Override
    public List<AutorizzazioniConcessioni> findConcessioniByMercato(Mercati mercato) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioniByFkAutconcAutatt", "aut", DetachedCriteria.INNER_JOIN);
	det.createCriteria("mercatiD", "posteggio", DetachedCriteria.INNER_JOIN);
	det.add(Restrictions.eq("mercati.id.codice", mercato.getId().getCodice()));
	det.addOrder(Order.asc("posteggio.codiceposteggio"));
	List<AutorizzazioniConcessioni> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdAutorizzazioniAttiveByIdPosteggio(int idPosteggio) {

	String sql = "select " + //
		     "  autorizzazioni.id " + //
		     "from " + //
		     "  autorizzazioni" + //
		     "    inner join autorizzazioni_concessioni on " + //
		     "      autorizzazioni.idcomune = autorizzazioni_concessioni.idcomune and " + //
		     "      autorizzazioni.id = autorizzazioni_concessioni.fk_idaut_attuale " + //
		     "where" + //
		     "  autorizzazioni_concessioni.idcomune = ? and" + //
		     "  autorizzazioni_concessioni.fk_idposteggio = ? and" + //
		     "  autorizzazioni.flag_attiva = ? " + //
		     "order by " + //
		     "  autorizzazioni.id desc";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, idPosteggio);
	query.setInteger(2, 1);
	query.addScalar("id", Hibernate.INTEGER);
	return query.list();
    }

    @Override
    public Integer getFkIdautAttualePerAutCollegata(Integer fkIdautCollegata) {

	String sql = "select fk_idaut_attuale from autorizzazioni_concessioni where" +
		     "  autorizzazioni_concessioni.idcomune = :idcomune and fk_idaut_collegata=:fk_idaut_collegata";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	query.setString("idcomune", ORMHelper.getIdcomune());
	query.setInteger("fk_idaut_collegata", fkIdautCollegata);
	query.addScalar("fk_idaut_attuale", Hibernate.INTEGER);
	List<Integer> list = query.list();
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public Map<Integer, IdentificativoDescrizioneBean> findAutorizzazioniCollegate(Set<Integer> auts) {

	String sql = "select fk_idaut_attuale, fk_idaut_collegata, autorizzazioni.autoriznumero as autoriznumero " + //
		     " from autorizzazioni_concessioni inner join autorizzazioni on " + //
		     " autorizzazioni.idcomune = autorizzazioni_concessioni.idcomune and " + //
		     " autorizzazioni.id = autorizzazioni_concessioni.fk_idaut_collegata " + //
		     " where" + //
		     "  autorizzazioni_concessioni.idcomune = ? and ";
	StringBuilder inClause = new StringBuilder("");
	int num = auts.size();
	Double filterGetListaCodiceAttivitaLength = Double.valueOf(num);
	Double cicli = filterGetListaCodiceAttivitaLength / 1000;
	int cicliDaMille = cicli.intValue();
	int resto = num - (cicliDaMille * 1000);
	inClause.append("  ( 1=2 ");
	for (int i = 0; i < cicliDaMille; i++) {
	    String qm = StringUtils.repeat("?,", 1000);
	    qm = qm.substring(0, qm.length() - 1);
	    inClause.append(" or autorizzazioni_concessioni.fk_idaut_attuale in (" + qm + ")");
	}
	if (resto > 0) {
	    String qm = StringUtils.repeat("?,", resto);
	    qm = qm.substring(0, qm.length() - 1);
	    inClause.append(" or autorizzazioni_concessioni.fk_idaut_attuale in (" + qm + ")");
	}
	inClause.append(")");
	sql += inClause;
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(Autorizzazioni.class);
	int position = 0;
	query.setString(position++, ORMHelper.getIdcomune());
	//////////////////////////
	for (Integer idAutorizzazione : auts) {
	    query.setInteger(position++, idAutorizzazione);
	}
	/////////////////////////
	// query.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AutorizzazioniRestHelper.class));
	query.addScalar("fk_idaut_attuale", Hibernate.INTEGER);
	query.addScalar("fk_idaut_collegata", Hibernate.INTEGER);
	query.addScalar("autoriznumero", Hibernate.STRING);
	List list = query.list();
	Map<Integer, IdentificativoDescrizioneBean> ret = new HashMap<Integer, IdentificativoDescrizioneBean>();
	for (Object o : list) {
	    if (o instanceof Object[]) {
		Object[] rs = (Object[]) o;
		ret.put((Integer) rs[0], new IdentificativoDescrizioneBean((Integer) rs[1], (String) rs[2]));
	    }
	}
	return ret;
    }
}
