package it.gruppoinit.pal.gp.core.features.manifestazioni.blacklist;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.gruppoinit.pal.gp.core.dao.helper.BaseQueryHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ORMHelper;
import it.gruppoinit.pal.gp.core.dao.helper.ParameterHelper;
import it.gruppoinit.pal.gp.core.features.database.DialettoEnum;

public class QueryBlackListMercatiUsoHelper extends BaseQueryHelper {

    private static final Logger log = LoggerFactory.getLogger(QueryBlackListMercatiUsoHelper.class);
    private Integer idMercatiUso;
    BlackListContestoEnum[] contesti = null;

    public QueryBlackListMercatiUsoHelper(SessionFactoryImplementor sessimpl, Integer idMercatiUso, BlackListContestoEnum[] contesti) {

	super();
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryBlackListMercatiUsoHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryBlackListMercatiUsoHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryBlackListMercatiUsoHelper: schemaName={}", schemaName);
	this.idMercatiUso = idMercatiUso;
	log.debug("QueryBlackListMercatiUsoHelper: idMercatiUso={}", idMercatiUso);
	this.contesti = contesti;
    }

    @Override
    public void setFilterValues(SQLQuery q) {

	for (ParameterHelper parameter : parameters) {
	    log.debug("param {}={}", parameter.getPosition(), parameter.getValue());
	    q.setParameter(parameter.getPosition(), parameter.getValue(), parameter.getType());
	}
    }

    @Override
    public void setScalarProperties(SQLQuery q) {

	q.addScalar("autorizzazioniId", Hibernate.INTEGER);
	q.addScalar("mercatiUsoId", Hibernate.INTEGER);
	q.addScalar("contesto", Hibernate.STRING);
	q.addScalar("codicemercato", Hibernate.INTEGER);
    }

    @Override
    public String buildQuery() {

	String sql = "select " + //
		     "  blacklist_autorizzazioni.fk_id_autorizzazione as autorizzazioniid," + //
		     "  blacklist_autorizzazioni.fk_idmercatiuso as mercatiusoid, " + //
		     "  blacklist_motivi.contesto, " + //
		     "  autorizzazioni_concessioni.fk_codicemercato as codicemercato " + //
		     "from" + //
		     "  blacklist_motivi" + //
		     "    inner join blacklist_autorizzazioni on" + //
		     "      blacklist_motivi.idcomune = blacklist_autorizzazioni.idcomune and" + //
		     "      blacklist_motivi.id = blacklist_autorizzazioni.fk_id_blacklist_mot " + //
		     "    inner join autorizzazioni on" + //
		     "      blacklist_autorizzazioni.idcomune = autorizzazioni.idcomune and" + //
		     "      blacklist_autorizzazioni.fk_id_autorizzazione = autorizzazioni.id " + //
		     "    left join autorizzazioni_concessioni on" + //
		     "      autorizzazioni.idcomune = autorizzazioni_concessioni.idcomune and" + //
		     "      autorizzazioni.id = autorizzazioni_concessioni.fk_idaut_attuale " + //
		     "where " + //
		     "  blacklist_motivi.idcomune = ? and" + //
		     "  blacklist_motivi.data_fine_bl is null" + //
		     "  and (blacklist_autorizzazioni.fk_idmercatiuso = ? or blacklist_autorizzazioni.fk_idmercatiuso is null ) ";//  + //
	if (contesti != null && contesti.length > 0) {
	    String p1 = StringUtils.repeat("?,", contesti.length);
	    p1 = StringUtils.removeEnd(p1, ",");
	    sql += " and contesto in (" + p1 + ")";
	}
	sql += " group by" + //
	       "  blacklist_autorizzazioni.fk_id_autorizzazione," + //
	       "  blacklist_autorizzazioni.fk_idmercatiuso," + //
	       "  blacklist_motivi.contesto," + //
	       "  autorizzazioni_concessioni.fk_codicemercato ";
	int position = 0;
	parameters.add(new ParameterHelper(position++, ORMHelper.getIdcomune(), new StringType()));
	parameters.add(new ParameterHelper(position++, this.idMercatiUso, new IntegerType()));
	if (contesti != null && contesti.length > 0) {
	    for (BlackListContestoEnum con : contesti) {
		parameters.add(new ParameterHelper(position++, con.name().toLowerCase(), new StringType()));
	    }
	}
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
