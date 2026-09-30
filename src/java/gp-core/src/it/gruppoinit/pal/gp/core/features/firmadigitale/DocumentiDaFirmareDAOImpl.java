package it.gruppoinit.pal.gp.core.features.firmadigitale;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.IgnoreCaseAliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.constants.WebConstants;
import it.gruppoinit.pal.gp.core.dao.helper.DAOEnum;
import it.gruppoinit.pal.gp.core.dao.helper.DAOOrderTypeEnum;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.DocumentiDaFirmare.StatiDocumentiDaFirmare;
import it.gruppoinit.pal.gp.core.domain.PkId;

/**
 * 
 * @author
 */
@Repository
public class DocumentiDaFirmareDAOImpl extends BaseDAOImpl<DocumentiDaFirmare, PkId> implements DocumentiDaFirmareDAO {

    @Override
    public Class<DocumentiDaFirmare> getEntityClass() {

	return DocumentiDaFirmare.class;
    }

    @Override
    public List<DocumentiDaFirmare> findAll(Integer firstResult, Integer maxResult) {

	return super.findAll(firstResult, maxResult, DAOEnum.FIND_BY_IDCOMUNE, "data_richiesta", DAOOrderTypeEnum.ASC);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentiDaFirmare> findByResponsabile(Integer codiceresponsabile) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("firmatario.id.codice", codiceresponsabile));
	det.add(Restrictions.eq("flagDaFirmare", StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name()));
	return getHibernateTemplate().findByCriteria(det);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentiDaFirmare> findByIdOggetto(Integer codice) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("codiceoggettoId", codice));
	det.addOrder(Order.asc("dataRichiesta"));
	List<DocumentiDaFirmare> lista = getHibernateTemplate().findByCriteria(det);
	return lista;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentiDaFirmare> findDocumentiDaFirmareDTOPerFirmatario(Integer codiceresponsabile, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getBaseCriteria();
	criteria.add(Restrictions.eq("_firmatario.id.codice", codiceresponsabile));
	criteria.add(Restrictions.eq("flagDaFirmare", StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name()));
	criteria.addOrder(Order.asc("dataRichiesta"));
	List<DocumentiDaFirmare> list = new ArrayList<DocumentiDaFirmare>();
	if (firstResult != null && maxResult != null) {
	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	    return list;
	} else {
	    list = getHibernateTemplate().findByCriteria(criteria);
	    return list;
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentiDaFirmare> findDocumentiDaFirmareDTOPerRichiedenteAndStato(Integer codiceresponsabile, Boolean isMessiDallutenteLoggato,
	    String flagDaFirmare, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = getBaseCriteria();
	criteria = getFilterDocumentiDaFirmareCriteriaRichiedenteEStato(criteria, codiceresponsabile, isMessiDallutenteLoggato, flagDaFirmare);
	//	criteria.add(Restrictions.disjunction().add(Restrictions.eq("_richiedente.id.codice", codiceresponsabile))
	//		.add(Restrictions.eq("_firmatario.id.codice", codiceresponsabile)));
	//	//_firmatario
	//	if (StringUtils.isNotBlank(flagDaFirmare)) {
	//	    criteria.add(Restrictions.eq("flagDaFirmare", flagDaFirmare));
	//	}
	criteria.addOrder(Order.desc("dataRichiesta"));
	criteria.addOrder(Order.asc("flagDaFirmare"));
	if (firstResult != null && maxResult != null) {
	    return getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    return getHibernateTemplate().findByCriteria(criteria);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public Integer countDocumentiDaFirmareDTOPerRichiedenteAndStato(Integer codiceresponsabile, Boolean isMessiDallutenteLoggato,
	    String flagDaFirmare) {

	DetachedCriteria criteria = getBaseCriteria();
	criteria = getFilterDocumentiDaFirmareCriteriaRichiedenteEStato(criteria, codiceresponsabile, isMessiDallutenteLoggato, flagDaFirmare);
	//	
	criteria.setProjection(Projections.rowCount());
	List<Object> objs = new ArrayList<Object>();
	objs = getHibernateTemplate().findByCriteria(criteria);
	if (!objs.isEmpty())
	    return (Integer) objs.get(0);
	else
	    return 0;
    }

    private DetachedCriteria getFilterDocumentiDaFirmareCriteriaRichiedenteEStato(DetachedCriteria criteria, Integer codiceresponsabile,
	    Boolean isMessiDallutenteLoggato, String flagDaFirmare) {

	if (StringUtils.isNotBlank(flagDaFirmare)) {
	    criteria.add(Restrictions.eq("flagDaFirmare", flagDaFirmare));
	}
	// solo quelli messi alla firma dall'utente loggato
	if (isMessiDallutenteLoggato) {
	    criteria.add(Restrictions.eq("_richiedente.id.codice", codiceresponsabile));
	}
	return criteria;
    }

    /**
     * Il metodo imposta le join e le projection necessarie. Il metodo da la possibilità decidere se impostare o no la
     * join e la projection per Firmatario e Responsabile.
     * 
     * @param joinForFirmatario
     * @param joinForResponsabile
     * @return
     */
    private DetachedCriteria getBaseCriteria() {

	DetachedCriteria criteria = getIdcomuneCriteria();
	criteria.createAlias("firmatario", "_firmatario", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("oggetti", "_oggetti", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("movimentiallegati", "_movimentiallegati", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_movimentiallegati.movimento", "_movimento", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_movimento.tipomovimento", "_tipomovimento", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("istanze", "_istanze", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanze.richiedente", "_inQualitaDi", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanze.tipisoggetto", "_tipisoggetto", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("_istanze.titolarelegale", "_titolarelegale", DetachedCriteria.LEFT_JOIN);
	criteria.createAlias("richiedente", "_richiedente", DetachedCriteria.LEFT_JOIN);
	// NEL CASO IL SOFTWARE E' DIVERSO DA TT ALLORA DEVO FILTRARE PER IL SOFTWARE SETTATO
	//SULL' ORMHELPER UTILIZZANDO IL CAMPO SOFTWARE DELL'ISTANZA
	if (!ORMHelper.getSoftware().equals(WebConstants.SOFTWARE_TT)) {
	    criteria.createAlias("istanze.software", "_software", DetachedCriteria.LEFT_JOIN);
	    LogicalExpression software = Restrictions.or(Restrictions.eq("_software.codice", ORMHelper.getSoftware()),
		    Restrictions.isNull("_software.codice"));
	    criteria.add(software);
	}
	ProjectionList plist = Projections.projectionList();
	//GRADUATORIEDCOM
	plist.add(Projections.property("id.codice"), "ID_CODICE");
	plist.add(Projections.property("id.idcomune"), "ID_IDCOMUNE");
	plist.add(Projections.property("annotazioniRichiedente"), "ANNOTAZIONIRICHIEDENTE");
	plist.add(Projections.property("annotazioniFirmatario"), "ANNOTAZIONIFIRMATARIO");
	plist.add(Projections.property("dataFirma"), "DATAFIRMA");
	plist.add(Projections.property("dataRichiesta"), "DATARICHIESTA");
	plist.add(Projections.property("flagDaFirmare"), "FLAGDAFIRMARE");
	plist.add(Projections.property("flagLetto"), "FLAGLETTO");
	plist.add(Projections.property("_oggetti.id.codice"), "OGGETTI_ID_CODICE");
	plist.add(Projections.property("_oggetti.nomefile"), "OGGETTI_NOMEFILE");
	plist.add(Projections.property("_oggetti.dimensioneFile"), "OGGETTI_DIMENSIONEFILE");
	plist.add(Projections.property("_firmatario.id.codice"), "FIRMATARIO_ID_CODICE");
	plist.add(Projections.property("_firmatario.responsabile"), "FIRMATARIO_RESPONSABILE");
	plist.add(Projections.property("_movimentiallegati.id.codice"), "MOVIMENTIALLEGATI_ID_CODICE");
	plist.add(Projections.property("_movimento.id.codice"), "MOVIMENTIALLEGATI_MOVIMENTO_ID_CODICE");
	plist.add(Projections.property("_movimento.movimento"), "MOVIMENTIALLEGATI_MOVIMENTO_MOVIMENTO");
	plist.add(Projections.property("_movimento.data"), "MOVIMENTIALLEGATI_MOVIMENTO_DATA");
	plist.add(Projections.property("_movimento.numeroprotocollo"), "MOVIMENTIALLEGATI_MOVIMENTO_NUMEROPROTOCOLLO");
	plist.add(Projections.property("_movimento.dataprotocollo"), "MOVIMENTIALLEGATI_MOVIMENTO_DATAPROTOCOLLO");
	plist.add(Projections.property("_tipomovimento.id.tipomovimento"), "MOVIMENTIALLEGATI_MOVIMENTO_TIPOMOVIMENTO_ID_TIPOMOVIMENTO");
	plist.add(Projections.property("_tipomovimento.movimento"), "MOVIMENTIALLEGATI_MOVIMENTO_TIPOMOVIMENTO_MOVIMENTO");
	//ISTANZA
	plist.add(Projections.property("_istanze.id.codice"), "ISTANZE_ID_CODICE");
	plist.add(Projections.property("_istanze.numeroistanza"), "ISTANZE_NUMEROISTANZA");
	plist.add(Projections.property("_istanze.software.codice"), "ISTANZE_SOFTWARE_CODICE");
	plist.add(Projections.property("_inQualitaDi.id.codice"), "ISTANZE_RICHIEDENTE_ID_CODICE");
	plist.add(Projections.property("_inQualitaDi.nome"), "ISTANZE_RICHIEDENTE_NOME");
	plist.add(Projections.property("_inQualitaDi.nominativo"), "ISTANZE_RICHIEDENTE_NOMINATIVO");
	plist.add(Projections.property("_tipisoggetto.id.codice"), "ISTANZE_TIPISOGGETTO_ID_CODICE");
	plist.add(Projections.property("_tipisoggetto.flgSpecificadescrizione"), "ISTANZE_TIPISOGGETTO_FLGSPECIFICADESCRIZIONE");
	plist.add(Projections.property("_tipisoggetto.tiposoggetto"), "ISTANZE_TIPISOGGETTO_TIPOSOGGETTO");
	plist.add(Projections.property("_tipisoggetto.tiposoggetto"), "ISTANZE_TIPISOGGETTO_TIPOSOGGETTO");
	plist.add(Projections.property("_titolarelegale.id.codice"), "ISTANZE_TITOLARELEGALE_ID_CODICE");
	plist.add(Projections.property("_titolarelegale.nominativo"), "ISTANZE_TITOLARELEGALE_NOMINATIVO");
	//RICHIEDENTE
	plist.add(Projections.property("_richiedente.id.codice"), "RICHIEDENTE_ID_CODICE");
	plist.add(Projections.property("_richiedente.responsabile"), "RICHIEDENTE_RESPONSABILE");
	//
	criteria.setProjection(plist);
	//
	criteria.setResultTransformer(new IgnoreCaseAliasToBeanResultTransformer(DocumentiDaFirmare.class));
	return criteria;
    }

    @Override
    public void eliminaDocDaFirmare(List<Integer> documentiDaFirmare) {

	if (documentiDaFirmare == null) {
	    throw new IllegalArgumentException("La lista dei documenti da cancellare non può essere nulla");
	}
	String sql = "delete from documenti_da_firmare where idcomune = ? and id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(DocumentiDaFirmare.class);
	for (Integer idDoc : documentiDaFirmare) {
	    query.setString(0, ORMHelper.getIdcomune());
	    query.setInteger(1, idDoc);
	    query.executeUpdate();
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findIdDocumentiDaFirmare(Integer codiceOggetto, Integer codiceFirmatario) {

	if (codiceOggetto == null) {
	    throw new IllegalArgumentException(
		    "Impossibile recuperare i riferimenti dei documenti messi alla firma senza passare il codiceOggetto di riferimento");
	}
	if (codiceFirmatario == null) {
	    throw new IllegalArgumentException(
		    "Impossibile recuperare i riferimenti dei documenti messi alla firma senza passare il codiceFirmatario di riferimento");
	}
	String sql = "select id from documenti_da_firmare where idcomune = ? and codicefirmatario = ? and codiceoggetto = ? and flag_da_firmare = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(DocumentiDaFirmare.class);
	query.setString(0, ORMHelper.getIdcomune());
	query.setInteger(1, codiceFirmatario);
	query.setInteger(2, codiceOggetto);
	query.setString(3, StatiDocumentiDaFirmare.FIRMA_RICHIESTA.name());
	query.addScalar("id", Hibernate.INTEGER);
	return query.list();
    }
}
