package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BandiAlberoprocDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.BandiAlberoproc;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class BandiAlberoprocDAOImpl extends BaseDAOImpl<BandiAlberoproc, PkId> implements BandiAlberoprocDAO {

    @Override
    public Class<BandiAlberoproc> getEntityClass() {

	return BandiAlberoproc.class;
    }

    @Override
    public List<BandiAlberoproc> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "ordine", DAOOrderTypeEnum.ASC);
    }

    @Override
    public int findMaxOrdine(Integer codiceBando) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("bandi", "_bandi");
	det.add(Restrictions.eq("_bandi.id.codice", codiceBando));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.max("ordine"));
	det.setProjection(projectionList);
	List<Integer> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    if (list.get(0) != null) {
		return list.get(0);
	    }
	}
	return 0;
    }

    @Override
    public List<Integer> findDistinctMercatiByBando(Integer codiceBando) {

	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.distinct(Projections.property("_mercato.id.codice")), "mercatoId");
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("alberoproc", "alberoprocAlias");
	criteria.createCriteria("alberoprocAlias.mercato", "_mercato");
	criteria.createCriteria("bandi", "bandiAlias").add(Restrictions.eq("bandiAlias.id.codice", codiceBando));
	criteria.setProjection(projectionList);
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<Integer> findDistinctMercatiUsoByBando(Integer codiceBando) {

	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.distinct(Projections.property("_mercatoUso.id.codice")), "mercatousoId");
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("alberoproc", "alberoprocAlias");
	criteria.createCriteria("alberoprocAlias.mercatoUso", "_mercatoUso", Criteria.LEFT_JOIN);
	criteria.createCriteria("bandi", "bandiAlias").add(Restrictions.eq("bandiAlias.id.codice", codiceBando));
	criteria.setProjection(projectionList);
	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	return getHibernateTemplate().findByCriteria(criteria);
	//	select distinct (fkidmercatiuso) from BANDI_ALBEROPROC left join  ALBEROPROC 
	//	on bandi_alberoproc.idcomune=ALBEROPROC.idcomune and bandi_alberoproc.fkscid=ALBEROPROC.sc_id
	//	where fkidbando=142;
	//	String hql = "select distinct p from Responsabili r inner join r.protocolloFlussos p where r.id.idcomune=? " + "and r.id.codice = ? ";
	//	int sizevalues = 2;
	//	if (escludiFlussi != null) {
	//	    if (escludiFlussi.size() > 0) {
	//		sizevalues += escludiFlussi.size();
	//		String questionMarks = "";
	//		for (String codicetipo : escludiFlussi) {
	//		    questionMarks += "?,";
	//		}
	//		if (escludiFlussi != null && escludiFlussi.size() > 0) {
	//		    questionMarks = questionMarks.substring(0, questionMarks.length() - 1);
	//		}
	//		hql += " and not p.codice in (" + questionMarks + ")";
	//	    }
	//	}
	//	hql += " order by p.descrizione asc";
	//	Object[] values = new Object[sizevalues];
	//	values[0] = ORMHelper.getIdcomune();
	//	values[1] = responsabili.getId().getCodice();
	//	if (escludiFlussi != null) {
	//	    if (escludiFlussi.size() > 0) {
	//		int contatore = 2;
	//		for (String codicetipo : escludiFlussi) {
	//		    values[contatore] = codicetipo;
	//		    contatore++;
	//		}
	//	    }
	//	}
	//	return getHibernateTemplate().find(hql, values);
    }
}
