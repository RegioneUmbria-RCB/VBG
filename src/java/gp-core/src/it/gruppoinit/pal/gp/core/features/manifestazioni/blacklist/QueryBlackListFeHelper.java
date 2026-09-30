package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.paevolution.ws.pagamenti_types.StatoPagamentoType;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;
import it.gruppoinit.pal.gp.core.features.nodopagamenti.StatiPosizioniDebitorieConverter;

public class QueryBlackListFeHelper extends BaseQueryHelper {
    
    protected static final Logger log = LoggerFactory.getLogger(QueryBlackListFeHelper.class);
    private static final String SENZA_ACCERTAMENTO = "Senza accertamento";
    private static final String ACCERTAMENTO_NON_VALORIZZATO = "-";
    
    private BlackListContestoEnum contesto;
    private Map<String, Object> params = new HashMap<String,Object>();
    
    public QueryBlackListFeHelper(SessionFactoryImplementor sessimpl, BlackListContestoEnum contesto) {
	
	if(BlackListContestoEnum.PRESENZE != contesto && BlackListContestoEnum.BOLLETTAZIONE != contesto){
	    throw new RuntimeException("Contesto non valido");
	}
	
	this.contesto = contesto;
	
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryBlackListFeHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryBlackListFeHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryBlackListFeHelper: schemaName={}", schemaName);
    }
    public String concatAndConditions(
	    
	    String dataaccertamento,
	    Date dalladatablacklist,
	    Date alladatablacklist,
	    Date dalladataiuv,
	    Date alladataiuv,
	    String iuv,
	    String titolare,
	    String cf
	    ){
	
	StringBuilder sb = new StringBuilder();
	
	if(!StringUtils.isBlank(dataaccertamento) && !ACCERTAMENTO_NON_VALORIZZATO.equals(dataaccertamento)){	    
	    if(SENZA_ACCERTAMENTO.equals(dataaccertamento)){
		sb.append(" AND BLACKLIST_MOTIVI.DATA_ACCERTAMENTO IS NULL ");
	    }else{
		sb.append(" AND BLACKLIST_MOTIVI.DATA_ACCERTAMENTO = :dataaccertamento ");
		try {
		    params.put("dataaccertamento", getDateByString(dataaccertamento));
		} catch (ParseException e) {
		   throw new RuntimeException(e);
		}
	    }   
	}
	
	if(dalladatablacklist != null){
	    sb.append(" AND BLACKLIST_MOTIVI.DATA_INIZIO_BL >= :dalladatablacklist ");
	    params.put("dalladatablacklist", dalladatablacklist);
	}
	if(alladatablacklist != null){
	    sb.append(" AND BLACKLIST_MOTIVI.DATA_INIZIO_BL < :alladatablacklist ");
	    params.put("alladatablacklist", giornoSuccessivo(alladatablacklist));
	}
	if(dalladataiuv != null){
	    sb.append(" AND DETT_POSIZIONE_DEBITORIA.DATA_REGISTRAZIONE >= :dalladataiuv ");
	    params.put("dalladataiuv", dalladataiuv);
	}
	if(alladataiuv != null){
	    sb.append(" AND DETT_POSIZIONE_DEBITORIA.DATA_REGISTRAZIONE < :alladataiuv ");
	    params.put("alladataiuv", giornoSuccessivo(alladataiuv));
	}
	if(!StringUtils.isBlank(iuv)){
	    sb.append(" AND DETT_POSIZIONE_DEBITORIA.IUV LIKE :iuv ");
	    params.put("iuv", iuv+"%");
	}
	
	//Questo è da vedere, facciamo così per ora
	if(!StringUtils.isBlank(titolare)){
	    /*
	     * Così va bene, concat (nominativo,' ') comunque soddisfa il like.
	     * Esempio 'GJONI ' soddisferebbe GJONI%
	     */
	    sb.append(" AND UPPER(") .append(getConcatAnagrafeFunction(this._dialetto)).append(")").append(" LIKE :titolare ");
	    params.put("titolare", titolare.toUpperCase()+"%");
	}
	
	if(!StringUtils.isBlank(cf)){
	    sb.append(" AND UPPER(ANAGRAFE.CODICEFISCALE) LIKE :cf ");
	    params.put("cf", cf.toUpperCase()+"%");
	}
	
	return sb.toString();
	
    }
    private static Date getDateByString(String dateStr) throws ParseException{
	if(StringUtils.isBlank(dateStr)){
	    return null;
	}
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	return sdf.parse(dateStr);
    }
    
