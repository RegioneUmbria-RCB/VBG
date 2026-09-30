/**
 * 
 */
package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.BandiDAO;
import it.gruppoinit.pal.gp.core.domain.Alberoproc;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Bandi;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafeFiere;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

/**
 * @author fabrizioc
 * 
 */
@Repository
public class BandiDAOImpl extends BaseDAOImpl<Bandi, PkId> implements BandiDAO {

    @Override
    public Class<Bandi> getEntityClass() {

	return Bandi.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Bandi> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Bandi> findByAlberoproc(Alberoproc alberoproc) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("alberoproc.id.codice", alberoproc.getId().getCodice()));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AnagrafeFiere> findIstanzeAnagrafeGraduatoria(Alberoproc alberoproc, Anagrafe anagrafe) {

	ProjectionList projectionList = Projections.projectionList();
	projectionList.add(Projections.distinct(Projections.property("istanzaAlias.id.codice")), "istanzaId");
	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createCriteria("alberoproc", "alberoprocAlias").add(Restrictions.eq("alberoprocAlias.id.codice", alberoproc.getId().getCodice()));
	criteria.createAlias("graduatoriets", "graduatorietsAlias");
	criteria.createAlias("graduatorietsAlias.graduatorieds", "graduatoriedsAlias");
	criteria.createCriteria("graduatoriedsAlias.istanza", "istanzaAlias");
	criteria.setProjection(projectionList).add(
		Restrictions.or(Restrictions.eq("istanzaAlias.richiedente.id.codice", anagrafe.getId().getCodice()),
			Restrictions.eq("istanzaAlias.titolarelegale.id.codice", anagrafe.getId().getCodice())));
	criteria.setResultTransformer(Transformers.aliasToBean(AnagrafeFiere.class));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
