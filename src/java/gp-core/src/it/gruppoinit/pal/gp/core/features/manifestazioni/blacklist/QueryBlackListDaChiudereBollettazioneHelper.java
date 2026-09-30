package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.StringType;

import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;

public class QueryBlackListDaChiudereBollettazioneHelper extends QueryBlackListDaChiudereHelper{

    public QueryBlackListDaChiudereBollettazioneHelper(SessionFactoryImplementor sessimpl) {

	super(sessimpl);
	// TODO Auto-generated constructor stub
    }
    
    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idBlackListMotivi", Hibernate.INTEGER);
	q.addScalar("idDettPosizioneDebitoria", Hibernate.INTEGER);
	q.addScalar("stato", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String sql = "SELECT "  
		+"    blacklist_motivi.id AS idBlackListMotivi, "  
		+"    dett_posizione_debitoria.id AS idDettPosizioneDebitoria, " 
		+"    dett_posizione_debitoria.stato "   
		+"   FROM "
		+"    blacklist_motivi "
		+"      INNER JOIN blacklist_src_p_deb_sp ON " 
		+"         blacklist_motivi.idcomune = blacklist_src_p_deb_sp.idcomune AND "
		+"         blacklist_motivi.id = blacklist_src_p_deb_sp.fk_id_blacklist_mot " 
		+"      LEFT JOIN dett_posizione_debitoria ON "
		+"         blacklist_src_p_deb_sp.idcomune = dett_posizione_debitoria.idcomune AND "
		+"         blacklist_src_p_deb_sp.fk_id_paypos_deb = dett_posizione_debitoria.id "
		+"   WHERE "
		+"    blacklist_motivi.idcomune = ? AND "
		+"    blacklist_motivi.contesto = ? AND "
		+"    blacklist_motivi.data_fine_bl IS NULL ";
	int position = 0;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	position++;
	parameters.add(new ParameterHelper(position, BlackListContestoEnum.BOLLETTAZIONE.name().toLowerCase(), new StringType()));
	
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
