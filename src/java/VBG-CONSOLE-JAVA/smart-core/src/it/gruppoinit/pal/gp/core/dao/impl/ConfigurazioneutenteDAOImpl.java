package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ConfigurazioneutenteDAO;
import it.gruppoinit.pal.gp.core.domain.Configurazioneutente;
import it.gruppoinit.pal.gp.core.domain.ConfigurazioneutenteId;
import it.gruppoinit.pal.gp.core.domain.Responsabili;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class ConfigurazioneutenteDAOImpl extends BaseDAOImpl<Configurazioneutente, ConfigurazioneutenteId> implements ConfigurazioneutenteDAO {

    @Override
    public Class<Configurazioneutente> getEntityClass() {

	return Configurazioneutente.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Configurazioneutente> findByResponsabile(Responsabili responsabile) {

	if (responsabile == null) {
	    throw new RuntimeException("Il Responsabile non può essere nullo");
	}
	if (responsabile.getId() == null) {
	    throw new RuntimeException("Il responsabile non può essere nullo");
	}
	Integer codiceResponsabile = responsabile.getId().getCodice();
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("responsabile.id.codice", codiceResponsabile));
	return (List<Configurazioneutente>) getHibernateTemplate().findByCriteria(det);
    }
}
