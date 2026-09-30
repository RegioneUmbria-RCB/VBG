package it.gruppoinit.stc.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.stc.dao.MessaggiattivitaDAO;
import it.gruppoinit.stc.domain.Messaggiattivita;
import it.gruppoinit.stc.service.MessaggiattivitaService.TIPO_COLLEGAMENTO;

@Repository
public class MessaggiattivitaDAOImpl extends BaseDAOImpl<Messaggiattivita, Integer> implements MessaggiattivitaDAO {

    @Override
    public Class<Messaggiattivita> getEntityClass() {

	return Messaggiattivita.class;
    }

    @Override
    public List<Messaggiattivita> findByIdAttivita(int idAttivita, TIPO_COLLEGAMENTO collegamento) {

	DetachedCriteria criteria = getDefaultCriteria();
	if (collegamento.equals(TIPO_COLLEGAMENTO.RICHIESTA)) {
	    criteria.add(Restrictions.eq("attivitaByFkidrichiesta.id", idAttivita));
	} else {
	    criteria.add(Restrictions.eq("attivitaByFkidrisposta.id", idAttivita));
	}
	return this.getHibernateTemplate().findByCriteria(criteria);
    }
}
