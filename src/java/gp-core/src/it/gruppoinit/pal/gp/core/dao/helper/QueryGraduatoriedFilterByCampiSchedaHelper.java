package it.gruppoinit.pal.gp.core.dao.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.mutable.MutableInt;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.Dyn2CampiDAO;
import it.gruppoinit.pal.gp.core.domain.web.GraduatoriedFilter;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

/**
 * <pre>
 * La classe crea una query che verifica se i campi dinamici dell' istanza associata alla graduatoria d di rispetta le
 * condizioni impostate sul filtro
 * 
 * ES. 
 * Come filtro impostato la scheda A e i valori dei campi CAMPOA valore 100 e CAMPOB valore maggiore 01/03/2013
 * 
 * Se l'istanza rispetta le stesse condizioni sui propri campi dinamici allora uno altrimento 0
 * 
 * @author gianpaolot
 * </pre>
 * 
 */
public class QueryGraduatoriedFilterByCampiSchedaHelper extends BaseQueryHelper {

    public QueryGraduatoriedFilterByCampiSchedaHelper(SessionFactoryImplementor sessimpl, GraduatoriedFilter graduatoriedFilter,
	    Dyn2CampiDAO dyn2CampiDAO) {

	this.dyn2CampiDAO = dyn2CampiDAO;
	this.filter = graduatoriedFilter;
	log.debug("QueryIAttivitaHelper: recupero il dialetto della SessionFactoryImplementor");
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryIAttivitaHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryIAttivitaHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryIAttivitaHelper: schemaName={}", schemaName);
    }

    @Override
    public String buildQuery() {

	// dal filtro costruisco la query
	//String result = isCountQuery == true ? countQuery : selectQuery;
	String result = countQuery;
	result += fromQuery + whereQuery;
	int position = 2;
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	MutableInt posRef = new MutableInt(position);
	result += createSQLFragment("istanze.", "codiceistanza", "codiceistanza", "istanzedyn2dati", schemaName, filter.getSchedaDinamicaFilter(),
		posRef);
	position = posRef.intValue();
	////////////////////////////////////////////////
	////////////////////////////////////////////////
	if (StringUtils.isNotBlank(schemaName)) {
	    result = result.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), result);
	return result;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	int _position = 0;
	log.debug("param {}={}", _position, ORMHelper.getIdcomune());
	q.setString(_position, ORMHelper.getIdcomune()); // IDCOMUNE
	_position++;
	q.setInteger(_position, filter.getGraduatoriedDTO().getId().getCodice()); // CODICE GRADUATORIAD
	_position++;
	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("idcomune", Hibernate.STRING);
	q.addScalar("id", Hibernate.BIG_DECIMAL);
    }

    private String countQuery = "select count(*) as cont_graduatoried_compatibili ";
    private String fromQuery = " from" +
	    " " +
	    SCHEMA_NAME +
	    "graduatoried " +
	    " left join " +
	    SCHEMA_NAME +
	    "istanze on graduatoried.idcomune=istanze.idcomune and graduatoried.codiceistanza=istanze.codiceistanza";
    private String whereQuery = " where graduatoried.idcomune = ? and graduatoried.id = ?";
    private GraduatoriedFilter filter;
    private static final Logger log = LoggerFactory.getLogger(QueryGraduatoriedFilterByCampiSchedaHelper.class);
}
