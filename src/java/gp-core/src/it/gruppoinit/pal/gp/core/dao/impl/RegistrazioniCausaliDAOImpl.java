package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.RegistrazioniCausaliDAO;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.RegistrazioniCausali;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class RegistrazioniCausaliDAOImpl extends BaseDAOImpl<RegistrazioniCausali, PkId> implements RegistrazioniCausaliDAO {

    @Override
    public Class<RegistrazioniCausali> getEntityClass() {

	return RegistrazioniCausali.class;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RegistrazioniCausali> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RegistrazioniCausali> findByDescrizione(String descrizione) {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	det.add(Restrictions.eq("abilitato", true));
	return (List<RegistrazioniCausali>) getHibernateTemplate().findByCriteria(det);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RegistrazioniCausali> findByAbilitato() {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	det.add(Restrictions.eq("abilitato", true));
	return (List<RegistrazioniCausali>) getHibernateTemplate().findByCriteria(det);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RegistrazioniCausali> findByDescrizioneMercati(String descrizione) {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	det.add(Restrictions.eq("abilitato", true));
	det.add(Restrictions.eq("richiedePosteggio", true));
	return (List<RegistrazioniCausali>) getHibernateTemplate().findByCriteria(det);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RegistrazioniCausali> findByDescrizioneEscluseRiduzioni(String descrizione) {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	det.add(Restrictions.eq("abilitato", true));
	det.add(Restrictions.eq("richiedePosteggio", true));
	det.add(Restrictions.eq("nonPrevedeIncassi", false));
	det.add(Restrictions.eq("soloImportiNegativi", false));
	return (List<RegistrazioniCausali>) getHibernateTemplate().findByCriteria(det);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<RegistrazioniCausali> findByDescrizioneSoloRiduzioni(String descrizione) {

	// §§§BEGIN§§§
	DetachedCriteria det = getIdcomuneAndSoftwareCriteria();
	if (descrizione != null && !descrizione.equals("") && !descrizione.equals("%")) {
	    det.add(Restrictions.ilike("descrizione", descrizione, MatchMode.ANYWHERE));
	}
	det.add(Restrictions.eq("abilitato", true));
	det.add(Restrictions.eq("richiedePosteggio", true));
	det.add(Restrictions.eq("nonPrevedeIncassi", true));
	det.add(Restrictions.eq("soloImportiNegativi", true));
	return (List<RegistrazioniCausali>) getHibernateTemplate().findByCriteria(det);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
