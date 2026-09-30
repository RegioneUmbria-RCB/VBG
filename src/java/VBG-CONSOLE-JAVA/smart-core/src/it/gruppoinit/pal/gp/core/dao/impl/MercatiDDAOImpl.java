package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MercatiDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.PosteggiEnum;
import it.gruppoinit.pal.gp.core.domain.Mercati;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.SessionFactoryImplementor;
import org.springframework.stereotype.Repository;

@Repository
public class MercatiDDAOImpl extends BaseDAOImpl<MercatiD, PkId> implements MercatiDDAO {

    @Override
    public Class<MercatiD> getEntityClass() {

	return MercatiD.class;
    }

    @Override
    public List<MercatiD> findAllByMercato(Mercati mercati) {

	return this.findByMercato(mercati, PosteggiEnum.ALL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findByMercato(Mercati mercati, PosteggiEnum posteggiEnum) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("mercati", mercati));
	det.addOrder(Order.asc("codiceposteggio"));
	switch (posteggiEnum) {
	case ACTIVE:
	    det.add(Restrictions.or(Restrictions.eq("disabilitato", false), Restrictions.isNull("disabilitato")));
	    break;
	case DISABLED:
	    det.add(Restrictions.eq("disabilitato", true));
	    break;
	case ALL:
	    break;
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findByMercatoOrderByPeso(Mercati mercati, PosteggiEnum posteggiEnum) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("mercati", mercati));
	det.addOrder(Order.desc("peso"));
	switch (posteggiEnum) {
	case ACTIVE:
	    det.add(Restrictions.or(Restrictions.eq("disabilitato", false), Restrictions.isNull("disabilitato")));
	    break;
	case DISABLED:
	    det.add(Restrictions.eq("disabilitato", true));
	    break;
	case ALL:
	    break;
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findByPosteggiConConti(Mercati mercati, Integer anno) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercati", mercati));
	if (anno != null) {
	    criteria.createAlias("listaContiPosteggio", "_listaContiPosteggio", DetachedCriteria.LEFT_JOIN);
	    criteria.add(Restrictions.eq("_listaContiPosteggio.anno", anno.shortValue()));
	    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	}
	criteria.addOrder(Order.asc("codiceposteggio"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findPosteggioByMercatiMercatoUso(Mercati mercati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercati", mercati));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public MercatiD findPosteggioByCodicePosteggio(String codiceposteggio, Mercati mercati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercati", mercati));
	criteria.add(Restrictions.eq("codiceposteggio", codiceposteggio));
	List<MercatiD> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @Override
    public List<MercatiD> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "codiceposteggio", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MercatiD> findByMercatiD(MercatiD filter) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("mercati.id.codice", filter.getMercati().getId().getCodice()));
	// se sono non nulli uno dei campi transiet per la ricerca significa che stiamo cercando una lista filtra e
	// applichiamo i filtri
	if (filter.getConsentita() != null || (filter.getListaCodiciMerceologie() != null && filter.getListaCodiciMerceologie().length > 0)) {
	    criteria.createCriteria("mercatiDattivitaistats", "mercatiDattivitaistat");
	    if (filter.getListaCodiciMerceologie() != null && filter.getListaCodiciMerceologie().length > 0) {
		String[] listacodice = filter.getListaCodiciMerceologie();
		criteria.createAlias("mercatiDattivitaistat.attivita", "_attivita");
		criteria.add(Restrictions.in("_attivita.id.codiceistat", listacodice));
	    }
	    if (filter.getConsentita() != null) {
		criteria.add(Restrictions.eq("mercatiDattivitaistat.flagConsentito", filter.getConsentita()));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	    }
	}
	if (filter.getStradario() != null && filter.getStradario().getId().getCodice() != null) {
	    criteria.add(Restrictions.eq("stradario.id.codice", filter.getStradario().getId().getCodice()));
	}
	if (filter.getNote() != null && !filter.getNote().equals("")) {
	    criteria.add(Restrictions.ilike("note", filter.getNote(), MatchMode.ANYWHERE));
	}
	criteria.addOrder(Order.asc("codiceposteggio"));
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	// se non sono applicati i filtri si comporta come un findAll filtrando solo per idcomune e software
	List<MercatiD> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<CodiceDescrizioneBean> findPosteggiNonAssegnatiByMercato(Integer codiceMercato, Integer codiceMercatiUso, PosteggiEnum tipo) {

	if (tipo == null) {
	    tipo = PosteggiEnum.ALL;
	}
	Session session = this.getSession(false);
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	String schema = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	StringBuffer SQL = new StringBuffer();
	SQL.append("SELECT idposteggio, ");
	SQL.append("  codiceposteggio ");
	SQL.append("FROM " + schema + ".mercati_d ");
	SQL.append("WHERE idcomune =? ");
	SQL.append("AND fkcodicemercato=? ");
	switch (tipo) {
	case ACTIVE:
	    SQL.append("AND (disabilitato=0 or disabilitato is null) ");
	    break;
	case DISABLED:
	    SQL.append("AND disabilitato=1 ");
	    break;
	case ALL:
	    break;
	}
	SQL.append("AND idposteggio NOT IN ");
	SQL.append("  (SELECT md.idposteggio ");
	SQL.append("  FROM " + schema + ".mercati_d md ");
	SQL.append("  INNER JOIN " + schema + ".autorizzazioni_concessioni ac ");
	SQL.append("  ON ac.idcomune        =md.idcomune ");
	SQL.append("  AND ac.fk_idposteggio =md.idposteggio ");
	// Per mostrare i posteggi dove è stata cessata una concessione
	////////////////////////////////////////////////////////
	SQL.append("  INNER JOIN " + schema + ".autorizzazioni aut ");
	SQL.append("  ON aut.idcomune        =ac.idcomune ");
	SQL.append("  AND aut.id =ac.fk_idaut_attuale ");
	///////////////////////////////////////////////////
	SQL.append("  WHERE md.idcomune     =? ");
	SQL.append("  AND md.fkcodicemercato=? ");
	if (codiceMercatiUso != null) {
	    SQL.append("  AND ac.fk_idmercatiuso=? ");
	}
	//Condizione where per mostrare i posteggi dove è stata cessata una concessione
	////////////////////////////////////////////////////////
	SQL.append("  AND aut.flag_attiva=? ");
	//////////////////////////////////////////////////////////////
	SQL.append("  )");
	SQLQuery q = session.createSQLQuery(SQL.toString());
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceMercato);
	q.setString(2, ORMHelper.getIdcomune());
	q.setInteger(3, codiceMercato);
	if (codiceMercatiUso != null) {
	    q.setInteger(4, codiceMercatiUso);
	    q.setInteger(5, 1);
	} else {
	    q.setInteger(4, 1);
	}
	q.addScalar("idposteggio", Hibernate.INTEGER);
	q.addScalar("codiceposteggio", Hibernate.STRING);
	List result = q.list();
	List<CodiceDescrizioneBean> output = new ArrayList<CodiceDescrizioneBean>();
	if (!result.isEmpty()) {
	    for (Object o : result) {
		if (o instanceof Object[]) {
		    Object[] os = (Object[]) o;
		    Integer codice = (Integer) os[0];
		    String descrizione = (String) os[1];
		    CodiceDescrizioneBean b = new CodiceDescrizioneBean();
		    b.setCodice(String.valueOf(codice));
		    b.setDescrizione(descrizione);
		    output.add(b);
		}
	    }
	}
	return output;
    }
}
