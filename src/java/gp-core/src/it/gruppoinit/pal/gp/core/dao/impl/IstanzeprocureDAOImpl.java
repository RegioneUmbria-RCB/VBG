package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.IstanzeprocureDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.Istanzeprocure;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.IstanzeprocureDTO;
import it.gruppoinit.pal.gp.core.service.helper.TipoRicercaDocumentoEnum;

import java.util.ArrayList;
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

/**
 * 
 * @author
 */
@Repository
public class IstanzeprocureDAOImpl extends BaseDAOImpl<Istanzeprocure, PkId> implements IstanzeprocureDAO {

    private static final Logger log = LoggerFactory.getLogger(IstanzeprocureDAOImpl.class);

    @Override
    public Class<Istanzeprocure> getEntityClass() {

	return Istanzeprocure.class;
    }

    @Override
    public List<Istanzeprocure> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza) {

	DetachedCriteria criteria = getCriteriFindIstanzeProcureDTO(codiceIstanza, null);
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeprocureDTO.class));
	List<IstanzeprocureDTO> list = getHibernateTemplate().findByCriteria(criteria);
	return list;
    }

    //    @Override
    //    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza, TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum) {
    //
    //	DetachedCriteria criteria = null;
    //	List<IstanzeprocureDTO> list = new ArrayList<IstanzeprocureDTO>();
    //	switch (tipoRicercaDocumentoEnum) {
    //	case RICERCA_CON_OGGETTO:
    //	    //criteria = getCriteriFindIstanzeProcureDTO(codiceIstanza, true, null, null);
    //	    criteria = getCriteriFindIstanzeProcureDTO(codiceIstanza, true);
    //	    criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeprocureDTO.class));
    //	    list = getHibernateTemplate().findByCriteria(criteria);
    //	    break;
    //	case RICERCA_SENZA_OGGETTO:
    //	    //criteria = getCriteriFindIstanzeProcureDTO(codiceIstanza, false, null, null);
    //	    criteria = getCriteriFindIstanzeProcureDTO(codiceIstanza, false);
    //	    criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeprocureDTO.class));
    //	    list = getHibernateTemplate().findByCriteria(criteria);
    //	    break;
    //	case RICERCA_TUTTI:
    //	    criteria = getCriteriFindIstanzeProcureDTO(codiceIstanza, null);
    //	    criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeprocureDTO.class));
    //	    list = getHibernateTemplate().findByCriteria(criteria);
    //	    break;
    //	default:
    //	    throw new RuntimeException("Errore, tipo ricerca mancante");
    //	}
    //	return list;
    //    }

    private DetachedCriteria getCriteriFindIstanzeProcureDTO(Integer codiceIstanza, Boolean isCercaProcuraConOggetto) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("istanze", "_istanze");
	criteria.createAlias("anagrafeProcuratore", "_anagrafeProcuratore", Criteria.LEFT_JOIN);
	criteria.createAlias("_anagrafeProcuratore.formagiuridica", "_anagrafeProcuratoreFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("anagrafeRappresentato", "_anagrafeRappresentato", Criteria.LEFT_JOIN);
	criteria.createAlias("_anagrafeRappresentato.formagiuridica", "_anagrafeRappresentatoFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("oggetti", "_oggetti", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("oggettiDocIdent", "_oggettiDocIdent", DetachedCriteria.LEFT_JOIN);
	criteria.add(Restrictions.eq("_istanze.id.codice", codiceIstanza));
	//.Restriction per selezionare documento con o zenza oggetto
	//1. isCercaProcuraConOggetto==null : tutti
	//2. isCercaProcuraConOggetto==true : solo quelli con oggetto
	//3. isCercaProcuraConOggetto==false : solo quelli senza oggetto
	if (isCercaProcuraConOggetto != null) {
	    log.debug("getCriteriFindIstanzeProcureDTO# isCercaProcuraConOggetto={}", isCercaProcuraConOggetto);
	    if (isCercaProcuraConOggetto == true)
		criteria.add(Restrictions.isNotNull("_oggetti.id.codice"));
	    else {
		criteria.add(Restrictions.isNull("_oggetti.id.codice"));
	    }
	}
	criteria.addOrder(Order.asc("_anagrafeProcuratore.nominativo"));
	criteria.addOrder(Order.asc("_anagrafeProcuratore.nome"));
	ProjectionList plist = Projections.projectionList();
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	plist.add(Projections.property("stcIdDocumento"), "STCIDDOCUMENTO");
	plist.add(Projections.property("stcIdAllegato"), "STCIDALLEGATO");
	plist.add(Projections.property("controllook"), "CONTROLLOOK");
	plist.add(Projections.property("note"), "NOTE");
	plist.add(Projections.property("necessario"), "NECESSARIO");
	plist.add(Projections.property("presente"), "PRESENTE");
	plist.add(Projections.property("_istanze.id.codice"), "CODICEISTANZA");
	plist.add(Projections.property("_istanze.data"), "DATADOCUMENTO");
	plist.add(Projections.property("_oggetti.id.codice"), "CODICEOGGETTO");
	plist.add(Projections.property("_oggetti.nomefile"), "NOMEFILE");
	plist.add(Projections.property("_oggetti.dimensioneFile"), "DIMENSIONEFILE");
	plist.add(Projections.property("_oggettiDocIdent.id.codice"), "CODICEOGGETTODOCID");
	plist.add(Projections.property("_oggettiDocIdent.nomefile"), "NOMEFILEDOCID");
	plist.add(Projections.property("_oggettiDocIdent.dimensioneFile"), "DIMENSIONEFILEDOCID");
	plist.add(Projections.property("stcIdDocDocIde"), "STCIDDOCUMENTODOCID");
	plist.add(Projections.property("stcIdAllDocIde"), "STCIDALLEGATODOCID");
	//ANAGRAFEPROCURATORE
	plist.add(Projections.property("_anagrafeProcuratore.id.codice"), "ANAGRAFEPROCURATORE_ID_CODICE");
	plist.add(Projections.property("_anagrafeProcuratore.tipoanagrafe"), "ANAGRAFEPROCURATORE_TIPOANAGRAFE");
	plist.add(Projections.property("_anagrafeProcuratore.nominativo"), "ANAGRAFEPROCURATORE_NOMINATIVO");
	plist.add(Projections.property("_anagrafeProcuratore.nome"), "ANAGRAFEPROCURATORE_NOME");
	plist.add(Projections.property("_anagrafeProcuratore.codicefiscale"), "ANAGRAFEPROCURATORE_CODICEFISCALE");
	plist.add(Projections.property("_anagrafeProcuratoreFormaGiuridica.formagiuridica"), "ANAGRAFEPROCURATORE_FORMAGIURIDICA");
	plist.add(Projections.property("_anagrafeProcuratore.partitaiva"), "ANAGRAFEPROCURATORE_PARTITAIVA");
	plist.add(Projections.property("_anagrafeProcuratore.tipologia"), "ANAGRAFEPROCURATORE_TIPOLOGIA");
	plist.add(Projections.property("_anagrafeProcuratore.flagDisabilitato"), "ANAGRAFEPROCURATORE_FLAGDISABILITATO");
	//ANAGRAFERAPPRESENTATO
	plist.add(Projections.property("_anagrafeRappresentato.id.codice"), "ANAGRAFERAPPRESENTATO_ID_CODICE");
	plist.add(Projections.property("_anagrafeRappresentato.tipoanagrafe"), "ANAGRAFERAPPRESENTATO_TIPOANAGRAFE");
	plist.add(Projections.property("_anagrafeRappresentato.nominativo"), "ANAGRAFERAPPRESENTATO_NOMINATIVO");
	plist.add(Projections.property("_anagrafeRappresentato.nome"), "ANAGRAFERAPPRESENTATO_NOME");
	plist.add(Projections.property("_anagrafeRappresentato.codicefiscale"), "ANAGRAFERAPPRESENTATO_CODICEFISCALE");
	plist.add(Projections.property("_anagrafeRappresentatoFormaGiuridica.formagiuridica"), "ANAGRAFERAPPRESENTATO_FORMAGIURIDICA");
	plist.add(Projections.property("_anagrafeRappresentato.partitaiva"), "ANAGRAFERAPPRESENTATO_PARTITAIVA");
	plist.add(Projections.property("_anagrafeRappresentato.tipologia"), "ANAGRAFERAPPRESENTATO_TIPOLOGIA");
	plist.add(Projections.property("_anagrafeRappresentato.flagDisabilitato"), "ANAGRAFERAPPRESENTATO_FLAGDISABILITATO");
	criteria.setProjection(plist);
	return criteria;
    }

    @Override
    public List<IstanzeprocureDTO> findIstanzeprocureDTOByIstanza(Integer codiceIstanza, TipoRicercaDocumentoEnum tipoRicercaDocumentoEnum) {

	List<IstanzeprocureDTO> list = new ArrayList<IstanzeprocureDTO>();
	DetachedCriteria criteria = null;
	switch (tipoRicercaDocumentoEnum) {
	case RICERCA_CON_OGGETTO:
	    criteria = getCriteriFindIstanzeProcureDTO(codiceIstanza, true);
	    criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeprocureDTO.class));
	    list = getHibernateTemplate().findByCriteria(criteria);
	    break;
	case RICERCA_SENZA_OGGETTO:
	    criteria = getCriteriFindIstanzeProcureDTO(codiceIstanza, false);
	    criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(IstanzeprocureDTO.class));
	    list = getHibernateTemplate().findByCriteria(criteria);
	    break;
	case RICERCA_TUTTI:
	    list = this.findIstanzeprocureDTOByIstanza(codiceIstanza);
	    break;
	default:
	    throw new RuntimeException("Errore, tipo ricerca mancante");
	}
	return list;
    }

   
}
