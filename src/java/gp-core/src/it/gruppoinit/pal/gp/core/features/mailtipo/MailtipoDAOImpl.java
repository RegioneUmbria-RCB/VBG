package it.gruppoinit.pal.gp.core.features.mailtipo;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.ContestiMailTipoEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.Mailtipo;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MailtipoDAOImpl extends BaseDAOImpl<Mailtipo, PkId> implements MailtipoDAO {

    @Override
    public Class<Mailtipo> getEntityClass() {

	return Mailtipo.class;
    }

    @Override
    public List<Mailtipo> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE_AND_SOFTWARE, "descrizione", DAOOrderTypeEnum.DESC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mailtipo> findByFilter(Mailtipo filter) {

	DetachedCriteria criteria = getIdcomuneAndSoftwareCriteria();
	if (StringUtils.isNotBlank(filter.getSoftware().getCodice())) {
	    criteria.createAlias("software", "_software");
	    criteria.add(Restrictions.eq("_software.codice", filter.getSoftware().getCodice()));
	}
	if (StringUtils.isNotBlank(filter.getAmbito())) {
	    criteria.add(Restrictions.eq("ambito", filter.getAmbito()));
	}
	if (StringUtils.isNotBlank(filter.getDescrizione())) {
	    try {
		criteria.add(Restrictions.eq("id.codice", Integer.parseInt(filter.getDescrizione().replaceAll("%", ""))));
	    } catch (Exception e) {
		criteria.add(Restrictions.ilike("descrizione", filter.getDescrizione(), MatchMode.ANYWHERE));
	    }
	}
	criteria.addOrder(Order.asc("descrizione"));
	//criteria.add(Restrictions.ilike("descrizione", filter.getDescrizione(), MatchMode.ANYWHERE));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Mailtipo> findAllBySoftwareAndTT(ContestiMailTipoEnum contestiMailTipoEnum) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.in("software.codice", new Object[] { WebConstants.SOFTWARE_TT, ORMHelper.getSoftware() }));
	switch (contestiMailTipoEnum) {
	case CONFERENZE:
	    det.add(Restrictions.eq("ambito", WebConstants.CONFERENZE.toUpperCase()));
	    break;
	case MAIL:
	    det.add(Restrictions.or(Restrictions.eq("ambito", WebConstants.MAIL.toUpperCase()), Restrictions.isNull("ambito")));
	    break;
	case PARERI:
	    det.add(Restrictions.eq("ambito", WebConstants.PARERI.toUpperCase()));
	    break;
	case PROTOCOLLO:
	    det.add(Restrictions.eq("ambito", "P"));
	    break;
	default:
	    // NON SETTA NESSUN FILTRO SUGLI AMBITI
	}
	det.addOrder(Order.asc("descrizione"));
	return (List<Mailtipo>) getHibernateTemplate().findByCriteria(det);
    }
}
