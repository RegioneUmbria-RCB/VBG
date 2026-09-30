package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.MovimentiallegatiDAO;
import it.gruppoinit.pal.gp.core.domain.Movimentiallegati;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.MovimentiallegatiDTO;

import java.util.List;

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
public class MovimentiallegatiDAOImpl extends BaseDAOImpl<Movimentiallegati, PkId> implements MovimentiallegatiDAO {

    private static final Logger log = LoggerFactory.getLogger(MovimentiallegatiDAOImpl.class);

    @Override
    public Class<Movimentiallegati> getEntityClass() {

	return Movimentiallegati.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movimentiallegati> findByIstanza(int codiceIstanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("movimento", "_movimento");
	det.add(Restrictions.eq("_movimento.istanza.id.codice", codiceIstanza));
	det.addOrder(Order.asc("descrizione"));
	det.addOrder(Order.asc("id.codice"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movimentiallegati> findByMovimento(int codiceMovimento) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("movimentoId", codiceMovimento));
	det.addOrder(Order.asc("descrizione"));
	det.addOrder(Order.asc("id.codice"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Movimentiallegati> findByIstanzaOggetto(int codiceIstanza, int codicemovimento) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("movimento", "_movimento");
	det.add(Restrictions.eq("movimentoId", codicemovimento));
	det.add(Restrictions.eq("_movimento.istanza.id.codice", codiceIstanza));
	det.add(Restrictions.isNotNull("oggetto.id.codice"));
	det.addOrder(Order.asc("descrizione"));
	det.addOrder(Order.asc("id.codice"));
	return getHibernateTemplate().findByCriteria(det);
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza, Boolean isCodiceOggetto) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("movimento", "_movimento");
	criteria.createAlias("_movimento.istanza", "_istanza");
	criteria.createAlias("_movimento.responsabile", "_responsabile");
	criteria.createAlias("_movimento.tipomovimento", "_tipomovimento");
	criteria.createAlias("oggetto", "_oggetto", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_movimento.amministrazioni", "_amministrazioni");
	criteria.createAlias("_istanza.comune", "_comune");
	criteria.add(Restrictions.eq("_istanza.id.codice", codiceIstanza));
	criteria.addOrder(Order.desc("dataregistrazione"));
	if (isCodiceOggetto != null) {
	    log.debug("findMovimentiallegatiDTOByIstanza# isCodiceOggetto={}", isCodiceOggetto);
	    if (isCodiceOggetto) {
		criteria.add(Restrictions.isNotNull("_oggetto.id.codice"));
	    } else {
		criteria.add(Restrictions.isNull("_oggetto.id.codice"));
	    }
	}
	criteria.addOrder(Order.asc("descrizione"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("_istanza.id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	plist.add(Projections.property("dataregistrazione"), "DATAREGISTRAZIONE");
	plist.add(Projections.property("descrizione"), "DESCRIZIONE");
	plist.add(Projections.property("controllook"), "CONTROLLOOK");
	plist.add(Projections.property("note"), "NOTE");
	plist.add(Projections.property("flagPubblica"), "FLAGPUBBLICA");
	plist.add(Projections.property("stcIddocumento"), "STCIDDOCUMENTO");
	plist.add(Projections.property("stcIdallegato"), "STCIDALLEGATO");
	plist.add(Projections.property("_movimento.id.codice"), "CODICEMOVIMENTO");
	plist.add(Projections.property("_movimento.movimento"), "DESCRIZIONEMOVIMENTO");
	plist.add(Projections.property("_movimento.numeroprotocollo"), "NUMEROPROTOCOLLO");
	plist.add(Projections.property("_movimento.dataprotocollo"), "DATAPROTOCOLLO");
	plist.add(Projections.property("_movimento.data"), "DATAMOVIMENTO");
	plist.add(Projections.property("_responsabile.responsabile"), "RESPONSABILEMOVIMENTO");
	plist.add(Projections.property("_oggetto.id.codice"), "CODICEOGGETTO");
	plist.add(Projections.property("_oggetto.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_oggetto.dimensioneFile"), "DIMENSIONEFILE");
	plist.add(Projections.property("_amministrazioni.amministrazione"), "AMMINISTRAZIONE");
	plist.add(Projections.property("_tipomovimento.id.tipomovimento"), "TIPOMOVIMENTO");
	plist.add(Projections.property("_tipomovimento.movimento"), "DESCTIPOMOVIMENTO");
	plist.add(Projections.property("_comune.codicecomune"), "CODICECOMUNE");
	criteria.setProjection(plist);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MovimentiallegatiDTO.class));
	List<MovimentiallegatiDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByIstanza(Integer codiceIstanza) {

	return findMovimentiallegatiDTOByIstanza(codiceIstanza, null);
    }

    @Override
    public List<MovimentiallegatiDTO> findMovimentiallegatiDTOByMovimenti(Integer codiceMovimento) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// pongo le condizioni di where
	criteria.createAlias("movimento", "_movimento", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_movimento.istanza", "_istanza");
	criteria.createAlias("_movimento.responsabile", "_responsabile");
	criteria.createAlias("_movimento.tipomovimento", "_tipomovimento");
	criteria.createAlias("_istanza.comune", "_comune");
	criteria.add(Restrictions.eq("_movimento.id.codice", codiceMovimento));
	//
	// order by codiceposteggio
	criteria.addOrder(Order.desc("dataregistrazione"));
	criteria.addOrder(Order.asc("descrizione"));
	criteria.addOrder(Order.desc("id.codice"));
	//
	// Condizioni projection su documenti istanza
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("_istanza.id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	plist.add(Projections.property("dataregistrazione"), "DATAREGISTRAZIONE");
	plist.add(Projections.property("descrizione"), "DESCRIZIONE");
	plist.add(Projections.property("controllook"), "CONTROLLOOK");
	plist.add(Projections.property("note"), "NOTE");
	plist.add(Projections.property("flagPubblica"), "FLAGPUBBLICA");
	plist.add(Projections.property("stcIddocumento"), "STCIDDOCUMENTO");
	plist.add(Projections.property("stcIdallegato"), "STCIDALLEGATO");
	plist.add(Projections.property("idBase"), "IDBASE");
	plist.add(Projections.property("messageId"), "MESSAGEID");
	plist.add(Projections.property("_comune.codicecomune"), "CODICECOMUNE");
	plist.add(Projections.property("_movimento.id.codice"), "CODICEMOVIMENTO");
	plist.add(Projections.property("_movimento.movimento"), "DESCRIZIONEMOVIMENTO");
	plist.add(Projections.property("_movimento.data"), "DATAMOVIMENTO");
	plist.add(Projections.property("_responsabile.responsabile"), "RESPONSABILEMOVIMENTO");
	// Condizione di join con oggetti
	criteria.createAlias("oggetto", "_oggetto", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_movimento.amministrazioni", "_amministrazioni");
	plist.add(Projections.property("_oggetto.id.codice"), "CODICEOGGETTO");
	plist.add(Projections.property("_oggetto.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_amministrazioni.amministrazione"), "AMMINISTRAZIONE");
	plist.add(Projections.property("_tipomovimento.id.tipomovimento"), "TIPOMOVIMENTO");
	plist.add(Projections.property("_tipomovimento.movimento"), "DESCTIPOMOVIMENTO");
	plist.add(Projections.property("_comune.codicecomune"), "CODICECOMUNE");
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(MovimentiallegatiDTO.class));
	List<MovimentiallegatiDTO> list = getHibernateTemplate().findByCriteria(criteria);
	if (log.isDebugEnabled()) {
	    log.debug("findMovimentiallegatiDTOByMovimenti# la lista contiene elementi :", list.isEmpty());
	}
	return list;
    }
}
