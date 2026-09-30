package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AnagrafedocumentiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.domain.Anagrafe;
import it.gruppoinit.pal.gp.core.domain.Anagrafedocumenti;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.AnagrafedocumentiDTO;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author francescop
 * @author gianpaolot
 */
@Repository
public class AnagrafedocumentiDAOImpl extends BaseDAOImpl<Anagrafedocumenti, PkId> implements AnagrafedocumentiDAO {

    private static final Logger log = LoggerFactory.getLogger(AnagrafedocumentiDAOImpl.class);

    @Override
    public Class<Anagrafedocumenti> getEntityClass() {

	return Anagrafedocumenti.class;
    }

    @Override
    public List<Anagrafedocumenti> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, null);
    }

    /**
     * Ritorna tutti i documenti che hanno il riferimento all'istanza e all'anagrafica passata, il parametro
     * isDocumentoPresente ci permette eventualmnete di filtrare solo i record che contengono l'allegato fisico (In
     * questi caso non sarnno presenti tutti i campi contenuti nell'oggetto Anagrafedocumenti )
     * 
     * @param istanza
     * @param anagrafe
     * @param isDocumentoPresente
     * 
     * @return List<AnagrafedocumentiDTO>
     */
    @Override
    public List<AnagrafedocumentiDTO> findByIstanzaAndAnagrafeDTO(Istanze istanza, Anagrafe anagrafe, boolean isDocumentoPresente) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// Condizioni di join
	criteria.createAlias("istanza", "_istanza", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("oggetto", "_oggetto", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("tipidocumento", "_tipidocumento", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("anagrafe", "_anagrafe", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.comune", "_comune", DetachedCriteria.LEFT_JOIN);
	// pongo le condizioni di where
	if (istanza != null) {
	    if (istanza.getId() != null) {
		if (istanza.getId().getCodice() != null) {
		    criteria.add(Restrictions.eq("_istanza.id.codice", istanza.getId().getCodice()));
		}
	    }
	}
	criteria.add(Restrictions.eq("_anagrafe.id.codice", anagrafe.getId().getCodice()));
	// Condizione che mi permette di scegliere se visualizzare tutti i documenti  o solo quelli
	// con allegato fisico
	if (isDocumentoPresente == true) {
	    criteria.add(Restrictions.isNotNull("_oggetto.id.codice"));
	}
	//	// order by codiceposteggio
	//	criteria.addOrder(Order.asc("documento"));
	//
	// Condizioni projection su documenti istanza
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	//plist.add(Projections.property("data"), "DATA");
	plist.add(Projections.property("_tipidocumento.documento"), "DOCUMENTO");
	plist.add(Projections.property("_istanza.id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("_oggetto.id.codice"), "CODICEOGGETTO");
	plist.add(Projections.property("_oggetto.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_anagrafe.nome"), "NOME");
	plist.add(Projections.property("_anagrafe.nominativo"), "NOMINATIVO");
	plist.add(Projections.property("_comune.codicecomune"), "CODICECOMUNE");
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(AnagrafedocumentiDTO.class));
	List<AnagrafedocumentiDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }
}
