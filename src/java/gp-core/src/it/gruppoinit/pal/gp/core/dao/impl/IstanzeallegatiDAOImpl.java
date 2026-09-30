package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeallegatiDAO;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.Allegati;
import it.gruppoinit.pal.gp.core.domain.Istanzeallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeallegatiDTO;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class IstanzeallegatiDAOImpl extends BaseDAOImpl<Istanzeallegati, PkId> implements IstanzeallegatiDAO {

    private static final Logger log = LoggerFactory.getLogger(IstanzeallegatiDAOImpl.class);

    @Override
    public Class<Istanzeallegati> getEntityClass() {

	return Istanzeallegati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzeallegati> findByIstanza(int codiceIstanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("istanza", "_istanza");
	det.add(Restrictions.eq("_istanza.id.codice", codiceIstanza));
	det.createAlias("inventarioprocedimenti", "_inventarioprocedimenti");
	det.addOrder(Order.asc("_inventarioprocedimenti.id.codice"));
	det.createAlias("allegati", "_allegati", Criteria.LEFT_JOIN);
	det.addOrder(Order.asc("_allegati.ordine"));
	det.addOrder(Order.asc("allegatoextra"));
	det.addOrder(Order.asc("id.codice"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzeallegati> findByIstanzaAndEndo(int codiceIstanza, int codiceInventario) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("istanza", "_istanza");
	det.createAlias("inventarioprocedimenti", "_inventarioprocedimenti");
	det.add(Restrictions.eq("_istanza.id.codice", codiceIstanza));
	det.add(Restrictions.eq("_inventarioprocedimenti.id.codice", codiceInventario));
	det.createAlias("allegati", "_allegati", Criteria.LEFT_JOIN);
	det.addOrder(Order.asc("_allegati.ordine"));
	det.addOrder(Order.asc("allegatoextra"));
	det.addOrder(Order.asc("id.codice"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Istanzeallegati> findByIstanzaOggetto(int codiceIstanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createAlias("istanza", "_istanza");
	det.add(Restrictions.eq("_istanza.id.codice", codiceIstanza));
	det.add(Restrictions.isNotNull("oggetto.id.codice"));
	det.createAlias("inventarioprocedimenti", "_inventarioprocedimenti");
	det.addOrder(Order.asc("_inventarioprocedimenti.id.codice"));
	det.createAlias("allegati", "_allegati", Criteria.LEFT_JOIN);
	det.addOrder(Order.asc("_allegati.ordine"));
	det.addOrder(Order.asc("allegatoextra"));
	det.addOrder(Order.asc("id.codice"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public int updateResettaRiferimentoAllegatoEndo(Allegati allegato) {

	String hql = "update Istanzeallegati ia set ia.allegatiId=null where ia.id.idcomune=? and ia.allegatiId=?";
	return getHibernateTemplate().bulkUpdate(hql, new Object[] { ORMHelper.getIdcomune(), allegato.getId().getCodice() });
    }

    @Override
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanza(Integer codiceIstanza) {

	return findIstanzeallegatiDTOByIstanza(codiceIstanza, null);
    }

    @Override
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("oggetto", "_oggetto", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("inventarioprocedimenti", "_inventarioprocedimenti");
	criteria.add(Restrictions.eq("_istanza.id.codice", codiceIstanza));
	if (isCodiceOggetto != null) {
	    log.debug("findIstanzeallegatiDTOByIstanza# isCodiceOggetto={}", isCodiceOggetto);
	    if (isCodiceOggetto) {
		criteria.add(Restrictions.isNotNull("_oggetto.id.codice"));
	    } else {
		criteria.add(Restrictions.isNull("_oggetto.id.codice"));
	    }
	}
	criteria.addOrder(Order.asc("allegatoextra"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("_istanza.id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	plist.add(Projections.property("allegatoextra"), "ALLEGATOEXTRA");
	plist.add(Projections.property("note"), "NOTE");
	plist.add(Projections.property("verificato"), "VERIFICATO");
	plist.add(Projections.property("controllook"), "CONTROLLOOK");
	plist.add(Projections.property("presente"), "PRESENTE");
	plist.add(Projections.property("stcIddocumento"), "STCIDDOCUMENTO");
	plist.add(Projections.property("stcIdallegato"), "STCIDALLEGATO");
	plist.add(Projections.property("necessario"), "NECESSARIO");
	plist.add(Projections.property("_oggetto.id.codice"), "CODICEOGGETTO");
	plist.add(Projections.property("_oggetto.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_oggetto.dimensioneFile"), "DIMENSIONEFILE");
	plist.add(Projections.property("_inventarioprocedimenti.procedimento"), "PROCEDIMENTO");
	plist.add(Projections.property("_inventarioprocedimenti.dataaggiornamento"), "DATAENDO");
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeallegatiDTO.class));
	List<IstanzeallegatiDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<IstanzeallegatiDTO> findIstanzeallegatiDTOByIstanzaAndEndo(Integer codiceIstanza, Integer codicendo,
	    TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// pongo le condizioni di where
	criteria.createAlias("istanza", "_istanza");
	criteria.createAlias("_istanza.comune", "_comune");
	criteria.add(Restrictions.eq("_istanza.id.codice", codiceIstanza));
	//criteria.createAlias("inventarioprocedimenti", "_inventarioprocedimenti");
	criteria.add(Restrictions.eq("_inventarioprocedimenti.id.codice", codicendo));
	switch (tipoRicercaDocumentoEnum) {
	case RICERCA_CON_OGGETTO:
	    //criteria.createAlias("oggetto", "_oggetto");
	    criteria.add(Restrictions.isNotNull("_oggetto.id.codice"));
	    break;
	case RICERCA_SENZA_OGGETTO:
	    //criteria.createAlias("oggetto", "_oggetto");
	    criteria.add(Restrictions.isNull("_oggetto.id.codice"));
	case RICERCA_TUTTI:
	    break;
	default:
	    break;
	}
	//
	// order by allegatoextra
	criteria.addOrder(Order.asc("allegatoextra"));
	//
	// Setto le projection
	ProjectionList plist = getProjection(criteria);
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeallegatiDTO.class));
	List<IstanzeallegatiDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    private ProjectionList getProjection(DetachedCriteria criteria) {

	// Condizioni projection su documenti istanza
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("_istanza.id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	plist.add(Projections.property("allegatoextra"), "ALLEGATOEXTRA");
	plist.add(Projections.property("note"), "NOTE");
	plist.add(Projections.property("verificato"), "VERIFICATO");
	plist.add(Projections.property("controllook"), "CONTROLLOOK");
	plist.add(Projections.property("presente"), "PRESENTE");
	plist.add(Projections.property("stcIddocumento"), "STCIDDOCUMENTO");
	plist.add(Projections.property("stcIdallegato"), "STCIDALLEGATO");
	// Condizione di join con oggetti
	criteria.createAlias("oggetto", "_oggetto", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("inventarioprocedimenti", "_inventarioprocedimenti");
	plist.add(Projections.property("_oggetto.id.codice"), "CODICEOGGETTO");
	plist.add(Projections.property("_oggetto.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_inventarioprocedimenti.procedimento"), "PROCEDIMENTO");
	plist.add(Projections.property("_comune.codicecomune"), "CODICECOMUNE");
	return plist;
    }
}
