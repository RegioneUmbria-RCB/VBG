/**
 * 
 */
package it.gruppoinit.pal.gp.pay.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.PkId;
import it.gruppoinit.pal.gp.pay.dao.PayDettaglioImportiDAO;
import it.gruppoinit.pal.gp.pay.domain.PayDettaglioImporti;
import it.gruppoinit.pal.gp.pay.domain.PayPosizioniDebitorie;
import it.gruppoinit.pal.gp.pay.domain.PayRegistrazioniContabili;

/**
 * @author francol
 *
 */
@Repository
public class PayDettaglioImportiDAOImpl extends BaseDAOImpl<PayDettaglioImporti, PkId> implements PayDettaglioImportiDAO {

    @Override
    public Class<PayDettaglioImporti> getEntityClass() {

	return PayDettaglioImporti.class;
    }

    @Override
    public List<Integer> findCausaliRaggruppate(List<Integer> idRcs) {

	String query = "SELECT " + //
		"   pay_dettaglio_importi.FK_REG_CAUSALE as codice  " + //
		" FROM " + //
		"   pay_registrazioni_contabili " + //
		"   INNER JOIN pay_posizioni_debitorie ON pay_posizioni_debitorie.idcomune = pay_registrazioni_contabili.idcomune " + //
		"   AND pay_posizioni_debitorie.FK_REGISTRAZIONE_CONTABILE = pay_registrazioni_contabili.id " + //
		"   INNER JOIN pay_dettaglio_importi ON pay_dettaglio_importi.idcomune = pay_posizioni_debitorie.idcomune " + //
		"   AND pay_dettaglio_importi.FK_POSIZIONE_DEBITORIA = pay_posizioni_debitorie.id " + //
		" WHERE " + //
		"   pay_registrazioni_contabili.idcomune = ? " + //
		"   AND pay_registrazioni_contabili.ID IN (LISTACAUSALI) " + //
		" GROUP BY " + //
		"   pay_dettaglio_importi.FK_REG_CAUSALE";
	// 
	String qm = StringUtils.repeat("?,", idRcs.size());
	qm = qm.substring(0, qm.length() - 1);
	query = query.replace("LISTACAUSALI", qm);
	//
	SQLQuery q = currentSession().createSQLQuery(query).addSynchronizedEntityClass(PayRegistrazioniContabili.class) //
		.addSynchronizedEntityClass(PayPosizioniDebitorie.class) //
		.addSynchronizedEntityClass(PayDettaglioImporti.class);
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	for (Integer id : idRcs) {
	    q.setInteger(pos++, id);
	}
	q.addScalar("codice", IntegerType.INSTANCE);
	return q.list();
    }

    @Override
    public List<Integer> findCausaliRaggruppatePosizioneDebitoria(PayPosizioniDebitorie payPos) {

	String query = "SELECT " + //
		"   pay_dettaglio_importi.FK_REG_CAUSALE as codice  " + //
		" FROM " + //
		"   pay_posizioni_debitorie " + //
		"   INNER JOIN pay_dettaglio_importi ON pay_dettaglio_importi.idcomune = pay_posizioni_debitorie.idcomune " + //
		"   AND pay_dettaglio_importi.FK_POSIZIONE_DEBITORIA = pay_posizioni_debitorie.id " + //
		" WHERE " + //
		"   pay_posizioni_debitorie.idcomune = ? " + //
		"   AND pay_posizioni_debitorie.ID = ? " + //
		" GROUP BY " + //
		"   pay_dettaglio_importi.FK_REG_CAUSALE";
	//
	SQLQuery q = currentSession().createSQLQuery(query).addSynchronizedEntityClass(PayDettaglioImporti.class);
	int pos = 0;
	q.setString(pos++, ORMHelper.getIdcomune());
	q.setInteger(pos++, payPos.getId().getCodice());
	q.addScalar("codice", IntegerType.INSTANCE);
	return q.list();
    }
}
