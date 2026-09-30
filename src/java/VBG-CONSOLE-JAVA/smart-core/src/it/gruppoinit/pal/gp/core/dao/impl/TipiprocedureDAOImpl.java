package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.TipiprocedureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedure;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipiprocedureDAOImpl extends BaseDAOImpl<Tipiprocedure, PkId> implements TipiprocedureDAO {

    @Override
    public Class<Tipiprocedure> getEntityClass() {

	return Tipiprocedure.class;
    }

    @Override
    public List<Tipiprocedure> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "procedura", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipiprocedure> findAllBySoftwareAndTT() {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.in("software.codice", new Object[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }));
	DetachedCriteria softwareCrit = det.createAlias("software", "_software");
	softwareCrit.addOrder(Order.asc("_software.ordine"));
	det.addOrder(Order.asc("procedura"));
	return (List<Tipiprocedure>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipiprocedure> findByDescrizione(String descrizione) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (StringUtils.isNotBlank(descrizione)) {
	    det.add(Restrictions.ilike("procedura", descrizione, MatchMode.ANYWHERE));
	}
	det.addOrder(Order.asc("procedura"));
	return (List<Tipiprocedure>) getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipiprocedure> findByCodiceODescrizione(String text, boolean includiDisabilitate, boolean soloConMovimentoAvvio) {

	DetachedCriteria det = getIdcomuneCriteria();
	Integer codice = null;
	try {
	    codice = Integer.valueOf(text);
	} catch (NumberFormatException e) {
	}
	if (codice != null) {
	    det.add(Restrictions.eq("id.codice", codice));
	} else if (StringUtils.isNotBlank(text)) {
	    det.add(Restrictions.ilike("procedura", text, MatchMode.ANYWHERE));
	}
	det.add(Restrictions.in("software.codice", new Object[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }));
	if (includiDisabilitate == false) {
	    det.add(Restrictions.or(Restrictions.eq("flagDisabilitato", Boolean.FALSE), Restrictions.isNull("flagDisabilitato")));
	}
	if (soloConMovimentoAvvio) {
	    det.add(Restrictions.isNotEmpty("tipiProcedureavvios"));
	}
	DetachedCriteria softwareCrit = det.createAlias("software", "_software");
	softwareCrit.addOrder(Order.asc("_software.ordine"));
	det.addOrder(Order.asc("procedura"));
	return (List<Tipiprocedure>) getHibernateTemplate().findByCriteria(det);
    }
}
