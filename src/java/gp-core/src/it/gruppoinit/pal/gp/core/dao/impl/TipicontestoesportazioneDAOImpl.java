package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.TipicontestoesportazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicontestoesportazione;

/**
 * 
 * @author
 */
@Repository
public class TipicontestoesportazioneDAOImpl extends BaseDAOImpl<Tipicontestoesportazione, PkId> implements TipicontestoesportazioneDAO {

    @Override
    public Class<Tipicontestoesportazione> getEntityClass() {

	return Tipicontestoesportazione.class;
    }

    @Override
    public List<Tipicontestoesportazione> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_ALL, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public Tipicontestoesportazione findByCodice(String codiceTipoContesto) {

	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	Dialect dialetto = sessimpl.getDialect();
	String sql = "select codice, descrizione from tipicontestoesportazione where CODICE=?";
	SQLQuery q = getSession().createSQLQuery(sql);
	q.setString(0, codiceTipoContesto);
	q.addScalar("codice", Hibernate.STRING);
	q.addScalar("descrizione", Hibernate.STRING);
	q.setResultTransformer(Transformers.aliasToBean(Tipicontestoesportazione.class));
	List result = q.list();
	Tipicontestoesportazione tipicontestoesportazione = new Tipicontestoesportazione();
	if (result.size() > 0) {
	    tipicontestoesportazione = (Tipicontestoesportazione) result.get(0);
	}
	return tipicontestoesportazione;
    }
}
