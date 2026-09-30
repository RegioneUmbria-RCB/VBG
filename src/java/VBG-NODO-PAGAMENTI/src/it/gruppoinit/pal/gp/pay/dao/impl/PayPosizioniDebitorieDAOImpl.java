/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;
	
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayPosizioniDebitorieDAO;
import it.gruppoinit.pal.gp.pay.dao.utils.PosizioneDebitoriaFiltrata;
import it.gruppoinit.pal.gp.pay.dao.utils.QueryPosizioniDebitorieDaRichiestaHelper;
import it.gruppoinit.pal.gp.pay.dao.utils.QueryPosizioniDebitorieFiltrateHelper;
import it.gruppoinit.pal.gp.pay.dao.utils.QueryRicercaNuovePosizioniHelper;
import it.gruppoinit.pal.gp.pay.dao.utils.QueryRicercaPosizioniAnnullateHelper;
import it.gruppoinit.pal.gp.pay.dao.utils.QueryRicercaPosizioniEffettuateHelper;
import it.gruppoinit.pal.gp.pay.dao.utils.RiferimentiClientHelper;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;
import it.gruppoinit.pal.gp.pay.ws.rest.NuoviPagamentiRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiAnnullatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PagamentiEffettuatiRestRequest;
import it.gruppoinit.pal.gp.pay.ws.rest.PosizioneDebitoriaInfoRestResponse;
import it.gruppoinit.pal.gp.pay.ws.schema.PosizioneDebitoriaRequestType;

/**
 * @author francol
 *
 */
@Repository
public class PayPosizioniDebitorieDAOImpl extends BaseDAOImpl<PayPosizioniDebitorie, PkId> implements PayPosizioniDebitorieDAO {

