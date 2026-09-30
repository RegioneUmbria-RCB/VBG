package it.gruppoinit.pal.gp.core.dao.impl;

import it.gruppoinit.pal.gp.core.dao.AutorizzazioniSubentriDAO;
import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula;
import it.gruppoinit.pal.gp.core.dao.helper.OrderBySqlFormula.FunctionsEnum;
import it.gruppoinit.pal.gp.core.domain.AutorizzazioniSubentri;
import it.gruppoinit.pal.gp.core.domain.Istanze;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.core.domain.util.EntityUtils;
import it.gruppoinit.pal.gp.core.domain.web.AutorizzazioniFilter;
import it.gruppoinit.pal.gp.core.filters.OrderTypeEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author fabrizioc
 */
@Repository
public class AutorizzazioniSubentriDAOImpl extends BaseDAOImpl<AutorizzazioniSubentri, PkId> implements AutorizzazioniSubentriDAO {

    @Override
    public Class<AutorizzazioniSubentri> getEntityClass() {

	return AutorizzazioniSubentri.class;
    }

    @Override
    public List<AutorizzazioniSubentri> findAll(Integer firstResult, Integer maxResult) {

	throw new NotImplementedException("metodo non implementato.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public AutorizzazioniSubentri findByEstremi(String autoriznumero, Date autorizdata, String codicecomune, Integer codiceregistro) {

	// chiave univoca
	DetachedCriteria det = getIdcomuneCriteria();
	det.add(Restrictions.eq("autoriznumero", autoriznumero));
	det.add(Restrictions.eq("autorizdata", autorizdata));
	det.add(Restrictions.eq("autorizcomune.codicecomune", codicecomune));
	det.add(Restrictions.eq("tipologiaregistro.id.codice", codiceregistro));
	det.addOrder(Order.desc("dataCessazione"));
	det.addOrder(Order.desc("id.codice"));
	List<AutorizzazioniSubentri> list = getHibernateTemplate().findByCriteria(det);
	if (!list.isEmpty()) {
	    return list.get(0);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniSubentri> findAutorizzazioniSubentriByIstanza(Istanze istanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioni", "aut", DetachedCriteria.INNER_JOIN);
	det.createCriteria("aut.autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	det.add(Restrictions.isNull("aut_conc.id.codice"));
	det.add(Restrictions.eq("istanze.id.codice", istanza.getId().getCodice()));
	det.addOrder(Order.desc("dataCessazione"));
	det.addOrder(Order.desc("id.codice"));
	List<AutorizzazioniSubentri> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<AutorizzazioniSubentri> findConcessioniSubentriByIstanza(Integer codiceIstanza) {

	DetachedCriteria det = getIdcomuneCriteria();
	det.createCriteria("autorizzazioni", "aut", DetachedCriteria.INNER_JOIN);
	det.createCriteria("aut.autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.INNER_JOIN);
	det.add(Restrictions.eq("istanze.id.codice", codiceIstanza));
	det.addOrder(Order.desc("dataCessazione"));
	det.addOrder(Order.desc("id.codice"));
	List<AutorizzazioniSubentri> list = getHibernateTemplate().findByCriteria(det);
	return list;
    }

    @Override
    public int countByFilter(AutorizzazioniFilter filter) {

	DetachedCriteria criteria = createCriteriaFilter(filter);
	criteria.setProjection(Projections.rowCount());
	int ris = ((Integer) getHibernateTemplate().findByCriteria(criteria).get(0)).intValue();
	return ris;
    }

    @Override
    public List<AutorizzazioniSubentri> findAutorizzazioniSubentriByFilter(AutorizzazioniFilter filter, Integer firstResult, Integer maxResult) {

	DetachedCriteria criteria = createCriteriaFilter(filter);
	List<AutorizzazioniSubentri> list = new ArrayList<AutorizzazioniSubentri>();
	if (null != firstResult && null != maxResult) {
	    list = getHibernateTemplate().findByCriteria(criteria, firstResult, maxResult);
	} else {
	    list = getHibernateTemplate().findByCriteria(criteria);
	}
	return list;
    }

    private DetachedCriteria createCriteriaFilter(AutorizzazioniFilter filter) {

	DetachedCriteria det = getIdcomuneCriteria();
	// /////////////////////////////////////////////////// FILTRI PER DATI AUTORIZZAZIONE SUBENTRI //////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	if (StringUtils.isNotBlank(filter.getAutoriznumero())) {
	    det.add(Restrictions.eq("autoriznumero", filter.getAutoriznumero()));
	}
	if (filter.getDallaData() != null) {
	    det.add(Restrictions.ge("autorizdata", filter.getDallaData()));
	}
	if (filter.getAllaData() != null) {
	    det.add(Restrictions.le("autorizdata", filter.getAllaData()));
	}
	if (filter.getAutorizcomune() != null && StringUtils.isNotBlank(filter.getAutorizcomune().getCodicecomune())) {
	    det.add(Restrictions.eq("autorizcomune.codicecomune", filter.getAutorizcomune().getCodicecomune()));
	}
	if (filter.getDallaDataScadenza() != null) {
	    det.add(Restrictions.ge("datascadenza", filter.getDallaDataScadenza()));
	}
	if (filter.getAllaDataScadenza() != null) {
	    det.add(Restrictions.le("datascadenza", filter.getAllaDataScadenza()));
	}
	det.createAlias("anagrafe", "_anagrafe", Criteria.LEFT_JOIN);
	if (filter.getAnagrafe() != null && filter.getAnagrafe().getId() != null
			&& filter.getAnagrafe().getId().getCodice() != null) {
		det.add(Restrictions.or(Restrictions.eq("anagrafe.id.codice", filter.getAnagrafe().getId().getCodice()),
				Restrictions.eq("occupante.id.codice", filter.getAnagrafe().getId().getCodice())));
	}
	// non ha senso filtrare per quelle attive/non attive in quanto è subentrata e quindi non attiva
	//	if (!filter.getIncludiCessate()) {
	//	    det.add(Restrictions.eq("autorizzazioni.flagAttiva", true));
	//	}
	////////////////////////// SEZIONE NON PRESENTE SULLA PAGINA DI RICERCA AUT ///////////////////////////////////////////////////
	//	if (filter.isEscludiConcessioni()) {
	//	    det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "aut_conc", DetachedCriteria.LEFT_JOIN);
	//	    det.add(Restrictions.isNull("aut_conc.id.codice"));
	//	}
	//////////////////////////////////////////////////////////// END //////////////////////////////////////////////////////////////
	////----------------------------------------------------  START ---------------------------------------------------------------
	//////////////////////////////////////////SEZIONE NON PRESENTE SU RICERCA ATTIVITA //////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// FILTRO DATI DELLA MANIFESTAZIONE (PRESENTI SOLO SE PER IDCOMUNE E SOFTWARE IN USO E' ATTIVA LA CONFIGURAZIONE DELLE MANIFESTAZIONI)
	// Se è impostato almeno uno dei tre filtri creo la condizione di join
	// Era commentato?!?!?!?!?!?!!?
	//	if (EntityUtils.getNestedProperty(filter.getMercati(), "id.codice") != null
	//		|| EntityUtils.getNestedProperty(filter.getMercatiUso(), "id.codice") != null
	//		|| EntityUtils.getNestedProperty(filter.getMercatiD(), "id.codice") != null) {
	//	    det.createCriteria("autorizzazioniConcessionisForFkAutconcAutatt", "_autorizzazioniConcessionisForFkAutconcAutatt",
	//		    DetachedCriteria.LEFT_JOIN);
	//	}
	//	if (EntityUtils.getNestedProperty(filter.getMercati(), "id.codice") != null) {
	//	    det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiId", filter.getMercati().getId().getCodice()));
	//	}
	//	if (EntityUtils.getNestedProperty(filter.getMercatiUso(), "id.codice") != null) {
	//	    det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiUsoId", filter.getMercatiUso().getId().getCodice()));
	//	}
	//	if (EntityUtils.getNestedProperty(filter.getMercatiD(), "id.codice") != null) {
	//	    det.add(Restrictions.eq("_autorizzazioniConcessionisForFkAutconcAutatt.mercatiDId", filter.getMercatiD().getId().getCodice()));
	//	}
	////----------------------------------------------------  END ------------------------------------------------------------------///
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////// FILTRI PER DATI DELL'ISTANZA /////////////////////////////////////////////////
	det.createAlias("istanze", "_istanza", Criteria.LEFT_JOIN);
	// Se passo l'id dell'istanza filtro per istanza specifica
	if (filter.getIstanzeFilter() != null && filter.getIstanzeFilter().getCodiceIstanza() != null) {
	    det.add(Restrictions.eq("_istanza.id.codice", filter.getIstanzeFilter().getCodiceIstanza()));
	}
	//	// criterio per includere le aut non collegate all'istanza (quindi collegate all'anagrafica)
	//	Criterion softwareCrit = Restrictions.or(Restrictions.eq("_istanza.software.codice", ORMHelper.getSoftware()),
	//		Restrictions.isNull("_istanza.id.codice"));
	//	det.add(softwareCrit);
	det.createAlias("tipologiaregistro", "_tipologiaregistro", Criteria.INNER_JOIN);
	if (filter.getTipologiaregistro() != null && filter.getTipologiaregistro().getId() != null
		&& filter.getTipologiaregistro().getId().getCodice() != null) {
	    det.add(Restrictions.eq("_tipologiaregistro.id.codice", filter.getTipologiaregistro().getId().getCodice()));
	}
	Criterion softwareCrit = Restrictions.eq("_tipologiaregistro.software.codice", ORMHelper.getSoftware());
	det.add(softwareCrit);
	if (filter.getCodiceIstanzaDaEscludere() != null) {
	    Criterion codiceIstanzaCrit = Restrictions.or(Restrictions.isNull("_istanza.id.codice"),
		    Restrictions.ne("_istanza.id.codice", filter.getCodiceIstanzaDaEscludere()));
	    det.add(codiceIstanzaCrit);
	}
	if (StringUtils.isNotBlank(filter.getIstanzeFilter().getNumeroistanza())) {
	    det.add(Restrictions.eq("_istanza.numeroistanza", filter.getIstanzeFilter().getNumeroistanza()));
	}
	if (filter.getIstanzeFilter().getDallaData() != null) {
	    det.add(Restrictions.ge("_istanza.data", filter.getIstanzeFilter().getDallaData()));
	}
	if (filter.getIstanzeFilter().getAllaData() != null) {
	    det.add(Restrictions.le("_istanza.data", filter.getIstanzeFilter().getAllaData()));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getAlberoproc(), "id.codice") != null) {
	    det.createCriteria("_istanza.alberoproc", "_alberoproc");
	    det.add(Restrictions.eq("_alberoproc.id.codice", filter.getIstanzeFilter().getAlberoproc().getId().getCodice()));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getProcedura(), "id.codice") != null) {
	    det.createCriteria("_istanza.procedura", "_procedura");
	    det.add(Restrictions.eq("_procedura.id.codice", filter.getIstanzeFilter().getProcedura().getId().getCodice()));
	}
	boolean isIstanzestradarioJoinPresent = false;
	boolean isStradarioJoinPresent = false;
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getIstanzestradario().getStradario(), "id.codice") != null) {
	    det.createCriteria("_istanza.istanzestradarios", "istanzestradario");
	    det.createCriteria("istanzestradario.stradario", "_stradario");
	    det.add(Restrictions.eq("_stradario.id.codice", filter.getIstanzeFilter().getIstanzestradario().getStradario().getId().getCodice()));
	    isStradarioJoinPresent = true;
	}
	if (StringUtils.isNotBlank(filter.getIstanzeFilter().getIstanzestradario().getCap())) {
	    if (!isStradarioJoinPresent) {
		det.createCriteria("_istanza.istanzestradarios", "istanzestradario");
		isIstanzestradarioJoinPresent = true;
	    }
	    det.add(Restrictions.eq("istanzestradario.cap", filter.getIstanzeFilter().getIstanzestradario().getCap()));
	}
	if (filter.getIstanzeFilter().getComune() != null && StringUtils.isNotBlank(filter.getIstanzeFilter().getComune().getCodicecomune())) {
	    det.createCriteria("_istanza.comune", "_comune");
	    det.add(Restrictions.eq("_comune.codicecomune", filter.getIstanzeFilter().getComune().getCodicecomune()));
	}
	if (EntityUtils.getNestedProperty(filter.getIstanzeFilter().getRichiedente(), "id.codice") != null) {
	    det.createAlias("_istanza.richiedente", "_richiedenteistanza", Criteria.LEFT_JOIN);
	    det.add(Restrictions.eq("_richiedenteistanza.id.codice", filter.getIstanzeFilter().getRichiedente().getId().getCodice()));
	}
	/////////////////////////////////////////// GESSTIONE ORDINAMENBTO /////////////////////////////////////////////////////////
	String[] padNumeroautorizzazione = new String[] { "20", "' '" };
	String[] padNumeroistanza = new String[] { "20", "' '" };
	if (filter.getOrderBy() != null) {
	    String[] field = filter.getOrderBy().split(",");
	    for (int i = 0; i < field.length; i++) {
		if (field[i].equals("autorizdata")) {
		    setOrdine(field[i], det, filter.getOrderAscDesc(), "data", null);
		}
		if (field[i].equalsIgnoreCase("tipologiaregistro.trDescrizione")) {
		    setOrdine("_tipologiaregistro.trDescrizione", det, filter.getOrderAscDesc(), "standard", null);
		}
		if (field[i].equalsIgnoreCase("istanza.data")) {
		    setOrdine("_istanza.data", det, filter.getOrderAscDesc(), "data", null);
		}
		if (field[i].equalsIgnoreCase("anagrafeautotizzazione")) {
		    setOrdine("_anagrafe.nominativo", det, filter.getOrderAscDesc(), "standard", null);
		}
		if (field[i].equals("istanza.numeroistanza")) {
		    setOrdine("_istanza.numeroistanza", det, filter.getOrderAscDesc(), "leftpad", padNumeroistanza);
		}
		if (field[i].equals("autoriznumero")) {
		    setOrdine("autoriznumero", det, filter.getOrderAscDesc(), "leftpad", padNumeroautorizzazione);
		}
		if (field[i].equals("istanze.istanzestradario.stradario.descrizione")) {
		    if (isStradarioJoinPresent) {
			setOrdine("_stradario.descrizione", det, filter.getOrderAscDesc(), "standard", null);
		    } else if (isIstanzestradarioJoinPresent) {
			det.createCriteria("istanzestradario.stradario", "_stradario");
			setOrdine("_stradario.descrizione", det, filter.getOrderAscDesc(), "standard", null);
		    } else {
			det.createCriteria("_istanza.istanzestradarios", "istanzestradario", DetachedCriteria.FULL_JOIN);
			det.createCriteria("istanzestradario.stradario", "_stradario", DetachedCriteria.FULL_JOIN);
			setOrdine("_stradario.descrizione", det, filter.getOrderAscDesc(), "standard", null);
		    }
		}
	    }
	}
	return det;
    }

    /**
     * <pre>
     * Aggionge al DetachedCriteria passato l'ordinamento dove:
     * @param FiledOrder: obbligatorio - indica il campo per cui ordinare 
     * @param det : obbligatorio - DetachedCriteria a cui aggiungere l'ordinamento 
     * @param orderTypeEnum : Obbligatorio - ASC o DESC
     * @param tipoDatoOrdinamento : tipologia di ordinamento 
     * 					standard : campo semplice
     *                                  data     : campo data (applica ordinamento fatto tramite la funzione FunctionsEnum.NLV_FUNCTION )
     *                                  leftpad  : campo stringa (applica ordinamento fatto tramite la funzione FunctionsEnum.LPAD_FUNCTION )
     * @param parametro
     * </pre>
     */
    private void setOrdine(String FiledOrder, DetachedCriteria det, OrderTypeEnum orderTypeEnum, String tipoDatoOrdinamento, String... parametro) {

	if (tipoDatoOrdinamento.equalsIgnoreCase("standard")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(Order.asc(FiledOrder));
	    } else {
		det.addOrder(Order.desc(FiledOrder));
	    }
	}
	if (tipoDatoOrdinamento.equalsIgnoreCase("data")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(OrderBySqlFormula.asc(FiledOrder, FunctionsEnum.NVL_FUNCTION, "'01/01/2999'",
			OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	    } else {
		det.addOrder(OrderBySqlFormula.desc(FiledOrder, FunctionsEnum.NVL_FUNCTION, "'01/01/2999'",
			OrderBySqlFormula.NVL_CONVERT_STRING_TO_DATE));
	    }
	}
	if (tipoDatoOrdinamento.equalsIgnoreCase("leftpad")) {
	    if (orderTypeEnum.name().equalsIgnoreCase("ASC")) {
		det.addOrder(OrderBySqlFormula.asc(FiledOrder, FunctionsEnum.LPAD_FUNCTION, parametro));
	    } else {
		det.addOrder(OrderBySqlFormula.desc(FiledOrder, FunctionsEnum.LPAD_FUNCTION, parametro));
	    }
	}
    }
}
