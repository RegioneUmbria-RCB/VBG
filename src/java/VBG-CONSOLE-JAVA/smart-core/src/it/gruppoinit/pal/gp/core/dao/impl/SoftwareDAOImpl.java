package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ResponsabilisoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.SoftwareDAO;
import it.gruppoinit.pal.gp.core.dao.SoftwareattiviDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Responsabili;
import it.gruppoinit.pal.gp.core.domain.Responsabilisoftware;
import it.gruppoinit.pal.gp.core.domain.Software;
import it.gruppoinit.pal.gp.core.domain.Softwareattivi;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SoftwareDAOImpl extends BaseDAOImpl<Software, String> implements SoftwareDAO {

    @Autowired
    private ResponsabilisoftwareDAO responsabilisoftwareDAO;
    @Autowired
    private SoftwareattiviDAO softwareattiviDAO;

    @Override
    @SuppressWarnings("unchecked")
    public List<Software> findAll(Integer firstResult, Integer maxResult) {

	if (null != firstResult && null != maxResult) {
	    return (List<Software>) getHibernateTemplate().findByExample(new Software(), firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<Software>) getHibernateTemplate().findByExample(new Software());
	}
    }

    @Override
    public Class<Software> getEntityClass() {

	return Software.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Software> findByFilter(Software entity) {

	DetachedCriteria det = DetachedCriteria.forClass(getEntityClass());
	det.add(Restrictions.ilike("descrizione", entity.getDescrizione(), MatchMode.ANYWHERE));
	return (List<Software>) getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<Software> findSoftwareAbilitati(Responsabili responsabile, boolean escludiNonOpzionali) {

	List<Software> softwareAttivi = this.findSoftwareAttivi(false);
	List<Software> softwareAbilitatiAttivi = new ArrayList<Software>();
	if (responsabile != null) {
	    if (responsabile.getAmministratore() != null && responsabile.getAmministratore().equals("1")) {
		softwareAbilitatiAttivi = softwareAttivi;
	    } else {
		List<Responsabilisoftware> softwareAbilitati = responsabilisoftwareDAO.findByResponsabile(responsabile);
		if (softwareAbilitati != null) {
		    for (Software softwareAtt : softwareAttivi) {
			for (Responsabilisoftware softwareAbi : softwareAbilitati) {
			    if (softwareAtt.getCodice().equals(softwareAbi.getId().getSoftware())) {
				softwareAbilitatiAttivi.add(softwareAtt);
			    }
			}
		    }
		}
	    }
	}
	if (escludiNonOpzionali) {
	    List<Software> result = new ArrayList<Software>();
	    int idx = 0;
	    for (Software software : softwareAbilitatiAttivi) {
		if (BooleanUtils.isTrue(software.getModuloopzionale())) {
		    result.add(idx, software);
		    idx++;
		}
	    }
	    return result;
	} else {
	    return softwareAbilitatiAttivi;
	}
    }

    @Override
    public List<Software> findSoftwareAttivi(boolean frontoffice) {

	List<Software> softwares = new ArrayList<Software>();
	List<Softwareattivi> softwareattivis = softwareattiviDAO.findAll(null, null);
	if (frontoffice) {
	    for (Softwareattivi softwareattivi : softwareattivis) {
		if (softwareattivi.isAttivoFo()) {
		    softwares.add(softwareattivi.getSoftware());
		}
	    }
	} else {
	    for (Softwareattivi softwareattivi : softwareattivis) {
		softwares.add(softwareattivi.getSoftware());
	    }
	}
	return softwares;
    }
}
