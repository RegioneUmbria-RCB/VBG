package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.AreeDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Aree;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AreeDAOImpl extends BaseComuniAssociatiDAOImpl<Aree, PkId> implements AreeDAO {

    @Override
    public Class<Aree> getEntityClass() {

	return Aree.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Aree> findByDescrizione(String descrizione, String codiceComune, String[] codiciComuniAbilitati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.in("software.codice", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	if (descrizione != null) {
	    if (StringUtils.isNotBlank(descrizione)) {
		criteria.add(Restrictions.ilike("denominazione", descrizione, MatchMode.ANYWHERE));
	    }
	}
	if (null != codiciComuniAbilitati && codiciComuniAbilitati.length > 0) {
	    // devo mettere anche i valori null nella ricerca per non escludere quei record che non hanno
	    // associato alcun comune per questo ho utilizzato la LEFT_JOIN
	    DetachedCriteria comuneCriteria = criteria.createCriteria("comune", Criteria.LEFT_JOIN);
	    if (StringUtils.isNotBlank(codiceComune)) {
		comuneCriteria.add(Restrictions.or(Restrictions.in("codicecomune", new String[] { codiceComune }),
			Restrictions.isNull("codicecomune")));
	    } else {
		comuneCriteria.add(Restrictions.or(Restrictions.in("codicecomune", codiciComuniAbilitati), Restrictions.isNull("codicecomune")));
	    }
	}
	criteria.addOrder(Order.asc("denominazione"));
	return (List<Aree>) getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<Aree> findByDescrizioneDehors(String textToSearch, String codiceComune, String[] codiciComuniAbilitati) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.in("software.codice", new Object[] { ORMHelper.getSoftware(), WebConstants.SOFTWARE_TT }));
	if (textToSearch != null) {
	    if (StringUtils.isNotBlank(textToSearch)) {
		criteria.add(Restrictions.ilike("denominazione", textToSearch, MatchMode.ANYWHERE));
	    }
	}
	if (null != codiciComuniAbilitati && codiciComuniAbilitati.length > 0) {
	    // devo mettere anche i valori null nella ricerca per non escludere quei record che non hanno
	    // associato alcun comune per questo ho utilizzato la LEFT_JOIN
	    DetachedCriteria comuneCriteria = criteria.createCriteria("comune", Criteria.LEFT_JOIN);
	    if (StringUtils.isNotBlank(codiceComune)) {
		comuneCriteria.add(Restrictions.or(Restrictions.in("codicecomune", new String[] { codiceComune }),
			Restrictions.isNull("codicecomune")));
	    } else {
		comuneCriteria.add(Restrictions.or(Restrictions.in("codicecomune", codiciComuniAbilitati), Restrictions.isNull("codicecomune")));
	    }
	}
	// cr
	criteria.createAlias("dehorsArees", "_dehorsArees", Criteria.LEFT_JOIN);
	criteria.add(Restrictions.isNotNull("_dehorsArees.id.codice"));
	criteria.addOrder(Order.asc("denominazione"));
	return (List<Aree>) getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<Aree> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "denominazione", DAOOrderTypeEnum.ASC);
    }

    @Override
    protected void setCodiceComune(Aree entity) {

	if (!checkIfCodiceComuneIsSet(entity.getComune())) {
	    entity.setComune(getDefaultComune());
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Aree> findAllByCodiciComuni(String[] codiciComune) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	DetachedCriteria comuneCriteria = criteria.createCriteria("comune", Criteria.LEFT_JOIN);
	if (null != codiciComune && codiciComune.length > 0) {
	    // devo mettere anche i valori null nella ricerca per non escludere quei record che non hanno
	    // associato alcun comune
	    comuneCriteria.add(Restrictions.or(Restrictions.in("codicecomune", codiciComune), Restrictions.isNull("codicecomune")));
	}
	comuneCriteria.addOrder(Order.asc("comune"));
	criteria.addOrder(Order.asc("denominazione"));
	return (List<Aree>) getHibernateTemplate().findByCriteria(criteria);
    }
}