    public static String sqlBollettazioneQuery(){
	return "SELECT  DISTINCT "
		+ " BLACKLIST_MOTIVI.ID AS IDBLACKLISTMOTIVI,     "
		+ " DETT_POSIZIONE_DEBITORIA.ID AS IDDETTPOSIZIONEDEBITORIA,    "
		+ " DETT_POSIZIONE_DEBITORIA.IUV,  "
		+ " DETT_POSIZIONE_DEBITORIA.DATA_REGISTRAZIONE AS DATA,  "
		+ " DETT_POSIZIONE_DEBITORIA.DESCRIZIONE_CAUSALE AS DESCRIZIONE,  "
		+ " ANAGRAFE.NOMINATIVO,  "
		+ " ANAGRAFE.NOME,  "
		+ " ANAGRAFE.CODICEFISCALE,  "
		+ " DETT_POSIZIONE_DEBITORIA.IMPORTO_IVATO AS IMPORTO,  "
		+ " BLACKLIST_MOTIVI.DATA_INIZIO_BL  AS DATAINIZIOBLACKLIST,    "
		+ " BLACKLIST_MOTIVI.DATA_ACCERTAMENTO  AS DATAACCERTAMENTO      "
		+ " FROM   "
		+ " BLACKLIST_MOTIVI   "
		+ "    INNER JOIN BLACKLIST_SRC_P_DEB_SP ON    "
		+ "       BLACKLIST_MOTIVI.IDCOMUNE = BLACKLIST_SRC_P_DEB_SP.IDCOMUNE AND   "
		+ "       BLACKLIST_MOTIVI.ID = BLACKLIST_SRC_P_DEB_SP.FK_ID_BLACKLIST_MOT    "
		+ "    LEFT JOIN DETT_POSIZIONE_DEBITORIA ON   "
		+ "       BLACKLIST_SRC_P_DEB_SP.IDCOMUNE = DETT_POSIZIONE_DEBITORIA.IDCOMUNE AND   "
		+ "       BLACKLIST_SRC_P_DEB_SP.FK_ID_PAYPOS_DEB = DETT_POSIZIONE_DEBITORIA.ID  "
		+ "    JOIN BLACKLIST_AUTORIZZAZIONI ON BLACKLIST_AUTORIZZAZIONI.IDCOMUNE = BLACKLIST_MOTIVI.IDCOMUNE AND BLACKLIST_AUTORIZZAZIONI.FK_ID_BLACKLIST_MOT = BLACKLIST_MOTIVI.ID "
		+ "    LEFT JOIN ANAGRAFE ON  "
		+ "       BLACKLIST_AUTORIZZAZIONI.IDCOMUNE = ANAGRAFE.IDCOMUNE AND  "
		+ "       BLACKLIST_AUTORIZZAZIONI.FK_CODICEANAGRAFE = ANAGRAFE.CODICEANAGRAFE "
		+ " WHERE   "
		+ " BLACKLIST_MOTIVI.IDCOMUNE = :idcomune AND   "
		+ " BLACKLIST_MOTIVI.CONTESTO = :contesto AND  "
		+ " DETT_POSIZIONE_DEBITORIA.STATO IN (:statipagabili)  "
		+ " AND BLACKLIST_MOTIVI.DATA_FINE_BL IS NULL  "
		+ " AND BLACKLIST_AUTORIZZAZIONI.FLAG_PRINCIPALE =1 ";
    }
    public static void setScalarPropertiesBollettazione(SQLQuery q){
	q.addScalar("idblacklistmotivi", Hibernate.INTEGER);
	q.addScalar("iddettposizionedebitoria", Hibernate.INTEGER);
	q.addScalar("iuv", Hibernate.STRING);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("importo", Hibernate.BIG_DECIMAL);
	q.addScalar("dataInizioBlacklist", Hibernate.DATE);
	q.addScalar("dataAccertamento", Hibernate.DATE);
    }
    
