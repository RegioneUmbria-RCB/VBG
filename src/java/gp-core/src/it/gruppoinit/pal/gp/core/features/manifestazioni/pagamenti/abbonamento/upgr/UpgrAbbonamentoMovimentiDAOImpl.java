package it.gruppoinit.pal.gp.core.features.manifestazioni.pagamenti.abbonamento.upgr;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import it.gruppoinit.pal.gp.core.dao.impl.BaseDAOImpl;
import it.gruppoinit.pal.gp.core.domain.BorsellinoMovimenti;
import it.gruppoinit.pal.gp.core.domain.DettPosizioneDebitoria;

@SuppressWarnings("rawtypes")
@Repository
public class UpgrAbbonamentoMovimentiDAOImpl extends BaseDAOImpl implements UpgrAbbonamentoMovimentiDAO{

    @Override
    public Class getEntityClass() {
	return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllBorsellini(){
	//Non dovrebbe essere richiesto l idcomune, perche l obiettivo e di estrarli tutti
	//Conviene estrarre solo quelli con saldo null, cosi se si dovesse bloccare non riparte il processo su tutti
	String qry = "select idcomune, id from borsellino where saldo_totale is null order by idcomune";
	SQLQuery q = getSession().createSQLQuery(qry);
	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("id", Hibernate.INTEGER);
	return q.list();
    }

    @Override
    public void updateCreditiMovimento(String idcomune, Integer idmovimento, BigDecimal creditoiniziale, BigDecimal creditofinale) {

	String sql = "update borsellino_movimenti set credito_iniziale = ?, credito_finale = ? where idcomune = ? and id = ?";
	SQLQuery query = getSession().createSQLQuery(sql).addSynchronizedEntityClass(BorsellinoMovimenti.class);
	query.setBigDecimal(0, creditoiniziale);
	query.setBigDecimal(1, creditofinale);
	query.setString(2, idcomune);
	query.setInteger(3, idmovimento);
	query.executeUpdate();
	
    }
    
    
}
