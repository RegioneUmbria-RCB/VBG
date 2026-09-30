/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayProfiliEntiCreditoriDAO;
import it.gruppoinit.pal.gp.pay.domain.PayProfiliEntiCreditori;
import it.gruppoinit.pal.gp.pay.features.interfaccia.ConnectorConfigHelper;

/**
 * @author francol
 *
 */
@Repository
public class PayProfiliEntiCreditoriDAOImpl extends BaseDAOImpl<PayProfiliEntiCreditori, PkId> implements PayProfiliEntiCreditoriDAO {

    @Override
    public List<PayProfiliEntiCreditori> findByCfCodiceProfilo(String codProfiloEnte) {

	List<PayProfiliEntiCreditori> profs = new ArrayList<PayProfiliEntiCreditori>();
	if (StringUtils.isNotBlank(codProfiloEnte)) {
	    DetachedCriteria crit = getEmptyCriteriaForClass();
	    crit.add(Restrictions.eq("cfCodiceProfilo", codProfiloEnte));
	    profs = (List<PayProfiliEntiCreditori>) getHibernateTemplate().findByCriteria(crit);
	}
	return profs;
    }

    @Override
    public List<PayProfiliEntiCreditori> findByCodiceProfiloPSP(String codProfiloEnte) {

	List<PayProfiliEntiCreditori> profs = new ArrayList<PayProfiliEntiCreditori>();
	if (StringUtils.isNotBlank(codProfiloEnte)) {
	    DetachedCriteria crit = getEmptyCriteriaForClass();
	    crit.add(Restrictions.eq("cfCodiceProfiloPSP", codProfiloEnte));
	    profs = (List<PayProfiliEntiCreditori>) getHibernateTemplate().findByCriteria(crit);
	}
	return profs;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PayProfiliEntiCreditori> findAll(Integer firstResult, Integer maxResult) {

	DetachedCriteria det = getEmptyCriteriaForClass();
	if (null != firstResult && null != maxResult) {
	    return (List<PayProfiliEntiCreditori>) getHibernateTemplate().findByCriteria(det, firstResult.intValue(), maxResult.intValue());
	} else {
	    return (List<PayProfiliEntiCreditori>) getHibernateTemplate().findByCriteria(det);
	}
    }

    @Override
    public Class<PayProfiliEntiCreditori> getEntityClass() {

	return PayProfiliEntiCreditori.class;
    }

    @Override
    public PayProfiliEntiCreditori findByIdAppPspAndIdPosizionePsp(String idAppPsp, String idPosizionePsp) {

	String query = "SELECT pay_profili_enti_creditori.id as codice,pay_profili_enti_creditori.idcomune as idcomune FROM pay_profili_enti_creditori INNER JOIN pay_posizioni_debitorie ON pay_posizioni_debitorie.idcomune=pay_profili_enti_creditori.idcomune AND pay_posizioni_debitorie.fk_profilo_ente=pay_profili_enti_creditori.id WHERE pay_posizioni_debitorie.ID_POSIZIONE_PSP=? AND pay_profili_enti_creditori.ID_APP_PSP=? ";
	SQLQuery q = currentSession().createSQLQuery(query);
	q.setString(0, idPosizionePsp);
	q.setString(1, idAppPsp);
	q.addScalar("codice", IntegerType.INSTANCE);
	q.addScalar("idcomune", StringType.INSTANCE);
	q.setResultTransformer(Transformers.aliasToBean(PkId.class));
	List<PkId> ret = q.list();
	if (ret.isEmpty()) {
	    return null;
	}
	return this.findById(ret.get(0));
    }

    @Override
    public List<ConnectorConfigHelper> findConfigHelperByJavaclass(String javaClass) {

	String query = "select " + //
		       "pay_connector_config.codice as codice, " + //
		       "pay_connector_config.descrizione as descrizione, " + //
		       "pay_connector_config.pay_connector_java_class as javaClass, " + //
		       "pay_profili_enti_creditori.cf_codice_profilo as cfcodprofilo, " + //
		       "pay_profili_enti_creditori.idcomune as idcomune " + //
		       "from " + //
		       "pay_profili_enti_creditori " + //
		       "inner join pay_connector_config on " + //
		       "pay_profili_enti_creditori.codice_connettore=pay_connector_config.codice " + //
		       "where pay_connector_config.PAY_CONNECTOR_JAVA_CLASS like ?";
	SQLQuery q = currentSession().createSQLQuery(query);
	q.setString(0, "%" + javaClass + "%");
	q.addScalar("codice", StringType.INSTANCE);
	q.addScalar("descrizione", StringType.INSTANCE);
	q.addScalar("javaClass", StringType.INSTANCE);
	q.addScalar("cfcodprofilo", StringType.INSTANCE);
	q.addScalar("idcomune", StringType.INSTANCE);
	q.setResultTransformer(Transformers.aliasToBean(ConnectorConfigHelper.class));
	return q.list();
    }
}
