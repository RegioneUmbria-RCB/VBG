package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class QueryIstanzeStradarioDaTmpHelper extends BaseQueryHelper {

    private static final Logger logger = LoggerFactory.getLogger(QueryIstanzeStradarioDaTmpHelper.class);
    private String token;

    public QueryIstanzeStradarioDaTmpHelper(String token) {

	this.token = token;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	String debugParam = "param {}={}";
	logger.debug(debugParam, 0, ORMHelper.getIdcomune());
	q.setString(0, ORMHelper.getIdcomune()); // IDCOMUNE
	logger.debug(debugParam, 1, this.token);
	q.setString(1, this.token); // TOKEN
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idComune", Hibernate.STRING);
	q.addScalar("software", Hibernate.STRING);
	q.addScalar("codiceComune", Hibernate.STRING);
	q.addScalar("comune", Hibernate.STRING);
	q.addScalar("codiceIstat", Hibernate.STRING);
	q.addScalar("codiceIstanza", Hibernate.INTEGER);
	q.addScalar("numeroIstanza", Hibernate.STRING);
	q.addScalar("uuidIstanzeStradario", Hibernate.STRING);
	q.addScalar("codiceViario", Hibernate.STRING);
	q.addScalar("civico", Hibernate.STRING);
	q.addScalar("prefisso", Hibernate.STRING);
	q.addScalar("descrizione", Hibernate.STRING);
	q.addScalar("km", Hibernate.STRING);
	q.addScalar("primario", Hibernate.INTEGER);
	q.addScalar("latitudine", Hibernate.STRING);
	q.addScalar("longitudine", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String sql = "" + //
		"select" + //
		" istanze.idcomune as idComune, istanze.software, istanze.codicecomune as codiceComune," + //
		" comuni.comune, comuni.codiceistat as codiceIstat, istanze.codiceistanza as codiceIstanza," + //
		" istanze.numeroistanza as numeroIstanza, istanzestradario.uuid as uuidIstanzeStradario," + //
		" stradario.codviario as codiceViario, istanzestradario.civico, stradario.prefisso, stradario.descrizione," + //
		" istanzestradario.km, istanzestradario.primario," + //
		" istanzestradario.latitudine, istanzestradario.longitudine " + //
		"from" + //
		" tmp_istanze" + //
		"  inner join istanze on tmp_istanze.idcomune = istanze.idcomune and tmp_istanze.uuid = istanze.uuid" + //
		"  inner join comuni on istanze.codicecomune = comuni.codicecomune" + //
		"  inner join istanzestradario on istanze.idcomune = istanzestradario.idcomune and istanze.codiceistanza = istanzestradario.codiceistanza" + //
		"  inner join stradario on istanzestradario.idcomune = stradario.idcomune and istanzestradario.codicestradario = stradario.codicestradario " + //
		"where" + //
		"  tmp_istanze.idcomune = ? and tmp_istanze.sessionid = ?";
	return sql;
    }
}
