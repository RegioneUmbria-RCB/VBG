package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabiliDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.helper.CodiceDescrizioneBean;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

@Repository
public class ResponsabiliDAOImpl extends BaseDAOImpl<Responsabili, PkId> implements ResponsabiliDAO {

    @Override
    public Class<Responsabili> getEntityClass() {

	return Responsabili.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Responsabili findByUserid(String userid) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.or(Restrictions.eq("userid", userid), Restrictions.eq("strongAuthId", userid)));
	List<Responsabili> result = getHibernateTemplate().findByCriteria(det);
	if (result != null && result.size() > 0) {
	    return result.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    public List<Responsabili> findByFilter(Responsabili responsabili) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (responsabili.getResponsabile() != null && !responsabili.getResponsabile().equals("") && !responsabili.getResponsabile().equals("%")) {
	    det.add(Restrictions.ilike("responsabile", responsabili.getResponsabile(), MatchMode.ANYWHERE));
	}
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Responsabili> findResponsabiliProcedimento(Responsabili responsabili) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (responsabili.getResponsabile() != null && !responsabili.getResponsabile().equals("") && !responsabili.getResponsabile().equals("%")) {
	    det.add(Restrictions.ilike("responsabile", "%" + responsabili.getResponsabile() + "%", MatchMode.ANYWHERE));
	}
	det.add(Restrictions.eq("disabilitato", false));
	det.createCriteria("softwareAbilitati", "_softwareAbilitati");
	det.add(Restrictions.or(Restrictions.eq("_softwareAbilitati.id.software", ORMHelper.getSoftware()), Restrictions.eq("amministratore", "1")));
	det.createCriteria("tiporesponsabile", "_tiporesponsabile");
	det.add(Restrictions.eq("_tiporesponsabile.trFlagresponsabile", true));
	det.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	List<Responsabili> list = getHibernateTemplate().findByCriteria(det);
	if (list.isEmpty()) {
	    DetachedCriteria criteria = getIdcomuneCriteria();
	    if (responsabili.getResponsabile() != null && !responsabili.getResponsabile().equals("") && !responsabili.getResponsabile().equals("%")) {
		criteria.add(Restrictions.ilike("responsabile", "%" + responsabili.getResponsabile() + "%", MatchMode.ANYWHERE));
	    }
	    criteria.add(Restrictions.eq("disabilitato", false));
	    criteria.createCriteria("softwareAbilitati", "_softwareAbilitati");
	    criteria.add(Restrictions.or(Restrictions.eq("_softwareAbilitati.id.software", ORMHelper.getSoftware()),
		    Restrictions.eq("amministratore", "1")));
	    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	return list;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Responsabili> findResponsabiliIstruttoria(Responsabili responsabili) {

	DetachedCriteria det = getIdcomuneCriteria();
	if (responsabili.getResponsabile() != null && !responsabili.getResponsabile().equals("") && !responsabili.getResponsabile().equals("%")) {
	    det.add(Restrictions.ilike("responsabile", "%" + responsabili.getResponsabile() + "%", MatchMode.ANYWHERE));
	}
	det.add(Restrictions.eq("disabilitato", false));
	det.createCriteria("softwareAbilitati", "_softwareAbilitati");
	det.add(Restrictions.or(Restrictions.eq("_softwareAbilitati.id.software", ORMHelper.getSoftware()), Restrictions.eq("amministratore", "1")));
	det.createCriteria("tiporesponsabile", "_tiporesponsabile");
	det.add(Restrictions.eq("_tiporesponsabile.trFlagistruttore", true));
	det.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	List<Responsabili> list = getHibernateTemplate().findByCriteria(det);
	if (list.isEmpty()) {
	    DetachedCriteria criteria = getIdcomuneCriteria();
	    if (responsabili.getResponsabile() != null && !responsabili.getResponsabile().equals("") && !responsabili.getResponsabile().equals("%")) {
		criteria.add(Restrictions.ilike("responsabile", "%" + responsabili.getResponsabile() + "%", MatchMode.ANYWHERE));
	    }
	    criteria.add(Restrictions.eq("disabilitato", false));
	    criteria.createCriteria("softwareAbilitati", "_softwareAbilitati");
	    criteria.add(Restrictions.or(Restrictions.eq("_softwareAbilitati.id.software", ORMHelper.getSoftware()),
		    Restrictions.eq("amministratore", "1")));
	    criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Responsabili> findAllByAbilitati() {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("disabilitato", false));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Responsabili> findAll(Integer firstResult, Integer maxResult) {

	return this.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "responsabile", DAOOrderTypeEnum.ASC);
    }

    @Override
    public boolean isAbilitaBloccaOneri() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("flagBloccaOneri", Boolean.TRUE));
	criteria.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
	return ris > 0;
    }

    @Override
    public CodiceDescrizioneBean findDescrizioneById(Integer codiceOperatore) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("responsabile"), "DESCRIZIONE");
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(CodiceDescrizioneBean.class));
	criteria.add(Restrictions.eq("id.codice", codiceOperatore));
	List<CodiceDescrizioneBean> result = getHibernateTemplate().findByCriteria(criteria);
	if (result == null || result.size() == 0) {
	    return null;
	} else {
	    CodiceDescrizioneBean c = result.get(0);
	    c.setCodice(String.valueOf(codiceOperatore));
	    return c;
	}
    }
}
