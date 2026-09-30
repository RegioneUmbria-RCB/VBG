/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayRegistrazioniCausaliDAO;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniCausali;
import it.gruppoinit.pal.gp.pay.features.interfaccia.InfoCausaliParam;
import it.gruppoinit.pal.gp.pay.service.helper.CausaliConnettoreBean;

/**
 * @author francol
 *
 */
@Repository
public class PayRegistrazioniCausaliDAOImpl extends BaseDAOImpl<PayRegistrazioniCausali, PkId> implements PayRegistrazioniCausaliDAO {

    @Override
    public Class<PayRegistrazioniCausali> getEntityClass() {

	return PayRegistrazioniCausali.class;
    }

    @Override
    public List<CausaliConnettoreBean> findInfoCausaliPerConnettore(List<String> cfCodiciProfilo) {

	String query = " SELECT  " + // 
		       " pay_connector_config.descrizione AS descrizioneconn, " + // 
		       " pay_connector_config.PAY_CONNECTOR_JAVA_CLASS AS javaclass, " + // 
		       " pay_profili_enti_creditori.cf_codice_profilo AS cfcodiceprofilo, " + // 
		       " pay_registrazioni_causali.idcomune, " + // 
		       " pay_registrazioni_causali.id " + // 
		       " FROM " + // 
		       " pay_registrazioni_causali  " + // 
		       " INNER JOIN  " + // 
		       " pay_profili_enti_creditori ON " + // 
		       " pay_registrazioni_causali.idcomune=pay_profili_enti_creditori.idcomune " + // 
		       " INNER JOIN pay_connector_config ON " + // 
		       " pay_connector_config.codice=pay_profili_enti_creditori.codice_connettore "; //
	if (!cfCodiciProfilo.isEmpty()) {
	    query += " WHERE  " + // 
		     " pay_profili_enti_creditori.cf_codice_profilo IN( " + // 
		     " REP_CODICE_PROFILO " + // 
		     " )";
	    // 
	    String qm = StringUtils.repeat("?,", cfCodiciProfilo.size());
	    qm = qm.substring(0, qm.length() - 1);
	    query = query.replace("REP_CODICE_PROFILO", qm);
	    //
	}
	SQLQuery q = currentSession().createSQLQuery(query);
	q.addScalar("id", IntegerType.INSTANCE);
	q.addScalar("idcomune", StringType.INSTANCE);
	q.addScalar("descrizioneconn", StringType.INSTANCE);
	q.addScalar("javaclass", StringType.INSTANCE);
	q.addScalar("cfcodiceprofilo", StringType.INSTANCE);
	if (!cfCodiciProfilo.isEmpty()) {
	    int pos = 0;
	    for (String profilo : cfCodiciProfilo) {
		q.setString(pos++, profilo);
	    }
	}
	q.setResultTransformer(Transformers.aliasToBean(CausaliConnettoreBean.class));
	return q.list();
    }

    @Override
    public List<InfoCausaliParam> findInfoCausaliRidottePerConnettore(String cfcodprofilo) {

	String query = " SELECT  " + // 
		       " pay_connector_config.descrizione AS descrizioneconn, " + //  
		       " pay_registrazioni_causali.idcomune, " + // 
		       " pay_registrazioni_causali.id " + // 
		       " FROM " + // 
		       " pay_registrazioni_causali  " + // 
		       " INNER JOIN  " + // 
		       " pay_profili_enti_creditori ON " + // 
		       " pay_registrazioni_causali.idcomune=pay_profili_enti_creditori.idcomune " + // 
		       " INNER JOIN pay_connector_config ON " + // 
		       " pay_connector_config.codice=pay_profili_enti_creditori.codice_connettore " + //
		       " WHERE  " + // 
		       " pay_profili_enti_creditori.cf_codice_profilo = ? ";// 
	SQLQuery q = currentSession().createSQLQuery(query);
	q.addScalar("id", IntegerType.INSTANCE);
	q.addScalar("idcomune", StringType.INSTANCE);
	q.addScalar("descrizioneconn", StringType.INSTANCE);
	q.setString(0, cfcodprofilo);
	q.setResultTransformer(Transformers.aliasToBean(InfoCausaliParam.class));
	return q.list();
    }
    
    @Override
    public String findMappaturaClientByPosizioneDebitoria(PayPosizioniDebitorie payPos) {

	String query = "SELECT " + //
		       "    causali.MAPPATURA_CLIENT AS mappatura " + //
		       " FROM " +
		       "   pay_posizioni_debitorie pos " + //
		       "   INNER JOIN pay_dettaglio_importi dett ON dett.idcomune = pos.idcomune " + //
		       "   AND dett.FK_POSIZIONE_DEBITORIA = pos.id " + //
		       "   INNER JOIN pay_registrazioni_causali causali ON causali.idcomune = dett.idcomune " + //
		       "   AND causali.id = dett.FK_REG_CAUSALE " + //
		       " WHERE pos.idcomune = ? " + //
		       " AND pos.ID = ? "; //
	SQLQuery q = currentSession().createSQLQuery(query).addSynchronizedEntityClass(PayDettaglioImporti.class);
	int pos = 0;
	q.setString(pos++, payPos.getId().getIdcomune());
	q.setInteger(pos, payPos.getId().getCodice());
	q.addScalar("mappatura", StringType.INSTANCE);

	List<?> results = q.list();
	if (results.isEmpty()) {
	    return null;
	}
	return String.valueOf(results.get(0));
    }
}
