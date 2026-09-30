package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;

public class QueryPosizioniDaAggiungereABLBollHelper extends QueryPosizioniDaAggiungereABlackListHelper{
    
    public QueryPosizioniDaAggiungereABLBollHelper(SessionFactoryImplementor sessimpl, List<String> statiPagabili) {

	super(sessimpl, statiPagabili);
	// TODO Auto-generated constructor stub
    }

    @Override
    public String buildQuery() {
	String parStati = StringUtils.repeat("?,", statiPagabili.size());
	parStati = parStati.substring(0, parStati.length() - 1);
	
	String sql = "SELECT "   
		+" COALESCE(rate.id,dett_posizione_debitoria.id) AS idDettPosizioneDebitoria, "  
		+" autorizzazioni.ID AS idAutorizzazione, "
		+" boll_gest_dettaglio.FK_CODICEANAGRAFE AS codiceanagrafe "
		+"        FROM "
		+"          BOLL_GEST_DETTAGLIO "
		+"            INNER JOIN  boll_gest_dett_autorizz ON "
		+"            boll_gest_dett_autorizz.IDCOMUNE= BOLL_GEST_DETTAGLIO.IDCOMUNE AND "
		+"            boll_gest_dett_autorizz.FK_BOLLGESTDET_ID= BOLL_GEST_DETTAGLIO.ID "
		+"            INNER JOIN autorizzazioni ON " 
		+"              boll_gest_dett_autorizz.idcomune = autorizzazioni.idcomune AND " 
		+"              boll_gest_dett_autorizz.FK_AUTORIZZAZIONE_ID = autorizzazioni.id " 
		+"             LEFT JOIN dett_posizione_debitoria   ON "  
		+"              BOLL_GEST_DETTAGLIO.idcomune=dett_posizione_debitoria.idcomune AND "  
		+"              BOLL_GEST_DETTAGLIO.FK_POSDEBDETTAGLIO_ID=dett_posizione_debitoria.id "  
		+"             LEFT JOIN boll_gest_dett_rate ON "              
		+"              BOLL_GEST_DETTAGLIO.idcomune=boll_gest_dett_rate.idcomune AND " 
		+"              BOLL_GEST_DETTAGLIO.id=boll_gest_dett_rate.FK_BOLLGESTDET_ID "
		+"      LEFT JOIN dett_posizione_debitoria rate ON " 
		+"       boll_gest_dett_rate.idcomune=rate.idcomune AND " 
		+"       boll_gest_dett_rate.FK_POSDEBDETTAGLIO_ID=rate.id "
		+"        WHERE "
		+"          BOLL_GEST_DETTAGLIO.idcomune = ? AND "  
		+"          BOLL_GEST_DETTAGLIO.FLAG_VALIDATA=? AND "
		+"          (dett_posizione_debitoria.ID is not null or rate.ID is not null) AND "
		+"          ( "
		+"          dett_posizione_debitoria.stato IN ( "  
		+"        "+parStati+" " 
		+"         ) " 
		+"         OR "
		+"          rate.stato IN ( " 
		+"        "+parStati+" "
		+"         ) "
		+"         ) "
		+"         AND " 
		+"         ( NOT EXISTS " 
		+"          ( "  
		+"            SELECT 1 "  
		+"            FROM blacklist_src_p_deb_sp "  
		+"              INNER JOIN blacklist_motivi ON "  
		+"                blacklist_src_p_deb_sp.idcomune = blacklist_motivi.idcomune AND "  
		+"                blacklist_src_p_deb_sp.fk_id_blacklist_mot = blacklist_motivi.id AND " 
		+"                blacklist_motivi.data_fine_bl IS NULL "  
		+"            WHERE "  
		+"              blacklist_src_p_deb_sp.idcomune = dett_posizione_debitoria.idcomune AND " 
		+"              blacklist_src_p_deb_sp.FK_ID_PAYPOS_DEB = dett_posizione_debitoria.id " 
		+"          ) "  
		+"          AND " 
		+"          NOT EXISTS " 
		+"          ( "  
		+"            SELECT 1 " 
		+"            FROM blacklist_src_p_deb_sp "  
		+"              INNER JOIN blacklist_motivi ON "  
		+"                blacklist_src_p_deb_sp.idcomune = blacklist_motivi.idcomune AND "  
		+"                blacklist_src_p_deb_sp.fk_id_blacklist_mot = blacklist_motivi.id AND "  
		+"                blacklist_motivi.data_fine_bl IS NULL "  
		+"            WHERE "  
		+"              blacklist_src_p_deb_sp.idcomune = rate.idcomune AND "  
		+"              blacklist_src_p_deb_sp.FK_ID_PAYPOS_DEB = rate.id " 
		+"          ) "  
		+"          ) "
		+" group by idDettPosizioneDebitoria, idAutorizzazione, codiceanagrafe "
		;
	
	
	int position = 0;
	
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, 1, new IntegerType()));
	position++;
	
	//VA FATTO DUE VOLTE
	for (String stato : statiPagabili) {
	    parameters.add(new ParameterHelper(position, stato, new StringType()));
	    position++;
	}
	for (String stato : statiPagabili) {
	    parameters.add(new ParameterHelper(position, stato, new StringType()));
	    position++;
	}
	
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
    
    @Override
    public void setScalarProperties(SQLQuery q) {
	q.addScalar("idDettPosizioneDebitoria", Hibernate.INTEGER);
	q.addScalar("idAutorizzazione", Hibernate.INTEGER);
	q.addScalar("codiceanagrafe", Hibernate.INTEGER);
    }
}
