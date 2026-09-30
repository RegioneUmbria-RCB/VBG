package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AlboPubblicazioniAllegatiDAO;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioni;
import it.gruppoinit.pal.gp.core.domain.AlboPubblicazioniAllegati;
import it.gruppoinit.pal.gp.core.domain.Oggetti;
import it.gruppoinit.pal.gp.core.domain.PkId;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class AlboPubblicazioniAllegatiDAOImpl extends BaseDAOImpl<AlboPubblicazioniAllegati, PkId> implements AlboPubblicazioniAllegatiDAO {

    @Override
    public Class<AlboPubblicazioniAllegati> getEntityClass() {

	return AlboPubblicazioniAllegati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Oggetti findByPubblicazioneEOggetti(Oggetti oggetti, AlboPubblicazioni alboPubblicazioni) {

	// §§§BEGIN§§§
	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	if (alboPubblicazioni.getId().getCodice() != null) {
	    detachedCriteria.add(Restrictions.eq("alboPubblicazioni.id.codice", alboPubblicazioni.getId().getCodice()));
	}
	if (oggetti.getId().getCodice() != null) {
	    detachedCriteria.add(Restrictions.eq("oggetti.id.codice", oggetti.getId().getCodice()));
	}
	List<AlboPubblicazioniAllegati> list = getHibernateTemplate().findByCriteria(detachedCriteria);
	if (!list.isEmpty() && list.size() == 1) {
	    return oggetti;
	}
	return null;
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AlboPubblicazioniAllegati> findOrderByOrdine(AlboPubblicazioni alboPubblicazioni) {

	// §§§BEGIN§§§
	DetachedCriteria detachedCriteria = getIdcomuneCriteria();
	detachedCriteria.add(Restrictions.eq("alboPubblicazioni.id.codice", alboPubblicazioni.getId().getCodice()));
	detachedCriteria.addOrder(Order.asc("ordine"));
	return getHibernateTemplate().findByCriteria(detachedCriteria);
	// §§§END§§§
	// @@@ALTERNATIVEEXIT@@@ return null;@@@ENDALTERNATIVEEXIT@@@
    }
}
