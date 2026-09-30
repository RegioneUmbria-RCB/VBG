/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliruoliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Responsabiliruoli;
import it.gruppoinit.pal.gp.core.domain.ResponsabiliruoliId;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * 
 */
@Repository
public class ResponsabiliruoliDAOImpl extends BaseDAOImpl<Responsabiliruoli, ResponsabiliruoliId> implements ResponsabiliruoliDAO {

    @Override
    public Class<Responsabiliruoli> getEntityClass() {

	return Responsabiliruoli.class;
    }

    @Override
    public List<Integer> findCodiciRuoloByResponsabile(Integer codiceResponsabile) {

	String hql = "Select rr.id.idruolo from Responsabiliruoli rr where rr.id.idcomune=? and rr.id.codiceresponsabile = ?";
	Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
	Query q = s.createQuery(hql);
	q.setString(0, ORMHelper.getIdcomune());
	q.setInteger(1, codiceResponsabile);
	List<Integer> codiciRuolo = q.list();
	return codiciRuolo;
    }
}
