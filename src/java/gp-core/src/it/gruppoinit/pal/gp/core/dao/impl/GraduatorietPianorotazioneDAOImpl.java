package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.GraduatorietPianorotazioneDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.GiorniSettimanaEnum;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.Graduatoriet;
import it.gruppoinit.pal.gp.core.domain.GraduatorietPianorotazione;
import it.gruppoinit.pal.gp.core.domain.MercatiD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.GraduatorietPianoRotazioneDTO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class GraduatorietPianorotazioneDAOImpl extends BaseDAOImpl<GraduatorietPianorotazione, PkId> implements GraduatorietPianorotazioneDAO {

    @Override
    public Class<GraduatorietPianorotazione> getEntityClass() {

	return GraduatorietPianorotazione.class;
    }

    @Override
    public List<GraduatorietPianorotazione> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, null, DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<GraduatorietPianoRotazioneDTO> findByGraduatorieT(Graduatoriet graduatoriat, MercatiD mercatiD,
	    GiorniSettimanaEnum giorniSettimanaEnum) {

	DetachedCriteria criteria = getBaseCriteriaFilterByGraduatoriaT(graduatoriat, null);
	// CRITERI WHERE MERCATIUSO
	criteria.add(Restrictions.eq("_mercatiUso.descrizione", giorniSettimanaEnum.getValore()));
	criteria.add(Restrictions.eq("_mercatiD.id.codice", mercatiD.getId().getCodice()));
	return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * <pre>
     * Il metodo privato ha il compito di settare il criteri di base di:
     * 		a.SELECT	:
     * 		b.WHERE 	: graduatoriet.id.codice
     * 		c.ORDERBY 	: posizione
     * @param graduatoriet
     * @return criteria :DetachedCriteria
     * </pre>
     */
    private DetachedCriteria getBaseCriteriaFilterByGraduatoriaT(Graduatoriet graduatoriet, DAOOrderTypeEnum daoOrderTypeEnum) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("graduatoried", "_graduatoried");
	criteria.createAlias("_graduatoried.graduatoriet", "_graduatoriet");
	criteria.add(Restrictions.eq("_graduatoriet.id.codice", graduatoriet.getId().getCodice()));
	criteria.createAlias("istanze", "_istanza");
	//criteria.createAlias("_istanza.autorizzazionis", "_aut", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("_aut.autorizcomune", "_autorizcomune", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("_aut.tipologiaregistro", "_autorizregistro", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("_aut.autorizzazioniConcessionisForFkAutconcAutatt", "_conc", DetachedCriteria.LEFT_JOIN);
	//criteria.createAlias("_conc.mercatiD", "_mercatiD", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.richiedente", "_richiedente", Criteria.LEFT_JOIN);
	criteria.createAlias("_richiedente.formagiuridica", "_richiedenteFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegale", "_titolarelegale", Criteria.LEFT_JOIN);
	criteria.createAlias("_titolarelegale.formagiuridica", "_titolarelegaleFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.tipisoggetto", "_tipisoggetto", Criteria.LEFT_JOIN);
	criteria.createAlias("mercatiUso", "_mercatiUso");
	criteria.createAlias("mercatiD", "_mercatiD");
	// CRITERI ORDER
	//
	//criteria.addOrder(Order.asc("_mercatiD.codiceposteggio"));
	criteria.addOrder(OrderBySqlFormula.asc("_mercatiD.codiceposteggio", FunctionsEnum.NVL_FUNCTION, "'ZZZZZZZZZZZZZZZZZZZZ'"));
	//	switch (daoOrderTypeEnum) {
	//	case ASC:
	//	    criteria.addOrder(Order.asc("posizione"));
	//	    break;
	//	case DESC:
	//	    criteria.addOrder(Order.desc("posizione"));
	//	default:
	//	    criteria.addOrder(Order.asc("posizione"));
	//	}
	ProjectionList plist = Projections.projectionList();
	//GRADUATORIED
	//plist.add(Projections.property("id.codice"), "ID_CODICE");
	//plist.add(Projections.property("posizione"), "POSIZIONE");
	//ISTANZA
	plist.add(Projections.property("_istanza.id.codice"), "ISTANZE_ID_CODICE");
	plist.add(Projections.property("_istanza.numeroistanza"), "ISTANZE_NUMEROISTANZA");
	//plist.add(Projections.property("_istanza.software.codice"), "ISTANZA_SOFTWARE");
	//CONCESSIONI
	//plist.add(Projections.property("_aut.id.codice"), "CONCESSIONE_ID_CODICE");
	//plist.add(Projections.property("_aut.autoriznumero"), "CONCESSIONE_AUTORIZNUMERO");
	//plist.add(Projections.property("_aut.autorizdata"), "CONCESSIONE_AUTORIZDATA");
	//plist.add(Projections.property("_autorizcomune.comune"), "CONCESSIONE_AUTORIZCOMUNE");
	//plist.add(Projections.property("_autorizregistro.trDescrizione"), "CONCESSIONE_TIPOLOGIAREGISTRO");
	//plist.add(Projections.property("_aut.flagAttiva"), "CONCESSIONE_FLAGATTIVA");
	//plist.add(Projections.property("_aut.dataCessazione"), "CONCESSIONE_DATACESSAZIONE");
	//plist.add(Projections.property("_mercatiD.codiceposteggio"), "CONCESSIONE_CODICEPOSTEGGIO");
	// TIPI SOGGETTO
	//plist.add(Projections.property("_tipisoggetto.tiposoggetto"), "ISTANZA_TIPOSOGGETTO");
	//RICHIEDENTE
	plist.add(Projections.property("_richiedente.id.codice"), "ISTANZE_RICHIEDENTE_ID_CODICE");
	plist.add(Projections.property("_richiedente.tipoanagrafe"), "ISTANZE_RICHIEDENTE_TIPOANAGRAFE");
	plist.add(Projections.property("_richiedente.nominativo"), "ISTANZE_RICHIEDENTE_NOMINATIVO");
	plist.add(Projections.property("_richiedente.nome"), "ISTANZE_RICHIEDENTE_NOME");
	plist.add(Projections.property("_richiedente.codicefiscale"), "ISTANZE_RICHIEDENTE_CODICEFISCALE");
	plist.add(Projections.property("_richiedenteFormaGiuridica.formagiuridica"), "ISTANZE_RICHIEDENTE_FORMAGIURIDICA");
	plist.add(Projections.property("_richiedente.partitaiva"), "ISTANZE_RICHIEDENTE_PARTITAIVA");
	plist.add(Projections.property("_richiedente.tipologia"), "ISTANZE_RICHIEDENTE_TIPOLOGIA");
	plist.add(Projections.property("_richiedente.flagDisabilitato"), "ISTANZE_RICHIEDENTE_FLAGDISABILITATO");
	//MERCATIUSO
	plist.add(Projections.property("_mercatiUso.id.codice"), "MERCATIUSO_ID_CODICE");
	plist.add(Projections.property("_mercatiUso.descrizione"), "MERCATIUSO_DESCRIZIONE");
	// POSTEGGIO
	plist.add(Projections.property("_mercatiD.id.codice"), "MERCATID_ID_CODICE");
	plist.add(Projections.property("_mercatiD.codiceposteggio"), "MERCATID_CODICEPOSTEGGIO");
	//TITOLARE LEGALE
	//	plist.add(Projections.property("_titolarelegale.id.codice"), "ISTANZA_TITOLARELEGALE_ID_CODICE");
	//	plist.add(Projections.property("_titolarelegale.tipoanagrafe"), "ISTANZA_TITOLARELEGALE_TIPOANAGRAFE");
	//	plist.add(Projections.property("_titolarelegale.nominativo"), "ISTANZA_TITOLARELEGALE_NOMINATIVO");
	//	plist.add(Projections.property("_titolarelegale.nome"), "ISTANZA_TITOLARELEGALE_NOME");
	//	plist.add(Projections.property("_titolarelegale.codicefiscale"), "ISTANZA_TITOLARELEGALE_CODICEFISCALE");
	//	plist.add(Projections.property("_titolarelegaleFormaGiuridica.formagiuridica"), "ISTANZA_TITOLARELEGALE_FORMAGIURIDICA");
	//	plist.add(Projections.property("_titolarelegale.partitaiva"), "ISTANZA_TITOLARELEGALE_PARTITAIVA");
	//	plist.add(Projections.property("_titolarelegale.tipologia"), "ISTANZA_TITOLARELEGALE_TIPOLOGIA");
	//	plist.add(Projections.property("_titolarelegale.flagDisabilitato"), "ISTANZA_TITOLARELEGALE_FLAGDISABILITATO");
	//
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(GraduatorietPianoRotazioneDTO.class));
	return criteria;
    }

    @Override
    public List<GraduatorietPianoRotazioneDTO> findByGraduatorieTAndIstanza(Graduatoriet graduatoriat, Integer codiceIstanza) {

	DetachedCriteria criteria = getBaseCriteriaFilterByGraduatoriaT(graduatoriat, null);
	criteria.add(Restrictions.eq("_istanza.id.codice", codiceIstanza));
	return getHibernateTemplate().findByCriteria(criteria);
    }
}
