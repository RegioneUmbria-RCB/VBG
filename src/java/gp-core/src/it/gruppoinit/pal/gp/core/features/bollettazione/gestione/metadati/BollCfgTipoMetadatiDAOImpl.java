package it.gruppoinit.pal.gp.core.features.bollettazione.gestione.metadati;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipo;
import it.gruppoinit.pal.gp.core.domain.BollCfgTipoMetadati;
import it.gruppoinit.pal.gp.core.domain.Comuni;
import it.gruppoinit.pal.gp.core.domain.PkId;

@Repository
public class BollCfgTipoMetadatiDAOImpl extends BaseDAOImpl<BollCfgTipoMetadati, PkId> implements BollCfgTipoMetadatiDAO {

    @Override
    public Class<BollCfgTipoMetadati> getEntityClass() {

	return BollCfgTipoMetadati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BollCfgTipoMetadati> findMetadatiConfigurati(Integer codiceBollcfgTipo) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	criteria.add(Restrictions.eq("bollCfgTipo.id.codice", codiceBollcfgTipo));
	criteria.addOrder(Order.asc("id.idcomune"));
	criteria.addOrder(Order.asc("bollCfgTipo.id.codice"));
	criteria.addOrder(Order.asc("chiave"));
	List<BollCfgTipoMetadati> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public void updateMetadato(UpdateMetadatoRequest jsonRequest) {

	if (jsonRequest == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo updateMetadato senza passare i riferimenti da aggiornare");
	}
	if (jsonRequest.getId() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo updateMetadato senza passare il riferimento della riga da aggiornare");
	}
	if (jsonRequest.getIdCfgTipo() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo updateMetadato senza passare il riferimento della configurazione della bollettazione");
	}
	if (StringUtils.isBlank(jsonRequest.getChiave())) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo updateMetadato senza passare il riferimento del metadato");
	}
	if (StringUtils.isBlank(jsonRequest.getValore())) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo updateMetadato senza passare il valore del metadato");
	}
	BollCfgTipoMetadati metadato = new BollCfgTipoMetadati(jsonRequest.getId());
	metadato.setBollCfgTipo(new BollCfgTipo(jsonRequest.getIdCfgTipo()));
	if (!StringUtils.isEmpty(jsonRequest.getCodiceComune())) {
	    metadato.setComune(new Comuni(jsonRequest.getCodiceComune()));
	}
	metadato.setChiave(jsonRequest.getChiave());
	metadato.setValore(jsonRequest.getValore());
	this.update(metadato);
    }

    @Override
    public void deleteMetadato(DeleteMetadatoRequest jsonRequest) {

	if (jsonRequest == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo deleteMetadato senza passare i riferimenti da aggiornare");
	}
	if (jsonRequest.getId() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo deleteMetadato senza passare il riferimento della riga da aggiornare");
	}
	this.delete(this.findById(new PkId(jsonRequest.getId())));
    }

    @Override
    public int insertMetadato(InsertMetadatoRequest jsonRequest) {

	if (jsonRequest == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insertMetadato senza passare i riferimenti da aggiornare");
	}
	if (jsonRequest.getIdCfgTipo() == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo insertMetadato senza passare il riferimento della configurazione della bollettazione");
	}
	if (StringUtils.isBlank(jsonRequest.getChiave())) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insertMetadato senza passare il riferimento del metadato");
	}
	if (StringUtils.isBlank(jsonRequest.getValore())) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo insertMetadato senza passare il valore del metadato");
	}
	BollCfgTipoMetadati metadato = new BollCfgTipoMetadati();
	metadato.setBollCfgTipo(new BollCfgTipo(jsonRequest.getIdCfgTipo()));
	if (!StringUtils.isEmpty(jsonRequest.getCodiceComune())) {
	    metadato.setComune(new Comuni(jsonRequest.getCodiceComune()));
	} else {
	    metadato.setComune(null);
	}
	metadato.setChiave(jsonRequest.getChiave());
	metadato.setValore(jsonRequest.getValore());
	this.insert(metadato);
	return metadato.getId().getCodice();
    }
}
