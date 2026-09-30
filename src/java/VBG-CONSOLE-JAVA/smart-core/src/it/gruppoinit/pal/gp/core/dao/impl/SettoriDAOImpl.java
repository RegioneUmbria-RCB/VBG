package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.SettoriDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Settori;
import it.gruppoinit.pal.gp.core.domain.SettoriId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author francescop
 * @author gianpaolot
 * 
 */
@Repository
public class SettoriDAOImpl extends BaseDAOImpl<Settori, SettoriId> implements SettoriDAO {

    @Override
    public Class<Settori> getEntityClass() {

	return Settori.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Settori> findByFilter(Settori entity) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (entity != null) {
	    if (StringUtils.isNotBlank(entity.getSettore())) {
		det.add(Restrictions.or(Restrictions.ilike("id.codicesettore", entity.getId().getCodicesettore(), MatchMode.ANYWHERE),
			Restrictions.ilike("settore", entity.getSettore(), MatchMode.ANYWHERE)));
	    }
	    if (entity.getFlagContamqattivita() != null) {
		det.add(Restrictions.eq("flagContamqattivita", entity.getFlagContamqattivita()));
	    }
	    if (entity.getFlagInsmultiplo() != null) {
		det.add(Restrictions.eq("flagInsmultiplo", entity.getFlagInsmultiplo()));
	    }
	    if (entity.getFoRichiesto() != null) {
		det.add(Restrictions.eq("foRichiesto", entity.getFoRichiesto()));
	    }
	    if (entity.getFlagDisabilitato() != null) {
		det.add(Restrictions.eq("flagDisabilitato", entity.getFlagDisabilitato()));
	    }
	    if (EntityUtils.getNestedProperty(entity, "tipiunitamisura") != null) {
		boolean aliasCreato = false;
		if (StringUtils.isNotBlank(entity.getTipiunitamisura().getUmDescrbreve())) {
		    det.createAlias("tipiunitamisura", "_tipiunitamisura");
		    det.add(Restrictions.ilike("_tipiunitamisura.umDescrbreve", entity.getTipiunitamisura().getUmDescrbreve()));
		    aliasCreato = true;
		}
		if (EntityUtils.getNestedProperty(entity, "tipiunitamisura.id.codice") != null) {
		    if (!aliasCreato) {
			det.createAlias("tipiunitamisura", "_tipiunitamisura");
		    }
		    det.add(Restrictions.eq("_tipiunitamisura.id.codice", entity.getTipiunitamisura().getId().getCodice()));
		}
	    }
	}
	det.addOrder(Order.asc("settore"));
	return (List<Settori>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Settori> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "settore", DAOOrderTypeEnum.ASC);
    }
}
