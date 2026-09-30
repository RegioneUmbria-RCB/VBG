/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.TipicausalioneriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicausalioneri;

/**
 * @author francescop
 * 
 */
@Repository
public class TipicausalioneriDAOImpl extends BaseDAOImpl<Tipicausalioneri, PkId> implements TipicausalioneriDAO {

    @Override
    public Class<Tipicausalioneri> getEntityClass() {

	return Tipicausalioneri.class;
    }

    @Override
    public List<Tipicausalioneri> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "coDescrizione", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipicausalioneri> findCausaliBollo(Tipicausalioneri tipicausalioneri) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("pagamentiregulus", true));
	det.add(Restrictions.isNull("causalebollo.id.codice"));
	if (tipicausalioneri != null) {
	    det.add(Restrictions.ne("id.codice", tipicausalioneri.getId().getCodice()));
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isCausaleBollo(Tipicausalioneri causalebollo, Tipicausalioneri tipicausalioneri) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("causalebollo.id.codice", causalebollo.getId().getCodice()));
	if (tipicausalioneri != null && tipicausalioneri.getId().getCodice() != null) {
	    det.add(Restrictions.ne("id.codice", tipicausalioneri.getId().getCodice()));
	}
	List<Tipicausalioneri> tipicausalioneris = getHibernateTemplate().findByCriteria(det);
	if (!tipicausalioneris.isEmpty()) {
	    return true;
	}
	return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipicausalioneri> findByDescrizione(String descrizione, String codiceSoftware) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(codiceSoftware)) {
	    criteria.add(Restrictions.eq("software.codice", codiceSoftware));
	} else {
	    criteria.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	}
	if (StringUtils.isNotBlank(descrizione)) {
	    criteria.add(Restrictions.ilike("coDescrizione", "%" + descrizione + "%"));
	}
	criteria.add(Restrictions.eq("coDisabilitato", false));
	criteria.addOrder(Order.asc("coOrdinamento"));
	criteria.addOrder(Order.asc("coDescrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipicausalioneri> findByDescrizioneAndFlagEndo(String textToSearch, Boolean flagEndo, String codiceSoftware) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	//	if (StringUtils.isNotBlank(textToSearch)) {
	//	    criteria.add(Restrictions.ilike("coDescrizione", "%" + textToSearch + "%"));
	//	}
	if (StringUtils.isNotBlank(textToSearch)) {
	    try {
		criteria.add(Restrictions.eq("id.codice", Integer.parseInt(textToSearch.replaceAll("%", ""))));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("coDescrizione", textToSearch, MatchMode.ANYWHERE));
	    }
	}
	if (StringUtils.isNotBlank(codiceSoftware)) {
	    criteria.add(Restrictions.eq("software.codice", codiceSoftware));
	} else {
	    criteria.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	}
	criteria.add(Restrictions.eq("coDisabilitato", false));
	if (BooleanUtils.isTrue(flagEndo)) {
	    criteria.add(Restrictions.eq("coSerichiedeendo", true));
	} else {
	    criteria.add(Restrictions.eq("coSerichiedeendo", false));
	}
	criteria.addOrder(Order.asc("coOrdinamento"));
	criteria.addOrder(Order.asc("coDescrizione"));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<Tipicausalioneri> findAll(Integer firstResult, Integer maxResult, boolean isInteressiDiMora) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (isInteressiDiMora) {
	    criteria.add(Restrictions.eq("flgTipicausaliinteressi", isInteressiDiMora));
	} else {
	    criteria.add(
		    Restrictions.or(Restrictions.eq("flgTipicausaliinteressi", isInteressiDiMora), Restrictions.isNull("flgTipicausaliinteressi")));
	}
	if (null != firstResult && null != maxResult) {
	    return (List<Tipicausalioneri>) getHibernateTemplate().findByCriteria(criteria, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Tipicausalioneri>) getHibernateTemplate().findByCriteria(criteria);
	}
    }

    @Override
    public List<Tipicausalioneri> findByDescrizioneEsatta(String descrizione, String codiceSoftware) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (StringUtils.isNotBlank(codiceSoftware)) {
	    criteria.add(Restrictions.eq("software.codice", codiceSoftware));
	} else {
	    criteria.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	}
	if (StringUtils.isNotBlank(descrizione)) {
	    criteria.add(Restrictions.ilike("coDescrizione", descrizione));
	}
	criteria.add(Restrictions.eq("coDisabilitato", false));
	criteria.addOrder(Order.desc("id.codice"));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
