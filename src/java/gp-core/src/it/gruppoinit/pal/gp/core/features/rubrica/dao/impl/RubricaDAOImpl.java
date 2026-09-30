package it.gruppoinit.pal.gp.core.features.rubrica.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Rubrica;
import it.gruppoinit.pal.gp.core.features.rubrica.dao.IRubricaDAO;

@Repository
public class RubricaDAOImpl extends BaseDAOImpl<Rubrica, PkId> implements IRubricaDAO {

    @Override
    public Class<Rubrica> getEntityClass() {

	return Rubrica.class;
    }

    @Override
    public List<Rubrica> ricercaIndirizzo(String partial, int resultCount) {

	partial = preparePartial(partial);
	String hql = "select r from Rubrica r where r.id.idcomune=? and (upper(r.descrizione) like ? or upper(r.mail) like ?) order by r.descrizione";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setMaxResults(resultCount);
	q.setString(0, ORMHelper.getIdcomune());
	q.setString(1, partial);
	q.setString(2, partial);
	return (List<Rubrica>) q.list();
    }

    private String preparePartial(String partial) {

	return "%" + //
		partial.toUpperCase().replace("%", "") + //
		"%";
    }
}
