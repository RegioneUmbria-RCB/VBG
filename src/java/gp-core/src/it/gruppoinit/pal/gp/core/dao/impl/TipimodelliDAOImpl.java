package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipimodelliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipifamiglieendo;
import it.gruppoinit.pal.gp.core.domain.Tipimodelli;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class TipimodelliDAOImpl extends BaseDAOImpl<Tipimodelli, PkId> implements TipimodelliDAO {

    @Override
    public Class<Tipimodelli> getEntityClass() {

	return Tipimodelli.class;
    }

    @Override
    public List<Tipimodelli> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "descrizione", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<Tipifamiglieendo> findByDescrizione(String textToSearch, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(textToSearch)) {
	    try {
		criteria.add(Restrictions.eq("id.codice", Integer.parseInt(textToSearch.replaceAll("%", ""))));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("descrizione", textToSearch, MatchMode.ANYWHERE));
	    }
	}
	criteria.addOrder(Order.asc("descrizione"));
	if (null != firstResult && null != maxResult) {
	    return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    return getHibernateTemplate().findByCriteria(criteria);
	}
    }

    @Override
    public List<Tipimodelli> findBySoftwareAndModulo(String codicesoftware) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	//detachedCriteria.createAlias("modellis.software", "_software", DetachedCriteria.LEFT_JOIN);
	detachedCriteria.createAlias("modellis", "_modellis", DetachedCriteria.LEFT_JOIN);
	detachedCriteria.createAlias("_modellis.software", "_software", DetachedCriteria.LEFT_JOIN);
	if (codicesoftware.equals(WebConstants.SOFTWARE_TT)) {
	    detachedCriteria.add(Restrictions.eq("_software.codice", codicesoftware));
	} else {
	    detachedCriteria.add(Restrictions.in("_software.codice", new String[] { codicesoftware, WebConstants.SOFTWARE_TT }));
	}
	ProjectionList projList = Projections.projectionList();
	projList.add(Projections.groupProperty("id.codice"));
	detachedCriteria.setProjection(projList);
	projList.add(Projections.groupProperty("descrizione"));
	detachedCriteria.addOrder(Order.asc("descrizione"));
	//List<Integer> list = getHibernateTemplate().findByCriteria(detachedCriteria);
	List<Object[]> list = getHibernateTemplate().findByCriteria(detachedCriteria);
	// Ricostruisco l'ogetto Tipimodelli a partire dagli oggetti
	// recuperati con la query
	List<Tipimodelli> tipimodellis = new ArrayList<Tipimodelli>();
	Tipimodelli temp = null;
	for (Object[] obj : list) {
	    temp = new Tipimodelli();
	    temp = this.findById(new PkId((Integer) obj[0]));
	    tipimodellis.add(temp);
	}
	return tipimodellis;
    }
}