    public static String sqlPresenzeQuery(){
	return "SELECT   "
		+ "BLACKLIST_MOTIVI.ID AS IDBLACKLISTMOTIVI,   "
		+ "DETT_POSIZIONE_DEBITORIA.ID AS IDDETTPOSIZIONEDEBITORIA,  "
		+ "DETT_POSIZIONE_DEBITORIA.IUV, "
		+ "DETT_POSIZIONE_DEBITORIA.DESCRIZIONE_CAUSALE AS DESCRIZIONE, "
		+ "MERCATIPRESENZE_T.DESCRIZIONE AS MERCATO, "
		+ "DETT_POSIZIONE_DEBITORIA.DATA_REGISTRAZIONE AS DATA, "
		+ "ANAGRAFE.NOMINATIVO, "
		+ "ANAGRAFE.NOME, "
		+ "ANAGRAFE.CODICEFISCALE, "
		+ "DETT_POSIZIONE_DEBITORIA.IMPORTO_IVATO AS IMPORTO, "
		+ "BLACKLIST_MOTIVI.DATA_INIZIO_BL  AS DATAINIZIOBLACKLIST,     "
		+ "BLACKLIST_MOTIVI.DATA_ACCERTAMENTO  AS DATAACCERTAMENTO     "
		+ "FROM  "
		+ "BLACKLIST_MOTIVI  "
		+ "   INNER JOIN BLACKLIST_SRC_P_DEB_SP ON  "
		+ "      BLACKLIST_MOTIVI.IDCOMUNE = BLACKLIST_SRC_P_DEB_SP.IDCOMUNE AND  "
		+ "      BLACKLIST_MOTIVI.ID = BLACKLIST_SRC_P_DEB_SP.FK_ID_BLACKLIST_MOT  "
		+ "   LEFT JOIN DETT_POSIZIONE_DEBITORIA ON  "
		+ "      BLACKLIST_SRC_P_DEB_SP.IDCOMUNE = DETT_POSIZIONE_DEBITORIA.IDCOMUNE AND  "
		+ "      BLACKLIST_SRC_P_DEB_SP.FK_ID_PAYPOS_DEB = DETT_POSIZIONE_DEBITORIA.ID "
		+ "   JOIN BLACKLIST_AUTORIZZAZIONI ON BLACKLIST_AUTORIZZAZIONI.IDCOMUNE = BLACKLIST_MOTIVI.IDCOMUNE AND BLACKLIST_AUTORIZZAZIONI.FK_ID_BLACKLIST_MOT = BLACKLIST_MOTIVI.ID "
		+ "   LEFT JOIN ANAGRAFE ON "
		+ "      BLACKLIST_AUTORIZZAZIONI.IDCOMUNE = ANAGRAFE.IDCOMUNE AND "
		+ "      BLACKLIST_AUTORIZZAZIONI.FK_CODICEANAGRAFE = ANAGRAFE.CODICEANAGRAFE       "
		+ "   LEFT JOIN MERCATIPRESENZE_D ON  "
		+ "		       DETT_POSIZIONE_DEBITORIA.IDCOMUNE = MERCATIPRESENZE_D.IDCOMUNE AND  "
		+ "		       DETT_POSIZIONE_DEBITORIA.ID = MERCATIPRESENZE_D.FK_PAY_POS_DEB   "
		+ "   INNER JOIN MERCATIPRESENZE_T ON "
		+ "     MERCATIPRESENZE_D.IDCOMUNE = MERCATIPRESENZE_T.IDCOMUNE AND "
		+ "     MERCATIPRESENZE_D.FKIDTESTATA = MERCATIPRESENZE_T.ID "
		+ "	WHERE  "
		+ "	 BLACKLIST_MOTIVI.IDCOMUNE = :idcomune AND  "
		+ "	 BLACKLIST_MOTIVI.DATA_FINE_BL IS NULL AND  "
		+ "	 BLACKLIST_MOTIVI.CONTESTO = :contesto "
		+ "	 AND BLACKLIST_AUTORIZZAZIONI.FLAG_PRINCIPALE =1 "
		+ "     AND DETT_POSIZIONE_DEBITORIA.STATO IN (:statipagabili) ";
    }
    public static void setScalarPropertiesPresenze(SQLQuery q){
	q.addScalar("idblacklistmotivi", Hibernate.INTEGER);
	q.addScalar("iddettposizionedebitoria", Hibernate.INTEGER);
	q.addScalar("iuv", Hibernate.STRING);
	q.addScalar("mercato", Hibernate.STRING);
	q.addScalar("data", Hibernate.DATE);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("nominativo", Hibernate.STRING);
	q.addScalar("nome", Hibernate.STRING);
	q.addScalar("codicefiscale", Hibernate.STRING);
	q.addScalar("importo", Hibernate.BIG_DECIMAL);
	q.addScalar("dataInizioBlacklist", Hibernate.DATE);
	q.addScalar("dataAccertamento", Hibernate.DATE);
    }
    
    
    
