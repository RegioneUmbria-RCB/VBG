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

public class QueryBlackListDaChiudereHelper extends BaseQueryHelper {

    protected static final Logger log = LoggerFactory.getLogger(QueryBlackListDaChiudereHelper.class);
    protected static final Integer FLAG_PRINCIPALE = 1;

    public QueryBlackListDaChiudereHelper(SessionFactoryImplementor sessimpl) {

	super();
	Dialect dialetto = sessimpl.getDialect();
	log.debug("QueryBlackListDaChiudereHelper: Il dialetto della SessionFactoryImplementor è {}", dialetto);
	String hibernateDialect = dialetto.toString();
	this._dialetto = DialettoEnum.fromHibernateDialect(hibernateDialect);
	log.debug("QueryPosizioniDaAggiungereABlackListHelper: Il dialetto è {}", _dialetto);
	this.schemaName = StringUtils.defaultIfEmpty(sessimpl.getSettings().getDefaultSchemaName(), "");
	log.debug("QueryPosizioniDaAggiungereABlackListHelper: schemaName={}", schemaName);
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

	q.addScalar("idBlackListMotivi", Hibernate.INTEGER);
	q.addScalar("idMercatiPresenzeD", Hibernate.INTEGER);
	q.addScalar("idAutBlackList", Hibernate.INTEGER);
	q.addScalar("idAutMercatiPresenzeD", Hibernate.INTEGER);
	q.addScalar("idBlackListSrcPDebSp", Hibernate.INTEGER);
	q.addScalar("idDettPosizioneDebitoria", Hibernate.INTEGER);
	q.addScalar("stato", Hibernate.STRING);
    }

    @Override
    public String buildQuery() {

	String sql = "select " +
		" blacklist_motivi.id as idBlackListMotivi," +
		" mercatipresenze_d.id as idMercatiPresenzeD," +
		" blacklist_autorizzazioni.fk_id_autorizzazione as idAutBlackList," +
		" mercatipresenze_d.fk_autorizzazioni_id as idAutMercatiPresenzeD," +
		" blacklist_src_p_deb_sp.id as idBlackListSrcPDebSp," +
		" dett_posizione_debitoria.id as idDettPosizioneDebitoria," +
		" dett_posizione_debitoria.stato " +
		"from" +
		" blacklist_motivi" +
		"   inner join blacklist_src_p_deb_sp on" +
		"      blacklist_motivi.idcomune = blacklist_src_p_deb_sp.idcomune and" +
		"      blacklist_motivi.id = blacklist_src_p_deb_sp.fk_id_blacklist_mot" +
		"   left join dett_posizione_debitoria on" +
		"      blacklist_src_p_deb_sp.idcomune = dett_posizione_debitoria.idcomune and" +
		"      blacklist_src_p_deb_sp.fk_id_paypos_deb = dett_posizione_debitoria.id" +
		"   left join blacklist_autorizzazioni on" +
		"      blacklist_motivi.idcomune = blacklist_autorizzazioni.idcomune and" +
		"      blacklist_motivi.id = blacklist_autorizzazioni.fk_id_blacklist_mot and" +
		"      blacklist_autorizzazioni.flag_principale = ?" +
		"   left join mercatipresenze_d ";
	if (DialettoEnum.MYSQL.equals(this._dialetto)) {
	    sql += "     force index for join (mercatipresenzed_002) ";
	}
	sql += "     on" +
		"       dett_posizione_debitoria.idcomune = mercatipresenze_d.idcomune and" +
		"       dett_posizione_debitoria.id = mercatipresenze_d.fk_pay_pos_deb " +
		"where" +
		" blacklist_motivi.idcomune = ? and" +
		" blacklist_motivi.data_fine_bl is null";
	int position = 0;
	parameters.add(new ParameterHelper(position, FLAG_PRINCIPALE, new IntegerType()));
	position++;
	parameters.add(new ParameterHelper(position, ORMHelper.getIdcomune(), new StringType()));
	if (StringUtils.isNotBlank(schemaName)) {
	    sql = sql.replaceAll(SCHEMA_NAME, schemaName + ".");
	}
	log.debug("{}#buildQuery: {}", getClass().getSimpleName(), sql);
	return sql;
    }
}
