package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniDConcessioniDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniDConcessioni;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.helper.ComunicazioniDConcessioniDTO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

/*
 * 
 * @author
 */
@Repository
public class ComunicazioniDConcessioniDAOImpl extends BaseDAOImpl<ComunicazioniDConcessioni, PkId> implements ComunicazioniDConcessioniDAO {

    @Override
    public Class<ComunicazioniDConcessioni> getEntityClass() {

	return ComunicazioniDConcessioni.class;
    }

    @Override
    public List<ComunicazioniDConcessioni> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }

    @Override
    public List<ComunicazioniDConcessioniDTO> findByComunicazioniT(Integer codiceComunicazione, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getBaseCriteriaFilterByComunicazioneT(codiceComunicazione);
	List<ComunicazioniDConcessioniDTO> list = null;
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	return list;
    }

    private DetachedCriteria getBaseCriteriaFilterByComunicazioneT(Integer codiceComunicazione) {

	DetachedCriteria criteria = getIdcomuneCriteria();
	// alias
	criteria.createAlias("comunicazioniD", "_comunicazioniD");
	criteria.createAlias("_comunicazioniD.comunicazioniT", "_comunicazioniT");
	criteria.createAlias("autorizzazioniConcessioni", "_autorizzazioniConcessioni");
	criteria.createAlias("_autorizzazioniConcessioni.autorizzazioniByFkAutconcAutatt", "_autorizzazioniByFkAutconcAutatt");
	criteria.createAlias("_autorizzazioniByFkAutconcAutatt.istanza", "_istanza");
	criteria.createAlias("_autorizzazioniByFkAutconcAutatt.autorizcomune", "_autorizcomune", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniByFkAutconcAutatt.autorigComune", "_autorigComune", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniByFkAutconcAutatt.tipologiaregistro", "_tipologiaregistro", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_autorizzazioniConcessioni.mercatiD", "_mercatiD");
	criteria.createAlias("_autorizzazioniConcessioni.mercatiUso", "_mercatiUso");
	//
	criteria.createAlias("_comunicazioniD.oggetti", "_oggetti", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_comunicazioniD.movimenti", "_movimenti", DetachedCriteria.LEFT_JOIN);
	//	criteria.createAlias("_movimenti.istanzeeventis", "_istanzeeventis", Criteria.LEFT_JOIN);
	criteria.createAlias("_comunicazioniD.movimentimail", "_movimentimail", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanza.richiedente", "_richiedente", Criteria.LEFT_JOIN);
	criteria.createAlias("_richiedente.formagiuridica", "_richiedenteFormaGiuridica", Criteria.LEFT_JOIN);
	criteria.createAlias("_istanza.titolarelegale", "_titolarelegale", Criteria.LEFT_JOIN);
	criteria.createAlias("_titolarelegale.formagiuridica", "_titolarelegaleFormaGiuridica", Criteria.LEFT_JOIN);
	//	criteria.createAlias("_istanzeeventis.categorieeventibase", "_categorieeventibase", Criteria.LEFT_JOIN);
	//	criteria.add(Restrictions.eq("_categorieeventibase.id", IstanzeeventiConstants.CATEGORIA_COMUNICAZIONI_GRADUATORIE));
	//RESTRICTION 
	criteria.add(Restrictions.eq("_comunicazioniT.id.codice", codiceComunicazione));
	ProjectionList plist = Projections.projectionList();
	// PROJECTIONS
	plist.add(Projections.property("id.codice"), "IDCOMUNICAZIONIDCONCESSIONI_CODICE");
	plist.add(Projections.property("_comunicazioniT.id.codice"), "CODICECOMUNICAZIONIT");
	plist.add(Projections.property("_comunicazioniD.id.codice"), "IDCOMUNICAZIONED");
	plist.add(Projections.property("_comunicazioniD.statoElaborazione"), "STATOELABORAZIONE");
	plist.add(Projections.property("_oggetti.id.codice"), "OGGETTO");
	plist.add(Projections.property("_movimenti.id.codice"), "MOVIMENTI");
	plist.add(Projections.property("_movimenti.movimento"), "DESCMOVIMENTI");
	plist.add(Projections.property("_movimentimail.id.codice"), "MOVIMENTIMAIL"); //
	// ISTANZA
	plist.add(Projections.property("_istanza.id.codice"), "ISTANZA_ID_CODICE");
	plist.add(Projections.property("_istanza.numeroistanza"), "ISTANZA_NUMEROISTANZA");
	plist.add(Projections.property("_istanza.software.codice"), "ISTANZA_SOFTWARE");
	//RICHIEDENTE 
	plist.add(Projections.property("_richiedente.id.codice"), "ISTANZA_RICHIEDENTE_ID_CODICE");
	plist.add(Projections.property("_richiedente.tipoanagrafe"), "ISTANZA_RICHIEDENTE_TIPOANAGRAFE");
	plist.add(Projections.property("_richiedente.nominativo"), "ISTANZA_RICHIEDENTE_NOMINATIVO");
	plist.add(Projections.property("_richiedente.nome"), "ISTANZA_RICHIEDENTE_NOME");
	plist.add(Projections.property("_richiedente.codicefiscale"), "ISTANZA_RICHIEDENTE_CODICEFISCALE");
	plist.add(Projections.property("_richiedenteFormaGiuridica.formagiuridica"), "ISTANZA_RICHIEDENTE_FORMAGIURIDICA");
	plist.add(Projections.property("_richiedente.partitaiva"), "ISTANZA_RICHIEDENTE_PARTITAIVA");
	plist.add(Projections.property("_richiedente.tipologia"), "ISTANZA_RICHIEDENTE_TIPOLOGIA");
	plist.add(Projections.property("_richiedente.flagDisabilitato"), "ISTANZA_RICHIEDENTE_FLAGDISABILITATO");
	// //TITOLARE LEGALE
	plist.add(Projections.property("_titolarelegale.id.codice"), "ISTANZA_TITOLARELEGALE_ID_CODICE");
	plist.add(Projections.property("_titolarelegale.tipoanagrafe"), "ISTANZA_TITOLARELEGALE_TIPOANAGRAFE");
	plist.add(Projections.property("_titolarelegale.nominativo"), "ISTANZA_TITOLARELEGALE_NOMINATIVO");
	plist.add(Projections.property("_titolarelegale.nome"), "ISTANZA_TITOLARELEGALE_NOME");
	plist.add(Projections.property("_titolarelegale.codicefiscale"), "ISTANZA_TITOLARELEGALE_CODICEFISCALE");
	plist.add(Projections.property("_titolarelegaleFormaGiuridica.formagiuridica"), "ISTANZA_TITOLARELEGALE_FORMAGIURIDICA");
	plist.add(Projections.property("_titolarelegale.partitaiva"), "ISTANZA_TITOLARELEGALE_PARTITAIVA");
	plist.add(Projections.property("_titolarelegale.tipologia"), "ISTANZA_TITOLARELEGALE_TIPOLOGIA");
	plist.add(Projections.property("_titolarelegale.flagDisabilitato"), "ISTANZA_TITOLARELEGALE_FLAGDISABILITATO");
	// AUTORIZZAZIONE_CONCESSIONE 
	plist.add(Projections.property("_autorizzazioniConcessioni.id.codice"), "AUTORIZZAZIONICONCESSIONI_ID_CODICE");
	plist.add(Projections.property("_mercatiD.codiceposteggio"), "AUTORIZZAZIONICONCESSIONI_CODICEPOSTEGGIO");
	plist.add(Projections.property("_mercatiD.id.codice"), "AUTORIZZAZIONICONCESSIONI_IDPOSTEGGIO");
	plist.add(Projections.property("_mercatiUso.descrizione"), "AUTORIZZAZIONICONCESSIONI_MERCATIUSODESCRIZIONE");
	plist.add(Projections.property("_mercatiUso.id.codice"), "AUTORIZZAZIONICONCESSIONI_CODICEMERCATIUSO");
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.id.codice"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_ID_CODICE");
	plist.add(Projections.property("_autorizcomune.comune"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_AUTORIZCOMUNE");
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.autoriznumero"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_AUTORIZNUMERO");
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.autorizdata"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_AUTORIZDATA");
	plist.add(Projections.property("_tipologiaregistro.trDescrizione"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_TIPOLOGIAREGISTRO"); //
	plist.add(Projections.property("_mercatiD.codiceposteggio"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_CODICEPOSTEGGIO"); //
	plist.add(Projections.property("_mercatiD.id.codice"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_IDPOSTEGGIO"); //
	//	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.transientEstremiAut"),
	//		"AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_TRANSIENTESTREMIAUT"); //
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.flagAttiva"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_FLAGATTIVA"); //
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.dataCessazione"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_DATACESSAZIONE"); //
	//plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.id.codice"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_AUTORIZNUMEROSUBENTRO"); //
	plist.add(Projections.property("_mercatiUso.descrizione"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_MERCATIUSODESCRIZIONE"); //
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.note"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_NOTE"); //
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.noteSistema"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_NOTESISTEMA"); //
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.autorigNumero"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_AUTORIGNUMERO"); //
	plist.add(Projections.property("_autorizzazioniByFkAutconcAutatt.autorigData"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_AUTORIGDATA"); //
	plist.add(Projections.property("_autorigComune.comune"), "AUTORIZZAZIONICONCESSIONI_AUTORIZZAZIONI_AUTORIGCOMUNE"); //
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(ComunicazioniDConcessioniDTO.class));
	return criteria;
    }
}