    @Override
    public Class<PayPosizioniDebitorie> getEntityClass() {

	return PayPosizioniDebitorie.class;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PayPosizioniDebitorie findByRiferimenti(String iuv, Integer id) {

	PayPosizioniDebitorie pd = null;
	DetachedCriteria crit = getEmptyCriteriaForClass();
	boolean isValidRef = false;
	if (StringUtils.isNotBlank(iuv)) {
	    crit.add(Restrictions.eq("iuv", iuv));
	    isValidRef = true;
	}
	if (id != null) {
	    crit.add(Restrictions.eq("id.codice", id));
	    isValidRef = true;
	}
	crit.add(Restrictions.eq("id.idcomune", ORMHelper.getIdcomune()));
	List<PayPosizioniDebitorie> pds = new ArrayList<>();
	if (isValidRef) {
	    pds = (List<PayPosizioniDebitorie>) getHibernateTemplate().findByCriteria(crit);
	}
	if (!pds.isEmpty()) {
	    pd = pds.get(0);
	}
	return pd;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PayPosizioniDebitorie findByIdPosizionePSP(String idPSP) {

	List<PayPosizioniDebitorie> pds = this.findAllByIdPosizionePSP(idPSP);
	if (!pds.isEmpty()) {
	    return pds.get(0);
	} else {
	    return null;
	}
    }

    @Override
    public List<PayPosizioniDebitorie> findAllByIdPosizionePSP(String idPSP) {

	List<PayPosizioniDebitorie> poss = new ArrayList<PayPosizioniDebitorie>();
	if (StringUtils.isNotBlank(idPSP)) {
	    DetachedCriteria crit = getEmptyCriteriaForClass();
	    crit.add(Restrictions.eq("idPosizionePsp", idPSP));
	    poss = (List<PayPosizioniDebitorie>) getHibernateTemplate().findByCriteria(crit);
	}
	return poss;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PayPosizioniDebitorie findByCodiceAvviso(String codiceAvviso) {

	PayPosizioniDebitorie pd = null;
	if (StringUtils.isNotBlank(codiceAvviso)) {
	    DetachedCriteria crit = getEmptyCriteriaForClass();
	    crit.add(Restrictions.eq("codiceAvviso", codiceAvviso));
	    List<PayPosizioniDebitorie> pds = (List<PayPosizioniDebitorie>) getHibernateTemplate().findByCriteria(crit);
	    if (!pds.isEmpty()) {
		pd = pds.get(0);
	    }
	}
	return pd;
    }

    @Override
    public List<Integer> findByJsonRequestFilter(PosizioneDebitoriaRequestType richiesta, Integer offset, Integer limit) {

	if (offset == null) {
	    offset = 0;
	}
	if (limit == null) {
	    limit = 50;
	}
	if (richiesta == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo PayPosizioniDebitorieDAOImpl.findByJsonRequestFilter passando una richiesta nulla");
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	boolean isCount = false;
	QueryPosizioniDebitorieDaRichiestaHelper queryHelper = new QueryPosizioniDebitorieDaRichiestaHelper(sessimpl, richiesta, isCount);
	String sql = queryHelper.buildQuery();
	SQLQuery q = currentSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setFirstResult(offset);
	q.setMaxResults(limit);
	return q.list();
    }

    @Override
    public int countByJsonRequestFilter(PosizioneDebitoriaRequestType richiesta) {

	if (richiesta == null) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo PayPosizioniDebitorieDAOImpl.findByJsonRequestFilter passando una lista di posizioni nulla");
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	boolean isCount = true;
	QueryPosizioniDebitorieDaRichiestaHelper queryHelper = new QueryPosizioniDebitorieDaRichiestaHelper(sessimpl, richiesta, isCount);
	String sql = queryHelper.buildQuery();
	SQLQuery q = currentSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	List<Integer> rs = q.list();
	return ((Integer) rs.get(0)).intValue();
    }

    @Override
    public List<PosizioneDebitoriaFiltrata> findByIdPosizioni(List<Integer> idPosizioni) {

	if (idPosizioni == null || idPosizioni.isEmpty()) {
	    throw new IllegalArgumentException(
		    "Impossibile utilizzare il metodo PayPosizioniDebitorieDAOImpl.findByJsonRequestFilter passando una lista di posizioni nulla");
	}
	SessionFactoryImplementor sessimpl = (SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory();
	QueryPosizioniDebitorieFiltrateHelper queryHelper = new QueryPosizioniDebitorieFiltrateHelper(sessimpl, idPosizioni);
	String sql = queryHelper.buildQuery();
	SQLQuery q = currentSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setResultTransformer(Transformers.aliasToBean(PosizioneDebitoriaFiltrata.class));
	return q.list();
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findNuoviPagamentiDeiConnettori(NuoviPagamentiRequest richiesta) {

	if (richiesta == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findNuoviPagamentiDeiConnettori passando una richiesta nulla");
	}
	int offset = 0;
	int limit = 50;
	if (richiesta.getOffset() != null) {
	    offset = richiesta.getOffset().intValue();
	}
	if (richiesta.getLimit() != null) {
	    limit = richiesta.getLimit().intValue();
	}
	QueryRicercaNuovePosizioniHelper queryHelper = new QueryRicercaNuovePosizioniHelper(
		(SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory(), richiesta);
	String sql = queryHelper.buildQuery();
	SQLQuery q = currentSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setFirstResult(offset);
	q.setMaxResults(limit);
	q.setResultTransformer(Transformers.aliasToBean(PosizioneDebitoriaInfoRestResponse.class));
	return q.list();
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findPagamentiAnnullatiDeiConnettori(PagamentiAnnullatiRestRequest richiesta) {

	if (richiesta == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findNuoviPagamentiDeiConnettori passando una richiesta nulla");
	}
	int offset = 0;
	int limit = 50;
	if (richiesta.getOffset() != null) {
	    offset = richiesta.getOffset().intValue();
	}
	if (richiesta.getLimit() != null) {
	    limit = richiesta.getLimit().intValue();
	}
	QueryRicercaPosizioniAnnullateHelper queryHelper = new QueryRicercaPosizioniAnnullateHelper(
		(SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory(), richiesta);
	String sql = queryHelper.buildQuery();
	SQLQuery q = currentSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setFirstResult(offset);
	q.setMaxResults(limit);
	q.setResultTransformer(Transformers.aliasToBean(PosizioneDebitoriaInfoRestResponse.class));
	return q.list();
    }

    @Override
    public List<PosizioneDebitoriaInfoRestResponse> findPagamentiEffettuatiDeiConnettori(PagamentiEffettuatiRestRequest richiesta) {

	if (richiesta == null) {
	    throw new IllegalArgumentException("Impossibile utilizzare il metodo findNuoviPagamentiDeiConnettori passando una richiesta nulla");
	}
	int offset = 0;
	int limit = 50;
	if (richiesta.getOffset() != null) {
	    offset = richiesta.getOffset().intValue();
	}
	if (richiesta.getLimit() != null) {
	    limit = richiesta.getLimit().intValue();
	}
	QueryRicercaPosizioniEffettuateHelper queryHelper = new QueryRicercaPosizioniEffettuateHelper(
		(SessionFactoryImplementor) getSessionFactory().getCurrentSession().getSessionFactory(), richiesta);
	String sql = queryHelper.buildQuery();
	SQLQuery q = currentSession().createSQLQuery(sql);
	queryHelper.setFilterValues(q);
	queryHelper.setScalarProperties(q);
	q.setFirstResult(offset);
	q.setMaxResults(limit);
	q.setResultTransformer(Transformers.aliasToBean(PosizioneDebitoriaInfoRestResponse.class));
	return q.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public PayPosizioniDebitorie findByIdPosizionePSPOrIUVOrCodiceAvviso(String idPSP, String iuv, String codiceAvviso) {

	PayPosizioniDebitorie pd = null;
	DetachedCriteria crit = getEmptyCriteriaForClass();
	boolean isValidRef = false;
	Criterion combinedOrCriterion = null;
	Criterion condition = null;
	if (StringUtils.isNotBlank(idPSP)) {
	    condition = Restrictions.eq("idPosizionePsp", idPSP);
	    combinedOrCriterion = condition;
	    isValidRef = true;
	}
	if (StringUtils.isNotBlank(iuv)) {
	    condition = Restrictions.eq("iuv", iuv);
	    combinedOrCriterion = combinedOrCriterion == null ? condition : Restrictions.or(combinedOrCriterion, condition);
	    isValidRef = true;
	}
	if (StringUtils.isNotBlank(codiceAvviso)) {
	    condition = Restrictions.eq("codiceAvviso", codiceAvviso);
	    combinedOrCriterion = combinedOrCriterion == null ? condition : Restrictions.or(combinedOrCriterion, condition);
	    isValidRef = true;
	}
	// Aggiunge il criterio combinato ai criteri di ricerca
	if (isValidRef && combinedOrCriterion != null) {
	    crit.add(combinedOrCriterion);
	}
	List<PayPosizioniDebitorie> pds = new ArrayList<>();
	if (isValidRef) {
	    pds = (List<PayPosizioniDebitorie>) getHibernateTemplate().findByCriteria(crit);
	}
	if (!pds.isEmpty()) {
	    pd = pds.get(0);
	}
	return pd;
    }

    @Override
    public Set<String> findRiferimentiClientByPosizioneDebitoria(Integer idPosizioneDebitoria) {

	String query = "SELECT pay_posizioni_debitorie.RIFERIMENTO_CLIENT as rifpos, " + //
		       "pay_posdeb_rifclient.RIFERIMENTO_CLIENT as riftab " + //
		       " FROM pay_posizioni_debitorie LEFT JOIN pay_posdeb_rifclient ON " + //
		       "pay_posizioni_debitorie.idcomune=pay_posdeb_rifclient.idcomune AND " + //
		       "pay_posizioni_debitorie.id=pay_posdeb_rifclient.fk_posizione_debitoria " + //
		       "WHERE pay_posizioni_debitorie.idcomune=:idcomune AND " + //
		       "pay_posizioni_debitorie.id=:idposizione";
	//
	SQLQuery q = currentSession().createSQLQuery(query).addSynchronizedEntityClass(PayRegistrazioniContabili.class) //
		.addSynchronizedEntityClass(PayPosizioniDebitorie.class) //
		.addSynchronizedEntityClass(PayDettaglioImporti.class);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idposizione", idPosizioneDebitoria);
	q.addScalar("rifpos", StringType.INSTANCE);
	q.addScalar("riftab", StringType.INSTANCE);
	q.setResultTransformer(Transformers.aliasToBean(RiferimentiClientHelper.class));
	List<RiferimentiClientHelper> list = q.list();
	Set<String> ret = new HashSet<>();
	list.forEach(resultSet -> {
	    ret.add(resultSet.getRiftab());
	    if (resultSet.getRifpos() != null) {
		ret.add(resultSet.getRifpos());
	    }
	});
	return ret;
    }
}
