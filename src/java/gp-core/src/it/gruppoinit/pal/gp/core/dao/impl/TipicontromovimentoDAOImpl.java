package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.TipicontromovimentoDAO;
import it.gruppoinit.pal.gp.core.domain.Amministrazioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.Tipicontromovimento;
import it.gruppoinit.pal.gp.core.domain.Tipimovimento;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class TipicontromovimentoDAOImpl extends BaseDAOImpl<Tipicontromovimento, PkId> implements TipicontromovimentoDAO {

    @Override
    public Class<Tipicontromovimento> getEntityClass() {

	return Tipicontromovimento.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipicontromovimento> findTipicontromovimentiByAmministrazioniTipiContromovimento(Amministrazioni amministrazioni) {

	DetachedCriteria criteria = criteriaFilterTypeAmministrazione(amministrazioni, "amministrazioni_tipo_contromovimento");
	List<Tipicontromovimento> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Tipicontromovimento> findTipicontromovimentiByAmministrazioniTipiMovimento(Amministrazioni amministrazioni) {

	DetachedCriteria criteria = criteriaFilterTypeAmministrazione(amministrazioni, "amministrazioni_tipo_movimento");
	List<Tipicontromovimento> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    private DetachedCriteria criteriaFilterTypeAmministrazione(Amministrazioni amministrazioni, String typeAmministrazione) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	if (typeAmministrazione.equals("amministrazioni_tipo_movimento")) {
	    criteria.add(Restrictions.eq("amministrazioniTipiMovimento.id.codice", amministrazioni.getId().getCodice()));
	}
	if (typeAmministrazione.equals("amministrazioni_tipo_contromovimento")) {
	    criteria.add(Restrictions.eq("amministrazioniTipiContromovimento.id.codice", amministrazioni.getId().getCodice()));
	}
	return criteria;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Tipicontromovimento findByTipoMovimentoAndTipoContromovimento(Tipimovimento tipomovimento, Tipimovimento tipocontromovimento) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("tipomovimento.id.tipomovimento", tipomovimento.getId().getTipomovimento()));
	criteria.add(Restrictions.eq("tipocontromovimento.id.tipomovimento", tipocontromovimento.getId().getTipomovimento()));
	List<Tipicontromovimento> list = getHibernateTemplate().findByCriteria(criteria);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }
}
