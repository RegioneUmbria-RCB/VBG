/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayStatoPagamentiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayStatoPagamenti;

/**
 * @author francol
 *
 */
@Repository
public class PayStatoPagamentiDAOImpl extends BaseDAOImpl<PayStatoPagamenti, PkId> implements PayStatoPagamentiDAO {

    @Override
    public Class<PayStatoPagamenti> getEntityClass() {

	return PayStatoPagamenti.class;
    }
    
    @Override
    public PayStatoPagamenti.StatiPagamento findStatoByPosizioneDebitoria(PayPosizioniDebitorie posizioneDebitoria) {

	String query = "SELECT " + //
		"    stati.stato AS stato " + //
		" FROM " +
		"   pay_stato_pagamenti stati " + //
		" WHERE stati.idcomune = ? " + //
		"   AND stati.fk_posizione_debitoria = ? " + //
		" ORDER BY id DESC "; //
	SQLQuery q = currentSession().createSQLQuery(query).addSynchronizedEntityClass(PayStatoPagamenti.class);
	int pos = 0;
	q.setString(pos++, posizioneDebitoria.getId().getIdcomune());
	q.setInteger(pos, posizioneDebitoria.getId().getCodice());
	q.addScalar("stato", StringType.INSTANCE);

	List<?> results = q.list();
	if (results.isEmpty()) {
	    return null;
	}
	return PayStatoPagamenti.StatiPagamento.valueOf(String.valueOf(results.get(0)));
    }
}
