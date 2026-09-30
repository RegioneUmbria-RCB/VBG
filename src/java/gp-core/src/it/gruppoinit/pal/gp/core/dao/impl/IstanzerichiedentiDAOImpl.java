package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.IstanzerichiedentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.Istanzerichiedenti;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.features.rabbitmq.model.CfRichiedentiBean;

/**
 * 
 * @author francescop
 */
@Repository
public class IstanzerichiedentiDAOImpl extends BaseDAOImpl<Istanzerichiedenti, PkId> implements IstanzerichiedentiDAO {

    protected final String SCHEMA_NAME = "#SCHEMA_NAME#";

    @Override
    public Class<Istanzerichiedenti> getEntityClass() {

	return Istanzerichiedenti.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzerichiedenti> findByIstanza(Istanze istanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("istanza.id.codice", istanza.getId().getCodice()));
	List<Istanzerichiedenti> istanzerichiedentis = getHibernateTemplate().findByCriteria(det);
	return istanzerichiedentis;
    }

    @Override
    public List<CfRichiedentiBean> findBeanByCodiceIstanza(Integer codiceIstanza) {

	String sql = "select " + // 
		     "a.codicefiscale as codicefiscale, " + // 
		     "a.tipoanagrafe as tipoanagrafe " + // 
		     "from " + // 
		     "istanzerichiedenti i " + // 
		     "inner join anagrafe a on " + // 
		     "i.idcomune = a.idcomune " + // 
		     "and i.codicerichiedente = a.codiceanagrafe " + // 
		     "inner join tipisoggetto t on " + // 
		     "i.idcomune = t.idcomune " + // 
		     "and i.codicetiposoggetto = t.codicetiposoggetto " + // 
		     "where " + // 
		     "i.idcomune = ? " + // 
		     "and i.codiceistanza = ? and " + // 
		     "t.flag_riceve_notifiche = ? ";
	SQLQuery q = getSession().createSQLQuery(sql.toString());
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("tipoanagrafe", Hibernate.STRING);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceIstanza);
	q.setInteger(2, 1);
	q.setResultTransformer(Transformers.aliasToBean(CfRichiedentiBean.class));
	List<CfRichiedentiBean> list = q.list();
	return list;
    }
}
