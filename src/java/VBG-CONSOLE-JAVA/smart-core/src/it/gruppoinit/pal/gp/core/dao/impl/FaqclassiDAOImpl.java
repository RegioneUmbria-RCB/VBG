package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.FaqclassiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Faqclassi;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Riccardo Bocci
 */
@Repository
public class FaqclassiDAOImpl extends BaseDAOImpl<Faqclassi, PkId> implements FaqclassiDAO {

    @Override
    public Class<Faqclassi> getEntityClass() {

	return Faqclassi.class;
    }

    @Override
    public List<Faqclassi> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "faqclasse", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Faqclassi> findByFaqclasse(String faqclasse) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(faqclasse)) {
	    criteria.add(Restrictions.ilike("faqclasse", "%" + faqclasse + "%"));
	}
	criteria.addOrder(Order.asc("faqclasse"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<Faqclassi> findBySoftwareAndFaq(String software, boolean isCercaPerTT) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	//detachedCriteria.createAlias("modellis.software", "_software", DetachedCriteria.LEFT_JOIN);
	detachedCriteria.createAlias("faqs", "_faqs", DetachedCriteria.LEFT_JOIN);
	detachedCriteria.createAlias("_faqs.software", "_software", DetachedCriteria.LEFT_JOIN);
	if (!isCercaPerTT) {
	    detachedCriteria.add(Restrictions.eq("_software.codice", software));
	} else {
	    detachedCriteria.add(Restrictions.in("_software.codice", new String[] { software, WebConstants.SOFTWARE_TT }));
	}
	ProjectionList projList = Projections.projectionList();
	projList.add(Projections.groupProperty("id.codice"));
	projList.add(Projections.groupProperty("faqclasse"));
	detachedCriteria.addOrder(Order.asc("faqclasse"));
	detachedCriteria.setProjection(projList);
	List<Object[]> list = getHibernateTemplate().findByCriteria(detachedCriteria);
	// Ricostruisco l'ogetto Tipimodelli a partire dagli oggetti
	// recuperati con la query
	List<Faqclassi> faqclassi = new ArrayList<Faqclassi>();
	Faqclassi temp = null;
	for (Object[] obj : list) {
	    temp = new Faqclassi();
	    temp = this.findById(new PkId((Integer) obj[0]));
	    faqclassi.add(temp);
	}
	return faqclassi;
    }
}
