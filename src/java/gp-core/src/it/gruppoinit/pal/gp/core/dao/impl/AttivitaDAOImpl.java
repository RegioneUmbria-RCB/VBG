package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AttivitaDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Attivita;
import it.gruppoinit.pal.gp.core.domain.AttivitaId;

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
public class AttivitaDAOImpl extends BaseDAOImpl<Attivita, AttivitaId> implements AttivitaDAO {

    @Override
    public Class<Attivita> getEntityClass() {

	return Attivita.class;
    }

    @SuppressWarnings("unchecked")
    public List<Attivita> findAttivitaBySettore(Attivita attivita) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("settori", attivita.getSettori()));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Attivita> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "istat", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    public List<Attivita> findByFilter(Attivita entity) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (entity.getSettori() != null) {
	    det.add(Restrictions.eq("settori", entity.getSettori()));
	}
	if (entity.getFlagDisabilitato() != null) {
	    det.add(Restrictions.eq("flagDisabilitato", entity.getFlagDisabilitato()));
	}
	if (StringUtils.isNotBlank(entity.getIstat())) {
	    det.add(Restrictions.or(Restrictions.ilike("id.codiceistat", entity.getId().getCodiceistat(), MatchMode.ANYWHERE),
		    Restrictions.ilike("istat", entity.getIstat(), MatchMode.ANYWHERE)));
	}
	det.addOrder(Order.asc("istat"));
	List<Attivita> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }
}
