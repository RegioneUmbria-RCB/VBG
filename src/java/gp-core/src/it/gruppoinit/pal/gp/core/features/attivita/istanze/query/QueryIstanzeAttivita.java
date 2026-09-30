package it.gruppoinit.pal.gp.core.features.attivita.istanze.query;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;

import it.gruppoinit.pal.gp.core.dao.exception.NotImplementedException;
import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryIstanzeAttivita extends BaseQueryHelper {

    private DialettoEnum dialetto;
    private List<Param> params;
    private List<OrderByParams> orderByParams;

    public QueryIstanzeAttivita(DialettoEnum dialetto, List<Param> params, List<OrderByParams> orderByParams) {

	this.dialetto = dialetto;
	this.params = params;
	this.orderByParams = orderByParams;
    }

    private String getSelect() {

	String sql = "select" + // 
		" istanze.codiceistanza" + // 
		" , istanze.software" + // 
		" , istanze.data" + // 
		" , istanze.datavalidita" + // 
		" , istanze.dataprotocollo" + // 
		" , istanze.numeroprotocollo" + // 
		" , istanze.azione" + // 
		" , istanze.nomeattivita" + // 
		" , istanze.i_attivitaordine as iattivitaordine" + // 
		" , case " + // 
		" when titolarelegale.codiceanagrafe is null then";
	switch (dialetto) {
	    case MYSQL:
		sql += " TRIM(CONCAT_WS(' ',richiedente.nominativo ,richiedente.nome ) ) ELSE TRIM(CONCAT_WS(' ',TITOLARELEGALE.nominativo ,TITOLARELEGALE.nome ) )";
		break;
	    case ORACLE:
		sql += " TRIM(richiedente.nominativo || ' ' ||richiedente.nome ) ELSE TRIM(TITOLARELEGALE.nominativo || ' ' ||TITOLARELEGALE.nome )";
		break;
	    default:
		break;
	}
	sql += " END AS richiedente" + // 
		" , statiistanza.fkcodcomportamento";
	return sql;
    }

    private String getOrderBy() {

	if (orderByParams == null || orderByParams.isEmpty()) {
	    switch (dialetto) {
		case ORACLE:
		    return "order by" + // 
			    " coalesce(datavalidita,to_date('01/01/1900','dd/mm/yyyy')) desc" + // 
			    " , istanze.i_attivitaordine asc";
		case MYSQL:
		    return "order by" + // 
			    "  COALESCE(datavalidita,STR_TO_DATE('01/01/1900','%d-%m-%Y'))  desc" + // 
			    " , istanze.i_attivitaordine asc";
		default:
		    return "";
	    }
	}
	// TODO IMPLEMENTARE ORDER BY ALTERNATIVO
	return "";
    }

    private String getWhere(DialettoEnum dialetto, List<Param> params) {

	String ret = " where ";
	int posizione = 0;
	for (Param param : params) {
	    ret += param.getSqlFragment() + "  and ";
	    this.parameters.add(new ParameterHelper(posizione++, param.getValue(), param.getTipoValore()));
	}
	return ret.substring(0, ret.length() - 4);
    }

    private String getFrom() {

	return "from" + // 
		" istanze" + // 
		" inner join" + // 
		" anagrafe richiedente" + // 
		" on" + // 
		" richiedente.idcomune =istanze.idcomune" + // 
		" and richiedente.codiceanagrafe=istanze.codicerichiedente" + // 
		" left join" + // 
		" anagrafe titolarelegale" + // 
		" on" + // 
		" titolarelegale .idcomune =istanze.idcomune" + // 
		" and titolarelegale .codiceanagrafe=istanze.codicetitolarelegale" + // 
		" inner join" + // 
		" statiistanza" + // 
		" on" + // 
		" statiistanza.idcomune =istanze.idcomune" + // 
		" and statiistanza.codicestato=istanze.chiusura" + // 
		" and statiistanza.software =istanze.software ";
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int _position = 0;
	q.setInteger(_position, 1);
	_position++;
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	//	String sql = "select" + // 
	//		" istanze.codiceistanza" + // 
	//		" , istanze.software" + // 
	//		" , istanze.data" + // 
	//		" , istanze.datavalidita" + // 
	//		" , istanze.dataprotocollo" + // 
	//		" , istanze.numeroprotocollo" + // 
	//		" , istanze.azione" + // 
	//		" , istanze.nomeattivita" + // 
	//		" , istanze.i_attivitaordine as iattivitaordine" + // 
	//
	//	 END AS richiedente" + // 
	//		" , statiistanza.fkcodcomportamento";
	q.addScalar("codiceistanza");
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("data");
	q.addScalar("datavalidita");
	q.addScalar("dataprotocollo");
	q.addScalar("numeroprotocollo", Hibernate.STRING);
	q.addScalar("azione", Hibernate.STRING);
	q.addScalar("nomeattivita", Hibernate.STRING);
	q.addScalar("iattivitaordine", Hibernate.INTEGER);
	q.addScalar("richiedente", Hibernate.STRING);
	q.addScalar("fkcodcomportamento", Hibernate.INTEGER);
	q.addScalar("idcomune", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	if (dialetto == null || dialetto.equals(DialettoEnum.POSTGRES) || dialetto.equals(DialettoEnum.SQLSERVER)) {
	    throw new NotImplementedException("Dialetto " + dialetto + " non supportato");
	}
	if (params == null || params.isEmpty()) {
	    throw new IllegalArgumentException("Parametri nulli");
	}
	return getSelect().concat(getFrom()).concat(getWhere(dialetto, params)).concat(getOrderBy());
    }
}
