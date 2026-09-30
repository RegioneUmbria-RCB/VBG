package it.gruppoinit.pal.gp.core.features.istanze.datilocalizzativi;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;

public class QueryIstanzeStradarioDaIdHelper extends BaseQueryHelper {

    private static final Logger logger = LoggerFactory.getLogger(QueryIstanzeStradarioDaIdHelper.class);
    private Integer idIstanzeStradario;

    public QueryIstanzeStradarioDaIdHelper(Integer idIstanzeStradario) {

	this.idIstanzeStradario = idIstanzeStradario;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	String debugParam = "param {}={}";
	logger.debug(debugParam, 0, ORMHelper.getIdcomune());
	q.setString(0, ORMHelper.getIdcomune()); // IDCOMUNE
	logger.debug(debugParam, 1, this.idIstanzeStradario);
	q.setInteger(1, this.idIstanzeStradario); // TOKEN
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
		" stradario.codviario as codiceViario, istanzestradario.km, istanzestradario.primario," + //
		" istanzestradario.latitudine, istanzestradario.longitudine " + //
		"from" + //
		" istanzestradario" + //
		"  inner join istanze on istanzestradario.idcomune = istanze.idcomune and istanzestradario.codiceistanza = istanze.codiceistanza" + //
		"  inner join comuni on istanze.codicecomune = comuni.codicecomune" + //
		"  inner join stradario on istanzestradario.idcomune = stradario.idcomune and istanzestradario.codicestradario = stradario.codicestradario " + //
		"where" + //
		"  istanzestradario.idcomune = ? and istanzestradario.id = ?";
	return sql;
    }
}
