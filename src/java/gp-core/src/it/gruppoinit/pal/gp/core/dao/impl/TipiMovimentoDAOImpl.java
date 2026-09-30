package it.gruppoinit.pal.gp.core.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.TipiMovimentoDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;
import it.gruppoinit.pal.gp.core.domain.TipimovimentoId;

@Repository
public class TipiMovimentoDAOImpl extends BaseDAOImpl<Tipimovimento, TipimovimentoId> implements TipiMovimentoDAO {

    private static Logger log = LoggerFactory.getLogger(TipiMovimentoDAOImpl.class);

    @Override
    public Class<Tipimovimento> getEntityClass() {

	return Tipimovimento.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipimovimento> findByDescrizione(Tipimovimento entity, boolean includiDisabilitati, List<Software> softwareDaCercare) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (softwareDaCercare != null && !softwareDaCercare.isEmpty()) {
	    det.add(Restrictions.in("software", softwareDaCercare));
	}
	if (includiDisabilitati == false) {
	    det.add(Restrictions.or(Restrictions.eq("flagDisabilitato", Boolean.FALSE), Restrictions.isNull("flagDisabilitato")));
	}
	det.add(Restrictions.or(Restrictions.ilike("id.tipomovimento", entity.getMovimento(), MatchMode.ANYWHERE),
		Restrictions.ilike("movimento", entity.getMovimento(), MatchMode.ANYWHERE)));
	det.addOrder(Order.asc("movimento"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Tipimovimento getTipiMovimentoFlagCamcom() {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("flagCamcom", true));
	List<Tipimovimento> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    if (list.size() == 1) {
		return list.get(0);
	    } else {
		throw new RuntimeException("Sono presenti più Tipimovimento con il flag CAMCOM a true");
	    }
	}
	return null;
    }

    @Override
    public List<Tipimovimento> findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity, String software, boolean includiDisabilitati) {

	return findTipimovimentoByDescrizioneAndSoftware(entity, software, includiDisabilitati, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipimovimento> findTipimovimentoByDescrizioneAndSoftware(Tipimovimento entity, String software, boolean includiDisabilitati,
	    boolean escludiNonUsatiInProtocollo) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (software == null || software.equals("")) {
	    det.add(Restrictions.eq("software.codice", ORMHelper.getSoftware()));
	} else {
	    det.add(Restrictions.eq("software.codice", software));
	}
	if (!entity.getMovimento().equals("") && !entity.getId().getTipomovimento().equals("")) {
	    det.add(Restrictions.or(Restrictions.ilike("id.tipomovimento", entity.getId().getTipomovimento(), MatchMode.ANYWHERE),
		    Restrictions.ilike("movimento", entity.getMovimento(), MatchMode.ANYWHERE)));
	}
	if (includiDisabilitati == false) {
	    det.add(Restrictions.or(Restrictions.eq("flagDisabilitato", Boolean.FALSE), Restrictions.isNull("flagDisabilitato")));
	}
	if (escludiNonUsatiInProtocollo) {
	    det.add(Restrictions.eq("flagUsadalprotocollo", Boolean.TRUE));
	}
	det.addOrder(Order.asc("movimento"));
	return getHibernateTemplate().findByCriteria(det);
    }

    public List<Tipimovimento> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "movimento", DAOOrderTypeEnum.ASC);
    }

    @Override
    public boolean getFlagNoamminterna(String tipomovimento) {

	if (StringUtils.isBlank(StringUtils.defaultString(tipomovimento).trim())) {
	    log.error("getFlagNoamminterna# il parametro tipomovimento non è stato specificato");
	    throw new IllegalArgumentException("getFlagNoamminterna# il parametro tipomovimento non è stato specificato");
	}
	DetachedCriteria crit = getIdcomuneCriteria();
	crit.add(Restrictions.eq("id.tipomovimento", tipomovimento));
	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.property("flagNoamminterna"));
	crit.setProjection(projectionList);
	List<Boolean> list = (List<Boolean>) getHibernateTemplate().findByCriteria(crit);
	if (list != null) {
	    if (list.size() > 0) {
		return (list.get(0) == null) ? false : ((Boolean) list.get(0)).booleanValue();
	    }
	}
	log.error("getFlagNoamminterna# tipomovimento [{}-{}] non esiste", tipomovimento, ORMHelper.getIdcomune());
	throw new IllegalArgumentException(
		"getFlagNoamminterna# il tipomovimento [" + tipomovimento + "-" + ORMHelper.getIdcomune() + "] non esiste");
    }

    @SuppressWarnings("unchecked")
    @Override
    public Tipimovimento findTipimovimentoBySoggettiEsterniAndRichiestaIntegrazioniAndCodiceIstanza(Integer codiceIstanza, String idcomune) {

	String hql = "SELECT this_" +
		" FROM Tipimovimento this_" +
		" , Movimenti m WHERE this_.id.tipomovimento = m.tipomovimento.id.tipomovimento" +
		" AND m.istanza.id.codice = ?" +
		" AND m.id.idcomune = ?" +
		" AND this_.flagRichiestaintegrazione = ?" +
		" AND m.data IS NULL" +
		" AND (this_.foSoggettiesterni.codice = ?" +
		" OR this_.foSoggettiesterni.codice = ?)";
	Object[] values = new Object[] { codiceIstanza, idcomune, Boolean.valueOf(true), Integer.valueOf(1), Integer.valueOf(2) };
	List<Tipimovimento> result = getHibernateTemplate().find(hql, values);
	if (!result.isEmpty() && result.size() == 1) {
	    return result.get(0);
	}
	return null;
    }
}
