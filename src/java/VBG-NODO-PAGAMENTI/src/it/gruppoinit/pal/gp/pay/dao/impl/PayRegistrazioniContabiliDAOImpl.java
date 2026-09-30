/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayRegistrazioniContabiliDAO;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;

/**
 * @author francol
 *
 */
@Repository
public class PayRegistrazioniContabiliDAOImpl extends BaseDAOImpl<PayRegistrazioniContabili, PkId> implements PayRegistrazioniContabiliDAO {

    @Override
    public Class<PayRegistrazioniContabili> getEntityClass() {

	return PayRegistrazioniContabili.class;
    }

    @Override
    public int countPosizioniByIdRegistrazione(Integer idRegistrazioneContabile) {

	String query = "SELECT  count(*) as conteggio " + // 
		       " FROM " + // 
		       " pay_registrazioni_contabili  " + // 
		       " INNER JOIN  " + // 
		       " pay_posizioni_debitorie ON " + // 
		       " pay_posizioni_debitorie.idcomune=pay_registrazioni_contabili.idcomune and " + // 
		       " pay_posizioni_debitorie.fk_registrazione_contabile=pay_registrazioni_contabili.id " + //
		       " WHERE  " + // 
		       " pay_registrazioni_contabili.idcomune = :idcomune and pay_registrazioni_contabili.id= :idregistrazione";// 
	SQLQuery q = currentSession().createSQLQuery(query).addSynchronizedEntityClass(PayRegistrazioniContabili.class);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idregistrazione", idRegistrazioneContabile);
	q.addScalar("conteggio", IntegerType.INSTANCE);
	return (Integer) q.uniqueResult();
    }

    @Override
    public List<Integer> findIdPosizioniByIdRegistrazione(Integer idRegistrazioneContabile) {

	String query = "SELECT pay_posizioni_debitorie.id as id " + // 
		       " FROM " + // 
		       " pay_registrazioni_contabili  " + // 
		       " INNER JOIN  " + // 
		       " pay_posizioni_debitorie ON " + // 
		       " pay_posizioni_debitorie.idcomune=pay_registrazioni_contabili.idcomune and " + // 
		       " pay_posizioni_debitorie.fk_registrazione_contabile=pay_registrazioni_contabili.id " + //
		       " WHERE  " + //  
		       " pay_registrazioni_contabili.idcomune = :idcomune and pay_registrazioni_contabili.id= :idregistrazione group by pay_posizioni_debitorie.id";// 
	SQLQuery q = currentSession().createSQLQuery(query).addSynchronizedEntityClass(PayRegistrazioniContabili.class);
	q.setString("idcomune", ORMHelper.getIdcomune());
	q.setInteger("idregistrazione", idRegistrazioneContabile);
	q.addScalar("id", IntegerType.INSTANCE);
	return (List<Integer>) q.list();
    }
}
