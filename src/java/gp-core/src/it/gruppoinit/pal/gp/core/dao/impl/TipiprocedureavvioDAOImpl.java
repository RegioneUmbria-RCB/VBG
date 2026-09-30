package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipiprocedureavvioDAO;
import it.gruppoinit.pal.gp.core.domain.Tipiprocedureavvio;
import it.gruppoinit.pal.gp.core.domain.TipiprocedureavvioId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipiprocedureavvioDAOImpl extends BaseDAOImpl<Tipiprocedureavvio, TipiprocedureavvioId> implements TipiprocedureavvioDAO {

    @Override
    public Class<Tipiprocedureavvio> getEntityClass() {

	return Tipiprocedureavvio.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Boolean isMovimentoAvvioDefault(Tipiprocedureavvio tipiprocedureavvio) {

	Boolean isPresent = false;
	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	detachedCriteria.add(Restrictions.eq("tipoProcedura.id.codice", tipiprocedureavvio.getTipoProcedura().getId().getCodice()));
	detachedCriteria.add(Restrictions.eq("defaultsn", true));
	List<Tipiprocedureavvio> list = getHibernateTemplate().findByCriteria(detachedCriteria);
	if (!list.isEmpty()) {
	    isPresent = true;
	}
	return isPresent;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Tipiprocedureavvio findTipiprocedureavvioDeafult(Tipiprocedureavvio tipiprocedureavvio) {

	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	detachedCriteria.add(Restrictions.eq("tipoProcedura.id.codice", tipiprocedureavvio.getTipoProcedura().getId().getCodice()));
	detachedCriteria.add(Restrictions.eq("defaultsn", true));
	List<Tipiprocedureavvio> list = getHibernateTemplate().findByCriteria(detachedCriteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	} else {
	    return null;
	}
    }
}