    @Override
    public void setFilterValues(SQLQuery q) {

	q.setParameter("idcomune", ORMHelper.getIdcomune());
	q.setParameter("contesto", contesto.name().toLowerCase());
	q.setParameterList("statipagabili", getStatiPagabiliInput());
	for(Map.Entry<String, Object> entry : params.entrySet()){
	    q.setParameter(entry.getKey(), entry.getValue());
	}
	
    }
    @Override
    public void setScalarProperties(SQLQuery q) {

	if(BlackListContestoEnum.PRESENZE == contesto){
	    setScalarPropertiesPresenze(q); 
	}else if(BlackListContestoEnum.BOLLETTAZIONE == contesto){
	    setScalarPropertiesBollettazione(q);
	}
	
    }
    @Override
    public String buildQuery() {

	if(BlackListContestoEnum.PRESENZE == contesto){
	    return QueryBlackListFeHelper.sqlPresenzeQuery(); 
	}else if(BlackListContestoEnum.BOLLETTAZIONE == contesto){
	    return QueryBlackListFeHelper.sqlBollettazioneQuery();
	}
	return null;
    }
    
    private static String getConcatAnagrafeFunction(DialettoEnum dialetto) {
	String result = "";
	switch (dialetto) {
	     case MYSQL:
	    	 result = "CONCAT(ANAGRAFE.NOMINATIVO, ' ', COALESCE(ANAGRAFE.NOME,''))";
	    	 break;
	     case ORACLE:
	     case POSTGRES:
	    	 result = "(ANAGRAFE.NOMINATIVO || ' ' || COALESCE(ANAGRAFE.NOME,''))";
	    	 break;
	     case SQLSERVER:
	    	 result = "(ANAGRAFE.NOMINATIVO + ' ' + COALESCE(ANAGRAFE.NOME,''))";
	    	 break;
	     default:
	    	 break;
	}
	return result;
   }
    
    private static List<String> getStatiPagabiliInput(){
	List<String> returnL = new ArrayList<String>();
	StatoPagamentoType[] statipagabili = new StatiPosizioniDebitorieConverter().getStatiPosizioniPagabili();
	for(StatoPagamentoType stato : statipagabili){
	    returnL.add(stato.name());  
	}
	return returnL;
    }
    
    private static Date startOfDay(Date data) {
	    if (data == null) {
	        return null;
	    }

	    Calendar cal = Calendar.getInstance();
	    cal.setTime(data);
	    cal.set(Calendar.HOUR_OF_DAY, 0);
	    cal.set(Calendar.MINUTE, 0);
	    cal.set(Calendar.SECOND, 0);
	    cal.set(Calendar.MILLISECOND, 0);

	    return cal.getTime();
    }
    private static Date giornoSuccessivo(Date data) {
	    if (data == null) {
	        return null;
	    }

	    Calendar cal = Calendar.getInstance();
	    cal.setTime(startOfDay(data));
	    cal.add(Calendar.DAY_OF_MONTH, 1);

	    return cal.getTime();
    }
}
