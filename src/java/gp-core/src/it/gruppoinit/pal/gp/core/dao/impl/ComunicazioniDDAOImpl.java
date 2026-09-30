package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.ComunicazioniDDAO;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.domain.ComunicazioniD;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.service.helper.ComunicazioniDStatoEnum;

import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * 
 * @author
 */
@Repository
public class ComunicazioniDDAOImpl extends BaseDAOImpl<ComunicazioniD, PkId> implements ComunicazioniDDAO {

    @Override
    public Class<ComunicazioniD> getEntityClass() {

	return ComunicazioniD.class;
    }

    @Override
    public List<ComunicazioniD> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "id.codice", DAOOrderTypeEnum.ASC);
    }

    //    @Override
    //    public List<ComunicazioniDDTO> findByComunicazioniT(Integer codiceComunicazione, Integer firstResult, Integer maxResult) {
    //
    //	DetachedCriteria criteria = getBaseCriteriaFilterByComunicazioneT(codiceComunicazione);
    //	List<ComunicazioniDDTO> list = null;
    //	if (null != firstResult && null != maxResult) {
    //	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
    //	} else {
    //	    list = getHibernateTemplate().findByCriteria(criteria);
    //	}
    //	return list;
    //    }
    ////
    //    private DetachedCriteria getBaseCriteriaFilterByComunicazioneT(Integer codiceComunicazione) {
    //
    //	DetachedCriteria criteria = getIdcomuneCriteria();
    //	criteria.add(Restrictions.eq("comunicazioniT.id.codice", codiceComunicazione));
    //	//	criteria.createAlias("graduatoried", "_graduatoried");
    //	//	criteria.createAlias("graduatoried.istanza", "_istanza");
    //	criteria.createAlias("oggetti", "_oggetti", DetachedCriteria.LEFT_JOIN);
    //	criteria.createAlias("movimenti", "_movimenti", DetachedCriteria.LEFT_JOIN);
    //	//	criteria.createAlias("_movimenti.istanzeeventis", "_istanzeeventis", Criteria.LEFT_JOIN);
    //	criteria.createAlias("movimentimail", "_movimentimail", DetachedCriteria.LEFT_JOIN);
    //	//	criteria.createAlias("_istanza.richiedente", "_richiedente", Criteria.LEFT_JOIN);
    //	//	criteria.createAlias("_richiedente.formagiuridica", "_richiedenteFormaGiuridica", Criteria.LEFT_JOIN);
    //	//	criteria.createAlias("_istanza.titolarelegale", "_titolarelegale", Criteria.LEFT_JOIN);
    //	//	criteria.createAlias("_titolarelegale.formagiuridica", "_titolarelegaleFormaGiuridica", Criteria.LEFT_JOIN);
    //	//	criteria.createAlias("_istanzeeventis.categorieeventibase", "_categorieeventibase", Criteria.LEFT_JOIN);
    //	//	criteria.add(Restrictions.eq("_categorieeventibase.id", IstanzeeventiConstants.CATEGORIA_COMUNICAZIONI_GRADUATORIE));
    //	ProjectionList plist = Projections.projectionList();
    //	//GRADUATORIEDCOM
    //	plist.add(Projections.property("id.codice"), "ID_CODICE");
    //	plist.add(Projections.property("statoElaborazione"), "STATOELABORAZIONE");
    //	plist.add(Projections.property("_oggetti.id.codice"), "OGGETTO");
    //	plist.add(Projections.property("_movimenti.id.codice"), "MOVIMENTI");
    //	plist.add(Projections.property("_movimenti.movimento"), "DESCMOVIMENTI");
    //	plist.add(Projections.property("_movimentimail.id.codice"), "MOVIMENTIMAIL");
    //	//	plist.add(Projections.property("_istanzeeventis.id"), "ISTANZEEVENTI_ID");
    //	//GRADUATORIED
    //	//	plist.add(Projections.property("_graduatoried.id.codice"), "GRADUATORIED_ID_CODICE");
    //	//	plist.add(Projections.property("_graduatoried.posizione"), "GRADUATORIED_POSIZIONE");
    //	//ISTANZA
    //	//	plist.add(Projections.property("_istanza.id.codice"), "GRADUATORIED_ISTANZA_ID_CODICE");
    //	//	plist.add(Projections.property("_istanza.numeroistanza"), "GRADUATORIED_ISTANZA_NUMEROISTANZA");
    //	//	plist.add(Projections.property("_istanza.software.codice"), "GRADUATORIED_ISTANZA_SOFTWARE");
    //	//RICHIEDENTE
    //	//	plist.add(Projections.property("_richiedente.id.codice"), "GRADUATORIED_ISTANZA_RICHIEDENTE_ID_CODICE");
    //	//	plist.add(Projections.property("_richiedente.tipoanagrafe"), "GRADUATORIED_ISTANZA_RICHIEDENTE_TIPOANAGRAFE");
    //	//	plist.add(Projections.property("_richiedente.nominativo"), "GRADUATORIED_ISTANZA_RICHIEDENTE_NOMINATIVO");
    //	//	plist.add(Projections.property("_richiedente.nome"), "GRADUATORIED_ISTANZA_RICHIEDENTE_NOME");
    //	//	plist.add(Projections.property("_richiedente.codicefiscale"), "GRADUATORIED_ISTANZA_RICHIEDENTE_CODICEFISCALE");
    //	//	plist.add(Projections.property("_richiedenteFormaGiuridica.formagiuridica"), "GRADUATORIED_ISTANZA_RICHIEDENTE_FORMAGIURIDICA");
    //	//	plist.add(Projections.property("_richiedente.partitaiva"), "GRADUATORIED_ISTANZA_RICHIEDENTE_PARTITAIVA");
    //	//	plist.add(Projections.property("_richiedente.tipologia"), "GRADUATORIED_ISTANZA_RICHIEDENTE_TIPOLOGIA");
    //	//	plist.add(Projections.property("_richiedente.flagDisabilitato"), "GRADUATORIED_ISTANZA_RICHIEDENTE_FLAGDISABILITATO");
    //	//	//TITOLARE LEGALE
    //	//	plist.add(Projections.property("_titolarelegale.id.codice"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_ID_CODICE");
    //	//	plist.add(Projections.property("_titolarelegale.tipoanagrafe"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_TIPOANAGRAFE");
    //	//	plist.add(Projections.property("_titolarelegale.nominativo"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_NOMINATIVO");
    //	//	plist.add(Projections.property("_titolarelegale.nome"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_NOME");
    //	//	plist.add(Projections.property("_titolarelegale.codicefiscale"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_CODICEFISCALE");
    //	//	plist.add(Projections.property("_titolarelegaleFormaGiuridica.formagiuridica"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_FORMAGIURIDICA");
    //	//	plist.add(Projections.property("_titolarelegale.partitaiva"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_PARTITAIVA");
    //	//	plist.add(Projections.property("_titolarelegale.tipologia"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_TIPOLOGIA");
    //	//	plist.add(Projections.property("_titolarelegale.flagDisabilitato"), "GRADUATORIED_ISTANZA_TITOLARELEGALE_FLAGDISABILITATO");
    //	//
    //	criteria.setProjection(plist);
    //	//
    //	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(ComunicazioniDDTO.class));
    //	return criteria;
    //    }
    @Override
    public void upadateStato(Integer codiceComD, ComunicazioniDStatoEnum stato) {

	if (stato == null) {
	    throw new RuntimeException("upadateStato: il parametro stato passato è nullo");
	}
	if (codiceComD == null) {
	    throw new RuntimeException("upadateStato: il parametro codiceComD passato è nullo");
	}
	Integer nuovoStato = stato.value();
	if (nuovoStato != null) {
	    String hql = "update ComunicazioniD set statoElaborazione = ? where id.idcomune = ? and id.codice=?";
	    int i = getHibernateTemplate().bulkUpdate(hql, new Object[] { nuovoStato, ORMHelper.getIdcomune(), codiceComD });
	    if (i != 1) {
		throw new RuntimeException("La query di aggiornamento dello stato ComunicazioniD :[" + codiceComD + "] con stato [" + nuovoStato
			+ "] ha influito su " + i + " record");
	    }
	}
    }
}
